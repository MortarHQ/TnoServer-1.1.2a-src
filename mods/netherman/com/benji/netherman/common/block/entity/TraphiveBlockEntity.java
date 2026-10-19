package com.benji.netherman.common.block.entity;

import com.benji.netherman.common.block.TraphiveBlock;
import com.benji.netherman.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TraphiveBlockEntity extends BlockEntity implements GeoBlockEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final int STATE_CLOSED = 0;
   public static final int STATE_OPENING = 1;
   public static final int STATE_OPEN = 2;
   public static final int STATE_CLOSING = 3;
   private static final RawAnimation ANIM_OPEN = RawAnimation.begin().thenPlayAndHold("open");
   private static final RawAnimation ANIM_IDLE_OPEN = RawAnimation.begin().thenLoop("open_idle");
   private static final RawAnimation ANIM_CLOSE = RawAnimation.begin().thenPlayAndHold("close");
   private static final RawAnimation ANIM_IDLE_CLOSE = RawAnimation.begin().thenLoop("close_idle");
   private int blockStateAnim = 0;
   private boolean isWaitingToOpen = false;
   private int delayTimer = 0;
   private int animTimer = 0;
   private int autoCloseTimer = 0;
   private int contactTimer = 0;

   public TraphiveBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.TRAPHIVE.get(), pos, state);
   }

   public int getBlockStateAnim() {
      return this.blockStateAnim;
   }

   public void setBlockStateAnim(int state) {
      this.blockStateAnim = state;
      this.setChanged();
      if (this.level != null && !this.level.isClientSide()) {
         this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
      }
   }

   public void triggerWave(int distance, int maxDistance) {
      if (this.blockStateAnim == 0) {
         this.delayTimer = (int)(Math.pow(distance, 0.8) * 3.0);
         this.isWaitingToOpen = true;
         int reverseDistance = maxDistance - distance;
         this.autoCloseTimer = 100 + (int)(Math.pow(reverseDistance, 0.8) * 3.0);
      }
   }

   public static void tick(Level level, BlockPos pos, BlockState state, TraphiveBlockEntity entity) {
      if (!level.isClientSide()) {
         if (entity.blockStateAnim == 0 && !entity.isWaitingToOpen) {
            AABB touchBox = new AABB(pos).inflate(0.05);
            boolean isTouching = false;

            for (Player player : level.players()) {
               if (player.isAlive() && player.getBoundingBox().intersects(touchBox)) {
                  isTouching = true;
                  break;
               }
            }

            if (isTouching) {
               entity.contactTimer++;
               if (entity.contactTimer >= 10) {
                  if (state.getBlock() instanceof TraphiveBlock block) {
                     block.activateWave(level, pos);
                  }

                  entity.contactTimer = 0;
               }
            } else if (entity.contactTimer > 0) {
               entity.contactTimer--;
            }
         }

         if (entity.isWaitingToOpen) {
            entity.delayTimer--;
            if (entity.delayTimer <= 0) {
               entity.isWaitingToOpen = false;
               entity.setBlockStateAnim(1);
               entity.animTimer = 20;
               level.playSound(null, pos, SoundEvents.SHROOMLIGHT_BREAK, SoundSource.BLOCKS, 1.0F, 0.9F + level.random.nextFloat() * 0.2F);
               level.setBlockAndUpdate(pos, (BlockState)state.setValue(BlockStateProperties.OPEN, true));
            }
         } else if (entity.blockStateAnim == 1) {
            entity.animTimer--;
            if (entity.animTimer <= 0) {
               entity.setBlockStateAnim(2);
            }
         } else if (entity.blockStateAnim == 2) {
            entity.autoCloseTimer--;
            if (entity.autoCloseTimer <= 0) {
               entity.setBlockStateAnim(3);
               entity.animTimer = 20;
               level.playSound(null, pos, SoundEvents.SHROOMLIGHT_BREAK, SoundSource.BLOCKS, 1.0F, 0.9F + level.random.nextFloat() * 0.2F);
            }
         } else if (entity.blockStateAnim == 3) {
            entity.animTimer--;
            if (entity.animTimer <= 0) {
               entity.setBlockStateAnim(0);
               level.setBlockAndUpdate(pos, (BlockState)state.setValue(BlockStateProperties.OPEN, false));
            }
         }
      } else if (entity.blockStateAnim == 1 || entity.blockStateAnim == 3) {
         for (int i = 0; i < 2; i++) {
            double px = pos.getX() + level.random.nextDouble();
            double py = pos.getY() + level.random.nextDouble();
            double pz = pos.getZ() + level.random.nextDouble();
            level.addParticle(DustParticleOptions.REDSTONE, px, py, pz, 0.0, 0.05, 0.0);
         }
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, event -> {
         return switch (this.blockStateAnim) {
            case 1 -> event.setAndContinue(ANIM_OPEN);
            case 2 -> event.setAndContinue(ANIM_IDLE_OPEN);
            case 3 -> event.setAndContinue(ANIM_CLOSE);
            default -> event.setAndContinue(ANIM_IDLE_CLOSE);
         };
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.putInt("BlockStateAnim", this.blockStateAnim);
      tag.putBoolean("Waiting", this.isWaitingToOpen);
      tag.putInt("DelayTimer", this.delayTimer);
      tag.putInt("AnimTimer", this.animTimer);
      tag.putInt("AutoClose", this.autoCloseTimer);
      tag.putInt("ContactTimer", this.contactTimer);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.blockStateAnim = tag.getInt("BlockStateAnim");
      this.isWaitingToOpen = tag.getBoolean("Waiting");
      this.delayTimer = tag.getInt("DelayTimer");
      this.animTimer = tag.getInt("AnimTimer");
      this.autoCloseTimer = tag.getInt("AutoClose");
      this.contactTimer = tag.getInt("ContactTimer");
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag tag = new CompoundTag();
      this.saveAdditional(tag, registries);
      return tag;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }
}
