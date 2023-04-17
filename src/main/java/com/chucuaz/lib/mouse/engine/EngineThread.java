package com.chucuaz.lib.mouse.engine;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.chucuaz.lib.mouse.utils.EngineDebug;
import com.chucuaz.lib.mouse.utils.Settings;

public class EngineThread extends EngineDebug implements Runnable {
	
	private static final EngineThread S_INSTANCE = new EngineThread();
	private final String TAG = "Single";
	private String server_ip = "";
	private InetSocketAddress socketAddress;
	private EngineAES aes = new EngineAES(Settings.ENCRYPTION_KEY);
	private boolean l_ready = false;
	private Socket socket = null;

	@Override
	public void run() {
		S_INSTANCE.socketAddress = new InetSocketAddress(S_INSTANCE.server_ip, Settings.SERVER_PORT);
		initSocket(S_INSTANCE.socketAddress);
	}

	protected static EngineThread getInstance() {
		return S_INSTANCE;
	}

	protected void setServerIp(String ip) {
		if (!ip.isEmpty()) {
			S_INSTANCE.server_ip = ip;
		}
	}

	protected boolean isConnected() {
		return S_INSTANCE.l_ready;
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Connection
	// -----------------------------------------------------------------------------------------------------------------
	public void initSocket(final InetSocketAddress scatAddress) {

		if (S_INSTANCE.l_ready) {
			S_INSTANCE.l_ready = false;
			try {
				S_INSTANCE.socket.close();
				WARN("ClientThread", "connection is closed thank you ");
			} catch (Exception e) {
				ERR("ClientThread", "connection problem 2:" + e.toString());
			}
			sleep(200);
		}

		INFO("ClientThread", " --- begin initSocket(InetSocketAddress, int); --- ");

		try {
			S_INSTANCE.socket = new Socket();
			// WARN(TAG, " --- initSocket step 1 --- ");
			S_INSTANCE.socket.connect(scatAddress, Settings.SERVER_TIME_OUT);

			WARN("ClientThread", " --- initSocket() --> success connection <--- ");
			System.setProperty("http.keepAlive", "false");
			S_INSTANCE.l_ready = true;
		} catch (Throwable e) {
			ERR("ClientThread", "connection problem: " + e.toString());
			S_INSTANCE.l_ready = false;
			try {
				S_INSTANCE.socket.close();
				WARN("ClientThread", "connection is closed thank you ");
			} catch (Exception e1) {
				ERR("ClientThread", "connection problem 2:" + e1.toString());
			}
		}

	}
	// Connection
	// -----------------------------------------------------------------------------------------------------------------

	// send data
	// ------------------------------------------------------------------------------------------------------------------
	public void sendData(String data) {
		if (S_INSTANCE.l_ready) {
			try {
				Thread.sleep(10);
				// INFO("sendData", "Send data: " + s_instance.data);
				data = aes.encryptAsBase64(data).trim();

				PrintWriter out = new PrintWriter(
						new BufferedWriter(new OutputStreamWriter(S_INSTANCE.socket.getOutputStream())), true);

				if (out != null) {
					out.println(data);
				}

				// DBG("sendData", "Send data: " + data);

			} catch (Exception e) {
				ERR("sendData", "sendData fail 3 e: " + e.getMessage());
				S_INSTANCE.l_ready = false;
				e.printStackTrace();
			}
		}
	}
	// send data
	// ------------------------------------------------------------------------------------------------------------------
}
