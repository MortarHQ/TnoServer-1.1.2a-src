package com.finderfeed.fdlib.systems.bedrock.models.model_render_info;

import com.finderfeed.fdlib.data_structures.Pair;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.LayerAttachments;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachment;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class AttachmentAdditionalRenderInfo implements IFDModelAdditionalInfo {
   private LayerAttachments layerAttachments;

   public AttachmentAdditionalRenderInfo(LayerAttachments layerAttachments) {
      this.layerAttachments = layerAttachments;
   }

   @Override
   public Collection<? extends ModelAttachment<?, ?>> getBoneModelAttachments(String bone) {
      List<Pair<UUID, ModelAttachment<?, ?>>> models = this.layerAttachments.getAllBoneAttachments(bone);
      return models.stream().map(v -> v.second).toList();
   }
}
