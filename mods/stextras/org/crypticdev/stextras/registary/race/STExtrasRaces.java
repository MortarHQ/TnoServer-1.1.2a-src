package org.crypticdev.stextras.registary.race;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.impl.RaceRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.race.kobold.DivineKoboldRace;
import org.crypticdev.stextras.race.kobold.KoboldChieftainRace;
import org.crypticdev.stextras.race.kobold.KoboldRace;
import org.crypticdev.stextras.race.kobold.KoboldTraderRace;
import org.crypticdev.stextras.race.kobold.KoboldWarriorRace;
import org.crypticdev.stextras.race.mystic_fox.DivineFoxRace;
import org.crypticdev.stextras.race.mystic_fox.MysticFoxRace;
import org.crypticdev.stextras.race.mystic_fox.NinetailRace;
import org.crypticdev.stextras.race.mystic_fox.SoulBeastRace;
import org.crypticdev.stextras.race.wispfolk.DivineWispRace;
import org.crypticdev.stextras.race.wispfolk.GaleWispRace;
import org.crypticdev.stextras.race.wispfolk.WispfolkRace;
import org.crypticdev.stextras.race.wispfolk.ZephyrWispRace;

public class STExtrasRaces {
   public static final RegistrySupplier<ManasRace> KOBOLD = register("kobold", KoboldRace::new);
   public static final RegistrySupplier<ManasRace> KOBOLD_TRADER = register("kobold_trader", KoboldTraderRace::new);
   public static final RegistrySupplier<ManasRace> KOBOLD_WARRIOR = register("kobold_warrior", KoboldWarriorRace::new);
   public static final RegistrySupplier<ManasRace> KOBOLD_CHIEFTAIN = register("kobold_chieftain", KoboldChieftainRace::new);
   public static final RegistrySupplier<ManasRace> DIVINE_KOBOLD = register("divine_kobold", DivineKoboldRace::new);
   public static final RegistrySupplier<ManasRace> MYSTIC_FOX = register("mystic_fox", MysticFoxRace::new);
   public static final RegistrySupplier<ManasRace> SOUL_BEAST = register("soul_beast", SoulBeastRace::new);
   public static final RegistrySupplier<ManasRace> NINETAIL = register("ninetail", NinetailRace::new);
   public static final RegistrySupplier<ManasRace> DIVINE_FOX = register("divine_fox", DivineFoxRace::new);
   public static final RegistrySupplier<ManasRace> WISPFOLK = register("wispfolk", WispfolkRace::new);
   public static final RegistrySupplier<ManasRace> GALE_WISP = register("gale_wisp", GaleWispRace::new);
   public static final RegistrySupplier<ManasRace> ZEPHYR_WISP = register("zephyr_wisp", ZephyrWispRace::new);
   public static final RegistrySupplier<ManasRace> DIVINE_WISP = register("divine_wisp", DivineWispRace::new);

   private static <E extends ManasRace> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return RaceRegistry.RACES.register(ResourceLocation.fromNamespaceAndPath("stextras", name), supplier);
   }

   public static void init() {
   }
}
