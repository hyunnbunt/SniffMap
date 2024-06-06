package sniffmap.api;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import sniffmap.domain.CustomUserDetails;
import sniffmap.dto.ParentDto;
import sniffmap.service.ParentProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ParentProfileAPIController {

    private final ParentProfileService parentProfileService;

    @GetMapping("/my/profile")
    public ResponseEntity<ParentDto> showProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(parentProfileService.showProfile(customUserDetails.getUsername()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/my/profile")
    public ResponseEntity<ParentDto> registerProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(parentProfileService.registerProfile(customUserDetails));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/my/profile")
    public ResponseEntity<ParentDto> deleteProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(parentProfileService.deleteProfile(customUserDetails.getUsername()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
