import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	public Main() throws FileNotFoundException {
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
		if (in.hasNext()) rand = new Random(Integer.parseInt(in.next()));
		
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

		String Seed = "";
		System.out.println("What would you like the seed to be?");
		try {
			Seed = systemScan.nextLine();
			if(Seed.length() < 1) {
				Random random = new Random();
				int sum = 0;
				while(sum < input.length() && Seed.length() < 9) {
					int num = random.nextInt(10);
					sum += num;
					Seed += num;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		Random rand = new Random(Integer.parseInt(Seed));

		PrintStream out = null;
		try {out = new PrintStream(new File(filePath));} catch (FileNotFoundException e) {e.printStackTrace();}
		
		int position = 0;
		
		for(int i = 0; i < input.length() + Seed.length(); i++) {
			try {
				System.out.println(i + " : " + Seed.charAt(position));
				if(i == position) {
					System.out.println("HEY");
					out.print(Seed.charAt(position) + "");
					position += Integer.parseInt(Seed.charAt(position) + "");
				} else {
					int c = (int)input.charAt(i) + rand.nextInt(94);
					if(c >= 127) c -= 94;
					char encoded = (char) c;
					
					out.print(encoded);
				}
			} catch(Exception e) {}
		}
		System.out.println(Seed);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		new Main();
	}
}