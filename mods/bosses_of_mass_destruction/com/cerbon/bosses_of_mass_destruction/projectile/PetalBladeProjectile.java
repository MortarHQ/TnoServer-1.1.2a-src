package com.cerbon.bosses_of_mass_destruction.projectile;

import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.projectile.util.ExemptEntities;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class PetalBladeProjectile extends BaseThrownItemProjectile {
   private Consumer<LivingEntity> entityHit;
   public static final EntityDataAccessor<Float> renderRotation = SynchedEntityData.defineId(PetalBladeProjectile.class, EntityDataSerializers.FLOAT);

   public PetalBladeProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
      this.getEntityData().set(renderRotation, 0.0F);
   }

   public PetalBladeProjectile(LivingEntity livingEntity, Level level, Consumer<LivingEntity> entityHit, List<EntityType<?>> exemptEntities, float rotation) {
      super((EntityType<? extends ThrowableItemProjectile>)BMDEntities.PETAL_BLADE.get(), livingEntity, level, new ExemptEntities(exemptEntities));
      this.entityHit = entityHit;
      this.getEntityData().set(renderRotation, rotation);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(renderRotation, 0.0F);
   }

   @Override
   public void entityHit(EntityHitResult entityHitResult) {
      Entity entity = entityHitResult.getEntity();
      Entity owner = this.getOwner();
      if (owner instanceof LivingEntity livingEntity) {
         entity.hurt(entity.level().damageSources().thrown(this, owner), (float)livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE));
         if (entity instanceof LivingEntity && this.entityHit != null) {
            this.entityHit.accept((LivingEntity)entity);
         }
      }

      this.discard();
   }

   protected void onHitBlock(@NotNull BlockHitResult result) {
      super.onHitBlock(result);
      this.discard();
   }
}
