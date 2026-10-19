package com.foxablazeultimate.registry;

import com.foxablazeultimate.ability.skill.azathoth.AzathothSkill;
import com.foxablazeultimate.ability.skill.beelzebub.BeelzebubSkill;
import com.foxablazeultimate.ability.skill.ciel.CielSkill;
import com.foxablazeultimate.ability.skill.fortuna.FortunaSkill;
import com.foxablazeultimate.ability.skill.hellsniper.HellSniperSkill;
import com.foxablazeultimate.ability.skill.mammon.MammonSkill;
import com.foxablazeultimate.ability.skill.raphael.RaphaelSkill;
import com.foxablazeultimate.ability.skill.satanael.SatanaelSkill;
import com.foxablazeultimate.ability.skill.shubniggurath.ShubNiggurathSkill;
import com.foxablazeultimate.ability.skill.uriel.UrielSkill;
import com.foxablazeultimate.ability.skill.veldora.VeldoraSkill;
import com.foxablazeultimate.ability.skill.velgrynd.VelgryndSkill;
import com.foxablazeultimate.ability.skill.yogsothoth.YogSothothSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.Optional;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class FoxAblazeUltimateSkills {
   public static final String TENSURA_EXTRAS_MOD_ID = "tno_tensura_extras";
   public static final DeferredRegister<ManasSkill> SKILL_REGISTRY = DeferredRegister.create(SkillAPI.getSkillRegistryKey(), "foxablazeultimate");
   public static final DeferredHolder<ManasSkill, RaphaelSkill> RAPHAEL = SKILL_REGISTRY.register("raphael", RaphaelSkill::new);
   public static final DeferredHolder<ManasSkill, CielSkill> CIEL = SKILL_REGISTRY.register("ciel", CielSkill::new);
   public static final DeferredHolder<ManasSkill, BeelzebubSkill> BEELZEBUB = SKILL_REGISTRY.register("beelzebuth", BeelzebubSkill::new);
   public static final DeferredHolder<ManasSkill, UrielSkill> URIEL = SKILL_REGISTRY.register("uriel", UrielSkill::new);
   public static final DeferredHolder<ManasSkill, VeldoraSkill> VELDORA = SKILL_REGISTRY.register("veldora", VeldoraSkill::new);
   public static final DeferredHolder<ManasSkill, SatanaelSkill> SATANAEL = SKILL_REGISTRY.register("satanael", SatanaelSkill::new);
   public static final DeferredHolder<ManasSkill, ShubNiggurathSkill> SHUB_NIGGURATH = SKILL_REGISTRY.register("shub_niggurath", ShubNiggurathSkill::new);
   public static final DeferredHolder<ManasSkill, FortunaSkill> FORTUNA = SKILL_REGISTRY.register("fortuna", FortunaSkill::new);
   public static final DeferredHolder<ManasSkill, MammonSkill> MAMMON = SKILL_REGISTRY.register("mammon", MammonSkill::new);
   public static final DeferredHolder<ManasSkill, HellSniperSkill> HELL_SNIPER = SKILL_REGISTRY.register("hell_sniper", HellSniperSkill::new);
   public static final DeferredHolder<ManasSkill, VelgryndSkill> VELGRYND = SKILL_REGISTRY.register("velgrynd", VelgryndSkill::new);
   private static DeferredHolder<ManasSkill, YogSothothSkill> yogSothoth;
   public static final DeferredHolder<ManasSkill, AzathothSkill> AZATHOTH = SKILL_REGISTRY.register("azathoth", AzathothSkill::new);

   private FoxAblazeUltimateSkills() {
   }

   public static void register(IEventBus modBus) {
      SKILL_REGISTRY.addAlias(LegacySkillIds.BEELZEBUB, LegacySkillIds.BEELZEBUTH);
      if (ModList.get().isLoaded("tno_tensura_extras")) {
         yogSothoth = SKILL_REGISTRY.register("yog_sothoth", YogSothothSkill::new);
      }

      SKILL_REGISTRY.register(modBus);
   }

   public static Optional<DeferredHolder<ManasSkill, YogSothothSkill>> yogSothothHolder() {
      return Optional.ofNullable(yogSothoth);
   }

   public static Optional<YogSothothSkill> yogSothoth() {
      DeferredHolder<ManasSkill, YogSothothSkill> holder = yogSothoth;
      return holder != null && holder.isBound() ? Optional.of((YogSothothSkill)holder.get()) : Optional.empty();
   }
}
