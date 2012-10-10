package com.google.code.BSGemu.net;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.text.ChangedCharSetException;

public abstract class Packet {
	protected final static void writeChar(char c, ByteArrayOutputStream outputStream )throws IOException{
		if(c>0x00FF){
			throw new IOException(new Exception("Invalid Character: ASCII chars only"));
		}
		write((byte)(c & 0x00FF),outputStream);
	}
	
	protected final void writeString(String s, ByteArrayOutputStream outputStream)throws IOException{
		char[] stringAsCharArray = s.toCharArray();
		for(int i= 0; i < stringAsCharArray.length;i++) {
			writeChar(stringAsCharArray[i], outputStream);
		}
	}
	
	protected final static void write(byte b,ByteArrayOutputStream outputStream) throws IOException{
		byte[] bArray = new byte[]{b};
		outputStream.write(bArray);
	}
	
	protected final void write(byte[] bArray,ByteArrayOutputStream outputStream)throws IOException{
		outputStream.write(bArray);
	}
	
	protected final void writeInt(int integer,ByteArrayOutputStream outputStream)throws IOException{
		String s = Integer.toString(integer, 10);
		writeString(s, outputStream);
	}
	
	protected final void writeShort(short shortInteger, ByteArrayOutputStream outputStream)throws IOException{
		String s = Short.toString(shortInteger);
		writeString(s, outputStream);
	}
	
	protected final void writeLong(long longInteger, ByteArrayOutputStream outputStream)throws IOException{
		String s = Long.toString(longInteger, 10);
		writeString(s, outputStream);
	}
	
	protected final void writeFloat(float floatingPointNumber, ByteArrayOutputStream outputStream)throws IOException{
		String s = Float.toString(floatingPointNumber);
		writeString(s, outputStream);
	}
	
	protected final void writeDouble(long doubleLengthFloatingPointNumber, ByteArrayOutputStream outputStream)throws IOException{
		String s = Double.toString(doubleLengthFloatingPointNumber);
		writeString(s, outputStream);
	}
	protected final static char readChar(InputStream inputStream) throws IOException{
		byte[] character = new byte[1];
		inputStream.read(character);
		return (char) character[0];
	}
	
	protected final static String readString(InputStream readStream,char customDelimiter) throws IOException{
		char tempChar;
		String s = "";
		do{
			tempChar = readChar(readStream);
			if(tempChar != customDelimiter&&tempChar!='\0'){
				s += tempChar;
			}
		}while(tempChar != customDelimiter&&tempChar!='\0');
		return s;
	}
	
	protected final int readInt(InputStream inputStream,char customDelimiter) throws IOException{
		String s = readString(inputStream,customDelimiter);
		return Integer.parseInt(s, 10);
	}
	
	protected final short readShort(InputStream inputStream,char customDelimiter) throws IOException{
		String s = readString(inputStream,customDelimiter);
		return Short.parseShort(s, 10);
	}
	
	protected final long readLong(InputStream inputStream,char customDelimiter) throws IOException{
		String s = readString(inputStream,customDelimiter);
		return Long.parseLong(s, 10);
	}
	
	protected final float readFloat(InputStream inputStream,char customDelimiter) throws IOException{
		String s = readString(inputStream,customDelimiter);
		return Float.parseFloat(s);
	}
	
	protected final byte read(InputStream inputStream) throws IOException{
		byte[] b = new byte[1];
		inputStream.read(b);
		return b[0];
	}
	
	protected final byte[] read(InputStream inputStream,int length) throws IOException{
		byte[] b = new byte[length];
		inputStream.read(b);
		return b;
	}
	
	protected final double readDouble(InputStream inputStream,char customDelimiter) throws IOException{
		String s = readString(inputStream,customDelimiter);
		return Double.parseDouble(s);
	}
	
	protected abstract void writePacketData(ByteArrayOutputStream outputStream)  throws IOException;
	
	protected abstract void readPacketData(InputStream readStream)  throws IOException;
	
	
	
}
