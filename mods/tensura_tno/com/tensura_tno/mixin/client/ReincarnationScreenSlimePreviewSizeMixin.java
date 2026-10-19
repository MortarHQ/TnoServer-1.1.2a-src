package com.tensura_tno.mixin.client;

import com.tensura_tno.TensuraTNOCompatConfig;
import com.tensura_tno.race.SlimeRaceHelper;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.tensura.client.screen.ReincarnationScreen;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ReincarnationScreen.class, priority = 900)
public abstract class ReincarnationScreenSlimePreviewSizeMixin {
   private static final int SLIME_PREVIEW_SCALE = 50;
   private static final int SLIME_PREVIEW_Y_OFFSET = 20;
   private static final int TARATECT_PREVIEW_SCALE = 35;
   private static final int TARATECT_PREVIEW_Y_OFFSET = 10;
   private static final ResourceLocation SMALL_LESSER_TARATECT_ID = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "small_lesser_taratect");

   @Redirect(
      method = "renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphics;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"
      ),
      require = 0
   )
   private void tensuraTno$resizeSlimePreview(
      GuiGraphics graphics, int x1, int y1, int x2, int y2, int scale, float yOffset, float mouseX, float mouseY, LivingEntity entity
   ) {
      if (TensuraTNOCompatConfig.isRacePlayerModelsEnabled() && this.isSlimeRaceSelected()) {
         int adjustedY1 = y1 - 20;
         int adjustedY2 = y2 - 20;
         InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, x1, adjustedY1, x2, adjustedY2, 50, yOffset, mouseX, mouseY, entity);
      } else if (this.isTaratectRaceSelected()) {
         int adjustedY1 = y1 - 10;
         int adjustedY2 = y2 - 10;
         InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, x1, adjustedY1, x2, adjustedY2, 35, yOffset, mouseX, mouseY, entity);
      } else {
         InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, x1, y1, x2, y2, scale, yOffset, mouseX, mouseY, entity);
      }
   }

   private boolean isSlimeRaceSelected() {
      try {
         ResourceLocation id = this.getSelectedRaceId();
         return id != null && SlimeRaceHelper.SLIME_RACE_IDS.contains(id);
      } catch (Throwable t) {
         return false;
      }
   }

   private boolean isTaratectRaceSelected() {
      try {
         ResourceLocation id = this.getSelectedRaceId();
         return SMALL_LESSER_TARATECT_ID.equals(id);
      } catch (Throwable t) {
         return false;
      }
   }

   private ResourceLocation getSelectedRaceId() {
      ReincarnationScreen self = (ReincarnationScreen)this;
      ReincarnationMenu menu = (ReincarnationMenu)self.getMenu();
      if (menu == null) {
         return null;
      } else {
         int index = menu.selectedManasRaceIndex.get();
         List<ManasRace> pool = menu.getRacePool();
         if (pool != null && index >= 0 && index < pool.size()) {
            ManasRace race = pool.get(index);
            return race == null ? null : race.getRegistryName();
         } else {
            return null;
         }
      }
   }
}
