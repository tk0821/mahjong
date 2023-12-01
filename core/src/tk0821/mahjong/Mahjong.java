package tk0821.mahjong;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Mahjong extends ApplicationAdapter {

	private OrthographicCamera camera;
	Vector3 v;

	MyInputProcessor inputProcessor;

	public static boolean isClicked = false;
	public static int mouseX;
	public static int mouseY;

	public static boolean winFlag;
	private boolean furitenFlag;

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
	boolean[] winningTileArray;

	FreeTypeFontGenerator fontGeneraror;
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	HandRenderer handRenderer;
	DiscardRenderer discardRenderer;

	List<String> yakuList;
	int han;

	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);

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

		shapeRenderer = new ShapeRenderer();
		handRenderer = new HandRenderer();
		discardRenderer = new DiscardRenderer();

		FreeTypeFontGenerator fontGeneraror = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/rounded-mplus-2c-regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;

		font = fontGeneraror.generateFont(parameter);

		fontGeneraror.dispose();

		waitingHandChecker = new WaitingHandChecker();
		winningTileArray = waitingHandChecker.getWinningTileArray();

		currentPlayer = Table.PLAYER;

		inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		v = new Vector3();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0.33f, 0.33f, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		for (int i = 0; i < Table.PLAYERS; i++) {
			handRenderer.draw(players[i].getHand(), players[i].getId(), players[i].getHasTile(), players[i].getTile());
			discardRenderer.draw(table.getDiscard(i), players[i].getId());
		}

		batch.end();

		if (winFlag) {
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(Color.BROWN);
			shapeRenderer.rect(600, 130, 160, 50);
			shapeRenderer.rect(770, 130, 160, 50);
			shapeRenderer.end();
			batch.begin();
			if (currentPlayer == Table.PLAYER) {
				font.draw(batch, new GlyphLayout(font, "TSUMO"), 610, 170);
				font.draw(batch, new GlyphLayout(font, "CANCEL"), 780, 170);
			} else {
				font.draw(batch, new GlyphLayout(font, "RON"), 640, 170);
				font.draw(batch, new GlyphLayout(font, "CANCEL"), 780, 170);
			}
			batch.end();

			if (isClicked) {
				camera.unproject(v.set(mouseX, mouseY, 0));
				if (v.x >= 600 && v.x <= 760 && v.y >= 130 && v.y <= 180) {
					if (currentPlayer == Table.PLAYER) {
						System.out.println("TSUMO!");
					} else {
						System.out.println("RON");
					}

					System.out.println("*******************");
					for (var s : yakuList)
						System.out.println(s);
					System.out.println("*******************");
					System.out.println(han + "han");
					System.out.println("xxxx points");

					isClicked = false;
				} else if (v.x >= 770 && v.x <= 930 && v.y >= 130 && v.y <= 180) {
					System.out.println("CANCEL");
					winFlag = false;
					isClicked = false;
				}
			}
		}

		if (time > 0.3f && !winFlag) {
			if (table.canGetWallTop() && !players[currentPlayer].getHasTile()) {
				drawTile();
				if (currentPlayer == Table.PLAYER) {
					furitenFlag = false;
					if (checkWinning(players[currentPlayer].getHand(), players[currentPlayer].getTile())) {
						System.out.println("check winning true");
					}
				}
			} else if (currentPlayer != Table.PLAYER && players[currentPlayer].getHasTile()) {
				discardTile();
				if (!furitenFlag) {
					Tile tile = table.getDiscard(currentPlayer).get(table.getDiscard(currentPlayer).size() - 1);
					if (checkWinning(players[currentPlayer].getHand(), tile)) {
						System.out.println("check winning true");
					}
				}
				if (!winFlag) {
					currentPlayer = (currentPlayer + 1) % Table.PLAYERS;
				}
			} else if (isClicked && currentPlayer == Table.PLAYER) {
				if (!winFlag) {
					waitingHandChecker.isWaitingHand(players[0].getHand(), players[0].getTile());
					playerTurn();
				}
			} else if (!table.canGetWallTop()) {
				System.out.println("drawn game");
			}
			time = 0;
			isClicked = false;
		}

		time += Gdx.graphics.getDeltaTime();

	}

	private void setCompleteHandFlags(CompleteHand completeHand) {
		completeHand.setTsumo(currentPlayer == Table.PLAYER);
		completeHand.setCalling(false); //未実装
		completeHand.setRiichi(false); //未実装
		completeHand.setDoubleRiichi(false); //未実装
		completeHand.setIppatsu(false); //未実装
		completeHand.setKan(false); // 未実装
		completeHand.setLastTile(!table.canGetWallTop());
		completeHand.setPrevalentWindIndex(27); // 東
		completeHand.setSeatWindIndex(27); // 東
	}

	private void drawTile() {
		players[currentPlayer].setTile(table.getWallTop());
	}

	private void discardTile() {
		table.addTileToDiscard(players[currentPlayer].discard(players[currentPlayer].getHand().size()),
				players[currentPlayer].getId());
	}

	private boolean checkWinning(List<Tile> hand, Tile tile) {
		if (winningTileArray[parseTileToArrayIndex(tile)]) {
			CompleteHand completeHand = new CompleteHand(hand, tile);
			setCompleteHandFlags(completeHand);
			WinningChecker winningChecker = new WinningChecker(completeHand);
			yakuList = winningChecker.getYakuList();
			han = winningChecker.getMaxHan();
			winFlag = winningChecker.isWinning();
			return true;
		}
		return false;
	}

	private boolean checkDiscard() {
		if (waitingHandChecker.isWaitingHand(players[0].getHand())) {
			waitingHandChecker.parseWinningTileArray();
			for (int i = 0; i < table.getDiscard(currentPlayer).size(); i++) {
				Tile tile = table.getDiscard(currentPlayer).get(i);
				if (winningTileArray[parseTileToArrayIndex(tile)]) {
					System.out.println(parseTileToArrayIndex(tile));
					System.out.println(winningTileArray[parseTileToArrayIndex(tile)]);
					furitenFlag = true;
					System.out.println(furitenFlag);
					return true;
				}
			}
		}
		return false;
	}

	private int parseTileToArrayIndex(Tile tile) {
		return (tile.getKind() - 1) * 9 + (tile.getValue() - 1);
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
			checkDiscard();
			currentPlayer = Table.COM1;
		}
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
	}
}
