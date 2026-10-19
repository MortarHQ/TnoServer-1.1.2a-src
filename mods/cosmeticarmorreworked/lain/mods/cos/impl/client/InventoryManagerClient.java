package lain.mods.cos.impl.client;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.UUID;
import lain.mods.cos.impl.InventoryManager;
import lain.mods.cos.impl.inventory.ContainerCosArmor;
import lain.mods.cos.impl.inventory.InventoryCosArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.common.NeoForge;

public class InventoryManagerClient extends InventoryManager {
   protected final LoadingCache<UUID, InventoryCosArmor> ClientCache = CacheBuilder.newBuilder().build(new CacheLoader<UUID, InventoryCosArmor>() {
      public InventoryCosArmor load(UUID key) throws Exception {
         return new InventoryCosArmor();
      }
   });

   @Override
   public ContainerCosArmor createContainerClient(int windowId, Inventory invPlayer) {
      Player player = Minecraft.getInstance().player;
      return new ContainerCosArmor(invPlayer, this.getCosArmorInventoryClient(player.getUUID()), player, windowId);
   }

   @Override
   public InventoryCosArmor getCosArmorInventoryClient(UUID uuid) {
      return uuid == null ? Dummy : (InventoryCosArmor)this.ClientCache.getUnchecked(uuid);
   }

   private void handleLoggedOut(LoggingOut event) {
      this.ClientCache.invalidateAll();
   }

   @Override
   public void registerEventsClient() {
      NeoForge.EVENT_BUS.addListener(this::handleLoggedOut);
   }
}
