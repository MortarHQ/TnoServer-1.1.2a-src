package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;

public class WeatheringGolemSteelPillarBlock extends RotatedPillarBlock implements WeatheringGolemSteel {
   public static final MapCodec<WeatheringGolemSteelPillarBlock> CODEC = simpleCodec(WeatheringGolemSteelPillarBlock::new);

   public WeatheringGolemSteelPillarBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<WeatheringGolemSteelPillarBlock> codec() {
      return CODEC;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      return this.use(stack, state, level, pos, player);
   }

   public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      this.changeOverTime(blockState, serverLevel, blockPos, randomSource);
   }

   public boolean isRandomlyTicking(BlockState blockState) {
      return !this.isOxidized() && !this.isWaxed();
   }
}
