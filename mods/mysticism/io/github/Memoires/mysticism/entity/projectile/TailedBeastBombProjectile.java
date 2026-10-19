package io.github.Memoires.mysticism.entity.projectile;

import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
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

public class TailedBeastBombProjectile extends TensuraFlyingProjectile implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private float explosionDamage = 0.0F;
   private float blastRadius = 0.0F;

   public TailedBeastBombProjectile(EntityType<? extends TailedBeastBombProjectile> entityType, Level level) {
      super(entityType, level);
      this.setSize(0.4F);
   }

   public TailedBeastBombProjectile(Level levelIn, LivingEntity shooter) {
      super((EntityType)MysticismProjectileEntityTypes.TAILED_BEAST_BOMB.get(), levelIn);
      this.setOwner(shooter);
      this.setSize(0.4F);
   }

   public void setExplosionDamage(float explosionDamage) {
      this.explosionDamage = explosionDamage;
   }

   public void setBlastRadius(float blastRadius) {
      this.blastRadius = blastRadius;
   }

   public void tick() {
      super.tick();
      HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, x$0 -> this.canHitEntity(x$0));
      if (hitresult.getType() != Type.MISS) {
         if (hitresult.getType() == Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult)hitresult;
            this.hitEntity(entityHitResult.getEntity(), null);
         }

         this.explodeOnImpact(hitresult.getLocation());
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
      if (entity instanceof LivingEntity directTarget && this.isValidTarget(directTarget)) {
         this.applyDamageAndEffects(directTarget, this.damage);
      }

      double range = this.getEffectRange();
      if (range > 0.0) {
         for (LivingEntity extraTarget : this.level()
            .getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(range), entityData -> entityData != entity && this.isValidTarget(entityData))) {
            this.applyDamageAndEffects(extraTarget, this.damage);
         }
      }

      return true;
   }

   private void explodeOnImpact(Vec3 impact) {
      if (!this.level().isClientSide()) {
         if (!(this.explosionDamage <= 0.0F) && !(this.blastRadius <= 0.0F)) {
            double radius = this.blastRadius;
            AABB area = new AABB(impact, impact).inflate(radius);

            for (LivingEntity target : this.level()
               .getEntitiesOfClass(LivingEntity.class, area, targetx -> this.isValidTarget(targetx) && targetx.distanceToSqr(impact) <= radius * radius)) {
               this.applyDamageAndEffects(target, this.explosionDamage);
            }

            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, impact.x, impact.y, impact.z, 1, 0.0, 0.0, 0.0, 0.0);
               serverLevel.playSound(null, impact.x, impact.y, impact.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.NEUTRAL, 4.0F, 0.7F);
            }
         }
      }
   }

   private boolean isValidTarget(LivingEntity target) {
      Entity owner = this.getOwner();
      return owner == null || !target.isAlliedTo(owner) && !target.is(owner);
   }

   private void applyDamageAndEffects(LivingEntity target, float amount) {
      DamageSource damageSource = this.getDamageSource(this.getDamageType(), 0.0F);
      if (TensuraDamageHelper.hurtDouble(target, damageSource, amount, damageSource, 0.0F)) {
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
