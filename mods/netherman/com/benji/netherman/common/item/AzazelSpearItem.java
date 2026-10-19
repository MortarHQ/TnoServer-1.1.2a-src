package com.benji.netherman.common.item;

import com.benji.netherman.common.entity.AzazelHumanEntity;
import com.benji.netherman.common.entity.AzazelSpikeEntity;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AzazelSpearItem extends SwordItem implements GeoItem {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public AzazelSpearItem(Properties properties) {
      super(AzazelTier.INSTANCE, properties.attributes(SwordItem.createAttributes(AzazelTier.INSTANCE, 11, -2.4F)));
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (!level.isClientSide()) {
         ServerLevel serverLevel = (ServerLevel)level;
         if (player.isShiftKeyDown()) {
            this.triggerAnim(player, GeoItem.getOrAssignId(stack, serverLevel), "controller", "scyth_mode");
            level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.HIRRING.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("TransformTimer", 10));
            return InteractionResultHolder.success(stack);
         }

         AABB box = player.getBoundingBox().inflate(8.0);
         List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, box, e -> e != player && !(e instanceof AzazelHumanEntity));
         if (!targets.isEmpty()) {
            for (LivingEntity target : targets) {
               AzazelSpikeEntity spike = (AzazelSpikeEntity)((EntityType)ModEntities.SPIKE_ENTITY.get()).create(level);
               if (spike != null) {
                  spike.setPos(target.getX(), target.getY(), target.getZ());
                  spike.setOwner(player);
                  level.addFreshEntity(spike);
               }
            }

            level.playSound(null, player.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.5F, 0.8F);
            EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            stack.hurtAndBreak(5, player, slot);
            player.getCooldowns().addCooldown(this, 100);
            this.triggerAnim(player, GeoItem.getOrAssignId(stack, serverLevel), "controller", "spear_attack");
            return InteractionResultHolder.success(stack);
         }
      }

      return InteractionResultHolder.pass(stack);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      Component spear = Component.translatable("tooltip.netherman.spear").withStyle(ChatFormatting.DARK_RED);
      tooltipComponents.add(Component.translatable("tooltip.netherman.spear.line1", new Object[]{spear}).withStyle(ChatFormatting.GOLD));
      tooltipComponents.add(spear);
   }

   public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!target.level().isClientSide() && attacker instanceof Player player) {
         Vec3 look = player.getLookAngle();
         target.setDeltaMovement(target.getDeltaMovement().add(look.x * 2.0, 0.4, look.z * 2.0));
         this.triggerAnim(player, GeoItem.getOrAssignId(stack, (ServerLevel)target.level()), "controller", "spear_attack");
      }

      super.postHurtEnemy(stack, target, attacker);
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      if (!level.isClientSide() && entity instanceof Player player) {
         CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
         if (customData.contains("TransformTimer")) {
            int timer = customData.copyTag().getInt("TransformTimer");
            if (timer > 0) {
               int finalTimer = timer - 1;
               CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("TransformTimer", finalTimer));
               if (finalTimer <= 0) {
                  ItemStack scytheStack = new ItemStack((ItemLike)ModItems.AZAZEL_SCYTHE.get());
                  scytheStack.applyComponents(stack.getComponents());
                  CustomData.update(DataComponents.CUSTOM_DATA, scytheStack, tag -> tag.remove("TransformTimer"));
                  player.getInventory().setItem(slotId, scytheStack);
               }
            }
         }
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, event -> PlayState.CONTINUE));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
