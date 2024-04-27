package test;
import java.util.Random;

public  class Tile {
    public final int score;
    public final char letter;
    private Tile(int score, char letter) {
        this.score = score;
        this.letter = letter;
    }
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Tile tile = (Tile) object;
        return score == tile.score && letter == tile.letter;
    }
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), score, letter);
    }
    public static  class Bag
    {
        private static Bag instance= null;
        private static int num_bags =0;
        int[] amount= new int[26];
        Tile[] tiles= new Tile[26];
        private Bag()
        {
            amount = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            for (int i = 0; i < 26; i++) {
                char letter = (char) ('A' + i); // Calculate the letter based on its position in the alphabet
                int score = getScore(letter); // Get the score for the letter
                tiles[i] = new Tile(score, letter); // Create a new Tile object with the score and letter
            }
        }
        private int getScore(char letter)  // for the constructor tiles
        {
            switch (letter) {
                case 'A': case 'E': case 'I': case 'O': case 'U': case 'L': case 'N': case 'S': case 'T': case 'R':
                    return 1;
                case 'D': case 'G':
                    return 2;
                case 'B': case 'C': case 'M': case 'P':
                    return 3;
                case 'F': case 'H': case 'V': case 'W': case 'Y':
                    return 4;
                case 'K':
                    return 5;
                case 'J': case 'X':
                    return 8;
                case 'Q': case 'Z':
                    return 10;
                default:
                    return 0; // Default score for unknown letters
            }

        }
        public Tile getRand()
        {
            int empty=1;
            for(int i=0; i<26; i++)
            {
                if(amount[i]!=0)
                {
                    empty=0;
                    break;
                }
            }
            if(empty==1)
            {
                return null;
            }
            int randomIndex = (int) (Math.random() * 26);
            while(amount[randomIndex]==0) // as long as we land on tile with amount 0 we want to get to other
            {
                randomIndex = (int) (Math.random() * 26);
            }
            //if(amount[randomIndex]!= 0) // we got to a tile that exists
            {
                amount[randomIndex]-=1;
                return tiles[randomIndex];
            }
            //return tiles[randomIndex];
        }
        public Tile getTile(char c)
        {
            char leti =c;
            if (c >= 'A' && c <= 'Z')
                if (amount[leti-'A']!=0)
               {
                amount[leti-'A']-=1;
                return tiles[leti-'A'];
               }

            return null;

        }
        public void put(Tile t)
        {
            int [] max_amount = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            char letter= t.letter;
            if (letter >= 'A' && letter <= 'Z')
            {
                int allowed_amount = max_amount[letter - 'A'];
                if (amount[letter - 'A'] + 1 <= allowed_amount) {
                    amount[letter - 'A'] += 1;
                }
            }
        }
        public int size()
        {
            int sum=0;
            for(int i=0; i<26; i++)
            {
                sum+= amount[i];
            }
            return sum;
        }
        public int[] getQuantities() { //clone
            int[] copy_amount = new int[26];
            for (int i = 0; i < 26; i++) {
                copy_amount[i] = this.amount[i];
            }
            return copy_amount;
        }
        public static Bag getBag()
        {
            if(num_bags== 0) // if instance=null
            {
                instance= new Bag();
                num_bags+=1;
                return instance;
            }
            else //num_bags=1
            {
                return instance;
            }
        }


    }


}