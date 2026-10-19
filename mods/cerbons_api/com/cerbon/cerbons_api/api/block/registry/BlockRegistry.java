package com.cerbon.cerbons_api.api.block.registry;

import com.cerbon.cerbons_api.api.item.registry.ItemRegistry;
import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.google.common.base.Suppliers;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockRegistry {
   private final ResourcefulRegistry<Block> blockRegistry;
   private final Supplier<ItemRegistry> itemRegistry;
   private boolean registerItemRegistry = false;

   public BlockRegistry(String modId) {
      this(modId, Suppliers.memoize(() -> new ItemRegistry(modId)));
      this.registerItemRegistry = true;
   }

   public BlockRegistry(String modId, Supplier<ItemRegistry> itemRegistry) {
      this.blockRegistry = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, modId);
      this.itemRegistry = itemRegistry;
   }

   public RegistryEntry<Block> registerBlockWithItem(String id) {
      return this.registerBlockWithItem(Properties.of(), id);
   }

   public RegistryEntry<Block> registerBlockWithItem(Properties blockProperties, String id) {
      return this.registerBlockWithItem(blockProperties, new net.minecraft.world.item.Item.Properties(), id);
   }

   public RegistryEntry<Block> registerBlockWithItem(Properties blockProperties, net.minecraft.world.item.Item.Properties itemProperties, String id) {
      Supplier<Block> block = () -> new Block(blockProperties);
      RegistryEntry<Block> blockEntry = this.registerBlock(block, id);
      this.itemRegistry.get().registerBlockItem(blockEntry, itemProperties, id);
      return blockEntry;
   }

   public RegistryEntry<Block> registerBlockWithItem(Supplier<Block> block, net.minecraft.world.item.Item.Properties itemProperties, String id) {
      RegistryEntry<Block> blockEntry = this.registerBlock(block, id);
      this.itemRegistry.get().registerBlockItem(blockEntry, itemProperties, id);
      return blockEntry;
   }

   public RegistryEntry<Block> registerBlockWithItem(Supplier<Block> block, String id) {
      RegistryEntry<Block> blockEntry = this.registerBlock(block, id);
      this.itemRegistry.get().registerBlockItem(blockEntry, id);
      return blockEntry;
   }

   public RegistryEntry<Block> registerBlock(String id) {
      return this.registerBlock(Properties.of(), id);
   }

   public RegistryEntry<Block> registerBlock(Properties blockProperties, String id) {
      return this.registerBlock(() -> new Block(blockProperties), id);
   }

   public <T extends Block> RegistryEntry<T> registerBlock(Supplier<T> block, String id) {
      return this.blockRegistry.register(id, block);
   }

   public Collection<RegistryEntry<Block>> getEntries() {
      return this.blockRegistry.getEntries();
   }

   public Stream<RegistryEntry<Block>> stream() {
      return this.blockRegistry.stream();
   }

   public Stream<Block> boundStream() {
      return this.blockRegistry.boundStream();
   }

   public void register() {
      this.blockRegistry.register();
      if (this.registerItemRegistry) {
         this.itemRegistry.get().register();
      }
   }
}
