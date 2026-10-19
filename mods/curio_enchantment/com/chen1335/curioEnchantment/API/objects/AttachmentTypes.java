package com.chen1335.curioEnchantment.API.objects;

import com.chen1335.curioEnchantment.common.attachmentTypes.PlayerData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttachmentTypes {
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE_DEFERRED_REGISTER = DeferredRegister.create(
      NeoForgeRegistries.ATTACHMENT_TYPES, "curio_enchantment"
   );
   public static final DeferredHolder<AttachmentType<?>, AttachmentType<PlayerData>> PLAYER_DATA = ATTACHMENT_TYPE_DEFERRED_REGISTER.register(
      "player_data", () -> AttachmentType.builder(PlayerData::new).build()
   );
}
