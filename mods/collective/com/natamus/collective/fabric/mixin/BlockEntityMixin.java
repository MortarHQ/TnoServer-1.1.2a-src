package com.natamus.collective.fabric.mixin;

import com.natamus.collective_common_fabric.data.BlockEntityData;
import com.natamus.collective_common_fabric.globalcallbacks.CachedBlockEntityCallback;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_1937;
import net.minecraft.class_2586;
import net.minecraft.class_2591;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_2586.class, priority = 1001)
public class BlockEntityMixin {
   @Shadow
   @Final
   private class_2591<?> field_11864;
   @Shadow
   protected class_1937 field_11863;

   @Inject(method = "setLevel(Lnet/minecraft/world/level/Level;)V", at = @At("TAIL"))
   public void setLevel(class_1937 level, CallbackInfo ci) {
      if (BlockEntityData.blockEntitiesToCache.contains(this.field_11864) && level != null) {
         if (!BlockEntityData.cachedBlockEntities.get(this.field_11864).containsKey(level)) {
            BlockEntityData.cachedBlockEntities.get(this.field_11864).put(level, new CopyOnWriteArrayList<>());
         }

         class_2586 blockEntity = (class_2586)this;
         BlockEntityData.cachedBlockEntities.get(this.field_11864).get(level).add(blockEntity);
         CachedBlockEntityCallback.BLOCK_ENTITY_ADDED.invoker().onBlockEntityAdded(level, blockEntity, this.field_11864);
      }
   }

   @Inject(method = "setRemoved()V", at = @At("TAIL"))
   public void setRemoved(CallbackInfo ci) {
      if (BlockEntityData.blockEntitiesToCache.contains(this.field_11864)
         && this.field_11863 != null
         && BlockEntityData.cachedBlockEntities.get(this.field_11864).containsKey(this.field_11863)) {
         class_2586 blockEntity = (class_2586)this;
         BlockEntityData.cachedBlockEntities.get(this.field_11864).get(this.field_11863).remove(blockEntity);
         CachedBlockEntityCallback.BLOCK_ENTITY_REMOVED.invoker().onBlockEntityRemoved(this.field_11863, blockEntity, this.field_11864);
      }
   }
}
