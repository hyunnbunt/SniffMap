package sniffmap.api;

import sniffmap.dto.OwnerCreateDto;
import sniffmap.dto.OwnerDto;
import sniffmap.service.OwnerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OwnerApiController {

    private final OwnerService ownerService;

    @GetMapping("owners/{ownerId}")
    public ResponseEntity<OwnerDto> showProfile(@PathVariable Long ownerId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ownerService.showProfile(ownerId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("owners")
    public ResponseEntity<OwnerDto> createProfile(@RequestBody @Validated OwnerCreateDto ownerCreateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ownerService.createProfile(ownerCreateDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("owners/{ownerId}")
    public ResponseEntity<OwnerDto> deleteProfile(@PathVariable Long ownerId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ownerService.deleteProfile(ownerId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
