package tk0821.mahjong;

import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

public class Mahjong extends ApplicationAdapter {
	
	TileTextures tileTextures;
	Table table;
	List<Tile> hand;

	@Override
	public void create() {
	
		tileTextures = new TileTextures();
		
		table = new Table();

		table.generateWall();
		table.printWall();

		hand = table.generateHand();

		Collections.sort(hand);

		System.out.println(hand);
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		
		
		
	}

	@Override
	public void dispose() {

	}
}
