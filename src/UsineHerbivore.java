public final class UsineHerbivore extends UsineAnimal {
    private double voraciteMin;
    private double voraciteMax;

    public UsineHerbivore(){
        super();
        this.initializingJudge.put("voraciteMin",0);
        this.initializingJudge.put("voraciteMax",0);
    }

    @Override
    protected void clearJudge() {
        super.clearJudge();
        this.initializingJudge.put("voraciteMin",0);
        this.initializingJudge.put("voraciteMax",0);
    }

    public Herbivore creerHerbivore(){
        for(int v : this.initializingJudge.values()){
            if(v == 0){
                throw new IllegalArgumentException("Un ou plusieurs attributs n'ont pas été spécifiés.");
            }
        }
        Herbivore objHerbivore = new Herbivore(
            this.nomEspece,
            this.energieEnfant,
            0,
            this.besoinEnergie,
            this.efficaciteEnergie,
            this.resilience,
            this.fertilite,
            this.ageFertilite,
            this.energieEnfant,
            this.debrouillardise,
            this.voraciteMin,
            this.voraciteMax,
            this.aliments,
            this.tailleMaximum);
        return objHerbivore;
    }

    @Override
    public void copierParent(Organisme parent){
        if(!(parent instanceof Herbivore)){
            throw new IllegalArgumentException("Parent doit être un herbivore!");
        }
        super.copierParent(parent);
        setVoraciteMax(((Herbivore)parent).getVoraciteMax());
        setVoraciteMin(((Herbivore)parent).getVoraciteMin());
    }

    @Override
    public String getType(){
        return "un herbivore";
    }

    // Setters
    public void setVoraciteMin(double voraciteMin) {
        this.voraciteMin = voraciteMin;
        this.initializingJudge.put("voraciteMin",1);
    }

    public void setVoraciteMax(double voraciteMax) {
        if(voraciteMax>this.voraciteMin){
            this.voraciteMax = voraciteMax;
            this.initializingJudge.put("voraciteMax",1);
        }else{
            throw new IllegalArgumentException(
                "faut d'abord specifier voraciteMin et n'etre pas iferieur a celui-ci");
        }
    }
}
