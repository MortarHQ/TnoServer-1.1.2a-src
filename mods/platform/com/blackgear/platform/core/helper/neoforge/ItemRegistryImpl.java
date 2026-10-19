package com.blackgear.platform.core.helper.neoforge;

import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

public class ItemRegistryImpl {
   public static <T extends Mob> Item createSpawnEgg(Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Properties properties) {
      return new DeferredSpawnEggItem(entity, primaryColor, secondaryColor, properties);
   }
}
