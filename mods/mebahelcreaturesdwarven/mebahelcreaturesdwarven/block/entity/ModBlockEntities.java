package mebahelcreaturesdwarven.block.entity;

import mebahelcreaturesdwarven.MebahelCreaturesDwarven;
import mebahelcreaturesdwarven.block.block.ModBlocks;
import mebahelcreaturesdwarven.block.entity.custom.DwemerChestBlockEntity;
import mebahelcreaturesdwarven.block.entity.custom.DwemerMetalPipeGearBlockEntity;
import mebahelcreaturesdwarven.block.entity.custom.DwemerSpiderBlockEntity;
import net.minecraft.class_2248;
import net.minecraft.class_2378;
import net.minecraft.class_2591;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import net.minecraft.class_2591.class_2592;

public class ModBlockEntities {
   public static final class_2591<DwemerChestBlockEntity> DWEMER_CHEST_ENTITY = (class_2591<DwemerChestBlockEntity>)class_2378.method_10230(
      class_7923.field_41181,
      class_2960.method_60655("mebahelcreaturesdwarven", "dwemer_chest"),
      class_2592.method_20528(DwemerChestBlockEntity::new, new class_2248[]{ModBlocks.DWEMER_CHEST}).build()
   );
   public static final class_2591<DwemerMetalPipeGearBlockEntity> DWARVEN_METAL_PIPE_GEAR = (class_2591<DwemerMetalPipeGearBlockEntity>)class_2378.method_10230(
      class_7923.field_41181,
      class_2960.method_60655("mebahelcreaturesdwarven", "dwemer_metal_pipe_gear"),
      class_2592.method_20528(DwemerMetalPipeGearBlockEntity::new, new class_2248[]{ModBlocks.DWEMER_METAL_PIPE_GEAR}).build()
   );
   public static final class_2591<DwemerSpiderBlockEntity> DWEMER_SPIDER_BLOCK_ENTITY = (class_2591<DwemerSpiderBlockEntity>)class_2378.method_10230(
      class_7923.field_41181,
      class_2960.method_60655("mebahelcreaturesdwarven", "dwarven_spider_block"),
      class_2592.method_20528(DwemerSpiderBlockEntity::new, new class_2248[]{ModBlocks.DWARVEN_SPIDER_BLOCK}).build()
   );

   public static void registerModBlockEntities() {
      MebahelCreaturesDwarven.LOGGER.info("Registering BlockEntities for mebahelcreaturesdwarven");
   }
}
