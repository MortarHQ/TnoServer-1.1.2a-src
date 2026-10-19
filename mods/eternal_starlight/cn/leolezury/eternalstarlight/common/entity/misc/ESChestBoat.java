package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

public class ESChestBoat extends ESBoat implements HasCustomInventoryScreen, ContainerEntity {
   private NonNullList<ItemStack> itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
   @Nullable
   private ResourceKey<LootTable> lootTable;
   private long lootTableSeed;

   public ESChestBoat(EntityType<? extends ESChestBoat> type, Level level) {
      super(type, level);
   }

   public ESChestBoat(Level level, double x, double y, double z) {
      this(ESEntities.CHEST_BOAT.get(), level);
      this.setPos(x, y, z);
      this.xo = x;
      this.yo = y;
      this.zo = z;
   }

   protected float getSinglePassengerXOffset() {
      return 0.15F;
   }

   protected int getMaxPassengers() {
      return 1;
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      this.addChestVehicleSaveData(tag, this.registryAccess());
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.readChestVehicleSaveData(tag, this.registryAccess());
   }

   public void destroy(DamageSource source) {
      super.destroy(source);
      this.chestVehicleDestroyed(source, this.level(), this);
   }

   public void remove(RemovalReason reason) {
      if (!this.level().isClientSide && reason.shouldDestroy()) {
         Containers.dropContents(this.level(), this, this);
      }

      super.remove(reason);
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      if (this.canAddPassenger(player) && !player.isSecondaryUseActive()) {
         return super.interact(player, hand);
      }

      InteractionResult interactionresult = this.interactWithContainerVehicle(player);
      if (interactionresult.consumesAction()) {
         this.gameEvent(GameEvent.CONTAINER_OPEN, player);
         PiglinAi.angerNearbyPiglins(player, true);
      }

      return interactionresult;
   }

   public void openCustomInventoryScreen(Player player) {
      player.openMenu(this);
      if (!player.level().isClientSide) {
         this.gameEvent(GameEvent.CONTAINER_OPEN, player);
         PiglinAi.angerNearbyPiglins(player, true);
      }
   }

   @Override
   public Item getDropItem() {
      return switch (this.getESBoatType()) {
         case LUNAR -> (Item)ESItems.LUNAR_CHEST_BOAT.get();
         case NORTHLAND -> (Item)ESItems.NORTHLAND_CHEST_BOAT.get();
         case BANYIN -> (Item)ESItems.BANYIN_CHEST_BOAT.get();
         case SCARLET -> (Item)ESItems.SCARLET_CHEST_BOAT.get();
         case TORREYA -> (Item)ESItems.TORREYA_CHEST_BOAT.get();
         case JINGLESTEM -> (Item)ESItems.JINGLESTEM_CHEST_RAFT.get();
         case CRADLEWOOD -> (Item)ESItems.CRADLEWOOD_CHEST_BOAT.get();
      };
   }

   public void clearContent() {
      this.clearChestVehicleContent();
   }

   public int getContainerSize() {
      return 27;
   }

   public ItemStack getItem(int index) {
      return this.getChestVehicleItem(index);
   }

   public ItemStack removeItem(int index, int amount) {
      return this.removeChestVehicleItem(index, amount);
   }

   public ItemStack removeItemNoUpdate(int index) {
      return this.removeChestVehicleItemNoUpdate(index);
   }

   public void setItem(int index, ItemStack stack) {
      this.setChestVehicleItem(index, stack);
   }

   public SlotAccess getSlot(int index) {
      return this.getChestVehicleSlot(index);
   }

   public void setChanged() {
   }

   public boolean stillValid(Player player) {
      return this.isChestVehicleStillValid(player);
   }

   @Nullable
   public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
      if (this.lootTable != null && player.isSpectator()) {
         return null;
      }

      this.unpackLootTable(inventory.player);
      return ChestMenu.threeRows(id, inventory, this);
   }

   public void unpackLootTable(@Nullable Player player) {
      this.unpackChestVehicleLootTable(player);
   }

   @Nullable
   public ResourceKey<LootTable> getLootTable() {
      return this.lootTable;
   }

   public void setLootTable(@Nullable ResourceKey<LootTable> resourceKey) {
      this.lootTable = resourceKey;
   }

   public long getLootTableSeed() {
      return this.lootTableSeed;
   }

   public void setLootTableSeed(long seed) {
      this.lootTableSeed = seed;
   }

   public NonNullList<ItemStack> getItemStacks() {
      return this.itemStacks;
   }

   public void clearItemStacks() {
      this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
   }
}
