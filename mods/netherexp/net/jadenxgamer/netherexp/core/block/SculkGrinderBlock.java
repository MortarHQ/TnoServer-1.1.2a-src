package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class SculkGrinderBlock extends Block {
   public SculkGrinderBlock(Properties properties) {
      super(properties);
   }

   @NotNull
   public ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      boolean bl = false;
      if (stack.is(JNETags.Items.SCULK_GRINDABLES)) {
         level.playSound(player, pos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
         if (level instanceof ServerLevel serverLevel) {
            ExperienceOrb.award(serverLevel, hitResult.getLocation(), (Integer)JNEConfigs.SCULK_GRINDER_EXPERIENCE.get());
         }

         if (!player.getAbilities().instabuild) {
            stack.shrink(1);
         }

         blockParticle(level, pos, (ParticleOptions)JNEParticleTypes.TREACHEROUS_FLAME.get());
         bl = true;
      } else if (stack.is(Items.SCULK)) {
         level.playSound(player, pos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
         if (!level.isClientSide()) {
            Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack((ItemLike)JNEBlocks.SCULK_GRINDER.get()));
         }

         if (!player.getAbilities().instabuild) {
            stack.shrink(1);
         }

         blockParticle(level, pos, ParticleTypes.SCULK_CHARGE_POP);
         bl = true;
      }

      return bl ? ItemInteractionResult.sidedSuccess(level.isClientSide) : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (random.nextInt(2) == 0) {
         blockParticle(level, pos, ParticleTypes.SCULK_CHARGE_POP);
      }
   }

   private static void blockParticle(Level level, BlockPos pos, ParticleOptions particle) {
      RandomSource randomSource = level.random;

      for (Direction direction : Direction.values()) {
         BlockPos blockPos2 = pos.relative(direction);
         if (!level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) {
            Axis axis = direction.getAxis();
            double e = axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : randomSource.nextFloat();
            double f = axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : randomSource.nextFloat();
            double g = axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : randomSource.nextFloat();
            level.addParticle(particle, pos.getX() + e, pos.getY() + f, pos.getZ() + g, 0.0, 0.0, 0.0);
         }
      }
   }
}
