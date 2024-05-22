package test;
import java.util.HashMap;
import java.util.Map;

public class DictionaryManager
{
    public static DictionaryManager instance= null;
    public static Map<String, Dictionary> map = new HashMap<>();

    public static boolean query(String...args)
    {
        boolean flag=false;
        int numbooks= args.length;
        int i;
        for(i=0; i<numbooks-1;i++) // check if the books are not in the map yet
        {
            if(!map.containsKey(args[i]))
            {
                Dictionary newdic= new Dictionary(args[i]);
                map.put(args[i],newdic);
            }
            if(map.get(args[i]).query(args[numbooks-1]))
            {
                flag=true;
            }
        }
        if(flag)
        {
            return true;
        }
        return false;
    }
    public static boolean challenge(String...args)
    {
        boolean flag=false;
        int numbooks= args.length;
        int i;
        for(i=0; i<numbooks-1;i++) // check if the books are not in the map yet
        {
            if(!map.containsKey(args[i]))
            {
                Dictionary newdic= new Dictionary(args[i]);
                map.put(args[i],newdic);
            }
            if(map.get(args[i]).challenge(args[numbooks-1]))
            {
                flag=true;
            }
        }
        if(flag)
        {
            return true;
        }
        return false;
    }
    public static DictionaryManager get()
    {
        if(instance==null) // we didnt make dictionry manger yet
        {
            instance= new DictionaryManager();
            return instance;
        }
        return instance;
    }
    public int getSize()
    {
        return map.size();
    }
}
