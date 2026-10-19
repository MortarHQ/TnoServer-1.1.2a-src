package com.teamresourceful.resourcefullib.common.fluid;

import com.teamresourceful.resourcefullib.common.fluid.data.FluidData;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResourcefulBucketItem extends BucketItem {
   private final FluidData data;

   public ResourcefulBucketItem(FluidData data, Properties properties) {
      super((Fluid)data.still().get(), properties);
      this.data = data;
      this.data.setBucket(() -> this);
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      return this.data.properties().canPlace() ? super.use(level, player, hand) : InteractionResultHolder.fail(player.getItemInHand(hand));
   }

   protected void playEmptySound(@Nullable Player player, LevelAccessor level, BlockPos pos) {
      SoundEvent event = this.data.properties().sounds().getOrDefault("bucket_empty", SoundEvents.BUCKET_EMPTY);
      level.playSound(player, pos, event, SoundSource.BLOCKS, 1.0F, 1.0F);
      level.gameEvent(player, GameEvent.FLUID_PLACE, pos);
   }
}
