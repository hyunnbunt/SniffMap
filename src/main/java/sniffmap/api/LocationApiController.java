package sniffmap.api;

import sniffmap.dto.LocationDto;
import sniffmap.dto.LocationCreateDto;
import sniffmap.service.LocationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LocationApiController {

    private final LocationService locationService;

    /** Show the list of locations. */
    @GetMapping("locations")
    public List<LocationDto> showLocationsList() {
        return locationService.showLocationsList();
    }

    /** Show a location's detail. */
    @GetMapping("locations/{locationId}")
    public ResponseEntity<LocationDto> showLocationDetail(@PathVariable Long locationId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(locationService.showLocationsDetail(locationId));
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /** Add a new walk location. */
    @PostMapping("locations")
    public ResponseEntity<LocationDto> createLocation(@RequestBody @Validated LocationCreateDto locationCreateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(locationService.createLocation(locationCreateDto));
        } catch (EntityNotFoundException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e2) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("locations/{locationId}")
    public ResponseEntity<LocationDto> deleteLocation(@PathVariable Long locationId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(locationService.deleteLocation(locationId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}