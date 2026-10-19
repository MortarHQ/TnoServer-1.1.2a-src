package dev.ftb.mods.ftblibrary.util.client;

import dev.ftb.mods.ftblibrary.util.CustomComponentParser;
import dev.ftb.mods.ftblibrary.util.StringUtils;
import dev.ftb.mods.ftblibrary.util.TextComponentParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent.Action;

public class ClientTextComponentUtils {
   private static final List<CustomComponentParser> CUSTOM_COMPONENT_PARSERS = new ArrayList<>();
   private static final Function<String, Component> DEFAULT_STRING_TO_COMPONENT = ClientTextComponentUtils::defaultStringToComponent;

   public static void addCustomParser(CustomComponentParser function) {
      CUSTOM_COMPONENT_PARSERS.add(function);
   }

   public static Component parse(String s) {
      return TextComponentParser.parse(s, DEFAULT_STRING_TO_COMPONENT);
   }

   private static Component defaultStringToComponent(String s) {
      if (s.isEmpty()) {
         return Component.empty();
      }

      if (s.indexOf(58) != -1) {
         Map<String, String> map = StringUtils.splitProperties(s);

         for (CustomComponentParser parser : CUSTOM_COMPONENT_PARSERS) {
            Component c = parser.parse(s, map);
            if (c != null && c != Component.EMPTY) {
               return c;
            }
         }

         if (map.containsKey("image")) {
            ImageComponent c = ImageComponent.create(
               map.get("image"),
               Integer.parseInt(map.getOrDefault("width", "100")),
               Integer.parseInt(map.getOrDefault("height", "100")),
               ImageComponent.ImageAlign.byName(map.getOrDefault("align", "center")),
               map.getOrDefault("fit", "false").equals("true"),
               map.getOrDefault("click_action", "")
            );
            MutableComponent output = MutableComponent.create(c);
            if (map.containsKey("text")) {
               output.withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(Action.SHOW_TEXT, parse(map.get("text")))));
            }

            return output;
         }

         if (map.containsKey("open_url")) {
            return parse(map.get("text"))
               .copy()
               .withStyle(Style.EMPTY.withClickEvent(new ClickEvent(net.minecraft.network.chat.ClickEvent.Action.OPEN_URL, map.get("open_url"))));
         }
      }

      return parse(I18n.get(s, new Object[0]));
   }

   public static FormattedText ellipsize(Font font, FormattedText text, int maxWidth) {
      int strWidth = font.width(text);
      int ellipsisWidth = font.width(CommonComponents.ELLIPSIS);
      if (strWidth > maxWidth) {
         return ellipsisWidth >= maxWidth
            ? font.substrByWidth(text, maxWidth)
            : FormattedText.composite(new FormattedText[]{font.substrByWidth(text, maxWidth - ellipsisWidth), CommonComponents.ELLIPSIS});
      } else {
         return text;
      }
   }
}
