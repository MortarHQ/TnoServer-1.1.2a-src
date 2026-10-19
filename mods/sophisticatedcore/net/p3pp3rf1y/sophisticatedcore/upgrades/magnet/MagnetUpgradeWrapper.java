package net.p3pp3rf1y.sophisticatedcore.upgrades.magnet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.level.LevelEvent.Unload;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Pre;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.init.ModFluids;
import net.p3pp3rf1y.sophisticatedcore.inventory.IItemHandlerSimpleInserter;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IContentsFilteredUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IPickupResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.XpHelper;

public class MagnetUpgradeWrapper
   extends UpgradeWrapperBase<MagnetUpgradeWrapper, MagnetUpgradeItem>
   implements IContentsFilteredUpgrade,
   ITickableUpgrade,
   IPickupResponseUpgrade {
   private static final String PREVENT_REMOTE_MOVEMENT = "PreventRemoteMovement";
   private static final String ALLOW_MACHINE_MOVEMENT = "AllowMachineRemoteMovement";
   private static final int COOLDOWN_TICKS = 10;
   private static long nextTickTime = Long.MIN_VALUE;
   private static final int FULL_COOLDOWN_TICKS = 40;
   private final ContentsFilterLogic filterLogic;
   private static final Set<IMagnetPreventionChecker> magnetCheckers = new HashSet<>();

   public static void globalPostTick(Pre event) {
      if (!event.getLevel().isClientSide()) {
         long gameTime = event.getLevel().getGameTime();
         if (gameTime > nextTickTime) {
            nextTickTime = gameTime + 10L;
         }
      }
   }

   public static void onWorldUnload(Unload evt) {
      nextTickTime = Long.MIN_VALUE;
   }

   public static void addMagnetPreventionChecker(IMagnetPreventionChecker checker) {
      magnetCheckers.add(checker);
   }

   public MagnetUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.filterLogic = new ContentsFilterLogic(
         upgrade,
         upgradeSaveHandler,
         this.upgradeItem.getFilterSlotCount(),
         storageWrapper::getInventoryHandler,
         storageWrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class),
         ModCoreDataComponents.FILTER_ATTRIBUTES
      );
   }

   private boolean isInCooldown(Level level, @Nullable Entity entity) {
      return !(entity instanceof Player) ? super.isInCooldown(level) : nextTickTime > level.getGameTime();
   }

   @Override
   public ContentsFilterLogic getFilterLogic() {
      return this.filterLogic;
   }

   @Override
   public ItemStack pickup(Level level, ItemStack stack, boolean simulate) {
      return this.shouldPickupItems() && this.filterLogic.matchesFilter(stack)
         ? this.storageWrapper.getInventoryForUpgradeProcessing().insertItem(stack, simulate)
         : stack;
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!this.isInCooldown(level, entity)) {
         int cooldown = this.shouldPickupItems() ? this.pickupItems(entity, level, pos) : 40;
         if (this.shouldPickupXp() && this.canFillStorageWithXp()) {
            cooldown = Math.min(cooldown, this.pickupXpOrbs(entity, level, pos));
         }

         this.setCooldown(level, cooldown);
      }
   }

   private boolean canFillStorageWithXp() {
      return this.storageWrapper
         .getFluidHandler()
         .map(fluidHandler -> fluidHandler.fill(ModFluids.EXPERIENCE_TAG, 1, (Fluid)ModFluids.XP_STILL.get(), FluidAction.SIMULATE) > 0)
         .orElse(false);
   }

   private int pickupXpOrbs(@Nullable Entity entity, Level level, BlockPos pos) {
      List<ExperienceOrb> xpEntities = level.getEntitiesOfClass(ExperienceOrb.class, new AABB(pos).inflate(this.upgradeItem.getRadius()), e -> true);
      if (xpEntities.isEmpty()) {
         return 10;
      }

      int cooldown = 10;

      for (ExperienceOrb xpOrb : xpEntities) {
         if (xpOrb.isAlive() && !this.canNotPickup(xpOrb, entity) && !this.tryToFillTank(xpOrb, entity, level)) {
            cooldown = 40;
            break;
         }
      }

      return cooldown;
   }

   private boolean tryToFillTank(ExperienceOrb xpOrb, @Nullable Entity entity, Level level) {
      int amountToTransfer = XpHelper.experienceToLiquid(xpOrb.getValue());
      return this.storageWrapper.getFluidHandler().map(fluidHandler -> {
         int amountAdded = fluidHandler.fill(ModFluids.EXPERIENCE_TAG, amountToTransfer, (Fluid)ModFluids.XP_STILL.get(), FluidAction.EXECUTE);
         if (amountAdded > 0) {
            Vec3 pos = xpOrb.position();
            xpOrb.value = 0;
            xpOrb.discard();
            Player player = entity instanceof Player ? (Player)entity : null;
            if (player != null) {
               playXpPickupSound(level, player);
            }

            if (amountToTransfer > amountAdded) {
               level.addFreshEntity(new ExperienceOrb(level, pos.x(), pos.y(), pos.z(), (int)XpHelper.liquidToExperience(amountToTransfer - amountAdded)));
            }

            return true;
         } else {
            return false;
         }
      }).orElse(false);
   }

   private int pickupItems(@Nullable Entity entity, Level level, BlockPos pos) {
      List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(this.upgradeItem.getRadius()), e -> true);
      if (itemEntities.isEmpty()) {
         return 10;
      }

      Player player = entity instanceof Player ? (Player)entity : null;
      int cooldown = 40;

      for (ItemEntity itemEntity : itemEntities) {
         if (itemEntity.isAlive()
            && itemEntity.pickupDelay != 32767
            && this.filterLogic.matchesFilter(itemEntity.getItem())
            && !this.canNotPickup(itemEntity, entity)
            && this.tryToInsertItem(player, itemEntity)) {
            if (player != null) {
               playItemPickupSound(level, player);
            }

            cooldown = 10;
         }
      }

      return cooldown;
   }

   private static void playItemPickupSound(Level level, @Nonnull Player player) {
      level.playSound(
         null,
         player.getX(),
         player.getY(),
         player.getZ(),
         SoundEvents.ITEM_PICKUP,
         SoundSource.PLAYERS,
         0.2F,
         (level.random.nextFloat() - level.random.nextFloat()) * 1.4F + 2.0F
      );
   }

   private static void playXpPickupSound(Level level, @Nonnull Player player) {
      level.playSound(
         null,
         player.getX(),
         player.getY(),
         player.getZ(),
         SoundEvents.EXPERIENCE_ORB_PICKUP,
         SoundSource.PLAYERS,
         0.1F,
         (level.random.nextFloat() - level.random.nextFloat()) * 0.35F + 0.9F
      );
   }

   private boolean isBlockedBySomething(Entity entity) {
      for (IMagnetPreventionChecker checker : magnetCheckers) {
         if (checker.isBlocked(entity)) {
            return true;
         }
      }

      return false;
   }

   private boolean canNotPickup(Entity pickedUpEntity, @Nullable Entity entity) {
      if (this.isBlockedBySomething(pickedUpEntity)) {
         return true;
      }

      CompoundTag data = pickedUpEntity.getPersistentData();
      return entity instanceof Player
         ? data.contains("PreventRemoteMovement")
         : data.contains("PreventRemoteMovement") && !data.contains("AllowMachineRemoteMovement");
   }

   private boolean tryToInsertItem(@Nullable Player player, ItemEntity itemEntity) {
      ItemStack stack = itemEntity.getItem();
      IItemHandlerSimpleInserter inventory = this.storageWrapper.getInventoryForUpgradeProcessing();
      ItemStack remaining = inventory.insertItem(stack, true);
      boolean insertedSomething = false;
      if (remaining.getCount() != stack.getCount()) {
         insertedSomething = true;
         int originalCount = stack.getCount();
         Item item = stack.getItem();
         remaining = inventory.insertItem(stack, false);
         itemEntity.setItem(remaining);
         if (player != null) {
            player.awardStat(Stats.ITEM_PICKED_UP.get(item), originalCount - remaining.getCount());
         }
      }

      return insertedSomething;
   }

   public void setPickupItems(boolean pickupItems) {
      this.upgrade.set(ModCoreDataComponents.PICKUP_ITEMS, pickupItems);
      this.save();
   }

   public boolean shouldPickupItems() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.PICKUP_ITEMS, true);
   }

   public void setPickupXp(boolean pickupXp) {
      this.upgrade.set(ModCoreDataComponents.PICKUP_XP, pickupXp);
      this.save();
   }

   public boolean shouldPickupXp() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.PICKUP_XP, true);
   }
}
