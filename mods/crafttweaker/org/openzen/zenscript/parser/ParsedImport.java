package org.openzen.zenscript.parser;

import java.util.ArrayList;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSToken;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;

public class ParsedImport {
   public final CodePosition position;
   private final boolean relative;
   private final List<String> importName;
   private final String rename;

   public ParsedImport(CodePosition position, boolean relative, List<String> importName, String rename) {
      this.position = position;
      this.relative = relative;
      this.importName = importName;
      this.rename = rename;
   }

   public static ParsedImport parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
      try {
         boolean relative = tokens.optional(ZSTokenType.T_DOT) != null;
         List<String> importName = new ArrayList<>();
         ZSToken tName = tokens.required(ZSTokenType.T_IDENTIFIER, "identifier expected");
         importName.add(tName.content);

         while (tokens.optional(ZSTokenType.T_DOT) != null) {
            ZSToken tNamePart = tokens.required(ZSTokenType.T_IDENTIFIER, "identifier expected");
            importName.add(tNamePart.content);
         }

         String rename = null;
         if (tokens.optional(ZSTokenType.K_AS) != null) {
            ZSToken tRename = tokens.required(ZSTokenType.T_IDENTIFIER, "identifier expected");
            rename = tRename.content;
         }

         tokens.required(ZSTokenType.T_SEMICOLON, "; expected");
         return new ParsedImport(position, relative, importName, rename);
      } catch (ParseException ex) {
         tokens.recoverUntilTokenOrNewline(ZSTokenType.T_SEMICOLON);
         throw ex;
      }
   }

   public String getName() {
      return this.rename == null ? this.importName.get(this.importName.size() - 1) : this.rename;
   }

   public boolean isRelative() {
      return this.relative;
   }

   public List<String> getPath() {
      return this.importName;
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();

      for (int i = 0; i < this.importName.size(); i++) {
         if (i > 0) {
            result.append('.');
         }

         result.append(this.importName.get(i));
      }

      return result.toString();
   }
}
