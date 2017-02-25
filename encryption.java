import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Scanner;


public class encryption {

	static boolean fileExists = false;
	static Scanner keyboard = new Scanner(System.in);
	static String fileDirectory;
	static String fileName;
	public static void main(String [] args) throws Exception{

		//array to store each line in an entry
		String directory;

		//Prompts the user to enter a directory of the text file
		System.out.println("Please enter the directory of the file you would like to read including the file name (please dont include the extension)");
		directory = keyboard.nextLine(); 

		//opens the file for the first time to count the line
		FileReader file = new FileReader(directory +".rtf");
		BufferedReader reader = new BufferedReader(file);
		LineNumberReader LineNumber = new LineNumberReader(file);
		int numOfLines = 0;

		//loop to count the number of lines to determine how big the array should be
		while(LineNumber.readLine() != null){
			numOfLines++;
		}

		//opens the file a second time to copy the lines
		FileReader file1 = new FileReader(directory +".rtf");
		BufferedReader reader1 = new BufferedReader(file1);

		//creates an array that will be sized according to the number of lines in the text hold all the lines in the text file
		int elementLocation = 0;
		String read [] = new String [numOfLines];
		read[elementLocation] = "";
		String line = reader1.readLine();

		//cycles through each line and saves it in the text file to save it to an array entry
		while(line != null){	
			read[elementLocation] = line;
			line = reader1.readLine();
			elementLocation++;
			if (elementLocation == numOfLines){
				break;
			}
		}
		reader.close();
		reader1.close();

		//declaration of variables needed to store parts of messages from
		char choice;
		int columnSize;
		String message;

		//cycles through the array of stored sentences and breaks down the message
		for(int i = 0; i < numOfLines; i++){
			choice = read[i].charAt(0);
			columnSize = Character.getNumericValue(read[i].charAt(1));
			message = read[i].substring(2);

			//if the choice has a value of E then the encryption algorithm is initiated
			if((choice == 'E') || (choice == 'e')){
				System.out.print("Beginning Encryption Process");
				encryptMessage(message, columnSize);
				System.out.println("\nEncryption Process Finished.\n");
			}

			//if the choice has a value of D then the Decryption algorithm is initiated
			else if ((choice == 'D') || (choice == 'd')){
				System.out.println("Beginning Decryption Process");
				decryptMessage(message, columnSize);
				System.out.println("\nDecryption Process Finished.\n");
			}

			else{
				System.out.println("Incorect File Format, please start the string with E (ENCRYPTION) or D (DECRYPTION) followed by the Column number, then the message");
				break;
			}
		}
	}

	//decryption algorithm
	public static void decryptMessage(String message, int columnCount) throws Exception{

		// Removes all whitespaces, spaces, tabs, etc from string
		message = message.trim().replaceAll("\\s+", "");

		// Transforms the string to all UPPERCASE letters
		message = message.toUpperCase();

		int charLocation = 0;
		int rowCount;

		//determines if the amount of rows, if not divisible by the amount of columns, then an extra row is added
		if(message.length() % columnCount != 0){
			rowCount = (message.length() / columnCount) + 1;
		}
		else{
			rowCount = message.length() / columnCount;
		}

		//Array initialization
		char decryptArray[][] = new char [rowCount][columnCount];

		//goes from row 0 then runs for loop for column population then goes to row 2 and so on until row 5 then breaks
		for (int row = 0; row < rowCount; row++){

			//goes from column 0 to column 5 populating them respectively
			for(int column = 0; column < columnCount; column++){                      

				//a break to make sure the character length does not exceed the amount of characters in the original string
				if (charLocation >= message.length()) {
					break;
				}
				//begins populating the 2d array table with the string characters
				decryptArray[row][column] = message.charAt(charLocation);
				charLocation++;
			} 	
		}
		//initializes a stringBuilder to turn the table into a string
		StringBuilder transferToString = new StringBuilder();

		//for loop that goes through all the columns of each row in the encryptArray and puts it in a string in order to send it to the writeTo method
		for(int column = 0; column < columnCount; column++){
			for(int row = 0; row < rowCount; row++){
				transferToString.append(decryptArray[row][column]);
			}
		}
		String decryptionMessage = transferToString.toString();
		writeTo(decryptionMessage);

	}

