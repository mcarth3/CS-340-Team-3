package shared.definitions;

import java.awt.Color;

public enum CatanColor
{
	RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN;
	
	private Color color;
	
	static{
		RED.color = new Color(227, 66, 52);
		ORANGE.color = new Color(255, 165, 0);
		YELLOW.color = new Color(253, 224, 105);
		BLUE.color = new Color(111, 183, 246);
		GREEN.color = new Color(109, 192, 102);
		PURPLE.color = new Color(157, 140, 212);
		PUCE.color = new Color(204, 136, 153);
		WHITE.color = new Color(223, 223, 223);
		BROWN.color = new Color(161, 143, 112);
	}
	
	public Color getJavaColor()
	{
		return color;
	}

	public static CatanColor toColor(String result){
		CatanColor color = CatanColor.RED; 
		if(result.equals("red")){
			color= CatanColor.RED;
		}else if(result.equals("orange")){
			color = CatanColor.ORANGE;
		}else if(result.equals("yellow")){
			color = CatanColor.YELLOW;
		}else if(result.equals("blue")){
			color = CatanColor.BLUE;
		}else if(result.equals("green")){
			color = CatanColor.GREEN;
		}else if(result.equals("purple")){
			color = CatanColor.PURPLE;
		}else if(result.equals("puce")){
			color = CatanColor.PUCE;
		}else if(result.equals("white")){
			color = CatanColor.WHITE;
		}else if(result.equals("brown")){
			color = CatanColor.BROWN;
		}
		return color;
	}
	
	

}

