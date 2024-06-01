package sniffmap.controller;

import sniffmap.dto.DogDto;
import sniffmap.dto.DogShowMustacheDto;
import sniffmap.service.DogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DogsController {

    private final DogService dogService;

    @GetMapping("bunt-project/dogs")
    public String showDogs(Model model) {
        List<DogDto> dogs = dogService.showDogs();
        List<DogShowMustacheDto> dogShowMustacheDtos =
                dogs.stream().map(dogProfileDto -> DogShowMustacheDto.fromDogProfileDto(dogProfileDto))
                                .toList();

        model.addAttribute("dogs", dogShowMustacheDtos);

        return "dogs/showDogs";
    }
}
