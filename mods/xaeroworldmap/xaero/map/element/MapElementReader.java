package xaero.map.element;

import xaero.map.element.render.ElementReader;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.element.render.ElementRenderer;

public abstract class MapElementReader<E, C, R extends ElementRenderer<E, ?, R>> extends ElementReader<E, C, R> {
   @Deprecated
   public boolean isInteractable(int location, E element) {
      return super.isInteractable(ElementRenderLocation.fromIndex(location), element);
   }

   @Deprecated
   public float getBoxScale(int location, E element, C context) {
      return super.getBoxScale(ElementRenderLocation.fromIndex(location), element, context);
   }

   @Deprecated
   public boolean isHoveredOnMap(
      int location, E element, double mouseX, double mouseZ, double scale, double screenSizeBasedScale, double rendererDimDiv, C context, float partialTicks
   ) {
      return super.isHoveredOnMap(
         ElementRenderLocation.fromIndex(location), element, mouseX, mouseZ, scale, screenSizeBasedScale, rendererDimDiv, context, partialTicks
      );
   }

   @Override
   public boolean isInteractable(ElementRenderLocation location, E element) {
      return this.isInteractable(location.getIndex(), element);
   }

   @Override
   public float getBoxScale(ElementRenderLocation location, E element, C context) {
      return this.getBoxScale(location.getIndex(), element, context);
   }

   @Override
   public boolean isHoveredOnMap(
      ElementRenderLocation location,
      E element,
      double mouseX,
      double mouseZ,
      double scale,
      double screenSizeBasedScale,
      double rendererDimDiv,
      C context,
      float partialTicks
   ) {
      return this.isHoveredOnMap(location.getIndex(), element, mouseX, mouseZ, scale, screenSizeBasedScale, rendererDimDiv, context, partialTicks);
   }
}
