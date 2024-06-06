package sniffmap.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Slf4j
@Builder
public class Dog {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long number;
    @ManyToOne
    @JoinColumn(nullable = false)
    Parent parent;
    @Column
    String name;
    @Column
    Double age;
    @Column
    Double weight;
    @Column
    String sex;
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Dog> friends;
    @Column
    Integer happinessPoints;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organizerDog", cascade = CascadeType.REMOVE)
    Set<Event> organizingEvents;
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Event> participatingEvents;
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Location> walkLocations;

    public Dog(Parent parent, String name, Double age, Double weight, String sex) {
        this.parent = parent;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.sex = sex;
    }

    public boolean addEvent(Event targetEvent) {
        if (this.participatingEvents == null) {
            this.participatingEvents = new HashSet<>();
        }
        if (this.participatingEvents.contains(targetEvent)) {
            return false;
        }
        this.participatingEvents.add(targetEvent);
        return true;
    }

    public boolean removeEvent(Event targetEvent) {
        if (this.participatingEvents == null) {
            return false;
        }
        return this.participatingEvents.remove(targetEvent);
    }

    public void emptyFriendsList() {
        Set<Dog> dogFriends = this.getFriends();
        for (Dog friend : dogFriends) {
            friend.getFriends().remove(this);
        }
        this.setFriends(null);
    }

    public boolean joinLocation(Location location) {
        return this.getWalkLocations().add(location);
    }

    public boolean cancelLocation(Location location) { return this.getWalkLocations().remove(location);}
}
