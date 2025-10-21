public enum Skills {
    
    HERO("Lighting Jump", 20),
    YANGUS("Stun Axe", 30),
    ANGELO("Falcon Cut",15),
    JESSICA("Hellfire",18),
    ENEMIGO1("Water Jet", 15),
    ENEMIGO2("Cura total" , 6),
    ENEMIGO3("Defensa Ferrea", 4),
    ENEMIGO4("Bola de Fuego", 13),

    ;

    private String nombreS;
    private int daño;


    Skills(String nombre, int daño){
        this.nombreS = nombre;
        this.daño = daño;
    }


    public String getNombreS() {
        return nombreS;
    }


    public void setNombreS(String nombre) {
        this.nombreS = nombre;
    }


    public int getDaño() {
        return daño;
    }


    public void setDaño(int daño) {
        this.daño = daño;
    }

    public String toString(){
        return nombreS;
    }

}



