package com.grumpybear.chromeng.lib;


public class LibTextures {
	
	public static final int SIZE_X = 6;
	public static final int SIZE_Y = 103;
	
	public static final int SIZE_FULL_X = 5;
	public static final int SIZE_FULL_Y = 101;
	
	public static final int CONTAINER_SIZE_X = 79;
	public static final int CONTAINER_SIZE_Y = 110;
	
	public static final Pair RED_LOCATION = new Pair(8, 4);
	public static final Pair ORANGE_LOCATION = new Pair(18, 4);
	public static final Pair YELLOW_LOCATION = new Pair(28, 4);
	public static final Pair GREEN_LOCATION = new Pair(38, 4);
	public static final Pair BLUE_LOCATION = new Pair(48, 4);
	public static final Pair INDIGO_LOCATION = new Pair(58, 4);
	public static final Pair VIOLET_LOCATION = new Pair(68, 4);
	
	public static final Pair RED_FULL_LOCATION = new Pair(80, 5);
	public static final Pair ORANGE_FULL_LOCATION = new Pair(90, 5);
	public static final Pair YELLOW_FULL_LOCATION = new Pair(100, 5);
	public static final Pair GREEN_FULL_LOCATION = new Pair(110, 5);
	public static final Pair BLUE_FULL_LOCATION = new Pair(120, 5);
	public static final Pair INDIGO_FULL_LOCATION = new Pair(130, 5);
	public static final Pair VIOLET_FULL_LOCATION = new Pair(140, 5);
	
	public static final Pair[] COLOR_LOCATIONS = {
			RED_LOCATION,
			ORANGE_LOCATION,
			YELLOW_LOCATION,
			GREEN_LOCATION,
			BLUE_LOCATION,
			INDIGO_LOCATION,
			VIOLET_LOCATION
	};
	
	public static final Pair[] COLOR_FULL_LOCATIONS = {
			RED_FULL_LOCATION,
			ORANGE_FULL_LOCATION,
			YELLOW_FULL_LOCATION,
			GREEN_FULL_LOCATION,
			BLUE_FULL_LOCATION,
			INDIGO_FULL_LOCATION,
			VIOLET_FULL_LOCATION
	};
	
	
	public static class Pair {
		private final int textureX, textureY;
		
		public Pair(int x, int y) {
			textureX = x;
			textureY = y;
		}
		
		public int getX() {
			return textureX;
		}
		
		public int getY() {
			return textureY;
		}
	}
}