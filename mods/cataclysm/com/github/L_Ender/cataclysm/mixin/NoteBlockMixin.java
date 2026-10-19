package com.github.L_Ender.cataclysm.mixin;

import com.github.L_Ender.cataclysm.blockentities.Cataclysm_Skull_BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin extends Block {
   public NoteBlockMixin(Properties p_49795_) {
      super(p_49795_);
   }

   @Inject(method = "getCustomSoundId", at = @At("HEAD"), cancellable = true)
   public void getMobHeadCustomSoundId(Level level, BlockPos pos, CallbackInfoReturnable<ResourceLocation> cir) {
      if (level.getBlockEntity(pos.above()) instanceof Cataclysm_Skull_BlockEntity cataclysmSkullBlock) {
         cir.setReturnValue(cataclysmSkullBlock.getNoteBlockSound());
      }
   }
}
