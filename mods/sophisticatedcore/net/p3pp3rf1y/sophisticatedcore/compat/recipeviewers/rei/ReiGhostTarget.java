package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei;

import me.shedaniel.rei.api.client.gui.drag.DraggableStackVisitor.BoundsProvider;
import net.minecraft.world.phys.AABB;

public abstract class ReiGhostTarget implements BoundsProvider {
   public abstract void accept();

   public boolean contains(int x, int y) {
      AABB box = this.bounds().bounds();
      return box.contains(x, y, box.minZ);
   }
}
