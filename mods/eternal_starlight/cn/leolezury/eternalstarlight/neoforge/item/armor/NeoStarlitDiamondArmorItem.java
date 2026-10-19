package cn.leolezury.eternalstarlight.neoforge.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.Nullable;

public class NeoStarlitDiamondArmorItem extends ArmorItem {
   public NeoStarlitDiamondArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
      super(material, type, properties);
   }

   @Nullable
   public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, Layer layer, boolean innerModel) {
      return EternalStarlight.id("textures/armor/starlit_diamond_layer_" + (slot == EquipmentSlot.LEGS ? "2.png" : "1.png"));
   }
}
