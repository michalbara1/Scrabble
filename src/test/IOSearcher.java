package test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class IOSearcher
{
    public static boolean search(String word, String...fileNames)
    {
        int num_files;
        num_files= fileNames.length;
        for(int i=0; i<num_files; i++)
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileNames[i])))
            {
                String line;
                while((line= reader.readLine())!=null) // means we can keep reading lines
                {
                    if (line.contains(word))
                    {
                        return true;
                    }
                }
                // buffer reader closes automatically
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }
}
