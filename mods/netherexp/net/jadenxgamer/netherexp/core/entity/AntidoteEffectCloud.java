package net.jadenxgamer.netherexp.core.entity;

import java.util.List;
import net.jadenxgamer.netherexp.core.item.components.AntidoteContents;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;

public class AntidoteEffectCloud extends AreaEffectCloud {
   private final AntidoteContents contents;

   public AntidoteEffectCloud(EntityType<AntidoteEffectCloud> entityType, Level level) {
      super(entityType, level);
      this.contents = AntidoteContents.EMPTY;
   }

   public AntidoteEffectCloud(Level level, double x, double y, double z, AntidoteContents contents) {
      super(JNEEntityType.ANTIDOTE_EFFECT_CLOUD.get(), level);
      this.contents = contents;
      this.setPos(x, y, z);
      this.setRadius(12.0F);
      this.setDuration(200);
      this.setWaitTime(0);
      this.setRadiusPerTick(-this.getRadius() / this.getDuration());
      this.setRadiusOnUse(0.0F);
      this.setDurationOnUse(0);
      int color = contents.customColor().orElseGet(() -> AntidoteContents.getColorFromEffects(contents.getAllEffects()).orElse(8421504));
      this.setParticle(ColorParticleOption.create(JNEParticleTypes.IMMUNITY_EFFECT.get(), ARGB32.opaque(color)));
   }

   public EntityDimensions getDimensions(Pose pose) {
      return EntityDimensions.scalable(this.getRadius() * 2.0F, this.getRadius());
   }

   public void tick() {
      if (!this.level().isClientSide) {
         float radius = this.getRadius() + this.getRadiusPerTick();
         if (radius < 1.0F) {
            this.discard();
         } else {
            this.setRadius(radius);
         }

         if (this.tickCount % 5 == 0) {
            this.applyEffectsToEntities();
         }

         if (this.tickCount >= this.getDuration()) {
            this.discard();
         }
      } else {
         this.spawnAntidoteParticles();
      }

      this.baseTick();
   }

   private void spawnAntidoteParticles() {
      float radius = this.getRadius();
      if (!(radius <= 0.0F)) {
         int count = Math.min(Mth.ceil((float) Math.PI * radius * radius * (radius / 0.5F) * 0.1F), 100);
         ParticleOptions particle = this.getParticle();

         for (int i = 0; i < count; i++) {
            float angle = this.random.nextFloat() * (float) (Math.PI * 2);
            float dist = Mth.sqrt(this.random.nextFloat()) * radius;
            this.level()
               .addAlwaysVisibleParticle(
                  particle,
                  this.getX() + Mth.cos(angle) * dist,
                  this.getY() - radius / 2.0 + this.random.nextDouble() * radius,
                  this.getZ() + Mth.sin(angle) * dist,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }
   }

   private void applyEffectsToEntities() {
      List<MobEffectInstance> effects = this.contents.getAllEffects();
      if (!effects.isEmpty()) {
         List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
         entities.stream().filter(e -> e.isAffectedByPotions() && effects.stream().anyMatch(e::canBeAffected)).forEach(e -> effects.forEach(inst -> {
            if (((MobEffect)inst.getEffect().value()).isInstantenous()) {
               ((MobEffect)inst.getEffect().value()).applyInstantenousEffect(this, this.getOwner(), e, inst.getAmplifier(), 0.5);
            } else {
               e.addEffect(new MobEffectInstance(inst), this);
            }
         }));
      }
   }
}
