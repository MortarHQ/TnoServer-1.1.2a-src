package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Instrument;

public class ModInstrumentTagProvider extends AbstractTagProvider<Instrument> {
   public ModInstrumentTagProvider(DataProviderContext context) {
      super(Registries.INSTRUMENT, context);
   }

   public void addTags(Provider provider) {
      this.add(ModRegistry.HORN_OF_SIGHT_INSTRUMENT_TAG).add((Instrument)ModRegistry.REVEAL_INSTRUMENT.value());
   }
}
