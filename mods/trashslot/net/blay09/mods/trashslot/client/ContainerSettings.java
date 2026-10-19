package net.blay09.mods.trashslot.client;

public class ContainerSettings {
   public static final ContainerSettings NONE = new ContainerSettings() {
      @Override
      public void setEnabled(boolean isEnabled) {
      }

      @Override
      public void setLocked(boolean isLocked) {
      }
   };
   private int slotX;
   private int slotY;
   private float anchorX;
   private float anchorY;
   private boolean isEnabled;
   private boolean isLocked;

   public ContainerSettings() {
   }

   public ContainerSettings(int slotX, int slotY, float anchorX, float anchorY, boolean isEnabled) {
      this.slotX = slotX;
      this.slotY = slotY;
      this.anchorX = anchorX;
      this.anchorY = anchorY;
      this.isEnabled = isEnabled;
   }

   public boolean isEnabled() {
      return this.isEnabled;
   }

   public void setEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
   }

   public boolean isLocked() {
      return this.isLocked;
   }

   public void setLocked(boolean isLocked) {
      this.isLocked = isLocked;
   }

   public int getSlotX() {
      return this.slotX;
   }

   public void setSlotX(int slotX) {
      this.slotX = slotX;
   }

   public int getSlotY() {
      return this.slotY;
   }

   public void setSlotY(int slotY) {
      this.slotY = slotY;
   }

   public float getAnchorX() {
      return this.anchorX;
   }

   public void setAnchorX(float anchorX) {
      this.anchorX = anchorX;
   }

   public float getAnchorY() {
      return this.anchorY;
   }

   public void setAnchorY(float anchorY) {
      this.anchorY = anchorY;
   }
}
