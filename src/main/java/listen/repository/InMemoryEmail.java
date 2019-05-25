package listen.repository;

import listen.business.Email;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEmail implements EmailRepository {

    private String intro;
    private List<Email> messages = new ArrayList<Email>();

    public void vider(){
        messages = new ArrayList<Email>();
    }

    public List<Email> findAll() {
        return messages;
    }

    public String findIntro() {
        return intro;
    }

    public void save(Email email) {
        messages.add(email);
    }

    public void saveIntro(String intro) {
        this.intro = intro;
    }

}
