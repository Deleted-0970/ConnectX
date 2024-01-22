import java.util.*;

public class Client {
    public static void main(String[] args) {
		Scanner console = new Scanner(System.in);	
        ConnectX game = new ConnectX();
        System.out.println(game.instructions());
        System.out.println();

		int row = -1;
		System.out.println("Enter 0 for default Connect4");
		while (row < 0) {
			System.out.print("How many rows will the board have?: ");
			row = game.checkInput(console.next());
		}
		if (row != 0) {
			int col = -1;
			while (col < 1) {
				System.out.print("How may columns will the board have?: ");
				col = game.checkInput(console.next());
			}
			int connect = -1;
			while (connect < 0) {
				System.out.print("What will X be in ConnectX?: ");
				connect = game.checkConnect(row, col, console.next());
				if (connect < 0) {
					System.out.println("That won't fit on the board!");
				}
			}
			game = new ConnectX(row, col, connect);
			System.out.println("Player 1 will make the first move! GOOBLUCK :3");
		}

        while (!game.isGameOver()) {
            System.out.println(game);
            System.out.printf("Player %d's turn.\n", game.getNextPlayer());
            game.makeMove(console);
        }
        System.out.println(game);
        int winner = game.getWinner();
        if (winner > 0) {
            System.out.printf("Player %d wins!\n", winner);
        } else {
            System.out.println("It's a tie!");
        }
    }
}
