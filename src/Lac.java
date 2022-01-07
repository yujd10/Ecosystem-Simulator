import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.*;

public final class Lac {
    private final int energieSolaire;
    private final List<Plante> plantes;
    private final List<Herbivore> herbivores;
    private final List<Carnivore> carnivores;

    public Lac(int energieSolaire, List<Plante> plantes, List<Herbivore> herbivores, List<Carnivore> carnivores) {
        this.energieSolaire = energieSolaire;
        this.plantes = plantes;
        this.herbivores = herbivores;
        this.carnivores = carnivores;
    }

    /**
     * Avance la simulation d'un cycle.
     */
    public void tick() {
        // À compléter.

        // Mélanger les organismes pour éliminer l'avantage des premiers
        Collections.shuffle(plantes);
        Collections.shuffle(herbivores);
        Collections.shuffle(carnivores);

        // Listes pour garder les enfants générés
        List<Plante> enfantsPlantes = new ArrayList<>();
        List<Herbivore> enfantsHerbivores = new ArrayList<>();
        List<Carnivore> enfantsCarnivores = new ArrayList<>();

        // Calculer l'énergie totale des plantes
        Double energieTotale = 0.0;
        for(Plante plante: this.plantes){
            energieTotale += plante.getEnergie();
        }

        // Boucle plantes
        int i = 0;
        while(i < this.plantes.size()){
            Plante plante = this.plantes.get(i);

            // 1. Distribuer l'énergie solaire
            double energieSupplementaire = 0;
            if(energieTotale!=0){
                energieSupplementaire = this.energieSolaire*plante.getEnergie()/energieTotale;
            }

            // 2. Vérifier si la plante survit
            energieSupplementaire = plante.survivre(energieSupplementaire);

            //---- Si la plante ne survit pas
            if(energieSupplementaire == Double.NEGATIVE_INFINITY){
                this.plantes.remove(i);
                //---- Passer les autres processus
                continue;
            }

            //---- Si la plante survit, elle commence à reproduire

            // 3. Reporduction
            //---- Calculer le nombre des enfants à faire et l'énergie restante
            Map<String, Double> mapEngEnf = plante.reproduire(energieSupplementaire);
            int nombreDesEnfants = mapEngEnf.get("nombreDesEnfants").intValue();
            energieSupplementaire = mapEngEnf.get("energieSupplementaire");

            //---- Créer les enfants
            UsinePlante usine = new UsinePlante();
            usine.copierParent(plante);
            for(int j=0;j<nombreDesEnfants;++j){
                enfantsPlantes.add(usine.creerPlante());
            }
            
            // 4. Assimiler l'énergie supplémentaire
            plante.assimilerEnergie(energieSupplementaire);
            // 5. Avoir un an de plus
            plante.prendreAge();

            ++i;
        }

        // Boucle herbivores
        i = 0;
        while(i<this.herbivores.size()){
            Herbivore herbivore = this.herbivores.get(i);

            // 1. Obtenir l'énergie des plantes
            List<Plante> plantesMangees = herbivore.chercherPlantes(this.plantes);
            double energieSupplementaire = herbivore.mangerPlantes(plantesMangees);

            // 2. Vérifier si l'herbivore survit
            energieSupplementaire = herbivore.survivre(energieSupplementaire);

            if(energieSupplementaire == Double.NEGATIVE_INFINITY){
                this.herbivores.remove(i);
                continue;
            }

            // 3. Reporduction
            Map<String, Double> mapEngEnf = herbivore.reproduire(energieSupplementaire);
            int nombreDesEnfants = mapEngEnf.get("nombreDesEnfants").intValue();
            energieSupplementaire = mapEngEnf.get("energieSupplementaire");

            UsineHerbivore usine = new UsineHerbivore();
            usine.copierParent(herbivore);
            for(int j=0;j<nombreDesEnfants;++j){
                enfantsHerbivores.add(usine.creerHerbivore());
            }
            
            // 4. Assimiler l'énergie supplémentaire
            herbivore.assimilerEnergie(energieSupplementaire);
            // 5. Avoir un an de plus
            herbivore.prendreAge();

            ++i;
        }

        // Boucle carnivores
        i = 0;
        while(i<this.carnivores.size()){
            Carnivore carnivore = this.carnivores.get(i);

            // 1. Obtenir l'énergie des animaux

            //---- Concaténer la liste de proies
            List<Animal> animaux = new ArrayList<>();
            animaux.addAll(this.herbivores);
            animaux.addAll(this.carnivores);

            //---- Se nourir
            List<Animal> animauxManges = carnivore.chercherAnimaux(animaux);
            double energieSupplementaire = carnivore.mangerAnimaux(animauxManges);

            //---- Eliminer les animaux mangés
            for(Animal animal: animauxManges){
                if(animal instanceof Herbivore){
                    this.herbivores.remove(animal);
                }else if(animal instanceof Carnivore){
                    int position = carnivores.indexOf(animal);
                    this.carnivores.remove(position);
                    // Ajuster l'indice
                    if(position<i){i-=1;}
                }
            }

            // 2. Vérifier si le carnivore survit
            energieSupplementaire = carnivore.survivre(energieSupplementaire);

            if(energieSupplementaire == Double.NEGATIVE_INFINITY){
                this.carnivores.remove(i);
                continue;
            }

            // 3. Reporduction
            Map<String, Double> mapEngEnf = carnivore.reproduire(energieSupplementaire);
            int nombreDesEnfants = mapEngEnf.get("nombreDesEnfants").intValue();
            energieSupplementaire = mapEngEnf.get("energieSupplementaire");

            UsineCarnivore usine = new UsineCarnivore();
            usine.copierParent(carnivore);
            for(int j=0;j<nombreDesEnfants;++j){
                enfantsCarnivores.add(usine.creerCarnivore());
            }
            
            // 4. Assimiler l'énergie supplémentaire
            carnivore.assimilerEnergie(energieSupplementaire);
            // 5. Avoir un an de plus
            carnivore.prendreAge();

            ++i;
        }

        // Ajouter enfants à l'écosystème
        plantes.addAll(enfantsPlantes);
        herbivores.addAll(enfantsHerbivores);
        carnivores.addAll(enfantsCarnivores);
    }

