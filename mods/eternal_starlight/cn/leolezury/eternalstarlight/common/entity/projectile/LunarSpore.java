package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.joml.Vector4f;

public class LunarSpore extends ThrowableProjectile implements TrailOwner {
   public LunarSpore(EntityType<? extends LunarSpore> type, Level level) {
      super(type, level);
   }

   public LunarSpore(Level level, LivingEntity entity, double x, double y, double z) {
      super(ESEntities.LUNAR_SPORE.get(), x, y, z, level);
      this.setOwner(entity);
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isOnFire() {
      return false;
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      return false;
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void tick() {
      super.tick();
      if ((this.tickCount > (this.getOwner() instanceof Player ? 60 : 200) || this.getDeltaMovement().length() < 0.001) && !this.level().isClientSide) {
         this.explodeAndDiscard();
      }
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      if (hitResult.getType() != Type.ENTITY || ESEntityUtil.shouldHarm(this.getOwner(), ((EntityHitResult)hitResult).getEntity())) {
         if (!this.level().isClientSide) {
            this.explodeAndDiscard();
         }
      }
   }

   private void explodeAndDiscard() {
      this.playSound((SoundEvent)SoundEvents.GENERIC_EXPLODE.value());
      if (this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 4; i++) {
            Vec3 vec3 = new Vec3(
               this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
               this.getY() + this.random.nextFloat() * this.getBbHeight(),
               this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth()
            );

            for (int m = 0; m < serverLevel.players().size(); m++) {
               ServerPlayer serverPlayer = (ServerPlayer)serverLevel.players().get(m);
               serverLevel.sendParticles(serverPlayer, ESExplosionParticleOptions.LUNAR, true, vec3.x, vec3.y, vec3.z, 2, 0.0, 0.0, 0.0, 0.0);
               serverLevel.sendParticles(serverPlayer, ESSmokeParticleOptions.LUNAR_SHORT, true, vec3.x, vec3.y, vec3.z, 2, 0.0, 0.0, 0.0, 0.0);
            }
         }

         ScreenShakeVfx.createInstance(this.level().dimension(), this.position(), 45.0F, 40, 0.02F, 0.03F, 4.5F, 5.0F).send(serverLevel);
      }

      for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0))) {
         if (ESEntityUtil.shouldHarm(this.getOwner(), entity)) {
            if (this.getOwner() instanceof Player) {
               entity.invulnerableTime = 0;
            }

            entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.POISON, this, this.getOwner()), 5.0F);
         }
      }

      this.discard();
   }

   @Override
   public TrailEffect createNewTrail() {
      return new TrailEffect(0.4F, switch (this.getOwner()) {
         case LunarMonstrosity ignored -> 15.0F;
         case null, default -> 12.0F;
      });
   }

   @Override
   public void updateTrail(TrailEffect effect) {
      Vec3 oldPos = new Vec3(this.xOld, this.yOld, this.zOld);
      effect.update(oldPos.add(0.0, this.getBbHeight() / 2.0F, 0.0));
      if (this.isRemoved()) {
         effect.setLength(Math.max(effect.getLength() - 0.5F, 0.0F));
      }
   }

   @Override
   public Vector4f getTrailColor() {
      return new Vector4f(0.1254902F, 0.1254902F, 0.2509804F, 2.0F);
   }
}
