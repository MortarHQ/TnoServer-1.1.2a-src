package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleRotation;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class EntityBoulderProjectile extends EntityBoulderBase {
   protected final List<Entity> ridingEntities = new ArrayList<>();
   protected boolean travelling = false;
   protected float speed = 1.5F;
   protected int damage = 10;
   private boolean didShootParticles = false;
   private static final EntityDataAccessor<Vector3f> SHOOT_DIRECTION = SynchedEntityData.defineId(EntityBoulderProjectile.class, EntityDataSerializers.VECTOR3);
   private List<Entity> hitEntities = new ArrayList<>();

   public EntityBoulderProjectile(EntityType<? extends EntityBoulderProjectile> type, Level world) {
      super(type, world);
   }

   public EntityBoulderProjectile(
      EntityType<? extends EntityBoulderProjectile> type,
      Level world,
      LivingEntity caster,
      BlockState blockState,
      BlockPos pos,
      EntityGeomancyBase.GeomancyTier tier
   ) {
      super(type, world, caster, blockState, pos, tier);
   }

   @Override
   public void setSizeParams() {
      super.setSizeParams();
      EntityGeomancyBase.GeomancyTier size = this.getTier();
      if (size == EntityGeomancyBase.GeomancyTier.MEDIUM) {
         this.damage = 14;
         this.speed = 1.4F;
      } else if (size == EntityGeomancyBase.GeomancyTier.LARGE) {
         this.damage = 18;
         this.speed = 1.2F;
      } else if (size == EntityGeomancyBase.GeomancyTier.HUGE) {
         this.damage = 25;
         this.speed = 1.1F;
      }

      this.damage = (int)(this.damage * this.getDamageMult());
   }

   protected double getDamageMult() {
      if (this.getCaster() instanceof Player) {
         return (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHREND_GAUNTLET.attackMultiplier.get();
      } else {
         return this.getCaster() instanceof EntitySculptor ? (Double)ConfigHandler.COMMON.MOBS.SCULPTOR.combatConfig.attackMultiplier.get() : 1.0;
      }
   }

   public float getSpeed() {
      return this.speed;
   }

   @NotNull
   @Override
   protected AABB makeBoundingBox() {
      AABB boundingBox = super.makeBoundingBox();
      if (this.shouldExtendBoundsDown()) {
         boundingBox = boundingBox.expandTowards(0.0, -0.5, 0.0);
      }

      if (this.isTravelling()) {
         EntityGeomancyBase.GeomancyTier size = this.getTier();
         if (size == EntityGeomancyBase.GeomancyTier.MEDIUM) {
            boundingBox = boundingBox.deflate(0.15);
         } else if (size == EntityGeomancyBase.GeomancyTier.LARGE) {
            boundingBox = boundingBox.deflate(0.35);
         } else if (size == EntityGeomancyBase.GeomancyTier.HUGE) {
            boundingBox = boundingBox.deflate(0.6);
         }
      }

      return boundingBox;
   }

   protected boolean shouldExtendBoundsDown() {
      return !this.travelling;
   }

   protected void findRidingEntities() {
      if (!(this.getCaster() instanceof EntitySculptor)) {
         if (this.ridingEntities != null) {
            this.ridingEntities.clear();
         }

         for (Entity entity : this.level()
            .getEntities(
               this,
               this.getBoundingBox().contract(0.0, this.getBbHeight() - 1.0F, 0.0).move(new Vec3(0.0, this.getBbHeight() - 0.5, 0.0)).inflate(0.6, 0.5, 0.6)
            )) {
            if (entity != null
               && entity.isPickable()
               && !(entity instanceof EntityBoulderProjectile)
               && entity.getY() >= this.getY() + 0.2
               && entity.isPushable()) {
               this.ridingEntities.add(entity);
            }
         }
      }
   }

   @Override
   public void tick() {
      if (this.startActive() && this.tickCount == 1) {
         this.activate();
      }

      super.tick();
      if (this.level().isLoaded(this.blockPosition()) && !this.level().isClientSide() && (this.getCaster() == null || this.getCaster().isRemoved())) {
         this.explode();
      }

      this.findRidingEntities();
      if (this.travelling) {
         for (Entity entity : this.ridingEntities) {
            entity.move(MoverType.SHULKER_BOX, this.getDeltaMovement().add(0.0, 0.1, 0.0));
         }
      }

      List<Entity> entitiesHit = this.getEntitiesNearby(1.7);
      if (this.travelling && !entitiesHit.isEmpty()) {
         for (Entity entity : entitiesHit) {
            if (!entity.isRemoved()
               && !(entity instanceof LivingEntity livingEntity && livingEntity.isDeadOrDying())
               && !this.level().isClientSide
               && entity != this.getCaster()
               && !entity.noPhysics
               && entity.canBeHitByProjectile()
               && !(entity instanceof ItemEntity)
               && this.travellingBlockedBy(entity)
               && (this.ridingEntities == null || !this.ridingEntities.contains(entity))
               && !this.hitEntities.contains(entity)) {
               boolean didHurt;
               if (this.getCaster() != null) {
                  didHurt = entity.hurt(this.damageSources().mobProjectile(this, this.getCaster()), this.damage);
               } else {
                  didHurt = entity.hurt(this.damageSources().generic(), this.damage);
               }

               if (didHurt) {
                  this.hitEntities.add(entity);
               }

               if (this.isAlive() && this.getTier() != EntityGeomancyBase.GeomancyTier.HUGE) {
                  this.explode();
               }
            }
         }
      }

      this.handleHitOtherBoulders();
      if (this.travelling
         && (
            !this.level()
                  .getEntities(
                     this,
                     this.getBoundingBox().inflate(0.1),
                     e -> (this.ridingEntities == null || !this.ridingEntities.contains(e)) && e.canBeCollidedWith() && this.travellingBlockedBy(e)
                  )
                  .isEmpty()
               || Iterables.size(this.level().getBlockCollisions(this, this.getBoundingBox().inflate(0.1))) > 0
         )) {
         this.explode();
      }

      if (this.level().isClientSide() && ((Vector3f)this.getEntityData().get(SHOOT_DIRECTION)).length() > 0.0F && !this.didShootParticles) {
         Vec3 ringOffset = new Vec3((Vector3f)this.getEntityData().get(SHOOT_DIRECTION)).scale(-1.0).normalize();
         ParticleRotation.OrientVector rotation = new ParticleRotation.OrientVector(ringOffset);
         AdvancedParticleBase.spawnAlwaysVisibleParticle(
            this.level(),
            ParticleHandler.RING2,
            64.0,
            (float)this.getX() + (float)ringOffset.x,
            (float)this.getY() + 0.5F + (float)ringOffset.y,
            (float)this.getZ() + (float)ringOffset.z,
            0.0,
            0.0,
            0.0,
            rotation,
            3.5,
            0.83F,
            1.0,
            0.39F,
            1.0,
            1.0,
            (int)(5.0F + 2.0F * this.getBbWidth()),
            true,
            true,
            new ParticleComponent[]{
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.7F, 0.0F), false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                  ParticleComponent.KeyTrack.startAndEnd(0.0F, (1.0F + 0.5F * this.getBbWidth()) * 8.0F * this.getShootRingParticleScale()),
                  false
               )
            }
         );
         this.didShootParticles = true;
      }
   }

   public List<Entity> getRidingEntities() {
      return this.ridingEntities;
   }

   protected boolean startActive() {
      return true;
   }

   protected boolean travellingBlockedBy(Entity entity) {
      if (this.getCaster() instanceof EntitySculptor) {
         return entity instanceof EntityEarthSpike
            ? false
            : !(entity instanceof EntityBoulderBase) || ((EntityBoulderProjectile)entity).getCaster() != this.getCaster();
      } else {
         return true;
      }
   }

   public boolean canCollideWith(Entity entity) {
      if (this.getCaster() instanceof EntitySculptor) {
         if (this.travelling && entity instanceof EntityEarthSpike) {
            return false;
         } else if (entity instanceof EntityBoulderBase && ((EntityBoulderProjectile)entity).getCaster() == this.getCaster()) {
            return false;
         } else {
            return entity == this.getCaster() ? false : super.canCollideWith(entity);
         }
      } else {
         return super.canCollideWith(entity);
      }
   }

   protected void handleHitOtherBoulders() {
      List<EntityBoulderProjectile> bouldersHit = this.level()
         .getEntitiesOfClass(EntityBoulderProjectile.class, this.getBoundingBox().inflate(0.2, 0.2, 0.2).move(this.getDeltaMovement().normalize().scale(0.5)));
      if (this.travelling && !bouldersHit.isEmpty()) {
         for (EntityBoulderProjectile otherBoulder : bouldersHit) {
            if (otherBoulder.getCaster() == this.getCaster() && !otherBoulder.travelling && this.travellingBlockedBy(otherBoulder)) {
               otherBoulder.skipAttackInteraction(this);
               this.explode();
            }
         }
      }
   }

   @Override
   public boolean isPickable() {
      return true;
   }

   public void shoot(Vec3 shootDirection) {
      this.setDeltaMovement(shootDirection);
      if (!this.travelling) {
         this.setDeathTime(60);
      }

      this.travelling = true;
      this.setBoundingBox(this.getType().getSpawnAABB(this.getX(), this.getY(), this.getZ()));
      if (this.boulderSize == EntityGeomancyBase.GeomancyTier.SMALL) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_SMALL.get(), 1.5F, 1.3F);
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.9F);
      } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.MEDIUM) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_SMALL.get(), 1.5F, 0.9F);
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.5F);
      } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.LARGE) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_SMALL.get(), 1.5F, 0.5F);
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 1.5F, 1.3F);
         EntityCameraShake.cameraShake(this.level(), this.position(), 10.0F, 0.05F, 0, 20);
      } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_MEDIUM_1.get(), 1.5F, 1.0F);
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 1.5F, 0.9F);
         EntityCameraShake.cameraShake(this.level(), this.position(), 15.0F, 0.05F, 0, 20);
      }

      this.getEntityData().set(SHOOT_DIRECTION, shootDirection.toVector3f());
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SHOOT_DIRECTION, new Vector3f(0.0F, 0.0F, 0.0F));
   }

   @Override
   public void handleEntityEvent(byte id) {
      super.handleEntityEvent(id);
   }

   protected float getShootRingParticleScale() {
      return 1.0F;
   }

   public boolean skipAttackInteraction(Entity entityIn) {
      if (this.risingTick > this.finishedRisingTick - 1 && !this.travelling) {
         if (entityIn instanceof Player player) {
            if (EffectGeomancy.canUse((Player)entityIn)) {
               if (this.ridingEntities.contains(player)) {
                  Vec3 lateralLookVec = Vec3.directionFromRotation(0.0F, player.getYRot()).normalize();
                  this.shoot(new Vec3(this.speed * 0.5 * lateralLookVec.x, this.getDeltaMovement().y, this.speed * 0.5 * lateralLookVec.z));
               } else {
                  this.shoot(player.getLookAngle().scale(this.speed * 0.5));
               }

               AbilityHandler.INSTANCE.sendAbilityMessage(player, AbilityHandler.HIT_BOULDER_ABILITY);
            } else if (!this.level().isClientSide() && this.getCaster() == player) {
               this.explode();
            }
         } else if (entityIn instanceof EntityBoulderProjectile boulder && ((EntityBoulderProjectile)entityIn).travelling) {
            Vec3 thisPos = this.position();
            Vec3 boulderPos = boulder.position();
            Vec3 velVec = thisPos.subtract(boulderPos).normalize();
            this.shoot(velVec.scale(this.speed * 0.5));
         }
      }

      return super.skipAttackInteraction(entityIn);
   }

   public boolean isTravelling() {
      return this.travelling;
   }

   public void setTravelling(boolean travel) {
      this.travelling = travel;
   }

   public void setDamage(int dam) {
      this.damage = dam;
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }
}
