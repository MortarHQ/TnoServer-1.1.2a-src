package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.SpecialItemCooldown;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ThrownEnergyBoomerang extends ThrownBoomerang {
   public ThrownEnergyBoomerang(EntityType<? extends ThrownEnergyBoomerang> type, Level level) {
      super(type, level);
   }

   public ThrownEnergyBoomerang(Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack) {
      super(ESEntities.ENERGY_BOOMERANG.get(), level, owner, x, y, z, pickupItemStack);
   }

   protected void doPostHurtEffects(LivingEntity livingEntity) {
      super.doPostHurtEffects(livingEntity);
      Entity owner = this.getOwner();
      ItemStack weapon = this.getWeaponItem();
      livingEntity.igniteForSeconds(2.0F);
      if ((owner == null || weapon == null || !SpecialItemCooldown.isOnCooldown(owner, weapon.getItem())) && this.level() instanceof ServerLevel serverLevel) {
         for (LivingEntity living : this.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(2.0))) {
            if (ESEntityUtil.shouldHarm(this.getOwner(), living) && living != livingEntity) {
               living.invulnerableTime = 0;
               if (living.hurt(ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.ELECTRIC_SHOCK, this, this.getOwner()), 8.0F)) {
                  living.igniteForSeconds(2.0F);
                  Vec3 speed = living.position()
                     .add(
                        (this.random.nextDouble() - 0.5) * living.getBbWidth(),
                        this.random.nextDouble() * living.getBbHeight(),
                        (this.random.nextDouble() - 0.5) * living.getBbWidth()
                     )
                     .subtract(this.position());
                  ESPlatform.INSTANCE
                     .sendToAllClients(
                        serverLevel,
                        new ParticlePacket((ParticleOptions)ESParticles.ELECTRIC_SPARK.get(), this.getX(), this.getY(), this.getZ(), speed.x, speed.y, speed.z)
                     );
               }
            }
         }

         if (owner != null && weapon != null) {
            SpecialItemCooldown.setCooldown(owner, weapon.getItem(), 40);
         }
      }
   }

   protected ItemStack getDefaultPickupItem() {
      return ESItems.ENERGY_BOOMERANG.get().getDefaultInstance();
   }
}
