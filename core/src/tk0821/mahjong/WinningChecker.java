package tk0821.mahjong;

import java.util.ArrayList;
import java.util.List;

public class WinningChecker {

	CompleteHand hand;

	private int[] tileCount;
	private int[][] completeHand;
	private int han;
	private int openIndex;

	private int HonorTilesCount;

	private boolean isWinning;
	private boolean isDoubleSideWaited;

	private int maxHan;
	private int[][] maxHanCompleteHand;

	private List<String> yakuList;

	public WinningChecker(CompleteHand hand) {

		this.hand = hand;
		completeHand = new int[5][];
		tileCount = new int[34];

		int[][] openHandSet = hand.getOpenHandSet();
		if (openHandSet != null) {
			while (openHandSet[openIndex] != null) {
				completeHand[openIndex] = openHandSet[openIndex];
				openIndex += 1;
			}
		}
		for (var t : hand.getHand()) {
			tileCount[(t.getKind() - 1) * 9 + (t.getValue() - 1)] += 1;
		}

		bt(tileCount, openIndex, false,
				(hand.getWinningTile().getKind() - 1) * 9 + (hand.getWinningTile().getValue() - 1), openIndex);

		if (isWinning) {
			for (var s : getYakuList()) {
				System.out.println(s);
			}

			for (var s : maxHanCompleteHand) {
				for (var x : s) {
					System.out.print(x);
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	private void checkYaku() {
		List<String> yakuList = new ArrayList<>();

		han = 0;
		maxHan = 0;

		if (checkTsumo())
			yakuList.add("Tsumo：1 han");
		if (checkRiichi())
			yakuList.add("Riichi：1 han");
		if (checkDoubleRiichi())
			yakuList.add("Double Riichi：2 han");
		if (checkIppatsu())
			yakuList.add("Ippatsu：1 han");
		if (checkAllSimples())
			yakuList.add("All Simples：1 han");
		if (checkPinfu())
			yakuList.add("Pinfu：1 han");
		if (checkTwinSequences())
			yakuList.add("Twin Sequences：1 han");
		if (checkHonorTiles())
			yakuList.add("Honor Tiles x " + HonorTilesCount + "：" + HonorTilesCount + " han");
		if (checkRobbingAKan())
			yakuList.add("Robbing a Kan：1 han");
		if (checkAfterAKan())
			yakuList.add("After a Kan：1 han");
		if (checkLastTileDraw())
			yakuList.add("Last Tile Draw：1 han");
		if (checkLastTileClaim())
			yakuList.add("Last Tile Claim：1 han");

		if (maxHan < han) {
			this.setYakuList(yakuList);
			setWinning(true);
			maxHan = han;
			maxHanCompleteHand = completeHand.clone();
		}
	}

	// 面前自摸
	private boolean checkTsumo() {
		if (hand.isTsumo() && !hand.isCalling()) {
			han += 1;
			return true;
		}
		return false;
	}

	// 立直
	private boolean checkRiichi() {
		if (hand.isRiichi()) {
			han += 1;
			//canCheckUraDora = true;
			return true;
		}
		return false;
	}

	// 一発
	private boolean checkIppatsu() {
		if (hand.isRiichi() && hand.isIppatsu()) {
			han += 1;
			return true;
		}
		return false;
	}

	// タンヤオ
	private boolean checkAllSimples() {

		for (var set : completeHand) {
			for (var i : set) {
				if (i >= 27) {
					return false;
				}
				if (i % 9 == 0 || i % 9 == 8) {
					return false;
				}
			}
		}
		han += 1;
		return true;
	}

	private boolean isSuits(int index) {
		return index < 27;
	}

	private boolean isHonours(int index) {
		return index >= 27;
	}

	private boolean isHonorTiles(int index) {
		if (index == hand.getPrevalentWindIndex()) {
			return true;
		}
		if (index == hand.getSeatWindIndex()) {
			return true;
		}
		if (index >= 31) {
			return true;
		}
		return false;
	}

	private boolean isSequence(int[] set) {
		if (set.length != 3) {
			return false;
		}
		if (isSuits(set[0])) {
			if (set[0] == set[1] - 1 || set[1] == set[2] - 1) {
				if (set[0] % 9 < set[0] % 9) {
					return true;
				}
			}
		}
		return false;
	}

	// 平和
	private boolean checkPinfu() {
		if (!isDoubleSideWaited) {
			return false;
		}
		for (var set : completeHand) {
			if (set.length == 2) {
				if (isHonorTiles(set[0])) {
					return false;
				}
			} else if (!isSequence(set)) {
				return false;
			}
		}
		han += 1;
		return true;
	}

	// 一盃口
	private boolean checkTwinSequences() {
		for (int i = 0; i < completeHand.length; i++) {
			if (completeHand[i].length == 2) {
				continue;
			}
			for (int j = i + 1; j < completeHand.length; j++) {
				if (completeHand[j].length == 2) {
					continue;
				}
				for (int k = 0; k < completeHand[j].length; k++) {
					if (completeHand[i][k] == completeHand[j][k]) {
						if (k == completeHand[j].length - 1) {
							han += 1;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// 役牌
	// 連風牌
	private boolean checkHonorTiles() {
		HonorTilesCount = 0;
		for (var set : completeHand) {
			if (set.length == 3) {
				if (isHonorTiles(set[0])) {
					han += 1;
					HonorTilesCount += 1;
				}
				if (set[0] == hand.getPrevalentWindIndex() && set[0] == hand.getSeatWindIndex()) {
					han += 1;
					HonorTilesCount += 1;
				}
			}
		}
		return HonorTilesCount > 0;
	}

	// 槍槓
	private boolean checkRobbingAKan() {
		if (hand.isKan() && !hand.isTsumo()) {
			han += 1;
			return true;
		}
		return false;
	}

	// 嶺上開花
	private boolean checkAfterAKan() {
		if (hand.isKan() && hand.isTsumo()) {
			han += 1;
			return true;
		}
		return false;
	}

	// 海底
	private boolean checkLastTileDraw() {
		if (hand.isTsumo() && hand.isLastTile()) {
			han += 1;
			return true;
		}
		return false;
	}

	// 河底
	private boolean checkLastTileClaim() {
		if (!hand.isTsumo() && hand.isLastTile()) {
			han += 1;
			return true;
		}
		return false;
	}

	// ダブルリーチ
	private boolean checkDoubleRiichi() {
		if (hand.isDoubleRiichi()) {
			han += 2;
			return true;
		}
		return false;
	}

	// 七対子
	private boolean checkSevenPairs() {
		for (var set : completeHand) {
			if (set.length == 3) {
				return false;
			}
		}
		han += 2;
		return true;
	}

	private boolean isTriplets(int[] set) {
		if (set.length >= 3) {
			if (set[0] != set[1] || set[1] != set[2]) {
				return false;
			}
		}
		return true;
	}

	// 対々和
	private boolean checkAllTriplets() {
		for (var set : completeHand) {
			if (set.length != 2) {
				if (!isTriplets(set)) {
					return false;
				}
			}
		}
		han += 2;
		return true;
	}

	// 三暗刻
	private boolean checkThreeConcealedTriplets() {
		int count = 0;
		for (int i = openIndex; i < completeHand.length; i++) {
			if (isTriplets(completeHand[i])) {
				count += 1;
			}
		}
		if (count == 3) {
			han += 2;
			return true;
		}
		return false;
	}

	// 三色同刻
	private boolean MixedTriplets() {

		int count = 0;

		for (int i = 0; i < completeHand.length - 2; i++) {
			count = 0;
			if (isTriplets(completeHand[i])) {
				for (int j = i + 1; j < completeHand.length; j++) {
					if (isTriplets(completeHand[j])) {
						if (completeHand[i][0] == completeHand[j][0]) {
							count += 1;
							if (count == 3) {
								han += 2;
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	// 三色同順
	// 混老頭
	// 一気通貫
	// チャンタ
	// 小三元
	// 三槓子

	// 混一色
	// 純チャン
	// 二盃口

	// 流し満貫

	// 清一色

	// 天和
	// 地和
	// 人和
	// 緑一色
	// 大三元
	// 小四喜
	// 字一色
	// 国士無双
	// 九蓮宝燈
	// 四暗刻
	// 清老頭
	// 四槓子

	// 四暗刻単騎
	// 大四喜
	// 純正九蓮宝燈
	// 国士無双十三面待ち

	private boolean bt(int[] tileCount, int completeHandCount, boolean hasHead, int tileIndex, int completeHandIndex) {
		for (int i = 0; i < 34; i++) {
			if (!hasHead) {
				if (tileCount[i] >= 2) {
					tileCount[i] -= 2;
					completeHand[completeHandIndex] = new int[] { i, i };
					bt(tileCount, 0, true, tileIndex, completeHandIndex + 1);
					completeHand[completeHandIndex] = null;
					tileCount[i] += 2;
				}
			}
			if (i % 9 < 8 && i < 27 && tileCount[i] >= 1 && tileCount[i + 1] >= 1 && tileCount[i + 2] >= 1) {
				tileCount[i] -= 1;
				tileCount[i + 1] -= 1;
				tileCount[i + 2] -= 1;
				completeHand[completeHandIndex] = new int[] { i, i + 1, i + 2 };
				bt(tileCount, completeHandCount + 1, hasHead, tileIndex, completeHandIndex + 1);
				completeHand[completeHandIndex] = null;
				tileCount[i] += 1;
				tileCount[i + 1] += 1;
				tileCount[i + 2] += 1;
			}
			if (tileCount[i] >= 3) {
				tileCount[i] -= 3;
				completeHand[completeHandIndex] = new int[] { i, i, i };
				bt(tileCount, completeHandCount + 1, hasHead, tileIndex, completeHandIndex + 1);
				completeHand[completeHandIndex] = null;
				tileCount[i] += 3;
			}

		}

		if (hasHead && completeHandCount >= 3) {
			if (tileCount[tileIndex] == 2) {
				completeHand[completeHandIndex] = new int[] { tileIndex, tileIndex, tileIndex };
				checkYaku();
				completeHand[completeHandIndex] = null;
				return true;
			}
			for (int i = 0; i < 27; i++) {
				if ((i % 9) < ((i + 1) % 9) && tileCount[i] == 1 && tileCount[i + 1] == 1) {
					if (i % 9 < 7 && i + 2 == tileIndex) {
						completeHand[completeHandIndex] = new int[] { i, i + 1, i + 2 };
						if (i % 9 > 0) {
							isDoubleSideWaited = true;
						}
						checkYaku();
						isDoubleSideWaited = false;
						completeHand[completeHandIndex] = null;
						return true;
					}
					if (i % 9 > 0 && i - 1 == tileIndex) {
						completeHand[completeHandIndex] = new int[] { i - 1, i, i + 1 };
						if (i % 9 < 7) {
							isDoubleSideWaited = true;
						}
						checkYaku();
						isDoubleSideWaited = false;
						completeHand[completeHandIndex] = null;
						return true;
					}
				}
				if ((i % 9) < ((i + 2) % 9) && i < 27 && tileCount[i] == 1 && tileCount[i + 2] == 1) {
					if (i + 1 == tileIndex) {
						completeHand[completeHandIndex] = new int[] { i, i + 1, i + 2 };
						checkYaku();
						completeHand[completeHandIndex] = null;
						return true;
					}
				}
			}
		} else if (completeHandCount == 4) {
			if (tileCount[tileIndex] == 1) {
				completeHand[completeHandIndex] = new int[] { tileIndex, tileIndex };
				checkYaku();
				completeHand[completeHandIndex] = null;
				return true;
			}

		}
		return false;
	}

	public boolean isWinning() {
		return isWinning;
	}

	public void setWinning(boolean isWinning) {
		this.isWinning = isWinning;
	}

	public List<String> getYakuList() {
		return yakuList;
	}

	public void setYakuList(List<String> yakuList) {
		this.yakuList = yakuList;
	}

	public int getMaxHan() {
		return maxHan;
	}
}
