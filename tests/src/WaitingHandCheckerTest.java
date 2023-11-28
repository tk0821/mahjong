import static org.junit.Assert.*;

import org.junit.Test;

import tk0821.mahjong.WaitingHandChecker;

public class WaitingHandCheckerTest {

	// m: 萬豆 characters
	// p: 筒子 circles
	// s: 索子 bamboos
	// 字牌は漢字で表し、後ろに半角スペース
	
	//@Test
	public void waitingHandIsTrue() {
		WaitingHandChecker waitingHandChecker = new WaitingHandChecker();
		
		assertEquals(true, waitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p5p6p東 東 東 中 中 "));
		assertEquals(true, waitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p5p6p東 東 東 白 中 "));
		assertEquals(true, waitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p5p9p東 東 東 中 中 "));
		assertEquals(true, waitingHandChecker.isWaitingHand("2m3m4m5m6m9s東 東 東 南 南 中 中 中 "));
		assertEquals(true, waitingHandChecker.isWaitingHand("1m2m3m4m5m6m7m5p東 東 東 中 中 中 "));
		
		assertEquals(true, waitingHandChecker.isWaitingHand("1m1m2m2m3m3m4m4m6m東 東 中 中 白 "));
		assertEquals(true, waitingHandChecker.isWaitingHand("1m1m2p2p3s3s4s4s6s東 東 中 中 白 "));
		
		assertEquals(true, waitingHandChecker.isWaitingHand("1m9m1p9p1s9s東 南 西 北 白 發 中 "));
		assertEquals(true, waitingHandChecker.isWaitingHand("9m9m1p9p1s9s東 南 西 北 白 發 中 "));
		assertEquals(true, waitingHandChecker.isWaitingHand("1m1m1p9p1s9s東 東 南 西 北 白 發 中 "));
		assertEquals(true, waitingHandChecker.isWaitingHand("1m9m1p9p1s9s東 南 西 北 白 發 中 中 "));
		assertEquals(true, waitingHandChecker.isWaitingHand("1m9m1p9p1s9s9s東 南 北 白 發 中 中 "));
	}
	
	//@Test
	public void waitingHandIsFalse() {
		WaitingHandChecker waitingHandChecker = new WaitingHandChecker();
		
		assertEquals(false, waitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p5p7p東 東 東 中 白 "));
		
		assertEquals(false, waitingHandChecker.isWaitingHand("1m1m9m1p9p1s9s9s東 南 北 白 發 發 "));
	}
	
	@Test
	public void parseWinningTileArrayIsTrue() {
		WaitingHandChecker waitingHandChecker = new WaitingHandChecker();
		
		waitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p5p6p東 東 東 中 ");
		assertEquals("中 ", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p5p東 東 東 中 中 ");
		assertEquals("3p6p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p6p東 東 東 中 中 ");
		assertEquals("5p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m1m2m2m3m3m1p2p東 東 東 中 中 ");
		assertEquals("3p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m1m2m2m3m3m8p9p東 東 東 中 中 ");
		assertEquals("7p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m1m2m2m3m3m2p3p4p5p6p中 中 ");
		assertEquals("1p4p7p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m1m2m2m3m3m3p4p4p4p5p中 中 ");
		assertEquals("4p中 ", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m7m8m9m2p3p3p3p");
		assertEquals("1p2p4p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m7m8m9m3p3p3p5p");
		assertEquals("4p5p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m7m8m9m8p9p9p9p");
		assertEquals("7p8p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m7m8m9m2p3p4p5p");
		assertEquals("2p5p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m2p3p4p5p6p7p8p");
		assertEquals("2p5p8p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m2p3p4p5p5p6p7p");
		assertEquals("2p5p8p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m4p5p6p7p7p7p8p");
		assertEquals("3p6p8p9p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m3p3p3p4p5p9p9p");
		assertEquals("3p6p9p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m3p4p5p5p6p6p6p");
		assertEquals("2p4p5p7p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m3p4p4p5p6p6p6p");
		assertEquals("2p4p5p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m4p4p4p5p6p7p8p");
		assertEquals("3p5p6p8p9p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m2m3m4m5m6m2p2p2p3p4p5p7p");
		assertEquals("6p7p", waitingHandChecker.parseWinningTileArray());
		
		waitingHandChecker.isWaitingHand("1m1m1m2m3m4m5m6m7m8m9m9m9m");
		assertEquals("1m2m3m4m5m6m7m8m9m", waitingHandChecker.parseWinningTileArray());
	}
}
