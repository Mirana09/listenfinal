package listen.business;

import listen.repository.CSVRepository;

import java.io.File;

public class FeedCSVServiceImpl implements FeedCSVService {

    private CSVRepository csvRepository;

    public FeedCSVServiceImpl(CSVRepository csvRepository){
        this.csvRepository = csvRepository;
    }

    @Override
    public File feedCsvRecus() {
        return csvRepository.find_fichier_recus();
    }

    @Override
    public File feedCsvEnvoyes() {
        return csvRepository.find_fichier_envoyes();
    }
}
