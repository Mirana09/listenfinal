package listen.repository;

import java.io.File;

public interface CSVRepository {

    void save_messages_recus(File messages_recus);
    void save_messages_envoyes(File messages_envoyes);

    File find_fichier_recus();
    File find_fichier_envoyes();

}
