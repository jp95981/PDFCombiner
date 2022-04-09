import java.io.File;
import java.util.Comparator;
import java.util.Objects;

public class LectureNote {
  String filename;
  int lectureNumber;

  LectureNote(String f, int s){
    this.filename = f;
    this.lectureNumber = s;
  }

  @Override
  public boolean equals(Object o) {
    if ( this == o ) {
      return true;
    }
    if ( o == null || getClass() != o.getClass() ) {
      return false;
    }
    LectureNote that = (LectureNote) o;
    return this.lectureNumber == ( (LectureNote) o ).lectureNumber;
  }

  @Override
  public int hashCode() {
    return Objects.hash(filename, lectureNumber);
  }

  @Override
  public String toString() {
    return "LectureNote{" +
        "filename='" + filename + '\'' +
        ", lectureNumber=" + lectureNumber +
        '}' + "\n";
  }
}

class noteComparator implements Comparator<LectureNote>{
  @Override
  public int compare(LectureNote l1, LectureNote l2) {
    return l1.lectureNumber - l2.lectureNumber;
  }
}
