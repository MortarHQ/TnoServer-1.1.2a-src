package com.blorb.morerelics.dataComponents;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttachmentHandler {
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "morerelics");
   public static final Supplier<AttachmentType<Integer>> COUNTER = ATTACHMENT_TYPES.register(
      "counter", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
   );
}
