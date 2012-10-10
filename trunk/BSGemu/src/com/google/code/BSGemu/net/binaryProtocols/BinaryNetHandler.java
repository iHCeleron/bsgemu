/**
 * 
 */
package com.google.code.BSGemu.net.binaryProtocols;

import java.io.InputStream;
import java.io.OutputStream;

import com.google.code.BSGemu.BSGemu;
import com.google.code.BSGemu.net.BinaryPacket;
import com.google.code.BSGemu.net.Packet;




/**
 * @author ILOVEPIE
 * 
 */
public class BinaryNetHandler extends Thread {

	private InputStream input = null;
	private OutputStream output = null;
	private WriteThread writeThread = null;
	private ReadThread readThread = null;

	public BinaryNetHandler(InputStream inputStream, OutputStream outputStream) {
		super();
		input = inputStream;
		output = outputStream;
		writeThread = new WriteThread(output);
		readThread = new ReadThread(input);
		this.start();
	}

	public void run() {
		sendHello();
		
		for (;;) {
			if (readThread.getNumUnproccessedPackets() > 0) {
						processPacket(readThread.getPacket());
				
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
			}
		}
	}

	private void sendHello() {
		BinaryPacket helloPacket = new BinaryPacket();
		helloPacket.id = ProtocolID.Login;
		helloPacket.bytes = new byte[2];
		writeThread.addToSendQueue(helloPacket);
	}

	private void processPacket(Packet p) {
		if (p==null)return;
		BinaryPacket packet = (BinaryPacket) p;
		switch (packet.id) {
		case Login:
			handleLogin(packet);
			break;
		case Universe:
			handleUniverse(packet);
			break;
		case Game:
			handleGame(packet);
			break;
		case Sync:
			handleSync(packet);
			break;
		case Player:
			handlePlayer(packet);
			break;
		case Debug:
			handleDebug(packet);
			break;
		default:
			System.out.println("Unsupported Packet ID: "+packet.id);
			break;
		}

	}

	private void handleDebug(BinaryPacket packet) {
		System.out.println("Debug");
	}

	private void handlePlayer(BinaryPacket packet) {
		System.out.println("Player");
	}

	private void handleSync(BinaryPacket packet) {
		System.out.println("Sync");
	}

	private void handleGame(BinaryPacket packet) {
		System.out.println("Game");
	}

	private void handleUniverse(BinaryPacket packet) {
		System.out.println("Universe");
	}

	private void handleLogin(BinaryPacket packet) {
		// TODO Auto-generated method stub
		int messageType = cSharpShort(0,packet.bytes);
		switch (messageType) {
		case 5:/// Echo Packet (i assume used to test the server to see if it is online)
			System.out.println("echo");
			BinaryPacket returnPacketEcho = new BinaryPacket();
			returnPacketEcho.bytes = packet.bytes;
			returnPacketEcho.id = ProtocolID.Login;
			this.writeThread.addToSendQueue(returnPacketEcho);
			return;
		case 1:/// Login Query Init Packet
			System.out.println("init");
			BinaryPacket returnPacketInit = new BinaryPacket();
			returnPacketInit.bytes = new byte[] { (byte) 0, (byte) 1,
					(byte) (BSGemu.ServerVersion & 0xFF),
					(byte) (BSGemu.ServerVersion >>> 8) };
			returnPacketInit.id = ProtocolID.Login;
			this.writeThread.addToSendQueue(returnPacketInit);
			return;
		default:/// unrecognized packet results in error
			System.out.println("err");
			System.out.println("Unknown Login Packet id: " + messageType);
			BinaryPacket returnPacketUnk = new BinaryPacket();
			returnPacketUnk.bytes = new byte[] { (byte) 0, (byte) 2, (byte) 0 };
			returnPacketUnk.id = ProtocolID.Login;
			this.writeThread.addToSendQueue(returnPacketUnk);
			return;
		}
	}
	
	private int cSharpShort(int offset,byte[] data){
		return (int) (((data[1+offset] << 8)&0xFF00) | (data[0+offset]&0x00FF));
	}

}
