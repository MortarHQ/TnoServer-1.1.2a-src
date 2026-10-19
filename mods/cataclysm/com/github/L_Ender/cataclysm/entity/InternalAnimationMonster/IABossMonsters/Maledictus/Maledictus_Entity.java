package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus;

import com.github.L_Ender.cataclysm.blocks.Cursed_Tombstone_Block;
import com.github.L_Ender.cataclysm.client.particle.Options.AfterImageParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.RingParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.RoarParticleOptions;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AI.EntityAINearestTarget3D;
import com.github.L_Ender.cataclysm.entity.AI.HurtByNearestTargetGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.IABoss_monster;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.IHoldEntity;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.message.MessageEntityCameraSwitch;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.network.PacketDistributor;

public class Maledictus_Entity extends IABoss_monster implements IHoldEntity {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState swingAnimationState = new AnimationState();
   public AnimationState shotAnimationState = new AnimationState();
   public AnimationState flyingshotAnimationState = new AnimationState();
   public AnimationState fallloopAnimationState = new AnimationState();
   public AnimationState falllendAnimationState = new AnimationState();
   public AnimationState masseffectAnimationState = new AnimationState();
   public AnimationState flyingsmash1AnimationState = new AnimationState();
   public AnimationState flyingsmash2AnimationState = new AnimationState();
   public AnimationState BackstepAnimationState = new AnimationState();
   public AnimationState BackstepRushAnimationState = new AnimationState();
   public AnimationState BackstepRushNobackstepAnimationState = new AnimationState();
   public AnimationState dash1AnimationState = new AnimationState();
   public AnimationState dash1NobackstepAnmationState = new AnimationState();
   public AnimationState dash2AnmationState = new AnimationState();
   public AnimationState dash2NobackstepAnmationState = new AnimationState();
   public AnimationState dash3AnimationState = new AnimationState();
   public AnimationState spinslashesAnimationState = new AnimationState();
   public AnimationState combofirstAnimationState = new AnimationState();
   public AnimationState combofirstendAnimationState = new AnimationState();
   public AnimationState combosecondAnimationState = new AnimationState();
   public AnimationState uppercutleftAnimationState = new AnimationState();
   public AnimationState uppercutrightAnimationState = new AnimationState();
   public AnimationState flyinghalberdsmash1AnimationState = new AnimationState();
   public AnimationState flyinghalberdsmash2AnimationState = new AnimationState();
   public AnimationState radagonAnimationState = new AnimationState();
   public AnimationState halberdswingAnimationState = new AnimationState();
   public AnimationState grab_startAnimationState = new AnimationState();
   public AnimationState grab_loopAnimationState = new AnimationState();
   public AnimationState grab_failAnimationState = new AnimationState();
   public AnimationState grab_successAnimationState = new AnimationState();
   public AnimationState grab_success_loopAnimationState = new AnimationState();
   public AnimationState grab_success_endAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   private boolean combo;
   private boolean grab;
   private int rageTicks;
   private int masseffect_cooldown = 0;
   private int flyattack_cooldown = 0;
   private int charge_cooldown = 0;
   private int uppercut_cooldown = 0;
   private int spin_cooldown = 0;
   private int radagon_cooldown = 0;
   private int spear_swing_cooldown = 0;
   private int grab_cooldown = 0;
   public static final int MASSEFFECT_COOLDOWN = 150;
   public static final int FLYATTACK_COOLDOWN = 100;
   public static final int CHARGE_COOLDOWN = 80;
   public static final int UPPERCUT_COOLDOWN = 80;
   public static final int SPIN_COOLDOWN = 100;
   public static final int RADAGON_COOLDOWN = 250;
   public static final int SPEAR_SWING_COOLDOWN = 100;
   public static final int GRAB_COOLDOWN = 300;
   private int destroyBlocksTick;
   private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(Maledictus_Entity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Integer> RAGE = SynchedEntityData.defineId(Maledictus_Entity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> WEAPON = SynchedEntityData.defineId(Maledictus_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Direction> TOMBSTONE_DIRECTION = SynchedEntityData.defineId(Maledictus_Entity.class, EntityDataSerializers.DIRECTION);
   private final CMBossInfoServer bossEvent1 = new CMBossInfoServer(this.getDisplayName(), BossBarColor.GREEN, true, 9);
   private final CMBossInfoServer bossEvent2 = new CMBossInfoServer(Component.translatable("entity.cataclysm.rage_meter"), BossBarColor.GREEN, false, 10);

   public Maledictus_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 500;
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setConfigattribute(this, CMCommonConfig.Maledictus.healthMultiplier, CMCommonConfig.Maledictus.attackMultiplier);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByNearestTargetGoal(this));
      this.targetSelector.addGoal(2, new EntityAINearestTarget3D(this, Player.class, true));
      this.goalSelector.addGoal(3, new InternalMoveGoal(this, false, 1.0));
      this.goalSelector.addGoal(1, new Maledictus_Entity.MaledictusSpinSlashes(this, 0, 18, 0, 68, 15, 23, 29, 15, 29, 6.5F, 0, 0, 24.0F));
      this.goalSelector.addGoal(1, new InternalAttackGoal(this, 0, 27, 0, 50, 22, 3.5F) {
         @Override
         public boolean canUse() {
            return super.canUse() && Maledictus_Entity.this.getRandom().nextFloat() * 100.0F < 32.0F && Maledictus_Entity.this.spear_swing_cooldown <= 0;
         }

         @Override
         public void start() {
            super.start();
            Maledictus_Entity.this.setWeapon(2);
         }

         @Override
         public void stop() {
            super.stop();
            Maledictus_Entity.this.spear_swing_cooldown = 100;
            Maledictus_Entity.this.setWeapon(0);
         }
      });
      this.goalSelector.addGoal(1, new Maledictus_Entity.MaledictusGrabGoal(this, 0, 28, 29, 26, 24, 9.0F, 3, 3, 25.0F));
      this.goalSelector.addGoal(1, new Maledictus_Entity.MaledictusGrabState(this, 29, 29, 30, 15, 0, 3, 3));
      this.goalSelector.addGoal(0, new InternalStateGoal(this, 30, 30, 0, 30, 0) {
         @Override
         public void stop() {
            super.stop();
            Maledictus_Entity.this.setWeapon(0);
         }
      });
      this.goalSelector.addGoal(0, new Maledictus_Entity.MaledictusSuccessState(this, 31, 31, 32, 60, 0, 30, 3, 3, 2.0));
      this.goalSelector.addGoal(1, new Maledictus_Entity.MaledictusfallingState(this, 32, 32, 33, 100, 0, 3, 3));
      this.goalSelector.addGoal(0, new Maledictus_Entity.MaledictusfallingState(this, 33, 33, 0, 35, 0, 3, 0));
      this.goalSelector
         .addGoal(
            1,
            new InternalAttackGoal(this, 0, 19, 20, 27, 10, 6.0F) {
               @Override
               public boolean canUse() {
                  LivingEntity target = this.entity.getTarget();
                  return super.canUse()
                     && Maledictus_Entity.this.getRandom().nextFloat() * 100.0F < 24.0F
                     && target != null
                     && this.entity.distanceTo(target) >= 1.75;
               }

               @Override
               public boolean canContinueToUse() {
                  return super.canContinueToUse() && !Maledictus_Entity.this.combo;
               }

               @Override
               public void tick() {
                  super.tick();
                  if (this.entity.attackTicks == 8) {
                     float f1 = (float)Math.cos(Math.toRadians(this.entity.getYRot() + 90.0F));
                     float f2 = (float)Math.sin(Math.toRadians(this.entity.getYRot() + 90.0F));
                     this.entity.push(f1 * 1.35, 0.0, f2 * 1.35);
                  }
               }

               @Override
               public void stop() {
                  if (Maledictus_Entity.this.combo) {
                     this.entity.setAttackState(21);
                     Maledictus_Entity.this.combo = false;
                  } else {
                     super.stop();
                  }
               }
            }
         );
      this.goalSelector.addGoal(1, new Maledictus_Entity.Uppercut(this, 0, 22, 0, 60, 16, 3.0F, 32.0F));
      this.goalSelector.addGoal(1, new Maledictus_Entity.Uppercut(this, 0, 23, 0, 60, 16, 3.0F, 32.0F));
      this.goalSelector.addGoal(0, new InternalStateGoal(this, 20, 20, 0, 13, 0));
      this.goalSelector.addGoal(0, new InternalStateGoal(this, 21, 21, 0, 45, 8) {
         @Override
         public void tick() {
            super.tick();
            if (this.entity.attackTicks == 8) {
               float f1 = (float)Math.cos(Math.toRadians(this.entity.getYRot() + 90.0F));
               float f2 = (float)Math.sin(Math.toRadians(this.entity.getYRot() + 90.0F));
               this.entity.push(f1 * 1.5, 0.0, f2 * 1.5);
            }
         }
      });
      this.goalSelector.addGoal(1, new Maledictus_Entity.Maledictus_Swing(this, 0, 1, 0, 44, 25, 6.5F, 35.0F, 20.0F));
      this.goalSelector.addGoal(1, new Maledictus_Entity.Maledictus_Bow(this, 0, 2, 0, 45, 29, 8.0F, 35.0F, 29, 16.0F));
      this.goalSelector.addGoal(1, new Maledictus_Entity.Maledictus_Flying_Bow(this, 0, 3, 4, 68, 50, 40.0F, 50, 35.0F));
      this.goalSelector.addGoal(1, new Maledictus_Entity.MaledictusfallingState(this, 4, 4, 5, 100, 100, 1, 0));
      this.goalSelector.addGoal(0, new Maledictus_Entity.MaledictusfallingState(this, 5, 5, 0, 27, 0, 0, 0));
      this.goalSelector
         .addGoal(
            1,
            new InternalAttackGoal(this, 0, 7, 0, 66, 28, 4.5F) {
               @Override
               public boolean canUse() {
                  return super.canUse()
                     && Maledictus_Entity.this.getRandom().nextFloat() * 100.0F < 7.0F * Maledictus_Entity.this.getRageMeter()
                     && Maledictus_Entity.this.masseffect_cooldown <= 0;
               }

               @Override
               public void tick() {
                  super.tick();
                  this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
               }

               @Override
               public void stop() {
                  super.stop();
                  Maledictus_Entity.this.masseffect_cooldown = 150;
               }
            }
         );
      this.goalSelector.addGoal(1, new Maledictus_Entity.Maledictus_Flying_Smash(this, 0, 8, 9, 100, 100, 30.0F, 56, 0, 0, 17.0F, 0.125));
      this.goalSelector.addGoal(0, new Maledictus_Entity.MaledictusfallingState(this, 9, 9, 0, 27, 0, 0, 0));
      this.goalSelector.addGoal(1, new Maledictus_Entity.Maledictus_Flying_Smash(this, 0, 24, 25, 100, 100, 30.0F, 51, 2, 2, 17.0F, 0.145));
      this.goalSelector.addGoal(0, new Maledictus_Entity.MaledictusfallingState(this, 25, 25, 0, 75, 0, 2, 0));
      this.goalSelector
         .addGoal(
            1,
            new InternalAttackGoal(this, 0, 26, 0, 73, 43, 13.0F) {
               @Override
               public boolean canUse() {
                  if (Maledictus_Entity.this.isQuarterHealth()) {
                     return super.canUse() && Maledictus_Entity.this.getRandom().nextFloat() * 100.0F < 35.0F && Maledictus_Entity.this.radagon_cooldown <= 0;
                  } else {
                     return !Maledictus_Entity.this.isHalfHealth()
                        ? false
                        : super.canUse() && Maledictus_Entity.this.getRandom().nextFloat() * 100.0F < 25.0F && Maledictus_Entity.this.radagon_cooldown <= 0;
                  }
               }

               @Override
               public void start() {
                  super.start();
                  Maledictus_Entity.this.setWeapon(2);
               }

               @Override
               public void stop() {
                  super.stop();
                  Maledictus_Entity.this.radagon_cooldown = 250;
                  Maledictus_Entity.this.setWeapon(0);
               }
            }
         );
      this.goalSelector.addGoal(1, new InternalAttackGoal(this, 0, 10, 0, 15, 15, 4.5F) {
         @Override
         public boolean canUse() {
            return super.canUse() && Maledictus_Entity.this.getRandom().nextFloat() * 100.0F < 18.0F && Maledictus_Entity.this.charge_cooldown <= 0;
         }

         @Override
         public void start() {
            super.start();
            float speed = -1.7F;
            float dodgeYaw = (float)Math.toRadians(Maledictus_Entity.this.getYRot() + 90.0F);
            Vec3 m = Maledictus_Entity.this.getDeltaMovement().add(speed * Math.cos(dodgeYaw), 0.0, speed * Math.sin(dodgeYaw));
            Maledictus_Entity.this.playSound((SoundEvent)ModSounds.MALEDICTUS_JUMP.get(), 1.0F, 1.0F);
            Maledictus_Entity.this.setDeltaMovement(m.x, 0.4, m.z);
            Maledictus_Entity.this.hasImpulse = true;
         }

         @Override
         public void stop() {
            if (Maledictus_Entity.this.isHalfHealth()) {
               this.entity.setAttackState(12);
            } else {
               this.entity.setAttackState(11);
            }
         }
      });
      this.goalSelector.addGoal(0, new Maledictus_Entity.MaledictusChargeState(this, 11, 11, 0, 65, 18, 31, 24, 33, 2, 0, 0));
      this.goalSelector.addGoal(0, new Maledictus_Entity.MaledictusChargeState(this, 12, 12, 0, 55, 18, 31, 24, 0, 2, 0, 1));
      this.goalSelector.addGoal(1, new Maledictus_Entity.MaledictusChargeGoal(this, 0, 19, 30, 24, 4.5F, 13.0F, 2, 0, 18.0F));
      this.goalSelector.addGoal(0, new Maledictus_Entity.MaledictusChargeState(this, 15, 15, 0, 55, 10, 25, 16, 27, 2, 0, 2));
      this.goalSelector.addGoal(0, new Maledictus_Entity.MaledictusChargeState(this, 16, 16, 0, 40, 10, 25, 16, 0, 2, 0, 2));
      this.goalSelector.addGoal(0, new Maledictus_Entity.MaledictusChargeState(this, 17, 17, 0, 58, 10, 28, 16, 30, 2, 0, 3));
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public static Builder maledictus() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 50.0)
         .add(Attributes.MOVEMENT_SPEED, 0.33F)
         .add(Attributes.ATTACK_DAMAGE, 13.0)
         .add(Attributes.MAX_HEALTH, 420.0)
         .add(Attributes.ARMOR, 10.0)
         .add(Attributes.STEP_HEIGHT, 1.25)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   public ItemEntity spawnAtLocation(ItemStack stack) {
      ItemEntity itementity = this.spawnAtLocation(stack, 0.0F);
      if (itementity != null) {
         itementity.setGlowingTag(true);
         itementity.setExtendedLifetime();
      }

      return itementity;
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if ((this.getAttackState() == 31 || this.getAttackState() == 32 || this.getAttackState() == 33) && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      }

      if (this.destroyBlocksTick <= 0) {
         this.destroyBlocksTick = 20;
      }

      return super.hurt(source, damage);
   }

   @Override
   public float DamageCap() {
      return (float)CMCommonConfig.Maledictus.damageCap;
   }

   @Override
   public float NatureRegen() {
      return (float)CMCommonConfig.Maledictus.natureHeal;
   }

   @Override
   public float DpsCap() {
      return (float)CMCommonConfig.Maledictus.dpsCap;
   }

   public int DpsMulti() {
      return CMCommonConfig.Maledictus.dpsLimitTime;
   }

   @Override
   public double RangeLimit() {
      return CMCommonConfig.Maledictus.rangeCap;
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
      return false;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "swing") {
         return this.swingAnimationState;
      } else if (input == "shoot") {
         return this.shotAnimationState;
      } else if (input == "death") {
         return this.deathAnimationState;
      } else if (input == "flying_shoot") {
         return this.flyingshotAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "fall_loop") {
         return this.fallloopAnimationState;
      } else if (input == "fall_end") {
         return this.falllendAnimationState;
      } else if (input == "mass_effect") {
         return this.masseffectAnimationState;
      } else if (input == "flying_smash_1") {
         return this.flyingsmash1AnimationState;
      } else if (input == "flying_smash_2") {
         return this.flyingsmash2AnimationState;
      } else if (input == "back_step") {
         return this.BackstepAnimationState;
      } else if (input == "back_step_dash") {
         return this.BackstepRushAnimationState;
      } else if (input == "back_step_dash_no_back_step") {
         return this.BackstepRushNobackstepAnimationState;
      } else if (input == "dash") {
         return this.dash1AnimationState;
      } else if (input == "dash_no_back_step") {
         return this.dash1NobackstepAnmationState;
      } else if (input == "dash2") {
         return this.dash2AnmationState;
      } else if (input == "dash2_no_back_step") {
         return this.dash2NobackstepAnmationState;
      } else if (input == "dash3") {
         return this.dash3AnimationState;
      } else if (input == "spin_slashes") {
         return this.spinslashesAnimationState;
      } else if (input == "combo_first") {
         return this.combofirstAnimationState;
      } else if (input == "combo_first_end") {
         return this.combofirstendAnimationState;
      } else if (input == "combo_second") {
         return this.combosecondAnimationState;
      } else if (input == "uppercut_right") {
         return this.uppercutrightAnimationState;
      } else if (input == "uppercut_left") {
         return this.uppercutleftAnimationState;
      } else if (input == "flying_halberd_smash_1") {
         return this.flyinghalberdsmash1AnimationState;
      } else if (input == "flying_halberd_smash_2") {
         return this.flyinghalberdsmash2AnimationState;
      } else if (input == "radagon") {
         return this.radagonAnimationState;
      } else if (input == "halberd_swing") {
         return this.halberdswingAnimationState;
      } else if (input == "grab_start") {
         return this.grab_startAnimationState;
      } else if (input == "grab_loop") {
         return this.grab_loopAnimationState;
      } else if (input == "grab_fail") {
         return this.grab_failAnimationState;
      } else if (input == "grab_success") {
         return this.grab_successAnimationState;
      } else if (input == "grab_success_loop") {
         return this.grab_success_loopAnimationState;
      } else {
         return input == "grab_success_end" ? this.grab_success_endAnimationState : new AnimationState();
      }
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(TOMBSTONE_DIRECTION, Direction.NORTH);
      p_326229_.define(WEAPON, 0);
      p_326229_.define(FLYING, false);
      p_326229_.define(RAGE, 0);
   }

