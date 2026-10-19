package io.github.Memoires.mysticism.registry.skill;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.ability.skill.ultimate.EmbryoSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class UltimateMysticismSkills {
   public static final RegistrySupplier<ManasSkill> EMBRYO = register("embryo", EmbryoSkill::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("mysticism", name), supplier);
   }

   public static void init() {
   }
}
