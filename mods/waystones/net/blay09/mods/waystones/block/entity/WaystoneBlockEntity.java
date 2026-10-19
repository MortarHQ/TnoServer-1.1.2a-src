package net.blay09.mods.waystones.block.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import net.blay09.mods.balm.api.menu.BalmMenuProvider;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.menu.ModMenus;
import net.blay09.mods.waystones.menu.WaystoneSelectionMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class WaystoneBlockEntity extends WaystoneBlockEntityBase {
   public WaystoneBlockEntity(BlockPos blockPos, BlockState blockState) {
      super((BlockEntityType<?>)ModBlockEntities.waystone.get(), blockPos, blockState);
   }

   @Override
   protected ResourceLocation getWaystoneType() {
      return WaystoneTypes.WAYSTONE;
   }

   @Override
   public Component getName() {
      return Component.translatable("container.waystones.waystone");
   }

   @Override
   public Optional<MenuProvider> getSelectionMenuProvider() {
      return Optional.of(
         new BalmMenuProvider<WaystoneSelectionMenu.Data>() {
            public Component getDisplayName() {
               return Component.translatable("container.waystones.waystone_selection");
            }

            public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
               Collection<Waystone> waystones = PlayerWaystoneManager.getTargetsForWaystone(player, WaystoneBlockEntity.this.getWaystone());
               PlayerWaystoneManager.ensureSortingIndex(player, waystones);
               return new WaystoneSelectionMenu(
                  (MenuType<WaystoneSelectionMenu>)ModMenus.waystoneSelection.get(),
                  WaystoneBlockEntity.this.getWaystone(),
                  windowId,
                  waystones,
                  Collections.emptySet()
               );
            }

            public WaystoneSelectionMenu.Data getScreenOpeningData(ServerPlayer serverPlayer) {
               return new WaystoneSelectionMenu.Data(
                  WaystoneBlockEntity.this.getWaystone(), PlayerWaystoneManager.getTargetsForWaystone(serverPlayer, WaystoneBlockEntity.this.getWaystone())
               );
            }

            public StreamCodec<RegistryFriendlyByteBuf, WaystoneSelectionMenu.Data> getScreenStreamCodec() {
               return WaystoneSelectionMenu.STREAM_CODEC;
            }
         }
      );
   }
}
