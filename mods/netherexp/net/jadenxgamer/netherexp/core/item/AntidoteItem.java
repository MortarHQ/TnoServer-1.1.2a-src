package net.jadenxgamer.netherexp.core.item;

import java.awt.Color;
import java.util.List;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.item.components.AntidoteContents;
import net.jadenxgamer.netherexp.registry.JNEDataComponents;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class AntidoteItem extends Item {
   public AntidoteItem(Properties properties) {
      super(properties);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
      Player player = user instanceof Player ? (Player)user : null;
      if (player instanceof ServerPlayer serverPlayer) {
         CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
      }

      AntidoteContents contents = (AntidoteContents)stack.getOrDefault(JNEDataComponents.ANTIDOTE_CONTENTS.get(), AntidoteContents.EMPTY);
      boolean hasEffect = (Boolean)JNEConfigs.POTION_CONSUMPTION_PARTICLES.get() && !contents.getAllEffects().isEmpty();
      if (!level.isClientSide) {
         contents.forEachEffect(action -> {
            if (((MobEffect)action.getEffect().value()).isInstantenous()) {
               ((MobEffect)action.getEffect().value()).applyInstantenousEffect(player, player, user, action.getAmplifier(), 1.0);
            } else {
               user.addEffect(action);
            }
         });
         if (hasEffect) {
            level.playSound(null, user.blockPosition(), JNESoundEvents.POTION_POSTDRINK.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
         }
      } else if (hasEffect) {
         CommonParticles.potionConsumeParticle(level, level.random, user, new Color(AntidoteContents.getColor(stack)));
      }

      if (player != null) {
         player.awardStat(Stats.ITEM_USED.get(this));
         if (!player.getAbilities().instabuild) {
            stack.shrink(1);
            level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.5F, 1.0F);
         }
      }

      user.gameEvent(GameEvent.DRINK);
      return stack;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      return ItemUtils.startUsingInstantly(level, player, hand);
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 48;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.DRINK;
   }

   public String getDescriptionId(ItemStack stack) {
      AntidoteContents contents = (AntidoteContents)stack.getOrDefault(JNEDataComponents.ANTIDOTE_CONTENTS.get(), AntidoteContents.EMPTY);
      return contents.getName(this.getDescriptionId() + ".effect.");
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      AntidoteContents contents = (AntidoteContents)stack.get(JNEDataComponents.ANTIDOTE_CONTENTS.get());
      if (contents != null) {
         contents.addAntidoteTooltip(tooltipComponents::add, 1.0F, context.tickRate());
      }
   }
}
