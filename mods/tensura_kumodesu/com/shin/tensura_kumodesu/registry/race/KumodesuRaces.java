package com.shin.tensura_kumodesu.registry.race;

import com.shin.tensura_kumodesu.races.monster.ArchTaratectRace;
import com.shin.tensura_kumodesu.races.monster.GreatTaratectRace;
import com.shin.tensura_kumodesu.races.monster.GreaterTaratectRace;
import com.shin.tensura_kumodesu.races.monster.QueenTaratectRace;
import com.shin.tensura_kumodesu.races.monster.SmallTaratectRace;
import com.shin.tensura_kumodesu.races.monster.TaratectRace;
import com.shin.tensura_kumodesu.races.ogre.HighOgreRace;
import com.shin.tensura_kumodesu.races.ogre.OgreGeneralRace;
import com.shin.tensura_kumodesu.races.ogre.OgreKingRace;
import com.shin.tensura_kumodesu.races.ogre.OgreShamanRace;
import com.shin.tensura_kumodesu.races.ogre.WrathOniRace;
import com.shin.tensura_kumodesu.races.other.OrthocadinahtRace;
import com.shin.tensura_kumodesu.races.other.SmallLesserTaratectRace;
import com.shin.tensura_kumodesu.races.poison.GreatPoisonTaratectRace;
import com.shin.tensura_kumodesu.races.poison.LesserPoisonTaratectRace;
import com.shin.tensura_kumodesu.races.poison.LesserTaratectRace;
import com.shin.tensura_kumodesu.races.poison.PoisonTaratectRace;
import com.shin.tensura_kumodesu.races.spec.ArachneRace;
import com.shin.tensura_kumodesu.races.spec.EdeSaineRace;
import com.shin.tensura_kumodesu.races.spec.SmallPoisonTaratectRace;
import com.shin.tensura_kumodesu.races.spec.ZanaHorowaRace;
import com.shin.tensura_kumodesu.races.spec.ZoaEleRace;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.impl.RaceRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class KumodesuRaces {
   public static final RegistrySupplier<ManasRace> SMALL_LESSER_TARATECT = register("small_lesser_taratect", SmallLesserTaratectRace::new);
   public static final RegistrySupplier<ManasRace> ORTHOCADINAHT = register("orthocadinaht", OrthocadinahtRace::new);
   public static final RegistrySupplier<ManasRace> SMALL_TARATECT = register("small_taratect", SmallTaratectRace::new);
   public static final RegistrySupplier<ManasRace> TARATECT = register("taratect", TaratectRace::new);
   public static final RegistrySupplier<ManasRace> GREAT_TARATECT = register("great_taratect", GreatTaratectRace::new);
   public static final RegistrySupplier<ManasRace> GREATER_TARATECT = register("greater_taratect", GreaterTaratectRace::new);
   public static final RegistrySupplier<ManasRace> ARCH_TARATECT = register("arch_taratect", ArchTaratectRace::new);
   public static final RegistrySupplier<ManasRace> QUEEN_TARATECT = register("queen_taratect", QueenTaratectRace::new);
   public static final RegistrySupplier<ManasRace> SMALL_POISON_TARATECT = register("small_poison_taratect", SmallPoisonTaratectRace::new);
   public static final RegistrySupplier<ManasRace> ZOA_ELE = register("zoa_ele", ZoaEleRace::new);
   public static final RegistrySupplier<ManasRace> EDE_SAINE = register("ede_saine", EdeSaineRace::new);
   public static final RegistrySupplier<ManasRace> ZANA_HOROWA = register("zana_horowa", ZanaHorowaRace::new);
   public static final RegistrySupplier<ManasRace> ARACHNE = register("arachne", ArachneRace::new);
   public static final RegistrySupplier<ManasRace> LESSER_TARATECT = register("lesser_taratect", LesserTaratectRace::new);
   public static final RegistrySupplier<ManasRace> LESSER_POISON_TARATECT = register("lesser_poison_taratect", LesserPoisonTaratectRace::new);
   public static final RegistrySupplier<ManasRace> POISON_TARATECT = register("poison_taratect", PoisonTaratectRace::new);
   public static final RegistrySupplier<ManasRace> GREAT_POISON_TARATECT = register("great_poison_taratect", GreatPoisonTaratectRace::new);
   public static final RegistrySupplier<ManasRace> OGRE_SHAMAN = register("ogre_shaman", OgreShamanRace::new);
   public static final RegistrySupplier<ManasRace> HIGH_OGRE = register("high_ogre", HighOgreRace::new);
   public static final RegistrySupplier<ManasRace> OGRE_GENERAL = register("ogre_general", OgreGeneralRace::new);
   public static final RegistrySupplier<ManasRace> OGRE_KING = register("ogre_king", OgreKingRace::new);
   public static final RegistrySupplier<ManasRace> WRATH_ONI = register("wrath_oni", WrathOniRace::new);

   private static <E extends ManasRace> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return RaceRegistry.RACES.register(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", name), supplier);
   }

   public static void init() {
   }
}
