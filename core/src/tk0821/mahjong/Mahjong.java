package tk0821.mahjong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

public class Mahjong extends ApplicationAdapter {

	private Tiles tiles;
	private Tiles hands1;
	private Tiles hands2;
	private Tiles hands3;
	private Tiles hands4;
	
	@Override
	public void create() {
		tiles = new Tiles();
		tiles.generateWall();
		tiles.printWall();
		
		hands1 = tiles.createHands();
		hands1.printTiles();
		hands2 = tiles.createHands();
		hands2.printTiles();
		hands3 = tiles.createHands();
		hands3.printTiles();
		hands4 = tiles.createHands();
		hands4.printTiles();
		tiles.printTiles();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
	}

	@Override
	public void dispose() {

	}
}
