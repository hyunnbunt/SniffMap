package sniffmap.api;

import sniffmap.service.DogService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sniffmap.dto.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DogApiController {
    private final DogService dogService;

    /** Show all dogs (nearby). */
    @GetMapping("dogs")
    public List<DogDto> showDogs() {
        return dogService.showDogs();
    }

    /** Show a dog's profile. */
    @GetMapping("dogs/{id}")
    public ResponseEntity<DogDto> showDogProfile(@PathVariable Long id) {
        try {
           return ResponseEntity.status(HttpStatus.OK).body(dogService.showDogProfile(id));
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /** Register a new dog profile. */
    @PostMapping("dogs")
    public ResponseEntity<DogDto> createDog(@RequestBody @Validated DogCreateDto dogCreateDto) {
       try {
           return ResponseEntity.status(HttpStatus.OK).body(dogService.createDog(dogCreateDto));
       } catch (IllegalArgumentException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }

    /** Update some basic fields of a dog's profile. Can't update organizing/participating events, dog friends, or happiness points. */
    @PatchMapping("dogs/{id}")
    public ResponseEntity<DogDto> updateDog(@PathVariable Long id, @RequestBody DogUpdateDto dogUpdateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(dogService.updateDog(id, dogUpdateDto));
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    /** Delete a dog's profile. */
    @DeleteMapping("dogs/{id}")
    public ResponseEntity<DogDto> deleteDog(@PathVariable Long id) {
        try {
            return  ResponseEntity.status(HttpStatus.OK).body(dogService.deleteDog(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /** A dog joins an event. */
    @PatchMapping("dog-event-participation")
    public ResponseEntity<DogDto> joinEvent(@RequestBody DogEventUpdateDto dogEventUpdateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(dogService.joinEvent(dogEventUpdateDto));
        } catch (EntityNotFoundException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e2) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("dog-event-cancellation")
    public ResponseEntity<DogDto> cancelEvent(@RequestBody DogEventUpdateDto dogEventUpdateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(dogService.cancelEvent(dogEventUpdateDto));
        } catch (EntityNotFoundException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e2) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("dog-location-registration")
    public  ResponseEntity<LocationDto> joinLocation(@RequestBody DogLocationUpdateDto dogLocationUpdateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(dogService.joinLocation(dogLocationUpdateDto));
        } catch (EntityNotFoundException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e2) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
