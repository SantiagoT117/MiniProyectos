import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner;


//ejemplo para verificar que todo este funcionando 

public class App {
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);

        // Listas donde se almacenaran las habilidades de cada personaje y de los enemigos segun su tipo de comportamiento

        ArrayList<String> skillsAggressive = new ArrayList<>();
        skillsAggressive.add("Placaje");

        ArrayList<String> skillsHealer = new ArrayList<>();
        skillsHealer.add("Cura Total");

        ArrayList<String> skillsDefensive = new ArrayList<>();
        skillsDefensive.add("Defensa Ferrea");

        ArrayList<String> skillsSorcerer = new ArrayList<>();
        skillsSorcerer.add("Bola de Fuego");

        // Creacion de los personajes principales y enemigos con sus atributos predeterminados de nivel 1

        The_Hero the_Hero = new The_Hero("The Hero",22, 0, 8, 6, 6, 1.5f, 3, 3.0f, 5, new ArrayList<>());
        the_Hero.agregarSkill(Skills.HERO);

        Yangus yangus = new Yangus("Yangus", 25, 0, 18, 12, 6, 2.0f, 8, 3.0f, 5, new ArrayList<>());
        yangus.agregarSkill(Skills.YANGUS);

        Jessica jessica = new Jessica("Jessica",16, 10, 6, 6, 10, 1.2f, 7, 3.0f, 12, new ArrayList<>());
        jessica.agregarSkill(Skills.JESSICA);

        Angelo angelo = new Angelo("Angelo", 18, 8, 8, 8, 9, 1.3f, 9, 3.0f, 11, new ArrayList<>());
        angelo.agregarSkill(Skills.ANGELO);

        
        Enemy limo = new Enemy("Limo", 3, 5, 5, 3, 2, 1f, 1, 2f, 2, new ArrayList<>(), TypeEnemy.AGGRESSIVE);
        limo.agregarSkill(Skills.ENEMIGO1);
        Enemy berenjeno = new Enemy("Berenjeno", 5, 10, 2, 3, 6, 1f, 2, 1f, 1, new ArrayList<>(), TypeEnemy.HEALER);
        berenjeno.agregarSkill(Skills.ENEMIGO2);
        Enemy pinchorugo = new Enemy("Pinchorugo", 10, 5, 2, 10, 1, 3f, 1, 2f, 1, new ArrayList<>(), TypeEnemy.DEFENSIVE);
        pinchorugo.agregarSkill(Skills.ENEMIGO3);
        Enemy labibabosa = new Enemy("Labibabosa", 10, 10, 50, 2, 2, 5f, 5, 1f, 1, new ArrayList<>(), TypeEnemy.SORCERER);
        labibabosa.agregarSkill(Skills.ENEMIGO4);
        // Listas con todos los personajes y enemigos 

        ArrayList<Characters> enemy = new ArrayList<>();
        enemy.add(limo);
        enemy.add(berenjeno);
        enemy.add(pinchorugo);
        enemy.add(labibabosa);


        ArrayList <Characters> listaCharacters = new ArrayList<Characters>();    
        listaCharacters.add(the_Hero);
        listaCharacters.add(yangus);
        listaCharacters.add(jessica);
        listaCharacters.add(angelo);
        
        // Lista segun el orden de ataque

        ArrayList <Characters> OrdenAtaque = new ArrayList<Characters>();

        // Lista con TODOS los personajes

        ArrayList <Characters> todos = new ArrayList<Characters>();
        todos.add(the_Hero);
        todos.add(yangus);
        todos.add(jessica);
        todos.add(angelo);
        todos.add(limo);
        todos.add(berenjeno);
        todos.add(pinchorugo);
        todos.add(labibabosa);

        while(!todos.isEmpty()){
            Characters mosFast = todos.get(0);

            for(Characters c : todos){
                if(c.getSpeed() > mosFast.getSpeed()){
                    mosFast = c;
                }
            }

            OrdenAtaque.add(mosFast);

            todos.remove(mosFast);
            }


            System.out.println("orden de ataque");
            for (Characters c : OrdenAtaque){
            System.out.println(c.getName() + "(velocidad: " + c.getSpeed() + ")");
            }

        boolean inCombat = true;
        int turnIndex = 0; // controla quién ataca

