package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedcore.common.gui.IServerUpdater;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class ContentsFilterLogicContainer extends FilterLogicContainer<ContentsFilterLogic> {
   private static final String DATA_CONTENTS_FILTER_TYPE = "contentsFilterType";

   public ContentsFilterLogicContainer(Supplier<ContentsFilterLogic> filterLogic, IServerUpdater serverUpdater, Consumer<Slot> addSlot) {
      super(filterLogic, serverUpdater, addSlot);
      if (this.getFilterType() == ContentsFilterType.STORAGE) {
         this.getFilterSlots().forEach(s -> s.setEnabled(false));
      }
   }

   public void setFilterType(ContentsFilterType depositFilterType) {
      this.filterLogic.get().setDepositFilterType(depositFilterType);
      this.sendDataToServer(() -> NBTHelper.putEnumConstant(new CompoundTag(), "contentsFilterType", depositFilterType));
   }

   @Override
   public boolean handlePacket(CompoundTag data) {
      if (this.isDifferentFilterLogicsData(data)) {
         return false;
      }

      if (data.contains("contentsFilterType")) {
         this.setFilterType(ContentsFilterType.fromName(data.getString("contentsFilterType")));
      }

      return super.handlePacket(data);
   }

   public ContentsFilterType getFilterType() {
      return this.filterLogic.get().getFilterType();
   }
}
