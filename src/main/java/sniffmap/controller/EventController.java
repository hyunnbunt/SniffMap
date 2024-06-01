package sniffmap.controller;

import sniffmap.service.DogService;
import sniffmap.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final DogService dogService;

    @GetMapping("bunt-project/events")
    public String showEvents(Model model) {
//        List<EventDto> eventDtoList = eventService.showEvents();
//        if (eventDtoList.isEmpty()) {
//            log.info("No events");
//            return "events/noEvents/";
//        }
//        model.addAttribute("events", eventDtoList);
        return "events/showEvents/";
    }



    // RestAPI
    //    GET (/events) : see all today’s events
    //    POST (/events) : create a new event (body : organizer_Id = Dog.id}
    //    GET (/events/{eventId}) : see the detail of the event
    // PATCH (/dogs/{dogId}) : add an event to participating event column

}
