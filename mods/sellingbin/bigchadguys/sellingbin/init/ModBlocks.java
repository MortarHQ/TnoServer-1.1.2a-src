package bigchadguys.sellingbin.init;

import bigchadguys.sellingbin.block.SellingBinBlock;
import bigchadguys.sellingbin.block.entity.SellingBinBlockEntity;
import bigchadguys.sellingbin.item.SellingBinItem;
import com.mojang.datafixers.types.Type;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModBlocks extends ModRegistries {
   public static RegistrySupplier<Block> ERROR;
   public static RegistrySupplier<SellingBinBlock> SELLING_BIN;

   public static void register() {
      ERROR = register(
         "error",
         () -> new Block(Properties.ofFullCopy(Blocks.BEDROCK)),
         block -> new BlockItem((Block)block.get(), new net.minecraft.world.item.Item.Properties())
      );
      SELLING_BIN = register(
         "selling_bin", () -> new SellingBinBlock(Properties.ofFullCopy(Blocks.OAK_WOOD).noOcclusion()), block -> new SellingBinItem((Block)block.get())
      );
   }

   public static <V extends Block> RegistrySupplier<V> register(ResourceLocation id, Supplier<V> block) {
      return register(id, block);
   }

   public static <V extends Block> RegistrySupplier<V> register(String name, Supplier<V> block) {
      return register(name, block);
   }

   public static <V extends Block> RegistrySupplier<V> register(ResourceLocation id, Supplier<V> block, Function<RegistrySupplier<V>, BlockItem> item) {
      return register(id, block, id, item);
   }

   public static <V extends Block> RegistrySupplier<V> register(String name, Supplier<V> block, Function<RegistrySupplier<V>, BlockItem> item) {
      return register(name, block, name, item);
   }

   public static <V extends Block> RegistrySupplier<V> register(
      ResourceLocation id, Supplier<V> block, ResourceLocation itemId, Function<RegistrySupplier<V>, BlockItem> item
   ) {
      RegistrySupplier<V> entry = register(BLOCKS, id, block);
      if (item != null) {
         Supplier<BlockItem> value = () -> item.apply(entry);
         ModItems.register(itemId, (Supplier<V>)value);
      }

      return entry;
   }

   public static <V extends Block> RegistrySupplier<V> register(String name, Supplier<V> block, String itemName, Function<RegistrySupplier<V>, BlockItem> item) {
      RegistrySupplier<V> entry = register(BLOCKS, name, block);
      if (item != null) {
         Supplier<BlockItem> value = () -> item.apply(entry);
         ModItems.register(itemName, (Supplier<V>)value);
      }

      return entry;
   }

   public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(
      ResourceLocation id, BlockEntitySupplier<T> blockEntity, RegistrySupplier<Block>... blocks
   ) {
      return register(id, blockEntity, Util.fetchChoiceType(References.BLOCK_ENTITY, null), blocks);
   }

   public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(
      ResourceLocation id, BlockEntitySupplier<T> blockEntity, String typeId, RegistrySupplier<Block>... blocks
   ) {
      return register(id, blockEntity, Util.fetchChoiceType(References.BLOCK_ENTITY, typeId), blocks);
   }

   public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(
      ResourceLocation id, BlockEntitySupplier<T> blockEntity, Type<?> type, RegistrySupplier<Block>... blocks
   ) {
      return register(BLOCK_ENTITY_TYPES, id, () -> {
         Block[] values = new Block[blocks.length];

         for (int i = 0; i < blocks.length; i++) {
            values[i] = (Block)blocks[i].get();
         }

         return Builder.of(blockEntity, values).build(type);
      });
   }

   public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(
      String name, BlockEntitySupplier<T> blockEntity, RegistrySupplier<? extends Block>... blocks
   ) {
      return register(name, blockEntity, (Type<?>)null, blocks);
   }

   public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(
      String name, BlockEntitySupplier<T> blockEntity, String typeId, RegistrySupplier<? extends Block>... blocks
   ) {
      return register(name, blockEntity, Util.fetchChoiceType(References.BLOCK_ENTITY, typeId), blocks);
   }

   public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(
      String name, BlockEntitySupplier<T> blockEntity, Type<?> type, RegistrySupplier<? extends Block>... blocks
   ) {
      return register(BLOCK_ENTITY_TYPES, name, () -> {
         Block[] values = new Block[blocks.length];

         for (int i = 0; i < blocks.length; i++) {
            values[i] = (Block)blocks[i].get();
         }

         return Builder.of(blockEntity, values).build(type);
      });
   }

   public static class Entities extends ModBlocks {
      public static RegistrySupplier<BlockEntityType<SellingBinBlockEntity>> SELLING_BIN;

      public static void register() {
         SELLING_BIN = register("selling_bin", SellingBinBlockEntity::new, ModBlocks.SELLING_BIN);
      }
   }
}
