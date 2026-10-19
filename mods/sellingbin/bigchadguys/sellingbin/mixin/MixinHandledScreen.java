package bigchadguys.sellingbin.mixin;

import bigchadguys.sellingbin.render.SellingBinTooltipComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class MixinHandledScreen {
   @Shadow
   protected Slot hoveredSlot;

   @Inject(method = "renderTooltip(Lnet/minecraft/client/gui/GuiGraphics;II)V", at = @At("HEAD"))
   protected void drawMouseoverTooltipHead(GuiGraphics context, int x, int y, CallbackInfo ci) {
      if (this.hoveredSlot != null) {
         SellingBinTooltipComponent.STACK.set(this.hoveredSlot.getItem());
      }
   }

   @Inject(method = "renderTooltip(Lnet/minecraft/client/gui/GuiGraphics;II)V", at = @At("RETURN"))
   protected void drawMouseoverTooltipReturn(GuiGraphics context, int x, int y, CallbackInfo ci) {
      SellingBinTooltipComponent.STACK.remove();
   }
}
