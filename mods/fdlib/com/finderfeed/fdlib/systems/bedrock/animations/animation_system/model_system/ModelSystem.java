package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system;

import com.finderfeed.fdlib.data_structures.Pair;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationSystem;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachment;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachmentData;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachmentType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;

public abstract class ModelSystem {
   private AnimationSystem animationSystem;
   private HashMap<Integer, LayerAttachments> layerBoneModelMap = new HashMap<>();

   public ModelSystem(AnimationSystem animationSystem) {
      this.animationSystem = animationSystem;
   }

   public AnimationSystem getAnimationSystem() {
      return this.animationSystem;
   }

   public void tick() {
      this.animationSystem.tick();
   }

   public void attachToLayer(int layer, String boneName, UUID attachmentUUID, ModelAttachmentData<?> attachmentData) {
      if (!this.hasAttachment(attachmentUUID)) {
         LayerAttachments layerAttachments = this.getLayerAttachments(layer);
         ModelAttachmentType attachmentType = attachmentData.type();
         ModelAttachment modelAttachment = attachmentType.attachmentFromData(attachmentData);
         layerAttachments.attach(boneName, attachmentUUID, modelAttachment);
         this.onAttachment(layer, boneName, attachmentUUID, attachmentData);
      }
   }

   public void removeAttachment(UUID attachmentUUID) {
      for (Entry<Integer, LayerAttachments> entry : this.layerBoneModelMap.entrySet()) {
         LayerAttachments layerAttachments = entry.getValue();
         if (layerAttachments.removeAttachment(attachmentUUID)) {
            this.onAttachmentRemoved(attachmentUUID);
            return;
         }
      }
   }

   public List<Pair<UUID, ModelAttachment<?, ?>>> getAllLayerAttachments(int layerId) {
      LayerAttachments layerAttachments = this.getLayerAttachments(layerId);
      return layerAttachments.getAllAttachments();
   }

   public Collection<Integer> allLayers() {
      return this.layerBoneModelMap.keySet();
   }

   public boolean hasAttachment(UUID uuid) {
      for (Entry<Integer, LayerAttachments> entry : this.layerBoneModelMap.entrySet()) {
         LayerAttachments layerAttachments = entry.getValue();
         if (layerAttachments.hasAttachment(uuid)) {
            return true;
         }
      }

      return false;
   }

   public LayerAttachments getLayerAttachments(int layerId) {
      return this.layerBoneModelMap.computeIfAbsent(layerId, v -> new LayerAttachments());
   }

   public void saveAttachments(Provider provider, CompoundTag compoundTag) {
      CompoundTag modelSystemData = new CompoundTag();
      int id = 0;

      for (Entry<Integer, LayerAttachments> entry : this.layerBoneModelMap.entrySet()) {
         CompoundTag layerAttachmentsData = new CompoundTag();
         int layerId = entry.getKey();
         LayerAttachments layerAttachments = entry.getValue();
         CompoundTag clayer = layerAttachments.serializeNBT(provider);
         layerAttachmentsData.putInt("layerId", layerId);
         layerAttachmentsData.put("layerAttachments", clayer);
         modelSystemData.put("attachments_" + id, layerAttachmentsData);
         id++;
      }

      compoundTag.put("modelAttachmentsData", modelSystemData);
   }

   public void loadAttachments(Provider provider, CompoundTag tag) {
      this.layerBoneModelMap = new HashMap<>();
      int id = 0;

      for (CompoundTag attachmentsData = tag.getCompound("modelAttachmentsData"); attachmentsData.contains("attachments_" + id); id++) {
         CompoundTag attachments = attachmentsData.getCompound("attachments_" + id);
         int layerId = attachments.getInt("layerId");
         LayerAttachments layerAttachments = new LayerAttachments();
         layerAttachments.deserializeNBT(provider, attachments.getCompound("layerAttachments"));
         this.layerBoneModelMap.put(layerId, layerAttachments);
      }
   }

   public abstract void onAttachment(int var1, String var2, UUID var3, ModelAttachmentData<?> var4);

   public abstract void onAttachmentRemoved(UUID var1);
}
