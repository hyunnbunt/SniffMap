package sniffmap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import sniffmap.domain.CustomUserDetails;
import sniffmap.dto.ParentDto;
import sniffmap.entity.Dog;
import sniffmap.entity.Parent;
import sniffmap.repository.DogRepository;
import sniffmap.repository.ParentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParentProfileService {

    private final ParentRepository parentRepository;
    private final DogRepository dogRepository;

    public ParentDto showProfile(String username) {
        try {
            Parent parent = parentRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
            return ParentDto.fromEntity(parent);
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

    @Transactional
    public ParentDto registerProfile(CustomUserDetails customUserDetails) throws BadRequestException {
        Parent parent = Parent.fromUserDetails(customUserDetails);
        parent.setDogs(new HashSet<>());
        parent.setOwnerPoints(0L);
        Parent created = parentRepository.save(parent);
        return ParentDto.fromEntity(created);
    }

    @Transactional
    public ParentDto deleteProfile(String username) throws EntityNotFoundException {
        Parent target = parentRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        parentRepository.delete(target);
        Set<Dog> targetDogs = target.getDogs();
        for (Dog dog : targetDogs) {
            dogRepository.delete(dog);
        }
        return ParentDto.fromEntity(target);
    }
}
