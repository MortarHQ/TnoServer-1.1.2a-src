package xaero.map.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;

public class MenuScrollReader extends MenuOnlyElementReader<MapElementMenuScroll> {
   public int getLeftSideLength(MapElementMenuScroll element, Minecraft mc) {
      return 9 + mc.font.width(I18n.get(element.getName(), new Object[0]));
   }

   public String getMenuName(MapElementMenuScroll element) {
      return I18n.get(element.getName(), new Object[0]);
   }

   public String getFilterName(MapElementMenuScroll element) {
      return this.getMenuName(element);
   }

   public int getMenuTextFillLeftPadding(MapElementMenuScroll element) {
      return 0;
   }

   public int getRightClickTitleBackgroundColor(MapElementMenuScroll element) {
      return 0;
   }

   @Override
   public boolean shouldScaleBoxWithOptionalScale() {
      return false;
   }
}
