package servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node;

import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.Token;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.TokenParser;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;

public final class TagPart implements Tag.Argument {
   private final String value;
   private final Token token;

   public TagPart(@NotNull final String sourceMessage, @NotNull final Token token, final TokenParser.@NotNull TagProvider tagResolver) {
      String v = unquoteAndEscape(sourceMessage, token.startIndex(), token.endIndex());
      v = TokenParser.resolvePreProcessTags(v, tagResolver);
      this.value = v;
      this.token = token;
   }

   @NotNull
   @Override
   public String value() {
      return this.value;
   }

   @NotNull
   public Token token() {
      return this.token;
   }

   @NotNull
   public static String unquoteAndEscape(@NotNull final String text, final int start, final int end) {
      if (start == end) {
         return "";
      }

      int startIndex = start;
      int endIndex = end;
      char firstChar = text.charAt(startIndex);
      char lastChar = text.charAt(endIndex - 1);
      if (firstChar != '\'' && firstChar != '"') {
         return text.substring(startIndex, endIndex);
      }

      startIndex++;
      if (lastChar == '\'' || lastChar == '"') {
         endIndex--;
      }

      return startIndex > endIndex ? text.substring(start, end) : TokenParser.unescape(text, startIndex, endIndex, i -> i == firstChar || i == 92);
   }

   @Override
   public String toString() {
      return this.value;
   }
}
