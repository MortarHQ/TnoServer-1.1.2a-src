package com.tno_tensura_extras.registry;

import com.tno_tensura_extras.magic.AlignmentFieldMagic;
import com.tno_tensura_extras.magic.DayNightInversionMagic;
import com.tno_tensura_extras.magic.HolyDemonicInversionMagic;
import com.tno_tensura_extras.skill.unique.AllRounderSkill;
import com.tno_tensura_extras.skill.unique.LooterSkill;
import com.tno_tensura_extras.skill.unique.OptimistSkill;
import com.tno_tensura_extras.skill.unique.SeducerSkill;
import com.tno_tensura_extras.skill.unique.TimeTravelerSkill;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public final class ExtrasSkills {
   public static final String ALL_ROUNDER_ID = "tno_tensura_extras:all_rounder";
   public static final String OPTIMIST_ID = "tno_tensura_extras:optimist";
   public static final String TIME_TRAVELER_ID = "tno_tensura_extras:time_traveler";
   public static final String LOOTER_ID = "tno_tensura_extras:looter";
   public static final String SEDUCER_ID = "tno_tensura_extras:seducer";
   public static final String DAY_NIGHT_INVERSION_ID = "tno_tensura_extras:day_night_inversion";
   public static final String HOLY_DEMONIC_INVERSION_ID = "tno_tensura_extras:holy_demonic_inversion";
   public static final String ALIGNMENT_FIELD_ID = "tno_tensura_extras:alignment_field";
   public static final RegistrySupplier<AllRounderSkill> ALL_ROUNDER = register("all_rounder", AllRounderSkill::new);
   public static final RegistrySupplier<OptimistSkill> OPTIMIST = register("optimist", OptimistSkill::new);
   public static final RegistrySupplier<TimeTravelerSkill> TIME_TRAVELER = register("time_traveler", TimeTravelerSkill::new);
   public static final RegistrySupplier<LooterSkill> LOOTER = register("looter", LooterSkill::new);
   public static final RegistrySupplier<SeducerSkill> SEDUCER = register("seducer", SeducerSkill::new);
   public static final RegistrySupplier<DayNightInversionMagic> DAY_NIGHT_INVERSION = register("day_night_inversion", DayNightInversionMagic::new);
   public static final RegistrySupplier<HolyDemonicInversionMagic> HOLY_DEMONIC_INVERSION = register("holy_demonic_inversion", HolyDemonicInversionMagic::new);
   public static final RegistrySupplier<AlignmentFieldMagic> ALIGNMENT_FIELD = register("alignment_field", AlignmentFieldMagic::new);

   private ExtrasSkills() {
   }

   private static <T extends ManasSkill> RegistrySupplier<T> register(String name, Supplier<T> supplier) {
      return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", name), supplier);
   }

   public static void init() {
   }
}
