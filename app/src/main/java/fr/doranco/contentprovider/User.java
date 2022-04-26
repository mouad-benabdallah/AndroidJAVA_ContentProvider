package fr.doranco.contentprovider;

public class User {

    private Integer id;
    private String nom;
    private String prenom;
    private Adresse adresse;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        if (adresse != null) {
            return id + " - " + nom + " " + prenom + " " + adresse.toString();
        }
        return id + " - " + nom + " " + prenom;
    }
}
