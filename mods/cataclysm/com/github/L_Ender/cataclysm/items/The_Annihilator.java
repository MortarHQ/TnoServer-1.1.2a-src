package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.client.particle.Options.RingParticleOptions;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class The_Annihilator extends Cataclysm_Weapon {
   public The_Annihilator(Properties group) {
      super(group);
   }

   public UseAnim getUseAnimation(ItemStack p_77661_1_) {
      return UseAnim.SPEAR;
   }

   public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
      return 72000;
   }

   public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
      if (p_43396_ instanceof Player player) {
         int i = this.getUseDuration(p_43394_, p_43396_) - p_43397_;
         if (i >= 40) {
            this.yall(p_43395_, p_43396_);
            if (!p_43395_.isClientSide) {
               player.getCooldowns().addCooldown(this, 100);
            }
         }
      }
   }

   public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
      int i = this.getUseDuration(stack, livingEntityIn) - count;
      if (i == 10) {
         this.masseffectParticle(worldIn, livingEntityIn, 2.0F);
      }

      if (i == 20) {
         this.masseffectParticle(worldIn, livingEntityIn, 3.5F);
      }

      if (i == 30) {
         this.masseffectParticle(worldIn, livingEntityIn, 5.0F);
      }

      if (i == 40) {
         livingEntityIn.playSound((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F);
      }
   }

   private void yall(Level world, LivingEntity caster) {
      double radius = 6.0;
      ScreenShake_Entity.ScreenShake(world, caster.position(), 30.0F, 0.1F, 0, 30);
      world.playSound(
         null,
         caster.getX(),
         caster.getY(),
         caster.getZ(),
         (SoundEvent)ModSounds.EXPLOSION.get(),
         SoundSource.PLAYERS,
         1.5F,
         1.0F / (caster.getRandom().nextFloat() * 0.4F + 0.8F)
      );

      for (Entity entity : world.getEntities(caster, caster.getBoundingBox().inflate(radius, radius, radius))) {
         if (entity instanceof LivingEntity) {
            entity.hurt(world.damageSources().mobAttack(caster), (float)caster.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0F);
         }
      }

      if (world.isClientSide) {
         world.addParticle(
            new RingParticleOptions(0.0F, (float) (Math.PI / 2), 30, 86, 236, 204, 1.0F, 85.0F, false, 0),
            caster.getX(),
            caster.getY() + 0.03F,
            caster.getZ(),
            0.0,
            0.0,
            0.0
         );
      }
   }

   private void masseffectParticle(Level world, LivingEntity caster, float radius) {
      if (world.isClientSide) {
         for (int j = 0; j < 70; j++) {
            float angle = (float)(Math.random() * 2.0 * Math.PI);
            double distance = Math.sqrt(Math.random()) * radius;
            double extraX = caster.getX() + distance * Mth.cos(angle);
            double extraY = caster.getY() + 0.3F;
            double extraZ = caster.getZ() + distance * Mth.sin(angle);
            world.addParticle((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), extraX, extraY, extraZ, 0.0, world.random.nextGaussian() * 0.04, 0.0);
         }
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      ItemStack otherHand = hand == InteractionHand.MAIN_HAND
         ? player.getItemInHand(InteractionHand.OFF_HAND)
         : player.getItemInHand(InteractionHand.MAIN_HAND);
      if (otherHand.is((Item)ModItems.THE_ANNIHILATOR.get())) {
         player.startUsingItem(hand);
         return InteractionResultHolder.consume(itemstack);
      } else {
         return InteractionResultHolder.fail(itemstack);
      }
   }

   public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
      return true;
   }

   public boolean isEnchantable(ItemStack stack) {
      return true;
   }

   public int getEnchantmentValue() {
      return 16;
   }

   public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
      return !player.isCreative();
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flags) {
      tooltips.add(Component.translatable("item.cataclysm.annihilator.desc").withStyle(ChatFormatting.DARK_GREEN));
      tooltips.add(Component.translatable("item.cataclysm.annihilator2.desc").withStyle(ChatFormatting.DARK_GREEN));
   }
}