    public void imprimeRapport(PrintStream out) {
        var especes = this.plantes.stream().collect(groupingBy(
                Plante::getNomEspece,
                summarizingDouble(Plante::getEnergie)));
        out.println("Il reste " + especes.size() + " espèces de plantes.");
        for (var entry : especes.entrySet()) {
            var value = entry.getValue();
            out.printf(
                "%s: %d individus qui contiennent en tout %.2f unités d'énergie.\n",
                entry.getKey(),
                value.getCount(),
                value.getSum());
        }

        especes = this.herbivores.stream().collect(groupingBy(
                Herbivore::getNomEspece,
                summarizingDouble(Herbivore::getEnergie)));
        out.println("Il reste " + especes.size() + " espèces d'herbivores.");
        for (var entry : especes.entrySet()) {
            var value = entry.getValue();
            out.printf(
                "%s: %d individus qui contiennent en tout %.2f unités d'énergie.\n",
                entry.getKey(),
                value.getCount(),
                value.getSum());
        }

        especes = this.carnivores.stream().collect(groupingBy(
                Carnivore::getNomEspece,
                summarizingDouble(Carnivore::getEnergie)));
        out.println("Il reste " + especes.size() + " espèces de carnivores.");
        for (var entry : especes.entrySet()) {
            var value = entry.getValue();
            out.printf(
                "%s: %d individus qui contiennent en tout %.2f unités d'énergie.",
                entry.getKey(),
                value.getCount(),
                value.getSum());
        }
    }
}
