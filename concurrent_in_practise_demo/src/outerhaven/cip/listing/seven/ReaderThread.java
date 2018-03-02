package outerhaven.cip.listing.seven;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import outerhaven.cip.common.PhantomCode;

/**
 * Listing 7.11. Encapsulating Nonstandard Cancellation in a THRead by Overriding Interrupt.
 * @author threepwood
 *
 */
public class ReaderThread extends Thread {
	private final Socket socket;
	private final InputStream in;
	@PhantomCode
	private static final int BUFSZ=0;

	public ReaderThread(Socket socket) throws IOException {
		this.socket = socket;
		this.in = socket.getInputStream();
	}

	public void interrupt() {
		try {
			socket.close();
		} catch (IOException ignored) {
		} finally {
			super.interrupt();
		}
	}

	public void run() {
		try {
			byte[] buf = new byte[BUFSZ];
			while (true) {
				int count = in.read(buf);
				if (count < 0)
					break;
				else if (count > 0)
					processBuffer(buf, count);
			}
		} catch (IOException e) {
			/* Allow thread to exit */ }
	}
	@PhantomCode
	void processBuffer(byte[] buf, int count){
		
	}
}