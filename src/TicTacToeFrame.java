import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TicTacToeFrame extends JFrame {


    private static final int SIZE = 3;
    private static final int MOVES_FOR_WIN = 5;
    private static final int MOVES_FOR_TIE = 7;

    private final String[][] board = new String[SIZE][SIZE];
    private final TicTacToeButton[][] buttons =
            new TicTacToeButton[SIZE][SIZE];

    private final JLabel statusLabel =
            new JLabel("", SwingConstants.CENTER);

    private String player;
    private int moveCount;
    private boolean playing;

    public TicTacToeFrame(){


        super("Coool Tic Tac Toe ");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ActionListener boardListener = event ->{
            TicTacToeButton button =
                    (TicTacToeButton) event.getSource();

            makeMove(button.getRow(), button.getCol());
        };

        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++)
            {
                buttons[row][col] = new TicTacToeButton(row, col);
                buttons[row][col].setFont(
                        new Font("SansSerif", Font.BOLD, 60));
                buttons[row][col].addActionListener(boardListener);
                boardPanel.add(buttons[row][col]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(event -> confirmQuit());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(quitButton);
        add(bottomPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent event){
                confirmQuit();
            }
        });

        startNewGame();

        setSize(450, 500);
        setLocationRelativeTo(null);
    }

    private void startNewGame(){
        player = "X";
        moveCount = 0;
        playing = true;

        for (int row = 0; row < SIZE; row++){


            for (int col = 0; col < SIZE; col++){
                board[row][col] = " ";
                buttons[row][col].setText("");
            }
        }

        statusLabel.setText("Player X's turn");
    }

    private void makeMove(int row, int col){
        if (!playing){
            return;
        }

        if (!board[row][col].equals(" ")){
            JOptionPane.showMessageDialog(
                    this,
                    "That square is already taken. Player "
                            + player + ", choose an empty square.",
                    "Illegal Move",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        board[row][col] = player;
        buttons[row][col].setText(player);
        moveCount++;

        if (moveCount >= MOVES_FOR_WIN && isWin(player)){
            finishGame("Player " + player + " wins!", "Win");
            return;
        }

        if (moveCount >= MOVES_FOR_TIE && isTie()){
            if (moveCount == SIZE * SIZE){
                finishGame("It's a tie! The board is full.",
                        "Full Board Tie");
            }


            else{
                finishGame(
                        "It's a tie! Neither player can win,\n"
                                + "even though the board is not full.",
                        "Not Full Board Tie");
            }

            return;
        }

        player = player.equals("X") ? "O" : "X";
        statusLabel.setText("Player " + player + "'s turn");
    }

    private boolean isWin(String mark) {



        for (int index = 0; index < SIZE; index++){
            if (board[index][0].equals(mark)
                    && board[index][1].equals(mark)
                    && board[index][2].equals(mark)){
                return true;
            }

            if (board[0][index].equals(mark)
                    && board[1][index].equals(mark)
                    && board[2][index].equals(mark)){
                return true;
            }
        }

        return (board[0][0].equals(mark)
                && board[1][1].equals(mark)
                && board[2][2].equals(mark))
                ||
                (board[0][2].equals(mark)
                        && board[1][1].equals(mark)
                        && board[2][0].equals(mark));
    }

    private boolean isTie(){



        for (int index = 0; index < SIZE; index++){
            if (!isBlocked(board[index][0],
                    board[index][1], board[index][2])){
                return false;
            }

            if (!isBlocked(board[0][index],
                    board[1][index], board[2][index])){
                return false;
            }
        }

        return isBlocked(board[0][0], board[1][1], board[2][2])
                && isBlocked(board[0][2], board[1][1], board[2][0]);
    }

    private boolean isBlocked(String first, String second, String third){
        boolean hasX = first.equals("X")
                || second.equals("X")
                || third.equals("X");

        boolean hasO = first.equals("O")
                || second.equals("O")
                || third.equals("O");

        return hasX && hasO;
    }

    private void finishGame(String message, String title){
        playing = false;
        statusLabel.setText(title);

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE);

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Would you like to play another game?",
                "Play Again",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION){
            startNewGame();
        }
        else{
            quitGame();
        }
    }

    private void confirmQuit(){
        int answer = JOptionPane.showConfirmDialog(
                this,
                "sure you want to quit?",
                "quit",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION){
            quitGame();
        }
    }

    private void quitGame(){
        playing = false;

        JOptionPane.showMessageDialog(
                this,
                "Thanks for playing!",
                "Goodbye",
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}