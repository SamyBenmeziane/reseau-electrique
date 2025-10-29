package modele.src;
/**
 * Classe représentant une maison consommant de l'électricité.
 */
public class Maison {

    /**
     * Énumération des types de consommation électrique avec leur puissance en kW.
     */
    public enum Consommation {
        BASSE(10),   // 10 kW
        NORMAL(20),  // 20 kW
        FORTE(40);   // 40 kW

        private final int kW;

        Consommation(int kW) {
            this.kW = kW;
        }

        public int getKW() {
            return kW;
        }
    }

    // Attributs privés
    private String nom;
    private Consommation consommation;

    /**
     * Constructeur pour créer une maison avec son nom et son type de consommation.
     * 
     * @param nom           le nom de la maison
     * @param consommation  le type de consommation (BASSE, NORMAL, FORTE)
     */
    public Maison(String nom, Consommation consommation) {
        this.nom = nom;
        this.consommation = consommation;
    }

    // Getters

    public String getNom() {
        return nom;
    }

    public Consommation getConsommation() {
        return consommation;
    }

    // Setters

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setConsommation(Consommation consommation) {
        this.consommation = consommation;
    }

    /**
     * Retourne la consommation en kW.
     *
     * @return consommation en kW
     */
    public int consommationEnKW() {
        return consommation.getKW();
    }

    @Override
    public String toString() {
        return "Maison{" +
                "nom='" + nom + '\'' +
                ", consommation=" + consommation +
                ", consommationEnKW=" + consommationEnKW() + " kW" +
                '}';
    }
}
