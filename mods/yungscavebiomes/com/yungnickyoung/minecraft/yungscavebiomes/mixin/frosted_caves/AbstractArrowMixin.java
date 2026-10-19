package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.block.IceSheetBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.ArrowAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Entity {
   private AbstractArrowMixin(EntityType<?> entityType, Level level) {
      super(entityType, level);
   }

   @Inject(method = "onHitBlock", at = @At("RETURN"))
   public void yungscavebiomes_tippedArrowOnHitBlockSpawnIceSheets(BlockHitResult blockHitResult, CallbackInfo ci) {
      if (!this.level().isClientSide && isArrow(this)) {
         Arrow arrow = asArrow(this);
         PotionContents potionContents = ((ArrowAccessor)arrow).callGetPotionContents();

         for (MobEffectInstance mobEffectInstance : potionContents.getAllEffects()) {
            if (mobEffectInstance.getEffect().is(MobEffectModule.FROZEN_EFFECT.getHolder())) {
               BlockPos originPos = blockHitResult.getBlockPos();
               boolean amplified = mobEffectInstance.getAmplifier() > 0;
               placeIceSheets(this.level(), originPos, amplified);
            }
         }
      }
   }

   @Inject(method = "onHitEntity", at = @At("RETURN"))
   public void yungscavebiomes_tippedArrowOnHitEntitySpawnIceSheets(EntityHitResult entityHitResult, CallbackInfo ci) {
      if (!this.level().isClientSide && isArrow(this)) {
         Arrow arrow = asArrow(this);
         PotionContents potionContents = ((ArrowAccessor)arrow).callGetPotionContents();

         for (MobEffectInstance mobEffectInstance : potionContents.getAllEffects()) {
            if (mobEffectInstance.getEffect().is(MobEffectModule.FROZEN_EFFECT.getHolder())) {
               BlockPos originPos = entityHitResult.getEntity().getOnPos();
               boolean amplified = mobEffectInstance.getAmplifier() > 0;
               placeIceSheets(this.level(), originPos, amplified);
            }
         }
      }
   }

   @Unique
   private static void placeIceSheets(Level level, BlockPos originPos, boolean amplified) {
      MutableBlockPos currPos = originPos.mutable();
      MutableBlockPos mutable = currPos.mutable();
      int attemptDistance = amplified ? 4 : 3;
      int maxDist = amplified ? 14 : 8;

      for (int x = -attemptDistance; x <= attemptDistance; x++) {
         for (int y = -attemptDistance; y <= attemptDistance; y++) {
            for (int z = -attemptDistance; z <= attemptDistance; z++) {
               if (x * x + y * y + z * z <= maxDist) {
                  currPos.setWithOffset(originPos, x, y, z);
                  BlockState currState = level.getBlockState(currPos);
                  if (currState.isAir() || currState.is(Blocks.WATER) || currState.is(BlockTags.REPLACEABLE)) {
                     for (Direction direction : Direction.values()) {
                        mutable.setWithOffset(currPos, direction);
                        IceSheetBlock iceSheetBlock = (IceSheetBlock)BlockModule.ICE_SHEET.get();
                        BlockState updatedBlockState = iceSheetBlock.getStateForPlacement(currState, level, currPos, direction);
                        if (updatedBlockState != null) {
                           updatedBlockState = (BlockState)updatedBlockState.setValue(IceSheetBlock.GROWTH_DISTANCE, 0);
                           currState = updatedBlockState;
                           level.setBlock(currPos, updatedBlockState, 3);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Unique
   private static boolean isArrow(Object object) {
      return object instanceof Arrow;
   }

   @Unique
   private static Arrow asArrow(Object object) {
      return (Arrow)object;
   }
}
