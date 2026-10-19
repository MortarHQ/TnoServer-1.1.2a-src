package twilightforest.item.travellers_gear.modifiers.display;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import twilightforest.client.overlay.display.ItemDisplay;

public record ItemDisplayType(Predicate<ItemStack> validItems, Supplier<ItemDisplay> display, Optional<ResourceLocation> slotTexture) {
}
