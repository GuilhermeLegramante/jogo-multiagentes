package jogo;

import jplay.Scene;
import jplay.Sprite;
import jplay.URL;

public class Incendiario extends Sprite {
	
	private double velocidade = 10;
	private int direcao = 2;
	private boolean movendo = false;

	public Incendiario(int x, int y) {
		super(URL.sprite("civil.png"), 20);
		this.x = x;
		this.y = y;
		this.setTotalDuration(2000);
	}
	
	public void andarAleatoriamente(double x, double y) {	
		
		if(this.x > x && this.y <= y + 50 && this.y >= y -50) {
			moveTo(x, y, velocidade);
			if(direcao != 1) {
				direcao = 1;
			}
			movendo = true;
		}
		
		else if (this.x < x && this.y <= y + 50 && this.y >= -50) {
			moveTo(x, y, velocidade);
			if(direcao != 2) {
				direcao = 2;
			}
			movendo = true;
		}
		
		else if (this.y > y) {
			moveTo(x, y, velocidade);
			if(direcao != 4) {
				direcao = 4;
			}
			movendo = true;
		}
		
		else if (this.y < y) {
			moveTo(x, y, velocidade);
			if(direcao != 5) {
				direcao = 5;
			}
			movendo = true;
		}
		
		if(movendo) {
			update();
			movendo = false;
		}
	}

	public void atearFogo(Scene cena, Incendiario incendiario, Fogo fogo) {
		if(incendiario.collided(fogo)) {
			cena.addOverlay(fogo);
		}
	}
	
	

}