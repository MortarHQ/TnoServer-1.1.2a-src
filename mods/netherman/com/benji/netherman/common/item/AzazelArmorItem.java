package com.benji.netherman.common.item;

import com.benji.netherman.client.renderer.AzazelArmorRenderer;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AzazelArmorItem extends ArmorItem implements GeoItem {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public AzazelArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
      super(material, type, properties.fireResistant());
   }

   public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
      consumer.accept(
         new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(
               @Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original
            ) {
               if (this.renderer == null) {
                  this.renderer = new AzazelArmorRenderer();
               }

               return this.renderer;
            }
         }
      );
   }

   public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
      return this.getType() == Type.CHESTPLATE;
   }

   public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
      if (!entity.level().isClientSide && (flightTicks + 1) % 20 == 0) {
         stack.hurtAndBreak(1, entity, EquipmentSlot.CHEST);
      }

      return true;
   }

   public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
      return repair.is(Items.NETHERITE_INGOT) || super.isValidRepairItem(toRepair, repair);
   }

   public boolean isEnchantable(ItemStack stack) {
      return true;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(
            this,
            "controller",
            10,
            event -> {
               Entity entity = (Entity)event.getData(DataTickets.ENTITY);
               if (entity instanceof LivingEntity wearer) {
                  if (wearer.isFallFlying()) {
                     return event.setAndContinue(RawAnimation.begin().thenLoop("wings_fly"));
                  } else {
                     return wearer.fallDistance > 0.5F && !wearer.onGround()
                        ? event.setAndContinue(RawAnimation.begin().thenLoop("fall"))
                        : event.setAndContinue(RawAnimation.begin().thenLoop("wings_idle"));
                  }
               } else {
                  return PlayState.STOP;
               }
            }
         )
      );
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      Type type = this.getType();
      switch (type) {
         case HELMET:
            tooltipComponents.add(Component.translatable("tooltip.netherman.azazel_helmet.line1").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("tooltip.netherman.azazel_helmet.line2").withStyle(ChatFormatting.YELLOW));
            break;
         case CHESTPLATE:
            tooltipComponents.add(Component.translatable("tooltip.netherman.azazel_chestplate.line1").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("tooltip.netherman.azazel_chestplate.line2").withStyle(ChatFormatting.YELLOW));
            tooltipComponents.add(Component.translatable("tooltip.netherman.azazel_chestplate.line3").withStyle(ChatFormatting.AQUA));
            break;
         case LEGGINGS:
            tooltipComponents.add(Component.translatable("tooltip.netherman.azazel_leggings.line1").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("tooltip.netherman.azazel_leggings.line2").withStyle(ChatFormatting.YELLOW));
            break;
         case BOOTS:
            tooltipComponents.add(Component.translatable("tooltip.netherman.azazel_boots.line1").withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("tooltip.netherman.azazel_boots.line2").withStyle(ChatFormatting.YELLOW));
      }

      tooltipComponents.add(Component.empty());
      tooltipComponents.add(Component.translatable("tooltip.netherman.azazel_set_bonus").withStyle(ChatFormatting.DARK_RED));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
