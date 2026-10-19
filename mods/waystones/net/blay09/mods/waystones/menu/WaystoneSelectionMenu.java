package net.blay09.mods.waystones.menu;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTeleportContext;
import net.blay09.mods.waystones.core.WaystoneImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WaystoneSelectionMenu extends AbstractContainerMenu {
   public static final StreamCodec<RegistryFriendlyByteBuf, WaystoneSelectionMenu.Data> STREAM_CODEC = StreamCodec.composite(
      WaystoneImpl.STREAM_CODEC,
      WaystoneSelectionMenu.Data::fromWaystone,
      WaystoneImpl.LIST_STREAM_CODEC,
      WaystoneSelectionMenu.Data::waystones,
      WaystoneSelectionMenu.Data::new
   );
   private final Waystone fromWaystone;
   private final Collection<Waystone> waystones;
   private final Set<ResourceLocation> flags;
   private Consumer<WaystoneTeleportContext> postTeleportHandler = it -> {};
   private ItemStack warpItem = ItemStack.EMPTY;

   public WaystoneSelectionMenu(
      MenuType<WaystoneSelectionMenu> type, @Nullable Waystone fromWaystone, int windowId, Collection<Waystone> waystones, Set<ResourceLocation> flags
   ) {
      super(type, windowId);
      this.fromWaystone = fromWaystone;
      this.waystones = waystones;
      this.flags = flags;
   }

   public WaystoneSelectionMenu withWarpItem(ItemStack warpItem) {
      this.warpItem = warpItem;
      return this;
   }

   public ItemStack quickMoveStack(Player player, int i) {
      return ItemStack.EMPTY;
   }

   public boolean stillValid(Player player) {
      if (this.fromWaystone != null) {
         BlockPos pos = this.fromWaystone.getPos();
         return player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
      } else {
         return true;
      }
   }

   @Nullable
   public Waystone getWaystoneFrom() {
      return this.fromWaystone;
   }

   public ItemStack getWarpItem() {
      return this.warpItem;
   }

   public Collection<Waystone> getWaystones() {
      return this.waystones;
   }

   public Set<ResourceLocation> getFlags() {
      return this.flags;
   }

   public Consumer<WaystoneTeleportContext> getPostTeleportHandler() {
      return this.postTeleportHandler;
   }

   public WaystoneSelectionMenu setPostTeleportHandler(Consumer<WaystoneTeleportContext> postTeleportHandler) {
      this.postTeleportHandler = postTeleportHandler;
      return this;
   }

   public record Data(Waystone fromWaystone, Collection<Waystone> waystones) {
   }
}
