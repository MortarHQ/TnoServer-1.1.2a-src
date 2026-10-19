package com.finderfeed.raids_enhanced.content.items;

import com.finderfeed.raids_enhanced.content.entities.player_blimp.PlayerBlimpEntity;
import com.finderfeed.raids_enhanced.init.REEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public class PlayerBlimpItem extends ItemWithDescription {
   public PlayerBlimpItem(Properties props) {
      super(props, Component.translatable("raidsenhanced.item_description.player_blimp").withStyle(ChatFormatting.GOLD));
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      HitResult hitresult = getPlayerPOVHitResult(level, player, Fluid.ANY);
      if (hitresult.getType() == Type.MISS) {
         return InteractionResultHolder.pass(itemstack);
      }

      if (hitresult.getType() == Type.BLOCK) {
         PlayerBlimpEntity blimp = new PlayerBlimpEntity(REEntities.PLAYER_BLIMP.get(), level);
         blimp.setPos(hitresult.getLocation());
         blimp.setYRot(player.getYRot());
         if (!level.noCollision(blimp, blimp.getBoundingBox())) {
            return InteractionResultHolder.fail(itemstack);
         }

         if (!level.isClientSide) {
            level.addFreshEntity(blimp);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
            itemstack.consume(1, player);
         }

         player.awardStat(Stats.ITEM_USED.get(this));
         return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
      } else {
         return InteractionResultHolder.pass(itemstack);
      }
   }
}
