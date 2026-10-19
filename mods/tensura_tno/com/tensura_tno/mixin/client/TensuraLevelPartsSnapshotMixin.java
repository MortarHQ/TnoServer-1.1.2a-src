package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.TensuraMultipartQuerySafety;
import io.github.manasmods.tensura.entity.template.TensuraPartEntity;
import io.github.manasmods.tensura.world.subclass.IMultipartLevel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Level.class, priority = 900)
public abstract class TensuraLevelPartsSnapshotMixin {
   @Inject(method = "tensura$getPartsIn", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   @Dynamic("Added to Level by Tensura 2.0.1.1's MixinLevel")
   private void tensuraTno$boundMultipartQuery(AABB bounds, CallbackInfoReturnable<Collection<TensuraPartEntity>> cir) {
      if (!TensuraMultipartQuerySafety.canUseChunkBuckets(bounds)) {
         if (bounds == null) {
            cir.setReturnValue(List.of());
         } else {
            IMultipartLevel multipartLevel = (IMultipartLevel)this;
            Collection<TensuraPartEntity> parts = new ArrayList<>(multipartLevel.tensura$getParts().values());
            List<TensuraPartEntity> result = new ArrayList<>();

            for (TensuraPartEntity part : parts) {
               if (part != null && part.isAlive() && part.getBoundingBox().intersects(bounds)) {
                  result.add(part);
               }
            }

            cir.setReturnValue(result);
         }
      }
   }
}
