package com.chronodawn.neoforge.mixin;

import com.chronodawn.client.PortalFadeHandler;
import com.chronodawn.registry.ModBlocks;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiPortalOverlayMixin {
   @Unique
   private DeltaTracker chronodawn$deltaTracker;

   @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V", at = @At("TAIL"))
   private void renderChronoDawnPortalOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
      this.chronodawn$deltaTracker = deltaTracker;
      this.chronodawn$renderPortalEffects(guiGraphics);
   }

   @Unique
   private void chronodawn$renderPortalEffects(GuiGraphics guiGraphics) {
      Minecraft minecraft = Minecraft.getInstance();
      LocalPlayer player = minecraft.player;
      if (player != null && minecraft.level != null) {
         BlockPos feetPos = player.blockPosition();
         BlockPos eyePos = BlockPos.containing(player.getEyePosition());
         BlockState feetState = minecraft.level.getBlockState(feetPos);
         BlockState eyeState = minecraft.level.getBlockState(eyePos);
         boolean inPortal = feetState.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get()) || eyeState.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get());
         if (inPortal) {
            BlockPos portalPos = feetState.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get()) ? feetPos : eyePos;
            float overlayAlpha = this.chronodawn$calculateOverlayAlpha(player, portalPos);
            if (overlayAlpha > 0.0F) {
               this.chronodawn$renderPortalOverlay(guiGraphics, overlayAlpha);
            }
         }

         float fadeAlpha = PortalFadeHandler.getFadeAlpha();
         if (fadeAlpha > 0.0F) {
            int color = (int)(fadeAlpha * 255.0F) << 24;
            guiGraphics.fill(0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), color);
         }
      }
   }

   @Unique
   private float chronodawn$calculateOverlayAlpha(LocalPlayer player, BlockPos portalPos) {
      return 0.6F;
   }

   @Unique
   private void chronodawn$renderPortalOverlay(GuiGraphics guiGraphics, float alpha) {
      Minecraft minecraft = Minecraft.getInstance();
      LocalPlayer player = minecraft.player;
      if (player != null) {
         int screenWidth = guiGraphics.guiWidth();
         int screenHeight = guiGraphics.guiHeight();
         int r = 219;
         int g = 136;
         int b = 19;
         float partialTick = this.chronodawn$deltaTracker != null ? this.chronodawn$deltaTracker.getGameTimeDeltaPartialTick(false) : 0.0F;
         float time = (float)minecraft.level.getGameTime() + partialTick;
         float pulseAlpha = alpha * (0.8F + 0.2F * (float)Math.sin(time * 0.1F));
         int a = (int)(pulseAlpha * 255.0F);
         int colorWithAlpha = a << 24 | r << 16 | g << 8 | b;
         guiGraphics.fill(0, 0, screenWidth, screenHeight, colorWithAlpha);
      }
   }
}
