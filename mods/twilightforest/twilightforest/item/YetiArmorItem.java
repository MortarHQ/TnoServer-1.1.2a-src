package twilightforest.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.armor.YetiArmorModel;
import twilightforest.init.TFItems;

public class YetiArmorItem extends ArmorItem {
   private static final MutableComponent TOOLTIP = Component.translatable("item.twilightforest.yeti_armor.desc")
      .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY));

   public YetiArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
      super(material, type, properties);
   }

   public void appendHoverText(ItemStack stack, @Nullable TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(TOOLTIP);
   }

   public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
      return stack.is((Item)TFItems.YETI_BOOTS.get());
   }

   public static final class ArmorRender implements IClientItemExtensions {
      public static final YetiArmorItem.ArmorRender INSTANCE = new YetiArmorItem.ArmorRender();
      private static final Lazy<HumanoidModel<?>> INNER_ARMOR_MODEL = Lazy.of(
         () -> new YetiArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(TFModelLayers.YETI_ARMOR_INNER))
      );
      private static final Lazy<HumanoidModel<?>> OUTER_ARMOR_MODEL = Lazy.of(
         () -> new YetiArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(TFModelLayers.YETI_ARMOR_OUTER))
      );

      @NotNull
      public HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> model) {
         return slot == EquipmentSlot.LEGS ? (HumanoidModel)INNER_ARMOR_MODEL.get() : (HumanoidModel)OUTER_ARMOR_MODEL.get();
      }
   }
}
