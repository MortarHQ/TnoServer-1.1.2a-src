package net.blay09.mods.balm.api;

import java.util.Collection;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public interface BalmRegistries {
   @Deprecated(forRemoval = true, since = "1.21.5")
   default ResourceLocation getKey(Item item) {
      return BuiltInRegistries.ITEM.getKey(item);
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default ResourceLocation getKey(Block block) {
      return BuiltInRegistries.BLOCK.getKey(block);
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default ResourceLocation getKey(Fluid fluid) {
      return BuiltInRegistries.FLUID.getKey(fluid);
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default ResourceLocation getKey(EntityType<?> entityType) {
      return BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default ResourceLocation getKey(MenuType<?> menuType) {
      return BuiltInRegistries.MENU.getKey(menuType);
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default Collection<ResourceLocation> getItemKeys() {
      return BuiltInRegistries.ITEM.keySet();
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default Item getItem(ResourceLocation key) {
      return (Item)BuiltInRegistries.ITEM.get(key);
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default Block getBlock(ResourceLocation key) {
      return (Block)BuiltInRegistries.BLOCK.get(key);
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default Fluid getFluid(ResourceLocation key) {
      return (Fluid)BuiltInRegistries.FLUID.get(key);
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default MobEffect getMobEffect(ResourceLocation key) {
      return (MobEffect)BuiltInRegistries.MOB_EFFECT.get(key);
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default TagKey<Item> getItemTag(ResourceLocation key) {
      return TagKey.create(Registries.ITEM, key);
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default Attribute getAttribute(ResourceLocation key) {
      return (Attribute)BuiltInRegistries.ATTRIBUTE.get(key);
   }

   void enableMilkFluid();

   Fluid getMilkFluid();

   <T> DeferredObject<T> register(Registry<T> var1, Function<ResourceLocation, T> var2, ResourceLocation var3);
}
