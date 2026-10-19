package com.benji.netherman.common.block.entity;

import com.benji.netherman.common.block.AltarBlock;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AltarBlockEntity extends BlockEntity implements GeoBlockEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private BlockPos targetPuzzlePos = null;
   private boolean needsSearch = false;
   private int searchLetter = 0;

   public AltarBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.ALTAR.get(), pos, state);
   }

   public void triggerPuzzleSearch(int letter) {
      this.needsSearch = true;
      this.searchLetter = letter;
   }

   public BlockPos getTargetPuzzlePos() {
      return this.targetPuzzlePos;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, AltarBlockEntity entity) {
      if ((Boolean)state.getValue(AltarBlock.LIT)) {
         if (level.isClientSide() && level.random.nextInt(8) == 0) {
            level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0.0, 0.02, 0.0);
         }

         if (!level.isClientSide() && (Boolean)state.getValue(AltarBlock.ACTIVE)) {
            if (entity.needsSearch) {
               entity.performSearch(level, pos);
            }

            if (entity.targetPuzzlePos != null) {
               if (level.getBlockState(entity.targetPuzzlePos).isAir()) {
                  entity.targetPuzzlePos = null;
               } else {
                  ((ServerLevel)level)
                     .sendParticles(
                        ParticleTypes.FLAME,
                        entity.targetPuzzlePos.getX() + 0.5,
                        entity.targetPuzzlePos.getY() + 0.5,
                        entity.targetPuzzlePos.getZ() + 0.5,
                        2,
                        0.2,
                        0.2,
                        0.2,
                        0.01
                     );
               }
            }
         }
      }
   }

   public void performSearch(Level level, BlockPos center) {
      this.needsSearch = false;
      Block targetBlock = this.getTargetBlockForLetter(this.searchLetter);
      if (targetBlock != null) {
         for (int x = -20; x <= 20; x++) {
            for (int y = -20; y <= 20; y++) {
               for (int z = -20; z <= 20; z++) {
                  BlockPos checkPos = center.offset(x, y, z);
                  if (level.getBlockState(checkPos).is(targetBlock)) {
                     this.targetPuzzlePos = checkPos;
                     return;
                  }
               }
            }
         }
      }
   }

   private Block getTargetBlockForLetter(int letter) {
      return switch (letter) {
         case 1 -> (Block)ModBlocks.A_PUZZLE.get();
         case 5 -> (Block)ModBlocks.E_PUZZLE.get();
         case 12 -> (Block)ModBlocks.L_PUZZLE.get();
         case 26 -> (Block)ModBlocks.Z_PUZZLE.get();
         default -> null;
      };
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(
            this,
            "controller",
            0,
            event -> {
               BlockState state = this.getBlockState();
               if (state.hasProperty(AltarBlock.GUESSED) && (Boolean)state.getValue(AltarBlock.GUESSED)) {
                  return event.setAndContinue(RawAnimation.begin().thenLoop("idle_off"));
               }

               if (state.hasProperty(AltarBlock.LIT)) {
                  if (!(Boolean)state.getValue(AltarBlock.LIT)) {
                     return event.setAndContinue(RawAnimation.begin().thenLoop("idle_off"));
                  } else {
                     return state.getValue(AltarBlock.LETTER) == 0
                        ? event.setAndContinue(RawAnimation.begin().thenLoop("idle_on"))
                        : event.setAndContinue(RawAnimation.begin().thenLoop("idle_choose"));
                  }
               } else {
                  return event.setAndContinue(RawAnimation.begin().thenLoop("idle_off"));
               }
            }
         )
      );
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      if (this.targetPuzzlePos != null) {
         tag.putLong("PuzzlePos", this.targetPuzzlePos.asLong());
      }
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      if (tag.contains("PuzzlePos")) {
         this.targetPuzzlePos = BlockPos.of(tag.getLong("PuzzlePos"));
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
