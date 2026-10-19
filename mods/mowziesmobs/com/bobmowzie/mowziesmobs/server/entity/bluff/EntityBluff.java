package com.bobmowzie.mowziesmobs.server.entity.bluff;

import com.bobmowzie.mowziesmobs.client.particle.AdvancedTerrainParticle;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.DieAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.HurtAbility;
import com.bobmowzie.mowziesmobs.server.ai.UseAbilityAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityFissure;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.Animation.LoopType;

public class EntityBluff extends MowzieGeckoEntity {
   private float allowedHeightOffset = 0.5F;
   private int nextHeightOffsetChangeTick;
   public Vec3[] feetPos;
   public Vec3[] corePos;
   private GroundPathNavigation groundNav;
   private MoveControl groundMoveControl;
   private FlyingPathNavigation flyingNav;
   private FlyingMoveControl flyingMoveControl;
   public static final AbilityType<EntityBluff, HurtAbility<EntityBluff>> HURT_ABILITY = new AbilityType(
      "bluff_hurt", (type, entity) -> new HurtAbility<>(type, entity, RawAnimation.begin().thenPlay("hurt"), 7, 0)
   );
   public static final AbilityType<EntityBluff, DieAbility<EntityBluff>> DIE_ABILITY = new AbilityType(
      "bluff_die", (type, entity) -> new DieAbility<>(type, entity, RawAnimation.begin().thenPlay("death"), 50)
   );
   public static final AbilityType<EntityBluff, EntityBluff.BluffAttackAbility> ATTACK_ABILITY = new AbilityType(
      "bluff_attack", EntityBluff.BluffAttackAbility::new
   );

