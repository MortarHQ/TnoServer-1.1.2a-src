package bigchadguys.sellingbin.mixin;

import bigchadguys.sellingbin.render.SellingBinTooltipComponent;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public class MixinDrawContext {
   @Inject(method = "renderTooltip(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V", at = @At("HEAD"))
   public void drawItemTooltipHead(Font textRenderer, ItemStack stack, int x, int y, CallbackInfo ci) {
      SellingBinTooltipComponent.STACK.set(stack);
   }

   @Inject(method = "renderTooltip(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V", at = @At("RETURN"))
   public void drawItemTooltipReturn(Font textRenderer, ItemStack stack, int x, int y, CallbackInfo ci) {
      SellingBinTooltipComponent.STACK.remove();
   }

   @Redirect(
      method = "renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V"
      )
   )
   public void drawTooltip(GuiGraphics instance, Font textRenderer, List<ClientTooltipComponent> components, int x, int y, ClientTooltipPositioner positioner) {
      if (SellingBinTooltipComponent.STACK.get() != null) {
         components = new ArrayList<>(components);
         SellingBinTooltipComponent component = new SellingBinTooltipComponent(SellingBinTooltipComponent.STACK.get());
         if (component.isActive()) {
            components.add(component);
         }
      }

      instance.renderTooltipInternal(textRenderer, components, x, y, positioner);
   }
}
