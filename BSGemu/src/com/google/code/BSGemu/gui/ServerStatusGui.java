package com.google.code.BSGemu.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import com.google.code.BSGemu.util.Counter;

public class ServerStatusGui extends JFrame{
	
	private Container root = null;
	private Counter playerCounter = null;
	private Counter humanCounter = null;
	private Counter syntheticsCounter = null;
	private ServerDisplay display = null;
	private ServerOptions options = null;
	public ServerStatusGui(String servername,String ip,int port){
		super("BSGEmu - " + servername + " | " + ip + ":" + port);
		this.setSize(900,600);
		root = this.getRootPane();
		playerCounter = new Counter(0);
		humanCounter = new Counter(0);
		syntheticsCounter = new Counter(0);
		display = new ServerDisplay(playerCounter,humanCounter,syntheticsCounter);
		this.add(display,BorderLayout.CENTER);
		options = new ServerOptions(this);
		this.add(options,BorderLayout.EAST);
	}

}
