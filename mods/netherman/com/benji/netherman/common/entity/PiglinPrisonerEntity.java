package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PiglinPrisonerEntity extends PathfinderMob implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(PiglinPrisonerEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> MASTER_ID = SynchedEntityData.defineId(PiglinPrisonerEntity.class, EntityDataSerializers.INT);
   public static final int STATE_IDLE_PRISON = 0;
   public static final int STATE_MINING = 1;
   public static final int STATE_FREEDOM = 2;
   public static final int STATE_IDLE = 3;
   public static final int STATE_GIVE = 4;
   public static final int STATE_TRANSFORM = 5;
   private int actionTimer = 0;
   private int miningSoundTimer = 0;

   public PiglinPrisonerEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.2).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(STATE, 0);
      builder.define(MASTER_ID, -1);
   }

   public int getEntityState() {
      return (Integer)this.entityData.get(STATE);
   }

   public void setEntityState(int state) {
      this.entityData.set(STATE, state);
   }

   public int getMasterId() {
      return (Integer)this.entityData.get(MASTER_ID);
   }

   public void setMasterId(int id) {
      this.entityData.set(MASTER_ID, id);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.PIGLIN_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.PIGLIN_DEATH;
   }

   public void tick() {
      super.tick();
      int state = this.getEntityState();
      Entity master = this.level().getEntity(this.getMasterId());
      if (this.level().isClientSide()) {
         if ((state == 0 || state == 1) && master != null && this.tickCount % 40 == 0) {
            Vec3 start = this.position().add(0.0, 1.0, 0.0);
            Vec3 end = master.position().add(0.0, 1.0, 0.0);
            double distance = start.distanceTo(end);
            Vec3 dir = end.subtract(start).normalize();

            for (double d = 0.0; d < distance; d++) {
               Vec3 pos = start.add(dir.scale(d));
               this.level().addParticle(DustParticleOptions.REDSTONE, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            }
         }
      } else {
         this.getNavigation().stop();
         if (state != 0 && state != 1) {
            if (state == 2) {
               this.getNavigation().stop();
               if (this.actionTimer > 0) {
                  this.actionTimer--;
               } else {
                  this.setEntityState(3);
               }
            } else if (state == 3) {
               this.getNavigation().stop();
               Player nearestPlayer = this.level().getNearestPlayer(this, 3.0);
               if (nearestPlayer != null) {
                  this.getLookControl().setLookAt(nearestPlayer);
                  this.setEntityState(4);
                  this.actionTimer = 10;
               }
            } else if (state == 4) {
               this.getNavigation().stop();
               if (this.actionTimer > 0) {
                  this.actionTimer--;
               } else {
                  int rand = this.random.nextInt(100);
                  ItemStack reward = new ItemStack(rand < 60 ? Items.GOLD_INGOT : (rand < 90 ? Items.ENDER_PEARL : Items.NETHERITE_INGOT));
                  this.spawnAtLocation(reward);
                  this.playSound(SoundEvents.PIGLIN_CELEBRATE, 1.0F, this.getVoicePitch());
                  this.setEntityState(5);
                  this.actionTimer = 15;
               }
            } else if (state == 5) {
               this.getNavigation().stop();
               if (this.actionTimer > 0) {
                  this.actionTimer--;
               } else {
                  Piglin piglin = (Piglin)EntityType.PIGLIN.create(this.level());
                  if (piglin != null) {
                     piglin.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                     this.level().addFreshEntity(piglin);
                     if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 20, 0.3, 0.5, 0.3, 0.05);
                     }
                  }

                  this.discard();
               }
            }
         } else {
            if (master == null || !master.isAlive()) {
               this.getNavigation().stop();
               this.setEntityState(2);
               this.actionTimer = 20;
               return;
            }

            BlockPos myPos = this.blockPosition();
            BlockPos targetMiningPos = null;

            label146:
            for (int x = -1; x <= 1; x++) {
               for (int z = -1; z <= 1; z++) {
                  for (int y = 0; y <= 1; y++) {
                     if (x != 0 || z != 0 || y != 0) {
                        BlockPos checkPos = myPos.offset(x, y, z);
                        Block block = this.level().getBlockState(checkPos).getBlock();
                        if (block == Blocks.STONE
                           || block == Blocks.BLACKSTONE
                           || block == Blocks.NETHER_GOLD_ORE
                           || block == Blocks.NETHER_QUARTZ_ORE
                           || block == Blocks.GILDED_BLACKSTONE) {
                           targetMiningPos = checkPos;
                           break label146;
                        }
                     }
                  }
               }
            }

            if (targetMiningPos != null) {
               if (state != 1) {
                  this.setEntityState(1);
               }

               this.getNavigation().stop();
               double dx = targetMiningPos.getX() + 0.5 - this.getX();
               double dz = targetMiningPos.getZ() + 0.5 - this.getZ();
               float targetRot = (float)(Math.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
               this.setYRot(targetRot);
               this.yHeadRot = targetRot;
               this.yBodyRot = targetRot;
               if (this.miningSoundTimer <= 0) {
                  int randSound = this.random.nextInt(4);
                  switch (randSound) {
                     case 0:
                        this.playSound((SoundEvent)ModSounds.PRISON_1.get(), 1.0F, this.getVoicePitch());
                        this.miningSoundTimer = 17;
                        break;
                     case 1:
                        this.playSound((SoundEvent)ModSounds.PRISON_2.get(), 1.0F, this.getVoicePitch());
                        this.miningSoundTimer = 38;
                        break;
                     case 2:
                        this.playSound((SoundEvent)ModSounds.PRISON_3.get(), 1.0F, this.getVoicePitch());
                        this.miningSoundTimer = 14;
                        break;
                     case 3:
                        this.playSound((SoundEvent)ModSounds.PRISON_4.get(), 1.0F, this.getVoicePitch());
                        this.miningSoundTimer = 19;
                  }
               } else {
                  this.miningSoundTimer--;
               }
            } else {
               if (state != 0) {
                  this.setEntityState(0);
               }

               this.miningSoundTimer = 0;
               if (this.distanceToSqr(master) > 25.0) {
                  this.getNavigation().moveTo(master, 1.0);
               } else {
                  this.getNavigation().stop();
               }
            }
         }
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 5, event -> {
         return switch (this.getEntityState()) {
            case 1 -> event.setAndContinue(RawAnimation.begin().thenLoop("mining"));
            case 2 -> event.setAndContinue(RawAnimation.begin().thenPlay("freedom"));
            case 3 -> event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            case 4 -> event.setAndContinue(RawAnimation.begin().thenPlay("give"));
            case 5 -> event.setAndContinue(RawAnimation.begin().thenPlay("transform"));
            default -> event.setAndContinue(RawAnimation.begin().thenLoop("idle_prison"));
         };
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("MasterId", this.getMasterId());
      tag.putInt("PrisonState", this.getEntityState());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setMasterId(tag.getInt("MasterId"));
      this.setEntityState(tag.getInt("PrisonState"));
   }
}
