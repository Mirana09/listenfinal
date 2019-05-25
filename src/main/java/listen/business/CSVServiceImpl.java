package listen.business;

import com.opencsv.CSVWriter;
import listen.repository.CSVRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;

public class CSVServiceImpl implements CSVService{

    private CSVRepository csvRepository;

    public CSVServiceImpl(CSVRepository csvRepository){
        this.csvRepository = csvRepository;
    }

    @Override
    public void create(User user) throws Exception {
        File messages_envoyes = new File("src/main/resources/static/messages_envoyes.csv");
        create_csv_envoyes(user,messages_envoyes);
        csvRepository.save_messages_envoyes(messages_envoyes);

        File messages_recus = new File("src/main/resources/static/messages_recus.csv");
        create_csv_recus(user,messages_recus);
        csvRepository.save_messages_envoyes(messages_recus);
    }

    public void create_csv_envoyes(User user,File fichier) throws Exception {
        String host = "imap.gmail.com";
        String username = user.getEmail();
        String motdepasse = user.getPassword();

        try {
            Session session = Session.getDefaultInstance(new Properties());
            Store store = session.getStore("imaps");
            store.connect(host, username, motdepasse);

            Folder inbox = store.getFolder("[Gmail]/Messages envoyés");
            //store.getDefaultFolder().list("*");
            //Folder sent_message = store.getFolder("SENT");
            inbox.open(Folder.READ_WRITE);

            int messageCount = inbox.getMessageCount();

            Message[] lus = inbox.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), true));

            String expediteur = "";
            for (int i = 0; i < lus.length; i++) {
                // messages[i].getFrom()[0];
                //List<String> toAddresses = new ArrayList<String>();
                Address[] recipients = lus[i].getRecipients(Message.RecipientType.TO);
                for (Address address : recipients) {
                    //toAddresses.add(address.toString());
                    expediteur = expediteur + ";" + address.toString();
                }
                //if (i==0) expediteur = expediteur + lus[i].getFrom()[0];
                //else expediteur = expediteur + ";" + lus[i].getFrom()[0];
            }

            compterEmailEnvoyes(user,expediteur,fichier);

            inbox.close(true);
            store.close();
        }
        catch (MessagingException  e) {
            System.out.println(" dans le catch ");
            e.printStackTrace();
        }
    }

    public void create_csv_recus(User user,File fichier) throws Exception {
        String host = "imap.gmail.com";
        String username = user.getEmail();
        String motdepasse = user.getPassword();

        try {
            Session session = Session.getDefaultInstance(new Properties());
            Store store = session.getStore("imaps");
            store.connect(host, username, motdepasse);

            Folder inbox = store.getFolder("INBOX");
            //store.getDefaultFolder().list("*");
            //Folder sent_message = store.getFolder("SENT");
            inbox.open(Folder.READ_WRITE);

            int messageCount = inbox.getMessageCount();

            Message[] lus = inbox.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), true));

            String expediteur = "";
            for (int i = 0; i < lus.length; i++) {
                // messages[i].getFrom()[0];
                Address address = lus[i].getFrom()[0];
                String mail = ((InternetAddress) address).getAddress();
                if (i==0) expediteur = expediteur + mail;
                else expediteur = expediteur + ";" + mail;
            }

            // Fetch unseen messages from inbox folder
            Message[] non_lus = inbox.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (int i = 0; i < non_lus.length; i++) {
                // messages[i].getFrom()[0];
                Address address = non_lus[i].getFrom()[0];
                String mail = ((InternetAddress) address).getAddress();
                expediteur = expediteur + ";" + mail;
                //non_lus[i].setFlag(Flags.Flag.SEEN, true);
            }

            compterEmailRecus(user,expediteur,fichier);

            inbox.close(true);
            store.close();
        }
        catch (MessagingException  e) {
            System.out.println(" dans le catch ");
            e.printStackTrace();
        }
    }

    public void compterEmailEnvoyes (User user,String text, File file) throws IOException {
        String source = user.getEmail();
        Hashtable table = new Hashtable();

        StringTokenizer st;
        String mot;
        int nbOcc;


        st = new StringTokenizer(text, ";");
        while(st.hasMoreTokens())
        {
            mot = st.nextToken();
            if (table.containsKey(mot))
            {
                nbOcc = ((Integer)table.get(mot)).intValue();
                nbOcc++;
            }
            else nbOcc = 1;
            table.put(mot, new Integer(nbOcc));
        }

        // first create file object for file placed at location
        // specified by filepath
        //File file = new File(fichier);
        file = new File("src/main/resources/static/messages_envoyes.csv");
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile, ',', CSVWriter.NO_QUOTE_CHARACTER);

            // adding header to csv
            String[] header = { "source", "target", "value" };
            writer.writeNext(header);


            Enumeration lesMots = table.keys();
            while (lesMots.hasMoreElements())
            {
                mot = (String)lesMots.nextElement();
                nbOcc = ((Integer)table.get(mot)).intValue();
                String nb = Integer.toString(nbOcc);
                //System.out.println("Le mot " + mot + " figure " + nbOcc + " fois");

                // add data to csv
                String[] data = { source,mot,nb };
                writer.writeNext(data);

                // closing writer connection

            }
            writer.close();

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void compterEmailRecus (User user,String text, File file) throws IOException {
        String source = user.getEmail();
        Hashtable table = new Hashtable();

        StringTokenizer st;
        String mot;
        int nbOcc;


        st = new StringTokenizer(text, ";");
        while(st.hasMoreTokens())
        {
            mot = st.nextToken();
            if (table.containsKey(mot))
            {
                nbOcc = ((Integer)table.get(mot)).intValue();
                nbOcc++;
            }
            else nbOcc = 1;
            table.put(mot, new Integer(nbOcc));
        }

        // first create file object for file placed at location
        // specified by filepath
        //File file = new File(fichier);
        file = new File("src/main/resources/static/messages_recus.csv");
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile, ',', CSVWriter.NO_QUOTE_CHARACTER);

            // adding header to csv
            String[] header = { "source", "target", "value" };
            writer.writeNext(header);


            Enumeration lesMots = table.keys();
            while (lesMots.hasMoreElements())
            {
                mot = (String)lesMots.nextElement();
                nbOcc = ((Integer)table.get(mot)).intValue();
                String nb = Integer.toString(nbOcc);
                //System.out.println("Le mot " + mot + " figure " + nbOcc + " fois");

                // add data to csv
                String[] data = { mot,source,nb };
                writer.writeNext(data);

                // closing writer connection

            }
            writer.close();

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
