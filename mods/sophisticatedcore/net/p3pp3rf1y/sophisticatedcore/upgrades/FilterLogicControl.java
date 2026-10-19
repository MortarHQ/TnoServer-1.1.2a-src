package net.p3pp3rf1y.sophisticatedcore.upgrades;

import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public class FilterLogicControl<L extends FilterLogic, C extends FilterLogicContainer<L>>
   extends FilterLogicControlBase<L, FilterLogicContainer.FilterLogicSlot, C> {
   public FilterLogicControl(
      StorageScreenBase<?> screen, Position position, C filterLogicContainer, int slotsPerRow, FilterLogicControlBase.MatchButton... showMatchButtons
   ) {
      this(screen, position, filterLogicContainer, slotsPerRow, showMatchButtons.length > 0, showMatchButtons);
   }

   protected FilterLogicControl(
      StorageScreenBase<?> screen,
      Position position,
      C filterLogicContainer,
      int slotsPerRow,
      boolean buttonsVisible,
      FilterLogicControlBase.MatchButton... showMatchButtons
   ) {
      super(screen, filterLogicContainer, position, buttonsVisible, slotsPerRow, showMatchButtons);
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }

   public static class Advanced extends FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>> {
      public Advanced(StorageScreenBase<?> screen, Position position, FilterLogicContainer<FilterLogic> filterLogicContainer, int slotsPerRow) {
         super(
            screen,
            position,
            filterLogicContainer,
            slotsPerRow,
            FilterLogicControlBase.MatchButton.ALLOW_LIST,
            FilterLogicControlBase.MatchButton.PRIMARY_MATCH,
            FilterLogicControlBase.MatchButton.DURABILITY,
            FilterLogicControlBase.MatchButton.NBT
         );
      }
   }

   public static class Basic extends FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>> {
      public Basic(StorageScreenBase<?> screen, Position position, FilterLogicContainer<FilterLogic> filterLogicContainer, int slotsPerRow) {
         super(screen, position, filterLogicContainer, slotsPerRow, FilterLogicControlBase.MatchButton.ALLOW_LIST);
      }
   }
}
