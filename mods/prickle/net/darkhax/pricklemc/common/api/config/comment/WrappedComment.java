package net.darkhax.pricklemc.common.api.config.comment;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import net.darkhax.pricklemc.common.api.annotations.Value;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public class WrappedComment extends Comment {
   public static final ICommentResolver RESOLVER = new WrappedComment.WrappedCommentResolver(80, true);

   public WrappedComment(String text, int lineLength, boolean pad) {
      super(wrap(text, lineLength, pad));
   }

   private static String[] wrap(String text, int lineLength, boolean pad) {
      if (text.length() <= lineLength) {
         return new String[]{text};
      }

      List<String> lines = new LinkedList<>();
      String[] words = text.split("\\s+");
      StringBuilder currentLine = new StringBuilder();

      for (String word : words) {
         if (currentLine.length() + word.length() <= lineLength) {
            currentLine.append(word);
            if (currentLine.length() != lineLength) {
               currentLine.append(" ");
            }
         } else {
            lines.add(pad ? StringUtils.rightPad(currentLine.toString(), lineLength) : currentLine.toString().trim());
            currentLine = new StringBuilder(word + " ");
         }
      }

      if (!currentLine.isEmpty()) {
         lines.add(pad ? StringUtils.rightPad(currentLine.toString(), lineLength) : currentLine.toString().trim());
      }

      return lines.toArray(String[]::new);
   }

   public static class WrappedCommentResolver implements ICommentResolver {
      private final int lineLength;
      private final boolean padLength;

      public WrappedCommentResolver(int lineLength, boolean padLength) {
         this.lineLength = lineLength;
         this.padLength = padLength;
      }

      @Nullable
      public WrappedComment resolve(Field field, @Nullable Object value, Value valueMeta) throws IOException {
         return ICommentResolver.hasComment(valueMeta) ? new WrappedComment(valueMeta.comment(), this.lineLength, this.padLength) : null;
      }
   }
}
