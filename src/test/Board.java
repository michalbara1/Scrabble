package test;
import java.util.ArrayList;
import test.Tile;
import test.Word;

public class Board {
    static boolean first_word=false;
    private static int num_boards = 0;
    private static Board instance;
    private Tile[][] board;
    private final int ROW = 15;
    private final int COL = 15;
    private ArrayList<Word> words;
    int[][] score = { //enum
            {9, 1, 1, 2, 1, 1, 1, 9, 1, 1, 1, 2, 1, 1, 9},
            {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
            {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
            {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
            {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
            {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
            {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
            {9, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 9},
            {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
            {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
            {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
            {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
            {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
            {9, 1, 1, 2, 1, 1, 1, 9, 1, 1, 1, 2, 1, 1, 9}
    };

    public Board() {
        this.board = new Tile[ROW][COL];
        this.words = new ArrayList<>();
    }

    public static Board getBoard() {
        if (num_boards == 0) {
            instance = new Board();
            num_boards += 1;
            return instance;
        } else {
            return instance;
        }
    }

    public Tile[][] getTiles() {
        return board;

    }

    public Boolean boardLegal(Word w)
    {
        int flag_cross=0;
        int i,j,m;
        if(w.vertical) {
            if (w.row < 0) {
                return false;
            }
            if (w.col < 0) {
                return false;
            }
            if (w.row + w.tiles.length > 15) {
                return false;
            }
            j = w.col;
            for (i = w.row, m = 0; i < w.tiles.length + w.row; i++, m++) {
                if (board[i][j] != null) {
                    flag_cross = 1; //means we crossed another word
                    if (w.tiles[m] != null) //checks if we didnt replace a letter there
                    {
                        return false;
                    }
                    break;

                }
                if (j - 1 > 0 && board[i][j - 1] != null || j + 1 < 15 && board[i][j + 1] != null)// to check if there is a tile near the word so its legale
                {
                    flag_cross = 1;
                    break;
                }
                // we want to check if its the first word
                if (!first_word) {
                    if (i == 7 && j == 7) // means we passed the center
                    {
                        return true;
                    }
                }
            }
            if (flag_cross == 0) // means we didnt cross any word
            {
                return false;
            }
            if (flag_cross == 1)
            {
                return true;
            }
        }
        else // word is not vertical
        {
            if(w.col<0)
            {
                return false;
            }
            if(w.row<0)
            {
                return false;
            }
            if(w.col+w.tiles.length>15)
            {
                return false;
            }
            i=w.row;
            for(j= w.col,m=0; j<w.tiles.length +w.col; j++,m++)
            {
                if(board[i][j]!=null)
                {
                    flag_cross=1;
                    if(w.tiles[m]!=null)
                    {
                        return false;
                    }
                    break;
                }
                if(i+1<15 && board[i+1][j]!=null || i-1 >0 && board[i-1][j]!=null) // to check if there are letters near
                {
                    flag_cross=1;
                    break;
                }
                if(!first_word)
                {
                    if(i==7 && j==7) // the middle goes through 7
                    {
                        return true;
                    }
                }
            }
            if(flag_cross==0)
            {
                return false;
            }
            if(flag_cross==1)
            {
                return true;
            }

        }
        return true;
    }
    public Boolean dictionaryLegal(Word w)
    {
        return true;
    }
    public  ArrayList<Word> getWords(Word w)
    {
        int i,j,start_w, end_w,c,d,m;
        ArrayList<Word> new_words= new ArrayList<>();
        if(!w.vertical)
        {
            i=w.row;
            j=w.col;
            // before the loop we want to see if something from one of the sides became a word
            if(board[i][j-1]!=null && j-1>=0) // only for the first time we want to check it
            {
                start_w=j-1;
                end_w=j;
                while(board[i][start_w]!=null)
                {
                    start_w-=1;
                }
                start_w+=1;
                while(board[i][end_w]!=null)
                {
                    end_w+=1;
                }
                Tile[] check_word= new Tile[end_w- start_w];
                for(c=start_w,d=0; c<end_w; c++,d++)
                {
                    check_word[d]=board[c][j];
                }
                Word wordi= new Word(check_word, start_w, end_w,false);
                check_exist(wordi, new_words);
            }
            if(board[i][j+w.tiles.length+1]!=null && j+w.tiles.length+1<15)
            {
                start_w=j-1;
                end_w=j;
                while(board[i][start_w]!=null && start_w>=0)
                {
                    start_w-=1;
                }
                start_w+=1;
                while(board[i][end_w]!=null && end_w<15)
                {
                    end_w+=1;
                }
                Tile[] check_word= new Tile[end_w- start_w];
                for(c=start_w,d=0; c<end_w; c++,d++)
                {
                    check_word[d]=board[c][j];
                }
                Word wordi= new Word(check_word, start_w, end_w,false);
                check_exist(wordi, new_words);

            }
            for(j= w.col,m=0; j<w.tiles.length+w.col; j++,m++)
            {
                if(board[i+1][j]!= null && i+1<15)
                {
                    Word wordi= find_word(i,j, true);
                    check_exist(wordi, new_words);
                    if(w.tiles[m]==null)
                    {
                        w.tiles[m] = board[i][j];
                        check_exist(w,  new_words);
                    }
                }
                if(board[i-1][j]!=null && i-1>=0)
                {
                    Word wordi= find_word(i-1,j, true);
                    check_exist(wordi, new_words);
                    if(w.tiles[m]==null)
                    {
                        w.tiles[m] = board[i][j];
                        check_exist(w,  new_words);
                    }
                }

            }
            boolean flag_standalone=true; // if this word is not near any word and is full we want to enter her in the board
            for(i=0; i<w.tiles.length; i++)
            {
                if(w.tiles[i]== null)
                {
                    flag_standalone=false;
                    break;
                }
            }
            if(flag_standalone)
            {
                check_exist(w, new_words);
            }

        }
        else // w is  vertical
        {
            i=w.row;
            j= w.col;
            // before the loop we want to see if it comppletes to a word on the sides
            if(board[i-1][j]!=null && i-1>=0)
            {
                start_w=i-1;
                end_w=i;
                while(board[start_w][j]!=null && start_w>=0)
                {
                    start_w-=1;
                }
                start_w+=1;
                while(board[end_w][j]!=null && end_w<15)
                {
                    end_w+=1;
                }
                Tile[] check_word= new Tile[end_w- start_w];
                for(c=start_w,d=0; c<end_w; c++,d++)
                {
                    check_word[d]=board[c][j];
                }
                Word wordi= new Word(check_word, start_w, end_w,true);
                check_exist(wordi, new_words);

            }
            if(board[i+w.tiles.length+1][j]!=null && i+w.tiles.length+1<15)
            {
                start_w=i-1;
                end_w=i;
                while(board[start_w][j]!=null && start_w>=0)
                {
                    start_w-=1;
                }
                start_w+=1;
                while(board[end_w][j]!=null && end_w<15)
                {
                    end_w+=1;
                }
                Tile[] check_word= new Tile[end_w- start_w];
                for(c=start_w,d=0; c<end_w; c++,d++)
                {
                    check_word[d]=board[c][j];
                }
                Word wordi= new Word(check_word, start_w, end_w,true);
                check_exist(wordi, new_words);
            }
            for(i=w.row,m=0; i< w.tiles.length+w.row ; i++,m++)
            {
                if(board[i][j-1]!=null && i-1>=0)
                {
                    start_w=j-1;
                    Word wordi=find_word_vert(start_w, i,false);
                    check_exist(wordi,  new_words);
                    if(w.tiles[m]==null)
                    {
                        w.tiles[m] = board[i][j];
                        check_exist(w, new_words);
                    }


                }
                if(board[i][j+1]!=null && j+1<15)
                {
                    start_w=j+1;
                    Word wordi=find_word_vert(start_w, i,false);
                    check_exist(wordi,  new_words);
                    if(w.tiles[m]==null)
                    {
                        w.tiles[m] = board[i][j];
                        check_exist(w,new_words);
                    }

                }
            }
            boolean flag_standalone=true; // if this word is not near any word and is full we want to enter her in the board
            for(i=0; i<w.tiles.length; i++)
            {
                if(w.tiles[i]== null)
                {
                    flag_standalone=false;
                    break;
                }
            }
            if(flag_standalone)
            {
                check_exist(w, new_words);
            }
        }
        return new_words;
    }
    private Word find_word(int start_w, int j, boolean verti)
    {
        int c,d;
        int end_w=start_w+1;
        while(board[start_w][j]!=null && start_w>=0) // we want to find where the word start
        {
            start_w-=1;
        }
        start_w+=1;
        while(board[end_w][j]!=null && end_w<15)
        {
            end_w+=1;
        }
        Tile[] check_word= new Tile[end_w- start_w];
        for(c=start_w,d=0; c<end_w; c++,d++)
        {
            check_word[d]=board[c][j];
        }
        Word wordi= new Word(check_word, start_w, j,verti);
        return wordi;

    }
    private Word find_word_vert(int start_w, int i, boolean verti)
    {
        int c,d;
        int end_w=start_w+1;
        while(board[i][start_w]!=null && start_w>=0) // we want to find where the word start
        {
            start_w-=1;
        }
        start_w+=1;
        while(board[i][end_w]!=null && end_w<15)
        {
            end_w += 1;
        }
        Tile[] check_word= new Tile[end_w- start_w];
        for(c=start_w,d=0; c<end_w; c++,d++)
        {
            check_word[d]=board[i][c];
        }
        Word wordi= new Word(check_word, i, start_w,verti);
        return wordi;

    }
   private void check_exist(Word wordi, ArrayList<Word> new_words) {
        int i;
        boolean flag= true;
        boolean found = true;
        boolean added = true;
        for (Word word : words) {
            flag=true;
            if (word.tiles.length == wordi.tiles.length)
            {
                if (word.col == wordi.col && word.row == wordi.row && word.vertical == wordi.vertical) {
                    for (i = 0; i < wordi.tiles.length; i++)
                    {
                        if (word.tiles[i] != wordi.tiles[i])
                        {
                            flag = false;
                            break;
                        }
                    }
                    if(flag) // this means we found the word in the words
                    {
                        found=true;
                        break;
                    }
                    if(!flag)
                    {
                        found= false;
                    }
                }
                else
                {
                    found=false;
                }
            }
            else
            {
                found=false;
            }

        }
        if(new_words.size()==0)
        {
            added=false;
        }
        for (Word newWord : new_words) {
            flag=true;
            if (newWord.tiles.length == wordi.tiles.length) {

                if (newWord.col == wordi.col && newWord.row == wordi.row && newWord.vertical == wordi.vertical) {
                    for (i = 0; i < wordi.tiles.length; i++)
                    {
                        if (newWord.tiles[i] != wordi.tiles[i]) {
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                    {
                        added=true;
                        break;
                    }
                    if(!flag)
                    {
                        added=false;
                    }
                }
                else
                {
                    added=false;
                }
            }
            else
            {
                added=false;
            }

        }
        if (!found && !added)
        {
            new_words.add(wordi);

        }

    }

    public int getScore(Word w)
    {
        int i,j,m, sum=0, word_score;
        if(w.vertical)
        {
            i= w.row;
            j= w.col;// beacuse it starts from 0 and the board doesnt
            word_score=totalw_score(w);
            for(i=w.row, m=0; i< w.tiles.length+w.row; i++,m++)
            {
                if(score[i][j]==9 )
                {
                    sum += word_score *2;
                    sum += w.tiles[m].score;
                }
                else if(score[i][j]==4)
                {
                    sum += word_score;
                    sum += w.tiles[m].score;
                }
                else if(score[i][j]==5) // this is the star
                {
                    if(first_word==false)
                    {
                        sum += word_score;
                        sum += w.tiles[m].score;
                    }
                    else
                    {
                        sum+=w.tiles[m].score; // this gives score only to the first word
                    }


                }
                else
                {
                    sum += w.tiles[m].score * score[i][j];
                }

            }
        }
        else // not vertical
        {
            i= w.row;
            j= w.col;
            word_score= totalw_score(w);
            for(j=w.col,m=0; j<w.tiles.length+w.col; j++, m++)
            {
                if(score[i][j]==9 )
                {
                    sum += word_score * 2;
                    sum += w.tiles[m].score;
                }
                else if(score[i][j]==4)
                {
                    sum += word_score;
                    sum += w.tiles[m].score;
                }
                else if(score[i][j]==5) // this is the star
                {
                    if(first_word==false)
                    {
                        sum += word_score;
                        sum += w.tiles[m].score;
                    }
                    else
                    {
                        sum+=w.tiles[m].score;
                    }
                }
                else
                {
                    sum += w.tiles[m].score * score[i][j];
                }

            }

        }
        return sum;
    }
    private int totalw_score(Word w)
    {
        int score=0;
        int i;
        for(i=0;i< w.tiles.length; i++)
        {
            score+=w.tiles[i].score;
        }
        return score;
    }
    public int tryPlaceWord(Word w)
    {
        int totalscore=0,i,j,m;
        ArrayList<Word> addedwords;
        if(boardLegal(w))
        {
            //put w on the board
            if(w.vertical)
            {
                j=w.col;
                for(i=w.row,m=0; i<w.row+w.tiles.length; i++,m++)
                {
                    if(w.tiles[m]==null) // meand there is alredy domething on the board
                    {
                        continue;
                    }
                    else
                    {
                        board[i][j] = w.tiles[m];
                    }
                }
            }
            if(!w.vertical)
            {
                i=w.row;
                for(j=w.col,m=0; j<w.col+w.tiles.length; j++,m++)
                {
                    if(w.tiles[m]== null)
                    {
                        continue;
                    }
                    else
                    {
                        board[i][j]= w.tiles[m];
                    }
                }
            }
            words.add(w);
            addedwords= getWords(w);
            for(Word word: addedwords)
            {
                if(!dictionaryLegal(word))
                {
                    if(w.vertical)
                    {
                        j=w.col;
                        for(i=w.row,m=0; i<w.row+w.tiles.length; i++,m++)
                        {
                            if(w.tiles[m]== null) // means there was a letter there before and we dont want to delete it
                            {
                                continue;
                            }
                            else
                            {
                                board[i][j]= null;
                            }

                        }
                    }
                    if(!w.vertical)
                    {
                        i=w.row;
                        for(j=w.col,m=0; j<w.col+w.tiles.length; j++,m++)
                        {
                            if(w.tiles[m]== null)
                            {
                                continue;
                            }
                            else
                            {
                                board[i][j]= null;
                            }

                        }
                    }
                    words.remove(w); // delete the w from words since its not legeale because bring not leagle words
                    for (Word addedWord : addedwords)  // remove all the words i alredy added to words
                    {
                        words.remove(addedWord);
                    }
                    return 0;
                }
                totalscore+= getScore(word);
                words.add(word);
            }
            //the word itself is not in added words so we calculate her here
            totalscore+= getScore(w);
            if(!first_word) // if this was the first word
            {
                first_word=true;
            }
            return totalscore;

        }
        return 0;
    }

}

