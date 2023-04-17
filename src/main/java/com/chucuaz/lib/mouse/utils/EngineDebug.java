package com.chucuaz.lib.mouse.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by efren.robles on 8/31/2015.
 */
public abstract class EngineDebug {
	static private final boolean isDebug = true;

	private static Logger logger = LogManager.getLogger(EngineDebug.class);
	
	static public void DBG(String tag, String data) {
		if (isDebug) {
			logger.debug(tag +": " + data);
		}
	}

	static public void INFO(String tag, String data) {
		if (isDebug) {
			logger.info(tag +": " + data);
		}
	}

	static public void WARN(String tag, String data) {
		if (isDebug) {
			logger.warn(tag +": " + data);
		}
	}

	static public void ERR(String tag, String data) {
		if (isDebug) {
			logger.error(tag +": " + data);
		}
	}

	static public void ERR(String tag, String data, Object e) {
		if (isDebug) {
			logger.error(tag +": " + e);
		}
	}
}