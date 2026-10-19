package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.worldgen.decorators.FruitDecorator;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import java.lang.reflect.Constructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ModTreeDecoratorTypes {
   public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create("chronodawn", Registries.TREE_DECORATOR_TYPE);
   public static final RegistrySupplier<TreeDecoratorType<FruitDecorator>> FRUIT_DECORATOR = TREE_DECORATOR_TYPES.register("fruit_decorator", () -> {
      try {
         Constructor<TreeDecoratorType> constructor = TreeDecoratorType.class.getDeclaredConstructor(MapCodec.class);
         constructor.setAccessible(true);
         return constructor.newInstance(FruitDecorator.CODEC);
      } catch (Exception e) {
         ChronoDawn.LOGGER.error("Failed to create FruitDecorator TreeDecoratorType", e);
         throw new RuntimeException("Failed to create FruitDecorator TreeDecoratorType", e);
      }
   });

   public static void register() {
      TREE_DECORATOR_TYPES.register();
      ChronoDawn.LOGGER.debug("Registered ModTreeDecoratorTypes");
   }
}
