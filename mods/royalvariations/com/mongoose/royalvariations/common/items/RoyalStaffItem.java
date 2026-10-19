package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.utils.ItemHelper;
import java.util.List;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class RoyalStaffItem extends SwordItem {
   public RoyalStaffItem() {
      super(RVTiers.ROYAL, new Properties());
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (!itemstack.is(this)) {
         return super.use(level, player, hand);
      }

      if (level instanceof ServerLevel serverLevel) {
         player.addEffect(new MobEffectInstance(RVEffects.ROYAL_BLESSING, 400, 0));
         serverLevel.sendParticles((SimpleParticleType)RVParticleTypes.SHINE.get(), player.getX(), player.getY() + 1.3, player.getZ(), 6, 0.2, 0.3, 0.2, 0.1);

         for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(13.0))) {
            if (living instanceof OwnableEntity ownable && ownable.getOwner() == player) {
               living.addEffect(new MobEffectInstance(RVEffects.ROYAL_BLESSING, 600, 1));
               serverLevel.sendParticles(
                  (SimpleParticleType)RVParticleTypes.SHINE.get(), living.getX(), living.getY() + 1.3, living.getZ(), 6, 0.2, 0.3, 0.2, 0.1
               );

               for (int i = 0; i <= serverLevel.getRandom().nextInt(10) + 10; i++) {
                  serverLevel.sendParticles(
                     (SimpleParticleType)RVParticleTypes.HOLY.get(), living.getRandomX(1.5), living.getRandomY(), living.getRandomZ(1.5), 0, 0.0, 1.0, 0.0, 1.0
                  );
               }
            }
         }
      }

      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_FALL, SoundSource.PLAYERS, 1.0F, 1.0F);
      ItemHelper.hurtAndBreak(itemstack, 1, player);
      player.getCooldowns().addCooldown(this, 300);
      return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      ItemHelper.addOnShift(tooltip, () -> this.addInformationAfterShift(tooltip));
   }

   public void addInformationAfterShift(List<Component> tooltip) {
      tooltip.add(Component.translatable("info.royalvariations.royal_staff"));
   }
}
