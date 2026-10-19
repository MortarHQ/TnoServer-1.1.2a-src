package net.crypticmc.tr_unique_monsters.registry.skill;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.crypticmc.tr_unique_monsters.ability.skill.extra.AppraisalEyeSkill;

public class ExtraSkills {
   private static final DeferredRegister<ManasSkill> SKILLS = DeferredRegister.create("tr_unique_monsters", SkillAPI.getSkillRegistryKey());
   public static final RegistrySupplier<AppraisalEyeSkill> APPRAISAL_EYE = SKILLS.register("appraisal_eye", AppraisalEyeSkill::new);

   public static void init() {
      SKILLS.register();
   }
}
