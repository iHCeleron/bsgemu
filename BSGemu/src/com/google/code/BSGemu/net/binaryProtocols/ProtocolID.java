
package com.google.code.BSGemu.net.binaryProtocols;

public enum ProtocolID
{	
    Login {
		
		public byte id(){
			return (byte)0;
		}
	},
    Universe {
		
		public byte id(){
			return (byte)1;
		}
	},
    Game {
		
		public byte id(){
			return (byte)2;
		}
	},
    Sync {
		
		public byte id(){
			return (byte)3;
		}
	},
    Player {
		
		public byte id(){
			return (byte)4;
		}
	},
    Debug {
		
		public byte id(){
			return (byte)5;
		}
	},
    Catalogue {
		
		public byte id(){
			return (byte)6;
		}
	},
    Ranking {
		
		public byte id(){
			return (byte)7;
		}
	},
    Story {
		
		public byte id(){
			return (byte)8;
		}
	},
    Scene {
		
		public byte id(){
			return (byte)9;
		}
	},
    Room {
		
		public byte id(){
			return (byte)10;
		}
	},
    Community {
		
		public byte id(){
			return (byte)11;
		}
	},
    Shop {
		
		public byte id(){
			return (byte)12;
		}
	},
    Setting {
		
		public byte id(){
			return (byte)13;
		}
	},
    Ship {
		
		public byte id(){
			return (byte)14;
		}
	},
    Dialog {
		
		public byte id(){
			return (byte)15;
		}
	},
    Market {
		
		public byte id(){
			return (byte)16;
		}
	},
    Notification {
		
		public byte id(){
			return (byte)17;
		}
	},
    Subscribe {
		
		public byte id(){
			return (byte)18;
		}
	},
    Feedback {
		
		public byte id(){
			return (byte)19;
		}
	},
    Tournament {
		
		public byte id(){
			return (byte)20;
		}
	};
	
	public abstract byte id();
    
}