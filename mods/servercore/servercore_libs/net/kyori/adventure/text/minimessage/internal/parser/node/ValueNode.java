package servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.Token;

public abstract class ValueNode extends ElementNode {
   private final String value;

   ValueNode(@Nullable final ElementNode parent, @Nullable final Token token, @NotNull final String sourceMessage, @NotNull final String value) {
      super(parent, token, sourceMessage);
      this.value = value;
   }

   abstract String valueName();

   @NotNull
   public String value() {
      return this.value;
   }

   @NotNull
   @Override
   public Token token() {
      return Objects.requireNonNull(super.token(), "token is not set");
   }

   @NotNull
   @Override
   public StringBuilder buildToString(@NotNull final StringBuilder sb, final int indent) {
      char[] in = this.ident(indent);
      sb.append(in).append(this.valueName()).append("('").append(this.value).append("')\n");
      return sb;
   }
}
