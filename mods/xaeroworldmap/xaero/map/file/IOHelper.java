package xaero.map.file;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class IOHelper {
   public static void readToBuffer(byte[] buffer, int count, DataInputStream input) throws IOException {
      int currentTotal = 0;

      while (currentTotal < count) {
         int readCount = input.read(buffer, currentTotal, count - currentTotal);
         if (readCount == -1) {
            throw new EOFException();
         }

         currentTotal += readCount;
      }
   }
}
