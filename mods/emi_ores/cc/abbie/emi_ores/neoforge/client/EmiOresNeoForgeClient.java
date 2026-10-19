package cc.abbie.emi_ores.neoforge.client;

import cc.abbie.emi_ores.client.EmiOresClient;
import cc.abbie.emi_ores.client.FeaturesReciever;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = "emi_ores", dist = Dist.CLIENT)
public class EmiOresNeoForgeClient {
   public EmiOresNeoForgeClient(ModContainer mod, IEventBus modBus) {
      EmiOresClient.init();
      NeoForge.EVENT_BUS.addListener(e -> FeaturesReciever.clearFeatures());
   }
}
