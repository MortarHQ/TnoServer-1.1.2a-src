package twilightforest.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.entity.boss.Hydra;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFEntities;

public class HydraSpawnerBlockEntity extends BossSpawnerBlockEntity<Hydra> {
   public HydraSpawnerBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)TFBlockEntities.HYDRA_SPAWNER.get(), (EntityType<Hydra>)TFEntities.HYDRA.get(), pos, state);
   }

   @Override
   protected int getRange() {
      return 50;
   }

   @Override
   public ParticleOptions getSpawnerParticle() {
      return ParticleTypes.FLAME;
   }
}
