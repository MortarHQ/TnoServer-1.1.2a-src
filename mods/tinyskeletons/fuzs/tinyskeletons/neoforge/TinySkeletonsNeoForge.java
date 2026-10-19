package fuzs.tinyskeletons.neoforge;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext.Factory;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.data.ModEntityTypeLootProvider;
import fuzs.tinyskeletons.data.ModEntityTypeTagProvider;
import net.neoforged.fml.common.Mod;

@Mod("tinyskeletons")
public class TinySkeletonsNeoForge {
   public TinySkeletonsNeoForge() {
      ModConstructor.construct("tinyskeletons", TinySkeletons::new);
      DataProviderHelper.registerDataProviders("tinyskeletons", new Factory[]{ModEntityTypeLootProvider::new, ModEntityTypeTagProvider::new});
   }
}
