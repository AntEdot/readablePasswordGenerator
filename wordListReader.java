import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.nio.file.*;

import java.util.Random; 

public class WordListReader{
	String fileName;
	String[] banned;
	String[] rm;

	File wordList;
	Scanner reader;

	public WordListReader(String fileName){
		this.fileName = fileName;
	}
	
	private void openFile(){
		try {
			wordList = new File(fileName);
			reader = new Scanner(wordList);
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}

	private void closeFile(){
		reader.close();
	}

	public String getList(){
		openFile();
		StringBuilder sb = new StringBuilder();
		while (reader.hasNextLine()) {
			sb.append(reader.nextLine());
			sb.append("\r\n");
		}
		closeFile();
		return sb.toString();
	}

	private String cutString(String data, String cut) throws NullPointerException{
		StringBuilder sb = new StringBuilder(data);
		int c = sb.indexOf(cut);
		while(c > -1){
			sb.delete(c, c + cut.length());
			c = sb.indexOf(cut);
		}
		return sb.toString();
	}

	public void addBannedCondition(String[] sa){
		if(sa == null) return;
		
		int ba = 0;
		if(banned != null){
			ba = banned.length;
		}
		
		String[] arr = new String[ba + sa.length];
		if(banned != null){
			for(int i = 0; i < ba; i++){
				arr[i] = banned[i];
			}
		}
		
		for(int i = 0; i < sa.length; i++){
			arr[ba + i] = sa[i];
		}
		banned = arr;
	}

	public void addRemoveCondition(String[] sa){
		if(sa == null) return;
		
		int ra = 0;
		if(rm != null){
			ra = rm.length;
		}
		
		String[] arr = new String[ra + sa.length];
		if(rm != null){
			for(int i = 0; i < ra; i++){
				arr[i] = rm[i];
			}
		}
		
		for(int i = 0; i < sa.length; i++){
			arr[ra + i] = sa[i];
		}
		rm = arr;
	}

	public String fixList(){
		openFile();
		StringBuilder sb = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		while (reader.hasNextLine()) {
			String s = reader.nextLine();
			if(banned != null){
				for(String st : banned){
					if(s.contains(st)){
						s = "";
						break;
					}
				}
			}
			if(rm != null){
				if(!s.isEmpty()) {
					for(String st : rm){
						s = cutString(s, st);
					}
					sb.append(s);
					sb.append("\r\n");
					sb1.append(s);
					sb1.append("\",\r\n\"");
				}
			}
		}
		closeFile();
		return sb1.toString();
	}

	private int countLines(String str){
		String[] lines = str.split("\r\n|\r|\n");
		return  lines.length;
	}

	public String getRandomWord(){
		Random rand = new Random();
		String list = fixList();
		writeToFile(list, "words.txt");


		String[] lines = list.split("\r\n|\r|\n");
		return lines[rand.nextInt(lines.length)];
	}

	private void writeToFile(String data, String fileName){
		try {
			Path path = Paths.get(fileName);
			byte[] strToBytes = data.getBytes();
		
			Files.write(path, strToBytes);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}