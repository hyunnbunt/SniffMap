package sniffmap.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@Setter
@Getter
public class DogLocationUpdateDto {
    @NotNull
    Long dogId;
    @NotNull
    Long walkingLocation;
}
