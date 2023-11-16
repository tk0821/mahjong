package tk0821.mahjong;

import java.util.Collections;
import java.util.List;

public class Player {
	
	private List<Tile> hand;
	private Tile tile;
	private boolean hasTile;

	public Player() {
		
	}
	
	public void setHasTile(boolean hasTile) {
		this.hasTile = hasTile;
	}
	
	public boolean getHasTile() {
		return hasTile;
	}
	
	public void setHand(List<Tile> hand) {
		this.hand = hand;
	}
	
	public List<Tile> getHand() {
		return hand;
	}
	
	public void sortHand() {
		Collections.sort(hand);
	}
	
	public void setTile(Tile tile) {
		hasTile = true;
		this.tile = tile;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public Tile discard(int index) {
		hasTile = false;
		if (index >= hand.size()) {
			return tile;
		}
		hand.add(tile);
		return hand.remove(index);
	}
}
