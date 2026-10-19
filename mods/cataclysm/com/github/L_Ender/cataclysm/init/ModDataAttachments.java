package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.Attachment.ChargeAttachment;
import com.github.L_Ender.cataclysm.Attachment.ParryAttachment;
import com.github.L_Ender.cataclysm.Attachment.RenderRushAttachment;
import com.github.L_Ender.cataclysm.Attachment.TidalTentacleAttachment;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class ModDataAttachments {
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(Keys.ATTACHMENT_TYPES, "cataclysm");
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> HOOK_FALLING = ATTACHMENT_TYPES.register(
      "hook_falling", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build()
   );
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<ChargeAttachment>> CHARGE_ATTACHMENT = ATTACHMENT_TYPES.register(
      "charge_attachment", () -> AttachmentType.builder(ChargeAttachment::new).build()
   );
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<RenderRushAttachment>> RENDER_RUSH_ATTACHMENT = ATTACHMENT_TYPES.register(
      "render_rush_attachment", () -> AttachmentType.builder(RenderRushAttachment::new).build()
   );
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<TidalTentacleAttachment>> TIDAL_TENTACLE_ATTACHMENT = ATTACHMENT_TYPES.register(
      "tidal_tentacle_attachment", () -> AttachmentType.builder(TidalTentacleAttachment::new).build()
   );
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<ParryAttachment>> PARRY_ATTACHMENT = ATTACHMENT_TYPES.register(
      "parry_attachment", () -> AttachmentType.builder(ParryAttachment::new).build()
   );
}
