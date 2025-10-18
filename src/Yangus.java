import java.util.ArrayList;
import java.util.List;

public class Yangus extends Characters{

    public Yangus(String name,int hP, int mP, int atack, int defense, int speed, float crit_Damage, int luck, float precision,
            int wisdom, List<Skills> skill){
        super(name, hP, mP, atack, defense, speed, crit_Damage, luck, precision, wisdom, skill);
    }

    public String toString(){
        return getName() + 
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