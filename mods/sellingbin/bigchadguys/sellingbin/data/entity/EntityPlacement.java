package bigchadguys.sellingbin.data.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelWriter;

public interface EntityPlacement<T> extends EntityPredicate {
   boolean isSubsetOf(T var1);

   boolean isSubsetOf(Entity var1);

   void fillInto(T var1);

   void place(LevelWriter var1);

   T copy();
}
