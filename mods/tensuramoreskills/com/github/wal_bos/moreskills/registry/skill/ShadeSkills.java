package com.github.wal_bos.moreskills.registry.skill;

import com.github.wal_bos.moreskills.ability.skill.shades.AsmodaySkill;
import com.github.wal_bos.moreskills.ability.skill.shades.IstarothSkill;
import com.github.wal_bos.moreskills.ability.skill.shades.NaberiusSkill;
import com.github.wal_bos.moreskills.ability.skill.shades.PhainonSkill;
import com.github.wal_bos.moreskills.ability.skill.shades.RonovaSkill;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class ShadeSkills {
   public static final RegistrySupplier<RonovaSkill> RONOVA = register("ronova", RonovaSkill::new);
   public static final RegistrySupplier<NaberiusSkill> NABERIUS = register("naberius", NaberiusSkill::new);
   public static final RegistrySupplier<IstarothSkill> ISTAROTH = register("istaroth", IstarothSkill::new);
   public static final RegistrySupplier<AsmodaySkill> ASMODAY = register("asmoday", AsmodaySkill::new);
   public static final RegistrySupplier<PhainonSkill> PHAINON = register("phainon", PhainonSkill::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", name), supplier);
   }

   public static void init() {
   }
}
