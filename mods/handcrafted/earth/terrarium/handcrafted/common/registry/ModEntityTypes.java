package earth.terrarium.handcrafted.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.handcrafted.common.entities.FancyPainting;
import earth.terrarium.handcrafted.common.entities.Seat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;

public class ModEntityTypes {
   public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, "handcrafted");
   public static final RegistryEntry<EntityType<Seat>> SEAT = ENTITY_TYPES.register(
      "seat", () -> Builder.of(Seat::new, MobCategory.MISC).sized(1.0F, 1.0F).noSave().fireImmune().noSummon().build("seat")
   );
   public static final RegistryEntry<EntityType<FancyPainting>> FANCY_PAINTING = ENTITY_TYPES.register(
      "fancy_painting",
      () -> Builder.of(FancyPainting::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("handcrafted")
   );
}
