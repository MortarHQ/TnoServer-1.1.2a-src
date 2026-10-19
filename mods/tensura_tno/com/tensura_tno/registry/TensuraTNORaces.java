package com.tensura_tno.registry;

import com.tensura_tno.race.fox_spirit.BabySpiritFoxRace;
import com.tensura_tno.race.fox_spirit.FoxSpiritEnvoyRace;
import com.tensura_tno.race.fox_spirit.HeavenlyFoxSovereignRace;
import com.tensura_tno.race.fox_spirit.MysticFoxMasterRace;
import com.tensura_tno.race.fox_spirit.SpiritFoxContractMasterRace;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.impl.RaceRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public final class TensuraTNORaces {
   public static final RegistrySupplier<ManasRace> BABY_SPIRIT_FOX = register("baby_spirit_fox", BabySpiritFoxRace::new);
   public static final RegistrySupplier<ManasRace> FOX_SPIRIT_ENVOY = register("fox_spirit_envoy", FoxSpiritEnvoyRace::new);
   public static final RegistrySupplier<ManasRace> SPIRIT_FOX_CONTRACT_MASTER = register("spirit_fox_contract_master", SpiritFoxContractMasterRace::new);
   public static final RegistrySupplier<ManasRace> MYSTIC_FOX_MASTER = register("mystic_fox_master", MysticFoxMasterRace::new);
   public static final RegistrySupplier<ManasRace> HEAVENLY_FOX_SOVEREIGN = register("heavenly_fox_sovereign", HeavenlyFoxSovereignRace::new);

   private TensuraTNORaces() {
   }

   private static <E extends ManasRace> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return RaceRegistry.RACES.register(ResourceLocation.fromNamespaceAndPath("tensura_tno", name), supplier);
   }

   public static void init() {
   }
}
