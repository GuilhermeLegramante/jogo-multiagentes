package jogo;

import java.util.Random;

import javax.swing.JOptionPane;

import jplay.Keyboard;
import jplay.Scene;
import jplay.URL;
import jplay.Window;

public class Cenario {

	private Window janela;
	private Scene cena;
	private Keyboard teclado;
	private Civil civil;
	private Incendiario incendiario;
	private Bombeiro bombeiro;
	private Fogo fogo1, fogo2, fogo3, fogo4, fogo5, fogo6, fogo7, fogo8, fogo9, fogo10;
	private DirecaoAleatoria direcaoAleatoriaIncendiario;
	private DirecaoAleatoria direcaoAleatoriaBombeiro;
	private int direcaoCivil = 39;

	public Cenario(Window window) {

		janela = window;
		cena = new Scene();
		cena.loadFromFile(URL.scenario("Cenario.scn"));
		civil = new Civil(1, 1);
		direcaoAleatoriaIncendiario = new DirecaoAleatoria(100, 50);
		direcaoAleatoriaBombeiro = new DirecaoAleatoria(1, 500);
		incendiario = new Incendiario(200, 100);
		bombeiro = new Bombeiro(500, 50);
		fogo1 = new Fogo(100, 1);
		fogo2 = new Fogo(350, 100);
		fogo3 = new Fogo(200, 5);
		fogo4 = new Fogo(100, 700);
		fogo5 = new Fogo(600, 300);
		fogo6 = new Fogo(100, 250);
		fogo7 = new Fogo(750, 400);
		fogo8 = new Fogo(700, 700);
		fogo9 = new Fogo(450, 500);
		fogo10 = new Fogo(500, 450);
		teclado = janela.getKeyboard();

		run();

	}

	private void run() {
		boolean rodando = true;
		while (rodando) {

			Random n1 = new Random();
			int direcaoIncendiario = n1.nextInt(4) + 37;
			
			Random n2 = new Random();
			int direcaoBombeiro = n2.nextInt(4) + 37;
			

			for (int i = 0; i < 4; i++) {
				direcaoAleatoriaIncendiario.andar(janela, direcaoIncendiario);
			}
			
			for (int i = 0; i < 4; i++) {
				direcaoAleatoriaBombeiro.andar(janela, direcaoBombeiro);
			}

			cena.moveScene(direcaoAleatoriaIncendiario);
			civil.draw();
			incendiario.draw();
			incendiario.andarAleatoriamente(direcaoAleatoriaIncendiario.getX(), direcaoAleatoriaIncendiario.getY());

			if (incendiario.collided(fogo1)) {
				incendiario.atearFogo(cena, incendiario, fogo1);
			}
			if (incendiario.collided(fogo2)) {
				incendiario.atearFogo(cena, incendiario, fogo2);
			}
			if (incendiario.collided(fogo3)) {
				incendiario.atearFogo(cena, incendiario, fogo3);
			}
			if (incendiario.collided(fogo4)) {
				incendiario.atearFogo(cena, incendiario, fogo4);
			}
			if (incendiario.collided(fogo5)) {
				incendiario.atearFogo(cena, incendiario, fogo5);
			}
			if (incendiario.collided(fogo6)) {
				incendiario.atearFogo(cena, incendiario, fogo6);
			}
			if (incendiario.collided(fogo7)) {
				incendiario.atearFogo(cena, incendiario, fogo7);
			}
			if (incendiario.collided(fogo8)) {
				incendiario.atearFogo(cena, incendiario, fogo8);
			}
			if (incendiario.collided(fogo9)) {
				incendiario.atearFogo(cena, incendiario, fogo9);
			}
			if (incendiario.collided(fogo10)) {
				incendiario.atearFogo(cena, incendiario, fogo10);
			}

			civil.andar(janela, direcaoCivil);
			civil.mover();

			if (civil.getX() > 740) {
				civil.moveTo(civil.getX(), civil.getY() + 10, 100);
				direcaoCivil = 37;
			}

			if (civil.getX() < 10) {
				direcaoCivil = 39;
			}

			if ((civil.getY() > 500 && civil.getX() < 10)) {
				civil.moveTo(civil.getX(), civil.getY() - 10, 500);
				direcaoCivil = 37;
			}
			
			
			bombeiro.draw();
			bombeiro.andarAleatoriamente(direcaoAleatoriaBombeiro.getX(), direcaoAleatoriaBombeiro.getY());

			if (bombeiro.collided(fogo1)) {
				bombeiro.apagarFogo(cena, bombeiro, fogo1);
			}
			if (bombeiro.collided(fogo2)) {
				bombeiro.apagarFogo(cena, bombeiro, fogo2);
			}
			if (bombeiro.collided(fogo3)) {
				bombeiro.apagarFogo(cena, bombeiro, fogo3);
			}
			if (bombeiro.collided(fogo4)) {
				bombeiro.apagarFogo(cena, bombeiro, fogo4);
			}
			if (bombeiro.collided(fogo5)) {
				bombeiro.apagarFogo(cena, bombeiro, fogo5);
			}
			if (bombeiro.collided(fogo6)) {
				bombeiro.apagarFogo(cena, bombeiro, fogo6);
			}
			if (bombeiro.collided(fogo7)) {
				bombeiro.apagarFogo(cena, bombeiro, fogo7);
			}
			if (bombeiro.collided(fogo8)) {
				bombeiro.apagarFogo(cena, bombeiro, fogo8);
			}
			if (bombeiro.collided(fogo9)) {
				bombeiro.apagarFogo(cena, bombeiro, fogo9);
			}
			if (bombeiro.collided(fogo10)) {
				bombeiro.apagarFogo(cena, bombeiro, fogo10);
			}

			janela.update();

			if (teclado.keyDown(Keyboard.ESCAPE_KEY)) {
				System.exit(0);
			}
		}
	}

}
