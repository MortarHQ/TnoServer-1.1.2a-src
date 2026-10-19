package com.github.wal_bos.moreskills.registry.race;

import com.github.wal_bos.moreskills.race.bloodfiend.BloodMonarchRace;
import com.github.wal_bos.moreskills.race.bloodfiend.BloodNobleRace;
import com.github.wal_bos.moreskills.race.bloodfiend.BloodfiendRace;
import com.github.wal_bos.moreskills.race.bloodfiend.CrimsonProgenitorRace;
import com.github.wal_bos.moreskills.race.bloodfiend.ElderBloodfiendRace;
import com.github.wal_bos.moreskills.race.parasite.ParasiteRace;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.impl.RaceRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public final class MoreskillsRaces {
   public static final RegistrySupplier<ManasRace> BLOODFIEND = register("bloodfiend", BloodfiendRace::new);
   public static final RegistrySupplier<ManasRace> ELDER_BLOODFIEND = register("elder_bloodfiend", ElderBloodfiendRace::new);
   public static final RegistrySupplier<ManasRace> BLOOD_NOBLE = register("blood_noble", BloodNobleRace::new);
   public static final RegistrySupplier<ManasRace> BLOOD_MONARCH = register("blood_monarch", BloodMonarchRace::new);
   public static final RegistrySupplier<ManasRace> CRIMSON_PROGENITOR = register("crimson_progenitor", CrimsonProgenitorRace::new);
   public static final RegistrySupplier<ManasRace> PARASITE = register("parasite", ParasiteRace::new);

   private MoreskillsRaces() {
   }

   private static <T extends ManasRace> RegistrySupplier<T> register(String name, Supplier<T> supplier) {
      return RaceRegistry.RACES.register(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", name), supplier);
   }

   public static void init() {
   }
}
