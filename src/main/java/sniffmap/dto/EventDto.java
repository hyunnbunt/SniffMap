package sniffmap.dto;

import sniffmap.entity.Dog;
import sniffmap.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Slf4j
public class EventDto {
    @NotNull
    Long id;
    Long date;
    Long time;
    Double latitude;
    Double longitude;
    Set<Long> participantDogIds;

    public EventDto(Long date, Long time, Double latitude, Double longitude) {
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "EventDto{" +
                "date=" + date +
                ", time=" + time +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public static EventDto fromEntity(Event event) {
        Set<Long> participantDogIds;
        if (event.getParticipantDogs() == null) {
            participantDogIds = null;
        } else {
            participantDogIds = event.getParticipantDogs().stream().map(Dog::getNumber).collect(Collectors.toSet());
        }
        return new EventDto(event.getId(), event.getDate(), event.getTime(), event.getLatitude(), event.getLongitude(), participantDogIds);
    }
    public Event toEntity() {
        return new Event(this.getId(), this.getDate(), this.getTime(), this.getLatitude(), this.getLongitude());
    }
}
