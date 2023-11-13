package tk0821.mahjong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

public class Mahjong extends ApplicationAdapter {

	private Tiles tiles;
	private Tiles hand1;
	private Tiles hand2;
	private Tiles hand3;
	private Tiles hand4;
	
	@Override
	public void create() {
		tiles = new Tiles();
		tiles.generateWall();
		//tiles.printWall();
		
		hand1 = tiles.createHand();
		hand1.printTilesName();
		hand2 = tiles.createHand();
		hand2.printTilesName();
		hand3 = tiles.createHand();
		hand3.printTilesName();
		hand4 = tiles.createHand();
		hand4.printTilesName();
		//tiles.printTiles();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
	}

	@Override
	public void dispose() {

	}
}
