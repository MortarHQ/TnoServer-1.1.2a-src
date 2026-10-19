package dev.ftb.mods.ftblibrary.sidebar;

public class SidebarGuiButton {
   private final RegisteredSidebarButton sidebarButton;
   public int x = 0;
   public int y = 0;
   private GridLocation gridLocation;
   private boolean enabled;

   public SidebarGuiButton(GridLocation girdLocation, boolean enabled, RegisteredSidebarButton sidebarButton) {
      this.gridLocation = girdLocation;
      this.sidebarButton = sidebarButton;
      this.enabled = enabled;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public RegisteredSidebarButton getSidebarButton() {
      return this.sidebarButton;
   }

   public GridLocation getGridLocation() {
      return this.gridLocation;
   }

   public void setGridLocation(GridLocation gridLocation) {
      this.gridLocation = gridLocation;
   }

   public void setGridLocation(int x, int y) {
      this.gridLocation = new GridLocation(x, y);
   }

   @Override
   public String toString() {
      return this.sidebarButton.getId().toString();
   }
}
