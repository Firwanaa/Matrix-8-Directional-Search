package ca.sheridancollege.matrixGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : A Firwana
 * @id : 
 */

@Data // Beans low 2: all properties are private , Lombok took care of the rest
@NoArgsConstructor
@AllArgsConstructor
public class squareMatrix implements Serializable {// Beans Low 1, implementing serializable

	/**
	 *
	 */
	private static final long serialVersionUID = -1970234996292509735L;

	private int d1; // square side
	private StringBuilder strMatrix = new StringBuilder(); // String hold the matrix values
	private char[][] charMatrix; // Matrix of Char
	private String word; // Search word from user input
	private int failedAttemptsCounter = 0; // count how many times the program failed after first char success

	/**
	 * PART A Generate Matrix of Random Char
	 *
	 * @return
	 */

	public String randomOrWords() throws Exception { // Switch between random char matrix or from words matrix
		if (getD1() < 14) {
			return setStrMatrix();
		} else {
			readFile();
			writeToFile();
			return matrixFromList();
		}
	}

	public String setStrMatrix() { // generate random Chars and fill the matrix
		strMatrix.delete(0, strMatrix.length()); // flush matrix before populating it.
		charMatrix = new char[d1][d1]; // Define matrix side

		for (int i = 0; i < charMatrix.length; i++) {
			for (int j = 0; j < charMatrix[i].length; j++) {
				int number = (int) (Math.random() * 26) + 65;// generate random num between(65~97) (A~Z)
				charMatrix[i][j] = (char) number;
			} // inner for loop
		} // outer for loop

		/** Convert the 2D Matrix to String */
		for (char[] x : charMatrix) {
			for (char y : x) {
				strMatrix.append(y + " ");
			}
			strMatrix.append("\n");
		}
		return strMatrix.toString().toUpperCase();
	}

	/**
	 * Searh for Word in all 8 directions
	 */

	// All 8 direction coordinates
	private int[] x = { -1, -1, -1, 0, 0, 1, 1, 1 };
	private int[] y = { -1, 0, 1, -1, 1, -1, 0, 1 };
	private StringBuilder searchCoordinates = new StringBuilder();

	public boolean multiDirectionalSearch(char[][] grid, int row, int col, String word) { // search in any direction
		searchCoordinates.delete(0, searchCoordinates.length());// Flush coordinates before
		if (grid[row][col] != word.charAt(0))
			return false;// condition to check First Char,if no match break

		int wordLength = word.length(); // assign word length to int

		// Search word in all 8 directions
		// starting from (row, col) location
		for (int dir = 0; dir < 8; dir++) {

			// Starting point for current location
			int rowDir = row + x[dir];
			int colDir = col + y[dir];
			int w;
			// First character is already checked, So start with 1 not 0 to check the rest
			for (w = 1; w < wordLength; w++) {
				// If index out of bound break.
				if (rowDir >= getD1() || rowDir < 0 || colDir >= getD1() || colDir < 0)
					break;

				// If no match, break
				if (grid[rowDir][colDir] != word.charAt(w))
					break;
				// If matched , append coordinates to a StringBuilder
				searchCoordinates.append(grid[rowDir][colDir] + " " + "|" + rowDir + "," + colDir + "|" + " ");
				// Moving in particular direction according to x and y
				rowDir += x[dir];
				colDir += y[dir];
			}
			// If W equals word length means we have a match, return True
			if (w == wordLength) {
				return true;
			} else {
				failedAttemptsCounter++;
				searchCoordinates.delete(0, searchCoordinates.length()); // Thank you ! Flush failed attempts.
				// However, we can keep track of failed attempts as well if we want
			}
		}
		return false;
	}

	private StringBuilder sb = new StringBuilder(); // Results String builder
	// Search for word

