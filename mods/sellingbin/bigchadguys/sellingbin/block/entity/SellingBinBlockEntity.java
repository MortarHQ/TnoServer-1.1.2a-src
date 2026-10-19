package bigchadguys.sellingbin.block.entity;

import bigchadguys.sellingbin.SellingBinInventory;
import bigchadguys.sellingbin.block.BinMaterial;
import bigchadguys.sellingbin.block.BinMode;
import bigchadguys.sellingbin.block.SellingBinBlock;
import bigchadguys.sellingbin.init.ModBlocks;
import bigchadguys.sellingbin.init.ModWorldData;
import bigchadguys.sellingbin.screen.handler.SellingBinScreenHandler;
import bigchadguys.sellingbin.world.data.SellingBinData;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SellingBinBlockEntity extends BaseBlockEntity implements ExtendedMenuProvider {
   private final SellingBinInventory inventory;
   private long timeLeft;

   public SellingBinBlockEntity(BlockPos pos, BlockState state) {
      this((BlockEntityType<?>)ModBlocks.Entities.SELLING_BIN.get(), pos, state);
   }

   public SellingBinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);

      this.inventory = switch (this.getMode()) {
         case BLOCK_BOUND -> new SellingBinInventory(this, ((BinMaterial)state.getValue(SellingBinBlock.MATERIAL)).getRows() * 9);
         case PLAYER_BOUND -> null;
      };
   }

   public SellingBinInventory getInventory() {
      return this.inventory;
   }

   public long getTimeLeft() {
      return this.timeLeft;
   }

   public void setTimeLeft(long updateTimeleft) {
      this.timeLeft = updateTimeleft;
      this.sendUpdatesToClient();
   }

   public BinMaterial getMaterial() {
      return (BinMaterial)this.getBlockState().getValue(SellingBinBlock.MATERIAL);
   }

   public BinMode getMode() {
      return (BinMode)this.getBlockState().getValue(SellingBinBlock.MODE);
   }

   public static void tick(Level world, BlockPos pos, BlockState state, SellingBinBlockEntity entity) {
      if (world instanceof ServerLevel && entity.getMode() == BinMode.BLOCK_BOUND) {
         if (entity.getTimeLeft() > entity.getMaterial().getUpdateDelayTicks()) {
            entity.setTimeLeft(entity.getMaterial().getUpdateDelayTicks());
         }

         if (entity.getTimeLeft() < 0L) {
            entity.getInventory().executeTrades();
            entity.setTimeLeft(entity.getMaterial().getUpdateDelayTicks());
         }

         entity.setTimeLeft(entity.getTimeLeft() - 1L);
      }
   }

   @Override
   public void writeNbt(CompoundTag nbt, Provider registries, BaseBlockEntity.UpdateType type) {
      if (this.inventory != null && !this.inventory.isEmpty()) {
         this.inventory.writeNbt().ifPresent(tag -> nbt.put("inventory", tag));
      }

      if (this.timeLeft > 0L) {
         nbt.putLong("timeLeft", this.timeLeft);
      }
   }

   @Override
   public void readNbt(CompoundTag nbt, Provider registries, BaseBlockEntity.UpdateType type) {
      if (this.inventory != null) {
         this.inventory.readNbt(nbt.getCompound("inventory"));
      }

      this.timeLeft = nbt.getLong("timeLeft");
   }

   public Component getDisplayName() {
      return Component.translatable("container.selling_bin");
   }

   public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
      if (this.getMode() == BinMode.BLOCK_BOUND) {
         this.getInventory().setCapacity(this.getMaterial().getRows() * 9);
         return new SellingBinScreenHandler(syncId, playerInventory, this.getInventory(), this.getBlockPos(), this.getMaterial().getRows());
      } else if (this.getMode() == BinMode.PLAYER_BOUND) {
         SellingBinData data = ModWorldData.SELLING_BIN.getGlobal(player.level());
         SellingBinInventory inventory = data.getOrCreate(player, this.getMaterial()).getInventory();
         inventory.setCapacity(this.getMaterial().getRows() * 9);
         data.sendEntriesToClient((ServerPlayer)player);
         return new SellingBinScreenHandler(syncId, playerInventory, inventory, this.getBlockPos(), this.getMaterial().getRows());
      } else {
         return null;
      }
   }

   public void saveExtraData(FriendlyByteBuf buf) {
      buf.writeBlockPos(this.getBlockPos());
      buf.writeVarInt(this.getMaterial().getRows());
   }
}
