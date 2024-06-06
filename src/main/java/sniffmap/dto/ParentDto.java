package sniffmap.dto;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import sniffmap.entity.Parent;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Builder
public class ParentDto {
    Long number;
    @NotNull
    String username;
    @NotNull
    String email;
    Set<DogDto> dogs;
    Long ownerPoints;

    public static ParentDto fromEntity(Parent parent) {
        return ParentDto.builder()
                .number(parent.getNumber())
                .username(parent.getUsername())
                .email(parent.getEmail())
                .dogs(parent.getDogs().stream().map(DogDto::fromEntity).collect(Collectors.toSet()))
                .ownerPoints(parent.getOwnerPoints())
                .build();
    }
}
