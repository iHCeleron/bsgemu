package com.google.code.BSGemu.net;


import java.io.*;

import com.google.code.BSGemu.net.packets.chat.PacketAChatMessage;

public abstract class ChatPacket extends Packet{
	
	
	protected static final char PACKET_DELIMITER = '%';
	
	private static final int MAX_PACKET_TYPES = 1024;

	public abstract char[] getPacketId();
	
	@SuppressWarnings("unchecked")
	private static Class<? extends ChatPacket>[] packets = (Class<? extends ChatPacket>[]) new Class<?>[MAX_PACKET_TYPES];
	
	
	public static final void writePacket(ChatPacket packet, ByteArrayOutputStream outputStream) throws IOException{
		char[] packetid = packet.getPacketId();
		for(int i = 0; i< packetid.length;i++){
			packet.writeChar(packetid[i], outputStream);
		}
		packet.writeChar(PACKET_DELIMITER, outputStream);
		packet.writePacketData(outputStream);
		packet.writeChar('\0',outputStream);
	}
	
	protected final void writePacketDelimiter(ByteArrayOutputStream outputStream)throws IOException{
		writeChar(PACKET_DELIMITER, outputStream);
	}
	
	protected final void writeArray(Object[] array, ByteArrayOutputStream outputStream) throws IOException{
		String s = "";
		int pos = 0;
		do{
			s += array[pos++].toString();
			if(pos < array.length){
				s+="|";
			}
		}while(pos<array.length);
		writeString(s,outputStream);
	}
	
	protected final String[] readArray(ByteArrayInputStream inputStream) throws IOException{
		String[] s = readString(inputStream,PACKET_DELIMITER).split("\\|");
		return s;
	}

	public static final Packet readPacket(InputStream readStream) throws IOException{
		ChatPacket packet = getNewPacket(readStream);
		if(packet == null) return null;
		packet.readPacketData(readStream);
		return packet;
	}

	private static final ChatPacket getNewPacket(InputStream readStream) throws IOException{
		
		String packetid = readString(readStream,PACKET_DELIMITER);
		
		for(int i = 0; i<MAX_PACKET_TYPES;i++){
			try{
			ChatPacket p =packets[i].newInstance();
			if(new String(p.getPacketId()).equals(packetid)){
				return p;
			}
			}catch(Throwable t){
			}
		}
		System.out.println("Unrecognised Chat Packet: "+ packetid);
		char tempChar;
		do{
			tempChar = readChar(readStream);
		}while(tempChar != '\0');
		return null;
	}
	
	static{
		packets[0] = PacketAChatMessage.class;
		
	}
	
}
