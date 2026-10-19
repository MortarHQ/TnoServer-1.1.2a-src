package cc.sighs.oelib.registry.action;

import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public record CreativeTabAppendStackAction(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> item) implements RegistrationAction {
   public CreativeTabAppendStackAction {
      Objects.requireNonNull(tab, "tab");
      Objects.requireNonNull(item, "item");
   }
}
