/**
 * 
 */
package com.google.code.BSGemu.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Vector;

import com.google.code.BSGemu.net.binaryProtocols.BinaryNetHandler;
import com.google.code.BSGemu.net.chatProtocols.ChatNetHandler;

/**
 * @author ILOVEPIE
 *
 */
public final class NetManager extends Thread {
	
	ServerSocket binarySocket = null;
	ServerSocket chatSocket = null;
	Vector<BinaryNetHandler> binaryconnections = new Vector<>();
	private Vector<ChatNetHandler> chatconnections = new Vector<>();
	public NetManager(){
		super();
		this.start();
	}
	
	public void run(){
		new BGOBinaryCrossdomainServer();
		new BGOChatCrossdomainServer();
		try {
			binarySocket = new ServerSocket();
			chatSocket = new ServerSocket();
			binarySocket.bind(new InetSocketAddress(27050));
			chatSocket.bind(new InetSocketAddress(9338));
			for(;;){
				Socket bs = binarySocket.accept();
				if(bs != null){
				System.out.println("accepted binary");
				BinaryNetHandler b = new BinaryNetHandler(bs.getInputStream(),bs.getOutputStream());
				binaryconnections.add(b);
				}
				Socket cs = chatSocket.accept();
				if(cs != null){
				System.out.println("accepted chat");
				ChatNetHandler c = new ChatNetHandler(cs.getInputStream(),cs.getOutputStream());
				chatconnections.add(c);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}
