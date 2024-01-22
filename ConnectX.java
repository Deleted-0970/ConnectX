import java.util.*;

public class ConnectX {
	private int[][] board; 
	private int currPlayer;
	private int connect;

	// This constructor creates a default game of ConnectFour
	public ConnectX() {
		this(6, 7, 4);
	}
	
	// This constructor creates a custom game of ConnectX
	// Parameters:
	//    - int rows: the number of rows on the board
	//    - int cols: the number of columns on the board
	//    - int connect: the number in a row required to win
	public ConnectX(int rows, int cols, int connect) {
		this.board = new int[rows][cols];
		this.currPlayer = 2;
		this.connect = connect;
	}
	
	// This method checks if the input is a valid positive integer
	// Parameters:
	//    - String test: the test input to see if it is a positive integer
	// Return:
	//    - int: the integer that was created from the string. -1 if the 
	//			 integer is invalid
	public int checkInput(String test) {
		int num = -1;
		try {
			num = Integer.parseInt(test);
		} catch (NumberFormatException e) {}
		return num;
	}
	
	// This method checks if the connect number input is valid
	// and fits within the board. Returns -1 if invalid.
	// Parameters:
	//    - int row: the number of rows in the board
	//    - int col: the number of columns in the board
	//    - String test: the connect number being tested.
	// Return:
	//    - int: the connect number or -1 if invalid
	public int checkConnect(int row, int col, String test) {
		int num = this.checkInput(test);
		if (num > row || num > col) {
			num = -1;
		}
		return num;
	}

	// This method creates a String containing instructions on how to play ConnectFour
	// Return:
	//    - String: String containing instructions on how to play ConnectFour 
	public String instructions() {
		String ins = "Welcome to ConnectX!\n";
		ins += "This is Connect 4 but you choose how large the board is and how many in a row\n";
		ins += "counts as a win!\n\n";
		ins += "There are two players, Player 1's tokens will be represented by [●]\n";
		ins += "and Player 2's tokens will be represented by [○].\n\n";
		ins += "For those of you who don't know how to play Connect 4...\n";
		ins += "On each player's turn, choose a column to drop a token.\n";
		ins += "The token will fall to the bottom of the board or onto another token.\n";
		ins += "The next player will then drop their token, and so on.\n\n";
		ins += "A player wins if they have X tokens in a row horizontally, vertically, or diagonally.";
		ins += "\nIf the board is full with no player's tokens creating a row of X, there is a tie!\n";
		return ins;
	}

