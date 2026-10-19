package it.hurts.sskirillss.relics.client.handlers;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.init.HotkeyRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(Dist.CLIENT)
public class DescriptionHandler {
   private static final int REQUIRED_TIME = 20;
   private static int ticksCountOld;
   private static int ticksCount;
   private static Slot slot;
   private static int width = 200;

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof LocalPlayer player) {
         ticksCountOld = ticksCount;
         Minecraft var17 = Minecraft.getInstance();
         int key = HotkeyRegistry.RESEARCH_RELIC.getKey().getValue();
         if (key != -1) {
            boolean isResearching = InputConstants.isKeyDown(var17.getWindow().getWindow(), key);
            if (ticksCount > 0 && !isResearching) {
               ticksCount--;
            }

            if (var17.screen instanceof AbstractContainerScreen<? extends AbstractContainerMenu> screen) {
               Window var18 = var17.getWindow();
               double mouseX = var17.mouseHandler.xpos() * var18.getGuiScaledWidth() / var18.getScreenWidth();
               double mouseY = var17.mouseHandler.ypos() * var18.getGuiScaledHeight() / var18.getScreenHeight();
               AbstractContainerMenu menu = player.containerMenu;
               int oldId = slot == null ? -1 : slot.getContainerSlot();
               int id = -1;

               for (int i = 0; i < menu.slots.size(); i++) {
                  Slot entry = (Slot)menu.slots.get(i);
                  if (isHovering(screen.getGuiLeft(), screen.getGuiTop(), entry.x, entry.y, mouseX, mouseY)) {
                     slot = entry;
                     id = i;
                     break;
                  }
               }

               if (slot != null && id != -1) {
                  ItemStack stack = slot.getItem();
                  if (slot.getContainerSlot() != oldId) {
                     ticksCount = 0;
                     ticksCountOld = 0;
                  }

                  if (stack.getItem() instanceof IRelicItem relic && isResearching) {
                     ticksCount++;
                     if (ticksCountOld >= 20) {
                        DescriptionUtils.openCachedScreen(relic, player, id, screen);
                        ticksCount = 0;
                        ticksCountOld = 0;
                     }
                  }
               } else {
                  ticksCount = 0;
                  ticksCountOld = 0;
               }
            }
         }
      }
   }

   protected static boolean isHovering(int leftPos, int topPos, int slotX, int slotY, double mouseX, double mouseY) {
      mouseX -= leftPos;
      mouseY -= topPos;
      return mouseX >= slotX - 1 && mouseX < slotX + 16 + 1 && mouseY >= slotY - 1 && mouseY < slotY + 16 + 1;
   }

   @SubscribeEvent
   public static void onItemTooltip(ItemTooltipEvent event) {
      if (event.getItemStack().getItem() instanceof IRelicItem) {
         String filler = "|";
         event.getToolTip().add(drawProgressBar(filler.repeat(width / Minecraft.getInstance().font.width(filler))));
      }
   }

   public static MutableComponent drawProgressBar(String style) {
      StringBuilder string = new StringBuilder(style);
      float percentage = Mth.lerp(Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true), ticksCountOld, ticksCount) / 20.0F;
      int offset = (int)Math.min(string.length(), Math.floor(string.length() * percentage));
      MutableComponent component = Component.literal("");
      String start = string.substring(0, offset);
      int startColor = 1573171;
      int endColor = 2885731;

      for (int i = 0; i < offset; i++) {
         float fraction = (float)i / (offset - 1);
         int r = (int)((1.0F - fraction) * (startColor >> 16 & 0xFF) + fraction * (endColor >> 16 & 0xFF));
         int g = (int)((1.0F - fraction) * (startColor >> 8 & 0xFF) + fraction * (endColor >> 8 & 0xFF));
         int b = (int)((1.0F - fraction) * (startColor & 0xFF) + fraction * (endColor & 0xFF));
         int color = r << 16 | g << 8 | b;
         component.append(Component.literal(String.valueOf(start.charAt(i))).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(color))));
      }

      component.append(Component.literal(string.substring(offset)).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY)));
      return component;
   }
}
