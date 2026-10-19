package org.openzen.zencode.shared;

import java.util.HashMap;
import java.util.Map;
import stdlib.Result;
import stdlib.Strings;
import zsynthetic.ArrayHelpers;

public final class StringExpansion {
   private static final Map<String, CharacterEntity> NAMED_CHARACTER_ENTITIES = new HashMap<>();

   private StringExpansion() {
   }

   private static CharacterEntity[] getCharacterEntities() {
      return new CharacterEntity[]{
         new CharacterEntity("quot", '"'),
         new CharacterEntity("amp", '&'),
         new CharacterEntity("apos", '\''),
         new CharacterEntity("lt", '<'),
         new CharacterEntity("gt", '>'),
         new CharacterEntity("nbsp", ' '),
         new CharacterEntity("iexcl", '¡'),
         new CharacterEntity("cent", '¢'),
         new CharacterEntity("pound", '£'),
         new CharacterEntity("curren", '¤'),
         new CharacterEntity("yen", '¥'),
         new CharacterEntity("brvbar", '¦'),
         new CharacterEntity("sect", '§'),
         new CharacterEntity("uml", '¨'),
         new CharacterEntity("copy", '©'),
         new CharacterEntity("ordf", 'ª'),
         new CharacterEntity("laquo", '«'),
         new CharacterEntity("not", '¬'),
         new CharacterEntity("shy", '\u00ad'),
         new CharacterEntity("reg", '®'),
         new CharacterEntity("macr", '¯'),
         new CharacterEntity("deg", '°'),
         new CharacterEntity("plusmn", '±'),
         new CharacterEntity("sup2", '²'),
         new CharacterEntity("sup3", '³'),
         new CharacterEntity("acute", '´'),
         new CharacterEntity("micro", 'µ'),
         new CharacterEntity("para", '¶'),
         new CharacterEntity("middot", '·'),
         new CharacterEntity("cedil", '¸'),
         new CharacterEntity("sup1", '¹'),
         new CharacterEntity("ordm", 'º'),
         new CharacterEntity("raquo", '»'),
         new CharacterEntity("frac14", '¼'),
         new CharacterEntity("frac12", '½'),
         new CharacterEntity("frac34", '¾'),
         new CharacterEntity("iquest", '¿'),
         new CharacterEntity("Agrave", 'À'),
         new CharacterEntity("Aacute", 'Á'),
         new CharacterEntity("Acirc", 'Â'),
         new CharacterEntity("Atilde", 'Ã'),
         new CharacterEntity("Auml", 'Ä'),
         new CharacterEntity("Aring", 'Å'),
         new CharacterEntity("AElig", 'Æ'),
         new CharacterEntity("Ccedil", 'Ç'),
         new CharacterEntity("Egrave", 'È'),
         new CharacterEntity("Eacute", 'É'),
         new CharacterEntity("Ecirc", 'Ê'),
         new CharacterEntity("Euml", 'Ë'),
         new CharacterEntity("lgrave", 'Ì'),
         new CharacterEntity("lacute", 'Í'),
         new CharacterEntity("lcirc", 'Î'),
         new CharacterEntity("luml", 'Ï'),
         new CharacterEntity("ETH", 'Ð'),
         new CharacterEntity("Ntilde", 'Ñ'),
         new CharacterEntity("Ograve", 'Ò'),
         new CharacterEntity("Oacute", 'Ó'),
         new CharacterEntity("Ocirc", 'Ô'),
         new CharacterEntity("Otilde", 'Õ'),
         new CharacterEntity("Ouml", 'Ö'),
         new CharacterEntity("times", '×'),
         new CharacterEntity("Oslash", 'Ø'),
         new CharacterEntity("Ugrave", 'Ù'),
         new CharacterEntity("Uacute", 'Ú'),
         new CharacterEntity("Ucirc", 'Û'),
         new CharacterEntity("Uuml", 'Ü'),
         new CharacterEntity("Yacute", 'Ý'),
         new CharacterEntity("THORN", 'Þ'),
         new CharacterEntity("szlig", 'ß'),
         new CharacterEntity("agrave", 'à'),
         new CharacterEntity("aacute", 'á'),
         new CharacterEntity("acirc", 'â'),
         new CharacterEntity("atilde", 'ã'),
         new CharacterEntity("auml", 'ä'),
         new CharacterEntity("aring", 'å'),
         new CharacterEntity("aelig", 'æ'),
         new CharacterEntity("ccedil", 'ç'),
         new CharacterEntity("egrave", 'è'),
         new CharacterEntity("eacute", 'é'),
         new CharacterEntity("ecirc", 'ê'),
         new CharacterEntity("euml", 'ë'),
         new CharacterEntity("igrave", 'ì'),
         new CharacterEntity("iacute", 'í'),
         new CharacterEntity("icirc", 'î'),
         new CharacterEntity("iuml", 'ï'),
         new CharacterEntity("eth", 'ð'),
         new CharacterEntity("ntilde", 'ñ'),
         new CharacterEntity("ograve", 'ò'),
         new CharacterEntity("oacute", 'ó'),
         new CharacterEntity("ocirc", 'ô'),
         new CharacterEntity("otilde", 'õ'),
         new CharacterEntity("ouml", 'ö'),
         new CharacterEntity("divide", '÷'),
         new CharacterEntity("oslash", 'ø'),
         new CharacterEntity("ugrave", 'ù'),
         new CharacterEntity("uacute", 'ú'),
         new CharacterEntity("ucirc", 'û'),
         new CharacterEntity("uuml", 'ü'),
         new CharacterEntity("yacute", 'ý'),
         new CharacterEntity("thorn", 'þ'),
         new CharacterEntity("yuml", 'ÿ'),
         new CharacterEntity("OElig", 'Œ'),
         new CharacterEntity("oelig", 'œ'),
         new CharacterEntity("Scaron", 'Š'),
         new CharacterEntity("scaron", 'š'),
         new CharacterEntity("Yuml", 'Ÿ'),
         new CharacterEntity("fnof", 'ƒ'),
         new CharacterEntity("circ", 'ˆ'),
         new CharacterEntity("tilde", '˜'),
         new CharacterEntity("Alpha", 'Α'),
         new CharacterEntity("Beta", 'Β'),
         new CharacterEntity("Gamma", 'Γ'),
         new CharacterEntity("Delta", 'Δ'),
         new CharacterEntity("Epsilon", 'Ε'),
         new CharacterEntity("Zeta", 'Ζ'),
         new CharacterEntity("Eta", 'Η'),
         new CharacterEntity("Theta", 'Θ'),
         new CharacterEntity("Iota", 'Ι'),
         new CharacterEntity("Kappa", 'Κ'),
         new CharacterEntity("Lambda", 'Λ'),
         new CharacterEntity("Mu", 'Μ'),
         new CharacterEntity("Nu", 'Ν'),
         new CharacterEntity("Xi", 'Ξ'),
         new CharacterEntity("Omicron", 'Ο'),
         new CharacterEntity("Pi", 'Π'),
         new CharacterEntity("Rho", 'Ρ'),
         new CharacterEntity("Sigma", 'Σ'),
         new CharacterEntity("Tau", 'Τ'),
         new CharacterEntity("Upsilon", 'Υ'),
         new CharacterEntity("Phi", 'Φ'),
         new CharacterEntity("Chi", 'Χ'),
         new CharacterEntity("Psi", 'Ψ'),
         new CharacterEntity("Omega", 'Ω'),
         new CharacterEntity("alpha", 'α'),
         new CharacterEntity("beta", 'β'),
         new CharacterEntity("gamma", 'γ'),
         new CharacterEntity("delta", 'δ'),
         new CharacterEntity("epsilon", 'ε'),
         new CharacterEntity("zeta", 'ζ'),
         new CharacterEntity("eta", 'η'),
         new CharacterEntity("theta", 'θ'),
         new CharacterEntity("iota", 'ι'),
         new CharacterEntity("kappa", 'κ'),
         new CharacterEntity("lambda", 'λ'),
         new CharacterEntity("mu", 'μ'),
         new CharacterEntity("nu", 'ν'),
         new CharacterEntity("xi", 'ξ'),
         new CharacterEntity("omicron", 'ο'),
         new CharacterEntity("pi", 'π'),
         new CharacterEntity("rho", 'ρ'),
         new CharacterEntity("sigmaf", 'ς'),
         new CharacterEntity("sigma", 'σ'),
         new CharacterEntity("tau", 'τ'),
         new CharacterEntity("upsilon", 'υ'),
         new CharacterEntity("phi", 'φ'),
         new CharacterEntity("chi", 'χ'),
         new CharacterEntity("psi", 'ψ'),
         new CharacterEntity("omega", 'ω'),
         new CharacterEntity("thetasym", 'ϑ'),
         new CharacterEntity("upsih", 'ϒ'),
         new CharacterEntity("piv", 'ϖ'),
         new CharacterEntity("ensp", ' '),
         new CharacterEntity("emsp", ' '),
         new CharacterEntity("thinsp", ' '),
         new CharacterEntity("zwnj", '\u200c'),
         new CharacterEntity("zwj", '\u200d'),
         new CharacterEntity("lrm", '\u200e'),
         new CharacterEntity("rlm", '\u200f'),
         new CharacterEntity("ndash", '–'),
         new CharacterEntity("mdash", '—'),
         new CharacterEntity("lsquo", '‘'),
         new CharacterEntity("rsquo", '’'),
         new CharacterEntity("sbquo", '‚'),
         new CharacterEntity("ldquo", '“'),
         new CharacterEntity("rdquo", '”'),
         new CharacterEntity("bdquo", '„'),
         new CharacterEntity("dagger", '†'),
         new CharacterEntity("Dagger", '‡'),
         new CharacterEntity("bull", '•'),
         new CharacterEntity("hellip", '…'),
         new CharacterEntity("permil", '‰'),
         new CharacterEntity("prime", '′'),
         new CharacterEntity("Prime", '″'),
         new CharacterEntity("lsaquo", '‹'),
         new CharacterEntity("rsaquo", '›'),
         new CharacterEntity("oline", '‾'),
         new CharacterEntity("frasl", '⁄'),
         new CharacterEntity("euro", '€'),
         new CharacterEntity("image", 'ℑ'),
         new CharacterEntity("weierp", '℘'),
         new CharacterEntity("real", 'ℜ'),
         new CharacterEntity("trade", '™'),
         new CharacterEntity("alefsym", 'ℵ'),
         new CharacterEntity("larr", '←'),
         new CharacterEntity("uarr", '↑'),
         new CharacterEntity("rarr", '→'),
         new CharacterEntity("darr", '↓'),
         new CharacterEntity("harr", '↔'),
         new CharacterEntity("crarr", '↵'),
         new CharacterEntity("lArr", '⇐'),
         new CharacterEntity("uArr", '⇑'),
         new CharacterEntity("rArr", '⇒'),
         new CharacterEntity("dArr", '⇓'),
         new CharacterEntity("hArr", '⇔'),
         new CharacterEntity("forall", '∀'),
         new CharacterEntity("part", '∂'),
         new CharacterEntity("exist", '∃'),
         new CharacterEntity("empty", '∅'),
         new CharacterEntity("nabla", '∇'),
         new CharacterEntity("isin", '∈'),
         new CharacterEntity("notin", '∉'),
         new CharacterEntity("ni", '∋'),
         new CharacterEntity("prod", '∏'),
         new CharacterEntity("sum", '∑'),
         new CharacterEntity("minus", '−'),
         new CharacterEntity("lowast", '∗'),
         new CharacterEntity("radic", '√'),
         new CharacterEntity("prop", '∝'),
         new CharacterEntity("infin", '∞'),
         new CharacterEntity("ang", '∠'),
         new CharacterEntity("and", '∧'),
         new CharacterEntity("or", '∨'),
         new CharacterEntity("cap", '∩'),
         new CharacterEntity("cup", '∪'),
         new CharacterEntity("int", '∫'),
         new CharacterEntity("there4", '∴'),
         new CharacterEntity("sim", '∼'),
         new CharacterEntity("cong", '≅'),
         new CharacterEntity("asymp", '≈'),
         new CharacterEntity("ne", '≠'),
         new CharacterEntity("equiv", '≡'),
         new CharacterEntity("le", '≤'),
         new CharacterEntity("ge", '≥'),
         new CharacterEntity("sub", '⊂'),
         new CharacterEntity("sup", '⊃'),
         new CharacterEntity("nsub", '⊄'),
         new CharacterEntity("sube", '⊆'),
         new CharacterEntity("supe", '⊇'),
         new CharacterEntity("oplus", '⊕'),
         new CharacterEntity("otimes", '⊗'),
         new CharacterEntity("perp", '⊥'),
         new CharacterEntity("sdot", '⋅'),
         new CharacterEntity("lceil", '⌈'),
         new CharacterEntity("rceil", '⌉'),
         new CharacterEntity("lfloor", '⌊'),
         new CharacterEntity("rfloor", '⌋'),
         new CharacterEntity("lang", '〈'),
         new CharacterEntity("rang", '〉'),
         new CharacterEntity("loz", '◊'),
         new CharacterEntity("spades", '♠'),
         new CharacterEntity("clubs", '⋟'),
         new CharacterEntity("hearts", '♥'),
         new CharacterEntity("diams", '♦')
      };
   }

