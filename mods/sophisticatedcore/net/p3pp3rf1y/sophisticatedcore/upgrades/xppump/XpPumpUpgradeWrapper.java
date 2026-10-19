package net.p3pp3rf1y.sophisticatedcore.upgrades.xppump;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageFluidHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.init.ModFluids;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.XpHelper;

public class XpPumpUpgradeWrapper extends UpgradeWrapperBase<XpPumpUpgradeWrapper, XpPumpUpgradeItem> implements ITickableUpgrade {
   private static final int DEFAULT_LEVEL = 10;
   private static final int COOLDOWN = 5;
   private static final int ALL_LEVELS = 10000;
   private static final int PLAYER_SEARCH_RANGE = 3;
   private final XpPumpUpgradeConfig xpPumpUpgradeConfig = this.upgradeItem.getXpPumpUpgradeConfig();

   protected XpPumpUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!this.isInCooldown(level)) {
         if (entity instanceof Player player) {
            this.interactWithPlayer(player);
            this.mendItems(player);
         } else {
            AABB searchBox = new AABB(pos).inflate(3.0);

            for (Player player : level.players()) {
               if (searchBox.contains(player.getX(), player.getY(), player.getZ())) {
                  this.interactWithPlayer(player);
                  this.mendItems(player);
               }
            }
         }

         this.setCooldown(level, 5);
      }
   }

   private void mendItems(Player player) {
      if (!Boolean.FALSE.equals(this.xpPumpUpgradeConfig.mendingOn.get()) && this.shouldMendItems()) {
         this.getRandomDamagedItemWithMending(player)
            .ifPresent(
               itemStack -> {
                  if (!itemStack.isEmpty() && itemStack.isDamaged() && itemStack.getXpRepairRatio() > 0.0F) {
                     float xpToTryDrain = Math.min(
                        ((Integer)this.xpPumpUpgradeConfig.maxXpPointsPerMending.get()).intValue(), itemStack.getDamageValue() / itemStack.getXpRepairRatio()
                     );
                     if (xpToTryDrain > 0.0F) {
                        this.storageWrapper.getFluidHandler().ifPresent(fluidHandler -> {
                           FluidStack drained = fluidHandler.drain(
                              ModFluids.EXPERIENCE_TAG, XpHelper.experienceToLiquid(xpToTryDrain), FluidAction.EXECUTE, false
                           );
                           float xpDrained = XpHelper.liquidToExperience(drained.getAmount());
                           int durabilityToRepair = (int)(xpDrained * itemStack.getXpRepairRatio());
                           itemStack.setDamageValue(itemStack.getDamageValue() - durabilityToRepair);
                        });
                     }
                  }
               }
            );
      }
   }

   private Optional<ItemStack> getRandomDamagedItemWithMending(Player player) {
      List<ItemStack> matchingItems = new ArrayList<>();

      for (IItemHandler handler : InventoryHelper.getEquipmentItemHandlersFromPlayer(player)) {
         for (int slot = 0; slot < handler.getSlots(); slot++) {
            ItemStack itemStack = handler.getStackInSlot(slot);
            if (itemStack.isDamaged()) {
               ItemEnchantments enchantments = (ItemEnchantments)itemStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);

               for (Entry<Holder<Enchantment>> enchantmentEntry : enchantments.entrySet()) {
                  Holder<Enchantment> enchantmentHolder = (Holder<Enchantment>)enchantmentEntry.getKey();
                  Enchantment enchantment = (Enchantment)enchantmentHolder.value();
                  if (enchantment.effects().has(EnchantmentEffectComponents.REPAIR_WITH_XP)) {
                     matchingItems.add(itemStack);
                  }
               }
            }
         }
      }

      return Util.getRandomSafe(matchingItems, player.getRandom());
   }

   private void interactWithPlayer(Player player) {
      this.storageWrapper
         .getFluidHandler()
         .ifPresent(
            fluidHandler -> {
               int level = this.getLevel();
               AutomationDirection direction = this.getDirection();
               if (direction != AutomationDirection.OFF) {
                  if (direction != AutomationDirection.INPUT && direction != AutomationDirection.KEEP
                     || level >= player.experienceLevel && (level != player.experienceLevel || !(player.experienceProgress > 0.0F))) {
                     if ((direction == AutomationDirection.OUTPUT || direction == AutomationDirection.KEEP) && level > player.experienceLevel) {
                        this.tryGivePlayerExperienceFromTank(player, fluidHandler, level, false);
                     }
                  } else {
                     this.tryFillTankWithPlayerExperience(player, fluidHandler, level, false);
                  }
               }
            }
         );
   }

   private void tryGivePlayerExperienceFromTank(Player player, IStorageFluidHandler fluidHandler, int stopAtLevel) {
      this.tryGivePlayerExperienceFromTank(player, fluidHandler, stopAtLevel, true);
   }

   private void tryGivePlayerExperienceFromTank(Player player, IStorageFluidHandler fluidHandler, int stopAtLevel, boolean ignoreInOutLimit) {
      int maxXpPointsToGive = XpHelper.getExperienceForLevel(stopAtLevel) - XpHelper.getPlayerTotalExperience(player);
      FluidStack drained = fluidHandler.drain(ModFluids.EXPERIENCE_TAG, XpHelper.experienceToLiquid(maxXpPointsToGive), FluidAction.EXECUTE, ignoreInOutLimit);
      if (!drained.isEmpty()) {
         player.giveExperiencePoints((int)XpHelper.liquidToExperience(drained.getAmount()));
      }
   }

   private void tryFillTankWithPlayerExperience(Player player, IStorageFluidHandler fluidHandler, int stopAtLevel) {
      this.tryFillTankWithPlayerExperience(player, fluidHandler, stopAtLevel, true);
   }

   private void tryFillTankWithPlayerExperience(Player player, IStorageFluidHandler fluidHandler, int stopAtLevel, boolean ignoreInOutLimit) {
      int maxXpPointsToTake = XpHelper.getPlayerTotalExperience(player) - XpHelper.getExperienceForLevel(stopAtLevel);
      int filled = fluidHandler.fill(
         ModFluids.EXPERIENCE_TAG, XpHelper.experienceToLiquid(maxXpPointsToTake), (Fluid)ModFluids.XP_STILL.get(), FluidAction.EXECUTE, ignoreInOutLimit
      );
      if (filled > 0) {
         player.giveExperiencePoints((int)(-XpHelper.liquidToExperience(filled)));
      }
   }

   public void takeLevelsFromPlayer(Player player) {
      this.storageWrapper
         .getFluidHandler()
         .ifPresent(fluidHandler -> this.tryFillTankWithPlayerExperience(player, fluidHandler, Math.max(player.experienceLevel - this.getLevelsToStore(), 0)));
   }

   public void takeAllExperienceFromPlayer(Player player) {
      this.storageWrapper.getFluidHandler().ifPresent(fluidHandler -> this.tryFillTankWithPlayerExperience(player, fluidHandler, 0));
   }

   public void giveLevelsToPlayer(Player player) {
      this.storageWrapper
         .getFluidHandler()
         .ifPresent(fluidHandler -> this.tryGivePlayerExperienceFromTank(player, fluidHandler, player.experienceLevel + this.getLevelsToTake()));
   }

   public void giveAllExperienceToPlayer(Player player) {
      this.storageWrapper.getFluidHandler().ifPresent(fluidHandler -> this.tryGivePlayerExperienceFromTank(player, fluidHandler, 10000));
   }

   public AutomationDirection getDirection() {
      return (AutomationDirection)this.upgrade.getOrDefault(ModCoreDataComponents.AUTOMATION_DIRECTION, AutomationDirection.INPUT);
   }

   public void setDirection(AutomationDirection direction) {
      this.upgrade.set(ModCoreDataComponents.AUTOMATION_DIRECTION, direction);
      this.save();
   }

   public void setLevel(int level) {
      this.upgrade.set(ModCoreDataComponents.LEVEL, level);
      this.save();
   }

   public int getLevel() {
      return (Integer)this.upgrade.getOrDefault(ModCoreDataComponents.LEVEL, 10);
   }

   public void setLevelsToStore(int levelsToStore) {
      this.upgrade.set(ModCoreDataComponents.LEVELS_TO_STORE, levelsToStore);
      this.save();
   }

   public int getLevelsToStore() {
      return (Integer)this.upgrade.getOrDefault(ModCoreDataComponents.LEVELS_TO_STORE, 1);
   }

   public void setLevelsToTake(int levelsToTake) {
      this.upgrade.set(ModCoreDataComponents.LEVELS_TO_TAKE, levelsToTake);
      this.save();
   }

   public int getLevelsToTake() {
      return (Integer)this.upgrade.getOrDefault(ModCoreDataComponents.LEVELS_TO_TAKE, 1);
   }

   public boolean shouldMendItems() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.MEND_ITEMS, true);
   }

   public void setMendItems(boolean mendItems) {
      this.upgrade.set(ModCoreDataComponents.MEND_ITEMS, mendItems);
      this.save();
   }
}
