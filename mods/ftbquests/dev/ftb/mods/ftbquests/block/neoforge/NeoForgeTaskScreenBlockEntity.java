package dev.ftb.mods.ftbquests.block.neoforge;

import dev.ftb.mods.ftbquests.block.TaskScreenBlock;
import dev.ftb.mods.ftbquests.block.entity.TaskScreenBlockEntity;
import dev.ftb.mods.ftbquests.integration.item_filtering.ItemMatchingSystem;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.FluidTask;
import dev.ftb.mods.ftbquests.quest.task.ItemTask;
import dev.ftb.mods.ftbquests.quest.task.neoforge.ForgeEnergyTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class NeoForgeTaskScreenBlockEntity extends TaskScreenBlockEntity {
   private AABB cachedRenderAABB = null;
   private final NeoForgeTaskScreenBlockEntity.TaskItemHandler itemHandler = new NeoForgeTaskScreenBlockEntity.TaskItemHandler();
   private final NeoForgeTaskScreenBlockEntity.TaskFluidHandler fluidHandler = new NeoForgeTaskScreenBlockEntity.TaskFluidHandler();
   private final NeoForgeTaskScreenBlockEntity.TaskEnergyHandler energyHandler = new NeoForgeTaskScreenBlockEntity.TaskEnergyHandler();

   public NeoForgeTaskScreenBlockEntity(BlockPos blockPos, BlockState blockState) {
      super(blockPos, blockState);
   }

   public IItemHandler getItemHandler() {
      return this.itemHandler;
   }

   public IFluidHandler getFluidHandler() {
      return this.fluidHandler;
   }

   public IEnergyStorage getEnergyHandler() {
      return this.energyHandler;
   }

   public AABB getRenderBoundingBox() {
      if (this.cachedRenderAABB == null) {
         AABB box = new AABB(this.getBlockPos());
         if (this.getBlockState().getBlock() instanceof TaskScreenBlock tsb && tsb.getSize() != 1) {
            this.cachedRenderAABB = box.inflate(tsb.getSize());
         } else {
            this.cachedRenderAABB = box;
         }
      }

      return this.cachedRenderAABB;
   }

   private class TaskEnergyHandler implements IEnergyStorage {
      public int receiveEnergy(int amount, boolean simulate) {
         if (NeoForgeTaskScreenBlockEntity.this.getTask() instanceof ForgeEnergyTask energyTask) {
            TeamData data = NeoForgeTaskScreenBlockEntity.this.getCachedTeamData();
            if (data != null && data.canStartTasks(energyTask.getQuest()) && !data.isCompleted(energyTask)) {
               long space = energyTask.getMaxProgress() - data.getProgress(energyTask);
               long toAdd = Math.min(energyTask.getMaxInput(), Math.min(amount, space));
               if (!simulate) {
                  data.addProgress(energyTask, toAdd);
               }

               return (int)toAdd;
            }
         }

         return 0;
      }

      public int extractEnergy(int amount, boolean simulate) {
         return 0;
      }

      public int getEnergyStored() {
         return NeoForgeTaskScreenBlockEntity.this.getTask() instanceof ForgeEnergyTask energyTask
            ? (int)NeoForgeTaskScreenBlockEntity.this.getCachedTeamData().getProgress(energyTask)
            : 0;
      }

      public int getMaxEnergyStored() {
         return NeoForgeTaskScreenBlockEntity.this.getTask() instanceof ForgeEnergyTask energyTask ? (int)energyTask.getValue() : 0;
      }

      public boolean canExtract() {
         return false;
      }

      public boolean canReceive() {
         return true;
      }
   }

   private class TaskFluidHandler implements IFluidHandler {
      public int getTanks() {
         return 1;
      }

      @NotNull
      public FluidStack getFluidInTank(int tank) {
         return tank == 0 && NeoForgeTaskScreenBlockEntity.this.getTask() instanceof FluidTask fluidTask
            ? new FluidStack(fluidTask.getFluid(), (int)NeoForgeTaskScreenBlockEntity.this.getCachedTeamData().getProgress(fluidTask))
            : FluidStack.EMPTY;
      }

      public int getTankCapacity(int tank) {
         return tank == 0 && NeoForgeTaskScreenBlockEntity.this.getTask() instanceof FluidTask fluidTask ? (int)fluidTask.getMaxProgress() : 0;
      }

      public boolean isFluidValid(int tank, @NotNull FluidStack fluidStack) {
         if (tank == 0 && NeoForgeTaskScreenBlockEntity.this.getTask() instanceof FluidTask fluidTask) {
            FluidStack taskFluidStack = new FluidStack(Holder.direct(fluidTask.getFluid()), 1000, fluidTask.getFluidDataComponentPatch());
            return FluidStack.isSameFluidSameComponents(taskFluidStack, fluidStack);
         } else {
            return false;
         }
      }

      public int fill(FluidStack fluidStack, FluidAction fluidAction) {
         if (NeoForgeTaskScreenBlockEntity.this.getTask() instanceof FluidTask fluidTask && this.isFluidValid(0, fluidStack)) {
            TeamData data = NeoForgeTaskScreenBlockEntity.this.getCachedTeamData();
            if (data != null && data.canStartTasks(fluidTask.getQuest()) && !data.isCompleted(fluidTask)) {
               long space = fluidTask.getMaxProgress() - data.getProgress(fluidTask);
               long toAdd = Math.min(fluidStack.getAmount(), space);
               if (fluidAction.execute()) {
                  data.addProgress(fluidTask, Math.min(fluidStack.getAmount(), toAdd));
               }

               return (int)toAdd;
            }
         }

         return 0;
      }

      @NotNull
      public FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
         return NeoForgeTaskScreenBlockEntity.this.getTask() instanceof FluidTask task && fluidStack.getFluid() == task.getFluid()
            ? this.drain(fluidStack.getAmount(), fluidAction)
            : FluidStack.EMPTY;
      }

      @NotNull
      public FluidStack drain(int maxDrain, FluidAction fluidAction) {
         if (NeoForgeTaskScreenBlockEntity.this.getTask() instanceof FluidTask task) {
            TeamData data = NeoForgeTaskScreenBlockEntity.this.getCachedTeamData();
            if (data != null && data.canStartTasks(task.getQuest()) && !data.isCompleted(task)) {
               long toTake = Math.min(maxDrain, data.getProgress(task));
               if (fluidAction.execute()) {
                  data.addProgress(task, -toTake);
               }

               FluidStack result = new FluidStack(task.getFluid(), (int)toTake);
               result.applyComponents(task.getFluidDataComponents());
               return result;
            }
         }

         return FluidStack.EMPTY;
      }
   }

   private class TaskItemHandler implements IItemHandler {
      public int getSlots() {
         return 2;
      }

      @NotNull
      public ItemStack getStackInSlot(int slot) {
         return NeoForgeTaskScreenBlockEntity.this.getTask() instanceof ItemTask itemTask && slot == 0
            ? itemTask.getItemStack()
               .copyWithCount(
                  (int)Math.min(NeoForgeTaskScreenBlockEntity.this.getCachedTeamData().getProgress(itemTask), itemTask.getItemStack().getMaxStackSize())
               )
            : ItemStack.EMPTY;
      }

      @NotNull
      public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
         TeamData data = NeoForgeTaskScreenBlockEntity.this.getCachedTeamData();
         return NeoForgeTaskScreenBlockEntity.this.getTask() instanceof ItemTask itemTask && data.canStartTasks(itemTask.getQuest())
            ? itemTask.insert(data, stack, simulate)
            : stack;
      }

      @NotNull
      public ItemStack extractItem(int slot, int count, boolean simulate) {
         if (!NeoForgeTaskScreenBlockEntity.this.isInputOnly()
            && NeoForgeTaskScreenBlockEntity.this.getTask() instanceof ItemTask itemTask
            && !ItemMatchingSystem.INSTANCE.isItemFilter(itemTask.getItemStack())) {
            TeamData data = NeoForgeTaskScreenBlockEntity.this.getCachedTeamData();
            if (data != null && data.canStartTasks(itemTask.getQuest()) && !data.isCompleted(itemTask)) {
               int itemsRemoved = (int)Math.min(data.getProgress(itemTask), count);
               if (!simulate) {
                  data.addProgress(itemTask, -itemsRemoved);
               }

               return itemTask.getItemStack().copyWithCount(itemsRemoved);
            }
         }

         return ItemStack.EMPTY;
      }

      public int getSlotLimit(int slot) {
         return NeoForgeTaskScreenBlockEntity.this.getTask() instanceof ItemTask itemTask ? itemTask.getItemStack().getMaxStackSize() : 0;
      }

      public boolean isItemValid(int slot, @NotNull ItemStack stack) {
         return NeoForgeTaskScreenBlockEntity.this.getTask() instanceof ItemTask itemTask && itemTask.test(stack);
      }
   }
}
