package xaero.map.element;

import net.minecraft.client.Minecraft;

public class MenuHitboxReader extends MenuOnlyElementReader<MapElementMenuHitbox> {
   public int getLeftSideLength(MapElementMenuHitbox element, Minecraft mc) {
      return 0;
   }

   public boolean isMouseOverMenuElement(MapElementMenuHitbox element, int menuX, int menuY, int mouseX, int mouseY, Minecraft mc) {
      int hitboxMinX = menuX + element.getX();
      int hitboxMinY = menuY + element.getY();
      int hitboxMaxX = hitboxMinX + element.getW();
      int hitboxMaxY = hitboxMinY + element.getH();
      return mouseX >= hitboxMinX && mouseX < hitboxMaxX && mouseY >= hitboxMinY && mouseY < hitboxMaxY;
   }

   public String getMenuName(MapElementMenuHitbox element) {
      return "";
   }

   public int getMenuTextFillLeftPadding(MapElementMenuHitbox element) {
      return 0;
   }

   public String getFilterName(MapElementMenuHitbox element) {
      return this.getMenuName(element);
   }

   public int getRightClickTitleBackgroundColor(MapElementMenuHitbox element) {
      return 0;
   }

   @Override
   public boolean shouldScaleBoxWithOptionalScale() {
      return false;
   }
}
