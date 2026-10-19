package net.jadenxgamer.netherexp.mixin.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import java.awt.Color;
import net.jadenxgamer.netherexp.client.assetdriven.FireParticles;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.TreacherousFireBlock;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin extends Block {
   public BaseFireBlockMixin(Properties properties) {
      super(properties);
   }

   @Inject(method = "getState", at = @At("HEAD"), cancellable = true)
   private static void netherexp$getState(BlockGetter reader, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
      BlockState state = reader.getBlockState(pos.below());
      if (TreacherousFireBlock.canSurviveOnBlock(state)) {
         cir.setReturnValue(JNEBlocks.TREACHEROUS_FIRE.get().defaultBlockState());
      }
   }

   @WrapMethod(method = "animateTick")
   private void netherexp$betterFireParticles(BlockState state, Level level, BlockPos pos, RandomSource random, Operation<Void> original) {
      if ((Boolean)JNEConfigs.IMPROVED_FIRE_PARTICLES.get()) {
         if (random.nextInt(24) == 0) {
            level.playLocalSound(
               pos.getX() + 0.5,
               pos.getY() + 0.5,
               pos.getZ() + 0.5,
               SoundEvents.FIRE_AMBIENT,
               SoundSource.BLOCKS,
               1.0F + random.nextFloat(),
               random.nextFloat() * 0.7F + 0.3F,
               false
            );
         }

         ResourceLocation blockId = level.registryAccess().registryOrThrow(Registries.BLOCK).getKey(state.getBlock());
         if ((Boolean)JNEConfigs.FIRE_SMOKE_PARTICLES.get()) {
            FireParticles particles = FireParticles.FIRE_PARTICLES.getOrDefault(blockId, FireParticles.DEFAULT);
            Color smokeStart = particles.smokeStartColors()[random.nextInt(particles.smokeStartColors().length)];
            Color smokeEnd = particles.smokeEndColors()[random.nextInt(particles.smokeEndColors().length)];
            CommonParticles.smokeParticle(level, random, pos.getX(), pos.getY(), pos.getZ(), smokeStart, smokeEnd);
         }

         if ((Boolean)JNEConfigs.FIRE_EMBER_PARTICLES.get()) {
            FireParticles particles = FireParticles.FIRE_PARTICLES.getOrDefault(blockId, FireParticles.DEFAULT);
            if (particles != null && particles.emberColors().length > 0) {
               Color ember = particles.emberColors()[random.nextInt(particles.emberColors().length)];
               CommonParticles.emberParticle(level, random, pos.getX(), pos.getY(), pos.getZ(), ember);
            }
         }
      } else {
         original.call(new Object[]{state, level, pos, random});
      }
   }
}
