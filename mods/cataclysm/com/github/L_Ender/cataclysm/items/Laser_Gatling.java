package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.init.ModDataComponents;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class Laser_Gatling extends Item {
   private static final String TAG_CHARGED = "Charged";
   public static final Predicate<ItemStack> REDSTONE = stack -> stack.getItem() == Items.REDSTONE;

   public Laser_Gatling(Properties properties) {
      super(properties);
   }

   public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
      return isUsable(pStack) ? Integer.MAX_VALUE : 0;
   }

   public boolean isBarVisible(ItemStack itemStack) {
      return super.isBarVisible(itemStack) && isUsable(itemStack);
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   public static boolean isUsable(ItemStack stack) {
      return stack.getDamageValue() < stack.getMaxDamage() - 1;
   }

   public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.getItemInHand(handIn);
      playerIn.startUsingItem(handIn);
      if (!isUsable(itemstack)) {
         ItemStack ammo = this.findAmmo(playerIn);
         boolean flag = playerIn.isCreative();
         if (!ammo.isEmpty()) {
            ammo.shrink(1);
            flag = true;
         }

         if (flag) {
            itemstack.setDamageValue(0);
         }
      }

      return InteractionResultHolder.consume(itemstack);
   }

   public ItemStack findAmmo(Player entity) {
      if (entity.isCreative()) {
         return ItemStack.EMPTY;
      }

      for (int i = 0; i < entity.getInventory().getContainerSize(); i++) {
         ItemStack itemstack1 = entity.getInventory().getItem(i);
         if (REDSTONE.test(itemstack1)) {
            return itemstack1;
         }
      }

      return ItemStack.EMPTY;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return !ItemStack.isSameItem(oldStack, newStack);
   }

   public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
      if (isUsable(stack)) {
         stack.set(ModDataComponents.LASER_GATLING, false);
         if (count % 2 == 0) {
            Vec3 vector3d = livingEntityIn.getViewVector(1.0F);
            Vec3 vec3 = vector3d.normalize();
            Laser_Beam_Entity laser = new Laser_Beam_Entity(livingEntityIn, vec3, worldIn, (float)CMCommonConfig.LaserGatling.damage);
            float yRot = (float)(Mth.atan2(vec3.z, vec3.x) * (180.0 / Math.PI)) + 90.0F;
            float xRot = (float)(-(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180.0 / Math.PI)));
            laser.setYRot(yRot);
            laser.setXRot(xRot);
            laser.setPosRaw(livingEntityIn.getX(), livingEntityIn.getY() + livingEntityIn.getEyeHeight() * 0.8F, livingEntityIn.getZ());
            RandomSource rand = worldIn.getRandom();
            livingEntityIn.gameEvent(GameEvent.ITEM_INTERACT_START);
            livingEntityIn.playSound((SoundEvent)ModSounds.HARBINGER_LASER.get(), 0.2F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
            if (!worldIn.isClientSide) {
               worldIn.addFreshEntity(laser);
            }

            stack.hurtAndBreak(1, livingEntityIn, EquipmentSlot.MAINHAND);
         }
      } else if (livingEntityIn instanceof Player) {
         ItemStack ammo = this.findAmmo((Player)livingEntityIn);
         boolean flag = ((Player)livingEntityIn).isCreative();
         if (!ammo.isEmpty()) {
            ammo.shrink(1);
            flag = true;
         }

         if (flag) {
            ((Player)livingEntityIn).getCooldowns().addCooldown(this, 20);
            stack.setDamageValue(0);
         }

         livingEntityIn.stopUsingItem();
      }
   }

   public void releaseUsing(ItemStack stack, Level world, LivingEntity living, int remainingUseTicks) {
      stack.set(ModDataComponents.LASER_GATLING, false);
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean held) {
      super.inventoryTick(stack, level, entity, i, held);
      boolean using = entity instanceof LivingEntity living && living.getUseItem().equals(stack);
      if (using) {
         stack.set(ModDataComponents.LASER_GATLING, true);
      }

      if (!using) {
         stack.set(ModDataComponents.LASER_GATLING, false);
      }
   }

   public static boolean isCharged(ItemStack p_40933_) {
      return (Boolean)p_40933_.getOrDefault(ModDataComponents.LASER_GATLING, true);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
      tooltip.add(Component.translatable("item.cataclysm.laser_gatling.desc").withStyle(ChatFormatting.DARK_GREEN));
   }
}
