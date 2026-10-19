package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.HitResult;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFItems;

public class OminousFireBlock extends BaseFireBlock {
   public static final MapCodec<OminousFireBlock> CODEC = simpleCodec(OminousFireBlock::new);

   public MapCodec<OminousFireBlock> codec() {
      return CODEC;
   }

   public OminousFireBlock(Properties properties) {
      super(properties, 1.0F);
   }

   protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      return this.canSurvive(state, level, currentPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP) && level.getFluidState(pos).isEmpty();
   }

   protected boolean canBurn(BlockState state) {
      return true;
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      return new ItemStack((ItemLike)TFItems.EXANIMATE_ESSENCE.value());
   }

   protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (!entity.getType().is(EntityTypeTags.UNDEAD)) {
         entity.hurt(TFDamageTypes.getDamageSource(level, TFDamageTypes.OMINOUS_FIRE), 1.0F);
      }
   }
}
