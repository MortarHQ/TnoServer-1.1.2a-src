package io.github.Memoires.mysticism.registry.skill;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.ability.skill.intrinsic.BulletPunchSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.ClangorousSoulSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.CorrosionTransformSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.DischargeSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.DissonanceSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.ExoskeletonSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.HellHallSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.LethalPoisonSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.LightningModeSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.MagisteelBodySkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.ParalysisTransformSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.PoisonTransformSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.RelapseSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.SparkSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.TenacitySkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class IntrinsicMysticismSkills {
   public static final RegistrySupplier<BulletPunchSkill> BULLET_PUNCH = register("bullet_punch", BulletPunchSkill::new);
   public static final RegistrySupplier<CorrosionTransformSkill> CORROSION_TRANSFORM = register("corrosion_transform", CorrosionTransformSkill::new);
   public static final RegistrySupplier<ClangorousSoulSkill> CLANGOROUS_SOUL = register("clangorous_soul", ClangorousSoulSkill::new);
   public static final RegistrySupplier<DischargeSkill> DISCHARGE = register("discharge", DischargeSkill::new);
   public static final RegistrySupplier<DissonanceSkill> DISSONANCE = register("dissonance", DissonanceSkill::new);
   public static final RegistrySupplier<ExoskeletonSkill> EXOSKELETON = register("exoskeleton", ExoskeletonSkill::new);
   public static final RegistrySupplier<HellHallSkill> HELL_HALL = register("hell_hall", HellHallSkill::new);
   public static final RegistrySupplier<LethalPoisonSkill> LETHAL_POISON = register("lethal_poison", LethalPoisonSkill::new);
   public static final RegistrySupplier<LightningModeSkill> LIGHTNING_MODE = register("lightning_mode", LightningModeSkill::new);
   public static final RegistrySupplier<MagisteelBodySkill> MAGISTEEL_BODY = register("magisteel_body", MagisteelBodySkill::new);
   public static final RegistrySupplier<ParalysisTransformSkill> PARALYSIS_TRANSFORM = register("paralysis_transform", ParalysisTransformSkill::new);
   public static final RegistrySupplier<PoisonTransformSkill> POISON_TRANSFORM = register("poison_transform", PoisonTransformSkill::new);
   public static final RegistrySupplier<RelapseSkill> RELAPSE = register("relapse", RelapseSkill::new);
   public static final RegistrySupplier<SparkSkill> SPARK = register("spark", SparkSkill::new);
   public static final RegistrySupplier<TenacitySkill> TENACITY = register("tenacity", TenacitySkill::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("mysticism", name), supplier);
   }

   public static void init() {
   }
}
