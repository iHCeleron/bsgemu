package com.google.code.BSGemu.net.chatProtocols;

import java.io.*;
import java.util.Vector;

import com.google.code.BSGemu.net.ChatPacket;
import com.google.code.BSGemu.net.Packet;
import com.google.code.BSGemu.net.XMLPacket;
import com.google.code.BSGemu.net.packets.chat.PacketAChatMessage;

public final class ChatNetHandler extends Thread{
	private InputStream input = null;
	private OutputStream output = null;
	private WriteThread writeThread = null;
	private ReadThread readThread;
	
	public ChatNetHandler(InputStream inputStream, OutputStream outputStream) {
		super();
		input = inputStream;
		output = outputStream;
		writeThread = new WriteThread(output);
		readThread = new ReadThread(input);
		this.start();
	}
	public void run(){
		for(;;){
			if(readThread.getNumUnproccessedPackets()>0){
				
						processPacket(readThread.getPacket());
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
			}
		}
	}
	private void processPacket(Packet packet) {
		if (packet != null)
			switch(new String(((ChatPacket)packet).getPacketId())){
			case "A":
				handleChatMessage((PacketAChatMessage)packet);
				break;
			default:
				System.out.println("Unsupported ChatMessage: "+packet.toString());
				break;
			}
			
	}
	
	private static void handleChatMessage(PacketAChatMessage packet) {
		System.out.println(packet.playerName+":"+packet.message);
	}
	
}
