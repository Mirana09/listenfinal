package listen.config;

import listen.business.FeedService;
import listen.business.FeedServiceImpl;
import listen.business.ReadService;
import listen.business.ReadServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import listen.repository.InMemoryEmail;
import listen.repository.EmailRepository;

@Configuration
public class AppConfig {

    @Bean
    ReadService connexionGmail() {
        return new ReadServiceImpl(repository());
    }

    @Bean
    FeedService feedService() {
        return new FeedServiceImpl(repository());
    }

    @Bean
    EmailRepository repository() {
        return new InMemoryEmail();
    }

}
