import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static java.util.stream.Collectors.*;

public final class Lac {
    private final int energieSolaire;
    private final List<Plante> plantes;

    public Lac(int energieSolaire, List<Plante> plantes) {
        this.energieSolaire = energieSolaire;
        this.plantes = plantes;
    }

    /**
     * Avance la simulation d'un cycle.
     */
    public void tick() {
        // À compléter.
        // System.out.println("==============================");

        Double energieTotale = 0.0;
        for(Plante plante: this.plantes){
            energieTotale += plante.getEnergie();
        }

        List<Plante> enfants = new ArrayList<>();
        int i = 0;
        while(i < this.plantes.size()){
            Plante plante = this.plantes.get(i);
            // System.out.println("------------------------------");
            // System.out.printf("Plante: %s\nAge: %d\n",plante.getNomEspece(),plante.getAge());
            double energieSupplementaire = this.energieSolaire*plante.getEnergie()/energieTotale;
            // System.out.printf("Obtient energie solaire %.2f\n", energieSupplementaire);
            energieSupplementaire = plante.survivre(energieSupplementaire);

            // Si la plante ne survit pas
            if(energieSupplementaire == Double.NEGATIVE_INFINITY){
                this.plantes.remove(i);
                // System.out.println("Plante mort");
                continue;
            }
            // System.out.printf("Energie reste %.2f pour reproduire\n",energieSupplementaire);

            // Si la plante survit, elle commence à reproduire
            Map<String, Double> mapEngEnf = plante.reproduire(energieSupplementaire);
            int nombreDesEnfants = mapEngEnf.get("nombreDesEnfants").intValue();
            // System.out.printf("Va faire %d enfants\n",nombreDesEnfants);
            energieSupplementaire = mapEngEnf.get("energieSupplementaire");

            // Reproduction
            UsinePlante usine = new UsinePlante();
            usine.copierParent(plante);
            for(int j=0;j<nombreDesEnfants;++j){
                enfants.add(usine.creerPlante());
            }
            
            // Assimiler l'énergie supplémentaire
            plante.assimilerEnergie(energieSupplementaire);
            // Avoir un an de plus
            plante.prendreAge();

            ++i;
        }
        // Ajouter enfants à l'écosystème
        plantes.addAll(enfants);
    }

    public void imprimeRapport(PrintStream out) {
        var especes = this.plantes.stream().collect(groupingBy(
                Plante::getNomEspece,
                summarizingDouble(Plante::getEnergie)));
        out.println("Il reste " + especes.size() + " espèces de plantes.");
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
