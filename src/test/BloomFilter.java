package test;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.BitSet;
import java.security.NoSuchAlgorithmException;



public class BloomFilter
{
    BitSet bitSet;
    int num_hash;
    String[] algs;
    BigInteger bigint;

    public BloomFilter(int size, String...algs)
    {
        num_hash= algs.length;
        bitSet= new BitSet(size); // by default all bits are 0
       this.algs=algs;
    }
    public void add(String word)
    {
        byte[] wordBytes = word.getBytes(); // turn the word into bytes
        int i;
        for(i=0; i< this.algs.length; i++)
        {
            try {
                if (algs[i] != null) {
                    MessageDigest md = MessageDigest.getInstance(algs[i].trim());
                    byte[] hashword = md.digest(wordBytes);
                    bigint= new BigInteger(hashword);
                    int inti= bigint.intValue();
                    inti = Math.abs(inti);
                    int turn_on= inti % (bitSet.size());
                    bitSet.set(turn_on);
                }
            }
            catch (NoSuchAlgorithmException e)  // in case the algs are not names of hash
            {
                throw new RuntimeException("Error creating MessageDigest", e);
            }
        }
    }
    public boolean contains(String word)
    {
        byte[] wordBytes = word.getBytes(); // turn the word into bytes
        int i;
        for(i=0; i<num_hash; i++)
        {
            try {
                if (algs[i] != null) {
                    MessageDigest md = MessageDigest.getInstance(algs[i].trim());
                    byte[] hashword = md.digest(wordBytes);
                    bigint= new BigInteger(hashword);
                    int inti= bigint.intValue();
                    inti = Math.abs(inti);
                    int check_on= inti % (bitSet.size());
                    if(!(bitSet.get(check_on)))
                    {
                        return false;
                    }
                }
            }
            catch (NoSuchAlgorithmException e)  // in case the algs are not names of hash
            {
                throw new RuntimeException("Error creating MessageDigest", e);
            }
        }
        return true;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitSet.length(); i++) {
            if (bitSet.get(i)) {
                sb.append('1');
            } else {
                sb.append('0');
            }
        }
        return sb.toString();
    }

}
