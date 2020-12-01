package FileSystemApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileInstance {

    private String fileTitle;
    private int version = 0;
    private boolean isLocked = false;
    
    public boolean isLocked() {
      return isLocked;
    }
    public void setLocked(boolean isLocked) {
      this.isLocked = isLocked;
    }
    public FileInstance(String title) {
      fileTitle = title;
    }
    public void updateVersion() {
      version++;
    }
    public int getVersion() {
      return version;
    }
    public String getTitle() {
      return fileTitle;
    }
    
    public static String readLine(File file, int lineNumber) {
      Scanner sc;
      try {
        sc = new Scanner(file);
        int currLineNum = 0;
        while(sc.hasNextLine() && currLineNum < lineNumber) {
          String line = sc.nextLine();
          currLineNum++;
        }
        sc.close();
        return sc.nextLine();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      return null;
    }

}
