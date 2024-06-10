package sniffmap.dto;

import sniffmap.entity.Dog;
import sniffmap.entity.Location;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class LocationDto {
    @NotNull
    Long id;
    Double latitude;
    Double longitude;
    Set<Long> walkingDogIds;

    public static LocationDto fromEntity(Location updatedLocation) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(updatedLocation.getNumber());
        locationDto.setLatitude(updatedLocation.getLatitude());
        locationDto.setLongitude(updatedLocation.getLongitude());
        Set<Dog> walkingDogs = updatedLocation.getWalkingDogs();
        locationDto.setWalkingDogIds(
                walkingDogs.stream().map(Dog::getNumber).
                        collect(Collectors.toSet())
        );
        return locationDto;
    }

}