   public static String capitalize(String self) {
      if (self.length() <= 0) {
         throw new AssertionError("Cannot capitalize an empty string");
      } else {
         return self.substring(0, 1).toUpperCase() + self.substring(1, self.length());
      }
   }

   public static Result<String, String> unescape(String self) {
      if (self.charAt(0) != self.charAt(self.length() - 1)) {
         throw new AssertionError("Unbalanced quotes");
      }

      if ((self.charAt(0) != '@' || !ArrayHelpers.containsChar(new char[]{'"', '\''}, self.charAt(1)))
         && !ArrayHelpers.containsChar(new char[]{'"', '\''}, self.charAt(0))) {
         throw new AssertionError("String is not quoted");
      }

      if (self.length() < 2) {
         throw new AssertionError("String is not quoted");
      }

      boolean isLiteral = self.charAt(0) == '@';
      String quoted = self;
      if (isLiteral) {
         quoted = quoted.substring(1, quoted.length());
      }

      if (isLiteral) {
         return new Result.Ok<>(quoted.substring(1, quoted.length() - 1));
      }

      StringBuilder result = new StringBuilder(self.length() - 2);

      for (int i = 1; i < quoted.length() - 1; i++) {
         if (quoted.charAt(i) == '\\') {
            if (i >= quoted.length() - 1) {
               return new Result.Error<>("Unfinished escape sequence");
            }

            switch (quoted.charAt(i + 1)) {
               case '"':
                  i++;
                  result.append('"');
                  break;
               case '&':
                  Result<CharacterEntity, String> temp1 = readCharacterEntity(quoted, i + 1);
                  if (temp1 instanceof Result.Error) {
                     return new Result.Error<>((String)((Result.Error)temp1).value);
                  }

                  CharacterEntity characterEntity = (CharacterEntity)((Result.Ok)temp1).value;
                  i += characterEntity.stringValue.length() + 2;
                  result.append(characterEntity.charValue);
                  break;
               case '\'':
                  i++;
                  result.append('\'');
                  break;
               case '\\':
                  i++;
                  result.append('\\');
                  break;
               case 'b':
                  i++;
                  result.append('\b');
                  break;
               case 'f':
                  i++;
                  result.append('\f');
                  break;
               case 'n':
                  i++;
                  result.append('\n');
                  break;
               case 'r':
                  i++;
                  result.append('\r');
                  break;
               case 't':
                  i++;
                  result.append('\t');
                  break;
               case 'u':
                  if (i >= quoted.length() - 5) {
                     return new Result.Error<>("Unfinished escape sequence");
                  }

                  Result<Integer, String> temp2 = readHexCharacter(quoted.charAt(i + 2));
                  if (temp2 instanceof Result.Error) {
                     return new Result.Error<>((String)((Result.Error)temp2).value);
                  }

                  int hex0 = (Integer)((Result.Ok)temp2).value;
                  Result<Integer, String> temp3 = readHexCharacter(quoted.charAt(i + 3));
                  if (temp3 instanceof Result.Error) {
                     return new Result.Error<>((String)((Result.Error)temp3).value);
                  }

                  int hex1 = (Integer)((Result.Ok)temp3).value;
                  Result<Integer, String> temp4 = readHexCharacter(quoted.charAt(i + 4));
                  if (temp4 instanceof Result.Error) {
                     return new Result.Error<>((String)((Result.Error)temp4).value);
                  }

                  int hex2 = (Integer)((Result.Ok)temp4).value;
                  Result<Integer, String> temp5 = readHexCharacter(quoted.charAt(i + 5));
                  if (temp5 instanceof Result.Error) {
                     return new Result.Error<>((String)((Result.Error)temp5).value);
                  }

                  int hex3 = (Integer)((Result.Ok)temp5).value;
                  i += 5;
                  result.append((char)(hex0 << 12 | hex1 << 8 | hex2 << 4 | hex3));
                  break;
               default:
                  return new Result.Error<>("Illegal escape sequence");
            }
         } else {
            result.append(quoted.charAt(i));
         }
      }

      return new Result.Ok<>(result.toString());
   }

