package listen.business;

import listen.repository.EmailRepository;

import java.util.List;

public class FeedServiceImpl implements FeedService{

    private EmailRepository emailRepository;

    public FeedServiceImpl(EmailRepository emailRepository){
        this.emailRepository = emailRepository;
    }

    public List<Email> afficher(){
        return emailRepository.findAll();
    }

    public String afficherIntro(){
        return emailRepository.findIntro();
    }

}
