package io.github.Memoires.mysticism.registry.skill;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.ability.skill.unique.ButcherSkill;
import io.github.Memoires.mysticism.ability.skill.unique.CaptivatorSkill;
import io.github.Memoires.mysticism.ability.skill.unique.CoalescenceSkill;
import io.github.Memoires.mysticism.ability.skill.unique.ConstantSkill;
import io.github.Memoires.mysticism.ability.skill.unique.CorroderSkill;
import io.github.Memoires.mysticism.ability.skill.unique.CrasherSkill;
import io.github.Memoires.mysticism.ability.skill.unique.CultivatorSkill;
import io.github.Memoires.mysticism.ability.skill.unique.DreamerSkill;
import io.github.Memoires.mysticism.ability.skill.unique.EngineerSkill;
import io.github.Memoires.mysticism.ability.skill.unique.FourthWallSkill;
import io.github.Memoires.mysticism.ability.skill.unique.GardenerSkill;
import io.github.Memoires.mysticism.ability.skill.unique.GatekeeperSkill;
import io.github.Memoires.mysticism.ability.skill.unique.HiddenRulerSkill;
import io.github.Memoires.mysticism.ability.skill.unique.JinchurikiSkill;
import io.github.Memoires.mysticism.ability.skill.unique.KyuremSkill;
import io.github.Memoires.mysticism.ability.skill.unique.MalleableSkill;
import io.github.Memoires.mysticism.ability.skill.unique.MelancholySkill;
import io.github.Memoires.mysticism.ability.skill.unique.PhaserSkill;
import io.github.Memoires.mysticism.ability.skill.unique.ProviderSkill;
import io.github.Memoires.mysticism.ability.skill.unique.ReducerSkill;
import io.github.Memoires.mysticism.ability.skill.unique.RepeaterSkill;
import io.github.Memoires.mysticism.ability.skill.unique.ReshiramSkill;
import io.github.Memoires.mysticism.ability.skill.unique.RestrictedSkill;
import io.github.Memoires.mysticism.ability.skill.unique.ScholarSkill;
import io.github.Memoires.mysticism.ability.skill.unique.SchrodingerSkill;
import io.github.Memoires.mysticism.ability.skill.unique.SpiritualistSkill;
import io.github.Memoires.mysticism.ability.skill.unique.StagnatorSkill;
import io.github.Memoires.mysticism.ability.skill.unique.SubjugatorSkill;
import io.github.Memoires.mysticism.ability.skill.unique.VictoriousHarbingerSkill;
import io.github.Memoires.mysticism.ability.skill.unique.ZekromSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class UniqueMysticismSkills {
   public static final RegistrySupplier<ManasSkill> BUTCHER = register("butcher", ButcherSkill::new);
   public static final RegistrySupplier<ManasSkill> CAPTIVATOR = register("captivator", CaptivatorSkill::new);
   public static final RegistrySupplier<ManasSkill> COALESCENCE = register("coalescence", CoalescenceSkill::new);
   public static final RegistrySupplier<ManasSkill> CONSTANT = register("constant", ConstantSkill::new);
   public static final RegistrySupplier<ManasSkill> CORRODER = register("corroder", CorroderSkill::new);
   public static final RegistrySupplier<ManasSkill> CRASHER = register("crasher", CrasherSkill::new);
   public static final RegistrySupplier<ManasSkill> CULTIVATOR = register("cultivator", CultivatorSkill::new);
   public static final RegistrySupplier<ManasSkill> DREAMER = register("dreamer", DreamerSkill::new);
   public static final RegistrySupplier<ManasSkill> ENGINEER = register("engineer", EngineerSkill::new);
   public static final RegistrySupplier<ManasSkill> FOURTH_WALL = register("fourth_wall", FourthWallSkill::new);
   public static final RegistrySupplier<ManasSkill> GARDENER = register("gardener", GardenerSkill::new);
   public static final RegistrySupplier<ManasSkill> GATEKEEPER = register("gatekeeper", GatekeeperSkill::new);
   public static final RegistrySupplier<ManasSkill> HIDDEN_RULER = register("hidden_ruler", HiddenRulerSkill::new);
   public static final RegistrySupplier<ManasSkill> JINCHURIKI = register("jinchuriki", JinchurikiSkill::new);
   public static final RegistrySupplier<ManasSkill> KYUREM = register("kyurem", KyuremSkill::new);
   public static final RegistrySupplier<ManasSkill> MALLEABLE = register("malleable", MalleableSkill::new);
   public static final RegistrySupplier<ManasSkill> MELANCHOLY = register("melancholy", MelancholySkill::new);
   public static final RegistrySupplier<ManasSkill> PHASER = register("phaser", PhaserSkill::new);
   public static final RegistrySupplier<ManasSkill> PROVIDER = register("provider", ProviderSkill::new);
   public static final RegistrySupplier<ManasSkill> REDUCER = register("reducer", ReducerSkill::new);
   public static final RegistrySupplier<ManasSkill> RESHIRAM = register("reshiram", ReshiramSkill::new);
   public static final RegistrySupplier<ManasSkill> RESTRICTED = register("restricted", RestrictedSkill::new);
   public static final RegistrySupplier<ManasSkill> REPEATER = register("repeater", RepeaterSkill::new);
   public static final RegistrySupplier<ManasSkill> SCHOLAR = register("scholar", ScholarSkill::new);
   public static final RegistrySupplier<ManasSkill> SCHRODINGER = register("schrodinger", SchrodingerSkill::new);
   public static final RegistrySupplier<ManasSkill> SPIRITUALIST = register("spiritualist", SpiritualistSkill::new);
   public static final RegistrySupplier<ManasSkill> STAGNATOR = register("stagnator", StagnatorSkill::new);
   public static final RegistrySupplier<ManasSkill> SUBJUGATOR = register("subjugator", SubjugatorSkill::new);
   public static final RegistrySupplier<ManasSkill> VICTORIOUS_HARBINGER = register("victorious_harbinger", VictoriousHarbingerSkill::new);
   public static final RegistrySupplier<ManasSkill> ZEKROM = register("zekrom", ZekromSkill::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("mysticism", name), supplier);
   }

   public static void init() {
   }
}
