package com.benji.netherman.common.item;

import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModSounds;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AzazelShieldItem extends ShieldItem implements GeoItem {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public AzazelShieldItem(Properties properties) {
      super(properties.durability(2000));
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (level.isClientSide()) {
         return InteractionResultHolder.consume(stack);
      }

      ServerLevel serverLevel = (ServerLevel)level;
      level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.SMOKE_BREATH.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      this.triggerAnim(player, GeoItem.getOrAssignId(stack, serverLevel), "controller", "ability");

      for (int i = 0; i < 60; i++) {
         double offsetX = (level.random.nextDouble() - 0.5) * 10.0;
         double offsetZ = (level.random.nextDouble() - 0.5) * 10.0;
         serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX() + offsetX, player.getY() + 1.0, player.getZ() + offsetZ, 2, 0.0, 0.0, 0.0, 0.05);
      }

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(8.0))) {
         if (target != player) {
            target.addEffect(new MobEffectInstance(ModEffects.MANIPULATION, 400, 0));
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 400, 1));
            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 400, 0));
         }
      }

      EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
      stack.hurtAndBreak(10, player, slot);
      player.getCooldowns().addCooldown(this, 200);
      return InteractionResultHolder.success(stack);
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 0;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      Component shield = Component.translatable("tooltip.netherman.shield").withStyle(ChatFormatting.GOLD);
      tooltipComponents.add(shield);
   }

   public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
      return repair.is(Items.NETHERITE_INGOT) || super.isValidRepairItem(toRepair, repair);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, event -> PlayState.CONTINUE));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
