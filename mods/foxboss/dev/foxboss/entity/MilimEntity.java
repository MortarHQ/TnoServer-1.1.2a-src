package dev.foxboss.entity;

import dev.foxboss.FoxBossConfig;
import dev.foxboss.network.MilimBossBarPayload;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.entity.template.TensuraHumanoidEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.entity.template.TensuraHumanoidEntity.HumanoidContainer;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.AnimationController.State;
import software.bernie.geckolib.animation.RawAnimation.Stage;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class MilimEntity extends TensuraHumanoidEntity implements GeoEntity {
   public static final int NORMAL = 0;
   public static final int CUTE = 1;
   public static final int MELEE = 2;
   public static final int BURST = 3;
   public static final int LEAP = 4;
   public static final int FLY = 5;
   public static final int DEFEAT = 6;
   public static final int SIT = 7;
   public static final int SIT_IDLE = 8;
   public static final int KICK = 9;
   public static final int PROUD = 10;
   public static final int CURIOUS = 11;
   public static final int STAND_UP = 12;
   public static final int PREVIEW = 13;
   public static final int CAST = 14;
   public static final int TACTICAL_FLIGHT = 15;
   public static final int HEAVY = 16;
   public static final int THROW_BLOCK = 17;
   public static final int THROW_PICKUP_TICK = 8;
   public static final int THROW_RELEASE_TICK = 20;
   public static final int THROW_END_TICK = 32;
   private static final EntityDataAccessor<BlockState> CARRIED_BLOCK = SynchedEntityData.defineId(MilimEntity.class, EntityDataSerializers.BLOCK_STATE);
   private static final RawAnimation THROW_ANIM = RawAnimation.begin().thenPlayAndHold("animation.throw_block");
   public static final int HEAVY_HIT_TICK = 13;
   public static final int HEAVY_DURATION_TICKS = 26;
   public static final int MELEE_DURATION_TICKS = 12;
   public static final int MELEE_HIT_TICK = 7;
   public static final double MELEE_REACH = 2.0;
   public static final int KICK_HIT_TICK = 7;
   public static final float KICK_DAMAGE = 110.0F;
   public static final float KICK_YAW_OFFSET = MilimMeleeContact.yawOffset(9);
   public static final int CUTE_COOLDOWN_TICKS = 1200;
   public static final int CUTE_END_TICKS = 76;
   private static final EntityDataAccessor<Integer> ACTION = SynchedEntityData.defineId(MilimEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Long> ACTION_START = SynchedEntityData.defineId(MilimEntity.class, EntityDataSerializers.LONG);
   private static final EntityDataAccessor<Boolean> DEMO = SynchedEntityData.defineId(MilimEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<String> DEBUG_ANIMATION = SynchedEntityData.defineId(MilimEntity.class, EntityDataSerializers.STRING);
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.idle");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.walk");
   private static final RawAnimation RUN = RawAnimation.begin().thenLoop("animation.run");
   private static final RawAnimation RUN_LEGS = RawAnimation.begin().thenLoop("animation.run_legs");
   private static final RawAnimation CUTE_ANIM = RawAnimation.begin().thenPlayAndHold("animation.idle_cute");
   private static final RawAnimation ATTACK = RawAnimation.begin().thenPlayAndHold("animation.attack");
   private static final RawAnimation HEAVY_PUNCH = RawAnimation.begin().thenPlayAndHold("animation.heavy_punch");
   private static final RawAnimation BURST_ANIM = RawAnimation.begin().thenPlayAndHold("animation.dragon_star_diffusion_burst");
   private static final RawAnimation LEAP_ANIM = RawAnimation.begin().thenPlayAndHold("animation.leap_to_air");
   private static final RawAnimation FLY_ANIM = RawAnimation.begin().thenLoop("animation.fly_idle");
   private static final RawAnimation DEFEAT_ANIM = RawAnimation.begin().thenPlayAndHold("animation.defeat_fall");
   private static final RawAnimation SIT_ANIM = RawAnimation.begin().thenPlayAndHold("animation.sit");
   private static final RawAnimation SIT_IDLE_ANIM = RawAnimation.begin().thenLoop("animation.sit_idle");
   private static final RawAnimation KICK_ANIM = RawAnimation.begin().thenPlayAndHold("animation.side_kick");
   private static final RawAnimation PROUD_ANIM = RawAnimation.begin().thenPlayAndHold("animation.idle_proud");
   private static final RawAnimation CURIOUS_ANIM = RawAnimation.begin().thenPlayAndHold("animation.idle_curious");
   private static final RawAnimation STAND_UP_ANIM = RawAnimation.begin().thenPlayAndHold("animation.stand_up");
   private static final List<String> ANIMATION_IDS = List.of(
      "animation.idle",
      "animation.idle_cute",
      "animation.walk",
      "animation.run",
      "animation.attack",
      "animation.dragon_star_diffusion_burst",
      "animation.leap_to_air",
      "animation.fly_idle",
      "animation.defeat_fall",
      "animation.sit",
      "animation.sit_idle",
      "animation.side_kick",
      "animation.idle_proud",
      "animation.idle_curious",
      "animation.stand_up",
      "animation.heavy_punch"
   );
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final ServerBossEvent bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.PINK, BossBarOverlay.PROGRESS);
   private int burstCooldown = 80;
   private int meleeCombo;
   private int cuteCooldown = 1200;
   private LivingEntity observedOwner;
   private Vec3 lastOwnerPosition;
   private boolean ownerMoving;
   private float castYaw;
   private boolean discardOnLoad;
   private int demoAction = 3;
   private int kickCooldown;
   private int heavyPunchCooldown = 200;
   private LivingEntity heavyPunchTarget;
   private LivingEntity kickTarget;
   private int debugDuration;
   private final MilimCombatTactics tactics = new MilimCombatTactics(this);
   private final MilimDragonAura dragonAura = new MilimDragonAura();
   private final MilimBlockThrow blockThrow = new MilimBlockThrow(this);
   private final MilimCombatSpacing combatSpacing = new MilimCombatSpacing(this);
   private final MilimAggroController aggro = new MilimAggroController(this);
   private final MilimBurstPlanner burstPlanner = new MilimBurstPlanner(this);
   private final MilimSatanaelCombat satanaelCombat = new MilimSatanaelCombat(this);

   public MilimEntity(EntityType<? extends MilimEntity> type, Level level) {
      super(type, level);
      this.xpReward = 100;
      this.setPersistenceRequired();
   }

   public boolean canOpenMountInventory(Player player) {
      return this.isAlive()
         && this.isTame()
         && player.getUUID().equals(this.getOwnerUUID())
         && player.level() == this.level()
         && this.distanceToSqr(player) < 64.0;
   }

   public void openMainInventory(Player player) {
      if (this.canOpenMountInventory(player)) {
         super.openMainInventory(player);
      }
   }

   public void openSideInventory(Player player, int page) {
      if (this.canOpenMountInventory(player) && page >= 0 && page < (this.getChestSlots() + 35) / 36) {
         super.openSideInventory(player, page);
      }
   }

   protected void createInventory() {
      super.createInventory();
      HumanoidContainer original = this.inventory;
      original.removeListener(this);
      this.inventory = new HumanoidContainer(this.getInventorySize(), this.getMiscSlots()) {
         public boolean stillValid(Player player) {
            return MilimEntity.this.canOpenMountInventory(player) && super.stillValid(player);
         }
      };

      for (int slot = 0; slot < original.getContainerSize(); slot++) {
         this.inventory.setItem(slot, original.getItem(slot).copy());
      }

      this.inventory.addListener(this);
      this.updateContainerEquipment();
   }

   public static Builder createAttributes() {
      return TensuraTamableEntity.setAttributes()
         .add(Attributes.MAX_HEALTH, 4000.0)
         .add(Attributes.ARMOR, 16.0)
         .add(Attributes.ATTACK_DAMAGE, 50.0)
         .add(Attributes.MOVEMENT_SPEED, 0.28)
         .add(Attributes.FOLLOW_RANGE, 48.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ACTION, 0);
      builder.define(ACTION_START, 0L);
      builder.define(DEMO, false);
      builder.define(DEBUG_ANIMATION, "");
      builder.define(CARRIED_BLOCK, Blocks.AIR.defaultBlockState());
   }

   public boolean checkSpawnRules(LevelAccessor level, MobSpawnType reason) {
      return MilimSpawning.checkSpawnChance(level, reason) && super.checkSpawnRules(level, reason);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new MilimEntity.ScriptedGoal());
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new MilimEntity.CombatGoal());
      this.goalSelector.addGoal(3, new MilimFollowOwnerGoal(this));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.75));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this) {
         public boolean canUse() {
            return !MilimEntity.this.hasCombatTarget() && super.canUse();
         }
      });
      this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this) {
         public boolean canUse() {
            return !MilimEntity.this.hasCombatTarget() && super.canUse();
         }
      });
      this.targetSelector.addGoal(3, new HurtByTargetGoal(this) {
         public boolean canUse() {
            return !MilimEntity.this.hasCombatTarget() && super.canUse();
         }
      });
   }

   public int action() {
      return (Integer)this.entityData.get(ACTION);
   }

   public BlockState getCarriedBlockState() {
      return (BlockState)this.entityData.get(CARRIED_BLOCK);
   }

   void setCarriedBlockState(BlockState state) {
      this.entityData.set(CARRIED_BLOCK, state);
   }

   public boolean isDemo() {
      return (Boolean)this.entityData.get(DEMO);
   }

   public boolean isDebugAnimation() {
      return !((String)this.entityData.get(DEBUG_ANIMATION)).isEmpty();
   }

   public static List<String> animationIds() {
      return ANIMATION_IDS;
   }

   public boolean playDebugAnimation(String id) {
      if (!this.level().isClientSide && this.isAlive() && !this.isDemo() && ANIMATION_IDS.contains(id)) {
         int next = switch (id) {
            case "animation.idle_cute" -> 1;
            case "animation.attack" -> 2;
            case "animation.dragon_star_diffusion_burst" -> 3;
            case "animation.leap_to_air" -> 4;
            case "animation.fly_idle" -> 5;
            case "animation.defeat_fall" -> 6;
            case "animation.sit" -> 7;
            case "animation.sit_idle" -> 8;
            case "animation.side_kick" -> 9;
            case "animation.idle_proud" -> 10;
            case "animation.idle_curious" -> 11;
            case "animation.stand_up" -> 12;
            default -> 13;
         };

         this.debugDuration = switch (id) {
            case "animation.idle", "animation.idle_curious" -> 64;
            case "animation.idle_cute" -> 76;
            case "animation.walk" -> 20;
            case "animation.run" -> 13;
            case "animation.attack", "animation.sit" -> 16;
            case "animation.dragon_star_diffusion_burst" -> 250;
            case "animation.leap_to_air" -> 18;
            case "animation.fly_idle" -> 48;
            case "animation.defeat_fall", "animation.heavy_punch" -> 26;
            case "animation.sit_idle" -> 80;
            case "animation.side_kick" -> 22;
            case "animation.idle_proud" -> 60;
            default -> 30;
         };
         this.entityData.set(DEBUG_ANIMATION, id);
         this.startAction(next);
         return true;
      } else {
         return false;
      }
   }

   public void stopDebugAnimation() {
      if (!this.level().isClientSide && this.isDebugAnimation()) {
         boolean seated = this.action() == 7 || this.action() == 8;
         this.entityData.set(DEBUG_ANIMATION, "");
         if (this.isOrderedToSit()) {
            this.startAction(8);
         } else if (seated) {
            this.startAction(12);
         } else {
            this.finishAction();
         }
      }
   }

   public float actionTicks(float partialTick) {
      return Math.max(0.0F, (float)(this.level().getGameTime() - (Long)this.entityData.get(ACTION_START)) + partialTick);
   }

   double animationTicks(float partialTick) {
      double ticks = this.actionTicks(partialTick);
      return this.isCombatMelee() ? Mth.clamp((ticks - 2.0) * 1.6, 0.0, 16.0) : ticks;
   }

   public Vec3 forward() {
      return Vec3.directionFromRotation(0.0F, this.getYRot());
   }

   public Vec3 chargeOrigin() {
      return this.position().add(0.0, 2.22, 0.0).add(this.forward().scale(0.22));
   }

   public Vec3 releaseOrigin() {
      return this.position().add(0.0, 1.45, 0.0).add(this.forward().scale(0.75));
   }

   public void prepareDemo(int action) {
      this.entityData.set(DEMO, true);
      this.demoAction = action;
      this.setNoAi(true);
      this.setNoGravity(true);
      this.setInvulnerable(true);
      this.castYaw = this.getYRot();
   }

   public void startAction(int action) {
      if (!this.level().isClientSide) {
         if (action != 17) {
            this.blockThrow.cancel();
         }

         this.combatSpacing.reset();
         this.burstPlanner.reset();
         this.setSprinting(false);
         if (action != 2 || this.isDemo() || this.isDebugAnimation()) {
            this.meleeCombo = 0;
         }

         this.getNavigation().stop();
         this.entityData.set(ACTION_START, this.level().getGameTime());
         this.entityData.set(ACTION, action);
         this.cuteCooldown = 1200;
         if (action == 3) {
            int a = (Integer)FoxBossConfig.BURST_COOLDOWN_MIN.get();
            int b = (Integer)FoxBossConfig.BURST_COOLDOWN_MAX.get();
            int min = Math.min(a, b);
            int max = Math.max(a, b);
            this.burstCooldown = min + this.random.nextInt(max - min + 1);
         }

         this.castYaw = this.getYRot();
         this.setNoGravity(this.isDemo() || action == 3 || action == 5 || action == 4);
         if (action == 9) {
            this.kickTarget = this.getTarget();
            this.aimKick();
         }

         if (action == 16) {
            this.heavyPunchTarget = this.getTarget();
            this.heavyPunchCooldown = 400;
            if (this.heavyPunchTarget != null) {
               this.faceCombatTarget(this.heavyPunchTarget, 16);
            }
         }
      }
   }

   private void finishAction() {
      if (this.action() == 17) {
         this.blockThrow.cancel();
      }

      this.cuteCooldown = 1200;
      this.entityData.set(ACTION, 0);
      this.setNoGravity(this.isDemo());
      this.setDeltaMovement(0.0, this.isNoGravity() ? 0.0 : this.getDeltaMovement().y, 0.0);
      this.kickTarget = null;
      this.heavyPunchTarget = null;
   }

   public void tick() {
      if (!this.level().isClientSide) {
         this.updateOwnerMovement();
         this.interruptIdleForActivity();
      }

      super.tick();
      if (!this.level().isClientSide) {
         if (this.discardOnLoad) {
            this.discard();
         } else {
            this.blockThrow.tick();
            MilimResistances.migrate(this);
            if (!this.isDemo() && !this.isDebugAnimation() && this.isAlive()) {
               if (this.isOrderedToSit()) {
                  this.clearTarget();
                  this.setInSittingPose(true);
                  if (this.action() != 7 && this.action() != 8) {
                     this.startAction(7);
                  }
               } else if (this.action() == 7 || this.action() == 8) {
                  this.setInSittingPose(false);
                  this.startAction(12);
               }
            }

            if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget().level() != this.level() || !this.canAttack(this.getTarget()))) {
               this.clearTarget();
            }

            this.interruptIdleForActivity();
            this.aggro.tick();
            this.tactics.tick();
            MilimWrathController.tick(this);
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
            this.bossEvent.setVisible(!this.isDemo() && !this.isTame() && this.isAlive());
            if (this.isAlive()) {
               if (this.burstCooldown > 0 && this.action() != 3) {
                  this.burstCooldown--;
               }

               if (this.canRest()) {
                  this.cuteCooldown = Math.max(0, this.cuteCooldown - 1);
               } else {
                  this.cuteCooldown = 1200;
               }

               if (this.kickCooldown > 0) {
                  this.kickCooldown--;
               }

               if (this.heavyPunchCooldown > 0 && this.action() != 16) {
                  this.heavyPunchCooldown--;
               }

               if (this.isDemo() && this.tickCount == 6) {
                  this.startAction(this.demoAction);
               }

               int t = (int)this.actionTicks(0.0F);
               if (this.isDebugAnimation() && t >= this.debugDuration) {
                  this.stopDebugAnimation();
               } else {
                  if (this.isCombatMelee() && t <= 7 && this.hasCombatTarget()) {
                     this.faceCombatTarget(this.getTarget());
                  }

                  if (this.action() == 17 && t > 8 && t <= 20 && this.hasCombatTarget()) {
                     this.castYaw = (float)(Mth.atan2(this.getTarget().getZ() - this.getZ(), this.getTarget().getX() - this.getX()) * 180.0 / Math.PI) - 90.0F;
                  }

                  if (this.action() == 9 && t <= 7) {
                     this.aimKick();
                  }

                  if (this.action() == 16 && t <= 13 && this.canReachAttack(this.heavyPunchTarget, 16)) {
                     this.faceCombatTarget(this.heavyPunchTarget, 16);
                  }

                  if (this.action() != 0 && this.action() != 15) {
                     this.setYRot(this.castYaw);
                     this.setYHeadRot(this.castYaw);
                     this.setYBodyRot(this.castYaw);
                     if (this.action() != 4 && !this.isCombatMelee()) {
                        this.setDeltaMovement(0.0, this.isNoGravity() ? 0.0 : this.getDeltaMovement().y, 0.0);
                     }
                  }

                  switch (this.action()) {
                     case 1:
                        if (t >= 76) {
                           this.endDemoOrAction();
                        }
                        break;
                     case 2:
                        LivingEntity victim = this.getTarget();
                        if (t == (this.isCombatMelee() ? 7 : 8) && this.attackContacts(victim, 2)) {
                           this.dragonAura.strike(false, () -> this.doHurtTarget(victim));
                        }

                        if (t >= (this.isCombatMelee() ? 12 : 16)) {
                           if (this.isCombatMelee() && !this.isNoAi() && !this.isOrderedToSit() && this.inMeleeReach(victim)) {
                              if (this.meleeCombo >= 3 && this.burstPlanner.wantsBurst(victim, this.burstCooldown, this.tactics.prefersPursuit())) {
                                 this.meleeCombo = 0;
                                 this.finishAction();
                              } else if (this.meleeCombo >= 3 && this.tactics.tryDefensiveHaki(victim)) {
                                 this.meleeCombo = 0;
                                 this.finishAction();
                              } else if (this.meleeCombo >= 3
                                 && this.heavyPunchCooldown <= 0
                                 && this.canReachAttack(victim, 16)
                                 && this.random.nextInt(10) == 0) {
                                 this.startAction(16);
                              } else if (this.meleeCombo >= 3 && this.kickCooldown <= 0 && this.canReachAttack(victim, 9)) {
                                 this.meleeCombo = 0;
                                 this.kickCooldown = 100;
                                 this.tactics.preferPursuit(100);
                                 this.startAction(9);
                              } else if (this.meleeCombo >= 3) {
                                 this.meleeCombo = 0;
                                 this.finishAction();
                                 this.combatSpacing.request();
                              } else {
                                 this.startMelee(victim);
                              }
                           } else {
                              this.meleeCombo = 0;
                              this.endDemoOrAction();
                           }
                        }
                        break;
                     case 3:
                        if (t == 24) {
                           this.playSound(SoundEvents.BEACON_POWER_SELECT, 1.2F, 1.6F);
                        }

                        if (BurstTimeline.shouldFire(t, (Integer)FoxBossConfig.VOLLEY_INTERVAL.get())) {
                           this.fireVolley(t);
                        }

                        if (t >= 250) {
                           this.tactics.preferPursuit(100);
                           if (this.isDemo()) {
                              this.vanish();
                           } else {
                              this.finishAction();
                           }
                        }
                        break;
                     case 4:
                        this.setDeltaMovement(0.0, t < 12 ? 0.13 * (1.0 - t / 18.0) : 0.0, 0.0);
                        if (t >= 18 && !this.isDebugAnimation()) {
                           this.startAction(5);
                        }
                        break;
                     case 5:
                        if (t >= 48) {
                           this.endDemoOrAction();
                        }
                        break;
                     case 6:
                        if (t >= 30) {
                           this.endDemoOrAction();
                        }
                        break;
                     case 7:
                        if (t >= 16 && !this.isDebugAnimation()) {
                           this.entityData.set(ACTION, 8);
                        }
                     case 8:
                     case 15:
                        break;
                     case 9:
                        if (t == 7) {
                           this.hitKick();
                        }

                        if (t >= 22) {
                           this.tactics.preferPursuit(100);
                           this.endDemoOrAction();
                           this.combatSpacing.request();
                        }
                        break;
                     case 10:
                        if (t >= 60) {
                           this.endDemoOrAction();
                        }
                        break;
                     case 11:
                        if (t >= 64) {
                           this.endDemoOrAction();
                        }
                        break;
                     case 12:
                        if (t >= 30) {
                           this.endDemoOrAction();
                        }
                        break;
                     case 13:
                     default:
                        if (this.canRest() && this.cuteCooldown <= 0) {
                           this.cuteCooldown = 1200;

                           this.startAction(switch (this.random.nextInt(3)) {
                              case 0 -> 10;
                              case 1 -> 11;
                              default -> 1;
                           });
                        }
                        break;
                     case 14:
                        if (t >= 16) {
                           this.finishAction();
                        }
                        break;
                     case 16:
                        if (t == 13) {
                           this.hitHeavyPunch();
                        }

                        if (t >= 26) {
                           this.tactics.preferPursuit(100);
                           this.endDemoOrAction();
                           this.combatSpacing.request();
                        }
                        break;
                     case 17:
                        this.blockThrow.animateTick(t);
                        if (t >= 32) {
                           this.finishAction();
                        }
                  }
               }
            }
         }
      }
   }

   private void updateOwnerMovement() {
      LivingEntity owner = this.getOwner();
      this.ownerMoving = owner != null
         && (
            owner.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5
               || owner == this.observedOwner
                  && this.lastOwnerPosition != null
                  && owner.position().subtract(this.lastOwnerPosition).horizontalDistanceSqr() > 1.0E-5
         );
      this.observedOwner = owner;
      this.lastOwnerPosition = owner == null ? null : owner.position();
   }

   boolean needsOwnerFollowing() {
      LivingEntity owner = this.getOwner();
      if (owner != null
         && owner.isAlive()
         && !owner.isSpectator()
         && owner.level() == this.level()
         && !this.isWandering()
         && !this.isOrderedToSit()
         && !this.unableToMoveToOwner()) {
         double distance = this.distanceToSqr(owner);
         return distance >= 36.0 || distance > 4.0 && (this.ownerMoving || owner.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5);
      } else {
         return false;
      }
   }

   private boolean isIdleAction() {
      return this.action() == 1 || this.action() == 10 || this.action() == 11;
   }

   private boolean hasIdleMovement() {
      double dx = this.getX() - this.xo;
      double dz = this.getZ() - this.zo;
      return !this.getNavigation().isDone() || this.isSprinting() || this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5 || dx * dx + dz * dz > 1.0E-5;
   }

   private boolean canRest() {
      return this.action() == 0
         && !this.isDemo()
         && !this.isDebugAnimation()
         && !this.isNoAi()
         && !this.isOrderedToSit()
         && this.onGround()
         && !this.isInLiquid()
         && !this.isPassenger()
         && !this.hasCombatTarget()
         && !this.hasIdleMovement()
         && !this.needsOwnerFollowing();
   }

   private void interruptIdleForActivity() {
      if (this.isIdleAction() && !this.isDemo() && !this.isDebugAnimation() && (this.hasCombatTarget() || this.needsOwnerFollowing() || this.hasIdleMovement())
         )
       {
         this.finishAction();
      }
   }

   void finishTacticalFlight() {
      if (this.action() == 15) {
         this.finishAction();
      }
   }

   public boolean isCombatMelee() {
      return this.action() == 2 && !this.isDemo() && !this.isDebugAnimation();
   }

   private boolean inMeleeReach(LivingEntity target) {
      return this.canReachAttack(target, 2);
   }

   private boolean canReachAttack(LivingEntity target, int attack) {
      return target != null
         && target.level() == this.level()
         && target.isAlive()
         && this.canAttack(target)
         && this.hasLineOfSight(target)
         && MilimMeleeContact.canReach(this, target, attack);
   }

   private boolean attackContacts(LivingEntity target, int attack) {
      return target != null
         && target.level() == this.level()
         && target.isAlive()
         && this.canAttack(target)
         && this.hasLineOfSight(target)
         && MilimMeleeContact.hits(this, target, attack);
   }

   private void faceCombatTarget(LivingEntity target) {
      this.faceCombatTarget(target, 2);
   }

   private void faceCombatTarget(LivingEntity target, int attack) {
      this.castYaw = (float)(Mth.atan2(target.getZ() - this.getZ(), target.getX() - this.getX()) * 180.0 / Math.PI)
         - 90.0F
         + MilimMeleeContact.yawOffset(attack);
      this.getLookControl().setLookAt(target, 360.0F, 360.0F);
      this.setYRot(this.castYaw);
      this.setYHeadRot(this.castYaw);
      this.setYBodyRot(this.castYaw);
   }

   private void startMelee(LivingEntity target) {
      this.faceCombatTarget(target);
      this.meleeCombo++;
      this.startAction(2);
   }

   private void aimKick() {
      if (this.kickTarget != null && this.kickTarget.isAlive()) {
         float targetYaw = (float)(Mth.atan2(this.kickTarget.getZ() - this.getZ(), this.kickTarget.getX() - this.getX()) * 180.0 / Math.PI) - 90.0F;
         this.castYaw = targetYaw + KICK_YAW_OFFSET;
      }
   }

   private void hitKick() {
      LivingEntity victim = this.kickTarget;
      if (this.attackContacts(victim, 9)) {
         if (victim.hurt(this.damageSources().mobAttack(this), MilimSatanaelBonuses.kickDamage(this, 110.0F))) {
            this.satanaelCombat.contact(victim);
            Vec3 away = victim.position().subtract(this.position()).multiply(1.0, 0.0, 1.0).normalize();
            victim.setDeltaMovement(away.scale(0.52).add(0.0, 0.2, 0.0));
            victim.hurtMarked = true;
            this.setLastHurtMob(victim);
            this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, 0.9F);
         }
      }
   }

   private void hitHeavyPunch() {
      LivingEntity victim = this.heavyPunchTarget;
      if (this.attackContacts(victim, 16)) {
         if (this.dragonAura.strike(true, () -> this.doHurtTarget(victim, 3.0F))) {
            Vec3 away = victim.position().subtract(this.position()).multiply(1.0, 0.0, 1.0).normalize();
            if (away.lengthSqr() < 1.0E-6) {
               away = this.forward();
            }

            victim.setDeltaMovement(away.scale(1.2).add(0.0, 0.35, 0.0));
            victim.hurtMarked = true;
            this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, 0.7F);
         }
      }
   }

   public boolean canAttack(LivingEntity target) {
      return target != this && !this.isAlliedTo(target) && super.canAttack(target);
   }

   public DamageSource getBaseDamageSource() {
      return this.dragonAura.decorate(super.getBaseDamageSource());
   }

   public boolean doHurtTarget(Entity target, float multiplier) {
      boolean hit = super.doHurtTarget(target, multiplier);
      if (hit && target instanceof LivingEntity living) {
         this.satanaelCombat.contact(living);
      }

      return hit;
   }

   void tickSatanaelCombat(SkillStorage skills, ManasSkillInstance skill, boolean fighting) {
      this.satanaelCombat.tick(skills, skill, fighting);
   }

   void recordSatanaelContact(LivingEntity target) {
      this.satanaelCombat.contact(target);
   }

   public boolean isAlliedTo(Entity other) {
      if (this.isTame() && this.getOwnerUUID() != null) {
         if (this.getOwnerUUID().equals(other.getUUID())) {
            return true;
         }

         if (other instanceof TamableAnimal pet && this.getOwnerUUID().equals(pet.getOwnerUUID())) {
            return true;
         }
      }

      return super.isAlliedTo(other);
   }

   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   private void endDemoOrAction() {
      if (this.isDemo()) {
         this.vanish();
      } else {
         this.finishAction();
      }
   }

   private void vanish() {
      ((ServerLevel)this.level()).sendParticles(ParticleTypes.END_ROD, this.getX(), this.getY() + 1.0, this.getZ(), 24, 0.35, 0.75, 0.35, 0.05);
      this.discard();
   }

   public List<LivingEntity> burstTargets() {
      double range = (Double)FoxBossConfig.BURST_RANGE.get();
      Vec3 f = this.forward();
      return this.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            this.getBoundingBox().inflate(range),
            target -> {
               if (target != this
                  && !this.isAlliedTo(target)
                  && target.isAlive()
                  && !target.isSpectator()
                  && !(target instanceof Player p && p.getAbilities().invulnerable)) {
                  Vec3 d = target.position().subtract(this.position());
                  return BurstTimeline.isInFront(d.x, d.y, d.z, f.x, f.z, range);
               } else {
                  return false;
               }
            }
         );
   }

   private void fireVolley(int tick) {
      ServerLevel server = (ServerLevel)this.level();
      List<LivingEntity> targets = this.burstTargets();
      int volley = (tick - 89) / (Integer)FoxBossConfig.VOLLEY_INTERVAL.get();
      int index = 0;

      for (LivingEntity target : targets) {
         DragonStarEntity.launch(server, this, target, this.releaseOrigin(), target.getBoundingBox().getCenter(), index++ + volley * 7, false);
      }

      for (int i = 0; i < 11; i++) {
         Vec3 dir = this.forward().yRot((i - 5) * 0.12F);
         Vec3 end = this.releaseOrigin().add(dir.scale(22 + i % 3 * 4)).add(0.0, i % 3 * 0.4 - 0.7, 0.0);
         DragonStarEntity.launch(server, this, null, this.releaseOrigin(), end, i + volley * 11, true);
      }

      server.playSound(null, this.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.HOSTILE, 1.6F, 1.4F + this.random.nextFloat() * 0.2F);
      if (tick == 89) {
         this.playSound((SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), 1.3F, 1.8F);
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      LivingEntity previousTarget = this.getTarget();
      boolean wasOrderedToSit = this.isOrderedToSit();
      boolean result = super.hurt(source, amount);
      if (!wasOrderedToSit) {
         this.aggro.attacked(source, amount, previousTarget);
      }

      boolean hostileAttempt = !this.level().isClientSide
         && amount > 0.0F
         && !this.isInvulnerable()
         && source.getEntity() instanceof LivingEntity attacker
         && attacker.isAlive()
         && this.canAttack(attacker);
      if (!wasOrderedToSit && hostileAttempt) {
         this.satanaelCombat.contact((LivingEntity)source.getEntity());
      }

      if (result || hostileAttempt) {
         this.cuteCooldown = 1200;
      }

      if (this.isIdleAction() && (result || hostileAttempt && !this.isDemo() && !this.isDebugAnimation())) {
         this.entityData.set(DEBUG_ANIMATION, "");
         this.finishAction();
      }

      return result;
   }

   boolean hasCombatTarget() {
      LivingEntity target = this.getTarget();
      return target != null && target.level() == this.level() && target.isAlive() && this.canAttack(target);
   }

   void prioritizeAttacker(LivingEntity attacker) {
      this.burstPlanner.reset();
      this.tactics.cancelForRetarget();
      this.meleeCombo = 0;
      this.clearTarget();
      this.setTarget(attacker);
      this.getNavigation().stop();
      if (this.isIdleAction() || this.action() == 2 || this.action() == 9 || this.action() == 16 || this.action() == 14) {
         this.finishAction();
      }
   }

   public void die(DamageSource source) {
      this.entityData.set(DEBUG_ANIMATION, "");
      if (!this.level().isClientSide) {
         this.startAction(6);
      }

      this.setNoGravity(false);
      super.die(source);
   }

   protected void tickDeath() {
      this.deathTime++;
      if (this.deathTime >= 30 && !this.level().isClientSide) {
         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.setVisible(!this.isDemo() && !this.isTame() && this.isAlive());
      PacketDistributor.sendToPlayer(player, new MilimBossBarPayload(this.bossEvent.getId(), true), new CustomPacketPayload[0]);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
      PacketDistributor.sendToPlayer(player, new MilimBossBarPayload(this.bossEvent.getId(), false), new CustomPacketPayload[0]);
   }

   public boolean removeWhenFarAway(double distance) {
      return false;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putBoolean("FoxBossDebug", this.isDemo());
      tag.putInt("BurstCooldown", this.burstCooldown);
      tag.putInt("CuteCooldown", this.cuteCooldown);
      tag.putBoolean("FoxBossRestTimer", true);
      tag.putInt("KickCooldown", this.kickCooldown);
      tag.putInt("HeavyPunchCooldown", this.heavyPunchCooldown);
      this.blockThrow.save(tag);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      if (!tag.contains("Items")) {
         tag = tag.copy();
         ListTag items = new ListTag();
         EquipmentSlot[] slots = new EquipmentSlot[]{
            EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD
         };

         for (int i = 0; i < slots.length; i++) {
            CompoundTag item = tag.getList(i < 2 ? "HandItems" : "ArmorItems", 10).getCompound(i < 2 ? i : i - 2).copy();
            if (item.contains("id")) {
               item.putByte("Slot", (byte)this.getSlotId(slots[i]));
               items.add(item);
            }
         }

         tag.put("Items", items);
      }

      super.readAdditionalSaveData(tag);
      this.blockThrow.load(tag);
      AttributeInstance attackDamage = this.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attackDamage != null && attackDamage.getBaseValue() == 30.0) {
         attackDamage.setBaseValue(50.0);
      }

      this.meleeCombo = 0;
      this.discardOnLoad = tag.getBoolean("FoxBossDebug");
      this.burstPlanner.reset();
      this.satanaelCombat.reset();
      this.burstCooldown = tag.getInt("BurstCooldown");
      this.kickCooldown = tag.getInt("KickCooldown");
      this.heavyPunchCooldown = tag.contains("HeavyPunchCooldown") ? Mth.clamp(tag.getInt("HeavyPunchCooldown"), 0, 400) : 200;
      this.cuteCooldown = tag.getBoolean("FoxBossRestTimer") && tag.contains("CuteCooldown") ? Mth.clamp(tag.getInt("CuteCooldown"), 0, 1200) : 1200;
      this.setNoGravity(false);
      this.entityData.set(DEBUG_ANIMATION, "");
      if (this.isOrderedToSit()) {
         this.entityData.set(ACTION, 8);
      }
   }

   public void registerControllers(ControllerRegistrar registrar) {
      registrar.add(
         new AnimationController(this, "punch_legs", 2, state -> this.isCombatMelee() && state.isMoving() ? state.setAndContinue(RUN_LEGS) : PlayState.STOP)
      );
      registrar.add(new MilimEntity.SyncedController(this));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public RawAnimation animationForState(boolean moving) {
      if (this.action() == 13) {
         return switch ((String)this.entityData.get(DEBUG_ANIMATION)) {
            case "animation.walk" -> WALK;
            case "animation.run" -> RUN;
            case "animation.heavy_punch" -> HEAVY_PUNCH;
            default -> IDLE;
         };
      } else {
         return switch (this.action()) {
            case 1 -> CUTE_ANIM;
            case 2 -> ATTACK;
            case 3 -> BURST_ANIM;
            case 4 -> LEAP_ANIM;
            case 5 -> FLY_ANIM;
            case 6 -> DEFEAT_ANIM;
            case 7 -> SIT_ANIM;
            case 8 -> SIT_IDLE_ANIM;
            case 9 -> KICK_ANIM;
            case 10 -> PROUD_ANIM;
            case 11 -> CURIOUS_ANIM;
            case 12 -> STAND_UP_ANIM;
            default -> moving ? (this.isSprinting() ? RUN : WALK) : IDLE;
            case 14 -> ATTACK;
            case 15 -> FLY_ANIM;
            case 16 -> HEAVY_PUNCH;
            case 17 -> THROW_ANIM;
         };
      }
   }

   private PlayState animate(AnimationState<MilimEntity> state) {
      RawAnimation previous = state.getController().getCurrentRawAnimation();
      String clip = previous != null && !previous.getAnimationStages().isEmpty() ? ((Stage)previous.getAnimationStages().getFirst()).animationName() : "";

      int matchedEnd = switch (clip) {
         case "animation.idle_cute" -> 76;
         case "animation.side_kick" -> 22;
         case "animation.idle_proud" -> 60;
         case "animation.idle_curious" -> 64;
         case "animation.stand_up" -> 30;
         case "animation.attack" -> 16;
         case "animation.dragon_star_diffusion_burst" -> 250;
         case "animation.sit" -> this.action() == 8 ? 16 : Integer.MAX_VALUE;
         default -> Integer.MAX_VALUE;
      };
      boolean matchedExit = (this.action() == 0 || this.action() == 8 && clip.equals("animation.sit"))
         && !state.isMoving()
         && this.actionTicks(state.getPartialTick()) >= matchedEnd;
      state.getController().transitionLength(matchedExit ? 0 : (this.action() == 2 ? 2 : 4));
      return state.setAndContinue(this.animationForState(state.isMoving()));
   }

   private final class CombatGoal extends Goal {
      CombatGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         return MilimEntity.this.hasCombatTarget()
            && (MilimEntity.this.action() == 0 || MilimEntity.this.isCombatMelee())
            && !MilimEntity.this.isDemo()
            && !MilimEntity.this.isDebugAnimation()
            && !MilimEntity.this.isOrderedToSit();
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         if (!MilimEntity.this.isOrderedToSit() && !MilimEntity.this.isDebugAnimation() && !MilimEntity.this.isDemo()) {
            LivingEntity target = MilimEntity.this.getTarget();
            if (MilimEntity.this.hasCombatTarget()) {
               if (MilimEntity.this.isCombatMelee()) {
                  if (!MilimEntity.this.inMeleeReach(target)) {
                     MilimEntity.this.setSprinting(true);
                     MilimEntity.this.getNavigation().moveTo(target, 1.45);
                  } else {
                     MilimEntity.this.setSprinting(false);
                     MilimEntity.this.getNavigation().stop();
                  }
               } else if (MilimEntity.this.action() == 0) {
                  if (MilimEntity.this.tactics.busy()) {
                     MilimEntity.this.setSprinting(false);
                     MilimEntity.this.getNavigation().stop();
                  } else {
                     MilimEntity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
                     double distance = MilimEntity.this.distanceToSqr(target);
                     if (MilimEntity.this.burstPlanner.tick(target, MilimEntity.this.burstCooldown, MilimEntity.this.tactics.prefersPursuit())) {
                        MilimEntity.this.combatSpacing.reset();
                        MilimEntity.this.meleeCombo = 0;
                     } else if (!MilimEntity.this.combatSpacing.tick(target)) {
                        if (!MilimEntity.this.blockThrow.tryStart(target)) {
                           if (MilimEntity.this.inMeleeReach(target)) {
                              MilimEntity.this.startMelee(target);
                           } else if (MilimEntity.this.tactics.choose(target, distance)) {
                              MilimEntity.this.setSprinting(false);
                              MilimEntity.this.getNavigation().stop();
                           } else {
                              MilimEntity.this.meleeCombo = 0;
                              MilimEntity.this.setSprinting(true);
                              MilimEntity.this.getNavigation().moveTo(target, 1.45);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      public void stop() {
         MilimEntity.this.setSprinting(false);
         MilimEntity.this.getNavigation().stop();
      }
   }

   private final class ScriptedGoal extends Goal {
      ScriptedGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public boolean canUse() {
         return MilimEntity.this.action() != 0 && !MilimEntity.this.isCombatMelee() || MilimEntity.this.isDebugAnimation() || MilimEntity.this.isOrderedToSit();
      }

      public void start() {
         MilimEntity.this.getNavigation().stop();
      }
   }

   static class SyncedController extends AnimationController<MilimEntity> {
      private float partial;
      private long meleeStart = Long.MIN_VALUE;

      SyncedController(MilimEntity entity) {
         super(entity, "body", 4, entity::animate);
         this.setOverrideEasingTypeFunction(e -> this.getAnimationState() == State.TRANSITIONING ? EasingType.EASE_IN_OUT_SINE : null);
      }

      public void process(
         GeoModel<MilimEntity> model,
         AnimationState<MilimEntity> state,
         Map<String, GeoBone> bones,
         Map<String, BoneSnapshot> snapshots,
         double seekTime,
         boolean crashIfBoneMissing
      ) {
         this.partial = state.getPartialTick();
         if (((MilimEntity)this.animatable).isCombatMelee()) {
            long start = (Long)((MilimEntity)this.animatable).entityData.get(MilimEntity.ACTION_START);
            if (start != this.meleeStart) {
               this.forceAnimationReset();
               this.meleeStart = start;
            }
         }

         super.process(model, state, bones, snapshots, seekTime, crashIfBoneMissing);
      }

      protected double adjustTick(double tick) {
         double local = super.adjustTick(tick);
         return this.getAnimationState() != State.RUNNING && this.getAnimationState() != State.PAUSED
               || ((MilimEntity)this.animatable).action() == 0
               || ((MilimEntity)this.animatable).action() == 5
               || ((MilimEntity)this.animatable).action() == 15
               || ((MilimEntity)this.animatable).action() == 8
               || ((MilimEntity)this.animatable).action() == 13
                  && !((String)((MilimEntity)this.animatable).entityData.get(MilimEntity.DEBUG_ANIMATION)).equals("animation.heavy_punch")
            ? local
            : ((MilimEntity)this.animatable).animationTicks(this.partial);
      }
   }
}
