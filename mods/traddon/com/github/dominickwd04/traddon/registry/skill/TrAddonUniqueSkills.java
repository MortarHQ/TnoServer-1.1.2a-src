package com.github.dominickwd04.traddon.registry.skill;

import com.github.dominickwd04.traddon.ability.skill.unique.DevastatorSkill;
import com.github.dominickwd04.traddon.ability.skill.unique.ResonantCoreSkill;
import com.github.dominickwd04.traddon.ability.skill.unique.TotalReflectionSkill;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;

public class TrAddonUniqueSkills {
   private static final DeferredRegister<ManasSkill> SKILLS = DeferredRegister.create("traddon", SkillAPI.getSkillRegistryKey());
   public static final RegistrySupplier<ManasSkill> DEVASTATOR = SKILLS.register("devastator", DevastatorSkill::new);
   public static final RegistrySupplier<ManasSkill> RESONANT_CORE = SKILLS.register("resonant_core", ResonantCoreSkill::new);
   public static final RegistrySupplier<ManasSkill> TOTAL_REFLECTION = SKILLS.register("total_reflection", TotalReflectionSkill::new);

   public static void init() {
      SKILLS.register();
   }
}
