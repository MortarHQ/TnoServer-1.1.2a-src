package twilightforest.block.entity;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerRegistries.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.RecipeManager.CachedCheck;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import twilightforest.block.DryingRackBlock;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFRecipes;
import twilightforest.item.recipe.DryingRecipe;

public class DryingRackBlockEntity extends BlockEntity {
   public static final int DEFAULT_DRYING_TIME = 6000;
   private ItemStack stack = ItemStack.EMPTY;
   private final CachedCheck<SingleRecipeInput, DryingRecipe> quickCheck = RecipeManager.createCheck((RecipeType)TFRecipes.DRYING_RECIPE.get());
   protected boolean drying;
   protected int dryTime;
   protected int totalDryTime;

   public DryingRackBlockEntity(BlockPos pos, BlockState blockState) {
      super((BlockEntityType)TFBlockEntities.DRYING_RACK.get(), pos, blockState);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, DryingRackBlockEntity entity) {
      if (!(Boolean)state.getValue(DryingRackBlock.WATERLOGGED) && !level.isClientSide()) {
         if (!entity.getTheItem().isEmpty()) {
            SingleRecipeInput input = new SingleRecipeInput(entity.getTheItem());
            RecipeHolder<DryingRecipe> recipeholder = (RecipeHolder<DryingRecipe>)entity.quickCheck.getRecipeFor(input, level).orElse(null);
            boolean recipeHolderExists = recipeholder != null;
            entity.updateDryingTime(recipeHolderExists);
            if (recipeHolderExists) {
               entity.dryTime++;
               if (entity.dryTime >= entity.totalDryTime) {
                  entity.setTheItem(((DryingRecipe)recipeholder.value()).assemble(input, level.registryAccess()));
                  setChanged(level, pos, state);
               }
            }
         } else {
            entity.updateDryingTime(false);
         }
      }
   }

   private void updateDryingTime(boolean drying) {
      boolean wasDrying = this.drying;
      this.drying = drying;
      if (wasDrying != drying) {
         this.setChanged();
      }
   }

   public void setChanged() {
      super.setChanged();
      if (this.level != null) {
         this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
      }
   }

   public ItemStack getTheItem() {
      return this.stack;
   }

   public void setTheItem(ItemStack newItem) {
      boolean updateDryTime = newItem.isEmpty() || !ItemStack.isSameItemSameComponents(this.stack, newItem);
      this.stack = newItem;
      this.stack.limitSize(1);
      if (updateDryTime) {
         this.totalDryTime = this.getDryingTime();
         this.dryTime = 0;
         this.setChanged();
         if (this.level != null && !this.level.isClientSide) {
            if (!newItem.isEmpty() && !(Boolean)this.getBlockState().getValue(DryingRackBlock.WATERLOGGED)) {
               this.drying = this.quickCheck.getRecipeFor(new SingleRecipeInput(newItem), this.level).isPresent();
            } else {
               this.drying = false;
            }
         }
      }
   }

   public ItemStack takeTheItem() {
      ItemStack theItem = this.getTheItem();
      this.setTheItem(ItemStack.EMPTY);
      return theItem;
   }

   public boolean fillFromLootTable(ResourceKey<LootTable> lootTableKey, long seed, ServerLevel level) {
      MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
      return this.fillFromLootTable(lootTableKey, seed, level, currentServer.reloadableRegistries());
   }

   public boolean fillFromLootTable(ResourceKey<LootTable> lootTableKey, long seed, ServerLevel serverLevel, Holder holder) {
      LootTable lootTable = holder.getLootTable(lootTableKey);
      if (lootTable == LootTable.EMPTY) {
         return false;
      }

      LootParams params = new Builder(serverLevel)
         .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.getBlockPos()))
         .create(LootContextParamSets.CHEST);
      lootTable.getRandomItemsRaw(
         new net.minecraft.world.level.storage.loot.LootContext.Builder(params).withOptionalRandomSeed(seed).create(Optional.of(lootTableKey.location())),
         lootStack -> this.stack = lootStack
      );
      return true;
   }

   private int getDryingTime() {
      SingleRecipeInput singlerecipeinput = new SingleRecipeInput(this.getTheItem());
      Level level = this.getLevel();
      return level == null
         ? 6000
         : this.quickCheck.getRecipeFor(singlerecipeinput, level).map(holder -> ((DryingRecipe)holder.value()).getDryingTime()).orElse(6000);
   }

   public boolean isDrying() {
      return this.drying;
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      if (!this.stack.isEmpty()) {
         tag.put("item", this.stack.save(registries));
      }

      tag.putInt("dry_time", this.dryTime);
      tag.putInt("total_dry_time", this.totalDryTime);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      if (tag.contains("item", 10)) {
         this.stack = ItemStack.parse(registries, tag.getCompound("item")).orElse(ItemStack.EMPTY);
      } else {
         this.stack = ItemStack.EMPTY;
      }

      this.dryTime = tag.getInt("dry_time");
      this.totalDryTime = tag.getInt("total_dry_time");
      if (tag.contains("drying")) {
         this.drying = tag.getBoolean("drying");
      }
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag tag = super.getUpdateTag(registries);
      tag.putBoolean("drying", this.drying);
      if (!this.stack.isEmpty()) {
         tag.put("item", this.stack.save(registries));
      }

      return tag;
   }

   public record DryingRackHandler(DryingRackBlockEntity inventory) implements IItemHandlerModifiable {
      public int getSlots() {
         return 1;
      }

      public ItemStack getStackInSlot(int slot) {
         return this.inventory.getTheItem();
      }

      public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
         if (!this.inventory.getTheItem().isEmpty()) {
            return stack;
         }

         ItemStack copyStack = stack.copy();
         ItemStack splitOut = copyStack.split(1);
         if (!simulate) {
            this.inventory.setTheItem(splitOut);
         }

         return copyStack;
      }

      public ItemStack extractItem(int slot, int amount, boolean simulate) {
         if (this.inventory.drying) {
            return ItemStack.EMPTY;
         } else {
            return simulate ? this.inventory.getTheItem() : this.inventory.takeTheItem();
         }
      }

      public int getSlotLimit(int slot) {
         return 1;
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         return true;
      }

      public void setStackInSlot(int slot, ItemStack stack) {
         this.inventory.setTheItem(stack);
      }
   }
}
