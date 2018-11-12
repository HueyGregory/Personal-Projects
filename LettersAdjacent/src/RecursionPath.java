import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.io.IOException;

// Yair Wasserman 10/10/2017

public class RecursionPath
{
	private String directory;
	private String searchString;
	private String extension;
	private Scanner sc;
	private Path[] thePathArray;
	private int numFound;
	private ArrayList<Path> thePathList = new ArrayList<Path>();
	private int[][] resultArray = new int[1600][1600];
	private boolean[] possibleChars = new boolean[1600];
	private char[] theEnglishAlphabet = new char[]{ 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z' 
	};
	private char[] theHebrewAlphabet = new char[]{ 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 
			'י', 'כ', 'ך', 'ל', 'מ', 'ם', 'נ', 'ן', 'ס', 'ע', 'פ', 'ף', 'צ', 'ץ', 'ק',
			'ר', 'ש', 'ת' 
	};
	private boolean possibleCharAccessed;
	
	public RecursionPath(String[] args) 
	{
		sc = new Scanner(System.in);
		initTheFields(args);
		fillInManually();
		run();
	}
	
	// parse args[] and assign each input to its appropriate place of directory, searchString, or extension.
	private void initTheFields (String[] args) {
		for(int i = 0; i < args.length; i++) {
			args[i] = args[i].toLowerCase();
			if (args[i].equals("dir")) {
				// if the input was formatted as "dir = " or "dir == " or "dir is " then need to pass by the = or == or "is" which is another slot in args.
				directory = args[++i];
				if ((directory.equals("=")) || (directory.equals("==")) || (directory.equalsIgnoreCase("is"))) {
					directory = args[++i];
				}
			}
			else if(args[i].equals("string")) {
				searchString = args[++i];
				// if the input was formatted as "search = " or "search == " then need to pass by the = or == which is another slot in args.
				if ((searchString.equals("=")) || (searchString.equals("=="))) {
					searchString = args[++i];
				}
			}
			else if (args[i].equals("extension")) {
				extension = args[++i];
				// if the input was formatted as "extension = " or "extension == " then need to pass by the = or == which is another slot in args.
				if ((extension.equals("=")) || (extension.equals("=="))) {
					extension = args[++i];
				}
			}
			else if (args[i].equals("language")) {
				String language = args[++i];
				// if the input was formatted as "extension = " or "extension == " then need to pass by the = or == which is another slot in args.
				if ((language.equals("=")) || (language.equals("=="))) {
					language = args[++i];
				}
				if (language.equals("English")) {
					initPossibleChars(theEnglishAlphabet);
				}
				else if (language.equals("Hebrew")) {
					initPossibleChars(theHebrewAlphabet);
				}
			}
		}
		numFound = 0;
	}
	
	// check directory, searchString, and extension and if they are still null, prompt the user to fill them in manually.
	private void fillInManually () {
		if(directory == null) {
			System.out.println("what directory? ");
			directory = sc.nextLine();
		}
		if (searchString == null) {
			System.out.println("what string do you want to search for? ");
			searchString = sc.nextLine();
		}
		if(extension == null) {
			System.out.println("what is the extension type for the file in " + directory + " that you want to search through for " + searchString + ".");
			extension = sc.nextLine();
		}
		if (!possibleCharAccessed) {
			System.out.println("what language?");
			String language = sc.nextLine();
			if (language.equals("English")) {
				initPossibleChars(theEnglishAlphabet);
			}
			if (language.equals("Hebrew")) {
				initPossibleChars(theHebrewAlphabet);
			}
		}
	}
	
	private void initPossibleChars(char[] givenAlphabet) {
		possibleCharAccessed = true;
		for (int i = 0; i < givenAlphabet.length; i++) {
			possibleChars[givenAlphabet[i]] = true;
		}
	}
	
	// Running the program.
	private void run() {
		// Open each file using recursion
		// convert the string directory to type path.
		Path theDirectory = Paths.get(directory);
		// check if the directory isDirectory() or isRegularFile();
		if (Files.isDirectory(theDirectory) == true) {
		// use Files.list along with a for each loop and recursion to read through the entire directory. Must be put in a try block.
			getFiles(theDirectory);
			thePathList.trimToSize();
			thePathArray = thePathList.toArray(new Path[thePathList.size()]);
			recursing(0);
		}
			
		// if this were the only file in the directory.
		else if (Files.isRegularFile(theDirectory)) {
			thePathArray = new Path[]{theDirectory};
			recursing(0);
		}
		System.out.println('"' + searchString + '"' + " was found " + numFound + " time(s).");
		System.out.println("Thank you for playing!");
	}
	
