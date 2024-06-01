package sniffmap.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class LocationCreateDto {
    @NotNull
    Double latitude;
    @NotNull
    Double longitude;
    @NotNull
    Long creatorDogId;
}
