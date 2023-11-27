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

	private boolean winFlag;
	
	private int tileW;
	private int tileH;

	private int currentPlayer;
	private float time;

	private static final int HAND_X = 400;
	private static final int HAND_Y = 30;

	private static final int WINDOW_X = 1280;
	private static final int WINDOW_Y = 720;

	static SpriteBatch batch;
	static TileTextures tileTextures;

	Table table;
	Player[] players;

	WaitingHandChecker waitingHandChecker;
	boolean[] winningTileList;

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

		waitingHandChecker = new WaitingHandChecker();
		winningTileList = waitingHandChecker.getWinningTileList();

		currentPlayer = Table.PLAYER;

		inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0.33f, 0.33f, 1);

		batch.begin();
		for (int i = 0; i < Table.PLAYERS; i++) {
			handRenderer.draw(players[i].getHand(), players[i].getId(), players[i].getHasTile(), players[i].getTile());
			discardRenderer.draw(table.getDiscard(i), players[i].getId());
		}
		batch.end();

		if (time > 0.3f && !winFlag) {
			if (table.canGetWallTop() && !players[currentPlayer].getHasTile()) {
				drawTile();
			} else if (currentPlayer != Table.PLAYER && players[currentPlayer].getHasTile()) {
				discardTile();
				
				Tile tile = table.getDiscard(currentPlayer).get(table.getDiscard(currentPlayer).size() - 1);
				
				for (int i = currentPlayer - 1; i > Table.PLAYER; i--) {
					
				}
				
				if (winningTileList[(tile.getKind() - 1) * 9 + (tile.getValue() - 1)]) {
					System.out.println("WIN");
					winFlag = true;
				}
				currentPlayer = (currentPlayer + 1) % Table.PLAYERS;
				
			} else if (isClicked && currentPlayer == Table.PLAYER) {
				if (winningTileList[(players[0].getTile().getKind() - 1) * 9 + (players[0].getTile().getValue() - 1)]) {
					System.out.println("WIN");
					winFlag = true;
				} else {
					waitingHandChecker.isWaitingHand(players[0].getHand(), players[0].getTile());
					playerTurn();
					if (waitingHandChecker.isWaitingHand(players[0].getHand())) {
						waitingHandChecker.parseWinningTileList();
					}
				}
			} else if (!table.canGetWallTop()) {
				System.out.println("drawn game");
			}
			time = 0;
			isClicked = false;
		}

		time += Gdx.graphics.getDeltaTime();

	}

	private void drawTile() {
		players[currentPlayer].setTile(table.getWallTop());
	}

	private void discardTile() {
		table.addTileToDiscard(players[currentPlayer].discard(players[currentPlayer].getHand().size()),
				players[currentPlayer].getId());
	}

	private void playerTurn() {
		if (players[currentPlayer].getHasTile()) {
			int index = (int) Math.floor((mouseX - HAND_X) / tileW);
			if (mouseX - HAND_X >= 0 && index < players[currentPlayer].getHand().size() && mouseY <= WINDOW_Y - HAND_Y
					&& mouseY >= WINDOW_Y - HAND_Y - tileH) {
				if (mouseY <= WINDOW_Y - HAND_Y && mouseY >= WINDOW_Y - HAND_Y - tileH) {
					table.addTileToDiscard(players[currentPlayer].discard(index), players[currentPlayer].getId());
				}
			} else if (index == players[currentPlayer].getHand().size() + 1) {
				table.addTileToDiscard(players[currentPlayer].discard(players[currentPlayer].getHand().size()),
						players[currentPlayer].getId());
			} else {
				return;
			}
			players[currentPlayer].sortHand();
			currentPlayer = Table.COM1;
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
