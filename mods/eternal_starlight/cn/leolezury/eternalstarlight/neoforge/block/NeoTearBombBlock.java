package cn.leolezury.eternalstarlight.neoforge.block;

import cn.leolezury.eternalstarlight.common.block.TearBombBlock;
import cn.leolezury.eternalstarlight.common.entity.misc.TearBomb;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class NeoTearBombBlock extends TearBombBlock {
   public static final MapCodec<NeoTearBombBlock> CODEC = simpleCodec(NeoTearBombBlock::new);

   public NeoTearBombBlock(Properties properties) {
      super(properties);
   }

   public void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
      if (!level.isClientSide) {
         if (igniter instanceof ServerPlayer serverPlayer) {
            ((PlayerTrigger)ESCriteriaTriggers.IGNITE_TEAR_BOMB.get()).trigger(serverPlayer);
         }

         TearBomb bomb = new TearBomb(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, igniter);
         level.addFreshEntity(bomb);
         level.playSound(null, bomb.getX(), bomb.getY(), bomb.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
         level.gameEvent(igniter, GameEvent.PRIME_FUSE, pos);
      }
   }
}
