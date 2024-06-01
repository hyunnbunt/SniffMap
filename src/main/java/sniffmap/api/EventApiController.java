package sniffmap.api;

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

    @GetMapping("/events")
    public List<EventDto> showEvents() {
        return eventService.showEvents();
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventDto> showEventDetail(@PathVariable Long eventId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.showEventDetail(eventId));
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/events")
    public ResponseEntity<EventDto> createEvent(@RequestBody @Validated EventCreateDto eventCreateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.createEvent(eventCreateDto));
        } catch (EntityNotFoundException e1) {
            log.info(e1.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e2) {
            log.info(e2.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/events/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long eventId, @RequestBody EventUpdateDto eventUpdateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.updateEvent(eventId, eventUpdateDto));
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<EventDto> deleteEvent(@PathVariable Long eventId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.deleteEvent(eventId));
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
