package com.benji.netherman.common.block.entity;

import com.benji.netherman.common.block.GrandDoorBlock;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

public class GrandDoorBlockEntity extends BlockEntity implements GeoBlockEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final int STATE_CLOSED = 0;
   public static final int STATE_OPENING = 1;
   public static final int STATE_OPEN = 2;
   public static final int STATE_CLOSING = 3;
   private static final RawAnimation ANIM_OPEN = RawAnimation.begin().thenPlayAndHold("open");
   private static final RawAnimation ANIM_IDLE_OPEN = RawAnimation.begin().thenLoop("idle_open");
   private static final RawAnimation ANIM_CLOSE = RawAnimation.begin().thenPlayAndHold("close");
   private static final RawAnimation ANIM_IDLE_CLOSE = RawAnimation.begin().thenLoop("idle_close");
   public int bossCooldown = 0;
   private int doorState = 0;
   private int animTimer = 0;
   private int autoCloseTimer = 0;
   private boolean isPermanent = false;

   public GrandDoorBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.GRAND_DOOR.get(), pos, state);
   }

   public int getDoorState() {
      return this.doorState;
   }

   public void setDoorState(int state) {
      this.doorState = state;
      this.setChanged();
      if (this.level != null && !this.level.isClientSide()) {
         boolean isOpen = state == 2 || state == 1;
         BlockState mainState = this.getBlockState();
         if (state == 1) {
            this.level.playSound(null, this.worldPosition, (SoundEvent)ModSounds.GRAND_DOOR_OPEN.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
         } else if (state == 3) {
            this.level.playSound(null, this.worldPosition, (SoundEvent)ModSounds.GRAND_DOOR_CLOSE.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
         }

         if (mainState.hasProperty(BlockStateProperties.OPEN) && (Boolean)mainState.getValue(BlockStateProperties.OPEN) != isOpen) {
            this.level.setBlock(this.worldPosition, (BlockState)mainState.setValue(BlockStateProperties.OPEN, isOpen), 3);
            Direction right = ((Direction)mainState.getValue(GrandDoorBlock.FACING)).getClockWise();

            for (int w = 0; w < 6; w++) {
               for (int h = 0; h < 12; h++) {
                  if (w != 0 || h != 0) {
                     BlockPos p = this.worldPosition.relative(right, w).above(h);
                     BlockState st = this.level.getBlockState(p);
                     if (st.is((Block)ModBlocks.GRAND_DOOR_PART.get())
                        && st.hasProperty(BlockStateProperties.OPEN)
                        && (Boolean)st.getValue(BlockStateProperties.OPEN) != isOpen) {
                        this.level.setBlock(p, (BlockState)st.setValue(BlockStateProperties.OPEN, isOpen), 3);
                     }
                  }
               }
            }
         }

         this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
      }
   }

   public void togglePermanent() {
      if (this.doorState == 0 || this.doorState == 3) {
         this.setDoorState(1);
         this.animTimer = 60;
         this.isPermanent = true;
         this.bossCooldown = 36000;
      } else if (this.doorState == 2 || this.doorState == 1) {
         this.setDoorState(3);
         this.animTimer = 40;
         this.isPermanent = true;
         this.bossCooldown = 36000;
      }
   }

   public void openTemporary() {
      if (this.doorState == 0) {
         this.setDoorState(1);
         this.animTimer = 60;
         this.autoCloseTimer = 1200;
         this.isPermanent = false;
      }
   }

   public static void tick(Level level, BlockPos pos, BlockState state, GrandDoorBlockEntity entity) {
      if (!level.isClientSide()) {
         if (entity.bossCooldown > 0) {
            entity.bossCooldown--;
         }

         if (entity.doorState == 1) {
            entity.animTimer--;
            if (entity.animTimer <= 0) {
               entity.setDoorState(2);
            }
         } else if (entity.doorState == 3) {
            entity.animTimer--;
            if (entity.animTimer <= 0) {
               entity.setDoorState(0);
            }
         } else if (entity.doorState == 2 && !entity.isPermanent) {
            if (entity.autoCloseTimer > 0) {
               entity.autoCloseTimer--;
            } else {
               entity.setDoorState(3);
               entity.animTimer = 40;
            }
         }
      } else if (entity.doorState == 1 || entity.doorState == 3) {
         Direction facing = (Direction)state.getValue(GrandDoorBlock.FACING);
         Direction right = facing.getClockWise();

         for (int i = 0; i < 40; i++) {
            double w = level.random.nextDouble() * 6.0;
            double h = level.random.nextDouble() * 12.0;
            double px = pos.getX() + 0.5 + right.getStepX() * w;
            double py = pos.getY() + h;
            double pz = pos.getZ() + 0.5 + right.getStepZ() * w;
            level.addParticle(
               new BlockParticleOption(ParticleTypes.BLOCK, Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState()),
               px,
               py,
               pz,
               (level.random.nextDouble() - 0.5) * 0.3,
               level.random.nextDouble() * 0.4,
               (level.random.nextDouble() - 0.5) * 0.3
            );
         }

         LocalPlayer player = Minecraft.getInstance().player;
         if (player != null) {
            double distSq = player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
            if (distSq <= 400.0) {
               float intensity = (float)(1.0 - distSq / 400.0) * 5.5F;
               player.setXRot(player.getXRot() + (level.random.nextFloat() - 0.5F) * intensity);
               player.setYRot(player.getYRot() + (level.random.nextFloat() - 0.5F) * intensity);
            }
         }
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 5, event -> {
         return switch (this.doorState) {
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

   public AABB getRenderBoundingBox() {
      return new AABB(this.getBlockPos()).inflate(12.0);
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.putInt("DoorState", this.doorState);
      tag.putInt("AnimTimer", this.animTimer);
      tag.putInt("AutoClose", this.autoCloseTimer);
      tag.putBoolean("Permanent", this.isPermanent);
      tag.putInt("BossCooldown", this.bossCooldown);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.doorState = tag.getInt("DoorState");
      this.animTimer = tag.getInt("AnimTimer");
      this.autoCloseTimer = tag.getInt("AutoClose");
      this.isPermanent = tag.getBoolean("Permanent");
      this.bossCooldown = tag.getInt("BossCooldown");
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
