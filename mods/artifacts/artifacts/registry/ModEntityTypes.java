package artifacts.registry;

import artifacts.Artifacts;
import artifacts.entity.MimicEntity;
import java.util.function.BiConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class ModEntityTypes {
   public static final Register<EntityType<?>> ENTITY_TYPES = Register.create(Registries.ENTITY_TYPE);
   public static final RegistryHolder<EntityType<?>, EntityType<MimicEntity>> MIMIC = ENTITY_TYPES.register(
      "mimic", () -> Builder.of(MimicEntity::new, MobCategory.MISC).sized(0.875F, 0.875F).clientTrackingRange(8).build(Artifacts.id("mimic").toString())
   );

   public static void registerMobAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> consumer) {
      consumer.accept(MIMIC.get(), MimicEntity.createMobAttributes().build());
   }
}
