package xaero.common.server.level;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class LevelMapPropertiesIO {
   public static final String FILE_NAME = "xaeromap.txt";

   public void load(Path file, LevelMapProperties dest) throws IOException {
      BufferedReader reader = null;

      try {
         reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile()), "UTF8"));
         dest.read(reader);
      } catch (UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      } finally {
         if (reader != null) {
            reader.close();
         }
      }
   }

   public void save(Path file, LevelMapProperties dest) throws IOException {
      try (
         BufferedOutputStream bufferedOutput = new BufferedOutputStream(new FileOutputStream(file.toFile()));
         PrintWriter writer = new PrintWriter(new OutputStreamWriter(bufferedOutput, StandardCharsets.UTF_8));
      ) {
         dest.write(writer);
      }
   }
}
