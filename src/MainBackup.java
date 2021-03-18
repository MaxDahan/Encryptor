import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class MainBackup {
	
	public MainBackup() throws FileNotFoundException {
		System.out.println("Encrypt (E) / Decrypt (D)");
		Scanner systemScan = new Scanner(System.in);
		String response = systemScan.nextLine();
		if(response.equals("E") || response.equals("e")) encrypt(systemScan);
		else if(response.equals("D") || response.equals("d")) decrypt(systemScan);
		else System.out.println("I hate you.");
	}
	
	public void decrypt(Scanner systemScan) {
		System.out.println("What is the file path?");
		String filePath = systemScan.nextLine();
		if(filePath.length() > 4) {
			if(!filePath.substring(filePath.length() - 4, filePath.length()).equals(".txt")) filePath = filePath + ".txt";
		} else filePath = filePath + ".txt";
		
		Scanner in = null;
		try {in = new Scanner(new File(filePath));} catch (FileNotFoundException e) {e.printStackTrace();}
		Random rand = new Random();
		if(in.hasNext()) rand = new Random(Integer.parseInt(in.next()));
		
		Random r = new Random(12);
		System.out.println(r.nextInt(100));
		
		while(in.hasNext()) {
			String word = in.next();
			for(int i = 0; i < word.length(); i++) {
				int c = (int)word.charAt(i) - rand.nextInt(94);
				if(c < 32) c += 94;
				char encoded = (char) c;
				System.out.print(encoded);
			}
		}
	}
	
	public void encrypt(Scanner systemScan) {
		System.out.println("Where would you like to save it (include file name)?");
		String filePath = systemScan.nextLine();
		if(filePath.length() > 4) {
			if(!filePath.substring(filePath.length() - 4, filePath.length()).equals(".txt")) filePath = filePath + ".txt";
		} else filePath = filePath + ".txt";
		
		System.out.println("What would you like to encrypt?");
		String input = systemScan.nextLine();
		
		System.out.println("What would you like the seed to be?");
		int seed = 0;
		try {
			seed = Integer.parseInt(systemScan.nextLine());
		} catch(Exception e) {
			Random random = new Random();
			seed = Math.abs(random.nextInt());
		}
		Random rand = new Random(seed);

		PrintStream out = null;
		try {out = new PrintStream(new File(filePath));} catch (FileNotFoundException e) {e.printStackTrace();}
		out.print(seed + " ");
		
		for(int i = 0; i < input.length(); i++) {
			int c = (int)input.charAt(i) + rand.nextInt(94);
			if(c >= 127) c -= 94;
			char encoded = (char) c;
			out.print(encoded);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		new MainBackup();
	}
}
