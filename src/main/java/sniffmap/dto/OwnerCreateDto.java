package sniffmap.dto;

import sniffmap.entity.Owner;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class OwnerCreateDto {
    @NotNull
    String name;

    public Owner toEntity() {
        return Owner.builder()
                .name(this.getName())
                .build();
//        Owner owner = new Owner();
//        owner.setName(this.getName());
//        return owner;
    }
}
