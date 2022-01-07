import java.util.HashSet;
import java.util.Set;

public abstract class UsineAnimal extends Usine {
    protected double debrouillardise;
    protected Set<String> aliments;
    protected double tailleMaximum;
    

    public UsineAnimal(){
        super();
        this.initializingJudge.put("debrouillardise",0);
        this.initializingJudge.put("aliments",0);
        this.initializingJudge.put("tailleMaximum",0);

        this.aliments = new HashSet<>();
    }

    @Override
    protected void clearJudge() {
        super.clearJudge();
        this.initializingJudge.put("debrouillardise",0);
        this.initializingJudge.put("aliments",0);
        this.initializingJudge.put("tailleMaximum",0);
    }

    @Override
    public void copierParent(Organisme parent){
        if(!(parent instanceof Animal)){
            throw new IllegalArgumentException("Parent doit être un animal!");
        }
        super.copierParent(parent);
        setDebrouillardise(((Animal)parent).getDebrouillardise());
        setAliments(((Animal)parent).getAliments());
        setTailleMaximum(((Animal)parent).getTailleMaximum());
    }

    // Setters
    public void setDebrouillardise(double debrouillardise) {
        if(debrouillardise>=0 && debrouillardise<=1){
            this.debrouillardise = debrouillardise;
            this.initializingJudge.put("debrouillardise",1);
        }else{
            throw new IllegalArgumentException("faut etre entre 0 et 1");
        }
        
    }

    public void setAliments(Set<String> aliments) {
        this.aliments = aliments;
        this.initializingJudge.put("aliments",1);
    }

    public void addAliment(String aliment) {
        this.aliments.add(aliment);
        this.initializingJudge.put("aliments",1);
    }

    public void setTailleMaximum(double tailleMaximum){
        if(tailleMaximum>=this.energieEnfant){
            this.tailleMaximum = tailleMaximum;
            this.initializingJudge.put("tailleMaximum",1);
        }else{
            throw new IllegalArgumentException(
                "faut d'abord specifier energieEnfant et n'etre pas iferieur a celui-ci");
        }
    }

    public void defautTailleMaximum(){
        this.tailleMaximum = 10*this.energieEnfant;
        this.initializingJudge.put("tailleMaximum",1);
    }
}
