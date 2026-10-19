package org.anti_ad.mc.ipnext.mixin;

import dev.isxander.controlify.api.vmousesnapping.SnapPoint;
import dev.isxander.controlify.virtualmouse.VirtualMouseHandler;
import java.util.Set;
import org.anti_ad.mc.ipnext.gui.inject.ContainerScreenEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = VirtualMouseHandler.class, remap = false)
public class MixinVirtualMouseHandler {
   @Shadow
   private Set snapPoints;

   @Inject(
      at = @At(
         value = "FIELD",
         target = "Ldev/isxander/controlify/virtualmouse/VirtualMouseHandler;snapPoints:Ljava/util/Set;",
         shift = Shift.AFTER,
         ordinal = 0
      ),
      method = "handleControllerInput(Ldev/isxander/controlify/controller/ControllerEntity;)V",
      constraints = ""
   )
   void ipn$VirtualMouseHandler$handleControllerInput(CallbackInfo var1) {
      ContainerScreenEventHandler.INSTANCE.getWidgetPoints().forEach(var1x -> {
         SnapPoint var2 = new SnapPoint(var1x.getX() + var1x.getWidth() / 2, var1x.getY() + var1x.getHeight() / 2, 8);
         this.snapPoints.add(var2);
      });
   }
}
