package sniffmap.service;

import lombok.RequiredArgsConstructor;
import sniffmap.dto.EventCreateDto;
import sniffmap.dto.EventDto;
import sniffmap.dto.EventUpdateDto;
import sniffmap.entity.Dog;
import sniffmap.entity.Event;
import sniffmap.entity.Parent;
import sniffmap.repository.DogRepository;
import sniffmap.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import sniffmap.repository.ParentRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final DogRepository dogRepository;
    private final ParentRepository parentRepository;

    public List<EventDto> showEvents() {
        return eventRepository.findAll().stream().map(EventDto::fromEntity).toList();
    }

    public EventDto showEventDetail(@PathVariable Long id) throws EntityNotFoundException {
        Event event = eventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return EventDto.fromEntity(event);
    }

    @Transactional
    public EventDto createEvent(@RequestBody EventCreateDto eventCreateDto) throws EntityNotFoundException, IllegalArgumentException {
        Dog creatorDog = dogRepository.findById(eventCreateDto.getCreatorDogId()).orElseThrow(EntityNotFoundException::new);
        Event event = eventCreateDto.toEntity();
        event.addParticipatingDog(creatorDog);
        Event created = eventRepository.save(event);
        return EventDto.fromEntity(created);
    }

    @Transactional
    public EventDto updateEvent(@PathVariable Long eventId, @RequestBody EventUpdateDto eventUpdateDto) throws EntityNotFoundException {
        Event target = eventRepository.findById(eventId).orElseThrow(EntityNotFoundException::new);
        patchEvent(target, eventUpdateDto);
        Event updated = eventRepository.save(target);
        return EventDto.fromEntity(updated);
    }

    @Transactional
    public void patchEvent(Event event, EventUpdateDto eventUpdateDto) {
        if (eventUpdateDto.getDate() != null) {
            event.setDate(eventUpdateDto.getDate());
        }
        if (eventUpdateDto.getTime() != null) {
            event.setTime(eventUpdateDto.getTime());
        }
        if (eventUpdateDto.getLatitude() != null) {
            event.setLatitude(eventUpdateDto.getLatitude());
        }
        if (eventUpdateDto.getLongitude() != null) {
            event.setLongitude(eventUpdateDto.getLongitude());
        }
    }

    @Transactional
    public EventDto deleteEvent(@PathVariable Long eventId) throws EntityNotFoundException {
        Event target = eventRepository.findById(eventId).orElseThrow(EntityNotFoundException::new);
        eventRepository.delete(target);
        return EventDto.fromEntity(target);
    }

    public void validateOrganizingEvent(String username, Long eventNumber) {
        Parent parent = parentRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
        boolean containsEvent = false;
        for (Event event : parent.getOrganizingEvents()) {
            if (event.getNumber().equals(eventNumber)) {
                containsEvent = true;
            }
        }
        if (!containsEvent) {
            throw new IllegalArgumentException();
        }
    }
}
