package tk0821.mahjong;

import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Mahjong extends ApplicationAdapter {
	
	SpriteBatch batch;
	TileTextures tileTextures;
	
	Table table;
	List<Tile> hand;

	@Override
	public void create() {
		batch = new SpriteBatch();
		
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
		
		batch.begin();
		
		for (int i = 0; i < Table.HAND; i++) {
			batch.draw(tileTextures.getTileTexture(hand.get(i).getKind(), hand.get(i).getValue(), Tile.HAND), 400 + (i * 33), 100);
		}
		
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
