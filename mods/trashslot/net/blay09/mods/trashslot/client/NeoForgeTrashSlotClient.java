package net.blay09.mods.trashslot.client;

import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = "trashslot", dist = Dist.CLIENT)
public class NeoForgeTrashSlotClient {
   public NeoForgeTrashSlotClient(IEventBus eventBus) {
      NeoForgeLoadContext loadContext = new NeoForgeLoadContext(eventBus);
      BalmClient.initialize("trashslot", loadContext, TrashSlotClient::initialize);
   }
}
