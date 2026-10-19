package com.mortal_cultivation.block.entity;

import com.mortal_cultivation.init.BlockEntityRegistry;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class AncientSteleBlockEntity extends BlockEntity implements GeoBlockEntity {
   public static final int ABSORPTION_DURATION_TICKS = 100;
   public static final int SHATTER_DURATION_TICKS = 30;
   private static final String PHASE_TAG = "Phase";
   private static final String PHASE_START_TIME_TAG = "PhaseStartGameTime";
   private static final String ACTIVE_ORB_TAG = "ActiveOrb";
   private static final RawAnimation RUNE_ORBIT = RawAnimation.begin().thenLoop("animation.bei_wen.rune_orbit");
   private static final RawAnimation RUNE_ABSORB = RawAnimation.begin().thenPlay("animation.bei_wen.rune_absorb");
   private static final RawAnimation SHATTER = RawAnimation.begin().thenPlay("animation.bei_wen.shatter");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private AncientSteleBlockEntity.Phase phase = AncientSteleBlockEntity.Phase.IDLE;
   private long phaseStartGameTime;
   private boolean absorbAnimationActive;
   @Nullable
   private UUID activeOrbUuid;

   public AncientSteleBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)BlockEntityRegistry.ANCIENT_STELE.get(), pos, state);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "rune_orbit", 0, state -> {
         if (this.phase == AncientSteleBlockEntity.Phase.SHATTERING) {
            return PlayState.STOP;
         }

         state.getController().setAnimation(RUNE_ORBIT);
         return PlayState.CONTINUE;
      }));
      controllers.add(new AnimationController(this, "rune_absorb", 0, state -> {
         if (this.phase != AncientSteleBlockEntity.Phase.ABSORBING) {
            this.absorbAnimationActive = false;
            return PlayState.STOP;
         }

         if (!this.absorbAnimationActive) {
            state.getController().forceAnimationReset();
            this.absorbAnimationActive = true;
         }

         state.getController().setAnimation(RUNE_ABSORB);
         return PlayState.CONTINUE;
      }));
      controllers.add(new AnimationController(this, "stele_shatter", 0, state -> {
         if (this.phase != AncientSteleBlockEntity.Phase.SHATTERING) {
            return PlayState.STOP;
         }

         state.getController().setAnimation(SHATTER);
         return PlayState.CONTINUE;
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public boolean canReserveOrb() {
      return this.phase == AncientSteleBlockEntity.Phase.IDLE && this.activeOrbUuid == null;
   }

   public boolean reserveOrb(UUID orbUuid) {
      if (!this.canReserveOrb()) {
         return false;
      }

      this.activeOrbUuid = orbUuid;
      this.phase = AncientSteleBlockEntity.Phase.RESERVED;
      this.phaseStartGameTime = this.currentGameTime();
      this.syncState();
      return true;
   }

   public boolean beginAbsorption(UUID orbUuid) {
      if (this.phase == AncientSteleBlockEntity.Phase.RESERVED && this.isOwnedByOrb(orbUuid)) {
         this.phase = AncientSteleBlockEntity.Phase.ABSORBING;
         this.phaseStartGameTime = this.currentGameTime();
         this.syncState();
         return true;
      } else {
         return false;
      }
   }

   public void cancelOrb(UUID orbUuid) {
      if (this.phase != AncientSteleBlockEntity.Phase.SHATTERING && this.isOwnedByOrb(orbUuid)) {
         this.phase = AncientSteleBlockEntity.Phase.IDLE;
         this.activeOrbUuid = null;
         this.phaseStartGameTime = this.currentGameTime();
         this.syncState();
      }
   }

   public boolean isReservedBy(UUID orbUuid) {
      return this.phase == AncientSteleBlockEntity.Phase.RESERVED && this.isOwnedByOrb(orbUuid);
   }

   public boolean isOwnedByOrb(UUID orbUuid) {
      return this.activeOrbUuid != null && this.activeOrbUuid.equals(orbUuid);
   }

   public boolean isShattering() {
      return this.phase == AncientSteleBlockEntity.Phase.SHATTERING;
   }

   public boolean shouldRenderRunes() {
      return this.phase != AncientSteleBlockEntity.Phase.SHATTERING;
   }

   public static void serverTick(Level level, BlockPos pos, BlockState state, AncientSteleBlockEntity stele) {
      if (!level.isClientSide) {
         long phaseTicks = Math.max(0L, level.getGameTime() - stele.phaseStartGameTime);
         if (stele.phase == AncientSteleBlockEntity.Phase.ABSORBING) {
            if (phaseTicks >= 100L) {
               stele.phase = AncientSteleBlockEntity.Phase.SHATTERING;
               stele.phaseStartGameTime = level.getGameTime();
               stele.syncState();
            }
         } else {
            if (stele.phase == AncientSteleBlockEntity.Phase.SHATTERING && phaseTicks >= 30L) {
               level.removeBlock(pos, false);
            }
         }
      }
   }

   protected void saveAdditional(CompoundTag tag, Provider provider) {
      super.saveAdditional(tag, provider);
      this.writeState(tag);
   }

   protected void loadAdditional(CompoundTag tag, Provider provider) {
      super.loadAdditional(tag, provider);
      int phaseIndex = Math.max(0, Math.min(tag.getInt("Phase"), AncientSteleBlockEntity.Phase.values().length - 1));
      this.phase = AncientSteleBlockEntity.Phase.values()[phaseIndex];
      this.phaseStartGameTime = tag.getLong("PhaseStartGameTime");
      this.activeOrbUuid = tag.hasUUID("ActiveOrb") ? tag.getUUID("ActiveOrb") : null;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   @NotNull
   public CompoundTag getUpdateTag(Provider provider) {
      CompoundTag tag = new CompoundTag();
      this.writeState(tag);
      return tag;
   }

   private void writeState(CompoundTag tag) {
      tag.putInt("Phase", this.phase.ordinal());
      tag.putLong("PhaseStartGameTime", this.phaseStartGameTime);
      if (this.activeOrbUuid != null) {
         tag.putUUID("ActiveOrb", this.activeOrbUuid);
      }
   }

   private void syncState() {
      this.setChanged();
      if (this.level != null && !this.level.isClientSide) {
         BlockState state = this.getBlockState();
         this.level.sendBlockUpdated(this.worldPosition, state, state, 2);
      }
   }

   private long currentGameTime() {
      return this.level == null ? 0L : this.level.getGameTime();
   }

   private enum Phase {
      IDLE,
      RESERVED,
      ABSORBING,
      SHATTERING;
   }
}
