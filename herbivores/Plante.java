import java.util.Map;

public class Plante {
    private String nomEspece;
    private double energie;
    private int age;
    private double besoinEnergie;
    private double efficaciteEnergie;
    private double resilience;
    private double fertilite;
    private int ageFertilite;
    private double energieEnfant;


    public Plante() { }

    public Plante(
        String nomEspece,
        double energie,
        int age,
        double besoinEnergie,
        double efficaciteEnergie,
        double resilience,
        double fertilite,
        int ageFertilite,
        double energieEnfant){

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

    private void changerEnergie(double difference){
        this.energie += difference;
        this.energie = Math.max(this.energie, 0);
    }

    public double survivre(double energieDistribuee){
        double energieSupplementaire = energieDistribuee;
        energieSupplementaire -= this.besoinEnergie;
        // Si l'énergie comble le besoin
        if(energieSupplementaire >= 0){return energieSupplementaire;}
        // Sinon
        else{
            double tauxDeSurvie = Math.pow(this.resilience, Math.abs(energieSupplementaire));
            if(Math.random() <= tauxDeSurvie){
                // Si la plante résiste
                this.changerEnergie(energieSupplementaire);
                return 0.0;
            }else{
            // Plante mort
            return Double.NEGATIVE_INFINITY;
            }
        }
    }

    public Map<String, Double> reproduire(double energieSupplementaire){
        // Si la plante n'a pas encore l'âge de fertilité
        // ou la plante n'a pas assez d'énergie
        if(this.age < this.ageFertilite || energieSupplementaire < 1.0){
            return Map.of("energieSupplementaire", energieSupplementaire,
                          "nombreDesEnfants", 0.0);
        }

        // Calculer enfants et l'énergie restante
        int nombreDesEnfants = this.calculerEnfants(energieSupplementaire, 0);
        energieSupplementaire -= nombreDesEnfants*this.energieEnfant;

        if(energieSupplementaire < 0.0){
            this.changerEnergie(energieSupplementaire);
            energieSupplementaire = 0.0;
            
        }
        return Map.of("energieSupplementaire", energieSupplementaire,
                      "nombreDesEnfants", (double) nombreDesEnfants);
    }

    // Calculer enfants par récursion
    private int calculerEnfants(double eng, int enf){
        if(eng < 1.0){
            return enf;
        }
        loop:{
            for(int i=0;i<Math.floor(eng);++i){
                if(Math.random() <= this.fertilite){break loop;}
            }
            return enf;
        }
        eng -= this.energieEnfant;
        enf += 1;
        return calculerEnfants(eng, enf);
    }

    public void assimilerEnergie(double energieSupplementaire){
        this.changerEnergie(energieSupplementaire*this.efficaciteEnergie);
    }

    // Avoir un an de plus
    public void prendreAge(){
        this.age += 1;
    }

    //Getters
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

