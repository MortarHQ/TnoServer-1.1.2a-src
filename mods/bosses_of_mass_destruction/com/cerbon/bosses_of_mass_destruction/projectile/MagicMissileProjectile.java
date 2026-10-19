package com.cerbon.bosses_of_mass_destruction.projectile;

import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.projectile.util.ExemptEntities;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class MagicMissileProjectile extends BaseThrownItemProjectile {
   private Consumer<LivingEntity> entityHit;

   public MagicMissileProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
   }

   public MagicMissileProjectile(LivingEntity livingEntity, Level level, Consumer<LivingEntity> entityHit, List<EntityType<?>> exemptEntities) {
      super((EntityType<? extends ThrowableItemProjectile>)BMDEntities.MAGIC_MISSILE.get(), livingEntity, level, new ExemptEntities(exemptEntities));
      this.entityHit = entityHit;
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

   protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
      super.onHitBlock(blockHitResult);
      this.playSound((SoundEvent)BMDSounds.BLUE_FIREBALL_LAND.get(), 1.0F, 1.0F);
      this.discard();
   }
}
