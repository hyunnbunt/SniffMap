package sniffmap.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor // Entity는 항상 defaul constructor가 필요
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long number;
    @Column(nullable = false)
    Long date;
    @Column(nullable = false)
    Long time;
    @Column(nullable = false)
    Double latitude;
    @Column(nullable = false)
    Double longitude;

    @ManyToOne(optional = true)
    @JoinColumn // (nullable = false)
            @JsonIgnore
    Parent organizer;

    @ManyToMany(mappedBy = "participatingEvents")
    @JsonIgnore
    Set<Dog> participantDogs;

    public Event(Long id, Long date, Long time, Double latitude, Double longitude) {
        this.number = id;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "Event{" +
                "date='" + this.date + '\'' +
                '}';
    }

    public boolean patch(Event event) throws IllegalArgumentException {
        if (event.getDate() != null) {
            this.setDate(event.getDate());
        }
        if (event.getTime() != null) {
            this.setTime(event.getTime());
        }
        if (event.getLatitude() != null) {
            this.setLatitude(event.getLatitude());
        }
        if (event.getLongitude() != null) {
            this.setLongitude(event.getLongitude());
        }
        return event.getParticipantDogs() == null;
    }

    public boolean addParticipatingDog(Dog dog) {
        if (this.participantDogs == null) {
            this.participantDogs = new HashSet<>();
        }
        if (participantDogs.contains(dog)) {
            return false;
        }
        participantDogs.add(dog);
        return true;
    }
}