	//encryption algorithm
	public static void encryptMessage(String message, int columnCount) throws Exception {

		// Removes all white spaces, spaces, tabs, etc from string
		message = message.trim().replaceAll("\\s+", "");

		// Transforms the string to all UPPERCASE letters
		message = message.toUpperCase();

		int charLocation = 0;
		int rowCount;


		//determines if the amount of rows, if not divisible by the amount of columns, then an extra row is added
		if(message.length() % columnCount != 0){
			rowCount = (message.length() / columnCount) + 1;
		}
		else{
			rowCount = message.length() / columnCount;
		}

		//Array initialization
		char encryptArray[][] = new char [rowCount][columnCount];


		//goes from row 0 then runs for loop for column population then goes to row 2 and so on until row 5 then breaks
		for (int row = 0; row < rowCount; row++){

			//goes from column 0 to column 5 populating them respectively
			for(int column = 0; column < columnCount; column++){                      

				//a break to make sure the character length does not exceed the amount of characters in the original string
				if (charLocation >= message.length()) {
					for(; column < columnCount; column++){
						encryptArray[row][column] = 'Z';
					}
					break;
				}

				//begins populating the 2d array table with the string characters
				encryptArray[row][column] = message.charAt(charLocation);
				charLocation++;
			} 	
		}

		System.out.println();

		//initializes a stringBuilder to turn the table into a string
		StringBuilder transferToString = new StringBuilder();

		//for loop that goes through all the columns of each row in the encryptArray and puts it in a string in order to send it to the writeTo method
		for(int column = 0; column < columnCount; column++){
			for(int row = 0; row < rowCount; row++){
				transferToString.append(encryptArray[row][column]);
			}
		}
		String encryptionMessage = transferToString.toString();
		writeTo(encryptionMessage);
	}

	public static void writeTo(String messageToWriteToFile)throws Exception{

		// if this is the first time this function is called then it will ask for a directory and file name and create them
		if( fileExists == false){
			System.out.println("Please enter the directory to create a new text file (Don't include file name or extension at the end i.e (C:\\Users\\JohnDoe\\Desktop)");
			fileDirectory = keyboard.nextLine();
			System.out.println("Please choose a file name for your text file (Don't Include .rtf or any extensions at the end)");
			fileName = keyboard.nextLine();
			fileExists = true;
		}
		File newFile = new File(fileDirectory + "\\" + fileName +".rtf");

		//This is executed when the user has already created a file 
		if(newFile.exists()){
			System.out.println("The File " + fileName +" Already Exists in directory " + fileDirectory + " , appending to file now");
			FileWriter fileWrite = new FileWriter(newFile, true);
			BufferedWriter bufferedWrite = new BufferedWriter(fileWrite);
			PrintWriter printWrite = new PrintWriter(bufferedWrite);
			printWrite.println(messageToWriteToFile);

			System.out.println("Finished appending to file");
			printWrite.close();
			return;

		}

		//if the file has been created for the first time then this will begin to populate said file
		newFile.createNewFile();
		{
			System.out.println("The File " + fileName + " has been created in directory " + fileDirectory + " and is being populated for the first time"  );
			FileWriter fileWrite = new FileWriter(newFile, true);
			BufferedWriter bufferedWrite = new BufferedWriter(fileWrite);
			PrintWriter printWrite = new PrintWriter(bufferedWrite);
			printWrite.println(messageToWriteToFile);

			System.out.println("File Created and written to sucessfully, Please check " + fileDirectory +" For the File " + fileName);
			printWrite.close();

		}
	}
}
