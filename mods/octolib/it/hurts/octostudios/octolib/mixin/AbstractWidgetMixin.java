package it.hurts.octostudios.octolib.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import it.hurts.octostudios.octolib.client.screen.widget.Child;
import it.hurts.octostudios.octolib.client.screen.widget.HasRenderMatrix;
import it.hurts.octostudios.octolib.util.RenderUtils;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractWidget.class)
public abstract class AbstractWidgetMixin {
   @Shadow
   protected boolean isHovered;

   @Shadow
   public abstract boolean isMouseOver(double var1, double var3);

   @Accessor("x")
   public abstract int getLocalX();

   @Accessor("y")
   public abstract int getLocalY();

   @Inject(method = "clicked(DD)Z", at = @At("RETURN"), cancellable = true)
   private void inject(double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir) {
      if (this instanceof Child) {
         cir.setReturnValue(this.isMouseOver(mouseX, mouseY));
      }
   }

   @Redirect(
      method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/components/AbstractWidget;isHovered:Z", opcode = 181)
   )
   private void injectAsWell(
      AbstractWidget instance, boolean value, @Local(ordinal = 0, argsOnly = true) int mouseX, @Local(ordinal = 1, argsOnly = true) int mouseY
   ) {
      if (this instanceof Child) {
         this.isHovered = this.isMouseOver(mouseX, mouseY);
      } else {
         this.isHovered = value;
      }
   }

   @Inject(method = "isMouseOver(DD)Z", at = @At("HEAD"))
   private void injectMouseOver(
      CallbackInfoReturnable<Boolean> cir,
      @Local(ordinal = 0, argsOnly = true) LocalDoubleRef mouseX,
      @Local(ordinal = 1, argsOnly = true) LocalDoubleRef mouseY
   ) {
      if (this instanceof Child<?> child && child.getParent() instanceof HasRenderMatrix has) {
         Vec2 bleh = RenderUtils.toScreenCoords(has.getMatrix(), mouseX.get(), mouseY.get());
         mouseX.set(bleh.x);
         mouseY.set(bleh.y);
      }
   }

   @Inject(method = "getX()I", at = @At("HEAD"), cancellable = true)
   private void getGlobalX(CallbackInfoReturnable<Integer> cir) {
      if (this instanceof Child<?> child) {
         cir.setReturnValue(child.getPosition().x);
      }
   }

   @Inject(method = "getY()I", at = @At("HEAD"), cancellable = true)
   private void getGlobalY(CallbackInfoReturnable<Integer> cir) {
      if (this instanceof Child<?> child) {
         cir.setReturnValue(child.getPosition().y);
      }
   }
}
