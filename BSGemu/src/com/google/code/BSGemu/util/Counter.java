package com.google.code.BSGemu.util;

public class Counter extends Thread {
	
	private int value = 0;
	private int input = 0;
	
	public Counter(int initialValue){
		super();
		value = initialValue;
		this.start();
	}

	@Override
	public void run(){
		for(;;){
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				return;
			}
			synchronized(this){
				value += input;
				input = 0;
			}
		}
	}
	
	public void incrementCounter(){
		synchronized(this){
			input++;
		}
	}
	public void decrementCounter(){
		synchronized(this){
			input--;
		}
	}
	
	public int getValue(){
		int a;
		synchronized(this){
			a = value;
		}
		return a;
	}
	
}