   public static String escape(String self, char quote, boolean escapeUnicode) {
      StringBuilder output = new StringBuilder();
      output.append(quote);

      for (char c : self.toCharArray()) {
         switch (c) {
            case '\t':
               output.append("\\t");
               break;
            case '\n':
               output.append("\\n");
               break;
            case '\r':
               output.append("\\r");
               break;
            case '"':
               if (quote == '"') {
                  output.append("\\\"");
               } else {
                  output.append('"');
               }
               break;
            case '\'':
               if (quote == '\'') {
                  output.append("\\'");
               } else {
                  output.append('\'');
               }
               break;
            case '\\':
               output.append("\\\\");
               break;
            default:
               if (escapeUnicode && c > 127) {
                  output.append("\\u").append(Strings.lpad(Integer.toHexString(c), 4, '0'));
               } else {
                  output.append(c);
               }
         }
      }

      output.append(quote);
      return output.toString();
   }

   private static Result<Integer, String> readHexCharacter(char hex) {
      if (hex >= '0' && hex <= '9') {
         return new Result.Ok<>(hex - '0');
      } else if (hex >= 'A' && hex <= 'F') {
         return new Result.Ok<>(hex - 'A' + 10);
      } else {
         return hex >= 'a' && hex <= 'f' ? new Result.Ok<>(hex - 'a' + 10) : new Result.Error<>("Illegal hex character: " + Character.toString(hex));
      }
   }

