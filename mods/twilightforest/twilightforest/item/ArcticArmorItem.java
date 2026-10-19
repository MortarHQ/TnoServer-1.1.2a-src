package twilightforest.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.DyedItemColor;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.armor.TFArmorModel;
import twilightforest.client.renderer.armor.TFSimpleArmorRenderer;
import twilightforest.init.TFItems;

public class ArcticArmorItem extends ArmorItem {
   private static final MutableComponent TOOLTIP = Component.translatable("item.twilightforest.arctic_armor.desc").withStyle(ChatFormatting.GRAY);
   public static final int DEFAULT_COLOR = -4337703;

   public ArcticArmorItem(Holder<ArmorMaterial> armorMaterial, Type type, Properties properties) {
      super(armorMaterial, type, properties);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(TOOLTIP);
   }

   public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
      return stack.is((Item)TFItems.ARCTIC_BOOTS.get());
   }

   public static final class ArmorRender extends TFSimpleArmorRenderer {
      public ArmorRender() {
         super(TFArmorModel::new, TFModelLayers.ARCTIC_ARMOR_INNER, TFModelLayers.ARCTIC_ARMOR_OUTER);
      }

      public int getDefaultDyeColor(ItemStack stack) {
         return ARGB32.opaque(DyedItemColor.getOrDefault(stack, -4337703));
      }
   }
}
