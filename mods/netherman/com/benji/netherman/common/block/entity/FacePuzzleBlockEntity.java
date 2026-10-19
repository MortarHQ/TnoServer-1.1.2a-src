package com.benji.netherman.common.block.entity;

import com.benji.netherman.common.block.FacePuzzleBlock;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FacePuzzleBlockEntity extends BlockEntity implements GeoBlockEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private int clickState = 0;
   private boolean isSolved = false;
   private static final RawAnimation ANIM_INCORRECT_IDLE = RawAnimation.begin().thenLoop("incorrect_idle");
   private static final RawAnimation ANIM_CLICK_1 = RawAnimation.begin().thenPlayAndHold("click_1");
   private static final RawAnimation ANIM_CLICK_2 = RawAnimation.begin().thenPlayAndHold("click_2");
   private static final RawAnimation ANIM_CLICK_CORRECT = RawAnimation.begin().thenPlayAndHold("click_correct");

   public FacePuzzleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public int getClickState() {
      return this.clickState;
   }

   public boolean isSolved() {
      return this.isSolved;
   }

   public void advanceState() {
      if (!this.isSolved && this.level != null && !this.level.isClientSide()) {
         BlockState state = this.getBlockState();
         int maxStates = (Integer)state.getValue(FacePuzzleBlock.MAX_STATES);
         this.clickState++;
         if (this.clickState >= maxStates) {
            this.clickState = 0;
         }

         this.level.playSound(null, this.worldPosition, (SoundEvent)ModSounds.HIRRING.get(), SoundSource.BLOCKS, 1.0F, 0.8F);
         this.setChanged();
         this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
         this.checkWinCondition();
      }
   }

   private void checkWinCondition() {
      if (this.level != null) {
         int myMaxStates = (Integer)this.getBlockState().getValue(FacePuzzleBlock.MAX_STATES);
         if (this.clickState == myMaxStates - 1) {
            int correctPiecesCount = 0;
            int searchRadius = 3;

            for (BlockPos checkPos : BlockPos.betweenClosed(
               this.worldPosition.offset(-searchRadius, -searchRadius, -searchRadius), this.worldPosition.offset(searchRadius, searchRadius, searchRadius)
            )) {
               if (this.level.getBlockEntity(checkPos) instanceof FacePuzzleBlockEntity entity) {
                  int hisMaxStates = (Integer)entity.getBlockState().getValue(FacePuzzleBlock.MAX_STATES);
                  if (entity.getClickState() == hisMaxStates - 1 && !entity.isSolved()) {
                     correctPiecesCount++;
                  }
               }
            }

            if (correctPiecesCount == 4) {
               this.triggerWin();
            }
         }
      }
   }

   private void triggerWin() {
      if (this.level != null) {
         int searchRadius = 3;

         for (BlockPos checkPos : BlockPos.betweenClosed(
            this.worldPosition.offset(-searchRadius, -searchRadius, -searchRadius), this.worldPosition.offset(searchRadius, searchRadius, searchRadius)
         )) {
            if (this.level.getBlockEntity(checkPos) instanceof FacePuzzleBlockEntity entity) {
               int hisMaxStates = (Integer)entity.getBlockState().getValue(FacePuzzleBlock.MAX_STATES);
               if (entity.getClickState() == hisMaxStates - 1 && !entity.isSolved()) {
                  entity.isSolved = true;
                  entity.setChanged();
                  this.level.sendBlockUpdated(entity.worldPosition, entity.getBlockState(), entity.getBlockState(), 3);
               }
            }
         }

         this.level.playSound(null, this.worldPosition, (SoundEvent)ModSounds.GIANT_BELL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         if (this.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
               ParticleTypes.TOTEM_OF_UNDYING,
               this.worldPosition.getX() + 0.5,
               this.worldPosition.getY() + 0.5,
               this.worldPosition.getZ() + 0.5,
               100,
               0.5,
               0.5,
               0.5,
               0.2
            );
         }

         ItemEntity key = new ItemEntity(
            this.level,
            this.worldPosition.getX() + 0.5,
            this.worldPosition.getY() + 0.5,
            this.worldPosition.getZ() + 0.5,
            new ItemStack((ItemLike)ModItems.MAZE_KEY.get())
         );
         this.level.addFreshEntity(key);
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, event -> {
         if (this.isSolved) {
            return event.setAndContinue(ANIM_CLICK_CORRECT);
         }

         int maxStates = (Integer)this.getBlockState().getValue(FacePuzzleBlock.MAX_STATES);
         if (maxStates == 2) {
            return this.clickState == 1 ? event.setAndContinue(ANIM_CLICK_CORRECT) : event.setAndContinue(ANIM_INCORRECT_IDLE);
         }

         if (maxStates == 3) {
            if (this.clickState == 1) {
               return event.setAndContinue(ANIM_CLICK_1);
            } else {
               return this.clickState == 2 ? event.setAndContinue(ANIM_CLICK_CORRECT) : event.setAndContinue(ANIM_INCORRECT_IDLE);
            }
         } else {
            return event.setAndContinue(ANIM_INCORRECT_IDLE);
         }
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.putInt("ClickState", this.clickState);
      tag.putBoolean("IsSolved", this.isSolved);
   }

   public void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.clickState = tag.getInt("ClickState");
      this.isSolved = tag.getBoolean("IsSolved");
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag tag = new CompoundTag();
      this.saveAdditional(tag, registries);
      return tag;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, Provider registries) {
      if (pkt.getTag() != null) {
         this.loadAdditional(pkt.getTag(), registries);
      }
   }
}
