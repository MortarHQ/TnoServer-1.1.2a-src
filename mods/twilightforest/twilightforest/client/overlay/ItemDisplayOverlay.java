package twilightforest.client.overlay;

import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import twilightforest.client.overlay.display.ItemDisplay;
import twilightforest.components.item.ItemDisplayContents;
import twilightforest.config.TFConfig;
import twilightforest.init.TFDataComponents;
import twilightforest.init.custom.ItemDisplays;
import twilightforest.init.custom.TravellersModifiersManager;
import twilightforest.item.travellers_gear.modifiers.display.ItemDisplayType;

public class ItemDisplayOverlay {
   public static void render(GuiGraphics graphics, Minecraft minecraft, Window window, Gui gui, Player player) {
      if (player != null && !gui.getDebugOverlay().showDebugScreen() && !minecraft.options.hideGui) {
         ItemStack goggles = player.getItemBySlot(EquipmentSlot.HEAD);
         if (TravellersModifiersManager.isModifierActive(player, goggles, TravellersModifiersManager.ITEM_DISPLAY_MODIFIER)) {
            ItemDisplayContents contents = (ItemDisplayContents)goggles.get(TFDataComponents.ITEM_DISPLAY);
            if (contents != null && !contents.isEmpty()) {
               List<ItemDisplayOverlay.DisplayHolder> typesToRender = new ArrayList<>();
               int widest = fillDisplayHolders(typesToRender, contents, minecraft, gui, player);
               if (!typesToRender.isEmpty()) {
                  renderHolders(graphics, minecraft, gui, player, typesToRender, widest);
               }
            }
         }
      }
   }

   private static void renderHolders(
      GuiGraphics graphics, Minecraft minecraft, Gui gui, Player player, List<ItemDisplayOverlay.DisplayHolder> typesToRender, int widest
   ) {
      graphics.pose().pushPose();
      graphics.pose().translate(TFConfig.itemDisplayXOffs, TFConfig.itemDisplayYOffs, 0.0);
      graphics.pose().scale((float)TFConfig.itemDisplayScale, (float)TFConfig.itemDisplayScale, (float)TFConfig.itemDisplayScale);
      typesToRender.sort(Comparator.comparing(holderx -> holderx.display().displayPosition()));

      for (ItemDisplayOverlay.DisplayHolder holder : typesToRender) {
         graphics.pose().pushPose();
         holder.display().render(holder.stack(), graphics, minecraft, gui, player, widest);
         graphics.pose().popPose();
         graphics.pose().translate(0.0F, holder.bounds().height(), 0.0F);
      }

      graphics.pose().popPose();
   }

   private static int fillDisplayHolders(
      List<ItemDisplayOverlay.DisplayHolder> typesToRender, ItemDisplayContents contents, Minecraft minecraft, Gui gui, Player player
   ) {
      int widest = 0;
      NonNullList<ItemStack> items = contents.items();
      int slots = Math.min(ItemDisplayContents.LAYOUT.size(), items.size());
      int activeMapSlot = contents.findActiveMapSlot();

      for (int i = 0; i < slots; i++) {
         ItemStack stack = (ItemStack)items.get(i);
         if (!stack.isEmpty()) {
            ItemDisplayType type = (ItemDisplayType)ItemDisplayContents.LAYOUT.get(i).get();
            if ((type != ItemDisplays.MAP.get() || i == activeMapSlot) && ((ItemDisplayType)ItemDisplayContents.LAYOUT.get(i).get()).validItems().test(stack)) {
               ItemDisplay display = type.display().get();
               ItemDisplay.Bounds bounds = display.getWidgetSize(stack, minecraft, gui, player, widest);
               widest = Math.max(widest, bounds.width());
               typesToRender.add(new ItemDisplayOverlay.DisplayHolder(stack, display, bounds));
            }
         }
      }

      return widest;
   }

   public record DisplayHolder(ItemStack stack, ItemDisplay display, ItemDisplay.Bounds bounds) {
   }
}
