package com.tom.storagemod.block.entity;

import com.tom.storagemod.Config;
import com.tom.storagemod.Content;
import com.tom.storagemod.block.AbstractStorageTerminalBlock;
import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.NetworkInventory;
import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.inventory.TerminalItemStack;
import com.tom.storagemod.item.WirelessTerminal;
import com.tom.storagemod.menu.StorageTerminalMenu;
import com.tom.storagemod.platform.PlatformBlockEntity;
import com.tom.storagemod.util.BeaconLevelCalc;
import com.tom.storagemod.util.PlayerInvUtil;
import com.tom.storagemod.util.TickerUtil;
import com.tom.storagemod.util.Util;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class StorageTerminalBlockEntity extends PlatformBlockEntity implements MenuProvider, TickerUtil.TickableServer {
   private NetworkInventory itemCache = new NetworkInventory();
   private Map<StoredItemStack, TerminalItemStack> items = new HashMap<>();
   private int sort;
   private int searchType;
   private int modes;
   private String lastSearch = "";
   private boolean updateItems;
   private int changeCount;
   private int beaconLevel;
   private long changeTracker;
   private int slotCount;
   private int freeCount;

   public StorageTerminalBlockEntity(BlockPos pos, BlockState state) {
      super(Content.terminalBE.get(), pos, state);
   }

   public StorageTerminalBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public void onLoad() {
      super.onLoad();
      if (!this.level.isClientSide) {
         BlockState st = this.level.getBlockState(this.worldPosition);
         Direction d = (Direction)st.getValue(AbstractStorageTerminalBlock.FACING);
         AbstractStorageTerminalBlock.TerminalPos p = (AbstractStorageTerminalBlock.TerminalPos)st.getValue(AbstractStorageTerminalBlock.TERMINAL_POS);
         if (p == AbstractStorageTerminalBlock.TerminalPos.UP) {
            d = Direction.UP;
         }

         if (p == AbstractStorageTerminalBlock.TerminalPos.DOWN) {
            d = Direction.DOWN;
         }

         this.itemCache.onLoad(this.level, this.worldPosition.relative(d), d.getOpposite(), this);
      }
   }

   public AbstractContainerMenu createMenu(int id, Inventory plInv, Player arg2) {
      return new StorageTerminalMenu(id, plInv, this);
   }

   public Component getDisplayName() {
      return Component.translatable("menu.toms_storage.storage_terminal");
   }

   public Map<StoredItemStack, TerminalItemStack> getStacks() {
      this.updateItems = true;
      return this.items;
   }

   public StoredItemStack pullStack(StoredItemStack stack, long max) {
      if (stack == null) {
         return null;
      }

      IInventoryAccess ii = this.itemCache.getAccess(this.level, this.worldPosition);
      ItemStack ex = ii.pullMatchingStack(stack.getStack(), max);
      return ex.isEmpty() ? null : new StoredItemStack(ex);
   }

   public StoredItemStack pushStack(StoredItemStack stack) {
      if (stack == null) {
         return null;
      }

      IInventoryAccess ii = this.itemCache.getAccess(this.level, this.worldPosition);
      ItemStack r = ii.pushStack(stack.getActualStack());
      return r.isEmpty() ? null : new StoredItemStack(r);
   }

   public ItemStack pushStack(ItemStack itemstack) {
      StoredItemStack is = this.pushStack(new StoredItemStack(itemstack));
      return is == null ? ItemStack.EMPTY : is.getActualStack();
   }

   public void pushOrDrop(ItemStack st) {
      if (!st.isEmpty()) {
         StoredItemStack st0 = this.pushStack(new StoredItemStack(st));
         if (st0 != null) {
            this.dropItem(st0.getActualStack());
         }
      }
   }

   public void dropItem(ItemStack stack) {
      Containers.dropItemStack(this.level, this.worldPosition.getX() + 0.5F, this.worldPosition.getY() + 0.5F, this.worldPosition.getZ() + 0.5F, stack);
   }

   @Override
   public void updateServer() {
      if (this.updateItems) {
         IInventoryAccess ii = this.itemCache.getAccess(this.level, this.worldPosition);
         IInventoryAccess.IInventoryChangeTracker tr = ii.tracker();
         long ct = tr.getChangeTracker(this.level);
         if (this.changeTracker != ct) {
            this.changeTracker = ct;
            if (Config.get().runMultithreaded) {
               this.items = tr.streamWrappedStacks(true)
                  .map(TerminalItemStack::new)
                  .collect(Collectors.groupingBy(Function.identity(), Util.reducingWithCopy(null, TerminalItemStack::merge, TerminalItemStack::new)));
            } else {
               this.items = new HashMap<>();
               tr.streamWrappedStacks(false).map(TerminalItemStack::new).forEach(s -> this.items.merge(s, s, TerminalItemStack::merge));
               this.items.replaceAll((k, v) -> new TerminalItemStack(v));
            }

            this.slotCount = Mth.clamp(ii.getSlotCount(), 0, 32767);
            this.freeCount = Mth.clamp(ii.getFreeSlotCount(), 0, 32767);
            this.changeCount++;
         }

         this.updateItems = false;
      }

      if (this.level.getGameTime() % 40L == Math.abs(this.worldPosition.hashCode()) % 40) {
         this.beaconLevel = BlockPos.betweenClosedStream(new AABB(this.worldPosition).inflate(8.0)).mapToInt(p -> {
            if (this.level.isLoaded(p)) {
               BlockState st = this.level.getBlockState(p);
               if (st.is(Blocks.BEACON)) {
                  return BeaconLevelCalc.calcBeaconLevel(this.level, p.getX(), p.getY(), p.getZ());
               }
            }

            return -1;
         }).max().orElse(-1);
      }
   }

   public boolean canInteractWith(Player player, boolean menuCheck) {
      if (this.level.getBlockEntity(this.worldPosition) != this) {
         return false;
      }

      int d = 6;
      int termReach = PlayerInvUtil.findItem(player, i -> i.getItem() instanceof WirelessTerminal, 0, i -> ((WirelessTerminal)i.getItem()).getRange(player, i));
      if (Config.get().wirelessTermBeaconLvl == -1 || this.beaconLevel < Config.get().wirelessTermBeaconLvl || termReach <= 0) {
         d = Math.max(d, termReach);
         if (menuCheck) {
            d += d / 4;
         }

         return player.level() == this.level
            && !(player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) > d * d);
      } else {
         return Config.get().wirelessTermBeaconLvlCrossDim != -1 && this.beaconLevel >= Config.get().wirelessTermBeaconLvlCrossDim
            ? true
            : player.level() == this.level;
      }
   }

   public int getSorting() {
      return this.sort;
   }

   public int getModes() {
      return this.modes;
   }

   public int getSearchType() {
      return this.searchType;
   }

   public void setSorting(int newC) {
      this.sort = newC;
      this.setChanged();
   }

   public void setModes(int modes) {
      this.modes = modes;
      this.setChanged();
   }

   public void setSearchType(int searchType) {
      this.searchType = searchType;
      this.setChanged();
   }

   public void saveAdditional(CompoundTag compound, Provider provider) {
      super.saveAdditional(compound, provider);
      compound.putInt("sorting", this.sort);
      compound.putInt("modes", this.modes);
      compound.putInt("searchType", this.searchType);
   }

   public void loadAdditional(CompoundTag compound, Provider provider) {
      if (compound.contains("sort")) {
         int s = compound.getInt("sort");
         int controllMode = s & 3;
         boolean rev = (s & 4) > 0;
         int type = (s & 24) >> 3;
         this.searchType = (s & 224) >> 5;
         boolean ghostItems = (s & 512) != 0;
         boolean tallMode = (s & 1024) != 0;
         boolean pullFromInv = (s & 256) != 0;
         this.modes = controllMode | (tallMode ? 16 : 0) | (pullFromInv ? 32 : 0);
         this.sort = type | (rev ? 256 : 0) | (ghostItems ? 512 : 0);
      } else {
         this.sort = compound.getInt("sorting");
         this.modes = compound.getInt("modes");
         this.searchType = compound.getInt("searchType");
      }

      super.loadAdditional(compound, provider);
   }

   public String getLastSearch() {
      return this.lastSearch;
   }

   public void setLastSearch(String string) {
      this.lastSearch = string;
   }

   public int getBeaconLevel() {
      return this.beaconLevel;
   }

   public int getChangeCount() {
      return this.changeCount;
   }

   public int getFreeCount() {
      return this.freeCount;
   }

   public int getSlotCount() {
      return this.slotCount;
   }
}
