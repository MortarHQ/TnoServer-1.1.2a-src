package com.natamus.collective.neoforge.mixin;

import com.natamus.collective_common_neoforge.data.BlockEntityData;
import com.natamus.collective_common_neoforge.globalcallbacks.CachedBlockEntityCallback;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockEntity.class, priority = 1001)
public class BlockEntityMixin {
   @Shadow
   @Final
   private BlockEntityType<?> type;
   @Shadow
   protected Level level;

   @Inject(method = "setLevel(Lnet/minecraft/world/level/Level;)V", at = @At("TAIL"))
   public void setLevel(Level level, CallbackInfo ci) {
      if (BlockEntityData.blockEntitiesToCache.contains(this.type) && level != null) {
         if (!BlockEntityData.cachedBlockEntities.get(this.type).containsKey(level)) {
            BlockEntityData.cachedBlockEntities.get(this.type).put(level, new CopyOnWriteArrayList<>());
         }

         BlockEntity blockEntity = (BlockEntity)this;
         BlockEntityData.cachedBlockEntities.get(this.type).get(level).add(blockEntity);
         CachedBlockEntityCallback.BLOCK_ENTITY_ADDED.invoker().onBlockEntityAdded(level, blockEntity, this.type);
      }
   }

   @Inject(method = "setRemoved()V", at = @At("TAIL"))
   public void setRemoved(CallbackInfo ci) {
      if (BlockEntityData.blockEntitiesToCache.contains(this.type)
         && this.level != null
         && BlockEntityData.cachedBlockEntities.get(this.type).containsKey(this.level)) {
         BlockEntity blockEntity = (BlockEntity)this;
         BlockEntityData.cachedBlockEntities.get(this.type).get(this.level).remove(blockEntity);
         CachedBlockEntityCallback.BLOCK_ENTITY_REMOVED.invoker().onBlockEntityRemoved(this.level, blockEntity, this.type);
      }
   }
}
