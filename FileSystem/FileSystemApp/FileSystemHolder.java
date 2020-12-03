package FileSystemApp;

/**
* FileSystemApp/FileSystemHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from FileSystem.idl
* Thursday, December 3, 2020 8:47:16 AM EST
*/

public final class FileSystemHolder implements org.omg.CORBA.portable.Streamable
{
  public FileSystemApp.FileSystem value = null;

  public FileSystemHolder ()
  {
  }

  public FileSystemHolder (FileSystemApp.FileSystem initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = FileSystemApp.FileSystemHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    FileSystemApp.FileSystemHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return FileSystemApp.FileSystemHelper.type ();
  }

}
