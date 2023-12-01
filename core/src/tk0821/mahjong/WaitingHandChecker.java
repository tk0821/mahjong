package tk0821.mahjong;

import java.util.ArrayList;
import java.util.List;

public class WaitingHandChecker {

	private boolean[] winningTileArray;
	private int headTileIndex = -1;

	public WaitingHandChecker() {
		winningTileArray = new boolean[34];
	}

	public String parseWinningTileArray() {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 27; i++) {
			if (winningTileArray[i]) {
				sb.append(i % 9 + 1);

				if (i < 9) {
					sb.append("m");
				} else if (i < 18) {
					sb.append("p");
				} else {
					sb.append("s");
				}
			}
		}

		if (winningTileArray[27]) {
			sb.append("東 ");
		}
		if (winningTileArray[28]) {
			sb.append("南 ");
		}
		if (winningTileArray[29]) {
			sb.append("西 ");
		}
		if (winningTileArray[30]) {
			sb.append("北 ");
		}
		if (winningTileArray[31]) {
			sb.append("白 ");
		}
		if (winningTileArray[32]) {
			sb.append("發 ");
		}
		if (winningTileArray[33]) {
			sb.append("中 ");
		}
		System.out.println(sb);
		return sb.toString();
	}

	public boolean[] getWinningTileArray() {
		return winningTileArray;
	}

	public boolean isWaitingHand(String handString) {

		for (int i = 0; i < 34; i++) {
			winningTileArray[i] = false;
		}

		List<Tile> playerHand = parseHand(handString);
		int[] tileCount = new int[34];
		for (var t : playerHand) {
			tileCount[(t.getKind() - 1) * 9 + (t.getValue() - 1)]++;
		}
		print(tileCount);
		if (!checkThirteenOrphans(tileCount)) {
			bt(tileCount, 0, false);
		}
		for (var f : winningTileArray) {
			if (f) {
				return true;
			}
		}
		return false;
	}

	public boolean isWaitingHand(List<Tile> playerHand, Tile tile) {

		for (int i = 0; i < 34; i++) {
			winningTileArray[i] = false;
		}
		
		int[] tileCount = new int[34];
		tileCount[(tile.getKind() - 1) * 9 + (tile.getValue() - 1)]++;
		for (var t : playerHand) {
			tileCount[(t.getKind() - 1) * 9 + (t.getValue() - 1)]++;
		}
		if (!checkThirteenOrphans(tileCount)) {
			bt(tileCount, 0, false);
		}
		for (var f : winningTileArray) {
			if (f) {
				return true;
			}
		}
		return false;
	}

	public boolean isWaitingHand(List<Tile> playerHand) {

		for (int i = 0; i < 34; i++) {
			winningTileArray[i] = false;
		}

		int[] tileCount = new int[34];
		for (var t : playerHand) {
			tileCount[(t.getKind() - 1) * 9 + (t.getValue() - 1)]++;
		}
		print(tileCount);
		if (!checkThirteenOrphans(tileCount)) {
			bt(tileCount, 0, false);
		}
		for (var f : winningTileArray) {
			if (f) {
				return true;
			}
		}
		return false;
	}
	
	private boolean bt(int[] tileCount, int setCount, boolean hasHead) {

		for (int i = 0; i < 34; i++) {
			if (!hasHead) {
				if (tileCount[i] >= 2) {
					headTileIndex = i;
					tileCount[i] -= 2;
					bt(tileCount, 0, true);
					headTileIndex = -1;
					tileCount[i] += 2;
				}
			}
			if (i % 9 < 8 && i < 27 && tileCount[i] >= 1 && tileCount[i + 1] >= 1 && tileCount[i + 2] >= 1) {
				tileCount[i] -= 1;
				tileCount[i + 1] -= 1;
				tileCount[i + 2] -= 1;
				bt(tileCount, setCount + 1, hasHead);
				tileCount[i] += 1;
				tileCount[i + 1] += 1;
				tileCount[i + 2] += 1;
			}

			if (tileCount[i] >= 3) {
				tileCount[i] -= 3;
				bt(tileCount, setCount + 1, hasHead);
				tileCount[i] += 3;
			}

		}

		if (hasHead) {
			if (setCount >= 3) {
				for (int i = 0; i < 34; i++) {
					if (tileCount[i] == 2) {
						winningTileArray[i] = true;
						winningTileArray[headTileIndex] = true;
						return true;
					}
					if ((i % 9) < ((i + 1) % 9) && i < 27) {
						if (tileCount[i] == 1 && tileCount[i + 1] == 1) {
							if (i % 9 < 7) {
								winningTileArray[i + 2] = true;
							}
							if (i % 9 > 0) {
								winningTileArray[i - 1] = true;
							}
							return true;
						}
					}

					if ((i % 9) < ((i + 2) % 9) && i < 27) {
						if (tileCount[i] == 1 && tileCount[i + 2] == 1) {
							winningTileArray[i + 1] = true;
							return true;
						}
					}
				}
			}
			checkSevenPairs(tileCount);
		} else {
			if (setCount == 4) {

				for (int i = 0; i < 34; i++) {
					if (tileCount[i] == 1) {
						winningTileArray[i] = true;
					}
				}
				return true;
			}
		}

		return false;
	}

	private boolean checkSevenPairs(int[] tileCount) {
		int pairCount = 0;
		for (var x : tileCount) {
			if (x == 2) {
				pairCount++;
			}
		}
		if (pairCount >= 5) {
			for (var x : tileCount) {
				if (x == 1) {
					winningTileArray[x] = true;
				}
			}
			return true;
		}
		return false;
	}

	private boolean checkThirteenOrphans(int[] tileCount) {
		// 0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33

		boolean hasHead = false;
		int cnt = 0;
		for (int i = 0; i < 3; i++) {
			if (tileCount[i * 9] >= 1) {
				if (tileCount[i * 9] >= 2) {
					hasHead = true;
				}
				cnt++;
			}
			if (tileCount[(i + 1) * 9 - 1] >= 1) {
				if (tileCount[(i + 1) * 9 - 1] >= 2) {
					hasHead = true;
				}
				cnt++;
			}
		}
		for (int i = 27; i < 34; i++) {
			if (tileCount[i] >= 1) {
				if (tileCount[i] >= 2) {
					hasHead = true;
				}
				cnt++;
			}
		}
		if (cnt >= 13 || (hasHead && cnt >= 12)) {

			if (!hasHead || cnt >= 13) {
				for (int i = 0; i < 3; i++) {
					if (tileCount[i * 9] >= 1) {
						winningTileArray[i] = true;
					}
					if (tileCount[(i + 1) * 9 - 1] >= 1) {
						winningTileArray[i] = true;
					}
				}
				for (int i = 27; i < 34; i++) {
					winningTileArray[i] = true;
				}
			} else {
				for (int i = 0; i < 3; i++) {
					if (tileCount[i * 9] == 0) {
						winningTileArray[i] = true;
					}
					if (tileCount[(i + 1) * 9 - 1] == 0) {
						winningTileArray[i] = true;
					}
				}
				for (int i = 27; i < 34; i++) {
					if (tileCount[i] == 0) {
						winningTileArray[i] = true;
					}
				}
			}
			return true;
		}
		return false;
	}

	private void print(int[] tileCount) {
		int flag = 0;
		for (int i = 0; i < 34; i++) {
			if (i % 9 == 0) {
				if (flag == 0) {
					System.out.print("[");
					flag = 1;
				} else {
					System.out.print("][");
				}
			} else {
				System.out.print(",");
			}
			System.out.print(tileCount[i]);
		}
		System.out.println("]");
	}

	public List<Tile> parseHand(String handString) {
		List<Tile> playerHand = new ArrayList<>();
		for (int i = 0; i < handString.length(); i += 2) {
			String s = handString.substring(i, i + 2);
			switch (s.charAt(1)) {
			case 'm':
				playerHand.add(new Tile(Tile.CHARACTERS, Character.getNumericValue(s.charAt(0))));
				break;
			case 'p':
				playerHand.add(new Tile(Tile.CIRCLES, Character.getNumericValue(s.charAt(0))));
				break;
			case 's':
				playerHand.add(new Tile(Tile.BAMBOOS, Character.getNumericValue(s.charAt(0))));
				break;
			case ' ':
				switch (s.charAt(0)) {
				case '東':
					playerHand.add(new Tile(Tile.HONOURS, Tile.EAST));
					break;
				case '南':
					playerHand.add(new Tile(Tile.HONOURS, Tile.SOUTH));
					break;
				case '西':
					playerHand.add(new Tile(Tile.HONOURS, Tile.WEST));
					break;
				case '北':
					playerHand.add(new Tile(Tile.HONOURS, Tile.NORTH));
					break;
				case '白':
					playerHand.add(new Tile(Tile.HONOURS, Tile.WHITE));
					break;
				case '發':
					playerHand.add(new Tile(Tile.HONOURS, Tile.GREEN));
					break;
				case '中':
					playerHand.add(new Tile(Tile.HONOURS, Tile.RED));
					break;
				}
			}
		}
		return playerHand;
	}
}
