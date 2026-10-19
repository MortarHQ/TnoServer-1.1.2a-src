package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseAttributes {
   public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, "block_factorys_bosses");
   public static final DeferredHolder<Attribute, Attribute> ROLL_COUNT = REGISTRY.register(
      "roll_count", () -> new RangedAttribute("attribute.block_factorys_bosses.roll_count", 0.0, 0.0, 9.0).setSyncable(true)
   );

   @SubscribeEvent
   public static void addAttributes(EntityAttributeModificationEvent event) {
      event.add(EntityType.PLAYER, ROLL_COUNT);
   }
}
