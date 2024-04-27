package test;
import java.util.HashMap;
import java.util.LinkedHashSet;


public class LFU implements CacheReplacementPolicy
{
    private int key=1;
    private HashMap<String, Integer> keyToVal; // Map of key to value
    private HashMap<Integer, String> keyToWord; // Map of key to word
    private HashMap<Integer, Integer> keyToFreq; // Map of key to frequency
    private HashMap<Integer, LinkedHashSet<Integer>> freqToKeys; // Map of frequency to set of keys
    private int minFreq; // Minimum frequency in the cache

    public LFU()
    {
        keyToVal = new HashMap<>();
        keyToFreq = new HashMap<>();
        freqToKeys = new HashMap<>();
        keyToWord=new HashMap<>();
        minFreq = 0;
    }

    @Override
    public void add(String word)
    {
        if(!keyToVal.containsKey(word)) // if this word isn't already in the hash map
        {
            minFreq=1; // this is the new minmum
            keyToVal.put(word,key);
            keyToWord.put(key,word);
            keyToFreq.put(key,1);
            freqToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key); // if the freq doesnt exist will create a new one and if exist will add to the existing
            key+=1; // add another key because there is another value
        }
        else // this word i already in the hash map
        {
            int cur_key= keyToVal.get(word); // we want the key of the word we are looking for
            int freq=keyToFreq.get(cur_key); // the current freq of the word before adding another 1
            keyToFreq.remove(cur_key);
            keyToFreq.put(cur_key,freq+1); // update the frequencies
            freqToKeys.get(freq).remove(cur_key); // Remove the key from its previous frequency set
            freqToKeys.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(cur_key); // update the set
            if (freqToKeys.get(freq).isEmpty() && freq == minFreq)  // if the freq that we updated wat the min freq
            {
                minFreq++; // Update minimum frequency
            }
        }
    }
    @Override
    public String remove()
    {
        if(minFreq==0 || freqToKeys.isEmpty()) // means we didn't add anything yet
        {
            return null;
        }
        LinkedHashSet<Integer> keysWithMinFreq = freqToKeys.get(minFreq); // Get the set of keys with minimum frequency
        if (keysWithMinFreq == null || keysWithMinFreq.isEmpty())
        {
            return null; // If there are no keys with minimum frequency, return null
        }
        // get the key in first index of the set
        Integer keyToRemove = keysWithMinFreq.iterator().next();
        keyToVal.remove(keyToRemove);
        keyToFreq.remove(keyToRemove);
        if(keysWithMinFreq.size()==1) // if there is only 1 key with this frequency
        {
            int new_min= minFreq;
            freqToKeys.remove(keyToRemove);
            for (Integer freq : freqToKeys.keySet()) {
                new_min = Math.min(minFreq, freq);
            }
            minFreq= new_min;
        }
        else //there is more than one key
        {
            freqToKeys.get(minFreq).remove(keyToRemove); // Remove the key from the set associated with the minimum frequency
        }
        String word_rem;
        word_rem= keyToWord.get(keyToRemove);
        return word_rem;

    }
}
