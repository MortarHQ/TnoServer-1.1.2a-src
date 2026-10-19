package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.RareIceBlockEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.CriteriaModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.gameevent.GameEventListener;

@ParametersAreNonnullByDefault
public class RareIceBlock extends HalfTransparentBlock implements EntityBlock {
   public RareIceBlock(Properties properties) {
      super(properties);
   }

   public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack, boolean bl) {
      super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack, bl);
      Holder<Enchantment> silkTouch = serverLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SILK_TOUCH);
      if (EnchantmentHelper.getItemEnchantmentLevel(silkTouch, itemStack) == 0) {
         int xp = 15 + serverLevel.random.nextInt(20) + serverLevel.random.nextInt(20);
         this.popExperience(serverLevel, blockPos, xp);
      }
   }

   public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, ItemStack itemStack) {
      super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
      if (!level.isClientSide) {
         CriteriaModule.BREAK_ENCHANTED_ICE.trigger((ServerPlayer)player);
      }
   }

   public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
      double x = blockPos.getX() + 0.5 + (0.5 + random.nextDouble() * 0.5) * (random.nextBoolean() ? 1 : -1);
      double y = blockPos.getY() + 0.5 + (0.5 + random.nextDouble() * 0.5) * (random.nextBoolean() ? 1 : -1);
      double z = blockPos.getZ() + 0.5 + (0.5 + random.nextDouble() * 0.5) * (random.nextBoolean() ? 1 : -1);
      level.addParticle(ParticleTypes.GLOW, x, y, z, 0.0, 0.0, 0.0);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return new RareIceBlockEntity(blockPos, blockState);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
      return createTickerHelper(blockEntityType, (BlockEntityType)EntityTypeModule.RARE_ICE.get(), RareIceBlockEntity::tick);
   }

   @Nullable
   public <T extends BlockEntity> GameEventListener getListener(ServerLevel serverLevel, T blockEntity) {
      return super.getListener(serverLevel, blockEntity);
   }

   private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
      BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_
   ) {
      return p_152134_ == p_152133_ ? p_152135_ : null;
   }
}
