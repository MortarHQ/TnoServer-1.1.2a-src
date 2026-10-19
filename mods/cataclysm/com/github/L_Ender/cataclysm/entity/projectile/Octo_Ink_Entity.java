package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.EventHooks;

public class Octo_Ink_Entity extends Projectile {
   public Octo_Ink_Entity(EntityType<? extends Octo_Ink_Entity> entityType, Level level) {
      super(entityType, level);
   }

   public Octo_Ink_Entity(Level level, LivingEntity spitter) {
      this((EntityType<? extends Octo_Ink_Entity>)ModEntities.OCTO_INK.get(), level);
      this.setOwner(spitter);
      this.setPos(spitter.getX(), spitter.getEyeY() - 0.1F, spitter.getZ());
   }

   protected double getDefaultGravity() {
      return 0.06;
   }

   public void tick() {
      super.tick();
      Vec3 vec3 = this.getDeltaMovement();
      HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, x$0 -> this.canHitEntity(x$0));
      if (hitresult.getType() != Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
         this.hitTargetOrDeflectSelf(hitresult);
      }

      double d0 = this.getX() + vec3.x;
      double d1 = this.getY() + vec3.y;
      double d2 = this.getZ() + vec3.z;
      this.updateRotation();
      float f = 0.99F;
      if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockStateBase::isAir)) {
         this.discard();
      } else if (this.isInWaterOrBubble()) {
         this.discard();
      } else {
         this.setDeltaMovement(vec3.scale(0.99F));
         this.applyGravity();
         this.setPos(d0, d1, d2);
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (this.getOwner() instanceof LivingEntity livingentity) {
         Entity entity = result.getEntity();
         DamageSource damagesource = this.damageSources().spit(this, livingentity);
         if (entity.hurt(damagesource, 4.0F) && this.level() instanceof ServerLevel serverlevel) {
            EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
         }
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (!this.level().isClientSide) {
         this.discard();
      }
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      double d0 = packet.getXa();
      double d1 = packet.getYa();
      double d2 = packet.getZa();

      for (int i = 0; i < 7; i++) {
         double d3 = 0.4 + 0.1 * i;
         this.level().addParticle(ParticleTypes.SQUID_INK, this.getX(), this.getY(), this.getZ(), d0 * d3, d1, d2 * d3);
      }

      this.setDeltaMovement(d0, d1, d2);
   }
}
