package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments;

public interface ModelAttachmentData<T extends ModelAttachment<T, ?>> {
   ModelAttachmentType<T, ?> type();
}
