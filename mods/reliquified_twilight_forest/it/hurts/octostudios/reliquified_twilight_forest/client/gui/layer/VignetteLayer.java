package it.hurts.octostudios.reliquified_twilight_forest.client.gui.layer;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.item.relic.GoblinNoseItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class VignetteLayer implements Layer {
   private static final ResourceLocation VIGNETTE = ResourceLocation.withDefaultNamespace("textures/misc/vignette.png");

   public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      Minecraft MC = Minecraft.getInstance();
      Player player = MC.player;
      ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.GOBLIN_NOSE.get());
      if (player != null && stack.getItem() instanceof GoblinNoseItem relic && relic.isAbilityTicking(stack, "vein_seeker")) {
         PoseStack poseStack = guiGraphics.pose();
         Window window = MC.getWindow();
         int width = window.getGuiScaledWidth();
         int height = window.getGuiScaledHeight();
         float partialTick = deltaTracker.getGameTimeDeltaPartialTick(true);
         float alpha = (float)Mth.map(Math.sin((player.tickCount + partialTick) / 20.0F), -1.0, 1.0, 0.2F, 0.4F);
         poseStack.pushPose();
         RenderSystem.enableBlend();
         GUIRenderer.begin(VIGNETTE, poseStack)
            .anchor(SpriteAnchor.TOP_LEFT)
            .patternSize(width, height)
            .texSize(width, height)
            .color(12379707)
            .alpha(alpha)
            .end();
         RenderSystem.disableBlend();
         poseStack.popPose();
      }
   }
}
