package tk0821.mahjong;

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

	private static final int HAND_X = 300;
	private static final int HAND_Y = 100;
	private static final int WINDOW_X = 1280;
	private static final int WINDOW_Y = 720;
	
	SpriteBatch batch;
	TileTextures tileTextures;

	Table table;
	Player player;

	@Override
	public void create() {
		batch = new SpriteBatch();

		tileTextures = new TileTextures();
		
		tileW = tileTextures.getTileTexture(1, 1, 1).getWidth();
		tileH = tileTextures.getTileTexture(1, 1, 1).getHeight();
		
		table = new Table();
		table.generateWall();

		player = new Player();
		player.setHand(table.generateHand());
		player.sortHand();

		inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);

		batch.begin();
		for (int i = 0; i < Table.HAND; i++) {
			Texture texture = tileTextures.getTileTexture(player.getHand().get(i).getKind(),player.getHand().get(i).getValue(), Tile.HAND);
			batch.draw(texture, HAND_X + (i * tileW), HAND_Y);
		}
		if (player.getHasTile()) {
			Texture texture = tileTextures.getTileTexture(player.getTile().getKind(), player.getTile().getValue(), Tile.HAND);
			batch.draw(texture, HAND_X + ((player.getHand().size() + 1) * tileW), HAND_Y);
		}
		batch.end();

		if (isClicked) {
			if (player.getHasTile()) {
				int index = (int) Math.floor((mouseX - HAND_X) / tileW);
				if (mouseX - HAND_X >= 0 && index < player.getHand().size()) {
					if (mouseY <= WINDOW_Y - HAND_Y && mouseY >= WINDOW_Y - HAND_Y - tileH) {
						player.discard(index);
					}
				} else if (index == player.getHand().size() + 1) {
					player.discard(player.getHand().size());
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
