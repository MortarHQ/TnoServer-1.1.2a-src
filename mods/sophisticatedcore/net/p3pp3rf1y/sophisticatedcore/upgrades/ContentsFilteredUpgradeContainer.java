package net.p3pp3rf1y.sophisticatedcore.upgrades;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;

public class ContentsFilteredUpgradeContainer<W extends IUpgradeWrapper & IContentsFilteredUpgrade>
   extends UpgradeContainerBase<W, ContentsFilteredUpgradeContainer<W>> {
   private final ContentsFilterLogicContainer filterLogicContainer = new ContentsFilterLogicContainer(
      () -> this.upgradeWrapper.getFilterLogic(), this, this.slots::add
   );

   public ContentsFilteredUpgradeContainer(Player player, int containerId, W wrapper, UpgradeContainerType<W, ContentsFilteredUpgradeContainer<W>> type) {
      super(player, containerId, wrapper, type);
   }

   public ContentsFilterLogicContainer getFilterLogicContainer() {
      return this.filterLogicContainer;
   }

   @Override
   public void handlePacket(CompoundTag data) {
      this.filterLogicContainer.handlePacket(data);
   }
}
