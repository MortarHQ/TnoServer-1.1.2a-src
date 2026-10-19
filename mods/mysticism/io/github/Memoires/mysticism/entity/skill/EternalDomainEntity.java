package io.github.Memoires.mysticism.entity.skill;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.entity.magic.field.AreaField;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EternalDomainEntity extends AreaField {
   private static final UniqueMysticismSkillConfig.Coalescence CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class))
      .Coalescence;

   public EternalDomainEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public EternalDomainEntity(Level level, LivingEntity entity) {
      this((EntityType<? extends Projectile>)MysticismSkillEntityTypes.ETERNAL_DOMAIN.get(), level);
      this.setOwner(entity);
   }

   protected boolean applyMobEffects(LivingEntity target) {
      if (this.getOwner() instanceof LivingEntity owner) {
         if (target.isAlliedTo(owner)) {
            return false;
         }

         if (target == owner) {
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.STILLNESS), 100, 0, false, true), this.getOwner());
            return false;
         }
      }

      if (!target.hasInfiniteMaterials()) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION), 100, 0, false, true), this.getOwner());
      }

      return true;
   }

   public void spawnAmbientParticles() {
      if (this.getVisualSize() != this.getSize() || this.getAge() % 5 == 0) {
         Level level = this.level();
         float radius = this.getVisualSize();
         Vec3 pos = this.position().add(0.0, this.getSize(), 0.0);
         int ySteps = (int)(3.2 * radius);
         int xSteps = (int)(6.4 * radius);
         float yDeg = 180.0F / ySteps * (float) (Math.PI / 180.0);
         float xDeg = 360.0F / xSteps * (float) (Math.PI / 180.0);

         for (int x = 0; x < xSteps; x++) {
            for (int y = -ySteps; y < ySteps; y++) {
               double dx = level.random.nextDouble() * 0.05 - 0.05;
               double dy = level.random.nextDouble() * 0.05 - 0.05;
               double dz = level.random.nextDouble() * 0.05 - 0.05;
               Vec3 offset = new Vec3(0.0, 0.0, radius).yRot(y * yDeg).xRot(x * xDeg).zRot((float) (-Math.PI / 2)).multiply(1.0, 0.85F, 1.0);
               level.addParticle(ParticleTypes.ASH, pos.x + offset.x + dx, pos.y + 1.0 + offset.y + dy, pos.z + offset.z + dz, 0.0, 0.0, 0.0);
               level.addParticle(ParticleTypes.WHITE_ASH, pos.x + offset.x + dx, pos.y + 1.0 + offset.y + dy, pos.z + offset.z + dz, 0.0, 0.0, 0.0);
            }
         }
      }
   }
}
