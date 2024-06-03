package sniffmap.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
public class EventUpdateDto {
    Long date;
    Long time;
    Double latitude;
    Double longitude;
}
