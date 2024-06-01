package sniffmap.api;

import sniffmap.dto.DogDto;
import sniffmap.dto.DogFriendDto;
import sniffmap.service.DogService;
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
public class DogFriendsApiController {
    private final DogService dogService;
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
    @PostMapping("dogs/{dogId}/friends")
    public ResponseEntity<DogDto> makeFriends(@PathVariable("dogId") Long dogId, @RequestBody @Validated DogFriendDto dogFriendDto) {
        try {
           return ResponseEntity.status(HttpStatus.OK).body(dogService.makeFriends(dogId, dogFriendDto));
        } catch (EntityNotFoundException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e2) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("dogs/{dogId}/friends")
    public ResponseEntity<DogDto> cancelFriends(@PathVariable Long dogId, @RequestBody DogFriendDto dogFriendDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(dogService.cancelFriends(dogId, dogFriendDto));
        } catch (EntityNotFoundException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e2) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
