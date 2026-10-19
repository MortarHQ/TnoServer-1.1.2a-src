package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.attack.PermafrostCloud;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.joml.Vector4f;

public class PermafrostSpit extends ThrowableProjectile implements TrailOwner {
   private static final String TAG_SMALL = "small";
   protected static final EntityDataAccessor<Boolean> SMALL = SynchedEntityData.defineId(PermafrostSpit.class, EntityDataSerializers.BOOLEAN);

   public boolean isSmall() {
      return (Boolean)this.getEntityData().get(SMALL);
   }

   public void setSmall(boolean small) {
      this.getEntityData().set(SMALL, small);
   }

   public PermafrostSpit(EntityType<? extends PermafrostSpit> entityType, Level level) {
      super(entityType, level);
   }

   public PermafrostSpit(Level level, LivingEntity livingEntity) {
      super(ESEntities.PERMAFROST_SPIT.get(), livingEntity, level);
   }

   public PermafrostSpit(Level level, double x, double y, double z) {
      super(ESEntities.PERMAFROST_SPIT.get(), x, y, z, level);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(SMALL, false);
   }

   public boolean isOnFire() {
      return false;
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      if (hitResult.getType() != Type.MISS) {
         PermafrostCloud cloud = new PermafrostCloud(ESEntities.PERMAFROST_CLOUD.get(), this.level());
         if (this.getOwner() instanceof LivingEntity living) {
            cloud.setOwner(living);
         }

         cloud.setSmall(this.isSmall());
         cloud.setPos(hitResult.getLocation());
         this.level().addFreshEntity(cloud);
         if (!this.isSmall() && this.getOwner() instanceof LivingEntity living) {
            for (int i = 0; i < 5; i++) {
               Vec3 delta = ESMathUtil.rotationToPosition(1.0F, 35.0F, 360.0F * i / 5.0F + this.getRandom().nextInt(30));
               PermafrostSpit spit = new PermafrostSpit(this.level(), living);
               spit.shoot(delta.x, delta.y, delta.z, 0.4F, 0.1F);
               spit.setPos(this.position());
               spit.setSmall(true);
               this.level().addFreshEntity(spit);
            }
         }

         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
               ESExplosionParticleOptions.ENERGY,
               this.getX(),
               this.getY() + this.getBbHeight() / 2.0F,
               this.getZ(),
               20,
               this.getBbWidth() / 2.0F,
               this.getBbHeight() / 2.0F,
               this.getBbWidth() / 2.0F,
               0.0
            );
         }

         for (int x = -4; x <= 4; x++) {
            for (int y = -4; y <= 4; y++) {
               for (int z = -4; z <= 4; z++) {
                  BlockPos pos = this.blockPosition().offset(x, y, z);
                  if (this.level().getBlockState(pos).is(Blocks.FIRE) && this.blockPosition().distSqr(pos) <= 16.0) {
                     this.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                  }

                  if (this.level().getBlockState(pos).is(Blocks.WATER)
                     && this.level().getFluidState(pos).isSource()
                     && this.blockPosition().distSqr(pos) <= 16.0) {
                     this.level().setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                  }

                  if (this.level().getBlockState(pos).is(Blocks.LAVA) && this.level().getFluidState(pos).isSource() && this.blockPosition().distSqr(pos) <= 4.0
                     )
                   {
                     this.level().setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
                  }
               }
            }
         }

         this.level().getEntitiesOfClass(EnergizedFlame.class, this.getBoundingBox().inflate(5.0)).forEach(Entity::discard);
         this.discard();
      }
   }

   protected void onHitEntity(EntityHitResult hitResult) {
      if (hitResult.getType() != Type.MISS && this.getOwner() instanceof LivingEntity owner && ESEntityUtil.shouldHarm(owner, hitResult.getEntity())) {
         hitResult.getEntity()
            .hurt(
               ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.FREEZE, this, owner),
               (float)((owner.getAttribute(Attributes.ATTACK_SPEED) != null ? owner.getAttributeValue(Attributes.ATTACK_SPEED) : 12.0) * 1.25)
            );
         if (hitResult.getEntity() instanceof LivingEntity living && living.canFreeze()) {
            living.addEffect(new MobEffectInstance(ESMobEffects.BRITTLE.asHolder(), 200, 0));
         }
      }
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setSmall(tag.getBoolean("small"));
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putBoolean("small", this.isSmall());
   }

   @Override
   public TrailEffect createNewTrail() {
      return new TrailEffect(0.3F, 8.0F);
   }

   @Override
   public void updateTrail(TrailEffect effect) {
      Vec3 oldPos = new Vec3(this.xOld, this.yOld, this.zOld);
      effect.update(oldPos.add(0.0, this.getBbHeight() / 2.0F, 0.0));
      if (this.isRemoved()) {
         effect.setLength(Math.max(effect.getLength() - 0.9F, 0.0F));
      }
   }

   @Override
   public Vector4f getTrailColor() {
      return new Vector4f(0.40784314F, 0.8F, 1.0F, 1.0F);
   }
}
