package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception;

import java.util.Map;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition.Toggle;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;

public class InceptionUpgradeTab extends UpgradeSettingsTab<InceptionUpgradeContainer> {
   public static final UpgradeContainerType<InceptionUpgradeWrapper, InceptionUpgradeContainer> TYPE = new UpgradeContainerType(InceptionUpgradeContainer::new);
   private static final Toggle<InventoryOrder> INVENTORY_ORDER = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         InventoryOrder.MAIN_FIRST,
         GuiHelper.getButtonStateData(
            new UV(48, 32),
            Dimension.SQUARE_16,
            new Position(1, 1),
            SBPTranslationHelper.INSTANCE.getTranslatedLines(SBPTranslationHelper.INSTANCE.translUpgradeButton("inventory_order_main_first"), null)
         ),
         InventoryOrder.INCEPTED_FIRST,
         GuiHelper.getButtonStateData(
            new UV(64, 32),
            Dimension.SQUARE_16,
            new Position(1, 1),
            SBPTranslationHelper.INSTANCE.getTranslatedLines(SBPTranslationHelper.INSTANCE.translUpgradeButton("inventory_order_incepted_first"), null)
         )
      )
   );

   public InceptionUpgradeTab(InceptionUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
      super(
         upgradeContainer,
         position,
         screen,
         SBPTranslationHelper.INSTANCE.translUpgrade("inception", new Object[0]),
         SBPTranslationHelper.INSTANCE.translUpgradeTooltip("inception")
      );
      this.addHideableChild(
         new ToggleButton(
            new Position(this.x + 3, this.y + 24),
            INVENTORY_ORDER,
            button -> ((InceptionUpgradeContainer)this.getContainer())
               .setInventoryOrder(((InceptionUpgradeContainer)this.getContainer()).getInventoryOrder().next()),
            () -> ((InceptionUpgradeContainer)this.getContainer()).getInventoryOrder()
         )
      );
   }

   protected void moveSlotsToTab() {
   }
}
