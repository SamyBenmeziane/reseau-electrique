package src.modele;

import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Importez vos classes
import src.modele.Maison;
import src.modele.Generateur;
import src.modele.ReseauElectrique;




public class Main{

    public static void main(String[] args) {
        ReseauElectrique reseau = new ReseauElectrique();
        Scanner sc = new Scanner(System.in);
        menuPrincipal(sc, reseau);
        sc.close();
    }











   // MENU PRINCIPAL avec switch et boolean
public static void menuPrincipal(Scanner sc, ReseauElectrique reseau) {
    boolean continuer = true; // Contrôle de la boucle

    while (continuer) {
        System.out.println();
        System.out.println("===== MENU PRINCIPAL =====");
        System.out.println("1 - Ajouter Générateur");
        System.out.println("2 - Ajouter Maison");
        System.out.println("3 - Ajouter Connexion");
        System.out.println("4 - Supprimer Connexion");
        System.out.println("5 - Fin");
        System.out.print("Votre choix : ");

        String saisie = sc.nextLine();
        int choix = convertirEntier(saisie);

        switch (choix) {
            case 1 -> ajouterGenerateur(sc, reseau);
            case 2 -> ajouterMaison(sc, reseau);
            case 3 -> ajouterConnexion(sc, reseau);
            case 4 -> supprimerConnexion(sc, reseau);
            case 5 -> {
                boolean verif = verificationMenu(reseau);
                if (verif) {
                    menu2(reseau, sc); // Passe au menu secondaire
                    continuer = false;  // Quitte le menu principal
                }
            }
            default -> System.out.println("Choix invalide.");
        }
    }
}








