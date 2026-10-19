package com.shin.tensura_kumodesu.registry.skill;

import com.shin.tensura_kumodesu.ability.skill.extra.DeadlyPoisonSkill;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class KumoExtraSkills {
   public static final RegistrySupplier<DeadlyPoisonSkill> DEADLYPOISON = register("deadly_poison", DeadlyPoisonSkill::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", name), supplier);
   }

   public static void init() {
   }
}
