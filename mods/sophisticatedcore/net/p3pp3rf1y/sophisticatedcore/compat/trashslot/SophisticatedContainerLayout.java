package net.p3pp3rf1y.sophisticatedcore.compat.trashslot;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.blay09.mods.balm.mixin.AbstractContainerScreenAccessor;
import net.blay09.mods.trashslot.api.SlotRenderStyle;
import net.blay09.mods.trashslot.api.Snap;
import net.blay09.mods.trashslot.api.Snap.Type;
import net.blay09.mods.trashslot.client.gui.layout.SimpleGuiContainerLayout;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;

public class SophisticatedContainerLayout extends SimpleGuiContainerLayout {
   public static final SophisticatedContainerLayout INSTANCE = new SophisticatedContainerLayout();
   public static final int PLAYER_INVENTORY_WIDTH = 176;
   public static final int HEIGHT_OF_PLAYER_INVENTORY_STICKING_OUT = 83;

   private SophisticatedContainerLayout() {
      this.setEnabledByDefault();
   }

   public int getDefaultSlotX(AbstractContainerScreen<?> screen) {
      AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor)screen;
      return screen.height - screenAccessor.getImageHeight() > 2 * SlotRenderStyle.LONE.getHeight() ? 88 - SlotRenderStyle.LONE.getWidth() : 88;
   }

   public int getDefaultSlotY(AbstractContainerScreen<?> screen) {
      AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor)screen;
      return screen.height - screenAccessor.getImageHeight() > 2 * SlotRenderStyle.LONE.getHeight()
         ? screenAccessor.getImageHeight() / 2
         : screenAccessor.getImageHeight() / 2 - SlotRenderStyle.LONE.getHeight();
   }

   public List<Snap> getSnaps(AbstractContainerScreen<?> screen, SlotRenderStyle renderStyle) {
      List<Snap> list = Lists.newArrayList();
      AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor)screen;
      list.add(new Snap(Type.HORIZONTAL, 0, screenAccessor.getTopPos()));
      list.add(new Snap(Type.HORIZONTAL, 0, screenAccessor.getTopPos() + screenAccessor.getImageHeight() - renderStyle.getHeight()));
      list.add(new Snap(Type.VERTICAL, screenAccessor.getLeftPos(), 0));
      list.add(new Snap(Type.VERTICAL, screenAccessor.getLeftPos() + screenAccessor.getImageWidth() - renderStyle.getWidth(), 0));
      if (screen instanceof StorageScreenBase<?> storageScreen && this.isWiderScreen(storageScreen)) {
         list.add(new Snap(Type.VERTICAL, this.getPlayerInventoryLeftSnap(storageScreen, screenAccessor), 0));
         list.add(new Snap(Type.VERTICAL, this.getPlayerInventoryLeftSnap(storageScreen, screenAccessor) + 176, 0));
      }

      return list;
   }

   public SlotRenderStyle getSlotRenderStyle(AbstractContainerScreen<?> screen, int slotX, int slotY) {
      if (screen instanceof StorageScreenBase<?> storageScreen) {
         int leftSnap = this.getPlayerInventoryLeftSnap(storageScreen, (AbstractContainerScreenAccessor)screen);
         int rightSnap = leftSnap + 176;
         AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor)screen;
         if (slotX + SlotRenderStyle.LONE.getWidth() == leftSnap) {
            int slotBottom = slotY + SlotRenderStyle.LONE.getHeight();
            if (slotY == screenAccessor.getTopPos()) {
               return SlotRenderStyle.ATTACH_LEFT_TOP;
            }

            if (slotBottom == screenAccessor.getTopPos() + screenAccessor.getImageHeight()) {
               return SlotRenderStyle.ATTACH_LEFT_BOTTOM;
            }

            if (slotY >= screenAccessor.getTopPos() && slotBottom < screenAccessor.getTopPos() + screenAccessor.getImageHeight()) {
               return SlotRenderStyle.ATTACH_LEFT_CENTER;
            }
         }

         if (slotX == rightSnap) {
            int slotBottom = slotY + SlotRenderStyle.LONE.getHeight();
            if (slotY == screenAccessor.getTopPos()) {
               return SlotRenderStyle.ATTACH_RIGHT_TOP;
            }

            if (slotBottom == screenAccessor.getTopPos() + screenAccessor.getImageHeight()) {
               return SlotRenderStyle.ATTACH_RIGHT_BOTTOM;
            }

            if (slotY >= screenAccessor.getTopPos() && slotBottom < screenAccessor.getTopPos() + screenAccessor.getImageHeight()) {
               return SlotRenderStyle.ATTACH_RIGHT_CENTER;
            }
         }

         if (this.isWiderScreen(storageScreen)) {
            int stickingOut = slotY
               + SlotRenderStyle.ATTACH_LEFT_BOTTOM.getRenderHeight()
               - (screenAccessor.getTopPos() + screenAccessor.getImageHeight() - 83 - 1);
            if (stickingOut > 0) {
               if (slotX + SlotRenderStyle.LONE.getWidth() == screenAccessor.getLeftPos()) {
                  if (stickingOut < 5) {
                     return SlotRenderStyle.ATTACH_LEFT_BOTTOM;
                  }

                  return SlotRenderStyle.LONE;
               }

               if (slotX == screenAccessor.getLeftPos() + screenAccessor.getImageWidth()) {
                  if (stickingOut < 5) {
                     return SlotRenderStyle.ATTACH_RIGHT_BOTTOM;
                  }

                  return SlotRenderStyle.LONE;
               }

               if (slotX == leftSnap && slotY == screenAccessor.getTopPos() + screenAccessor.getImageHeight()) {
                  return SlotRenderStyle.ATTACH_BOTTOM_LEFT;
               }

               if (slotX + SlotRenderStyle.ATTACH_BOTTOM_CENTER.getRenderWidth() >= rightSnap
                  && slotY == screenAccessor.getTopPos() + screenAccessor.getImageHeight()) {
                  if (slotX + SlotRenderStyle.ATTACH_BOTTOM_CENTER.getRenderWidth() - rightSnap < 6) {
                     return SlotRenderStyle.ATTACH_BOTTOM_RIGHT;
                  }

                  return SlotRenderStyle.LONE;
               }
            }
         }
      }

      return super.getSlotRenderStyle(screen, slotX, slotY);
   }

   private boolean isWiderScreen(StorageScreenBase<?> storageScreen) {
      return storageScreen.getInventoryLabelX() - 7 - 1 > 0;
   }

   public List<Rect2i> getCollisionAreas(AbstractContainerScreen<?> screen) {
      if (screen instanceof StorageScreenBase<?> storageScreen) {
         List<Rect2i> collisionAreas = new ArrayList<>();
         AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor)screen;
         collisionAreas.add(
            new Rect2i(screenAccessor.getLeftPos(), screenAccessor.getTopPos(), screenAccessor.getImageWidth(), screenAccessor.getImageHeight() - 83)
         );
         collisionAreas.add(
            new Rect2i(this.getPlayerInventoryLeftSnap(storageScreen, screenAccessor), screenAccessor.getTopPos(), 176, screenAccessor.getImageHeight())
         );
         storageScreen.getUpgradeSlotsRectangle().ifPresent(collisionAreas::add);
         collisionAreas.addAll(storageScreen.getUpgradeSettingsControl().getTabRectangles());
         storageScreen.getSortButtonsRectangle().ifPresent(collisionAreas::add);
         return collisionAreas;
      } else {
         this.enableDefaultCollision();
         return super.getCollisionAreas(screen);
      }
   }

   private int getPlayerInventoryLeftSnap(StorageScreenBase<?> storageScreen, AbstractContainerScreenAccessor screenAccessor) {
      return screenAccessor.getLeftPos() + storageScreen.getInventoryLabelX() - 7 - 1;
   }

   public String getContainerId(AbstractContainerScreen<?> screen) {
      return screen.getMenu() instanceof StorageContainerMenuBase<?> storageContainerMenu
         ? "sophisticated_"
            + storageContainerMenu.getStorageWrapper().getStorageType()
            + "_"
            + storageContainerMenu.getNumberOfStorageInventorySlots()
            + "_"
            + storageContainerMenu.getColumnsTaken()
         : super.getContainerId(screen);
   }
}
