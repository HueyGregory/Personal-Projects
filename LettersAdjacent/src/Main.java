
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] args1 = new String[] {
				"dir", "C:\\Users\\yaeav\\Documents\\Java Projects\\Docs for Trial\\Bereishes1.txt",
				"string", "asdf",
				"extension", ".txt",
				"language", "Hebrew"
		};
		smallTests();
		RecursionPath recursionPath = new RecursionPath(args1);
		
		args1 = new String[] {
				"dir", "C:\\Users\\yaeav\\Documents\\Java Projects\\Docs for Trial",
				"string", "asdf",
				"extension", ".docx",
				"language", "English"
		};
		recursionPath = new RecursionPath(args1);
		
		args1 = new String[] {
				"dir", "C:\\Users\\yaeav\\Documents\\Java Projects\\Docs for Trial\\Plaintexttrial.txt",
				"string", "asdf",
				"extension", ".txt",
				"language", "English"
		};
		recursionPath = new RecursionPath(args1);
	}

	private static void smallTests() {
		int y = (int) 'א';
		System.out.println(y);
		y = (int) 'A';
		System.out.println(y);
	}
	

}
