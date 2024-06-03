package sniffmap.dto;

import sniffmap.entity.Dog;
import sniffmap.entity.Owner;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Builder
public class OwnerDto {
    @NotNull
    Long id;
    String name;
    Set<Dog> dogs;
    Long ownerPoints;

    public static OwnerDto fromEntity(Owner owner) {
        return OwnerDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .dogs(owner.getDogs())
                .ownerPoints(owner.getOwnerPoints())
                .build();
//        return new OwnerDto(owner.getId(), owner.getName(), owner.getDogs(), owner.getOwnerPoints());
    }
}
