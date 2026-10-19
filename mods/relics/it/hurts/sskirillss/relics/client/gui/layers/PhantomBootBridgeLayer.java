package it.hurts.sskirillss.relics.client.gui.layers;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.feet.PhantomBootItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PhantomBootBridgeLayer implements Layer {
   private static final ResourceLocation VIGNETTE = ResourceLocation.withDefaultNamespace("textures/misc/vignette.png");

   public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      Minecraft MC = Minecraft.getInstance();
      LocalPlayer player = MC.player;
      if (player != null) {
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.PHANTOM_BOOT.get());
         if (stack.getItem() instanceof PhantomBootItem relic) {
            int progress = relic.getTime(stack);
            if (progress > 0) {
               int maxProgress = relic.getMaxTime(stack);
               float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
               float progressRatio = Math.min(1.0F, (progress + partialTick * (relic.isToggled(stack) ? 1 : -1)) / maxProgress);
               PoseStack poseStack = guiGraphics.pose();
               poseStack.pushPose();
               Window window = MC.getWindow();
               int width = window.getGuiScaledWidth();
               int height = window.getGuiScaledHeight();
               float alpha = progressRatio * 0.5F;
               RenderSystem.enableBlend();
               GUIRenderer.begin(VIGNETTE, poseStack)
                  .anchor(SpriteAnchor.TOP_LEFT)
                  .patternSize(width, height)
                  .texSize(width, height)
                  .color(11940543)
                  .alpha(alpha)
                  .end();
               RenderSystem.disableBlend();
               poseStack.popPose();
            }
         }
      }
   }
}
