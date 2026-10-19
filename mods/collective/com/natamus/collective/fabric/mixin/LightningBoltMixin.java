package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveEntityEvents;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1538;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = class_1538.class, priority = 1001)
public class LightningBoltMixin {
   @Inject(
      method = "tick()V",
      at = @At(
         value = "INVOKE_ASSIGN",
         target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;",
         ordinal = 1
      ),
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   public void tick(CallbackInfo ci, List<class_1297> list) {
      class_1538 lightningBolt = (class_1538)this;
      list.removeIf(
         entity -> !((CollectiveEntityEvents.Entity_On_Lightning_Strike)CollectiveEntityEvents.ON_ENTITY_LIGHTNING_STRIKE.invoker())
            .onLightningStrike(entity.method_37908(), entity, lightningBolt)
      );
   }
}
