package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.blocks.BlockFurnaceHeater;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticleStream;
import java.lang.reflect.Field;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.util.ObfuscationReflectionHelper;

public class BlockEntityFurnaceHeater extends BlockEntityImpl implements ITickableBlockEntity {
   private static final Field FURNACE_DATA_FIELD = ObfuscationReflectionHelper.findField(AbstractFurnaceBlockEntity.class, "dataAccess");
   public boolean isActive;

   public BlockEntityFurnaceHeater(BlockPos pos, BlockState state) {
      super(ModBlockEntities.FURNACE_HEATER, pos, state);
   }

   public static ContainerData getFurnaceData(AbstractFurnaceBlockEntity tile) {
      try {
         return (ContainerData)FURNACE_DATA_FIELD.get(tile);
      } catch (IllegalAccessException e) {
         NaturesAura.LOGGER.fatal("Couldn't reflect furnace field", e);
         return null;
      }
   }

   public static RecipeType<? extends AbstractCookingRecipe> getRecipeType(AbstractFurnaceBlockEntity furnace) {
      if (furnace instanceof BlastFurnaceBlockEntity) {
         return RecipeType.BLASTING;
      } else {
         return furnace instanceof SmokerBlockEntity ? RecipeType.SMOKING : RecipeType.SMELTING;
      }
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.level.getGameTime() % 5L == 0L) {
         boolean did = false;
         Direction facing = (Direction)this.level.getBlockState(this.worldPosition).getValue(BlockFurnaceHeater.FACING);
         BlockPos tilePos = this.worldPosition.relative(facing.getOpposite());
         if (this.level.getBlockEntity(tilePos) instanceof AbstractFurnaceBlockEntity furnace && this.isReady(furnace)) {
            ContainerData data = getFurnaceData(furnace);
            int burnTime = data.get(0);
            if (burnTime <= 0) {
               this.level.setBlockAndUpdate(tilePos, (BlockState)this.level.getBlockState(tilePos).setValue(AbstractFurnaceBlock.LIT, true));
            }

            int toDrain = Mth.ceil((200 - burnTime) * 16.6F);
            if (this.canUseRightNow(toDrain)) {
               data.set(0, 200);
               data.set(2, Math.min(data.get(3) - 2, data.get(2) + 5));
               BlockPos spot = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 20, this.worldPosition);
               IAuraChunk chunk = IAuraChunk.getAuraChunk(this.level, spot);
               chunk.drainAura(spot, toDrain);
               did = true;
               if (this.level.getGameTime() % 15L == 0L) {
                  PacketHandler.sendToAllAround(
                     this.level,
                     this.worldPosition,
                     32,
                     new PacketParticleStream(
                        this.worldPosition.getX() + (float)this.level.random.nextGaussian() * 5.0F,
                        this.worldPosition.getY() + 1 + this.level.random.nextFloat() * 5.0F,
                        this.worldPosition.getZ() + (float)this.level.random.nextGaussian() * 5.0F,
                        tilePos.getX() + this.level.random.nextFloat(),
                        tilePos.getY() + this.level.random.nextFloat(),
                        tilePos.getZ() + this.level.random.nextFloat(),
                        this.level.random.nextFloat() * 0.07F + 0.07F,
                        IAuraType.forLevel(this.level).getColor(),
                        this.level.random.nextFloat() + 0.5F
                     )
                  );
               }
            }
         }

         if (did != this.isActive) {
            this.isActive = did;
            this.sendToClients();
         }
      }
   }

   private boolean isReady(AbstractFurnaceBlockEntity furnace) {
      if (!furnace.getItem(1).isEmpty()) {
         return false;
      }

      ItemStack input = furnace.getItem(0);
      if (!input.isEmpty()) {
         RecipeHolder<? extends AbstractCookingRecipe> recipe = (RecipeHolder<? extends AbstractCookingRecipe>)this.level
            .getRecipeManager()
            .getRecipeFor(getRecipeType(furnace), new SingleRecipeInput(input), this.level)
            .orElse(null);
         if (recipe == null) {
            return false;
         }

         ItemStack output = ((AbstractCookingRecipe)recipe.value()).getResultItem(this.level.registryAccess());
         ItemStack currOutput = furnace.getItem(2);
         return currOutput.isEmpty() || Helper.areItemsEqual(currOutput, output, true) && currOutput.getCount() + output.getCount() <= output.getMaxStackSize();
      } else {
         return false;
      }
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type == BlockEntityImpl.SaveType.SYNC) {
         compound.putBoolean("active", this.isActive);
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type == BlockEntityImpl.SaveType.SYNC) {
         this.isActive = compound.getBoolean("active");
      }
   }

   @Override
   public boolean allowsLowerLimiter() {
      return true;
   }
}
