package tk0821.mahjong;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public class DiscardRenderer {

	private static final int PLAYER_DISCARD_X = 530;
	private static final int PLAYER_DISCARD_Y = 200;

	private static final int COM1_DISCARD_X = 820;
	private static final int COM1_DISCARD_Y = 270;
	private static final int COM2_DISCARD_X = 700;
	private static final int COM2_DISCARD_Y = 470;
	private static final int COM3_DISCARD_X = 400;
	private static final int COM3_DISCARD_Y = 430;
	
	private int discardX;
	private int discardY;
	private int direction;
	
	public void draw(List<Tile> discard, int playerId) {
		switch (playerId) {
		case Table.PLAYER:
			discardX = PLAYER_DISCARD_X;
			discardY = PLAYER_DISCARD_Y;
			direction = Tile.DISPLAY;
			drawPlayer(discard);
			break;
		case Table.COM1:
			discardX = COM1_DISCARD_X;
			discardY = COM1_DISCARD_Y;
			direction = Tile.DISPLAY_LEFT;
			drawCom1(discard);
			break;
		case Table.COM2:
			discardX = COM2_DISCARD_X;
			discardY = COM2_DISCARD_Y;
			direction = Tile.DISPLAY_REVERSE;
			drawCom2(discard);
			break;
		case Table.COM3:
			discardX = COM3_DISCARD_X;
			discardY = COM3_DISCARD_Y;
			direction = Tile.DISPLAY_RIGHT;
			drawCom3(discard);
			break;
		}		
	}
	
	public void drawPlayer(List<Tile> discard) {
		for (int j = 0; j < discard.size(); j++) {
			int column = j < 18 ? j / 6 : 2;
			int row = j < 18 ? j % 6 : j - 12;
			Texture texture = Mahjong.tileTextures.getTileTexture(discard.get(j).getKind(), discard.get(j).getValue(),
					direction);
			Mahjong.batch.draw(texture, discardX + (row * texture.getWidth()), discardY - (column * (texture.getHeight() - 15)));
		}
	}
	
	public void drawCom1(List<Tile> discard) {
		for (int j = discard.size()-1; j >= 0; j--) {
			int column = j < 18 ? j % 6 : j - 12;
			int row = j < 18 ? j / 6 : 2;
			Texture texture = Mahjong.tileTextures.getTileTexture(discard.get(j).getKind(), discard.get(j).getValue(),
					direction);
			Mahjong.batch.draw(texture, discardX + (row * texture.getWidth()), discardY + (column * (texture.getHeight() - 15)));
		}
	}
	
	public void drawCom2(List<Tile> discard) {
		for (int j = discard.size()-1; j >= 0; j--) {
			int column = j < 18 ? j / 6 : 2;
			int row = j < 18 ? j % 6 : j - 12;
			Texture texture = Mahjong.tileTextures.getTileTexture(discard.get(j).getKind(), discard.get(j).getValue(),
					direction);
			Mahjong.batch.draw(texture, discardX - (row * texture.getWidth()), discardY + (column * (texture.getHeight() - 15)));
		}
	}
	
	public void drawCom3(List<Tile> discard) {
		for (int j = 0; j < discard.size(); j++) {
			int column = j < 18 ? j % 6 : j - 12;
			int row = j < 18 ? j / 6 : 2;
			Texture texture = Mahjong.tileTextures.getTileTexture(discard.get(j).getKind(), discard.get(j).getValue(),
					direction);
			Mahjong.batch.draw(texture, discardX - (row * texture.getWidth()), discardY - (column * (texture.getHeight() - 15)));
		}
	}
}
