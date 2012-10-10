package com.google.code.BSGemu.net.chatProtocols;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import com.google.code.BSGemu.net.ChatPacket;
import com.google.code.BSGemu.net.Packet;
import com.google.code.BSGemu.net.XMLPacket;



public final class WriteThread extends Thread {
	
	private OutputStream sendStream = null;
	private Vector<Packet> sendQueue = new Vector<>();
	private ByteArrayOutputStream sendBuffer = new ByteArrayOutputStream();
	public WriteThread(OutputStream output) {
		super();
		sendStream = output;
		this.start();
	}

	public void run(){
		Packet p = null;
		for(;;){
			if(sendQueue.size()>0){
				for(int i = 0; i<sendQueue.size();i++){
					p = sendQueue.firstElement();
					if(p instanceof ChatPacket){
						try {
							ChatPacket.writePacket((ChatPacket)p, sendBuffer);
						} catch (IOException e) {
							System.err.println(e.getLocalizedMessage());
						}
					}else if(p instanceof XMLPacket){
						try {
							XMLPacket.writePacket((XMLPacket)p, sendBuffer);
						} catch (IOException e) {
							System.err.println(e.getLocalizedMessage());
						}
					}
					sendQueue.remove(p);
				}
				try {
					sendStream.write(sendBuffer.toByteArray());
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
				}
			}else{
				try{
					Thread.sleep(10);
				}catch(InterruptedException e){
				}
			}
		}
	}
	
	public void addToSendQueue(Packet packet){
		if(packet != null)
		sendQueue.add(packet);
	}
	
	public int getNumPacketsInQueue(){
		return sendQueue.size();
	}

}
