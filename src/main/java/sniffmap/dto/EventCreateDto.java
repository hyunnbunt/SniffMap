package sniffmap.dto;

import sniffmap.entity.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@Setter
@Getter
public class EventCreateDto {
    @NotNull
    Long date;
    @NotNull
    Long time;
    @NotNull
    Double latitude;
    @NotNull
    Double longitude;
    @NotNull
    Long creatorDogId;

    public Event toEntity() {
        Event event = new Event();
        event.setDate(this.getDate());
        event.setTime(this.getTime());
        event.setLatitude(this.getLatitude());
        event.setLongitude(this.getLongitude());
        return event;
    }

}
