package noobanidus.mods.lootr.common.block.entity;

import com.google.auto.service.AutoService;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.IBrushable;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.LootrTags;
import noobanidus.mods.lootr.common.api.PlayerContext;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.SimpleLootrInstance;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootr.common.integration.digsite_workshop.IModdedBrushItem;
import noobanidus.mods.lootr.common.mixin.accessor.AccessorMixinFallingBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LootrBrushableBlockEntity extends BlockEntity implements ILootrBlockEntity, IBrushable {
   private final SimpleLootrInstance simpleLootrInstance = new SimpleLootrInstance(this::getVisualOpeners, 1);
   @Nullable
   private UUID brushingPlayer;
   @Nullable
   private Player brushingPlayerEntity;
   private int brushCount;
   private long brushCountResetsAtTick;
   private long coolDownEndsAtTick;
   private ItemStack item = ItemStack.EMPTY;
   @Nullable
   private Direction hitDirection;
   @Nullable
   private ResourceKey<LootTable> lootTable;
   private long lootTableSeed;

   public LootrBrushableBlockEntity(BlockPos blockPos, BlockState blockState) {
      super(LootrRegistry.getBrushableBlockEntity(), blockPos, blockState);
   }

   private CompoundTag getFallData(Provider provider) {
      CompoundTag tag = new CompoundTag();
      this.saveAdditional(tag, provider);
      return tag;
   }

   @Nullable
   @Override
   public IContainerTrigger getTrigger() {
      if (this.getBlockState().is(LootrTags.Blocks.SANDS)) {
         return LootrRegistry.getSandTrigger();
      } else {
         return this.getBlockState().is(LootrTags.Blocks.GRAVELS) ? LootrRegistry.getGravelTrigger() : null;
      }
   }

   @Override
   public boolean IBrushable$brush(long l, Player player, Direction direction) {
      ItemStack brushItem = this.getBrushItem(player);
      if (brushItem.getItem() instanceof IModdedBrushItem moddedBrushItem) {
         this.coolDownEndsAtTick = this.coolDownEndsAtTick - (10L - moddedBrushItem.lootr$getBrushingSpeed());
      }

      Player brushingPlayer = this.getBrushingPlayer();
      if (brushingPlayer != null) {
         if (player != brushingPlayer) {
            return false;
         }

         if (!this.hasLootAvailable((ServerPlayer)player)) {
            return false;
         }
      } else {
         if (!this.hasLootAvailable((ServerPlayer)player)) {
            this.brushingPlayer = null;
            this.brushingPlayerEntity = null;
            return false;
         }

         this.brushingPlayerEntity = player;
         this.brushingPlayer = null;
      }

      if (!this.simpleLootrInstance.hasBeenOpened()) {
         this.simpleLootrInstance.setHasBeenOpened();
         this.markChanged();
      }

      if (this.hitDirection == null) {
         this.hitDirection = direction;
      }

      this.brushCountResetsAtTick = l + 40L;
      if (l >= this.coolDownEndsAtTick && this.level instanceof ServerLevel) {
         this.coolDownEndsAtTick = l + 10L;
         int i = this.getCompletionState();
         if (++this.brushCount >= 10) {
            this.brushingCompleted(player);
            return true;
         }

         this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 2);
         int j = this.getCompletionState();
         if (i != j) {
            BlockState blockState = this.getBlockState();
            BlockState blockState2 = (BlockState)blockState.setValue(BlockStateProperties.DUSTED, j);
            this.level.setBlock(this.getBlockPos(), blockState2, 3);
         }

         return false;
      } else {
         return false;
      }
   }

   private ItemStack getBrushItem(Player player) {
      if (player.getMainHandItem().getItem() instanceof IModdedBrushItem) {
         return player.getMainHandItem();
      } else {
         return player.getOffhandItem().getItem() instanceof IModdedBrushItem ? player.getOffhandItem() : ItemStack.EMPTY;
      }
   }

   private void brushingCompleted(Player player) {
      if (this.level != null && this.level.getServer() != null) {
         this.dropContent(player);
         this.performTrigger((ServerPlayer)player);
         boolean shouldUpdate = false;
         if (!this.hasServerOpened(player)) {
            player.awardStat(LootrRegistry.getLootedStat());
            LootrRegistry.getStatTrigger().trigger((ServerPlayer)player);
         }

         if (this.addOpener(player)) {
            this.performOpen((ServerPlayer)player);
            shouldUpdate = true;
         }

         if (shouldUpdate) {
            this.performUpdate((ServerPlayer)player);
         }

         if (LootrAPI.isCustomTrapped() && this.isInfoReferenceInventory()) {
            Block block = this.getBlockState().getBlock();
            this.level.updateNeighborsAt(this.getBlockPos(), block);
            this.level.updateNeighborsAt(this.getBlockPos().below(), block);
         }
      }
   }

   private void dropContent(Player player) {
      if (this.level != null && this.level.getServer() != null) {
         ItemStack theItem = this.popItem(player);
         if (!theItem.isEmpty()) {
            double d = EntityType.ITEM.getWidth();
            double e = 1.0 - d;
            double f = d / 2.0;
            Direction direction = Objects.requireNonNullElse(this.hitDirection, Direction.UP);
            BlockPos blockPos = this.worldPosition.relative(direction, 1);
            double g = blockPos.getX() + 0.5 * e + f;
            double h = blockPos.getY() + 0.5 + EntityType.ITEM.getHeight() / 2.0F;
            double i = blockPos.getZ() + 0.5 * e + f;
            ItemEntity itemEntity = new ItemEntity(this.level, g, h, i, theItem.split(this.level.random.nextInt(21) + 10));
            itemEntity.setDeltaMovement(Vec3.ZERO);
            this.level.addFreshEntity(itemEntity);
            this.item = ItemStack.EMPTY;
         }
      }
   }

   @Override
   public void IBrushable$checkReset() {
      if (this.level != null) {
         if (this.brushCount != 0 && this.level.getGameTime() >= this.brushCountResetsAtTick) {
            int i = this.getCompletionState();
            this.brushCount = Math.max(0, this.brushCount - 2);
            int j = this.getCompletionState();
            if (i != j) {
               this.level.setBlock(this.getBlockPos(), (BlockState)this.getBlockState().setValue(BlockStateProperties.DUSTED, j), 3);
            }

            this.brushCountResetsAtTick = this.level.getGameTime() + 4L;
         }

         if (this.brushCount == 0) {
            this.brushingPlayer = null;
            this.brushingPlayerEntity = null;
            this.hitDirection = null;
            this.brushCountResetsAtTick = 0L;
            this.coolDownEndsAtTick = 0L;
         } else {
            this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 2);
         }
      }
   }

   private void tryLoadLootTable(CompoundTag compoundTag) {
      if (compoundTag.contains("LootTable")) {
         this.lootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(compoundTag.getString("LootTable")));
      }

      if (compoundTag.contains("LootTableSeed")) {
         this.lootTableSeed = compoundTag.getLong("LootTableSeed");
      }
   }

   private void trySaveLootTable(CompoundTag compoundTag) {
      if (this.lootTable != null) {
         compoundTag.putString("LootTable", this.lootTable.location().toString());
         if (this.lootTableSeed != 0L) {
            compoundTag.putLong("LootTableSeed", this.lootTableSeed);
         }
      }
   }

   @Override
   public void setLootTableInternal(ResourceKey<LootTable> lootTable, long seed) {
      this.lootTable = lootTable;
      this.lootTableSeed = seed;
   }

   @Nullable
   public Player getBrushingPlayer() {
      if (this.brushingPlayerEntity != null) {
         if (this.brushingPlayer != null) {
            this.brushingPlayer = null;
         }

         return this.brushingPlayerEntity;
      } else {
         if (this.brushingPlayer != null && this.level != null) {
            this.brushingPlayerEntity = this.level.getPlayerByUUID(this.brushingPlayer);
            this.brushingPlayer = null;
         }

         return null;
      }
   }

   public CompoundTag getUpdateTag(Provider provider) {
      CompoundTag compoundTag = super.getUpdateTag(provider);
      if (this.hitDirection != null) {
         compoundTag.putInt("hit_direction", this.hitDirection.ordinal());
      }

      Player player = this.getBrushingPlayer();
      if (player != null) {
         compoundTag.putUUID("brushing_player", player.getUUID());
         if (this.item.isEmpty()) {
            this.item = this.getItem(player);
         }

         if (!this.item.isEmpty()) {
            compoundTag.put("item", this.item.save(provider));
         }
      }

      this.simpleLootrInstance.fillUpdateTag(compoundTag, provider, this.level != null && this.level.isClientSide());
      return compoundTag;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
   }

   protected void loadAdditional(CompoundTag compoundTag, Provider provider) {
      super.loadAdditional(compoundTag, provider);
      this.tryLoadLootTable(compoundTag);
      if (compoundTag.contains("hit_direction")) {
         this.hitDirection = Direction.values()[compoundTag.getInt("hit_direction")];
      }

      if (compoundTag.hasUUID("brushing_player")) {
         this.brushingPlayer = compoundTag.getUUID("brushing_player");
      } else {
         this.brushingPlayer = null;
      }

      this.brushingPlayerEntity = null;
      if (this.brushingPlayer != null && compoundTag.contains("item")) {
         this.item = ItemStack.parseOptional(provider, compoundTag.getCompound("item"));
      } else {
         this.item = ItemStack.EMPTY;
      }

      this.simpleLootrInstance.loadAdditional(compoundTag, provider);
   }

   protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
      super.saveAdditional(compoundTag, provider);
      this.trySaveLootTable(compoundTag);
      this.simpleLootrInstance.saveAdditional(compoundTag, provider, this.level != null && this.level.isClientSide());
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

   public boolean isBrushingPlayer(PlayerContext context) {
      return context.hasPlayer() && this.isBrushingPlayer(context.player());
   }

   public boolean isBrushingPlayer(Player player) {
      Player brushingPlayer = this.getBrushingPlayer();
      return brushingPlayer != null && brushingPlayer == player;
   }

   public ItemStack getItem(Player player) {
      boolean clientSide = player.level().isClientSide();
      if (this.isBrushingPlayer(player)) {
         if (clientSide) {
            return this.item;
         }

         if (this.item.isEmpty()) {
            ILootrInventory inventory = LootrAPI.getInventory(this, (ServerPlayer)player);
            if (inventory == null) {
               this.item = ItemStack.EMPTY;
            } else {
               this.item = inventory.getItem(0);
            }
         }
      } else {
         this.item = ItemStack.EMPTY;
      }

      return this.item;
   }

   private ItemStack popItem(Player player) {
      if (player.level().isClientSide()) {
         return this.getItem(player);
      }

      ItemStack theItem = this.getItem(player);
      if (!theItem.isEmpty()) {
         ILootrInventory inventory = LootrAPI.getInventory(this, (ServerPlayer)player);
         if (inventory != null) {
            inventory.setItem(0, ItemStack.EMPTY);
            inventory.setChanged();
         }

         this.item = ItemStack.EMPTY;
      }

      return theItem;
   }

   @Nullable
   @Override
   public Set<UUID> getClientOpeners() {
      return this.simpleLootrInstance.getClientOpeners();
   }

   @Override
   public boolean isClientOpened() {
      return this.simpleLootrInstance.isClientOpened();
   }

   @Override
   public void setClientOpened(boolean opened) {
      this.simpleLootrInstance.setClientOpened(opened);
   }

   @Override
   public void markChanged() {
      this.setChanged();
      this.markDataChanged();
   }

   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return LootrBlockType.CHEST;
   }

   @Override
   public ILootrType getInfoNewType() {
      if (this.getBlockState().is(LootrTags.Blocks.SANDS)) {
         return BuiltInLootrTypes.SAND;
      }

      if (this.getBlockState().is(LootrTags.Blocks.GRAVELS)) {
         return BuiltInLootrTypes.GRAVEL;
      }

      LootrAPI.LOG
         .error(
            "Brushable {} at {} in {} is neither sand nor gravel and does not provide ILootrType. Defaulting to 'simple'",
            this.getBlockState(),
            this.getBlockPos(),
            this.getInfoDimension()
         );
      return BuiltInLootrTypes.SIMPLE;
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.simpleLootrInstance.getInfoUUID();
   }

   @Override
   public String getInfoKey() {
      return this.simpleLootrInstance.getInfoKey();
   }

   @Override
   public boolean hasBeenOpened() {
      return this.simpleLootrInstance.hasBeenOpened();
   }

   @Override
   public boolean isPhysicallyOpen() {
      return (Integer)this.getBlockState().getValue(BlockStateProperties.DUSTED) > 0;
   }

   @NotNull
   @Override
   public BlockPos getInfoPos() {
      return this.getBlockPos();
   }

   @Nullable
   @Override
   public Component getInfoDisplayName() {
      return null;
   }

   @NotNull
   @Override
   public ResourceKey<Level> getInfoDimension() {
      return this.level.dimension();
   }

   @Override
   public int getInfoContainerSize() {
      return 1;
   }

   @Nullable
   @Override
   public NonNullList<ItemStack> getInfoReferenceInventory() {
      return this.simpleLootrInstance.getCustomInventory();
   }

   @Override
   public void setInfoReferenceInventory(NonNullList<ItemStack> reference) {
      this.simpleLootrInstance.setCustomInventory(reference);
   }

   @Override
   public boolean isInfoReferenceInventory() {
      return this.isInfoReferenceInventoryInternal(this.simpleLootrInstance.isCustomInventory());
   }

   @Nullable
   @Override
   public ResourceKey<LootTable> getInfoLootTable() {
      return this.lootTable;
   }

   @Override
   public long getInfoLootSeed() {
      return this.lootTableSeed;
   }

   @Override
   public Level getInfoLevel() {
      return this.level;
   }

   public void removeComponentsFromTag(CompoundTag compoundTag) {
      super.removeComponentsFromTag(compoundTag);
      compoundTag.remove("LootTable");
      compoundTag.remove("LootTableSeed");
   }

   protected void applyImplicitComponents(DataComponentInput dataComponentInput) {
      super.applyImplicitComponents(dataComponentInput);
      SeededContainerLoot loot = (SeededContainerLoot)dataComponentInput.get(DataComponents.CONTAINER_LOOT);
      if (loot != null && loot.lootTable() != null) {
         this.lootTable = loot.lootTable();
         this.lootTableSeed = loot.seed();
      }
   }

   protected void collectImplicitComponents(Builder builder) {
      super.collectImplicitComponents(builder);
      if (this.lootTable != null) {
         builder.set(DataComponents.CONTAINER_LOOT, new SeededContainerLoot(this.lootTable, this.lootTableSeed));
      }
   }

   public static void fall(ServerLevel level, BlockPos blockPos, BlockState blockState, LootrBrushableBlockEntity brushableBlockEntity) {
      if (!LootrAPI.canBrushablesSelfSupport()) {
         FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(EntityType.FALLING_BLOCK, level);
         double d = blockPos.getX() + 0.5;
         double e = blockPos.getY();
         double f = blockPos.getZ() + 0.5;
         ((AccessorMixinFallingBlockEntity)fallingBlockEntity)
            .lootr$setBlockState(
               blockState.hasProperty(BlockStateProperties.WATERLOGGED)
                  ? (BlockState)blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE)
                  : blockState
            );
         fallingBlockEntity.blocksBuilding = true;
         fallingBlockEntity.setPos(d, e, f);
         fallingBlockEntity.setDeltaMovement(Vec3.ZERO);
         fallingBlockEntity.xo = d;
         fallingBlockEntity.yo = e;
         fallingBlockEntity.zo = f;
         fallingBlockEntity.blockData = brushableBlockEntity.getFallData(level.registryAccess());
         fallingBlockEntity.setStartPos(fallingBlockEntity.blockPosition());
         fallingBlockEntity.dropItem = false;
         level.setBlock(blockPos, blockState.getFluidState().createLegacyBlock(), 3);
         level.addFreshEntity(fallingBlockEntity);
      }
   }

   @AutoService(ILootrBlockEntityConverter.class)
   public static class DefaultBlockEntityConverter implements ILootrBlockEntityConverter<LootrBrushableBlockEntity> {
      public ILootrBlockEntity apply(LootrBrushableBlockEntity blockEntity) {
         return blockEntity;
      }

      @Override
      public BlockEntityType<?> getBlockEntityType() {
         return LootrRegistry.getBrushableBlockEntity();
      }
   }
}
