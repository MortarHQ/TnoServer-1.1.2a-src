package com.bobmowzie.mowziesmobs.server.entity.sculptor;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.model.tools.dynamics.GeckoDynamicChain;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleRotation;
import com.bobmowzie.mowziesmobs.client.sound.BossMusic;
import com.bobmowzie.mowziesmobs.client.sound.BossMusicPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.HurtAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.SimpleAnimationAbility;
import com.bobmowzie.mowziesmobs.server.advancement.AdvancementHandler;
import com.bobmowzie.mowziesmobs.server.advancement.SculptorChallengeTrigger;
import com.bobmowzie.mowziesmobs.server.ai.UseAbilityAI;
import com.bobmowzie.mowziesmobs.server.bossinfo.BossInfoSculptor;
import com.bobmowzie.mowziesmobs.server.bossinfo.MMBossInfoServer;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerData;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderProjectile;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderSculptor;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityGeomancyBase;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityPillar;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerSculptorTrade;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemSculptorStaff;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;

public class EntitySculptor extends MowzieGeckoEntity {
   public static int TEST_HEIGHT = 60;
   public static int TEST_RADIUS_BOTTOM = 6;
   public static int TEST_RADIUS = 12;
   public static int TEST_MAX_RADIUS_HEIGHT = 20;
   public static double TEST_RADIUS_FALLOFF = 5.0;
   private static final int HEAL_PAUSE = 75;
   public static float DEFENSE_HEALTH_THRESHOLD = 0.8F;
   public Vector3d calfRPos;
   public Vector3d calfLPos;
   public Vector3d thighRPos;
   public Vector3d thighLPos;
   public Vector3d skirtEndRPos;
   public Vector3d skirtEndLPos;
   public Vector3d skirtLocFrontRPos;
   public Vector3d skirtLocFrontLPos;
   public Vector3d skirtLocBackRPos;
   public Vector3d skirtLocBackLPos;
   public float disappearController = 0.0F;
   public Matrix4f frontClothRot;
   public static final AbilityType<EntitySculptor, HurtAbility<EntitySculptor>> HURT_ABILITY = new AbilityType(
      "sculptor_hurt", (type, entity) -> new HurtAbility<>(type, entity, RawAnimation.begin().thenPlay("hurt"), 16, 0)
   );
   public static final AbilityType<EntitySculptor, EntitySculptor.SculptorDieAbility> DIE_ABILITY = new AbilityType(
      "sculptor_die", EntitySculptor.SculptorDieAbility::new
   );
   public static final AbilityType<EntitySculptor, EntitySculptor.StartTestAbility> START_TEST = new AbilityType(
      "testStart", EntitySculptor.StartTestAbility::new
   );
   public static final AbilityType<EntitySculptor, EntitySculptor.FailTestAbility> FAIL_TEST = new AbilityType("testFail", EntitySculptor.FailTestAbility::new);
   public static final AbilityType<EntitySculptor, EntitySculptor.PassTestAbility> PASS_TEST = new AbilityType("testPass", EntitySculptor.PassTestAbility::new);
   public static final AbilityType<EntitySculptor, EntitySculptor.AttackAbility> ATTACK_ABILITY = new AbilityType("attack", EntitySculptor.AttackAbility::new);
   public static final AbilityType<EntitySculptor, EntitySculptor.GuardAbility> GUARD_ABILITY = new AbilityType("guard", EntitySculptor.GuardAbility::new);
   public static final AbilityType<EntitySculptor, EntitySculptor.DisappearAbility> DISAPPEAR_ABILITY = new AbilityType(
      "disappear", EntitySculptor.DisappearAbility::new
   );
   public static final AbilityType<EntitySculptor, SimpleAnimationAbility<EntitySculptor>> TALK_ABILITY = new AbilityType(
      "talk", (type, entity) -> new SimpleAnimationAbility<EntitySculptor>(type, entity, RawAnimation.begin().thenPlay("talk"), 27, true) {
         @Override
         public void start() {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_GREETING.get(), 1.0F, 1.0F);
            super.start();
         }
      }
   );
   public static final AbilityType<EntitySculptor, SimpleAnimationAbility<EntitySculptor>> IDLE_ABILITY = new AbilityType(
      "idle", (type, entity) -> new SimpleAnimationAbility<EntitySculptor>(type, entity, RawAnimation.begin().thenPlay("idle_variation_1"), 88, true) {
         @Override
         public void tickUsing() {
            super.tickUsing();
            if (this.getTicksInUse() == 10) {
               this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_HM.get(), 1.0F, 1.0F);
            }
         }
      }
   );
   public static final AbilityType<EntitySculptor, SimpleAnimationAbility<EntitySculptor>> LAUGH_ABILITY = new AbilityType(
      "laugh", (type, entity) -> new SimpleAnimationAbility<EntitySculptor>(type, entity, RawAnimation.begin().thenPlay("laugh"), 58, true) {
         @Override
         public void start() {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_LAUGH.get(), 1.0F, 1.0F);
            super.start();
         }
      }
   );
   private static final EntityDataAccessor<ItemStack> DESIRES = SynchedEntityData.defineId(EntitySculptor.class, EntityDataSerializers.ITEM_STACK);
   private static final EntityDataAccessor<Boolean> IS_TRADING = SynchedEntityData.defineId(EntitySculptor.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_FIGHTING = SynchedEntityData.defineId(EntitySculptor.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Optional<UUID>> TESTING_PLAYER = SynchedEntityData.defineId(
      EntitySculptor.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private Player customer;
   private Player testingPlayer;
   private Optional<Double> prevPlayerVelY;
   private Optional<Vec3> prevPlayerPosition;
   private int ticksAcceleratingUpward;
   private boolean testing;
   private int testTimePassed;
   private boolean isTestObstructed;
   private boolean isTestObstructedSoFar;
   private int obstructionTestHeight;
   private int timeUntilHeal = 0;
   private EntityPillar.EntityPillarSculptor pillar;
   public int numLivePaths = 0;
   private HurtByTargetGoal hurtByTargetAI;
   protected Projectile guardProjectileTarget;
   public List<EntityBoulderSculptor> boulders = new ArrayList<>();
   public ItemStack heldStaff;
   public GeckoDynamicChain beardChain;
   private boolean hasPingedBlockThisPass = false;
   private static RawAnimation HURT = RawAnimation.begin().thenPlay("hurt");
   private static RawAnimation TEST_OBSTRUCTED = RawAnimation.begin().thenLoop("test_obstructed");

   public EntitySculptor(EntityType<? extends MowzieEntity> type, Level world) {
      super(type, world);
      this.xpReward = 30;
      TEST_HEIGHT = (Integer)ConfigHandler.COMMON.MOBS.SCULPTOR.testHeight.get();
      this.heldStaff = new ItemStack((ItemLike)ItemHandler.SCULPTOR_STAFF.get());
      if (world.isClientSide) {
         this.beardChain = new GeckoDynamicChain(this);
         this.dynamicChains = new GeckoDynamicChain[]{this.beardChain};
      }
   }

   @Override
   public AbilityType getHurtAbility() {
      return HURT_ABILITY;
   }

   @Override
   public AbilityType getDeathAbility() {
      return DIE_ABILITY;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F, 0.06F) {
         public void start() {
            this.lookTime = this.adjustedTickDelay(80 + this.mob.getRandom().nextInt(80));
         }
      });
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, START_TEST, false));
      this.goalSelector.addGoal(1, new UseAbilityAI<>(this, DIE_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, HURT_ABILITY, false));
      this.goalSelector.addGoal(4, new EntitySculptor.RunTestGoal(this));
      this.goalSelector.addGoal(3, new EntitySculptor.CombatBehaviorGoal(this));
      this.goalSelector.addGoal(2, new EntitySculptor.GuardBehaviorGoal(this));
      this.hurtByTargetAI = new HurtByTargetGoal(this) {
         public void start() {
            super.start();
            if (this.mob instanceof EntitySculptor sculptor) {
               sculptor.setTestingPlayer(null);
               sculptor.setCustomer(null);
            }
         }

         public boolean canUse() {
            return super.canUse() && EntitySculptor.this.getHealthRatio() < EntitySculptor.DEFENSE_HEALTH_THRESHOLD;
         }

         public boolean canContinueToUse() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity == null) {
               livingentity = this.targetMob;
            }

            if (livingentity == null) {
               return false;
            }

            if (!this.mob.canAttack(livingentity)) {
               return false;
            }

            Team team = this.mob.getTeam();
            Team team1 = livingentity.getTeam();
            if (team != null && team1 == team) {
               return false;
            }

            double d0 = this.getFollowDistance();
            double yDistMax = 70.0;
            double yBase = this.mob.getY();
            EntitySculptor sculptor = (EntitySculptor)this.mob;
            if (sculptor.getPillar() != null) {
               yBase = sculptor.getPillar().getY();
            }

            if (!(this.mob.position().multiply(1.0, 0.0, 1.0).distanceToSqr(livingentity.position().multiply(1.0, 0.0, 1.0)) > d0 * d0)
               && !(livingentity.getY() < yBase - yDistMax)
               && !(livingentity.getY() > this.mob.getY() + yDistMax)) {
               this.mob.setTarget(livingentity);
               return true;
            } else {
               return false;
            }
         }
      };
      this.targetSelector.addGoal(3, this.hurtByTargetAI);
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(
         DESIRES,
         new ItemStack(
            (ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.tryParse((String)ConfigHandler.COMMON.MOBS.SCULPTOR.whichItem.get())),
            (Integer)ConfigHandler.COMMON.MOBS.SCULPTOR.howMany.get()
         )
      );
      builder.define(IS_TRADING, false);
      builder.define(IS_FIGHTING, false);
      builder.define(TESTING_PLAYER, Optional.empty());
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .add(Attributes.ATTACK_DAMAGE, 10.0)
         .add(Attributes.MAX_HEALTH, 140.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.FOLLOW_RANGE, 60.0);
   }

   @Override
   protected <E extends GeoEntity> void loopingAnimations(AnimationState<E> event) {
      event.getController().transitionLength(10);
      if (this.isTestObstructed && !this.isFighting()) {
         this.controller.setAnimation(TEST_OBSTRUCTED);
      } else {
         super.loopingAnimations(event);
      }
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)MMSounds.ENTITY_SCULPTOR_HURT.get();
   }

   public void playAmbientSound() {
      if (this.getActiveAbility() == null) {
         if (!this.isFighting()) {
            if (this.isTestObstructed) {
               if (this.random.nextFloat() > 0.5) {
                  this.playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_HM.get(), 1.0F, 1.0F);
               }
            } else if (this.random.nextFloat() < 0.1) {
               this.sendAbilityMessage(IDLE_ABILITY);
            } else if (this.getLookControl().isLookingAtTarget()) {
               if (this.isTrading() && this.random.nextFloat() > 0.5) {
                  return;
               }

               if (this.random.nextFloat() > 0.4) {
                  if (!this.isTesting()) {
                     this.sendAbilityMessage(TALK_ABILITY);
                  }
               } else {
                  this.sendAbilityMessage(LAUGH_ABILITY);
               }
            }
         }
      }
   }

   public float getVoicePitch() {
      return 1.0F;
   }

   public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
      return false;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public void push(double x, double y, double z) {
      super.push(0.0, y, 0.0);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public void setDesires(ItemStack stack) {
      this.getEntityData().set(DESIRES, stack);
   }

   public ItemStack getDesires() {
      return (ItemStack)this.getEntityData().get(DESIRES);
   }

   public boolean fulfillDesire(Slot input) {
      ItemStack desires = this.getDesires();
      if (canPayFor(input.getItem(), desires)) {
         input.remove(desires.getCount());
         return true;
      } else {
         return false;
      }
   }

   public boolean requiresCustomPersistence() {
      return true;
   }

   @Override
   public boolean hasBossBar() {
      return (Boolean)ConfigHandler.COMMON.MOBS.SCULPTOR.hasBossBar.get();
   }

   @Override
   public BossBarColor bossBarColor() {
      return BossBarColor.GREEN;
   }

   @Override
   protected MMBossInfoServer initBossInfo() {
      return new BossInfoSculptor(this);
   }

   @Override
   public void tick() {
      this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      super.tick();
      this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      if (this.testingPlayer == null && this.getTestingPlayerID().isPresent()) {
         this.testingPlayer = this.level().getPlayerByUUID(this.getTestingPlayerID().get());
         if (this.testingPlayer != null) {
            this.testing = true;
         }
      }

      if (this.testingPlayer != null) {
         this.getLookControl().setLookAt(this.testingPlayer);
      } else if (this.customer != null) {
         this.getLookControl().setLookAt(this.customer);
      }

      if (!this.testing && !this.isFighting()) {
         this.checkTestObstructedAtHeight(this.obstructionTestHeight + 1);
         int height = TEST_HEIGHT + 3;
         this.obstructionTestHeight = (this.obstructionTestHeight + 1) % height;
         if (this.obstructionTestHeight == 0) {
            this.isTestObstructed = this.isTestObstructedSoFar;
            this.isTestObstructedSoFar = false;
            this.hasPingedBlockThisPass = false;
         }
      }

      if (!this.level().isClientSide && this.getTarget() == null) {
         this.timeUntilHeal--;
         if ((Boolean)ConfigHandler.COMMON.MOBS.SCULPTOR.healsOutOfBattle.get() && this.timeUntilHeal <= 0) {
            this.heal(0.3F);
         }
      } else {
         this.timeUntilHeal = 75;
      }

      if (this.isTesting()) {
         this.testTimePassed++;
      } else {
         this.testTimePassed = 0;
      }

      if (this.level().isClientSide()) {
         this.beardChain.setSimulating(this.pillar == null || this.pillar.isRemoved() || !this.getPillar().isFalling() && !this.getPillar().isRising());
      }
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      this.timeUntilHeal = 75;
      return this.getActiveAbilityType() != PASS_TEST && this.getActiveAbilityType() != DISAPPEAR_ABILITY ? super.hurt(source, damage) : false;
   }

   public boolean isInvulnerable() {
      return super.isInvulnerable();
   }

   public boolean isPushable() {
      return false;
   }

   public boolean checkTestObstructed() {
      int height = TEST_HEIGHT + 3;
      this.hasPingedBlockThisPass = false;

      for (int i = 1; i < height; i++) {
         this.checkTestObstructedAtHeight(i);
         if (this.isTestObstructed) {
            return true;
         }
      }

      return false;
   }

   public boolean isTestObstructed() {
      return this.isTestObstructed;
   }

   private void checkTestObstructedAtHeight(int height) {
      BlockPos pos = this.blockPosition();
      int radius = TEST_RADIUS;

      for (int i = -radius; i < radius; i++) {
         for (int j = -radius; j < radius; j++) {
            Vec2 offset = new Vec2(i, j);
            BlockPos checkPos = pos.offset((int)offset.x, height, (int)offset.y);
            double testRadius = testRadiusAtHeight(height);
            if (offset.lengthSquared() < testRadius * testRadius) {
               BlockState checkState = this.level().getBlockState(checkPos);
               if (!checkState.isAir() && !checkState.is(Blocks.LIGHT)) {
                  this.isTestObstructed = true;
                  this.isTestObstructedSoFar = true;
                  if (this.level().isClientSide() && this.isPlayerInTestZone(MMCommon.PROXY.getLocalPlayer()) && this.blockHasExposedSide(checkPos)) {
                     MMCommon.PROXY.sculptorMarkBlock(this.getId(), checkPos);
                     ParticleRotation.FaceCamera faceCamera = new ParticleRotation.FaceCamera(0.0F);
                     AdvancedParticleBase.spawnAlwaysVisibleParticle(
                        this.level(),
                        ParticleHandler.RING2,
                        64.0,
                        checkPos.getX() + 0.5,
                        checkPos.getY() + 0.5,
                        checkPos.getZ() + 0.5,
                        0.0,
                        0.0,
                        0.0,
                        faceCamera,
                        3.5,
                        0.83F,
                        1.0,
                        0.39F,
                        1.0,
                        1.0,
                        20.0,
                        true,
                        false,
                        new ParticleComponent[]{
                           new ParticleComponent.PropertyControl(
                              ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.7F, 0.0F), false
                           ),
                           new ParticleComponent.PropertyControl(
                              ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(0.0F, 16.0F), false
                           )
                        }
                     );
                     if (!this.hasPingedBlockThisPass) {
                        AdvancedParticleBase.spawnAlwaysVisibleParticle(
                           this.level(),
                           ParticleHandler.ORB2,
                           64.0,
                           this.getX(),
                           this.getY() + this.getBbHeight() / 2.0,
                           this.getZ(),
                           0.0,
                           0.0,
                           0.0,
                           faceCamera,
                           6.0,
                           0.83F,
                           1.0,
                           0.39F,
                           0.7,
                           1.0,
                           30.0,
                           true,
                           false,
                           new ParticleComponent[]{
                              new ParticleComponent.PropertyControl(
                                 ParticleComponent.PropertyControl.EnumParticleProperty.POS_X,
                                 ParticleComponent.KeyTrack.startAndEnd((float)this.getX(), (float)(checkPos.getX() + 0.5)),
                                 false
                              ),
                              new ParticleComponent.PropertyControl(
                                 ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y,
                                 ParticleComponent.KeyTrack.startAndEnd((float)this.getY() + this.getBbHeight() / 2.0F, (float)(checkPos.getY() + 0.5)),
                                 false
                              ),
                              new ParticleComponent.PropertyControl(
                                 ParticleComponent.PropertyControl.EnumParticleProperty.POS_Z,
                                 ParticleComponent.KeyTrack.startAndEnd((float)this.getZ(), (float)(checkPos.getZ() + 0.5)),
                                 false
                              )
                           }
                        );
                        this.hasPingedBlockThisPass = true;
                     }
                  }
               }
            }
         }
      }
   }

   private boolean blockHasExposedSide(BlockPos pos) {
      return !this.level().getBlockState(pos.north()).canOcclude()
         || !this.level().getBlockState(pos.south()).canOcclude()
         || !this.level().getBlockState(pos.east()).canOcclude()
         || !this.level().getBlockState(pos.west()).canOcclude()
         || !this.level().getBlockState(pos.above()).canOcclude()
         || !this.level().getBlockState(pos.below()).canOcclude();
   }

   private void checkIfPlayerCheats() {
      if (this.testingPlayer != null) {
         if (this.isTesting() && !this.testingPlayer.isCreative()) {
            if (this.testingPlayer != null
               && this.testingPlayer.position().multiply(1.0, 0.0, 1.0).distanceTo(this.position().multiply(1.0, 0.0, 1.0)) > TEST_RADIUS + 4) {
               this.playerCheated();
            } else if (this.testingPlayer != null && this.pillar != null && this.testingPlayer.getY() < this.pillar.getY() - 10.0) {
               this.playerCheated();
            } else if (this.testingPlayer != null && this.testingPlayer.getAbilities().flying) {
               this.playerCheated();
            } else if (this.testingPlayer.isInWater() && !this.testingPlayer.onGround()) {
               this.playerCheated();
            } else {
               if (this.testingPlayer != null && !this.testingPlayer.onGround()) {
                  double playerVelY = this.testingPlayer.getDeltaMovement().y();
                  if (this.prevPlayerVelY != null && this.prevPlayerVelY.isPresent()) {
                     double acceleration = playerVelY - this.prevPlayerVelY.get();
                     if (acceleration >= 0.0) {
                        this.ticksAcceleratingUpward++;
                     } else if (this.ticksAcceleratingUpward > 0) {
                        this.ticksAcceleratingUpward--;
                     }

                     if (this.ticksAcceleratingUpward > 5) {
                        this.playerCheated();
                        return;
                     }
                  }

                  this.prevPlayerVelY = Optional.of(playerVelY);
               } else {
                  this.ticksAcceleratingUpward = 0;
                  this.prevPlayerVelY = Optional.empty();
               }

               if (this.testingPlayer != null) {
                  Vec3 currPosition = this.testingPlayer.position();
                  if (this.prevPlayerPosition != null && this.prevPlayerPosition.isPresent()) {
                     Vec3 predictedPosition = this.prevPlayerPosition.get().add(this.testingPlayer.getDeltaMovement());
                     if (currPosition.distanceTo(predictedPosition) > 3.0) {
                        this.playerCheated();
                        return;
                     }
                  }

                  this.prevPlayerPosition = Optional.of(this.testingPlayer.position());
               }
            }
         }
      }
   }

   public void playerCheated() {
      if (this.isTesting() && this.testingPlayer != null && this.getActiveAbilityType() != FAIL_TEST && this.getActiveAbilityType() != PASS_TEST) {
         this.sendAbilityMessage(FAIL_TEST);
      }
   }

   public float playerProgress() {
      return this.getPillar() != null && this.getTestingPlayer() != null
         ? Mth.clamp((float)(this.getTestingPlayer().getY() - this.getPillar().getY()) / TEST_HEIGHT, 0.0F, 1.0F)
         : 0.0F;
   }

   public static double testRadiusAtHeight(double height) {
      return TEST_RADIUS_BOTTOM + Math.pow(Math.min(height / TEST_MAX_RADIUS_HEIGHT, 1.0), TEST_RADIUS_FALLOFF) * (TEST_RADIUS - TEST_RADIUS_BOTTOM);
   }

   public void setTrading(boolean trading) {
      this.entityData.set(IS_TRADING, trading);
   }

   public boolean isTrading() {
      return (Boolean)this.entityData.get(IS_TRADING);
   }

   public void setFighting(boolean fighting) {
      this.entityData.set(IS_FIGHTING, fighting);
   }

   public boolean isFighting() {
      return (Boolean)this.entityData.get(IS_FIGHTING);
   }

   public Player getCustomer() {
      return this.customer;
   }

   public void setCustomer(Player customer) {
      this.setTrading(customer != null);
      this.customer = customer;
   }

   public boolean isTesting() {
      return this.getTestingPlayerID().isPresent();
   }

   public boolean isTestPassed() {
      return this.getActiveAbilityType() == PASS_TEST;
   }

   public boolean isTestFailed() {
      return this.getActiveAbilityType() == FAIL_TEST;
   }

   public boolean isTestOver() {
      return this.isTestPassed() || this.isTestFailed();
   }

   public Optional<UUID> getTestingPlayerID() {
      return (Optional<UUID>)this.getEntityData().get(TESTING_PLAYER);
   }

   public void setTestingPlayerID(UUID playerID) {
      if (playerID == null) {
         this.getEntityData().set(TESTING_PLAYER, Optional.empty());
      } else {
         this.getEntityData().set(TESTING_PLAYER, Optional.of(playerID));
      }
   }

   public void setTestingPlayer(Player testingPlayer) {
      this.testingPlayer = testingPlayer;
      this.setTestingPlayerID(testingPlayer == null ? null : testingPlayer.getUUID());
      if (testingPlayer != null) {
         PlayerData data = DataHandler.getData(testingPlayer, DataHandler.PLAYER_DATA);
         data.setTestingSculptor(this);
      }
   }

   public Player getTestingPlayer() {
      return this.testingPlayer;
   }

   public void openGUI(Player playerEntity) {
      this.setCustomer(playerEntity);
      MMCommon.PROXY.setReferencedMob(this);
      if (!this.level().isClientSide && this.getTarget() == null && this.isAlive()) {
         playerEntity.openMenu(new MenuProvider() {
            public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
               return new ContainerSculptorTrade(id, EntitySculptor.this, playerInventory);
            }

            public Component getDisplayName() {
               return EntitySculptor.this.getDisplayName();
            }
         });
      }
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (this.isTesting() && this.getPillar() != null && !this.getPillar().isRising()) {
         if (player == this.testingPlayer && this.getActiveAbilityType() != FAIL_TEST && player.distanceToSqr(this) <= 20.0) {
            this.sendAbilityMessage(PASS_TEST);
            if (player instanceof ServerPlayer serverPlayer) {
               ((SculptorChallengeTrigger)AdvancementHandler.SCULPTOR_CHALLENGE_TRIGGER.get()).trigger(serverPlayer);
            }

            return InteractionResult.SUCCESS;
         }
      } else if (this.canTradeWith(player) && this.getTarget() == null && this.isAlive()) {
         this.openGUI(player);
         this.sendAbilityMessage(TALK_ABILITY);
         return InteractionResult.SUCCESS;
      }

      return InteractionResult.PASS;
   }

   public boolean canTradeWith(Player player) {
      return !this.isTrading() && !(this.getHealth() <= 0.0F) && !this.testing;
   }

   public boolean doesItemSatisfyDesire(ItemStack stack) {
      return canPayFor(stack, this.getDesires());
   }

   private static boolean canPayFor(ItemStack stack, ItemStack worth) {
      return stack.getItem() == worth.getItem() && stack.getCount() >= worth.getCount();
   }

   public EntityPillar.EntityPillarSculptor getPillar() {
      return this.pillar;
   }

   public void setPillar(EntityPillar.EntityPillarSculptor pillar) {
      this.pillar = pillar;
   }

   @Override
   public void registerControllers(ControllerRegistrar controllers) {
      super.registerControllers(controllers);
      this.controller
         .setSoundKeyframeHandler(
            state -> {
               String sound = state.getKeyframeData().getSound();
               if (sound.equals("make_gauntlet_effects")) {
                  this.level()
                     .playSound(
                        MMCommon.PROXY.getLocalPlayer(),
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        (SoundEvent)MMSounds.ENTITY_SCULPTOR_MAKE_GAUNTLET_EFFECTS.get(),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                     );
               } else if (sound.equals("make_gauntlet_piece")) {
                  this.level()
                     .playSound(
                        MMCommon.PROXY.getLocalPlayer(),
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        (SoundEvent)MMSounds.ENTITY_SCULPTOR_MAKE_GAUNTLET_PIECE.get(),
                        SoundSource.NEUTRAL,
                        1.0F,
                        0.7F + 0.6F * this.random.nextFloat()
                     );
               } else if (sound.equals("clap1")) {
                  this.level()
                     .playSound(
                        MMCommon.PROXY.getLocalPlayer(),
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        (SoundEvent)MMSounds.ENTITY_SCULPTOR_CLAP.get(),
                        SoundSource.NEUTRAL,
                        1.0F,
                        0.7F + 0.6F * this.random.nextFloat()
                     );
               }
            }
         );
   }

   @Override
   public AbilityType<?, ?>[] getAbilities() {
      return new AbilityType[]{
         START_TEST,
         FAIL_TEST,
         PASS_TEST,
         HURT_ABILITY,
         DIE_ABILITY,
         ATTACK_ABILITY,
         GUARD_ABILITY,
         DISAPPEAR_ABILITY,
         TALK_ABILITY,
         IDLE_ABILITY,
         LAUGH_ABILITY
      };
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      if (this.testingPlayer != null && this.getTestingPlayerID().isPresent()) {
         compound.putUUID("TestingPlayer", this.getTestingPlayerID().get());
         compound.putInt("NumLivePaths", this.numLivePaths);
      }

      compound.putInt("TestTimePassed", this.testTimePassed);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("TestingPlayer")) {
         this.testing = true;
         this.setTestingPlayerID(compound.getUUID("TestingPlayer"));
         this.numLivePaths = compound.getInt("NumLivePaths");
      }

      this.testTimePassed = compound.getInt("TestTimePassed");
   }

   public boolean isPlayerInTestZone(Player player) {
      if (player == null) {
         return false;
      }

      double yDistMax = 12.0;
      double yBase = this.getY();
      if (this.getPillar() != null) {
         yBase = this.getPillar().getY();
      }

      return player.position().multiply(1.0, 0.0, 1.0).distanceToSqr(this.position().multiply(1.0, 0.0, 1.0)) < TEST_RADIUS * TEST_RADIUS + 9
         && player.getY() > yBase - yDistMax
         && player.getY() < yBase + TEST_HEIGHT + yDistMax;
   }

   @Override
   protected int getDeathDuration() {
      AbilityType deathAbilityType = this.getDeathAbility();
      Ability<?> deathAbility = this.getAbility(deathAbilityType);
      if (deathAbility == null || !deathAbility.isUsing()) {
         return 9;
      } else {
         return deathAbility.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY
            ? deathAbility.getTicksInUse() - deathAbility.getTicksInSection() + 84
            : 94;
      }
   }

   @NotNull
   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LootTableHandler.SCULPTOR;
   }

   public int getTestTimePassed() {
      return this.testTimePassed;
   }

   public int getMaxTestTime() {
      return (Integer)ConfigHandler.COMMON.MOBS.SCULPTOR.testTimeLimit.get() * 20;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.SCULPTOR.combatConfig;
   }

   @Override
   public boolean hasBossMusic() {
      return true;
   }

   @Override
   public BossMusic<?> getBossMusic() {
      return BossMusicPlayer.SCULPTOR_MUSIC;
   }

   @Override
   protected boolean canPlayMusic() {
      return this.isTesting() && !this.isTestFailed() ? true : super.canPlayMusic();
   }

   @Override
   public boolean canPlayerHearMusic(Player player) {
      return player != null && (this.canAttack(player) || this.isTesting()) && this.distanceTo(player) < 2500.0F;
   }

   public boolean canBeLeashed() {
      return false;
   }

   public static class AttackAbility extends Ability<EntitySculptor> {
      private EntityBoulderSculptor boulderToFire;
      private Vec3 prevTargetPos;
      private static final int STARTUP_TIME = 5;
      private EntitySculptor.AttackAbility.WhichHand whichHand = EntitySculptor.AttackAbility.WhichHand.NONE;
      private static RawAnimation ATTACK_START = RawAnimation.begin().thenPlayAndHold("attack_start");
      private static RawAnimation ATTACK_LEFT = RawAnimation.begin().thenPlayAndHold("attack_left");
      private static RawAnimation ATTACK_RIGHT = RawAnimation.begin().thenPlayAndHold("attack_right");
      private static RawAnimation ATTACK_LEFT_END = RawAnimation.begin().thenPlayAndHold("attack_left_end");
      private static RawAnimation ATTACK_RIGHT_END = RawAnimation.begin().thenPlayAndHold("attack_right_end");

      public AttackAbility(AbilityType abilityType, EntitySculptor user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 5),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, 7),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 11)
            }
         );
      }

      @Override
      public void start() {
         super.start();
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.getUser().getTarget();
         if (target == null) {
            return false;
         }

         Collections.shuffle(this.getUser().boulders);

         for (EntityBoulderSculptor boulder : this.getUser().boulders) {
            if (boulder != null
               && !boulder.isTravelling()
               && !boulder.isRemoved()
               && boulder.isFinishedRising()
               && boulder.isActive()
               && !(boulder.position().add(0.0, boulder.getBbHeight(), 0.0).distanceToSqr(target.position()) < 9.0)) {
               Vec3 vecBetweenSculptorAndTarget = this.getUser().getTarget().position().subtract(this.getUser().position()).normalize();
               Vec3 vecBetweenSculptorAndBoulder = boulder.position().subtract(this.getUser().position()).normalize();
               if (vecBetweenSculptorAndBoulder.dot(vecBetweenSculptorAndTarget) > 0.5) {
                  this.boulderToFire = boulder;
                  break;
               }
            }
         }

         if (this.boulderToFire == null) {
            return false;
         }

         this.prevTargetPos = target.position().add(0.0, target.getBbHeight() / 2.0, 0.0);
         return super.canUse();
      }

      @Override
      public boolean canCancelSelf() {
         return true;
      }

      @Override
      public boolean canCancelActiveAbility() {
         return this.getUser().getActiveAbilityType() == EntitySculptor.ATTACK_ABILITY;
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP
            && this.getTicksInSection() == 4
            && this.getUser().random.nextFloat() > 0.66) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_ATTACK.get(), 2.0F, 0.95F + this.getUser().random.nextFloat() * 0.1F);
         }
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            if (this.whichHand == EntitySculptor.AttackAbility.WhichHand.NONE) {
               this.playAnimation(ATTACK_START);
               this.whichHand = EntitySculptor.AttackAbility.WhichHand.LEFT;
            } else if (this.whichHand == EntitySculptor.AttackAbility.WhichHand.LEFT) {
               this.playAnimation(ATTACK_RIGHT);
               this.whichHand = EntitySculptor.AttackAbility.WhichHand.RIGHT;
            } else {
               this.playAnimation(ATTACK_LEFT);
               this.whichHand = EntitySculptor.AttackAbility.WhichHand.LEFT;
            }
         }

         if (!this.getUser().level().isClientSide() && this.getUser().getTarget() != null) {
            LivingEntity target = this.getUser().getTarget();
            if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
               shootBoulderAtTarget(target, this.prevTargetPos, this.boulderToFire, 0.93F);
               this.boulderToFire = null;
            }
         }

         if (section.sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
            if (!this.getUser().level().isClientSide() && this.getUser().getTarget() != null && this.canUse()) {
               AbilityHandler.INSTANCE.sendJumpToSectionMessage((EntitySculptor & LivingEntity)this.getUser(), this.getAbilityType(), 0);
            } else if (this.whichHand == EntitySculptor.AttackAbility.WhichHand.LEFT) {
               this.playAnimation(ATTACK_LEFT_END);
            } else {
               this.playAnimation(ATTACK_RIGHT_END);
            }
         }
      }

      @Override
      public void end() {
         super.end();
         this.whichHand = EntitySculptor.AttackAbility.WhichHand.NONE;
      }

      public static void shootBoulderAtTarget(LivingEntity target, Vec3 prevTargetPos, EntityBoulderProjectile boulderToFire, float timeScale) {
         if (boulderToFire != null) {
            Vec3 targetPos = target.position().add(0.0, target.getBbHeight() / 2.0, 0.0);
            double timeToReach = boulderToFire.position().subtract(targetPos).length() / boulderToFire.getSpeed();
            Vec3 targetMovement = targetPos.subtract(prevTargetPos).scale(timeToReach * timeScale * 1.0 / 4.0);
            targetMovement = targetMovement.multiply(1.0, 0.0, 1.0);
            Vec3 futureTargetPos = targetPos.add(targetMovement);
            Vec3 projectileMid = boulderToFire.position().add(0.0, boulderToFire.getBbHeight() / 2.0, 0.0);
            Vec3 shootVec = futureTargetPos.subtract(projectileMid).normalize();
            boulderToFire.shoot(shootVec.scale(boulderToFire.getSpeed()));
         }
      }

      private enum WhichHand {
         NONE,
         LEFT,
         RIGHT;
      }
   }

   public static class CombatBehaviorGoal extends Goal {
      private final EntitySculptor sculptor;

      public CombatBehaviorGoal(EntitySculptor sculptor) {
         this.sculptor = sculptor;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         LivingEntity target = this.sculptor.getTarget();
         return target != null && target.isAlive();
      }

      public boolean canContinueToUse() {
         return super.canContinueToUse()
            || this.sculptor.getActiveAbilityType() == EntitySculptor.ATTACK_ABILITY
            || this.sculptor.getActiveAbilityType() == EntitySculptor.GUARD_ABILITY;
      }

      public void start() {
         super.start();
         this.sculptor.setFighting(true);
         if (this.sculptor.getPillar() != null && !this.sculptor.getPillar().isRemoved() && this.sculptor.getPillar().getHeight() < EntitySculptor.TEST_HEIGHT) {
            this.sculptor.getPillar().startRising();
         } else {
            this.sculptor.sendAbilityMessage(EntitySculptor.START_TEST);
         }
      }

      public void tick() {
         super.tick();
         if (this.sculptor.getActiveAbility() == null) {
            AbilityHandler.INSTANCE.sendAbilityMessage(this.sculptor, EntitySculptor.ATTACK_ABILITY);
         }

         if (this.sculptor.boulders.isEmpty() && this.sculptor.getActiveAbilityType() != EntitySculptor.START_TEST) {
            EntitySculptor.StartTestAbility.placeStartingBoulders(this.sculptor);
         }

         if (this.sculptor.getTarget() != null && this.sculptor.getActiveAbilityType() != EntitySculptor.GUARD_ABILITY) {
            this.sculptor.getLookControl().setLookAt(this.sculptor.getTarget(), 30.0F, 30.0F);
         }
      }

      public void stop() {
         super.stop();
         this.sculptor.setFighting(false);
         this.sculptor.sendAbilityMessage(EntitySculptor.FAIL_TEST);
      }
   }

   public static class DisappearAbility extends SimpleAnimationAbility<EntitySculptor> {
      private static final RawAnimation DISAPPEAR = RawAnimation.begin().thenPlay("disappear");

      public DisappearAbility(AbilityType abilityType, EntitySculptor user) {
         super(abilityType, user, DISAPPEAR, 80);
      }

      @Override
      public void start() {
         super.start();
         if (this.getUser().level() instanceof ServerLevel) {
            ItemStack heldStaff = this.getUser().heldStaff;
            ItemSculptorStaff staffItem = (ItemSculptorStaff)heldStaff.getItem();
            staffItem.triggerAnim(this.getUser(), GeoItem.getOrAssignId(heldStaff, (ServerLevel)this.getUser().level()), "controller", "disappear");
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         int start = 1;
         int end = 70;
         if (this.getLevel().isClientSide() && this.getTicksInUse() > start && this.getTicksInUse() < end) {
            float a = ((float)this.getTicksInUse() - start) / (end - start);
            float spawnRate = 15.0F * (float)Math.pow(2.0, -(Math.pow(a - 0.5, 2.0) / 0.05));
            Vec3 windForce = new Vec3(1.0, 0.0, 0.0).yRot((float)Math.toRadians(-this.getUser().yBodyRot));
            windForce = windForce.add(0.0, 0.5, 0.0).normalize().scale(0.01);

            for (int i = 0; i < (int)spawnRate; i++) {
               AABB bounds = this.getUser().getBoundingBox();
               float x = (float)(this.getUser().getX() + this.getUser().random.nextGaussian() * (bounds.maxX - bounds.minX) / 3.0);
               float y = (float)(
                  this.getUser().getY() + this.getUser().random.nextGaussian() * (bounds.maxY - bounds.minY) / 5.0 + this.getUser().getBbHeight() / 2.0
               );
               float z = (float)(this.getUser().getZ() + this.getUser().random.nextGaussian() * (bounds.maxZ - bounds.minZ) / 3.0);
               double colorVariation = this.getUser().random.nextDouble() * 10.0;
               double yaw = this.getUser().random.nextDouble() * Math.PI / 2.0;
               double pitch = this.getUser().random.nextDouble() * Math.PI / 2.0;
               double roll = this.getUser().random.nextDouble() * Math.PI / 2.0;
               AdvancedParticleBase.spawnParticle(
                  this.getUser().level(),
                  ParticleHandler.LEAF,
                  x,
                  y,
                  z,
                  0.0,
                  0.0,
                  0.0,
                  false,
                  yaw,
                  pitch,
                  roll,
                  0.0,
                  1.0,
                  (247.0 + colorVariation) / 256.0,
                  (185.0 + colorVariation) / 256.0,
                  (220.0 + colorVariation) / 256.0,
                  1.0,
                  0.9,
                  35.0F + this.getUser().random.nextFloat() * 20.0F,
                  false,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                        new ParticleComponent.KeyTrack(new float[]{0.0F, 1.0F, 0.0F}, new float[]{0.0F, 0.5F, 1.0F}),
                        false
                     ),
                     new ParticleComponent.CurlNoise(0.01F, 4.0F),
                     new ParticleComponent.ForceOverTime(windForce),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.YAW, new ParticleComponent.Constant(0.04F), true
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.PITCH, new ParticleComponent.Constant(0.025F), true
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.ROLL, new ParticleComponent.Constant(0.01F), true
                     )
                  }
               );
            }

            for (int i = 0; i < (int)spawnRate / 2; i++) {
               AABB bounds = this.getUser().getBoundingBox();
               float x = (float)(this.getUser().getX() + this.getUser().random.nextGaussian() * (bounds.maxX - bounds.minX) / 3.0);
               float y = (float)(
                  this.getUser().getY() + this.getUser().random.nextGaussian() * (bounds.maxY - bounds.minY) / 5.0 + this.getUser().getBbHeight() / 2.0
               );
               float z = (float)(this.getUser().getZ() + this.getUser().random.nextGaussian() * (bounds.maxZ - bounds.minZ) / 3.0);
               AdvancedParticleBase.spawnParticle(
                  this.getUser().level(),
                  ParticleHandler.PIXEL,
                  x,
                  y,
                  z,
                  0.0,
                  0.0,
                  0.0,
                  true,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  1.0,
                  0.99609375,
                  0.96875,
                  0.578125,
                  1.0,
                  0.9,
                  35.0F + this.getUser().random.nextFloat() * 20.0F,
                  true,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                        new ParticleComponent.KeyTrack(new float[]{0.0F, 2.0F, 0.0F}, new float[]{0.0F, 0.5F, 1.0F}),
                        false
                     ),
                     new ParticleComponent.CurlNoise(0.01F, 4.0F),
                     new ParticleComponent.ForceOverTime(windForce)
                  }
               );
            }
         }

         if (this.getTicksInUse() == 8) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_DISAPPEAR_EFFECTS.get());
         }

         if (this.getTicksInUse() == 15) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_DISAPPEAR.get());
         }
      }

      @Override
      public void end() {
         super.end();
         this.getUser().remove(RemovalReason.KILLED);
      }

      @Override
      public boolean canCancelActiveAbility() {
         return false;
      }
   }

   public abstract static class EndTestAbility extends Ability<EntitySculptor> {
      public EndTestAbility(AbilityType<EntitySculptor, ? extends EntitySculptor.EndTestAbility> abilityType, EntitySculptor user, int recoveryDuration) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, recoveryDuration)
            }
         );
      }

      @Override
      public boolean tryAbility() {
         return true;
      }

      @Override
      public void start() {
         super.start();
         this.playStartingAnimation();
         if (this.getUser().pillar != null) {
            this.getUser().pillar.startFalling();
         }

         this.getUser().testing = false;
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            if (!this.getUser().level().isClientSide() && this.getUser().pillar != null && this.getUser().pillar.getHeight() > 1.0F) {
               this.getUser().setPos(this.getUser().pillar.position().add(0.0, this.getUser().pillar.getHeight() + 0.2, 0.0));
            }

            if (this.getUser().pillar == null || this.getUser().pillar.isRemoved()) {
               AbilityHandler.INSTANCE.sendJumpToSectionMessage((EntitySculptor & LivingEntity)this.getUser(), this.getAbilityType(), 1);
            }
         }
      }

      @Override
      protected boolean canContinueUsing() {
         return super.canContinueUsing() && this.getUser().getTarget() == null;
      }

      @Override
      public void end() {
         super.end();
         if (this.getUser() != null) {
            this.getUser().pillar = null;
            this.getUser().setTestingPlayer(null);
            this.getUser().testing = false;
            this.getUser().numLivePaths = 0;
         }
      }

      @Override
      public boolean canCancelActiveAbility() {
         return true;
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
            this.playFinishingAnimation();
         }
      }

      protected abstract void playFinishingAnimation();

      protected abstract void playStartingAnimation();
   }

   public static class FailTestAbility extends EntitySculptor.EndTestAbility {
      private static final RawAnimation TEST_FAIL_START = RawAnimation.begin().then("test_fail_start", LoopType.HOLD_ON_LAST_FRAME);
      private static RawAnimation TEST_FAIL_END = RawAnimation.begin().thenLoop("test_fail_end");

      public FailTestAbility(AbilityType<EntitySculptor, ? extends EntitySculptor.EndTestAbility> abilityType, EntitySculptor user) {
         super(abilityType, user, 30);
      }

      @Override
      protected void playStartingAnimation() {
         this.playAnimation(TEST_FAIL_START);
      }

      @Override
      protected void playFinishingAnimation() {
         this.playAnimation(TEST_FAIL_END);
         this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_DISAPPOINT.get());
      }
   }

   public static class GuardAbility extends Ability<EntitySculptor> {
      private EntityBoulderProjectile boulder;
      private UUID boulderID;
      private Entity target;
      private Vec3 prevTargetPos;
      private static final RawAnimation BLOCK = RawAnimation.begin().thenPlay("guard");

      public GuardAbility(AbilityType abilityType, EntitySculptor user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 11),
               new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 17)
            }
         );
      }

      @Override
      public void start() {
         super.start();
         this.playAnimation(BLOCK);
         if (!this.getUser().level().isClientSide()) {
            this.target = this.getUser().guardProjectileTarget.getOwner();
            if (this.target != null) {
               this.prevTargetPos = this.target.position().add(0.0, this.target.getBbHeight() / 2.0, 0.0);
            }
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getBoulder() != null && this.target != null) {
            this.getUser().getLookControl().setLookAt(this.target, 60.0F, 60.0F);
         }
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (!this.getUser().level().isClientSide()) {
            if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
               this.boulder = new EntityBoulderProjectile(
                  (EntityType<? extends EntityBoulderProjectile>)EntityHandler.BOULDER_PROJECTILE.get(),
                  this.getUser().level(),
                  this.getUser(),
                  Blocks.STONE.defaultBlockState(),
                  BlockPos.ZERO,
                  EntityGeomancyBase.GeomancyTier.SMALL
               );
               Vec3 betweenSculptorAndProjectile = this.getUser().guardProjectileTarget.position().subtract(this.getUser().position()).normalize();
               this.boulder
                  .setPos(
                     this.getUser()
                        .position()
                        .add(0.0, this.getUser().getBbHeight() / 2.0F, 0.0)
                        .add(betweenSculptorAndProjectile.scale(2.0).subtract(0.0, this.boulder.getBbHeight() / 2.0F, 0.0))
                  );
               this.boulder.activate();
               this.getUser().level().addFreshEntity(this.boulder);
               this.boulderID = this.boulder.getUUID();
            } else if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.getBoulder() != null) {
               if (this.target != null && !this.target.isRemoved() && this.target instanceof LivingEntity) {
                  EntitySculptor.AttackAbility.shootBoulderAtTarget((LivingEntity)this.target, this.prevTargetPos, this.getBoulder(), 0.45F);
                  this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_ATTACK.get(), 2.0F, 0.95F + this.getUser().random.nextFloat() * 0.1F);
               } else {
                  this.getBoulder().explode();
               }
            }
         }
      }

      @Override
      public CompoundTag writeNBT() {
         CompoundTag nbt = super.writeNBT();
         if (this.boulderID != null) {
            nbt.putUUID("boulder_guard", this.boulderID);
         }

         return nbt;
      }

      @Override
      public void readNBT(Tag nbt) {
         super.readNBT(nbt);
         CompoundTag compound = (CompoundTag)nbt;
         if (compound.hasUUID("boulder_guard")) {
            this.boulderID = compound.getUUID("boulder_guard");
         }
      }

      public EntityBoulderProjectile getBoulder() {
         if (this.boulder != null && !this.boulder.isRemoved()) {
            return this.boulder;
         }

         if (this.boulderID != null && this.getUser().level() instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.getUser().level()).getEntity(this.boulderID);
            if (entity instanceof EntityBoulderProjectile) {
               this.boulder = (EntityBoulderProjectile)entity;
            }

            return this.boulder;
         } else {
            return null;
         }
      }

      @Override
      public boolean canCancelActiveAbility() {
         return this.getUser().getActiveAbilityType() == EntitySculptor.ATTACK_ABILITY || this.getUser().getActiveAbilityType() == EntitySculptor.HURT_ABILITY;
      }

      @Override
      public void end() {
         super.end();
         if (this.getBoulder() != null) {
            this.getBoulder().explode();
         }
      }
   }

   public static class GuardBehaviorGoal extends Goal {
      private final EntitySculptor sculptor;
      protected final Predicate<Projectile> guardTargetSelector;
      private static final float GUARD_DISTANCE = 8.0F;

      public GuardBehaviorGoal(EntitySculptor sculptor) {
         this.sculptor = sculptor;
         this.guardTargetSelector = target -> {
            Vec3 aActualMotion = new Vec3(target.getX() - target.xo, target.getY() - target.yo, target.getZ() - target.zo);
            if (!(aActualMotion.length() < 0.1) && target.tickCount >= 0) {
               if (aActualMotion.length() * 9.0 < target.position().distanceTo(sculptor.position())) {
                  return false;
               }

               if (!sculptor.getSensing().hasLineOfSight(target)) {
                  return false;
               }

               float dot = (float)target.getDeltaMovement().normalize().dot(sculptor.position().subtract(target.position()).normalize());
               return !(dot < 0.8);
            } else {
               return false;
            }
         };
      }

      public boolean canUse() {
         this.sculptor.guardProjectileTarget = this.getMostMovingTowardsMeEntity(
            Projectile.class, this.guardTargetSelector, this.sculptor, this.sculptor.getBoundingBox().inflate(8.0, 8.0, 8.0)
         );
         return this.sculptor.guardProjectileTarget != null;
      }

      public void start() {
         super.start();
         if (this.sculptor.guardProjectileTarget != null) {
            AbilityHandler.INSTANCE.sendAbilityMessage(this.sculptor, EntitySculptor.GUARD_ABILITY);
         }
      }

      public boolean canContinueToUse() {
         return this.sculptor.getActiveAbilityType() == EntitySculptor.GUARD_ABILITY;
      }

      public void stop() {
         super.stop();
         AbilityHandler.INSTANCE.sendInterruptAbilityMessage(this.sculptor, EntitySculptor.GUARD_ABILITY);
      }

      @Nullable
      private <T extends Projectile> T getMostMovingTowardsMeEntity(
         Class<? extends T> entityClazz, Predicate<? super T> predicate, LivingEntity entity, AABB p_225318_10_
      ) {
         return this.getMostMovingTowardsMeEntityFromList(entity.level().getEntitiesOfClass(entityClazz, p_225318_10_, predicate), entity);
      }

      private <T extends Projectile> T getMostMovingTowardsMeEntityFromList(List<? extends T> entities, LivingEntity target) {
         double d0 = -2.0;
         T t = null;

         for (T t1 : entities) {
            double d1 = t1.getDeltaMovement().normalize().dot(target.position().subtract(t1.position()).normalize());
            if (d1 > d0) {
               d0 = d1;
               t = t1;
            }
         }

         return t;
      }
   }

   public static class PassTestAbility extends EntitySculptor.EndTestAbility {
      private static final RawAnimation TEST_PASS_START = RawAnimation.begin().then("test_pass_start", LoopType.HOLD_ON_LAST_FRAME);
      private static RawAnimation TEST_PASS_END = RawAnimation.begin().thenLoop("test_pass_end");

      public PassTestAbility(AbilityType<EntitySculptor, EntitySculptor.PassTestAbility> abilityType, EntitySculptor user) {
         super(abilityType, user, 150);
      }

      @Override
      public void start() {
         this.getUser().setInvulnerable(true);
         if (this.getUser().testingPlayer != null) {
            List<EntityBoulderSculptor> platforms = this.getUser()
               .level()
               .getEntitiesOfClass(EntityBoulderSculptor.class, this.getUser().testingPlayer.getBoundingBox().expandTowards(0.0, -6.0, 0.0));
            if (!platforms.isEmpty()) {
               EntityBoulderSculptor platformBelowPlayer = platforms.get(0);
               platformBelowPlayer.descend();
            }

            this.getUser()
               .testingPlayer
               .addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, (int)(EntitySculptor.TEST_HEIGHT / 5.0F) * 20, 0, false, false));
         }

         super.start();
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.getTicksInSection() == 15) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_CONGRATS.get());
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY && this.getTicksInSection() == 40) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_MAKE_GAUNTLET.get());
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY
            && this.getTicksInSection() == 134
            && !this.getUser().level().isClientSide()) {
            this.dropFromLootTable();
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.getTicksInSection() % 9 == 0) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_CLAP.get(), 1.0F, 0.8F + this.getUser().random.nextFloat() * 0.3F);
         }
      }

      protected void dropFromLootTable() {
         ResourceKey<LootTable> resourcekey = LootTableHandler.SCULPTOR_TEST;
         LootTable loottable = this.getUser().level().getServer().reloadableRegistries().getLootTable(resourcekey);
         DamageSource damageSource = this.getUser().damageSources().genericKill();
         if (this.getUser().getTestingPlayer() != null) {
            this.getUser().getTestingPlayer().damageSources().genericKill();
         }

         net.minecraft.world.level.storage.loot.LootParams.Builder lootparams$builder = new net.minecraft.world.level.storage.loot.LootParams.Builder(
               (ServerLevel)this.getUser().level()
            )
            .withParameter(LootContextParams.THIS_ENTITY, this.getUser())
            .withParameter(LootContextParams.ORIGIN, this.getUser().position())
            .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
            .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, damageSource.getEntity())
            .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, damageSource.getDirectEntity());
         if (this.getUser().getTestingPlayer() != null) {
            lootparams$builder = lootparams$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.getUser().getTestingPlayer())
               .withLuck(this.getUser().getTestingPlayer().getLuck());
         }

         LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
         loottable.getRandomItems(lootparams, this.getUser().getLootTableSeed(), this::spawnAtLocationInDirection);
      }

      @Nullable
      public ItemEntity spawnAtLocationInDirection(ItemStack stack) {
         Vec3 polarOffset = new Vec3(1.2, 0.0, 0.0).yRot((float)Math.toRadians(-this.getUser().yBodyRot - 90.0F));
         Vec3 itemPos = this.getUser().position().add(0.0, 1.2, 0.0).add(polarOffset);
         Vec3 itemVelocity = new Vec3(0.1F, 0.1F, 0.0).yRot((float)Math.toRadians(-this.getUser().yBodyRot - 90.0F));
         ItemEntity itementity = new ItemEntity(this.getUser().level(), itemPos.x, itemPos.y, itemPos.z, stack, itemVelocity.x, itemVelocity.y, itemVelocity.z);
         itementity.setDefaultPickUpDelay();
         if (this.getUser().captureDrops() != null) {
            this.getUser().captureDrops().add(itementity);
         } else {
            this.getUser().level().addFreshEntity(itementity);
         }

         return itementity;
      }

      @Override
      public boolean canCancelActiveAbility() {
         return true;
      }

      @Override
      protected void playStartingAnimation() {
         this.playAnimation(TEST_PASS_START);
      }

      @Override
      protected void playFinishingAnimation() {
         this.playAnimation(TEST_PASS_END);
      }

      @Override
      public void end() {
         super.end();
         if ((Boolean)ConfigHandler.COMMON.MOBS.SCULPTOR.disappearAfterReward.get()) {
            AbilityHandler.INSTANCE.sendAbilityMessage((EntitySculptor & LivingEntity)this.getUser(), EntitySculptor.DISAPPEAR_ABILITY);
         }
      }
   }

   public static class RunTestGoal extends Goal {
      private final EntitySculptor sculptor;

      RunTestGoal(EntitySculptor sculptor) {
         this.sculptor = sculptor;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         return this.sculptor.testingPlayer != null && this.sculptor.testingPlayer.isAlive() && !this.sculptor.testingPlayer.isRemoved();
      }

      public void start() {
         super.start();
         this.sculptor.testing = true;
         this.sculptor.sendAbilityMessage(EntitySculptor.START_TEST);
      }

      public void tick() {
         super.tick();
         if (this.sculptor.testingPlayer == null) {
            this.sculptor.sendAbilityMessage(EntitySculptor.FAIL_TEST);
            this.sculptor.prevPlayerPosition = Optional.empty();
            this.sculptor.prevPlayerVelY = Optional.empty();
         } else if (this.sculptor.testing
            && this.sculptor.getActiveAbilityType() != EntitySculptor.FAIL_TEST
            && this.sculptor.getActiveAbilityType() != EntitySculptor.PASS_TEST) {
            this.sculptor.checkIfPlayerCheats();
         }

         if (this.sculptor.getTestTimePassed() > this.sculptor.getMaxTestTime()) {
            this.sculptor.sendAbilityMessage(EntitySculptor.FAIL_TEST);
         }
      }

      public void stop() {
         super.stop();
         this.sculptor.setTestingPlayer(null);
         this.sculptor.testing = false;
      }
   }

   public static class SculptorDieAbility extends Ability<EntitySculptor> {
      private static AbilitySection.AbilitySectionDuration END_SECTION = new AbilitySection.AbilitySectionDuration(
         AbilitySection.AbilitySectionType.RECOVERY, 85
      );
      private static final RawAnimation DEATH_START = RawAnimation.begin().thenPlayAndHold("death_start");
      private static final RawAnimation DEATH_END = RawAnimation.begin().thenPlayAndHold("death_end");

      public SculptorDieAbility(AbilityType abilityType, EntitySculptor user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 9),
               new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
               END_SECTION
            }
         );
      }

      @Override
      public void start() {
         super.start();
         this.playAnimation(DEATH_START);
         this.getUser().playHurtSound(this.getUser().damageSources().generic());
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.getUser().onGround()) {
            this.nextSection();
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY && this.getTicksInSection() == 20) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_DEATH.get());
         }
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
            this.playAnimation(DEATH_END);
            this.getUser().playSound((SoundEvent)MMSounds.MISC_GROUNDHIT_1.get(), 1.0F, 1.2F);
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_HURT.get());
         }
      }

      @Override
      public boolean canCancelActiveAbility() {
         return true;
      }
   }

   public static class StartTestAbility extends Ability<EntitySculptor> {
      private static int MAX_RANGE_TO_GROUND = 12;
      private BlockPos spawnPillarPos;
      private BlockState spawnPillarBlock;
      private static final RawAnimation TEST_START_ANIM = RawAnimation.begin().then("testStart", LoopType.PLAY_ONCE);

      public StartTestAbility(AbilityType<EntitySculptor, EntitySculptor.StartTestAbility> abilityType, EntitySculptor user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 18),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, 34)
            }
         );
      }

      @Override
      public boolean tryAbility() {
         Vec3 from = this.getUser().position();
         Vec3 to = from.subtract(0.0, MAX_RANGE_TO_GROUND, 0.0);
         BlockHitResult result = this.getUser().level().clip(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this.getUser()));
         if (result.getType() == Type.MISS) {
            return false;
         }

         this.spawnPillarPos = result.getBlockPos();
         this.spawnPillarBlock = this.getUser().level().getBlockState(this.spawnPillarPos);
         if (result.getDirection() != Direction.UP) {
            BlockState blockAbove = this.getUser().level().getBlockState(this.spawnPillarPos.above());
            if (blockAbove.isSuffocating(this.getUser().level(), this.spawnPillarPos.above()) || blockAbove.isAir()) {
               return false;
            }
         }

         return true;
      }

      @Override
      public void start() {
         super.start();
         this.playAnimation(TEST_START_ANIM);
         this.getUser().prevPlayerPosition = Optional.empty();
         this.getUser().prevPlayerVelY = Optional.empty();
      }

      public static void placeStartingBoulders(EntitySculptor sculptor) {
         if (sculptor.pillar != null) {
            RandomSource rand = sculptor.getRandom();
            int numStartBoulders = rand.nextInt(2, 5);
            float angleOffset = rand.nextFloat() * (float) (Math.PI * 2);

            for (int i = 0; i < numStartBoulders; i++) {
               float angleInc = (float) (Math.PI * 2) / (numStartBoulders * 2.0F);
               float angle = angleOffset + angleInc * (i * 2) + rand.nextFloat() * angleInc;
               Vec3 spawnBoulderPos = sculptor.pillar.position().add(new Vec3(rand.nextFloat() * 3.0F + 3.0F, 0.0, 0.0).yRot(angle));
               EntityBoulderSculptor boulderPlatform = new EntityBoulderSculptor(
                  (EntityType<? extends EntityBoulderSculptor>)EntityHandler.BOULDER_SCULPTOR.get(),
                  sculptor.level(),
                  sculptor,
                  Blocks.STONE.defaultBlockState(),
                  BlockPos.ZERO,
                  EntityGeomancyBase.GeomancyTier.MEDIUM
               );
               boulderPlatform.setPos(spawnBoulderPos.add(0.0, 1.0, 0.0));
               if (i == 0) {
                  boulderPlatform.setMainPath();
               }

               sculptor.level().addFreshEntity(boulderPlatform);
            }

            sculptor.numLivePaths = numStartBoulders;
         }
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_TEST_START.get(), 1.0F, 1.0F);
         }

         if (!this.getUser().level().isClientSide() && section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.spawnPillarPos != null) {
            if (this.spawnPillarBlock == null || !EffectGeomancy.isBlockUseable(this.spawnPillarBlock)) {
               this.spawnPillarBlock = Blocks.STONE.defaultBlockState();
            }

            this.getUser().pillar = new EntityPillar.EntityPillarSculptor(
               (EntityType<? extends EntityPillar.EntityPillarSculptor>)EntityHandler.PILLAR_SCULPTOR.get(),
               this.getUser().level(),
               this.getUser(),
               Blocks.STONE.defaultBlockState(),
               this.spawnPillarPos
            );
            this.getUser().pillar.setTier(EntityGeomancyBase.GeomancyTier.SMALL);
            this.getUser().pillar.setPos(this.spawnPillarPos.getX() + 0.5F, this.spawnPillarPos.getY() + 1, this.spawnPillarPos.getZ() + 0.5F);
            this.getUser().pillar.setDoRemoveTimer(false);
            if (this.getUser().pillar.checkCanSpawn()) {
               this.getUser().level().addFreshEntity(this.getUser().pillar);
            }

            placeStartingBoulders(this.getUser());
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            for (LivingEntity livingEntity : this.getUser().getEntityLivingBaseNearby(5.0, 5.0, 5.0, 5.0)) {
               Vec3 userPos = this.getUser().position().multiply(1.0, 0.0, 1.0);
               Vec3 entityPos = livingEntity.position().multiply(1.0, 0.0, 1.0);
               Vec3 vec = userPos.subtract(entityPos).normalize().scale(-Math.min(1.0 / userPos.distanceToSqr(entityPos), 2.0));
               livingEntity.push(vec.x, vec.y, vec.z);
            }
         }
      }

      @Override
      public boolean canCancelActiveAbility() {
         return true;
      }
   }
}
