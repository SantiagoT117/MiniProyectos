import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Enemy extends Characters {

    private TypeEnemy type; // Se guarda el tipo de enemigo
    private ArrayList<String> skillList; // Almacena las habilidades
    private Random random; // Randomiza el tipo de accion que utiliza

    public Enemy(String name, int hP, int mP, int atack, int defense, int speed, float crit_Damage,
             int luck, float precision, int wisdom, List<Skills> skill, TypeEnemy type) {
        super(name, hP, mP, atack, defense, speed, crit_Damage, luck, precision, wisdom, skill);
        this.type = type;
        this.random = new Random();
        this.skillList = new ArrayList<>();
    }

    public TypeEnemy getType() {
        return type;
    }

    public void setType(TypeEnemy type) {
        this.type = type;
    }

    
    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public ArrayList<String> getSkillList() {
        return skillList;
    }

    public void setSkillList(ArrayList<String> skillList) {
        this.skillList = skillList;
    }

    public void addSkills(String skill){ 
        skillList.add(skill); 
    }
    
    public String decideAction(){ // Acciones de los enemigos
        switch (type) {
            case AGGRESSIVE:
                return "attack";
            case HEALER:
                return getHP() < getHP() * 0.4 ? "heal" : "attack";
            case DEFENSIVE:
                return random.nextBoolean() ? "defend" : "attack";
            case SORCERER:
                return getMP() >= 5 ? "Use skill" : "attack";
            default:
                return "attack";
        }
    }

    @Override
    public String toString() {
        return getName() + 
        "/ Type " + type +
        "/ HP " + getHP() +
        "/ MP " + getMP() +
        "/ Atack " + getAtack() +
        "/ Defense " + getDefense() +
        "/ Speed " + getSpeed() +
        "/ Crit Damage " + getCrit_Damage() +
        "/ Luck " + getLuck() +
        "/ Precision " + getPrecision() + 
        "/ Wisdom " + getWisdom();
    }
    
}