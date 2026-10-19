package com.finderfeed.raids_enhanced.content.entities.raid_blimp.cannons;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.ModelSystem;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.entity_model_system.EntityModelSystem;
import com.finderfeed.fdlib.util.FDProjectile;
import com.finderfeed.fdlib.util.FDTargetFinder;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.RaidBlimp;
import com.finderfeed.raids_enhanced.init.REEntities;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RaidBlimpCannonProjectile extends FDProjectile implements AnimatedObject {
   private EntityModelSystem<?> modelSystem = EntityModelSystem.create(this);
   private UUID blimp;

   public static void summon(RaidBlimp raidBlimp, Vec3 pos, Vec3 direction) {
      RaidBlimpCannonProjectile p = new RaidBlimpCannonProjectile(REEntities.RAID_BLIMP_CANNON_PROJECTILE.get(), raidBlimp.level());
      p.blimp = raidBlimp.getUUID();
      p.setDeltaMovement(direction.normalize().scale(2.0));
      p.setPos(pos);
      raidBlimp.level().addFreshEntity(p);
   }

   public RaidBlimpCannonProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
      super(type, level);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.tickCount > 2000) {
            this.explode(this.position());
         }
      } else {
         this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + this.getBbHeight() / 2.0F, this.getZ(), 0.0, 0.0, 0.0);
      }

      this.tickModelSystem();
   }

   protected void onHitBlock(BlockHitResult res) {
      super.onHitBlock(res);
      if (!this.level().isClientSide) {
         this.explode(res.getLocation());
      }
   }

   protected void onHitEntity(EntityHitResult res) {
      super.onHitEntity(res);
      if (!this.level().isClientSide) {
         Entity entity = ((ServerLevel)this.level()).getEntity(this.blimp);
         if (res.getEntity() != entity) {
            this.explode(res.getLocation());
         }
      }
   }

   private void explode(Vec3 pos) {
      Entity entity = ((ServerLevel)this.level()).getEntity(this.blimp);

      for (LivingEntity e : FDTargetFinder.getEntitiesInSphere(LivingEntity.class, this.level(), pos, 3.0F)) {
         if (e != entity) {
            float damage = 5.0F;
            DamageSource damageSource;
            if (entity != null) {
               damageSource = this.level().damageSources().mobAttack((LivingEntity)entity);
               damage = (float)((LivingEntity)entity).getAttributeValue(Attributes.ATTACK_DAMAGE);
            } else {
               damageSource = this.level().damageSources().generic();
            }

            e.hurt(damageSource, damage);
         }
      }

      this.level().playSound(null, pos.x, pos.y, pos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 3.0F, 1.0F);
      this.level().playSound(null, pos.x, pos.y, pos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 3.0F, 1.5F);

      for (ServerPlayer serverPlayer : FDTargetFinder.getEntitiesInSphere(ServerPlayer.class, this.level(), pos, 160.0F)) {
         ((ServerLevel)this.level()).sendParticles(serverPlayer, ParticleTypes.EXPLOSION_EMITTER, true, pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
      }

      this.remove(RemovalReason.DISCARDED);
   }

   public ModelSystem getModelSystem() {
      return this.modelSystem;
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.modelSystem.asServerside().syncToPlayer(player);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      this.modelSystem.saveAttachments(this.level().registryAccess(), tag);
      if (this.blimp != null) {
         tag.putUUID("blimp", this.blimp);
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.modelSystem.loadAttachments(this.level().registryAccess(), tag);
      if (tag.contains("blimp")) {
         this.blimp = tag.getUUID("blimp");
      }
   }

   public boolean deflect(ProjectileDeflection p_341900_, @Nullable Entity p_341912_, @Nullable Entity p_341932_, boolean p_341948_) {
      return false;
   }
}
