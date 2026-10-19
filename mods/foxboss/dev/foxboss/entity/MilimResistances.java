package dev.foxboss.entity;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill.ResistType;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

final class MilimResistances {
   static final String MIGRATED = "FoxBossResistancesV1";
   static final List<ResourceLocation> IDS = List.of(
         "abnormal_condition_resistance",
         "cold_resistance",
         "corrosion_resistance",
         "darkness_attack_resistance",
         "earth_attack_resistance",
         "electricity_resistance",
         "flame_attack_resistance",
         "gravity_attack_resistance",
         "heat_resistance",
         "holy_attack_resistance",
         "light_attack_resistance",
         "magic_resistance",
         "physical_attack_resistance",
         "pain_resistance",
         "paralysis_resistance",
         "pierce_resistance",
         "poison_resistance",
         "spatial_attack_resistance",
         "spiritual_attack_resistance",
         "thermal_fluctuation_resistance",
         "water_attack_resistance",
         "wind_attack_resistance"
      )
      .stream()
      .map(path -> ResourceLocation.fromNamespaceAndPath("tensura", path))
      .toList();

   static void migrate(MilimEntity milim) {
      if (!milim.level().isClientSide
         && milim.isAlive()
         && !milim.isDemo()
         && !milim.isDebugAnimation()
         && !milim.getPersistentData().getBoolean("FoxBossResistancesV1")) {
         SkillStorage storage = SkillAPI.getSkillsFrom(milim);
         storage.forgetSkill((ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.get());
         storage.forgetSkill((ManasSkill)ResistanceSkills.MAGIC_NULLIFICATION.get());

         for (ResourceLocation id : IDS) {
            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
            if (!(skill instanceof ResistSkill resistance) || resistance.getResistType() != ResistType.RESISTANCE) {
               return;
            }

            ManasSkillInstance instance = (ManasSkillInstance)storage.getSkill(id).orElse(null);
            if (instance == null) {
               if (!storage.learnSkill(skill.createDefaultInstance(), null)) {
                  return;
               }

               instance = (ManasSkillInstance)storage.getSkill(id).orElseThrow();
            }

            if (instance.getMastery() < 0.0) {
               instance.setMastery(0.0);
               instance.onLearnSkill(milim);
               storage.checkAndMarkDirty(instance);
            }

            if (!instance.isToggled()) {
               instance.setToggled(true);
               instance.onToggleOn(milim);
               storage.checkAndMarkDirty(instance);
            }
         }

         milim.getPersistentData().putBoolean("FoxBossResistancesV1", true);
      }
   }

   private MilimResistances() {
   }
}
