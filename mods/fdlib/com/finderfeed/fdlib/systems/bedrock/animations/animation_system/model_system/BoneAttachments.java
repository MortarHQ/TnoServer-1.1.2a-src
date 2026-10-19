package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system;

import com.finderfeed.fdlib.data_structures.Pair;
import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachment;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachmentData;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachmentType;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class BoneAttachments implements INBTSerializable<CompoundTag> {
   private List<Pair<UUID, ModelAttachment<?, ?>>> boneAttachments = new ArrayList<>();

   public boolean hasAttachment(UUID attachmentUUID) {
      return this.listContainsAttachmentWithUUID(this.boneAttachments, attachmentUUID);
   }

   public <M extends ModelAttachment<M, T>, T extends ModelAttachmentData<M>> ModelAttachment<M, T> getAttachment(UUID uuid) {
      for (Pair<UUID, ModelAttachment<?, ?>> pair : this.boneAttachments) {
         if (uuid.equals(pair.first)) {
            return (ModelAttachment<M, T>)pair.second;
         }
      }

      return null;
   }

   protected boolean removeAttachment(UUID uuid) {
      ListIterator<Pair<UUID, ModelAttachment<?, ?>>> iterator = this.boneAttachments.listIterator();

      while (iterator.hasNext()) {
         Pair<UUID, ModelAttachment<?, ?>> pair = iterator.next();
         UUID uuid2 = pair.first;
         if (uuid2.equals(uuid)) {
            iterator.remove();
            return true;
         }
      }

      return false;
   }

   protected void attach(UUID uuid, ModelAttachment<?, ?> fdModel) {
      if (!this.hasAttachment(uuid)) {
         this.boneAttachments.add(new Pair<>(uuid, fdModel));
      }
   }

   private boolean listContainsAttachmentWithUUID(List<Pair<UUID, ModelAttachment<?, ?>>> list, UUID uuid) {
      for (Pair<UUID, ModelAttachment<?, ?>> entry : list) {
         if (entry.first.equals(uuid)) {
            return true;
         }
      }

      return false;
   }

   public List<Pair<UUID, ModelAttachment<?, ?>>> getAttachments() {
      return this.boneAttachments;
   }

   public CompoundTag serializeNBT(Provider provider) {
      CompoundTag tag = new CompoundTag();
      int id = 0;

      for (Pair<UUID, ModelAttachment<?, ?>> pair : this.boneAttachments) {
         CompoundTag attachment = new CompoundTag();
         attachment.putUUID("uuid", pair.first);
         ModelAttachment<?, ?> modelAttachment = pair.second;
         ModelAttachmentType<?, ?> type = modelAttachment.attachmentData().type();
         ResourceLocation key = FDRegistries.MODEL_ATTACHMENT_TYPES.getKey(type);
         CompoundTag t = (CompoundTag)modelAttachment.serializeNBT(provider);
         attachment.put("attachmentData", t);
         attachment.putString("attachmentType", key.toString());
         tag.put("attachment_" + id++, attachment);
      }

      return tag;
   }

   public void deserializeNBT(Provider provider, CompoundTag tag) {
      this.boneAttachments = new ArrayList<>();

      for (int id = 0; tag.contains("attachment_" + id); id++) {
         CompoundTag attachment = tag.getCompound("attachment_" + id);
         UUID uuid = attachment.getUUID("uuid");
         CompoundTag modelAttachmentTag = attachment.getCompound("attachmentData");
         ResourceLocation location = ResourceLocation.parse(attachment.getString("attachmentType"));
         ModelAttachmentType<?, ?> type = (ModelAttachmentType<?, ?>)FDRegistries.MODEL_ATTACHMENT_TYPES.get(location);
         ModelAttachment<?, ? extends ModelAttachmentData<?>> modelAttachment = (ModelAttachment<?, ? extends ModelAttachmentData<?>>)type.createInstance();
         modelAttachment.deserializeNBT(provider, modelAttachmentTag);
         this.boneAttachments.add(new Pair<>(uuid, modelAttachment));
      }
   }
}