while (inCombat) {

    // si ya no quedan enemigos, gana el jugador
    if (enemy.isEmpty()) {
        System.out.println("\n¡Has ganado el combate!");
        break;
    }

    // si todos los héroes están muertos (opcional si ya implementas HP en todos)
    if (listaCharacters.isEmpty()) {
        System.out.println("\n¡Has sido derrotado!");
        break;
    }

    // obtener el personaje que actúa este turno
    Characters actual = OrdenAtaque.get(turnIndex);

    System.out.println("\n----------------------------------------");
    System.out.println("Turno de: " + actual.getName());

    // Si es un héroe (no enemigo)
    if (listaCharacters.contains(actual)) {
        System.out.println("\n¿Qué deseas hacer?");
        System.out.println("1. Atacar");
        System.out.println("2. Defenderse");
        System.out.println("3. Usar habilidad");
        System.out.println("4. Huir");
        System.out.print("Elige una opción: ");
        int opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                System.out.println("\nElige a qué enemigo atacar:");
                for (int i = 0; i < enemy.size(); i++) {
                    Characters e = enemy.get(i);
                    System.out.println((i + 1) + ". " + e.getName() + " (HP: " + e.getHP() + ")");
                }

                System.out.print("Opción: ");
                int targetIndex = sc.nextInt() - 1;

                if (targetIndex >= 0 && targetIndex < enemy.size()) {
                    Characters objetivo = enemy.get(targetIndex);
                    int damage = actual.getAtack();
                    objetivo.setHP((objetivo.getHP() + objetivo.getDefense()) - damage);

                    System.out.println(actual.getName() + " atacó a " + objetivo.getName() +
                                       " e hizo " + damage + " puntos de daño.");             

                    if (objetivo.getHP() <= 0) {
                        System.out.println(objetivo.getName() + " ha sido derrotado!");
                        enemy.remove(objetivo);
                        OrdenAtaque.remove(objetivo); // ya no debe tener turno
                    }else {
                        System.out.println(objetivo.getName() + " mitigo el daño gracias a su defensa de " + objetivo.getDefense() + "\n" + 
                                            objetivo.getName() + " Quedo con " + objetivo.getHP() + " puntos de vida");
                    }
                } else {
                    System.out.println("Opción no válida.");
                }
                break;
            case 3:

                System.out.println("\nElige a qué enemigo atacar:");
                for (int i = 0; i < enemy.size(); i++) {
                    Characters e = enemy.get(i);
                    System.out.println((i + 1) + ". " + e.getName() + " (HP: " + e.getHP() + ")");
                }

                System.out.print("Opción: ");
                int targetIndex2 = sc.nextInt() - 1; 
                
                if(targetIndex2 >= 0 && targetIndex2 < enemy.size()){
                    Characters objetivo = enemy.get(targetIndex2);
                    int damage = actual.usarHabilidad(0);
                    objetivo.setHP((objetivo.getHP() + objetivo.getDefense()) - damage);

                    System.out.println((actual.getName() + " ataco a " + objetivo.getName()) + " con " + actual.getSkill(0) +
                                        " e hizo " + damage + " puntos de daño ");

                    if (objetivo.getHP() <= 0){
                        System.out.println(objetivo.getName() + " ha sido derrotado");
                        enemy.remove(objetivo);
                        OrdenAtaque.remove(objetivo);
                    }else {
                        System.out.println(objetivo.getName() + " mitigo el daño gracias a su defensa de " + objetivo.getDefense() +
                                            objetivo.getName() + " Quedo con " + objetivo.getHP() + " puntos de vida");
                    }

                } else {
                    System.out.println("Opción no válida.");
                }
                break;               

            case 4:
                System.out.println("¡Has huido del combate!");
                inCombat = false;
                break;

            default:
                System.out.println("Opción aún no implementada.");
                break;
        }
    }
    // Si es enemigo, puede atacar automáticamente
    else {
        if (!listaCharacters.isEmpty()) {
            Characters objetivo = listaCharacters.get((int)(Math.random() * listaCharacters.size()));
            int damage = actual.getAtack();
            objetivo.setHP(objetivo.getHP() - damage);
            System.out.println(actual.getName() + " atacó a " + objetivo.getName() + " e hizo " + damage + " de daño.");

            if (objetivo.getHP() <= 0) {
                System.out.println(objetivo.getName() + " ha caído en combate!");
                listaCharacters.remove(objetivo);
                OrdenAtaque.remove(objetivo);
            }
        }
    }

    // pasar al siguiente turno
    turnIndex++;

    // si llegamos al final de la lista, reiniciar (nuevo "round")
    if (turnIndex >= OrdenAtaque.size()) {
        turnIndex = 0;
        System.out.println("\n--- Nuevo turno ---");
    }

    }
}
}