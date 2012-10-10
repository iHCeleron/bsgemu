/**
 * 
 */
package com.google.code.BSGemu.net.binaryProtocols;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import com.google.code.BSGemu.net.BinaryPacket;
import com.google.code.BSGemu.net.ChatPacket;
import com.google.code.BSGemu.net.Packet;
import com.google.code.BSGemu.net.XMLPacket;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * @author ILOVEPIE
 *
 */
public class ReadThread extends Thread {
	private InputStream readStream = null;
	private ByteArrayInputStream readBuffer = null;
	private Vector<Packet> readQueue = new Vector<>();
	private int num = 0;
	public ReadThread(InputStream stream){
		super();
		readStream = stream;
		this.start();
	}
	public void run(){
		Packet p = null;
		new Thread(){
			public void run(){
				for(;;){
					try{
						if(readBuffer==null)continue;
						synchronized(readBuffer){
							if(readBuffer.available()>0){
								readQueue.add(BinaryPacket.readPacket(readBuffer));
								num++;
								Thread.sleep(2);
							}else{
								Thread.sleep(10);
							}
						}
					}catch(Exception e){
						try {
							Thread.sleep(10);
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
