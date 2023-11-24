package tk0821.mahjong;

import java.util.ArrayList;
import java.util.List;

public class WaitingHandChecker {

	public static List<Tile> parseHand(String handString) {
		// String2文字で1牌を表すとする
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

	public static boolean isWaitingHand(String handString) {
		List<Tile> playerHand = parseHand(handString);
		int[] tileCount = new int[34];
		for (var t : playerHand) {
			tileCount[(t.getKind() - 1) * 9 + (t.getValue() - 1)]++;
		}
		print(tileCount);
		if (bt(tileCount, 0, false)) {
			System.out.println("waiting hand");
			return true;
		}
		return false;
	}

	public static boolean isWaitingHand(List<Tile> playerHand, Tile tile) {

		int[] tileCount = new int[34];

		tileCount[(tile.getKind() - 1) * 9 + (tile.getValue() - 1)]++;
		for (var t : playerHand) {
			tileCount[(t.getKind() - 1) * 9 + (t.getValue() - 1)]++;
		}

		print(tileCount);

		if (bt(tileCount, 0, false)) {
			return true;
		}

		return false;
	}

	private static boolean bt(int[] tileCount, int setCount, boolean hasHead) {

		for (int i = 0; i < 34; i++) {
			if (!hasHead) {
				//雀頭候補を探す
				if (tileCount[i] >= 2) {
					tileCount[i] -= 2;
					if (bt(tileCount, 0, true)) {
						return true;
					}
					tileCount[i] += 2;
				}
			}
			//面子を探す
			//順子
			if (i % 9 < 8 && i < 27 && tileCount[i] >= 1 && tileCount[i + 1] >= 1 && tileCount[i + 2] >= 1) {
				tileCount[i] -= 1;
				tileCount[i + 1] -= 1;
				tileCount[i + 2] -= 1;
				if (bt(tileCount, setCount + 1, hasHead)) {
					return true;
				}
				tileCount[i] += 1;
				tileCount[i + 1] += 1;
				tileCount[i + 2] += 1;
			}

			//刻子
			if (tileCount[i] >= 3) {
				tileCount[i] -= 3;
				if (bt(tileCount, setCount + 1, hasHead)) {
					return true;
				}
				tileCount[i] += 3;
			}

		}

		// 聴牌チェック
		if (hasHead) {
			if (setCount >= 3) {
				for (int i = 0; i < 34; i++) {
					//シャンポン
					if (tileCount[i] == 2) {
						return true;
					}
					// 数牌
					if ((i % 9) < ((i + 1) % 9) && i < 27) {
						//リャンメンorペンチャン
						if (tileCount[i] == 1 && tileCount[i + 1] == 1) {
							return true;
						}
					}

					if ((i % 9) < ((i + 2) % 9) && i < 27) {
						// カンチャン
						if (tileCount[i] == 1 && tileCount[i + 2] == 1) {
							return true;
						}
					}
				}
			}

			// 七対子
			int pairCount = 0;
			for (var x : tileCount) {
				if (x == 2) {
					pairCount++;
				}
			}
			if (pairCount >= 6) {
				return true;
			}

		} else {
			if (setCount == 4) {
				return true;
			}
		}
		return false;
	}

	private static void print(int[] tileCount) {
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
}
