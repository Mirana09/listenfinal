package listen.api;

import listen.business.Email;
import listen.business.FeedService;
import listen.business.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListenController {

    @Autowired
    ReadService readService;

    @Autowired
    FeedService feedService;

    @GetMapping("/feed")
    public List<Email> feed() {
        return feedService.afficher();
    }

    @GetMapping("/feedIntro")
    public String feedIntro() {
        return feedService.afficherIntro();
    }

    @PostMapping("/listen")
    public void play() throws Exception {
        String host = "imap.gmail.com";
        String username = "listen2.mail.s8@gmail.com";
        String motdepasse = "projets8%";
        readService.play(host,username,motdepasse);
    }

}
