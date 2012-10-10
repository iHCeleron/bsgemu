package com.google.code.BSGemu;


import javax.swing.JFrame;

import com.google.code.BSGemu.gui.ServerStatusGui;
import com.google.code.BSGemu.net.NetManager;

public final class BSGemu {
	
	/// THIS MUST BE CHANGED IF BSG UPDATES
	public static final short ServerVersion = 0x11E2;
	
	/**
	 * Starts the server up
	 * @param args
	 */
	public static void main(String[] args) {
		NetManager nm = new NetManager();
		JFrame gui = new ServerStatusGui("test", "127.0.0.1", 1337);
		gui.setVisible(true);
		gui.repaint();
	}

}
