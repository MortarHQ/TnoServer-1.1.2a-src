package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.particle.ParticleDecal;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleRibbon;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.RibbonComponent;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.BlockAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.DieAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.HurtAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.MeleeAttackAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.SimpleAnimationAbility;
import com.bobmowzie.mowziesmobs.server.ai.EntityAIAvoidEntity;
import com.bobmowzie.mowziesmobs.server.ai.UseAbilityAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.item.UmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.EnumSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.animation.AnimationProcessor.QueuedAnimation;

public abstract class EntityUmvuthana extends MowzieGeckoEntity {
   public static final AbilityType<EntityUmvuthana, DieAbility<EntityUmvuthana>> DIE_ABILITY = new AbilityType(
      "umvuthana_die", (type, entity) -> new DieAbility<EntityUmvuthana>(type, entity, RawAnimation.begin().thenPlay("die"), 70) {
         @Override
         public void tickUsing() {
            super.tickUsing();
            if (this.getTicksInUse() == 1) {
               this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_HURT.get(), this.getUser().getSoundVolume(), this.getUser().getVoicePitch());
            }

            if (this.getTicksInUse() == 15) {
               this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_RETRACT.get(), this.getUser().getSoundVolume(), 1.0F);
            }
         }
      }
   );
   public static final AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaHurtAbility> HURT_ABILITY = new AbilityType(
      "umvuthana_hurt", EntityUmvuthana.UmvuthanaHurtAbility::new
   );
   public static final AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaAttackAbility> ATTACK_ABILITY = new AbilityType(
      "umvuthana_attack", EntityUmvuthana.UmvuthanaAttackAbility::new
   );
   public static final AbilityType<EntityUmvuthana, SimpleAnimationAbility<EntityUmvuthana>> ALERT_ABILITY = new AbilityType(
      "umvuthana_alert", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthana>(type, entity, RawAnimation.begin().thenPlay("alert"), 15, true) {
         int soundFrame;

         @Override
         public void start() {
            super.start();
            this.soundFrame = this.rand.nextInt(7);
         }

         @Override
         public void tickUsing() {
            super.tickUsing();
            if (this.soundFrame == this.getTicksInUse()) {
               this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_ALERT.get(), this.getUser().getSoundVolume(), this.getUser().getVoicePitch());
            }

            if (this.getUser().getTarget() != null) {
               this.getUser().lookAt(this.getUser().getTarget(), 30.0F, 30.0F);
               this.getUser().getLookControl().setLookAt(this.getUser().getTarget(), 30.0F, 30.0F);
            }
         }

         @Override
         public void end() {
            super.end();
            if (this.rand.nextFloat() < 0.2) {
               this.getUser().sendAbilityMessage(EntityUmvuthana.ROAR_ABILITY);
            }
         }
      }
   );
   public static final AbilityType<EntityUmvuthana, SimpleAnimationAbility<EntityUmvuthana>> ROAR_ABILITY = new AbilityType(
      "umvuthana_roar", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthana>(type, entity, RawAnimation.begin().thenPlay("roar"), 35, true) {
         @Override
         public void tickUsing() {
            super.tickUsing();
            if (this.getTicksInUse() == 2) {
               this.getUser()
                  .playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_ROAR.get(), this.getUser().getSoundVolume() + 0.5F, this.getUser().getVoicePitch());
            }
         }
      }
   );
   public static final AbilityType<EntityUmvuthana, SimpleAnimationAbility<EntityUmvuthana>> ACTIVATE_ABILITY = new AbilityType(
      "umvuthana_activate", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthana>(type, entity, RawAnimation.begin().thenPlay("emerge"), 21) {
         @Override
         public void tickUsing() {
            super.tickUsing();
            if (this.getTicksInUse() == 5) {
               this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_EMERGE.get(), 1.0F, 1.0F);
            }

            if (this.getTicksInUse() == 10) {
               this.getUser().active = true;
            }
         }
      }
   );
   public static final AbilityType<EntityUmvuthana, SimpleAnimationAbility<EntityUmvuthana>> DEACTIVATE_ABILITY = new AbilityType(
      "umvuthana_deactivate", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthana>(type, entity, RawAnimation.begin().thenPlay("retract"), 11) {
         @Override
         public void end() {
            super.end();
            this.getUser().discard();
            ItemUmvuthanaMask mask = EntityUmvuthana.getMaskFromType(this.getUser().getMaskType());
            if (!this.getUser().level().isClientSide) {
               ItemEntity itemEntity = this.getUser().spawnAtLocation(this.getUser().getDeactivatedMask(mask), 1.5F);
               if (itemEntity != null) {
                  ItemStack item = itemEntity.getItem();
                  item.setDamageValue((int)Math.ceil((1.0F - this.getUser().getHealthRatio()) * item.getMaxDamage()));
                  item.set(DataComponents.CUSTOM_NAME, this.getUser().getCustomName());
               }
            }
         }
      }
   );
   public static final AbilityType<EntityUmvuthana, BlockAbility<EntityUmvuthana>> BLOCK_ABILITY = new AbilityType(
      "umvuthana_block", (type, entity) -> new BlockAbility<>(type, entity, RawAnimation.begin().thenPlay("block"), 10)
   );
   public static final AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaBlockCounterAbility> BLOCK_COUNTER_ABILITY = new AbilityType(
      "umvuthana_block_counter", EntityUmvuthana.UmvuthanaBlockCounterAbility::new
   );
   public static final AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaTeleportAbility> TELEPORT_ABILITY = new AbilityType(
      "umvuthana_teleport", EntityUmvuthana.UmvuthanaTeleportAbility::new
   );
   public static final AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaHealAbility> HEAL_ABILITY = new AbilityType(
      "umvuthana_heal", EntityUmvuthana.UmvuthanaHealAbility::new
   );
   private static final EntityDataAccessor<Boolean> DANCING = SynchedEntityData.defineId(EntityUmvuthana.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> MASK = SynchedEntityData.defineId(EntityUmvuthana.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> WEAPON = SynchedEntityData.defineId(EntityUmvuthana.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.defineId(EntityUmvuthana.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Float> HEALPOSX = SynchedEntityData.defineId(EntityUmvuthana.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> HEALPOSY = SynchedEntityData.defineId(EntityUmvuthana.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> HEALPOSZ = SynchedEntityData.defineId(EntityUmvuthana.class, EntityDataSerializers.FLOAT);
   private boolean circleDirection = true;
   protected int circleTick = 0;
   protected boolean attacking = false;
   private int ticksWithoutTarget;
   public int timeUntilDeath = -1;
   private int blockCount = 0;
   public Vec3[] staffPos;
   public Vec3[] headPos;
   public Vec3[] barakoPos;
   public Vec3[] myPos;
   protected Vec3 teleportDestination;
   private static final byte FOOTSTEP_ID = 69;
   private int footstepCounter = 0;
   int maskTimingOffset = this.random.nextInt(0, 150);
   protected AnimationController<MowzieGeckoEntity> maskController = new MowzieAnimationController(
      this, "mask_controller", 1, this::predicateMask, this.maskTimingOffset
   );
   protected MowzieAnimationController<MowzieGeckoEntity> walkRunController = new MowzieAnimationController(
      this, "walk_run_controller", 4, this::predicateWalkRun, 0.0
   );
   private float prevMaskRot = 0.0F;
   private boolean rattling = false;
   private static RawAnimation MASK_TWITCH_ANIM = RawAnimation.begin().thenLoop("mask_twitch");
   private static RawAnimation RUN_SWITCH_ANIM = RawAnimation.begin().thenLoop("run_switch");
   private static RawAnimation WALK_SWITCH_ANIM = RawAnimation.begin().thenLoop("walk_switch");
   private static final RawAnimation WALK_AGGRESSIVE_ANIM = RawAnimation.begin().thenLoop("walk_aggressive");
   private static final RawAnimation IDLE_AGGRESSIVE_ANIM = RawAnimation.begin().thenLoop("idle_aggressive");
   private static final RawAnimation WALK_NEUTRAL_ANIM = RawAnimation.begin().thenLoop("walk_neutral");
   private static final RawAnimation IDLE_NEUTRAL_ANIM = RawAnimation.begin().thenLoop("idle_neutral");
   private static final RawAnimation TUMBLE_ANIM = RawAnimation.begin().thenLoop("tumble");
   private static final RawAnimation INACTIVE_ANIM = RawAnimation.begin().thenLoop("inactive");

   public EntityUmvuthana(EntityType<? extends EntityUmvuthana> type, Level world) {
      super(type, world);
      this.setMask(MaskType.from(Mth.nextInt(this.random, 1, 4)));
      this.circleTick = this.circleTick + this.random.nextInt(200);
      this.frame = this.frame + this.random.nextInt(50);
      this.xpReward = 6;
      this.active = false;
      if (world.isClientSide) {
         this.staffPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.barakoPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.myPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.headPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(0, new UseAbilityAI<>(this, ACTIVATE_ABILITY));
      this.goalSelector.addGoal(0, new UseAbilityAI<>(this, DEACTIVATE_ABILITY));
      this.goalSelector.addGoal(1, new UseAbilityAI<>(this, DIE_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, HURT_ABILITY, false));
      this.goalSelector.addGoal(3, new EntityAIAvoidEntity<>(this, EntitySunstrike.class, EntitySunstrike::isStriking, 3.0F, 0.7F));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, ATTACK_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, ALERT_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, ROAR_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, BLOCK_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, BLOCK_COUNTER_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, TELEPORT_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, HEAL_ABILITY, false));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.4));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, EntityUmvuthana.class, 8.0F));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, EntityUmvuthi.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(5, new EntityUmvuthana.CircleAttackGoal(this, 6.5F));
      this.registerTargetGoals();
   }

   protected void registerTargetGoals() {
   }

   protected void registerHuntingTargetGoals() {
      this.targetSelector
         .addGoal(
            5,
            new NearestAttackableTargetGoal(
               this,
               Animal.class,
               200,
               true,
               false,
               target -> {
                  float volume = target.getBbWidth() * target.getBbWidth() * target.getBbHeight();
                  return (target.getAttribute(Attributes.ATTACK_DAMAGE) == null || target.getAttributeValue(Attributes.ATTACK_DAMAGE) < 3.0)
                     && volume > 0.1
                     && volume < 6.0F;
               }
            )
         );
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Zombie.class, 0, true, false, e -> !(e instanceof ZombifiedPiglin)));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, AbstractSkeleton.class, 0, true, false, null));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Zoglin.class, 0, true, false, null));
      this.targetSelector.addGoal(6, new AvoidEntityGoal(this, Creeper.class, 6.0F, 1.0, 1.2));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Player.class, 0, true, true, target -> {
         if (target instanceof Player) {
            if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
               return false;
            }

            ItemStack headArmorStack = (ItemStack)((Player)target).getInventory().armor.get(3);
            return !(headArmorStack.getItem() instanceof UmvuthanaMask);
         } else {
            return true;
         }
      }));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, EntityElokosa.class, 0, true, false, null));
   }

   @Override
   public void registerControllers(ControllerRegistrar controllers) {
      super.registerControllers(controllers);
      controllers.add(this.maskController);
      controllers.add(this.walkRunController);
   }

   protected <E extends GeoEntity> PlayState predicateMask(AnimationState<E> event) {
      if (this.isAlive() && this.active && this.getActiveAbilityType() != HEAL_ABILITY) {
         event.getController().setAnimation(MASK_TWITCH_ANIM);
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   protected <E extends GeoEntity> PlayState predicateWalkRun(AnimationState<E> event) {
      float threshold = 0.9F;
      QueuedAnimation currentAnim = event.getController().getCurrentAnimation();
      if (currentAnim != null && currentAnim.animation().name().equals("run_switch")) {
         threshold = 0.7F;
      }

      if (event.getLimbSwingAmount() > threshold && !this.isStrafing()) {
         event.getController().setAnimation(RUN_SWITCH_ANIM);
      } else {
         event.getController().setAnimation(WALK_SWITCH_ANIM);
      }

      return PlayState.CONTINUE;
   }

   @Override
   protected <E extends GeoEntity> void loopingAnimations(AnimationState<E> event) {
      if (this.active) {
         if (this.isAggressive()) {
            event.getController().transitionLength(4);
            if (event.isMoving()) {
               event.getController().setAnimation(WALK_AGGRESSIVE_ANIM);
            } else {
               event.getController().setAnimation(IDLE_AGGRESSIVE_ANIM);
            }
         } else {
            event.getController().transitionLength(4);
            if (event.isMoving()) {
               event.getController().setAnimation(WALK_NEUTRAL_ANIM);
            } else {
               event.getController().setAnimation(IDLE_NEUTRAL_ANIM);
            }
         }
      } else {
         event.getController().transitionLength(0);
         if (!this.onGround() && !this.isInLava() && !this.isInWater()) {
            event.getController().setAnimation(TUMBLE_ANIM);
         } else {
            event.getController().setAnimation(INACTIVE_ANIM);
         }
      }
   }

   protected BodyRotationControl createBodyControl() {
      return super.createBodyControl();
   }

   @Override
   public AbilityType getDeathAbility() {
      return DIE_ABILITY;
   }

   @Override
   public AbilityType getHurtAbility() {
      return HURT_ABILITY;
   }

   @Override
   public boolean shouldPlayHurtAnimation(DamageSource source, float damage) {
      return super.shouldPlayHurtAnimation(source, damage);
   }

   protected SoundEvent getAmbientSound() {
      if (this.getActiveAbilityType() == DEACTIVATE_ABILITY) {
         return null;
      }

      int i = Mth.nextInt(this.random, 0, MMSounds.ENTITY_UMVUTHANA_IDLE.size());
      return i < MMSounds.ENTITY_UMVUTHANA_IDLE.size() ? (SoundEvent)((DeferredHolder)MMSounds.ENTITY_UMVUTHANA_IDLE.get(i)).get() : null;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return this.active ? (SoundEvent)MMSounds.ENTITY_UMVUTHANA_HURT.get() : null;
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .add(Attributes.ATTACK_DAMAGE, 3.0)
         .add(Attributes.MAX_HEALTH, 8.0)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData livingData) {
      if (this.canHoldVaryingWeapons()) {
         this.setWeapon(this.random.nextInt(3) == 0 ? 1 : 0);
      }

      if (reason == MobSpawnType.COMMAND
         && !(this instanceof EntityUmvuthanaRaptor)
         && !(this instanceof EntityUmvuthanaCrane)
         && !(this instanceof EntityUmvuthanaCraneToPlayer)) {
         this.setMask(MaskType.from(Mth.nextInt(this.random, 1, 4)));
      }

      return super.finalizeSpawn(world, difficulty, reason, livingData);
   }

   protected boolean canHoldVaryingWeapons() {
      return true;
   }

   protected Vec3 updateCirclingPosition(float radius, float speed) {
      LivingEntity target = this.getTarget();
      if (target != null) {
         if (this.random.nextInt(200) == 0) {
            this.circleDirection = !this.circleDirection;
         }

         if (this.circleDirection) {
            this.circleTick++;
         } else {
            this.circleTick--;
         }

         return this.circleEntityPosition(target, radius, speed, true, this.circleTick, 0.0F);
      } else {
         return null;
      }
   }

   @Override
   protected boolean doDirectionalWalk() {
      return true;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level().isClientSide()
         && this.deathTime < 20
         && this.active
         && (
            this.getActiveAbilityType() != TELEPORT_ABILITY
               || this.getActiveAbility().getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY
         )) {
         if (this.tickCount % 10 == 1) {
            AdvancedParticleBase.spawnParticle(
               this.level(),
               ParticleHandler.GLOW,
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
               0.3,
               0.4,
               1.0,
               9.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.PinLocation(this.headPos),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.oscillate(9.0F, 10.0F, 12), false
                  )
               }
            );
         }

         if (this.headPos != null && this.headPos.length > 0 && this.headPos[0] != null && this.random.nextFloat() < 0.3F) {
            int amount = this.random.nextInt(2) + 1;

            while (amount-- > 0) {
               float theta = this.random.nextFloat() * (float) (Math.PI * 2);
               float r = this.random.nextFloat() * 0.4F;
               float x = r * Mth.cos(theta);
               float z = r * Mth.sin(theta);
               this.level().addParticle(ParticleTypes.SMOKE, this.headPos[0].x() + x, this.headPos[0].y() + 0.1, this.headPos[0].z() + z, 0.0, 0.0, 0.0);
            }
         }
      }

      if (this.getTarget() != null && (this.getTarget().isRemoved() || this.getTarget().isDeadOrDying())) {
         this.setTarget(null);
      }

      if (this.getActiveAbilityType() != BLOCK_ABILITY && this.blockCount > 0 && this.tickCount % 10 == 0) {
         this.blockCount--;
      }

      if (!this.level().isClientSide && this.active && !this.getActive()) {
         this.setActive(true);
      }

      this.active = this.getActive();
      if (this.active) {
         if (this.getActiveAbilityType() != null && this.getActiveAbilityType() != HURT_ABILITY) {
            this.getNavigation().stop();
            this.yHeadRot = this.yBodyRot = this.getYRot();
         }

         if (this.getTarget() != null && this.ticksWithoutTarget > 3) {
            this.sendAbilityMessage(ALERT_ABILITY);
         }

         if (this.getTarget() == null) {
            this.ticksWithoutTarget++;
         } else {
            this.ticksWithoutTarget = 0;
         }

         if (this.timeUntilDeath > 0) {
            this.timeUntilDeath--;
         } else if (this.timeUntilDeath == 0) {
            this.hurt(this.damageSources().indirectMagic(this, null), this.getHealth() + 1.0F);
         }
      } else {
         this.getNavigation().stop();
         this.setYRot(this.yRotO);
         this.yBodyRot = this.getYRot();
         if ((this.onGround() || this.isInWater() || this.isInLava()) && this.getActiveAbility() == null) {
            this.sendAbilityMessage(ACTIVATE_ABILITY);
         }
      }
   }

   public void updateRattleSound(float maskRot) {
      if (!this.rattling) {
         if (Math.abs(maskRot - this.prevMaskRot) > 0.06) {
            this.level()
               .playLocalSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  (SoundEvent)MMSounds.ENTITY_UMVUTHANA_RATTLE.get(),
                  SoundSource.HOSTILE,
                  0.03F,
                  this.getVoicePitch(),
                  false
               );
         }
      } else if (Math.abs(maskRot - this.prevMaskRot) < 1.0E-8) {
         this.rattling = false;
      }

      this.prevMaskRot = maskRot;
   }

   protected float nextStep() {
      if (this.onGround()) {
         this.level().broadcastEntityEvent(this, (byte)69);
      }

      return super.nextStep();
   }

   @Override
   public void handleEntityEvent(byte id) {
      if (id == 69 && (Boolean)ConfigHandler.CLIENT.umvuthanaFootprints.get()) {
         this.footstepCounter++;
         double rotation = Math.toRadians(this.yBodyRot + 180.0F);
         Vec3 offset = new Vec3(0.0, 0.0, this.footstepCounter % 2 == 0 ? 0.3 : -0.3).yRot((float)(-rotation) + 90.0F);
         ParticleDecal.spawnDecal(
            this.level(),
            ParticleHandler.STRIX_FOOTPRINT,
            this.getX() + offset.x(),
            this.getY() + 0.01,
            this.getZ() + offset.z(),
            0.0,
            0.0,
            0.0,
            rotation,
            1.0,
            1.0,
            0.95,
            0.1,
            1.0,
            1.0,
            200.0,
            true,
            8,
            32,
            new ParticleComponent[]{
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.RED,
                  new ParticleComponent.KeyTrack(new float[]{0.995F, 0.05F}, new float[]{0.0F, 0.3F}),
                  false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.GREEN,
                  new ParticleComponent.KeyTrack(new float[]{0.95F, 0.05F}, new float[]{0.0F, 0.3F}),
                  false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.BLUE,
                  new ParticleComponent.KeyTrack(new float[]{0.1F, 0.05F}, new float[]{0.0F, 0.3F}),
                  false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                  new ParticleComponent.KeyTrack(new float[]{1.0F, 0.8F}, new float[]{0.0F, 0.3F}),
                  false
               ),
               new ParticleComponent() {
                  @Override
                  public void postUpdate(AdvancedParticleBase particle) {
                     super.postUpdate(particle);
                     if (particle.getAge() < 80.0F && EntityUmvuthana.this.random.nextFloat() < 0.3F) {
                        int amount = 1;

                        while (amount-- > 0) {
                           float theta = EntityUmvuthana.this.random.nextFloat() * (float) (Math.PI * 2);
                           float r = EntityUmvuthana.this.random.nextFloat() * 0.2F;
                           float x = r * Mth.cos(theta);
                           float z = r * Mth.sin(theta);
                           EntityUmvuthana.this.level()
                              .addParticle(ParticleTypes.SMOKE, particle.getPosX() + x, particle.getPosY() + 0.05, particle.getPosZ() + z, 0.0, 0.0, 0.0);
                        }
                     }
                  }
               }
            }
         );
      } else {
         super.handleEntityEvent(id);
      }
   }

   public static ItemUmvuthanaMask getMaskFromType(MaskType maskType) {
      ItemUmvuthanaMask mask = (ItemUmvuthanaMask)ItemHandler.UMVUTHANA_MASK_FURY.get();
      switch (maskType) {
         case BLISS:
            mask = (ItemUmvuthanaMask)ItemHandler.UMVUTHANA_MASK_BLISS.get();
            break;
         case FEAR:
            mask = (ItemUmvuthanaMask)ItemHandler.UMVUTHANA_MASK_FEAR.get();
            break;
         case FURY:
            mask = (ItemUmvuthanaMask)ItemHandler.UMVUTHANA_MASK_FURY.get();
            break;
         case MISERY:
            mask = (ItemUmvuthanaMask)ItemHandler.UMVUTHANA_MASK_MISERY.get();
            break;
         case RAGE:
            mask = (ItemUmvuthanaMask)ItemHandler.UMVUTHANA_MASK_RAGE.get();
            break;
         case FAITH:
            mask = (ItemUmvuthanaMask)ItemHandler.UMVUTHANA_MASK_FAITH.get();
      }

      return mask;
   }

   protected ItemStack getDeactivatedMask(ItemUmvuthanaMask mask) {
      return new ItemStack(mask);
   }

   protected SoundEvent getDeathSound() {
      this.playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_DIE.get(), 1.0F, 0.95F + this.random.nextFloat() * 0.1F);
      return null;
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DANCING, false);
      builder.define(MASK, 0);
      builder.define(WEAPON, 0);
      builder.define(ACTIVE, true);
      builder.define(HEALPOSX, 0.0F);
      builder.define(HEALPOSY, 0.0F);
      builder.define(HEALPOSZ, 0.0F);
   }

   public boolean getDancing() {
      return (Boolean)this.getEntityData().get(DANCING);
   }

   public void setDancing(boolean dancing) {
      this.getEntityData().set(DANCING, dancing);
   }

   public MaskType getMaskType() {
      return MaskType.from((Integer)this.getEntityData().get(MASK));
   }

   public void setMask(MaskType type) {
      this.getEntityData().set(MASK, type.ordinal());
      this.setItemSlot(EquipmentSlot.HEAD, getMaskFromType(type).getDefaultInstance());
      this.setDropChance(EquipmentSlot.HEAD, 0.0F);
   }

   public int getWeapon() {
      return (Integer)this.getEntityData().get(WEAPON);
   }

   public void setWeapon(int type) {
      this.getEntityData().set(WEAPON, type);
   }

   public boolean getActive() {
      return (Boolean)this.getEntityData().get(ACTIVE);
   }

   public void setActive(boolean active) {
      this.getEntityData().set(ACTIVE, active);
   }

   public Vec3 getHealPos() {
      return new Vec3(
         ((Float)this.getEntityData().get(HEALPOSX)).floatValue(),
         ((Float)this.getEntityData().get(HEALPOSY)).floatValue(),
         ((Float)this.getEntityData().get(HEALPOSZ)).floatValue()
      );
   }

   public void setHealPos(Vec3 vec) {
      this.getEntityData().set(HEALPOSX, (float)vec.x);
      this.getEntityData().set(HEALPOSY, (float)vec.y);
      this.getEntityData().set(HEALPOSZ, (float)vec.z);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("mask", this.getMaskType().ordinal());
      compound.putInt("weapon", this.getWeapon());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setMask(MaskType.from(compound.getInt("mask")));
      this.setWeapon(compound.getInt("weapon"));
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if (this.getActiveAbilityType() == DEACTIVATE_ABILITY) {
         return false;
      }

      Entity entity = source.getEntity();
      if (source == this.damageSources().hotFloor()) {
         return false;
      }

      boolean angleFlag = true;
      if (entity != null) {
         int arc = 220;
         Vec3 entityPos = entity.position();
         float entityHitAngle = (float)((Math.atan2(entityPos.z() - this.getZ(), entityPos.x() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
         float entityAttackingAngle = this.yBodyRot % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         angleFlag = entityRelativeAngle <= arc / 2.0 && entityRelativeAngle >= -arc / 2.0
            || entityRelativeAngle >= 360.0 - arc / 2.0
            || entityRelativeAngle <= -arc + 45.0;
      }

      if (angleFlag
         && this.getMaskType().canBlock
         && entity instanceof LivingEntity
         && (this.getActiveAbility() == null || this.getActiveAbilityType() == HURT_ABILITY || this.getActiveAbilityType() == BLOCK_ABILITY)
         && !source.is(DamageTypeTags.BYPASSES_ARMOR)) {
         this.blockingEntity = (LivingEntity)entity;
         this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), 0.4F, 2.0F);
         if (this.blockingEntity == this.getTarget()
            && this.random.nextFloat() < Mth.clamp(this.blockCount / 5.0, 0.0, 1.0)
            && this.distanceTo(this.blockingEntity) < 4.0F) {
            AbilityHandler.INSTANCE.sendAbilityMessage(this, BLOCK_COUNTER_ABILITY);
            this.blockCount = 0;
         } else {
            AbilityHandler.INSTANCE.sendAbilityMessage(this, BLOCK_ABILITY);
            this.blockCount++;
         }

         return false;
      } else {
         return super.hurt(source, damage);
      }
   }

   @NotNull
   protected ResourceKey<LootTable> getDefaultLootTable() {
      return switch (this.getMaskType()) {
         case BLISS -> LootTableHandler.UMVUTHANA_BLISS;
         case FEAR -> LootTableHandler.UMVUTHANA_FEAR;
         case FURY -> LootTableHandler.UMVUTHANA_FURY;
         case MISERY -> LootTableHandler.UMVUTHANA_MISERY;
         case RAGE -> LootTableHandler.UMVUTHANA_RAGE;
         case FAITH -> LootTableHandler.UMVUTHANA_FAITH;
      };
   }

   public boolean isPickable() {
      return this.active;
   }

   public boolean causeFallDamage(float distance, float damageMultipler, DamageSource source) {
      return this.active ? super.causeFallDamage(distance, damageMultipler, source) : false;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.UMVUTHANA.combatConfig;
   }

   public boolean isUmvuthiDevoted() {
      return true;
   }

   public int randomizeWeapon() {
      return this.random.nextInt(3) == 0 ? 1 : 0;
   }

   public boolean canHeal(LivingEntity entity) {
      return false;
   }

   protected void sunBlockTarget() {
   }

   @Override
   public AbilityType<?, ?>[] getAbilities() {
      return new AbilityType[]{
         DIE_ABILITY,
         HURT_ABILITY,
         ATTACK_ABILITY,
         ALERT_ABILITY,
         ROAR_ABILITY,
         ACTIVATE_ABILITY,
         DEACTIVATE_ABILITY,
         BLOCK_ABILITY,
         BLOCK_COUNTER_ABILITY,
         TELEPORT_ABILITY,
         HEAL_ABILITY
      };
   }

   public int getAttackChance() {
      return 80;
   }

   public int getAttackCooldown() {
      return 80;
   }

   protected static class CircleAttackGoal extends Goal {
      private final EntityUmvuthana mob;
      private final float attackRadius;
      private int strafingLeftRightMul;
      private int strafingFrontBackMul;
      private boolean chasing = false;
      protected boolean attacking = false;
      private int timeSinceAttack = 0;

      public CircleAttackGoal(EntityUmvuthana mob, float attackRadius) {
         this.mob = mob;
         this.attackRadius = attackRadius;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         return this.mob.getTarget() != null;
      }

      public boolean canContinueToUse() {
         return this.canUse() || !this.mob.getNavigation().isDone();
      }

      public void start() {
         super.start();
         this.mob.setAggressive(true);
         this.timeSinceAttack = this.mob.random.nextInt(80);
      }

      public void stop() {
         super.stop();
         this.mob.setAggressive(false);
         this.mob.setStrafing(false);
         this.mob.setXxa(0.0F);
         this.mob.getMoveControl().strafe(0.0F, 0.0F);
         this.attacking = false;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity target = this.mob.getTarget();
         if (target != null) {
            if (this.timeSinceAttack < 80) {
               this.timeSinceAttack++;
            }

            double distToTarget = this.mob.distanceTo(target);
            float frontBackDistBuffer = 2.0F;
            float leftRightDistBuffer = 1.5F;
            if (this.chasing && distToTarget <= this.attackRadius) {
               this.chasing = false;
            }

            if (!this.chasing && distToTarget >= this.attackRadius + frontBackDistBuffer) {
               this.chasing = true;
            }

            if (this.chasing) {
               this.mob.getNavigation().moveTo(target, 0.6);
               this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.mob.setStrafing(false);
               this.mob.setXxa(0.0F);
               this.mob.getMoveControl().strafe(0.0F, 0.0F);
            } else {
               if (!this.attacking && this.mob.getActiveAbility() == null) {
                  this.mob.getNavigation().stop();
                  float strafeSpeed = 0.5F;
                  Vec3 circlePos = this.mob.updateCirclingPosition(this.attackRadius, strafeSpeed - 0.2F);
                  double distToCirclePos = this.mob.position().distanceTo(circlePos);
                  if (distToCirclePos <= leftRightDistBuffer) {
                     this.mob.setStrafing(true);
                     if (distToTarget > this.attackRadius + 0.5) {
                        this.strafingFrontBackMul = 1;
                     } else if (distToTarget < this.attackRadius - 0.5) {
                        this.strafingFrontBackMul = -1;
                     } else {
                        this.strafingFrontBackMul = 0;
                     }

                     Vec3 toTarget = target.position().subtract(this.mob.position()).multiply(1.0, 0.0, 1.0).normalize();
                     Vec3 toCirclePos = circlePos.subtract(this.mob.position()).multiply(1.0, 0.0, 1.0).normalize();
                     Vec3 cross = toTarget.cross(toCirclePos);
                     if (cross.y > 0.0) {
                        this.strafingLeftRightMul = 1;
                     } else if (cross.y < 0.0) {
                        this.strafingLeftRightMul = -1;
                     } else {
                        this.strafingLeftRightMul = 0;
                     }

                     float distScale = (float)Math.min(Math.pow(distToCirclePos * 1.0 / leftRightDistBuffer, 0.7), 1.0);
                     this.mob.getMoveControl().strafe(this.strafingFrontBackMul * strafeSpeed, this.strafingLeftRightMul * strafeSpeed * distScale);
                     this.mob.lookAt(target, 30.0F, 30.0F);
                     if (this.mob.random.nextFloat() < 0.002) {
                        this.mob.sendAbilityMessage(EntityUmvuthana.ROAR_ABILITY);
                     }
                  } else {
                     this.mob.setXxa(0.0F);
                     this.mob.setStrafing(false);
                     this.mob.getMoveControl().strafe(0.0F, 0.0F);
                     this.mob.getNavigation().moveTo(circlePos.x, circlePos.y, circlePos.z, 0.53);
                     this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                  }
               } else {
                  this.mob.setXxa(0.0F);
                  this.mob.getMoveControl().strafe(0.0F, 0.0F);
                  this.mob.setStrafing(false);
               }

               if (this.mob.random.nextInt(this.mob.getAttackChance()) == 0
                  && this.timeSinceAttack >= this.mob.getAttackCooldown()
                  && this.mob.getSensing().hasLineOfSight(target)) {
                  this.attacking = true;
               }

               if (this.attacking && this.mob.getActiveAbility() == null) {
                  this.mob.setXxa(0.0F);
                  this.mob.setStrafing(false);
                  this.mob.getNavigation().moveTo(target, 0.5);
                  if (distToTarget <= 3.75 && this.mob.getSensing().hasLineOfSight(target)) {
                     this.attacking = false;
                     this.timeSinceAttack = 0;
                     AbilityHandler.INSTANCE.sendAbilityMessage(this.mob, EntityUmvuthana.ATTACK_ABILITY);
                  }
               }
            }
         }
      }
   }

   private static class UmvuthanaAttackAbility extends MeleeAttackAbility<EntityUmvuthana> {
      public UmvuthanaAttackAbility(AbilityType<EntityUmvuthana, ? extends MeleeAttackAbility<EntityUmvuthana>> abilityType, EntityUmvuthana user) {
         super(
            abilityType,
            user,
            new RawAnimation[]{RawAnimation.begin().thenPlay("attack_slash_left"), RawAnimation.begin().thenPlay("attack_slash_right")},
            null,
            null,
            1.0F,
            3.0F,
            1.0F,
            13,
            9,
            true
         );
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 5) {
            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().add(this.getUser().getForward().normalize().scale(0.5)));
         }

         if (this.getTicksInUse() == 1) {
            int i = this.rand.nextInt(MMSounds.ENTITY_UMVUTHANA_ATTACK.size());
            this.getUser().playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_UMVUTHANA_ATTACK.get(i)).get(), 1.0F, this.rand.nextFloat(0.9F, 1.1F));
         }
      }
   }

   private static class UmvuthanaBlockCounterAbility extends MeleeAttackAbility<EntityUmvuthana> {
      public UmvuthanaBlockCounterAbility(AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaBlockCounterAbility> abilityType, EntityUmvuthana user) {
         super(abilityType, user, new RawAnimation[]{RawAnimation.begin().thenPlay("block_counter")}, null, null, 3.0F, 2.2F, 1.2F, 7, 11, false);
      }

      @Override
      public void start() {
         super.start();
         this.getUser().setInvulnerable(true);
      }

      @Override
      public void end() {
         super.end();
         this.getUser().setInvulnerable(false);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 5) {
            float distToTarget = 1.0F;
            if (this.getUser().getTarget() != null) {
               distToTarget = Mth.clamp(this.getUser().distanceTo(this.getUser().getTarget()) / 2.0F - 1.0F, 0.0F, 1.0F);
            }

            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().add(this.getUser().getForward().normalize().scale(1.6 * distToTarget)));
         }

         if (this.getTicksInUse() == 0) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_ATTACK_BIG.get(), 1.0F, this.rand.nextFloat(0.9F, 1.1F));
         }
      }

      @Override
      public boolean canCancelActiveAbility() {
         return super.canCancelActiveAbility()
            || this.getUser().getActiveAbility() instanceof BlockAbility
            || this.getUser().getActiveAbility() instanceof HurtAbility;
      }
   }

   private static class UmvuthanaHealAbility extends Ability<EntityUmvuthana> {
      private static final RawAnimation HEAL_START_ANIM = RawAnimation.begin().thenPlay("heal_start");
      private static final RawAnimation HEAL_LOOP_ANIM = RawAnimation.begin().thenLoop("heal_loop");
      private static final RawAnimation HEAL_END_ANIM = RawAnimation.begin().thenPlay("heal_end");

      public UmvuthanaHealAbility(AbilityType<EntityUmvuthana, ? extends Ability> abilityType, EntityUmvuthana user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 6),
               new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 16)
            }
         );
      }

      @Override
      protected void beginSection(AbilitySection section) {
         if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            this.playAnimation(HEAL_START_ANIM);
         } else if (section.sectionType != AbilitySection.AbilitySectionType.ACTIVE) {
            this.playAnimation(HEAL_END_ANIM);
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getUser().getTarget() != null) {
            this.getUser().getLookControl().setLookAt(this.getUser().getTarget(), this.getUser().getMaxHeadYRot(), this.getUser().getMaxHeadXRot());
            this.getUser().lookAt(this.getUser().getTarget(), this.getUser().getMaxHeadYRot(), this.getUser().getMaxHeadXRot());
         }

         if (this.getTicksInUse() == 6) {
            this.getUser().playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_UMVUTHANA_HEAL_START.get(this.rand.nextInt(3))).get(), 4.0F, 1.0F);
            MMCommon.PROXY.playSunblockSound(this.getUser());
         }

         if (this.getTicksInUse() >= 6) {
            EffectHandler.addOrCombineEffect(this.getUser(), MobEffects.GLOWING, 5, 0, false, false);
         }

         if (this.getUser().level().isClientSide && this.getTicksInUse() == 5 && this.getUser().headPos != null && this.getUser().headPos.length >= 1) {
            this.getUser().headPos[0] = this.getUser().position().add(0.0, this.getUser().getEyeHeight(), 0.0);
         }

         if (this.getTicksInUse() == 12) {
            this.playAnimation(HEAL_LOOP_ANIM);
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            this.spawnHealParticles();
            this.getUser().sunBlockTarget();
            if (!this.getLevel().isClientSide() && this.getUser().getTarget() == null) {
               AbilityHandler.INSTANCE.sendJumpToSectionMessage((EntityUmvuthana & LivingEntity)this.getUser(), this.getAbilityType(), 2);
            }
         }
      }

      public void spawnHealParticles() {
         if (this.getUser().getTarget() != null) {
            this.getUser().setHealPos(this.getUser().getTarget().position().add(new Vec3(0.0, this.getUser().getTarget().getBbHeight() / 2.0F, 0.0)));
         }

         if (this.getUser().level().isClientSide && this.getUser().barakoPos != null) {
            this.getUser().barakoPos[0] = this.getUser().getHealPos();
            if (this.getUser().headPos != null && this.getUser().headPos[0] != null) {
               double dist = Math.max(this.getUser().barakoPos[0].distanceTo(this.getUser().headPos[0]), 0.01);
               double radius = 0.5;
               double yaw = this.rand.nextFloat() * 2.0F * Math.PI;
               double pitch = this.rand.nextFloat() * 2.0F * Math.PI;
               double ox = radius * Math.sin(yaw) * Math.sin(pitch);
               double oy = radius * Math.cos(pitch);
               double oz = radius * Math.cos(yaw) * Math.sin(pitch);
               if (this.getTicksInUse() % 5 == 0) {
                  AdvancedParticleBase.spawnParticle(
                     this.getUser().level(),
                     ParticleHandler.ARROW_HEAD,
                     this.getUser().headPos[0].x(),
                     this.getUser().headPos[0].y(),
                     this.getUser().headPos[0].z(),
                     0.0,
                     0.0,
                     0.0,
                     false,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     3.5,
                     0.95,
                     0.9,
                     0.35,
                     0.75,
                     1.0,
                     Math.min(2.0 * dist, 60.0),
                     true,
                     false,
                     new ParticleComponent[]{
                        new ParticleComponent.Attractor(this.getUser().barakoPos, 0.5F, 0.2F, ParticleComponent.Attractor.EnumAttractorBehavior.LINEAR),
                        new RibbonComponent(
                           ParticleHandler.RIBBON_FLAT,
                           10,
                           0.0,
                           0.0,
                           0.0,
                           0.12F,
                           0.95,
                           0.9,
                           0.35,
                           0.75,
                           true,
                           true,
                           new ParticleComponent[]{
                              new RibbonComponent.PropertyOverLength(
                                 RibbonComponent.PropertyOverLength.EnumRibbonProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F)
                              )
                           }
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_X,
                           new ParticleComponent.Oscillator(0.0F, (float)ox, (float)(1.0 * dist), 2.5F),
                           true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y,
                           new ParticleComponent.Oscillator(0.0F, (float)oy, (float)(1.0 * dist), 2.5F),
                           true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Z,
                           new ParticleComponent.Oscillator(0.0F, (float)oz, (float)(1.0 * dist), 2.5F),
                           true
                        ),
                        new ParticleComponent.FaceMotion(),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                           new ParticleComponent.KeyTrack(new float[]{0.0F, 0.0F, 1.0F}, new float[]{0.0F, 0.05F, 0.06F}),
                           false
                        )
                     }
                  );
               }

               if (this.getTicksInUse() % 5 == 0) {
                  AdvancedParticleBase.spawnParticle(
                     this.getUser().level(),
                     ParticleHandler.RING2,
                     this.getUser().headPos[0].x(),
                     this.getUser().headPos[0].y(),
                     this.getUser().headPos[0].z(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     1.5,
                     1.0,
                     0.8745098F,
                     0.25882354F,
                     1.0,
                     1.0,
                     15.0,
                     true,
                     false,
                     new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 10.0F), false
                        )
                     }
                  );
               }

               int spawnFreq = 5;
               if (this.getTicksInUse() % spawnFreq == 0) {
                  ParticleRibbon.spawnRibbon(
                     this.getUser().level(),
                     ParticleHandler.RIBBON_SQUIGGLE,
                     (int)(0.5 * dist),
                     this.getUser().headPos[0].x(),
                     this.getUser().headPos[0].y(),
                     this.getUser().headPos[0].z(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.5,
                     0.95,
                     0.9,
                     0.35,
                     0.75,
                     1.0,
                     spawnFreq,
                     true,
                     new ParticleComponent[]{
                        new RibbonComponent.BeamPinning(this.getUser().headPos, this.getUser().barakoPos), new RibbonComponent.PanTexture(0.0F, 1.0F)
                     }
                  );
               }
            }
         }
      }

      @Override
      public boolean damageInterrupts() {
         return false;
      }
   }

   private static class UmvuthanaHurtAbility extends HurtAbility<EntityUmvuthana> {
      private static final RawAnimation HURT_RIGHT_AGGRESSIVE_ANIM = RawAnimation.begin().thenPlay("hurt_right_aggressive");
      private static final RawAnimation HURT_LEFT_AGGRESSIVE_ANIM = RawAnimation.begin().thenPlay("hurt_left_aggressive");
      private static final RawAnimation HURT_RIGHT_NEUTRAL_ANIM = RawAnimation.begin().thenPlay("hurt_right_neutral");
      private static final RawAnimation HURT_LEFT_NEUTRAL_ANIM = RawAnimation.begin().thenPlay("hurt_left_neutral");

      public UmvuthanaHurtAbility(AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaHurtAbility> abilityType, EntityUmvuthana user) {
         super(abilityType, user, RawAnimation.begin(), 12);
      }

      @Override
      public RawAnimation getAnimation() {
         if (this.getUser().isAggressive()) {
            return this.getUser().random.nextBoolean() ? HURT_RIGHT_AGGRESSIVE_ANIM : HURT_LEFT_AGGRESSIVE_ANIM;
         } else {
            return this.getUser().random.nextBoolean() ? HURT_RIGHT_NEUTRAL_ANIM : HURT_LEFT_NEUTRAL_ANIM;
         }
      }
   }

   private static class UmvuthanaTeleportAbility extends Ability<EntityUmvuthana> {
      private Vec3 teleportStart;
      private static int ACTIVE_DURATION = 7;
      private static final RawAnimation TELEPORT_START_ANIM = RawAnimation.begin().then("teleport_start", LoopType.PLAY_ONCE);
      private static final RawAnimation TELEPORT_LOOP_ANIM = RawAnimation.begin().thenLoop("teleport_loop");
      private static final RawAnimation TELEPORT_END_ANIM = RawAnimation.begin().then("teleport_end", LoopType.PLAY_ONCE);

      public UmvuthanaTeleportAbility(AbilityType<EntityUmvuthana, ? extends Ability> abilityType, EntityUmvuthana user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 7),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, ACTIVE_DURATION),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 13)
            }
         );
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            this.playAnimation(TELEPORT_START_ANIM);
         }

         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            this.teleportStart = this.getUser().position();
            this.playAnimation(TELEPORT_LOOP_ANIM);
         }

         if (section.sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
            this.playAnimation(TELEPORT_END_ANIM);
         }
      }

      @Override
      protected void endSection(AbilitySection section) {
         super.endSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.getUser().teleportDestination != null) {
            this.getUser().teleportTo(this.getUser().teleportDestination.x(), this.getUser().teleportDestination.y(), this.getUser().teleportDestination.z());
            this.getUser().setDeltaMovement(0.0, 0.0, 0.0);
            this.getUser().getNavigation().stop();
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 2) {
            this.getUser().playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_UMVUTHANA_TELEPORT.get(this.rand.nextInt(3))).get(), 3.0F, 1.0F);
         }

         if (this.getTicksInUse() == 16) {
            this.getUser().playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_UMVUTHANA_TELEPORT.get(this.rand.nextInt(3))).get(), 3.0F, 1.2F);
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE
            && this.teleportStart != null
            && this.getUser().teleportDestination != null) {
            float t = (float)this.getTicksInSection() / ACTIVE_DURATION;
            t = (float)(0.5 - 0.5 * Math.cos(t * Math.PI));
            Vec3 newPos = this.teleportStart.add(this.getUser().teleportDestination.subtract(this.teleportStart).scale(t));
            this.getUser().teleportTo(newPos.x(), newPos.y(), newPos.z());
            this.getUser().getNavigation().stop();
         }

         if (this.getUser().getTarget() != null) {
            this.getUser().getLookControl().setLookAt(this.getUser().getTarget(), 30.0F, 30.0F);
         }

         if (this.getUser().level().isClientSide) {
            this.getUser().myPos[0] = this.getUser().position().add(0.0, 1.2F, 0.0);
            if (this.getTicksInUse() == 5) {
               ParticleComponent.KeyTrack keyTrack1 = ParticleComponent.KeyTrack.oscillate(0.0F, 2.0F, 24);
               ParticleComponent.KeyTrack keyTrack2 = new ParticleComponent.KeyTrack(new float[]{0.0F, 18.0F, 18.0F, 0.0F}, new float[]{0.0F, 0.2F, 0.8F, 1.0F});
               AdvancedParticleBase.spawnParticle(
                  this.getUser().level(),
                  ParticleHandler.SUN,
                  this.getUser().getX(),
                  this.getUser().getY(),
                  this.getUser().getZ(),
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
                  1.0,
                  1.0,
                  15.0,
                  true,
                  false,
                  new ParticleComponent[]{
                     new ParticleComponent.PinLocation(this.getUser().myPos),
                     new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, keyTrack2, false),
                     new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, keyTrack1, true),
                     new RibbonComponent(
                        ParticleHandler.RIBBON_FLAT,
                        10,
                        0.0,
                        0.0,
                        0.0,
                        0.12F,
                        0.95,
                        0.9,
                        0.35,
                        0.75,
                        true,
                        true,
                        new ParticleComponent[]{
                           new RibbonComponent.PropertyOverLength(
                              RibbonComponent.PropertyOverLength.EnumRibbonProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F)
                           )
                        }
                     )
                  }
               );
            }

            this.getUser().myPos[0] = this.getUser().position().add(0.0, 1.2F, 0.0);
            if (this.getTicksInUse() == 4 || this.getTicksInUse() == 18) {
               int num = 5;

               for (int i = 0; i < num * num; i++) {
                  Vec3 v = new Vec3((0.3 + 0.15 * this.rand.nextFloat()) * 0.8, 0.0, 0.0);
                  float increment = (float) (Math.PI * 2) / num;
                  v = v.yRot(increment * this.rand.nextFloat() + increment * ((float)i / num));
                  v = v.zRot(increment * this.rand.nextFloat() + increment * (i % num));
                  AdvancedParticleBase.spawnParticle(
                     this.getUser().level(),
                     ParticleHandler.PIXEL,
                     this.getUser().myPos[0].x(),
                     this.getUser().myPos[0].y(),
                     this.getUser().myPos[0].z(),
                     v.x(),
                     v.y(),
                     v.z(),
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     4.0,
                     0.98,
                     0.94,
                     0.39,
                     1.0,
                     0.8,
                     6.0F + this.rand.nextFloat() * 4.0F,
                     true,
                     false,
                     new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.KeyTrack(new float[]{4.0F, 0.0F}, new float[]{0.8F, 1.0F}),
                           false
                        )
                     }
                  );
               }
            }
         }
      }

      @Override
      public boolean canCancelActiveAbility() {
         return this.getUser().getActiveAbility() instanceof EntityUmvuthana.UmvuthanaHealAbility;
      }
   }
}
