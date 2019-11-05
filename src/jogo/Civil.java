package jogo;

import jplay.Sprite;
import jplay.URL;
import jplay.Window;

public class Civil extends Sprite {
	
	private double velocidade = 3;
	private int direcao = 3;
	private boolean movendo = false;

	public Civil(int x, int y) {
		super(URL.sprite("zumbi.png"), 16);
		this.x = x;
		this.y = y;
		this.setTotalDuration(2000);
	}

	public void andar(Window janela, int direcao) {	
		// Esquerda
		if (direcao == 37) {
			if (this.x > 0) {
				
				this.x -= velocidade;
			}
			if (direcao != 1) {
				direcao = 1;
			}
			movendo = true;

		// Direita
		} else if (direcao == 39) {
			if (this.x < janela.getWidth() - 60) {
				setSequence(9, 13);
				this.x += velocidade;
			}
			if (direcao != 2) {
				direcao = 2;
			}
			movendo = true;

			// Cima
		} else if (direcao == 38) {
			if (this.y > 0) {
				this.y -= velocidade;
			}
			if (direcao != 4) {
				direcao = 4;
			}
			movendo = true;

			// Baixo
		} else if (direcao == 40) {
			if (this.y < janela.getHeight() - 60) {
				this.y += velocidade;
			}
			if (direcao != 5) {
				direcao = 5;
			}
			movendo = true;
		}

		if (movendo) {
			update();
			movendo = false;
		}
	}
	
	public void mover() {

		System.out.println(this.getX() + " Y: " + this.getY());
		
	}
	
	public void esperar() {
		movendo = false;
	}

}
