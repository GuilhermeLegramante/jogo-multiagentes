import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;
import jason.environment.grid.Location;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Logger;

public class MarsEnv extends Environment {

    public static final int GSize = 15; // grid size
    public static final int GARB  = 16; // garbage code in grid model

	//Movimento dos Agentes
	public static final Term    caminharPolicial = Literal.parseLiteral("caminharPolicial");
	public static final Term    caminharIncendiario = Literal.parseLiteral("caminharIncendiario");
	public static final Term    caminharCivil = Literal.parseLiteral("caminharCivil");
	public static final Term    caminharBombeiro = Literal.parseLiteral("caminharBombeiro");
	
	// Ações dos Agentes
    public static final Term    atearFogo = Literal.parseLiteral("atearFogo");
	public static final Term	apagarFogo = Literal.parseLiteral("apagarFogo");
	public static final Term	prenderIncendiario = Literal.parseLiteral("prenderIncendiario");
	public static final Term	chamarPolicial = Literal.parseLiteral("chamarPolicial");
	public static final Term	chamarBombeiro = Literal.parseLiteral("chamarBombeiro");
	
	// Percepções dos Agentes
	public static final Literal civilFogo = Literal.parseLiteral("civilFogo");
	public static final Literal policialFogo = Literal.parseLiteral("policialFogo");
	public static final Literal bombeiroFogo = Literal.parseLiteral("bombeiroFogo");
	public static final Literal civilIncendiario = Literal.parseLiteral("civilIncendiario");
	public static final Literal policialIncendiario = Literal.parseLiteral("policialIncendiario");
	public static final Literal bombeiroIncendiario = Literal.parseLiteral("bombeiroIncendiario");

    static Logger logger = Logger.getLogger(MarsEnv.class.getName());

    private MarsModel model;
    private MarsView  view;

    @Override
    public void init(String[] args) {
        model = new MarsModel();
        view  = new MarsView(model);
        model.setView(view);
        updatePercepts();
    }

