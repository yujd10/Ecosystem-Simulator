import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Herbivore extends Animal {
    private double voraciteMin;
    private double voraciteMax;

    // Constructor
    public Herbivore(
        String nomEspece,
        double energie,
        int age,
        double besoinEnergie,
        double efficaciteEnergie,
        double resilience,
        double fertilite,
        int ageFertilite,
        double energieEnfant,
        double debrouillardise,
        double voraciteMin,
        double voraciteMax,
        Set<String> aliments,
        double tailleMaximum
    ) {
        super(nomEspece,
        energie,
        age,
        besoinEnergie,
        efficaciteEnergie,
        resilience,
        fertilite,
        ageFertilite,
        energieEnfant,
        debrouillardise,
        aliments,
        tailleMaximum);

        this.voraciteMin = voraciteMin;
        this.voraciteMax = voraciteMax;
    }

   // Obtenir énergie des plantes mangées
    public double mangerPlantes(List<Plante> plantesMangees){
        double energieObtenueTotale = 0;
        for(Plante plante: plantesMangees){
            double energieObtenue = plante.getEnergie()*
                (voraciteMin+Math.random()*(voraciteMax-voraciteMin));
            energieObtenueTotale += energieObtenue;

            // Enlever à plante l'énergie mangée
            plante.changerEnergie(-energieObtenue);
        }
        return energieObtenueTotale;
    }

    // Obtenir une liste de plantes à manger
    public List<Plante> chercherPlantes(List<Plante> plantes){
        // Déterminer le nombre de manger
        int nombre = 0;
        while(Math.random() <= this.debrouillardise){
            nombre += 1;
        }

        List<Plante> plantesMangees = new ArrayList<>();

        while(nombre>0){
            int pos = this.chercherUnePlante(plantes);
            if(pos == -1){return new ArrayList<Plante>();}

            plantesMangees.add(plantes.get(pos));
            --nombre;
        }
        
        return plantesMangees;
    }

    // Obtenir aléatoirement la position d'une plante à manger
    private int chercherUnePlante(List<Plante> plantes){
        // Point de départ aléatoire pour chercher aliments. Intervalle: [0,size-1]
        int depart = (int)Math.floor(Math.random()*(plantes.size()));
        // Demi-intervalle de recherche, incrémentée par un à chaque cycle
        int i = 0;
        // Compter les plantes examinées
        int count = 0;

        while(count<plantes.size()){
            for(int j: new HashSet<>(Arrays.asList(depart+i,depart-i))){
                // Passer si on dépasse les limites
                if(j<0 || j>=plantes.size()){continue;}

                // Examiner la plante
                ++count;

                // Si la plante est mangeable
                if(this.aliments.contains(plantes.get(j).getNomEspece())){
                    return j;
                }
            }
            ++i;
        }
        // Retourner -1 si aucune plante mangeable
        return -1;
    }

    // Getters
    public double getVoraciteMin() {
        return voraciteMin;
    }

    public double getVoraciteMax() {
        return voraciteMax;
    }
}
