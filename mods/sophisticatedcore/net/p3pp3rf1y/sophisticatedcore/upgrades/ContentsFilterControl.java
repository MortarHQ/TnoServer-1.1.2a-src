package net.p3pp3rf1y.sophisticatedcore.upgrades;

import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public abstract class ContentsFilterControl extends FilterLogicControl<ContentsFilterLogic, ContentsFilterLogicContainer> {
   protected ContentsFilterControl(
      StorageScreenBase<?> screen,
      Position position,
      ContentsFilterLogicContainer filterLogicContainer,
      int slotsPerRow,
      ButtonDefinition.Toggle<ContentsFilterType> contentsFilterButton,
      FilterLogicControlBase.MatchButton... matchButtons
   ) {
      super(screen, position, filterLogicContainer, slotsPerRow, true, matchButtons);
      this.addChild(new ToggleButton<>(new Position(this.x, this.y), contentsFilterButton, button -> this.updateFilterType(), this.container::getFilterType));
   }

   private void updateFilterType() {
      ContentsFilterType next = this.container.getFilterType().next();
      if (this.container.getPrimaryMatch() == PrimaryMatch.TAGS && next == ContentsFilterType.STORAGE) {
         next = next.next();
      }

      this.container.setFilterType(next);
      boolean slotsEnabled = next != ContentsFilterType.STORAGE;
      this.container.getFilterSlots().forEach(slot -> slot.setEnabled(slotsEnabled));
   }

   @Override
   protected void onTagsMatchSelected() {
      if (this.container.getFilterType() == ContentsFilterType.STORAGE) {
         this.updateFilterType();
      }
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }

   public static class Advanced extends ContentsFilterControl {
      public Advanced(
         StorageScreenBase<?> screen,
         Position position,
         ContentsFilterLogicContainer filterLogicContainer,
         int slotsPerRow,
         ButtonDefinition.Toggle<ContentsFilterType> contentsFilterButton
      ) {
         super(
            screen,
            position,
            filterLogicContainer,
            slotsPerRow,
            contentsFilterButton,
            FilterLogicControlBase.MatchButton.PRIMARY_MATCH,
            FilterLogicControlBase.MatchButton.DURABILITY,
            FilterLogicControlBase.MatchButton.NBT
         );
      }
   }

   public static class Basic extends ContentsFilterControl {
      public Basic(
         StorageScreenBase<?> screen,
         Position position,
         ContentsFilterLogicContainer filterLogicContainer,
         int slotsPerRow,
         ButtonDefinition.Toggle<ContentsFilterType> contentsFilterButton
      ) {
         super(screen, position, filterLogicContainer, slotsPerRow, contentsFilterButton);
      }
   }
}
