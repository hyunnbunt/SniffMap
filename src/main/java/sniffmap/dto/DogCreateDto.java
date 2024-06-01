package sniffmap.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@Setter
@Getter
public class DogCreateDto {
    @NotNull
    Long ownerId;
    @NotNull
    String name;
    Double age;
    Double weight;
    String sex;
}
