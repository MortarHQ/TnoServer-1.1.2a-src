package org.crypticdev.stextras.command.support;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.utils.TextUtil;

public final class CommandText {
   private static final String PREFIX = TextUtil.toSmallCaps("stextras");

   public static MutableComponent success(Component body) {
      return prefixed("ok", ChatFormatting.GREEN, body);
   }

   public static MutableComponent info(Component body) {
      return prefixed("info", ChatFormatting.AQUA, body);
   }

   public static MutableComponent failure(Component body) {
      return prefixed("error", ChatFormatting.RED, body);
   }

   public static MutableComponent prefixed(String badge, ChatFormatting color, Component body) {
      return Component.empty()
         .append(Component.literal("[").withStyle(ChatFormatting.DARK_GRAY))
         .append(Component.literal(PREFIX).withStyle(ChatFormatting.AQUA))
         .append(Component.literal("] ").withStyle(ChatFormatting.DARK_GRAY))
         .append(Component.literal(TextUtil.toSmallCaps(badge)).withStyle(new ChatFormatting[]{color, ChatFormatting.BOLD}))
         .append(Component.literal(" "))
         .append(body.copy());
   }

   public static MutableComponent panel(String title, List<? extends Component> lines) {
      MutableComponent panel = Component.empty()
         .append(Component.literal("[").withStyle(ChatFormatting.DARK_GRAY))
         .append(Component.literal(TextUtil.toSmallCaps(title)).withStyle(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD}))
         .append(Component.literal("]").withStyle(ChatFormatting.DARK_GRAY));
      if (lines.isEmpty()) {
         panel.append(Component.literal("\n- ").withStyle(ChatFormatting.DARK_GRAY)).append(label("none"));
         return panel;
      }

      for (Component line : lines) {
         panel.append(Component.literal("\n- ").withStyle(ChatFormatting.DARK_GRAY)).append(line.copy());
      }

      return panel;
   }

   public static MutableComponent label(String text) {
      return Component.literal(TextUtil.toSmallCaps(text)).withStyle(ChatFormatting.GRAY);
   }

   public static MutableComponent value(Object value) {
      return Component.literal(String.valueOf(value)).withStyle(ChatFormatting.WHITE);
   }

   public static MutableComponent number(int value) {
      return Component.literal(Integer.toString(value)).withStyle(ChatFormatting.YELLOW);
   }

   public static MutableComponent signedNumber(int value) {
      ChatFormatting color = value > 0 ? ChatFormatting.GREEN : (value < 0 ? ChatFormatting.RED : ChatFormatting.YELLOW);
      String text = value > 0 ? "+" + value : Integer.toString(value);
      return Component.literal(text).withStyle(color);
   }

   public static MutableComponent bool(boolean value) {
      return Component.literal(Boolean.toString(value)).withStyle(value ? ChatFormatting.GREEN : ChatFormatting.RED);
   }

   public static MutableComponent player(ServerPlayer player) {
      return Component.literal(player.getGameProfile().getName()).withStyle(ChatFormatting.GOLD);
   }

   public static MutableComponent resource(ResourceLocation id) {
      return Component.literal(id.toString()).withStyle(ChatFormatting.AQUA);
   }

   public static MutableComponent namedResource(ResourceLocation id) {
      return Component.empty()
         .append(value(TextUtil.formatedResourceName(id)))
         .append(Component.literal(" ").withStyle(ChatFormatting.DARK_GRAY))
         .append(Component.literal("(").withStyle(ChatFormatting.DARK_GRAY))
         .append(resource(id))
         .append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY));
   }

   public static MutableComponent keyValue(String key, Object value) {
      return keyValue(key, value(value));
   }

   public static MutableComponent keyValue(String key, Component value) {
      return Component.empty().append(label(key)).append(Component.literal(": ").withStyle(ChatFormatting.DARK_GRAY)).append(value.copy());
   }

   public static MutableComponent tag(String text, ChatFormatting color) {
      return Component.empty()
         .append(Component.literal("[").withStyle(ChatFormatting.DARK_GRAY))
         .append(Component.literal(TextUtil.toSmallCaps(text)).withStyle(new ChatFormatting[]{color, ChatFormatting.BOLD}))
         .append(Component.literal("]").withStyle(ChatFormatting.DARK_GRAY));
   }

   private CommandText() {
   }
}
