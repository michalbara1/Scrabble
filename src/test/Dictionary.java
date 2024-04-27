package test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary
{
    CacheManager cash_lru;
    CacheManager cash_lfu;
    BloomFilter bloomFilter;
    String [] fileNames;
    public Dictionary(String...fileNames)
    {
        this.fileNames= fileNames;
        CacheReplacementPolicy crp_lru= new LRU();
        CacheReplacementPolicy crp_lfu= new LFU();
        cash_lru= new CacheManager(400,crp_lru);
        cash_lfu= new CacheManager(100,crp_lfu);
        bloomFilter= new BloomFilter(256,"MD5","SHA1");
        int num_files= fileNames.length;
        for(int i=0; i<num_files; i++)
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileNames[i])))
            {
                String line;
                while((line=reader.readLine())!=null)
                {
                    String[] words= line.split("\\s+");
                    for(String word: words)
                    {
                        bloomFilter.add(word);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public boolean query(String word)
    {
        if(cash_lru.query(word)==true)
        {
            return true;
        }
        else if(cash_lfu.query(word)==true)
        {
            return false;
        }
        else if(bloomFilter.contains(word)==true)
        {
            cash_lru.add(word);
            return true;
        }
        else
        {
            cash_lfu.add(word);
            return false;
        }
    }
    public boolean challenge(String word)
    {
        if(IOSearcher.search(word,fileNames))//==true
        {
            cash_lru.add(word);
            return true;
        }
        else
        {
            cash_lfu.add(word);
            return false;
        }
    }
}

