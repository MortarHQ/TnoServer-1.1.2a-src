package com.github.dominickwd04.traddon.registry.race;

import com.github.dominickwd04.traddon.race.lithoid.DiamondSovereignRace;
import com.github.dominickwd04.traddon.race.lithoid.GeodeGolemRace;
import com.github.dominickwd04.traddon.race.lithoid.PrismKnightRace;
import com.github.dominickwd04.traddon.race.lithoid.ShardlingRace;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;

public class TrAddonRaces {
   private static final DeferredRegister<ManasRace> RACES = DeferredRegister.create("traddon", RaceAPI.getRaceRegistryKey());
   public static final RegistrySupplier<ManasRace> SHARDLING = RACES.register("shardling", ShardlingRace::new);
   public static final RegistrySupplier<ManasRace> GEODE_GOLEM = RACES.register("geode_golem", GeodeGolemRace::new);
   public static final RegistrySupplier<ManasRace> PRISM_KNIGHT = RACES.register("prism_knight", PrismKnightRace::new);
   public static final RegistrySupplier<ManasRace> DIAMOND_SOVEREIGN = RACES.register("diamond_sovereign", DiamondSovereignRace::new);

   public static void init() {
      RACES.register();
   }
}
