package earth.terrarium.handcrafted.common.blocks.trophies;

import com.mojang.serialization.MapCodec;
import earth.terrarium.handcrafted.common.blocks.base.SimpleBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class HangingTrophyBlock extends SimpleBlock {
   public static final MapCodec<HangingTrophyBlock> CODEC = simpleCodec(HangingTrophyBlock::new);

   public HangingTrophyBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }
}
