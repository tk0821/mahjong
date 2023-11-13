package tk0821.mahjong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tiles {

	private static final int SUIT = 9 * 3; // ch, ci, ba
	private static final int CHARACTER = 7; // ew, sw, ww, nw, wd, gd, rd
	private static final int TILES = SUIT + CHARACTER;
	private static final int IDENTICIAL_TILES = 4;
	private static final int FULL_TILES = TILES * IDENTICIAL_TILES;
	private static final int HANDS = 13;
	private static final int DEAD_WALL = 14;
	private static final int DEAD_WALL_START = FULL_TILES - DEAD_WALL;

	// For array index calculation
	private static final int CHARACTERS = 0;
	private static final int CIRCLES = 9;
	private static final int BAMBOOS = 18;
	private static final int WINDS = 27;
	private static final int DRAGONS = 31;

	private int[] tiles; // store number of tiles
	private static int[] wall; // store the index of the tile
	private static int wallIndex;
	private static int deadWallIndex;

	public Tiles() {
		tiles = new int[TILES];
	}

	public Tiles createHands() {
		Tiles hands = new Tiles();
		int[] tiles = new int[TILES];
		for (int i = 0; i < HANDS; i++) {
			tiles[wall[wallIndex]]++;
			this.tiles[wall[wallIndex]]--;
			wallIndex++;
		}
		hands.setTiles(tiles);
		return hands;
	}

	public void setTiles(int[] tiles) {
		this.tiles = tiles;
	}

	public void generateWall() {
		Arrays.fill(tiles, IDENTICIAL_TILES);
		wallIndex = 0;
		deadWallIndex = DEAD_WALL_START;
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < TILES; i++) {
			for (int j = 0; j < IDENTICIAL_TILES; j++) {
				list.add(i);
			}
		}

		Collections.shuffle(list);
		wall = new int[FULL_TILES];
		for (int i = 0; i < FULL_TILES; i++) {
			wall[i] = list.get(i);
		}
	}

	public void printTiles() {
		for (int x : tiles) {
			System.out.print(x + " ");
		}
		System.out.println();
	}

	public void printWall() {
		System.out.println("<wall>");
		for (int i = wallIndex; i < DEAD_WALL_START; i++ ) {
			System.out.print(wall[i] + " ");
		}
		System.out.println();
		System.out.println("<dead wall>");
		for (int i = DEAD_WALL_START; i < FULL_TILES; i++ ) {
			System.out.print(wall[i] + " ");
		}
		System.out.println();
	}
}
