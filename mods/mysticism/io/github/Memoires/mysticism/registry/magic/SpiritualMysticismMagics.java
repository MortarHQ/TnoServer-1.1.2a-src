package io.github.Memoires.mysticism.registry.magic;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.ability.magic.spiritual.earth.GravitationalVoidMagic;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class SpiritualMysticismMagics {
   public static final RegistrySupplier<GravitationalVoidMagic> GRAVITATIONAL_VOID = register("gravitational_void", GravitationalVoidMagic::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("mysticism", name), supplier);
   }

   public static void init() {
   }
}
