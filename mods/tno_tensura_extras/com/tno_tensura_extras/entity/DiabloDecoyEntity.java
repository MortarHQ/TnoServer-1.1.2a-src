package com.tno_tensura_extras.entity;

import java.util.UUID;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class DiabloDecoyEntity extends PathfinderMob implements GeoEntity {
   public static final int LIFETIME_TICKS = 400;
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.diablo.idle");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.diablo.walk");
   private static final RawAnimation WINGS_IDLE = RawAnimation.begin().thenLoop("animation.diablo.wings_idle");
   private static final RawAnimation WINGS_WALK = RawAnimation.begin().thenLoop("animation.diablo.wings_walk");
   private static final DustParticleOptions GRAY_DUST = new DustParticleOptions(new Vector3f(0.45F), 1.3F);
   private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
   private UUID ownerUuid;
   private UUID targetUuid;

   public DiabloDecoyEntity(EntityType<? extends DiabloDecoyEntity> type, Level level) {
      super(type, level);
      this.xpReward = 0;
      this.setCanPickUpLoot(false);
      this.setPersistenceRequired();
   }

   public static Builder createAttributes() {
      return createMobAttributes()
         .add(Attributes.MAX_HEALTH, 20.0)
         .add(Attributes.ATTACK_DAMAGE, 0.0)
         .add(Attributes.ARMOR, 0.0)
         .add(Attributes.ARMOR_TOUGHNESS, 0.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.0)
         .add(Attributes.MOVEMENT_SPEED, 0.32)
         .add(Attributes.FOLLOW_RANGE, 64.0);
   }

   public void initialize(DiabloEntity owner, ServerPlayer target) {
      this.ownerUuid = owner.getUUID();
      this.targetUuid = target.getUUID();
      this.setCustomName(owner.getName());
      this.setHealth(20.0F);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.level() instanceof ServerLevel server) {
         Entity owner = this.ownerUuid == null ? null : server.getEntity(this.ownerUuid);
         Entity target = this.targetUuid == null ? null : server.getEntity(this.targetUuid);
         if (this.tickCount < 400
            && owner instanceof DiabloEntity diablo
            && diablo.isAlive()
            && target instanceof ServerPlayer player
            && diablo.isAllowedCombatTarget(player)
            && !(this.distanceToSqr(player) > 4096.0)) {
            this.getLookControl().setLookAt(player, 30.0F, 30.0F);
            if (this.distanceToSqr(player) <= 1.9599999999999997) {
               this.getNavigation().stop();
            } else if (this.tickCount % 10 == 0) {
               this.getNavigation().moveTo(player, 1.15);
            }
         } else {
            this.disappear();
         }
      }
   }

   public boolean doHurtTarget(Entity target) {
      return false;
   }

   public void die(DamageSource source) {
      this.disappear();
   }

   private void disappear() {
      if (!this.isRemoved() && this.level() instanceof ServerLevel server) {
         server.sendParticles(
            GRAY_DUST,
            this.getX(),
            this.getY() + this.getBbHeight() * 0.5,
            this.getZ(),
            36,
            this.getBbWidth() * 0.5,
            this.getBbHeight() * 0.4,
            this.getBbWidth() * 0.5,
            0.0
         );
         this.discard();
      }
   }

   public AABB getBoundingBoxForCulling() {
      return this.getBoundingBox().inflate(2.2, 0.5, 2.2);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "bodyController", 2, state -> state.setAndContinue(state.isMoving() ? WALK : IDLE)));
      controllers.add(new AnimationController(this, "wingsController", 8, state -> state.setAndContinue(state.isMoving() ? WINGS_WALK : WINGS_IDLE)));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.animationCache;
   }
}
