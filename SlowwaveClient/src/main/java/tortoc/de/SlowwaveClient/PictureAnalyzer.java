package tortoc.de.SlowwaveClient;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class PictureAnalyzer {
	public static String[][] getPixels(String filepath) throws Exception {
		File file = new File(filepath);
		BufferedImage image = ImageIO.read(file);
		int w = image.getWidth();
		int h = image.getHeight();
		String[][] pixelz = new String[w][h];
		System.out.println("Picture width, height: " + w + ", " + h);
		// StringBuilder b = new StringBuilder();
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				// System.out.println("x,y: " + j + ", " + i);
				int pixel = image.getRGB(j, i);
				int red = (pixel & 0x00ff0000) >> 16;
				int green = (pixel & 0x0000ff00) >> 8;
				int blue = pixel & 0x000000ff;
				// System.out.println(red + " - " + green + " - " + blue);
				String hexred = Integer.toHexString(red);
				while (hexred.length() < 2) {
					hexred = "0" + hexred;
				}
				String hexgreen = Integer.toHexString(green);
				while (hexgreen.length() < 2) {
					hexgreen = "0" + hexgreen;
				}
				String hexblue = Integer.toHexString(blue);
				while (hexblue.length() < 2) {
					hexblue = "0" + hexblue;
				}
				String pix = hexred + hexgreen + hexblue;
				// System.out.println(pix);
				pixelz[j][i] = pix;
			}
		}
		// System.out.println(b.toString());

		return pixelz;
	}
}
