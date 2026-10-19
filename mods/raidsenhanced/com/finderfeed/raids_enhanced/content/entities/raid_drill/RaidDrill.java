package com.finderfeed.raids_enhanced.content.entities.raid_drill;

import com.finderfeed.fdlib.FDClientHelpers;
import com.finderfeed.fdlib.nbt.AutoSerializable;
import com.finderfeed.fdlib.nbt.SerializableField;
import com.finderfeed.fdlib.systems.bedrock.animations.Animation;
import com.finderfeed.fdlib.systems.bedrock.animations.TransitionAnimation;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationTicker;
import com.finderfeed.raids_enhanced.REClientUtil;
import com.finderfeed.raids_enhanced.REUtil;
import com.finderfeed.raids_enhanced.RaidsEnhanced;
import com.finderfeed.raids_enhanced.content.entities.FDRaider;
import com.finderfeed.raids_enhanced.init.REAnimations;
import com.finderfeed.raids_enhanced.init.REConfigs;
import com.finderfeed.raids_enhanced.init.RESounds;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable.Result;
import org.joml.Vector2i;

public class RaidDrill extends FDRaider implements AutoSerializable {
   public static final String BURROW_LAYER = "BURROWED";
   public static final EntityDataAccessor<Boolean> IS_VISIBLE = SynchedEntityData.defineId(RaidDrill.class, EntityDataSerializers.BOOLEAN);
   public static TagKey<Block> CANNOT_DIG_OUT_FROM = BlockTags.create(RaidsEnhanced.location("cannot_dig_out_from"));
   protected List<BlockState> blocksToRender = new ArrayList<>();
   @SerializableField
   public int reDigTicker = 0;
   @SerializableField
   public int hits = 0;
   @SerializableField
   public int raidersSpawningTicker = -1;
   @SerializableField
   private int raidersToSpawn = 0;
   @SerializableField
   private int idleTicker = 0;
   @SerializableField
   private int automaticReDigAmount = 0;
   private BlockPos noRaidPos;

   public RaidDrill(EntityType<? extends Raider> drill, Level level) {
      super(drill, level);
      this.setNoGravity(true);
      this.getAnimationSystem().startAnimation("IDLE", AnimationTicker.builder(REAnimations.RAIDER_DRILL_IDLE).build());
      this.getAnimationSystem().startAnimation("BURROWED", AnimationTicker.builder(REAnimations.RAIDER_DRILL_UNBURROW).build());
   }

   protected void registerGoals() {
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.noRaidPos == null) {
            this.noRaidPos = this.getOnPos().offset(1, 0, 0);
         }

         if (this.getVehicle() != null) {
            this.stopRiding();
         }

