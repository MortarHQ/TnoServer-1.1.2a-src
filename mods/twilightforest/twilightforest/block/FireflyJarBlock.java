package twilightforest.block;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import twilightforest.block.entity.JarBlockEntity;
import twilightforest.components.item.JarLid;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFParticleType;

public class FireflyJarBlock extends JarBlock {
   public FireflyJarBlock(Properties properties) {
      super(properties);
   }

   @Override
   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
      if (player.isShiftKeyDown() && level.getBlockEntity(pos) instanceof JarBlockEntity jarBE) {
         ItemEntity firefly = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(TFBlocks.FIREFLY));
         level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
         firefly.spawnAtLocation(firefly.getItem());
         firefly.spawnAtLocation(
            (ItemStack)Util.make(
               new ItemStack((ItemLike)TFBlocks.MASON_JAR.get()), jar -> jar.set((DataComponentType)TFDataComponents.JAR_LID.get(), new JarLid(jarBE.lid))
            )
         );
         level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
         return InteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useWithoutItem(state, level, pos, player, result);
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      for (int i = 0; i < 2; i++) {
         double dx = pos.getX() + ((random.nextFloat() - random.nextFloat()) * 0.2F + 0.5F);
         double dy = pos.getY() + 0.4F + (random.nextFloat() - random.nextFloat()) * 0.3F;
         double dz = pos.getZ() + ((random.nextFloat() - random.nextFloat()) * 0.2F + 0.5F);
         level.addParticle((ParticleOptions)TFParticleType.FIREFLY.get(), dx, dy, dz, 0.0, 0.0, 0.0);
      }
   }
}
