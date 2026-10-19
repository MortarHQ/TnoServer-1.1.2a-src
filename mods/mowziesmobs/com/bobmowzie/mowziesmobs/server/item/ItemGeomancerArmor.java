package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.client.render.item.RenderGeomancerArmor;
import java.util.List;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ItemGeomancerArmor extends ArmorItem implements GeoItem {
   public String controllerName = "controller";
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public ItemGeomancerArmor(Type slot, Properties builderIn) {
      super(MaterialHandler.GEOMANCER_ARMOR_MATERIAL, slot, builderIn);
   }

   private PlayState predicate(AnimationState<ItemGeomancerArmor> state) {
      return PlayState.STOP;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, this.controllerName, 0, this::predicate));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
   }

   public static class ClientExtensions implements IClientItemExtensions {
      private GeoArmorRenderer<?> armorRenderer;

      public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
         if (this.armorRenderer == null) {
            this.armorRenderer = new RenderGeomancerArmor();
         }

         this.armorRenderer.prepForRender(entityLiving, itemStack, equipmentSlot, original);
         return this.armorRenderer;
      }
   }
}
