package sniffmap.service;


import sniffmap.dto.LocationCreateDto;
import sniffmap.dto.LocationDto;
import sniffmap.entity.Dog;
import sniffmap.entity.Location;
import sniffmap.repository.DogRepository;
import sniffmap.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
public class LocationService {

    private final LocationRepository locationRepository;
    private final DogRepository dogRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, DogRepository dogRepository) {
        this.locationRepository = locationRepository;
        this.dogRepository = dogRepository;
    }

    /** Show a list of locations. */
    public List<LocationDto> showLocationsList() {
        List<Location> locationList = locationRepository.findAll();
        return locationList.stream().map(LocationDto::fromEntity).toList();
    }

    /** Show a detail of a location. */
    public LocationDto showLocationsDetail(Long locationId) throws EntityNotFoundException {
        Location location = locationRepository.findById(locationId).orElseThrow(EntityNotFoundException::new);
        return LocationDto.fromEntity(location);
    }

    @Transactional
    /** Add a new walk location. */
    public LocationDto createLocation(@RequestBody LocationCreateDto locationCreateDto) throws EntityNotFoundException, IllegalArgumentException {
        Location location = this.toEntity(locationCreateDto);
        Location created = locationRepository.save(location);
        return LocationDto.fromEntity(created);
    }

    /** Convert an instance of LocationCreateDto to an instance of Location Entity. Used in createLocation method. */
    public Location toEntity(@RequestBody LocationCreateDto locationCreateDto) throws EntityNotFoundException, IllegalArgumentException {
        Location location = new Location(locationCreateDto.getLatitude(), locationCreateDto.getLongitude());
        Dog dog = dogRepository.findById(locationCreateDto.getCreatorDogId()).orElseThrow(EntityNotFoundException::new);
        dog.joinLocation(location);
        dogRepository.save(dog);
        return location;
    }

    @Transactional
    public LocationDto deleteLocation(Long locationId) throws EntityNotFoundException {
        Location target = locationRepository.findById(locationId).orElseThrow(EntityNotFoundException::new);
        locationRepository.delete(target);
        return LocationDto.fromEntity(target);
    }
}
