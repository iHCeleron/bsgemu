/**
 * 
 */
package com.google.code.BSGemu.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.code.BSGemu.net.binaryProtocols.ProtocolID;

/**
 * @author ILOVEPIE
 *
 */
public class BinaryPacket extends Packet {
	public byte[] bytes;
	public int packetLen;
	public ProtocolID id;
	
	private static final BinaryPacket getNewPacket(InputStream readStream) throws IOException{
		BinaryPacket packet = new BinaryPacket();
		packet.readPacketData(readStream);
		return packet;
	}
	public static final void writePacket(BinaryPacket packet, ByteArrayOutputStream output) throws IOException{
		packet.writePacketData(output);
	}
	@Override
	protected void writePacketData(ByteArrayOutputStream outputStream)
			throws IOException {
		packetLen = (int) (bytes.length+1);
		write((byte)((packetLen&0xFF00)>>8),outputStream);
		write((byte)(packetLen&0xFF),outputStream);
		write(id.id(),outputStream);
		write(bytes, outputStream);
	}

	@Override
	protected void readPacketData(InputStream inputStream)
			throws IOException {
		packetLen = (int)(((((short)(read(inputStream))&0xFF)<<8)|((short)(read(inputStream))&0xFF))-1);
		byte idNum = read(inputStream);
		switch(idNum){
		case 0:
			id = ProtocolID.Login;
			break;
		case 1:
			id = ProtocolID.Universe;
			break;
		case 2:
			id = ProtocolID.Game;
			break;
		case 3:
			id = ProtocolID.Sync;
			break;
		case 4:
			id = ProtocolID.Player;
			break;
		case 5:
			id = ProtocolID.Debug;
			break;
		case 6:
			id = ProtocolID.Catalogue;
			break;
		case 7:
			id = ProtocolID.Ranking;
			break;
		case 8:
			id = ProtocolID.Story;
			break;
		case 9:
			id = ProtocolID.Scene;
			break;
		case 10:
			id = ProtocolID.Room;
			break;
		case 11:
			id = ProtocolID.Community;
			break;
		case 12:
			id = ProtocolID.Shop;
			break;
		case 13:
			id = ProtocolID.Setting;
			break;
		case 14:
			id = ProtocolID.Ship;
			break;
		case 15:
			id = ProtocolID.Dialog;
			break;
		case 16:
			id = ProtocolID.Market;
			break;
		case 17:
			id = ProtocolID.Notification;
			break;
		case 18:
			id = ProtocolID.Subscribe;
			break;
		case 19:
			id = ProtocolID.Feedback;
			break;
		case 20:
			id = ProtocolID.Tournament;
		}
		System.out.println(id.name());
		bytes = read(inputStream,packetLen);
	}
	public static Packet readPacket(ByteArrayInputStream readStream) throws IOException {
		// TODO Auto-generated method stub
		return getNewPacket(readStream);
	}

}
