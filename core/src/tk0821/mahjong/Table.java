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
	private static final int HAND = 13;

	private List<Tile> wall;
	private int wallIndex;

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

	public List<Tile> generateHand() {
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
