import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class Combiner {

  public static void mergePDFString(List<String> fileNames, String filename) throws IOException {
    PDFMergerUtility m = new PDFMergerUtility();
    for ( String s : fileNames ) {
      m.addSource(s);
    }
    m.setDestinationFileName(filename);
    m.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
  }

  public static void mergePDFString(File[] files, String filename) throws IOException {
    PDFMergerUtility m = new PDFMergerUtility();
    for ( File s : files ) {
      m.addSource(s);
    }
    m.setDestinationFileName(filename);
    m.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
  }


  private static int getInt(String s1, Pattern p) {
    Matcher n = p.matcher(s1);
    if ( n.find() ) {
      return Integer.parseInt(n.group(1));
    }
    ;
    return -1;
  }

  public static void main(String[] args) throws IOException {
    if ( args == null ) {
      return;
    }

    List<String> filenames;
    String dest;

    if (args[0].equals("-d")){
      // directory mode
      String dir = args[1];
      dest = args[2];
      Pattern p = Pattern.compile("([0-9])(.*).pdf");
      File[] files = new File(dir).listFiles((file, s) -> s.matches("(.*).pdf"));
      assert files != null;
      Arrays.sort(files, Comparator.comparingInt(f -> getInt(f.getName(), p)));
      mergePDFString(files, dest);
      return;

    }

    if ( args[0].equals("1") ) {
      Pattern p = Pattern.compile("Lecture ([0-9]+)(.*)");
      String path
          =
          "C:\\Users\\jai_p\\Documents\\Downloads\\Imperial-Computing-Year-2-Notes\\50004 - Operating Systems";
      File[] files = new File(path).listFiles((file, s) -> s.matches(p.pattern()));
      assert files != null;

      filenames = new ArrayList<>();
      for ( File f : files ) {
        String filename = f.getName();
        Matcher n = p.matcher(filename);
        int lecNum = getInt(filename, p);
        if ( lecNum != -1 ) {
          String digit = n.group(1);
          filename = ( path + "\\" + filename + "\\" + "Lecture " + digit + ".pdf" );
          filenames.add(filename);
        }
      }
      filenames.sort(Comparator.comparingInt(f -> getInt(f, p)));
      dest = "OSNotes.pdf";
    }

    else {
      filenames = new ArrayList<>(Arrays.asList(args).subList(0, args.length - 1));
      dest = args[args.length - 2];
    }

    mergePDFString(filenames, dest);
  }
}
