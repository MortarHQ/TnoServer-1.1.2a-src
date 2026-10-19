package team.lodestar.lodestone.registry.common;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.attribute.LodestoneAttributeBuilder;
import team.lodestar.lodestone.systems.attribute.LodestoneRangedAttribute;

@EventBusSubscriber(bus = Bus.MOD)
public class LodestoneAttributes {
   public static final ResourceLocation BASE_MAGIC_DAMAGE = LodestoneLib.lodestonePath("base_magic_damage");
   public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, "lodestone");
   public static final DeferredHolder<Attribute, Attribute> MAGIC_RESISTANCE = registerAttribute(
      ATTRIBUTES, LodestoneRangedAttribute.create(LodestoneLib.lodestonePath("magic_resistance"), 1.0, 0.0, 2048.0).forcePercentageDisplay().setSyncable(true)
   );
   public static final DeferredHolder<Attribute, Attribute> MAGIC_PROFICIENCY = registerAttribute(
      ATTRIBUTES, LodestoneRangedAttribute.create(LodestoneLib.lodestonePath("magic_proficiency"), 1.0, 0.0, 2048.0).forcePercentageDisplay().setSyncable(true)
   );
   public static final DeferredHolder<Attribute, Attribute> MAGIC_DAMAGE = registerAttribute(
      ATTRIBUTES,
      LodestoneRangedAttribute.create(LodestoneLib.lodestonePath("magic_damage"), 0.0, 0.0, 2048.0).setAsBaseAttribute(BASE_MAGIC_DAMAGE).setSyncable(true)
   );

   public static DeferredHolder<Attribute, Attribute> registerAttribute(DeferredRegister<Attribute> registry, LodestoneAttributeBuilder builder) {
      return registry.register(builder.id.getPath(), builder::build);
   }

   @SubscribeEvent
   public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
      event.getTypes().forEach(e -> {
         event.add(e, MAGIC_RESISTANCE);
         event.add(e, MAGIC_PROFICIENCY);
         event.add(e, MAGIC_DAMAGE);
      });
   }
}
