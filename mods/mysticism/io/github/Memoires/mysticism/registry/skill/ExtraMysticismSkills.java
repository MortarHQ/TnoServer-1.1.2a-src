package io.github.Memoires.mysticism.registry.skill;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.ability.skill.extra.CryogenicCessationSkill;
import io.github.Memoires.mysticism.ability.skill.extra.DarknessDominationSkill;
import io.github.Memoires.mysticism.ability.skill.extra.DarknessManipulationSkill;
import io.github.Memoires.mysticism.ability.skill.extra.GravityFluxSkill;
import io.github.Memoires.mysticism.ability.skill.extra.IceDominationSkill;
import io.github.Memoires.mysticism.ability.skill.extra.IceManipulationSkill;
import io.github.Memoires.mysticism.ability.skill.extra.LightDominationSkill;
import io.github.Memoires.mysticism.ability.skill.extra.LightManipulationSkill;
import io.github.Memoires.mysticism.ability.skill.extra.MithrilStrengthSkill;
import io.github.Memoires.mysticism.ability.skill.extra.ProfanedProminenceSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class ExtraMysticismSkills {
   public static final RegistrySupplier<MithrilStrengthSkill> MITHRIL_STRENGTH = register("mithril_strength", MithrilStrengthSkill::new);
   public static final RegistrySupplier<DarknessDominationSkill> DARKNESS_DOMINATION = register("darkness_domination", DarknessDominationSkill::new);
   public static final RegistrySupplier<DarknessManipulationSkill> DARKNESS_MANIPULATION = register("darkness_manipulation", DarknessManipulationSkill::new);
   public static final RegistrySupplier<GravityFluxSkill> GRAVITY_FLUX = register("gravity_flux", GravityFluxSkill::new);
   public static final RegistrySupplier<LightDominationSkill> LIGHT_DOMINATION = register("light_domination", LightDominationSkill::new);
   public static final RegistrySupplier<LightManipulationSkill> LIGHT_MANIPULATION = register("light_manipulation", LightManipulationSkill::new);
   public static final RegistrySupplier<IceManipulationSkill> ICE_MANIPULATION = register("ice_manipulation", IceManipulationSkill::new);
   public static final RegistrySupplier<IceDominationSkill> ICE_DOMINATION = register("ice_domination", IceDominationSkill::new);
   public static final RegistrySupplier<CryogenicCessationSkill> CRYOGENIC_CESSATION = register("cryogenic_cessation", CryogenicCessationSkill::new);
   public static final RegistrySupplier<ProfanedProminenceSkill> PROFANED_PROMINENCE = register("profaned_prominence", ProfanedProminenceSkill::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("mysticism", name), supplier);
   }

   public static void init() {
   }
}
