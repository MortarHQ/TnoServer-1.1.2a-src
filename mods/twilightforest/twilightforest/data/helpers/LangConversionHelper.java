package twilightforest.data.helpers;

import java.text.ChoiceFormat;
import java.util.List;
import twilightforest.TwilightForestMod;

public class LangConversionHelper {
   private static final String normal = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.?!;\"'`&_^()[]{}<>";
   private static final char[] upside_down = "ɐqɔpǝɟᵷɥᴉɾʞꞁɯuodbɹsʇnʌʍxʎzⱯᗺƆᗡƎℲ⅁HIՐꞰꞀWNOԀꝹᴚS⟘∩ΛMX⅄Z0⥝ᘔƐ߈ϛ9ㄥ86'˙¿¡؛„,,⅋‾v)(][}{><".toCharArray();

   public static char flip(char c) {
      int index = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.?!;\"'`&_^()[]{}<>".indexOf(c);
      return index == -1 ? c : upside_down[index];
   }

   public static String convertFormattingComponent(LangFormatSplitter.FormatComponent component, int curIndex, int numArguments) {
      if (component instanceof LangFormatSplitter.MessageFormatComponent messageFormatComponent) {
         return convertMessageFormatCode(messageFormatComponent);
      } else {
         String formattingCode = component.contents();
         int storedIndex = curIndex;
         String[] split = formattingCode.split("\\$");
         String ending;
         if (split.length == 2) {
            ending = split[1];
            storedIndex = Integer.parseInt(split[0].substring(1));
         } else {
            ending = formattingCode.substring(1);
         }

         return storedIndex == numArguments - curIndex + 1 ? "%" + ending : "%" + storedIndex + "$" + ending;
      }
   }

   public static String convertMessageFormatCode(LangFormatSplitter.MessageFormatComponent component) {
      String formatStyle = component.getFormatStyle();
      if (formatStyle != null && component.isChoice()) {
         String newFormatStyle = invertChoice(formatStyle);

         try {
            new ChoiceFormat(newFormatStyle);
         } catch (IllegalArgumentException e) {
            TwilightForestMod.LOGGER.warn("Failed to convert '{}' to an upside down choice format. Got: '{}' which was invalid.", formatStyle, newFormatStyle);
            return component.contents();
         }

         return "{" + component.getArgumentIndex() + "," + component.getFormatType() + "," + newFormatStyle + "}";
      } else {
         return component.contents();
      }
   }

   public static String invertChoice(String choice) {
      StringBuilder converted = new StringBuilder();
      StringBuilder literalBuilder = new StringBuilder();
      StringBuilder textBuilder = new StringBuilder();
      char[] exploded = choice.toCharArray();
      int leftBrackets = 0;
      boolean inLiteral = true;

      for (char c : exploded) {
         if (inLiteral) {
            literalBuilder.append(c);
            if (c == '#' || c == '<' || c == 8804) {
               inLiteral = false;
               converted.append(literalBuilder);
               literalBuilder = new StringBuilder();
            }
         } else {
            if (c == '{') {
               leftBrackets++;
            } else if (c == '}') {
               leftBrackets--;
            } else if (c == '|' && leftBrackets == 0) {
               inLiteral = true;
               converted.append(convertComponents(LangFormatSplitter.splitMessageFormat(textBuilder.toString())));
               textBuilder = new StringBuilder();
            }

            if (inLiteral) {
               literalBuilder.append(c);
            } else {
               textBuilder.append(c);
            }
         }
      }

      if (inLiteral) {
         converted.append(literalBuilder);
      } else {
         converted.append(convertComponents(LangFormatSplitter.splitMessageFormat(textBuilder.toString())));
      }

      return converted.toString();
   }

   public static String convertComponents(List<LangFormatSplitter.Component> splitText) {
      int numArguments = (int)splitText.stream().filter(componentx -> componentx instanceof LangFormatSplitter.FormatComponent).count();
      StringBuilder converted = new StringBuilder();
      int curIndex = numArguments;

      for (int i = splitText.size() - 1; i >= 0; i--) {
         LangFormatSplitter.Component component = splitText.get(i);
         if (component instanceof LangFormatSplitter.KeybindComponent keybindComponent) {
            converted.append(keybindComponent.contents());
         } else if (component instanceof LangFormatSplitter.FormatComponent formatComponent) {
            converted.append(convertFormattingComponent(formatComponent, curIndex--, numArguments));
         } else {
            char[] toConvertArr = component.contents().toCharArray();

            for (int j = toConvertArr.length - 1; j >= 0; j--) {
               converted.append(flip(toConvertArr[j]));
            }
         }
      }

      return new String(converted);
   }
}
