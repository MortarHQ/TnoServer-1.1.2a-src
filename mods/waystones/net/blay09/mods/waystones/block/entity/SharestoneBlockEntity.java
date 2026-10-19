package net.blay09.mods.waystones.block.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import net.blay09.mods.balm.api.menu.BalmMenuProvider;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneOrigin;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.block.SharestoneBlock;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.blay09.mods.waystones.menu.ModMenus;
import net.blay09.mods.waystones.menu.WaystoneSelectionMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SharestoneBlockEntity extends WaystoneBlockEntityBase {
   public SharestoneBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)ModBlockEntities.sharestone.get(), pos, state);
   }

   @Override
   protected ResourceLocation getWaystoneType() {
      return WaystoneTypes.getSharestone(((SharestoneBlock)this.getBlockState().getBlock()).getColor()).orElse(WaystoneTypes.WAYSTONE);
   }

   @Override
   public void initializeWaystone(ServerLevelAccessor world, @Nullable LivingEntity player, WaystoneOrigin origin) {
      super.initializeWaystone(world, player, origin);
      WaystoneSyncManager.sendWaystoneUpdateToAll(world.getServer(), this.getWaystone());
   }

   @Override
   public Component getName() {
      return Component.translatable("container.waystones.sharestone");
   }

   @Override
   public Optional<MenuProvider> getSelectionMenuProvider() {
      return Optional.of(
         new BalmMenuProvider<WaystoneSelectionMenu.Data>() {
            public Component getDisplayName() {
               return Component.translatable("container.waystones.waystone_selection");
            }

            public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
               Waystone fromWaystone = SharestoneBlockEntity.this.getWaystone();
               Collection<Waystone> waystones = PlayerWaystoneManager.getTargetsForWaystone(player, fromWaystone);
               PlayerWaystoneManager.ensureSortingIndex(player, waystones);
               return new WaystoneSelectionMenu(
                  (MenuType<WaystoneSelectionMenu>)ModMenus.sharestoneSelection.get(), fromWaystone, windowId, waystones, Collections.emptySet()
               );
            }

            public WaystoneSelectionMenu.Data getScreenOpeningData(ServerPlayer serverPlayer) {
               return new WaystoneSelectionMenu.Data(
                  SharestoneBlockEntity.this.getWaystone(), PlayerWaystoneManager.getTargetsForWaystone(serverPlayer, SharestoneBlockEntity.this.getWaystone())
               );
            }

            public StreamCodec<RegistryFriendlyByteBuf, WaystoneSelectionMenu.Data> getScreenStreamCodec() {
               return WaystoneSelectionMenu.STREAM_CODEC;
            }
         }
      );
   }
}
