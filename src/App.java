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

        The_Hero the_Hero = new The_Hero("The Hero",22, 0, 21, 6, 6, 1.5f, 6, 3.0f, 5, new ArrayList<>());
        the_Hero.agregarSkill(Skills.HERO);

        Yangus yangus = new Yangus("Yangus", 25, 0, 6, 12, 6, 2.0f, 8, 3.0f, 5, new ArrayList<>());
        yangus.agregarSkill(Skills.YANGUS);

        Jessica jessica = new Jessica("Jessica",16, 10, 6, 6, 10, 1.2f, 7, 3.0f, 12, new ArrayList<>());
        jessica.agregarSkill(Skills.JESSICA);

        Angelo angelo = new Angelo("Angelo", 18, 8, 4, 8, 9, 1.3f, 9, 3.0f, 11, new ArrayList<>());
        angelo.agregarSkill(Skills.ANGELO);

        
        Enemy limo = new Enemy("Limo", 3, 5, 10, 3, 2, 1f, 1, 2f, 2, new ArrayList<>(), TypeEnemy.AGGRESSIVE);
        limo.agregarSkill(Skills.ENEMIGO1);
        Enemy berenjeno = new Enemy("Berenjeno", 5, 11, 9, 3, 6, 1f, 2, 1f, 1, new ArrayList<>(), TypeEnemy.HEALER);
        berenjeno.agregarSkill(Skills.ENEMIGO2);
        Enemy pinchorugo = new Enemy("Pinchorugo", 10, 5, 7, 10, 3, 3f, 1, 2f, 1, new ArrayList<>(), TypeEnemy.DEFENSIVE);
        pinchorugo.agregarSkill(Skills.ENEMIGO3);
        Enemy labibabosa = new Enemy("Labibabosa", 10, 10, 50, 2, 20, 5f, 5, 1f, 1, new ArrayList<>(), TypeEnemy.SORCERER);
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

    // Condicioanl que permite saber si es el turno de un personaje si la listaCharacters contiene a actual que recorre la lista de orden ataque
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
            // Sistema para elegir a que enemigo se le desea hacer daño
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
                    
                    // Aqui si el daño es mayor a la defensa del bojetivo entonces se le hara daño, si el daño es mayor a la suma de la vida y la defensa, afectara a la vida
                    // En caso de que el daño no sea suficiente para sobrepasar la defensa del enemigo la vida quedara intacta al igual que la defensa
                    if(damage > objetivo.getDefense()){
                        objetivo.setHP((objetivo.getHP() + objetivo.getDefense()) - damage);

                        System.out.println(actual.getName() + " atacó a " + objetivo.getName() +
                        " e hizo " + damage + " puntos de daño.");  
                        System.out.println(actual.getName() + " Le ha bajado " + damage + " puntos de daño a la defensa de " + objetivo.getName());
                        System.out.println(actual.getName() + " tiene " + actual.getHP() + " puntos de vida");

                    }else {
                    // Si el daño no es mayor a la defensa el enemigo mitigara por completo el daño del ataque
                        objetivo.getHP();
                        System.out.println("La defensa del enemigo a mitigado el daño por completo");
                    }

                    // Aqui simplemente si la vida es 0 o igula a 0 entronces el enemigo habra sido derrotado
                    if (objetivo.getHP() <= 0) {
                        System.out.println(objetivo.getName() + " ha sido derrotado!");
                        enemy.remove(objetivo);
                        OrdenAtaque.remove(objetivo); // ya no debe tener turno
                    }else {
                        // Si no se le bajo la vida por completo mostrara el daño realizado, por cuanto mitigo el ataque la defensa y la vida con la que el enemigo quedo
                        // Esto si es que el ataque sobrepaso la defensa, si no la vida no sera disminuida
                        System.out.print(actual.getName() + " a realizado " + damage + " puntos de daño ");
                        System.out.println(objetivo.getName() + " mitigo el daño gracias a su defensa de " + objetivo.getDefense() + "\n" + 
                                            objetivo.getName() + " Quedo con " + objetivo.getHP() + " puntos de vida");
                    }
                } else {
                    System.out.println("Opción no válida.");
                }
                break;

            case 2:
                // Aqui se sube la defensa dle personaje x2 por un turno, despues de que acabe el turno la defensa vuelve a ser la inicial
                System.out.println(actual.getName() + "Ha decidido defenderse");
                actual.setDefense(actual.getDefense() * 2);
                System.out.println(actual.getName() + " Tiene por esta ronda una defensa de " + actual.getDefense());
                break;


            case 3:
                // Lanzar una habilidad funciona casi igual que atacar ya que se decide un enemigo al cual atacar 
                System.out.println("\nElige a qué enemigo atacar:");
                for (int i = 0; i < enemy.size(); i++) {
                    Characters e = enemy.get(i);
                    System.out.println((i + 1) + ". " + e.getName() + " (HP: " + e.getHP() + ")");
                }

                // Se le asigna una opcion de a cual enemigo desea atacar
                System.out.print("Opción: ");
                int targetIndex2 = sc.nextInt() - 1; 
                
                // condicional que permite saber filtrar si la opcion es mayor o igual a 0 y si es menor al tamaño de la lista de enemigos podras continuar con el ataque
                if(targetIndex2 >= 0 && targetIndex2 < enemy.size()){
                    Characters objetivo = enemy.get(targetIndex2);
                    int damage = actual.usarHabilidad(0);

                    // Se usa la misma logica que con el ataque, si la habilidad hace mas daño que la vida y la defensa juntas entonces le disminuira la vida
                    // Misma logica que en ataque (case 1)
                    if(damage > objetivo.getDefense()){
                        objetivo.setHP((objetivo.getHP() + objetivo.getDefense()) - damage);
                        System.out.println(actual.getName() + " atacó a " + objetivo.getName() + " con " + actual.verHabilidad(0) + 
                        " e hizo " + damage + " puntos de daño.");   
                        System.out.println(actual.getName() + " Le ha bajado " + damage + " puntos de daño a la defensa de " + objetivo.getName());

                    }else {
                        
                        objetivo.getHP();
                        System.out.println(actual.getName() + " ha atacado con " + actual.verHabilidad(0));
                        System.out.println("La defensa del enemigo a mitigado el daño por completo");
                    }

                    if (objetivo.getHP() <= 0){
                        System.out.println(objetivo.getName() + " ha sido derrotado");
                        enemy.remove(objetivo);
                        OrdenAtaque.remove(objetivo);
                    }else {
                        System.out.print(actual.getName() + " a realizado " + damage + " puntos de daño ");
                        System.out.println(objetivo.getName() + " mitigo el daño gracias a su defensa de " + objetivo.getDefense() + "\n" +
                                            objetivo.getName() + " Quedo con " + objetivo.getHP() + " puntos de vida");
                    }

                } else {
                    System.out.println("Opción no válida.");
                }
                break;               

            // opcion para huir del combate
            case 4:
                System.out.println("¡Has huido del combate!");
                inCombat = false;
                break;

            default:
                System.out.println("Opción aún no implementada.");
                break;
        }
    }
    // Si NO es un personaje entonces actuara el enemigo con una funcion random 
    else {
        if (!listaCharacters.isEmpty()) {
            // accion va a ser igual a un numero random que puede ser 3 opciones (atacar, defenderse y habilidad)
            int accion = (int)(Math.random() * 3) + 1;
            Characters objetivo = listaCharacters.get((int)(Math.random() * listaCharacters.size()));

            switch (accion) {
                // Aqui nuevamente se rehusa el codigo de acciones donde sigue la misma logica de vida + defensa menos el daño
                // Añadiendole el factor random para que cada enemigo decida una accion diferente
                case 1:

                    int damage = actual.getAtack();
        
                    if(damage > objetivo.getDefense()){
                        objetivo.setHP((objetivo.getHP() + objetivo.getDefense()) - damage);
                        System.out.println(actual.getName() + " atacó a " + objetivo.getName() + " e hizo " + damage + " de daño.");
                        System.out.println(actual.getName() + " Le ha bajado " + damage + " puntos de daño a la defensa de " + objetivo.getName());
                    } else {
        
                        objetivo.getHP();
                        System.out.print(actual.getName() + " a realizado " + damage + " puntos de daño ");
                        System.out.println(objetivo.getName() + " Ha mitigado el daño gracias a su defensa de " + objetivo.getDefense() + "\n" + objetivo.getName() + " quedo con " + objetivo.getHP() + " Puntos de vida");
                    }
                    
                    break;

                case 2:
                    
                    System.out.println(actual.getName() + "Ha decidido defenderse");
                    actual.setDefense(actual.getDefense() * 2);
                    System.out.println(actual.getName() + " Tiene por esta ronda una defensa de " + actual.getDefense());
                    break;

                case 3:

                    int damageSkill = actual.usarHabilidad(0);
                    if(damageSkill > objetivo.getDefense()){
                        objetivo.setHP((objetivo.getHP() + objetivo.getDefense()) - damageSkill);
                        System.out.println(actual.getName() + " atacó a " + objetivo.getName() + " con " + actual.verHabilidad(0) + 
                        " e hizo " + damageSkill + " puntos de daño.");  
                        System.out.println(actual.getName() + " Le ha bajado " + damageSkill + " puntos de daño a la defensa de " + objetivo.getName());
                        System.out.println(actual.getName() + " ha quedaod con " + actual.getHP() + " puntos de vida");

                    }else {

                        objetivo.getHP();
                        System.out.println(actual.getName() + " ha atacado con " + actual.verHabilidad(0));
                        System.out.println("La defensa del enemigo " + objetivo.getName() +" a mitigado el daño por completo");
                    }

                    break;
            }
            // si la vida del objetivo llega a ser 0 entonces etse personaje habra sido derrotado, por lo que ya no saldra en la lista de orden ataque y ya no tendra mas acciones
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

        for (Characters c : listaCharacters) {
        if (c.getDefense() % 2 == 0 && c.getDefense() > 0) {
            c.setDefense(c.getDefense() / 2);
        }
    }

        }
    }

    }
}
