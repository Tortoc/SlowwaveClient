package tortoc.de.SlowwaveClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class App {
	public static void main(String[] args) throws Exception {
		int xoff = 0;
		int yoff = 0;
		int threads = 1;
		int sleep = 0;
		int port = 4321;
		int threadPause = 1;
		int threadNumber = threads;
		boolean random = true;
		boolean stripes = false;
		boolean quadrants = false; // BEWARE!! MAKES threads x threads threads eg: 5 Threads -> 25 threads
		String host = "slowwave.poeschl.xyz";
		// String host = "94.45.232.143";
		String[][] pix = PictureAnalyzer.getPixels("/home/tortoc/Dokumente/Slowwave.png");
		String token = null;
		int usage = 0;
		long counter = 0;
		ArrayList<Quadrant> list = new ArrayList<Quadrant>();

		while (true) {
			Socket s = new Socket(host, port);

			PrintWriter writer = new PrintWriter(s.getOutputStream());
			InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
			BufferedReader reader = new BufferedReader(streamReader);

			if (token == null || usage == 0) {
				writer.write("TOKEN\n");
				writer.flush();

				String responseMessage = reader.readLine();
				System.out.println(responseMessage);
				token = responseMessage.split(" ")[1];
				usage = Integer.parseInt(responseMessage.split(" ")[2]);

			}
			if (usage > 0) {
				System.out.println("Start sending pixel");
				int part = pix.length / threadNumber;
				int start = part * 0;
				int end = start + part;
				for (int x = start; x < end; x++) {
					for (int y = 0; y < pix[x].length; y++) {
						String command = "PX " + (x + xoff) + " " + (y + yoff) + " " + pix[x][y] + " " + token + "\n";
						// System.out.println(command);
						writer.write(command);
						writer.flush();
						usage--;
						// String responseMessage = reader.readLine();
						// System.out.println(usage);
						// counter++;
						// System.out.println(counter);
						if (usage <= 0) {
							writer.write("TOKEN\n");
							writer.flush();

							String responseMessage = reader.readLine();
							token = responseMessage.split(" ")[1];
							while (token.contains("Invalid")) {
								responseMessage = reader.readLine();
								token = responseMessage.split(" ")[1];
							}
							// System.out.println(responseMessage + " : " + usage + " : " + counter);
							try {
								usage = Integer.parseInt(responseMessage.split(" ")[2]);
							} catch (Exception e) {
								// System.out.println(counter + " - " + e.getMessage());
								// e.printStackTrace(---);
								// break;
								Thread.sleep(500);
							}
							Thread.sleep(1);
						}
					}
				}
				// writer.close();
			}
			// System.out.println(usage);
			// writer.close();

		}
	}
}
