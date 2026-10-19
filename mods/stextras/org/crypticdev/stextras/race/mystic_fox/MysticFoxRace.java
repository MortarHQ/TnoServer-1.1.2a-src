package org.crypticdev.stextras.race.mystic_fox;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.BossRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.List;
import java.util.Map;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.crypticdev.stextras.config.race.MysticFoxConfig;
import org.crypticdev.stextras.registary.race.STExtrasRaces;

public class MysticFoxRace extends DefaultRace {
   public MysticFoxRace(Difficulty difficulty) {
      super(difficulty);
   }

   public MysticFoxRace() {
      this(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return this.getMysticFoxConfig().MysticFox;
   }

   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasSkill)IntrinsicSkills.BEAST_TRANSFORMATION.get(), (ManasSkill)CommonSkills.SELF_REGENERATION.get());
   }

   public void onActivateAbility(ManasRaceInstance instance, LivingEntity entity) {
      if (entity.hasEffect(MobEffects.MOVEMENT_SPEED)) {
         entity.removeEffect(MobEffects.MOVEMENT_SPEED);
      } else {
         entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1728000, 0, false, false, false));
      }
   }

   public double getElementalSpiritsChance(Element element, SpiritLevel level) {
      if (!Element.EARTH.equals(element)) {
         return super.getElementalSpiritsChance(element, level);
      } else {
         return SpiritLevel.MEDIUM.equals(level) ? 100.0 : super.getElementalSpiritsChance(element, level) * 2.0;
      }
   }

   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)STExtrasRaces.SOUL_BEAST.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)STExtrasRaces.SOUL_BEAST.get());
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance instance, LivingEntity entity) {
      return Map.of();
   }

   protected MysticFoxConfig getMysticFoxConfig() {
      return (MysticFoxConfig)ConfigRegistry.getConfig(MysticFoxConfig.class);
   }

   protected Map<EvolutionRequirement, Float> epRequirement(double requirement) {
      return requirement <= 0.0 ? Map.of() : Map.of(new EPRequirement(requirement), 100.0F);
   }

   protected Map<EvolutionRequirement, Float> epAndBossRequirement(double requirement, int bossRequirement) {
      if (requirement <= 0.0 && bossRequirement <= 0) {
         return Map.of();
      } else if (bossRequirement <= 0) {
         return this.epRequirement(requirement);
      } else {
         return requirement <= 0.0
            ? Map.of(new BossRequirement(bossRequirement), 100.0F)
            : Map.of(new EPRequirement(requirement), 50.0F, new BossRequirement(bossRequirement), 50.0F);
      }
   }
}
