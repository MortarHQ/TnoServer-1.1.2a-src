package net.blay09.mods.waystones.item;

import java.util.Random;
import net.blay09.mods.waystones.compat.Compat;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class ScrollItemBase extends Item {
   private static final Random random = new Random();

   public ScrollItemBase(Properties properties) {
      super(properties);
   }

   public UseAnim getUseAnimation(ItemStack itemStack) {
      return WaystonesConfig.getActive().general.scrollUseTime > 0 && !Compat.isVivecraftInstalled ? UseAnim.BOW : UseAnim.NONE;
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (!player.isUsingItem() && !world.isClientSide) {
         world.playSound(null, player, SoundEvents.PORTAL_TRIGGER, SoundSource.PLAYERS, 0.1F, 2.0F);
      }

      if (this.getUseDuration(itemStack, player) > 0 && !Compat.isVivecraftInstalled) {
         player.startUsingItem(hand);
      } else {
         this.finishUsingItem(itemStack, world, player);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, itemStack);
   }

   public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int remainingTicks) {
      if (level.isClientSide) {
         int duration = this.getUseDuration(itemStack, entity);
         float progress = (float)(duration - remainingTicks) / duration;
         int maxParticles = Math.max(4, (int)(progress * 48.0F));
         if (remainingTicks % 5 == 0) {
            for (int i = 0; i < maxParticles; i++) {
               level.addParticle(
                  ParticleTypes.REVERSE_PORTAL,
                  entity.getX() + (random.nextDouble() - 0.5) * 1.5,
                  entity.getY() + random.nextDouble(),
                  entity.getZ() + (random.nextDouble() - 0.5) * 1.5,
                  0.0,
                  random.nextDouble(),
                  0.0
               );
            }

            if (progress >= 0.25F) {
               for (int i = 0; i < maxParticles; i++) {
                  level.addParticle(
                     ParticleTypes.CRIMSON_SPORE,
                     entity.getX() + (random.nextDouble() - 0.5) * 1.5,
                     entity.getY() + random.nextDouble(),
                     entity.getZ() + (random.nextDouble() - 0.5) * 1.5,
                     0.0,
                     random.nextDouble(),
                     0.0
                  );
               }
            }

            if (progress >= 0.5F) {
               for (int i = 0; i < maxParticles / 3; i++) {
                  level.addParticle(
                     ParticleTypes.WITCH,
                     entity.getX() + (random.nextDouble() - 0.5) * 1.5,
                     entity.getY() + 0.5 + random.nextDouble(),
                     entity.getZ() + (random.nextDouble() - 0.5) * 1.5,
                     0.0,
                     random.nextDouble(),
                     0.0
                  );
               }
            }
         }

         if (remainingTicks == 1) {
            for (int i = 0; i < maxParticles; i++) {
               level.addParticle(
                  ParticleTypes.PORTAL,
                  entity.getX() + (random.nextDouble() - 0.5) * 1.5,
                  entity.getY() + random.nextDouble() + 1.0,
                  entity.getZ() + (random.nextDouble() - 0.5) * 1.5,
                  (random.nextDouble() - 0.5) * 0.0,
                  random.nextDouble(),
                  (random.nextDouble() - 0.5) * 0.0
               );
            }
         }
      }
   }
}
