package com.blackgear.vanillabackport.common.level.items;

import com.blackgear.vanillabackport.common.registries.ModEntityTypes;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;

public class SulfurCubeBucketItem extends BucketItem {
   private final EntityType<?> type;
   private final SoundEvent emptySound;

   public SulfurCubeBucketItem(EntityType<?> type, SoundEvent emptySound, Properties properties) {
      super(Fluids.EMPTY, properties);
      this.type = type;
      this.emptySound = emptySound;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      BlockHitResult hit = getPlayerPOVHitResult(level, player, Fluid.NONE);
      if (hit.getType() != Type.MISS && hit.getType() == Type.BLOCK) {
         BlockPos clickedPos = hit.getBlockPos();
         Direction direction = hit.getDirection();
         BlockPos placePos = clickedPos.relative(direction);
         if (!level.mayInteract(player, clickedPos) || !player.mayUseItemAt(placePos, direction, stack)) {
            return InteractionResultHolder.fail(stack);
         }

         if (this.emptyContents(player, level, placePos, hit)) {
            this.checkExtraContent(player, level, stack, placePos);
            if (player instanceof ServerPlayer serverPlayer) {
               CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, placePos, stack);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(stack, player), level.isClientSide());
         } else {
            return InteractionResultHolder.fail(stack);
         }
      } else {
         return InteractionResultHolder.pass(stack);
      }
   }

   public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult result) {
      this.playEmptySound(player, level, pos);
      return true;
   }

   public void checkExtraContent(@Nullable Player player, Level level, ItemStack containerStack, BlockPos pos) {
      if (level instanceof ServerLevel serverLevel) {
         this.spawn(serverLevel, containerStack, pos);
         level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
      }
   }

   protected void playEmptySound(@Nullable Player player, LevelAccessor level, BlockPos pos) {
      level.playSound(player, pos, this.emptySound, SoundSource.NEUTRAL, 1.0F, 1.0F);
   }

   private void spawn(ServerLevel level, ItemStack stack, BlockPos pos) {
      if (this.type.spawn(level, stack, null, pos, MobSpawnType.BUCKET, true, false) instanceof Bucketable bucketable) {
         CustomData customData = (CustomData)stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
         bucketable.loadFromBucketTag(customData.copyTag());
         bucketable.setFromBucket(true);
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      if (this.type == ModEntityTypes.SULFUR_CUBE) {
         CustomData customData = (CustomData)stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
         Provider registries = context.registries();
         if (customData.isEmpty() || registries == null) {
            return;
         }

         CompoundTag tag = customData.copyTag();
         if (tag.contains("absorbed_block", 10)) {
            ItemStack absorbedBlock = ItemStack.parse(registries, tag.getCompound("absorbed_block")).orElse(ItemStack.EMPTY);
            if (!absorbedBlock.isEmpty()) {
               tooltipComponents.add(
                  Component.translatable("entity.minecraft.sulfur_cube.content", new Object[]{absorbedBlock.getHoverName()})
                     .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY})
               );
            }
         }
      }
   }
}
