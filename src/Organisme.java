import java.util.Map;

public abstract class Organisme {
    protected String nomEspece;
    protected double energie;
    protected int age;
    protected double besoinEnergie;
    protected double efficaciteEnergie;
    protected double resilience;
    protected double fertilite;
    protected int ageFertilite;
    protected double energieEnfant;

    // Calculer le changement de l'énergie
    protected void changerEnergie(double difference){
        this.energie += difference;
        this.energie = Math.max(this.energie, 0);
    }

    // Déterminer si l'organisme peut survivre
    //---- Si oui, retourner l'énergie supplémentaire
    //---- Si non, retourner infini négatif
    public double survivre(double energieObtenue){
        double energieSupplementaire = energieObtenue;
        energieSupplementaire -= this.besoinEnergie;
        // Si l'énergie comble le besoin
        if(energieSupplementaire >= 0){return energieSupplementaire;}
        // Sinon
        else{
            double tauxDeSurvie = Math.pow(this.resilience, Math.abs(energieSupplementaire));
            if(Math.random() <= tauxDeSurvie){
                // Si l'organisme résiste
                this.changerEnergie(energieSupplementaire);
                return 0.0;
            }else{
                // Organisme mort
                return Double.NEGATIVE_INFINITY;
            }
        }
    }

    // Déterminer si l'organisme va reproduire
    // Retourner un map contenant l'énergie supplémentaire et le nombre des enfants
    public Map<String, Double> reproduire(double energieSupplementaire){
        // Si l'organisme n'a pas encore l'âge de fertilité
        // ou l'organisme n'a pas assez d'énergie
        if(this.age < this.ageFertilite || energieSupplementaire < 1.0){
            return Map.of("energieSupplementaire", energieSupplementaire,
                    "nombreDesEnfants", 0.0);
        }

        // Calculer enfants et l'énergie restante
        int nombreDesEnfants = this.calculerEnfants(energieSupplementaire, 0);
        energieSupplementaire -= nombreDesEnfants*this.energieEnfant;

        // Enlever l'énergie manquante
        if(energieSupplementaire < 0.0){
            this.changerEnergie(energieSupplementaire);
            energieSupplementaire = 0.0;
        }

        return Map.of("energieSupplementaire", energieSupplementaire,
                "nombreDesEnfants", (double) nombreDesEnfants);
    }


    // Calculer le nombre des enfants par récursion
    protected int calculerEnfants(double eng, int enf){
        // Cas de base
        if(eng < 1.0){
            return enf;
        }

        loop:{
            // Rouler pour chaque unité d'énergie
            for(int i=0;i<Math.floor(eng);++i){
                if(Math.random() <= this.fertilite){break loop;}
            }
            // Si plus d'enfant
            return enf;
        }
        // S'il va faire un enfant, compter et continuer
        eng -= this.energieEnfant;
        enf += 1;
        return calculerEnfants(eng, enf);
    }

    // Assimiler l'énergie supplémentaire avec un taux
    public void assimilerEnergie(double energieSupplementaire){
        this.changerEnergie(energieSupplementaire*this.efficaciteEnergie);
    }

    // Avoir un an de plus
    public void prendreAge(){
        this.age += 1;
    }

    // Constructor
    public Organisme(
        String nomEspece,
        double energie,
        int age,
        double besoinEnergie,
        double efficaciteEnergie,
        double resilience,
        double fertilite,
        int ageFertilite,
        double energieEnfant
    ) {
        this.nomEspece = nomEspece;
        this.energie = energie;
        this.age = age;
        this.besoinEnergie = besoinEnergie;
        this.efficaciteEnergie = efficaciteEnergie;
        this.resilience = resilience;
        this.fertilite = fertilite;
        this.ageFertilite = ageFertilite;
        this.energieEnfant = energieEnfant;
    }

    // Getters
    public String getNomEspece() {
        return nomEspece;
    }

    public double getEnergie() {
        return energie;
    }

    public int getAge() {
        return age;
    }

    public double getBesoinEnergie() {
        return besoinEnergie;
    }

    public double getEfficaciteEnergie() {
        return efficaciteEnergie;
    }

    public double getResilience() {
        return resilience;
    }

    public double getFertilite() {
        return fertilite;
    }

    public int getAgeFertilite() {
        return ageFertilite;
    }

    public double getEnergieEnfant() {
        return energieEnfant;
    }


}
