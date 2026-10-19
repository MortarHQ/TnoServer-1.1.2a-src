package cc.sighs.oelib.dev.example;

import java.util.Optional;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;

public class ExampleItem extends Item {
   public ExampleItem(Properties properties) {
      super(properties);
   }

   public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
      return Optional.of(new RainbowBarComponent());
   }
}
