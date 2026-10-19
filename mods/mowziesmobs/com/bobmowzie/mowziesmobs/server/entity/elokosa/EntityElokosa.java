package com.bobmowzie.mowziesmobs.server.entity.elokosa;

import com.bobmowzie.mowziesmobs.client.model.tools.dynamics.GeckoDynamicChain;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.HurtAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.MeleeAttackAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.SimpleAnimationAbility;
import com.bobmowzie.mowziesmobs.server.ai.Cooldown;
import com.bobmowzie.mowziesmobs.server.ai.ElokosaLeapGoal;
import com.bobmowzie.mowziesmobs.server.ai.MMAIAvoidEntity;
import com.bobmowzie.mowziesmobs.server.ai.UseAbilityAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityBlockSwapper;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.foliaath.EntityFoliaath;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.bobmowzie.mowziesmobs.server.util.MowzieLongJumpUtil;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.animation.AnimationProcessor.QueuedAnimation;

public abstract class EntityElokosa extends MowzieGeckoEntity implements Enemy {
   public static final EntityDimensions LONG_JUMP_DIMENSIONS = EntityDimensions.scalable(0.7F, 0.7F);
   public static final EntityDimensions DAY_FORM_DIMENSIONS = EntityDimensions.scalable(0.7F, 0.7F);
   protected static final int SCREAM_DURATION = 56;
   protected static final int SCREAM_CANCEL_TICK = 37;
   private static final RawAnimation HURT_GROUND_ANIMATION = RawAnimation.begin().then("hurt_ground", LoopType.PLAY_ONCE);
   public static final AbilityType<EntityElokosa, HurtAbility<EntityElokosa>> HURT_ABILITY = new AbilityType(
      "elokosa_hurt", (type, entity) -> new HurtAbility<EntityElokosa>(type, entity, HURT_GROUND_ANIMATION, 12, 0) {
         private static final RawAnimation HURT_WALL_ANIMATION = RawAnimation.begin().then("hurt_wall", LoopType.PLAY_ONCE);
         private static final RawAnimation HURT_CEILING_ANIMATION = RawAnimation.begin().then("hurt_ceiling", LoopType.PLAY_ONCE);

         @Override
         public RawAnimation getAnimation() {
            if (this.getUser().getClingDirection().getAxis().isHorizontal()) {
               return HURT_WALL_ANIMATION;
            } else {
               return this.getUser().getClingDirection() == Direction.UP ? HURT_CEILING_ANIMATION : super.getAnimation();
            }
         }
      }
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaDieAbility> DIE_ABILITY = new AbilityType(
      "elokosa_die", EntityElokosa.ElokosaDieAbility::new
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaLeapAbility> LEAP_ABILITY = new AbilityType(
      "elokosa_leap", EntityElokosa.ElokosaLeapAbility::new
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaLeapToTargetAbility> LEAP_TO_TARGET_ABILITY = new AbilityType(
      "elokosa_leap_to_target", EntityElokosa.ElokosaLeapToTargetAbility::new
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaDayToNightAbility> DAY_TO_NIGHT_ABILITY = new AbilityType(
      "elokosa_day_to_night", EntityElokosa.ElokosaDayToNightAbility::new
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaNightToDayAbility> NIGHT_TO_DAY_ABILITY = new AbilityType(
      "elokosa_night_to_day", EntityElokosa.ElokosaNightToDayAbility::new
   );
   public static final AbilityType<EntityElokosa, SimpleAnimationAbility<EntityElokosa>> SCREAM_ABILITY = new AbilityType(
      "elokosa_scream",
      (type, entity) -> new SimpleAnimationAbility<EntityElokosa>(type, entity, RawAnimation.begin().thenPlayAndHold("scream_ground"), 56, false) {
         private static final RawAnimation SCREAM_GROUND_ANIMATION = RawAnimation.begin().then("scream_ground", LoopType.PLAY_ONCE);
         private static final RawAnimation SCREAM_WALL_ANIMATION = RawAnimation.begin().then("scream_wall", LoopType.PLAY_ONCE);
         private static final RawAnimation SCREAM_CEILING_ANIMATION = RawAnimation.begin().then("scream_ceiling", LoopType.PLAY_ONCE);

         @Override
         public void start() {
            super.start();
            this.getUser().getNavigation().stop();
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_ELOKOSA_NIGHT_SCREAM_LONG.get(), 3.0F, 0.95F + this.getUser().random.nextFloat() * 0.1F);
            EntityCameraShake.cameraShake(this.getUser().level(), this.getUser().position(), 20.0F, 0.02F, 46, 20);

            for (EntityElokosa elokosa : this.getUser().getEntitiesNearby(EntityElokosa.class, 45.0)) {
               if (elokosa.getTarget() == this.getUser().getTarget()) {
                  elokosa.endStalkingSoon(6);
               }
            }

            this.getUser().screamTimer = 56;
         }

         @Override
         public RawAnimation getAnimation() {
            if (this.getUser().getClingDirection().getAxis().isHorizontal()) {
               return SCREAM_WALL_ANIMATION;
            } else {
               return this.getUser().getClingDirection() == Direction.UP ? SCREAM_CEILING_ANIMATION : super.getAnimation();
            }
         }

         @Override
         public void tickUsing() {
            super.tickUsing();
            if (this.getUser().getTarget() != null) {
               this.getUser().getLookControl().setLookAt(this.getUser().getTarget());
            }

            this.getUser().getNavigation().stop();
         }
      }
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaAttackComboAbility> ATTACK_COMBO_ABILITY = new AbilityType(
      "elokosa_attack_combo", EntityElokosa.ElokosaAttackComboAbility::new
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaAttackComboRoarAbility> ATTACK_COMBO_ROAR_ABILITY = new AbilityType(
      "elokosa_attack_combo_roar", EntityElokosa.ElokosaAttackComboRoarAbility::new
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaScytheAttackAbility> SCYTHE_ATTACK_ABILITY = new AbilityType(
      "elokosa_scythe_attack", EntityElokosa.ElokosaScytheAttackAbility::new
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaBackflipAbility> BACKFLIP_ABILITY = new AbilityType(
      "elokosa_backflip", EntityElokosa.ElokosaBackflipAbility::new
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaLeapAttackAbility> LEAP_ATTACK_ABILITY = new AbilityType(
      "elokosa_leap_attack", EntityElokosa.ElokosaLeapAttackAbility::new
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaAttackAbility> ATTACK_ABILITY = new AbilityType(
      "elokosa_attack", EntityElokosa.ElokosaAttackAbility::new
   );
   public static final AbilityType<EntityElokosa, EntityElokosa.ElokosaSitAbility> SIT_ABILITY = new AbilityType(
      "elokosa_sit", EntityElokosa.ElokosaSitAbility::new
   );
   private static final EntityDataAccessor<Integer> CLING_DIRECTION = SynchedEntityData.defineId(EntityElokosa.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> NIGHT_FORM = SynchedEntityData.defineId(EntityElokosa.class, EntityDataSerializers.BOOLEAN);
   public Vec3 jumpVel;
   public Vec3 jumpGoalPos;
   private boolean failedFleePath = false;
   private static final int STALKING_TIMER_MAX = 700;
   private static final int TIME_UNTIL_STALK_AGAIN = 300;
   private static final int LOOKING_AT_ME_TIMER_MAX = 16;
   private int stalkingTimer = 700;
   private int lookingAtMeTimer = 16;
   private boolean isStalking = true;
   private boolean circleDirection = true;
   protected float damageThreat = 0.0F;
   protected float damageThreatDecayRate = 0.01F;
   protected float damageThreatPerDamage = 0.05F;
   protected int screamTimer = 0;
   private MMAIAvoidEntity<EntityElokosa, LivingEntity> fleeGoal;
   protected MowzieAnimationController<MowzieGeckoEntity> walkRunController = new MowzieAnimationController(
      this, "walk_run_controller", 4, this::predicateWalkRun, 0.0
   );
   protected MowzieAnimationController<MowzieGeckoEntity> jawScreamController = new MowzieAnimationController(
      this, "jaw_scream_controller", 0, this::predicateJawScream, 0.0
   );
   private static final ResourceLocation DAY_SPEED_MODIFIER_ID = ResourceLocation.withDefaultNamespace("day_form_speed");
   private static final AttributeModifier SPEED_MODIFIER_DAY = new AttributeModifier(DAY_SPEED_MODIFIER_ID, -0.03F, Operation.ADD_VALUE);
   private static final ResourceLocation DAY_HEALTH_MODIFIER_ID = ResourceLocation.withDefaultNamespace("day_form_health");
   private static final AttributeModifier HEALTH_MODIFIER_DAY = new AttributeModifier(DAY_HEALTH_MODIFIER_ID, -14.0, Operation.ADD_VALUE);
   public GeckoDynamicChain tailChain;
   public boolean missingWallClingUpperBlock;
   public Cooldown dodgeCooldown = new Cooldown(this, 100, 180, "dodge_cooldown");
   public Cooldown attackCooldown = new Cooldown(this, 100, 180, "attack_cooldown");
   public Cooldown sitCooldown = new Cooldown(this, 100, 880, "sit_cooldown");
   private double knockBackAngle = -1.0;
   public Vec3[] droolPos1;
   public Vec3[] droolPos2;
   public Vec3[] droolPos3;
   public Vec3[] droolPos4;
   public Vec3[] droolPos5;
   public Vec3[] droolPos6;
   public List<Vec3[]> droolPositions = new ArrayList<>();
   protected boolean addedHuntingTargetGoals = false;
   protected boolean preventTransform = false;
   private static RawAnimation RUN_SWITCH_ANIM = RawAnimation.begin().thenLoop("run_switch");
   private static RawAnimation WALK_SWITCH_ANIM = RawAnimation.begin().thenLoop("walk_switch");
   private static RawAnimation SCREAM_JAW_ANIM = RawAnimation.begin().thenPlay("scream_jaw_only");
   private static final RawAnimation IDLE_WALL_ANIMATION = RawAnimation.begin().then("idle_wall", LoopType.LOOP);
   private static final RawAnimation IDLE_CEILING_ANIMATION = RawAnimation.begin().then("idle_ceiling", LoopType.LOOP);
   private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
   private static final List<Integer> ALLOWED_ANGLES = Lists.newArrayList(new Integer[]{-20, -10, -5, 0, 5, 20, 35, 50, 65, 70, 75, 80});
   protected final float maxJumpVelocityMultiplier = 3.5F;

   public EntityElokosa(EntityType<? extends MowzieEntity> type, Level world) {
      super(type, world);
      this.xpReward = 7;
      if (world.isClientSide) {
         this.tailChain = new GeckoDynamicChain(this);
         this.tailChain.setDoAttract(true);
         this.tailChain.setAttractStrength(2.0F);
         this.tailChain.setAttractFalloff(0.25F);
         this.tailChain.setChainDirection(new Vector3d(0.0, 0.0, 1.0));
         this.tailChain.setDamping(0.04F);
         this.dynamicChains = new GeckoDynamicChain[]{this.tailChain};
         this.droolPos1 = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.droolPos2 = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.droolPos3 = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.droolPos4 = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.droolPos5 = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.droolPos6 = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.droolPositions.add(this.droolPos1);
         this.droolPositions.add(this.droolPos2);
         this.droolPositions.add(this.droolPos3);
         this.droolPositions.add(this.droolPos4);
         this.droolPositions.add(this.droolPos5);
         this.droolPositions.add(this.droolPos6);
      }

      this.cooldowns = new Cooldown[]{this.attackCooldown, this.dodgeCooldown};
      this.sitCooldown.startCooldown(0, 780);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new EntityElokosa.WanderLeapGoal(this));
      this.goalSelector.addGoal(2, new UseAbilityAI<EntityElokosa>(this, SCREAM_ABILITY, true, false) {
         @Override
         public boolean canUse() {
            return super.canUse() && EntityElokosa.this.getActiveAbility().getTicksInUse() < 40;
         }
      });
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, ATTACK_COMBO_ABILITY, true));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, ATTACK_COMBO_ROAR_ABILITY, true));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, LEAP_ATTACK_ABILITY, true));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, SCYTHE_ATTACK_ABILITY, true));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, ATTACK_ABILITY, true));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, BACKFLIP_ABILITY, true));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, DAY_TO_NIGHT_ABILITY, true));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, NIGHT_TO_DAY_ABILITY, true));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, SIT_ABILITY, false));
      this.goalSelector.addGoal(1, new UseAbilityAI<>(this, DIE_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, HURT_ABILITY, false));
      this.goalSelector.addGoal(4, new EntityElokosa.CombatBehaviorGoal(this));
      this.goalSelector.addGoal(3, new EntityElokosa.StalkingLeapGoal(this));
      this.goalSelector.addGoal(3, new EntityElokosa.FleeingLeapGoal(this));
      this.goalSelector.addGoal(3, new EntityElokosa.CombatLeapGoal(this));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(6, new MMAIAvoidEntity<>(this, EntityFoliaath.class, 8.0F, 2.0, 2.8));
      this.fleeGoal = new MMAIAvoidEntity<EntityElokosa, LivingEntity>(
         this,
         LivingEntity.class,
         e -> !(e instanceof EntityElokosa) && e.getAttribute(Attributes.ATTACK_DAMAGE) != null && e.getAttributeValue(Attributes.ATTACK_DAMAGE) > 0.0,
         19.0F,
         2.0,
         2.8,
         4,
         12,
         7,
         12.0
      ) {
         private int fleeCheckCounter = 0;

         @Override
         public void start() {
            super.start();
            EntityElokosa.this.failedFleePath = false;
         }

         @Override
         protected void onSafe() {
            this.fleeCheckCounter = 0;
         }

         @Override
         protected void onPathNotFound() {
            EntityElokosa.this.failedFleePath = true;
            if (this.fleeCheckCounter < 4) {
               this.fleeCheckCounter++;
            }
         }

         @Override
         public boolean canUse() {
            return !EntityElokosa.this.getNightForm() && super.canUse();
         }

         @Override
         public boolean canContinueToUse() {
            return !EntityElokosa.this.getNightForm() && super.canContinueToUse();
         }
      };
      this.goalSelector.addGoal(5, this.fleeGoal);
      this.goalSelector
         .addGoal(3, new MMAIAvoidEntity<EntityElokosa, LivingEntity>(this, LivingEntity.class, e -> e == this.getTarget(), 6.0F, 1.0, 2.0, 3, 10, 7) {
            @Override
            public boolean canUse() {
               return EntityElokosa.this.isStalking && super.canUse();
            }
         });
      this.goalSelector.addGoal(2, new EntityElokosa.ChangeFormGoal(this));
      this.goalSelector.addGoal(6, new EntityElokosa.ElokosaSitGoal(this));
      this.registerTargetGoals();
   }

   protected void registerTargetGoals() {
   }

   protected void registerHuntingTargetGoals() {
      this.addedHuntingTargetGoals = true;
      this.targetSelector.addGoal(4, new EntityElokosa.ElokosaAttackTargetGoal(this, Player.class, false));
      this.targetSelector.addGoal(4, new EntityElokosa.ElokosaAttackTargetGoal(this, EntityUmvuthana.class, false));
      this.targetSelector.addGoal(4, new EntityElokosa.ElokosaAttackTargetGoal(this, EntityUmvuthi.class, false));
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(CLING_DIRECTION, Direction.DOWN.get3DDataValue());
      builder.define(NIGHT_FORM, false);
   }

   @Override
   public void registerControllers(ControllerRegistrar controllers) {
      super.registerControllers(controllers);
      controllers.add(this.walkRunController);
      controllers.add(this.jawScreamController);
   }

   @Override
   public AbilityType getHurtAbility() {
      return HURT_ABILITY;
   }

   @Override
   public AbilityType getDeathAbility() {
      return DIE_ABILITY;
   }

   protected <E extends GeoEntity> PlayState predicateWalkRun(AnimationState<E> event) {
      float threshold = this.getNightForm() ? 0.9F : 0.7F;
      QueuedAnimation currentAnim = event.getController().getCurrentAnimation();
      if (currentAnim != null && currentAnim.animation().name().equals("run_switch")) {
         threshold *= 0.777F;
      }

      if (event.getLimbSwingAmount() > threshold && !this.isStrafing()) {
         event.getController().setAnimation(RUN_SWITCH_ANIM);
      } else {
         event.getController().setAnimation(WALK_SWITCH_ANIM);
      }

      return PlayState.CONTINUE;
   }

   private PlayState predicateJawScream(AnimationState<MowzieGeckoEntity> event) {
      if (this.screamTimer > 0) {
         event.getController().setAnimation(SCREAM_JAW_ANIM);
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   @Override
   protected <E extends GeoEntity> void loopingAnimations(AnimationState<E> event) {
      event.getController().transitionLength(4);
      if (this.getClingDirection() != Direction.DOWN && this.getClingDirection() != Direction.UP) {
         event.getController().setAnimation(IDLE_WALL_ANIMATION);
      } else if (this.getClingDirection() == Direction.UP) {
         event.getController().setAnimation(IDLE_CEILING_ANIMATION);
      } else if (event.isMoving()) {
         event.getController().setAnimation(WALK_ANIM);
      } else {
         super.loopingAnimations(event);
      }
   }

   @Override
   public AbilityType<?, ?>[] getAbilities() {
      return new AbilityType[]{
         HURT_ABILITY,
         DIE_ABILITY,
         LEAP_ABILITY,
         LEAP_TO_TARGET_ABILITY,
         DAY_TO_NIGHT_ABILITY,
         NIGHT_TO_DAY_ABILITY,
         SCREAM_ABILITY,
         ATTACK_COMBO_ABILITY,
         ATTACK_COMBO_ROAR_ABILITY,
         BACKFLIP_ABILITY,
         SCYTHE_ATTACK_ABILITY,
         LEAP_ATTACK_ABILITY,
         ATTACK_ABILITY,
         SIT_ABILITY
      };
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .add(Attributes.ATTACK_DAMAGE, 5.0)
         .add(Attributes.MAX_HEALTH, 17.0)
         .add(Attributes.MOVEMENT_SPEED, 0.2F)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.JUMP_STRENGTH, 0.42F)
         .add(Attributes.GRAVITY, 0.095F)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5);
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if (source == this.damageSources().fall()) {
         return false;
      }

      this.damageThreat = this.damageThreat + damage * this.damageThreatPerDamage;
      return super.hurt(source, damage);
   }

   protected SoundEvent getAmbientSound() {
      if (!this.getNightForm() || !this.isStalking && this.getActiveAbilityType() != SCREAM_ABILITY) {
         return this.getNightForm()
            ? (SoundEvent)((DeferredHolder)MMSounds.ENTITY_ELOKOSA_NIGHT_IDLE.get(this.random.nextInt(MMSounds.ENTITY_ELOKOSA_NIGHT_IDLE.size()))).get()
            : (SoundEvent)((DeferredHolder)MMSounds.ENTITY_ELOKOSA_DAY_IDLE.get(this.random.nextInt(MMSounds.ENTITY_ELOKOSA_DAY_IDLE.size()))).get();
      } else {
         return null;
      }
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return this.getNightForm()
         ? (SoundEvent)((DeferredHolder)MMSounds.ENTITY_ELOKOSA_NIGHT_HURT.get(this.random.nextInt(MMSounds.ENTITY_ELOKOSA_NIGHT_HURT.size()))).get()
         : (SoundEvent)((DeferredHolder)MMSounds.ENTITY_ELOKOSA_DAY_HURT.get(this.random.nextInt(MMSounds.ENTITY_ELOKOSA_DAY_HURT.size()))).get();
   }

   protected SoundEvent getDeathSound() {
      return this.getNightForm() ? (SoundEvent)MMSounds.ENTITY_ELOKOSA_NIGHT_DEATH.get() : (SoundEvent)MMSounds.ENTITY_ELOKOSA_DAY_DEATH.get();
   }

   protected void checkFallDamage(double p_29370_, boolean p_29371_, BlockState p_29372_, BlockPos p_29373_) {
   }

   @Override
   protected boolean doDirectionalWalk() {
      return true;
   }

   public MoveControl getMoveControl() {
      return super.getMoveControl();
   }

   public void setStalking(boolean stalking) {
      this.isStalking = stalking;
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.ELOKOSA.spawnConfig;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.ELOKOSA.combatConfig;
   }

   @Override
   public void tick() {
      if (this.getClingDirection() != Direction.DOWN) {
         this.setDeltaMovement(0.0, 0.0, 0.0);
         this.setPos(this.xo, this.yo, this.zo);
      }

      super.tick();
      this.screamTimer = Math.max(0, this.screamTimer - 1);
      if (this.getClingDirection() != Direction.DOWN && this.getClingDirection() != Direction.UP) {
         this.setYRot(this.getClingDirection().getOpposite().toYRot());
         this.yRotO = this.getYRot();
         this.yBodyRot = this.getYRot();
         this.yBodyRotO = this.yBodyRot;
      }

      if (!this.level().isClientSide()) {
         if (this.getClingDirection() != Direction.DOWN && !this.canContinueClinging()) {
            this.stopClinging();
         }

         if (!this.getNightForm() && this.fleeGoal != null && !this.fleeGoal.isActive() && this.getClingDirection() == Direction.DOWN) {
            this.sitCooldown.tick();
         }

         boolean isDay = isDayTime(this.level());
         boolean isNewMoon = this.level().getMoonPhase() == 4;
         if (!isDay && !isNewMoon) {
            this.addEffect(new MobEffectInstance(EffectHandler.MOONS_CURSE, 10, 0, false, false));
         } else {
            this.removeEffect(EffectHandler.MOONS_CURSE);
         }
      } else if (this.getClingDirection().getAxis().isHorizontal()) {
         Vec3i clingDirVec = this.getClingDirection().getNormal();
         BlockState state = this.level().getBlockState(BlockPos.containing(this.position().add(0.0, this.getBbHeight(), 0.0)).offset(clingDirVec));
         this.missingWallClingUpperBlock = !state.isSolid();
      } else {
         this.missingWallClingUpperBlock = false;
      }

      this.damageThreat = this.damageThreat - this.damageThreatDecayRate;
      this.damageThreat = Mth.clamp(this.damageThreat, 0.0F, 1.0F);
      if (this.getTarget() != null && this.isStalking) {
         this.stalkingTimer--;
         if (this.isLookingAtMe(this.getTarget())) {
            this.lookingAtMeTimer--;
            this.lookingAtMeTimer = Math.max(this.lookingAtMeTimer, 0);
         } else {
            this.lookingAtMeTimer++;
            this.lookingAtMeTimer = Math.min(this.lookingAtMeTimer, 16);
         }

         if (this.getTarget() instanceof Mob mob && mob.getTarget() == this) {
            this.stalkingTimer = 0;
         }

         if (this.lookingAtMeTimer == 0) {
            this.stalkingTimer = 0;
         }
      } else {
         this.lookingAtMeTimer = 16;
      }

      if (this.getActiveAbilityType() != null && this.getActiveAbilityType() != HURT_ABILITY && this.getActiveAbilityType() != SCREAM_ABILITY) {
         this.getNavigation().stop();
         this.yHeadRot = this.yBodyRot = this.getYRot();
      }

      if (this.getNightForm() && this.level().isClientSide() && this.random.nextInt(15) == 0) {
         AdvancedParticleBase.spawnParticle(
            this.level(),
            ParticleHandler.PIXEL,
            this.getX(),
            this.getY(),
            this.getZ(),
            0.0,
            0.0,
            0.0,
            true,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0,
            1.0,
            1.0,
            0.4,
            1.0,
            80.0,
            false,
            true,
            new ParticleComponent[]{
               new ParticleComponent.Gravity(0.5F),
               new ParticleComponent.PinLocation(this.droolPositions.get(this.random.nextInt(this.droolPositions.size())), 20)
                  .setVelocityOnRelease(0.0F, -0.08F, 0.0F),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                  new ParticleComponent.KeyTrack(new float[]{0.0F, 2.0F}, new float[]{0.0F, 0.125F}),
                  false
               )
            }
         );
      }
   }

   public boolean isHowler() {
      return false;
   }

   private static boolean isDayTime(Level level) {
      return level.getTimeOfDay(0.0F) >= 0.7609 || level.getTimeOfDay(0.0F) < 0.23918849;
   }

   public void knockback(double strength, double x, double z) {
      super.knockback(strength, x, z);
      this.knockBackAngle = Math.toDegrees(Math.atan2(z, x));
   }

   @Override
   protected int getDeathDuration() {
      AbilityType deathAbilityType = this.getDeathAbility();
      Ability<?> deathAbility = this.getAbility(deathAbilityType);
      if (deathAbility == null || !deathAbility.isUsing()) {
         return 9;
      } else {
         return deathAbility.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY
            ? deathAbility.getTicksInUse() - deathAbility.getTicksInSection() + 19
            : 60;
      }
   }

   public void setSpeed(float speed) {
      super.setSpeed(speed);
   }

   private boolean canContinueClinging() {
      BlockState blockstate = this.getInBlockState();
      if (blockstate.isLadder(this.level(), this.blockPosition(), this)) {
         return true;
      }

      Vec3 clingDirVec = Vec3.atLowerCornerOf(this.getClingDirection().getNormal());
      Iterable<VoxelShape> blockCollisions = this.level().getBlockCollisions(this, this.getBoundingBox().inflate(0.2).expandTowards(clingDirVec.scale(0.5)));
      return blockCollisions.iterator().hasNext();
   }

   public boolean onClimbable() {
      return super.onClimbable() && this.getPose() != Pose.LONG_JUMPING;
   }

   @Nullable
   public static Vec3 generateRandomPos(Supplier<BlockPos> posSupplier, ToDoubleFunction<BlockPos> toDoubleFunction, int attempts) {
      double d0 = Double.NEGATIVE_INFINITY;
      BlockPos blockpos = null;

      for (int i = 0; i < attempts; i++) {
         BlockPos blockpos1 = posSupplier.get();
         if (blockpos1 != null) {
            double d1 = toDoubleFunction.applyAsDouble(blockpos1);
            if (d1 > d0) {
               d0 = d1;
               blockpos = blockpos1;
            }
         }
      }

      return blockpos != null ? Vec3.atBottomCenterOf(blockpos) : null;
   }

   @Nullable
   public static Vec3 getTreePos(EntityElokosa mob, Vec3 destination, float acceptableAngle, int radius, int yRange, int yOffset, boolean avoidTarget) {
      boolean flag = GoalUtils.mobRestricted(mob, radius);
      return generateRandomPos(() -> {
         Vec3 randomDirection;
         if (destination != null) {
            randomDirection = destination.subtract(mob.position()).normalize();
            randomDirection = randomDirection.yRot((mob.random.nextFloat() * 2.0F - 1.0F) * acceptableAngle);
         } else {
            randomDirection = new Vec3(1.0, 0.0, 0.0).yRot(mob.random.nextFloat() * (float) Math.PI * 2.0F);
         }

         Vec3 randomOffset = randomDirection.scale(radius).add(0.0, mob.random.nextInt(2 * yRange + 1) - yRange + yOffset, 0.0);
         BlockPos blockPos1 = BlockPos.containing(mob.position().add(randomOffset));
         if (blockPos1 == null) {
            return null;
         }

         BlockPos blockPos2 = findClosestPosWithRaycast(mob, blockPos1);
         if (avoidTarget && mob.getTarget() != null) {
            if (mob.getTarget().distanceToSqr(Vec3.atCenterOf(blockPos2)) < 36.0) {
               return null;
            }

            if (mob.getTarget().distanceToSqr(Vec3.atCenterOf(blockPos2)) < 400.0 && Vec3.atCenterOf(blockPos2).y() < mob.getTarget().getY() + 7.0) {
               return null;
            }
         }

         return isChosenBlockOkay(mob, blockPos2) ? blockPos2 : null;
      }, mob::getTreeTargetValue, 20);
   }

   public static boolean isChosenBlockOkay(PathfinderMob mob, BlockPos blockpos) {
      if (!mob.level().getBlockState(blockpos).isSolid()) {
         return false;
      }

      if (mob.distanceToSqr(blockpos.getBottomCenter()) < 6.0) {
         return false;
      }

      PathNavigation pathnavigation = mob.getNavigation();
      Path path = pathnavigation.createPath(blockpos, 0, 2);
      return path == null || path.getDistToTarget() > 5.0F;
   }

   public boolean checkForClingInDirection(Vec3 direction) {
      Iterable<VoxelShape> blockCollisions = this.level()
         .getBlockCollisions(this, this.getBoundingBox().expandTowards(this.getDeltaMovement().scale(0.5).add(direction)).inflate(0.2));

      for (int attempts = 0; blockCollisions.iterator().hasNext() && attempts < 8; attempts++) {
         VoxelShape shape = blockCollisions.iterator().next();
         Vec3 center = shape.bounds().getCenter();
         Vec3 start = this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0);
         BlockHitResult result = this.level().clip(new ClipContext(start, center, Block.COLLIDER, Fluid.NONE, this));
         if (result.getType() == Type.BLOCK) {
            this.clingToSurface(result.getLocation(), result.getDirection().getOpposite());
            return true;
         }
      }

      return false;
   }

   public boolean checkForCling(int ticksInAir) {
      if (!this.onGround() && ticksInAir > 1 && (this.getDeltaMovement().y() < 0.0 || this.jumpGoalPos == null || this.distanceToSqr(this.jumpGoalPos) < 2.0)) {
         BlockState blockstate = this.getInBlockState();
         if (!blockstate.isLadder(this.level(), this.blockPosition(), this)) {
            Vec3 towardsGoal;
            if (this.jumpGoalPos != null) {
               towardsGoal = this.jumpGoalPos.subtract(this.position()).normalize();
            } else {
               towardsGoal = this.getDeltaMovement().normalize();
            }

            return this.checkForClingInDirection(towardsGoal);
         }

         this.setClingDirection(this.getDirection());
      }

      return false;
   }

   @Nullable
   public static BlockPos generateRandomPosTowardDirection(PathfinderMob mob, int radius, boolean shortCircuit, BlockPos pos) {
      BlockPos blockpos = RandomPos.generateRandomPosTowardDirection(mob, radius, mob.getRandom(), pos);
      return !GoalUtils.isOutsideLimits(blockpos, mob) && !GoalUtils.isRestricted(shortCircuit, mob, blockpos) ? blockpos : null;
   }

   public static BlockPos findClosestPosWithRaycast(PathfinderMob mob, BlockPos pos) {
      BlockHitResult result = mob.level()
         .clip(new ClipContext(mob.position().add(0.0, mob.getBbHeight() / 2.0F, 0.0), pos.getCenter(), Block.COLLIDER, Fluid.NONE, mob));
      return result.getType() == Type.BLOCK ? result.getBlockPos() : pos;
   }

   public static BlockPos moveAnyDirectionOutOfSolid(PathfinderMob mob, BlockPos pos, int maxDist) {
      Predicate<BlockPos> posPredicate = p -> GoalUtils.isSolid(mob, p);
      if (!posPredicate.test(pos)) {
         return pos;
      }

      Map<Direction, MutableBlockPos> posForDirection = Map.of(
         Direction.DOWN,
         pos.mutable(),
         Direction.UP,
         pos.mutable(),
         Direction.NORTH,
         pos.mutable(),
         Direction.SOUTH,
         pos.mutable(),
         Direction.WEST,
         pos.mutable(),
         Direction.EAST,
         pos.mutable()
      );

      for (int dist = 0; dist < maxDist; dist++) {
         for (Direction dir : Direction.allShuffled(mob.getRandom())) {
            MutableBlockPos thisPos = posForDirection.get(dir);
            BlockPos nextPos = thisPos.relative(dir);
            if (!posPredicate.test(nextPos)) {
               return thisPos;
            }

            thisPos.move(dir);
         }
      }

      return null;
   }

   protected EntityDimensions getDefaultDimensions(Pose pose) {
      if (!this.getNightForm()) {
         return DAY_FORM_DIMENSIONS;
      } else {
         return pose == Pose.LONG_JUMPING ? LONG_JUMP_DIMENSIONS : super.getDefaultDimensions(pose);
      }
   }

   public float getTreeTargetValue(BlockPos pos) {
      BlockState state = this.level().getBlockState(pos);
      float treeBonus = !state.is(BlockTags.LOGS) && !state.is(BlockTags.LEAVES) ? 0.0F : 10.0F;
      return (double)((float)(pos.getY() - this.getY() - this.level().getPathfindingCostFromLightLevels(pos) + treeBonus));
   }

   public Direction getClingDirection() {
      return Direction.from3DDataValue((Integer)this.getEntityData().get(CLING_DIRECTION));
   }

   public void setClingDirection(Direction clingDirection) {
      this.getEntityData().set(CLING_DIRECTION, clingDirection.get3DDataValue());
   }

   public boolean getNightForm() {
      return (Boolean)this.getEntityData().get(NIGHT_FORM);
   }

   public void setNightForm(boolean nightForm) {
      this.getEntityData().set(NIGHT_FORM, nightForm);
      if (nightForm) {
         this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(DAY_SPEED_MODIFIER_ID);
         float healthFraction = this.getHealthRatio();
         this.getAttribute(Attributes.MAX_HEALTH).removeModifier(DAY_HEALTH_MODIFIER_ID);
         this.setHealth(this.getMaxHealth() * healthFraction);
         this.setPose(Pose.STANDING);
         this.refreshDimensions();
      } else {
         if (!this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(DAY_SPEED_MODIFIER_ID)) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(SPEED_MODIFIER_DAY);
         }

         float healthFraction = this.getHealthRatio();
         if (!this.getAttribute(Attributes.MAX_HEALTH).hasModifier(DAY_HEALTH_MODIFIER_ID)) {
            this.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(HEALTH_MODIFIER_DAY);
         }

         this.setHealth(this.getMaxHealth() * healthFraction);
         this.setPose(Pose.CROUCHING);
         this.refreshDimensions();
         if (this.getClingDirection() == Direction.UP) {
            float heightDiff = this.getType().getDimensions().height() - DAY_FORM_DIMENSIONS.height();
            this.setPos(this.getX(), this.getY() + heightDiff, this.getZ());
         }
      }
   }

   public void clingToSurface(Vec3 location, Direction direction) {
      if (direction != Direction.DOWN) {
         this.setClingDirection(direction);
         this.setDeltaMovement(0.0, 0.0, 0.0);
         this.setYRot(direction.getOpposite().toYRot());
         this.yRotO = this.getYRot();
         this.yBodyRot = this.getYRot();
         if (direction != Direction.DOWN) {
            float width = this.getBbWidth() / 2.0F;
            float height = this.getBbHeight();
            if (direction == Direction.UP && this.getNightForm() && this.tickCount == 0) {
               this.setPos(this.getX(), this.getY() - height * 0.5, this.getZ());
            } else if (this.getPose() != Pose.STANDING) {
               this.setPos(location.add(Vec3.atLowerCornerOf(direction.getOpposite().getNormal()).scale(width)).subtract(0.0, height / 2.0F, 0.0));
            }
         }
      }
   }

   public void stopClinging() {
      this.setClingDirection(Direction.DOWN);
      this.setPose(Pose.STANDING);
      this.refreshDimensions();
   }

   public boolean isOnGroundOrClinging() {
      return this.getClingDirection() != Direction.DOWN || this.onGround() || this.isInWater() || this.isInLava() || this.isInPowderSnow;
   }

   public boolean findAndComputeLeap(Supplier<Vec3> posSelector) {
      this.jumpGoalPos = posSelector.get();
      if (this.jumpGoalPos == null) {
         return false;
      }

      this.jumpVel = this.calculateOptimalJumpVector(this, this.jumpGoalPos.add(0.0, 0.5, 0.0));
      if (this.jumpVel == null) {
         EntityBlockSwapper.swapBlock(this.level(), BlockPos.containing(this.jumpGoalPos), Blocks.REDSTONE_BLOCK.defaultBlockState(), 70, false, false);
      } else {
         EntityBlockSwapper.swapBlock(this.level(), BlockPos.containing(this.jumpGoalPos), Blocks.IRON_BLOCK.defaultBlockState(), 70, false, false);
      }

      return this.jumpVel != null;
   }

   @Nullable
   public Vec3 calculateOptimalJumpVector(Mob mob, Vec3 target) {
      List<Integer> list = Lists.newArrayList(ALLOWED_ANGLES);
      Collections.shuffle(list);
      float f = (float)(mob.getAttributeValue(Attributes.JUMP_STRENGTH) * 3.5);

      for (int i : list) {
         Optional<Vec3> optional = MowzieLongJumpUtil.calculateJumpVectorForAngle(mob, target, f, i, true, 1.0F);
         if (optional.isPresent()) {
            return optional.get();
         }
      }

      return null;
   }

   protected PathNavigation createNavigation(Level level) {
      return new EntityElokosa.ElokosaNavigator(this, level);
   }

   public void endStalkingSoon(int maxTicksToEndIn) {
      if (this.isStalking && this.getTarget() != null) {
         this.stalkingTimer = this.random.nextInt(maxTicksToEndIn);
      }
   }

   boolean isLookingAtMe(LivingEntity entity) {
      Vec3 vec3 = entity.getViewVector(1.0F).normalize();
      Vec3 vec31 = new Vec3(this.getX() - entity.getX(), this.getEyeY() - entity.getEyeY(), this.getZ() - entity.getZ());
      double d0 = vec31.length();
      vec31 = vec31.normalize();
      double d1 = vec3.dot(vec31);
      return d1 > 0.9 && entity.hasLineOfSight(this);
   }

   protected Vec3 updateCirclingPosition(float speed) {
      LivingEntity target = this.getTarget();
      if (target != null) {
         if (this.random.nextInt(200) == 0) {
            this.circleDirection = !this.circleDirection;
         }

         Vec3 vecBetween = target.position().subtract(this.position());
         vecBetween = vecBetween.normalize();
         Vec3 crossProd = vecBetween.cross(new Vec3(0.0, 1.0, 0.0)).normalize();
         return this.position().add(crossProd.scale(speed * (this.circleDirection ? 1 : -1)));
      } else {
         return null;
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("cling_direction", this.getClingDirection().get3DDataValue());
      compound.putInt("stalking_timer", this.stalkingTimer);
      compound.putBoolean("is_stalking", this.isStalking);
      compound.putBoolean("is_night_form", this.getNightForm());
      compound.putBoolean("prevent_transform", this.preventTransform);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setClingDirection(Direction.from3DDataValue(compound.getInt("cling_direction")));
      this.stalkingTimer = compound.getInt("stalking_timer");
      this.isStalking = compound.getBoolean("is_stalking");
      this.setNightForm(compound.getBoolean("is_night_form"));
      this.preventTransform = compound.getBoolean("prevent_transform");
   }

   protected boolean shouldDespawnInPeaceful() {
      return true;
   }

   public boolean requiresCustomPersistence() {
      return !isDayTime(this.level()) || super.requiresCustomPersistence();
   }

   @org.jetbrains.annotations.Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @org.jetbrains.annotations.Nullable SpawnGroupData spawnGroupData
   ) {
      boolean isDay = isDayTime(this.level());
      boolean isNewMoon = this.level().getMoonPhase() == 4;
      if (!isDay && !isNewMoon) {
         this.addEffect(new MobEffectInstance(EffectHandler.MOONS_CURSE, 10, 0, false, false));
      }

      this.setNightForm(this.hasEffect(EffectHandler.MOONS_CURSE));
      if (spawnType == MobSpawnType.SPAWN_EGG) {
         List<Player> players = this.getPlayersNearby(5.0, 5.0, 5.0, 5.0);
         if (!players.isEmpty()) {
            Player closestPlayer = players.get(0);
            float closestPlayerDist = 6.0F;

            for (Player player : players) {
               if (player.getMainHandItem().getItem() == ItemHandler.UMVUTHI_SPAWN_EGG.get()
                  || player.getMainHandItem().getItem() == ItemHandler.UMVUTHI_SPAWN_EGG.get()) {
                  float thisDist = this.distanceTo(player);
                  if (thisDist < closestPlayerDist) {
                     closestPlayer = player;
                     closestPlayerDist = thisDist;
                  }
               }
            }

            this.checkForClingInDirection(this.position().subtract(closestPlayer.position()).normalize());
         }
      }

      return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
   }

   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LootTableHandler.ELOKOSA;
   }

   private static void spawnTransformParticle(LivingEntity elokosa, float heightDivider) {
      AABB bounds = elokosa.getBoundingBox();
      float x = (float)(elokosa.getX() + elokosa.getRandom().nextGaussian() * (bounds.maxX - bounds.minX) / 3.0);
      float y = (float)(elokosa.getY() + elokosa.getRandom().nextGaussian() * (bounds.maxY - bounds.minY) / 3.0 + elokosa.getBbHeight() / heightDivider);
      float z = (float)(elokosa.getZ() + elokosa.getRandom().nextGaussian() * (bounds.maxZ - bounds.minZ) / 3.0);
      AdvancedParticleBase.spawnParticle(
         elokosa.level(),
         ParticleHandler.PIXEL,
         x,
         y,
         z,
         0.0,
         0.05,
         0.0,
         true,
         0.0,
         0.0,
         0.0,
         0.0,
         1.0,
         0.02734375,
         0.02734375,
         0.02734375,
         1.0,
         0.9,
         25.0F + elokosa.getRandom().nextFloat() * 10.0F,
         true,
         true,
         new ParticleComponent[]{
            new ParticleComponent.PropertyControl(
               ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
               new ParticleComponent.KeyTrack(new float[]{0.0F, 2.5F, 0.0F}, new float[]{0.0F, 0.1F, 1.0F}),
               false
            ),
            new ParticleComponent.CurlNoise(0.015F, 4.0F),
            new ParticleComponent.ForceOverTime(new Vec3(0.0, 0.003, 0.0))
         }
      );
   }

   public static class ChangeFormGoal extends Goal {
      protected final EntityElokosa elokosa;
      protected int randomDelay;

      public ChangeFormGoal(EntityElokosa elokosa) {
         this.elokosa = elokosa;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         if (this.elokosa.preventTransform) {
            return false;
         }

         if (this.elokosa.hasEffect(EffectHandler.MOONS_CURSE) == !this.elokosa.getNightForm()) {
            if (this.randomDelay == -1) {
               this.randomDelay = this.elokosa.random.nextInt(5) + 5;
            }

            if (this.randomDelay == 0) {
               this.randomDelay = -1;
               return true;
            } else {
               this.randomDelay--;
               return false;
            }
         } else {
            this.randomDelay = -1;
            return false;
         }
      }

      public void start() {
         super.start();
         if (this.elokosa.getNightForm()) {
            AbilityHandler.INSTANCE.sendAbilityMessage(this.elokosa, EntityElokosa.NIGHT_TO_DAY_ABILITY);
         } else {
            AbilityHandler.INSTANCE.sendAbilityMessage(this.elokosa, EntityElokosa.DAY_TO_NIGHT_ABILITY);
         }
      }

      public boolean canContinueToUse() {
         Ability<?> abilityDayToNight = AbilityHandler.INSTANCE.getAbility(this.elokosa, EntityElokosa.DAY_TO_NIGHT_ABILITY);
         Ability<?> abilityNightToDay = AbilityHandler.INSTANCE.getAbility(this.elokosa, EntityElokosa.NIGHT_TO_DAY_ABILITY);
         return abilityDayToNight != null && abilityDayToNight.isUsing() || abilityNightToDay != null && abilityNightToDay.isUsing();
      }
   }

   public static class CombatBehaviorGoal extends Goal {
      protected final EntityElokosa elokosa;
      private float attackRadius = 6.5F;
      private static final float ATTACK_RADIUS_MIN = 6.3F;
      private static final float ATTACK_RADIUS_MAX = 10.0F;
      private int strafingLeftRightMul;
      private int strafingFrontBackMul;
      private boolean chasing = false;
      private boolean fleeing = false;
      private int fleeingTimer = 0;
      private boolean hasReturnedToCircleAfterAttacking = true;
      private int numberOfAttacksLeft = 0;
      protected boolean attacking = false;
      protected boolean doCombo = false;

      public CombatBehaviorGoal(EntityElokosa elokosa) {
         this.elokosa = elokosa;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         if (!this.elokosa.getNightForm()) {
            return false;
         }

         if (this.elokosa.getTarget() != null) {
            return true;
         }

         if (this.elokosa.stalkingTimer >= 300) {
            this.elokosa.isStalking = true;
         }

         if (this.elokosa.stalkingTimer < 700) {
            this.elokosa.stalkingTimer++;
         }

         return false;
      }

      public void stop() {
         super.stop();
         this.elokosa.setXxa(0.0F);
         this.elokosa.setStrafing(false);
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         super.tick();
         LivingEntity target = this.elokosa.getTarget();
         if (target != null) {
            double distToTarget = this.elokosa.distanceTo(target);
            this.elokosa.getLookControl().setLookAt(target);
            if (this.elokosa.isStalking) {
               if (this.elokosa.stalkingTimer <= 0 && this.elokosa.getActiveAbility() == null) {
                  this.elokosa.isStalking = false;
                  AbilityHandler.INSTANCE.sendAbilityMessage(this.elokosa, EntityElokosa.SCREAM_ABILITY);
               }

               if (target.tickCount - target.getLastHurtByMobTimestamp() < 20 && target.getLastHurtByMob() instanceof EntityElokosa) {
                  this.elokosa.isStalking = false;
               }
            } else {
               Path path = this.elokosa.getNavigation().createPath(target, 0);
               if (path == null) {
                  if (this.elokosa.isOnGroundOrClinging()
                     && (
                        this.elokosa.getActiveAbility() == null
                           || this.elokosa.getActiveAbility().getAbilityType() == EntityElokosa.SCREAM_ABILITY
                              && this.elokosa.getActiveAbility().getTicksInUse() >= 37
                     )) {
                     Vec3 vecBetween = target.position().multiply(1.0, 0.0, 1.0).subtract(this.elokosa.position().multiply(1.0, 0.0, 1.0)).normalize();
                     this.elokosa.jumpGoalPos = target.position().add(vecBetween.scale(-2.0));
                     this.elokosa.jumpVel = this.elokosa.calculateOptimalJumpVector(this.elokosa, this.elokosa.jumpGoalPos);
                     if (this.elokosa.jumpVel != null) {
                        this.elokosa.sendAbilityMessage(EntityElokosa.LEAP_TO_TARGET_ABILITY);
                     }
                  }
               } else {
                  float frontBackDistBuffer = 2.0F;
                  float leftRightDistBuffer = 1.5F;
                  if (this.chasing && distToTarget <= this.attackRadius) {
                     this.chasing = false;
                  }

                  if (!this.chasing && distToTarget >= this.attackRadius + frontBackDistBuffer + 2.0F) {
                     this.chasing = true;
                     this.attackRadius = this.elokosa.random.nextFloat() * 3.6999998F + 6.3F;
                  }

                  if (this.fleeing && distToTarget >= this.attackRadius || this.fleeingTimer >= 100) {
                     this.fleeing = false;
                     this.fleeingTimer = 0;
                  }

                  if (!this.fleeing && distToTarget <= this.attackRadius - frontBackDistBuffer && !this.hasReturnedToCircleAfterAttacking && !this.attacking) {
                     this.fleeing = true;
                     this.attackRadius = this.elokosa.random.nextFloat() * 3.6999998F + 6.3F;
                  }

                  if (!this.attacking
                     && this.elokosa.getActiveAbility() == null
                     && distToTarget <= 4.5
                     && this.elokosa.getDotProductBodyFacingEntity(target) > 0.3
                     && (this.elokosa.damageThreat > 0.5 || this.elokosa.random.nextFloat() > 0.7)
                     && this.elokosa.dodgeCooldown.isFinished()) {
                     this.elokosa.dodgeCooldown.startCooldown();
                     AbilityHandler.INSTANCE.sendAbilityMessage(this.elokosa, EntityElokosa.BACKFLIP_ABILITY);
                     return;
                  }

                  if (this.chasing) {
                     this.elokosa.setXxa(0.0F);
                     this.elokosa.getNavigation().moveTo(target, 2.0);
                     this.elokosa.getLookControl().setLookAt(target, 30.0F, 30.0F);
                     this.elokosa.setStrafing(false);
                  } else if (this.fleeing && !this.attacking) {
                     this.elokosa.setXxa(0.0F);
                     Vec3 vecBetween = target.position().subtract(this.elokosa.position());
                     vecBetween = vecBetween.normalize();
                     Vec3 fleePos = this.elokosa.position().add(vecBetween.scale(-5.0));
                     this.elokosa.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 2.0);
                     this.elokosa.getLookControl().setLookAt(target, 30.0F, 30.0F);
                     this.elokosa.setStrafing(false);
                     this.fleeingTimer++;
                  } else {
                     if (!this.attacking && this.elokosa.getActiveAbility() == null) {
                        this.elokosa.getNavigation().stop();
                        float strafeSpeed = 1.0F;
                        Vec3 circlePos = this.elokosa.updateCirclingPosition(1.0F);
                        double distToCirclePos = this.elokosa.position().distanceTo(circlePos);
                        if (distToCirclePos <= leftRightDistBuffer) {
                           this.hasReturnedToCircleAfterAttacking = true;
                           this.elokosa.setStrafing(true);
                           if (distToTarget > this.attackRadius + 0.5) {
                              this.strafingFrontBackMul = 1;
                           } else if (distToTarget < this.attackRadius - 0.5) {
                              this.strafingFrontBackMul = -1;
                           } else {
                              this.strafingFrontBackMul = 0;
                           }

                           Vec3 toTarget = target.position().subtract(this.elokosa.position()).multiply(1.0, 0.0, 1.0).normalize();
                           Vec3 toCirclePos = circlePos.subtract(this.elokosa.position()).multiply(1.0, 0.0, 1.0).normalize();
                           Vec3 cross = toTarget.cross(toCirclePos);
                           if (cross.y > 0.0) {
                              this.strafingLeftRightMul = 1;
                           } else if (cross.y < 0.0) {
                              this.strafingLeftRightMul = -1;
                           } else {
                              this.strafingLeftRightMul = 0;
                           }

                           float distScale = (float)Math.min(Math.pow(distToCirclePos * 1.0 / leftRightDistBuffer, 0.7), 1.0);
                           this.elokosa.getMoveControl().strafe(this.strafingFrontBackMul * strafeSpeed, this.strafingLeftRightMul * strafeSpeed * distScale);
                           this.elokosa.lookAt(target, 30.0F, 30.0F);
                           if (this.elokosa.random.nextFloat() < 0.002) {
                              this.elokosa.sendAbilityMessage(EntityElokosa.SCREAM_ABILITY);
                           }
                        } else {
                           this.elokosa.setXxa(0.0F);
                           this.elokosa.setStrafing(false);
                           this.elokosa.getNavigation().moveTo(circlePos.x, circlePos.y, circlePos.z, 1.7);
                           this.elokosa.getLookControl().setLookAt(target, 30.0F, 30.0F);
                        }
                     } else {
                        this.elokosa.setXxa(0.0F);
                        this.elokosa.setStrafing(false);
                     }

                     if (this.elokosa.random.nextInt(50) == 0 && this.elokosa.attackCooldown.isFinished() && this.elokosa.getSensing().hasLineOfSight(target)) {
                        this.attacking = true;
                        this.numberOfAttacksLeft = this.elokosa.random.nextInt(1, 3);
                        this.hasReturnedToCircleAfterAttacking = false;
                     }

                     if ((this.attacking || this.hasReturnedToCircleAfterAttacking && this.elokosa.random.nextInt(30) == 0 && distToTarget <= 4.0)
                        && this.elokosa.getActiveAbility() == null) {
                        this.elokosa.setXxa(0.0F);
                        this.elokosa.setStrafing(false);
                        this.elokosa.getNavigation().moveTo(target, 2.0);
                        float distanceThreshold = this.doCombo ? 3.75F : 4.75F;
                        if (distToTarget <= distanceThreshold && this.elokosa.getSensing().hasLineOfSight(target)) {
                           this.doCombo = this.elokosa.random.nextFloat() > 0.5;
                           if (this.numberOfAttacksLeft <= 0) {
                              this.attacking = false;
                              this.elokosa.attackCooldown.startCooldown();
                           }

                           if (this.doCombo) {
                              if (this.elokosa.random.nextFloat() < 0.25) {
                                 AbilityHandler.INSTANCE.sendAbilityMessage(this.elokosa, EntityElokosa.ATTACK_COMBO_ROAR_ABILITY);
                              } else {
                                 AbilityHandler.INSTANCE.sendAbilityMessage(this.elokosa, EntityElokosa.ATTACK_COMBO_ABILITY);
                              }
                           } else {
                              AbilityHandler.INSTANCE.sendAbilityMessage(this.elokosa, EntityElokosa.ATTACK_ABILITY);
                           }

                           this.numberOfAttacksLeft--;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static class CombatLeapGoal extends ElokosaLeapGoal {
      public CombatLeapGoal(EntityElokosa elokosa) {
         super(elokosa, UniformInt.of(30, 300));
      }

      @Override
      protected int scoreJumpRandomnessAmount() {
         return 20;
      }

      @Override
      protected int scoreJump(BlockPos pos, Direction dir) {
         if (this.elokosa.getTarget() == null) {
            return super.scoreJump(pos, dir);
         }

         Vec3 vecBetween = this.elokosa.getTarget().position().add(0.0, 12.0, 0.0).subtract(pos.getBottomCenter());
         double distance = vecBetween.lengthSqr();
         int distScore = (int)Mth.clampedMap(distance, 100.0, 625.0, 30.0, 0.0);
         return super.scoreJump(pos, dir) + distScore;
      }

      @Override
      protected boolean isAcceptableLandingPosition(ElokosaLeapGoal.PossibleJump jump) {
         return this.elokosa.getTarget() != null && jump.getJumpTarget().distToCenterSqr(this.elokosa.getTarget().position()) < 36.0
            ? false
            : super.isAcceptableLandingPosition(jump);
      }

      @Override
      public boolean canUse() {
         if (this.elokosa.getTarget() != null && !this.elokosa.isStalking && this.elokosa.getNightForm()) {
            Path path = this.elokosa.getNavigation().createPath(this.elokosa.getTarget(), 0);
            return path != null && super.canUse();
         } else {
            return false;
         }
      }
   }

   private static class ElokosaAttackAbility extends MeleeAttackAbility<EntityElokosa> {
      double distanceMult = 1.0;

      public ElokosaAttackAbility(AbilityType<EntityElokosa, ? extends MeleeAttackAbility<EntityElokosa>> abilityType, EntityElokosa user) {
         super(abilityType, user, new RawAnimation[]{RawAnimation.begin().thenPlayAndHold("attack_single")}, null, null, 1.0F, 3.0F, 1.0F, 16, 10, true);
      }

      @Override
      public void start() {
         super.start();
         if (this.getUser().getTarget() != null) {
            double distToTarget = this.getUser()
               .position()
               .multiply(1.0, 0.0, 1.0)
               .distanceToSqr(this.getUser().getTarget().position().multiply(1.0, 0.0, 1.0));
            this.distanceMult = Mth.clampedMap(distToTarget, 4.0, 12.0, 0.01, 1.0);
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 3) {
            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().add(this.getUser().getForward().normalize().scale(0.65 * this.distanceMult)));
         }

         if (this.getTicksInUse() >= 9 && this.getTicksInUse() <= 11) {
            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().add(this.getUser().getForward().normalize().scale(0.15 * this.distanceMult)));
         }

         if (this.getTicksInUse() == 13) {
            this.getUser().playSound(MMSounds.ENTITY_ELOKOSA_NIGHT_ATTACK, 1.0F, this.getUser().getVoicePitch());
         }

         if (this.getTicksInUse() == 4) {
            this.getUser().playSound(MMSounds.ENTITY_ELOKOSA_NIGHT_SCREAM, 1.0F, 0.9F + this.getUser().random.nextFloat() * 0.2F);
         }
      }
   }

   public static class ElokosaAttackComboAbility extends MeleeAttackAbility<EntityElokosa> {
      private static final AbilitySection[] SECTION_TRACK = new AbilitySection[]{
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 12),
         new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 10),
         new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 17)
      };
      private static final RawAnimation ATTACK_COMBO_ANIM = RawAnimation.begin().thenPlayAndHold("attack_combo");

      public ElokosaAttackComboAbility(AbilityType<EntityElokosa, ? extends MeleeAttackAbility<EntityElokosa>> abilityType, EntityElokosa user) {
         this(abilityType, user, SECTION_TRACK, ATTACK_COMBO_ANIM);
      }

      public ElokosaAttackComboAbility(
         AbilityType<EntityElokosa, ? extends MeleeAttackAbility<EntityElokosa>> abilityType,
         EntityElokosa user,
         AbilitySection[] sectionTrack,
         RawAnimation animation
      ) {
         super(abilityType, user, sectionTrack, new RawAnimation[]{animation}, null, null, 1.0F, 3.0F, 1.0F, true);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() >= 2 && this.getTicksInUse() <= 6) {
            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().add(this.getUser().getForward().scale(0.06)));
         }

         if (this.getTicksInUse() >= 7 && this.getTicksInUse() <= 11 || this.getTicksInUse() >= 17 && this.getTicksInUse() <= 21) {
            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().add(this.getUser().getForward().scale(0.12)));
         }

         if (this.getTicksInUse() > this.scootFrameStart() && this.getTicksInUse() < this.scootFrameEnd()) {
            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().add(this.getUser().getForward().scale(this.scootAmount())));
         }

         if (!this.getLevel().isClientSide()
            && this.getUser().isHowler()
            && this.getTicksInUse() == 28
            && this.getUser().getTarget() != null
            && this.getUser().getTarget().distanceToSqr(this.getUser()) < 25.0) {
            AbilityHandler.INSTANCE.sendAbilityMessage((EntityElokosa & LivingEntity)this.getUser(), EntityElokosa.SCYTHE_ATTACK_ABILITY);
         }

         if (this.getTicksInUse() == 10) {
            this.getUser().playSound(MMSounds.ENTITY_ELOKOSA_NIGHT_ATTACK, 1.0F, this.getUser().getVoicePitch());
         }

         if (this.getTicksInUse() == 22) {
            this.getUser().playSound(MMSounds.ENTITY_ELOKOSA_NIGHT_ATTACK, 1.0F, this.getUser().getVoicePitch());
         }
      }

      protected int scootFrameStart() {
         return 27;
      }

      protected int scootFrameEnd() {
         return 32;
      }

      protected double scootAmount() {
         return 0.04;
      }

      @Override
      public boolean canBeCanceledByAbility(Ability<?> ability) {
         return ability.getAbilityType() == EntityElokosa.SCYTHE_ATTACK_ABILITY;
      }
   }

   public static class ElokosaAttackComboRoarAbility extends EntityElokosa.ElokosaAttackComboAbility {
      private static final AbilitySection[] SECTION_TRACK = new AbilitySection[]{
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 12),
         new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 10),
         new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.MISC, 13),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 12)
      };
      private static final RawAnimation ATTACK_COMBO_ROAR_ANIM = RawAnimation.begin().thenPlayAndHold("attack_combo_roar");

      public ElokosaAttackComboRoarAbility(AbilityType<EntityElokosa, ? extends MeleeAttackAbility<EntityElokosa>> abilityType, EntityElokosa user) {
         super(abilityType, user, SECTION_TRACK, ATTACK_COMBO_ROAR_ANIM);
      }

      @Override
      protected int scootFrameStart() {
         return 27;
      }

      @Override
      protected int scootFrameEnd() {
         return 37;
      }

      @Override
      protected double scootAmount() {
         return 0.025;
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 35) {
            this.getUser().playSound(MMSounds.ENTITY_ELOKOSA_NIGHT_SCREAM, 2.0F, 0.9F + this.getUser().random.nextFloat() * 0.2F);
         }
      }
   }

   public static class ElokosaAttackTargetGoal extends NearestAttackableTargetGoal<EntityElokosa> {
      private EntityElokosa elokosa;

      public ElokosaAttackTargetGoal(EntityElokosa mob, Class targetType, boolean mustSee) {
         super(mob, targetType, mustSee);
         this.elokosa = mob;
      }

      protected double getFollowDistance() {
         return super.getFollowDistance() + 20.0;
      }

      public boolean canUse() {
         return this.elokosa.getNightForm() && super.canUse();
      }

      public boolean canContinueToUse() {
         return this.elokosa.getNightForm() && super.canContinueToUse();
      }
   }

   public static class ElokosaBackflipAbility extends Ability<EntityElokosa> {
      private boolean playedEndAnim = false;
      private static final RawAnimation BACKFLIP_ANIMATION = RawAnimation.begin().thenPlayAndHold("backflip");
      private static final RawAnimation BACKFLIP_LAND_ANIMATION = RawAnimation.begin().thenPlayAndHold("backflip_land");
      private static final RawAnimation LEAP_END_WALL_ANIMATION = RawAnimation.begin().then("leap_end_wall", LoopType.HOLD_ON_LAST_FRAME);
      private static final RawAnimation LEAP_END_CEILING_ANIMATION = RawAnimation.begin().then("leap_end_ceiling", LoopType.HOLD_ON_LAST_FRAME);

      public ElokosaBackflipAbility(AbilityType<EntityElokosa, ? extends Ability> abilityType, EntityElokosa user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 1),
               new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 10)
            },
            0
         );
      }

      @Override
      public void start() {
         super.start();
         this.playAnimation(BACKFLIP_ANIMATION);
         this.playedEndAnim = false;
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            this.getUser().addDeltaMovement(this.getUser().getForward().multiply(1.0, 0.0, 1.0).normalize().scale(-2.0).add(0.0, 0.75, 0.0));
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (!this.getLevel().isClientSide()) {
            if (this.getUser().getTarget() != null) {
               this.getUser().lookAt(this.getUser().getTarget(), 30.0F, 30.0F);
            }

            if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
               this.getUser().checkForCling(this.getTicksInSection());
               this.getUser().checkForCling(this.getTicksInSection());
               if (this.getUser().onGround() && this.getUser().prevPrevOnGround || this.getUser().getClingDirection() != Direction.DOWN) {
                  AbilityHandler.INSTANCE.sendJumpToSectionMessage((EntityElokosa & LivingEntity)this.getUser(), this.getAbilityType(), 2);
               }
            }
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY && !this.playedEndAnim) {
            if (this.getUser().getClingDirection().getAxis().isHorizontal()) {
               this.playAnimation(LEAP_END_WALL_ANIMATION);
               this.playedEndAnim = true;
            } else if (this.getUser().onGround()) {
               this.playAnimation(BACKFLIP_LAND_ANIMATION);
               this.playedEndAnim = true;
            } else if (this.getUser().getClingDirection() == Direction.UP) {
               this.playAnimation(LEAP_END_CEILING_ANIMATION);
               this.playedEndAnim = true;
            }
         }
      }

      @Override
      public boolean canUse() {
         return super.canUse() && this.getUser().getClingDirection() == Direction.DOWN;
      }
   }

   public static class ElokosaDayToNightAbility extends SimpleAnimationAbility<EntityElokosa> {
      private static final RawAnimation TRANSFORM_WALL_ANIMATION = RawAnimation.begin().then("transform_day_to_night_wall", LoopType.PLAY_ONCE);
      private static final RawAnimation TRANSFORM_CEILING_ANIMATION = RawAnimation.begin().then("transform_day_to_night_ceiling", LoopType.PLAY_ONCE);

      public ElokosaDayToNightAbility(AbilityType abilityType, EntityElokosa user) {
         super(abilityType, user, RawAnimation.begin().thenPlay("transform_day_to_night"), 55);
      }

      @Override
      public void end() {
         super.end();
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getUser().getClingDirection() == Direction.DOWN) {
            this.getUser().setYHeadRot(this.getUser().getYRot());
            this.getUser().setYBodyRot(this.getUser().getYRot());
            if (this.getTicksInUse() > 32 && this.getTicksInUse() < 42) {
               this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().add(this.getUser().getForward().scale(0.025)));
            }
         }

         if (this.getTicksInUse() == 15) {
            this.getUser().setNightForm(true);
         }

         if (this.getTicksInUse() == 3) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_ELOKOSA_DAY_TRANSFORM.get(), 1.0F, 1.0F);
         }

         if (this.getTicksInUse() == 16) {
            this.getUser().playSound(MMSounds.ENTITY_ELOKOSA_NIGHT_SCREAM, 1.5F, 0.7F + this.getUser().random.nextFloat() * 0.1F);
         }

         if (this.getLevel().isClientSide() && this.getTicksInUse() > 6 && this.getTicksInUse() < 14) {
            for (int i = 0; i < 18; i++) {
               EntityElokosa.spawnTransformParticle(this.getUser(), 6.0F);
            }
         }
      }

      @Override
      public RawAnimation getAnimation() {
         if (this.getUser().getClingDirection().getAxis().isHorizontal()) {
            return TRANSFORM_WALL_ANIMATION;
         } else {
            return this.getUser().getClingDirection() == Direction.UP ? TRANSFORM_CEILING_ANIMATION : super.getAnimation();
         }
      }
   }

   public static class ElokosaDieAbility extends Ability<EntityElokosa> {
      private static AbilitySection.AbilitySectionDuration END_SECTION = new AbilitySection.AbilitySectionDuration(
         AbilitySection.AbilitySectionType.RECOVERY, 20
      );
      private static final RawAnimation DEATH_START = RawAnimation.begin().thenPlayAndHold("death_start");
      private static final RawAnimation DEATH_END = RawAnimation.begin().thenPlayAndHold("death_splat");

      public ElokosaDieAbility(AbilityType<EntityElokosa, EntityElokosa.ElokosaDieAbility> abilityType, EntityElokosa user) {
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
         if (this.getUser().knockBackAngle != -1.0) {
            this.getUser().setYRot((float)this.getUser().knockBackAngle - 90.0F);
         } else if (this.getUser().getClingDirection().getAxis().isHorizontal()) {
            this.getUser().setYRot(this.getUser().getClingDirection().toYRot());
         }

         this.getUser().stopClinging();
         this.playAnimation(DEATH_START);
         this.getUser().setDeltaMovement(this.getUser().getForward().multiply(1.0, 0.0, 1.0).normalize().scale(-0.75).add(0.0, 0.65, 0.0).scale(4.0));
         this.getUser().playHurtSound(this.getUser().damageSources().generic());
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 0) {
            this.getUser().setDeltaMovement(this.getUser().getForward().multiply(1.0, 0.0, 1.0).normalize().scale(-0.75).add(0.0, 0.2, 0.0).scale(0.1));
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.getUser().prevPrevOnGround) {
            this.nextSection();
         }
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
            this.playAnimation(DEATH_END);
            this.getUser().playSound((SoundEvent)MMSounds.MISC_GROUNDHIT_1.get(), 1.0F, this.getUser().getNightForm() ? 1.2F : 1.6F);
         }
      }

      @Override
      public boolean canCancelActiveAbility() {
         return true;
      }
   }

   public static class ElokosaLeapAbility extends Ability<EntityElokosa> {
      public static AbilitySection[] SECTION_TRACK = new AbilitySection[]{
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 2),
         new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 17)
      };
      private static final RawAnimation LEAP_START_GROUND_ANIMATION = RawAnimation.begin().then("leap_start_ground", LoopType.HOLD_ON_LAST_FRAME);
      private static final RawAnimation LEAP_END_GROUND_ANIMATION = RawAnimation.begin().then("leap_end_ground", LoopType.HOLD_ON_LAST_FRAME);
      private static final RawAnimation LEAP_START_WALL_ANIMATION = RawAnimation.begin().then("leap_start_wall", LoopType.HOLD_ON_LAST_FRAME);
      private static final RawAnimation LEAP_END_WALL_ANIMATION = RawAnimation.begin().then("leap_end_wall", LoopType.HOLD_ON_LAST_FRAME);
      private static final RawAnimation LEAP_START_CEILING_ANIMATION = RawAnimation.begin().then("leap_start_ceiling", LoopType.HOLD_ON_LAST_FRAME);
      private static final RawAnimation LEAP_END_CEILING_ANIMATION = RawAnimation.begin().then("leap_end_ceiling", LoopType.HOLD_ON_LAST_FRAME);
      private static final RawAnimation LEAP_AIR_ANIMATION = RawAnimation.begin().then("leap_air", LoopType.LOOP);
      private int ticksInAir;
      private boolean playedEndAnim = false;

      public ElokosaLeapAbility(AbilityType abilityType, EntityElokosa user) {
         super(abilityType, user, SECTION_TRACK);
      }

      @Override
      public boolean canUse() {
         return super.canUse() && this.getUser().jumpVel != null;
      }

      @Override
      protected boolean canContinueUsing() {
         return super.canContinueUsing() && this.getUser().jumpGoalPos != null && this.getUser().jumpVel != null;
      }

      @Override
      public void start() {
         super.start();
         if (this.getUser().getClingDirection() == Direction.DOWN) {
            this.playAnimation(LEAP_START_GROUND_ANIMATION);
         } else if (this.getUser().getClingDirection().getAxis().isHorizontal()) {
            this.playAnimation(LEAP_START_WALL_ANIMATION);
         } else {
            this.playAnimation(LEAP_START_CEILING_ANIMATION);
         }

         this.playedEndAnim = false;
      }

      protected boolean doClingCheck() {
         return true;
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         EntityElokosa elokosa = this.getUser();
         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && !this.getLevel().isClientSide()) {
            elokosa.setYRot(elokosa.yBodyRot);
            elokosa.setDiscardFriction(true);
            double d0 = elokosa.jumpVel.length();
            double d1 = d0 + elokosa.getJumpBoostPower();
            elokosa.setDeltaMovement(elokosa.jumpVel.scale(d1 / d0).scale(1.05));
            elokosa.setPose(Pose.LONG_JUMPING);
            elokosa.refreshDimensions();
            elokosa.setClingDirection(Direction.DOWN);
            this.ticksInAir = 0;
         }

         if (section.sectionType == AbilitySection.AbilitySectionType.RECOVERY && !this.getLevel().isClientSide()) {
            if (elokosa.onGround() || elokosa.getClingDirection() != Direction.DOWN) {
               double horizSpeed = elokosa.getDeltaMovement().multiply(1.0, 0.0, 1.0).length();
               if (horizSpeed > 0.01) {
                  horizSpeed = 0.01;
               }

               elokosa.setDeltaMovement(elokosa.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(horizSpeed));
            }

            elokosa.setDiscardFriction(false);
            elokosa.setPose(Pose.STANDING);
            elokosa.refreshDimensions();
            if (elokosa.getTarget() != null && elokosa.getTarget().distanceToSqr(elokosa) < 14.0 && !elokosa.isStalking) {
               elokosa.sendAbilityMessage(EntityElokosa.LEAP_ATTACK_ABILITY);
            }
         }
      }

      public void doLookAt() {
         if (this.getUser().jumpGoalPos != null && this.getCurrentSection().sectionType != AbilitySection.AbilitySectionType.RECOVERY) {
            this.getUser().lookAt(Anchor.EYES, this.getUser().jumpGoalPos);
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         var elokosa = (EntityElokosa & LivingEntity)this.getUser();
         if (Double.isNaN(elokosa.getDeltaMovement().y())) {
            elokosa.setDeltaMovement(0.0, 0.0, 0.0);
            AbilityHandler.INSTANCE.sendInterruptAbilityMessage(elokosa, this.getAbilityType());
         }

         this.doLookAt();
         elokosa.yBodyRot = elokosa.getYRot();
         if (!this.getLevel().isClientSide() && elokosa.jumpGoalPos != null) {
            if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
               this.ticksInAir++;
               if (this.doClingCheck()) {
                  this.getUser().checkForCling(this.ticksInAir);
               }

               if (elokosa.onGround() || elokosa.getClingDirection() != Direction.DOWN) {
                  AbilityHandler.INSTANCE.sendJumpToSectionMessage(elokosa, this.getAbilityType(), 2);
               }
            }
         } else if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY && !this.playedEndAnim) {
            if (elokosa.getClingDirection().getAxis().isHorizontal()) {
               this.playAnimation(LEAP_END_WALL_ANIMATION);
               this.playedEndAnim = true;
            } else if (elokosa.onGround()) {
               this.playAnimation(LEAP_END_GROUND_ANIMATION);
               this.playedEndAnim = true;
            } else if (elokosa.getClingDirection() == Direction.UP) {
               this.playAnimation(LEAP_END_CEILING_ANIMATION);
               this.playedEndAnim = true;
            }
         }
      }

      @Override
      public void interrupt() {
         super.interrupt();
         this.getUser().setDiscardFriction(false);
         this.getUser().setPose(Pose.STANDING);
         this.getUser().refreshDimensions();
      }

      @Override
      public void end() {
         super.end();
         this.getUser().jumpVel = null;
         this.getUser().jumpGoalPos = null;
         this.ticksInAir = 0;
      }

      @Override
      public boolean canCancelActiveAbility() {
         return this.getActiveAbility().getAbilityType() == EntityElokosa.SCREAM_ABILITY && this.getActiveAbility().getTicksInUse() >= 37;
      }
   }

   public static class ElokosaLeapAttackAbility extends MeleeAttackAbility<EntityElokosa> {
      private static final RawAnimation LEAP_SLASH_ANIM = RawAnimation.begin().thenPlay("leap_end_slash");

      public ElokosaLeapAttackAbility(AbilityType<EntityElokosa, ? extends MeleeAttackAbility<EntityElokosa>> abilityType, EntityElokosa user) {
         super(abilityType, user, new RawAnimation[]{LEAP_SLASH_ANIM}, null, null, 1.5F, 3.5F, 1.3F, 4, 16, false);
      }

      @Override
      public void start() {
         super.start();
         this.getUser().playSound(MMSounds.ENTITY_ELOKOSA_NIGHT_ATTACK, 1.0F, this.getUser().getVoicePitch());
      }

      @Override
      public boolean canCancelActiveAbility() {
         return super.canCancelActiveAbility() || this.getUser().getActiveAbility() instanceof EntityElokosa.ElokosaLeapAbility;
      }
   }

   public static class ElokosaLeapToTargetAbility extends EntityElokosa.ElokosaLeapAbility {
      public ElokosaLeapToTargetAbility(AbilityType abilityType, EntityElokosa user) {
         super(abilityType, user);
      }

      @Override
      protected boolean doClingCheck() {
         return false;
      }

      @Override
      public void doLookAt() {
         if (this.getUser().getTarget() != null) {
            this.getUser().getLookControl().setLookAt(this.getUser().getTarget(), 30.0F, 30.0F);
         }
      }
   }

   static class ElokosaNavigator extends GroundPathNavigation {
      public ElokosaNavigator(Mob mob, Level level) {
         super(mob, level);
      }

      public boolean isStableDestination(BlockPos pos) {
         BlockPos blockpos = pos.below();
         return this.level.getBlockState(blockpos).isSolid();
      }
   }

   public static class ElokosaNightToDayAbility extends SimpleAnimationAbility<EntityElokosa> {
      private static final RawAnimation TRANSFORM_WALL_ANIMATION = RawAnimation.begin().then("transform_night_to_day_wall", LoopType.PLAY_ONCE);
      private static final RawAnimation TRANSFORM_CEILING_ANIMATION = RawAnimation.begin().then("transform_night_to_day_ceiling", LoopType.PLAY_ONCE);

      public ElokosaNightToDayAbility(AbilityType abilityType, EntityElokosa user) {
         super(abilityType, user, RawAnimation.begin().thenPlay("transform_night_to_day"), 25);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 10) {
            this.getUser().setNightForm(false);
         }

         if (this.getTicksInUse() == 1) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_ELOKOSA_NIGHT_TRANSFORM.get(), 1.0F, 1.0F);
         }

         if (this.getLevel().isClientSide() && this.getTicksInUse() > 1 && this.getTicksInUse() < 9) {
            for (int i = 0; i < 26; i++) {
               EntityElokosa.spawnTransformParticle(this.getUser(), 3.0F);
            }
         }
      }

      @Override
      public RawAnimation getAnimation() {
         if (this.getUser().getClingDirection().getAxis().isHorizontal()) {
            return TRANSFORM_WALL_ANIMATION;
         } else {
            return this.getUser().getClingDirection() == Direction.UP ? TRANSFORM_CEILING_ANIMATION : super.getAnimation();
         }
      }
   }

   public static class ElokosaScytheAttackAbility extends MeleeAttackAbility<EntityElokosa> {
      private static final RawAnimation SCYTHE_ATTACK_ANIM = RawAnimation.begin().thenPlay("attack_tail_scythe");

      public ElokosaScytheAttackAbility(AbilityType<EntityElokosa, ? extends MeleeAttackAbility<EntityElokosa>> abilityType, EntityElokosa user) {
         super(abilityType, user, new RawAnimation[]{SCYTHE_ATTACK_ANIM}, null, null, 1.6F, 3.5F, 1.5F, 11, 16, false);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() >= 6 && this.getTicksInUse() <= 9) {
            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().add(this.getUser().getForward().scale(0.3)));
         }

         if (this.getTicksInUse() == 3) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_ELOKOSA_NIGHT_ATTACK_BIG.get(), 1.0F, this.getUser().getVoicePitch());
         }
      }
   }

   public static class ElokosaSitAbility extends Ability<EntityElokosa> {
      private int timer;
      private boolean hasScratched;
      private static final RawAnimation SIT_START = RawAnimation.begin().thenPlayAndHold("sit_start");
      private static final RawAnimation SIT_LOOP = RawAnimation.begin().thenLoop("sit");
      private static final RawAnimation SIT_SCRATCH = RawAnimation.begin().thenPlayAndHold("sit_scratch");
      private static final RawAnimation SIT_END = RawAnimation.begin().thenPlayAndHold("sit_end");

      public ElokosaSitAbility(AbilityType<EntityElokosa, ? extends Ability> abilityType, EntityElokosa user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 14),
               new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.MISC, 70),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 16)
            }
         );
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            this.playAnimation(SIT_START);
            this.timer = this.getUser().random.nextInt(45, 240);
            this.hasScratched = false;
         } else if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            this.playAnimation(SIT_LOOP);
         } else if (section.sectionType == AbilitySection.AbilitySectionType.MISC) {
            this.playAnimation(SIT_SCRATCH);
         } else {
            this.playAnimation(SIT_END);
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (!this.getLevel().isClientSide()) {
            if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
               this.timer--;
               if (this.timer <= 0) {
                  AbilityHandler.INSTANCE.sendJumpToSectionMessage((EntityElokosa & LivingEntity)this.getUser(), this.getAbilityType(), 3);
               }

               if (!this.hasScratched && this.getUser().random.nextInt(100) == 0) {
                  this.hasScratched = true;
                  AbilityHandler.INSTANCE.sendJumpToSectionMessage((EntityElokosa & LivingEntity)this.getUser(), this.getAbilityType(), 2);
               }
            }

            if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.MISC
               && this.getTicksInSection() == ((AbilitySection.AbilitySectionDuration)this.getCurrentSection()).duration) {
               AbilityHandler.INSTANCE.sendJumpToSectionMessage((EntityElokosa & LivingEntity)this.getUser(), this.getAbilityType(), 1);
            }
         }
      }

      @Override
      public boolean canBeCanceledByAbility(Ability<?> ability) {
         return true;
      }

      @Override
      public boolean damageInterrupts() {
         return true;
      }
   }

   public static class ElokosaSitGoal extends Goal {
      private final EntityElokosa elokosa;

      public ElokosaSitGoal(EntityElokosa elokosa) {
         this.elokosa = elokosa;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return this.elokosa.sitCooldown.isFinished() && !this.elokosa.getNightForm() && this.elokosa.getClingDirection() == Direction.DOWN;
      }

      public void start() {
         super.start();
         AbilityHandler.INSTANCE.sendAbilityMessage(this.elokosa, EntityElokosa.SIT_ABILITY);
      }

      public void stop() {
         super.stop();
         AbilityHandler.INSTANCE.sendInterruptAbilityMessage(this.elokosa, EntityElokosa.SIT_ABILITY);
         this.elokosa.sitCooldown.startCooldown();
      }

      public boolean canContinueToUse() {
         return super.canContinueToUse() && this.elokosa.getActiveAbilityType() == EntityElokosa.SIT_ABILITY;
      }
   }

   public static class FleeingLeapGoal extends ElokosaLeapGoal {
      public FleeingLeapGoal(EntityElokosa elokosa) {
         super(elokosa, UniformInt.of(13, 15));
      }

      @Override
      protected int scoreJumpRandomnessAmount() {
         return 13;
      }

      @Override
      protected int scoreJump(BlockPos pos, Direction dir) {
         if (!this.elokosa.fleeGoal.isActive() && !this.elokosa.failedFleePath) {
            return super.scoreJump(pos, dir);
         }

         LivingEntity entityEvading = (LivingEntity)this.elokosa.fleeGoal.getEntityEvading();
         Vec3 vecBetween = entityEvading.position().subtract(pos.getBottomCenter());
         double distance = vecBetween.lengthSqr();
         int distScore = (int)Mth.clampedMap(distance, 49.0, 625.0, 0.0, 30.0);
         return super.scoreJump(pos, dir) + distScore;
      }

      @Override
      protected boolean isAcceptableLandingPosition(ElokosaLeapGoal.PossibleJump jump) {
         if (!this.elokosa.fleeGoal.isActive() && !this.elokosa.failedFleePath) {
            return super.isAcceptableLandingPosition(jump);
         } else {
            return this.elokosa.fleeGoal.getEntityEvading() != null
                  && jump.getJumpTarget().distToCenterSqr(((LivingEntity)this.elokosa.fleeGoal.getEntityEvading()).position()) < 36.0
               ? false
               : super.isAcceptableLandingPosition(jump);
         }
      }

      @Override
      public boolean canUse() {
         return (this.elokosa.fleeGoal.isActive() || this.elokosa.failedFleePath) && this.elokosa.fleeGoal.getEntityEvading() != null && super.canUse();
      }

      @Override
      protected boolean ignoreTimerCondition() {
         return this.elokosa.fleeGoal.getEntityEvading() == null
            ? false
            : ((LivingEntity)this.elokosa.fleeGoal.getEntityEvading()).position().subtract(this.elokosa.position()).lengthSqr() < 144.0;
      }

      @Override
      public void start() {
         super.start();
         this.elokosa.failedFleePath = false;
      }
   }

   public static class StalkingLeapGoal extends ElokosaLeapGoal {
      public StalkingLeapGoal(EntityElokosa elokosa) {
         super(elokosa, UniformInt.of(13, 200));
      }

      @Override
      protected int scoreJumpRandomnessAmount() {
         return 20;
      }

      @Override
      protected int scoreJump(BlockPos pos, Direction dir) {
         if (this.elokosa.getTarget() == null) {
            return super.scoreJump(pos, dir);
         }

         Vec3 vecBetween = this.elokosa.getTarget().position().add(0.0, 12.0, 0.0).subtract(pos.getBottomCenter());
         double distance = vecBetween.lengthSqr();
         int distScore = (int)Mth.clampedMap(distance, 100.0, 625.0, 30.0, 0.0);
         return super.scoreJump(pos, dir) + distScore;
      }

      @Override
      protected boolean isAcceptableLandingPosition(ElokosaLeapGoal.PossibleJump jump) {
         return this.elokosa.getTarget() != null && jump.getJumpTarget().distToCenterSqr(this.elokosa.getTarget().position()) < 36.0
            ? false
            : super.isAcceptableLandingPosition(jump);
      }

      @Override
      public boolean canUse() {
         return this.elokosa.getNightForm() && this.elokosa.getTarget() != null && this.elokosa.isStalking && this.elokosa.stalkingTimer > 30 && super.canUse();
      }

      @Override
      protected boolean ignoreTimerCondition() {
         if (this.elokosa.getTarget() == null) {
            return false;
         } else if (this.elokosa.stalkingTimer <= 30) {
            return false;
         } else if (this.elokosa.position().subtract(this.elokosa.getTarget().position()).horizontalDistanceSqr() > 144.0) {
            return true;
         } else {
            return this.elokosa.getY() < this.elokosa.getTarget().getY() + 6.0 ? true : !this.elokosa.getSensing().hasLineOfSight(this.elokosa.getTarget());
         }
      }
   }

   public static class WanderLeapGoal extends ElokosaLeapGoal {
      public WanderLeapGoal(EntityElokosa elokosa) {
         super(elokosa, UniformInt.of(25, 200));
      }

      @Override
      public boolean canUse() {
         return this.elokosa.getTarget() == null && super.canUse();
      }

      @Override
      protected int scoreJump(BlockPos pos, Direction dir) {
         if (this.elokosa instanceof EntityElokosaFollower<? extends LivingEntity> elokosaFollower && elokosaFollower.getLeader() != null) {
            LivingEntity leader = elokosaFollower.getLeader();
            Vec3 vecBetween = leader.position().subtract(pos.getBottomCenter());
            double distance = vecBetween.lengthSqr();
            int distScore = (int)Mth.clampedMap(distance, 100.0, 625.0, 30.0, 0.0);
            return super.scoreJump(pos, dir) + distScore;
         } else {
            return super.scoreJump(pos, dir);
         }
      }
   }
}
