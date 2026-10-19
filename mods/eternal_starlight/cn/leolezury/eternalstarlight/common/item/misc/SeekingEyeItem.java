package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.entity.misc.EyeOfSeeking;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.levelgen.structure.Structure;

public class SeekingEyeItem extends Item {
   public SeekingEyeItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      player.startUsingItem(hand);
      if (level instanceof ServerLevel serverLevel) {
         TagKey<Structure> key = ESTags.Structures.BOSS_LANDMARKS;
         ItemStack otherHandStack = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
         if (otherHandStack.is(ESTags.Items.GOLEM_FORGE_LOCATORS)) {
            key = ESTags.Structures.GOLEM_FORGE;
         }

         if (otherHandStack.is(ESTags.Items.CURSED_GARDEN_LOCATORS)) {
            key = ESTags.Structures.CURSED_GARDEN;
         }

         BlockPos blockPos = serverLevel.findNearestMapStructure(key, player.blockPosition(), 100, false);
         if (blockPos != null) {
            EyeOfSeeking eye = new EyeOfSeeking(level, player.getX(), player.getY(0.5), player.getZ());
            eye.setItem(stack);
            eye.signalTo(blockPos);
            level.gameEvent(GameEvent.PROJECTILE_SHOOT, eye.position(), Context.of(player));
            level.addFreshEntity(eye);
            level.playSound(
               null,
               player.getX(),
               player.getY(),
               player.getZ(),
               ESSoundEvents.SEEKING_EYE_LAUNCH.get(),
               SoundSource.NEUTRAL,
               0.5F,
               0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
            );
            stack.consume(1, player);
            player.awardStat(Stats.ITEM_USED.get(this));
            player.swing(hand, true);
         }
      }

      return InteractionResultHolder.consume(stack);
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> components, TooltipFlag tooltipFlag) {
      super.appendHoverText(itemStack, tooltipContext, components, tooltipFlag);
      components.add(CommonComponents.EMPTY);
      components.add(Component.translatable("tooltip.eternal_starlight.seeking_eye.target").withStyle(ChatFormatting.GRAY));
      components.add(
         Component.literal(" ").append(Component.translatable("tooltip.eternal_starlight.seeking_eye.associated_item")).withStyle(ChatFormatting.BLUE)
      );
   }
}
