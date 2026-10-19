package net.mcreator.rpgstylemorebosses.world.features.treedecorators;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(bus = Bus.MOD)
public class DungeonVoidTrunkDecorator extends TrunkVineDecorator {
   public static MapCodec<DungeonVoidTrunkDecorator> CODEC = MapCodec.unit(DungeonVoidTrunkDecorator::new);
   public static TreeDecoratorType<?> DECORATOR_TYPE = new TreeDecoratorType(CODEC);

   @SubscribeEvent
   public static void registerTreeDecorator(RegisterEvent event) {
      event.register(Registries.TREE_DECORATOR_TYPE, ResourceLocation.parse("rpg_style_more_bosses:dungeon_void_tree_trunk_decorator"), () -> DECORATOR_TYPE);
   }

   protected TreeDecoratorType<?> type() {
      return DECORATOR_TYPE;
   }

   public void place(Context context) {
      context.logs().forEach(blockpos -> {
         if (context.random().nextInt(3) > 0) {
            BlockPos pos = blockpos.west();
            if (context.isAir(pos)) {
               context.setBlock(pos, oriented(Blocks.AIR.defaultBlockState(), Direction.EAST));
            }
         }

         if (context.random().nextInt(3) > 0) {
            BlockPos pos = blockpos.east();
            if (context.isAir(pos)) {
               context.setBlock(pos, oriented(Blocks.AIR.defaultBlockState(), Direction.WEST));
            }
         }

         if (context.random().nextInt(3) > 0) {
            BlockPos pos = blockpos.north();
            if (context.isAir(pos)) {
               context.setBlock(pos, oriented(Blocks.AIR.defaultBlockState(), Direction.SOUTH));
            }
         }

         if (context.random().nextInt(3) > 0) {
            BlockPos pos = blockpos.south();
            if (context.isAir(pos)) {
               context.setBlock(pos, oriented(Blocks.AIR.defaultBlockState(), Direction.NORTH));
            }
         }
      });
   }

   private static BlockState oriented(BlockState blockstate, Direction direction) {
      return switch (direction) {
         case SOUTH -> blockstate.rotate(Rotation.CLOCKWISE_180);
         case EAST -> blockstate.rotate(Rotation.CLOCKWISE_90);
         case WEST -> blockstate.rotate(Rotation.COUNTERCLOCKWISE_90);
         default -> blockstate;
      };
   }
}
