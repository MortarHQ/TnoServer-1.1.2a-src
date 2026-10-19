package earth.terrarium.handcrafted.neoforge;

import earth.terrarium.handcrafted.Handcrafted;
import earth.terrarium.handcrafted.client.neoforge.HandcraftedClientNeoForge;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod("handcrafted")
public class HandcraftedNeoForge {
   public HandcraftedNeoForge() {
      Handcrafted.init();
      if (FMLEnvironment.dist.isClient()) {
         HandcraftedClientNeoForge.init();
      }
   }
}
