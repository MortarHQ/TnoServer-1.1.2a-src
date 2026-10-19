package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.instances.item_stack;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.BaseModelAttachmentData;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachment;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class ItemStackAttachment implements ModelAttachment<ItemStackAttachment, ItemStackAttachmentData> {
   private ItemStackAttachmentData data;

   public ItemStackAttachment(ItemStackAttachmentData itemStackAttachmentData) {
      this.data = itemStackAttachmentData;
   }

   public ItemStack getItemStack() {
      return this.data.getItemStack();
   }

   public BaseModelAttachmentData getBaseModelAttachmentData() {
      return this.data.getBaseModelAttachmentData();
   }

   public CompoundTag serializeNBT(Provider provider) {
      CompoundTag itemStackAttachment = new CompoundTag();
      Tag stack = this.getItemStack().save(provider);
      CompoundTag base = this.getBaseModelAttachmentData().serializeNBT(provider);
      itemStackAttachment.put("item", stack);
      itemStackAttachment.put("base", base);
      return itemStackAttachment;
   }

   public void deserializeNBT(Provider provider, CompoundTag nbt) {
      Tag item = nbt.get("item");
      ItemStack itemStack = (ItemStack)ItemStack.parse(provider, item).get();
      BaseModelAttachmentData baseModelAttachmentData = new BaseModelAttachmentData();
      baseModelAttachmentData.deserializeNBT(provider, nbt.getCompound("base"));
      this.data = new ItemStackAttachmentData(baseModelAttachmentData, itemStack);
   }

   public ItemStackAttachmentData attachmentData() {
      return this.data;
   }
}
