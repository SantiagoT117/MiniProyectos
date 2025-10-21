import javax.swing.*;
import java.awt.*;
import java.lang.classfile.instruction.SwitchCase;
import java.util.ArrayList;
import java.util.Random;
import java.applet.AudioClip;


public class GUI extends JFrame {

    // se declaran los botones, paneles y el textarea que usaremos
    private JButton btAtacar, btDefender, btHabilidad, btHuir, btVerHeroes;
    private JPanel panelHeroes, panelEnemigos;
    private JTextArea txtRegistro;

    private ArrayList<Characters> todos = new ArrayList<>();
    private ArrayList<Characters> listaCharacters = new ArrayList<>();
    private ArrayList<Characters> enemy = new ArrayList<>();
    private ArrayList<Characters> ordenAtaque = new ArrayList<>();

    private java.util.Map<Characters, JProgressBar> barrasVida = new java.util.HashMap<>();
    private java.util.Map<Characters, JProgressBar> barrasDefensa = new java.util.HashMap<>();

    // variables para la funcion de combate 
    private int turnIndex = 0;
    private boolean inCombat = false;
    private Characters personajeActual;
    
    public GUI(){         
        
        // titulo de la gui lo normal juntos con llamdos de variables que nos ayudaran agestionar el combate
        setTitle("Dragon Quest VIII");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        iniciarPersonajes();
        
        JLabel Titulito = new JLabel("Bienvenido al sistema de combate", SwingConstants.CENTER);
        add(Titulito, BorderLayout.NORTH);
        
        // creacion del panel central donde esta el panel de heroes y enemigos 
        JPanel panelCentro = new JPanel(new GridLayout(1,2,10,10));
        panelHeroes = crearPanelPersonajes("Heroes");
        panelEnemigos = crearPanelPersonajes("Enemigos");
        panelCentro.add(new JScrollPane(panelHeroes));
        panelCentro.add(new JScrollPane(panelEnemigos));
        add(panelCentro, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new BorderLayout());
        
        // creacion de los botones
        JPanel panelBotones = new JPanel();
        btAtacar = new JButton("Atacar");
        btDefender = new JButton("Defender");
        btHabilidad = new JButton("Habilidad");
        btVerHeroes = new JButton("Estadisticas");
        btHuir = new JButton("Huir");
        
        //adicion de los botones
        panelBotones.add(btAtacar);
        panelBotones.add(btDefender);
        panelBotones.add(btHabilidad);
        panelBotones.add(btVerHeroes);
        panelBotones.add(btHuir);
        panelInferior.add(panelBotones, BorderLayout.NORTH);
        
        txtRegistro = new JTextArea(8,40);
        txtRegistro.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtRegistro);
        panelInferior.add(scroll, BorderLayout.CENTER);
        
        add(panelInferior, BorderLayout.SOUTH);
        
        configurarBotones();

        setVisible(true);
        
