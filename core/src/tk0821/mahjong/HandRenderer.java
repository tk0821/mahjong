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
	private static final int COM1_HAND_X = 1160;
	private static final int COM1_HAND_Y = 550;
	private static final int COM2_HAND_X = 400;
	private static final int COM2_HAND_Y = 640;
	private static final int COM3_HAND_X = 80;
	private static final int COM3_HAND_Y = 550;

	public void draw(List<Tile> hand, int playerId) {
		determineHandLayout(playerId);
		for (int i = 0; i < Table.HAND; i++) {
			Texture texture;
			if (playerId == Table.PLAYER) {
				texture = Mahjong.tileTextures.getTileTexture(hand.get(i).getKind(), hand.get(i).getValue(),
						direction);
			} else {
				texture = Mahjong.tileTextures.getBackTileTexture(direction);
			}
			if (playerId % 2 == 0) {
				Mahjong.batch.draw(texture, handX + (i * texture.getWidth()), handY);
			} else {
				Mahjong.batch.draw(texture, handX, handY - (i * (texture.getHeight() - 15 )));
			}
		}
	}

	private void determineHandLayout(int playerId) {
		switch (playerId) {
		case Table.PLAYER:
			handX = PLAYER_HAND_X;
			handY = PLAYER_HAND_Y;
			direction = Tile.HAND;
			break;
		case Table.COM1:
			handX = COM1_HAND_X;
			handY = COM1_HAND_Y;
			direction = Tile.DISPLAY_LEFT;
			break;
		case Table.COM2:
			handX = COM2_HAND_X;
			handY = COM2_HAND_Y;
			direction = Tile.DISPLAY_REVERSE;
			break;
		case Table.COM3:
			handX = COM3_HAND_X;
			handY = COM3_HAND_Y;
			direction = Tile.DISPLAY_RIGHT;
			break;
		}
	}

}
