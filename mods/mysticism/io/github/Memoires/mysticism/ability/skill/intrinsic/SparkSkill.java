package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class SparkSkill extends Skill {
   protected static final IntrinsicMysticismSkillConfig.Spark CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .Spark;

   public SparkSkill() {
      super(SkillType.INTRINSIC);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/spark.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (instance.isToggled() && !instance.onCoolDown(0)) {
         target.setRemainingFireTicks(CONFIG.burnDurationMastered);
         return true;
      }

      if (this.isInSlot(owner) && !instance.onCoolDown(0)) {
         int fireDuration = instance.isMastered(owner) ? CONFIG.burnDurationMastered : CONFIG.burnDuration;
         target.setRemainingFireTicks(fireDuration);
         instance.addMasteryPoint(owner);
         int cooldown = instance.isMastered(owner) ? CONFIG.cooldownMastered : CONFIG.cooldown;
         instance.setCoolDown(cooldown, 0);
      }

      return true;
   }
}
