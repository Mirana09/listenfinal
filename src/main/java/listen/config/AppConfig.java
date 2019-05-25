package listen.config;

import listen.business.*;
import listen.repository.CSVRepository;
import listen.repository.CSVRepositoryImpl;
import listen.repository.EmailRepository;
import listen.repository.InMemoryEmail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    ReadService readService() {
        return new ReadServiceImpl(repository());
    }

    @Bean
    FeedService feedService() {
        return new FeedServiceImpl(repository());
    }

    @Bean
    CSVService csvService() {
        return new CSVServiceImpl(csvrepository());
    }

    @Bean
    FeedCSVService feedCSVService() {
        return new FeedCSVServiceImpl(csvrepository());
    }

    @Bean
    EmailRepository repository() {
        return new InMemoryEmail();
    }

    @Bean
    CSVRepository csvrepository() {
        return new CSVRepositoryImpl();
    }

}
