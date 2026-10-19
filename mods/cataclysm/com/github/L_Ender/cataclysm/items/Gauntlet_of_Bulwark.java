package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Attachment.ChargeAttachment;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.init.ModDataAttachments;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;

public class Gauntlet_of_Bulwark extends Cataclysm_Weapon {
   public Gauntlet_of_Bulwark(Properties group) {
      super(group);
   }

   public UseAnim getUseAnimation(ItemStack p_77661_1_) {
      return UseAnim.BOW;
   }

   public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
      return 72000;
   }

   public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
      ItemStack item = p_77659_2_.getItemInHand(p_77659_3_);
      InteractionHand otherhand = p_77659_3_ == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
      ItemStack otheritem = p_77659_2_.getItemInHand(otherhand);
      if (otheritem.canPerformAction(ItemAbilities.SHIELD_BLOCK) && !p_77659_2_.getCooldowns().isOnCooldown(otheritem.getItem())) {
         return InteractionResultHolder.fail(item);
      }

      p_77659_2_.startUsingItem(p_77659_3_);
      return InteractionResultHolder.consume(item);
   }

   public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
      double radius = 4.5;
      Level world = livingEntityIn.level();
      List<Entity> list = world.getEntities(livingEntityIn, livingEntityIn.getBoundingBox().inflate(radius));
      int c = this.getUseDuration(stack, livingEntityIn) - count;
      if (c == 20) {
         livingEntityIn.playSound((SoundEvent)ModSounds.FLAME_BURST.get(), 1.0F, 1.0F);

         for (Entity entity : list) {
            if (entity instanceof LivingEntity && (!(entity instanceof Player) || !((Player)entity).getAbilities().invulnerable)) {
               ((LivingEntity)entity).addEffect(new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND, 40));
               if (entity.onGround()) {
                  double d0 = entity.getX() - livingEntityIn.getX();
                  double d1 = entity.getZ() - livingEntityIn.getZ();
                  double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                  float f = 1.5F;
                  entity.push(d0 / d2 * f, 0.1F, d1 / d2 * f);
               }
            }
         }

         if (world.isClientSide) {
            for (int i = 0; i < 20; i++) {
               float velocity = 0.2F;
               float yaw = i * (float) (Math.PI / 10);
               float vy = world.getRandom().nextFloat() * 0.1F - 0.05F;
               float vx = 0.2F * Mth.cos(yaw);
               float vz = 0.2F * Mth.sin(yaw);
               world.addParticle(ParticleTypes.FLAME, livingEntityIn.getX(), livingEntityIn.getY() + 1.0, livingEntityIn.getZ(), vx, vy, vz);
            }
         }
      }
   }

   public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
      if (!entityLiving.isShiftKeyDown() && !entityLiving.isFallFlying()) {
         int i = this.getUseDuration(stack, entityLiving) - timeLeft;
         int t = Mth.clamp(i, 1, 5);
         float f7 = entityLiving.getYRot();
         float f = entityLiving.getXRot();
         if (i >= 20) {
            float f1 = -Mth.sin(f7 * (float) (Math.PI / 180.0)) * Mth.cos(f * (float) (Math.PI / 180.0));
            float f2 = -Mth.sin(f * (float) (Math.PI / 180.0));
            float f3 = Mth.cos(f7 * (float) (Math.PI / 180.0)) * Mth.cos(f * (float) (Math.PI / 180.0));
            float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
            float f5 = 3.0F * (t / 6.0F);
            f1 *= f5 / f4;
            f3 *= f5 / f4;
            entityLiving.push(f1, 0.0, f3);
            if (entityLiving.onGround()) {
               float f6 = 1.1999999F;
               entityLiving.move(MoverType.SELF, new Vec3(0.0, f6 / 2.0, 0.0));
            }

            ChargeAttachment charge = (ChargeAttachment)entityLiving.getData(ModDataAttachments.CHARGE_ATTACHMENT);
            charge.setCharge(true);
            charge.setTimer(t * 2);
            charge.seteffectiveChargeTime(t * 2);
            charge.setknockbackSpeedIndex(t * 0.35F);
            charge.setdamagePerEffectiveCharge(1.2F);
            charge.setdx(f1 * 0.5F);
            charge.setdZ(f3 * 0.5F);
            if (!level.isClientSide) {
               ((Player)entityLiving).getCooldowns().addCooldown(this, CMCommonConfig.GauntletOfBulwark.cooldown);
            }
         }
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
      tooltips.add(Component.translatable("item.cataclysm.gauntlet_of_bulwark.desc").withStyle(ChatFormatting.DARK_GREEN));
      tooltips.add(Component.translatable("item.cataclysm.gauntlet_of_bulwark.desc2").withStyle(ChatFormatting.DARK_GREEN));
   }
}
