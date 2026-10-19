package dev.ftb.mods.ftblibrary.util;

import dev.ftb.mods.ftblibrary.util.text.RainbowTextColor;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.contents.PlainTextContents;
import org.jetbrains.annotations.Nullable;

public class TextComponentParser {
   public static final Char2ObjectOpenHashMap<ChatFormatting> CODE_TO_FORMATTING = (Char2ObjectOpenHashMap<ChatFormatting>)Util.make(
      new Char2ObjectOpenHashMap(), map -> {
         map.put('0', ChatFormatting.BLACK);
         map.put('1', ChatFormatting.DARK_BLUE);
         map.put('2', ChatFormatting.DARK_GREEN);
         map.put('3', ChatFormatting.DARK_AQUA);
         map.put('4', ChatFormatting.DARK_RED);
         map.put('5', ChatFormatting.DARK_PURPLE);
         map.put('6', ChatFormatting.GOLD);
         map.put('7', ChatFormatting.GRAY);
         map.put('8', ChatFormatting.DARK_GRAY);
         map.put('9', ChatFormatting.BLUE);
         map.put('a', ChatFormatting.GREEN);
         map.put('b', ChatFormatting.AQUA);
         map.put('c', ChatFormatting.RED);
         map.put('d', ChatFormatting.LIGHT_PURPLE);
         map.put('e', ChatFormatting.YELLOW);
         map.put('f', ChatFormatting.WHITE);
         map.put('k', ChatFormatting.OBFUSCATED);
         map.put('l', ChatFormatting.BOLD);
         map.put('m', ChatFormatting.STRIKETHROUGH);
         map.put('n', ChatFormatting.UNDERLINE);
         map.put('o', ChatFormatting.ITALIC);
         map.put('r', ChatFormatting.RESET);
      }
   );
   public static final Char2ObjectOpenHashMap<TextColor> SPECIAL_COLOR_CODES = (Char2ObjectOpenHashMap<TextColor>)Util.make(
      new Char2ObjectOpenHashMap(), map -> map.put('z', RainbowTextColor.INSTANCE)
   );
   private final String text;
   private final Function<String, Component> substitutes;
   private MutableComponent component;
   private StringBuilder builder;
   private Style style;

   private TextComponentParser(String txt, @Nullable Function<String, Component> sub) {
      this.text = txt;
      this.substitutes = sub;
   }

   public static Component parse(String text, @Nullable Function<String, Component> substitutes) {
      Component c = parse0(text, substitutes);
      if (c == Component.EMPTY) {
         return c;
      }

      while (c.getContents() == PlainTextContents.EMPTY && c.getStyle().equals(Style.EMPTY) && c.getSiblings().size() == 1) {
         c = (Component)c.getSiblings().get(0);
      }

      return c;
   }

   private static Component parse0(String text, @Nullable Function<String, Component> substitutes) {
      try {
         return new TextComponentParser(text, substitutes).parse();
      } catch (TextComponentParser.BadFormatException ex) {
         return Component.literal(ex.getMessage()).withStyle(ChatFormatting.RED);
      } catch (Exception ex) {
         return Component.literal(ex.toString()).withStyle(ChatFormatting.RED);
      }
   }

   private Component parse() throws TextComponentParser.BadFormatException {
      if (this.text.isEmpty()) {
         return Component.empty();
      }

      char[] c = this.text.replaceAll("\\\\n", "\n").toCharArray();
      boolean hasSpecialCodes = false;

      for (char c1 : c) {
         if (c1 == '{' || c1 == '&' || c1 == 167) {
            hasSpecialCodes = true;
            break;
         }
      }

      if (!hasSpecialCodes) {
         return Component.literal(new String(c));
      }

      this.component = Component.literal("");
      this.style = Style.EMPTY;
      this.builder = new StringBuilder();
      boolean sub = false;

      for (int i = 0; i < c.length; i++) {
         boolean escape = i > 0 && c[i - 1] == '\\';
         boolean end = i == c.length - 1;
         if (!sub || !end && c[i] != '{' && c[i] != '}') {
            if (!escape) {
               if (c[i] == '&' || c[i] == 167) {
                  this.finishPart();
                  if (end) {
                     throw new TextComponentParser.BadFormatException("Invalid formatting! Can't end string with &!");
                  }

                  if (c[++i] == '#') {
                     char[] rrggbb = new char[7];
                     rrggbb[0] = '#';
                     System.arraycopy(c, i + 1, rrggbb, 1, 6);
                     i += 6;
                     this.style = this.style.withColor(TextColor.parseColor(new String(rrggbb)).result().orElse(TextColor.fromRgb(16777215)));
                  } else {
                     if (c[i] == ' ') {
                        throw new TextComponentParser.BadFormatException("Invalid formatting! You must escape whitespace after & with \\&!");
                     }

                     ChatFormatting formatting = (ChatFormatting)CODE_TO_FORMATTING.get(c[i]);
                     if (formatting == null) {
                        TextColor specialColor = (TextColor)SPECIAL_COLOR_CODES.get(c[i]);
                        if (specialColor == null) {
                           throw new TextComponentParser.BadFormatException("Invalid formatting! Unknown formatting symbol after &: '" + c[i] + "'!");
                        }

                        this.style = this.style.withColor(specialColor);
                     } else {
                        this.style = this.style.applyFormat(formatting);
                     }
                  }
                  continue;
               }

               if (c[i] == '{') {
                  this.finishPart();
                  if (end) {
                     throw new TextComponentParser.BadFormatException("Invalid formatting! Can't end string with {!");
                  }

                  sub = true;
               }
            }

            if (c[i] != '\\' || escape) {
               this.builder.append(c[i]);
            }
         } else {
            if (c[i] == '{') {
               throw new TextComponentParser.BadFormatException("Invalid formatting! Can't nest multiple substitutes!");
            }

            this.finishPart();
            sub = false;
         }
      }

      this.finishPart();
      return this.component;
   }

   private void finishPart() throws TextComponentParser.BadFormatException {
      String string = this.builder.toString();
      this.builder.setLength(0);
      if (!string.isEmpty()) {
         if (string.length() >= 2 && string.charAt(0) == '{') {
            Component component1 = this.substitutes.apply(string.substring(1));
            if (component1 != null) {
               Style style0 = component1.getStyle();
               Style style1 = this.style;
               style1 = style1.withHoverEvent(style0.getHoverEvent());
               style1 = style1.withClickEvent(style0.getClickEvent());
               style1 = style1.withInsertion(style0.getInsertion());
               Component var6 = Component.literal("").append(component1).withStyle(style1);
               this.component.append(var6);
            } else {
               throw new TextComponentParser.BadFormatException("Invalid formatting! Unknown substitute: " + string.substring(1));
            }
         } else {
            MutableComponent component1 = Component.literal(string);
            component1.setStyle(this.style);
            this.component.append(component1);
         }
      }
   }

   private static class BadFormatException extends IllegalArgumentException {
      private BadFormatException(String s) {
         super(s);
      }
   }
}
