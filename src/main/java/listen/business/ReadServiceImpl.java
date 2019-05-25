package listen.business;

import listen.repository.EmailRepository;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class ReadServiceImpl implements ReadService {

    private EmailRepository emailRepository;

    public ReadServiceImpl(EmailRepository emailRepository){
        this.emailRepository = emailRepository;
    }

    public void play(User user) throws Exception {

        String username = user.getEmail();
        String motdepasse = user.getPassword();

        emailRepository.vider();

        String host = "imap.gmail.com";

        String intro = " ";
        String resultat = " ";
        try {
            Session session = Session.getDefaultInstance(new Properties());
            Store store = session.getStore("imaps");
            store.connect(host, username, motdepasse);

            Folder inbox = store.getFolder("INBOX");
            //intro = intro + "Lecture des messages non lus qui sont présents dans la boîte de réception";

            inbox.open(Folder.READ_WRITE);

            int messageCount = inbox.getMessageCount();

            //intro = intro + "\n Nombre de messages dans la boîte de réception : " + messageCount;

            // Fetch unseen messages from inbox folder
            Message[] messages = inbox.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            //resultat = resultat + "\n On a  " + messages.length + " messages non lus ";

            int unreadMsgCount = inbox.getUnreadMessageCount();

            if (unreadMsgCount == 0) {
                intro = "\n Pas de messages non lus";
                emailRepository.saveIntro(intro);
                emailRepository.vider();
            }
            else {
                intro = intro + "\n Nombre de messages non lus: " + unreadMsgCount;


                //resultat = resultat + "\n ";

                // Sort messages from recent to oldest
                Arrays.sort(messages, (message1, message2) -> {
                    try {
                        return message2.getSentDate().compareTo(message1.getSentDate());
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                });


                for (int i = 0; i < messages.length; i++) {
                    Email message = new Email();
                    resultat = resultat +  "";
                    resultat = resultat + "\n Message numéro " + (i+1);

                    message.setId(i+1);

                    resultat = resultat + "\n Date d'envoi : " + messages[i].getSentDate();
                    message.setDate_envoi(messages[i].getSentDate().toString());

                    resultat = resultat + "\n Expéditeur : " + messages[i].getFrom()[0];

                    message.setExpediteur(messages[i].getFrom()[0].toString());

                    resultat = resultat + "\n Objet : " + messages[i].getSubject();

                    if (messages[i].getSubject() != null) message.setObjet(messages[i].getSubject());


                    String contentType = messages[i].getContentType();
                    // store attachment file name, separated by comma
                    String attachFiles = " ";
                    Multipart multiPart = (Multipart) messages[i].getContent();
                    if (contentType.contains("multipart")) {
                        // content may contain attachments

                        int numberOfParts = multiPart.getCount();
                        for (int partCount = 0; partCount < numberOfParts; partCount++) {
                            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                // this part is attachment
                                String fileName = part.getFileName();
                                attachFiles += fileName + ", ";
                            }
                        }

                        if (attachFiles.length() > 1) {
                            attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                        }
                        // si on n'a pas de pièce jointe on ne l'affiche pas
                        if (attachFiles.length() >= 1) {
                            resultat = resultat + "\n Pièces jointes : " + attachFiles;
                            message.setPieces_jointes(attachFiles);
                        }
                    }
                    resultat = resultat + "\n Texte : " + getTextFromMimeMultipart((MimeMultipart) multiPart);

                    message.setTexte(getTextFromMimeMultipart((MimeMultipart) multiPart));

                    messages[i].setFlag(Flags.Flag.SEEN, true);

                    emailRepository.save(message);
                }
            }
            //System.out.println(resultat);
            emailRepository.saveIntro(intro);
            String voice = intro + resultat;
            getHTML("http://api.voicerss.org/?key=c35f7f01e1c44bdba53f1b7e457b9670&hl=fr-fr&src=" + URLEncoder.encode(voice,"UTF-8"));

            inbox.close(true);
            store.close();
        }
        catch (Exception e) {
            System.out.println(" dans le catch ");
            e.printStackTrace();
        }
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        String result = "";
        BodyPart bodyPart = mimeMultipart.getBodyPart(0);
        if (bodyPart.isMimeType("text/plain")) {
            result = "\n" + bodyPart.getContent();
        } else if (bodyPart.isMimeType("text/html")) {
            String html = (String) bodyPart.getContent();
            result = html;
        } else if (bodyPart.getContent() instanceof MimeMultipart) {
            result = getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
        }
        return result;
    }

    private void getHTML(String urltoread) throws Exception{
        StringBuilder result = new StringBuilder();
        URL url = new URL(urltoread);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        InputStream in = conn.getInputStream();
        AudioStream audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
        //  String line;
    }

}
