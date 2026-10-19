package twilightforest.block;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public interface SnowLoggable {
   float SNOW_Z_FIGHTING = 0.008F;
   int MIN_SNOW_LAYERS = 0;
   int MAX_SNOW_LAYERS = 8;
   IntegerProperty SNOW_LAYERS = IntegerProperty.create("layers", 0, 8);
   VoxelShape[] SNOW_SHAPE_BY_LAYER = (VoxelShape[])Util.make(new VoxelShape[9], shapes -> {
      shapes[0] = Shapes.empty();

      for (int i = 1; i <= 8; i++) {
         double height = i * 2.0;
         shapes[i] = Block.box(0.0, 0.0, 0.0, 16.0, height, 16.0);
      }
   });

   default void meltSnow(BlockState state, Level level, BlockPos pos) {
      int snowLayers = (Integer)state.getValue(SNOW_LAYERS);
      if (level.getBrightness(LightLayer.BLOCK, pos) > 11 && snowLayers > 0 && snowLayers < 8) {
         Block.dropResources((BlockState)Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, snowLayers), level, pos);
         level.setBlock(pos, (BlockState)state.setValue(SNOW_LAYERS, 0), 2);
      }
   }

   default void handleBreakingLogic(Level level, BlockPos pos, BlockState state, Player player, @Nullable BlockState blockToConvertTo) {
      BlockHitResult rayTraceResult = this.clip(player);
      Vec3 hitVec = rayTraceResult.getType() == Type.BLOCK ? rayTraceResult.getLocation() : null;
      if (hitVec != null) {
         hitVec = hitVec.add(-pos.getX(), -pos.getY(), -pos.getZ());
      }

      double snowLevel = SNOW_SHAPE_BY_LAYER[state.getValue(SNOW_LAYERS)].bounds().maxY;
      if (hitVec != null && hitVec.y() <= snowLevel) {
         level.levelEvent(player, 2001, pos, Block.getId(Blocks.SNOW.defaultBlockState()));
         level.setBlockAndUpdate(pos, blockToConvertTo != null ? blockToConvertTo : (BlockState)state.setValue(SNOW_LAYERS, 0));
      } else {
         level.levelEvent(player, 2001, pos, Block.getId((BlockState)state.setValue(SNOW_LAYERS, 0)));
         if (!player.isCreative()) {
            Block.dropResources(state, level, pos, null, player, player.getMainHandItem());
         }

         level.setBlockAndUpdate(pos, (BlockState)Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, (Integer)state.getValue(SNOW_LAYERS)));
      }
   }

   default BlockHitResult clip(Player entity) {
      Vec3 startPos = new Vec3(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
      Vec3 endPos = startPos.add(entity.getLookAngle().x() * 6.0, entity.getLookAngle().y() * 6.0, entity.getLookAngle().z() * 6.0);
      ClipContext rayTraceContext = new ClipContext(startPos, endPos, net.minecraft.world.level.ClipContext.Block.OUTLINE, Fluid.NONE, entity);
      return entity.level().clip(rayTraceContext);
   }

   default boolean isColliding(Player player, BlockPos pos, BlockState state) {
      CollisionContext collisioncontext = CollisionContext.of(player);
      return !player.level().isUnobstructed(state, pos, collisioncontext);
   }
}
