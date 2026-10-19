package uk.co.extraspecialstudio.lootr_liaison.mixin;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelChunk.class)
public class MixinLevelChunkSetLevelEarly {
   @Shadow
   @Final
   Level level;

   @Inject(method = "addAndRegisterBlockEntity", at = @At("HEAD"), require = 0)
   private void lootr_liaison_setLevelBeforeAdd(BlockEntity blockEntity, CallbackInfo ci) {
      if (blockEntity != null && blockEntity.getLevel() == null && this.level != null) {
         blockEntity.setLevel(this.level);
      }
   }
}
