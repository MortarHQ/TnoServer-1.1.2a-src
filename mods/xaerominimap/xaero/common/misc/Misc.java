package xaero.common.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.chunk.LevelChunk;

public class Misc {
   public static double getMouseX(Minecraft mc, boolean raw) {
      return raw ? mc.mouseHandler.xpos() : mc.mouseHandler.xpos() * mc.getWindow().getWidth() / mc.getWindow().getScreenWidth();
   }

   public static double getMouseY(Minecraft mc, boolean raw) {
      return raw ? mc.mouseHandler.ypos() : mc.mouseHandler.ypos() * mc.getWindow().getHeight() / mc.getWindow().getScreenHeight();
   }

   public static void drawNormalText(PoseStack matrices, String name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      Minecraft.getInstance().font.drawInBatch(name, x, y, color, shadow, matrices.last().pose(), renderTypeBuffer, DisplayMode.NORMAL, 0, 15728880);
   }

   public static void drawNormalText(PoseStack matrices, Component name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      Minecraft.getInstance().font.drawInBatch(name, x, y, color, shadow, matrices.last().pose(), renderTypeBuffer, DisplayMode.NORMAL, 0, 15728880);
   }

   public static void drawPiercingText(PoseStack matrices, String name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      Minecraft.getInstance().font.drawInBatch(name, x, y, color, shadow, matrices.last().pose(), renderTypeBuffer, DisplayMode.SEE_THROUGH, 0, 15728880);
   }

   public static void drawPiercingText(PoseStack matrices, Component name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      Minecraft.getInstance().font.drawInBatch(name, x, y, color, shadow, matrices.last().pose(), renderTypeBuffer, DisplayMode.SEE_THROUGH, 0, 15728880);
   }

   public static void drawCenteredPiercingText(PoseStack matrices, String name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      drawPiercingText(matrices, name, x - Minecraft.getInstance().font.width(name) / 2, y, color, shadow, renderTypeBuffer);
   }

   public static void drawCenteredPiercingText(PoseStack matrices, Component name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      drawPiercingText(matrices, name, x - Minecraft.getInstance().font.width(name) / 2, y, color, shadow, renderTypeBuffer);
   }

   public static long getChunkPosAsLong(LevelChunk chunk) {
      return chunk.getPos().toLong();
   }

   public static boolean hasItem(Player player, Item item) {
      return hasItem(player.getInventory().offhand, -1, item)
         || hasItem(player.getInventory().armor, -1, item)
         || hasItem(player.getInventory().items, 9, item);
   }

   public static boolean hasItem(NonNullList<ItemStack> inventory, int limit, Item item) {
      for (int i = 0; i < inventory.size() && (limit == -1 || i < limit); i++) {
         if (inventory.get(i) != null && ((ItemStack)inventory.get(i)).getItem() == item) {
            return true;
         }
      }

      return false;
   }

   public static Component getFixedDisplayName(Entity e) {
      Component baseName = e.getName();
      if (baseName == null) {
         return null;
      } else {
         return e.getTeam() == null ? baseName.copy() : e.getTeam().getFormattedName(baseName.copy());
      }
   }

   public static boolean hasEffect(Player player, Holder<MobEffect> effect) {
      return effect != null && player != null && player.hasEffect(effect);
   }

   public static boolean hasEffect(Holder<MobEffect> effect) {
      return hasEffect(Minecraft.getInstance().player, effect);
   }

   public static boolean isValidResourceLocationString(String resourceLocationString) {
      if (resourceLocationString.isEmpty()) {
         return false;
      }

      for (int i = 0; i < resourceLocationString.length(); i++) {
         if (!ResourceLocation.isAllowedInResourceLocation(resourceLocationString.charAt(i))) {
            return false;
         }
      }

      return true;
   }
}
