package com.mongoose.royalvariations.common.entities.projectile;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.common.entities.RVEntityType;
import com.mongoose.royalvariations.common.items.RVItems;
import com.mongoose.royalvariations.utils.MobUtil;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RoyalArrow extends Arrow {
   public boolean marking = true;

   public RoyalArrow(EntityType<? extends Arrow> p_36721_, Level p_36722_) {
      super(p_36721_, p_36722_);
      this.setBaseDamage(2.4);
   }

   public RoyalArrow(Level p_36861_, double p_36862_, double p_36863_, double p_36864_, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
      super(p_36861_, p_36862_, p_36863_, p_36864_, pickupItemStack, firedFromWeapon);
      this.setBaseDamage(2.4);
   }

   public RoyalArrow(Level p_36866_, LivingEntity p_36867_, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
      super(p_36866_, p_36867_, pickupItemStack, firedFromWeapon);
      this.setBaseDamage(2.4);
   }

   public RoyalArrow(Level p_36866_, LivingEntity p_36867_) {
      super(p_36866_, p_36867_, new ItemStack(RVItems.ROYAL_ARROW.asItem()), new ItemStack(RVItems.ROYAL_BOW.asItem()));
      this.setBaseDamage(2.4);
   }

   public EntityType<?> getType() {
      return (EntityType<?>)RVEntityType.ROYAL_ARROW.get();
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("Marking", this.marking);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("Marking")) {
         this.marking = compoundTag.getBoolean("Marking");
      }
   }

   public void setMarking(boolean marking) {
      this.marking = marking;
   }

   public void tick() {
      super.tick();
      if (!this.inGround) {
         Vec3 vec3 = this.getDeltaMovement();
         double d0 = this.getX() + vec3.x;
         double d1 = this.getY() + vec3.y;
         double d2 = this.getZ() + vec3.z;
         this.level().addParticle((ParticleOptions)RVParticleTypes.ROYAL_ARROW_TRACE.get(), d0, d1, d2, 0.0, 0.0, 0.0);
      }
   }

   protected void doPostHurtEffects(LivingEntity livingEntity) {
      super.doPostHurtEffects(livingEntity);
      if (this.marking) {
         int amp = 1;
         if (livingEntity instanceof Player || this.getOwner() instanceof Player) {
            amp = 0;
         }

         boolean flag = true;
         if (this.getOwner() instanceof Mob mob1) {
            flag = MobUtil.notMonsterOrAnimal(livingEntity) || mob1.getTarget() != null && mob1.getTarget() == livingEntity;
         }

         if (flag
            && livingEntity.addEffect(new MobEffectInstance(RVEffects.MARKED, 300, amp))
            && !(this.getOwner() instanceof Player)
            && MobUtil.notMonsterOrAnimal(livingEntity)) {
            for (Mob mob : livingEntity.level().getEntitiesOfClass(Mob.class, livingEntity.getBoundingBox().inflate(32.0))) {
               if (mob instanceof Enemy
                  && !(mob instanceof NeutralMob)
                  && !MobUtil.areAllies(livingEntity, mob)
                  && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
                  mob.setTarget(livingEntity);
               }
            }
         }
      }
   }

   protected void doKnockback(LivingEntity entity, DamageSource damageSource) {
      double d0 = this.getWeaponItem() != null && this.level() instanceof ServerLevel serverlevel
         ? EnchantmentHelper.modifyKnockback(serverlevel, this.getWeaponItem(), entity, damageSource, 2.0F)
         : 0.0F;
      if (d0 > 0.0) {
         double d1 = Math.max(0.0, 1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
         Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(d0 * 0.6 * d1);
         if (vec3.lengthSqr() > 0.0) {
            entity.push(vec3.x, 0.1, vec3.z);
         }
      }
   }

   protected boolean canHitEntity(Entity pEntity) {
      if (this.getOwner() != null) {
         if (pEntity == this.getOwner()) {
            return false;
         }

         if (this.getOwner() instanceof Mob mob && mob.getTarget() == pEntity) {
            return super.canHitEntity(pEntity);
         }

         if (MobUtil.areAllies(this.getOwner(), pEntity)) {
            return false;
         }
      }

      return super.canHitEntity(pEntity);
   }

   protected ItemStack getPickupItem() {
      return new ItemStack((ItemLike)RVItems.ROYAL_ARROW.get());
   }
}
