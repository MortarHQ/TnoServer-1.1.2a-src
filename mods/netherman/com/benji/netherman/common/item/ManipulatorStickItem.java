package com.benji.netherman.common.item;

import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModSounds;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ManipulatorStickItem extends SwordItem {
   public ManipulatorStickItem() {
      super(Tiers.DIAMOND, new Properties().durability(650).attributes(createAttributes()));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      Component manipulator = Component.translatable("tooltip.netherman.manipulator").withStyle(ChatFormatting.WHITE);
      tooltipComponents.add(Component.translatable("tooltip.netherman.manipulator.line1", new Object[]{manipulator}).withStyle(ChatFormatting.GOLD));
      tooltipComponents.add(manipulator);
   }

   public static ItemAttributeModifiers createAttributes() {
      return ItemAttributeModifiers.builder()
         .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 3.0, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
         .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.4, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
         .build();
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.level().isClientSide()) {
         target.addEffect(new MobEffectInstance(ModEffects.MANIPULATION, 600, 0));
         stack.hurtAndBreak(4, attacker, EquipmentSlot.MAINHAND);
      }

      return super.hurtEnemy(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (!level.isClientSide()) {
         ServerLevel serverLevel = (ServerLevel)level;
         if (player.isShiftKeyDown()) {
            if (stack.getDamageValue() + 50 > stack.getMaxDamage()) {
               return InteractionResultHolder.fail(stack);
            }

            for (double yOffset = 0.0; yOffset <= 2.0; yOffset += 0.2) {
               for (int i = 0; i < 360; i += 15) {
                  double rad = Math.toRadians(i);
                  double x = Math.cos(rad) * 2.0;
                  double z = Math.sin(rad) * 2.0;
                  serverLevel.sendParticles(DustParticleOptions.REDSTONE, player.getX() + x, player.getY() + yOffset, player.getZ() + z, 1, 0.0, 0.0, 0.0, 0.0);
               }
            }

            for (int i = 0; i < 5; i++) {
               double offsetX = (player.getRandom().nextDouble() - 0.5) * 4.0;
               double offsetZ = (player.getRandom().nextDouble() - 0.5) * 4.0;
               BlockPos spawnPos = player.blockPosition().offset((int)offsetX, 0, (int)offsetZ);
               WitherSkeleton skeleton = (WitherSkeleton)EntityType.WITHER_SKELETON.create(level);
               if (skeleton != null) {
                  skeleton.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, player.getYRot(), 0.0F);
                  skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
                  skeleton.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
                  skeleton.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
                  skeleton.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
                  skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));

                  for (EquipmentSlot slot : EquipmentSlot.values()) {
                     if (slot.isArmor() || slot == EquipmentSlot.MAINHAND) {
                        skeleton.setDropChance(slot, 0.0F);
                     }
                  }

                  skeleton.getPersistentData().putUUID("SummonerUUID", player.getUUID());
                  level.addFreshEntity(skeleton);
                  serverLevel.sendParticles(ParticleTypes.CRIMSON_SPORE, skeleton.getX(), skeleton.getY() + 1.0, skeleton.getZ(), 10, 0.3, 0.5, 0.3, 0.01);
               }
            }

            level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.SUMMON1.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            stack.hurtAndBreak(50, player, EquipmentSlot.MAINHAND);
            player.getCooldowns().addCooldown(this, 40);
            return InteractionResultHolder.success(stack);
         }

         if (stack.getDamageValue() + 20 > stack.getMaxDamage()) {
            return InteractionResultHolder.fail(stack);
         }

         Vec3 lookVec = player.getLookAngle();
         Vec3 startPos = player.getEyePosition(1.0F);
         LivingEntity hitEntity = null;

         for (double d = 0.5; d <= 15.0; d += 0.5) {
            Vec3 checkPos = startPos.add(lookVec.scale(d));
            serverLevel.sendParticles(DustParticleOptions.REDSTONE, checkPos.x, checkPos.y, checkPos.z, 1, 0.0, 0.0, 0.0, 0.0);
            if (hitEntity == null) {
               for (LivingEntity entity : level.getEntitiesOfClass(
                  LivingEntity.class, new AABB(checkPos.x, checkPos.y, checkPos.z, checkPos.x, checkPos.y, checkPos.z).inflate(0.6)
               )) {
                  if (entity != player && entity.isAlive()) {
                     hitEntity = entity;
                     break;
                  }
               }
            }
         }

         if (hitEntity != null) {
            hitEntity.addEffect(new MobEffectInstance(ModEffects.MANIPULATION, 600, 0));
            Vec3 pullVec = player.position().subtract(hitEntity.position()).normalize().scale(1.4);
            hitEntity.setDeltaMovement(pullVec.x, 0.4, pullVec.z);
            hitEntity.hurtMarked = true;
            level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.SUMMON1.get(), SoundSource.PLAYERS, 1.0F, 1.5F);
            stack.hurtAndBreak(20, player, EquipmentSlot.MAINHAND);
            return InteractionResultHolder.success(stack);
         }
      }

      return InteractionResultHolder.pass(stack);
   }
}
