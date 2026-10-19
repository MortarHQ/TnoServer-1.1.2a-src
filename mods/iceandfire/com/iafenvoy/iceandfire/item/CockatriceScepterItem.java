package com.iafenvoy.iceandfire.item;

import com.iafenvoy.iceandfire.data.component.MiscData;
import com.iafenvoy.iceandfire.entity.util.BlacklistedFromStatues;
import com.iafenvoy.iceandfire.entity.util.IafEntityUtil;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

public class CockatriceScepterItem extends Item {
   private final Random rand = new Random();
   private int specialWeaponDmg;

   public CockatriceScepterItem() {
      super(new Properties().durability(700));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(Component.translatable("item.iceandfire.legendary_weapon.desc").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.iceandfire.cockatrice_scepter.desc_0").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.iceandfire.cockatrice_scepter.desc_1").withStyle(ChatFormatting.GRAY));
   }

   public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity livingEntity, int timeLeft) {
      if (this.specialWeaponDmg > 0) {
         stack.hurtAndBreak(this.specialWeaponDmg, livingEntity, LivingEntity.getSlotForHand(livingEntity.getUsedItemHand()));
         this.specialWeaponDmg = 0;
      }

      MiscData.get(livingEntity).getTargetedByScepters().clear();
   }

   public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
      if (user instanceof Player player) {
         player.getCooldowns().addCooldown(this, 20);
      }

      return super.finishUsingItem(stack, world, user);
   }

   public int getUseDuration(ItemStack stack, LivingEntity user) {
      return 72000;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
      ItemStack itemStackIn = playerIn.getItemInHand(hand);
      playerIn.startUsingItem(hand);
      return new InteractionResultHolder(InteractionResult.PASS, itemStackIn);
   }

   public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
      if (player instanceof Player) {
         double dist = 32.0;
         Vec3 playerEyePosition = player.getEyePosition(1.0F);
         Vec3 playerLook = player.getViewVector(1.0F);
         Vec3 Vector3d2 = playerEyePosition.add(playerLook.x * dist, playerLook.y * dist, playerLook.z * dist);
         List<Entity> pointedEntities = new LinkedList<>();
         List<Entity> nearbyEntities = level.getEntities(
            player,
            player.getBoundingBox().expandTowards(playerLook.x * dist, playerLook.y * dist, playerLook.z * dist).inflate(1.0, 1.0, 1.0),
            entity -> {
               boolean blindness = entity instanceof LivingEntity && ((LivingEntity)entity).hasEffect(MobEffects.BLINDNESS)
                  || entity instanceof BlacklistedFromStatues blacklisted && !blacklisted.canBeTurnedToStone();
               return entity != null
                  && entity.isPickable()
                  && !blindness
                  && (entity instanceof Player || entity instanceof LivingEntity living && DragonUtils.isAlive(living));
            }
         );
         double d2 = dist;

         for (Entity nearbyEntity : nearbyEntities) {
            AABB axisalignedbb = nearbyEntity.getBoundingBox().inflate(nearbyEntity.getPickRadius());
            Optional<Vec3> optional = axisalignedbb.clip(playerEyePosition, Vector3d2);
            if (axisalignedbb.contains(playerEyePosition)) {
               if (d2 >= 0.0) {
                  pointedEntities.add(nearbyEntity);
                  d2 = 0.0;
               }
            } else if (optional.isPresent()) {
               double d3 = playerEyePosition.distanceTo(optional.get());
               if (d3 < d2 || d2 == 0.0) {
                  if (nearbyEntity.getRootVehicle() == player.getRootVehicle()) {
                     if (d2 == 0.0) {
                        pointedEntities.add(nearbyEntity);
                     }
                  } else {
                     pointedEntities.add(nearbyEntity);
                     d2 = d3;
                  }
               }
            }
         }

         for (Entity pointedEntity : pointedEntities) {
            if (pointedEntity instanceof LivingEntity target) {
               if (!target.isAlive()) {
                  return;
               }

               MiscData.get(player).addScepterTarget(target);
            }
         }

         this.attackTargets(player);
      }
   }

   private void attackTargets(LivingEntity caster) {
      MiscData miscData = MiscData.get(caster);
      Iterator<UUID> iterator = miscData.getTargetedByScepters().iterator();

      while (iterator.hasNext()) {
         UUID uuid = iterator.next();
         Entity entity = null;
         if (caster.level() instanceof ServerLevel serverWorld) {
            entity = serverWorld.getEntity(uuid);
         } else if (caster.level() instanceof ClientLevel clientWorld) {
            entity = (Entity)clientWorld.entityStorage.getEntityGetter().get(uuid);
         }

         if (entity instanceof LivingEntity target) {
            if (IafEntityUtil.isEntityLookingAt(caster, target, 0.2F) && !caster.isRemoved() && !target.isRemoved()) {
               target.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 2));
               if (caster.tickCount % 20 == 0) {
                  this.specialWeaponDmg++;
                  target.hurt(caster.level().damageSources.wither(), 2.0F);
               }

               this.drawParticleBeam(caster, target);
            } else {
               iterator.remove();
            }
         }
      }
   }

   private void drawParticleBeam(LivingEntity origin, LivingEntity target) {
      double d5 = 80.0;
      double d0 = target.getX() - origin.getX();
      double d1 = target.getY() + target.getBbHeight() * 0.5F - (origin.getY() + origin.getEyeHeight() * 0.5);
      double d2 = target.getZ() - origin.getZ();
      double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      d0 /= d3;
      d1 /= d3;
      d2 /= d3;
      double d4 = this.rand.nextDouble();

      while (d4 < d3) {
         origin.level()
            .addParticle(
               ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, -16777216),
               origin.getX() + d0 * ++d4,
               origin.getY() + d1 * d4 + origin.getEyeHeight() * 0.5,
               origin.getZ() + d2 * d4,
               0.0,
               0.0,
               0.0
            );
      }
   }
}
