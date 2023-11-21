package tk0821.mahjong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Mahjong extends ApplicationAdapter {

	MyInputProcessor inputProcessor;

	public static boolean isClicked = false;
	public static int mouseX;
	public static int mouseY;

	private int tileW;
	private int tileH;

	private static final int HAND_X = 400;
	private static final int HAND_Y = 30;
	
	private static final int WINDOW_X = 1280;
	private static final int WINDOW_Y = 720;



	static SpriteBatch batch;
	static TileTextures tileTextures;

	Table table;
	Player[] players;

	HandRenderer handRenderer;
	DiscardRenderer discardRenderer;
	
	@Override
	public void create() {
		batch = new SpriteBatch();

		tileTextures = new TileTextures();

		tileW = tileTextures.getTileTexture(1, 1, 1).getWidth();
		tileH = tileTextures.getTileTexture(1, 1, 1).getHeight();

		table = new Table();
		table.generateWall();
		table.generateDiscards();

		players = new Player[Table.PLAYERS];
		for (int i = 0; i < Table.PLAYERS; i++) {
			players[i] = new Player(i);
			players[i].setHand(table.dealHand());
			players[i].sortHand();		
		}

		handRenderer = new HandRenderer();
		discardRenderer = new DiscardRenderer();
		
		inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0.33f, 0.33f, 1);

		batch.begin();

		for (int i = 0; i < Table.PLAYERS; i++) {
			handRenderer.draw(players[i].getHand(),players[i].getId(), players[i].getHasTile(), players[i].getTile());
			discardRenderer.draw(table.getDiscard(i), players[i].getId());
		}
		
		batch.end();

		if (isClicked)

		{
			if (players[Table.PLAYER].getHasTile()) {
				int index = (int) Math.floor((mouseX - HAND_X) / tileW);
				if (mouseX - HAND_X >= 0 && index < players[Table.PLAYER].getHand().size()) {
					if (mouseY <= WINDOW_Y - HAND_Y && mouseY >= WINDOW_Y - HAND_Y - tileH) {
						table.addTileToDiscard(players[Table.PLAYER].discard(index), players[Table.PLAYER].getId());
					}
				} else if (index == players[Table.PLAYER].getHand().size() + 1) {
					table.addTileToDiscard(players[Table.PLAYER].discard(players[Table.PLAYER].getHand().size()), players[Table.PLAYER].getId());
				}
				players[Table.PLAYER].sortHand();

				table.addTileToDiscard(players[Table.COM1].discard(players[Table.COM1].getHand().size()), players[Table.COM1].getId());
				table.addTileToDiscard(players[Table.COM2].discard(players[Table.COM2].getHand().size()), players[Table.COM2].getId());
				table.addTileToDiscard(players[Table.COM3].discard(players[Table.COM3].getHand().size()), players[Table.COM3].getId());
				
			} else if (table.canGetWallTop()) {
				players[Table.PLAYER].setTile(table.getWallTop());
				players[Table.COM1].setTile(table.getWallTop());
				players[Table.COM2].setTile(table.getWallTop());
				players[Table.COM3].setTile(table.getWallTop());
			}
		}
		isClicked = false;
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
