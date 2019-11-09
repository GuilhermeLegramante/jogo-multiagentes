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
import javax.swing.JOptionPane;

public class MarsEnv extends Environment {

    public static final int GSize = 15; // grid size
    public static final int GARB  = 16; // garbage code in grid model

	//Movimento dos Agentes
	public static final Term    caminharIncendiario = Literal.parseLiteral("caminharIncendiario");
	public static final Term    caminharCivil = Literal.parseLiteral("caminharCivil");

	// Ações dos Agentes
    public static final Term    atearFogo = Literal.parseLiteral("atearFogo");
	public static final Term	apagarFogo = Literal.parseLiteral("apagarFogo");
	public static final Term	prenderIncendiario = Literal.parseLiteral("prenderIncendiario");

	// Percepções dos Agentes
	public static final Literal civilFogo = Literal.parseLiteral("civilFogo");
	public static final Literal policialFogo = Literal.parseLiteral("policialFogo");
	public static final Literal bombeiroFogo = Literal.parseLiteral("bombeiroFogo");
	public static final Literal civilIncendiario = Literal.parseLiteral("civilIncendiario");
	public static final Literal policialIncendiario = Literal.parseLiteral("policialIncendiario");
	public static final Literal bombeiroIncendiario = Literal.parseLiteral("bombeiroIncendiario");

    static Logger logger = Logger.getLogger(MarsEnv.class.getName());

    private Model model;
    private View  view;

    @Override
    public void init(String[] args) {
        model = new Model();
        view  = new View(model);
        model.setView(view);
        updatePercepts();
    }

