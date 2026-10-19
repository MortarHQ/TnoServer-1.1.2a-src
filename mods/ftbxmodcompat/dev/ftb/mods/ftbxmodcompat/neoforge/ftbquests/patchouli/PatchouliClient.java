package dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.patchouli;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliClient {
   private static Screen prevGui;
   private static Screen patchouliGui;

   static void openBook(ResourceLocation bookId, @Nullable ResourceLocation pageId, String anchor) {
      try {
         Screen prev = Minecraft.getInstance().screen;
         int pageNum = anchor.isEmpty() ? 1 : Integer.parseInt(anchor);
         PatchouliAPI.get().openBookEntry(bookId, pageId, pageNum);
         if (Minecraft.getInstance().screen != null && Minecraft.getInstance().screen.getClass().getPackageName().startsWith("vazkii.patchouli")) {
            patchouliGui = Minecraft.getInstance().screen;
            prevGui = prev;
         } else {
            patchouliGui = null;
            prevGui = null;
         }
      } catch (NumberFormatException var5) {
      }
   }

   @EventBusSubscriber(modid = "ftbxmodcompat", value = Dist.CLIENT)
   private static class ScreenListener {
      @SubscribeEvent
      public static void clientTick(Post event) {
         if (Minecraft.getInstance().screen == null && PatchouliClient.prevGui != null && PatchouliClient.patchouliGui != null) {
            Minecraft.getInstance().setScreen(PatchouliClient.prevGui);
            PatchouliClient.prevGui = null;
            PatchouliClient.patchouliGui = null;
         }
      }
   }
}
