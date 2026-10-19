package com.github.b4ndithelps.tennogamenolife.registry.skill;

import com.github.b4ndithelps.tennogamenolife.ability.skill.extra.BarrierSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.extra.BlackArrowSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.extra.HeavenlyShiftSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic.EternalFourthProtectionSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic.SpiritCircuitOverloadSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate.FarCryUltimateSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate.GodlySmiteUltimateSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate.ReisenUltimateSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate.TengekiUltimateSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.unique.MagicBeadsSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TenreincarnationSkill {
   public static DeferredRegister<ManasSkill> skillRegistry = DeferredRegister.create(SkillAPI.getSkillRegistryKey(), "tennogamenolife");
   public static final DeferredHolder<ManasSkill, BarrierSkill> BARRIER = skillRegistry.register("barrier", BarrierSkill::new);
   public static final DeferredHolder<ManasSkill, HeavenlyShiftSkill> HEAVENLY_SHIFT = skillRegistry.register("heavenly_shift", HeavenlyShiftSkill::new);
   public static final DeferredHolder<ManasSkill, BlackArrowSkill> BLACK_ARROW = skillRegistry.register("black_arrow", BlackArrowSkill::new);
   public static final DeferredHolder<ManasSkill, SpiritCircuitOverloadSkill> SPIRIT_CIRCUIT_OVERLOAD = skillRegistry.register(
      "spirit_circuit_overload", SpiritCircuitOverloadSkill::new
   );
   public static final DeferredHolder<ManasSkill, EternalFourthProtectionSkill> ETERNAL_FOURTH_PROTECTION = skillRegistry.register(
      "eternal_fourth_protection", EternalFourthProtectionSkill::new
   );
   public static final DeferredHolder<ManasSkill, MagicBeadsSkill> MAGIC_BEADS = skillRegistry.register("magic_beads", MagicBeadsSkill::new);
   public static final DeferredHolder<ManasSkill, TengekiUltimateSkill> TENGEKI = skillRegistry.register("tengeki", TengekiUltimateSkill::new);
   public static final DeferredHolder<ManasSkill, GodlySmiteUltimateSkill> GODLY_SMITE = skillRegistry.register("godly_smite", GodlySmiteUltimateSkill::new);
   public static final DeferredHolder<ManasSkill, ReisenUltimateSkill> REISEN = skillRegistry.register("reisen", ReisenUltimateSkill::new);
   public static final DeferredHolder<ManasSkill, FarCryUltimateSkill> FAR_CRY = skillRegistry.register("far_cry", FarCryUltimateSkill::new);

   public static void register(IEventBus modEventBus) {
      skillRegistry.register(modEventBus);
   }
}
