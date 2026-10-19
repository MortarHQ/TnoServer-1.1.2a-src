package nosferatu.eldergrove;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class VishroomBlock extends EldergrovePlantBlock {
   public VishroomBlock(Properties properties) {
      super(properties);
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (!level.isClientSide && entity instanceof LivingEntity living) {
         living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120, 0));
      }

      super.entityInside(state, level, pos, entity);
   }
}
