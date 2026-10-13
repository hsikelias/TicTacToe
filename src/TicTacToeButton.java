import javax.swing.JButton;

public class TicTacToeButton extends JButton{
    private final int row;
    private final int col;

    public TicTacToeButton(int row, int col){
        super("");
        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}