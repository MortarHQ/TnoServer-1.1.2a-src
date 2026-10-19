package com.tensura.naturesaura.registry;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class LegacyAttachments {
   private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
      NeoForgeRegistries.ATTACHMENT_TYPES, "tensura_naturesaura"
   );
   public static final Supplier<AttachmentType<Boolean>> BIOME_AURA_INITIALIZED = ATTACHMENT_TYPES.register(
      "biome_aura_initialized", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL, initialized -> initialized).build()
   );

   private LegacyAttachments() {
   }

   public static void register(IEventBus modBus) {
      ATTACHMENT_TYPES.register(modBus);
   }
}
