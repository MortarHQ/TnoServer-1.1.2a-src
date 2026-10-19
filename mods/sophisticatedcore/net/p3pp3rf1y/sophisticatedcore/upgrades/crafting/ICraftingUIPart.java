package net.p3pp3rf1y.sophisticatedcore.upgrades.crafting;

import java.util.List;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;

@OnlyIn(Dist.CLIENT)
public interface ICraftingUIPart {
   ICraftingUIPart NOOP = new ICraftingUIPart() {
      @Override
      public void onCraftingSlotsDisplayed(List<Slot> slots) {
      }

      @Override
      public void onCraftingSlotsHidden() {
      }

      @Override
      public int getWidth() {
         return 0;
      }

      @Override
      public void setStorageScreen(StorageScreenBase<?> screen) {
      }
   };

   void onCraftingSlotsDisplayed(List<Slot> var1);

   void onCraftingSlotsHidden();

   int getWidth();

   void setStorageScreen(StorageScreenBase<?> var1);
}
