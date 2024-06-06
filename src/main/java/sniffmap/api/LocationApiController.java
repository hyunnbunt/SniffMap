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
    @GetMapping("/locations")
    public List<LocationDto> showLocationsList() {
        return locationService.showLocationsList();
    }

    /** Show a location's detail. */
    @GetMapping("/locations/{number}")
    public ResponseEntity<LocationDto> showLocationDetail(@PathVariable Long number) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(locationService.showLocationsDetail(number));
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /** Add a new walk location. */
    @PostMapping("/locations")
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationCreateDto locationCreateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(locationService.createLocation(locationCreateDto));
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/locations/{number}")
    public ResponseEntity<LocationDto> deleteLocation(@PathVariable Long number) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(locationService.deleteLocation(number));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}