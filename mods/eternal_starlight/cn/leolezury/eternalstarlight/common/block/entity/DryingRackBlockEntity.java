package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.block.DryingRackBlock;
import cn.leolezury.eternalstarlight.common.item.recipe.DryingRecipe;
import cn.leolezury.eternalstarlight.common.item.recipe.DryingRecipeInput;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeManager.CachedCheck;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DryingRackBlockEntity extends SimpleContainerBlockEntity {
   private static final String TAG_DRYING_TICKS = "drying_ticks";
   private final CachedCheck<DryingRecipeInput, DryingRecipe> quickCheck = RecipeManager.createCheck(ESRecipes.DRYING.get());
   private boolean lastLit;
   private int dryingTicks = 0;
   private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

   protected DryingRackBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
      super(entityType, pos, state);
   }

   public DryingRackBlockEntity(BlockPos blockPos, BlockState blockState) {
      this(ESBlockEntities.DRYING_RACK.get(), blockPos, blockState);
   }

   public ItemStack getItem() {
      return (ItemStack)this.items.getFirst();
   }

   public void setItem(ItemStack item) {
      this.setItem(0, item);
      this.dryingTicks = 0;
   }

   public boolean canBeDried(ItemStack stack, boolean fireBelow) {
      if (this.level == null) {
         return false;
      }

      for (RecipeHolder<DryingRecipe> holder : this.level.getRecipeManager().getAllRecipesFor(ESRecipes.DRYING.get())) {
         DryingRecipe recipe = (DryingRecipe)holder.value();
         if (fireBelow == recipe.fireBelow() && recipe.input().test(stack)) {
            return true;
         }
      }

      return false;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, DryingRackBlockEntity entity) {
      if (!level.isClientSide) {
         if (!entity.getItem().isEmpty()) {
            boolean lit = (Boolean)state.getValue(DryingRackBlock.LIT);
            if (entity.lastLit != lit) {
               entity.dryingTicks = 0;
               entity.lastLit = lit;
            }

            Optional<RecipeHolder<DryingRecipe>> optionalRecipe = entity.quickCheck
               .getRecipeFor(new DryingRecipeInput((ItemStack)entity.items.getFirst(), lit), level);
            if (optionalRecipe.isPresent()) {
               DryingRecipe recipe = (DryingRecipe)optionalRecipe.get().value();
               entity.dryingTicks++;
               if (entity.dryingTicks > recipe.durationTicks()) {
                  entity.dryingTicks = 0;
                  entity.setItem(recipe.output().copy());
               }
            } else {
               entity.dryingTicks = 0;
            }
         } else {
            entity.dryingTicks = 0;
         }
      }
   }

   @Nullable
   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider provider) {
      return this.saveWithFullMetadata(provider);
   }

   public void setChanged() {
      super.setChanged();
      if (this.getLevel() != null) {
         this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
      }
   }

   public void loadAdditional(CompoundTag compoundTag, Provider provider) {
      super.loadAdditional(compoundTag, provider);
      this.dryingTicks = compoundTag.getInt("drying_ticks");
      this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      ContainerHelper.loadAllItems(compoundTag, this.items, provider);
   }

   protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
      super.saveAdditional(compoundTag, provider);
      compoundTag.putInt("drying_ticks", this.dryingTicks);
      ContainerHelper.saveAllItems(compoundTag, this.items, provider);
   }

   @Override
   protected NonNullList<ItemStack> getItems() {
      return this.items;
   }

   public int getContainerSize() {
      return 1;
   }

   public int getMaxStackSize() {
      return 1;
   }

   public boolean canPlaceItem(int index, ItemStack stack) {
      BlockState state = this.getBlockState();
      return this.canBeDried(stack, state.hasProperty(DryingRackBlock.LIT) && (Boolean)state.getValue(DryingRackBlock.LIT))
         && this.getItem(index).isEmpty()
         && stack.getCount() <= this.getMaxStackSize();
   }
}
