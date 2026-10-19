package it.hurts.sskirillss.relics.system.casts.abilities;

import it.hurts.sskirillss.relics.system.casts.slots.base.SlotReference;
import net.minecraft.nbt.CompoundTag;

public class AbilityReference {
   private String id;
   private SlotReference slot;

   public CompoundTag serializeNBT() {
      CompoundTag tag = new CompoundTag();
      tag.putString("id", this.id);
      tag.put("slot", this.slot.serializeNBT());
      return tag;
   }

   public AbilityReference deserializeNBT(CompoundTag tag) {
      try {
         this.id = tag.getString("id");
         CompoundTag slotTag = tag.getCompound("slot");
         this.slot = ((SlotReference)Class.forName(slotTag.getString("class")).getConstructor().newInstance()).deserializeNBT(slotTag);
         return this;
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   public String getId() {
      return this.id;
   }

   public SlotReference getSlot() {
      return this.slot;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setSlot(SlotReference slot) {
      this.slot = slot;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AbilityReference other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$slot = this.getSlot();
            Object other$slot = other.getSlot();
            return this$slot == null ? other$slot == null : this$slot.equals(other$slot);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof AbilityReference;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $slot = this.getSlot();
      return result * 59 + ($slot == null ? 43 : $slot.hashCode());
   }

   @Override
   public String toString() {
      return "AbilityReference(id=" + this.getId() + ", slot=" + this.getSlot() + ")";
   }

   public AbilityReference() {
   }

   public AbilityReference(String id, SlotReference slot) {
      this.id = id;
      this.slot = slot;
   }
}
