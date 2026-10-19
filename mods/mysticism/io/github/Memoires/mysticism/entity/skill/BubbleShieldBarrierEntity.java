package io.github.Memoires.mysticism.entity.skill;

import io.github.Memoires.mysticism.ability.skill.unique.EngineerSkill;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.tensura.entity.magic.barrier.BarrierEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BubbleShieldBarrierEntity extends BarrierEntity implements GeoEntity {
   private static final int DEFAULT_DURATION = 300;
   private static final float DEFAULT_HEALTH = 100.0F;
   private static final float DEFAULT_SIZE = 10.0F;
   private static final float DEFAULT_HEIGHT = -2.3F;
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public BubbleShieldBarrierEntity(EntityType<? extends BarrierEntity> entityType, Level level, LivingEntity entity) {
      super(entityType, level, entity);
      this.setLife(300);
      this.setHealth(100.0F);
      this.setSize(10.0F);
      this.setHeight(-2.3F);
   }

   public BubbleShieldBarrierEntity(EntityType<BubbleShieldBarrierEntity> entityType, Level level) {
      this(entityType, level, null);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, state -> {
         state.getController().setAnimation(RawAnimation.begin().thenPlay("Deploy").thenLoop("Idle"));
         return PlayState.CONTINUE;
      }));
   }

   public boolean hurt(DamageSource source, float amount) {
      if (EngineerSkill.CONFIG.bubbleShieldShouldHurt) {
         return false;
      } else {
         return this.isDamageFromInside(source) ? super.hurt(source, amount) : false;
      }
   }

   private boolean isDamageFromInside(DamageSource source) {
      Entity sourceEntity = source.getDirectEntity();
      return sourceEntity != null ? this.getBoundingBox().contains(sourceEntity.position()) : false;
   }

   public boolean canWalkThrough(Entity entity) {
      Entity owner = this.getOwner();
      return owner != null && (entity == owner || entity.isAlliedTo(owner));
   }

   public void spawnParticle() {
      for (int i = 0; i < 5; i++) {
         double x = this.getX() + (this.random.nextDouble() - 0.5) * this.getSize() * 2.0;
         double y = this.getY() + this.random.nextDouble() * (this.getSize() * 2.0F + this.getHeight());
         double z = this.getZ() + (this.random.nextDouble() - 0.5) * this.getSize() * 2.0;
         this.level()
            .addParticle(
               ParticleTypes.BUBBLE_POP,
               x,
               y,
               z,
               (this.random.nextDouble() - 0.5) * 0.1,
               this.random.nextDouble() * 0.1,
               (this.random.nextDouble() - 0.5) * 0.1
            );
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public static BubbleShieldBarrierEntity create(Level level, LivingEntity owner) {
      return new BubbleShieldBarrierEntity((EntityType<? extends BarrierEntity>)MysticismSkillEntityTypes.BUBBLE_SHIELD_BARRIER.get(), level, owner);
   }
}
