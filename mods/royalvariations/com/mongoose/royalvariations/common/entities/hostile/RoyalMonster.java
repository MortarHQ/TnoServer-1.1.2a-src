package com.mongoose.royalvariations.common.entities.hostile;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class RoyalMonster extends Monster {
   public int knightCool;

   public RoyalMonster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
      super(p_33002_, p_33003_);
      this.xpReward = 20;
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putInt("BlessCool", this.knightCool);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("BlessCool")) {
         this.knightCool = compoundTag.getInt("BlessCool");
      }
   }

   public void remove(RemovalReason p_276115_) {
      if (p_276115_ == RemovalReason.KILLED && this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(
            (SimpleParticleType)RVParticleTypes.ROYAL_SPIRIT.get(), this.getX() + 0.5, this.getY() + 1.0, this.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.1
         );
      }

      super.remove(p_276115_);
   }

   protected boolean isSunSensitive() {
      return false;
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide && this.knightCool > 0) {
         this.knightCool--;
      }
   }

   public void aiStep() {
      if (this.isAlive()) {
         boolean flag = this.isSunSensitive() && this.isSunBurnTick();
         if (flag) {
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
            if (!itemstack.isEmpty()) {
               if (itemstack.isDamageableItem()) {
                  itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                  if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                     this.onEquippedItemBroken(itemstack.getItem(), EquipmentSlot.HEAD);
                     this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                  }
               }

               flag = false;
            }

            if (flag) {
               this.igniteForSeconds(8.0F);
            }
         }
      }

      super.aiStep();
   }
}
