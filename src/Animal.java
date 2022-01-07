import java.util.Set;

public abstract class Animal extends Organisme {
    protected double debrouillardise;
    protected double tailleMaximum;
    protected Set<String> aliments;

    public Animal(
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
        energieEnfant);

        this.debrouillardise = debrouillardise;
        this.aliments = aliments;
        this.tailleMaximum = tailleMaximum;
    }

    @Override
    protected void changerEnergie(double difference){
        super.changerEnergie(difference);
        this.energie = Math.min(this.energie, this.tailleMaximum);
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
