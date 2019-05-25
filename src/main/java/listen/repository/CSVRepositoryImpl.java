package listen.repository;

import java.io.File;

public class CSVRepositoryImpl implements CSVRepository{

    private File messages_recus;
    private File messages_envoyes;

    public void save_messages_recus(File messages_recus) {
        this.messages_recus = messages_recus;
    }

    public void save_messages_envoyes(File messages_envoyes) {
        this.messages_envoyes = messages_envoyes;
    }

    public File find_fichier_recus(){
        return messages_recus;
    }

    public File find_fichier_envoyes(){
        return messages_envoyes;
    }
}
