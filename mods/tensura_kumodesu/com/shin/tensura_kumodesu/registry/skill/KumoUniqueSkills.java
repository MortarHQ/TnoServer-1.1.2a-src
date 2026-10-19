package com.shin.tensura_kumodesu.registry.skill;

import com.shin.tensura_kumodesu.ability.skill.unique.DeterminationSkill;
import com.shin.tensura_kumodesu.ability.skill.unique.HeightOfOccultismSkill;
import com.shin.tensura_kumodesu.ability.skill.unique.PerseveranceSkill;
import com.shin.tensura_kumodesu.ability.skill.unique.ResolveSkill;
import com.shin.tensura_kumodesu.ability.skill.unique.WeaponCreationSkill;
import com.shin.tensura_kumodesu.ability.skill.unique.WondrousWeaponCreationSkill;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class KumoUniqueSkills {
   public static final RegistrySupplier<WeaponCreationSkill> WEAPONCREATION = register("weapon_creation", WeaponCreationSkill::new);
   public static final RegistrySupplier<WondrousWeaponCreationSkill> WONDROUSWEAPONCREATION = register(
      "wondrous_weapon_creation", WondrousWeaponCreationSkill::new
   );
   public static final RegistrySupplier<ResolveSkill> RESOLVE = register("resolve", ResolveSkill::new);
   public static final RegistrySupplier<DeterminationSkill> DETERMINATION = register("determination", DeterminationSkill::new);
   public static final RegistrySupplier<PerseveranceSkill> PERSEVERANCE = register("perseverance", PerseveranceSkill::new);
   public static final RegistrySupplier<HeightOfOccultismSkill> HEIGHTOFOCCULTISM = register("height_of_occultism", HeightOfOccultismSkill::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", name), supplier);
   }

   public static void init() {
   }
}
