package sniffmap.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import sniffmap.domain.CustomUserDetails;
import sniffmap.repository.ParentRepository;
import sniffmap.service.DogService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sniffmap.dto.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DogApiController {

    private final DogService dogService;
    private final ParentRepository parentRepository;

//    /** Show all dogs nearby. */
//    @GetMapping("dogs")
//    public List<DogDto> showDogs() {
//        // Not implemented yet.
//        return dogService.showDogs();
//    }

    /** Show a dog's profile. */
    @GetMapping("dogs/{number}")
    public ResponseEntity<DogDto> showDogProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long dogNumber) {
        try {
            dogService.validateUserDog(customUserDetails.getUsername(), dogNumber);
           return ResponseEntity.status(HttpStatus.OK).body(dogService.showDogProfile(dogNumber));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /** Register a new dog profile. */
    @PostMapping("dogs")
    public ResponseEntity<DogDto> registerDogProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody DogRegistrationDto dogRegistrationDto) {
       try {
           if (!customUserDetails.getUsername().equals(dogRegistrationDto.getParentName())) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
           }
           return ResponseEntity.status(HttpStatus.OK).body(dogService.registerDogProfile(dogRegistrationDto));
       } catch (IllegalArgumentException e) {
           log.info(e.getMessage());
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }

    /** Update some basic fields of a dog's profile. Can't update organizing/participating events, dog friends, or happiness points. */
    @PatchMapping("dogs/{number}")
    public ResponseEntity<DogDto> updateDogProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long dogNumber, @RequestBody DogUpdateDto dogUpdateDto) {
        try {
            dogService.validateUserDog(customUserDetails.getUsername(), dogNumber);
            return ResponseEntity.status(HttpStatus.OK).body(dogService.updateDogProfile(dogNumber, dogUpdateDto));
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    /** Delete a dog's profile. */
    @DeleteMapping("dogs/{number}")
    public ResponseEntity<DogDto> deleteDogProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long dogNumber) {
        try {
            dogService.validateUserDog(customUserDetails.getUsername(), dogNumber);
            return  ResponseEntity.status(HttpStatus.OK).body(dogService.deleteDogProfile(dogNumber));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /** A dog joins an event. */
    @PatchMapping("dogs/{number}/events")
    public ResponseEntity<DogDto> joinEvent(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long dogNumber, @RequestBody Long eventNumber) {
        try {
            dogService.validateUserDog(customUserDetails.getUsername(), dogNumber);
            return ResponseEntity.status(HttpStatus.OK).body(dogService.joinEvent(dogNumber,eventNumber));
        } catch (EntityNotFoundException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e2) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("dogs/{number}/events")
    public ResponseEntity<DogDto> cancelEvent(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long dogNumber, @RequestBody Long eventNumber) {
        try {
            dogService.validateUserDog(customUserDetails.getUsername(), dogNumber);
            return ResponseEntity.status(HttpStatus.OK).body(dogService.cancelEvent(dogNumber, eventNumber));
        } catch (EntityNotFoundException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e2) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("dogs/{number}/events")
    public  ResponseEntity<LocationDto> joinLocation(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long dogNumber, @RequestBody Long locationNumber) {
        try {
            dogService.validateUserDog(customUserDetails.getUsername(), dogNumber);
            return ResponseEntity.status(HttpStatus.OK).body(dogService.joinLocation(dogNumber, locationNumber));
        } catch (EntityNotFoundException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e2) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /** Show all dog friends of a dog. */
    @GetMapping("dogs/{dogId}/friends")
    public ResponseEntity<List<DogDto>> showFriends(@PathVariable Long dogId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.dogService.showFriends(dogId));
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /** Make friends for two dogs. */
    @PostMapping("dogs/{number}/friends")
    public ResponseEntity<DogDto> makeFriends(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long dogNumber, @RequestBody Long friendNumber) {
        try {
            dogService.validateUserDog(customUserDetails.getUsername(), dogNumber);
            return ResponseEntity.status(HttpStatus.OK).body(dogService.makeFriends(dogNumber, friendNumber));
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("dogs/{number}/friends")
    public ResponseEntity<DogDto> cancelFriends(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long dogNumber, @RequestBody Long friendNumber) {
        try {
            dogService.validateUserDog(customUserDetails.getUsername(), dogNumber);
            return ResponseEntity.status(HttpStatus.OK).body(dogService.cancelFriends(dogNumber, friendNumber));
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
