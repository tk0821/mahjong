package tk0821.mahjong;

public class Tile implements Comparable<Tile> {

	// kind
	public static final int CHARACTERS = 1;
	public static final int CIRCLES = 2;
	public static final int BAMBOOS = 3;
	public static final int HONOURS = 4;
	public static final int KIND = 4;
	
	// honoursValue
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public static final int NORTH = 4;
	public static final int WHITE = 5;
	public static final int GREEN = 6;
	public static final int RED = 7;

	// range
	public static final int SUIT_RANGE = 9;
	public static final int HONOURS_RANGE = 7;

	// direction
	public static final int DIRECTION = 5;
	public static final int HAND = 1;
	public static final int DISPLAY = 2;
	public static final int DISPLAY_REVERSE = 3;
	public static final int DISPLAY_LEFT = 4;
	public static final int DISPLAY_RIGHT = 5;
	// public static final int HAND_REVERSE = 6;
	// public static final int HAND_LEFT = 7;
	// public static final int HAND_RIGHT = 8;
	
	private int kind; // 萬子,筒子,索子,字牌
	private int suitValue; //	[1, 9]
	private int honoursValue; // 東,南,西,北,白,發,中

	public Tile(int kind, int value) {
		setKind(kind);
		if (kind == HONOURS) {
			setHonoursValue(value);
		} else {
			setSuitValue(value);
		}
	}

	public int getKind() {
		return kind;
	}

	private void setKind(int kind) {
		this.kind = kind;
	}

	public int getValue() {
		if (kind == HONOURS) {
			return getHonoursValue();
		}
		return getSuitValue();
	}
	
	public int getSuitValue() {
		return suitValue;
	}

	private void setSuitValue(int suitValue) {
		this.suitValue = suitValue;
	}

	public int getHonoursValue() {
		return honoursValue;
	}

	private void setHonoursValue(int honoursValue) {
		this.honoursValue = honoursValue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (kind == HONOURS) {
			switch (honoursValue) {
			case EAST:
				sb.append("東");
				break;
			case SOUTH:
				sb.append("南");
				break;
			case WEST:
				sb.append("西");
				break;
			case NORTH:
				sb.append("北");
				break;
			case WHITE:
				sb.append("白");
				break;
			case GREEN:
				sb.append("發");
				break;
			case RED:
				sb.append("中");
				break;
			}
		} else {
			sb.append(suitValue);
			switch (kind) {
			case CHARACTERS:
				sb.append("萬");
				break;
			case CIRCLES:
				sb.append("筒");
				break;
			case BAMBOOS:
				sb.append("索");
				break;
			}
		}
		return sb.toString();
	}

	@Override
	public int compareTo(Tile o) {
		if (this.getKind() == o.getKind()) {
			if (this.getKind() == Tile.HONOURS) {
				return this.getHonoursValue() - o.getHonoursValue();
			} else {
				return this.getSuitValue() - o.getSuitValue();
			}
		} else {
			return this.getKind() - o.getKind();
		}
	}
}