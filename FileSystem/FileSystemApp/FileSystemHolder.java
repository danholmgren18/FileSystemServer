package FileSystemApp;

/**
* FileSystemApp/FileSystemHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from FileSystem.idl
* Tuesday, December 1, 2020 at 11:26:27 AM Eastern Standard Time
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
