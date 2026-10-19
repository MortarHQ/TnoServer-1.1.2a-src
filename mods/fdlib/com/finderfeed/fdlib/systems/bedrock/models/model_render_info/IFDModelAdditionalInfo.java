package com.finderfeed.fdlib.systems.bedrock.models.model_render_info;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.LayerAttachments;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachment;
import java.util.Collection;

public interface IFDModelAdditionalInfo {
   Collection<? extends ModelAttachment<?, ?>> getBoneModelAttachments(String var1);

   static IFDModelAdditionalInfo empty() {
      return new EmtpyModelAdditionalInfo();
   }

   static IFDModelAdditionalInfo attachmentData(LayerAttachments layerAttachments) {
      return new AttachmentAdditionalRenderInfo(layerAttachments);
   }
}
