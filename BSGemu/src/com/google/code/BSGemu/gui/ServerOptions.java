package com.google.code.BSGemu.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ServerOptions extends JPanel implements ActionListener {

	public static Object area = null;
	public JButton stop = null;

	public ServerOptions(Container c) {
		super();
		this.setLayout(new FlowLayout());
		this.setSize(400,c.getHeight());
		stop = new JButton("Stop");
		stop.setActionCommand("stop");
		stop.addActionListener(this);
		this.add(stop);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("stop")){
			System.exit(0);
		}
	}

}
