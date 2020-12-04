package FileSystemApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileInstance {

    private String fileTitle;
    private File zehFile;
    private int version = 0;
    private int amntOfPeopleReading = 0;
    

    private boolean isLocked = false;
    
    
    public FileInstance(File file) {
      zehFile = file;
      fileTitle = zehFile.getName();
    }
    public int getAmntOfPeopleReading() {
      return amntOfPeopleReading;
    }
    public void startReading() {
      amntOfPeopleReading ++;
    }
    public void stopReading() {
      amntOfPeopleReading --;
    }
    public boolean isLocked() {
      return isLocked;
    }
    public void setLocked(boolean isLocked) {
      this.isLocked = isLocked;
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
    
    public String readLine(int lineNumber) {
      Scanner sc;
      try {
        sc = new Scanner(zehFile);
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

    public String getContents() {
      Scanner sc;
      String line = "";
      try {
        sc = new Scanner(zehFile);
        while(sc.hasNextLine()) {
          line = line + '\n' + sc.nextLine();
        }
        sc.close();
        return line;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      return null;
    }

}
