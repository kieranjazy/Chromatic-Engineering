package com.grumpybear.chromeng.util;

import com.grumpybear.chromeng.chroma.EnumColour;
import com.grumpybear.chromeng.lib.LibTextures;
import com.grumpybear.chromeng.lib.LibTextures.Pair;

public class EnumColourUtil {

	
	public static EnumColour byteToColour(byte i) {
		switch(i) {
			case 1:		return EnumColour.RED;
			case 2:		return EnumColour.ORANGE;
			case 3: 	return EnumColour.YELLOW;
			case 4: 	return EnumColour.GREEN;
			case 5: 	return EnumColour.BLUE;
			case 6: 	return EnumColour.INDIGO;
			case 7: 	return EnumColour.VIOLET;
		}
		
		return null;
	}
	
	public static byte colourToByte(EnumColour colour) {
		switch(colour) {
			case RED:		return 1;
			case ORANGE:	return 2;
			case YELLOW:	return 3;
			case GREEN:		return 4;
			case BLUE:		return 5;
			case INDIGO:	return 6;
			case VIOLET: 	return 7;
		}
		
		return 0;
	}
	
	public static String colourToString(EnumColour colour) {
		switch(colour) {
			case RED:		return "red";
			case ORANGE:	return "orange";
			case YELLOW:	return "yellow";
			case GREEN:		return "green";
			case BLUE:		return "blue";
			case INDIGO:	return "indigo";
			case VIOLET: 	return "violet";
		}
	
		return null;
	}
	
	public static EnumColour pairToColour(Pair pair) {
		if (pair == LibTextures.RED_FULL_LOCATION)
			return EnumColour.RED;
		
		if (pair == LibTextures.ORANGE_FULL_LOCATION)
			return EnumColour.ORANGE;
		
		if (pair == LibTextures.YELLOW_FULL_LOCATION)
			return EnumColour.YELLOW;
		
		if (pair == LibTextures.BLUE_FULL_LOCATION)
			return EnumColour.BLUE;
		
		if (pair == LibTextures.INDIGO_FULL_LOCATION)
			return EnumColour.INDIGO;
		
		if (pair == LibTextures.VIOLET_FULL_LOCATION)
			return EnumColour.VIOLET;
		
		return EnumColour.BLUE;
	}
}
