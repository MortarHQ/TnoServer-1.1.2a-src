package it.hurts.octostudios.immersiveui.neoforge.mixin;

import it.hurts.octostudios.immersiveui.ImmersiveUI;
import it.hurts.octostudios.immersiveui.util.CommonCode;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class FloatingItemMixin {
   @Unique
   Map<Slot, Float> expandingProgress = new HashMap<>();
   @Shadow
   @Nullable
   protected Slot hoveredSlot;

   @Inject(
      method = "renderSlotContents(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/inventory/Slot;Ljava/lang/String;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderItem(Lnet/minecraft/world/item/ItemStack;III)V", shift = Shift.BEFORE)
   )
   public void renderSize(GuiGraphics guiGraphics, ItemStack itemstack, Slot slot, String countString, CallbackInfo ci) {
      CommonCode.floatingRenderSize(guiGraphics, slot, this.hoveredSlot, this.expandingProgress);
   }

   @Inject(method = "renderSlotHighlight(Lnet/minecraft/client/gui/GuiGraphics;IIII)V", at = @At("HEAD"), cancellable = true)
   private static void disableSlotHighlight(GuiGraphics arg, int i, int j, int k, int color, CallbackInfo ci) {
      if (!ImmersiveUI.CONFIG.isEnableVanillaSlotHighlighting()) {
         ci.cancel();
      } else {
         arg.fillGradient(RenderType.gui(), i, j, i + 16, j + 16, color, color, k);
         ci.cancel();
      }
   }
}