   public int getWeapon() {
      return (Integer)this.entityData.get(WEAPON);
   }

   public void setWeapon(int weapon) {
      this.entityData.set(WEAPON, weapon);
   }

   public boolean isFlying() {
      return (Boolean)this.entityData.get(FLYING);
   }

   public void setFlying(boolean flying) {
      this.entityData.set(FLYING, flying);
   }

   public Direction getTombstoneDirection() {
      return (Direction)this.entityData.get(TOMBSTONE_DIRECTION);
   }

   public void setTombstoneDirection(Direction p_30220_) {
      this.entityData.set(TOMBSTONE_DIRECTION, p_30220_);
   }

   public int getRageMeter() {
      return (Integer)this.entityData.get(RAGE);
   }

   public void setRageMeter(int Rage) {
      this.entityData.set(RAGE, Rage);
   }

   public void travel(Vec3 travelVector) {
      super.travel(travelVector);
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return null;
   }

   public boolean canRiderInteract() {
      return true;
   }

   public void positionRider(Entity passenger, MoveFunction moveFunc) {
      float f1 = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
      float f2 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      Vec3 vec3 = this.getPassengerRidingPosition(passenger);
      Vec3 vec31 = passenger.getVehicleAttachmentPoint(this);
      double px = vec3.x - vec31.x + 0.5 * vecX + f1 * -0.6;
      double pz = vec3.z - vec31.z + 0.5 * vecZ + f2 * -0.6;
      double y = this.getY() + 2.0;
      if (this.hasPassenger(passenger) && this.getAttackState() == 33) {
         y = this.getY() - 0.2F * Mth.clamp(0, 0, 23);
         if (this.attackTicks == 23) {
            if (!this.level().isClientSide) {
               PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                  passenger, new MessageEntityCameraSwitch.FirstPerson(passenger.getId()), new CustomPacketPayload[0]
               );
            }

            passenger.stopRiding();
         }
      }

