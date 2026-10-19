package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system;

import com.finderfeed.fdlib.data_structures.Pair;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class LayerAttachments implements INBTSerializable<CompoundTag> {
   private HashMap<String, BoneAttachments> layerAttachments = new HashMap<>();

   protected void attach(String boneName, UUID uuid, ModelAttachment<?, ?> attachment) {
      BoneAttachments boneAttachments = this.getBoneAttachments(boneName);
      boneAttachments.attach(uuid, attachment);
   }

   protected boolean removeAttachment(UUID uuid) {
      for (BoneAttachments v : this.layerAttachments.values()) {
         if (v.removeAttachment(uuid)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasAttachment(UUID attachmentUUID) {
      for (BoneAttachments boneAttachment : this.layerAttachments.values()) {
         if (boneAttachment.hasAttachment(attachmentUUID)) {
            return true;
         }
      }

      return false;
   }

   public BoneAttachments getBoneAttachments(String boneName) {
      return this.layerAttachments.computeIfAbsent(boneName, v -> new BoneAttachments());
   }

   public Collection<String> getAllBones() {
      return this.layerAttachments.keySet();
   }

   public List<Pair<UUID, ModelAttachment<?, ?>>> getAllAttachments() {
      List<Pair<UUID, ModelAttachment<?, ?>>> list = new ArrayList<>();

      for (BoneAttachments boneAttachments : this.layerAttachments.values()) {
         list.addAll(boneAttachments.getAttachments());
      }

      return list;
   }

   public List<Pair<UUID, ModelAttachment<?, ?>>> getAllBoneAttachments(String bone) {
      return this.getBoneAttachments(bone).getAttachments();
   }

   public CompoundTag serializeNBT(Provider provider) {
      CompoundTag tag = new CompoundTag();
      int id = 0;

      for (Entry<String, BoneAttachments> entry : this.layerAttachments.entrySet()) {
         CompoundTag boneAttachment = new CompoundTag();
         boneAttachment.putString("bone", entry.getKey());
         CompoundTag boneAttachments = entry.getValue().serializeNBT(provider);
         boneAttachment.put("attachment", boneAttachments);
         tag.put("attachment_" + id++, boneAttachment);
      }

      return tag;
   }

   public void deserializeNBT(Provider provider, CompoundTag tag) {
      this.layerAttachments = new HashMap<>();

      for (int id = 0; tag.contains("attachment_" + id); id++) {
         CompoundTag t = tag.getCompound("attachment_" + id);
         BoneAttachments boneAttachments = new BoneAttachments();
         boneAttachments.deserializeNBT(provider, t.getCompound("attachment"));
         String bone = t.getString("bone");
         this.layerAttachments.put(bone, boneAttachments);
      }
   }
}
