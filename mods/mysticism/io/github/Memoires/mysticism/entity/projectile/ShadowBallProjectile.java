package io.github.Memoires.mysticism.entity.projectile;

import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.TensuraProjectile;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShadowBallProjectile extends TensuraProjectile implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public ShadowBallProjectile(EntityType<? extends ShadowBallProjectile> entityType, Level level) {
      super(entityType, level);
      this.setSize(0.4F);
   }

   public ShadowBallProjectile(Level levelIn, LivingEntity shooter) {
      super((EntityType)MysticismProjectileEntityTypes.SHADOW_BALL.get(), levelIn);
      this.setOwner(shooter);
      this.setSize(0.4F);
   }

   public void tick() {
      super.tick();
      HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, x$0 -> this.canHitEntity(x$0));
      if (hitresult.getType() != Type.MISS) {
         if (hitresult.getType() == Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult)hitresult;
            this.hitEntity(entityHitResult.getEntity(), null);
         }

         this.remove();
      } else {
         Vec3 deltaMovement = this.getDeltaMovement();
         double nextX = this.getX() + deltaMovement.x;
         double nextY = this.getY() + deltaMovement.y;
         double nextZ = this.getZ() + deltaMovement.z;
         this.setPos(nextX, nextY, nextZ);
      }
   }

   public ResourceKey<DamageType> getDamageType() {
      return TensuraDamageTypes.DARKNESS_ELEMENTAL;
   }

   public boolean shouldDiscardInWater() {
      return false;
   }

   public boolean shouldDiscardInLava() {
      return false;
   }

   protected boolean hitEntity(Entity entity, @Nullable ProjectileHitResult result) {
      if (this.level().isClientSide()) {
         return true;
      }

      TensuraParticleHelper.addServerParticlesAroundSelf(this, (ParticleOptions)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(), 2.0);
      if (entity instanceof LivingEntity directTarget
         && (this.getOwner() == null || !directTarget.isAlliedTo(this.getOwner()) && !directTarget.is(this.getOwner()))) {
         this.applyDamageAndEffects(directTarget);
      }

      double range = this.getEffectRange();
      if (range > 0.0) {
         for (LivingEntity extraTarget : this.level()
            .getEntitiesOfClass(
               LivingEntity.class,
               this.getBoundingBox().inflate(range),
               entityData -> entityData != entity && (this.getOwner() == null || !entityData.isAlliedTo(this.getOwner()) && !entityData.is(this.getOwner()))
            )) {
            this.applyDamageAndEffects(extraTarget);
         }
      }

      return true;
   }

   private void applyDamageAndEffects(LivingEntity target) {
      DamageSource damageSource = this.getDamageSource(this.getDamageType(), 0.0F);
      if (TensuraDamageHelper.hurtDouble(target, damageSource, this.damage, damageSource, 0.0F)) {
         if (this.getMobEffect() != null) {
            target.addEffect(this.getMobEffect());
         }

         Holder<MobEffect> insanity = TensuraMobEffects.getReference(TensuraMobEffects.INSANITY);
         target.addEffect(new MobEffectInstance(insanity, 100, 1, false, false, false), this.getOwner());
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, this::predicate));
   }

   private <E extends GeoEntity> PlayState predicate(AnimationState<E> state) {
      state.getController().setAnimation(RawAnimation.begin().thenLoop("animation.model.idle"));
      return PlayState.CONTINUE;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
