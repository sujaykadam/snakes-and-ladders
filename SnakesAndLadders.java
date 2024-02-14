import java.util.Random;

public class SnakesAndLadders {
    private static final int BOARD_SIZE = 10;
    private static final int MIN_SNAKES = 2;
    private static final int MAX_SNAKES = 6;
    private static final int MIN_LADDERS = 2;
    private static final int MAX_LADDERS = 6;
    private static final int MIN_DICE_ROLL = 1;
    private static final int MAX_DICE_ROLL = 6;

    private int[][] board;
    private int playerPosRow;
    private int playerPosCol;
    private int[][][] snakes;
    private int[][][] ladders;

    public SnakesAndLadders() {
        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        this.playerPosRow = BOARD_SIZE - 1; // Player starts at the bottom-left corner
        this.playerPosCol = 0;

        initializeBoard();
        placeSnakesAndLadders();
    }

    // Initialize the game board
    private void initializeBoard() {
        int count = 1;
        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            if (i % 2 == 1) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    this.board[i][j] = count++;
                }
            } else {
                for (int j = BOARD_SIZE - 1; j >= 0; j--) {
                    this.board[i][j] = count++;
                }
            }
        }
    }

    // Place snakes and ladders randomly on the board
    private void placeSnakesAndLadders() {
        Random rand = new Random();
        this.snakes = new int[BOARD_SIZE][BOARD_SIZE][2];
        this.ladders = new int[BOARD_SIZE][BOARD_SIZE][2];

        int numSnakes = rand.nextInt(MAX_SNAKES - MIN_SNAKES + 1) + MIN_SNAKES;
        int numLadders = rand.nextInt(MAX_LADDERS - MIN_LADDERS + 1) + MIN_LADDERS;

        // Place snakes
        int i = 0;
        while (i < numSnakes) {
            int[] snakeStart = {rand.nextInt(BOARD_SIZE), rand.nextInt(BOARD_SIZE)};
            int[] snakeEnd = {rand.nextInt(BOARD_SIZE), rand.nextInt(BOARD_SIZE)};
            if (snakes[snakeStart[0]][snakeStart[1]][0] == 0 && snakes[snakeStart[0]][snakeStart[1]][1] == 0) {
                snakes[snakeStart[0]][snakeStart[1]] = snakeEnd;
                i++;
            }
        }

        // Place ladders
        i = 0;
        while (i < numLadders) {
            int[] ladderStart = {rand.nextInt(BOARD_SIZE), rand.nextInt(BOARD_SIZE)};
            int[] ladderEnd = {rand.nextInt(BOARD_SIZE), rand.nextInt(BOARD_SIZE)};
            if (ladders[ladderStart[0]][ladderStart[1]][0] == 0 && ladders[ladderStart[0]][ladderStart[1]][1] == 0) {
                ladders[ladderStart[0]][ladderStart[1]] = ladderEnd;
                i++;
            }
        }
    }

    // Move the player based on dice roll
    private void movePlayer(int diceRoll) {
        if (playerPosRow % 2 == 1) {
            playerPosCol += diceRoll;
            if (playerPosCol > BOARD_SIZE - 1) {
                playerPosRow--;
                playerPosCol = 2 * (BOARD_SIZE - 1) - playerPosCol;
            }
        } else {
            playerPosCol -= diceRoll;
            if (playerPosCol < 0) {
                playerPosRow--;
                playerPosCol = -playerPosCol - 1;
            }
        }
    }

    // Check if the player landed on a snake or ladder
    private void checkSnakesAndLadders() {
        int[] snakeEnd = snakes[playerPosRow][playerPosCol];
        if (snakeEnd[0] != 0 || snakeEnd[1] != 0) {
            System.out.println("Player landed on a snake at " + board[playerPosRow][playerPosCol]);
            movePlayer(snakeEnd[0], snakeEnd[1]);
        }
        int[] ladderEnd = ladders[playerPosRow][playerPosCol];
        if (ladderEnd[0] != 0 || ladderEnd[1] != 0) {
            System.out.println("Player landed on a ladder at " + board[playerPosRow][playerPosCol]);
            movePlayer(ladderEnd[0], ladderEnd[1]);
        }
    }
	private void movePlayer(int dRow, int dCol){
		this.playerPosRow = dRow;
		this.playerPosCol = dCol;
	}
    // Simulate the game
    public void play() throws InterruptedException {
        Random rand = new Random();
        while (playerPosRow >= 0 && !(playerPosRow == 0 && playerPosCol == 0)) {
            int diceRoll = rand.nextInt(MAX_DICE_ROLL - MIN_DICE_ROLL + 1) + MIN_DICE_ROLL;
            System.out.println("Dice Rolled: " + diceRoll);

            movePlayer(diceRoll);

            if (playerPosRow < 0) {
                break;
            }

            checkSnakesAndLadders();

            System.out.println("Player is now on square " + board[playerPosRow][playerPosCol]);
            Thread.sleep(200);
        }

        System.out.println("Congratulations! Player has reached square 100 or beyond and won the game!");
    }

    // Main method to start the game
    public static void main(String[] args) {
        SnakesAndLadders game = new SnakesAndLadders();
        try {
            game.play();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
