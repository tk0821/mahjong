package tk0821.mahjong;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public class HandRenderer {

	private int handX;
	private int handY;
	private int direction;

	// coordinates of each player's leftmost tile
	private static final int PLAYER_HAND_X = 400;
	private static final int PLAYER_HAND_Y = 30;
	private static final int COM1_HAND_X = 1000;
	private static final int COM1_HAND_Y = 500;
	private static final int COM2_HAND_X = 400;
	private static final int COM2_HAND_Y = 640;
	private static final int COM3_HAND_X = 250;
	private static final int COM3_HAND_Y = 580;

	public void draw(List<Tile> hand, int playerId, boolean hasTile, Tile tile) {
		switch (playerId) {
		case Table.PLAYER:
			handX = PLAYER_HAND_X;
			handY = PLAYER_HAND_Y;
			direction = Tile.HAND;
			drawPlayer(hand, hasTile, tile);
			break;
		case Table.COM1:
			handX = COM1_HAND_X;
			handY = COM1_HAND_Y;
			direction = Tile.DISPLAY_LEFT;
			drawCom1(hasTile, hand.size());
			break;
		case Table.COM2:
			handX = COM2_HAND_X;
			handY = COM2_HAND_Y;
			direction = Tile.DISPLAY_REVERSE;
			drawCom2(hasTile, hand.size());
			break;
		case Table.COM3:
			handX = COM3_HAND_X;
			handY = COM3_HAND_Y;
			direction = Tile.DISPLAY_RIGHT;
			drawCom3(hasTile, hand.size());
			break;
		}
	}

	private void drawPlayer(List<Tile> hand, boolean hasTile, Tile tile) {
		for (int i = 0; i < Table.HAND; i++) {
			Texture texture = Mahjong.tileTextures.getTileTexture(hand.get(i).getKind(), hand.get(i).getValue(),
					direction);
			Mahjong.batch.draw(texture, handX + (i * texture.getWidth()), handY);
		}
		if (hasTile) {
			Texture texture = Mahjong.tileTextures.getTileTexture(tile.getKind(), tile.getValue(), Tile.HAND);
			Mahjong.batch.draw(texture, handX + ((hand.size() + 1) * texture.getWidth()), handY);
		}
	}

	private void drawCom1(boolean hasTile, int size) {
		for (int i = 0; i < Table.HAND; i++) {
			Texture texture = Mahjong.tileTextures.getBackTileTexture(direction);
			Mahjong.batch.draw(texture, handX, handY - (i * (texture.getHeight() - 15)));
		}
		if (hasTile) {
			Texture texture = Mahjong.tileTextures.getBackTileTexture(direction);
			Mahjong.batch.draw(texture, handX, handY - ((-2) * (texture.getHeight() - 15)));
		}
	}

	private void drawCom2(boolean hasTile, int size) {
		for (int i = 0; i < Table.HAND; i++) {
			Texture texture = Mahjong.tileTextures.getBackTileTexture(direction);
			Mahjong.batch.draw(texture, handX + (i * texture.getWidth()), handY);
		}
		if (hasTile) {
			Texture texture = Mahjong.tileTextures.getBackTileTexture(direction);
			Mahjong.batch.draw(texture, handX + ((-2) * texture.getWidth()), handY);
		}
	}

	private void drawCom3(boolean hasTile, int size) {
		for (int i = 0; i < Table.HAND; i++) {
			Texture texture = Mahjong.tileTextures.getBackTileTexture(direction);
			Mahjong.batch.draw(texture, handX, handY - (i * (texture.getHeight() - 15)));
		}
		if (hasTile) {
			Texture texture = Mahjong.tileTextures.getBackTileTexture(direction);
			Mahjong.batch.draw(texture, handX, handY - ((size + 1) * (texture.getHeight() - 15)));
		}
	}

}