    @Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" fazendo: "+ action);
        try {
            
			if (action.equals(caminharCivil)) {
                model.caminharCivil();
			} else if (action.getFunctor().equals("perseguirIncendiario")) {
                int x = (int)((NumberTerm)action.getTerm(0)).solve();
                int y = (int)((NumberTerm)action.getTerm(1)).solve();
                model.perseguirIncendiario(x,y);
			} else if (action.getFunctor().equals("apagarIncendio")) {
                int x = (int)((NumberTerm)action.getTerm(0)).solve();
                int y = (int)((NumberTerm)action.getTerm(1)).solve();
                model.apagarIncendio(x,y);
				
			}else if (action.equals(caminharIncendiario)) {
                model.caminharIncendiario();
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

    /** cria a percepção dos agentes com base no Model */ 
    void updatePercepts() {
        clearPercepts();
		
		//Recebendo a localização dos Agentes
        Location bombeiroLoc = model.getAgPos(0);
        Location policialLoc = model.getAgPos(1);
		Location incendiarioLoc = model.getAgPos(2);
		Location civilLoc = model.getAgPos(3);

        Literal posicaoBombeiro = Literal.parseLiteral("pos(bombeiro," + bombeiroLoc.x + "," + bombeiroLoc.y + ")");
        Literal posicaoPolicial = Literal.parseLiteral("pos(policial," + policialLoc.x + "," + policialLoc.y + ")");
		Literal posicaoIncendiario = Literal.parseLiteral("pos(incendiario," + incendiarioLoc.x + "," + incendiarioLoc.y + ")");
		Literal posicaoCivil = Literal.parseLiteral("pos(civil," + civilLoc.x + "," + civilLoc.y + ")");
		
		//Adicionando percepções da localização dos Agentes
        addPercept(posicaoBombeiro);
        addPercept(posicaoPolicial);
		addPercept(posicaoIncendiario);
		addPercept(posicaoCivil);
		
		//Adicionando percepções de Agentes percebendo Fogo		
        if (model.hasObject(GARB, civilLoc)) {
            addPercept(civilFogo);
        }
        if (model.hasObject(GARB, policialLoc)) {
            addPercept(policialFogo);
        }
		if (model.hasObject(GARB, bombeiroLoc)) {
            addPercept(bombeiroFogo);
        }
		
		//Adicionando percepções de Agentes percebendo Incendiário
		if((civilLoc.x == incendiarioLoc.x) && (civilLoc.y == incendiarioLoc.y)){
			addPercept(civilIncendiario);
		}
		if((bombeiroLoc.x == incendiarioLoc.x) && (bombeiroLoc.y == incendiarioLoc.y)){
			addPercept(bombeiroIncendiario);
		}
		if ((policialLoc.x == incendiarioLoc.x) && (policialLoc.y == incendiarioLoc.y)){
			addPercept(policialIncendiario);
		}
				
    }

    class Model extends GridWorldModel {

        public static final int MErr = 2; // max error in pick garb
        int nerr; // number of tries of pick garb
        boolean r1HasGarb = false; // se r1 está carregando lixo ou não

        Random random = new Random(System.currentTimeMillis());

        private Model() {
            super(GSize, GSize, 4);

            //Localização inicial dos Agentes
            try {
				Location bombeiroLoc = new Location(14, 14);
				setAgPos(0, bombeiroLoc);
								
                Location policialLoc = new Location(13, 14);
                setAgPos(1, policialLoc);
				
				Location incendiarioLoc = new Location(6, 1);
                setAgPos(2, incendiarioLoc);
				
				Location civilLoc = new Location(1, 1);
				setAgPos(3, civilLoc);
				
            } catch (Exception e) {
                e.printStackTrace();
            }

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
		
		void atearFogo()throws Exception{
			Random n = new Random();
			if(n.nextInt(15) == 5){
				add(GARB, getAgPos(2));
			}		
		}
		
		void apagarFogo()throws Exception{
			remove(GARB, getAgPos(0));
		}

		void prenderIncendiario()throws Exception{
			JOptionPane.showMessageDialog(null, "INCENDIÁRIO CAPTURADO!!");
			System.exit(0);
		}
			
		void perseguirIncendiario(int x, int y) throws Exception {
            Location policial = getAgPos(1);
            if (policial.x < x)
                policial.x++;
            else if (policial.x > x)
                policial.x--;
            if (policial.y < y)
                policial.y++;
            else if (policial.y > y)
                policial.y--;
            setAgPos(1, policial);
            setAgPos(1, getAgPos(1)); // just to draw it in the view
        }

		void apagarIncendio(int x, int y) throws Exception {
            Location bombeiro = getAgPos(0);
            if (bombeiro.x < x)
                bombeiro.x++;
            else if (bombeiro.x > x)
                bombeiro.x--;
            if (bombeiro.y < y)
                bombeiro.y++;
            else if (bombeiro.y > y)
                bombeiro.y--;
            setAgPos(0, bombeiro);
            setAgPos(0, getAgPos(0)); // just to draw it in the view
        }		
	
    }

    class View extends GridWorldView {

        public View(Model model) {
            super(model, "Simulação Multiagentes", 1000);
            defaultFont = new Font("Serif", Font.PLAIN, 10); //alterar fonte padrão
            setVisible(true);
            repaint(); 
        }
        
		@Override
        public void draw(Graphics g, int x, int y, int object) {
            switch (object) {
            case MarsEnv.GARB:
                drawFogo(g, x, y);
                break;
            }
        }

        @Override
        public void drawAgent(Graphics g, int x, int y, Color c, int id) {
			String label = "label"; 
			g.setColor(Color.white);
			
			if (id == 0) {
				c = Color.RED;
				label = "BOMBEIRO";
				g.setColor(Color.white);
				super.drawAgent(g, x, y, c, -1);
				super.drawString(g, x, y, defaultFont, label);
				repaint();
            }
			
			if (id == 1) {
				c = Color.blue;
				label = "POLICIAL";
				g.setColor(Color.white);
				super.drawAgent(g, x, y, c, -1);
				super.drawString(g, x, y, defaultFont, label);
				repaint();
            }
			
			if (id == 2) {
				c = Color.DARK_GRAY;
				label = "INCENDIÁRIO";
				g.setColor(Color.white);
				super.drawAgent(g, x, y, c, -1);
				super.drawString(g, x, y, defaultFont, label);
				repaint();
			}
			
			if (id == 3) {
				c = Color.CYAN;
				label = "CIVIL";
				g.setColor(Color.white);
				super.drawAgent(g, x, y, c, -1);
				super.drawString(g, x, y, defaultFont, label);
				repaint();
            }         
			super.drawAgent(g, x, y, c, -1);
            g.setColor(Color.white);
            super.drawString(g, x, y, defaultFont, label);
            repaint();
		}
		
        public void drawFogo(Graphics g, int x, int y) {
            super.drawObstacle(g, x, y);
			Font fonteFogo = new Font("Serif", Font.BOLD, 15); //alterar fonte padrão
            g.setColor(Color.orange);
			g.setFont(fonteFogo);
            drawString(g, x, y, fonteFogo, "FOGO");
        }

    }
	

}
