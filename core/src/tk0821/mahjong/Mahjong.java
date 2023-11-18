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

	private int backTileW;
	private int backTileH;

	private static final int HAND_X = 400;
	private static final int HAND_Y = 30;
	private static final int WINDOW_X = 1280;
	private static final int WINDOW_Y = 720;

	private static final int DISCARD_PLAYER_X = 500;
	private static final int DISCARD_PLAYER_Y = 230;

	static SpriteBatch batch;
	static TileTextures tileTextures;

	Table table;
	Player player;
	Player com1;
	Player com2;
	Player com3;

	HandRenderer handRenderer;
	
	@Override
	public void create() {
		batch = new SpriteBatch();

		tileTextures = new TileTextures();

		tileW = tileTextures.getTileTexture(1, 1, 1).getWidth();
		tileH = tileTextures.getTileTexture(1, 1, 1).getHeight();
		discardTileW = tileTextures.getTileTexture(1, 1, Tile.DISPLAY).getWidth();
		discardTileH = tileTextures.getTileTexture(1, 1, Tile.DISPLAY).getHeight();
		backTileW = tileTextures.getBackTileTexture(Tile.DISPLAY).getWidth();
		backTileH = tileTextures.getBackTileTexture(Tile.DISPLAY).getHeight();

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

		handRenderer = new HandRenderer();
		
		inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0.33f, 0.33f, 1);

		batch.begin();

		handRenderer.draw(player.getHand(), player.getId());
		handRenderer.draw(com1.getHand(), com1.getId());
		handRenderer.draw(com2.getHand(), com2.getId());
		handRenderer.draw(com3.getHand(), com3.getId());


		if (player.getHasTile()) {
			Texture texture = tileTextures.getTileTexture(player.getTile().getKind(), player.getTile().getValue(),
					Tile.HAND);
			batch.draw(texture, HAND_X + ((player.getHand().size() + 1) * tileW), HAND_Y);
		}

		// TODO: loop
		List<Tile> discard = table.getDiscard(0);

		int x = 0;
		int y = 0;
		int direction = 0;

		x = DISCARD_PLAYER_X;
		y = DISCARD_PLAYER_Y;
		direction = Tile.DISPLAY;

		for (int j = 0; j < discard.size(); j++) {
			int column = j < 18 ? j / 6 : 2;
			int row = j < 18 ? j % 6 : j - 12;
			Texture texture = tileTextures.getTileTexture(discard.get(j).getKind(), discard.get(j).getValue(),
					direction);
			batch.draw(texture, x + (row * discardTileW), y - (column * discardTileH));
		}

		batch.end();

		if (isClicked)

		{
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
