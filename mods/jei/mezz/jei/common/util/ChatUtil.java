package mezz.jei.common.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public final class ChatUtil {
   private ChatUtil() {
   }

   public static void writeChatMessage(Player player, String translationKey, ChatFormatting color) {
      Component component = Component.translatable(translationKey);
      component.getStyle().applyFormat(color);
      player.sendSystemMessage(component);
   }
}
