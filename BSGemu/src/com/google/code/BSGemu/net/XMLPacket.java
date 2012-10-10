package com.google.code.BSGemu.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;


public class XMLPacket extends Packet {
	
	public String contents;

	public XMLPacket(){
		contents = "";
	}
	
	public XMLPacket(String xml){
		contents = xml;
	}
	
	@Override
	protected void writePacketData(ByteArrayOutputStream outputStream)
			throws IOException {
		writeString(contents,outputStream);
	}

	@Override
	protected void readPacketData(InputStream inputStream)
			throws IOException {
		contents  = readString(inputStream,'\0');
	}
	
	public static final void writePacket(XMLPacket packet,ByteArrayOutputStream outputStream) throws IOException{
			packet.writePacketData(outputStream);
			writeChar('\0',outputStream);
	}

	public static final Packet readPacket(InputStream readStream) throws IOException{
		Packet packet = new XMLPacket();
		packet.readPacketData(readStream);
		return packet;
	}

}
