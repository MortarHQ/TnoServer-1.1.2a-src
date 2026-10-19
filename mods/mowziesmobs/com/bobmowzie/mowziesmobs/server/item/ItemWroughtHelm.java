package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.client.model.LayerHandler;
import com.bobmowzie.mowziesmobs.client.model.armor.WroughtHelmModel;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
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

public class ItemWroughtHelm extends ArmorItem {
   public ItemWroughtHelm(Properties properties) {
      super(MaterialHandler.ARMOR_WROUGHT_HELM, Type.HELMET, properties);
   }

   public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.breakable.get() ? super.isValidRepairItem(toRepair, repair) : false;
   }

   public boolean isEnchantable(ItemStack p_77616_1_) {
      return true;
   }

   @Nullable
   public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, Layer layer, boolean innerModel) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/item/wrought_helmet.png");
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
   }

   public static final class ArmorRender implements IClientItemExtensions {
      public static final ItemWroughtHelm.ArmorRender INSTANCE = new ItemWroughtHelm.ArmorRender();
      private static HumanoidModel<?> MODEL;

      public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
         if (MODEL == null) {
            EntityModelSet models = Minecraft.getInstance().getEntityModels();
            ModelPart root = models.bakeLayer(LayerHandler.WROUGHT_HELM_LAYER);
            MODEL = new WroughtHelmModel(root);
         }

         return MODEL;
      }
   }
}
