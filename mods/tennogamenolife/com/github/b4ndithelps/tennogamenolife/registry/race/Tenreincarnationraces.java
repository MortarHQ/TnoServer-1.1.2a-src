package com.github.b4ndithelps.tennogamenolife.registry.race;

import com.github.b4ndithelps.tennogamenolife.race.Doragonia.AranreivuRace;
import com.github.b4ndithelps.tennogamenolife.race.Doragonia.DragoniaRace;
import com.github.b4ndithelps.tennogamenolife.race.Doragonia.HartyleifRace;
import com.github.b4ndithelps.tennogamenolife.race.Doragonia.HigherDragoniaRace;
import com.github.b4ndithelps.tennogamenolife.race.Doragonia.LesserDragoniaRace;
import com.github.b4ndithelps.tennogamenolife.race.Doragonia.MiddleDragoniaRace;
import com.github.b4ndithelps.tennogamenolife.race.Doragonia.ReginreivuRace;
import com.github.b4ndithelps.tennogamenolife.race.Dwarf.DwarfRace;
import com.github.b4ndithelps.tennogamenolife.race.Dwarf.NiiTiruviruguRace;
import com.github.b4ndithelps.tennogamenolife.race.Dwarf.OcainRace;
import com.github.b4ndithelps.tennogamenolife.race.Dwarf.RoniDorauvuniruRace;
import com.github.b4ndithelps.tennogamenolife.race.Dwarf.VeiguDorauvuniruRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.BefehlerRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.EinzeigRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.EmirEinsRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.HorouRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.HubieDoraRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.KampfRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.MachineRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.PrueferRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.PuraiyaRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.SeherRace;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.ZeichenRace;
import com.github.b4ndithelps.tennogamenolife.race.Fantasuma.FantasumaRace;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.ArutoshuRace;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.AzrilRace;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.FlugelRace;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.HigherFlugelRace;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.JibrilRace;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.RaphaelRace;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.impl.RaceRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;

public class Tenreincarnationraces {
   public static final RegistrySupplier<ManasRace> FLUGEL = register("flugel", FlugelRace::new);
   public static final RegistrySupplier<ManasRace> JIBRIL = register("jibril", JibrilRace::new);
   public static final RegistrySupplier<ManasRace> MACHINE = register("machine", MachineRace::new);
   public static final RegistrySupplier<ManasRace> BEFEHLER = register("befehler", BefehlerRace::new);
   public static final RegistrySupplier<ManasRace> KAMPF = register("kampf", KampfRace::new);
   public static final RegistrySupplier<ManasRace> ZEICHEN = register("zeichen", ZeichenRace::new);
   public static final RegistrySupplier<ManasRace> PRUEFER = register("pruefer", PrueferRace::new);
   public static final RegistrySupplier<ManasRace> SEHER = register("seher", SeherRace::new);
   public static final RegistrySupplier<ManasRace> HUBIE_DORA = register("hubie_dora", HubieDoraRace::new);
   public static final RegistrySupplier<ManasRace> EINZEIG = register("einzeig", EinzeigRace::new);
   public static final RegistrySupplier<ManasRace> EMIR_EINS = register("emir_eins", EmirEinsRace::new);
   public static final RegistrySupplier<ManasRace> HOROU = register("horou", HorouRace::new);
   public static final RegistrySupplier<ManasRace> RAPHAEL = register("raphael", RaphaelRace::new);
   public static final RegistrySupplier<ManasRace> AZRIL = register("azril", AzrilRace::new);
   public static final RegistrySupplier<ManasRace> ARUTOSHU = register("arutoshu", ArutoshuRace::new);
   public static final RegistrySupplier<ManasRace> PURAIYA = register("puraiya", PuraiyaRace::new);
   public static final RegistrySupplier<ManasRace> FANTASUMA = register("fantasuma", FantasumaRace::new);
   public static final RegistrySupplier<ManasRace> DWARF = register("dwarf", DwarfRace::new);
   public static final RegistrySupplier<ManasRace> NII_TIRUVIRUGU = register("nii_tiruvirugu", NiiTiruviruguRace::new);
   public static final RegistrySupplier<ManasRace> VEIGU_DORAUVUNIRU = register("veigu_dorauvuniru", VeiguDorauvuniruRace::new);
   public static final RegistrySupplier<ManasRace> RONI_DORAUVUNIRU = register("roni_dorauvuniru", RoniDorauvuniruRace::new);
   public static final RegistrySupplier<ManasRace> OCAIN = register("ocain", OcainRace::new);
   public static final RegistrySupplier<ManasRace> DRAGONIA = register("dragonia", DragoniaRace::new);
   public static final RegistrySupplier<ManasRace> LESSER_DRAGONIA = register("lesser_dragonia", LesserDragoniaRace::new);
   public static final RegistrySupplier<ManasRace> MIDDLE_DRAGONIA = register("middle_dragonia", MiddleDragoniaRace::new);
   public static final RegistrySupplier<ManasRace> HIGHER_DRAGONIA = register("higher_dragonia", HigherDragoniaRace::new);
   public static final RegistrySupplier<ManasRace> ARANREIVU = register("aranreivu", AranreivuRace::new);
   public static final RegistrySupplier<ManasRace> HARTYLEIF = register("hartyleif", HartyleifRace::new);
   public static final RegistrySupplier<ManasRace> REGINREIVU = register("reginreivu", ReginreivuRace::new);
   public static final RegistrySupplier<ManasRace> HIGHER_FLUGEL = register("higher_flugel", HigherFlugelRace::new);

   public static void register(IEventBus modEventBus) {
   }

   private static <E extends ManasRace> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
      return RaceRegistry.RACES.register(ResourceLocation.fromNamespaceAndPath("tennogamenolife", name), supplier);
   }

   public static void init() {
   }
}
