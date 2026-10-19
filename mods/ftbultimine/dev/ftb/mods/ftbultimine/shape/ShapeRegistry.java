package dev.ftb.mods.ftbultimine.shape;

import dev.ftb.mods.ftbultimine.FTBUltimine;
import dev.ftb.mods.ftbultimine.api.shape.RegisterShapeEvent;
import dev.ftb.mods.ftbultimine.api.shape.Shape;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jetbrains.annotations.NotNull;

public enum ShapeRegistry implements RegisterShapeEvent.Registry {
   INSTANCE;

   private final List<Shape> shapesList = new CopyOnWriteArrayList<>();
   private Shape defaultShape = null;

   @Override
   public void register(Shape shape) {
      this.shapesList.add(shape);
      if (shape.isDefault()) {
         if (this.defaultShape != null) {
            FTBUltimine.LOGGER.warn("default shape already set to {}! ignoring attempt to make {} default", this.defaultShape.getName(), shape.getName());
         } else {
            this.defaultShape = shape;
         }
      }
   }

   @NotNull
   public Shape getShape(int idx) {
      if (idx < 0) {
         idx += this.shapesList.size();
      } else if (idx >= this.shapesList.size()) {
         idx -= this.shapesList.size();
      }

      return idx >= 0 && idx < this.shapesList.size() ? this.shapesList.get(idx) : this.defaultShape;
   }

   public int shapeCount() {
      return this.shapesList.size();
   }
}
