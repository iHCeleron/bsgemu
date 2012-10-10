/**
 * 
 */
package com.google.code.BSGemu.net.binaryProtocols;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import com.google.code.BSGemu.net.BinaryPacket;
import com.google.code.BSGemu.net.ChatPacket;
import com.google.code.BSGemu.net.Packet;
import com.google.code.BSGemu.net.XMLPacket;

/**
 * @author ILOVEPIE
 *
 */
public class WriteThread extends Thread {
	
	private OutputStream sendStream = null;
	private Vector<Packet> sendQueue = new Vector<>();
	private ByteArrayOutputStream sendBuffer = new ByteArrayOutputStream();
	private int num = 0;
	public WriteThread(OutputStream stream){
		super();
		sendStream = stream;
		this.start();
	}
	public void run(){
		Packet p = null;
		for(;;){
			if(sendQueue.size()>0){
				for(int i = 0; i<sendQueue.size();i++){
					p = sendQueue.firstElement();
					if(p instanceof BinaryPacket){
						try {
							System.out.println("Sent");
							BinaryPacket.writePacket((BinaryPacket)p, sendBuffer);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					sendQueue.remove(p);
					synchronized(this){
						num--;
					}
				}
				try {
					sendStream.write(sendBuffer.toByteArray());
					sendBuffer = new ByteArrayOutputStream();
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
		synchronized(this){
			num++;
		}
	}
	
	public int getNumPacketsInQueue(){
		return num;
	}

}
