package sniffmap.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import sniffmap.domain.CustomUserDetails;
import sniffmap.dto.EventCreateDto;
import sniffmap.dto.EventDto;
import sniffmap.dto.EventUpdateDto;
import sniffmap.service.EventService;
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
public class EventApiController {
    private final EventService eventService;
//
//    @GetMapping("/events")
//    public List<EventDto> showEvents() {
//        return eventService.showEvents();
//    }

    @GetMapping("/events/{eventNumber}")
    public ResponseEntity<EventDto> showEventDetail(@PathVariable Long eventNumber) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.showEventDetail(eventNumber));
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/events")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventCreateDto eventCreateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.createEvent(eventCreateDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/events/{eventNumber}")
    public ResponseEntity<EventDto> updateEvent(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long eventNumber, @RequestBody EventUpdateDto eventUpdateDto) {
        try {
            eventService.validateOrganizingEvent(customUserDetails.getUsername(), eventNumber);
            return ResponseEntity.status(HttpStatus.OK).body(eventService.updateEvent(eventNumber, eventUpdateDto));
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/events/{eventNumber}")
    public ResponseEntity<EventDto> deleteEvent(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long eventNumber) {
        try {
            eventService.validateOrganizingEvent(customUserDetails.getUsername(), eventNumber);
            return ResponseEntity.status(HttpStatus.OK).body(eventService.deleteEvent(eventNumber));
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
