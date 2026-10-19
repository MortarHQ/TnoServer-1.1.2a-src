package team.creative.creativecore.common.gui.extension;

import java.util.function.Function;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiControlRect;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.util.math.geo.Rect;

public class GuiExtensionCreator<P extends GuiControl, T extends GuiControl> {
   public final P parent;
   private T extension;
   private boolean lostFocus;

   public GuiExtensionCreator(P parent) {
      this.parent = parent;
   }

   public void toggle(Function<? extends GuiExtensionCreator, T> factory) {
      this.toggle(factory, GuiExtensionCreator.ExtensionDirection.BELOW_OR_ABOVE);
   }

   public void toggle(Function<? extends GuiExtensionCreator, T> factory, GuiExtensionCreator.ExtensionDirection direction) {
      if (this.extension == null) {
         this.open(factory.apply(this), direction);
      } else {
         this.close();
      }
   }

   public void open(T extension) {
      this.open(extension, GuiExtensionCreator.ExtensionDirection.BELOW_OR_ABOVE);
   }

   public void open(T extension, GuiExtensionCreator.ExtensionDirection direction) {
      this.open(extension, this.parent, direction);
   }

   public void open(T extension, GuiControl reference, GuiExtensionCreator.ExtensionDirection direction) {
      this.extension = extension;
      GuiLayer layer = reference.getLayer();
      layer.addHover(extension);
      Rect rect = reference.toLayerRect(new Rect(0.0, 0.0, reference.rect.getWidth(), reference.rect.getHeight()));
      extension.init();
      direction.apply(layer, extension.rect, rect, layer.getContentOffset());
   }

   public T get() {
      return this.extension;
   }

   public void close() {
      if (this.extension != null) {
         this.extension.closed();
         this.parent.getLayer().remove(this.extension);
         this.extension = null;
      }
   }

   public boolean hasLostFocus() {
      return this.lostFocus;
   }

   public boolean hasExtension() {
      return this.extension != null;
   }

   public boolean checkShouldClose() {
      boolean result = this.extension != null && this.lostFocus;
      this.markLostFocus();
      return result;
   }

   public void markLostFocus() {
      this.lostFocus = true;
   }

   public void markKeptFocus() {
      this.lostFocus = false;
   }

   public enum ExtensionDirection {
      BELOW_OR_ABOVE {
         @Override
         public void apply(GuiLayer layer, GuiControlRect extension, Rect creatorRect, int layerOffset) {
            extension.setX((int)creatorRect.minX);
            extension.setY((int)creatorRect.maxY);
            extension.setWidth((int)creatorRect.getWidth(), layer.rect.getWidth() - layerOffset * 2);
            extension.flowX();
            int layerHeight = layer.rect.getHeight() - layerOffset * 2;
            extension.setHeight(extension.getPreferredHeight(layerHeight), layerHeight);
            extension.flowY();
            Rect absolute = layer.getIntegratedParent().toScreenRect(layer, extension.rectCopy());
            Rect screen = Rect.getScreenRect();
            if (absolute.maxY > screen.maxY && absolute.minY - absolute.getHeight() >= screen.minX) {
               extension.setY(extension.getY() - ((int)creatorRect.getHeight() + extension.getHeight()));
            }
         }
      },
      BELOW_OR_ABOVE_ANY_SIZE {
         @Override
         public void apply(GuiLayer layer, GuiControlRect extension, Rect creatorRect, int layerOffset) {
            extension.setX((int)creatorRect.minX);
            extension.setY((int)creatorRect.maxY);
            int layerWidth = layer.rect.getWidth() - layerOffset * 2;
            extension.setWidth(extension.getPreferredWidth(layerWidth), layerWidth);
            extension.flowX();
            int layerHeight = layer.rect.getHeight() - layerOffset * 2;
            extension.setHeight(extension.getPreferredHeight(layerHeight), layerHeight);
            extension.flowY();
            Rect absolute = layer.getIntegratedParent().toScreenRect(layer, extension.rectCopy());
            Rect screen = Rect.getScreenRect();
            if (absolute.maxY > screen.maxY && absolute.minY - absolute.getHeight() >= screen.minX) {
               extension.setY(extension.getY() - ((int)creatorRect.getHeight() + extension.getHeight()));
            }
         }
      },
      RIGHT {
         @Override
         public void apply(GuiLayer layer, GuiControlRect extension, Rect creatorRect, int layerOffset) {
            extension.setX((int)creatorRect.maxX);
            extension.setY((int)creatorRect.minY);
            int layerWidth = layer.rect.getWidth() - layerOffset * 2;
            extension.setWidth(extension.getPreferredWidth(layerWidth), layerWidth);
            extension.flowX();
            int layerHeight = layer.rect.getHeight() - layerOffset * 2;
            extension.setHeight(extension.getPreferredHeight(layerHeight), layerHeight);
            extension.flowY();
            Rect absolute = layer.getIntegratedParent().toScreenRect(layer, extension.rectCopy());
            Rect screen = Rect.getScreenRect();
            if (absolute.maxY > screen.maxY && absolute.minY - absolute.getHeight() >= screen.minX) {
               extension.setY((int)creatorRect.maxY - extension.getHeight());
            }
         }
      };

      public abstract void apply(GuiLayer var1, GuiControlRect var2, Rect var3, int var4);
   }
}
