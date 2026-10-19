package servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.match;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.TokenType;

public abstract class MatchedTokenConsumer<T> {
   protected final String input;
   private int lastIndex = -1;

   public MatchedTokenConsumer(@NotNull final String input) {
      this.input = input;
   }

   @MustBeInvokedByOverriders
   public void accept(final int start, final int end, @NotNull final TokenType tokenType) {
      this.lastIndex = end;
   }

   public abstract @UnknownNullability T result();

   public final int lastEndIndex() {
      return this.lastIndex;
   }
}