	// This method creates a String representation of the current state of the 
	// ConnectFour game.
	// Return:
	//    - String: a String containing the current state of the ConnectFour game
	public String toString() {
		String state = " ";
		// print column nums
		for (int i = 1; i <= board[0].length; i++) {
			state += i + "  ";
		}
		state += "\n";
		// print tokens
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1) {
					state += "[●]";
				} else if (board[i][j] == 2) {
					state += "[○]";
				} else {
					state += "[ ]";
				}
			}
			state += "\n";
		}
		return state;
	}

	// This method checks if the game has ended.
	// Return:
	//    - boolean: a boolean on whether or not the game has ended
	public boolean isGameOver() {
		return this.getWinner() >= 0;
	}

	// This method checks for a winner in the game of ConnectFour
	// Return:
	//   - int: index of winner of game. -1 means no winner, 1 means Player 1, 
	//          2 means Player 2, 0 means tie
	public int getWinner() {
		if (this.rowWin() || this.colWin() || this.diagWin()) {
			return this.currPlayer;
		} else if (this.isTie()) {
			return 0;
		} else {
			return -1;
		}
	}

	// This method gets the index of which player's turn it is next.
    // Return:
	//    - int: index of next player. 1 representing Player 1, 2 representing Player 2.
	public int getNextPlayer() {
		if (currPlayer == 1) {
			return 2;
		} else {
			return 1;
		}
	}

	// This method has the next player drop a token in a game of ConnectFour by having them
	// select a column and dropping their token at the selected column. 
	// If the input is outside the range or invalid, the player will be reprompted to enter a valid 
	// column number. If the column is full, the player will be reprompted to enter a 
	// valid column number.
	// Parameters:
	//    - Scanner input: a scanner to read user input in the terminal
	public void makeMove(Scanner input) {
		System.out.print("Pick a column [1-" + board[0].length + "] to drop a token: ");
		String colAndRow = columnChecker(input.next());

		// If input is invald, ask for another input.		
		while (colAndRow.equals("inv")) {
			colAndRow = columnChecker(input.next());
		}

		// drop token at valid row in selected column
		int col = Integer.parseInt("" + colAndRow.charAt(0));
		int row = Integer.parseInt("" + colAndRow.charAt(1));
		this.board[row][col] = this.getNextPlayer();

		this.currPlayer = this.getNextPlayer();
	}

	// This is a private helper method that checks the input and column the player
	// makes when prompted to make a move. If the input isn't a valid column,
	// the player will be reprompted to enter a valid column to drop a token.
	// If the column that was selected is full, the player will be reprompted to
	// enter a valid column to drop a token. If the column selected is valid, 
	// a String is created containing the column and row the token will be placed.
	// Parameters:
	//    - String test: the input being checked for being a valid column
	// Return:
	//    - String: a String containing the column and row respectively the token will 
	//              be placed or an error if the column is invalid. 
	private String columnChecker(String test) {
		String possibleCols = "";
		for (int i = 1; i <= this.board[0].length; i++) {
			possibleCols += i + " ";
		}
		if (!possibleCols.contains(test)) {
			System.out.println("invalid column, please enter a number from 1-" + board[0].length);
			System.out.print("Pick a column to drop a token: ");
			return "inv";
		} 
		int col = Integer.parseInt(test) - 1;
		int row = -1;
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i][col] == 0) {
				row = i;
			}
		}
		if (row == -1) {
			System.out.println("that column is full.");
			System.out.print("Pick a column [1-" + board[0].length +"] to drop a token: ");
			return "inv";
		}
		return "" + col + row;
	}
	
	// This is a private helper method that checks the current state of the game on 
	// whether a player has X tokens in a row horizontally anywhere on the board.
	// If there is a winner, the winner is set as the current player
	// Return:
	//    - boolean: a boolean to represent whether or not a player has X in a row horizontally
	private boolean rowWin() {
		int rCount = 0; // originally player 1 was going to be Red so thats why its rCount...
		int yCount = 0; // originally player 2 was going to be Yellow so thats why its yCount...
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				if (this.board[i][j] == 1) {
					rCount++;
					if (rCount == this.connect) {
						this.currPlayer = 1;
						return true;
					}
					yCount = 0;
				} else if (board[i][j] == 2) {
					rCount = 0;
					yCount++;
					if (yCount == this.connect) {
						this.currPlayer = 2;
						return true;
					}
				} else {
					rCount = 0;
					yCount = 0;
				}
			}
			rCount = 0;
			yCount = 0;
		}
		return false;
	}

	// This is a private helper method that checks the current state of the game on 
	// whether a player has X tokens in a row vertically anywhere on the board.
	// If there is a winner, the winner is set as the current player
	// Return:
	//    - boolean: a boolean to represent whether or not a player has X in a row vertically
	private boolean colWin() {
		int rCount = 0;
		int yCount = 0;
		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[j][i] == 1) { 
					rCount++;
					yCount = 0;
					if (rCount == this.connect) {
						this.currPlayer = 1;
						return true;
					}
				} else if (board[j][i] == 2) {
					rCount = 0;
					yCount++;
					if (yCount == this.connect) {
						this.currPlayer = 2;
						return true;
					}
				} else {
					rCount = 0;
					yCount = 0;
				}
			}
			rCount = 0;
			yCount = 0;
		}
		return false;
	}

	// This is a private helper method that checks the current state of the game on 
	// whether or not there is a tie.
	// Return:
	//    - boolean: a boolean to represent whether or not there is a tie.
	private boolean isTie() {
		int sum = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				sum += board[i][j];
			}
		}
		// check if the board is filled by calculating the sum of all pieces
		if (sum == (board.length * board[0].length / 2) * 3) {
			return true;
		}
		return false;
	}

	// CREATIVE FEATURE:
	// This is a private helper method that checks the current state of the game on 
	// whether a player has x tokens in a row diagonally anywhere on the board.
	// If there is a winner, the winner is set as the current player
	// Return:
	//    - boolean: a boolean to represent whether or not a player has x in a row diagonally 
	private boolean diagWin() {
		Set<Integer> check = new HashSet<>(); // set to check if diagonal only contains one type
		for (int i = 0; i < board.length - (this.connect - 1); i++) { // check on diagonal direction
			for (int j = 0; j < board[i].length - (this.connect - 1); j++) {
				if (board[i][j] != 0) {
					for (int k = 0; k < this.connect; k++) {
						check.add(board[i + k][j + k]);
					}
					if (check.size() == 1) {
						this.currPlayer = board[i][j];
						return true;
					}
					check.clear();
				}
			}
		}
		for (int i = 0; i < board.length - (this.connect - 1); i++) { // check other diagonal direction
			for (int j = board[i].length - 1; j >= this.connect - 1; j--) {
				if (board[i][j] != 0) {
					for (int k = 0; k < this.connect; k++) {
						check.add(board[i + k][j - k]);
					}
					if (check.size() == 1) { 
						this.currPlayer = board[i][j];
						return true;
					}
					check.clear();
				}
			}
		}
		return false;
	}
}


