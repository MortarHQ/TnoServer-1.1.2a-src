package com.github.dominickwd04.traddon.entity.magic.field.cloud;

import com.github.dominickwd04.traddon.registry.entity.TrAddonMiscEntityTypes;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.field.cloud.AreaCloud;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.race.RaceUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CrystalDustCloud extends AreaCloud {
   public CrystalDustCloud(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public CrystalDustCloud(Level level, LivingEntity entity) {
      this((EntityType<? extends Projectile>)TrAddonMiscEntityTypes.CRYSTAL_DUST_CLOUD.get(), level);
      this.setOwner(entity);
   }

   protected boolean canHitEntity(Entity pTarget) {
      return !this.canExplodeEntity(pTarget)
         ? false
         : !RaceUtils.isSpiritual((LivingEntity)pTarget) && (this.getTarget() == null || pTarget == this.getTarget());
   }

   public void applyEffect(LivingEntity target, boolean instant) {
      if (this.dealDamage(target, this.getDamage(), 0.033333335F)) {
         target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 0, false, false), this.getOwner());
      }
   }

   public ResourceKey<DamageType> getDamageType() {
      return TensuraDamageTypes.EARTH_ELEMENTAL;
   }

   private boolean canExplodeEntity(Entity pTarget) {
      if (pTarget == this.getOwner()) {
         return false;
      } else {
         return !super.canHitEntity(pTarget) ? false : !(this.getOwner() instanceof LivingEntity owner && owner.isAlliedTo(pTarget));
      }
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         float size = this.getVisualSize() - 1.0F;
         float f = Mth.clamp(1.5F * size, 0.375F, 15.0F);

         for (int i = 0; i < f; i++) {
            if (f - i < 1.0F && this.random.nextFloat() > f - i) {
               return;
            }

            float distance = size * (1.0F - this.random.nextFloat() * this.random.nextFloat());
            float v = this.random.nextFloat() * 6.28F;
            Vec3 pos = new Vec3(distance * Mth.cos(v), 0.2, distance * Mth.sin(v));
            Vec3 motion = new Vec3((2.0 * Math.random() - 1.0) * 0.03, this.random.nextDouble() * 0.01, (2.0 * Math.random() - 1.0) * 0.03);
            this.level()
               .addParticle(TensuraParticleUtils.getAcidEffect(), this.getX() + pos.x, this.getY() + pos.y, this.getZ() + pos.z, motion.x, motion.y, motion.z);
         }
      }
   }
}
