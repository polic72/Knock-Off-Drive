package Drive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import Drive.HTTP.HTTP;

public class Drive {

	public static void main(String[] args){
		/*File file = new File("C:\\Users\\stonisg\\Desktop\\Programs\\HTML Files\\Testing\\D&D.png");//Background.png");
		//System.out.println(file.canRead());
		
		try {
			//Scanner s = new Scanner(file);
			FileInputStream s = new FileInputStream(file);
			
			byte[] bAr = new byte[(int)file.length()];
			
			s.read(bAr);
			
			String whoTheFuckCares = "Hey there bitch\r\n";
			
			byte[] ohBoy = whoTheFuckCares.getBytes();
			
			String rip = new String(ohBoy);
			
			ohBoy = rip.getBytes();
			
			rip = new String(ohBoy);
			
			System.out.println(rip);
			
			for (int i = 0; i < ohBoy.length; ++i){
				System.out.print(ohBoy[i] + " ");
			}
			
			
			int pos = 0;
			
			for (int i = pos; i < 12 + pos; ++i){
				//
			}
			pos = 12 + pos;
			
			System.out.println("\r\n" + pos);
			
			for (int i = pos; i < 12 + pos; ++i){
				//
			}
			pos = 12 + pos;
			
			System.out.println(pos);
			
			s.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
//		int num1 = 52;
//		int num2 = 128;
//		String chars = new String();
//		chars += (char)num1;
//		chars += (char)num2;
//		System.out.println((char)num1 + " : " + chars + " : " + (num1 == chars.charAt(0)));
//		System.out.println((char)num2 + " : " + chars + " : " + (num2 == chars.charAt(1)));
//		
//		System.out.println("\n\n\n\n\n");
//		
//		String test = "Hey there Beter, what's up?\r\nheader: line\r\n\r\n"; for (int i = 255; i >= 0; --i){test += (char)i;}
//		int totalSize = 0;
//		
//		System.out.println("Here's the original:\n" + test);
//		System.out.println("---Here's after:---");
//		
//		Scanner testScam = new Scanner(test);
//		String currentLine = new String();
//		
//		while (testScam.hasNextLine()){
//			currentLine = testScam.nextLine();
//			totalSize += currentLine.length() + 2;
//			
//			if (currentLine.isEmpty()){System.out.println("Here?");
//				break;
//			}
//			
//			System.out.println(currentLine);
//		}
//		
//		testScam.close();
//		
//		
//		System.out.println("totalSize: " + totalSize);
//		System.out.println("test.charAt(totalSize): " + test.charAt(totalSize));
//		
//		//Show only the data portion:
//		for (int i = totalSize; i < test.length(); ++i){
//			System.out.print(test.charAt(i));
//		}
		
		
		HTTP testServer;
		try{
			testServer = new HTTP(args[0]);//"C:\\Users\\stonisg\\Desktop\\Programs\\HTML Files\\Knock-Off Drive\\website 0.2");	//args[0]);
		
			while (true){
				testServer.makeConnection();
			
				testServer.readMessage();
				
					//System.out.println(testServer.getMessageRead());
			
				testServer.interpretMessage();
				
					//System.out.println(testServer.getMessageSend());
			
				testServer.sendMessage();
			
				testServer.breakConnection();
			}
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
















