package fuzs.tinyskeletons.neoforge.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext.Factory;
import fuzs.tinyskeletons.client.TinySkeletonsClient;
import fuzs.tinyskeletons.data.client.ModLanguageProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = "tinyskeletons", dist = Dist.CLIENT)
public class TinySkeletonsNeoForgeClient {
   public TinySkeletonsNeoForgeClient() {
      ClientModConstructor.construct("tinyskeletons", TinySkeletonsClient::new);
      DataProviderHelper.registerDataProviders("tinyskeletons", new Factory[]{ModLanguageProvider::new});
   }
}
