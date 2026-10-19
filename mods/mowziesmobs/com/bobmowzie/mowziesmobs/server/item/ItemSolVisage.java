package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.client.render.entity.RenderUmvuthi;
import com.bobmowzie.mowziesmobs.client.render.item.RenderSolVisageArmor;
import com.bobmowzie.mowziesmobs.client.render.item.RenderSolVisageItem;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
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

public class ItemSolVisage extends ArmorItem implements UmvuthanaMask, GeoItem {
   public String controllerName = "controller";
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public ItemSolVisage(Properties properties) {
      super(MaterialHandler.SOL_VISAGE_MATERIAL, Type.HELMET, properties);
   }

   public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get() ? super.isValidRepairItem(toRepair, repair) : false;
   }

   public boolean isEnchantable(ItemStack p_77616_1_) {
      return true;
   }

   public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
      return true;
   }

   @Nullable
   public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, Layer layer, boolean innerModel) {
      return RenderUmvuthi.TEXTURE;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.1").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.2").setStyle(ItemHandler.TOOLTIP_STYLE));
   }

   private PlayState predicate(AnimationState<ItemSolVisage> state) {
      return PlayState.STOP;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, this.controllerName, 0, this::predicate));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public static class ClientExtensions implements IClientItemExtensions {
      private final BlockEntityWithoutLevelRenderer itemRenderer = new RenderSolVisageItem();
      private GeoArmorRenderer<?> armorRenderer;

      public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
         if (this.armorRenderer == null) {
            this.armorRenderer = new RenderSolVisageArmor();
         }

         if (equipmentSlot == EquipmentSlot.HEAD) {
            this.armorRenderer.prepForRender(entityLiving, itemStack, equipmentSlot, original);
         }

         return this.armorRenderer;
      }

      public BlockEntityWithoutLevelRenderer getCustomRenderer() {
         return this.itemRenderer;
      }
   }
}
