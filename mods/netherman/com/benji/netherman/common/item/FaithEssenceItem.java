package com.benji.netherman.common.item;

import com.benji.netherman.QuotaManager;
import com.benji.netherman.init.ModEntities;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class FaithEssenceItem extends Item {
   public FaithEssenceItem(Properties properties) {
      super(properties);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      Component essence = Component.translatable("tooltip.netherman.essence").withStyle(ChatFormatting.GOLD);
      tooltipComponents.add(Component.translatable("tooltip.netherman.essence.line1", new Object[]{essence}).withStyle(ChatFormatting.AQUA));
      tooltipComponents.add(Component.translatable("tooltip.netherman.essence.line2", new Object[]{essence}).withStyle(ChatFormatting.BLUE));
      tooltipComponents.add(essence);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      if (!level.isClientSide() && player.getPersistentData().contains("AzazelPenaltyTime")) {
         QuotaManager.restoreHealth(player);
         player.getPersistentData().remove("AzazelPenaltyTime");
         if (!player.isCreative()) {
            player.getItemInHand(hand).shrink(1);
         }

         return InteractionResultHolder.consume(player.getItemInHand(hand));
      } else {
         return super.use(level, player, hand);
      }
   }

   public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
      Level level = player.level();
      if (level.isClientSide()) {
         return InteractionResult.SUCCESS;
      }

      boolean transformed = false;
      Entity newEntity = null;
      if (target instanceof Pillager) {
         newEntity = ((EntityType)ModEntities.BELIEVER.get()).create(level);
         transformed = true;
      } else if (target instanceof Villager) {
         if (level.random.nextFloat() < 0.1F) {
            newEntity = ((EntityType)ModEntities.WELCOMER.get()).create(level);
         } else {
            newEntity = ((EntityType)ModEntities.BELIEVER_VILLAGER.get()).create(level);
         }

         transformed = true;
      } else if (target.getMaxHealth() < 100.0F && !(target instanceof Player)) {
         newEntity = ((EntityType)ModEntities.STATUE.get()).create(level);
         transformed = true;
      }

      if (transformed && newEntity != null) {
         newEntity.moveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
         if (target.hasCustomName()) {
            newEntity.setCustomName(target.getCustomName());
         }

         level.addFreshEntity(newEntity);
         if (level instanceof ServerLevel serverLevel) {
            DustParticleOptions redMagic = new DustParticleOptions(new Vector3f(1.0F, 0.0F, 0.0F), 1.5F);
            serverLevel.sendParticles(redMagic, target.getX(), target.getY() + 1.0, target.getZ(), 40, 0.5, 0.8, 0.5, 0.1);
            serverLevel.playSound(null, target.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.2F, 0.8F);
            serverLevel.playSound(null, target.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.PLAYERS, 0.8F, 1.2F);
         }

         target.discard();
         if (target instanceof Pillager) {
            QuotaManager.addProgress(player, 4, 1);
         } else if (target instanceof Villager) {
            QuotaManager.addProgress(player, 5, 1);
         }

         if (!player.isCreative()) {
            stack.shrink(1);
         }

         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.PASS;
      }
   }
}
