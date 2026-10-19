package com.github.wal_bos.moreskills.world.entity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class ArsWeissCloneStatusFormatter {
   private ArsWeissCloneStatusFormatter() {
   }

   public static Component line(
      int id,
      String reason,
      String mode,
      String target,
      int cargo,
      int remaining,
      int needed,
      String goal,
      String nav,
      int stuck,
      String last,
      int hiveSpots,
      int hiveClones,
      BlockPos search,
      int radius,
      BlockPos pos,
      boolean leader
   ) {
      ChatFormatting modeColor = modeColor(mode);
      MutableComponent out = Component.literal("\nClone #" + id + ": ").withStyle(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD});
      out.append(Component.literal("(" + safe(reason) + ") ").withStyle(ChatFormatting.DARK_AQUA));
      if (leader) {
         out.append(Component.literal("COMMANDER ").withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      }

      out.append(Component.literal("mode=").withStyle(ChatFormatting.GRAY));
      out.append(Component.literal(safe(mode)).withStyle(new ChatFormatting[]{modeColor, ChatFormatting.BOLD}));
      out.append(Component.literal("\n  target: ").withStyle(ChatFormatting.GRAY));
      out.append(Component.literal(shorten(target, 34)).withStyle(ChatFormatting.WHITE));
      out.append(Component.literal(" | cargo: ").withStyle(ChatFormatting.GRAY));
      out.append(Component.literal(String.valueOf(cargo)).withStyle(cargo > 0 ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY));
      if (needed > 0) {
         out.append(Component.literal(" | left: ").withStyle(ChatFormatting.GRAY));
         out.append(Component.literal(remaining + "/" + needed).withStyle(remaining <= 0 ? ChatFormatting.GREEN : ChatFormatting.YELLOW));
      }

      out.append(Component.literal("\n  nav: ").withStyle(ChatFormatting.GRAY));
      out.append(Component.literal(safe(nav)).withStyle(nav.equals("moving") ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY));
      if (stuck > 0) {
         out.append(Component.literal(" | stuck: " + stuck).withStyle(stuck >= 60 ? ChatFormatting.RED : ChatFormatting.YELLOW));
      }

      if (goal != null && !goal.equals("none")) {
         out.append(Component.literal(" | goal: ").withStyle(ChatFormatting.GRAY));
         out.append(Component.literal(goal).withStyle(ChatFormatting.LIGHT_PURPLE));
      }

      out.append(Component.literal("\n  hive: ").withStyle(ChatFormatting.GRAY));
      out.append(Component.literal(hiveSpots + "/" + hiveClones).withStyle(ChatFormatting.BLUE));
      if (search != null && !search.equals(BlockPos.ZERO)) {
         out.append(Component.literal(" | search: ").withStyle(ChatFormatting.GRAY));
         out.append(Component.literal(search.toShortString() + " r" + radius).withStyle(ChatFormatting.DARK_GREEN));
      }

      out.append(Component.literal("\n  pos: ").withStyle(ChatFormatting.GRAY));
      out.append(Component.literal(pos == null ? "?" : pos.toShortString()).withStyle(ChatFormatting.GRAY));
      if (last != null && !last.isBlank()) {
         out.append(Component.literal(" | last: ").withStyle(ChatFormatting.DARK_GRAY));
         out.append(Component.literal(shorten(last, 28)).withStyle(ChatFormatting.DARK_GRAY));
      }

      return out;
   }

   private static ChatFormatting modeColor(String mode) {
      return switch (mode == null ? "" : mode) {
         case "gather" -> ChatFormatting.GREEN;
         case "mine" -> ChatFormatting.DARK_GREEN;
         case "hunt" -> ChatFormatting.RED;
         case "guard" -> ChatFormatting.GOLD;
         case "locate", "send" -> ChatFormatting.LIGHT_PURPLE;
         case "returning" -> ChatFormatting.YELLOW;
         case "army" -> ChatFormatting.DARK_PURPLE;
         default -> ChatFormatting.GRAY;
      };
   }

   private static String safe(String value) {
      return value != null && !value.isBlank() ? value : "none";
   }

   private static String shorten(String value, int max) {
      String v = safe(value);
      return v.length() <= max ? v : v.substring(0, Math.max(1, max - 1)) + "…";
   }
}
