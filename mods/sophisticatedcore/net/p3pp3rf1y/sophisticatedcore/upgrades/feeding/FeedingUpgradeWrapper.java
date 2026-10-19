package net.p3pp3rf1y.sophisticatedcore.upgrades.feeding;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.event.EventHooks;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IFilteredUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.CapabilityHelper;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public class FeedingUpgradeWrapper extends UpgradeWrapperBase<FeedingUpgradeWrapper, FeedingUpgradeItem> implements ITickableUpgrade, IFilteredUpgrade {
   private static final int COOLDOWN = 100;
   private static final int STILL_HUNGRY_COOLDOWN = 10;
   private static final int FEEDING_RANGE = 3;
   private final FilterLogic filterLogic;

   public FeedingUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.filterLogic = new FilterLogic(
         upgrade, upgradeSaveHandler, this.upgradeItem.getFilterSlotCount(), s -> s.getFoodProperties(null) != null, ModCoreDataComponents.FILTER_ATTRIBUTES
      );
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!this.isInCooldown(level)) {
         boolean hungryPlayer = false;
         if (!(entity instanceof Player)) {
            AtomicBoolean stillHungryPlayer = new AtomicBoolean(false);
            level.getEntities(EntityType.PLAYER, new AABB(pos).inflate(3.0), p -> true)
               .forEach(p -> stillHungryPlayer.set(stillHungryPlayer.get() || this.feedPlayerAndGetHungry(p, level)));
            hungryPlayer = stillHungryPlayer.get();
         } else if (this.feedPlayerAndGetHungry((Player)entity, level)) {
            hungryPlayer = true;
         }

         if (hungryPlayer) {
            this.setCooldown(level, 10);
         } else {
            this.setCooldown(level, 100);
         }
      }
   }

   private boolean feedPlayerAndGetHungry(Player player, Level level) {
      int hungerLevel = 20 - player.getFoodData().getFoodLevel();
      return hungerLevel == 0 ? false : this.tryFeedingFoodFromStorage(level, hungerLevel, player) && player.getFoodData().getFoodLevel() < 20;
   }

   private boolean tryFeedingFoodFromStorage(Level level, int hungerLevel, Player player) {
      ITrackedContentsItemHandler inventory = this.storageWrapper.getInventoryForUpgradeProcessing();
      return InventoryHelper.iterate(
         inventory, (slot, stack) -> this.tryFeedingStack(level, hungerLevel, player, slot, stack, inventory), () -> false, ret -> ret
      );
   }

   private boolean tryFeedingStack(Level level, int hungerLevel, Player player, Integer slot, ItemStack stack, ITrackedContentsItemHandler inventory) {
      boolean isHurt = player.getHealth() < player.getMaxHealth() - 0.1F;
      if (isEdible(stack, player)
         && this.filterLogic.matchesFilter(stack)
         && (this.isHungryEnoughForFood(hungerLevel, stack, player) || this.shouldFeedImmediatelyWhenHurt() && hungerLevel > 0 && isHurt)) {
         ItemStack mainHandItem = player.getMainHandItem();
         player.getInventory().items.set(player.getInventory().selected, stack);
         ItemStack singleItemCopy = stack.copy();
         singleItemCopy.setCount(1);
         if (singleItemCopy.use(level, player, InteractionHand.MAIN_HAND).getResult() == InteractionResult.CONSUME) {
            stack.shrink(1);
            inventory.setStackInSlot(slot, stack);
            ItemStack resultItem = EventHooks.onItemUseFinish(
               player, singleItemCopy.copy(), 0, singleItemCopy.getItem().finishUsingItem(singleItemCopy, level, player)
            );
            if (!resultItem.isEmpty()) {
               ItemStack insertResult = inventory.insertItem(resultItem, false);
               if (!insertResult.isEmpty()) {
                  CapabilityHelper.runOnCapability(
                     player, ItemHandler.ENTITY, null, playerInventory -> InventoryHelper.insertOrDropItem(player, insertResult, playerInventory)
                  );
               }
            }

            player.getInventory().items.set(player.getInventory().selected, mainHandItem);
            return true;
         }

         player.getInventory().items.set(player.getInventory().selected, mainHandItem);
      }

      return false;
   }

   private static boolean isEdible(ItemStack stack, LivingEntity player) {
      if (stack.getItem() == Items.OMINOUS_BOTTLE) {
         return false;
      }

      FoodProperties foodProperties = stack.getItem().getFoodProperties(stack, player);
      return foodProperties != null && foodProperties.nutrition() >= 1;
   }

   private boolean isHungryEnoughForFood(int hungerLevel, ItemStack stack, Player player) {
      FoodProperties foodProperties = stack.getItem().getFoodProperties(stack, player);
      if (foodProperties == null) {
         return false;
      }

      HungerLevel feedAtHungerLevel = this.getFeedAtHungerLevel();
      if (feedAtHungerLevel == HungerLevel.ANY) {
         return true;
      }

      int nutrition = foodProperties.nutrition();
      return (feedAtHungerLevel == HungerLevel.HALF ? nutrition / 2 : nutrition) <= hungerLevel;
   }

   @Override
   public FilterLogic getFilterLogic() {
      return this.filterLogic;
   }

   public HungerLevel getFeedAtHungerLevel() {
      return (HungerLevel)this.upgrade.getOrDefault(ModCoreDataComponents.FEED_AT_HUNGER_LEVEL, HungerLevel.HALF);
   }

   public void setFeedAtHungerLevel(HungerLevel hungerLevel) {
      this.upgrade.set(ModCoreDataComponents.FEED_AT_HUNGER_LEVEL, hungerLevel);
      this.save();
   }

   public boolean shouldFeedImmediatelyWhenHurt() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.FEED_IMMEDIATELY_WHEN_HURT, true);
   }

   public void setFeedImmediatelyWhenHurt(boolean feedImmediatelyWhenHurt) {
      this.upgrade.set(ModCoreDataComponents.FEED_IMMEDIATELY_WHEN_HURT, feedImmediatelyWhenHurt);
      this.save();
   }
}
