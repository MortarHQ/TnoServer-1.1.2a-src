package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.client.particles.RoyalGlowParticleOption;
import com.mongoose.royalvariations.common.attachments.RVCapHelper;
import com.mongoose.royalvariations.common.entities.RVEntityType;
import com.mongoose.royalvariations.common.events.TimedEvents;
import com.mongoose.royalvariations.utils.EventTask;
import com.mongoose.royalvariations.utils.ItemHelper;
import java.util.List;
import java.util.UUID;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class SpiritualCrownItem extends Item {
   public SpiritualCrownItem() {
      super(new Properties());
   }

   public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
      if (!RVCapHelper.isFlashing(target) && player.level() instanceof ServerLevel serverLevel && target.isAlive()) {
         EntityType<? extends Mob> entityType = null;
         if (target instanceof Zombie) {
            entityType = (EntityType<? extends Mob>)RVEntityType.ROYAL_ZOMBIE.get();
         } else if (target instanceof AbstractSkeleton) {
            entityType = (EntityType<? extends Mob>)RVEntityType.ROYAL_SKELETON.get();
         } else if (target instanceof Creeper) {
            entityType = (EntityType<? extends Mob>)RVEntityType.ROYAL_CREEPER.get();
         } else if (target instanceof EnderMan) {
            entityType = (EntityType<? extends Mob>)RVEntityType.ROYAL_ENDERMAN.get();
         }

         if (entityType != null) {
            TimedEvents.submitTask("royalvariations:convert", new SpiritualCrownItem.ConversionTask(target.getUUID(), serverLevel, entityType));
            serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.AMETHYST_CLUSTER_FALL, SoundSource.NEUTRAL, 0.9F, 1.0F);
            if (player instanceof ServerPlayer serverPlayer) {
               CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            }

            player.swing(hand);
            stack.shrink(1);
            return InteractionResult.SUCCESS;
         }
      }

      return InteractionResult.PASS;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      ItemHelper.addOnShift(tooltip, () -> this.addInformationAfterShift(tooltip));
   }

   public void addInformationAfterShift(List<Component> tooltip) {
      tooltip.add(Component.translatable("info.royalvariations.spiritual_crown"));
      tooltip.add(Component.translatable("info.royalvariations.spiritual_crown_disclaimer"));
   }

   public static class ConversionTask implements EventTask {
      public UUID entity;
      public ServerLevel level;
      public EntityType<? extends Mob> convertTo;

      public ConversionTask(UUID entity, ServerLevel level, EntityType<? extends Mob> convertTo) {
         this.entity = entity;
         this.level = level;
         this.convertTo = convertTo;
      }

      @Override
      public void startTask() {
         if (this.level.getEntity(this.entity) instanceof Mob mob) {
            RVCapHelper.setFlashTick(mob, 10);
         }
      }

      @Override
      public void tickTask() {
         if (this.level.getEntity(this.entity) instanceof Mob mob) {
            mob.getNavigation().stop();
            mob.getMoveControl().strafe(0.0F, 0.0F);
         }
      }

      @Override
      public boolean getAsBoolean() {
         boolean flag = this.convertTo == null;
         if (this.level.getEntity(this.entity) instanceof Mob mob) {
            if (!RVCapHelper.isFlashing(mob)) {
               flag = true;
            }

            if (flag) {
               Mob converted = mob.convertTo(this.convertTo, true);
               this.level.sendParticles((SimpleParticleType)RVParticleTypes.SPARKLE.get(), mob.getX(), mob.getY() + 1.5, mob.getZ(), 5, 0.4, 0.1, 0.4, 0.1);

               for (int i = 0; i <= this.level.getRandom().nextInt(10) + 10; i++) {
                  this.level
                     .sendParticles(
                        (SimpleParticleType)RVParticleTypes.HOLY.get(), mob.getRandomX(1.5), mob.getRandomY(), mob.getRandomZ(1.5), 0, 0.0, 1.0, 0.0, 1.0
                     );
               }

               this.level
                  .sendParticles(
                     new RoyalGlowParticleOption(1.0F, 1.0F, 1.0F, 1.25F, 2),
                     mob.getX(),
                     mob.getY() + (mob.getBbHeight() / 2.0 - 0.2),
                     mob.getZ(),
                     1,
                     0.0,
                     0.0,
                     0.0,
                     0.0
                  );
               this.level.playSound(null, mob.getX(), mob.getY(), mob.getZ(), SoundEvents.GLOW_SQUID_SQUIRT, SoundSource.NEUTRAL, 0.8F, 1.0F);
               if (converted != null) {
                  converted.setYRot(mob.getYRot());
                  converted.setYHeadRot(mob.getYHeadRot());
                  converted.setYBodyRot(mob.getYRot());
                  converted.setXRot(mob.getXRot());
               }
            }
         } else {
            flag = true;
         }

         return flag;
      }
   }
}
