package no.communitydetection;

public class ModularityChange {

	private int otherCommunity;
	private double modularityChange;

	public ModularityChange(int otherCommunity, double modularityChange) {
		this.otherCommunity = otherCommunity;
		this.modularityChange = modularityChange;
	}
	
	public int otherCommunity() {
		return this.otherCommunity;
	}
	
	public double modularityChange() {
		return this.modularityChange;
	}
}
