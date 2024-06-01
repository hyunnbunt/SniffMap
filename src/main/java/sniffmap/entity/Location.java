package sniffmap.entity;

import sniffmap.dto.LocationCreateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    Double latitude;
    @Column(nullable = false)
    Double longitude;
    @ManyToMany(mappedBy = "walkLocations")
    @JsonIgnore
    Set<Dog> walkingDogs;


    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", walkingDogs=" + walkingDogs +
                '}';
    }

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean addWalkingDog(Dog dog) {
        if (this.walkingDogs == null) {
            this.walkingDogs = new HashSet<>();
        }
        if (walkingDogs.contains(dog)) {
            return false;
        }
        walkingDogs.add(dog);
        return true;
    }

    public boolean patch(LocationCreateDto locationCreateDto) {
        if (locationCreateDto.getLatitude() != null) {
            this.setLatitude(locationCreateDto.getLatitude());
        }
        if (locationCreateDto.getLongitude() != null) {
            this.setLongitude(locationCreateDto.getLongitude());
        }
        return locationCreateDto.getCreatorDogId() == null;
    }

    public boolean addLocation(Dog targetDog) {
        if (this.walkingDogs == null) {
            this.walkingDogs = new HashSet<>();
        }
        if (this.walkingDogs.contains(targetDog)) {
            return false;
        }
        this.walkingDogs.add(targetDog);
        return true;
    }
}
