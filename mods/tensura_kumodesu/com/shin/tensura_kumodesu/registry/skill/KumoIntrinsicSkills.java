package com.shin.tensura_kumodesu.registry.skill;

import com.shin.tensura_kumodesu.ability.skill.intrinsic.DeadlySpiderPoisonSkill;
import com.shin.tensura_kumodesu.ability.skill.intrinsic.DivineMagicReleaseSkill;
import com.shin.tensura_kumodesu.ability.skill.intrinsic.EggLayingSkill;
import com.shin.tensura_kumodesu.ability.skill.intrinsic.FatalSpiderPoisonSkill;
import com.shin.tensura_kumodesu.ability.skill.intrinsic.HumanificationSkill;
import com.shin.tensura_kumodesu.ability.skill.intrinsic.SpiderMovementSkill;
import com.shin.tensura_kumodesu.ability.skill.intrinsic.TabooSkill;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class KumoIntrinsicSkills {
   public static final RegistrySupplier<SpiderMovementSkill> SPIDERMOVEMENT = register("spider_movement", SpiderMovementSkill::new);
   public static final RegistrySupplier<DeadlySpiderPoisonSkill> DEADLYSPIDERPOISON = register("deadly_spider_poison", DeadlySpiderPoisonSkill::new);
   public static final RegistrySupplier<DivineMagicReleaseSkill> DIVINEMAGICRELEASE = register("divine_magic_release", DivineMagicReleaseSkill::new);
   public static final RegistrySupplier<EggLayingSkill> EGGLAYING = register("egg_laying", EggLayingSkill::new);
   public static final RegistrySupplier<FatalSpiderPoisonSkill> FATALSPIDERPOISON = register("fatal_spider_poison", FatalSpiderPoisonSkill::new);
   public static final RegistrySupplier<HumanificationSkill> HUMANIFICATION = register("humanification", HumanificationSkill::new);
   public static final RegistrySupplier<TabooSkill> TABOO = register("taboo", TabooSkill::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", name), supplier);
   }

   public static void init() {
   }
}
