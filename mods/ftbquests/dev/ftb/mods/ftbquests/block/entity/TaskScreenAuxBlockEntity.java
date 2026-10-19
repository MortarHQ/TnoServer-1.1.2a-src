package dev.ftb.mods.ftbquests.block.entity;

import dev.ftb.mods.ftbquests.registry.ModBlockEntityTypes;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TaskScreenAuxBlockEntity extends BlockEntity implements ITaskScreen, Nameable {
   @NotNull
   private WeakReference<TaskScreenBlockEntity> coreScreen = new WeakReference<>(null);
   private BlockPos corePosPending;

   public TaskScreenAuxBlockEntity(BlockPos blockPos, BlockState blockState) {
      super((BlockEntityType)ModBlockEntityTypes.AUX_TASK_SCREEN.get(), blockPos, blockState);
   }

   public Component getName() {
      return (Component)this.getCoreScreen().map(s -> s.getBlockState().getBlock().getName()).orElse(Component.literal("Task Screen"));
   }

   @Override
   public Optional<TaskScreenBlockEntity> getCoreScreen() {
      if (this.corePosPending != null && this.level != null) {
         this.level.getBlockEntity(this.corePosPending, (BlockEntityType)ModBlockEntityTypes.CORE_TASK_SCREEN.get()).ifPresentOrElse(core -> {
            this.coreScreen = new WeakReference<>(core);
            this.corePosPending = null;
         }, () -> this.level.destroyBlock(this.getBlockPos(), false, null));
      }

      return Optional.ofNullable(this.coreScreen.get());
   }

   public void setCoreScreen(@NotNull TaskScreenBlockEntity coreScreen) {
      if (this.coreScreen.get() != null) {
         throw new IllegalStateException("coreScreen is already set and can't be changed!");
      }

      this.coreScreen = new WeakReference<>(coreScreen);
      this.setChanged();
   }

   public void loadAdditional(CompoundTag compoundTag, Provider provider) {
      super.loadAdditional(compoundTag, provider);
      this.corePosPending = (BlockPos)NbtUtils.readBlockPos(compoundTag, "CorePos").orElse(null);
   }

   protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
      super.saveAdditional(compoundTag, provider);
      if (this.corePosPending != null) {
         compoundTag.put("CorePos", NbtUtils.writeBlockPos(this.corePosPending));
      } else {
         TaskScreenBlockEntity cs = this.coreScreen.get();
         if (cs != null) {
            compoundTag.put("CorePos", NbtUtils.writeBlockPos(cs.getBlockPos()));
         }
      }
   }

   @Override
   public boolean isIndestructible() {
      return this.getCoreScreen().map(TaskScreenBlockEntity::isIndestructible).orElse(false);
   }

   @Override
   public ItemStack getSkin() {
      return this.getCoreScreen().map(TaskScreenBlockEntity::getSkin).orElse(ItemStack.EMPTY);
   }

   @Override
   public boolean hasPermissionToEdit(Player player) {
      return this.getCoreScreen().map(s -> s.hasPermissionToEdit(player)).orElse(false);
   }

   @NotNull
   @Override
   public UUID getTeamId() {
      return this.getCoreScreen().map(TaskScreenBlockEntity::getTeamId).orElse(Util.NIL_UUID);
   }

   @Override
   public boolean isInputOnly() {
      return this.getCoreScreen().map(TaskScreenBlockEntity::isInputOnly).orElse(false);
   }
}