   private static Result<CharacterEntity, String> readCharacterEntity(String str, int offset) {
      if (str.charAt(offset) != '&') {
         throw new AssertionError("Not a proper character entity");
      }

      if (offset + 3 >= str.length()) {
         return new Result.Error<>("Incomplete character entity");
      }

      int semi = str.indexOf(59, offset);
      if (semi < 0) {
         return new Result.Error<>("Incomplete character entity");
      }

      String entity = str.substring(offset + 1, semi);
      if (entity.isEmpty()) {
         return new Result.Error<>("Character entity cannot be empty");
      }

      if (NAMED_CHARACTER_ENTITIES.containsKey(entity)) {
         return new Result.Ok<>(NAMED_CHARACTER_ENTITIES.get(entity));
      }

      if (entity.charAt(0) == '#') {
         if (entity.length() < 2) {
            return new Result.Error<>("Character entity number too short");
         }

         if (str.charAt(1) == 'x') {
            if (entity.length() != 7) {
               return new Result.Error<>("Hexadecimal character entity must have 4 hex digits");
            }

            int ivalue = Integer.parseInt(entity.substring(2, entity.length()), 16);
            return new Result.Ok<>(new CharacterEntity(entity, (char)ivalue));
         } else {
            int ivalue = Integer.parseInt(entity.substring(1, entity.length()));
            return new Result.Ok<>(new CharacterEntity(entity, (char)ivalue));
         }
      } else {
         return new Result.Error<>("Not a valid named character entity");
      }
   }

   static {
      CharacterEntity[] entities = getCharacterEntities();

      for (CharacterEntity entity : entities) {
         NAMED_CHARACTER_ENTITIES.put(entity.stringValue, entity);
      }
   }
}
