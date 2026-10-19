package nadiendev.constructionwand.containers.handlers;

import com.refinedmods.refinedstorage.api.core.Action;
import com.refinedmods.refinedstorage.api.network.Network;
import com.refinedmods.refinedstorage.api.network.node.GraphNetworkComponent;
import com.refinedmods.refinedstorage.api.network.storage.StorageNetworkComponent;
import com.refinedmods.refinedstorage.api.storage.Actor;
import com.refinedmods.refinedstorage.common.Platform;
import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.support.network.item.NetworkItemPlayerValidator;
import com.refinedmods.refinedstorage.common.api.support.network.item.NetworkItemTargetBlockEntity;
import com.refinedmods.refinedstorage.common.api.support.network.item.NetworkItemPlayerValidator.PlayerCoordinates;
import com.refinedmods.refinedstorage.common.content.DataComponents;
import com.refinedmods.refinedstorage.common.grid.WirelessGridItem;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import nadiendev.constructionwand.api.IContainerHandler;
import nadiendev.constructionwand.containers.ContainerTrace;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HandlerWirelessGrid implements IContainerHandler {
   @Override
   public boolean matches(Player player, ItemStack itemStack, ItemStack inventoryStack) {
      return inventoryStack.getItem() instanceof WirelessGridItem;
   }

   @Override
   public int getSignature(Player player, ItemStack inventoryStack) {
      GlobalPos pos = (GlobalPos)inventoryStack.get(DataComponents.INSTANCE.getNetworkLocation());
      if (pos == null) {
         return -1;
      }

      int x = pos.pos().getX();
      int y = pos.pos().getY();
      int z = pos.pos().getZ();
      String dim = pos.dimension().location().toString();
      return x * 31 + y * 17 + z ^ dim.hashCode();
   }

   @Override
   public int countItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack) {
      if (!(player instanceof ServerPlayer)) {
         return 0;
      }

      Network network = this.resolveNetworkForItem(player, inventoryStack);
      if (network == null) {
         return 0;
      }

      if (this.isCreativeWirelessGrid(inventoryStack)) {
         long canExtract = this.extractItemAmount(network, itemStack, 2147483647L, Action.SIMULATE);
         if (canExtract <= 0L) {
            return 0;
         } else {
            return canExtract > 2147483647L ? Integer.MAX_VALUE : (int)canExtract;
         }
      } else {
         long extractCost = this.getWirelessGridExtractEnergyUsage();
         long energyStored = this.getItemEnergyStored(inventoryStack);
         if (extractCost > 0L && energyStored <= 0L) {
            return 0;
         } else {
            long maxByEnergy = extractCost <= 0L ? 2147483647L : energyStored / extractCost;
            if (maxByEnergy <= 0L) {
               return 0;
            } else {
               long canExtract = this.extractItemAmount(network, itemStack, maxByEnergy, Action.SIMULATE);
               if (canExtract <= 0L) {
                  return 0;
               } else {
                  return canExtract > 2147483647L ? Integer.MAX_VALUE : (int)canExtract;
               }
            }
         }
      }
   }

   @Override
   public int useItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack, int count) {
      if (player instanceof ServerPlayer && count > 0) {
         Network network = this.resolveNetworkForItem(player, inventoryStack);
         if (network == null) {
            return count;
         }

         if (this.isCreativeWirelessGrid(inventoryStack)) {
            long canExtract = this.extractItemAmount(network, itemStack, count, Action.SIMULATE);
            if (canExtract <= 0L) {
               return count;
            }

            long extracted = this.extractItemAmount(network, itemStack, canExtract, Action.EXECUTE);
            if (extracted <= 0L) {
               return count;
            }

            long remaining = count - extracted;
            return remaining <= 0L ? 0 : (remaining > 2147483647L ? Integer.MAX_VALUE : (int)remaining);
         } else {
            long extractCost = this.getWirelessGridExtractEnergyUsage();
            long energyStored = this.getItemEnergyStored(inventoryStack);
            long maxByEnergy = extractCost <= 0L ? count : energyStored / extractCost;
            if (maxByEnergy <= 0L) {
               return count;
            }

            long request = Math.min(count, maxByEnergy);
            long canExtract = this.extractItemAmount(network, itemStack, request, Action.SIMULATE);
            if (canExtract <= 0L) {
               return count;
            }

            long extracted = this.extractItemAmount(network, itemStack, canExtract, Action.EXECUTE);
            if (extracted <= 0L) {
               return count;
            }

            if (extractCost > 0L) {
               this.drainItemEnergy(inventoryStack, extracted * extractCost);
            }

            long remaining = count - extracted;
            return remaining <= 0L ? 0 : (remaining > 2147483647L ? Integer.MAX_VALUE : (int)remaining);
         }
      } else {
         return count;
      }
   }

   private Network resolveNetworkForItem(Player player, ItemStack stack) {
      GlobalPos pos = (GlobalPos)stack.get(DataComponents.INSTANCE.getNetworkLocation());
      if (pos == null) {
         return null;
      }

      if (player.getServer() == null) {
         return null;
      }

      Level level = player.getServer().getLevel(pos.dimension());
      if (level != null && level.isLoaded(pos.pos())) {
         if (level.getBlockEntity(pos.pos()) instanceof NetworkItemTargetBlockEntity targetBlockEntity) {
            Network network = targetBlockEntity.getNetworkForItem();
            if (network == null) {
               return null;
            } else {
               return this.isNetworkValidForPlayer(network, player) ? network : null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private boolean isNetworkValidForPlayer(Network network, Player player) {
      GraphNetworkComponent graph = (GraphNetworkComponent)network.getComponent(GraphNetworkComponent.class);
      PlayerCoordinates coordinates = new PlayerCoordinates(player.level().dimension(), player.position());

      for (NetworkItemPlayerValidator validator : graph.getContainers(NetworkItemPlayerValidator.class)) {
         if (validator.isValid(coordinates)) {
            return true;
         }
      }

      return false;
   }

   private long extractItemAmount(Network network, ItemStack stack, long amount, Action action) {
      if (amount <= 0L) {
         return 0L;
      }

      ItemResource resource = ItemResource.ofItemStack(stack);
      StorageNetworkComponent storage = (StorageNetworkComponent)network.getComponent(StorageNetworkComponent.class);
      return storage.extract(resource, amount, action, Actor.EMPTY);
   }

   private long getWirelessGridExtractEnergyUsage() {
      return Platform.INSTANCE.getConfig().getWirelessGrid().getExtractEnergyUsage();
   }

   private long getItemEnergyStored(ItemStack stack) {
      return RefinedStorageApi.INSTANCE.getEnergyStorage(stack).map(energyStorage -> Math.max(0L, energyStorage.getStored())).orElse(0L);
   }

   private void drainItemEnergy(ItemStack stack, long amount) {
      if (amount > 0L) {
         RefinedStorageApi.INSTANCE.getEnergyStorage(stack).ifPresent(energyStorage -> energyStorage.extract(amount, Action.EXECUTE));
      }
   }

   private boolean isCreativeWirelessGrid(ItemStack stack) {
      Item item = stack.getItem();
      ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
      return location != null && location.toString().equals("refinedstorage:creative_wireless_grid");
   }
}
