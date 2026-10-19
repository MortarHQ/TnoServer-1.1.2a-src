package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class RoyalBoneItem extends Item {
   public RoyalBoneItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
   }

   public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand p_41401_) {
      if (livingEntity instanceof Wolf wolf && !wolf.isTame() && !wolf.isAngry()) {
         if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
         }

         wolf.tame(player);
         wolf.getNavigation().stop();
         wolf.setTarget(null);
         wolf.setOrderedToSit(true);
         wolf.level().broadcastEntityEvent(wolf, (byte)7);
         if (wolf.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i <= serverLevel.getRandom().nextIntBetweenInclusive(5, 6); i++) {
               double d0 = serverLevel.random.nextGaussian() * 0.02;
               double d1 = serverLevel.random.nextGaussian() * 0.02;
               double d2 = serverLevel.random.nextGaussian() * 0.02;
               serverLevel.sendParticles(
                  (SimpleParticleType)RVParticleTypes.SHINE.get(), wolf.getRandomX(1.0), wolf.getRandomY(), wolf.getRandomZ(1.0), 0, d0, d1, d2, 1.0
               );
            }
         }

         return InteractionResult.SUCCESS;
      } else {
         return super.interactLivingEntity(itemStack, player, livingEntity, p_41401_);
      }
   }
}
