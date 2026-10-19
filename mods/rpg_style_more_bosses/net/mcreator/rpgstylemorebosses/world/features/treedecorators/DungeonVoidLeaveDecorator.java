package net.mcreator.rpgstylemorebosses.world.features.treedecorators;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(bus = Bus.MOD)
public class DungeonVoidLeaveDecorator extends LeaveVineDecorator {
   public static MapCodec<DungeonVoidLeaveDecorator> CODEC = MapCodec.unit(DungeonVoidLeaveDecorator::new);
   public static TreeDecoratorType<?> DECORATOR_TYPE = new TreeDecoratorType(CODEC);

   @SubscribeEvent
   public static void registerTreeDecorator(RegisterEvent event) {
      event.register(Registries.TREE_DECORATOR_TYPE, ResourceLocation.parse("rpg_style_more_bosses:dungeon_void_tree_leave_decorator"), () -> DECORATOR_TYPE);
   }

   public DungeonVoidLeaveDecorator() {
      super(0.25F);
   }

   protected TreeDecoratorType<?> type() {
      return DECORATOR_TYPE;
   }

   public void place(Context context) {
      context.leaves().forEach(blockpos -> {
         if (context.random().nextFloat() < 0.25F) {
            BlockPos pos = blockpos.west();
            if (context.isAir(pos)) {
               addVine(pos, Direction.WEST, context);
            }
         }

         if (context.random().nextFloat() < 0.25F) {
            BlockPos pos = blockpos.east();
            if (context.isAir(pos)) {
               addVine(pos, Direction.EAST, context);
            }
         }

         if (context.random().nextFloat() < 0.25F) {
            BlockPos pos = blockpos.north();
            if (context.isAir(pos)) {
               addVine(pos, Direction.NORTH, context);
            }
         }

         if (context.random().nextFloat() < 0.25F) {
            BlockPos pos = blockpos.south();
            if (context.isAir(pos)) {
               addVine(pos, Direction.SOUTH, context);
            }
         }
      });
   }

   private static void addVine(BlockPos pos, Direction direction, Context context) {
      context.setBlock(pos, Blocks.AIR.defaultBlockState());
      int i = 4;

      for (BlockPos blockpos = pos.below(); context.isAir(blockpos) && i > 0; i--) {
         context.setBlock(blockpos, oriented(Blocks.AIR.defaultBlockState(), direction));
         blockpos = blockpos.below();
      }
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
