package team.lodestar.lodestone.systems.particle.screen;

import java.util.Objects;
import net.minecraft.world.item.ItemStack;

public class ScreenParticleItemStackKey {
   public final boolean isHotbarItem;
   public final boolean isRenderedAfterItem;
   public final ItemStack itemStack;

   public ScreenParticleItemStackKey(boolean isHotbarItem, boolean isRenderedAfterItem, ItemStack itemStack) {
      this.isHotbarItem = isHotbarItem;
      this.isRenderedAfterItem = isRenderedAfterItem;
      this.itemStack = itemStack;
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof ScreenParticleItemStackKey key)
         ? false
         : key.isHotbarItem == this.isHotbarItem && key.isRenderedAfterItem == this.isRenderedAfterItem && key.itemStack == this.itemStack;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.isHotbarItem, this.isRenderedAfterItem, this.itemStack);
   }
}
