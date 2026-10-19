package net.blay09.mods.waystones.item;

import java.util.Collection;
import java.util.Collections;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.menu.BalmMenuProvider;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.trait.IResetUseOnDamage;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.menu.ModMenus;
import net.blay09.mods.waystones.menu.WaystoneSelectionMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class WarpScrollItem extends ScrollItemBase implements IResetUseOnDamage {
   public WarpScrollItem(Properties properties) {
      super(properties);
   }

   public int getUseDuration(ItemStack itemStack, LivingEntity entity) {
      return WaystonesConfig.getActive().general.scrollUseTime;
   }

   public ItemStack finishUsingItem(final ItemStack itemStack, Level world, LivingEntity entity) {
      if (!world.isClientSide && entity instanceof ServerPlayer player) {
         final Collection<Waystone> waystones = PlayerWaystoneManager.getTargetsForItem(player, itemStack);
         PlayerWaystoneManager.ensureSortingIndex(player, waystones);
         Balm.getNetworking()
            .openGui(
               (ServerPlayer)entity,
               new BalmMenuProvider<ModMenus.ItemInitiatedWaystoneMenuData>() {
                  public Component getDisplayName() {
                     return Component.translatable("container.waystones.waystone_selection");
                  }

                  public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player playerx) {
                     return new WaystoneSelectionMenu(
                           (MenuType<WaystoneSelectionMenu>)ModMenus.warpScrollSelection.get(), null, windowId, waystones, Collections.emptySet()
                        )
                        .withWarpItem(itemStack)
                        .setPostTeleportHandler(context -> itemStack.consume(1, inventory.player));
                  }

                  public ModMenus.ItemInitiatedWaystoneMenuData getScreenOpeningData(ServerPlayer serverPlayer) {
                     return new ModMenus.ItemInitiatedWaystoneMenuData(waystones, itemStack);
                  }

                  public StreamCodec<RegistryFriendlyByteBuf, ModMenus.ItemInitiatedWaystoneMenuData> getScreenStreamCodec() {
                     return ModMenus.ItemInitiatedWaystoneMenuData.STREAM_CODEC;
                  }
               }
            );
      }

      return itemStack;
   }
}
