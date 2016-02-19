package kipperorigin.armamentseffects.resources;

import org.bukkit.Color;

public class AE_CheckFireworkColor {

	public Color getFireworkColorByString(String string) {
		Color color;
		if (string.equalsIgnoreCase("aqua")) {
			color = Color.AQUA;
		} else if (string.equalsIgnoreCase("black")) {
			color = Color.BLACK;
		} else if (string.equalsIgnoreCase("blue")) {
			color = Color.BLUE;
		} else if (string.equalsIgnoreCase("fuchsia") || string.equalsIgnoreCase("pink")) {
			color = Color.FUCHSIA;
		} else if (string.equalsIgnoreCase("gray") || string.equalsIgnoreCase("gray")) {
			color = Color.GRAY;
		} else if (string.equalsIgnoreCase("green")) {
			color = Color.GREEN;
		} else if (string.equalsIgnoreCase("lime")) {
			color = Color.LIME;
		} else if (string.equalsIgnoreCase("maroon")) {
			color = Color.MAROON;
		} else if (string.equalsIgnoreCase("navy")) {
			color = Color.NAVY;
		} else if (string.equalsIgnoreCase("olive")) {
			color = Color.OLIVE;
		} else if (string.equalsIgnoreCase("orange")) {
			color = Color.ORANGE;
		} else if (string.equalsIgnoreCase("purple") || string.equalsIgnoreCase("violet")) {
			color = Color.PURPLE;
		} else if (string.equalsIgnoreCase("red")) {
			color = Color.RED;
		} else if (string.equalsIgnoreCase("silver")) {
			color = Color.SILVER;
		} else if (string.equalsIgnoreCase("teal")) {
			color = Color.TEAL;
		} else if (string.equalsIgnoreCase("white")) {
			color = Color.WHITE;
		} else if (string.equalsIgnoreCase("yellow")) {
			color = Color.YELLOW;
		} else {
			return null;
		}
		final Color finalColor = color;
		return finalColor;
	}	
}
