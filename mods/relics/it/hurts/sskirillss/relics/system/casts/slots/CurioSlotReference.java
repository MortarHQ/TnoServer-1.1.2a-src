package it.hurts.sskirillss.relics.system.casts.slots;

import it.hurts.sskirillss.relics.system.casts.slots.base.SlotReference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CurioSlotReference extends SlotReference {
   private int index;
   private String type;

   @Override
   public ItemStack gatherStack(Player player) {
      return CuriosApi.getCuriosInventory(player).map(itemHandler -> {
         IDynamicStackHandler stackHandler = ((ICurioStacksHandler)itemHandler.getCurios().get(this.getType())).getStacks();
         int index = this.getIndex();
         return index > stackHandler.getSlots() - 1 ? null : stackHandler.getStackInSlot(index);
      }).orElse(ItemStack.EMPTY);
   }

   @Override
   public CompoundTag serializeNBT() {
      CompoundTag tag = super.serializeNBT();
      tag.putInt("index", this.index);
      tag.putString("type", this.type);
      return tag;
   }

   @Override
   public SlotReference deserializeNBT(CompoundTag tag) {
      this.index = tag.getInt("index");
      this.type = tag.getString("type");
      return this;
   }

   public int getIndex() {
      return this.index;
   }

   public String getType() {
      return this.type;
   }

   public void setIndex(int index) {
      this.index = index;
   }

   public void setType(String type) {
      this.type = type;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CurioSlotReference other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getIndex() != other.getIndex()) {
            return false;
         }

         Object this$type = this.getType();
         Object other$type = other.getType();
         return this$type == null ? other$type == null : this$type.equals(other$type);
      }
   }

   @Override
   protected boolean canEqual(Object other) {
      return other instanceof CurioSlotReference;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getIndex();
      Object $type = this.getType();
      return result * 59 + ($type == null ? 43 : $type.hashCode());
   }

   @Override
   public String toString() {
      return "CurioSlotReference(index=" + this.getIndex() + ", type=" + this.getType() + ")";
   }

   public CurioSlotReference() {
   }

   public CurioSlotReference(int index, String type) {
      this.index = index;
      this.type = type;
   }
}
