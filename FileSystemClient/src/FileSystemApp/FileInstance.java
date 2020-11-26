package FileSystemApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileInstance {

    private File TheFile;
    private int version = 0;
    private int amntOfPeopleReading = 0;
    
    public FileInstance(File file) {
      TheFile = file;
    }
    
    public int getVersion() {
      return version;
    }
    public File getFile() {
      return TheFile;
    }
    public String getTitle() {
      return TheFile.getName();
    }
    
    public void closeRead() {
   
      amntOfPeopleReading--;
    }
    
    public String readLine(int lineNumber) {
      Scanner sc;
      try {
        sc = new Scanner(TheFile);
        int currLineNum = 0;
        while(sc.hasNextLine() && currLineNum < lineNumber) {
          String line = sc.nextLine();
          currLineNum++;
        }
        sc.close();
        amntOfPeopleReading++;
        return sc.nextLine();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      return null;
    }

}
