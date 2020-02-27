package it.dromedian;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FolderZip {

	public static void main(String[] a) throws Exception {

	  zipFolder("c:\\a", "c:\\a.zip");
  }





   public static void zipFolder(String srcFolder, String destZipFile) throws Exception {
    ZipOutputStream zip = null;
    FileOutputStream fileWriter = null;

    fileWriter = new FileOutputStream(destZipFile);
    zip = new ZipOutputStream(fileWriter);

    addFolderToZip("", srcFolder, zip);
    zip.flush();
    zip.close();
  }

private static void  addFileToZip(String path, String srcFile, ZipOutputStream zip)
      throws Exception {

    File folder = new File(srcFile);
    if (folder.isDirectory()) {
      addFolderToZip(path, srcFile, zip);
    } else {
      byte[] buf = new byte[1024];
      int len;
      FileInputStream in = new FileInputStream(srcFile);
      zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
      while ((len = in.read(buf)) > 0) {
        zip.write(buf, 0, len);
      }
    }
  }

  private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
      throws Exception {
    File folder = new File(srcFolder);

    for (String fileName : folder.list()) {

    	System.out.println(fileName);

    	//filtro per estenzione
    	if (GlobalVars.aext.size()>0)
    	{
    	  if (!GlobalVars.aext.stream().anyMatch(x -> fileName.toUpperCase().indexOf(x.toUpperCase())>0))
    		  continue;

    	  System.out.println("match "+fileName);
    	}


    	if (path.equals("")) {


        addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
      } else {
        addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
      }


    }
  }


  public boolean containsCaseInsensitive(String s, List<String> l){
      return l.stream().anyMatch(x -> x.equalsIgnoreCase(s));
  }

}
