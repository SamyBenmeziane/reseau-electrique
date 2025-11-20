package src.modele;

import java.util.*;

/**
 * Classe représentant un réseau électrique composé de maisons et de générateurs.
 */
public class ReseauElectrique {
    private final double LAMBDA = 10.0; // Coefficient pour le calcul du coût total

    private Set<Maison> maisons; // Ensemble des maisons
    private Set<Generateur> generateurs; // Ensemble des générateurs
    private Map<Maison, Set<Generateur>> connexions; // Connexions maison -> générateurs

    public ReseauElectrique() {
        maisons = new HashSet<>();
        generateurs = new HashSet<>();
        connexions = new HashMap<>();
    }

    // ===================== Gestion des maisons =====================

    public boolean ajouterMaison(Maison maison) {
        // Si la maison existe déjà, on met à jour la consommation
        if (maisons.contains(maison)) {
            for (Maison m : maisons) {
                if (m.equals(maison)) {
                    m.setConsommation(maison.getConsommation());
                    return false; // Mise à jour, pas ajout
                }
            }
        }
        maisons.add(maison);
        connexions.put(maison, new HashSet<>());
        return true; // Maison ajoutée
    }

    public boolean supprimerMaison(Maison maison) {
        if (!maisons.contains(maison)) return false;
        maisons.remove(maison);
        connexions.remove(maison);
        return true;
    }

    // ===================== Gestion des générateurs =====================

    public boolean ajouterGenerateur(Generateur g) {
        if (generateurs.contains(g)) {
            for (Generateur gen : generateurs) {
                if (gen.equals(g)) {
                    gen.setCapacite(g.getCapacite());
                    return false; // Mise à jour
                }
            }
        }
        generateurs.add(g);
        return true;
    }

    public boolean supprimerGenerateur(Generateur g) {
        if (!generateurs.contains(g)) return false;
        generateurs.remove(g);
        // Supprime le générateur de toutes les connexions
        for (Set<Generateur> setGen : connexions.values()) {
            setGen.remove(g);
        }
        return true;
    }

    // ===================== Gestion des connexions =====================

    public boolean connecterMaisonGenerateur(Maison maison, Generateur g) {
        if (!maisons.contains(maison) || !generateurs.contains(g)) return false;
        Set<Generateur> gens = connexions.get(maison);
        if (gens == null) {
            gens = new HashSet<>();
            connexions.put(maison, gens);
        }
        gens.add(g); // HashSet empêche les doublons
        return true;
    }

    public boolean deconnecterMaisonGenerateur(Maison maison, Generateur g) {
        if (!connexions.containsKey(maison)) return false;
        Set<Generateur> gens = connexions.get(maison);
        return gens.remove(g);
    }

    public Set<Generateur> getGenerateursMaison(Maison maison) {
        return connexions.getOrDefault(maison, new HashSet<>());
    }

    public boolean maisonExiste(Maison maison) {
        return maisons.contains(maison);
    }

    public boolean generateurExiste(Generateur g) {
        return generateurs.contains(g);
    }

    // ===================== Affichage =====================

    public void afficherReseau() {
        System.out.println("=== Maisons ===");
        for (Maison m : maisons) {
            System.out.println(m);
        }

        System.out.println("=== Générateurs ===");
        for (Generateur g : generateurs) {
            System.out.println(g);
        }

        System.out.println("=== Connexions ===");
        for (Map.Entry<Maison, Set<Generateur>> entry : connexions.entrySet()) {
            System.out.println(entry.getKey().getNom() + " connecté à " + entry.getValue());
        }
    }
    public Map<Maison, Set<Generateur>> getConnexions() {
        return connexions;
    }
    public Set<Generateur> getGenerateurs() {
        return generateurs;
    }
    public Set<Maison> getMaisons() {
        return maisons;
    }
    /**
     * Calcule la charge actuelle (Lg) d'un générateur 'g'.
     * Cette version suppose que le Set des connexions contient au plus un seul générateur
     * pour toute maison (la contrainte d'unicité est déjà appliquée dans le réseau).
     * @param g Le générateur dont on veut calculer la charge.
     * @return La charge actuelle en kW.
     */
public double ChargeActuelle(Generateur g) {
        double charge = 0;
        
        for (Map.Entry<Maison, Set<Generateur>> entree : connexions.entrySet()) {
       
            Maison maison = entree.getKey();
            Set<Generateur> generateursConnectes = entree.getValue();

            // 1. Vérifie si le générateur 'g' est dans le Set de connexions de la maison.
            // 2. Par hypothèse (contrainte déjà appliquée), si 'g' est là, il est le seul.
            if (generateursConnectes.contains(g)) {
                charge += maison.getConsommation().getKW();
            }
        }
        
        return charge;
    }
    
    public double tauxUtilisation(Generateur g) {
        return ChargeActuelle(g) / g.getCapacite();
    }
    
    private double calculerMoyenneTauxUtilisation() {
        if (generateurs.isEmpty()) {
            return 0;
        }

        double sommeTaux = 0;
        for (Generateur g : generateurs) {
            sommeTaux += tauxUtilisation(g);
        }
        return sommeTaux / generateurs.size();
    }
   

public double calculerSurcharge() {
        double surcharge = 0;
        
        for (Generateur g : generateurs) {
            double lg = ChargeActuelle(g); 
            double cg = g.getCapacite();           
            
            double surchargeTerme = (lg - cg) / cg;
            surcharge += Math.max(0, surchargeTerme); 
        }
        
        return surcharge;
    }

    public double calculerDispersion() {
        double uBarre = calculerMoyenneTauxUtilisation();
        double dispersion = 0;

        for (Generateur g : generateurs) {
            double ug = tauxUtilisation(g);
            dispersion += Math.abs(ug - uBarre);
        }
        return dispersion;
    }
   public double calculerCoutTotal() {
        double dispersion = calculerDispersion();
        double surcharge = calculerSurcharge();
        return dispersion + LAMBDA * surcharge;
    }
  

}
