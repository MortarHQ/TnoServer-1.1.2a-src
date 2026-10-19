package twilightforest.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;
import twilightforest.components.item.OreScannerComponent;
import twilightforest.components.item.OreScannerData;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFSounds;

public class OreMeterItem extends Item {
   public static final int MAX_CHUNK_SEARCH_RANGE = 2;
   public static final int LOAD_TIME = 50;

   public OreMeterItem(Properties properties) {
      super(properties);
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean held) {
      if (!level.isClientSide() && stack.has(TFDataComponents.ORE_SCANNING)) {
         OreScannerComponent newScan = ((OreScannerComponent)stack.get(TFDataComponents.ORE_SCANNING)).tickScan(level);
         if (newScan.isEmpty()) {
            stack.remove(TFDataComponents.ORE_SCANNING);
         } else if (!newScan.isFinished()) {
            stack.set(TFDataComponents.ORE_LOADING, newScan.getTickProgress());
            stack.set(TFDataComponents.ORE_SCANNING, newScan);
         } else {
            stack.set(
               TFDataComponents.ORE_DATA,
               OreScannerData.create(
                  newScan.getResults((Block)stack.get(TFDataComponents.ORE_FILTER)), newScan.centerChunkPos(), newScan.getVolume(level), getRange(stack)
               )
            );
            stack.remove(TFDataComponents.ORE_LOADING);
            stack.remove(TFDataComponents.ORE_SCANNING);
         }
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (player.getClass() != ServerPlayer.class) {
         return InteractionResultHolder.fail(stack);
      } else if (isLoading(stack)) {
         return InteractionResultHolder.pass(stack);
      } else {
         return !player.isSecondaryUseActive() ? beginScanning(level, player, stack) : toggleRange(level, player, stack);
      }
   }

   @NotNull
   private static InteractionResultHolder<ItemStack> beginScanning(Level level, Player player, ItemStack stack) {
      if (!level.isClientSide) {
         int range = getRange(stack);
         int scanTime = 50 + range * 25;
         OreScannerComponent data = OreScannerComponent.scanFromCenter(player.blockPosition(), range, scanTime);
         stack.set(TFDataComponents.ORE_SCANNING, data);
      }

      level.playSound(
         player, player.blockPosition(), (SoundEvent)TFSounds.ORE_METER_CRACKLE.get(), SoundSource.PLAYERS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F
      );
      return InteractionResultHolder.pass(stack);
   }

   @NotNull
   private static InteractionResultHolder<ItemStack> toggleRange(Level level, Player player, ItemStack stack) {
      HitResult result = getPlayerPOVHitResult(level, player, Fluid.ANY);
      if (result.getType() == Type.MISS) {
         if (!level.isClientSide) {
            int newRange = Mth.positiveModulo(getRange(stack) + 1, 3);
            stack.set(TFDataComponents.ORE_RANGE, newRange);
            player.displayClientMessage(Component.translatable("misc.twilightforest.ore_meter_new_range", new Object[]{newRange}), true);
            level.playSound(null, player.blockPosition(), (SoundEvent)SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 0.25F, 0.75F + newRange * 0.1F);
         }

         return InteractionResultHolder.success(stack);
      } else {
         return InteractionResultHolder.pass(stack);
      }
   }

   public InteractionResult useOn(UseOnContext context) {
      ItemStack stack = context.getItemInHand();
      if (context.isSecondaryUseActive()) {
         BlockState state = context.getLevel().getBlockState(context.getClickedPos());
         if (state.is(BlockTagGenerator.ORE_METER_TARGETABLE)) {
            stack.set(TFDataComponents.ORE_FILTER, state.getBlock());
            context.getPlayer()
               .displayClientMessage(
                  Component.translatable("misc.twilightforest.ore_meter_set_block", new Object[]{Component.translatable(state.getBlock().getDescriptionId())}),
                  true
               );
            context.getLevel()
               .playSound(
                  context.getPlayer(),
                  context.getPlayer().blockPosition(),
                  (SoundEvent)TFSounds.ORE_METER_TARGET_BLOCK.get(),
                  SoundSource.PLAYERS,
                  0.5F,
                  context.getLevel().getRandom().nextFloat() * 0.1F + 0.9F
               );
            return InteractionResult.SUCCESS;
         }
      }

      return super.useOn(context);
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged || newStack.getItem() != oldStack.getItem();
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      Block block = (Block)stack.get(TFDataComponents.ORE_FILTER);
      if (block != null) {
         tooltip.add(
            Component.translatable("misc.twilightforest.ore_meter_targeted_block", new Object[]{block.getDescriptionId()}).withStyle(ChatFormatting.GRAY)
         );
      }

      super.appendHoverText(stack, context, tooltip, flag);
   }

   public static boolean isLoading(ItemStack stack) {
      return stack.has(TFDataComponents.ORE_LOADING);
   }

   public static int getLoadProgress(ItemStack stack) {
      return (Integer)stack.getOrDefault(TFDataComponents.ORE_LOADING, 0);
   }

   @NotNull
   public static Integer getRange(ItemStack stack) {
      return (Integer)stack.getOrDefault(TFDataComponents.ORE_RANGE, 1);
   }
}
