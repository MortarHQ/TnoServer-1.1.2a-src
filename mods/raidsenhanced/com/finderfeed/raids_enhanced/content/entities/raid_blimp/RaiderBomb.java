package com.finderfeed.raids_enhanced.content.entities.raid_blimp;

import com.finderfeed.fdlib.FDLibCalls;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.FDEntity;
import com.finderfeed.fdlib.util.FDTargetFinder;
import com.finderfeed.raids_enhanced.content.entities.player_blimp.PlayerBlimpEntity;
import com.finderfeed.raids_enhanced.init.REConfigs;
import com.finderfeed.raids_enhanced.init.REEntities;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;

public class RaiderBomb extends FDEntity {
   private UUID owner;

   public static void summon(LivingEntity raidBlimp, Vec3 pos, Vec3 startSpeed) {
      RaiderBomb bomb = new RaiderBomb(REEntities.BOMB.get(), raidBlimp.level());
      bomb.owner = raidBlimp.getUUID();
      bomb.setPos(pos);
      bomb.setDeltaMovement(startSpeed);
      raidBlimp.level().addFreshEntity(bomb);
   }

   public RaiderBomb(EntityType<?> type, Level level) {
      super(type, level);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.raycast();
         if (this.tickCount > 2000) {
            this.explode(this.position());
         }
      } else {
         for (int i = 0; i < 2; i++) {
            this.level()
               .addParticle(
                  ParticleTypes.SMOKE,
                  this.getX() + this.level().random.nextFloat() * 0.2 - 0.1,
                  this.getY() + this.getBbHeight() / 2.0F + this.level().random.nextFloat() * 0.2 - 0.1,
                  this.getZ() + this.level().random.nextFloat() * 0.2 - 0.1,
                  0.0,
                  0.0,
                  0.0
               );
            this.level()
               .addParticle(
                  ParticleTypes.FLAME,
                  this.getX() + this.level().random.nextFloat() * 0.2 - 0.1,
                  this.getY() + this.getBbHeight() / 2.0F + this.level().random.nextFloat() * 0.2 - 0.1,
                  this.getZ() + this.level().random.nextFloat() * 0.2 - 0.1,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      this.applyGravity();
      this.setPos(this.position().add(this.getDeltaMovement()));
   }

   public void raycast() {
      Vec3 deltaMovement = this.getDeltaMovement();
      Vec3 start = this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0);
      Vec3 end = start.add(deltaMovement);
      ClipContext clipContext = new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, CollisionContext.empty());
      BlockHitResult res = this.level().clip(clipContext);
      if (res.getType() != Type.MISS) {
         this.explode(res.getLocation());
      } else {
         Entity owner = null;
         if (this.owner != null) {
            owner = ((ServerLevel)this.level()).getEntity(this.owner);
         }

         EntityHitResult result = ProjectileUtil.getEntityHitResult(
            this.level(), owner, start, end, new AABB(start, end), entity -> !(entity instanceof PlayerBlimpEntity)
         );
         if (result != null) {
            this.explode(result.getLocation());
         }
      }
   }

   public void explode(Vec3 pos) {
      Entity entity = null;
      if (this.owner != null) {
         entity = ((ServerLevel)this.level()).getEntity(this.owner);
      }

      for (LivingEntity e : FDTargetFinder.getEntitiesInSphere(LivingEntity.class, this.level(), pos, 5.0F)) {
         if (e != entity) {
            float damage = 10.0F;
            DamageSource damageSource;
            if (entity != null) {
               damageSource = this.level().damageSources().mobAttack((LivingEntity)entity);
               if (entity instanceof Player player) {
                  damage = REConfigs.CONFIG.get().handcannonBombDamage;
               } else {
                  damage = (float)((LivingEntity)entity).getAttributeValue(Attributes.ATTACK_DAMAGE);
               }
            } else {
               damageSource = this.level().damageSources().generic();
            }

            e.hurt(damageSource, damage);
            Vec3 speed = e.position().subtract(this.position()).normalize().scale(0.5).add(0.0, 0.5, 0.0);
            if (e instanceof ServerPlayer serverPlayer) {
               FDLibCalls.setServerPlayerSpeed(serverPlayer, speed);
               serverPlayer.hasImpulse = true;
            } else {
               e.setDeltaMovement(speed);
               e.hasImpulse = true;
            }
         }
      }

      this.level().playSound(null, pos.x, pos.y, pos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 3.0F, 1.0F);
      this.level().playSound(null, pos.x, pos.y, pos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 3.0F, 1.5F);

      for (ServerPlayer serverPlayer : FDTargetFinder.getEntitiesInSphere(ServerPlayer.class, this.level(), pos, 160.0F)) {
         ((ServerLevel)this.level()).sendParticles(serverPlayer, ParticleTypes.EXPLOSION_EMITTER, true, pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
      }

      this.remove(RemovalReason.DISCARDED);
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.owner != null) {
         tag.putUUID("owner", this.owner);
      }
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("owner")) {
         this.owner = tag.getUUID("owner");
      }
   }

   protected void defineSynchedData(Builder p_326003_) {
   }

   protected double getDefaultGravity() {
      return 0.08;
   }
}
