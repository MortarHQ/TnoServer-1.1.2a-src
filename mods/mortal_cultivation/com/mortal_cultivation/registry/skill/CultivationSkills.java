package com.mortal_cultivation.registry.skill;

import com.mortal_cultivation.ability.skill.extra.CultivatingTheMysticDaoSkill;
import com.mortal_cultivation.ability.skill.extra.HundredGhostsSurgeSkill;
import com.mortal_cultivation.ability.skill.extra.MyriadSwordsSkill;
import com.mortal_cultivation.ability.skill.extra.SwordFlightSkill;
import com.mortal_cultivation.ability.skill.intrinsic.AlchemistFireSkill;
import com.mortal_cultivation.ability.skill.intrinsic.EarlyCultivationSkill;
import com.mortal_cultivation.ability.skill.intrinsic.SizeTransformationSkill;
import com.mortal_cultivation.ability.skill.unique.AlchemyProdigySkill;
import com.mortal_cultivation.ability.skill.unique.GreatSageSkill;
import com.mortal_cultivation.ability.skill.unique.MischiefSkill;
import com.mortal_cultivation.ability.skill.unique.QiFlightSkill;
import com.mortal_cultivation.ability.skill.unique.TalismanMasterSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CultivationSkills {
   public static final DeferredRegister<ManasSkill> skillRegistry = DeferredRegister.create(SkillAPI.getSkillRegistryKey(), "mortal_cultivation");
   public static final DeferredHolder<ManasSkill, EarlyCultivationSkill> EARLY_CULTIVATION = skillRegistry.register(
      "early_cultivation", EarlyCultivationSkill::new
   );
   public static final DeferredHolder<ManasSkill, SizeTransformationSkill> SIZE_TRANSFORMATION = skillRegistry.register(
      "size_transformation", SizeTransformationSkill::new
   );
   public static final DeferredHolder<ManasSkill, AlchemistFireSkill> ALCHEMIST_FIRE = skillRegistry.register("alchemist_fire", AlchemistFireSkill::new);
   public static final DeferredHolder<ManasSkill, CultivatingTheMysticDaoSkill> CULTIVATING_THE_MYSTIC_DAO = skillRegistry.register(
      "cultivating_the_mystic_dao", CultivatingTheMysticDaoSkill::new
   );
   public static final DeferredHolder<ManasSkill, HundredGhostsSurgeSkill> HUNDRED_GHOSTS_SURGE = skillRegistry.register(
      "hundred_ghosts_surge", HundredGhostsSurgeSkill::new
   );
   public static final DeferredHolder<ManasSkill, SwordFlightSkill> SWORD_FLIGHT = skillRegistry.register("sword_flight", SwordFlightSkill::new);
   public static final DeferredHolder<ManasSkill, MyriadSwordsSkill> MYRIAD_SWORDS_RETURN = skillRegistry.register(
      "myriad_swords_return", MyriadSwordsSkill::new
   );
   public static final DeferredHolder<ManasSkill, TalismanMasterSkill> TALISMAN_MASTER = skillRegistry.register("talisman_master", TalismanMasterSkill::new);
   public static final DeferredHolder<ManasSkill, QiFlightSkill> QI_FLIGHT = skillRegistry.register("qi_flight", QiFlightSkill::new);
   public static final DeferredHolder<ManasSkill, AlchemyProdigySkill> ALCHEMY_PRODIGY = skillRegistry.register("alchemy_prodigy", AlchemyProdigySkill::new);
   public static final DeferredHolder<ManasSkill, MischiefSkill> MISCHIEF = skillRegistry.register("mischief", MischiefSkill::new);
   public static final DeferredHolder<ManasSkill, GreatSageSkill> GREAT_SAGE = skillRegistry.register("great_sage", GreatSageSkill::new);

   public static void register(IEventBus modEventBus) {
      skillRegistry.register(modEventBus);
   }
}
