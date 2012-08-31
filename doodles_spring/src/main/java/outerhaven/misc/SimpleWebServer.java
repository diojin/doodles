package outerhaven.misc;

import java.io.*;
import java.net.*;

class SimpleWebServer {
	public static void main(String[] args) {
		try {
			String str;
			ServerSocket ss = new ServerSocket(8888);
			Socket s = ss.accept();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			while ((str = dis.readLine()) != null)
				System.out.println(str);
		} catch (Exception e) {
		}
	}
}