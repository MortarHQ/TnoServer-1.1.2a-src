package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.deposit;

import java.util.Map;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition.Toggle;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicControl;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicControlBase.MatchButton;

public abstract class DepositFilterLogicControl extends FilterLogicControl<DepositFilterLogic, DepositFilterLogicContainer> {
   public static final Toggle<DepositFilterType> DEPOSIT_FILTER_TYPE = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         DepositFilterType.ALLOW,
         GuiHelper.getButtonStateData(new UV(0, 0), SBPTranslationHelper.INSTANCE.translUpgradeButton("allow"), Dimension.SQUARE_16, new Position(1, 1)),
         DepositFilterType.BLOCK,
         GuiHelper.getButtonStateData(new UV(16, 0), SBPTranslationHelper.INSTANCE.translUpgradeButton("block"), Dimension.SQUARE_16, new Position(1, 1)),
         DepositFilterType.INVENTORY,
         GuiHelper.getButtonStateData(
            new UV(64, 16), SBPTranslationHelper.INSTANCE.translUpgradeButton("deposit_filter_type_inventory"), Dimension.SQUARE_16, new Position(1, 1)
         )
      )
   );

   protected DepositFilterLogicControl(
      StorageScreenBase<?> screen, Position position, DepositFilterLogicContainer filterLogicContainer, int slotsPerRow, MatchButton... matchButtons
   ) {
      super(screen, position, filterLogicContainer, slotsPerRow, true, matchButtons);
      this.addChild(
         new ToggleButton(
            new Position(this.x, this.y),
            DEPOSIT_FILTER_TYPE,
            button -> this.updateDepositFilterType(),
            ((DepositFilterLogicContainer)this.container)::getDepositFilterType
         )
      );
   }

   private void updateDepositFilterType() {
      DepositFilterType next = ((DepositFilterLogicContainer)this.container).getDepositFilterType().next();
      ((DepositFilterLogicContainer)this.container).setDepositFilterType(next);
      ((DepositFilterLogicContainer)this.container).getFilterSlots().forEach(slot -> slot.setEnabled(next != DepositFilterType.INVENTORY));
   }

   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }

   public static class Advanced extends DepositFilterLogicControl {
      public Advanced(StorageScreenBase<?> screen, Position position, DepositFilterLogicContainer filterLogicContainer, int slotsPerRow) {
         super(screen, position, filterLogicContainer, slotsPerRow, MatchButton.PRIMARY_MATCH, MatchButton.DURABILITY, MatchButton.NBT);
      }
   }

   public static class Basic extends DepositFilterLogicControl {
      public Basic(StorageScreenBase<?> screen, Position position, DepositFilterLogicContainer filterLogicContainer, int slotsPerRow) {
         super(screen, position, filterLogicContainer, slotsPerRow);
      }
   }
}
