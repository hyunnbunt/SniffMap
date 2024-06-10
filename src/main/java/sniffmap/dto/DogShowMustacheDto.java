//package sniffmap.dto;
//
//import sniffmap.entity.Owner;
//import sniffmap.service.ParentProfileService;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@NoArgsConstructor
//@Setter
//@Getter
//@Component
//public class DogShowMustacheDto {
//    Long id;
//    String dogsOwnerName;
//    String name;
//    Double age;
//    Double weight;
//    String sex;
//    static ParentProfileService parentProfileService;
//
//    @Autowired
//    public DogShowMustacheDto(ParentProfileService parentProfileService) {
//        DogShowMustacheDto.parentProfileService = parentProfileService;
//    }
//    public static DogShowMustacheDto fromDogProfileDto(DogDto dogDto) {
//        Long dogsOwnerId = dogDto.getOwnerId();
//        Parent parent = parentProfileService.(dogsOwnerId);
//
//        DogShowMustacheDto dogShowMustacheDto = new DogShowMustacheDto();
//        dogShowMustacheDto.setId(dogDto.getId());
//        dogShowMustacheDto.setDogsOwnerName(owner.getEmail());
//        dogShowMustacheDto.setName(dogDto.getName());
//        dogShowMustacheDto.setAge(dogDto.getAge());
//        dogShowMustacheDto.setWeight(dogDto.getWeight());
//        dogShowMustacheDto.setSex(dogDto.getSex());
//        return dogShowMustacheDto;
//    }
//
//}
