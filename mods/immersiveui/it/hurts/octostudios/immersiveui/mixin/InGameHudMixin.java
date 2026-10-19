package it.hurts.octostudios.immersiveui.mixin;

import it.hurts.octostudios.immersiveui.ImmersiveUI;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Gui.class, priority = -1)
public abstract class InGameHudMixin {
   @Unique
   int scaledWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
   @Unique
   Minecraft client = Minecraft.getInstance();
   @Unique
   private double position = 0.0;

   @Shadow
   protected abstract Player getCameraPlayer();

   @Shadow
   public abstract void resetTitleTimes();

   @Inject(
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1),
      method = "renderItemHotbar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V"
   )
   private void translatePose(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(0.0F, 0.0F, ImmersiveUI.CONFIG.isRenderHotbarSelectorAboveItems() ? 400.0F : 0.0F);
   }

   @Inject(
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
         ordinal = 1,
         shift = Shift.AFTER
      ),
      method = "renderItemHotbar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V"
   )
   private void popPose(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
      guiGraphics.pose().popPose();
   }

   @ModifyArg(
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1),
      index = 1,
      method = "renderItemHotbar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V"
   )
   private int selectedSlotPositionX(int originalX) {
      if (!ImmersiveUI.CONFIG.isEnableHotbarSelectorAnimation()) {
         return originalX;
      }

      double speed = ImmersiveUI.CONFIG.getHotbarSelectorSpeed();
      int i = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
      Player playerEntity = this.getCameraPlayer();
      assert playerEntity != null;
      int selectedSlot = playerEntity.getInventory().selected;
      double toMove = Math.abs(selectedSlot - this.position) / 2.0 * this.client.getTimer().getRealtimeDeltaTicks() * speed;
      if (this.position > selectedSlot) {
         this.position = Mth.clamp(this.position - toMove, selectedSlot, this.position);
      } else if (this.position < selectedSlot) {
         this.position = Mth.clamp(this.position + toMove, this.position, selectedSlot);
      }

      if (Math.abs(this.position - selectedSlot) < 0.05) {
         this.position = selectedSlot;
      }

      return (int)(i - 91 - 1 + Math.round(this.position * 20.0));
   }
}
