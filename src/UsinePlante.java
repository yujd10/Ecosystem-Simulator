public final class UsinePlante extends Usine {

    public UsinePlante() {
        super();
    }

    public Plante creerPlante() {
        for(int v : this.initializingJudge.values()){
            if(v == 0){
                throw new IllegalArgumentException("Un ou plusieurs attributs n'ont pas été spécifiés.");
            }
        }
        Plante objPlante = new Plante(
            this.nomEspece,
            this.energieEnfant,
            0,
            this.besoinEnergie,
            this.efficaciteEnergie,
            this.resilience,
            this.fertilite,
            this.ageFertilite,
            this.energieEnfant);
        return objPlante;
    }

    @Override
    public void copierParent(Organisme parent){
        if(!(parent instanceof Plante)){
            throw new IllegalArgumentException("Parent doit être une plante!");
        }
        super.copierParent(parent);
    }

    @Override
    public String getType(){
        return "une plante";
    }

    // Setters
    public void setNomEspece(String nomEspece) {
        if (nomEspece != null) {
            this.nomEspece = nomEspece;
            this.initializingJudge.put("nomEspece",1);
        }else{
            throw new IllegalArgumentException("nomEspece ne peut pas etre NULL!!!!!");
        }
    }

    public void setBesoinEnergie(double besoinEnergie) {
        if (besoinEnergie > 0) {
            this.besoinEnergie = besoinEnergie;
            this.initializingJudge.put("besoinEnergie",1);
        }else{
            throw new IllegalArgumentException("faut etre > 0");
        }
    }

    public void setEfficaciteEnergie(double efficaciteEnergie) {
        if (efficaciteEnergie >= 0 && efficaciteEnergie <= 1) {
            this.efficaciteEnergie = efficaciteEnergie;
            this.initializingJudge.put("efficaciteEnergie",1);
        }else{
            throw new IllegalArgumentException("faut etre entre 0 et 1 ");
        }
    }

    public void setResilience(double resilience) {
        if (resilience >= 0 && resilience <= 1) {
            this.resilience = resilience;
            this.initializingJudge.put("resilience",1);
        }else{
            throw new IllegalArgumentException("faut etre entre 0 et 1 ");
        }
    }

    public void setFertilite(double fertilite) {
        if(fertilite >=0 && fertilite <=1){
        this.fertilite = fertilite;
        this.initializingJudge.put("fertilite",1);
        }else{
            throw new IllegalArgumentException("faut etre entre 0 et 1");
        }
    }

    public void setAgeFertilite(int ageFertilite) {
        if (ageFertilite >= 0) {
            this.ageFertilite = ageFertilite;
            this.initializingJudge.put("ageFertilite",1);
        }else{
            throw new IllegalArgumentException("faut etre >= 0");
        }
    }

    public void setEnergieEnfant(double energieEnfant) {
        if (energieEnfant > 0) {
            this.energieEnfant = energieEnfant;
            this.initializingJudge.put("energieEnfant",1);
        }else{
            throw new IllegalArgumentException("faut etre > 0");
        }
    }
}