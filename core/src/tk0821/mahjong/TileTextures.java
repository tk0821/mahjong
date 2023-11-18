package tk0821.mahjong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TileTextures {

	// directory
	private static final String TILES_DIR = "tiles/";
	private static final String CHARACTERS_DIR = "characters/";
	private static final String CIRCLES_DIR = "circles/";
	private static final String BAMBOOS_DIR = "bamboos/";
	private static final String HONOURS_DIR = "honours/";
	private static final String BACK_DIR = "back/";

	// prefix
	private static final String CHARACTERS_PREFIX = "CH_";
	private static final String CIRCLES_PREFIX = "CI_";
	private static final String BAMBOOS_PREFIX = "BA_";
	private static final String HONOURS_PREFIX = "HO_";
	private static final String BACK_TILE_PREFIX = "back_";
	
	// extension
	private static final String EXTENSION = ".gif";

	private Texture[][][] textures; // kind, value, direction
	private Texture[] backTileTexture;

	public TileTextures() {
		textures = new Texture[Tile.KIND+1][][];	// use a one-based index
		for (int i = 1; i <= Tile.KIND; i++) {
			int value = (i == Tile.HONOURS) ? Tile.HONOURS_RANGE : Tile.SUIT_RANGE;
			textures[i] = new Texture[value+1][Tile.DIRECTION+1];
			for (int j = 1; j <= value; j++) {
				for (int k = 1; k <= Tile.DIRECTION; k++) {
					textures[i][j][k] = new Texture(Gdx.files.internal(createPath(i, j, k)));
				}
			}
		}
		
		backTileTexture = new Texture[Tile.DIRECTION+1];
		for (int i = 1; i <= Tile.DIRECTION; i++) {
			backTileTexture[i] = new Texture(Gdx.files.internal(TILES_DIR+BACK_DIR+BACK_TILE_PREFIX+i+EXTENSION));
		}
	}

	public Texture getBackTileTexture(int direction) {
		return backTileTexture[direction];
	}
	
	public Texture getTileTexture(int kind, int value, int direction) {
		return textures[kind][value][direction];
	}
	
	private String createPath(int kind, int value, int direction) {
		StringBuilder sb = new StringBuilder();
		sb.append(TILES_DIR);
		sb.append(createPathKindAndPrefix(kind));
		sb.append(value + "_");
		sb.append(direction);
		sb.append(EXTENSION);
		return sb.toString();
	}

	private String createPathKindAndPrefix(int kind) {
		String path = "";
		switch (kind) {
		case Tile.CHARACTERS:
			path = CHARACTERS_DIR + CHARACTERS_PREFIX;
			break;
		case Tile.CIRCLES:
			path = CIRCLES_DIR + CIRCLES_PREFIX;
			break;
		case Tile.BAMBOOS:
			path = BAMBOOS_DIR + BAMBOOS_PREFIX;
			break;
		case Tile.HONOURS:
			path = HONOURS_DIR + HONOURS_PREFIX;
			break;
		}
		return path;
	}

}
