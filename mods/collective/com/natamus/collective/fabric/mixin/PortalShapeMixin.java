package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import com.natamus.collective_common_fabric.functions.WorldFunctions;
import net.minecraft.class_1936;
import net.minecraft.class_2338;
import net.minecraft.class_2424;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_2424.class, priority = 1001)
public class PortalShapeMixin {
   @Final
   @Shadow
   private class_1936 field_11318;
   @Shadow
   private class_2338 field_11316;

   @Inject(method = "createPortalBlocks()V", at = @At("HEAD"))
   public void createPortalBlocks(CallbackInfo ci) {
      class_2424 portalshape = (class_2424)this;
      ((CollectiveBlockEvents.Possible_Portal_Spawn)CollectiveBlockEvents.ON_NETHER_PORTAL_SPAWN.invoker())
         .onPossiblePortal(WorldFunctions.getWorldIfInstanceOfAndNotRemote(this.field_11318), this.field_11316, portalshape);
   }
}