    @Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" fazendo: "+ action);
        try {
            
			if (action.equals(caminharPolicial)){
				model.caminharPolicial();
			}
			else if (action.equals(caminharIncendiario)) {
                model.caminharIncendiario();
			}
			else if (action.equals(caminharCivil)) {
                model.caminharCivil();
			}
			else if (action.equals(caminharBombeiro)) {
                model.caminharBombeiro();
			}			
			else if (action.equals(atearFogo)) {
                model.atearFogo();
			}
			else if (action.equals(apagarFogo)) {
                model.apagarFogo();
            } 
			else if (action.equals(prenderIncendiario)) {
                model.prenderIncendiario();
			}
			else if (action.equals(chamarPolicial)) {
                model.chamarPolicial();
			}
			else if (action.equals(chamarBombeiro)) {
                model.chamarBombeiro();
			}
			else if (action.equals(civilFogo)) {
                model.civilFogo();
            } 
			else if (action.equals(policialFogo)) {
                model.policialFogo();
			}
			else if (action.equals(bombeiroFogo)) {
                model.bombeiroFogo();
			}
			else if (action.equals(civilIncendiario)) {
                model.civilIncendiario();
			}
			else if (action.equals(policialIncendiario)) {
                model.policialIncendiario();
			}
			else if (action.equals(bombeiroIncendiario)) {
                model.bombeiroIncendiario();
			}
			else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        updatePercepts();

        try {
            Thread.sleep(200);
        } catch (Exception e) {}
        informAgsEnvironmentChanged();
        return true;
    }

    /** cria a percepção dos agentes com base no MarsModel */ 
    void updatePercepts() {
        clearPercepts();

        Location bombeiroLoc = model.getAgPos(0);
        Location policialLoc = model.getAgPos(1);
		Location incendiarioLoc = model.getAgPos(2);
		Location civilLoc = model.getAgPos(3);

        Literal posicaoBombeiro = Literal.parseLiteral("pos(bombeiro," + bombeiroLoc.x + "," + bombeiroLoc.y + ")");
        Literal posicaoPolicial = Literal.parseLiteral("pos(policial," + policialLoc.x + "," + policialLoc.y + ")");
		Literal posicaoIncendiario = Literal.parseLiteral("pos(incendiario," + incendiarioLoc.x + "," + incendiarioLoc.y + ")");
		Literal posicaoCivil = Literal.parseLiteral("pos(civil," + civilLoc.x + "," + civilLoc.y + ")");

        addPercept(posicaoBombeiro);
        addPercept(posicaoPolicial);
		addPercept(posicaoIncendiario);
		addPercept(posicaoCivil);
		
        if (model.hasObject(GARB, civilLoc)) {
            addPercept(civilFogo);
        }
        if (model.hasObject(GARB, policialLoc)) {
            addPercept(policialFogo);
        }
		if (model.hasObject(GARB, bombeiroLoc)) {
            addPercept(bombeiroFogo);
        }
				
    }

    class MarsModel extends GridWorldModel {

        public static final int MErr = 2; // max error in pick garb
        int nerr; // number of tries of pick garb
        boolean r1HasGarb = false; // se r1 está carregando lixo ou não

        Random random = new Random(System.currentTimeMillis());

        private MarsModel() {
            super(GSize, GSize, 4);

            // localização inicial dos agentes
            try {
				Location bombeiroLoc = new Location(0, 1);
				setAgPos(0, bombeiroLoc);
								
                Location policialLoc = new Location(0, 2);
                setAgPos(1, policialLoc);
				
				Location incendiarioLoc = new Location(0, 3);
                setAgPos(2, incendiarioLoc);
				
				Location civilLoc = new Location(0, 4);
				setAgPos(3, civilLoc);
				
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
		
		void caminharBombeiro() throws Exception{
			Location bombeiro = getAgPos(0);
			bombeiro.x++;
			
			if (bombeiro.x == getWidth()) {
					bombeiro.x = 0;
					bombeiro.y++;				
			}
			
			if (bombeiro.x == getHeight()) {
					bombeiro.y++;
					return;
			}
			setAgPos(0, bombeiro);
			setAgPos(0, getAgPos(0)); // apenas para desenhá-lo na vista		
		}

		void caminharPolicial() throws Exception{
			Location policial = getAgPos(1);
			policial.x++;
			
			if (policial.x == getWidth()) {
					policial.x = 0;
					policial.y++;				
			}
			
			if (policial.x == getHeight()) {
					policial.y++;
					return;
			}
			setAgPos(1, policial);
			setAgPos(1, getAgPos(1)); // apenas para desenhá-lo na vista		
		}

		void caminharIncendiario() throws Exception{
			Location incendiario = getAgPos(2);
			Random n = new Random();
			int direcao = n.nextInt(2);
			
			if(direcao == 0){
				incendiario.x++;
			}
			
			if(direcao == 1){
				incendiario.y++;
			}
			
			if (incendiario.x == getWidth()) {
					incendiario.x = 0;
					incendiario.y--;		
			}
			
			if (incendiario.y == getHeight()) {
					incendiario.y = 0;
					incendiario.x--;
			}
			
			setAgPos(2, incendiario);
			setAgPos(2, getAgPos(2)); // apenas para desenhá-lo na vista
		}
		
		void caminharCivil() throws Exception{
			Location civil = getAgPos(3);
			civil.x++;
			
			if (civil.x == getWidth()) {
					civil.x = 0;
					civil.y++;				
			}
			
			if (civil.x == getHeight()) {
					civil.y++;
					return;
			}
			setAgPos(3, civil);
			setAgPos(3, getAgPos(3)); // apenas para desenhá-lo na vista		
		}
		
		void atearFogo(){
			System.out.println("Ateou Fogo!");
			Random n = new Random();
			if(n.nextInt(6) == 5){
				add(GARB, getAgPos(2));
			}
			
		}
		
		void apagarFogo(){
			System.out.println("Apagou Fogo!");
		}

		void prenderIncendiario(){
			System.out.println("Prendeu Incendiário!");
		}
		
		void chamarPolicial(){
			System.out.println("Chamou Policial!");
		}

		void chamarBombeiro(){
			System.out.println("Chamouo Bombeiro!");
		}
		
		void civilFogo(){
			System.out.println("Civil percebeu fogo!");
		}
		
		void policialFogo(){
			System.out.println("Policial percebeu fogo!");
		}
		
		void bombeiroFogo(){
			System.out.println("Bombeiro percebeu fogo!");
		}

		void civilIncendiario(){
			System.out.println("Civil percebeu incendiário!");
		}

		void policialIncendiario(){
			System.out.println("Policial percebeu incendiário!");
		}

		void bombeiroIncendiario(){
			System.out.println("Bombeiro percebeu incendiário!");
		}
		
		
    }

    class MarsView extends GridWorldView {

        public MarsView(MarsModel model) {
            super(model, "Mars World", 800);
            defaultFont = new Font("Verdana", Font.BOLD, 10); //alterar fonte padrão
            setVisible(true);
            repaint(); 
        }
        
		@Override
        public void draw(Graphics g, int x, int y, int object) {
            switch (object) {
            case MarsEnv.GARB:
                drawGarb(g, x, y);
                break;
            }
        }

        @Override
        public void drawAgent(Graphics g, int x, int y, Color c, int id) {
			String label = "label"; 
			g.setColor(Color.white);
			
			if (id == 0) {
				label = "Bombeiro";
				c = Color.red;
				g.setColor(Color.white);
				super.drawAgent(g, x, y, c, -1);
				super.drawString(g, x, y, defaultFont, label);
				repaint();
            }
			
			if (id == 1) {
				c = Color.blue;
				label = "Policial";
				g.setColor(Color.white);
				super.drawAgent(g, x, y, c, -1);
				super.drawString(g, x, y, defaultFont, label);
				repaint();
            }
			
			if (id == 2) {
				c = Color.black;
				label = "Incendiário";
				g.setColor(Color.white);
				super.drawAgent(g, x, y, c, -1);
				super.drawString(g, x, y, defaultFont, label);
				repaint();
			}
			
			if (id == 3) {
				c = Color.green;
				label = "Civil";
				g.setColor(Color.white);
				super.drawAgent(g, x, y, c, -1);
				super.drawString(g, x, y, defaultFont, label);
				repaint();
            }         
			super.drawAgent(g, x, y, c, -1);
            if (id == 0) {
                g.setColor(Color.black);
            } else {
                g.setColor(Color.white);
            }
            super.drawString(g, x, y, defaultFont, label);
            repaint();
		}
		
        public void drawGarb(Graphics g, int x, int y) {
            super.drawObstacle(g, x, y);
            g.setColor(Color.white);
            drawString(g, x, y, defaultFont, "Fogo");
        }

    }
	

}
