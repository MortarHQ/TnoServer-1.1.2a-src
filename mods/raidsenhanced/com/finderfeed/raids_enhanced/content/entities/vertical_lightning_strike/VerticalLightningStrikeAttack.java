package com.finderfeed.raids_enhanced.content.entities.vertical_lightning_strike;

import com.finderfeed.fdlib.systems.shake.FDShakeData;
import com.finderfeed.fdlib.systems.shake.PositionedScreenShakePacket;
import com.finderfeed.fdlib.util.FDTargetFinder;
import com.finderfeed.raids_enhanced.REClientUtil;
import com.finderfeed.raids_enhanced.content.particles.SimpleTexturedParticleOptions;
import com.finderfeed.raids_enhanced.content.particles.lightning_strike.LightningStrikeParticleOptions;
import com.finderfeed.raids_enhanced.content.util.HorizontalCircleRandomDirections;
import com.finderfeed.raids_enhanced.init.REConfigs;
import com.finderfeed.raids_enhanced.init.REEntities;
import com.finderfeed.raids_enhanced.init.REParticles;
import com.finderfeed.raids_enhanced.init.RESounds;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VerticalLightningStrikeAttack extends Entity {
   public static final int PREPARATION_TIME = 15;
   public UUID owner;

   public static void summon(LivingEntity owner, Vec3 pos) {
      VerticalLightningStrikeAttack entity = new VerticalLightningStrikeAttack(REEntities.VERTICAL_LIGHTNING.get(), owner.level());
      entity.setPos(pos);
      entity.owner = owner.getUUID();
      owner.level().addFreshEntity(entity);
   }

   public VerticalLightningStrikeAttack(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.tickCount >= 16) {
            this.damageEntities();
            ((ServerLevel)this.level())
               .playSound(
                  null,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  RESounds.LIGHTNING_STRIKE.get(),
                  SoundSource.HOSTILE,
                  2.0F,
                  this.random.nextFloat() * 0.2F + 0.8F
               );
         } else if (this.tickCount == 15) {
            PositionedScreenShakePacket.send(
               (ServerLevel)this.level(), FDShakeData.builder().frequency(5.0F).amplitude(2.5F).inTime(0).stayTime(0).outTime(6).build(), this.position(), 10.0
            );
         }
      } else if (this.tickCount == 15) {
         REClientUtil.lightningDebris(this.position(), 0);
         this.level()
            .addParticle(
               new SimpleTexturedParticleOptions(REParticles.VERTICAL_LIGHTNING.get(), 2.0F, 5),
               true,
               this.getX(),
               this.getY() + 2.0,
               this.getZ(),
               0.0,
               0.0,
               0.0
            );

         for (Vec3 dir : new HorizontalCircleRandomDirections(this.level().random, 6, 0.0F)) {
            Vec3 direction = dir.add(0.0, 0.5, 0.0);
            Vec3 pos = this.position().add(direction.multiply(0.4, 0.25, 0.4));
            this.level()
               .addParticle(
                  new LightningStrikeParticleOptions(REParticles.LIGHTNING_STRIKE.get(), direction, 1.0F, 4), true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0
               );
         }
      }
   }

   private void damageEntities() {
      ServerLevel level = (ServerLevel)this.level();
      float damage = REConfigs.CONFIG.get().zapperStaffLightningDamage;
      Entity ownerEntity;
      if (this.owner != null) {
         ownerEntity = level.getEntity(this.owner);
         if (ownerEntity instanceof LivingEntity livingEntity && !(ownerEntity instanceof Player)) {
            damage = (float)livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5F;
         }
      } else {
         ownerEntity = null;
      }

      Vec3 cylinderStart = this.position().add(0.0, -1.0, 0.0);

      for (LivingEntity entity : FDTargetFinder.getEntitiesInCylinder(LivingEntity.class, level, cylinderStart, 5.0F, 2.0F, e -> e != ownerEntity)) {
         if (ownerEntity instanceof LivingEntity livingEntity) {
            entity.hurt(this.level().damageSources().mobAttack(livingEntity), damage);
         } else {
            entity.hurt(this.level().damageSources().generic(), damage);
         }
      }

      this.remove(RemovalReason.DISCARDED);
   }

   protected void defineSynchedData(Builder p_326003_) {
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      if (tag.contains("owner")) {
         this.owner = tag.getUUID("owner");
      }
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      if (this.owner != null) {
         tag.putUUID("owner", this.owner);
      }
   }
}
