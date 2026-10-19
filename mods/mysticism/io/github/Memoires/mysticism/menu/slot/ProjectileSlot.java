package io.github.Memoires.mysticism.menu.slot;

import io.github.Memoires.mysticism.data.MysticismItemTags;
import io.github.manasmods.tensura.menu.slot.SpatialSlot;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class ProjectileSlot extends SpatialSlot {
   public ProjectileSlot(Container container, int index, int x, int y) {
      super(container, index, x, y);
   }

   public boolean mayPlace(ItemStack stack) {
      return stack.is(MysticismItemTags.KAMUI_PROJECTILE);
   }
}