	public String searchPattern(char[][] grid, String word) {
		sb.delete(0, sb.length()); // Flush Results string

		// point and search given word in all direction
		for (int row = 0; row < getD1(); row++) {
			for (int col = 0; col < getD1(); col++) {
				if (multiDirectionalSearch(grid, row, col, word.toUpperCase())) { // If True append results
					sb.append("Result(s) found: " + grid[row][col] + " |" + row + ", " + col + "|" + "\n");
					if (searchCoordinates.length() != 0)
						sb.append("Coordinate(s) : " + searchCoordinates + "\n");
					sb.append("=============================" + "\n");
					searchCoordinates.delete(0, searchCoordinates.length());// Thank you for your service! Flush
																			// searchCoordinates
				}
			}
		}
		if (sb.length() == 0)
			sb.append("Sorry ! No Results found!");
		return sb.toString(); // convert to StringBuilder obj to String
	}

	/**
	 * PART B
	 */
	private ArrayList<String> wordsFromFile = new ArrayList<>();// ArrayList to hold words from file to new file
	private ArrayList<String> wordsList = new ArrayList<>();// ArrayList to hold words from the new file

	public String readFile() throws Exception {
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("C:/words/words.txt")));
			while (sc.hasNext()) {
				// Read words.txt
				wordsFromFile.add(sc.nextLine()); // add words from File 'words.txt' to ArrayList
				int r = (int) (Math.random() * (10 - 0)) + 0; // generate random num 0 ~10
				for (int i = 0; i < wordsFromFile.size(); i++) { // Iterate over the arrayList
					if (i == r && wordsList.size() < 10) { // Pick random from WordsFromFile and add it to wordsList
						wordsList.add(wordsFromFile.get(i));
					}
				}
			}
			sc.close(); // Close scanner
		} catch (Exception e) {
			System.out.println("Error: file not found");
		}
		return wordsFromFile.toString(); // convert StringBuilder obj to String
	}

	public void writeToFile() { // write picked Words stored in wordsList to new File 'writewords.txt'
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:/words/writewords.txt"));
			for (int i = 0; i < wordsList.size(); i++) {
				bw.write(wordsList.get(i) + "\n");
				bw.newLine();
			}
			bw.flush(); // flush buffer
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// directions xy array
	private int[] x1 = { 1, 1, 1, 0, 0, -1, -1, -1 }; // I was trying to rearrange it to get to different directions
														// order, otherwise same as the two arrays before
	private int[] y1 = { 1, 0, -1, 1, -1, 1, 0, -1 };

	public String matrixFromList() {// generate new matrix of random chars from pre defined existing words
		strMatrix.delete(0, strMatrix.length()); // Flush
		charMatrix = new char[d1][d1];
		// boolean boo[][] = new boolean[getD1()][getD1()]; //** Failed try to write
		// words to array in readable pattern by prevent using pre-occupied locations
		// boo.delete(0, strMatrix.length());
		for (int row = 0; row < getD1(); row++) {
			for (int col = 0; col < getD1(); col++) {
				int r = (int) (Math.random() * 9);
				String wordSelected = wordsList.get(r).toUpperCase(); // pick random word
				for (int xy = 0; xy < 8; xy++) { // 8 directions
					int rowdir = row + x1[xy];
					int coldir = col + y1[xy];
					for (int k = 0; k < wordSelected.length(); k++) { // break word and fill the matrix
						// if(boo[row][col] == true) break; // ** cont' failed try mentioned above
						if (rowdir >= getD1() || rowdir < 0 || coldir >= getD1() || coldir < 0)
							break; // If index out of bound, break
						charMatrix[row][col] = wordSelected.charAt(k);
						// boo[row][col] = true; //** cont' failed try mentioned above
						rowdir += x1[xy];// shift row to particular direction (x)
						coldir += y1[xy];// shift col to particular direction (y)
					}
				}
			}

		}
		// Convert matrix to StringBuilder
		for (char[] x : charMatrix) {
			for (char y : x) {
				strMatrix.append(y + " ");
			}
			strMatrix.append("\n");
		}
		return strMatrix.toString().toUpperCase(); // optional To upper Case
	}
}
