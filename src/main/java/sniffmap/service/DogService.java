package sniffmap.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sniffmap.dto.*;
import sniffmap.entity.Dog;
import sniffmap.entity.Event;
import sniffmap.entity.Location;
import sniffmap.entity.Parent;
import sniffmap.repository.DogRepository;
import sniffmap.repository.EventRepository;
import sniffmap.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import sniffmap.repository.ParentRepository;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DogService {
    private final DogRepository dogRepository;
    private final ParentRepository parentRepository;
    private final ParentProfileService parentProfileService;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

//    public List<DogDto> showDogs() {
//        List<Dog> dogList = dogRepository.findAll();
//        return dogList.stream().map(DogDto::fromEntity).toList();
//    }

    public DogDto showDogProfile(@PathVariable Long number) throws IllegalArgumentException {
        Dog dog = dogRepository.findByNumber(number).orElseThrow(IllegalArgumentException::new);
        return DogDto.fromEntity(dog);
    }

    @Transactional
    public DogDto registerDogProfile(@RequestBody DogRegistrationDto dogRegistrationDto) {
        Dog dog = this.toEntity(dogRegistrationDto);
        Dog created = dogRepository.save(dog);
        return DogDto.fromEntity(created);
    }

    @Transactional
    public DogDto updateDogProfile(@PathVariable Long number, @RequestBody DogUpdateDto dogUpdateDto) throws IllegalArgumentException {
        Dog target = dogRepository.findByNumber(number).orElseThrow(IllegalArgumentException::new);
        this.patch(target, dogUpdateDto);
        Dog updated = dogRepository.save(target);
        return DogDto.fromEntity(updated);
    }

    @Transactional
    public DogDto deleteDogProfile(@PathVariable Long number) throws IllegalArgumentException {
        Dog target = dogRepository.findByNumber(number).orElseThrow(IllegalArgumentException::new);
        target.emptyFriendsList();
        dogRepository.delete(target);
        return DogDto.fromEntity(target);
    }

    /** Patch a Dog instance with a DogUpdateDto instance's data. Used in updateDog method. */
    public void patch(Dog target, DogUpdateDto dogUpdateDto) {
        if (dogUpdateDto.getName() != null) {
            target.setName(dogUpdateDto.getName());
        }
        if (dogUpdateDto.getAge() != null) {
            target.setAge(dogUpdateDto.getAge());
        }
        if (dogUpdateDto.getWeight() != null) {
            target.setWeight(dogUpdateDto.getWeight());
        }
        if (dogUpdateDto.getSex() != null) {
            target.setSex(dogUpdateDto.getSex());
        }
    }

    public void validateUserDog(String username, Long dogNumber) throws IllegalArgumentException {
        Parent parent = parentRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        boolean containsDog = false;
        for (Dog dog : parent.getDogs()) {
            if (dog.getNumber().equals(dogNumber)) {
                containsDog = true;
            }
        }
        if (!containsDog) {
            throw new IllegalArgumentException();
        }
    }

    /** Convert a DogCreateDto instance to a Dog instance. Used in createDog method. */
    private Dog toEntity(DogRegistrationDto dogCreateDto) {
        Parent parent = parentRepository.findByUsername(dogCreateDto.getParentName()).orElseThrow();
        return Dog.builder()
                .parent(parent)
                .name(dogCreateDto.getParentName())
                .age(dogCreateDto.getAge())
                .weight(dogCreateDto.getWeight())
                .sex(dogCreateDto.getSex()).build();
    }

    @Transactional
    public List<DogDto> showFriends(@PathVariable Long number) throws IllegalArgumentException {
        Dog dog = dogRepository.findByNumber(number).orElseThrow(IllegalArgumentException::new);
        return dog.getFriends().stream().map(DogDto::fromEntity).toList();
    }

    @Transactional
    public DogDto makeFriends(@PathVariable Long dogNumber, @RequestBody Long friendNumber) throws IllegalArgumentException {
        Dog dog = dogRepository.findByNumber(dogNumber).orElseThrow(IllegalArgumentException::new);
        Dog friend = dogRepository.findByNumber(friendNumber).orElseThrow(IllegalArgumentException::new);
        if (!dog.getFriends().add(friend)) {
            throw new IllegalArgumentException("Failed to make friends.");
        }
//        dogRepository.save(dog);
//        Dog newFriend = dogRepository.save(friend);
        return DogDto.fromEntity(friend);
    }

    @Transactional
    public DogDto cancelFriends(@PathVariable Long dogNumber, @RequestBody Long friendNumber) throws IllegalArgumentException {
        Dog dog = dogRepository.findByNumber(dogNumber).orElseThrow(IllegalArgumentException::new);
        Dog friend = dogRepository.findByNumber(friendNumber).orElseThrow(IllegalArgumentException::new);
        boolean removeFriend = dog.getFriends().remove(friend);
//        boolean removeDog = friend.getFriends().remove(dog);
//        if (!removeFriend || !removeDog) {
//            throw new IllegalArgumentException("Can't remove each other from friends list.");
//        }
        if (!removeFriend) {
            throw new IllegalArgumentException("Can't remove each other from friends list.");
        }
//        dogRepository.save(dog);
//        Dog exFriend = dogRepository.save(friend);
        return DogDto.fromEntity(friend);
    }


    @Transactional
    public DogDto joinEvent(@PathVariable Long dogNumber, @RequestBody Long eventNumber) throws IllegalArgumentException {
        Dog dog = dogRepository.findByNumber(dogNumber).orElseThrow(IllegalArgumentException::new);
        Event event = eventRepository.findById(eventNumber).orElseThrow(IllegalArgumentException::new);
        if (!dog.addEvent(event)) {
            throw new IllegalArgumentException("Can't join the event.");
        }
        Dog eventAdded = dogRepository.save(dog);
        return DogDto.fromEntity(eventAdded);
    }

    @Transactional
    public DogDto cancelEvent(@PathVariable Long dogNumber, @RequestBody Long eventNumber) throws IllegalArgumentException {
        Dog dog = dogRepository.findByNumber(dogNumber).orElseThrow(IllegalArgumentException::new);
        Event event = eventRepository.findById(eventNumber).orElseThrow(IllegalArgumentException::new);
        if (!dog.removeEvent(event)) {
            throw new IllegalArgumentException("Can't cancel the event.");
        }
        Dog eventRemoved = dogRepository.save(dog);
        return DogDto.fromEntity(eventRemoved);
    }

    @Transactional
    public LocationDto joinLocation(@PathVariable Long dogNumber, @RequestBody Long locationNumber) throws IllegalArgumentException {
        Dog dog = dogRepository.findByNumber(dogNumber).orElseThrow(IllegalArgumentException::new);
        Location location = locationRepository.findById(locationNumber).orElseThrow(IllegalArgumentException::new);
        if (!dog.joinLocation(location)) {
            throw new IllegalArgumentException("Can't join the location.");
        }
//        dogRepository.save(dog);
//        Location joined = locationRepository.save(location);
        return LocationDto.fromEntity(location);
    }

    @Transactional
    public LocationDto cancelLocation(@PathVariable Long dogNumber, @RequestBody Long locationNumber) throws IllegalArgumentException {
        Dog dog = dogRepository.findByNumber(dogNumber).orElseThrow(IllegalArgumentException::new);
        Location location = locationRepository.findById(locationNumber).orElseThrow(IllegalArgumentException::new);
        if (!dog.cancelLocation(location)) {
            throw new IllegalArgumentException("Can't cancel the location.");
        }
//        dogRepository.save(dog);
//        Location joined = locationRepository.save(location);
        return LocationDto.fromEntity(location);
    }
}
