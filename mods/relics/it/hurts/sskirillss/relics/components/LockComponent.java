package it.hurts.sskirillss.relics.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LockComponent(int unlocks) {
   public static final LockComponent EMPTY = new LockComponent(0);
   public static final Codec<LockComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.INT.fieldOf("breaks").forGetter(LockComponent::unlocks)).apply(instance, LockComponent::new)
   );

   public static LockComponent.LockComponentBuilder builder() {
      return new LockComponent.LockComponentBuilder();
   }

   public LockComponent.LockComponentBuilder toBuilder() {
      return new LockComponent.LockComponentBuilder().unlocks(this.unlocks);
   }

   public static class LockComponentBuilder {
      private int unlocks;

      LockComponentBuilder() {
      }

      public LockComponent.LockComponentBuilder unlocks(int unlocks) {
         this.unlocks = unlocks;
         return this;
      }

      public LockComponent build() {
         return new LockComponent(this.unlocks);
      }

      @Override
      public String toString() {
         return "LockComponent.LockComponentBuilder(unlocks=" + this.unlocks + ")";
      }
   }
}
