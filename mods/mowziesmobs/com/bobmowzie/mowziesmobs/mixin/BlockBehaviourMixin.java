package com.bobmowzie.mowziesmobs.mixin;

import com.bobmowzie.mowziesmobs.server.block.ICopiedBlockProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Properties.class)
public class BlockBehaviourMixin implements ICopiedBlockProperties {
   @Unique
   @Nullable
   public Block mowziesMobs$baseBlock;

   @Inject(method = "ofLegacyCopy", at = @At("RETURN"))
   private static void onCopy(BlockBehaviour blockBehaviour, CallbackInfoReturnable<Properties> cir) {
      if (blockBehaviour instanceof Block block) {
         ((ICopiedBlockProperties)cir.getReturnValue()).mowziesMobs$setBaseBlock(block);
      }
   }

   @Nullable
   @Override
   public Block mowziesMobs$getBaseBlock() {
      return this.mowziesMobs$baseBlock;
   }

   @Override
   public void mowziesMobs$setBaseBlock(Block block) {
      this.mowziesMobs$baseBlock = block;
   }
}
