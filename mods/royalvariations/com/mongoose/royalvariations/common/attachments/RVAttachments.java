package com.mongoose.royalvariations.common.attachments;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class RVAttachments {
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(Keys.ATTACHMENT_TYPES, "royalvariations");
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<RVAttachment>> RV_ATTACHMENT = ATTACHMENT_TYPES.register(
      "rv_attachment", () -> AttachmentType.builder(RVAttachment::new).serialize(RVAttachment.CODEC).build()
   );
}
