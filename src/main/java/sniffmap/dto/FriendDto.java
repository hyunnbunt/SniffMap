package sniffmap.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sniffmap.entity.Dog;
import sniffmap.entity.Event;
import sniffmap.entity.Location;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class FriendDto {
    Long id;
    String name;
    Double age;
    Double weight;
    String sex;
    Set<Long> participatingEventIds;
    Set<Long> walkingLocationIds;
    public static FriendDto fromEntity(Dog dog) {
        return FriendDto.builder()
                .id(dog.getNumber())
                .name(dog.getName())
                .age(dog.getAge())
                .weight(dog.getWeight())
                .sex(dog.getSex())
                .participatingEventIds(dog.getParticipatingEvents().stream().map(Event::getId).collect(Collectors.toSet()))
                .walkingLocationIds(dog.getWalkLocations().stream().map(Location::getId).collect(Collectors.toSet())).build();
    }
}
