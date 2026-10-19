package com.chaosthedude.explorerscompass.client;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.chaosthedude.explorerscompass.config.ConfigHandler;
import com.chaosthedude.explorerscompass.items.ExplorersCompassItem;
import com.chaosthedude.explorerscompass.util.CompassState;
import com.chaosthedude.explorerscompass.util.ItemUtils;
import com.chaosthedude.explorerscompass.util.RenderUtils;
import com.chaosthedude.explorerscompass.util.StructureUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExplorersCompassOverlay implements Layer {
   public static final Minecraft mc = Minecraft.getInstance();

   public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      if (mc.player != null
         && mc.level != null
         && !mc.options.hideGui
         && !mc.getDebugOverlay().showDebugScreen()
         && (mc.screen == null || (Boolean)ConfigHandler.CLIENT.displayWithChatOpen.get() && mc.screen instanceof ChatScreen)) {
         Player player = mc.player;
         ItemStack stack = ItemUtils.getHeldItem(player, ExplorersCompass.explorersCompass);
         if (stack != null && stack.getItem() instanceof ExplorersCompassItem) {
            ExplorersCompassItem compass = (ExplorersCompassItem)stack.getItem();
            if (compass.getState(stack) == CompassState.SEARCHING) {
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.status", new Object[0]), 5, 5, 16777215, 0);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.searching", new Object[0]), 5, 5, 11184810, 1);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.structure", new Object[0]), 5, 5, 16777215, 3);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, StructureUtils.getPrettyStructureName(compass.getStructureKey(stack)), 5, 5, 11184810, 4);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.radius", new Object[0]), 5, 5, 16777215, 6);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, String.valueOf(compass.getSearchRadius(stack)), 5, 5, 11184810, 7);
            } else if (compass.getState(stack) == CompassState.FOUND) {
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.status", new Object[0]), 5, 5, 16777215, 0);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.found", new Object[0]), 5, 5, 11184810, 1);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.structure", new Object[0]), 5, 5, 16777215, 3);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, StructureUtils.getPrettyStructureName(compass.getStructureKey(stack)), 5, 5, 11184810, 4);
               if (compass.shouldDisplayCoordinates(stack)) {
                  RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.coordinates", new Object[0]), 5, 5, 16777215, 6);
                  RenderUtils.drawConfiguredStringOnHUD(
                     guiGraphics, compass.getFoundStructureX(stack) + ", " + compass.getFoundStructureZ(stack), 5, 5, 11184810, 7
                  );
                  RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.distance", new Object[0]), 5, 5, 16777215, 9);
                  RenderUtils.drawConfiguredStringOnHUD(
                     guiGraphics,
                     String.valueOf(
                        StructureUtils.getHorizontalDistanceToLocation(player, compass.getFoundStructureX(stack), compass.getFoundStructureZ(stack))
                     ),
                     5,
                     5,
                     11184810,
                     10
                  );
               }
            } else if (compass.getState(stack) == CompassState.NOT_FOUND) {
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.status", new Object[0]), 5, 5, 16777215, 0);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.notFound", new Object[0]), 5, 5, 11184810, 1);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.structure", new Object[0]), 5, 5, 16777215, 3);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, StructureUtils.getPrettyStructureName(compass.getStructureKey(stack)), 5, 5, 11184810, 4);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, I18n.get("string.explorerscompass.radius", new Object[0]), 5, 5, 16777215, 6);
               RenderUtils.drawConfiguredStringOnHUD(guiGraphics, String.valueOf(compass.getSearchRadius(stack)), 5, 5, 11184810, 7);
            }
         }
      }
   }
}
