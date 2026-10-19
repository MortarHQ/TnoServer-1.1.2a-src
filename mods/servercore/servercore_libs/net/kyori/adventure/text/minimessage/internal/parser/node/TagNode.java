package servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.ParsingExceptionImpl;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.Token;
import servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.TokenParser;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;

public final class TagNode extends ElementNode {
   private final List<TagPart> parts;
   @Nullable
   private Tag tag = null;

   public TagNode(
      @NotNull final ElementNode parent, @NotNull final Token token, @NotNull final String sourceMessage, final TokenParser.@NotNull TagProvider tagProvider
   ) {
      super(parent, token, sourceMessage);
      this.parts = genParts(token, sourceMessage, tagProvider);
      if (this.parts.isEmpty()) {
         throw new ParsingExceptionImpl("Tag has no parts? " + this, this.sourceMessage(), this.token());
      }
   }

   @NotNull
   private static List<TagPart> genParts(@NotNull final Token token, @NotNull final String sourceMessage, final TokenParser.@NotNull TagProvider tagProvider) {
      ArrayList<TagPart> parts = new ArrayList<>();
      if (token.childTokens() != null) {
         for (Token childToken : token.childTokens()) {
            parts.add(new TagPart(sourceMessage, childToken, tagProvider));
         }
      }

      return parts;
   }

   @NotNull
   public List<TagPart> parts() {
      return this.parts;
   }

   @NotNull
   public String name() {
      return this.parts.get(0).value();
   }

   @NotNull
   @Override
   public Token token() {
      return Objects.requireNonNull(super.token(), "token is not set");
   }

   @NotNull
   public Tag tag() {
      return Objects.requireNonNull(this.tag, "no tag set");
   }

   public void tag(@NotNull final Tag tag) {
      this.tag = tag;
   }

   @NotNull
   @Override
   public StringBuilder buildToString(@NotNull final StringBuilder sb, final int indent) {
      char[] in = this.ident(indent);
      sb.append(in).append("TagNode(");
      int size = this.parts.size();

      for (int i = 0; i < size; i++) {
         TagPart part = this.parts.get(i);
         sb.append('\'').append(part.value()).append('\'');
         if (i != size - 1) {
            sb.append(", ");
         }
      }

      sb.append(") {\n");

      for (ElementNode child : this.children()) {
         child.buildToString(sb, indent + 1);
      }

      sb.append(in).append("}\n");
      return sb;
   }
}
