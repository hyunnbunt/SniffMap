package sniffmap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DogEventUpdateDto {
    @NotNull
    Long dogId;
    @NotNull
    Long targetEventId;
}
