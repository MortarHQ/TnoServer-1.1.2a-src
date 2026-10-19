package it.hurts.sskirillss.relics.items.relics.base.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class RelicAttributeModifier {
   List<RelicAttributeModifier.Modifier> attributes;

   RelicAttributeModifier(List<RelicAttributeModifier.Modifier> attributes) {
      this.attributes = attributes;
   }

   public static RelicAttributeModifier.RelicAttributeModifierBuilder builder() {
      return new RelicAttributeModifier.RelicAttributeModifierBuilder();
   }

   public List<RelicAttributeModifier.Modifier> getAttributes() {
      return this.attributes;
   }

   public void setAttributes(List<RelicAttributeModifier.Modifier> attributes) {
      this.attributes = attributes;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RelicAttributeModifier other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$attributes = this.getAttributes();
         Object other$attributes = other.getAttributes();
         return this$attributes == null ? other$attributes == null : this$attributes.equals(other$attributes);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof RelicAttributeModifier;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $attributes = this.getAttributes();
      return result * 59 + ($attributes == null ? 43 : $attributes.hashCode());
   }

   @Override
   public String toString() {
      return "RelicAttributeModifier(attributes=" + this.getAttributes() + ")";
   }

   public static class Modifier {
      private final Holder<Attribute> attribute;
      private final float multiplier;
      private final Operation operation;

      public Modifier(Holder<Attribute> attribute, float multiplier, Operation operation) {
         this.attribute = attribute;
         this.multiplier = multiplier;
         this.operation = operation;
      }

      public Modifier(Holder<Attribute> attribute, float multiplier) {
         this.attribute = attribute;
         this.multiplier = multiplier;
         this.operation = Operation.ADD_MULTIPLIED_TOTAL;
      }

      public Holder<Attribute> getAttribute() {
         return this.attribute;
      }

      public float getMultiplier() {
         return this.multiplier;
      }

      public Operation getOperation() {
         return this.operation;
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof RelicAttributeModifier.Modifier other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (Float.compare(this.getMultiplier(), other.getMultiplier()) != 0) {
            return false;
         } else {
            Object this$attribute = this.getAttribute();
            Object other$attribute = other.getAttribute();
            if (this$attribute == null ? other$attribute == null : this$attribute.equals(other$attribute)) {
               Object this$operation = this.getOperation();
               Object other$operation = other.getOperation();
               return this$operation == null ? other$operation == null : this$operation.equals(other$operation);
            } else {
               return false;
            }
         }
      }

      protected boolean canEqual(Object other) {
         return other instanceof RelicAttributeModifier.Modifier;
      }

      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         result = result * 59 + Float.floatToIntBits(this.getMultiplier());
         Object $attribute = this.getAttribute();
         result = result * 59 + ($attribute == null ? 43 : $attribute.hashCode());
         Object $operation = this.getOperation();
         return result * 59 + ($operation == null ? 43 : $operation.hashCode());
      }

      @Override
      public String toString() {
         return "RelicAttributeModifier.Modifier(attribute="
            + this.getAttribute()
            + ", multiplier="
            + this.getMultiplier()
            + ", operation="
            + this.getOperation()
            + ")";
      }
   }

   public static class RelicAttributeModifierBuilder {
      private ArrayList<RelicAttributeModifier.Modifier> attributes;

      RelicAttributeModifierBuilder() {
      }

      public RelicAttributeModifier.RelicAttributeModifierBuilder attribute(RelicAttributeModifier.Modifier attribute) {
         if (this.attributes == null) {
            this.attributes = new ArrayList<>();
         }

         this.attributes.add(attribute);
         return this;
      }

      public RelicAttributeModifier.RelicAttributeModifierBuilder attributes(Collection<? extends RelicAttributeModifier.Modifier> attributes) {
         if (attributes == null) {
            throw new NullPointerException("attributes cannot be null");
         }

         if (this.attributes == null) {
            this.attributes = new ArrayList<>();
         }

         this.attributes.addAll(attributes);
         return this;
      }

      public RelicAttributeModifier.RelicAttributeModifierBuilder clearAttributes() {
         if (this.attributes != null) {
            this.attributes.clear();
         }

         return this;
      }

      public RelicAttributeModifier build() {
         return new RelicAttributeModifier(switch (this.attributes == null ? 0 : this.attributes.size()) {
            case 0 -> Collections.emptyList();
            case 1 -> Collections.singletonList(this.attributes.get(0));
            default -> Collections.unmodifiableList(new ArrayList<>(this.attributes));
         });
      }

      @Override
      public String toString() {
         return "RelicAttributeModifier.RelicAttributeModifierBuilder(attributes=" + this.attributes + ")";
      }
   }
}
