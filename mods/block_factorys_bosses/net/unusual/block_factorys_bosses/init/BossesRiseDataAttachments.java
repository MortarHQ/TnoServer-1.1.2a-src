package net.unusual.block_factorys_bosses.init;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import net.unusual.block_factorys_bosses.attachment.entity.GauntletAttachment;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;

public class BossesRiseDataAttachments {
   public static final DeferredRegister<AttachmentType<?>> REGISTRY = DeferredRegister.create(Keys.ATTACHMENT_TYPES, "block_factorys_bosses");
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<PlayerVariables>> PLAYER_VARIABLES = REGISTRY.register(
      "player_variables", () -> AttachmentType.serializable(PlayerVariables::new).build()
   );
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<RollAttachment>> ROLL_ATTACHMENT = REGISTRY.register(
      "roll_attachment", () -> AttachmentType.serializable(RollAttachment::new).build()
   );
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<PlayerAnimationHandler>> PLAYER_ANIMATION = REGISTRY.register(
      "player_animation", () -> AttachmentType.serializable(PlayerAnimationHandler::new).build()
   );
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<GauntletAttachment>> GAUNTLET_ATTACHMENT = REGISTRY.register(
      "gauntlet_attachment", () -> AttachmentType.serializable(GauntletAttachment::new).build()
   );
}
