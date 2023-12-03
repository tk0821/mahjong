import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import tk0821.mahjong.CompleteHand;
import tk0821.mahjong.Tile;
import tk0821.mahjong.WaitingHandChecker;
import tk0821.mahjong.WinningChecker;

public class WinningCheckerTest {

	@Test
	public void isWinningIsTrue() {

		WaitingHandChecker waitingHandChecker = new WaitingHandChecker();
		List<Tile> hand = waitingHandChecker.parseHand("2m3m3p4p5p5p6p7p4s5s6s8s8s");
		int[][] openHand = new int[4][];
		Tile tile = new Tile(Tile.CHARACTERS, 4);
		CompleteHand completeHand = new CompleteHand(hand, openHand, tile);
		completeHand.setTsumo(true);
		completeHand.setPrevalentWindIndex(27); // 東
		completeHand.setSeatWindIndex(27); // 東

		WinningChecker winningChecker = new WinningChecker(completeHand);

		assertEquals(true, winningChecker.isWinning());
	}

	@Test
	public void getMaxHanIsTrue() {

		WaitingHandChecker waitingHandChecker = new WaitingHandChecker();
		List<Tile> hand = waitingHandChecker.parseHand("2m3m3p4p5p5p6p7p4s5s6s8s8s");
		int[][] openHand = new int[4][];
		Tile tile = new Tile(Tile.CHARACTERS, 4);
		CompleteHand completeHand = new CompleteHand(hand, openHand, tile);
		completeHand.setTsumo(true);
		completeHand.setPrevalentWindIndex(27); // 東
		completeHand.setSeatWindIndex(27); // 東

		WinningChecker winningChecker = new WinningChecker(completeHand);

		assertEquals(3, winningChecker.getMaxHan());

	}
}
