package test;


public class Board
{
    private static int num_boards=0;
    private static Board instance;
    private int[][] board;
    private final int ROW=15;
    private final int COL=15;
    public Board()
    {
        this.board= new int[ROW][COL];
    }

    public static Board getBoard()
    {
        if(num_boards==0)
        {
            instance= new Board();
            num_boards+=1;
        }
        else {
            return instance;
        }
    }


}
