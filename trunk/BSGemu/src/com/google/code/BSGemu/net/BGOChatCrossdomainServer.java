package com.google.code.BSGemu.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public final class BGOChatCrossdomainServer extends Thread {
	private ServerSocket serverSocket = null;
	private Socket s = null;
	
	public BGOChatCrossdomainServer(){
		super();
		try{
			serverSocket = new ServerSocket();
		}catch(Exception e){
			System.exit(-1);
		}
		this.start();
	}
	
	public void run(){
		try {
			serverSocket.bind(new InetSocketAddress(843));
		} catch (IOException e) {
			System.exit(-1);
		}
		for(;;){
			try{
			if(s!=null)s.close();
			s=serverSocket.accept();
			XMLPacket x;
			do{
				 x= (XMLPacket) XMLPacket.readPacket(s.getInputStream());
			}while(!x.contents.contains("<policy-file-request/>"));
			s.getOutputStream().write("<cross-domain-policy><site-control permitted-cross-domain-policies=\"master-only\" /><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>".getBytes(Charset.forName("ASCII")));
			s.getOutputStream().flush();
			Thread.sleep(30);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}
