package tortoc.de.SlowwaveClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Spammer implements Runnable {
	private Thread t;
	private int name;
	private String[][] pic;
	private int xoff;
	private int yoff;
	private String host;
	private int port;
	private int sleep;
	private boolean random;
	private boolean stripes;
	private boolean quadrants;
	private int threadNumber;
	private ArrayList<Quadrant> list;
	public String token;
	private Socket s;

	Spammer(int n, String[][] pix, int xof, int yof, String ho, int p, int sl, boolean r, int tn, boolean st, boolean q,
			ArrayList<Quadrant> l) throws Exception {
		name = n;
		pic = pix;
		xoff = xof;
		yoff = yof;
		host = ho;
		sleep = sl;
		port = p;
		random = r;
		threadNumber = tn;
		stripes = st;
		quadrants = q;
		list = l;
		token = null;
		Socket s = new Socket(host, port);
		System.out.println("Creating Spammer" + name);
	}

	@Override
	public void run() {
		try {
			Socket s = new Socket(host, port);

			PrintWriter writer = new PrintWriter(s.getOutputStream());
			if (token == null) {
				getToken();
			}
			InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
			BufferedReader reader = new BufferedReader(streamReader);

			String responseMessage = reader.readLine();
			System.out.println(responseMessage);

		} catch (Exception e) {

		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "" + name);
			t.start();
		}
	}

	public void getToken() throws Exception {

		PrintWriter writer = new PrintWriter(this.s.getOutputStream());
		if (token == null) {
			writer.write("TOKEN");
			writer.flush();
		}
		InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
		BufferedReader reader = new BufferedReader(streamReader);

		String responseMessage = reader.readLine();
		String[] a = responseMessage.split(" ");
		this.token = a[1];
		System.out.println(responseMessage);
	}
}
