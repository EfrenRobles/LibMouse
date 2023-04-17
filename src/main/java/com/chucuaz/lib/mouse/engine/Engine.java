package com.chucuaz.lib.mouse.engine;

import com.chucuaz.lib.mouse.utils.EngineDebug;

public class Engine extends EngineDebug {
	
	private static final String TAG = "Engine";

	public void connect(final String server_ip) {
		EngineThread.getInstance().setServerIp(server_ip);
		
		new Thread(new EngineThread()).start();
	}

	public boolean isConnected() {
		return EngineThread.getInstance().isConnected();
	}

	public void sendData(final String data) {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					EngineThread.getInstance().sendData(data);
				} catch (Exception e) {
					ERR(TAG, e.getMessage());
				}
			}
		});

		thread.start();
	}
	
	public void recieveData() {
		
	}
}
