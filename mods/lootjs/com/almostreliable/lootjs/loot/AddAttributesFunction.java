package com.almostreliable.lootjs.loot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class AddAttributesFunction implements LootItemFunction {
   private final boolean preserveDefaultModifier;
   private final List<AddAttributesFunction.Modifier> modifiers;

   public AddAttributesFunction(boolean preserveDefaultModifier, List<AddAttributesFunction.Modifier> modifiers) {
      this.preserveDefaultModifier = preserveDefaultModifier;
      this.modifiers = modifiers;
   }

   public ItemStack apply(ItemStack itemStack, LootContext context) {
      net.minecraft.world.item.component.ItemAttributeModifiers.Builder stackModifiers = ItemAttributeModifiers.builder();
      ItemAttributeModifiers existing = (ItemAttributeModifiers)itemStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
      if (existing != null && this.preserveDefaultModifier) {
         for (Entry entry : existing.modifiers()) {
            stackModifiers.add(entry.attribute(), entry.modifier(), entry.slot());
         }
      }

      EquipmentSlot slot = itemStack.getEquipmentSlot();
      if (slot == null) {
         slot = EquipmentSlot.MAINHAND;
      }

      for (AddAttributesFunction.Modifier modifier : this.modifiers) {
         if (context.getRandom().nextFloat() < modifier.probability) {
            AttributeModifier am = modifier.createAttributeModifier(context);

            for (EquipmentSlotGroup slotGroup : modifier.slots) {
               if (slotGroup.test(slot)) {
                  stackModifiers.add(modifier.attribute, am, slotGroup);
               }
            }
         }
      }

      return itemStack;
   }

   public LootItemFunctionType getType() {
      throw new UnsupportedOperationException("Do not call");
   }

   public static class Builder implements net.minecraft.world.level.storage.loot.functions.LootItemFunction.Builder {
      private final List<AddAttributesFunction.Modifier> modifiers = new ArrayList<>();
      private boolean preserveDefaults = true;

      public AddAttributesFunction.Builder preserveDefaults(boolean flag) {
         this.preserveDefaults = flag;
         return this;
      }

      public AddAttributesFunction.Builder simple(Attribute attribute, ResourceLocation name, NumberProvider amount) {
         return this.simple(1.0F, attribute, name, amount);
      }

      public AddAttributesFunction.Builder simple(float probability, Attribute attribute, ResourceLocation name, NumberProvider amount) {
         return this.add(attribute, name, amount, m -> m.setProbability(probability));
      }

      public AddAttributesFunction.Builder forSlots(Attribute attribute, ResourceLocation name, NumberProvider amount, EquipmentSlotGroup[] slots) {
         return this.add(attribute, name, amount, m -> m.setSlots(slots));
      }

      public AddAttributesFunction.Builder forSlots(
         float probability, Attribute attribute, ResourceLocation name, NumberProvider amount, EquipmentSlotGroup[] slots
      ) {
         return this.add(attribute, name, amount, m -> {
            m.setProbability(probability);
            m.setSlots(slots);
         });
      }

      public AddAttributesFunction.Builder add(
         Attribute attribute, ResourceLocation name, NumberProvider amount, Consumer<AddAttributesFunction.Modifier.Builder> action
      ) {
         AddAttributesFunction.Modifier.Builder builder = new AddAttributesFunction.Modifier.Builder(attribute, name, amount);
         action.accept(builder);
         return this.add(builder.build());
      }

      public AddAttributesFunction.Builder add(AddAttributesFunction.Modifier modifier) {
         Objects.requireNonNull(modifier);
         this.modifiers.add(modifier);
         return this;
      }

      public AddAttributesFunction build() {
         return new AddAttributesFunction(this.preserveDefaults, this.modifiers);
      }
   }

   public static class Modifier {
      protected final Holder<Attribute> attribute;
      protected final float probability;
      protected final Operation operation;
      protected final NumberProvider amount;
      protected final ResourceLocation name;
      protected final Set<EquipmentSlotGroup> slots;

      public Modifier(
         float probability, Holder<Attribute> attribute, Operation operation, NumberProvider amount, ResourceLocation name, Set<EquipmentSlotGroup> slots
      ) {
         this.attribute = attribute;
         this.probability = probability;
         this.operation = operation;
         this.amount = amount;
         this.name = name;
         this.slots = slots;
      }

      public AttributeModifier createAttributeModifier(LootContext context) {
         return new AttributeModifier(this.name, this.amount.getFloat(context), this.operation);
      }

      public static class Builder {
         protected final Attribute attribute;
         protected final ResourceLocation name;
         protected final NumberProvider amount;
         protected float probability;
         protected Operation operation;
         protected Set<EquipmentSlotGroup> slots;

         public Builder(Attribute attribute, ResourceLocation name, NumberProvider amount) {
            this.attribute = attribute;
            this.name = name;
            this.amount = amount;
            this.probability = 1.0F;
            this.operation = Operation.ADD_VALUE;
            this.slots = new HashSet<>();
         }

         public void setProbability(float probability) {
            this.probability = probability;
         }

         public void setOperation(Operation operation) {
            this.operation = operation;
         }

         public void setSlots(EquipmentSlotGroup[] slots) {
            this.slots = new HashSet<>(Arrays.asList(slots));
         }

         public AddAttributesFunction.Modifier build() {
            Holder<Attribute> attributeHolder = BuiltInRegistries.ATTRIBUTE.wrapAsHolder(this.attribute);
            return new AddAttributesFunction.Modifier(this.probability, attributeHolder, this.operation, this.amount, this.name, this.slots);
         }
      }
   }
}
