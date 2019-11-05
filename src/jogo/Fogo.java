package jogo;

import jplay.Sprite;
import jplay.URL;

public class Fogo extends Sprite {
	


	public Fogo(int x, int y) {
		super(URL.sprite("fogo.png"), 1);
		this.x = x;
		this.y = y;
		this.setTotalDuration(2000);
	}


}