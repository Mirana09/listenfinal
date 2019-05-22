package listen.business;

public class Email {

    private int id;
    private String date_envoi;
    private String expediteur;
    private String objet;
    private String pieces_jointes;
    private String texte;


    public Email(int id, String date_envoi, String expediteur, String objet, String pieces_jointes, String texte) {
        this.id = id;
        this.date_envoi = date_envoi;
        this.expediteur = expediteur;
        this.objet = objet;
        this.pieces_jointes = pieces_jointes;
        this.texte = texte;
    }

    public Email() {
    }

    public int getId() {
        return id;
    }

    public String getDate_envoi() {
        return date_envoi;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public String getObjet() {
        return objet;
    }

    public String getPieces_jointes() {
        return pieces_jointes;
    }

    public String getTexte() {
        return texte;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate_envoi(String date_envoi) {
        this.date_envoi = date_envoi;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public void setObjet(String objet) {
        if(objet != null) this.objet = objet;
        else this.objet = " ";
    }

    public void setPieces_jointes(String pieces_jointes) {
        this.pieces_jointes = pieces_jointes;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