    public static int afficherMenu2(Scanner scanner) {
        System.out.println("\n===== MENU 2 =====");
        System.out.println("1) Calculer le coût du réseau électrique actuel");
        System.out.println("2) Modifier une connexion");
        System.out.println("3) Afficher le réseau");
        System.out.println("4) Fin");
        System.out.print("Choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine(); 
        return choix;
    }





    

    
    public static void menu2(ReseauElectrique reseau, Scanner scanner) {
        boolean continuer = true;

        while (continuer) {
            int choix = afficherMenu2(scanner);

            switch (choix) {
                case 1 -> calculerCoutReseauMenu(reseau); 
                case 2 -> modifierConnexionMenu(reseau, scanner);
                case 3 -> reseau.afficherReseau() ;
                case 4 -> {
                    System.out.println("Fin ");
                    continuer = false;
                }
                default -> System.out.println("Choix invalide. Réessayez.");
            }
        }
        
        
     
           
    }

    public  static void calculerCoutReseauMenu(ReseauElectrique reseau) {
    	System.out.println("Coût total du réseau : " + reseau.calculerCoutTotal() + " unités.");
    }
  
    public static void  modifierConnexionMenu(ReseauElectrique reseau, Scanner scanner){ 
        System.out.print("Entrer deux noms (maison/generateur) : ");
       Object[] connexion = splitConnexion(scanner, reseau);
        if (connexion == null) return;

        Maison m1 = (Maison) connexion[0];
        Generateur g1 = (Generateur) connexion[1];
        System.out.print("Veuillez saisir la nouvelle connexion : ");
        Object[] connexion2 = splitConnexion(scanner, reseau);
        if (connexion2 == null) return;

        Maison m2 = (Maison) connexion2[0];
        Generateur g2 = (Generateur) connexion2[1];
        if(!m1.equals(m2)) {
            System.out.println("Les maisons doivent être identiques pour modifier la connexion.");
            return;  
        }
        if(g1.equals(g2)) {
            System.out.println("La nouvelle connexion est identique à l'ancienne.");
            return;  
        }
        boolean deconnecte = reseau.deconnecterMaisonGenerateur(m1, g1);
        if (!deconnecte) { System.out.println("Connexion inexistante."); return; }
        boolean connecte = reseau.connecterMaisonGenerateur(m1, g2);
        if (connecte) System.out.println("Connexion modifiée.");
        else {
            System.out.println("Échec de la connexion. Restauration de l'ancienne connexion.");
            reseau.connecterMaisonGenerateur(m1, g1);
        }
    }
    // ACTIONS
    public static void ajouterGenerateur( Scanner sc, ReseauElectrique reseau) {
        System.out.print("Entrer (nom capacite) : ");
        String ligne = sc.nextLine();
        String[] t = ligne.split(" ");
        if (t.length != 2) { System.out.println("Format incorrect."); return; }
        int capacite = convertirEntier(t[1]);
        if (capacite == -1) { System.out.println("Capacité invalide."); return; }
        Generateur g = new Generateur(t[0], capacite);
        boolean ajout = reseau.ajouterGenerateur(g);
        if (!ajout) System.out.println("Générateur existant. Capacité mise à jour.");
        else System.out.println("Générateur ajouté.");
    }

    public static void ajouterMaison(Scanner sc, ReseauElectrique reseau) {
        System.out.print("Entrer (nom consommation) : ");
        String ligne = sc.nextLine();
        String[] t = ligne.split(" ");
        if (t.length != 2) { System.out.println("Format incorrect."); return; }
        Maison.Consommation conso = convertirConsommation(t[1]);
        if (conso == null) { System.out.println("Consommation invalide (BASSE, NORMAL, FORTE)."); return; }
        Maison m = new Maison(t[0], conso);
        boolean ajout = reseau.ajouterMaison(m);
        if (!ajout) System.out.println("Maison existante. Consommation mise à jour.");
        else System.out.println("Maison ajoutée.");
    }

    public  static void ajouterConnexion(Scanner sc, ReseauElectrique reseau   ) {
        Object[] connexion = splitConnexion(sc, reseau);
        if (connexion == null) return;

        Maison m = (Maison) connexion[0];
        Generateur g = (Generateur) connexion[1];

        boolean ok = reseau.connecterMaisonGenerateur(m, g);
        if (ok) System.out.println("Connexion ajoutée.");
        else System.out.println("Échec de l'ajout.");

    }
        

    public static void supprimerConnexion(Scanner sc, ReseauElectrique reseau) {
        System.out.print("Entrer deux noms (maison/generateur) : ");
       Object[] connexion = splitConnexion(sc, reseau);
        if (connexion == null) return;

        Maison m = (Maison) connexion[0];
        Generateur g = (Generateur) connexion[1];

        boolean ok = reseau.deconnecterMaisonGenerateur(m, g);
        if (ok) System.out.println("Connexion supprimée.");
        else System.out.println("Connexion inexistante.");
    }


public static boolean verificationMenu(ReseauElectrique reseau) {
    List<Maison> maisonsProblemes = new ArrayList<>();
    for (Map.Entry<Maison, Set<Generateur>> entry : reseau.getConnexions().entrySet()) {
        Maison maison = entry.getKey();
        Set<Generateur> generateurs = entry.getValue();
        
        if (generateurs.size() != 1) {
            maisonsProblemes.add(maison);
        }
    }

    if (maisonsProblemes.isEmpty()) {
        System.out.println("Toutes les maisons sont connectées à un générateur unique.");
        return true; // Tout est correct
    } else {
        System.out.println("Problèmes de connexion détectés pour les maisons suivantes :");
        for (Maison maison : maisonsProblemes) {
            int nbGenerateurs = reseau.getConnexions().get(maison).size();
            String probleme = nbGenerateurs == 0 ? "Pas de connexion (0 générateur)"
                                                 : "Trop de connexions (" + nbGenerateurs + " générateurs)";
            System.out.println("- " + maison.getNom() + " : " + probleme);
        }
        return false; // Il y a des problèmes
    }
}

    

    // OUTILS
    public static int convertirEntier(String s) {
        for (char c : s.toCharArray()) if (!Character.isDigit(c)) return -1;
        return Integer.parseInt(s);
    }

    public static Maison.Consommation convertirConsommation(String s) {
        if (s.equals("BASSE")) return Maison.Consommation.BASSE;
        if (s.equals("NORMAL")) return Maison.Consommation.NORMAL;
        if (s.equals("FORTE")) return Maison.Consommation.FORTE;
        return null;
    }

    public static Object trouverObjet(String nom , ReseauElectrique reseau) {
        for (Maison m : reseau.getConnexions().keySet()) if (m.getNom().equals(nom)) return m;
        for (Generateur g : reseau.getGenerateurs()) if (g.getNom().equals(nom)) return g;
        return null;
    }
    public static Object[] splitConnexion(Scanner sc, ReseauElectrique reseau) {
    String ligne = sc.nextLine();
    String[] t = ligne.split(" ");
    if (t.length != 2) {
        System.out.println("Format incorrect.");
        return null;
    }

    Object o1 = trouverObjet(t[0], reseau);
    Object o2 = trouverObjet(t[1], reseau);

    if (o1 == null && o2 == null) {
        System.out.println(t[0] + " et " + t[1] + " sont inconnus.");
        return null;
    }
    if (o1 == null) {
        System.out.println(t[0] + " est inconnu.");
        return null;
    }
    if (o2 == null) {
        System.out.println(t[1] + " est inconnu.");
        return null;
    }

    Maison m;
    Generateur g;

    if (o1 instanceof Maison && o2 instanceof Generateur) {
        m = (Maison) o1;
        g = (Generateur) o2;
    } else if (o1 instanceof Generateur && o2 instanceof Maison) {
        g = (Generateur) o1;
        m = (Maison) o2;
    } else {
        System.out.println("Une connexion doit être entre une maison et un générateur.");
        return null;
    }

    return new Object[]{m, g};
    }

}