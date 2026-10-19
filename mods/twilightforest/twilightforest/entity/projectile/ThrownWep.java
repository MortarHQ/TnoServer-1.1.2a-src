package twilightforest.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import twilightforest.entity.boss.KnightPhantom;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFItems;

public class ThrownWep extends TFThrowable {
   private static final EntityDataAccessor<ItemStack> DATA_ITEMSTACK = SynchedEntityData.defineId(ThrownWep.class, EntityDataSerializers.ITEM_STACK);
   private static final EntityDataAccessor<Float> DATA_VELOCITY = SynchedEntityData.defineId(ThrownWep.class, EntityDataSerializers.FLOAT);
   private float projectileDamage = 6.0F;

   public ThrownWep(EntityType<? extends ThrownWep> type, Level world, LivingEntity thrower) {
      super(type, world, thrower);
   }

   public ThrownWep(EntityType<? extends ThrownWep> type, Level world) {
      super(type, world);
   }

   public ThrownWep setDamage(float damage) {
      this.projectileDamage = damage;
      return this;
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_ITEMSTACK, ItemStack.EMPTY);
      builder.define(DATA_VELOCITY, 0.001F);
   }

   public ThrownWep setItem(ItemStack stack) {
      this.getEntityData().set(DATA_ITEMSTACK, stack);
      return this;
   }

   public ItemStack getItem() {
      return (ItemStack)this.getEntityData().get(DATA_ITEMSTACK);
   }

   public ThrownWep setVelocity(float velocity) {
      this.getEntityData().set(DATA_VELOCITY, velocity);
      return this;
   }

   public void handleEntityEvent(byte id) {
      if (id == 3) {
         for (int i = 0; i < 8; i++) {
            this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
         }
      } else {
         super.handleEntityEvent(id);
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (!(result.getEntity() instanceof KnightPhantom) && result.getEntity() != this.getOwner()) {
         if (!this.level().isClientSide()) {
            result.getEntity()
               .hurt(
                  TFDamageTypes.getDamageSource(
                     this.level(), this.getItem().getItem() == TFItems.KNIGHTMETAL_PICKAXE.get() ? TFDamageTypes.THROWN_PICKAXE : TFDamageTypes.THROWN_AXE
                  ),
                  this.projectileDamage
               );
         }
      }
   }

   protected void onHit(HitResult result) {
      super.onHit(result);
      if (!this.level().isClientSide()) {
         this.level().broadcastEntityEvent(this, (byte)3);
         this.discard();
      }
   }

   public boolean isPickable() {
      return true;
   }

   public float getPickRadius() {
      return 1.0F;
   }

   protected double getDefaultGravity() {
      return ((Float)this.getEntityData().get(DATA_VELOCITY)).floatValue();
   }
}
