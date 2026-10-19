package earth.terrarium.handcrafted.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
   public static final ResourcefulRegistry<SoundEvent> SOUND_EVENTS = ResourcefulRegistries.create(BuiltInRegistries.SOUND_EVENT, "handcrafted");
   public static final RegistryEntry<SoundEvent> HAMMER_WOOD = SOUND_EVENTS.register(
      "hammer_wood", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("handcrafted", "hammer_wood"))
   );
   public static final RegistryEntry<SoundEvent> HAMMER_STONE = SOUND_EVENTS.register(
      "hammer_stone", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("handcrafted", "hammer_stone"))
   );
}
