package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class CrinoaBlock extends CropBlock {
   public CrinoaBlock(Properties properties) {
      super(properties);
   }

   protected ItemLike getBaseSeedId() {
      return (ItemLike)ESItems.CRINOA_SEEDS.get();
   }
}
