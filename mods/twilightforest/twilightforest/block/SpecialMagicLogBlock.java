package twilightforest.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public abstract class SpecialMagicLogBlock extends RotatedPillarBlock {
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

   protected SpecialMagicLogBlock(Properties properties) {
      super(properties.strength(2.0F).sound(SoundType.WOOD).lightLevel(state -> state.getValue(ACTIVE) ? 15 : 0));
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(ACTIVE, false));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder.add(new Property[]{ACTIVE}));
   }

   public int tickRate() {
      return 20;
   }

   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
      level.scheduleTick(pos, this, this.tickRate());
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
      if ((Boolean)state.getValue(ACTIVE) && this.doesCoreFunction()) {
         this.playSound(level, pos, rand);
         this.performTreeEffect(level, pos, rand);
         level.scheduleTick(pos, this, this.tickRate());
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
      if (!this.doesCoreFunction()) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(ACTIVE, false));
         player.displayClientMessage(
            Component.translatable("misc.twilightforest.core_disabled", new Object[]{this.getName()}).withStyle(ChatFormatting.RED), true
         );
         return InteractionResult.SUCCESS;
      } else if (!(Boolean)state.getValue(ACTIVE)) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(ACTIVE, true));
         level.scheduleTick(pos, this, this.tickRate());
         return InteractionResult.SUCCESS;
      } else if ((Boolean)state.getValue(ACTIVE)) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(ACTIVE, false));
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   abstract void performTreeEffect(ServerLevel var1, BlockPos var2, RandomSource var3);

   public abstract boolean doesCoreFunction();

   protected void playSound(Level level, BlockPos pos, RandomSource rand) {
   }
}