        MostrarOrdenAtaque();
        inCombat = true;
        turnIndex = 0;
        IniciarTurno();
        
    }
    
    // funcion que nos permite configurar los botones con addactionlistener y que queremos que hag cada uno
    private void configurarBotones(){
        // boton de ataque
        btAtacar.addActionListener(e ->{

            // se le puso sonido cada que se interactue con este boton :D
            AudioClip Sounds;
            Sounds = java.applet.Applet.newAudioClip(getClass().getResource("/bonk-sound-effect-36055.wav"));
            Sounds.play();

            // condicional que nos permite saber si el personaje actual esta disponible, esto ya que si el personaje es derrotado se quita de la listaCharacters
            if(personajeActual != null && listaCharacters.contains(personajeActual)){
                // se llama a seleccionarobjetivo
                seleccionarObjetivo(personajeActual, false, true);
            }
        });
        // boton de defensa
        btDefender.addActionListener(e -> {

            // tambein se le puso soniditos :D
            AudioClip Sounds;
            Sounds = java.applet.Applet.newAudioClip(getClass().getResource("/armament-haki-busoshoku-haki-v1.wav/"));
            Sounds.play();

            // aqui es casi lo mismo con la diferencia que aqui se pone el funcionamiento de defenderse
            if (personajeActual != null && listaCharacters.contains(personajeActual)) {
                txtRegistro.append(personajeActual.getName() + " se defiende.\n");
                personajeActual.setDefense(personajeActual.getDefense() * 2);
                pasarTurno();
            }
        });

        // boton de habilidad
        btHabilidad.addActionListener(e -> {
            // es lo mismo que se usa con ataque, con la diferencia que se le cambian algunas variables que entran a la funcion seleccionar objetivo
            if (personajeActual != null && listaCharacters.contains(personajeActual)) {
                seleccionarObjetivo(personajeActual, true, true);
            }
        });
        // boton para ver las estadisticas de los heroes, solo muestra la vida, cuanto ataque tiene y el nombre de la habilidad
        btVerHeroes.addActionListener(e -> {
            VerHeroes();
        });

        // boton para huir de la batalla
        btHuir.addActionListener(e -> {

            if (personajeActual != null && listaCharacters.contains(personajeActual)) {
                txtRegistro.append("¡Has huido del combate!\n");
                inCombat = false;
                desactivarBotones();
            }
        });

    }
    // funcion para desactivar los botones una vez terminada la batalla
    private void desactivarBotones() {
        btAtacar.setEnabled(false);
        btDefender.setEnabled(false);
        btHabilidad.setEnabled(false);
        btHuir.setEnabled(false);
    }

    // funcion que activa los botones una vez corre la GUI
    private void activarBotones() {
        btAtacar.setEnabled(true);
        btDefender.setEnabled(true);
        btHabilidad.setEnabled(true);
        btHuir.setEnabled(true);
    }
    
    // funcion para crear los paneles de los personajes
    private JPanel crearPanelPersonajes(String titulo){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        
        // se crea una lista que contenga a los heroes y enemigos y se recorre con un ciclo for para ir añadiendolos 1 a 1
        ArrayList<Characters> lista = titulo.equals("Heroes") ? listaCharacters : enemy;
        
        for( Characters c : lista){
            JPanel item = new JPanel(new GridLayout(3,1));
            item.setBorder(BorderFactory.createEtchedBorder());
            item.add(new JLabel("Nombre: " + c.getName()));
            
            // se crean dos barras progresivas para ver la vida y defensa a tiempo de juego 
            JProgressBar barraVida = new JProgressBar(0,100);
            barraVida.setValue(Math.min(c.getHP(), 100));
            barraVida.setString("HP: " + c.getHP());
            barraVida.setStringPainted(true);
            item.add(barraVida);
            
            JProgressBar barraDefensa = new JProgressBar(0,100);
            barraDefensa.setValue(Math.min(c.getDefense(), 100));
            barraDefensa.setString("Defensa: " + c.getDefense());
            barraDefensa.setStringPainted(true);
            item.add(barraDefensa);
            panel.add(item);
            barrasVida.put(c, barraVida);
            barrasDefensa.put(c, barraDefensa);
        }
        return panel;
        
    }
    // funcion que crea a los perosnajes 
    private void iniciarPersonajes(){
        
        // esto es practicamnete un copiar y pegar de lo que ya tenia en la app 
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

        
        Enemy limo = new Enemy("Geyser", 150, 20, 25, 20, 18, 1f, 10, 2f, 20, new ArrayList<>(), TypeEnemy.AGGRESSIVE);
        limo.agregarSkill(Skills.ENEMIGO1);
        Enemy berenjeno = new Enemy("Berenjeno", 5, 11, 9, 3, 6, 1f, 2, 1f, 1, new ArrayList<>(), TypeEnemy.HEALER);
        berenjeno.agregarSkill(Skills.ENEMIGO2);
        Enemy pinchorugo = new Enemy("Pinchorugo", 10, 5, 7, 10, 3, 3f, 1, 2f, 1, new ArrayList<>(), TypeEnemy.DEFENSIVE);
        pinchorugo.agregarSkill(Skills.ENEMIGO3);
        Enemy labibabosa = new Enemy("Labibabosa", 10, 10, 50, 2, 5, 5f, 5, 1f, 1, new ArrayList<>(), TypeEnemy.SORCERER);
        labibabosa.agregarSkill(Skills.ENEMIGO4);

        // y aqui se añaden a sus respecivas listas

        enemy.add(limo);
        enemy.add(berenjeno);
        enemy.add(pinchorugo);
        enemy.add(labibabosa);
   
        listaCharacters.add(the_Hero);
        listaCharacters.add(yangus);
        listaCharacters.add(jessica);
        listaCharacters.add(angelo);

        todos.add(the_Hero);
        todos.add(yangus);
        todos.add(jessica);
        todos.add(angelo);
        todos.add(limo);
        todos.add(berenjeno);
        todos.add(pinchorugo);
        todos.add(labibabosa);

        // se hace visible para que aparezcan en la GUI
        setVisible(true);


    }

    // funcion para mostrar el orden de ataque como lo mostraba en la terminal de la app
    private void MostrarOrdenAtaque(){
        // es muy parecida por no decir iguales a la logica del app que organiza el orden de ataque 
        ArrayList<Characters> copia = new ArrayList<>(todos);
        ordenAtaque.clear();

        while(!copia.isEmpty()){
            Characters masRapido = copia.get(0);
            for(Characters c : copia){
                if (c.getSpeed() > masRapido.getSpeed()){
                    masRapido = c;
                }
            }
            ordenAtaque.add(masRapido);
            copia.remove(masRapido);
        }
        txtRegistro.append("Orden ataque:\n");
        for(Characters c : ordenAtaque){
            txtRegistro.append(c.getName() + " (velocidad: " + c.getSpeed() + ")\n");
        }
        txtRegistro.append("\n----------------------------------------\n");

    }

    // funcion que permite iniciar el turno 
    private void IniciarTurno() {
        // condicional para saber si se esta en combate o no 
        if (!inCombat) return;

        personajeActual = ordenAtaque.get(turnIndex);
        txtRegistro.append("\n----------------------------------------\n");
        txtRegistro.append("Turno de: " + personajeActual.getName() + "\n");

        // aqui simplemente se pregunta que si el heroe esta vivo active los botones si no los desactiva y pasa al turno del enemigo
        if (listaCharacters.contains(personajeActual)) {
            activarBotones();
        } else {
            desactivarBotones();
            turnoEnemigo(personajeActual);
        }
    }

    // funcion para seleccionar el objetivo
    private void seleccionarObjetivo(Characters actual, boolean EsHabilidad, boolean EsJugador){
        if(enemy.isEmpty()){
            txtRegistro.append("\n" + "No quedan enemigos, has ganado");
            return;
        }

        // al tener botones la logica es diferente por lo que se hizo que cuandoi ataque creara una ventana emergente donde se pueda seleccionar a que enemigo atacar
        String[] nombresEnemigos = enemy.stream()
            .map(e ->  e.getName() + "HP: " + e.getHP() + ")")
            .toArray(String[]::new);
        int targetIndex = JOptionPane.showOptionDialog(
            this,
            "Elige a qué enemigo atacar:",
            "Selecciona objetivo",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            nombresEnemigos,
            nombresEnemigos[0]
        );

        // aqui simplemente verifica si el enemigo esta en el rango especifico de enemigos y reliza el ataque
        if(targetIndex >= 0 && targetIndex < enemy.size()){
            Characters objetivo = enemy.get(targetIndex);
            realizarAtaque(actual, objetivo, EsHabilidad, EsJugador);

        }else {
            txtRegistro.append("Ataque cancelado");
            pasarTurno();
        }
        
    }

    // funcion para realizar el ataque, esta viene con parametros que nos permitira saber quien hara el ataque, el afectado, si es habilidad o no y si es un jugador o es la IA
    private void realizarAtaque(Characters actual, Characters objetivo, boolean EsHabilidad, boolean EsJugador){
        // diferenciador si es habilidad se toma la habilidad con su daño y se le resta la defensa / 2 y si no es habilidad se hace lo mismo peor con ataque
        int damage = EsHabilidad 
            ? (actual.usarHabilidad(0) - (objetivo.getDefense() / 2))
            : (actual.getAtack() - (objetivo.getDefense() / 2));
        
        // logica casi igual a la del app
        if (damage < 0) damage = 0;

        objetivo.setHP(objetivo.getHP() - damage);
        if(objetivo.getHP() < 0) objetivo.setHP(0);

        txtRegistro.append(actual.getName() + " Ataco a " + objetivo.getName() + " e hizo " + damage + " Puntos de daño " + "\n");
        txtRegistro.append(objetivo.getName() + " tiene ahora " + objetivo.getHP()+ " puntos de vida");

        if(objetivo.getHP() <= 0){
            txtRegistro.append(objetivo.getName() + " ha sido derrotado");
            enemy.remove(objetivo);
            ordenAtaque.remove(objetivo);
        }
        // actualiza las barras de vida 
        actualizarBarras();
        pasarTurno();
    }

    // funcion para cuando ataca la IA
    private void turnoEnemigo(Characters actual){
        if(listaCharacters.isEmpty()){
            txtRegistro.append("Todos los heroes han sido derrotados");
            inCombat = false;
            return;
        }

        //parte casi igual a la del app
        int accion = (int)(Math.random() * 3) + 1;
        Characters objetivo = listaCharacters.get((int)(Math.random() * listaCharacters.size()));

        switch (accion) {
            case 1 -> realizarAtaque(actual, objetivo, false, false);
            case 2 -> {
                txtRegistro.append(actual.getName() + " se defiende");
                actual.setDefense(actual.getDefense() * 2);
                actualizarBarras();
                pasarTurno();
            }
            case 3 -> realizarAtaque(actual, objetivo, true, false);
        
        }
    }

    // funcion para pasar el turno una vez seleccionado una de las 4 opciones
    private void pasarTurno(){
        turnIndex++;
        if(turnIndex >= ordenAtaque.size()){
            turnIndex = 0;
            txtRegistro.append("\n" + "-------- Nuevo Turno -------");
            // se reinicia la defensa de  los personajes que se defendieron 
            for(Characters c : ordenAtaque){
                if(c.getDefense() % 2 == 0 && c.getDefense() > 0){
                    c.setDefense(c.getDefense() / 2);
                }
            }
        }

        if(enemy.isEmpty()){
            txtRegistro.append("\n" +"Has ganado el combate");
            inCombat = false;
            return;
        }

        if(listaCharacters.isEmpty()){
            txtRegistro.append("\n" + "Has sido derrotado :C");
            inCombat = false;
            return;
        }
        actualizarBarras();

        SwingUtilities.invokeLater(() -> IniciarTurno());

    }

    // funcion que actualiza las barreras para que vayan mostrando el daño realizado 
    private void actualizarBarras() {
    for (Characters c : todos) {
        // verifica que el perosnaje tenga la barra de vida registrada
        if (barrasVida.containsKey(c)) {
            JProgressBar vida = barrasVida.get(c);
            vida.setValue(Math.min(c.getHP(), 100));
            vida.setString("HP: " + c.getHP());
        }
        if (barrasDefensa.containsKey(c)) {
            JProgressBar def = barrasDefensa.get(c);
            def.setValue(Math.min(c.getDefense(), 100));
            def.setString("Defensa: " + c.getDefense());
            }
        }
    }

    // funcion para ver a los heroes y sus stats como se dijo solo muestra ataque, el nombre dle heroe y el nombre de la habilidad
    private void VerHeroes(){
        txtRegistro.append("Estadisticas heroes: " + "\n");
        for(Characters c : listaCharacters){
            txtRegistro.append(c.getName() + "\n" + "Vida: " + c.getHP() + "\n" + 
            "Ataque: " + c.getAtack() + "\n" + "Habilidad: " + c.getSkill(0) + "\n" + "-------------" + "\n");
        }
    }

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new GUI());
}

}
