package net.p3pp3rf1y.sophisticatedcore.upgrades.pump;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.wrappers.BucketPickupHandlerWrapper;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.CapabilityHelper;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

public class PumpUpgradeWrapper extends UpgradeWrapperBase<PumpUpgradeWrapper, PumpUpgradeItem> implements ITickableUpgrade {
   private static final int DID_NOTHING_COOLDOWN_TIME = 40;
   private static final int HAND_INTERACTION_COOLDOWN_TIME = 3;
   private static final int WORLD_INTERACTION_COOLDOWN_TIME = 20;
   private static final int FLUID_HANDLER_INTERACTION_COOLDOWN_TIME = 20;
   private static final int PLAYER_SEARCH_RANGE = 3;
   private static final int PUMP_IN_WORLD_RANGE = 4;
   private static final int PUMP_IN_WORLD_RANGE_SQR = 16;
   private long lastHandActionTime = -1L;
   private final FluidFilterLogic fluidFilterLogic;
   private final PumpUpgradeConfig pumpUpgradeConfig = this.upgradeItem.getPumpUpgradeConfig();

   protected PumpUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.fluidFilterLogic = new FluidFilterLogic((Integer)this.pumpUpgradeConfig.filterSlots.get(), upgrade, upgradeSaveHandler);
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!this.isInCooldown(level)) {
         this.setCooldown(
            level, this.storageWrapper.getFluidHandler().map(storageFluidHandler -> this.tick(storageFluidHandler, entity, level, pos)).orElse(40)
         );
      }
   }

   private int tick(IFluidHandlerItem storageFluidHandler, @Nullable Entity entity, Level level, BlockPos pos) {
      if (this.shouldInteractWithHand()) {
         if (entity instanceof Player player) {
            if (this.handleFluidContainerInHands(player, storageFluidHandler)) {
               this.lastHandActionTime = level.getGameTime();
               return 3;
            }
         } else if (this.handleFluidContainersInHandsOfNearbyPlayers(level, pos, storageFluidHandler)) {
            this.lastHandActionTime = level.getGameTime();
            return 3;
         }
      }

      return this.handleInWorldInteractions(storageFluidHandler, entity, level, pos)
         .orElseGet(() -> this.lastHandActionTime + 30L > level.getGameTime() ? 3 : 40);
   }

   private Optional<Integer> handleInWorldInteractions(IFluidHandlerItem storageFluidHandler, @Nullable Entity entity, Level level, BlockPos pos) {
      if (this.shouldInteractWithWorld()) {
         Optional<Integer> newCooldown = this.interactWithWorld(level, pos, storageFluidHandler, entity);
         if (newCooldown.isPresent()) {
            return newCooldown;
         }
      }

      return this.shouldInteractWithFluidHandlers() ? this.interactWithAttachedFluidHandlers(level, pos, storageFluidHandler) : Optional.empty();
   }

   private Optional<Integer> interactWithAttachedFluidHandlers(Level level, BlockPos pos, IFluidHandler storageFluidHandler) {
      for (Direction dir : Direction.values()) {
         boolean successful = WorldHelper.getBlockEntity(level, pos.offset(dir.getNormal()))
            .map(
               be -> CapabilityHelper.getFromFluidHandler(
                  be,
                  dir.getOpposite(),
                  fluidHandler -> this.isInput()
                     ? this.fillFromFluidHandler(fluidHandler, storageFluidHandler, this.getMaxInOut())
                     : this.fillFluidHandler(fluidHandler, storageFluidHandler, this.getMaxInOut()),
                  false
               )
            )
            .orElse(false);
         if (successful) {
            return Optional.of(20);
         }
      }

      return Optional.empty();
   }

   private int getMaxInOut() {
      return Math.max(
         1000,
         (Integer)this.pumpUpgradeConfig.maxInputOutput.get()
            * this.storageWrapper.getNumberOfSlotRows()
            * this.getAdjustedStackMultiplier(this.storageWrapper)
      );
   }

   public int getAdjustedStackMultiplier(IStorageWrapper storageWrapper) {
      return 1 + (int)((Double)this.pumpUpgradeConfig.stackMultiplierRatio.get() * (storageWrapper.getInventoryHandler().getStackSizeMultiplier() - 1.0));
   }

   private Optional<Integer> interactWithWorld(Level level, BlockPos pos, IFluidHandler storageFluidHandler, @Nullable Entity entity) {
      if (this.isInput()) {
         return this.fillFromBlockInRange(level, pos, storageFluidHandler, entity);
      }

      for (Direction dir : Direction.values()) {
         BlockPos offsetPos = pos.offset(dir.getNormal());
         if (this.placeFluidInWorld(level, storageFluidHandler, dir, offsetPos)) {
            return Optional.of(20);
         }
      }

      return Optional.empty();
   }

   private boolean placeFluidInWorld(Level level, IFluidHandler storageFluidHandler, Direction dir, BlockPos offsetPos) {
      if (dir != Direction.UP) {
         for (int tank = 0; tank < storageFluidHandler.getTanks(); tank++) {
            FluidStack tankFluid = storageFluidHandler.getFluidInTank(tank);
            if (!tankFluid.isEmpty()
               && this.fluidFilterLogic.fluidMatches(tankFluid)
               && this.isValidForFluidPlacement(level, offsetPos)
               && FluidUtil.tryPlaceFluid(null, level, InteractionHand.MAIN_HAND, offsetPos, storageFluidHandler, tankFluid)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isValidForFluidPlacement(Level level, BlockPos offsetPos) {
      BlockState blockState = level.getBlockState(offsetPos);
      return blockState.isAir() || !blockState.getFluidState().isEmpty() && !blockState.getFluidState().isSource();
   }

   private Optional<Integer> fillFromBlockInRange(Level level, BlockPos basePos, IFluidHandler storageFluidHandler, @Nullable Entity entity) {
      LinkedList<BlockPos> nextPositions = new LinkedList<>();
      Set<BlockPos> searchedPositions = new HashSet<>();
      nextPositions.add(basePos);

      while (!nextPositions.isEmpty()) {
         BlockPos pos = nextPositions.poll();
         if (this.fillFromBlock(level, pos, storageFluidHandler, entity)) {
            return Optional.of((int)(Math.max(1.0, Math.sqrt(basePos.distSqr(pos))) * 20.0));
         }

         for (Direction dir : Direction.values()) {
            BlockPos offsetPos = pos.offset(dir.getNormal());
            if (!searchedPositions.contains(offsetPos)) {
               searchedPositions.add(offsetPos);
               if (basePos.distSqr(offsetPos) < 16.0) {
                  nextPositions.add(offsetPos);
               }
            }
         }
      }

      return Optional.empty();
   }

   private boolean fillFromBlock(Level level, BlockPos pos, IFluidHandler storageFluidHandler, @Nullable Entity entity) {
      FluidState fluidState = level.getFluidState(pos);
      if (!fluidState.isEmpty()) {
         BlockState state = level.getBlockState(pos);
         IFluidHandler targetFluidHandler;
         if (state.getBlock() instanceof BucketPickup bucketPickup) {
            targetFluidHandler = new BucketPickupHandlerWrapper(entity instanceof Player player ? player : null, bucketPickup, level, pos);
         } else {
            Optional<IFluidHandler> fluidHandler = FluidUtil.getFluidHandler(level, pos, null);
            if (fluidHandler.isEmpty()) {
               return false;
            }

            targetFluidHandler = fluidHandler.get();
         }

         return this.fillFromFluidHandler(targetFluidHandler, storageFluidHandler);
      } else {
         return false;
      }
   }

   private boolean handleFluidContainersInHandsOfNearbyPlayers(Level level, BlockPos pos, IFluidHandler storageFluidHandler) {
      AABB searchBox = new AABB(pos).inflate(3.0);

      for (Player player : level.players()) {
         if (searchBox.contains(player.getX(), player.getY(), player.getZ()) && this.handleFluidContainerInHands(player, storageFluidHandler)) {
            return true;
         }
      }

      return false;
   }

   private boolean handleFluidContainerInHands(Player player, IFluidHandler storageFluidHandler) {
      return this.handleFluidContainerInHand(storageFluidHandler, player, InteractionHand.MAIN_HAND)
         || this.handleFluidContainerInHand(storageFluidHandler, player, InteractionHand.OFF_HAND);
   }

   private boolean handleFluidContainerInHand(IFluidHandler storageFluidHandler, Player player, InteractionHand hand) {
      ItemStack itemInHand = player.getItemInHand(hand);
      return itemInHand.getCount() == 1 && itemInHand != this.storageWrapper.getWrappedStorageStack()
         ? CapabilityHelper.getFromFluidHandler(
            itemInHand,
            itemFluidHandler -> this.isInput()
               ? this.fillFromHand(player, hand, itemFluidHandler, storageFluidHandler)
               : this.fillContainerInHand(player, hand, itemFluidHandler, storageFluidHandler),
            false
         )
         : false;
   }

   private boolean fillContainerInHand(Player player, InteractionHand hand, IFluidHandlerItem itemFluidHandler, IFluidHandler storageFluidHandler) {
      boolean ret = this.fillFluidHandler(itemFluidHandler, storageFluidHandler);
      if (ret) {
         player.setItemInHand(hand, itemFluidHandler.getContainer());
      }

      return ret;
   }

   private boolean fillFluidHandler(IFluidHandler fluidHandler, IFluidHandler storageFluidHandler) {
      return this.fillFluidHandler(fluidHandler, storageFluidHandler, 1000);
   }

   private boolean fillFluidHandler(IFluidHandler fluidHandler, IFluidHandler storageFluidHandler, int maxFill) {
      boolean ret = false;

      for (int tank = 0; tank < storageFluidHandler.getTanks(); tank++) {
         FluidStack tankFluid = storageFluidHandler.getFluidInTank(tank);
         if (!tankFluid.isEmpty()
            && this.fluidFilterLogic.fluidMatches(tankFluid)
            && !FluidUtil.tryFluidTransfer(fluidHandler, storageFluidHandler, new FluidStack(tankFluid.getFluid(), maxFill), true).isEmpty()) {
            ret = true;
            break;
         }
      }

      return ret;
   }

   private boolean fillFromHand(Player player, InteractionHand hand, IFluidHandlerItem itemFluidHandler, IFluidHandler storageFluidHandler) {
      if (this.fillFromFluidHandler(itemFluidHandler, storageFluidHandler)) {
         player.setItemInHand(hand, itemFluidHandler.getContainer());
         return true;
      } else {
         return false;
      }
   }

   private boolean fillFromFluidHandler(IFluidHandler fluidHandler, IFluidHandler storageFluidHandler) {
      return this.fillFromFluidHandler(fluidHandler, storageFluidHandler, 1000);
   }

   private boolean fillFromFluidHandler(IFluidHandler fluidHandler, IFluidHandler storageFluidHandler, int maxDrain) {
      FluidStack containedFluid = fluidHandler.drain(maxDrain, FluidAction.SIMULATE);
      return !containedFluid.isEmpty() && this.fluidFilterLogic.fluidMatches(containedFluid)
         ? !FluidUtil.tryFluidTransfer(storageFluidHandler, fluidHandler, containedFluid, true).isEmpty()
         : false;
   }

   public void setIsInput(boolean input) {
      this.upgrade.set(ModCoreDataComponents.IS_INPUT, input);
      this.save();
   }

   public boolean isInput() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.IS_INPUT, true);
   }

   public FluidFilterLogic getFluidFilterLogic() {
      return this.fluidFilterLogic;
   }

   public void setInteractWithHand(boolean interactWithHand) {
      this.upgrade.set(ModCoreDataComponents.INTERACT_WITH_HAND, interactWithHand);
      this.save();
   }

   public boolean shouldInteractWithHand() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.INTERACT_WITH_HAND, this.upgradeItem.getInteractWithHandDefault());
   }

   public void setInteractWithWorld(boolean interactWithWorld) {
      this.upgrade.set(ModCoreDataComponents.INTERACT_WITH_WORLD, interactWithWorld);
      this.save();
   }

   public boolean shouldInteractWithWorld() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.INTERACT_WITH_WORLD, this.upgradeItem.getInteractWithWorldDefault());
   }

   public void setInteractWithFluidHandlers(boolean interactWithFluidHandlers) {
      this.upgrade.set(ModCoreDataComponents.INTERACT_WITH_FLUID_HANDLERS, interactWithFluidHandlers);
      this.save();
   }

   public boolean shouldInteractWithFluidHandlers() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.INTERACT_WITH_FLUID_HANDLERS, this.upgradeItem.getInteractWithFluidHandlersDefault());
   }
}
