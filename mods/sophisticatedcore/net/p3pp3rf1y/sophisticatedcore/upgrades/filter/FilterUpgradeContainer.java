package net.p3pp3rf1y.sophisticatedcore.upgrades.filter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class FilterUpgradeContainer extends UpgradeContainerBase<FilterUpgradeWrapper, FilterUpgradeContainer> {
   public static final UpgradeContainerType<FilterUpgradeWrapper, FilterUpgradeContainer> BASIC_TYPE = new UpgradeContainerType<>(FilterUpgradeContainer::new);
   public static final UpgradeContainerType<FilterUpgradeWrapper, FilterUpgradeContainer> ADVANCED_TYPE = new UpgradeContainerType<>(
      FilterUpgradeContainer::new
   );
   private static final String DATA_DIRECTION = "direction";
   private final ContentsFilterLogicContainer filterLogicContainer = new ContentsFilterLogicContainer(
      () -> this.upgradeWrapper.getFilterLogic(), this, this.slots::add
   );

   private FilterUpgradeContainer(
      Player player, int containerId, FilterUpgradeWrapper wrapper, UpgradeContainerType<FilterUpgradeWrapper, FilterUpgradeContainer> type
   ) {
      super(player, containerId, wrapper, type);
   }

   public ContentsFilterLogicContainer getFilterLogicContainer() {
      return this.filterLogicContainer;
   }

   public Direction getDirection() {
      return this.upgradeWrapper.getDirection();
   }

   public void setDirection(Direction direction) {
      this.upgradeWrapper.setDirection(direction);
      this.sendDataToServer(() -> NBTHelper.putEnumConstant(new CompoundTag(), "direction", direction));
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (!this.filterLogicContainer.handlePacket(data)) {
         if (data.contains("direction")) {
            this.setDirection(Direction.fromName(data.getString("direction")));
         }
      }
   }
}
