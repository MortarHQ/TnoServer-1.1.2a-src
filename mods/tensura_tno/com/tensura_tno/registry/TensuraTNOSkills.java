package com.tensura_tno.registry;

import com.tensura_tno.ability.skill.ContractLittleFoxSkill;
import com.tensura_tno.ability.skill.EdoTenseiSkill;
import com.tensura_tno.ability.skill.HumanFormSkill;
import com.tensura_tno.ability.skill.MagicOreSenseSkill;
import com.tensura_tno.ability.skill.SpiritEnhancementSkill;
import com.tensura_tno.ability.skill.SpiritSummonSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TensuraTNOSkills {
   public static final DeferredRegister<ManasSkill> SKILL_REGISTRY = DeferredRegister.create(SkillAPI.getSkillRegistryKey(), "tensura_tno");
   public static final DeferredHolder<ManasSkill, ContractLittleFoxSkill> CONTRACT_LITTLE_FOX = SKILL_REGISTRY.register(
      "contract_little_fox", ContractLittleFoxSkill::new
   );
   public static final DeferredHolder<ManasSkill, SpiritSummonSkill> SPIRIT_SUMMON = SKILL_REGISTRY.register("spirit_summon", SpiritSummonSkill::new);
   public static final DeferredHolder<ManasSkill, EdoTenseiSkill> EDO_TENSEI = SKILL_REGISTRY.register("edo_tensei", EdoTenseiSkill::new);
   public static final DeferredHolder<ManasSkill, SpiritEnhancementSkill> SPIRIT_ENHANCEMENT = SKILL_REGISTRY.register(
      "spirit_enhancement", SpiritEnhancementSkill::new
   );
   public static final DeferredHolder<ManasSkill, MagicOreSenseSkill> MAGIC_ORE_SENSE = SKILL_REGISTRY.register("magic_ore_sense", MagicOreSenseSkill::new);
   public static final DeferredHolder<ManasSkill, HumanFormSkill> HUMAN_FORM = SKILL_REGISTRY.register("human_form", HumanFormSkill::new);

   private TensuraTNOSkills() {
   }

   public static void register(IEventBus modBus) {
      SKILL_REGISTRY.register(modBus);
   }
}
