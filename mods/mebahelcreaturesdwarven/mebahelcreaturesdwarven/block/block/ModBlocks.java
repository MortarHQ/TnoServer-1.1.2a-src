package mebahelcreaturesdwarven.block.block;

import mebahelcreaturesdwarven.MebahelCreaturesDwarven;
import mebahelcreaturesdwarven.block.block.custom.DwarvenMetalPipeBlock;
import mebahelcreaturesdwarven.block.block.custom.DwemerChestBlock;
import mebahelcreaturesdwarven.block.block.custom.DwemerMetalPipeGearBlock;
import mebahelcreaturesdwarven.block.block.custom.DwemerSpiderBlock;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import net.minecraft.class_1792.class_1793;
import net.minecraft.class_4970.class_2251;

public class ModBlocks {
   public static final class_2248 DWARVEN_SPIDER_BLOCK = registerBlock(
      "dwarven_spider_block", new DwemerSpiderBlock(class_2251.method_9630(class_2246.field_27119).method_22488())
   );
   public static final class_2248 DWEMER_CHEST = registerBlock(
      "dwemer_chest", new DwemerChestBlock(class_2251.method_9630(class_2246.field_27119).method_22488())
   );
   public static final class_2248 DWEMER_METAL_PIPE = registerBlock(
      "dwemer_metal_pipe", new DwarvenMetalPipeBlock(class_2251.method_9630(class_2246.field_27119).method_22488())
   );
   public static final class_2248 DWEMER_METAL_PIPE_GEAR = registerBlock(
      "dwemer_metal_pipe_gear", new DwemerMetalPipeGearBlock(class_2251.method_9630(class_2246.field_27119).method_22488())
   );
   public static final class_2248 DWEMER_METAL_BLOCK = registerBlock("dwemer_metal_block", new class_2248(class_2251.method_9630(class_2246.field_27119)));

   private static class_2248 registerBlock(String name, class_2248 block) {
      registerBlockItem(name, block);
      return (class_2248)class_2378.method_10230(class_7923.field_41175, class_2960.method_60655("mebahelcreaturesdwarven", name), block);
   }

   private static class_1792 registerBlockItem(String name, class_2248 block) {
      return (class_1792)class_2378.method_10230(
         class_7923.field_41178, class_2960.method_60655("mebahelcreaturesdwarven", name), new class_1747(block, new class_1793())
      );
   }

   public static void registerModBlocks() {
      MebahelCreaturesDwarven.LOGGER.info("Registering blocks for mebahelcreaturesdwarven.");
   }
}
