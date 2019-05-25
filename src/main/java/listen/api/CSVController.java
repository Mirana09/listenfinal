package listen.api;

import listen.business.FeedCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class CSVController {

    @Autowired
    FeedCSVService feedCSVService;

    @GetMapping("/feedCsvRecus")
    public File feedCsvRecus() {
        return feedCSVService.feedCsvRecus();
    }

    @GetMapping("/feedCsvEnvoyes")
    public File feedCsvEnvoyes() {
        return feedCSVService.feedCsvEnvoyes();
    }

}
