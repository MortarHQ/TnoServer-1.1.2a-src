package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.base.Suppliers;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import net.minecraft.world.level.Level;

public class DeepsilverArmorItem extends ArmorItem implements TickableArmor {
   private final Supplier<ItemAttributeModifiers> extraModifiers;

   public DeepsilverArmorItem(Holder<ArmorMaterial> materialHolder, Type type, Properties properties) {
      super(materialHolder, type, properties);
      this.extraModifiers = Suppliers.memoize(() -> {
         Builder builder = ItemAttributeModifiers.builder();
         EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
         ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
         builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(resourceLocation, 0.05, Operation.ADD_VALUE), equipmentSlotGroup);
         builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourceLocation, 0.01, Operation.ADD_VALUE), equipmentSlotGroup);
         return builder.build();
      });
   }

   public ItemAttributeModifiers getDefaultAttributeModifiers() {
      ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers();
      Builder builder = ItemAttributeModifiers.builder();

      for (Entry entry : modifiers.modifiers()) {
         builder.add(entry.attribute(), entry.modifier(), entry.slot());
      }

      for (Entry entry : this.extraModifiers.get().modifiers()) {
         builder.add(entry.attribute(), entry.modifier(), entry.slot());
      }

      return builder.build();
   }

   @Override
   public void tick(Level level, LivingEntity livingEntity, ItemStack armor) {
      AtomicBoolean fullSet = new AtomicBoolean(true);
      livingEntity.getArmorSlots().forEach(stack -> {
         if (!(stack.getItem() instanceof DeepsilverArmorItem)) {
            fullSet.set(false);
         }
      });
      if (fullSet.get()) {
         level.registryAccess().registryOrThrow(Registries.MOB_EFFECT).getTagOrEmpty(ESTags.MobEffects.DEEPSILVER_ARMOR_CAN_REMOVE).forEach(effect -> {
            if (livingEntity.hasEffect(effect)) {
               livingEntity.removeEffect(effect);
            }
         });
      }
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
      list.add(CommonComponents.EMPTY);
      list.add(Component.translatable("tooltip.eternal_starlight.full_set").withStyle(ChatFormatting.GRAY));
      list.add(Component.literal(" ").append(Component.translatable("tooltip.eternal_starlight.deepsilver_armor")).withStyle(ChatFormatting.YELLOW));
      super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
   }
}
