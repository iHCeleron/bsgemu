package com.google.code.BSGemu.net.chatProtocols;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.bouncycastle.*;
import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.engines.*;
import org.bouncycastle.crypto.modes.*;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.io.*;

import com.google.code.BSGemu.net.BinaryPacket;
import com.google.code.BSGemu.net.ChatPacket;
import com.google.code.BSGemu.net.Packet;
import com.google.code.BSGemu.net.XMLPacket;


public class ReadThread extends Thread {

	private InputStream readStream = null;
	private ByteArrayInputStream readBuffer = null;
	private Vector<Packet> readQueue = new Vector<>();
	private int num = 0;
	public ReadThread(InputStream input) {
		super();
		readStream = input;
		this.start();
	}
	public void run(){
		Packet p = null;
		new Thread(){
			public void run(){
				for(;;){
					try {
						if(readBuffer==null)continue;
						synchronized(readBuffer){
						if(readBuffer.available()>0){
							
							Packet p1=null;
								
								p1 = ChatPacket.readPacket(readBuffer);
								num++;
								readQueue.add(p1);
							
					
						}else{
							try {
								Thread.sleep(2);
							} catch (InterruptedException e) {
							}
						}
						}
					} catch (Exception e) {
						try {
							Thread.sleep(2);
						} catch (InterruptedException e1) {
						}
					}
				}
			}
		}.start();
		for(;;){
			try{
				if(readStream.available()>0){
					if(readBuffer != null){
							synchronized(readBuffer){
							byte[] temp = new byte[readBuffer.available()];
							readBuffer.read(temp);
							byte[] temp2 = new byte[readStream.available()+temp.length];
							for(int i = 0; i<temp.length;i++){
								temp2[i] = temp[i];
							}
							readStream.read(temp2, temp.length, readStream.available());
							readBuffer = new ByteArrayInputStream(temp2);
							}
						}else{
							byte[] temp = new byte[readStream.available()];
							readStream.read(temp);
							readBuffer = new ByteArrayInputStream(temp);
						
					}
					Thread.sleep(2);
				}
			}catch(Exception e){
				System.out.println(e.getLocalizedMessage());
			}
		}
		
	}
	public int getNumUnproccessedPackets() {
		return num;
	}

	public Packet getPacket() {
		Packet p;
		try{
		p = readQueue.get(0);
		}catch(Exception e){
			return null;
		}
		readQueue.remove(p);
		if(readBuffer!=null){
		synchronized(readBuffer){
			num--;
		}
		}else{
			num--;
		}
		return p;
	}

}
