package team.lodestar.lodestone.systems.enchanting;

import com.google.common.collect.HashMultimap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import team.lodestar.lodestone.registry.common.LodestoneEnchantmentComponents;
import team.lodestar.lodestone.registry.common.tag.LodestoneItemTags;

public record LodestoneSlotBasedEnchantmentAttributeEffect(ResourceLocation id, Holder<Attribute> attribute, LevelBasedValue amount, Operation operation)
   implements EnchantmentLocationBasedEffect {
   public static final MapCodec<LodestoneSlotBasedEnchantmentAttributeEffect> CODEC = RecordCodecBuilder.mapCodec(
      obj -> obj.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(LodestoneSlotBasedEnchantmentAttributeEffect::id),
            Attribute.CODEC.fieldOf("attribute").forGetter(LodestoneSlotBasedEnchantmentAttributeEffect::attribute),
            LevelBasedValue.CODEC.fieldOf("amount").forGetter(LodestoneSlotBasedEnchantmentAttributeEffect::amount),
            Operation.CODEC.fieldOf("operation").forGetter(LodestoneSlotBasedEnchantmentAttributeEffect::operation)
         )
         .apply(obj, LodestoneSlotBasedEnchantmentAttributeEffect::new)
   );

   public static void modifyAttributes(ItemAttributeModifierEvent event) {
      ItemStack stack = event.getItemStack();
      if (!stack.is(LodestoneItemTags.ENCHANTMENT_HOLDER)) {
         EnchantmentHelper.runIterationOnItem(stack, (holder, level) -> {
            Enchantment enchantment = (Enchantment)holder.value();
            enchantment.getEffects(LodestoneEnchantmentComponents.SLOT_BOUND_ATTRIBUTES.get()).forEach(effect -> {
               EquipmentSlot equipmentSlot = stack.getEquipmentSlot();
               if (equipmentSlot == null && stack.getItem() instanceof ArmorItem armorItem) {
                  equipmentSlot = armorItem.getEquipmentSlot();
               }

               EquipmentSlotGroup group = null;
               if (equipmentSlot != null) {
                  EquipmentSlot finalEquipmentSlot = equipmentSlot;
                  if (enchantment.definition().slots().stream().anyMatch(g -> g.test(finalEquipmentSlot))) {
                     group = EquipmentSlotGroup.bySlot(equipmentSlot);
                  }
               }

               if (group == null) {
                  group = EquipmentSlotGroup.ANY;
               }

               AttributeModifier modifier = effect.getModifier(level, group);
               event.addModifier(effect.attribute(), modifier, group);
            });
         });
      }
   }

   private ResourceLocation idForSlot(StringRepresentable slot) {
      return this.id.withSuffix("/" + slot.getSerializedName());
   }

   public AttributeModifier getModifier(int enchantmentLevel, StringRepresentable slot) {
      return new AttributeModifier(this.idForSlot(slot), this.amount().calculate(enchantmentLevel), this.operation());
   }

   public void onChangedBlock(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 pos, boolean applyTransientEffects) {
      if (applyTransientEffects && entity instanceof LivingEntity livingentity) {
         livingentity.getAttributes().addTransientAttributeModifiers(this.makeAttributeMap(enchantmentLevel, item));
      }
   }

   public void onDeactivated(EnchantedItemInUse item, Entity entity, Vec3 pos, int enchantmentLevel) {
      if (entity instanceof LivingEntity livingentity) {
         livingentity.getAttributes().removeAttributeModifiers(this.makeAttributeMap(enchantmentLevel, item));
      }
   }

   private HashMultimap<Holder<Attribute>, AttributeModifier> makeAttributeMap(int enchantmentLevel, EnchantedItemInUse item) {
      HashMultimap<Holder<Attribute>, AttributeModifier> hashmultimap = HashMultimap.create();
      hashmultimap.put(this.attribute, this.getModifier(enchantmentLevel, item.inSlot()));
      return hashmultimap;
   }

   public MapCodec<LodestoneSlotBasedEnchantmentAttributeEffect> codec() {
      return CODEC;
   }
}
