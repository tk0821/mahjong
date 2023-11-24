import static org.junit.Assert.*;

import org.junit.Test;

import tk0821.mahjong.WaitingHandChecker;

public class WaitingHandCheckerTest {

	@Test
	public void WaitingHandIsTrue() {
		assertEquals(true, WaitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p5p6p東 東 東 中 中 "));
		assertEquals(true, WaitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p5p6p東 東 東 白 中 "));
		assertEquals(true, WaitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p5p9p東 東 東 中 中 "));
	}
	
	@Test
	public void WaitingHandIsFalse() {
		assertEquals(false, WaitingHandChecker.isWaitingHand("1m1m2m2m3m3m4p5p7p東 東 東 中 白 "));
	}
}
