package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public class BlockEntityBlastFurnaceBooster extends BlockEntityImpl implements ITickableBlockEntity {
   public BlockEntityBlastFurnaceBooster(BlockPos pos, BlockState state) {
      super(ModBlockEntities.BLAST_FURNACE_BOOSTER, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         int toUse = 6500;
         if (this.canUseRightNow(toUse)) {
            if (this.level.getBlockEntity(this.worldPosition.below()) instanceof BlastFurnaceBlockEntity tile) {
               SingleRecipeInput input = new SingleRecipeInput(tile.getItem(0));
               RecipeHolder<? extends AbstractCookingRecipe> recipe = (RecipeHolder<? extends AbstractCookingRecipe>)this.level
                  .getRecipeManager()
                  .getRecipeFor(BlockEntityFurnaceHeater.getRecipeType(tile), input, this.level)
                  .orElse(null);
               if (recipe != null) {
                  if (this.isApplicable(((AbstractCookingRecipe)recipe.value()).getIngredients())) {
                     ContainerData data = BlockEntityFurnaceHeater.getFurnaceData(tile);
                     int doneDiff = data.get(3) - data.get(2);
                     if (doneDiff <= 1) {
                        if (this.level.random.nextFloat() > 0.45F) {
                           PacketHandler.sendToAllAround(
                              this.level,
                              this.worldPosition,
                              32,
                              new PacketParticles(
                                 this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), PacketParticles.Type.BLAST_FURNACE_BOOSTER, 0
                              )
                           );
                        } else {
                           ItemStack output = tile.getItem(2);
                           if (output.getCount() < output.getMaxStackSize()) {
                              if (output.isEmpty()) {
                                 ItemStack result = ((AbstractCookingRecipe)recipe.value()).getResultItem(this.level.registryAccess());
                                 tile.setItem(2, result.copy());
                              } else {
                                 output.grow(1);
                              }

                              BlockPos pos = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 30, this.worldPosition);
                              IAuraChunk.getAuraChunk(this.level, pos).drainAura(pos, toUse);
                              PacketHandler.sendToAllAround(
                                 this.level,
                                 this.worldPosition,
                                 32,
                                 new PacketParticles(
                                    this.worldPosition.getX(),
                                    this.worldPosition.getY(),
                                    this.worldPosition.getZ(),
                                    PacketParticles.Type.BLAST_FURNACE_BOOSTER,
                                    1
                                 )
                              );
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean isApplicable(List<Ingredient> ingredients) {
      for (Ingredient ing : ingredients) {
         for (ItemStack stack : ing.getItems()) {
            if (stack.is(Items.ORES) || stack.is(Items.RAW_MATERIALS)) {
               return true;
            }
         }
      }

      return false;
   }

   public IItemHandlerModifiable getItemHandler() {
      BlockEntity below = this.level.getBlockEntity(this.worldPosition.below());
      if (!(below instanceof BlastFurnaceBlockEntity)) {
         return null;
      }

      final IItemHandler handler = (IItemHandler)this.level.getCapability(ItemHandler.BLOCK, below.getBlockPos(), below.getBlockState(), below, Direction.UP);
      return handler == null ? null : new IItemHandlerModifiable() {
         public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
            if (handler instanceof IItemHandlerModifiable) {
               ((IItemHandlerModifiable)handler).setStackInSlot(0, stack);
            }
         }

         public int getSlots() {
            return 1;
         }

         @Nonnull
         public ItemStack getStackInSlot(int slot) {
            return handler.getStackInSlot(0);
         }

         @Nonnull
         public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return handler.insertItem(0, stack, simulate);
         }

         @Nonnull
         public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return handler.extractItem(0, amount, simulate);
         }

         public int getSlotLimit(int slot) {
            return handler.getSlotLimit(0);
         }

         public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return handler.isItemValid(0, stack);
         }
      };
   }

   @Override
   public void dropInventory() {
   }

   @Override
   public boolean allowsLowerLimiter() {
      return true;
   }
}
