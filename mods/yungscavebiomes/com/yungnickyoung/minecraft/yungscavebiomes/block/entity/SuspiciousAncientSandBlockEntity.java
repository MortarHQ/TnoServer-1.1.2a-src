package com.yungnickyoung.minecraft.yungscavebiomes.block.entity;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SuspiciousAncientSandBlockEntity extends BlockEntity {
   private static final String LOOT_TABLE_TAG = "LootTable";
   private static final String LOOT_TABLE_SEED_TAG = "LootTableSeed";
   private static final String HIT_DIRECTION_TAG = "hit_direction";
   private static final String ITEM_TAG = "item";
   private static final int BRUSH_COOLDOWN_TICKS = 10;
   private static final int BRUSH_RESET_TICKS = 40;
   private static final int REQUIRED_BRUSHES_TO_BREAK = 10;
   private int brushCount;
   private long brushCountResetsAtTick;
   private long coolDownEndsAtTick;
   private ItemStack item = ItemStack.EMPTY;
   @Nullable
   private Direction hitDirection;
   @Nullable
   private ResourceKey<LootTable> lootTable;
   private long lootTableSeed;

   public SuspiciousAncientSandBlockEntity(BlockPos $$0, BlockState $$1) {
      super((BlockEntityType)EntityTypeModule.SUSPICIOUS_ANCIENT_SAND.get(), $$0, $$1);
   }

   public boolean brush(long $$0, Player $$1, Direction $$2) {
      if (this.hitDirection == null) {
         this.hitDirection = $$2;
      }

      this.brushCountResetsAtTick = $$0 + 40L;
      if ($$0 >= this.coolDownEndsAtTick && this.level instanceof ServerLevel) {
         this.coolDownEndsAtTick = $$0 + 10L;
         this.unpackLootTable($$1);
         int $$3 = this.getCompletionState();
         if (++this.brushCount >= 10) {
            this.brushingCompleted($$1);
            return true;
         }

         this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 40);
         int $$4 = this.getCompletionState();
         if ($$3 != $$4) {
            BlockState $$5 = this.getBlockState();
            BlockState $$6 = (BlockState)$$5.setValue(BlockStateProperties.DUSTED, $$4);
            this.level.setBlock(this.getBlockPos(), $$6, 3);
         }

         return false;
      } else {
         return false;
      }
   }

   public void unpackLootTable(Player $$0) {
      if (this.lootTable != null && this.level != null && !this.level.isClientSide() && this.level.getServer() != null) {
         LootTable $$1 = this.level.getServer().reloadableRegistries().getLootTable(this.lootTable);
         if ($$0 instanceof ServerPlayer $$2) {
            CriteriaTriggers.GENERATE_LOOT.trigger($$2, this.lootTable);
         }

         LootParams $$3 = new Builder((ServerLevel)this.level)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition))
            .withLuck($$0.getLuck())
            .withParameter(LootContextParams.THIS_ENTITY, $$0)
            .create(LootContextParamSets.CHEST);
         ObjectArrayList<ItemStack> $$4 = $$1.getRandomItems($$3, this.lootTableSeed);

         this.item = switch ($$4.size()) {
            case 0 -> ItemStack.EMPTY;
            case 1 -> (ItemStack)$$4.getFirst();
            default -> {
               YungsCaveBiomesCommon.LOGGER.warn("Expected max 1 loot from loot table {} got {}", this.lootTable, $$4.size());
               yield (ItemStack)$$4.getFirst();
            }
         };
         this.lootTable = null;
         this.setChanged();
      }
   }

   private void brushingCompleted(Player $$0) {
      if (this.level != null && this.level.getServer() != null) {
         this.dropContent($$0);
         BlockState $$1 = this.getBlockState();
         this.level.levelEvent(3008, this.getBlockPos(), Block.getId($$1));
         Block $$4;
         if (this.getBlockState().getBlock() instanceof BrushableBlock $$3) {
            $$4 = $$3.getTurnsInto();
         } else {
            $$4 = Blocks.AIR;
         }

         this.level.setBlock(this.worldPosition, $$4.defaultBlockState(), 3);
      }
   }

   private void dropContent(Player $$0) {
      if (this.level != null && this.level.getServer() != null) {
         this.unpackLootTable($$0);
         if (!this.item.isEmpty()) {
            double $$1 = EntityType.ITEM.getWidth();
            double $$2 = 1.0 - $$1;
            double $$3 = $$1 / 2.0;
            Direction $$4 = Objects.requireNonNullElse(this.hitDirection, Direction.UP);
            BlockPos $$5 = this.worldPosition.relative($$4, 1);
            double $$6 = $$5.getX() + 0.5 * $$2 + $$3;
            double $$7 = $$5.getY() + 0.5 + EntityType.ITEM.getHeight() / 2.0F;
            double $$8 = $$5.getZ() + 0.5 * $$2 + $$3;
            ItemEntity $$9 = new ItemEntity(this.level, $$6, $$7, $$8, this.item.split(this.level.random.nextInt(21) + 10));
            $$9.setDeltaMovement(Vec3.ZERO);
            this.level.addFreshEntity($$9);
            this.item = ItemStack.EMPTY;
         }
      }
   }

   public void checkReset() {
      if (this.level != null) {
         if (this.brushCount != 0 && this.level.getGameTime() >= this.brushCountResetsAtTick) {
            int $$0 = this.getCompletionState();
            this.brushCount = Math.max(0, this.brushCount - 2);
            int $$1 = this.getCompletionState();
            if ($$0 != $$1) {
               this.level.setBlock(this.getBlockPos(), (BlockState)this.getBlockState().setValue(BlockStateProperties.DUSTED, $$1), 3);
            }

            int $$2 = 4;
            this.brushCountResetsAtTick = this.level.getGameTime() + 4L;
         }

         if (this.brushCount == 0) {
            this.hitDirection = null;
            this.brushCountResetsAtTick = 0L;
            this.coolDownEndsAtTick = 0L;
         } else {
            this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), (int)(this.brushCountResetsAtTick - this.level.getGameTime()));
         }
      }
   }

   private boolean tryLoadLootTable(CompoundTag tag) {
      if (tag.contains("LootTable", 8)) {
         this.lootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(tag.getString("LootTable")));
         this.lootTableSeed = tag.getLong("LootTableSeed");
         return true;
      } else {
         return false;
      }
   }

   private boolean trySaveLootTable(CompoundTag $$0) {
      if (this.lootTable == null) {
         return false;
      }

      $$0.putString("LootTable", this.lootTable.toString());
      if (this.lootTableSeed != 0L) {
         $$0.putLong("LootTableSeed", this.lootTableSeed);
      }

      return true;
   }

   @NotNull
   public CompoundTag getUpdateTag(@NotNull Provider $$0) {
      CompoundTag $$1 = super.getUpdateTag($$0);
      if (this.hitDirection != null) {
         $$1.putInt("hit_direction", this.hitDirection.ordinal());
      }

      if (!this.item.isEmpty()) {
         $$1.put("item", this.item.save($$0));
      }

      return $$1;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public void loadAdditional(@NotNull CompoundTag $$0, @NotNull Provider $$1) {
      super.loadAdditional($$0, $$1);
      if (!this.tryLoadLootTable($$0) && $$0.contains("item")) {
         this.item = ItemStack.parse($$1, $$0.getCompound("item")).orElse(ItemStack.EMPTY);
      } else {
         this.item = ItemStack.EMPTY;
      }

      if ($$0.contains("hit_direction")) {
         this.hitDirection = Direction.values()[$$0.getInt("hit_direction")];
      }
   }

   protected void saveAdditional(@NotNull CompoundTag $$0, @NotNull Provider $$1) {
      super.saveAdditional($$0, $$1);
      if (!this.trySaveLootTable($$0) && !this.item.isEmpty()) {
         $$0.put("item", this.item.save($$1));
      }
   }

   public void setLootTable(ResourceKey<LootTable> $$0, long $$1) {
      this.lootTable = $$0;
      this.lootTableSeed = $$1;
   }

   private int getCompletionState() {
      if (this.brushCount == 0) {
         return 0;
      } else if (this.brushCount < 3) {
         return 1;
      } else {
         return this.brushCount < 6 ? 2 : 3;
      }
   }

   @Nullable
   public Direction getHitDirection() {
      return this.hitDirection;
   }

   public ItemStack getItem() {
      return this.item;
   }

   public void setItem(ItemStack itemStack) {
      this.item = itemStack;
   }
}
