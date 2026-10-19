package team.lodestar.lodestone.registry.common;

import java.util.function.Supplier;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import team.lodestar.lodestone.attachment.WorldEventAttachment;

public class LodestoneAttachmentTypes {
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "lodestone");
   public static final Supplier<AttachmentType<WorldEventAttachment>> WORLD_EVENT_DATA = ATTACHMENT_TYPES.register(
      "world_event_data", () -> AttachmentType.serializable(WorldEventAttachment::new).build()
   );
}
