import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Carnivore extends Animal{

    public Carnivore(
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
    }

    // Obtenir énergie des animaux mangés
    public double mangerAnimaux(List<Animal> animauxManges){
        double energieObtenueTotale = 0;
        for(Animal animal: animauxManges){
            energieObtenueTotale += animal.getEnergie();
        }
        return energieObtenueTotale;
    }

    // Obtenir une liste d'animaux à manger
    public List<Animal> chercherAnimaux(List<Animal> animaux){
        // Déterminer le nombre de manger
        int nombre = 0;
        while(Math.random() < this.debrouillardise){
            nombre += 1;
        }
        return this.chercherNombreAnimaux(animaux, nombre);
    }

    // Obtenir aléatoirement une liste de longueur "nombre" d'animaux à manger
    private List<Animal> chercherNombreAnimaux(List<Animal> animaux, int nombre){
        List<Animal> animauxCherches = new ArrayList<>();

        // Point de départ aléatoire pour chercher aliments. Intervalle: [0,size-1]
        int depart = (int)Math.floor(Math.random()*(animaux.size()));
        // Demi-intervalle de recherche, incrémentée par un à chaque cycle
        int i = 0;
        // Compter les animaux examinés
        int count = 0;

        while(nombre>0 && count<animaux.size()){
            for(int j: new HashSet<>(Arrays.asList(depart+i,depart-i))){
                // Passer si on dépasse les limites
                if(j<0 || j>=animaux.size()){continue;}

                // Examiner l'animal
                ++count;

                // Si l'animal est mangeable
                    //---- Dans la liste d'aliments
                if(this.aliments.contains(animaux.get(j).getNomEspece())
                    //---- Pas trop grand
                    && this.tailleMaximum>=animaux.get(j).getTailleMaximum()
                    //---- Pas lui-même
                    && this!=animaux.get(j)
                ){
                    --nombre;
                    animauxCherches.add(animaux.get(j));
                    if(nombre<=0){return animauxCherches;}
                }
            }
            ++i;
        }
        // Pas assez d'animaux mangeables
        return animauxCherches;
    }


    // Getters
    public double getDebrouillardise() {
        return debrouillardise;
    }

    public Set<String> getAliments() {
        return aliments;
    }

    public double getTailleMaximum() {
        return tailleMaximum;
    }

}
