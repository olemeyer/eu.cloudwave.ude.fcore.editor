package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute;

public enum AttributeConstants {
	ATTRIBUTE_HEIGHT(20),
	ATTRIBUTE_WIDTH_DIFF(8),
	ATTRIBUTE_X(ATTRIBUTE_WIDTH_DIFF.getValue() / 2),
	ATTRIBUTE_Y(ATTRIBUTE_HEIGHT.getValue()),
	ATTRIBUTE_Y_ADD(3);
	
	private int value;
	
	private AttributeConstants(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