	// See the followup discussion on: https://piazza.com/class/j6f6fkubxxg1gn?cid=38 that this algorithm for using recursion to get all the files is okay.
	// If you want to see a different method using .walk(), look at the commented out method down below after the class.
	private void getFiles(Path thePath) {
		try (DirectoryStream<Path> entries = Files.newDirectoryStream(thePath)) {
			for (Path singlePath: entries) {
				if (Files.isDirectory(singlePath)) {
					// uses recursion to get all the files from the directory.
					getFiles(singlePath);
				}
				else if (Files.isRegularFile(singlePath)) {
					thePathList.add(singlePath);
				}
			}
		}
		catch (IOException ex) {
			System.out.println("caught exception: " + ex);
		}
	}

	// Method: recursing(index) passes through thePathArray of Paths 
	// and then calls insideFile() to continue on with the letter comparisons 
	// if the path is a file. Uses recursion while passing in an index as the value of the next path.
	private void recursing(int index) {
		// base case
			// return when searched at the end of the array.
			if(index == thePathArray.length) {
				return;
			}
			// if this is not the end of the array then continue below.
			else if (index < thePathArray.length) {
				// double check that the file is not a directory and isReadable.
				Path currPath = thePathArray[index]; // assign the current path to a local variable so will only have to access the array once.
				if((!Files.isDirectory(currPath)) && (Files.isReadable(currPath))) {
					// if the extension is not ".extension," then need to convert it into one using string concatenation.
					if(extension.charAt(0) != '.') {
						extension = "." + extension;
					}
					// make sure that the extension of the file equals the user-input extension.	
					if (currPath.toString().endsWith(extension)) {
						insideFile(currPath);
					}
				}
				
			}
		// recursive case.
			// return the index for the next file in the array.
			recursing(++index);
			
	}
			
// helper methods.
	
	// Method insideFile(currPath)
	private void insideFile(Path currPath) {
		// The file to be searched has been located, so compare searchString to the things in currPath.
		// Need to place into try statement in case of IOException with Files.
		try {
			// set long lengthOfFile = Files.size(currPath) page 108.
			long lengthOfFile = Files.size(currPath);

			// use the DataInputStream and PushbackInputStream.
			//PushbackInputStream pbin = new PushbackInputStream(new BufferedInputStream(new FileInputStream(currPath.toFile())));
			//DataInputStream din = new DataInputStream(pbin);
			// use a scanner with the DataInputstream to read input from the file.
			sc = new Scanner(currPath);
			sc.useDelimiter("");
			// If the file is shorter than the length of searchString, then no need to enter the while loop so move on to the next file.
			if(lengthOfFile < 1) {
				System.out.print(currPath + " obviously can not contain consecutive letters to be added to the letters adjacent array");
				System.out.println(" as it is smaller than the length of 1");
				return;
			}
			
			// use a while loop to pass though each file until the end. 
			readCharactersFromFile();	
			printResultArray();
		}	
		catch (IOException ex) {
			System.out.println("caught exception: " + ex);
		}	
	}
	
	// Method readCharactersFromFile(): reads characters from the entire file and fills resultArray. 
	private void readCharactersFromFile() {	
		// use a string to store the next line. Also check if the line is empty.
		boolean endOfFile = false; // keeps track of when finished reading the first file and are now ready to read the next file.
		Character currentChar = '\0';
		Character nextChar = '\0';
		if(sc.hasNext()) {
			currentChar = sc.next().charAt(0);
			currentChar = inAlphabet(currentChar);
		}
		while (!endOfFile) {
			if(sc.hasNext()) {
				nextChar = sc.next().charAt(0);
				nextChar = inAlphabet(nextChar);
			}
			else if (sc.hasNext() == false) {
				return; // means we are at end of file, so return
			}
			if (currentChar == null || nextChar == null) {
				return;
			}
			resultArray[currentChar][nextChar]++;
			currentChar = nextChar;
		}
	}
	
	private Character inAlphabet(Character givenChar) {
		// check that each character is in the alphabet.
		while (((int) givenChar > possibleChars.length) || ((int) givenChar < 0) || (!(possibleChars[givenChar]))) {
			int y = (int) givenChar;
			if(sc.hasNext()) {
				try { 
					givenChar = sc.next().charAt(0);
				}
				catch(Exception e) {
					System.out.println("that character does not exist");
					sc.reset();
				}
			}
			
			else {
				return null;
			}
		
		}
		return givenChar;		
	}
	
	private void printResultArray() {
		for (int row = 0; row < resultArray.length; row++) {
			boolean rowContains = false;
			for (int col = 0; col < resultArray[row].length; col++) {
				if (resultArray[row][col] > 0) {
					System.out.printf("\t%c%c %d",(char) row, (char) col, resultArray[row][col]);
					rowContains = true;
					//System.out.print((char) col);
					//System.out.println(" appears " + resultArray[row][col] + " number of times.");
				}
			}
			if (rowContains) {
				System.out.println();
			}
		}
	}
	
}
