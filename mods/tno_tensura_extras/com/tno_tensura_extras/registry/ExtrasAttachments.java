package com.tno_tensura_extras.registry;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ExtrasAttachments {
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "tno_tensura_extras");
   public static final Supplier<AttachmentType<Long>> NEXT_HIGH_QUALITY_HONEY_TIME = ATTACHMENT_TYPES.register(
      "next_high_quality_honey_time", () -> AttachmentType.builder(() -> 0L).serialize(Codec.LONG).build()
   );

   private ExtrasAttachments() {
   }
}
