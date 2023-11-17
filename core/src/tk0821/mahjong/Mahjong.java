package tk0821.mahjong;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Mahjong extends ApplicationAdapter {

	MyInputProcessor inputProcessor;

	public static boolean isClicked = false;
	public static int mouseX;
	public static int mouseY;

	private int tileW;
	private int tileH;

	private int discardTileW;
	private int discardTileH;

	private static final int HAND_X = 270;
	private static final int HAND_Y = 30;
	private static final int WINDOW_X = 1280;
	private static final int WINDOW_Y = 720;

	private static final int DISCARD_PLAYER_X = 400;
	private static final int DISCARD_PLAYER_Y = 300;

	SpriteBatch batch;
	TileTextures tileTextures;

	Table table;
	Player player;
	Player com1;
	Player com2;
	Player com3;

	@Override
	public void create() {
		batch = new SpriteBatch();

		tileTextures = new TileTextures();

		tileW = tileTextures.getTileTexture(1, 1, 1).getWidth();
		tileH = tileTextures.getTileTexture(1, 1, 1).getHeight();
		discardTileW = tileTextures.getTileTexture(1, 1, Tile.DISPLAY).getWidth();
		discardTileH = tileTextures.getTileTexture(1, 1, Tile.DISPLAY).getHeight();

		table = new Table();
		table.generateWall();
		table.generateDiscards();

		player = new Player(Table.PLAYER);
		player.setHand(table.dealHand());
		player.sortHand();
		
		com1 = new Player(Table.COM1);
		com1.setHand(table.dealHand());
		
		com2 = new Player(Table.COM2);
		com2.setHand(table.dealHand());
		
		com3 = new Player(Table.COM3);
		com3.setHand(table.dealHand());

		inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0.33f, 0.33f, 1);

		batch.begin();
		for (int i = 0; i < Table.HAND; i++) {
			Texture texture = tileTextures.getTileTexture(player.getHand().get(i).getKind(),
					player.getHand().get(i).getValue(), Tile.HAND);
			batch.draw(texture, HAND_X + (i * tileW), HAND_Y);
		}
		
		for (int i = 0; i < Table.HAND; i++) {
			Texture texture = tileTextures.getTileTexture(com1.getHand().get(i).getKind(),
					player.getHand().get(i).getValue(), Tile.HAND);
			batch.draw(texture, HAND_X + (i * tileW), HAND_Y);
		}		
		
		
		if (player.getHasTile()) {
			Texture texture = tileTextures.getTileTexture(player.getTile().getKind(), player.getTile().getValue(),
					Tile.HAND);
			batch.draw(texture, HAND_X + ((player.getHand().size() + 1) * tileW), HAND_Y);
		}

		// TODO: loop
		List<Tile> discard = table.getDiscard(player.getId());
		for (int i = 0; i < discard.size(); i++) {
			int column = i < 18 ? i / 6 : 2;
			int row = i < 18 ? i % 6 : i - 12;
			Texture texture = tileTextures.getTileTexture(discard.get(i).getKind(), discard.get(i).getValue(),
					Tile.DISPLAY);
			batch.draw(texture, DISCARD_PLAYER_X + (row * discardTileW), DISCARD_PLAYER_Y - (column * discardTileH));
		}

		batch.end();

		if (isClicked) {
			if (player.getHasTile()) {
				int index = (int) Math.floor((mouseX - HAND_X) / tileW);
				if (mouseX - HAND_X >= 0 && index < player.getHand().size()) {
					if (mouseY <= WINDOW_Y - HAND_Y && mouseY >= WINDOW_Y - HAND_Y - tileH) {
						table.addTileToDiscard(player.discard(index), player.getId());
					}
				} else if (index == player.getHand().size() + 1) {
					table.addTileToDiscard(player.discard(player.getHand().size()), player.getId());
				}
				player.sortHand();
			} else if (table.canGetWallTop()) {
				player.setTile(table.getWallTop());
			}
		}
		isClicked = false;
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
