package net.p3pp3rf1y.sophisticatedcore.upgrades.feeding;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class FeedingUpgradeContainer extends UpgradeContainerBase<FeedingUpgradeWrapper, FeedingUpgradeContainer> {
   private static final String DATA_HUNGER_LEVEL = "hungerLevel";
   private static final String DATA_FEED_IMMEDIATELY_WHEN_HURT = "feedImmediatelyWhenHurt";
   private final FilterLogicContainer<FilterLogic> filterLogicContainer = new FilterLogicContainer<>(
      () -> this.upgradeWrapper.getFilterLogic(), this, this.slots::add
   );

   public FeedingUpgradeContainer(
      Player player, int containerId, FeedingUpgradeWrapper wrapper, UpgradeContainerType<FeedingUpgradeWrapper, FeedingUpgradeContainer> type
   ) {
      super(player, containerId, wrapper, type);
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("hungerLevel")) {
         this.setFeedAtHungerLevel(HungerLevel.fromName(data.getString("hungerLevel")));
      } else if (data.contains("feedImmediatelyWhenHurt")) {
         this.setFeedImmediatelyWhenHurt(data.getBoolean("feedImmediatelyWhenHurt"));
      }

      this.filterLogicContainer.handlePacket(data);
   }

   public FilterLogicContainer<FilterLogic> getFilterLogicContainer() {
      return this.filterLogicContainer;
   }

   public void setFeedAtHungerLevel(HungerLevel hungerLevel) {
      this.upgradeWrapper.setFeedAtHungerLevel(hungerLevel);
      this.sendDataToServer(() -> NBTHelper.putEnumConstant(new CompoundTag(), "hungerLevel", hungerLevel));
   }

   public HungerLevel getFeedAtHungerLevel() {
      return this.upgradeWrapper.getFeedAtHungerLevel();
   }

   public void setFeedImmediatelyWhenHurt(boolean feedImmediatelyWhenHurt) {
      this.upgradeWrapper.setFeedImmediatelyWhenHurt(feedImmediatelyWhenHurt);
      this.sendBooleanToServer("feedImmediatelyWhenHurt", feedImmediatelyWhenHurt);
   }

   public boolean shouldFeedImmediatelyWhenHurt() {
      return this.upgradeWrapper.shouldFeedImmediatelyWhenHurt();
   }
}
