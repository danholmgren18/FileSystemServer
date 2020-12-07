package FileSystemApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class FileInstance {

  private String fileTitle;
  private File zehFile;
  private int version;
  private int amntOfPeopleReading;

  private boolean isLocked = false;

  public FileInstance(File file, int ver, int peopleReading) {
    zehFile = file;
    fileTitle = zehFile.getName();
    this.version = ver;
    this.amntOfPeopleReading = peopleReading;
  }

  public FileInstance(File file) {
    zehFile = file;
    fileTitle = zehFile.getName();
    this.version = 0;
    this.amntOfPeopleReading = 0;
  }

  public int getAmntOfPeopleReading() {
    return amntOfPeopleReading;
  }

  public void startReading() {
    amntOfPeopleReading++;
  }

  public void stopReading() {
    amntOfPeopleReading--;
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
      while (sc.hasNextLine() && currLineNum < lineNumber) {
        String line = sc.nextLine();
        currLineNum++;
      }
      sc.close();
      return sc.nextLine();
    } catch (FileNotFoundException e) {
      return "Error while trying to find the file";
    }
  }

  public String getContents() {
    Scanner sc;
    String line = "";
    try {
      sc = new Scanner(zehFile);
      while (sc.hasNextLine()) {
        line = line + '\n' + sc.nextLine();
      }
      sc.close();
      return line;
    } catch (FileNotFoundException e) {
      return "Error while trying to find the file";
    }
  }

  public boolean setContents(String newContents) {
    try {
      FileWriter fw = new FileWriter(fileTitle, false);
      fw.write(newContents);
      return true;
    } catch (Exception e) {
      System.out.println("Could not overwrite file: " + fileTitle + "\n encountered: " + e.getClass());
      e.printStackTrace();
      return false;
    }
  }

}
