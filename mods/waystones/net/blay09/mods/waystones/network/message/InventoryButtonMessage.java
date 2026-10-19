package net.blay09.mods.waystones.network.message;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.menu.BalmMenuProvider;
import net.blay09.mods.waystones.api.TeleportFlags;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.blay09.mods.waystones.config.InventoryButtonMode;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneImpl;
import net.blay09.mods.waystones.menu.ModMenus;
import net.blay09.mods.waystones.menu.WaystoneSelectionMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class InventoryButtonMessage implements CustomPacketPayload {
   public static final Type<InventoryButtonMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("waystones", "inventory_button"));

   public static void encode(FriendlyByteBuf buf, InventoryButtonMessage message) {
   }

   public static InventoryButtonMessage decode(FriendlyByteBuf buf) {
      return new InventoryButtonMessage();
   }

   public static void handle(ServerPlayer player, InventoryButtonMessage message) {
      InventoryButtonMode inventoryButtonMode = WaystonesConfig.getActive().getInventoryButtonMode();
      if (inventoryButtonMode.isEnabled()) {
         if (player != null) {
            if (player.getAbilities().instabuild) {
               PlayerWaystoneManager.resetCooldowns(player);
            }

            Optional<Waystone> waystone = PlayerWaystoneManager.getInventoryButtonTarget(player);
            if (waystone.isPresent()) {
               WaystonesAPI.createDefaultTeleportContext(player, waystone.get(), it -> it.addFlag(TeleportFlags.INVENTORY_BUTTON))
                  .mapLeft(WaystonesAPI::tryTeleport);
            } else if (inventoryButtonMode.isReturnToAny()) {
               final Collection<Waystone> waystones = PlayerWaystoneManager.getTargetsForInventoryButton(player);
               PlayerWaystoneManager.ensureSortingIndex(player, waystones);
               var containerProvider = new BalmMenuProvider<Collection<Waystone>>() {
                  public Component getDisplayName() {
                     return Component.translatable("container.waystones.waystone_selection");
                  }

                  public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                     return new WaystoneSelectionMenu(
                        (MenuType<WaystoneSelectionMenu>)ModMenus.inventorySelection.get(), null, windowId, waystones, Set.of(TeleportFlags.INVENTORY_BUTTON)
                     );
                  }

                  public Collection<Waystone> getScreenOpeningData(ServerPlayer serverPlayer) {
                     return waystones;
                  }

                  public StreamCodec<RegistryFriendlyByteBuf, Collection<Waystone>> getScreenStreamCodec() {
                     return WaystoneImpl.LIST_STREAM_CODEC;
                  }
               };
               Balm.getNetworking().openGui(player, containerProvider);
            }
         }
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
