package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.ability.skill.intrinsic.RelapseSkill;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class SanctifyingLightEffect extends TensuraMobEffect implements DamageAction {
   public SanctifyingLightEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(200, 150, 126).getRGB());
   }

   public boolean onDamagingEntity(LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!this.checkAttackerSkill(attacker)) {
         return true;
      }

      if (source.is(TensuraDamageTypes.HOLY_DAMAGE)) {
         return true;
      }

      float holyDamage = (float)this.damageFromRace(attacker);
      if (holyDamage <= 0.0F) {
         return true;
      }

      DamageSource holySource = TensuraDamageTypes.getEntityDamageSource(attacker.level(), TensuraDamageTypes.HOLY_DAMAGE, attacker)
         .tensura$setAbilityInstance(
            (ManasSkillInstance)SkillAPI.getSkillsFrom(attacker).getSkill((ManasSkill)IntrinsicMysticismSkills.RELAPSE.get()).orElse(null)
         )
         .tensura$setMagicType(MagicType.SPIRITUAL);
      target.hurt(holySource, holyDamage);
      if (this.checkIfClear(attacker)) {
         attacker.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.SANCTIFYING_LIGHT));
      }

      return super.onDamagingEntity(attacker, target, source, amount);
   }

   private boolean checkAttackerSkill(LivingEntity entity) {
      Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)IntrinsicMysticismSkills.RELAPSE.get());
      return skill.isPresent();
   }

   private double damageFromRace(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(manasRaceInstance -> {
         if (manasRaceInstance.getRace().equals(MysticismRaces.WHOLE)) {
            return RelapseSkill.CONFIG.gimelDamageWhole;
         } else if (manasRaceInstance.getRace().equals(MysticismRaces.ASCENDED)) {
            return RelapseSkill.CONFIG.gimelDamageAscended;
         } else {
            return manasRaceInstance.getRace().equals(MysticismRaces.DIVINE_EXCELSIUS) ? RelapseSkill.CONFIG.gimelDamageExcelsius : 0.0F;
         }
      }).orElse(0.0F).floatValue();
   }

   private boolean checkIfClear(LivingEntity entity) {
      Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)IntrinsicMysticismSkills.RELAPSE.get());
      if (skill.isPresent()) {
         int remainingHits = skill.get().getOrCreateTag().getInt("WholeHits");
         return remainingHits <= 0;
      } else {
         return true;
      }
   }
}
