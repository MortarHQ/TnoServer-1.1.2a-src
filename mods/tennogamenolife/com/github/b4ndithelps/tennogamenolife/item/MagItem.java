package com.github.b4ndithelps.tennogamenolife.item;

import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MagItem extends Item {
   private static final double EP_AMOUNT = 10000.0;

   public MagItem(Properties properties) {
      super(properties);
   }

   private void addEP(LivingEntity entity, double amount) {
      double halfAmount = amount / 2.0;
      EnergyHelper.gainAura(entity, halfAmount, GainType.MAX);
      EnergyHelper.gainMagicule(entity, halfAmount, GainType.MAX);
   }

   public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
      if (!player.level().isClientSide) {
         if (entity instanceof Player) {
            return InteractionResult.PASS;
         }

         if (this.applyToEntity(entity, stack, player)) {
            return InteractionResult.SUCCESS;
         }
      }

      return InteractionResult.PASS;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (!level.isClientSide) {
         Vec3 playerPos = player.position();
         Vec3 lookVec = player.getLookAngle();
         Vec3 targetPos = playerPos.add(lookVec.x * 3.0, lookVec.y * 3.0, lookVec.z * 3.0);
         AABB aabb = new AABB(targetPos.x - 1.0, targetPos.y - 1.0, targetPos.z - 1.0, targetPos.x + 1.0, targetPos.y + 1.0, targetPos.z + 1.0);

         for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, aabb)) {
            if (!(entity instanceof Player) && this.applyToEntity(entity, itemstack, player)) {
               break;
            }
         }
      }

      return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
   }

   private boolean applyToEntity(LivingEntity entity, ItemStack stack, Player player) {
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      if (existence != null && existence.getEP() > 0.0) {
         this.addEP(entity, 10000.0);
         player.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
         stack.shrink(1);
         return true;
      } else {
         return false;
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("item.tennogamenolife.mag.desc"));
      tooltip.add(Component.translatable("item.tennogamenolife.mag.desc.ep", new Object[]{10000.0}));
   }
}
