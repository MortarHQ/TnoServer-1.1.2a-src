package com.iafenvoy.iceandfire.item;

import com.google.common.base.Predicate;
import com.iafenvoy.iceandfire.entity.StoneStatueEntity;
import com.iafenvoy.iceandfire.entity.util.BlacklistedFromStatues;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.registry.IafDamageTypes;
import com.iafenvoy.iceandfire.registry.IafDataComponents;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.iceandfire.registry.tag.IafEntityTags;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GorgonHeadItem extends Item {
   public GorgonHeadItem() {
      super(new Properties().durability(1));
   }

   public int getUseDuration(ItemStack stack, LivingEntity user) {
      return 72000;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entity, int timeLeft) {
      double dist = 32.0;
      Vec3 Vector3d = entity.getEyePosition(1.0F);
      Vec3 Vector3d1 = entity.getViewVector(1.0F);
      Vec3 Vector3d2 = Vector3d.add(Vector3d1.x * dist, Vector3d1.y * dist, Vector3d1.z * dist);
      Entity pointedEntity = null;
      List<Entity> list = worldIn.getEntities(
         entity,
         entity.getBoundingBox().expandTowards(Vector3d1.x * dist, Vector3d1.y * dist, Vector3d1.z * dist).inflate(1.0, 1.0, 1.0),
         (Predicate)entity12 -> {
            if (!(entity12 instanceof LivingEntity livingEntity)) {
               return false;
            } else {
               boolean isImmune = livingEntity instanceof BlacklistedFromStatues blacklisted && !blacklisted.canBeTurnedToStone()
                  || entity12.getType().is(IafEntityTags.IMMUNE_TO_GORGON_STONE)
                  || livingEntity.hasEffect(MobEffects.BLINDNESS);
               return !isImmune && entity12.isPickable() && !livingEntity.isDeadOrDying() && (entity12 instanceof Player || DragonUtils.isAlive(livingEntity));
            }
         }
      );
      double d2 = dist;

      for (Entity entity1 : list) {
         AABB axisalignedbb = entity1.getBoundingBox().inflate(entity1.getPickRadius());
         Optional<Vec3> optional = axisalignedbb.clip(Vector3d, Vector3d2);
         if (axisalignedbb.contains(Vector3d)) {
            if (d2 >= 0.0) {
               d2 = 0.0;
            }
         } else if (optional.isPresent()) {
            double d3 = Vector3d.distanceTo(optional.get());
            if (d3 < d2 || d2 == 0.0) {
               if (entity1.getRootVehicle() == entity.getRootVehicle()) {
                  if (d2 == 0.0) {
                     pointedEntity = entity1;
                  }
               } else {
                  pointedEntity = entity1;
                  d2 = d3;
               }
            }
         }
      }

      if (pointedEntity != null && pointedEntity instanceof LivingEntity livingEntity) {
         boolean wasSuccesful = true;
         if (pointedEntity instanceof Player) {
            wasSuccesful = pointedEntity.hurt(IafDamageTypes.causeGorgonDamage(pointedEntity), 2.1474836E9F);
         } else if (!worldIn.isClientSide) {
            pointedEntity.remove(RemovalReason.KILLED);
         }

         if (wasSuccesful) {
            pointedEntity.playSound((SoundEvent)IafSounds.TURN_STONE.get(), 1.0F, 1.0F);
            StoneStatueEntity statue = StoneStatueEntity.buildStatueEntity(livingEntity);
            statue.absMoveTo(pointedEntity.getX(), pointedEntity.getY(), pointedEntity.getZ(), pointedEntity.getYRot(), pointedEntity.getXRot());
            statue.yBodyRot = pointedEntity.getYRot();
            if (!worldIn.isClientSide) {
               worldIn.addFreshEntity(statue);
            }
         }

         if (entity instanceof Player player && !player.isCreative()) {
            stack.shrink(1);
         }
      }

      stack.remove((DataComponentType)IafDataComponents.ACTIVE.get());
   }

   public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
      ItemStack itemStackIn = playerIn.getItemInHand(hand);
      playerIn.startUsingItem(hand);
      itemStackIn.set((DataComponentType)IafDataComponents.ACTIVE.get(), Unit.INSTANCE);
      return new InteractionResultHolder(InteractionResult.SUCCESS, itemStackIn);
   }

   public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(Component.translatable("item.iceandfire.legendary_weapon.desc").withStyle(ChatFormatting.GRAY));
   }
}
