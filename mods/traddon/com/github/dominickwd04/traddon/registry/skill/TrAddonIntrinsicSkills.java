package com.github.dominickwd04.traddon.registry.skill;

import com.github.dominickwd04.traddon.ability.skill.intrinsic.CrystalDustSkill;
import com.github.dominickwd04.traddon.ability.skill.intrinsic.PhotonBladeSkill;
import com.github.dominickwd04.traddon.ability.skill.intrinsic.RefractiveShellSkill;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;

public class TrAddonIntrinsicSkills {
   private static final DeferredRegister<ManasSkill> SKILLS = DeferredRegister.create("traddon", SkillAPI.getSkillRegistryKey());
   public static final RegistrySupplier<ManasSkill> CRYSTAL_DUST = SKILLS.register("crystal_dust", CrystalDustSkill::new);
   public static final RegistrySupplier<ManasSkill> REFRACTIVE_SHELL = SKILLS.register("refractive_shell", RefractiveShellSkill::new);
   public static final RegistrySupplier<ManasSkill> PHOTON_BLADE = SKILLS.register("photon_blade", PhotonBladeSkill::new);

   public static void init() {
      SKILLS.register();
   }
}
