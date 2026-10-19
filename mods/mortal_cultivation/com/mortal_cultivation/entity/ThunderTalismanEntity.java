package com.mortal_cultivation.entity;

import com.mortal_cultivation.registry.EntityRegistry;
import com.mortal_cultivation.registry.item.CultivationItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ThunderTalismanEntity extends ThrowableItemProjectile {
   private int ticksInAir = 0;
   private boolean isCritical = false;
   private boolean hasGravity = false;
   private static final int NO_GRAVITY_TICKS = 40;
   private static final EntityType<?> BLACK_LIGHTNING_TYPE = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE
      .getOptional(ResourceLocation.fromNamespaceAndPath("tensura", "black_lightning_bolt"))
      .orElse(null);

   public ThunderTalismanEntity(EntityType<? extends ThunderTalismanEntity> entityType, Level level) {
      super(entityType, level);
   }

   public ThunderTalismanEntity(Level level, LivingEntity shooter) {
      super((EntityType)EntityRegistry.THUNDER_TALISMAN_ENTITY.get(), shooter, level);
   }

   public ThunderTalismanEntity(Level level, double x, double y, double z) {
      super((EntityType)EntityRegistry.THUNDER_TALISMAN_ENTITY.get(), x, y, z, level);
   }

   protected Item getDefaultItem() {
      return (Item)CultivationItems.THUNDER_TALISMAN.get();
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
   }

   public void setCritArrow(boolean critical) {
      this.isCritical = critical;
   }

   public boolean isCritArrow() {
      return this.isCritical;
   }

   public void tick() {
      Vec3 currentVelocity = this.getDeltaMovement();
      this.ticksInAir++;
      if (this.ticksInAir > 40 && !this.hasGravity) {
         this.hasGravity = true;
      }

      if (!this.hasGravity) {
         this.setNoGravity(true);
      } else {
         this.setNoGravity(false);
      }

      super.tick();
      if (!this.hasGravity) {
         this.setDeltaMovement(this.getDeltaMovement().x, currentVelocity.y, this.getDeltaMovement().z);
      }

      if (this.level().isClientSide) {
         this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
         if (this.isCritical) {
            for (int i = 0; i < 2; i++) {
               this.level()
                  .addParticle(
                     ParticleTypes.ENCHANTED_HIT,
                     this.getX() + (this.random.nextFloat() - 0.5) * 0.5,
                     this.getY() + (this.random.nextFloat() - 0.5) * 0.5,
                     this.getZ() + (this.random.nextFloat() - 0.5) * 0.5,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (this.hasGravity) {
            this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
         }
      }

      if (this.ticksInAir > 100 && !this.level().isClientSide) {
         this.discard();
      }
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      if (!this.level().isClientSide) {
         ((ServerLevel)this.level()).sendParticles(ParticleTypes.FLASH, this.getX(), this.getY(), this.getZ(), 10, 0.5, 0.5, 0.5, 0.0);

         for (int i = 0; i < 5; i++) {
            int index = i;
            this.level().getServer().tell(new TickTask(this.level().getServer().getTickCount() + i * 5, () -> {
               if (this.getOwner() instanceof LivingEntity) {
                  LivingEntity owner = (LivingEntity)this.getOwner();
               } else {
                  LivingEntity owner = null;
               }

               boolean useBlackLightning = index >= 3 && BLACK_LIGHTNING_TYPE != null;
               Entity lightning;
               if (useBlackLightning) {
                  lightning = BLACK_LIGHTNING_TYPE.create(this.level());
               } else {
                  lightning = EntityType.LIGHTNING_BOLT.create(this.level());
               }

               if (lightning != null) {
                  double offsetX = (this.level().random.nextDouble() - 0.5) * 2.0;
                  double offsetZ = (this.level().random.nextDouble() - 0.5) * 2.0;
                  lightning.setPos(this.getX() + offsetX, this.getY(), this.getZ() + offsetZ);
                  this.level().addFreshEntity(lightning);
               }
            }));
         }

         this.discard();
      }
   }

   protected void onHitEntity(EntityHitResult hitResult) {
      super.onHitEntity(hitResult);
      if (!this.level().isClientSide) {
         Entity target = hitResult.getEntity();
         LivingEntity owner = this.getOwner() instanceof LivingEntity ? (LivingEntity)this.getOwner() : null;
         if (target instanceof LivingEntity) {
            float damage = this.isCritical ? 75.0F : 50.0F;
            target.hurt(this.level().damageSources().thrown((Entity)(owner != null ? owner : this), this), damage);
         }
      }
   }
}
