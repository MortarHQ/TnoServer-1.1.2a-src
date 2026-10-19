package io.github.Memoires.mysticism.entity.projectile;

import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class StasisShotProjectile extends TensuraFlyingProjectile {
   public StasisShotProjectile(EntityType<? extends StasisShotProjectile> entityType, Level level) {
      super(entityType, level);
      this.setSize(0.25F);
   }

   public StasisShotProjectile(Level worldIn, LivingEntity shooter) {
      this((EntityType<? extends StasisShotProjectile>)MysticismProjectileEntityTypes.STASIS_SHOT.get(), worldIn);
      this.setOwner(shooter);
      this.setSize(0.25F);
      this.setVisible(false);
   }

   public ResourceKey<DamageType> getDamageType() {
      return TensuraDamageTypes.SEVERANCE;
   }

   public void flyingParticles() {
   }
}
