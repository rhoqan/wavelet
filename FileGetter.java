import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class FileGetter {
    static List<File> result = new ArrayList<>();  

	static List<File> getFiles(File start) throws IOException {
	  File f = start;
    if(f.isDirectory()) {
      File[] paths = f.listFiles();
      for(File subfile: paths) {
        if(subfile.isDirectory()) {
          getFiles(subfile);
        }
        else {
          result.add(subfile);
        }
      }
    }
    else {
      result.add(start);
    }
	  return result;
	}
}

