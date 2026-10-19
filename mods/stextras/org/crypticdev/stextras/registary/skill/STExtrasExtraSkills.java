package org.crypticdev.stextras.registary.skill;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.ability.skill.extra.KeenNoseSkill;

public class STExtrasExtraSkills {
   public static final RegistrySupplier<KeenNoseSkill> KEEN_NOSE = register("keen_nose", KeenNoseSkill::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillAPI.getSkillRegistry().register(ResourceLocation.fromNamespaceAndPath("stextras", name), supplier);
   }

   public static void init() {
   }
}
