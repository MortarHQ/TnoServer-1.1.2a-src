package org.popcraft.chunky.shape;

import java.util.List;
import org.popcraft.chunky.Selection;
import org.popcraft.chunky.platform.util.Vector2;

public abstract class AbstractPolygon extends AbstractShape {
   protected AbstractPolygon(Selection selection, boolean chunkAligned) {
      super(selection, chunkAligned);
   }

   public abstract List<Vector2> points();
}