   public EntityBluff(EntityType<? extends MowzieEntity> type, Level world) {
      super(type, world);
      this.xpReward = 14;
      this.groundMoveControl = this.moveControl;
      this.flyingMoveControl = new FlyingMoveControl(this, 10, true);
      if (world.isClientSide) {
         this.feetPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.corePos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
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

   @Nullable
   protected SoundEvent getHurtSound(DamageSource p_21239_) {
      return (SoundEvent)MMSounds.ENTITY_BLUFF_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      this.playSound((SoundEvent)MMSounds.ENTITY_BLUFF_DEATH.get(), 1.0F, 1.1F);
      return null;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)MMSounds.ENTITY_BLUFF_IDLE.get();
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, ATTACK_ABILITY));
      this.goalSelector.addGoal(1, new UseAbilityAI<>(this, DIE_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, HURT_ABILITY, false));
      this.goalSelector.addGoal(3, new EntityBluff.BluffNoPathGoal(this));
      this.goalSelector.addGoal(4, new EntityBluff.BluffAttackGoal(this));
      this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, (new HurtByTargetGoal(this) {
         protected boolean canAttack(@Nullable LivingEntity entity, TargetingConditions conditions) {
            return !(entity instanceof EntityBluff) && super.canAttack(entity, conditions);
         }
      }).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, EntitySculptor.class, true));
   }

   @Override
   protected <E extends GeoEntity> void loopingAnimations(AnimationState<E> event) {
      super.loopingAnimations(event);
      event.getController().transitionLength(5);
   }

   @Override
   public AbilityType<?, ?>[] getAbilities() {
      return new AbilityType[]{HURT_ABILITY, DIE_ABILITY, ATTACK_ABILITY};
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .add(Attributes.ATTACK_DAMAGE, 18.0)
         .add(Attributes.MAX_HEALTH, 45.0)
         .add(Attributes.MOVEMENT_SPEED, 0.23F)
         .add(Attributes.FLYING_SPEED, 0.23F)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.FOLLOW_RANGE, 32.0);
   }

   protected PathNavigation createNavigation(Level level) {
      this.groundNav = new GroundPathNavigation(this, level) {
         protected boolean canUpdatePath() {
            return super.canUpdatePath() || EntityBluff.this.navigation == EntityBluff.this.flyingNav;
         }
      };
      this.flyingNav = new FlyingPathNavigation(this, level);
      return this.groundNav;
   }

   public void setFlying(boolean flying) {
      if (flying) {
         this.moveControl = this.flyingMoveControl;
         this.navigation = this.flyingNav;
         this.setNoGravity(true);
      } else {
         this.moveControl = this.groundMoveControl;
         this.navigation = this.groundNav;
         this.setNoGravity(false);
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level().isClientSide && this.level().getDifficulty() == Difficulty.PEACEFUL) {
         this.discard();
      }

      if (this.getActiveAbilityType() == DIE_ABILITY && this.getActiveAbility().getTicksInUse() < 14) {
         this.yBodyRot = this.yHeadRot = this.yRotO;
         if (this.level().isClientSide) {
            for (int i = 0; i < 4; i++) {
               if (this.random.nextFloat() < 0.1F) {
                  AdvancedParticleBase.spawnParticle(
                     this.level(),
                     ParticleHandler.PIXEL,
                     this.getRandomX(0.4F),
                     this.getY() + 1.0,
                     this.getRandomZ(0.4F),
                     0.0,
                     this.random.nextFloat() / 15.0F,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     1.3 + this.random.nextFloat() * 1.0F,
                     0.63671875,
                     0.96484375,
                     0.2890625,
                     0.5,
                     0.9,
                     17.0F + this.random.nextFloat() * 10.0F,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                           new ParticleComponent.KeyTrack(new float[]{1.0F, 0.0F}, new float[]{0.0F, 1.0F}),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.MOTION_Y,
                           new ParticleComponent.KeyTrack(new float[]{0.1F, 0.0F}, new float[]{0.0F, 1.0F}),
                           false
                        )
                     }
                  );
               }
            }
         }
      }

      if (this.level().isClientSide && this.isAlive()) {
         if (this.feetPos != null && this.feetPos.length > 0) {
            this.feetPos[0] = this.position().add(0.0, 0.05F, 0.0);
            if (this.tickCount % 4 == 0) {
               AdvancedParticleBase.spawnParticle(
                  this.level(),
                  ParticleHandler.RING2,
                  this.feetPos[0].x(),
                  this.feetPos[0].y(),
                  this.feetPos[0].z(),
                  0.0,
                  0.0,
                  0.0,
                  false,
                  0.0,
                  Math.PI / 2,
                  0.0,
                  0.0,
                  1.5,
                  0.83F,
                  1.0,
                  0.39F,
                  1.0,
                  1.0,
                  20.0,
                  true,
                  false,
                  new ParticleComponent[]{
                     new ParticleComponent.PinLocation(this.feetPos),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 7.0F), false
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y, ParticleComponent.KeyTrack.startAndEnd(0.0F, 1.5F), true
                     )
                  }
               );
            }
         }

         BlockState state = this.level().getBlockState(this.getOnPos());
         if (EffectGeomancy.isBlockUseable(state) && this.feetPos != null && this.feetPos.length > 0 && this.tickCount % 2 == 0) {
            Vec3 pos = new Vec3(1.0, 0.0, 0.0).yRot((float)(this.random.nextDouble() * Math.PI * 2.0)).scale(this.random.nextFloat());
            float phaseOffset = this.random.nextFloat();
            float scale = (float)this.random.nextGaussian() * 0.2F + 0.3F;
            AdvancedTerrainParticle.spawnTerrainParticle(
               this.level(),
               ParticleHandler.TERRAIN,
               this.getX() + pos.x(),
               this.getY() + pos.y() + 1.0,
               this.getZ() + pos.z(),
               0.0,
               0.0,
               0.0,
               0.0,
               1.0,
               1.0,
               25.0F + this.random.nextFloat() * 5.0F,
               state,
               new ParticleComponent[]{
                  new ParticleComponent.Orbit(
                     this.feetPos,
                     ParticleComponent.KeyTrack.startAndEnd(0.0F + phaseOffset, 0.8F + phaseOffset),
                     ParticleComponent.KeyTrack.startAndEnd(this.random.nextFloat() * 0.75F, 0.1F + this.random.nextFloat()),
                     ParticleComponent.constant(0.0F),
                     ParticleComponent.constant(1.0F),
                     ParticleComponent.constant(0.0F),
                     false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y, ParticleComponent.KeyTrack.startAndEnd(0.0F, 1.1F), true
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     new ParticleComponent.KeyTrack(new float[]{0.0F, scale * 1.0F, scale * 1.0F, 0.0F}, new float[]{0.0F, 0.1F, 0.9F, 1.0F}),
                     false
                  )
               }
            );
         }
      }
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      return source == this.damageSources().fall() ? false : super.hurt(source, damage);
   }

   public void aiStep() {
      if (!this.onGround() && this.getDeltaMovement().y < 0.0) {
         this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
      }

      super.aiStep();
   }

   @NotNull
   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LootTableHandler.BLUFF;
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      return super.checkSpawnRules(world, reason)
         && this.getEntitiesNearby(EntitySculptor.class, 8.0, 8.0, 8.0, 8.0).isEmpty()
         && world.getDifficulty() != Difficulty.PEACEFUL;
   }

   protected void checkFallDamage(double p_29370_, boolean p_29371_, BlockState p_29372_, BlockPos p_29373_) {
   }

   public static class BluffAttackAbility extends Ability<EntityBluff> {
      private static int STARTUP_DURATION = 9;
      private Vec3 prevTargetPos;
      public static AbilitySection[] SECTION_TRACK = new AbilitySection[]{
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 11),
         new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.MISC),
         new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 34)
      };
      private static final RawAnimation ATTACK_START_ANIMATION = RawAnimation.begin().then("attack_start", LoopType.HOLD_ON_LAST_FRAME);
      private static final RawAnimation ATTACK_END_ANIMATION = RawAnimation.begin().then("attack_end", LoopType.HOLD_ON_LAST_FRAME);

      public BluffAttackAbility(AbilityType abilityType, EntityBluff user) {
         super(abilityType, user, SECTION_TRACK);
      }

      @Override
      public void start() {
         super.start();
         this.getUser().setFlying(false);
         LivingEntity entityTarget = this.getUser().getTarget();
         if (entityTarget != null) {
            this.prevTargetPos = entityTarget.position().add(0.0, entityTarget.getBbHeight() / 2.0, 0.0);
         }

         this.playAnimation(ATTACK_START_ANIMATION);
         this.getUser().playSound((SoundEvent)MMSounds.ENTITY_BLUFF_ATTACK.get(), 1.0F, 1.2F);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         LivingEntity entityTarget = this.getUser().getTarget();
         if (entityTarget != null) {
            this.getUser().getLookControl().setLookAt(entityTarget, 30.0F, 30.0F);
            this.getUser().setYRot(this.getUser().getYHeadRot());
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            this.getUser().setDeltaMovement(0.0, 0.0, 0.0);
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.MISC
            || this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            double fallSpeed = this.getUser().getDeltaMovement().y;
            fallSpeed -= 2.0;
            fallSpeed = Math.max(fallSpeed, -7.0);
            this.getUser().setDeltaMovement(0.0, fallSpeed, 0.0);
            this.getUser().hasImpulse = true;
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.MISC && this.getUser().onGround()) {
            this.jumpToSection(2);
         }
      }

      @Override
      public <E extends GeoEntity> PlayState animationPredicate(AnimationState<E> e, GeckoPlayer.Perspective perspective) {
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            e.getController().transitionLength(4);
         }

         return super.animationPredicate(e, perspective);
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            this.getUser().playSound((SoundEvent)((DeferredHolder)MMSounds.EFFECT_GEOMANCY_HIT_MEDIUM.get(0)).get(), 1.0F, 1.2F);
            if (!this.getLevel().isClientSide()) {
               this.shootFissureAtTarget(this.getUser().getTarget(), this.prevTargetPos, 0.1F);
               List<Entity> entitiesHit = this.getLevel().getEntities(this.getUser(), this.getUser().getBoundingBox().inflate(0.4), e -> e != this.getUser());
               double damage = 10.0;
               AttributeInstance attrib = this.getUser().getAttribute(Attributes.ATTACK_DAMAGE);
               if (attrib != null) {
                  damage = attrib.getValue();
               }

               damage *= ConfigHandler.COMMON.MOBS.BLUFF.combatConfig.attackMultiplier.get();

               for (Entity entity : entitiesHit) {
                  if (!(entity instanceof EntityBluff)) {
                     entity.hurt(this.getUser().damageSources().mobAttack(this.getUser()), (float)damage);
                  }
               }
            }

            this.playAnimation(ATTACK_END_ANIMATION);
            if (this.getLevel().isClientSide()) {
               BlockState blockBeneath = this.getUser().level().getBlockState(this.getUser().getOnPos());

               for (byte i = 0; i < 30; i++) {
                  this.getLevel()
                     .addParticle(
                        new BlockParticleOption(ParticleTypes.BLOCK, blockBeneath),
                        this.getUser().getX(),
                        this.getUser().getBlockY() + 0.1F,
                        this.getUser().getZ(),
                        this.getUser().random.nextFloat() * 3.0F - 1.5F,
                        2.2,
                        this.getUser().random.nextFloat() * 3.0F - 1.5F
                     );
               }
            }
         }
      }

      public void shootFissureAtTarget(LivingEntity target, Vec3 prevTargetPos, float timeScale) {
         EntityFissure fissure = new EntityFissure((EntityType<? extends EntityFissure>)EntityHandler.FISSURE.get(), this.getLevel());
         fissure.setOwner(this.getUser());
         fissure.setPos(this.getUser().position());
         Vec3 shootVec;
         if (target != null) {
            float speed = 2.0F / EntityFissure.TICKS_PER_PIECE;
            Vec3 targetPos = target.position().add(0.0, target.getBbHeight() / 2.0, 0.0);
            double timeToReach = fissure.position().subtract(targetPos).length() / speed;
            Vec3 targetMovement = targetPos.subtract(prevTargetPos).scale(timeToReach * timeScale * 1.0 / 4.0);
            targetMovement = targetMovement.multiply(1.0, 0.0, 1.0);
            Vec3 futureTargetPos = targetPos.add(targetMovement);
            Vec3 projectileMid = fissure.position().add(0.0, fissure.getBbHeight() / 2.0, 0.0);
            shootVec = futureTargetPos.subtract(projectileMid).normalize();
         } else {
            shootVec = this.getUser().getForward();
         }

         fissure.shoot(shootVec.x, shootVec.z);
         this.getLevel().addFreshEntity(fissure);
      }
   }

   static class BluffAttackGoal extends Goal {
      private final EntityBluff bluff;
      private final double speedModifier = 1.0;
      private int attackIntervalMin = 50;
      private final float attackMaxRadiusSqr = 144.0F;
      private final float attackMinRadiusSqr = 36.0F;
      private int attackTime = -1;
      private int seeTime;
      private boolean strafingClockwise;
      private boolean strafingBackwards;
      private boolean isStrafing;
      private int strafingTime = -1;

      public BluffAttackGoal(EntityBluff bluff) {
         this.bluff = bluff;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         LivingEntity livingentity = this.bluff.getTarget();
         return livingentity != null && livingentity.isAlive() && this.bluff.canAttack(livingentity);
      }

      public void start() {
         super.start();
         this.bluff.setAggressive(true);
      }

      public void stop() {
         super.stop();
         this.bluff.setAggressive(false);
         this.seeTime = 0;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity livingentity = this.bluff.getTarget();
         if (livingentity != null) {
            double d0 = this.bluff.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            boolean flag = this.bluff.getSensing().hasLineOfSight(livingentity);
            boolean flag1 = this.seeTime > 0;
            if (flag != flag1) {
               this.seeTime = 0;
            }

            if (flag) {
               this.seeTime++;
            } else {
               this.seeTime--;
            }

            if (!(d0 > 144.0) && this.seeTime >= 20) {
               this.bluff.getNavigation().stop();
               this.strafingTime++;
            } else {
               this.bluff.getNavigation().moveTo(livingentity, 1.0);
               this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
               if (this.bluff.getRandom().nextFloat() < 0.3) {
                  this.isStrafing = !this.isStrafing;
               }

               if (this.bluff.getRandom().nextFloat() < 0.3) {
                  this.strafingClockwise = !this.strafingClockwise;
               }

               if (this.bluff.getRandom().nextFloat() < 0.3) {
                  this.strafingBackwards = !this.strafingBackwards;
               }

               this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
               if (d0 > 144.0) {
                  this.strafingBackwards = false;
               } else if (d0 < 36.0) {
                  this.strafingBackwards = true;
               }

               if (this.isStrafing) {
                  this.bluff.getMoveControl().strafe(this.strafingBackwards ? -0.3F : 0.3F, this.strafingClockwise ? 0.3F : -0.3F);
               } else {
                  this.bluff.getMoveControl().strafe(0.0F, 0.0F);
               }

               this.bluff.lookAt(livingentity, 30.0F, 30.0F);
            }

            this.bluff.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            if (--this.attackTime <= 0 && this.seeTime >= -60 && d0 < 144.0) {
               this.bluff.sendAbilityMessage(EntityBluff.ATTACK_ABILITY);
               this.attackTime = this.attackIntervalMin + this.bluff.random.nextInt(40);
            }
         }
      }
   }

   public static class BluffNoPathGoal extends Goal {
      private final EntityBluff bluff;

      public BluffNoPathGoal(EntityBluff bluff) {
         this.bluff = bluff;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         LivingEntity target = this.bluff.getTarget();
         if (target == null) {
            return false;
         }

         if (!this.bluff.onGround()) {
            return false;
         }

         Path path = this.bluff.groundNav.createPath(target, 0);
         return path == null || path.getEndNode() == null || path.getEndNode().asVec3().add(0.5, 0.5, 0.5).distanceToSqr(path.getTarget().getCenter()) > 4.0;
      }

      public boolean canContinueToUse() {
         LivingEntity target = this.bluff.getTarget();
         if (target == null) {
            return false;
         }

         Path path = this.bluff.groundNav.createPath(target, 0);
         return path == null || path.getEndNode() == null || path.getEndNode().asVec3().add(0.5, 0.5, 0.5).distanceToSqr(path.getTarget().getCenter()) > 4.0;
      }

      public void start() {
         super.start();
         this.bluff.setFlying(true);
      }

      public void tick() {
         super.tick();
         LivingEntity target = this.bluff.getTarget();
         if (target != null) {
            this.bluff.getNavigation().moveTo(target, 1.2);
            this.bluff.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }
      }

      public void stop() {
         super.stop();
         this.bluff.getNavigation().stop();
         this.bluff.setFlying(false);
      }
   }
}
