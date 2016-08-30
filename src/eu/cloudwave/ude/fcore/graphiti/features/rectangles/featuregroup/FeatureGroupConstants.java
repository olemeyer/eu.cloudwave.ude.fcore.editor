package eu.cloudwave.ude.fcore.graphiti.features.rectangles.featuregroup;

public enum FeatureGroupConstants {
	FEATURE_GROUP_WIDTH_DIFF(8),
	FEATURE_GROUP_Y(FEATURE_GROUP_WIDTH_DIFF.getValue() / 2);
	
	private int value;
	
	private FeatureGroupConstants(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
