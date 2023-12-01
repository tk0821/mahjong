package tk0821.mahjong;

import java.util.List;

public class CompleteHand {

	private List<Tile> hand;
	private Tile winningTile;
	
	private int prevalentWindIndex;
	private int seatWindIndex;
	private boolean isTsumo;
	private boolean isCalling;
	private boolean isRiichi;
	private boolean isDoubleRiichi;
	private boolean isIppatsu;
	private boolean isKan;
	private boolean isLastTile;
	
	public CompleteHand(List<Tile> hand, Tile winningTile) {
		setHand(hand);
		setWinningTile(winningTile);
	}

	public List<Tile> getHand() {
		return hand;
	}
	public void setHand(List<Tile> hand) {
		this.hand = hand;
	}	
	public Tile getWinningTile() {
		return winningTile;
	}
	public void setWinningTile(Tile winningTile) {
		this.winningTile = winningTile;
	}
	public int getPrevalentWindIndex() {
		return prevalentWindIndex;
	}
	public void setPrevalentWindIndex(int prevalentWindIndex) {
		this.prevalentWindIndex = prevalentWindIndex;
	}
	public int getSeatWindIndex() {
		return seatWindIndex;
	}
	public void setSeatWindIndex(int seatWindIndex) {
		this.seatWindIndex = seatWindIndex;
	}
	public boolean isTsumo() {
		return isTsumo;
	}
	public void setTsumo(boolean isTsumo) {
		this.isTsumo = isTsumo;
	}
	public boolean isCalling() {
		return isCalling;
	}
	public void setCalling(boolean isCalling) {
		this.isCalling = isCalling;
	}
	public boolean isRiichi() {
		return isRiichi;
	}
	public void setRiichi(boolean isRiichi) {
		this.isRiichi = isRiichi;
	}
	public boolean isDoubleRiichi() {
		return isDoubleRiichi;
	}
	public void setDoubleRiichi(boolean isDoubleRiichi) {
		this.isDoubleRiichi = isDoubleRiichi;
	}
	public boolean isIppatsu() {
		return isIppatsu;
	}
	public void setIppatsu(boolean isIppatsu) {
		this.isIppatsu = isIppatsu;
	}
	public boolean isKan() {
		return isKan;
	}
	public void setKan(boolean isKan) {
		this.isKan = isKan;
	}
	public boolean isLastTile() {
		return isLastTile;
	}
	public void setLastTile(boolean isLastTile) {
		this.isLastTile = isLastTile;
	}
	
	
}
