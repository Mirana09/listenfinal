package listen.api;

import listen.business.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListenController {

    @Autowired
    ReadService readService;

    @Autowired
    FeedService feedService;



    @Autowired
    CSVService csvService;

    @GetMapping("/feed")
    public List<Email> feed() {
        return feedService.afficher();
    }

    @GetMapping("/feedIntro")
    public String feedIntro() {
        return feedService.afficherIntro();
    }

    @PostMapping("/listen")
    public void play(@RequestBody User user) throws Exception {
        readService.play(user);
    }

    @PostMapping("/create")
    public void create(@RequestBody User user) throws Exception {
        csvService.create(user);
    }

}
