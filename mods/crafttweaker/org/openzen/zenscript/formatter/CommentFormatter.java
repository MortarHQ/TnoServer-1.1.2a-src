package org.openzen.zenscript.formatter;

public class CommentFormatter {
   private CommentFormatter() {
   }

   public static String[] format(String[] commentLines) {
      if (commentLines.length == 0) {
         return commentLines;
      }

      boolean isInMultilineComment = false;
      String[] result = new String[commentLines.length];

      for (int i = 0; i < commentLines.length; i++) {
         String comment = commentLines[i];
         if (isInMultilineComment) {
            if (!comment.startsWith("*")) {
               comment = "* " + comment;
            }

            comment = " " + comment;
         }

         result[i] = comment;
         int index = 0;

         while (isInMultilineComment || comment.indexOf("//", index) <= 0) {
            int newIndexOpen = comment.indexOf("/*", index);
            if (newIndexOpen < 0) {
               break;
            }

            isInMultilineComment = true;
            int newIndexClose = comment.indexOf("*/", newIndexOpen);
            if (newIndexClose < 0) {
               break;
            }

            isInMultilineComment = false;
            index = newIndexClose;
         }
      }

      return result;
   }
}
