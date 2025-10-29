package modele.src;

/**
 * Classe représentant un générateur électrique.
 */
public class Generateur {

    // Attributs privés du générateur
    private String nom;       // Nom du générateur
    private int capacite;     // Capacité maximale en kW

    /**
     * Constructeur pour créer un générateur avec un nom et une capacité.
     *
     * @param nom      le nom du générateur
     * @param capacite la capacité maximale du générateur en kW
     */
    public Generateur(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    // Getters

    /**
     * Retourne le nom du générateur.
     * @return nom du générateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la capacité maximale du générateur.
     * @return capacité maximale en kW
     */
    public int getCapacite() {
        return capacite;
    }

    // Setters

    /**
     * Définit le nom du générateur.
     * @param nom nouveau nom du générateur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Définit la capacité maximale du générateur.
     * @param capacite nouvelle capacité maximale en kW
     */
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    /**
     * Affiche les informations du générateur sous forme de chaîne de caractères.
     * @return description du générateur
     */
    @Override
    public String toString() {
        return "Generateur{" +
                "nom='" + nom + '\'' +
                ", capacite=" + capacite + " kW" +
                '}';
    }
}
