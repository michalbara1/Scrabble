package test;
import java.util.HashSet;

public class CacheManager
{
    int size;
    CacheReplacementPolicy crp;
    HashSet<String> words;

    public CacheManager(int size, CacheReplacementPolicy crp)
    {
        this.size = size;
        this.crp = crp;
        words= new HashSet<>();
    }
    public boolean query(String word)
    {
        if(words.contains(word))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void add(String word)
    {
        String wordi_rem;
        if(words.size()+1> size)
        {
            wordi_rem= crp.remove();
            words.remove(wordi_rem);
            crp.add(word);
        }
        else
        {
            crp.add(word);
        }
        words.add(word);
    }



}
