package earth.terrarium.handcrafted.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.handcrafted.common.blockentities.ContainerBlockEntity;
import earth.terrarium.handcrafted.common.blockentities.OvenBlockEntity;
import earth.terrarium.handcrafted.common.blocks.crockery.CrockeryBlockEntity;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;

public class ModBlockEntityTypes {
   public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = ResourcefulRegistries.create(
      BuiltInRegistries.BLOCK_ENTITY_TYPE, "handcrafted"
   );
   public static final RegistryEntry<BlockEntityType<OvenBlockEntity>> OVEN = BLOCK_ENTITY_TYPES.register(
      "oven", () -> createBlockEntityType(OvenBlockEntity::new, (Block)ModBlocks.OVEN.get())
   );
   public static final RegistryEntry<BlockEntityType<CrockeryBlockEntity>> CROCKERY = BLOCK_ENTITY_TYPES.register(
      "crockery", () -> createBlockEntityType(CrockeryBlockEntity::new, ModBlocks.CROCKERY_COMBOS)
   );
   public static final RegistryEntry<BlockEntityType<ContainerBlockEntity>> CONTAINER;

   public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntitySupplier<E> factory, Block... blocks) {
      return Builder.of(factory, blocks).build(null);
   }

   public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntitySupplier<E> factory, ResourcefulRegistry<Block> registry) {
      return Builder.of(factory, registry.stream().map(RegistryEntry::get).toArray(Block[]::new)).build(null);
   }

   static {
      List<RegistryEntry<Block>> entries = new ArrayList<>();
      entries.addAll(ModBlocks.COUNTERS.getEntries());
      entries.addAll(ModBlocks.CUPBOARDS.getEntries());
      entries.addAll(ModBlocks.DESKS.getEntries());
      entries.addAll(ModBlocks.DRAWERS.getEntries());
      entries.addAll(ModBlocks.NIGHTSTANDS.getEntries());
      entries.addAll(ModBlocks.SHELVES.getEntries());
      entries.addAll(ModBlocks.SIDE_TABLES.getEntries());
      CONTAINER = BLOCK_ENTITY_TYPES.register(
         "container", () -> createBlockEntityType(ContainerBlockEntity::new, entries.stream().map(RegistryEntry::get).toArray(Block[]::new))
      );
   }
}
