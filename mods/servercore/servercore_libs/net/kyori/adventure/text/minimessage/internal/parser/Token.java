package servercore_libs.net.kyori.adventure.text.minimessage.internal.parser;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.internal.Internals;
import servercore_libs.net.kyori.examination.Examinable;
import servercore_libs.net.kyori.examination.ExaminableProperty;

public final class Token implements Examinable {
   private final int startIndex;
   private final int endIndex;
   private final TokenType type;
   private List<Token> childTokens = null;

   public Token(final int startIndex, final int endIndex, final TokenType type) {
      this.startIndex = startIndex;
      this.endIndex = endIndex;
      this.type = type;
   }

   public int startIndex() {
      return this.startIndex;
   }

   public int endIndex() {
      return this.endIndex;
   }

   public TokenType type() {
      return this.type;
   }

   public List<Token> childTokens() {
      return this.childTokens;
   }

   public void childTokens(final List<Token> childTokens) {
      this.childTokens = childTokens;
   }

   public CharSequence get(final CharSequence message) {
      return message.subSequence(this.startIndex, this.endIndex);
   }

   @NotNull
   @Override
   public Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(
         ExaminableProperty.of("startIndex", this.startIndex), ExaminableProperty.of("endIndex", this.endIndex), ExaminableProperty.of("type", this.type)
      );
   }

   @Override
   public boolean equals(final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof Token)) {
         return false;
      }

      Token that = (Token)other;
      return this.startIndex == that.startIndex && this.endIndex == that.endIndex && this.type == that.type;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.startIndex, this.endIndex, this.type);
   }

   @Override
   public String toString() {
      return Internals.toString(this);
   }
}
