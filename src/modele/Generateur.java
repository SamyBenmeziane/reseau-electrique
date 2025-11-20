package src.modele;

/**
 * Classe représentant un générateur électrique.
 */
public class Generateur {

    // Attributs privés
    private String nom;
    private int capacite; // en kW

    /**
     * Constructeur pour créer un générateur avec son nom et sa capacité maximale.
     *
     * @param nom       le nom du générateur
     * @param capacite  la capacité maximale en kW
     */
    public Generateur(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    @Override
    public String toString() {
        return "Generateur{" +
                "nom='" + nom + '\'' +
                ", capacite=" + capacite + " kW" +
                '}';
    }

    /**
     * Deux générateurs sont considérés identiques si leur nom est identique.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Generateur autre = (Generateur) obj;
        return nom.equals(autre.nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}

