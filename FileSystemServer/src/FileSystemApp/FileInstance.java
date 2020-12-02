package FileSystemApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileInstance {

    private String fileTitle;
    private File zehFile;
    private int version = 0;
    private boolean isLocked = false;
    
    
    public FileInstance(File file) {
      zehFile = file;
      fileTitle = zehFile.getName();
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

    public static String getContents(FileInstance fileInstance) {
      // TODO Auto-generated method stub
      return null;
    }

}