      moveFunc.accept(passenger, px, y, pz);
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_)) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.swingAnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.shotAnimationState.startIfStopped(this.tickCount);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.flyingshotAnimationState.startIfStopped(this.tickCount);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.fallloopAnimationState.startIfStopped(this.tickCount);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.falllendAnimationState.startIfStopped(this.tickCount);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.deathAnimationState.startIfStopped(this.tickCount);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.masseffectAnimationState.startIfStopped(this.tickCount);
               break;
            case 8:
               this.stopAllAnimationStates();
               this.flyingsmash1AnimationState.startIfStopped(this.tickCount);
               break;
            case 9:
               this.stopAllAnimationStates();
               this.flyingsmash2AnimationState.startIfStopped(this.tickCount);
               break;
            case 10:
               this.stopAllAnimationStates();
               this.BackstepAnimationState.startIfStopped(this.tickCount);
               break;
            case 11:
               this.stopAllAnimationStates();
               this.BackstepRushAnimationState.startIfStopped(this.tickCount);
               break;
            case 12:
               this.stopAllAnimationStates();
               this.BackstepRushNobackstepAnimationState.startIfStopped(this.tickCount);
               break;
            case 13:
               this.stopAllAnimationStates();
               this.dash1AnimationState.startIfStopped(this.tickCount);
               break;
            case 14:
               this.stopAllAnimationStates();
               this.dash1NobackstepAnmationState.startIfStopped(this.tickCount);
               break;
            case 15:
               this.stopAllAnimationStates();
               this.dash2AnmationState.startIfStopped(this.tickCount);
               break;
            case 16:
               this.stopAllAnimationStates();
               this.dash2NobackstepAnmationState.startIfStopped(this.tickCount);
               break;
            case 17:
               this.stopAllAnimationStates();
               this.dash3AnimationState.startIfStopped(this.tickCount);
               break;
            case 18:
               this.stopAllAnimationStates();
               this.spinslashesAnimationState.startIfStopped(this.tickCount);
               break;
            case 19:
               this.stopAllAnimationStates();
               this.combofirstAnimationState.startIfStopped(this.tickCount);
               break;
            case 20:
               this.stopAllAnimationStates();
               this.combofirstendAnimationState.startIfStopped(this.tickCount);
               break;
            case 21:
               this.stopAllAnimationStates();
               this.combosecondAnimationState.startIfStopped(this.tickCount);
               break;
            case 22:
               this.stopAllAnimationStates();
               this.uppercutrightAnimationState.startIfStopped(this.tickCount);
               break;
            case 23:
               this.stopAllAnimationStates();
               this.uppercutleftAnimationState.startIfStopped(this.tickCount);
               break;
            case 24:
               this.stopAllAnimationStates();
               this.flyinghalberdsmash1AnimationState.startIfStopped(this.tickCount);
               break;
            case 25:
               this.stopAllAnimationStates();
               this.flyinghalberdsmash2AnimationState.startIfStopped(this.tickCount);
               break;
            case 26:
               this.stopAllAnimationStates();
               this.radagonAnimationState.startIfStopped(this.tickCount);
               break;
            case 27:
               this.stopAllAnimationStates();
               this.halberdswingAnimationState.startIfStopped(this.tickCount);
               break;
            case 28:
               this.stopAllAnimationStates();
               this.grab_startAnimationState.startIfStopped(this.tickCount);
               break;
            case 29:
               this.stopAllAnimationStates();
               this.grab_loopAnimationState.startIfStopped(this.tickCount);
               break;
            case 30:
               this.stopAllAnimationStates();
               this.grab_failAnimationState.startIfStopped(this.tickCount);
               break;
            case 31:
               this.stopAllAnimationStates();
               this.grab_successAnimationState.startIfStopped(this.tickCount);
               break;
            case 32:
               this.stopAllAnimationStates();
               this.grab_success_loopAnimationState.startIfStopped(this.tickCount);
               break;
            case 33:
               this.stopAllAnimationStates();
               this.grab_success_endAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.swingAnimationState.stop();
      this.shotAnimationState.stop();
      this.deathAnimationState.stop();
      this.flyingshotAnimationState.stop();
      this.fallloopAnimationState.stop();
      this.falllendAnimationState.stop();
      this.masseffectAnimationState.stop();
      this.flyingsmash1AnimationState.stop();
      this.flyingsmash2AnimationState.stop();
      this.BackstepAnimationState.stop();
      this.BackstepRushAnimationState.stop();
      this.BackstepRushNobackstepAnimationState.stop();
      this.dash1AnimationState.stop();
      this.dash1NobackstepAnmationState.stop();
      this.dash2AnmationState.stop();
      this.dash2NobackstepAnmationState.stop();
      this.dash3AnimationState.stop();
      this.spinslashesAnimationState.stop();
      this.combofirstAnimationState.stop();
      this.combofirstendAnimationState.stop();
      this.combosecondAnimationState.stop();
      this.uppercutrightAnimationState.stop();
      this.uppercutleftAnimationState.stop();
      this.flyinghalberdsmash1AnimationState.stop();
      this.flyinghalberdsmash2AnimationState.stop();
      this.radagonAnimationState.stop();
      this.halberdswingAnimationState.stop();
      this.grab_startAnimationState.stop();
      this.grab_loopAnimationState.stop();
      this.grab_failAnimationState.stop();
      this.grab_successAnimationState.stop();
      this.grab_success_loopAnimationState.stop();
      this.grab_success_endAnimationState.stop();
   }

   @Override
   public void die(DamageSource p_21014_) {
      super.die(p_21014_);
      this.setAttackState(6);
      this.setFlying(false);
   }

   @Override
   public int deathtimer() {
      return 60;
   }

   @Override
   protected void AfterDefeatBoss(@Nullable LivingEntity living) {
      if (!this.level().isClientSide && this.getHomePos() != null && this.level() instanceof ServerLevel serverLevel) {
         MinecraftServer server = serverLevel.getServer();
         ServerLevel targetLevel = server.getLevel(this.getHomePos().dimension());
         if (targetLevel != null) {
            BlockPos targetPos = this.getHomePos().pos();
            BlockState blockState = ((Block)ModBlocks.CURSED_TOMBSTONE.get()).defaultBlockState();
            if (this.getTombstoneDirection() != Direction.UP && this.getTombstoneDirection() != Direction.DOWN) {
               this.level().setBlockAndUpdate(targetPos, (BlockState)blockState.setValue(Cursed_Tombstone_Block.FACING, this.getTombstoneDirection()));
            } else {
               this.level().setBlockAndUpdate(targetPos, (BlockState)blockState.setValue(Cursed_Tombstone_Block.FACING, Direction.NORTH));
            }
         }
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("RageMeter", this.getRageMeter());
      compound.putByte("Tombstone_Direction", (byte)this.getTombstoneDirection().get3DDataValue());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setTombstoneDirection(Direction.from3DDataValue(compound.getByte("Tombstone_Direction")));
      this.setRageMeter(compound.getInt("RageMeter"));
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_30153_, DifficultyInstance p_30154_, MobSpawnType p_30155_, @Nullable SpawnGroupData p_30156_) {
      this.setTombstoneDirection(Direction.SOUTH);
      return super.finalizeSpawn(p_30153_, p_30154_, p_30155_, p_30156_);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.getPassengers().isEmpty()
         && ((Entity)this.getPassengers().get(0)).isShiftKeyDown()
         && (this.getAttackState() == 31 || this.getAttackState() == 32 || this.getAttackState() == 33)) {
         ((Entity)this.getPassengers().get(0)).setShiftKeyDown(false);
      }

      if (this.level().isClientSide()) {
         this.idleAnimationState.animateWhen(!this.walkAnimation.isMoving() && this.getAttackState() == 0, this.tickCount);
      } else {
         if (this.rageTicks > 0) {
            this.rageTicks--;
         } else if (this.getRageMeter() > 0) {
            this.setRageMeter(this.getRageMeter() - 1);
            this.rageTicks = 200;
         }

         this.bossEvent1.setLife(this.getLife());
      }

      this.bossEvent1.setProgress(this.getHealth() / this.getMaxHealth());
      this.bossEvent2.setProgress(this.getRageMeter() / 5.0F);
      if (this.masseffect_cooldown > 0) {
         this.masseffect_cooldown--;
      }

      if (this.flyattack_cooldown > 0) {
         this.flyattack_cooldown--;
      }

      if (this.charge_cooldown > 0) {
         this.charge_cooldown--;
      }

      if (this.uppercut_cooldown > 0) {
         this.uppercut_cooldown--;
      }

      if (this.spin_cooldown > 0) {
         this.spin_cooldown--;
      }

      if (this.radagon_cooldown > 0) {
         this.radagon_cooldown--;
      }

      if (this.spear_swing_cooldown > 0) {
         this.spear_swing_cooldown--;
      }

      if (this.grab_cooldown > 0) {
         this.grab_cooldown--;
      }

      if (this.isFlying()) {
         this.setNoGravity(!this.onGround());
      } else {
         this.setNoGravity(false);
      }

      LivingEntity target = this.getTarget();
      this.blockbreak();
   }

   public boolean isHalfHealth() {
      float healthAmount = this.getHealth() / this.getMaxHealth();
      return healthAmount <= 0.5F;
   }

   public boolean isQuarterHealth() {
      float healthAmount = this.getHealth() / this.getMaxHealth();
      return healthAmount <= 0.25F;
   }

   private float DMG() {
      return (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) + this.getAttributeValue(Attributes.ATTACK_DAMAGE) * this.getRageMeter() * 0.2F);
   }

   public void aiStep() {
      super.aiStep();
      if (this.getAttackState() == 1) {
         this.flyingdestroy();
         if (this.attackTicks == 23) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 25) {
            this.AreaAttack(5.5F, 5.5F, 270.0F, 1.0F, (float)CMCommonConfig.Maledictus.SmashHpDamage, 200, false, false);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.05F, 0, 20);
            this.MakeRingparticle(2.5F, 0.2F, 40, 86, 236, 204, 1.0F, 30.0F);
            this.BlockPillarparticle(1.0F, 2.5F, 0.2F, 20, 6);
         }
      }

      if (this.getAttackState() == 2 && this.attackTicks == 8) {
         this.playSound((SoundEvent)ModSounds.MALEDICTUS_BOW_PULL.get(), 1.0F, 1.0F);
      }

      if (this.getAttackState() == 3) {
         this.flyingdestroy();
         if (this.attackTicks == 6) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_LEAP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 28) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_BOW_PULL.get(), 1.0F, 1.0F);
         }
      }

      if (this.getAttackState() == 4 && (this.verticalCollisionBelow || !this.getInBlockState().getFluidState().isEmpty())) {
         this.setAttackState(5);
      }

      if (this.getAttackState() == 6 && this.attackTicks == 30 && this.level().isClientSide) {
         for (int i = 0; i < 20 + this.random.nextInt(2); i++) {
            float f2 = this.random.nextFloat() * (float) (Math.PI * 2);
            float f3 = Mth.sqrt(this.random.nextFloat()) * this.getBbWidth() * 0.5F;
            double d0 = this.getX() + Mth.cos(f2) * f3;
            double d4 = this.getZ() + Mth.sin(f2) * f3;
            this.level()
               .addParticle((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), d0, this.getY() + this.getBbHeight() * 0.6 + i * 0.05, d4, 0.0, 0.1, 0.0);
         }
      }

      if (this.getAttackState() == 7) {
         if (this.attackTicks == 10) {
            this.masseffectParticle(5.0F);
         }

         if (this.attackTicks == 15) {
            this.masseffectParticle(7.0F);
         }

         if (this.attackTicks == 20) {
            this.masseffectParticle(9.0F);
         }

         if (this.attackTicks == 30) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_BATTLE_CRY.get(), 1.0F, 1.0F);
            this.Roarparticle(1.5F, 0.0F, 1.8F, 20, 26, 158, 130, 0.4F, 0.4F, 0.5F, 2.5F);
         }

         if (this.attackTicks == 33 || this.attackTicks == 36) {
            this.Roarparticle(1.5F, 0.0F, 1.8F, 20, 26, 158, 130, 0.4F, 0.4F, 0.5F, 2.5F);
         }

         if (this.attackTicks == 32) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 34) {
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.2F, 0, 40);
            this.setRageMeter(0);
            if (this.level().isClientSide) {
               float vec = 1.0F;
               float math = 0.0F;
               float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
               float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
               double theta = this.yBodyRot * (Math.PI / 180.0);
               theta += Math.PI / 2;
               double vecX = Math.cos(theta);
               double vecZ = Math.sin(theta);
               this.level()
                  .addParticle(
                     new RingParticleOptions(0.0F, (float) (Math.PI / 2), 30, 86, 236, 204, 1.0F, 85.0F, false, 0),
                     this.getX() + vec * vecX + f * math,
                     this.getY() + 0.02F,
                     this.getZ() + vec * vecZ + f1 * math,
                     0.0,
                     0.0,
                     0.0
                  );
            } else {
               DamageSource damagesource = CMDamageTypes.causeMaledictioSoulDamage(this);

               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(7.0))) {
                  if (!this.isAlliedTo(entity) && entity != this) {
                     entity.hurt(
                        damagesource, this.DMG() * 1.5F + Math.min(this.DMG() * 1.5F, entity.getMaxHealth() * (float)CMCommonConfig.Maledictus.AOEHpDamage)
                     );
                  }
               }
            }
         }

         if (this.attackTicks > 34 && this.attackTicks < 44) {
            this.Sphereparticle(0.3F, 1.0F, 4.0F);
         }
      }

      if (this.getAttackState() == 8) {
         this.flyingdestroy();
         if (this.attackTicks == 23) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_LEAP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks >= 65 && (this.onGround() || !this.getInBlockState().getFluidState().isEmpty())) {
            this.setAttackState(9);
         }
      }

      if (this.getAttackState() == 9) {
         this.flyingdestroy();
         if (this.attackTicks == 2) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 4) {
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.3F, 0, 40);
            this.MakeRingparticle(2.5F, 0.2F, 40, 86, 236, 204, 1.0F, 50.0F);
            this.BlockPillarparticle(1.5F, 2.5F, 0.2F, 70, 30);
            if (!this.level().isClientSide) {
               DamageSource damagesource = CMDamageTypes.causeMaledictioDamage(this);

               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.5))) {
                  if (!this.isAlliedTo(entity) && entity != this) {
                     boolean flag = entity.hurt(
                        damagesource,
                        this.DMG() * 1.25F + Math.min(this.DMG() * 1.25F, entity.getMaxHealth() * (float)CMCommonConfig.Maledictus.FlyingSmashHpDamage)
                     );
                     if (flag) {
                        this.rageTicks = 200;
                        if (this.getRageMeter() < 5) {
                           this.setRageMeter(this.getRageMeter() + 1);
                        }
                     }
                  }
               }
            }
         }

         if (this.attackTicks == 8) {
            this.ShieldSmashDamage(2.0F, 2, 4.0F, 2.5F, 1.0F, (float)CMCommonConfig.Maledictus.ShockWaveHpDamage, 0.05F);
         }

         if (this.attackTicks == 10) {
            this.ShieldSmashDamage(2.0F, 3, 4.0F, 2.5F, 1.0F, (float)CMCommonConfig.Maledictus.ShockWaveHpDamage, 0.05F);
         }

         if (this.attackTicks == 12) {
            this.ShieldSmashDamage(2.0F, 4, 4.0F, 2.5F, 1.0F, (float)CMCommonConfig.Maledictus.ShockWaveHpDamage, 0.05F);
         }

         if (this.attackTicks == 14) {
            this.ShieldSmashDamage(2.0F, 5, 4.0F, 2.5F, 1.0F, (float)CMCommonConfig.Maledictus.ShockWaveHpDamage, 0.05F);
         }

         if (this.attackTicks == 16) {
            this.ShieldSmashDamage(2.0F, 6, 4.0F, 2.5F, 1.0F, (float)CMCommonConfig.Maledictus.ShockWaveHpDamage, 0.05F);
         }
      }

      if (this.getAttackState() == 11 || this.getAttackState() == 12 || this.getAttackState() == 13 || this.getAttackState() == 14) {
         if (this.attackTicks == 21) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.attackTicks == 24) {
            this.playSound((SoundEvent)ModSounds.PHANTOM_SPEAR.get(), 1.0F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.attackTicks >= 24 && this.attackTicks <= 33) {
            this.AfterImageParticle(-0.4F, 255, 255, 255);
            this.Rushattack(-0.05, 0.5, 3.25, 1.1F, (float)CMCommonConfig.Maledictus.HpDamage, 0, true);
            if (this.level().isClientSide) {
               double x = this.getX();
               double y = this.getY() + this.getBbHeight() / 2.0F;
               double z = this.getZ();
               float yaw = (float)Math.toRadians(-this.getYRot());
               float yaw2 = (float)Math.toRadians(-this.getYRot() + 180.0F);
               float pitch = (float)Math.toRadians(-this.getXRot());
               this.level().addParticle(new RingParticleOptions(yaw, pitch, 40, 86, 236, 204, 1.0F, 50.0F, false, 2), x, y, z, 0.0, 0.0, 0.0);
               this.level().addParticle(new RingParticleOptions(yaw2, pitch, 40, 86, 236, 204, 1.0F, 50.0F, false, 2), x, y, z, 0.0, 0.0, 0.0);
            }
         }
      }

      if (this.getAttackState() == 15 || this.getAttackState() == 16) {
         if (this.attackTicks == 13) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.attackTicks == 16) {
            this.playSound((SoundEvent)ModSounds.PHANTOM_SPEAR.get(), 1.0F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.attackTicks >= 16 && this.attackTicks <= 25) {
            this.AfterImageParticle(-0.4F, 255, 255, 255);
            this.Rushattack(-0.035, 0.5, 3.25, 1.2F, (float)CMCommonConfig.Maledictus.HpDamage, 0, true);
            if (this.level().isClientSide) {
               double x = this.getX();
               double y = this.getY() + this.getBbHeight() / 2.0F;
               double z = this.getZ();
               float yaw = (float)Math.toRadians(-this.getYRot());
               float yaw2 = (float)Math.toRadians(-this.getYRot() + 180.0F);
               float pitch = (float)Math.toRadians(-this.getXRot());
               this.level().addParticle(new RingParticleOptions(yaw, pitch, 40, 86, 236, 204, 1.0F, 50.0F, false, 2), x, y, z, 0.0, 0.0, 0.0);
               this.level().addParticle(new RingParticleOptions(yaw2, pitch, 40, 86, 236, 204, 1.0F, 50.0F, false, 2), x, y, z, 0.0, 0.0, 0.0);
            }
         }
      }

      if (this.getAttackState() == 8 || this.getAttackState() == 3 || this.getAttackState() == 24 || this.getAttackState() == 28 || this.getAttackState() == 29
         )
       {
         this.AfterImageParticle(-0.4F, 255, 255, 255);
      }

      if (this.getAttackState() == 17) {
         if (this.attackTicks == 13) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.attackTicks == 16) {
            this.playSound((SoundEvent)ModSounds.PHANTOM_SPEAR.get(), 1.0F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.attackTicks >= 16 && this.attackTicks <= 24) {
            this.Rushattack(0.0, 0.5, 3.25, 1.3F, (float)CMCommonConfig.Maledictus.HpDamage, 0, true);
            this.AfterImageParticle(-0.4F, 255, 255, 255);
            if (this.level().isClientSide) {
               double x = this.getX();
               double y = this.getY() + this.getBbHeight() / 2.0F;
               double z = this.getZ();
               float yaw = (float)Math.toRadians(-this.getYRot());
               float yaw2 = (float)Math.toRadians(-this.getYRot() + 180.0F);
               float pitch = (float)Math.toRadians(-this.getXRot());
               this.level().addParticle(new RingParticleOptions(yaw, pitch, 40, 86, 236, 204, 1.0F, 50.0F, false, 2), x, y, z, 0.0, 0.0, 0.0);
               this.level().addParticle(new RingParticleOptions(yaw2, pitch, 40, 86, 236, 204, 1.0F, 50.0F, false, 2), x, y, z, 0.0, 0.0, 0.0);
            }
         }
      }

      if (this.getAttackState() == 18) {
         if (this.attackTicks == 18) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 20) {
            this.AreaAttack(3.25F, 3.25F, 220.0F, 1.0F, (float)CMCommonConfig.Maledictus.HpDamage, 0, false, false);
         }

         if (this.attackTicks == 32) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 34) {
            this.AreaAttack(3.25F, 3.25F, 220.0F, 1.0F, (float)CMCommonConfig.Maledictus.HpDamage, 160, false, false);
         }
      }

      if (this.getAttackState() == 19) {
         if (this.attackTicks == 10) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 12) {
            this.AreaAttack(4.0F, 4.0F, 180.0F, 1.0F, (float)CMCommonConfig.Maledictus.HpDamage, 0, false, false);
         }

         if (this.attackTicks == 22) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 24) {
            this.MakeRingparticle(2.5F, 0.2F, 40, 86, 236, 204, 1.0F, 30.0F);
            this.BlockPillarparticle(1.0F, 2.5F, 0.2F, 20, 4);
            this.ComboAreaAttack(5.0F, 5.0F, 70.0F, 1.2F, (float)CMCommonConfig.Maledictus.HpDamage, 200, false);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.05F, 0, 20);
         }
      }

      if (this.getAttackState() == 21) {
         if (this.attackTicks == 10) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 12) {
            this.AreaAttack(4.25F, 4.25F, 180.0F, 1.0F, (float)CMCommonConfig.Maledictus.HpDamage, 0, true, false);
         }

         if (this.attackTicks == 22) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 24) {
            this.AreaAttack(5.0F, 5.0F, 70.0F, 1.2F, (float)CMCommonConfig.Maledictus.HpDamage, 0, true, false);
            this.MakeRingparticle(2.5F, 0.2F, 40, 86, 236, 204, 1.0F, 30.0F);
            this.BlockPillarparticle(1.0F, 2.5F, 0.2F, 20, 4);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.05F, 0, 20);
         }
      }

      if (this.getAttackState() == 22) {
         if (this.attackTicks == 21) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 23) {
            this.uppercut(0.2, 3.25, 1.0F, (float)CMCommonConfig.Maledictus.HpDamage, 120, true);
         }

         if (this.attackTicks == 31) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 0.5F, 1.0F);
         }

         if (this.attackTicks == 33) {
            this.uppercut(0.25, 3.8, 1.0F, (float)CMCommonConfig.Maledictus.HpDamage, 120, false);
            this.MakeRingparticle(3.5F, -0.3F, 40, 86, 236, 204, 1.0F, 20.0F);
            this.BlockPillarparticle(1.0F, 3.5F, -0.3F, 20, 4);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.05F, 0, 20);
         }
      }

      if (this.getAttackState() == 23) {
         if (this.attackTicks == 21) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 23) {
            this.uppercut(0.2, 3.25, 1.0F, (float)CMCommonConfig.Maledictus.HpDamage, 120, true);
         }

         if (this.attackTicks == 31) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 0.5F, 1.0F);
         }

         if (this.attackTicks == 33) {
            this.uppercut(0.25, 3.8, 1.0F, (float)CMCommonConfig.Maledictus.HpDamage, 120, false);
            this.MakeRingparticle(3.5F, 0.7F, 40, 86, 236, 204, 1.0F, 20.0F);
            this.BlockPillarparticle(1.0F, 3.5F, 0.7F, 20, 4);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.05F, 0, 20);
         }
      }

      if (this.getAttackState() == 24) {
         this.flyingdestroy();
         if (this.attackTicks == 23) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_LEAP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks >= 60 && (this.onGround() || !this.getInBlockState().getFluidState().isEmpty())) {
            this.setAttackState(25);
         }
      }

      if (this.getAttackState() == 25) {
         this.flyingdestroy();
         if (this.attackTicks == 4) {
            this.playSound((SoundEvent)ModSounds.PHANTOM_SPEAR.get(), 1.0F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.3F, 0, 40);
            this.MakeRingparticle(2.25F, 0.3F, 40, 86, 236, 204, 1.0F, 30.0F);
            this.BlockPillarparticle(1.0F, 2.25F, 0.3F, 20, 4);
            if (!this.level().isClientSide) {
               DamageSource damagesource = CMDamageTypes.causeMaledictioDamage(this);

               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0))) {
                  if (!this.isAlliedTo(entity) && entity != this) {
                     boolean flag = entity.hurt(
                        damagesource,
                        this.DMG() * 1.25F + Math.min(this.DMG() * 1.25F, entity.getMaxHealth() * (float)CMCommonConfig.Maledictus.FlyingSmashHpDamage)
                     );
                     if (flag) {
                        this.rageTicks = 200;
                        if (this.getRageMeter() < 5) {
                           this.setRageMeter(this.getRageMeter() + 1);
                        }
                     }
                  }
               }
            }

            this.StrikeWindmillHalberd(6, 10, 1.0, 0.75, 0.2, 1);
            this.StrikeWindmillHalberd(4, 10, 1.0, 1.2, 0.15, 1);
         }

         if (this.attackTicks == 37) {
            this.MakeRingparticle(2.25F, 0.3F, 40, 86, 236, 204, 1.0F, 30.0F);
            this.BlockPillarparticle(1.5F, 2.25F, 0.3F, 40, 10);
            if (!this.level().isClientSide) {
               DamageSource damagesource = CMDamageTypes.causeMaledictioDamage(this);

               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0))) {
                  if (!this.isAlliedTo(entity) && entity != this) {
                     boolean flag = entity.hurt(
                        damagesource,
                        this.DMG() * 1.25F + Math.min(this.DMG() * 1.25F, entity.getMaxHealth() * (float)CMCommonConfig.Maledictus.FlyingSmashHpDamage)
                     );
                     if (flag) {
                        this.rageTicks = 200;
                        if (this.getRageMeter() < 5) {
                           this.setRageMeter(this.getRageMeter() + 1);
                        }
                     }
                  }
               }
            }
         }

         if (this.attackTicks == 39) {
            this.StrikeHalberd(14, 14.0F, 7 + this.random.nextInt(3), 4.0, 1);
         }

         if (this.attackTicks == 40) {
            this.StrikeHalberd(16, 16.0F, 10 + this.random.nextInt(5), 5.5, 1);
         }

         if (this.attackTicks == 42 && this.isHalfHealth()) {
            this.StrikeHalberd(18, 18.0F, 13 + this.random.nextInt(8), 6.5, 1);
         }

         if (this.attackTicks == 43 && this.isQuarterHealth()) {
            this.StrikeHalberd(20, 20.0F, 16 + this.random.nextInt(10), 7.5, 1);
         }
      }

      if (this.getAttackState() == 26) {
         if (this.attackTicks == 13) {
            this.playSound((SoundEvent)ModSounds.PHANTOM_SPEAR.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 15) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F);
            this.AreaAttack(4.5F, 4.5F, 80.0F, 1.2F, (float)CMCommonConfig.Maledictus.SmashHpDamage, 0, true, false);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
         }

         if (this.attackTicks == 44) {
            this.AreaAttack(5.5F, 5.5F, 110.0F, 1.0F, (float)CMCommonConfig.Maledictus.SmashHpDamage, 0, true, false);
         }

         if (this.attackTicks == 44) {
            this.radagonskill(0.7F, 2, 2);
         }

         if (this.attackTicks == 46) {
            this.radagonskill(0.7F, 4, 2);
         }

         if (this.attackTicks == 48) {
            this.radagonskill(0.7F, 6, 2);
         }

         if (this.attackTicks == 50) {
            this.radagonskill(0.7F, 8, 2);
         }

         if (this.attackTicks == 52) {
            this.radagonskill(0.7F, 10, 2);
         }

         if (this.attackTicks == 54) {
            this.radagonskill(0.7F, 12, 2);
         }

         if (this.attackTicks == 56) {
            this.radagonskill(0.7F, 14, 2);
         }

         if (this.attackTicks == 58) {
            this.radagonskill(0.7F, 16, 2);
         }
      }

      if (this.getAttackState() == 27 && this.attackTicks == 20) {
         this.AreaAttack(5.25F, 5.25F, 110.0F, 1.0F, (float)CMCommonConfig.Maledictus.HpDamage, 120, false, true);
         this.playSound((SoundEvent)ModSounds.AXE_SWING.get(), 1.0F, 1.3F);
      }

      if (this.getAttackState() == 29) {
         if (this.attackTicks == 1) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F);
         }

         this.Grab(-0.025, 0.5, 1.5, 0.2F, 0.0F, 0, true);
         if (this.level().isClientSide) {
            for (int i = 0; i < 2; i++) {
               this.level()
                  .addParticle(
                     (ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0
                  );
            }
         }
      }

      if (this.getAttackState() == 31) {
         if (this.level().isClientSide) {
            for (int i = 0; i < 2; i++) {
               this.level()
                  .addParticle(
                     (ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0
                  );
            }
         }

         if (this.attackTicks == 17) {
            this.playSound((SoundEvent)ModSounds.MALEDICTUS_LEAP.get(), 1.0F, 1.0F);
         }
      }

      if (this.getAttackState() == 32) {
         if (this.onGround() || !this.getInBlockState().getFluidState().isEmpty()) {
            this.setAttackState(33);
         }

         if (this.level().isClientSide) {
            for (int i = 0; i < 2; i++) {
               this.level()
                  .addParticle(
                     (ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0
                  );
            }
         }
      }

      if (this.getAttackState() == 33) {
         if (this.attackTicks == 2) {
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.2F, 0, 40);
            if (this.level().isClientSide) {
               float vec = 1.0F;
               float math = 0.0F;
               float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
               float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
               double theta = this.yBodyRot * (Math.PI / 180.0);
               theta += Math.PI / 2;
               double vecX = Math.cos(theta);
               double vecZ = Math.sin(theta);
               this.level()
                  .addParticle(
                     new RingParticleOptions(0.0F, (float) (Math.PI / 2), 30, 86, 236, 204, 1.0F, 85.0F, false, 0),
                     this.getX() + vec * vecX + f * math,
                     this.getY() + 0.02F,
                     this.getZ() + vec * vecZ + f1 * math,
                     0.0,
                     0.0,
                     0.0
                  );
            } else {
               DamageSource damagesource = CMDamageTypes.causeMaledictioSoulDamage(this);

               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(7.0))) {
                  if (!this.isAlliedTo(entity) && entity != this) {
                     entity.hurt(
                        damagesource, this.DMG() * 1.75F + Math.min(this.DMG() * 1.75F, entity.getMaxHealth() * (float)CMCommonConfig.Maledictus.AOEHpDamage)
                     );
                  }
               }
            }
         }

         if (this.attackTicks > 1 && this.attackTicks < 11) {
            this.Sphereparticle(0.3F, 1.0F, 4.0F);
         }
      }
   }

   private void blockbreak() {
      if (!this.isNoAi() && !this.level().isClientSide) {
         if (CMCommonConfig.Maledictus.ignoreMobGriefing) {
            this.blockdestroy();
         } else if (EventHooks.canEntityGrief(this.level(), this)) {
            this.blockdestroy();
         }
      }
   }

   private void flyingdestroy() {
      if (!this.level().isClientSide) {
         if (CMCommonConfig.Maledictus.ignoreMobGriefing) {
            this.blockdestroy2(0.35, 2.0);
         } else if (EventHooks.canEntityGrief(this.level(), this)) {
            this.blockdestroy2(0.35, 2.0);
         }
      }
   }

   private void blockdestroy() {
      if (this.destroyBlocksTick > 0) {
         this.destroyBlocksTick--;
         if (this.destroyBlocksTick == 0) {
            AABB aabb = this.getBoundingBox().inflate(0.5);

            for (BlockPos blockpos : BlockPos.betweenClosed(
               Mth.floor(aabb.minX), Mth.floor(this.getY()), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
            )) {
               BlockState blockstate = this.level().getBlockState(blockpos);
               if (!blockstate.isAir()
                  && blockstate.canEntityDestroy(this.level(), blockpos, this)
                  && !blockstate.is(ModTag.MALEDICTUS_IMMUNE)
                  && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)) {
                  this.level().destroyBlock(blockpos, true, this);
               }
            }
         }
      }

      AABB aabb = this.getBoundingBox().inflate(0.2, 0.5, 0.2);

      for (BlockPos blockpos : BlockPos.betweenClosed(
         Mth.floor(aabb.minX), Mth.floor(this.getY()), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
      )) {
         BlockState blockstate = this.level().getBlockState(blockpos);
         if (!blockstate.isAir()
            && blockstate.canEntityDestroy(this.level(), blockpos, this)
            && blockstate.is(ModTag.FROSTED_PRISON_CHANDELIER)
            && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)) {
            this.level().destroyBlock(blockpos, true, this);
         }
      }
   }

   private void blockdestroy2(double xz, double y) {
      AABB aabb = this.getBoundingBox().inflate(xz, y, xz);

      for (BlockPos blockpos : BlockPos.betweenClosed(
         Mth.floor(aabb.minX), Mth.floor(this.getY()), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
      )) {
         BlockState blockstate = this.level().getBlockState(blockpos);
         if (!blockstate.isAir()
            && blockstate.canEntityDestroy(this.level(), blockpos, this)
            && !blockstate.is(ModTag.MALEDICTUS_IMMUNE)
            && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)) {
            this.level().destroyBlock(blockpos, true, this);
         }
      }
   }

   private void StrikeHalberd(int rune, float close, float radius, double range, int delay) {
      float angle2 = (float) (Math.PI / 180.0) * this.yBodyRot;

      for (int k = 0; k < rune; k++) {
         float f2 = angle2 + k * (float) Math.PI * 2.0F / close + (float) (Math.PI * 2) / radius;
         this.spawnHalberd(this.getX() + Mth.cos(f2) * range, this.getZ() + Mth.sin(f2) * range, this.getY() - 5.0, this.getY() + 3.0, f2, delay);
      }

      if (this.level().isClientSide) {
         for (int k = 0; k < rune; k++) {
            float f2 = angle2 + k * (float) Math.PI * 2.0F / close + (float) (Math.PI * 2) / radius;

            for (int i1 = 0; i1 < 6 + this.random.nextInt(2); i1++) {
               double DeltaMovementX = this.getRandom().nextGaussian() * 0.007;
               double DeltaMovementY = this.getRandom().nextGaussian() * 0.007;
               double DeltaMovementZ = this.getRandom().nextGaussian() * 0.007;
               float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
               double extraX = 0.5F * Mth.sin((float)(Math.PI + angle));
               double extraY = 0.3F;
               double extraZ = 0.5F * Mth.cos(angle);
               this.level()
                  .addParticle(
                     (ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(),
                     this.getX() + Mth.cos(f2) * range + extraX,
                     this.getY() + extraY,
                     this.getZ() + Mth.sin(f2) * range + extraZ,
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }
      }
   }

   private void StrikeWindmillHalberd(int numberOfBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay) {
      float angleIncrement = (float)((Math.PI * 2) / numberOfBranches);

      for (int branch = 0; branch < numberOfBranches; branch++) {
         float baseAngle = angleIncrement * branch;

         for (int i = 0; i < particlesPerBranch; i++) {
            double currentRadius = initialRadius + i * radiusIncrement;
            float currentAngle = (float)(baseAngle + i * angleIncrement / initialRadius + (float)(i * curveFactor));
            double xOffset = currentRadius * Math.cos(currentAngle);
            double zOffset = currentRadius * Math.sin(currentAngle);
            double spawnX = this.getX() + xOffset;
            double spawnY = this.getY() + 0.3;
            double spawnZ = this.getZ() + zOffset;
            int d3 = delay * (i + 1);
            this.spawnHalberd(spawnX, spawnZ, this.getY() - 5.0, this.getY() + 3.0, currentAngle, d3);
            double deltaX = this.getRandom().nextGaussian() * 0.007;
            double deltaY = this.getRandom().nextGaussian() * 0.007;
            double deltaZ = this.getRandom().nextGaussian() * 0.007;
            if (this.level().isClientSide) {
               this.level().addParticle((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
            }
         }
      }
   }

   private void radagonskill(float spreadarc, int distance, int delay) {
      double perpFacing = this.yBodyRot * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      double spread = Math.PI * spreadarc;
      int arcLen = Mth.ceil(distance * spread);

      for (int i = 0; i < arcLen; i++) {
         double theta = (i / (arcLen - 1.0) - 0.5) * spread + facingAngle;
         double vx = Math.cos(theta);
         double vz = Math.sin(theta);
         double px = this.getX() + vx * distance;
         double pz = this.getZ() + vz * distance;
         int hitX = Mth.floor(px);
         int hitZ = Mth.floor(pz);
         this.spawnHalberd(hitX + 0.5, hitZ + 0.5, this.getY() - 5.0, this.getY() + 3.0, (float)theta, delay);
         this.radagonparticle(hitX + 0.5, hitZ + 0.5, this.getY() - 5.0, this.getY() + 3.0);
      }
   }

   private void radagonparticle(double x, double z, double minY, double maxY) {
      BlockPos blockpos = BlockPos.containing(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.below();
         BlockState blockstate = this.level().getBlockState(blockpos1);
         if (blockstate.isFaceSturdy(this.level(), blockpos1, Direction.UP)) {
            if (!this.level().isEmptyBlock(blockpos)) {
               BlockState blockstate1 = this.level().getBlockState(blockpos);
               VoxelShape voxelshape = blockstate1.getCollisionShape(this.level(), blockpos);
               if (!voxelshape.isEmpty()) {
                  d0 = voxelshape.max(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.below();
      } while (blockpos.getY() >= Mth.floor(minY) - 1);

      if (flag && this.level().isClientSide) {
         for (int i1 = 0; i1 < 2; i1++) {
            double DeltaMovementX = this.getRandom().nextGaussian() * 0.007;
            double DeltaMovementY = this.getRandom().nextGaussian() * 0.007;
            double DeltaMovementZ = this.getRandom().nextGaussian() * 0.007;
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
            double extraX = 0.35F * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.3F;
            double extraZ = 0.35F * Mth.cos(angle);
            this.level()
               .addParticle(
                  (ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(),
                  x + extraX,
                  blockpos.getY() + d0 + extraY,
                  z + extraZ,
                  DeltaMovementX,
                  DeltaMovementY,
                  DeltaMovementZ
               );
         }
      }
   }

   private void spawnHalberd(double x, double z, double minY, double maxY, float rotation, int delay) {
      BlockPos blockpos = BlockPos.containing(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.below();
         BlockState blockstate = this.level().getBlockState(blockpos1);
         if (blockstate.isFaceSturdy(this.level(), blockpos1, Direction.UP)) {
            if (!this.level().isEmptyBlock(blockpos)) {
               BlockState blockstate1 = this.level().getBlockState(blockpos);
               VoxelShape voxelshape = blockstate1.getCollisionShape(this.level(), blockpos);
               if (!voxelshape.isEmpty()) {
                  d0 = voxelshape.max(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.below();
      } while (blockpos.getY() >= Mth.floor(minY) - 1);

      if (flag) {
         this.level()
            .addFreshEntity(
               new Phantom_Halberd_Entity(
                  this.level(),
                  x,
                  blockpos.getY() + d0,
                  z,
                  rotation,
                  delay,
                  this,
                  (float)CMCommonConfig.Maledictus.PhantomHalberdDamage + (float)CMCommonConfig.Maledictus.PhantomHalberdDamage * this.getRageMeter() * 0.1F
               )
            );
      }
   }

   private void ComboAreaAttack(float range, float height, float arc, float damage, float hpdamage, int shieldbreakticks, boolean maledictio) {
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(range, height, range, range);
      if (!this.level().isClientSide) {
         DamageSource damagesource = maledictio ? CMDamageTypes.causeMaledictioDamage(this) : this.damageSources().mobAttack(this);

         for (LivingEntity entityHit : entitiesHit) {
            float entityHitAngle = (float)((Math.atan2(entityHit.getZ() - this.getZ(), entityHit.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
            float entityAttackingAngle = this.yBodyRot % 360.0F;
            if (entityHitAngle < 0.0F) {
               entityHitAngle += 360.0F;
            }

            if (entityAttackingAngle < 0.0F) {
               entityAttackingAngle += 360.0F;
            }

            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            float entityHitDistance = (float)Math.sqrt(
               (entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ()) + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX())
            );
            if ((
                  entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                     || entityRelativeAngle >= 360.0F - arc / 2.0F
                     || entityRelativeAngle <= -360.0F + arc / 2.0F
               )
               && !this.isAlliedTo(entityHit)
               && !(entityHit instanceof Maledictus_Entity)
               && entityHit != this) {
               boolean flag = entityHit.hurt(damagesource, this.DMG() * damage + Math.min(this.DMG() * damage, entityHit.getMaxHealth() * hpdamage));
               if (entityHit.isDamageSourceBlocked(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               if (flag) {
                  this.combo = true;
                  this.rageTicks = 200;
                  if (this.getRageMeter() < 5) {
                     this.setRageMeter(this.getRageMeter() + 1);
                  }
               }
            }
         }
      }
   }

   private void Grab(double inflateXZ, double inflateY, double range, float damage, float hpdamage, int shieldbreakticks, boolean maledictio) {
      double yaw = this.yBodyRot * (Math.PI / 180.0);
      yaw += Math.PI / 2;
      double xExpand = range * Math.cos(yaw);
      double zExpand = range * Math.sin(yaw);
      AABB attackRange = this.getBoundingBox().inflate(inflateXZ, inflateY, inflateXZ).expandTowards(xExpand, 0.0, zExpand);
      if (!this.level().isClientSide) {
         DamageSource damagesource = maledictio ? CMDamageTypes.causeMaledictioDamage(this) : this.damageSources().mobAttack(this);

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, attackRange)) {
            if (!this.isAlliedTo(entity) && entity != this) {
               boolean flag = entity.hurt(damagesource, this.DMG() * damage + Math.min(this.DMG() * damage, entity.getMaxHealth() * hpdamage));
               if (entity.isDamageSourceBlocked(damagesource) && entity instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               if (flag) {
                  this.grab = true;
                  if (!entity.getType().is(ModTag.IGNIS_CANT_POKE) && entity.isAlive()) {
                     if (entity.isShiftKeyDown()) {
                        entity.setShiftKeyDown(false);
                     }

                     if (this.getPassengers().isEmpty() && !this.level().isClientSide) {
                        entity.startRiding(this, true);
                        PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                           entity, new MessageEntityCameraSwitch.ThridPerson(entity.getId()), new CustomPacketPayload[0]
                        );
                     }
                  }
               }
            }
         }
      }
   }

   private void ShieldSmashDamage(float spreadarc, int distance, float mxy, float vec, float damage, float hpdamage, float airborne) {
      if (!this.level().isClientSide) {
         double bodyRotRad = this.yBodyRot * (Math.PI / 180.0);
         double cosBodyRot = Math.cos(bodyRotRad);
         double sinBodyRot = Math.sin(bodyRotRad);
         double facingAngle = bodyRotRad + (Math.PI / 2);
         double commonOffsetX = vec * -sinBodyRot;
         double commonOffsetZ = vec * cosBodyRot;
         double baseX = this.getX() + commonOffsetX;
         double baseZ = this.getZ() + commonOffsetZ;
         int hitY = Mth.floor(this.getBoundingBox().minY - 0.5);
         double spread = Math.PI * spreadarc;
         int arcLen = Mth.ceil(distance * spread);
         double minY = this.getY() - 1.0;
         double maxY = this.getY() + mxy;
         DamageSource damageSource = CMDamageTypes.causeMaledictioDamage(this);
         float baseDamage = this.DMG() * damage;

         for (int i = 0; i < arcLen; i++) {
            double thetaRatio = arcLen > 1 ? (double)i / (arcLen - 1) : 0.5;
            double theta = (thetaRatio - 0.5) * spread + facingAngle;
            double vx = Math.cos(theta);
            double vz = Math.sin(theta);
            double px = baseX + vx * distance;
            double pz = baseZ + vz * distance;
            int hitX = Mth.floor(px);
            int hitZ = Mth.floor(pz);
            BlockPos pos = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.level().getBlockState(pos);
            int maxDepth = 30;

            for (int depthCount = 0; depthCount < maxDepth && block.getRenderShape() != RenderShape.MODEL; depthCount++) {
               pos = pos.below();
               block = this.level().getBlockState(pos);
            }

            if (block.getRenderShape() != RenderShape.MODEL) {
               block = Blocks.AIR.defaultBlockState();
            }

            Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(this.level(), hitX + 0.5, hitY + 1.0, hitZ + 0.5, block, 10);
            fallingBlockEntity.push(0.0, 0.2 + this.getRandom().nextGaussian() * 0.15, 0.0);
            this.level().addFreshEntity(fallingBlockEntity);
            AABB selection = new AABB(px - 0.5, minY, pz - 0.5, px + 0.5, maxY, pz + 0.5);

            for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, selection)) {
               if (!this.isAlliedTo(entity) && entity != this) {
                  float finalDamage = baseDamage + Math.min(baseDamage, entity.getMaxHealth() * hpdamage);
                  boolean flag = entity.hurt(damageSource, finalDamage);
                  if (flag) {
                     entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, airborne * distance + this.level().random.nextDouble() * 0.15, 0.0));
                     entity.hasImpulse = true;
                  }
               }
            }
         }
      }
   }

   private void MakeRingparticle(float vec, float math, int duration, int r, int g, int b, float a, float scale) {
      if (this.level().isClientSide) {
         float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);
         this.level()
            .addParticle(
               new RingParticleOptions(0.0F, (float) (Math.PI / 2), duration, r, g, b, a, scale, false, 2),
               this.getX() + vec * vecX + f * math,
               this.getY() + 0.02F,
               this.getZ() + vec * vecZ + f1 * math,
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void AfterImageParticle(float vec, int r, int g, int b) {
      if (this.level().isClientSide) {
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);
         this.level()
            .addParticle(
               new AfterImageParticleOptions(this.getId(), r, g, b, false, 5), this.xOld + vecX * vec, this.yOld, this.zOld + vecZ * vec, 0.0, 0.0, 0.0
            );
      }
   }

   private void BlockPillarparticle(float size, float vec, float math, int amount, int randomInt) {
      if (this.level().isClientSide) {
         float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);

         for (int i1 = 0; i1 < amount + this.random.nextInt(2 + randomInt); i1++) {
            double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
            double extraX = size * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.3F;
            double extraZ = size * Mth.cos(angle);
            int hitX = Mth.floor(this.getX() + vec * vecX + extraX);
            int hitY = Mth.floor(this.getY());
            int hitZ = Mth.floor(this.getZ() + vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.level().getBlockState(hit.below());
            int maxDepth = 5;

            for (int depthCount = 0; depthCount < maxDepth && block.getRenderShape() != RenderShape.MODEL; depthCount++) {
               hit = hit.below();
               block = this.level().getBlockState(hit);
            }

            if (block.getRenderShape() != RenderShape.MODEL) {
               block = Blocks.AIR.defaultBlockState();
            }

            if (block.getRenderShape() != RenderShape.INVISIBLE) {
               this.level()
                  .addParticle(
                     new BlockParticleOption(ParticleTypes.DUST_PILLAR, block),
                     this.getX() + vec * vecX + extraX + f * math,
                     this.getY() + extraY,
                     this.getZ() + vec * vecZ + extraZ + f1 * math,
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }
      }
   }

   private void Sphereparticle(float height, float vec, float size) {
      if (this.level().isClientSide && this.tickCount % 2 == 0) {
         double d0 = this.getX();
         double d1 = this.getY() + height;
         double d2 = this.getZ();
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);

         for (float i = -size; i <= size; i++) {
            for (float j = -size; j <= size; j++) {
               for (float k = -size; k <= size; k++) {
                  double d3 = j + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double d4 = i + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double d5 = k + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double d6 = Mth.sqrt((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + this.random.nextGaussian() * 0.05;
                  this.level().addParticle((ParticleOptions)ModParticle.CURSED_FLAME.get(), d0 + vec * vecX, d1, d2 + vec * vecZ, d3 / d6, d4 / d6, d5 / d6);
                  if (i != -size && i != size && j != -size && j != size) {
                     k += size * 2.0F - 1.0F;
                  }
               }
            }
         }
      }
   }

   private void masseffectParticle(float radius) {
      if (this.level().isClientSide) {
         for (int j = 0; j < 70; j++) {
            float angle = (float)(Math.random() * 2.0 * Math.PI);
            double distance = Math.sqrt(Math.random()) * radius;
            double extraX = this.getX() + distance * Mth.cos(angle);
            double extraY = this.getY() + 0.3F;
            double extraZ = this.getZ() + distance * Mth.sin(angle);
            this.level()
               .addParticle((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), extraX, extraY, extraZ, 0.0, this.random.nextGaussian() * 0.04, 0.0);
         }
      }
   }

   private void Roarparticle(float vec, float math, float y, int duration, int r, int g, int b, float a, float start, float inc, float end) {
      if (this.level().isClientSide) {
         float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);
         this.level()
            .addParticle(
               new RoarParticleOptions(duration, r, g, b, a, start, inc, end),
               this.getX() + vec * vecX + f * math,
               this.getY() + y,
               this.getZ() + vec * vecZ + f1 * math,
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void Rushattack(double inflateXZ, double inflateY, double range, float damage, float hpdamage, int shieldbreakticks, boolean maledictio) {
      double yaw = this.yBodyRot * (Math.PI / 180.0);
      yaw += Math.PI / 2;
      double xExpand = range * Math.cos(yaw);
      double zExpand = range * Math.sin(yaw);
      AABB attackRange = this.getBoundingBox().inflate(inflateXZ, inflateY, inflateXZ).expandTowards(xExpand, 0.0, zExpand);
      if (!this.level().isClientSide) {
         DamageSource damagesource = maledictio ? CMDamageTypes.causeMaledictioDamage(this) : this.damageSources().mobAttack(this);

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, attackRange)) {
            if (!this.isAlliedTo(entity) && entity != this) {
               boolean flag = entity.hurt(damagesource, this.DMG() * damage + Math.min(this.DMG() * damage, entity.getMaxHealth() * hpdamage));
               if (entity.isDamageSourceBlocked(damagesource) && entity instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               if (flag) {
                  this.rageTicks = 200;
                  if (this.getRageMeter() < 5) {
                     this.setRageMeter(this.getRageMeter() + 1);
                  }
               }
            }
         }
      }
   }

   private void uppercut(double inflate, double range, float damage, float hpdamage, int shieldbreakticks, boolean airborne) {
      double yaw = this.yBodyRot * (Math.PI / 180.0);
      yaw += Math.PI / 2;
      double xExpand = range * Math.cos(yaw);
      double zExpand = range * Math.sin(yaw);
      AABB attackRange = this.getBoundingBox().inflate(inflate).expandTowards(xExpand, 0.0, zExpand);
      DamageSource damagesource = this.damageSources().mobAttack(this);

      for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, attackRange)) {
         if (!this.isAlliedTo(entity) && entity != this) {
            boolean flag = entity.hurt(damagesource, this.DMG() * damage + Math.min(this.DMG() * damage, entity.getMaxHealth() * hpdamage));
            if (entity.isDamageSourceBlocked(damagesource) && entity instanceof Player player && shieldbreakticks > 0) {
               EntityUtil.disableShield(player, shieldbreakticks);
            }

            if (flag) {
               this.rageTicks = 200;
               if (this.getRageMeter() < 5) {
                  this.setRageMeter(this.getRageMeter() + 1);
               }

               if (airborne) {
                  double d0 = entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                  double d1 = Math.max(0.0, 1.0 - d0);
                  entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 0.4F * d1, 0.0));
                  entity.hasImpulse = true;
               }
            }
         }
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, float hpdamage, int shieldbreakticks, boolean maledictio, boolean knockback) {
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(range, height, range, range);
      DamageSource damagesource = maledictio ? CMDamageTypes.causeMaledictioDamage(this) : this.damageSources().mobAttack(this);

      for (LivingEntity entityHit : entitiesHit) {
         float entityHitAngle = (float)((Math.atan2(entityHit.getZ() - this.getZ(), entityHit.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
         float entityAttackingAngle = this.yBodyRot % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         float entityHitDistance = (float)Math.sqrt(
            (entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ()) + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX())
         );
         if ((
               entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                  || entityRelativeAngle >= 360.0F - arc / 2.0F
                  || entityRelativeAngle <= -360.0F + arc / 2.0F
            )
            && !this.isAlliedTo(entityHit)
            && !(entityHit instanceof Maledictus_Entity)
            && entityHit != this) {
            boolean flag = entityHit.hurt(damagesource, this.DMG() * damage + Math.min(this.DMG() * damage, entityHit.getMaxHealth() * hpdamage));
            if (entityHit.isDamageSourceBlocked(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               EntityUtil.disableShield(player, shieldbreakticks);
            }

            if (flag) {
               this.rageTicks = 200;
               if (this.getRageMeter() < 5) {
                  this.setRageMeter(this.getRageMeter() + 1);
               }

               double d0 = entityHit.getX() - this.getX();
               double d1 = entityHit.getZ() - this.getZ();
               double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
               if (knockback) {
                  entityHit.push(d0 / d2 * 2.5, 0.18, d1 / d2 * 2.2);
               }
            }
         }
      }
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_MALEDICTUS) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.MALEDICTUS_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.MALEDICTUS_DEATH.get();
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSounds.MALEDICTUS_IDLE.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.MALEDICTUS_MUSIC.get();
   }

   protected boolean isAffectedByFluids() {
      return false;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent1.addPlayer(player);
      this.bossEvent2.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent1.removePlayer(player);
      this.bossEvent2.removePlayer(player);
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   static class MaledictusChargeGoal extends Goal {
      private final Maledictus_Entity entity;
      private final int getattackstate;
      private final float attackrange;
      private final float attackminrange;
      private final int startweapon;
      private final int stopweapon;
      private final int attackseetick;
      private final int attackseetick2;
      private final int attackchargetick;
      private final float random;

      public MaledictusChargeGoal(
         Maledictus_Entity entity,
         int getattackstate,
         int attackseetick,
         int attackseetick2,
         int attackchargetick,
         float attackminrange,
         float attackrange,
         int startbow,
         int stopbow,
         float random
      ) {
         this.entity = entity;
         this.getattackstate = getattackstate;
         this.attackrange = attackrange;
         this.attackminrange = attackminrange;
         this.attackseetick = attackseetick;
         this.attackseetick2 = attackseetick2;
         this.attackchargetick = attackchargetick;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && target.isAlive()
            && this.entity.distanceTo(target) > this.attackminrange
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.charge_cooldown <= 0
            && this.entity.distanceTo(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.getSensing().hasLineOfSight(target);
      }

      public void start() {
         if (this.entity.isHalfHealth()) {
            this.entity.setAttackState(14);
         } else {
            this.entity.setAttackState(13);
         }

         this.entity.setWeapon(this.startweapon);
      }

      public boolean canContinueToUse() {
         return this.entity.getAttackState() == 13 ? this.entity.attackTicks <= 65 : this.entity.attackTicks <= 55;
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            boolean flag = this.entity.attackTicks < this.attackseetick || this.entity.attackTicks > this.attackseetick2;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 60.0F, 30.0F);
               this.entity.lookAt(target, 60.0F, 30.0F);
            } else {
               this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.attackTicks == this.attackchargetick) {
            float f1 = (float)Math.cos(Math.toRadians(this.entity.getYRot() + 90.0F));
            float f2 = (float)Math.sin(Math.toRadians(this.entity.getYRot() + 90.0F));
            if (target != null) {
               float r = this.entity.distanceTo(target);
               r = Mth.clamp(r, 0.0F, 7.0F);
               this.entity.push(f1 * 0.9 * r, 0.0, f2 * 0.9 * r);
            } else {
               this.entity.push(f1 * 3.0, 0.0, f2 * 3.0);
            }
         }

         if (this.entity.getAttackState() == 13
            && this.entity.attackTicks == 34
            && (this.entity.onGround() || this.entity.isInLava() || this.entity.isInWater())) {
            float speed = -1.7F;
            float dodgeYaw = (float)Math.toRadians(this.entity.getYRot() + 90.0F);
            Vec3 m = this.entity.getDeltaMovement().add(speed * Math.cos(dodgeYaw), 0.0, speed * Math.sin(dodgeYaw));
            this.entity.setDeltaMovement(m.x, 0.4, m.z);
            this.entity.hasImpulse = true;
         }
      }

      public void stop() {
         if (this.entity.getAttackState() == 14) {
            if (this.entity.isQuarterHealth()) {
               this.entity.setAttackState(16);
            } else {
               this.entity.setAttackState(15);
            }
         } else {
            this.entity.setAttackState(0);
            this.entity.charge_cooldown = 80;
            this.entity.setWeapon(this.stopweapon);
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class MaledictusChargeState extends InternalStateGoal {
      private final Maledictus_Entity entity;
      private final int startweapon;
      private final int stopweapon;
      private final int attackseetick;
      private final int attackseetick2;
      private final int attackchargetick;
      private final int backsteptick;
      private final int count;

      public MaledictusChargeState(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         int attackseetick2,
         int attackchargetick,
         int backsteptick,
         int startbow,
         int stopbow,
         int count
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick);
         this.entity = entity;
         this.attackseetick = attackseetick;
         this.attackseetick2 = attackseetick2;
         this.attackchargetick = attackchargetick;
         this.backsteptick = backsteptick;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
         this.count = count;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setWeapon(this.startweapon);
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            boolean flag = this.entity.attackTicks < this.attackseetick || this.entity.attackTicks > this.attackseetick2;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 60.0F, 30.0F);
               this.entity.lookAt(target, 60.0F, 30.0F);
            } else {
               this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.attackTicks == this.attackchargetick) {
            float f1 = (float)Math.cos(Math.toRadians(this.entity.getYRot() + 90.0F));
            float f2 = (float)Math.sin(Math.toRadians(this.entity.getYRot() + 90.0F));
            if (target != null) {
               float r = this.entity.distanceTo(target);
               r = Mth.clamp(r, 0.0F, 7.0F);
               this.entity.push(f1 * 0.9F * r, 0.0, f2 * 0.9F * r);
            } else {
               this.entity.push(f1 * 3.0, 0.0, f2 * 3.0);
            }
         }

         if (this.backsteptick > 0
            && this.entity.attackTicks == this.backsteptick
            && (this.entity.onGround() || this.entity.isInLava() || this.entity.isInWater())) {
            float speed = -1.7F;
            float dodgeYaw = (float)Math.toRadians(this.entity.getYRot() + 90.0F);
            Vec3 m = this.entity.getDeltaMovement().add(speed * Math.cos(dodgeYaw), 0.0, speed * Math.sin(dodgeYaw));
            this.entity.playSound((SoundEvent)ModSounds.MALEDICTUS_JUMP.get(), 1.0F, 1.0F);
            this.entity.setDeltaMovement(m.x, 0.4, m.z);
            this.entity.hasImpulse = true;
         }
      }

      @Override
      public void stop() {
         if (this.count == 1) {
            if (this.entity.isQuarterHealth()) {
               this.entity.setAttackState(16);
            } else {
               this.entity.setAttackState(15);
            }
         } else if (this.count == 2 && this.entity.getAttackState() == 16) {
            this.entity.setAttackState(17);
         } else {
            super.stop();
            this.entity.charge_cooldown = 80;
            this.entity.setWeapon(this.stopweapon);
         }
      }
   }

   static class MaledictusGrabGoal extends Goal {
      protected final Maledictus_Entity entity;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;
      private final int attackseetick;
      private final float attackrange;
      private final float random;
      private final int startweapon;
      private final int stopweapon;

      public MaledictusGrabGoal(
         Maledictus_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int startbow,
         int stopbow,
         float random
      ) {
         this.entity = entity;
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
         this.attackrange = attackrange;
         this.attackseetick = attackseetick;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && target.isAlive()
            && this.entity.distanceTo(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.grab_cooldown <= 0;
      }

      public void start() {
         this.entity.setAttackState(this.attackstate);
         this.entity.setWeapon(this.startweapon);
      }

      public void stop() {
         this.entity.setAttackState(this.attackendstate);
         this.entity.grab_cooldown = 300;
         this.entity.setWeapon(this.stopweapon);
      }

      public boolean canContinueToUse() {
         return this.entity.getAttackState() == this.attackstate && this.entity.attackTicks <= this.attackMaxtick;
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            boolean flag = this.entity.attackTicks < this.attackseetick;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.entity.lookAt(target, 30.0F, 30.0F);
            } else {
               this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class MaledictusGrabState extends InternalStateGoal {
      private final Maledictus_Entity entity;
      private final int startweapon;
      private final int stopweapon;

      public MaledictusGrabState(
         Maledictus_Entity entity, int getAttackState, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, int startbow, int stopbow
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick);
         this.entity = entity;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setWeapon(this.startweapon);
      }

      @Override
      public boolean canContinueToUse() {
         return super.canContinueToUse() && !this.entity.grab;
      }

      @Override
      public void tick() {
         if (this.entity.onGround()) {
            Vec3 vector3d = this.entity.getDeltaMovement();
            float f = this.entity.getYRot() * (float) (Math.PI / 180.0);
            Vec3 vector3d1 = new Vec3(-Mth.sin(f), this.entity.getDeltaMovement().y, Mth.cos(f)).scale(0.8).add(vector3d.scale(0.8));
            this.entity.setDeltaMovement(vector3d1.x, this.entity.getDeltaMovement().y, vector3d1.z);
            this.entity.hasImpulse = true;
         }
      }

      @Override
      public void stop() {
         if (this.entity.grab) {
            this.entity.setAttackState(31);
            this.entity.grab = false;
         } else {
            super.stop();
         }

         this.entity.setWeapon(this.stopweapon);
      }
   }

   static class MaledictusSpinSlashes extends InternalAttackGoal {
      private final Maledictus_Entity entity;
      private final int startweapon;
      private final int stopweapon;
      private final int attackseetick;
      private final int attackseetick2;
      private final int attackseetick3;
      private final int attackchargetick1;
      private final int attackchargetick2;
      private final float random;

      public MaledictusSpinSlashes(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         int attackseetick2,
         int attackseetick3,
         int attackchargetick1,
         int attackchargetick2,
         float attackrange,
         int startbow,
         int stopbow,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackseetick = attackseetick;
         this.attackseetick2 = attackseetick2;
         this.attackseetick3 = attackseetick3;
         this.attackchargetick1 = attackchargetick1;
         this.attackchargetick2 = attackchargetick2;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         return super.canUse() && this.entity.getRandom().nextFloat() * 100.0F < this.random && this.entity.spin_cooldown <= 0;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setWeapon(this.startweapon);
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            boolean flag = this.entity.attackTicks < this.attackseetick
               || this.entity.attackTicks > this.attackseetick2 && this.entity.attackTicks < this.attackseetick3;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 60.0F, 30.0F);
               this.entity.lookAt(target, 60.0F, 30.0F);
            } else {
               this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.attackTicks == this.attackchargetick1 || this.entity.attackTicks == this.attackchargetick2) {
            float f1 = (float)Math.cos(Math.toRadians(this.entity.getYRot() + 90.0F));
            float f2 = (float)Math.sin(Math.toRadians(this.entity.getYRot() + 90.0F));
            if (target != null) {
               float r = this.entity.distanceTo(target);
               r = Mth.clamp(r, 2.5F, 6.5F);
               this.entity.push(f1 * 0.35F * r, 0.0, f2 * 0.35F * r);
            } else {
               this.entity.push(f1 * 2.25, 0.0, f2 * 2.25);
            }
         }
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.setWeapon(this.stopweapon);
         this.entity.spin_cooldown = 100;
      }

      @Override
      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class MaledictusSuccessState extends InternalStateGoal {
      private final Maledictus_Entity entity;
      private final int startweapon;
      private final int stopweapon;
      private final int attackstrike;
      private final double dropspeed;

      public MaledictusSuccessState(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         int attackstrike,
         int startbow,
         int stopbow,
         double dropspeed
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick);
         this.entity = entity;
         this.attackstrike = attackstrike;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
         this.dropspeed = dropspeed;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setWeapon(this.startweapon);
      }

      @Override
      public void tick() {
         if (this.entity.attackTicks == 19) {
            this.entity.setDeltaMovement(0.0, 1.2, 0.0);
            this.entity.hasImpulse = true;
            this.entity.setFlying(true);
         }

         this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
         if (this.entity.attackTicks == this.attackstrike) {
            this.entity.setFlying(false);
         }
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.setWeapon(this.stopweapon);
      }
   }

   static class Maledictus_Bow extends InternalAttackGoal {
      private final Maledictus_Entity entity;
      private final float attackminrange;
      private final int attackshot;
      private final float random;

      public Maledictus_Bow(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackminrange,
         float attackrange,
         int attackshot,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackminrange = attackminrange;
         this.attackshot = attackshot;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse()
            && target != null
            && this.entity.distanceTo(target) > this.attackminrange
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.getSensing().hasLineOfSight(target);
      }

      @Override
      public void start() {
         super.start();
         this.entity.setWeapon(1);
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.setWeapon(0);
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         super.tick();
         if (this.entity.attackTicks == this.attackshot && target != null) {
            int arrowcount = 4;
            double offsetangle = Math.toRadians(6.0);
            double d1 = target.getX() - this.entity.getX();
            double d2 = target.getY(0.3333333333333333) - this.entity.getY();
            double d3 = target.getZ() - this.entity.getZ();

            for (int i = 0; i <= arrowcount - 1; i++) {
               double angle = (i - (arrowcount - 1) / 2.0) * offsetangle;
               double x = d1 * Math.cos(angle) + d3 * Math.sin(angle);
               double z = -d1 * Math.sin(angle) + d3 * Math.cos(angle);
               double distance = Math.sqrt(x * x + z * z);
               Phantom_Arrow_Entity throwntrident = new Phantom_Arrow_Entity(this.entity.level(), this.entity, target);
               throwntrident.setBaseDamage(
                  (float)CMCommonConfig.Maledictus.PhantomArrowDamage
                     + (float)CMCommonConfig.Maledictus.PhantomArrowDamage * this.entity.getRageMeter() * 0.05F
               );
               throwntrident.shoot(x, d2 + distance * 0.15F, z, 1.8F, 1.0F);
               this.entity.playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F, 1.0F / (this.entity.getRandom().nextFloat() * 0.4F + 0.8F));
               this.entity.level().addFreshEntity(throwntrident);
            }
         }
      }

      @Override
      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class Maledictus_Flying_Bow extends InternalAttackGoal {
      private final Maledictus_Entity entity;
      private final int attackshot;
      private final float random;

      public Maledictus_Flying_Bow(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackshot = attackshot;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse() && target != null && this.entity.getRandom().nextFloat() * 100.0F < this.random && this.entity.flyattack_cooldown <= 0;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setWeapon(1);
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.setWeapon(0);
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            boolean flag = this.entity.attackTicks < this.attackshot;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
               this.entity.lookAt(target, 30.0F, 90.0F);
            } else {
               this.entity.getLookControl().setLookAt(target, 0.0F, 90.0F);
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.attackTicks == 8) {
            if (target != null) {
               double x = 0.0;
               double z = 0.0;
               double r = 0.0;
               double maxR = 3.0;
               if (this.entity.distanceToSqr(target) < 36.0) {
                  float dodgeYaw = (float)Math.toRadians(this.entity.getYRot() + 90.0F);
                  double dis = this.entity.distanceToSqr(target);
                  r = Mth.clamp(8.0 / (dis + 0.1), 0.0, maxR);
                  x = Math.cos(dodgeYaw);
                  z = Math.sin(dodgeYaw);
               }

               double d1 = target.getY() - this.entity.getY();
               this.entity.setDeltaMovement(x * -r, 0.9 + Mth.clamp(d1 * 0.075, 0.0, 7.0), z * -r);
               this.entity.hasImpulse = true;
            } else {
               this.entity.setDeltaMovement(0.0, 0.9, 0.0);
               this.entity.hasImpulse = true;
            }

            this.entity.setFlying(true);
         }

         if (this.entity.attackTicks == 20) {
            this.entity.setDeltaMovement(0.0, 0.0, 0.0);
         }

         if (this.entity.attackTicks == 60) {
            this.entity.setFlying(false);
         }

         if (this.entity.attackTicks == this.attackshot && target != null) {
            double arrowcount = 4.0;
            double offsetangle = Math.toRadians(9.0);
            double d1 = target.getX() - this.entity.getX();
            double d2 = target.getY(0.3333333333333333) - this.entity.getY();
            double d3 = target.getZ() - this.entity.getZ();

            for (int i = 0; i <= arrowcount - 1.0; i++) {
               double angle = (i - (arrowcount - 1.0) / 2.0) * offsetangle;
               double x = d1 * Math.cos(angle) + d3 * Math.sin(angle);
               double z = -d1 * Math.sin(angle) + d3 * Math.cos(angle);
               double distance = Math.sqrt(x * x + z * z);
               Phantom_Arrow_Entity throwntrident = new Phantom_Arrow_Entity(this.entity.level(), this.entity, target);
               throwntrident.setBaseDamage(
                  (float)CMCommonConfig.Maledictus.PhantomArrowDamage
                     + (float)CMCommonConfig.Maledictus.PhantomArrowDamage * this.entity.getRageMeter() * 0.05F
               );
               throwntrident.shoot(x, d2 + distance * 0.15F, z, 1.5F, 1.0F);
               this.entity.playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F, 1.0F / (this.entity.getRandom().nextFloat() * 0.4F + 0.8F));
               this.entity.level().addFreshEntity(throwntrident);
            }
         }
      }

      @Override
      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class Maledictus_Flying_Smash extends InternalAttackGoal {
      private final Maledictus_Entity entity;
      private final int attackstrike;
      private final float random;
      private final double dropspeed;
      private final int startweapon;
      private final int stopweapon;

      public Maledictus_Flying_Smash(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         int startweapon,
         int stopweapon,
         float random,
         double dropspeed
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackstrike = attackshot;
         this.random = random;
         this.dropspeed = dropspeed;
         this.startweapon = startweapon;
         this.stopweapon = stopweapon;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse() && target != null && this.entity.getRandom().nextFloat() * 100.0F < this.random && this.entity.flyattack_cooldown <= 0;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setWeapon(this.startweapon);
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.setFlying(false);
         this.entity.setWeapon(this.stopweapon);
         this.entity.flyattack_cooldown = 100;
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.attackTicks < this.attackstrike && target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 0.0F);
            this.entity.lookAt(target, 30.0F, 0.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.attackTicks == 25) {
            if (target != null) {
               double d1 = target.getY() - this.entity.getY();
               this.entity.setDeltaMovement(0.0, 0.9 + Mth.clamp(d1 * 0.075, 0.0, 7.0), 0.0);
               this.entity.hasImpulse = true;
            } else {
               this.entity.setDeltaMovement(0.0, 0.9, 0.0);
               this.entity.hasImpulse = true;
            }

            this.entity.setFlying(true);
         }

         if (this.entity.attackTicks == this.attackstrike) {
            this.entity.setFlying(false);
            if (target != null) {
               double Y = this.dropspeed * Math.abs(this.entity.getY() - target.getY());
               double Z = 0.1F * (target.getZ() - this.entity.getZ());
               double X = 0.1F * (target.getX() - this.entity.getX());
               this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(X, -1.0 * Y, Z));
               this.entity.hasImpulse = true;
            }
         }
      }

      @Override
      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class Maledictus_Swing extends InternalAttackGoal {
      private final float attackminrange;
      private final float random;

      public Maledictus_Swing(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackminrange,
         float attackrange,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.attackminrange = attackminrange;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse()
            && target != null
            && this.entity.distanceTo(target) > this.attackminrange
            && this.entity.getRandom().nextFloat() * 100.0F < this.random;
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         super.tick();
         if (this.entity.attackTicks == 5) {
            if (target != null) {
               double d0 = target.getX() - this.entity.getX();
               double d1 = target.getY() - this.entity.getY();
               double d2 = target.getZ() - this.entity.getZ();
               Vec3 vec3 = new Vec3(d0, 0.7 + Mth.clamp(d1 * 0.075, 0.0, 10.0), d2).multiply(0.2, 1.0, 0.2);
               this.entity.setDeltaMovement(vec3);
               this.entity.hasImpulse = true;
            } else {
               Vec3 vec3 = new Vec3(0.0, 0.7, 0.0);
               this.entity.setDeltaMovement(vec3);
               this.entity.hasImpulse = true;
            }
         }
      }

      @Override
      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class MaledictusfallingState extends InternalStateGoal {
      private final Maledictus_Entity entity;
      private final int startbow;
      private final int stopbow;
      private final int attackseetick;

      public MaledictusfallingState(
         Maledictus_Entity entity, int getAttackState, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, int startbow, int stopbow
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick);
         this.entity = entity;
         this.attackseetick = attackseetick;
         this.startbow = startbow;
         this.stopbow = stopbow;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setWeapon(this.startbow);
         if (this.entity.isFlying()) {
            this.entity.setFlying(false);
         }
      }

      @Override
      public void tick() {
         this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
         LivingEntity target = this.entity.getTarget();
         if (this.entity.attackTicks < this.attackseetick && target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 0.0F);
            this.entity.lookAt(target, 30.0F, 0.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.flyattack_cooldown = 100;
         this.entity.setWeapon(this.stopbow);
      }
   }

   static class Uppercut extends InternalAttackGoal {
      protected final Maledictus_Entity entity;
      private final float random;

      public Uppercut(
         Maledictus_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         float random
      ) {
         super(entity, getattackstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.random = random;
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && super.canUse()
            && this.entity.getSensing().hasLineOfSight(target)
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.uppercut_cooldown <= 0;
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.uppercut_cooldown = 80;
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            boolean flag = this.entity.attackTicks < this.attackseetick;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 60.0F, 30.0F);
               this.entity.lookAt(target, 60.0F, 30.0F);
            } else {
               this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }
      }
   }
}
