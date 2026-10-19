package it.hurts.octostudios.reliquified_twilight_forest.client.gui.layer;

import com.mojang.blaze3d.systems.RenderSystem;
import it.hurts.octostudios.reliquified_twilight_forest.init.DataComponentRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.item.GemItem;
import it.hurts.octostudios.reliquified_twilight_forest.item.ability.LichCrownAbilities;
import it.hurts.octostudios.reliquified_twilight_forest.item.relic.LichCrownItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TwilightIndicatorLayer implements Layer {
   public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      Minecraft mc = Minecraft.getInstance();
      ItemStack stack = EntityUtils.findEquippedCurio(mc.player, (Item)ItemRegistry.LICH_CROWN.get());
      if (mc.player != null
         && stack.getItem() instanceof LichCrownItem relic
         && relic.getAbilityLevel(stack, "twilight") > 0
         && LichCrownAbilities.ClientEvents.getEntityLookingAt(mc.player, 64.0) != null
         && mc.options.getCameraType().isFirstPerson()
         && !(mc.getCameraEntity() instanceof LivingEntity living && living.isSleeping())
         && !mc.options.hideGui
         && !mc.player.isSpectator()
         && mc.crosshairPickEntity == null) {
         float partialTick = mc.player.tickCount + deltaTracker.getGameTimeDeltaPartialTick(true);
         float scale = 0.5F;
         float guiScale = (float)(Minecraft.getInstance().getWindow().getGuiScale() * scale);
         float sin = (float)(Math.sin(partialTick / 2.0F) / 4.0 + 0.75);
         int itemX = guiGraphics.guiWidth();
         int itemY = guiGraphics.guiHeight();
         int scissorOffset = Math.round(
            Mth.map(((Integer)stack.getOrDefault(DataComponentRegistry.TWILIGHT_TIME, 0)).intValue(), 0.0F, 50.0F, 0.0F, 12.0F * guiScale)
         );
         guiGraphics.pose().pushPose();
         guiGraphics.pose().scale(scale, scale, 1.0F);
         guiGraphics.pose().translate(0.0F, 0.0F, -150.0F);
         if (scissorOffset == 0) {
            RenderSystem.setShaderColor(sin / 2.0F + 1.125F, sin / 2.0F + 1.125F, sin + 1.25F, sin);
         }

         RenderSystem.enableScissor(
            (int)(itemX * guiScale),
            (int)(Minecraft.getInstance().getWindow().getScreenHeight() - (14 + itemY) * guiScale),
            (int)(16.0F * guiScale),
            (int)(12.0F * guiScale - scissorOffset)
         );
         guiGraphics.renderItem(((GemItem)ItemRegistry.TWILIGHT_GEM.get()).getDefaultInstance(), itemX, itemY);
         guiGraphics.pose().popPose();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableScissor();
      }
   }
}
