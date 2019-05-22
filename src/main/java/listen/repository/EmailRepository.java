package listen.repository;

import listen.business.Email;

import java.util.List;

public interface EmailRepository {

    void vider();

    List<Email> findAll();
    void save(Email email);

    String findIntro();
    void saveIntro(String intro);

}
