package jogo;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.URL;
import jplay.Window;

public class Main {

	public static void main(String[] args) {
		
		Window janela = new Window(800, 600);
		GameImage telaInicio = new GameImage(URL.sprite("menu.png"));
		Keyboard teclado = janela.getKeyboard();
		boolean rodando = true;
		
		while(rodando) {
			
			telaInicio.draw();
			janela.update();
			
			new Cenario(janela);
			
			if(teclado.keyDown(Keyboard.ENTER_KEY)) {
				new Cenario(janela);
			}
			if(teclado.keyDown(Keyboard.ESCAPE_KEY)) {
				System.exit(0);
			}
		}
	}
}
