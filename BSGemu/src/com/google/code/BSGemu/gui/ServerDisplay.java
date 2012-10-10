package com.google.code.BSGemu.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import com.google.code.BSGemu.util.*;

public class ServerDisplay extends JPanel implements Runnable{
	
	private int playerCount = 0;
	
	private int humanCount = 0;
	
	private int syntheticsCount = 0;
	
	private Counter p = null;
	
	private Counter h = null;
	
	private Counter c = null;
	
	private Counter npc = null;
	
	private Thread updateScreen = null;
	
	public ServerDisplay(Counter players, Counter humans, Counter synthetics){
		super();
		this.setDoubleBuffered(true);
		p = players;
		h = humans;
		c = synthetics;
	}

	@Override
	public void run() {
		for(;;){
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				return;
			}
			this.repaint();
		}
	}
	
	public void paint(Graphics g){
		if(updateScreen == null){
			updateScreen = new Thread(this);
			updateScreen.start();
		}
		
		int players = p.getValue();
		int humans = h.getValue();
		int synthetics = c.getValue();
		
		if(playerCount!=players){
			playerCount = players;
			drawPlayers((Graphics2D) g);
		}
		if(humanCount!=humans){
			humanCount = humans;
			drawHumans((Graphics2D) g);
		}
		if(syntheticsCount!=synthetics){
			syntheticsCount = synthetics;
			drawSynthetics((Graphics2D) g);
		}
		drawArea((Graphics2D) g,ServerOptions.area);
	}

	private void drawArea(Graphics2D g, Object area) {
		g.clearRect(0, 0, this.getWidth()-2, this.getHeight()-g.getFontMetrics().getHeight()-2);
		g.drawRect(0,0,this.getWidth()-2,this.getHeight()-g.getFontMetrics().getHeight()-2);
		
	}

	private void drawSynthetics(Graphics2D g) {
		String s = Integer.toString(playerCount);
		g.clearRect(this.getWidth()-g.getFontMetrics().stringWidth(s)-42, this.getHeight()-g.getFontMetrics().getHeight(),g.getFontMetrics().stringWidth(s), g.getFontMetrics().getHeight());
		g.drawString(s, this.getWidth()-g.getFontMetrics().stringWidth(s)-42, this.getHeight());
	}

	private void drawHumans(Graphics2D g) {
		String s = Integer.toString(humanCount);
		g.clearRect(this.getWidth()-g.getFontMetrics().stringWidth(s)-22, this.getHeight()-g.getFontMetrics().getHeight(),g.getFontMetrics().stringWidth(s), g.getFontMetrics().getHeight());
		g.drawString(s, this.getWidth()-g.getFontMetrics().stringWidth(s)-22, this.getHeight());
	}

	private void drawPlayers(Graphics2D g) {
		String s = Integer.toString(playerCount);
		g.clearRect(this.getWidth()-g.getFontMetrics().stringWidth(s)-2, this.getHeight()-g.getFontMetrics().getHeight(),g.getFontMetrics().stringWidth(s), g.getFontMetrics().getHeight());
		g.drawString(s, this.getWidth()-g.getFontMetrics().stringWidth(s)-2, this.getHeight());
	}
	

}
