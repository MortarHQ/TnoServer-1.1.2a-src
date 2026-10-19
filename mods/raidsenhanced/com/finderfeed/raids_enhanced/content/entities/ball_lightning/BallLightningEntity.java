package com.finderfeed.raids_enhanced.content.entities.ball_lightning;

import com.finderfeed.fdlib.FDHelpers;
import com.finderfeed.fdlib.util.FDTargetFinder;
import com.finderfeed.raids_enhanced.content.particles.SimpleTexturedParticleOptions;
import com.finderfeed.raids_enhanced.init.REConfigs;
import com.finderfeed.raids_enhanced.init.REEntities;
import com.finderfeed.raids_enhanced.init.REParticles;
import com.finderfeed.raids_enhanced.init.RESounds;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;

public class BallLightningEntity extends Entity {
   private UUID uuid;

   public static void summon(LivingEntity owner, Level level, Vec3 pos, Vec3 deltaMovement) {
      BallLightningEntity ballLightningEntity = new BallLightningEntity(REEntities.BALL_LIGHTNING.get(), level);
      ballLightningEntity.setPos(pos);
      ballLightningEntity.uuid = owner.getUUID();
      ballLightningEntity.setDeltaMovement(deltaMovement);
      level.addFreshEntity(ballLightningEntity);
   }

   public BallLightningEntity(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.tickCount > 1000) {
            this.remove(RemovalReason.DISCARDED);
         }

         LivingEntity owner = this.getOwner();
         if (owner == null) {
            this.remove(RemovalReason.DISCARDED);
         }

         this.detectAndExplode();
      }

      this.setPos(this.position().add(this.getDeltaMovement()));
   }

   private void detectAndExplode() {
      Vec3 start = this.position();
      Vec3 end = start.add(this.getDeltaMovement());
      ClipContext clipContext = new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, CollisionContext.empty());
      BlockHitResult result = this.level().clip(clipContext);
      LivingEntity owner = this.getOwner();
      if (result.getType() != Type.MISS) {
         this.explode(owner, this.position(), null);
      } else {
         List<Entity> entities = FDHelpers.traceEntities(
            this.level(), start, end, 0.1F, entityx -> entityx instanceof LivingEntity livingEntity && entityx != owner
         );
         if (!entities.isEmpty()) {
            Entity entity = entities.get(this.random.nextInt(entities.size()));
            Vec3 bbCenter = entity.getBoundingBox().getBottomCenter();
            Vec3 pos = new Vec3(bbCenter.x, Mth.clamp(this.position().y, bbCenter.y, bbCenter.y + entity.getBbHeight()), bbCenter.z);
            this.explode(owner, pos, (LivingEntity)entity);
         }
      }
   }

   private void explode(LivingEntity owner, Vec3 explodePos, LivingEntity guaranteedHit) {
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(
            new SimpleTexturedParticleOptions(REParticles.BALL_LIGHTNING_EXPLOSION.get(), 1.0F, 3),
            explodePos.x,
            explodePos.y,
            explodePos.z,
            1,
            0.0,
            0.0,
            0.0,
            0.0
         );
      }

      this.level()
         .playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            RESounds.ENGINEER_BALL_LIGHTNING_EXPLOSION.get(),
            SoundSource.HOSTILE,
            2.0F,
            this.random.nextFloat() * 0.2F + 0.9F
         );
      DamageSources damageSources = this.level().damageSources();
      float damage;
      DamageSource source;
      if (owner != null) {
         damage = (float)owner.getAttributeValue(Attributes.ATTACK_DAMAGE);
         source = damageSources.mobAttack(owner);
         if (owner instanceof Player player) {
            damage = REConfigs.CONFIG.get().zapperStaffBallLightningDamage;
         }
      } else {
         damage = 5.0F;
         source = damageSources.generic();
      }

      ArrayList<LivingEntity> targets = new ArrayList<>(FDTargetFinder.getEntitiesInSphere(LivingEntity.class, this.level(), explodePos, 2.0F, e -> e != owner));
      if (guaranteedHit != null) {
         targets.add(guaranteedHit);
      }

      for (LivingEntity entity : targets) {
         entity.hurt(source, damage);
      }

      this.setRemoved(RemovalReason.DISCARDED);
   }

   private LivingEntity getOwner() {
      return this.level() instanceof ServerLevel serverLevel && this.uuid != null && serverLevel.getEntity(this.uuid) instanceof LivingEntity livingEntity
         ? livingEntity
         : null;
   }

   protected void defineSynchedData(Builder p_326003_) {
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      if (tag.contains("owner")) {
         this.uuid = tag.getUUID("owner");
      }
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      if (this.uuid != null) {
         tag.putUUID("owner", this.uuid);
      }
   }
}
