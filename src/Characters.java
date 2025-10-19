import java.util.List;
import java.util.ArrayList;

public class Characters {
    // se ponen todos los atributos que tendran en comun los personajes 
    private String Name;
    private int HP;
    private int MP;
    private int Atack;
    private int Defense;
    private int Speed;
    private float Crit_Damage;
    private int Luck;
    private float Precision;
    private int Wisdom;
    private List<Skills> Skill;
    
    public Characters(String name,int hP, int mP, int atack, int defense, int speed,
                      float crit_Damage, int luck, float precision,int wisdom, List<Skills> skill) {
        this.Name = name;
        this.HP = hP;
        this.MP = mP;
        this.Atack = atack;
        this.Defense = defense;
        this.Speed = speed;
        this.Crit_Damage = crit_Damage;
        this.Luck = luck;
        this.Precision = precision;
        this.Wisdom = wisdom;
        this.Skill = new ArrayList<>();
    }
    
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getHP() {
        return HP;
    }

    public int getWisdom() {
        return Wisdom;
    }

    public void setWisdom(int wisdom) {
        Wisdom = wisdom;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int mP) {
        MP = mP;
    }

    public int getAtack() {
        return Atack;
    }

    public void setAtack(int atack) {
        Atack = atack;
    }

    public int getDefense() {
        return Defense;
    }

    public void setDefense(int defense) {
        Defense = defense;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public float getCrit_Damage() {
        return Crit_Damage;
    }

    public void setCrit_Damage(float crit_Damage) {
        Crit_Damage = crit_Damage;
    }

    public int getLuck() {
        return Luck;
    }

    public void setLuck(int luck) {
        Luck = luck;
    }

    public float getPrecision() {
        return Precision;
    }

    public void setPrecision(float precision) {
        Precision = precision;
    }

    public Skills getSkill(int indice) {
        return Skill.get(indice);
    }
    
    public int usarHabilidad(int indice){
        Skills s = getSkill(indice);    
        return s.getDaño();
    }

    public void agregarSkill(Skills s){
        Skill.add(s);
    }

    public String verHabilidad(int indice){
        Skills s = getSkill(indice);
        return s.getNombreS();
    }
    
}