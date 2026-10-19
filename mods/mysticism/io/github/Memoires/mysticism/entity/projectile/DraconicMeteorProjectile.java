package io.github.Memoires.mysticism.entity.projectile;

import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DraconicMeteorProjectile extends TensuraFlyingProjectile implements GeoEntity {
   private final AnimatableInstanceCache geckoCache = GeckoLibUtil.createInstanceCache(this);

   public DraconicMeteorProjectile(EntityType<? extends DraconicMeteorProjectile> entityType, Level level) {
      super(entityType, level);
   }

   public DraconicMeteorProjectile(Level levelIn, LivingEntity shooter) {
      super((EntityType)MysticismProjectileEntityTypes.DRACONIC_METEOR_PROJECTILE.get(), levelIn);
      this.setNoGravity(true);
      this.setOwner(shooter);
   }

   public ResourceKey<DamageType> getDamageType() {
      return TensuraDamageTypes.MAGIC_GENERIC;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(this, "controller", 0, event -> event.setAndContinue(RawAnimation.begin().thenLoop("animation.draconic_meteor.fly")))
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geckoCache;
   }

   public void flyingParticles() {
   }
}
