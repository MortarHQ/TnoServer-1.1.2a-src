package net.blay09.mods.waystones.client;

import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = "waystones", dist = Dist.CLIENT)
public class NeoForgeWaystonesClient {
   public NeoForgeWaystonesClient(IEventBus modEventBus) {
      NeoForgeLoadContext context = new NeoForgeLoadContext(modEventBus);
      BalmClient.initialize("waystones", context, WaystonesClient::initialize);
   }
}
