import java.util.Random; 

class readablePasswordGenerator{
	public static void main(String args[]){
		int n = Integer.parseInt(args[0]);

		String banned[] = {"å", "ä", "ö", "é", "/"};
		String rm[] = {"en ", "ett "};

		WordListReader w = new WordListReader("wordList.txt");
		w.addBannedCondition(banned);
		w.addRemoveCondition(rm);

		System.out.println();
		for(int i = 0; i < n; i++){
			System.out.println(genPass(w));
		}
		System.out.println();
	}

	public static String genPass(WordListReader w){
		Random rand = new Random();
		String r = w.getRandomWord() + w.getRandomWord() + rand.nextInt(99);
		while(r.length() < 20){
			r += w.getRandomWord();
		}
		return r;
	}
}