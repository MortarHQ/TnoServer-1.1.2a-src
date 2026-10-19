package net.blay09.mods.waystones.menu;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.menu.BalmMenuFactory;
import net.blay09.mods.balm.api.menu.BalmMenus;
import net.blay09.mods.waystones.api.TeleportFlags;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.core.WaystoneImpl;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModMenus {
   private static final BalmMenus menus = Balm.getMenus();
   public static DeferredObject<MenuType<WaystoneSelectionMenu>> waystoneSelection = menus.registerMenu(
      id("waystone_selection"),
      new BalmMenuFactory<WaystoneSelectionMenu, WaystoneSelectionMenu.Data>() {
         public WaystoneSelectionMenu create(int windowId, Inventory inventory, WaystoneSelectionMenu.Data data) {
            return new WaystoneSelectionMenu(
               (MenuType<WaystoneSelectionMenu>)ModMenus.waystoneSelection.get(), data.fromWaystone(), windowId, data.waystones(), Collections.emptySet()
            );
         }

         public StreamCodec<RegistryFriendlyByteBuf, WaystoneSelectionMenu.Data> getStreamCodec() {
            return WaystoneSelectionMenu.STREAM_CODEC;
         }
      }
   );
   public static DeferredObject<MenuType<WaystoneSelectionMenu>> warpScrollSelection = menus.registerMenu(
      id("warp_scroll_selection"),
      new BalmMenuFactory<WaystoneSelectionMenu, ModMenus.ItemInitiatedWaystoneMenuData>() {
         public WaystoneSelectionMenu create(int windowId, Inventory inventory, ModMenus.ItemInitiatedWaystoneMenuData data) {
            return new WaystoneSelectionMenu(
                  (MenuType<WaystoneSelectionMenu>)ModMenus.warpScrollSelection.get(), null, windowId, data.waystones(), Collections.emptySet()
               )
               .withWarpItem(data.itemStack());
         }

         public StreamCodec<RegistryFriendlyByteBuf, ModMenus.ItemInitiatedWaystoneMenuData> getStreamCodec() {
            return ModMenus.ItemInitiatedWaystoneMenuData.STREAM_CODEC;
         }
      }
   );
   public static DeferredObject<MenuType<WaystoneSelectionMenu>> warpStoneSelection = menus.registerMenu(
      id("warp_stone_selection"),
      new BalmMenuFactory<WaystoneSelectionMenu, ModMenus.ItemInitiatedWaystoneMenuData>() {
         public WaystoneSelectionMenu create(int windowId, Inventory inventory, ModMenus.ItemInitiatedWaystoneMenuData data) {
            return new WaystoneSelectionMenu(
                  (MenuType<WaystoneSelectionMenu>)ModMenus.warpStoneSelection.get(), null, windowId, data.waystones(), Collections.emptySet()
               )
               .withWarpItem(data.itemStack());
         }

         public StreamCodec<RegistryFriendlyByteBuf, ModMenus.ItemInitiatedWaystoneMenuData> getStreamCodec() {
            return ModMenus.ItemInitiatedWaystoneMenuData.STREAM_CODEC;
         }
      }
   );
   public static DeferredObject<MenuType<WaystoneSelectionMenu>> portstoneSelection = menus.registerMenu(
      id("portstone_selection"),
      new BalmMenuFactory<WaystoneSelectionMenu, Collection<Waystone>>() {
         public WaystoneSelectionMenu create(int windowId, Inventory inventory, Collection<Waystone> waystones) {
            return new WaystoneSelectionMenu(
               (MenuType<WaystoneSelectionMenu>)ModMenus.portstoneSelection.get(), null, windowId, waystones, Set.of(TeleportFlags.PORTSTONE)
            );
         }

         public StreamCodec<RegistryFriendlyByteBuf, Collection<Waystone>> getStreamCodec() {
            return WaystoneImpl.LIST_STREAM_CODEC;
         }
      }
   );
   public static DeferredObject<MenuType<WaystoneSelectionMenu>> inventorySelection = menus.registerMenu(
      id("inventory_selection"),
      new BalmMenuFactory<WaystoneSelectionMenu, Collection<Waystone>>() {
         public WaystoneSelectionMenu create(int windowId, Inventory inventory, Collection<Waystone> waystones) {
            return new WaystoneSelectionMenu(
               (MenuType<WaystoneSelectionMenu>)ModMenus.inventorySelection.get(), null, windowId, waystones, Set.of(TeleportFlags.INVENTORY_BUTTON)
            );
         }

         public StreamCodec<RegistryFriendlyByteBuf, Collection<Waystone>> getStreamCodec() {
            return WaystoneImpl.LIST_STREAM_CODEC;
         }
      }
   );
   public static DeferredObject<MenuType<WaystoneSelectionMenu>> adminSelection = menus.registerMenu(
      id("admin_selection"),
      new BalmMenuFactory<WaystoneSelectionMenu, Collection<Waystone>>() {
         public WaystoneSelectionMenu create(int windowId, Inventory inventory, Collection<Waystone> waystones) {
            return new WaystoneSelectionMenu(
               (MenuType<WaystoneSelectionMenu>)ModMenus.adminSelection.get(), null, windowId, waystones, Set.of(TeleportFlags.ADMIN)
            );
         }

         public StreamCodec<RegistryFriendlyByteBuf, Collection<Waystone>> getStreamCodec() {
            return WaystoneImpl.LIST_STREAM_CODEC;
         }
      }
   );
   public static DeferredObject<MenuType<WaystoneSelectionMenu>> sharestoneSelection = menus.registerMenu(
      id("sharestone_selection"),
      new BalmMenuFactory<WaystoneSelectionMenu, WaystoneSelectionMenu.Data>() {
         public WaystoneSelectionMenu create(int windowId, Inventory inventory, WaystoneSelectionMenu.Data data) {
            return new WaystoneSelectionMenu(
               (MenuType<WaystoneSelectionMenu>)ModMenus.sharestoneSelection.get(), data.fromWaystone(), windowId, data.waystones(), Collections.emptySet()
            );
         }

         public StreamCodec<RegistryFriendlyByteBuf, WaystoneSelectionMenu.Data> getStreamCodec() {
            return WaystoneSelectionMenu.STREAM_CODEC;
         }
      }
   );
   public static DeferredObject<MenuType<WaystoneModifierMenu>> waystoneModifiers = menus.registerMenu(
      id("waystone_modifiers"), new BalmMenuFactory<WaystoneModifierMenu, Waystone>() {
         public WaystoneModifierMenu create(int windowId, Inventory inventory, Waystone waystone) {
            return new WaystoneModifierMenu(windowId, inventory, waystone);
         }

         public StreamCodec<RegistryFriendlyByteBuf, Waystone> getStreamCodec() {
            return WaystoneImpl.STREAM_CODEC;
         }
      }
   );
   public static DeferredObject<MenuType<WaystoneEditMenu>> waystoneSettings = menus.registerMenu(
      id("waystone"), new BalmMenuFactory<WaystoneEditMenu, WaystoneEditMenu.Data>() {
         public WaystoneEditMenu create(int windowId, Inventory inventory, WaystoneEditMenu.Data data) {
            return new WaystoneEditMenu(windowId, data.waystone(), data.modifierCount(), data.error().orElse(null), data.visibilityOptions());
         }

         public StreamCodec<RegistryFriendlyByteBuf, WaystoneEditMenu.Data> getStreamCodec() {
            return WaystoneEditMenu.STREAM_CODEC;
         }
      }
   );

   public static void initialize() {
   }

   @NotNull
   private static ResourceLocation id(String name) {
      return ResourceLocation.fromNamespaceAndPath("waystones", name);
   }

   public record ItemInitiatedWaystoneMenuData(Collection<Waystone> waystones, ItemStack itemStack) {
      public static final StreamCodec<RegistryFriendlyByteBuf, ModMenus.ItemInitiatedWaystoneMenuData> STREAM_CODEC = StreamCodec.composite(
         WaystoneImpl.LIST_STREAM_CODEC,
         ModMenus.ItemInitiatedWaystoneMenuData::waystones,
         ItemStack.STREAM_CODEC,
         ModMenus.ItemInitiatedWaystoneMenuData::itemStack,
         ModMenus.ItemInitiatedWaystoneMenuData::new
      );
   }
}
