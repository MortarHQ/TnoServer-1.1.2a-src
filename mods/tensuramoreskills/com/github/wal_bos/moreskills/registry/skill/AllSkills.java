package com.github.wal_bos.moreskills.registry.skill;

import com.github.wal_bos.moreskills.ability.skill.extra.MorphSkill;
import com.github.wal_bos.moreskills.ability.skill.extra.PurifierSkill;
import com.github.wal_bos.moreskills.ability.skill.intrinsic.AntiSymbioteSkill;
import com.github.wal_bos.moreskills.ability.skill.intrinsic.BlackCommunionSkill;
import com.github.wal_bos.moreskills.ability.skill.intrinsic.BloodArtsSkill;
import com.github.wal_bos.moreskills.ability.skill.intrinsic.SymbioteSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.AbhothLordOfPestilenceSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.ArthurOnceAndFutureKingSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.AvalonGodOfEternalKingdomSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.AxiomLordOfReflectedJudgment;
import com.github.wal_bos.moreskills.ability.skill.ultimate.CaedrosGodOfConquestSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.DivineGeneralMahoragaSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.ElfariaTheHallowedIcemaidenSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.KronairosLordOfTheEndlessSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.MammonCasinoUltimateSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.MichaelAngelOfLocksSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.PainLordOfSixPathsSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.SchwiExMachinaSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.TrueAuthorityOfGreedSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.WitchOfEnvySatellaSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.WitchOfVainglorySkill;
import com.github.wal_bos.moreskills.ability.skill.unique.AdaptabilitySkill;
import com.github.wal_bos.moreskills.ability.skill.unique.AlbisVinaSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.AuthorityOfGreedSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.BoogieWoogieSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.DomainOfNegationSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.ExploiterSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.FungusInfectionSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.GeneEditorSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.GodlyScientistSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.GodlySmitherySkill;
import com.github.wal_bos.moreskills.ability.skill.unique.GreenThumbSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.IWantToBeJustLikeYouSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.MitamaFaithSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.NirvanaSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.ParasiteSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.SatellaSkill;
import com.github.wal_bos.moreskills.ability.skill.unique.VoidArchiveSkill;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class AllSkills {
   public static final RegistrySupplier<BoogieWoogieSkill> BOOGIE_WOOGIE = register("boogie_woogie", BoogieWoogieSkill::new);
   public static final RegistrySupplier<DomainOfNegationSkill> DOMAIN_OF_NEGATION = register("domain_of_negation", DomainOfNegationSkill::new);
   public static final RegistrySupplier<AdaptabilitySkill> ADAPTABILITY = register("adaptability", AdaptabilitySkill::new);
   public static final RegistrySupplier<FungusInfectionSkill> FUNGUS_INFECTION = register("fungus_infection", FungusInfectionSkill::new);
   public static final RegistrySupplier<MichaelAngelOfLocksSkill> MICHAEL_ANGEL_OF_LOCKS = register("michael_angel_of_locks", MichaelAngelOfLocksSkill::new);
   public static final RegistrySupplier<GreenThumbSkill> GREEN_THUMB = register("green_thumb", GreenThumbSkill::new);
   public static final RegistrySupplier<WitchOfEnvySatellaSkill> WITCH_OF_ENVY_SATELLA = register("witch_of_envy_satella", WitchOfEnvySatellaSkill::new);
   public static final RegistrySupplier<GodlyScientistSkill> GODLY_SCIENTIST = register("godly_scientist", GodlyScientistSkill::new);
   public static final RegistrySupplier<GodlySmitherySkill> GODLY_SMITHERY = register("godly_smithery", GodlySmitherySkill::new);
   public static final RegistrySupplier<SatellaSkill> SATELLA = register("satella", SatellaSkill::new);
   public static final RegistrySupplier<ArthurOnceAndFutureKingSkill> ARTHUR_ONCE_AND_FUTURE_KING = register(
      "arthur_once_and_future_king", ArthurOnceAndFutureKingSkill::new
   );
   public static final RegistrySupplier<AvalonGodOfEternalKingdomSkill> AVALON_GOD_OF_ETERNAL_KINGDOM = register(
      "avalon_god_of_eternal_kingdom", AvalonGodOfEternalKingdomSkill::new
   );
   public static final RegistrySupplier<CaedrosGodOfConquestSkill> CAEDROS_GOD_OF_CONQUEST = register("caedros_god_of_conquest", CaedrosGodOfConquestSkill::new);
   public static final RegistrySupplier<TrueAuthorityOfGreedSkill> TRUE_AUTHORITY_OF_GREED = register("true_authority_of_greed", TrueAuthorityOfGreedSkill::new);
   public static final RegistrySupplier<IWantToBeJustLikeYouSkill> I_WANT_TO_BE_JUST_LIKE_YOU = register(
      "i_want_to_be_just_like_you", IWantToBeJustLikeYouSkill::new
   );
   public static final RegistrySupplier<PurifierSkill> PURIFIER = register("purifier", PurifierSkill::new);
   public static final RegistrySupplier<NirvanaSkill> NIRVANA = register("nirvana", NirvanaSkill::new);
   public static final RegistrySupplier<AbhothLordOfPestilenceSkill> ABHOTH_LORD_OF_PESTILENCE = register(
      "abhoth_lord_of_pestilence", AbhothLordOfPestilenceSkill::new
   );
   public static final RegistrySupplier<GeneEditorSkill> GENE_EDITOR = register("gene_editor", GeneEditorSkill::new);
   public static final RegistrySupplier<ExploiterSkill> EXPLOITER = register("exploiter", ExploiterSkill::new);
   public static final RegistrySupplier<ParasiteSkill> PARASITE = register("parasite", ParasiteSkill::new);
   public static final RegistrySupplier<SymbioteSkill> SYMBIOTE = register("symbiote", SymbioteSkill::new);
   public static final RegistrySupplier<AntiSymbioteSkill> ANTI_SYMBIOTE = register("anti_symbiote", AntiSymbioteSkill::new);
   public static final RegistrySupplier<PainLordOfSixPathsSkill> PAIN_LORD_OF_SIX_PATHS = register("pain_lord_of_six_paths", PainLordOfSixPathsSkill::new);
   public static final RegistrySupplier<WitchOfVainglorySkill> WITCH_OF_VAINGLORY = register("witch_of_vainglory", WitchOfVainglorySkill::new);
   public static final RegistrySupplier<MitamaFaithSkill> MITAMA_FAITH = register("mitama_faith", MitamaFaithSkill::new);
   public static final RegistrySupplier<ElfariaTheHallowedIcemaidenSkill> ELFARIA_THE_HALLOWED_ICEMAIDEN = register(
      "elfaria_the_hallowed_icemaiden", ElfariaTheHallowedIcemaidenSkill::new
   );
   public static final RegistrySupplier<SchwiExMachinaSkill> SCHWI_EX_MACHINA = register("schwi_ex_machina", SchwiExMachinaSkill::new);
   public static final RegistrySupplier<KronairosLordOfTheEndlessSkill> KRONAIROS_LORD_OF_THE_ENDLESS = register(
      "kronairos_lord_of_the_endless", KronairosLordOfTheEndlessSkill::new
   );
   public static final RegistrySupplier<ManasSkill> VOID_ARCHIVE = register("void_archive", VoidArchiveSkill::new);
   public static final RegistrySupplier<ManasSkill> DIVINE_GENERAL_MAHORAGA = register("divine_general_mahoraga", DivineGeneralMahoragaSkill::new);
   public static final RegistrySupplier<AxiomLordOfReflectedJudgment> AXIOM_LORD_OF_REFLECTED_JUDGMENT = register(
      "axiom_lord_of_reflected_judgment", AxiomLordOfReflectedJudgment::new
   );
   public static final RegistrySupplier<AuthorityOfGreedSkill> AUTHORITY_OF_GREED = register("authority_of_greed", AuthorityOfGreedSkill::new);
   public static final RegistrySupplier<AlbisVinaSkill> ALBIS_VINA = register("albis_vina", AlbisVinaSkill::new);
   public static final RegistrySupplier<MammonCasinoUltimateSkill> MAMMON_CASINO = register("mammon_casino", MammonCasinoUltimateSkill::new);
   public static final RegistrySupplier<BloodArtsSkill> BLOOD_ARTS = register("blood_arts", BloodArtsSkill::new);
   public static final RegistrySupplier<MorphSkill> MORPH = register("morph", MorphSkill::new);
   public static final RegistrySupplier<BlackCommunionSkill> BLACK_COMMUNION = register("black_communion", BlackCommunionSkill::new);

   private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", name), supplier);
   }

   public static void init() {
   }
}
