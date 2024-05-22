package test;
import java.io.*;
import java.util.Scanner;


public class BookScrabbleHandler implements ClientHandler
{

    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(inFromclient));
            writer = new BufferedWriter(new OutputStreamWriter(outToClient));
            // Read the first character to determine if it's a query or a challenge
            char firstChar = (char) reader.read();
            boolean isQuery = (firstChar == 'Q');
            // Read the book names separated by commas
            String[] bookNames = null; // Declare the array variable

            String line = reader.readLine();
            if (line != null) {
                String[] books = line.split(",");
                bookNames = new String[books.length];
                // Copy each book name from the books array to the bookNamesArray
                for (int i = 0; i < books.length; i++) {
                    bookNames[i] = books[i].trim(); // Trim any leading/trailing spaces
                }
            }

            if (isQuery) {
                if (DictionaryManager.query(bookNames)) {
                    writer.write("true");
                    writer.write("\n"); // Add a newline character to indicate the end of the response
                    writer.flush(); // Flush the writer to ensure all data is sent
                } else {
                    writer.write("false");
                    writer.write("\n"); // Add a newline character to indicate the end of the response
                    writer.flush(); // Flush the writer to ensure all data is sent
                }
            } else // its a challenge
            {
                if (DictionaryManager.challenge(bookNames)) {
                    writer.write("true");
                    writer.write("\n"); // Add a newline character to indicate the end of the response
                    writer.flush(); // Flush the writer sh the writer to ensure all data is sent
                } else {
                    writer.write("false");
                    writer.write("\n"); // Add a newline character to indicate the end of the response
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            try {
                if (outToClient != null) {
                    outToClient.close(); // Close the input stream
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inFromclient != null) {
                    inFromclient.close(); // Close the output stream
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader != null) {
                    reader.close(); // Close the output stream
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (writer != null) {
                    writer.close(); // Close the output stream
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void close()
    {
       ///
    }
}
