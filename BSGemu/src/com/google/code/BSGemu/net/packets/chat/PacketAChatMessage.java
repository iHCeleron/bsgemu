package com.google.code.BSGemu.net.packets.chat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.code.BSGemu.net.ChatPacket;

public class PacketAChatMessage extends ChatPacket {
	private static final char[] id = new char[]{'a'};
	public int roomID;
	public String message;
	public String playerName;
	
	public PacketAChatMessage(){
		roomID=0;
		message = "";
		playerName = "";
	}
	
	@Override
	public char[] getPacketId() {
		return id;
	}

	@Override
	protected void writePacketData(ByteArrayOutputStream outputStream)
			throws IOException {
		writeInt(roomID,outputStream);
		writePacketDelimiter(outputStream);
		writeString(playerName,outputStream);
		writePacketDelimiter(outputStream);
		writeString(message,outputStream);
		writeChar('#', outputStream);
	}

	@Override
	protected void readPacketData(InputStream inputStream)
			throws IOException {
		roomID = readInt(inputStream,PACKET_DELIMITER);
		message = readString(inputStream,'@');
		readChar(inputStream);
		
	}

}
