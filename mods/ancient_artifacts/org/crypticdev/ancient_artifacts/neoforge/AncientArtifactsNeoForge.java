package org.crypticdev.ancient_artifacts.neoforge;

import java.util.function.Supplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import org.crypticdev.ancient_artifacts.AncientArtifacts;
import org.crypticdev.ancient_artifacts.attachments.CombatData;
import org.crypticdev.ancient_artifacts.platform.neoforge.PlatformAttributesImpl;

@Mod("ancient_artifacts")
public final class AncientArtifactsNeoForge {
   private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(Keys.ATTACHMENT_TYPES, "ancient_artifacts");
   public static final Supplier<AttachmentType<CombatData>> COMBAT_DATA = ATTACHMENT_TYPES.register(
      "combat_data", () -> AttachmentType.builder(() -> new CombatData(0L, 0L)).serialize(CombatData.CODEC).build()
   );

   public AncientArtifactsNeoForge(IEventBus modEventBus) {
      AncientArtifacts.init();
      ATTACHMENT_TYPES.register(modEventBus);
      PlatformAttributesImpl.init(modEventBus);
   }
}
