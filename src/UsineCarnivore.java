public final class UsineCarnivore extends UsineAnimal {

    public UsineCarnivore(){
        super();
    }

    public Carnivore creerCarnivore(){
        for(int v : this.initializingJudge.values()){
            if(v == 0){
                throw new IllegalArgumentException("Un ou plusieurs attributs n'ont pas été spécifiés.");
            }
        }
        Carnivore objCarnivore = new Carnivore(
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
                this.aliments,
                this.tailleMaximum);
        return objCarnivore;
    }

    @Override
    public void copierParent(Organisme parent) {
        if(!(parent instanceof Carnivore)){
            throw new IllegalArgumentException("Parent doit être un Carnivore!");
        }
        super.copierParent(parent);
    }

    @Override
    public String getType(){
        return "un carnivore";
    }
}
