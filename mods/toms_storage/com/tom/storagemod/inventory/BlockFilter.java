package com.tom.storagemod.inventory;

import com.tom.storagemod.StorageTags;
import com.tom.storagemod.api.MultiblockInventoryAPI;
import com.tom.storagemod.inventory.filter.IFilter;
import com.tom.storagemod.inventory.filter.ItemFilter;
import com.tom.storagemod.inventory.filter.ItemPredicate;
import com.tom.storagemod.item.IItemFilter;
import com.tom.storagemod.util.BlockFaceReference;
import com.tom.storagemod.util.Priority;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

public class BlockFilter implements IFilter {
   private BlockPos pos;
   private Direction side;
   private Set<BlockPos> connected;
   public SimpleContainer filter = new SimpleContainer(1);
   private boolean skip;
   private boolean keepLast;
   private Priority priority;
   private ItemFilter itemFilter = ItemFilter.TRUE;
   private boolean filterNeedsUpdate = true;
   private boolean multiblockFilled;

   public BlockFilter(BlockPos pos) {
      this.pos = pos;
      this.side = Direction.DOWN;
      this.priority = Priority.NORMAL;
      this.connected = new HashSet<>();
      this.connected.add(pos);
      this.filter.addListener(__ -> this.markFilterDirty());
   }

   public Set<BlockPos> getConnectedBlocks() {
      return this.connected;
   }

   public BlockPos getMainPos() {
      return this.pos;
   }

   public Direction getSide() {
      return this.side;
   }

   public CompoundTag serializeNBT(Provider provider) {
      CompoundTag tag = new CompoundTag();
      ListTag conn = new ListTag();
      this.connected.forEach(e -> {
         CompoundTag t = new CompoundTag();
         t.putInt("x", e.getX() - this.pos.getX());
         t.putInt("y", e.getY() - this.pos.getY());
         t.putInt("z", e.getZ() - this.pos.getZ());
         conn.add(t);
      });
      tag.put("connected", conn);
      tag.putBoolean("skip", this.skip);
      tag.putString("side", this.side.getSerializedName());
      if (!this.filter.getItem(0).isEmpty()) {
         tag.put("filter", this.filter.getItem(0).save(provider));
      }

      tag.putInt("priority", this.priority.ordinal());
      tag.putBoolean("keepLast", this.keepLast);
      return tag;
   }

   public void deserializeNBT(Provider provider, CompoundTag tag) {
      ListTag conn = tag.getList("connected", 10);

      for (int i = 0; i < conn.size(); i++) {
         CompoundTag t = conn.getCompound(i);
         int x = t.getInt("x") + this.pos.getX();
         int y = t.getInt("y") + this.pos.getY();
         int z = t.getInt("z") + this.pos.getZ();
         this.connected.add(new BlockPos(x, y, z));
      }

      this.skip = tag.getBoolean("skip");
      this.side = Direction.byName(tag.getString("side"));
      this.filter.setItem(0, ItemStack.parseOptional(provider, tag.getCompound("filter")));
      this.priority = Priority.VALUES[Math.abs(tag.getInt("priority")) % Priority.VALUES.length];
      this.keepLast = tag.getBoolean("keepLast");
      this.multiblockFilled = true;
   }

   public void dropContents(LevelAccessor level, BlockPos pos2) {
      if (level instanceof Level lvl) {
         this.filter.removeAllItems().forEach(f -> Block.popResource(lvl, pos2, f));
      }
   }

   public IInventoryAccess wrap(Level level, IInventoryAccess acc) {
      ItemStack filter = this.filter.getItem(0);
      if (this.filterNeedsUpdate || !this.itemFilter.configMatch(filter)) {
         if (filter.getItem() instanceof IItemFilter f) {
            this.itemFilter = f.createFilter(new BlockFaceReference(level, this.pos, this.side), filter);
         } else {
            this.itemFilter = ItemFilter.TRUE;
         }

         this.filterNeedsUpdate = false;
      }

      if (acc instanceof PlatformFilteredInventoryAccess f) {
         IFilter.MultiFilter mf = new IFilter.MultiFilter(f.getFilter(), this);
         return new PlatformFilteredInventoryAccess(f.getActualInventory(), mf);
      } else {
         return new PlatformFilteredInventoryAccess(acc, this);
      }
   }

   public boolean skip() {
      return this.skip;
   }

   public static BlockFilter findBlockFilterAt(Level level, BlockPos pos) {
      return BlockPos.betweenClosedStream(new AABB(pos).inflate(8.0))
         .map(p -> getFilterAt(level, p))
         .filter(p -> p != null && p.getConnectedBlocks().contains(pos))
         .findFirst()
         .orElseGet(() -> level.getBlockState(pos).is(StorageTags.INV_CONFIG_SKIP) ? null : getOrCreateFilterAt(level, pos));
   }

   public static BlockFilter getFilterAt(Level level, BlockPos pos) {
      return PlatformInventoryAccess.getBlockFilterAt(level, pos, false);
   }

   public static BlockFilter getOrCreateFilterAt(Level level, BlockPos pos) {
      BlockFilter f = PlatformInventoryAccess.getBlockFilterAt(level, pos, true);
      if (f != null) {
         f.fillMultiblock(level);
      }

      return f;
   }

   @Override
   public Priority getPriority() {
      return this.priority;
   }

   public void setPriority(Priority priority) {
      this.priority = priority;
   }

   @Override
   public ItemPredicate getItemPred() {
      return this.itemFilter;
   }

   public void setSkip(boolean skip) {
      this.skip = skip;
   }

   public void setSide(Direction side) {
      this.side = side;
   }

   public void addConnected(Level level, BlockPos pos) {
      if (!this.pos.equals(pos)) {
         PlatformInventoryAccess.removeBlockFilterAt(level, pos);
      }

      this.connected.add(pos.immutable());
   }

   @Override
   public boolean isKeepLast() {
      return this.keepLast;
   }

   public void setKeepLast(boolean keepLast) {
      this.keepLast = keepLast;
   }

   public void markFilterDirty() {
      this.filterNeedsUpdate = true;
   }

   private void fillMultiblock(Level level) {
      if (!this.multiblockFilled) {
         MultiblockInventoryAPI.EVENT.invoker().detectMultiblocks(level, this.pos, level.getBlockState(this.pos), p -> this.addConnected(level, p));
         this.multiblockFilled = true;
      }
   }

   @Override
   public String toString() {
      return "BlockFilter@" + this.pos;
   }
}
