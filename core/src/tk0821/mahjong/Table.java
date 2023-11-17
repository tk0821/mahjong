package tk0821.mahjong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {

	private static final int TILES = 34;
	private static final int IDENTICIAL_TILES = 4;
	private static final int FULL_TILES = TILES * IDENTICIAL_TILES;
	private static final int DEAD_WALL = 14;
	private static final int DEAD_WALL_START = FULL_TILES - DEAD_WALL;
	public static final int HAND = 13;

	public static final int PLAYERS = 4;
	
	// discards
	public static final int PLAYER = 0;
	public static final int COM1 = 1;
	public static final int COM2 = 2;
	public static final int COM3 = 3;
	
	private List<Tile> wall;
	private List<List<Tile>> discards;
	private int wallIndex;

	public boolean canGetWallTop() {
		return (wallIndex < DEAD_WALL_START);
	}
	
	public Tile getWallTop() {
		return wall.get(wallIndex++);
	}
	
	public void addTileToDiscard(Tile tile, int playerId) {
		discards.get(playerId).add(tile);
	}
	
	public List<Tile> getDiscard(int playerId) {
		return discards.get(playerId);
	}
	
	public void generateDiscards() {
		discards = new ArrayList<>();
		for (int i = 0; i < PLAYERS; i++) {
			List<Tile> list = new ArrayList<>();
			discards.add(list);
		}
	}
	
	public void generateWall() {
		wallIndex = 0;

		wall = new ArrayList<>();
		for (int i = 1; i <= Tile.HONOURS; i++) { // 萬子,筒子,索子,字牌
			int value = (i == Tile.HONOURS) ? Tile.HONOURS_RANGE : Tile.SUIT_RANGE;
			for (int j = 1; j <= value; j++) {
				for (int k = 1; k <= IDENTICIAL_TILES; k++) {
					Tile tile = new Tile(i, j);
					wall.add(tile);
				}
			}
		}
		Collections.shuffle(wall);
	}
	
	public List<Tile> dealHand() {
		List<Tile> hand = new ArrayList<>();
		for (int i = 0; i < HAND; i++) {
			hand.add(wall.get(wallIndex++));
		}
		return hand;
	}

	public void printWall() {
		System.out.println("<wall>");
		for (int i = wallIndex; i < DEAD_WALL_START; i++) {
			System.out.print(wall + " ");
		}
		System.out.println();
	}
}
