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
		maxHan = 0;

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
	}

	private void checkYaku() {
		List<String> yakuList = new ArrayList<>();
		han = 0;

		boolean isLimit = false;
		if (checkBlessingOfHeaven()) {
			yakuList.add("Blessing of Heaven：Limit");
			isLimit = true;
		}
		if (checkBlessingOfEarth()) {
			yakuList.add("Blessing of Earth：Limit");
			this.setYakuList(yakuList);
			isLimit = true;
		}
		if (checkAllGreen()) {
			yakuList.add("All Green：Limit");
			isLimit = true;
		}
		if (checkBigThreeDragons()) {
			yakuList.add("Big Three Dragons：Limit");
			isLimit = true;
		}
		if (checkAllHonors()) {
			yakuList.add("All Honors：Limit");
			isLimit = true;
		}
		if (checkThirteenOrphans()) {
			yakuList.add("Thirteen Orphans：Limit");
			isLimit = true;
		}
		if (checkNineGates()) {
			yakuList.add("Nine Gates：Limit");
			isLimit = true;
		}
		if (checkFourConcealedTriplets()) {
			yakuList.add("Four Concealed Triplets：Limit");
			isLimit = true;
		}
		if (checkAllTerminals()) {
			yakuList.add("All Terminals：Limit");
			isLimit = true;
		}
		if (checkFourKan()) {
			yakuList.add("Four Kan：Limit");
			isLimit = true;
		}
		if (checkLittleFourWinds()) {
			yakuList.add("Little Four Winds：Limit");
			isLimit = true;
		}
		if (checkBigFourWinds()) {
			yakuList.add("Big Four Winds：Limit");
			isLimit = true;
		}

		if (isLimit) {
			this.setYakuList(yakuList);
			setWinning(true);
			maxHan = 100;
			maxHanCompleteHand = completeHand.clone();
			return;
		}

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
		if (checkSevenPairs())
			yakuList.add("Seven Pairs：2 han");
		if (checkAllTriplets())
			yakuList.add("All Triplets：2 han");
		if (checkThreeConcealedTriplets())
			yakuList.add("Three Concealed Triplets：2 han");
		if (checkMixedTriplets())
			yakuList.add("Mixed Triplets：2 han");
		if (checkMixedSequences())
			yakuList.add("Mixed Sequences：" + (hand.isCalling() ? 1 : 2) + " han");
		if (checkAllTerminalsAndHonors())
			yakuList.add("All Terminals and Honors：2 han");
		if (checkFullStraight())
			yakuList.add("Full Straight：" + (hand.isCalling() ? 1 : 2) + " han");
		if (checkCommonEnds())
			yakuList.add("Common Ends：" + (hand.isCalling() ? 1 : 2) + " han");
		if (checkLittleThreeDragons())
			yakuList.add("Little Three Dragons：2 han");
		if (checkThreeKan())
			yakuList.add("Three Kan：2 han");
		if (checkHalfFlush())
			yakuList.add("Half Flush：" + (hand.isCalling() ? 2 : 3) + " han");
		if (checkCommonTerminals())
			yakuList.add("Common Terminals：" + (hand.isCalling() ? 2 : 3) + " han");
		if (checkDoubleTwinSequences())
			yakuList.add("Double Twin Sequences：3 han");
		if (checkFullFlush())
			yakuList.add("Full Flush：" + (hand.isCalling() ? 5 : 6) + " han");

		if (maxHan < han) {
			this.setYakuList(yakuList);
			setWinning(true);
			maxHan = han;
			maxHanCompleteHand = completeHand.clone();
		}
	}
	
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

	// 嶺上開花
	private boolean checkAfterAKan() {
		if (hand.isKan() && hand.isTsumo()) {
			han += 1;
			return true;
		}
		return false;
	}

	// 緑一色
	private boolean checkAllGreen() {
		for (var set : completeHand) {
			if (isBamboo(set[0])) {
				for (var tile : set) {
					switch (tile % 9) {
					case 1:
					case 2:
					case 3:
					case 5:
					case 7:
						break;
					default:
						return false;
					}
				}
			} else if (set[0] != 32) {
				return false;
			}
		}
		return true;
	}

	// 字一色
	private boolean checkAllHonors() {
		for (var set : completeHand) {
			if (!isHonors(set[0])) {
				return false;
			}
		}
		return true;
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

	// 清老頭
	private boolean checkAllTerminals() {
		int count = 0;
		for (var set : completeHand) {
			if (isTerminals(set)) {
				count += 1;
			} else {
				return false;
			}
		}
		return true;
	}

	// 混老頭
	private boolean checkAllTerminalsAndHonors() {

		for (var set : completeHand) {
			if (!isHonors(set[0]) && !isAllTerminals(set)) {
				return false;
			}
		}
		han += 2;
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

	// 大四喜
	private boolean checkBigFourWinds() {
		int count = 0;
		int pair = 0;
		for (var set : completeHand) {
			if (isWinds(set[0]) && isTriplets(set)) {
				count += 1;
			}
		}
		if (count == 4) {
			return true;
		}
		return false;
	}

	// 大三元
	private boolean checkBigThreeDragons() {
		int count = 0;
		for (var set : completeHand) {
			if (isDragons(set[0]) && isTriplets(set)) {
				count += 1;
			}
		}
		if (count == 3) {
			return true;
		}
		return false;
	}

	// 地和
	private boolean checkBlessingOfEarth() {
		if (hand.getSeatWindIndex() != Tile.EAST && hand.isTsumo()) {
			if (!hand.isKan()) {
				return true;
			}
		}
		return false;
	}

	// 天和
	private boolean checkBlessingOfHeaven() {
		if (hand.getSeatWindIndex() == Tile.EAST && hand.isTsumo()) {
			if (!hand.isKan()) {
				return true;
			}
		}
		return false;
	}

	// チャンタ
	private boolean checkCommonEnds() {
		for (var set : completeHand) {
			if (!isHonors(set[0]) && !isTerminals(set)) {
				return false;
			}
		}
		han += 2;
		if (hand.isCalling())
			han -= 1;
		return true;
	}

	// 純チャン
	private boolean checkCommonTerminals() {
		for (var set : completeHand) {
			if (!isTerminals(set)) {
				return false;
			}
		}
		han += 3;
		if (hand.isCalling())
			han -= 1;
		return true;
	}

	// ダブルリーチ
	private boolean checkDoubleRiichi() {
		if (hand.isDoubleRiichi()) {
			han += 2;
			return true;
		}
		return false;
	}

	// 二盃口
	private boolean checkDoubleTwinSequences() {
		if (hand.isCalling())
			return false;
		int count = 0;
		int skipIndex = -1;
		for (int i = 0; i < completeHand.length; i++) {
			if (completeHand[i].length == 2 || !isSequence(completeHand[i])) {
				continue;
			}
			if (i == skipIndex) {
				continue;
			}
			for (int j = i + 1; j < completeHand.length; j++) {
				if (completeHand[j].length == 2 || !isSequence(completeHand[i])) {
					continue;
				}
				if (completeHand[i][0] == completeHand[j][0]) {
					skipIndex = j;
					count += 1;
				}
			}
		}
		if (count == 2) {
			han += 3;
			return true;
		}

		return false;
	}

	// 四暗刻
	private boolean checkFourConcealedTriplets() {
		if (hand.isCalling()) {
			return false;
		}
		int count = 0;
		for (var set : completeHand) {
			if (isTriplets(set)) {
				count += 1;
			}
		}
		if (count == 4) {
			return true;
		}
		return false;
	}

	// 四槓子
	private boolean checkFourKan() {
		int count = 0;
		for (var set : completeHand) {
			if (isKan(set)) {
				count += 1;
			}
		}
		if (count == 4) {
			return true;
		}
		return false;
	}

	// 清一色
	private boolean checkFullFlush() {
		int index = completeHand[0][0];
		if (isHonors(index)) {
			return false;
		}
		for (int i = 1; i < completeHand.length; i++) {
			if (!isSameKind(index, completeHand[i][0])) {
				return false;
			}
		}
		han += 6;
		if (hand.isCalling()) {
			han -= 1;
		}
		return true;
	}

	// 一気通貫
	private boolean checkFullStraight() {

		for (int i = 0; i < completeHand.length - 2; i++) {
			if (!isSequence(completeHand[i]) || completeHand[i][0] % 3 != 0)
				continue;
			for (int j = i + 1; j < completeHand.length - 1; j++) {
				if (!isSequence(completeHand[j]) || completeHand[j][0] % 3 != 0
						|| completeHand[i][0] % 3 != completeHand[j][0] % 3)
					continue;
				for (int k = j + 1; k < completeHand.length; k++) {
					if (!isSequence(completeHand[k]) || completeHand[k][0] % 3 != 0
							|| completeHand[i][0] % 3 != completeHand[k][0] % 3
							|| completeHand[j][0] % 3 != completeHand[k][0] % 3)
						continue;
					han += 2;
					if (hand.isCalling())
						han -= 1;
					return true;
				}
			}
		}
		return false;
	}

	// 混一色
	private boolean checkHalfFlush() {

		boolean hasSuit = false;
		for (int i = 0; i < completeHand.length - 1; i++) {
			if (isHonors(completeHand[i][0]))
				continue;
			hasSuit = true;
			for (int j = i + 1; j < completeHand.length; j++) {
				if (isHonors(completeHand[j][0]))
					continue;
				if (!isSameKind(completeHand[i][0], completeHand[j][0])) {
					return false;
				}
			}
		}
		if (hasSuit) {
			han += 3;
			if (hand.isCalling())
				han -= 1;
			return true;
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

	// 一発
	private boolean checkIppatsu() {
		if (hand.isRiichi() && hand.isIppatsu()) {
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

	// 海底
	private boolean checkLastTileDraw() {
		if (hand.isTsumo() && hand.isLastTile()) {
			han += 1;
			return true;
		}
		return false;
	}

	// 小四喜
	private boolean checkLittleFourWinds() {
		int count = 0;
		int pair = 0;
		for (var set : completeHand) {
			if (isWinds(set[0])) {
				if (set.length == 2) {
					pair += 1;
				}
				count += 1;
			}
		}
		if (pair == 1 && count == 4) {
			return true;
		}
		return false;
	}

	// 小三元
	private boolean checkLittleThreeDragons() {
		int count = 0;
		boolean hasHead = false;
		for (var set : completeHand) {
			if (isDragons(set[0])) {
				if (isTriplets(set))
					count += 1;
				if (set.length == 2)
					hasHead = true;
			}
		}
		if (count == 2 && hasHead) {
			han += 2;
			return true;
		}
		return false;
	}

	// 三色同順
	private boolean checkMixedSequences() {
		for (int i = 0; i < completeHand.length - 2; i++) {
			if (isSequence(completeHand[i])) {
				int secondIndex = -1;
				for (int j = i + 1; j < completeHand.length; j++) {
					if (isSequence(completeHand[j])
							&& !isSameKind(completeHand[i][0], completeHand[j][0])
							&& isSameValue(completeHand[i][0], completeHand[j][0])) {
						if (secondIndex == -1) {
							secondIndex = completeHand[j][0];
						} else if (completeHand[j][0] != secondIndex) {
							han += 2;
							if (hand.isCalling()) {
								han -= 1;
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// 三色同刻
	private boolean checkMixedTriplets() {

		int count = 0;

		for (int i = 0; i < completeHand.length - 2; i++) {
			count = 0;
			if (isSuits(completeHand[i][0]) && isTriplets(completeHand[i])) {
				for (int j = i + 1; j < completeHand.length; j++) {
					if (isSuits(completeHand[j][0]) && isTriplets(completeHand[j])) {
						if (isSameValue(completeHand[i][0], completeHand[j][0])) {
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

	// 九蓮宝燈
	private boolean checkNineGates() {
		if (hand.isCalling()) {
			return false;
		}
		int[] counts = new int[9];
		int kind = completeHand[0][0];
		if (!isHonors(kind)) {
			return false;
		}
		for (var set : completeHand) {
			if (!isSameKind(kind, set[0])) {
				return false;
			}
			for (var index : set) {
				counts[index % 9] += 1;
			}
		}
		if (counts[0] >= 3 && counts[8] >= 3) {
			for (int i = 1; i < 8; i++) {
				if (counts[i] == 0) {
					return false;
				}
			}
			return true;
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

	// 立直
	private boolean checkRiichi() {
		if (hand.isRiichi()) {
			han += 1;
			//canCheckUraDora = true;
			return true;
		}
		return false;
	}

	// 槍槓
	private boolean checkRobbingAKan() {
		if (hand.isKan() && !hand.isTsumo()) {
			han += 1;
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

	// 国士無双
	private boolean checkThirteenOrphans() {
		int count = 0;
		for (var set : completeHand) {
			if (isTerminals(set) || isHonors(set[0])) {
				count += 1;
			} else {
				return false;
			}
		}
		if (count == 13) {
			return true;
		}
		return false;
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

	// 三槓子
	private boolean checkThreeKan() {
		int count = 0;
		for (var set : completeHand) {
			if (isKan(set)) {
				count += 1;
			}
		}
		if (count == 3) {
			han += 2;
			return true;
		}
		return false;
	}

	// 面前自摸
	private boolean checkTsumo() {
		if (hand.isTsumo() && !hand.isCalling()) {
			han += 1;
			return true;
		}
		return false;
	}

	// 流し満貫

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


	public int getMaxHan() {
		return maxHan;
	}

	public List<String> getYakuList() {
		return yakuList;
	}

	private boolean isAllTerminals(int[] set) {
		if (isTriplets(set) || set.length == 2) {
			return isTerminals(set);
		}
		return false;
	}

	private boolean isBamboo(int index) {
		return index >= 18 && index < 27;
	}

	private boolean isDragons(int index) {
		return index > 30;
	}

	private boolean isHonors(int index) {
		return index >= 27;
	}

	private boolean isHonorTiles(int index) {
		if (index == hand.getPrevalentWindIndex()) {
			return true;
		}
		if (index == hand.getSeatWindIndex()) {
			return true;
		}
		if (isDragons(index)) {
			return true;
		}
		return false;
	}

	private boolean isKan(int[] set) {
		if (set.length == 4) {
			return true;
		}
		return false;
	}

	private boolean isSameKind(int index1, int index2) {
		return Math.floor((double) index1 / 9) == Math.floor((double) index2 / 9);
	}

	private boolean isSameValue(int index1, int index2) {
		if (isHonors(index1) || isHonors(index2))
			return false;
		return index1 % 9 == index2 % 9;
	}

	private boolean isSequence(int[] set) {
		if (set.length != 3) {
			return false;
		}
		if (isSuits(set[0])) {
			if (set[0] == set[1] - 1 && set[1] == set[2] - 1) {
				if (isSameKind(set[0], set[2])) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isSuits(int index) {
		return index < 27;
	}

	// 四暗刻単騎
	// 純正九蓮宝燈
	// 国士無双十三面待ち

	private boolean isTerminals(int[] set) {
		if (isHonors(set[0]))
			return false;
		for (var index : set) {
			if (index % 9 == 0 || index % 9 == 8) {
				return true;
			}
		}
		return false;
	}

	private boolean isTriplets(int[] set) {
		if (set.length >= 3) {
			if (set[0] != set[1] || set[1] != set[2]) {
				return false;
			}
		}
		return true;
	}

	private boolean isWinds(int index) {
		return index >= 27 && index < 31;
	}

	public boolean isWinning() {
		return isWinning;
	}

	public void setWinning(boolean isWinning) {
		this.isWinning = isWinning;
	}

	public void setYakuList(List<String> yakuList) {
		this.yakuList = yakuList;
	}
}
