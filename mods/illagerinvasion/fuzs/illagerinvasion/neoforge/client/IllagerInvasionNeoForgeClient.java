package fuzs.illagerinvasion.neoforge.client;

import fuzs.illagerinvasion.client.IllagerInvasionClient;
import fuzs.illagerinvasion.data.client.ModLanguageProvider;
import fuzs.illagerinvasion.data.client.ModModelProvider;
import fuzs.illagerinvasion.neoforge.data.client.ModParticleDescriptionProvider;
import fuzs.illagerinvasion.neoforge.data.client.ModSoundDefinitionProvider;
import fuzs.illagerinvasion.neoforge.data.client.ModSpriteSourceProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext.Factory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = "illagerinvasion", dist = Dist.CLIENT)
public class IllagerInvasionNeoForgeClient {
   public IllagerInvasionNeoForgeClient() {
      ClientModConstructor.construct("illagerinvasion", IllagerInvasionClient::new);
      DataProviderHelper.registerDataProviders(
         "illagerinvasion",
         new Factory[]{
            ModLanguageProvider::new, ModModelProvider::new, ModParticleDescriptionProvider::new, ModSoundDefinitionProvider::new, ModSpriteSourceProvider::new
         }
      );
   }
}
