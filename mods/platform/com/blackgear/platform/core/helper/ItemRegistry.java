package com.blackgear.platform.core.helper;

import com.blackgear.platform.core.CoreRegistry;
import com.blackgear.platform.core.helper.neoforge.ItemRegistryImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class ItemRegistry {
   private final CoreRegistry<Item> items;

   private ItemRegistry(String modId) {
      this.items = CoreRegistry.create(BuiltInRegistries.ITEM, modId);
   }

   public static ItemRegistry create(String modId) {
      return new ItemRegistry(modId);
   }

   public <T extends Item> Supplier<T> register(String name, Supplier<T> item) {
      return this.items.register(name, item);
   }

   public Supplier<Item> register(String name) {
      return this.register(name, new Properties());
   }

   public Supplier<Item> register(String name, Properties properties) {
      return this.register(name, Item::new, properties);
   }

   public Supplier<Item> register(String name, Function<Properties, Item> function) {
      return this.register(name, function, new Properties());
   }

   public Supplier<Item> register(String name, Function<Properties, Item> factory, Properties properties) {
      return this.items.register(name, () -> factory.apply(properties));
   }

   public <T extends Mob> Supplier<Item> spawnEgg(String name, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Properties properties) {
      return this.items.register(name, () -> createSpawnEgg(entity, primaryColor, secondaryColor, properties));
   }

   public Supplier<Item> fromBlock(Supplier<Block> block) {
      return () -> block.get().asItem();
   }

   public void register() {
      this.items.register();
   }

   public CoreRegistry<Item> registry() {
      return this.items;
   }

   @ExpectPlatform
   @Transformed
   public static <T extends Mob> Item createSpawnEgg(Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Properties properties) {
      return ItemRegistryImpl.createSpawnEgg(entity, primaryColor, secondaryColor, properties);
   }
}
