package sniffmap.dto;

import sniffmap.entity.Dog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DogUpdateDto {

    String name;
    Double age;
    Double weight;
    String sex;

    public Dog toEntity() {
        Dog dogEntity = new Dog();
        dogEntity.setName(this.name);
        dogEntity.setAge(this.age);
        dogEntity.setWeight(this.weight);
        dogEntity.setSex(this.sex);
        return dogEntity;
    }

    @Override
    public String toString() {
        return "DogUpdateDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", sex='" + sex + '\'' +
                '}';
    }
}