         this.tickReDig();
         this.tickRaidersSpawning();
      } else {
         if (this.tickCount % 4 == 0) {
            this.blocksToRender = REClientUtil.collectStates(this.level(), this.position().add(0.0, -0.5, 0.0), 1);
         }

         AnimationTicker animationTicker = this.getAnimationSystem().getTicker("BURROWED");
         boolean shouldPlaySound = true;
         if (animationTicker != null) {
            Animation animation = animationTicker.getAnimation();
            if (animation instanceof TransitionAnimation transitionAnimation) {
               animation = transitionAnimation.getTransitionTo();
            }

            if (animation == REAnimations.RAIDER_DRILL_BURROW.get()) {
               float time = animationTicker.getTime(1.0F);
               if (time > 0.0F) {
                  shouldPlaySound = false;
               }
            }
         }

         if (shouldPlaySound && this.tickCount % 7 == 0) {
            this.level()
               .playSound(
                  FDClientHelpers.getClientPlayer(), this.getX(), this.getY(), this.getZ(), RESounds.RAID_DRILL_IDLE.get(), SoundSource.HOSTILE, 1.0F, 1.0F
               );
         }
      }
   }

   public void tickRaidersSpawning() {
      if (this.raidersSpawningTicker != -1) {
         if (this.raidersToSpawn <= 0 || this.raidersSpawningTicker % 60 != 0) {
            this.idleTicker++;
            if (this.automaticReDigAmount < REConfigs.CONFIG.get().raidDrill.automaticBurrowTimes
               && this.idleTicker >= REConfigs.CONFIG.get().raidDrill.burrowAgainAfter) {
               this.automaticReDigAmount++;
               this.launchReDig();
            }
         } else if (this.spawnRaider()) {
            this.raidersToSpawn--;
         }

         this.raidersSpawningTicker++;
      }
   }

   private boolean spawnRaider() {
      EntityType<? extends Raider> type;
      if (this.random.nextInt(2) == 1) {
         type = EntityType.VINDICATOR;
      } else {
         type = EntityType.PILLAGER;
      }

      Entity entity = type.create(this.level());
      if (entity instanceof Raider raider) {
         if (this.raid != null) {
            this.raid.joinRaid(this.raid.getGroupsSpawned(), raider, this.getOnPos(), false);
         } else {
            entity.setPos(this.position());
            raider.finalizeSpawn((ServerLevel)this.level(), this.level().getCurrentDifficultyAt(this.getOnPos()), MobSpawnType.EVENT, null);
            this.level().addFreshEntity(raider);
         }

         return true;
      } else {
         return false;
      }
   }

   public void tickReDig() {
      if (this.reDigTicker != -1) {
         int burrowedTime = ((Animation)REAnimations.RAIDER_DRILL_BURROW.get()).getAnimTime();
         int unburrowTime = burrowedTime + 10;
         if (this.reDigTicker >= burrowedTime && this.reDigTicker <= unburrowTime) {
            this.setVisible(false);
         } else {
            this.setVisible(true);
         }

         if (this.reDigTicker == 0) {
            this.getAnimationSystem().startAnimation("BURROWED", AnimationTicker.builder(REAnimations.RAIDER_DRILL_BURROW).build());
         } else if (this.reDigTicker == burrowedTime + 5) {
            BlockPos relativePos;
            if (this.raid != null) {
               relativePos = this.raid.getCenter();
            } else {
               if (this.noRaidPos == null) {
                  this.noRaidPos = this.getOnPos().offset(1, 0, 0);
               }

               relativePos = this.noRaidPos;
            }

            BlockPos blockPos = this.calculateDigOutPos(relativePos);
            if (blockPos != null) {
               this.teleportTo(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);
               Vec3 lookAtPos;
               if (this.raid != null) {
                  lookAtPos = blockPos.getCenter();
               } else {
                  lookAtPos = this.position().add(new Vec3(1.0, 0.0, 0.0).yRot((float) (Math.PI * 2) * this.random.nextFloat()));
               }

               this.lookAt(Anchor.FEET, lookAtPos);
            }
         } else if (this.reDigTicker == burrowedTime + 10) {
            REUtil.lightningDebris((ServerLevel)this.level(), this.position(), 60.0);
            this.getAnimationSystem().startAnimation("BURROWED", AnimationTicker.builder(REAnimations.RAIDER_DRILL_UNBURROW).build());
         } else if (this.reDigTicker == burrowedTime + 12) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), RESounds.RAID_DRILL_DIG_OUT.get(), SoundSource.HOSTILE, 5.0F, 1.0F);
         } else if (this.reDigTicker >= unburrowTime + ((Animation)REAnimations.RAIDER_DRILL_UNBURROW.get()).getAnimTime() + 10) {
            this.raidersSpawningTicker = 0;
            this.idleTicker = 0;
            this.raidersToSpawn = REConfigs.CONFIG.get().raidDrill.minRaidersSpawn
               + this.random.nextInt(REConfigs.CONFIG.get().raidDrill.maxRaidersSpawn - REConfigs.CONFIG.get().raidDrill.minRaidersSpawn + 1);
            this.reDigTicker = -1;
            return;
         }

         this.reDigTicker++;
      } else {
         this.setVisible(true);
         this.getAnimationSystem().startAnimation("BURROWED", AnimationTicker.builder(REAnimations.RAIDER_DRILL_UNBURROW).build());
      }
   }

   public void checkDespawn() {
      if (!EventHooks.checkMobDespawn(this)) {
         if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
         } else {
            this.noActionTime = 0;
         }
      }
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.WOOD_BREAK;
   }

   protected void tickDeath() {
      this.deathTime++;
      if (this.deathTime >= 1 && !this.level().isClientSide() && !this.isRemoved()) {
         this.remove(RemovalReason.KILLED);
      }
   }

   public boolean hurt(DamageSource src, float damage) {
      if (src.getEntity() != null) {
         if (this.reDigTicker == -1 && super.hurt(src, 0.1F)) {
            this.idleTicker = 0;
            this.automaticReDigAmount = 0;
            REUtil.drillDeath((ServerLevel)this.level(), this.position(), 60.0, this.getId());
            if (++this.hits >= 3) {
               int newHealth = (int)this.getHealth();
               if (newHealth == 0) {
                  this.setHealth(0.0F);
                  this.die(src);
               } else {
                  this.launchReDig();
                  this.setHealth(newHealth);
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return (src.is(DamageTypes.FELL_OUT_OF_WORLD) || src.is(DamageTypes.GENERIC_KILL)) && super.hurt(src, damage);
      }
   }

   public boolean isVisible() {
      return (Boolean)this.getEntityData().get(IS_VISIBLE);
   }

   public void setVisible(boolean state) {
      this.getEntityData().set(IS_VISIBLE, state);
   }

   private void launchReDig() {
      this.raidersToSpawn = 0;
      this.idleTicker = 0;
      this.raidersSpawningTicker = -1;
      this.reDigTicker = 0;
      this.hits = 0;
   }

   private BlockPos calculateDigOutPos(BlockPos origin) {
      Vec3 pos = this.position();
      Vec3 center = origin.getCenter();
      Vec3 between = center.subtract(pos);
      List<Vector2i> candidates = new ArrayList<>();
      int maxRadius = 25;
      int minRadius = 15;

      for (int x = -maxRadius; x <= maxRadius; x++) {
         for (int z = -maxRadius; z <= maxRadius; z++) {
            if (Math.abs(x) >= minRadius || Math.abs(z) >= minRadius) {
               double dot = x * between.x + z * between.z;
               if (dot > 0.0) {
                  candidates.add(new Vector2i(x, z));
               }
            }
         }
      }

      List<BlockPos> positions = new ArrayList<>();
      Iterator<Vector2i> iterator = candidates.iterator();

      while (iterator.hasNext()) {
         Vector2i p = iterator.next();
         Vector2i v = new Vector2i(p.x + origin.getX(), p.y + origin.getZ());
         BlockPos ps = this.isValidDigOutPos(v);
         if (ps == null) {
            iterator.remove();
         } else {
            positions.add(ps);
         }
      }

      return positions.isEmpty() ? null : positions.get(this.random.nextInt(positions.size()));
   }

   public BlockPos isValidDigOutPos(Vector2i vec) {
      int height = this.level().getHeight(Types.MOTION_BLOCKING_NO_LEAVES, vec.x, vec.y);
      BlockPos blockPos = new BlockPos(vec.x, height - 1, vec.y);
      BlockState b = this.level().getBlockState(blockPos);
      FluidState bf = this.level().getFluidState(blockPos);
      return bf.isEmpty()
            && !this.isBlockNotValidForSpawn(b)
            && !this.level().getBlockState(blockPos).getCollisionShape(this.level(), blockPos).isEmpty()
            && this.level().getBlockState(blockPos.above()).getCollisionShape(this.level(), blockPos.above()).isEmpty()
            && this.level().getBlockState(blockPos.above(2)).getCollisionShape(this.level(), blockPos.above(2)).isEmpty()
         ? blockPos
         : null;
   }

   private boolean isBlockNotValidForSpawn(BlockState blockState) {
      return blockState.is(CANNOT_DIG_OUT_FROM);
   }

   protected SoundEvent getHurtSound(DamageSource p_33034_) {
      return SoundEvents.WOOD_BREAK;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(IS_VISIBLE, false);
   }

   public boolean isInvulnerableTo(DamageSource src) {
      return super.isInvulnerableTo(src) || src.is(DamageTypes.IN_WALL);
   }

   public void applyRaidBuffs(ServerLevel p_348605_, int p_37844_, boolean p_37845_) {
   }

   protected boolean canRide(Entity p_20339_) {
      return false;
   }

   public SoundEvent getCelebrateSound() {
      return null;
   }

   protected double getDefaultGravity() {
      return 0.0;
   }

   public void setDeltaMovement(Vec3 p_20257_) {
   }

   public void setDeltaMovement(double p_20335_, double p_20336_, double p_20337_) {
   }

   @Override
   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      this.autoSave(tag);
      if (this.noRaidPos != null) {
         tag.putInt("noRaidPosX", this.noRaidPos.getX());
         tag.putInt("noRaidPosY", this.noRaidPos.getY());
         tag.putInt("noRaidPosZ", this.noRaidPos.getZ());
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.autoLoad(tag);
      if (tag.contains("noRaidPosX")) {
         this.noRaidPos = new BlockPos(tag.getInt("noRaidPosX"), tag.getInt("noRaidPosY"), tag.getInt("noRaidPosZ"));
      }
   }

   @EventBusSubscriber
   public static class Events {
      @SubscribeEvent
      public static void effectEvent(Applicable event) {
         if (event.getEntity() instanceof RaidDrill raidDrill && event.getEffectInstance() != null && !event.getEffectInstance().is(MobEffects.GLOWING)) {
            event.setResult(Result.DO_NOT_APPLY);
         }
      }

      @SubscribeEvent
      public static void targetEvent(LivingChangeTargetEvent event) {
         if (event.getNewAboutToBeSetTarget() instanceof RaidDrill) {
            event.setCanceled(true);
         }
      }
   }
}
