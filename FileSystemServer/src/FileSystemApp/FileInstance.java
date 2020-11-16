package FileSystemApp;

import java.io.File;

public class FileInstance {

    private File TheFile;
    private int version = 0;
    
    public FileInstance(File file) {
      TheFile = file;
    }
    
    public File getFile() {
      return TheFile;
    }

    public String getTitle() {
      return TheFile.getName();
    }
}
