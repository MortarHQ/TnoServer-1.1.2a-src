package io.github.manasmods.tensura_neb;

import dev.architectury.platform.Platform;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura_neb.client.TensuraNebClient;
import io.github.manasmods.tensura_neb.entity.CarrionEntity;
import io.github.manasmods.tensura_neb.entity.LuminousValentineEntity;
import io.github.manasmods.tensura_neb.entity.RimuruCloneEntity;
import io.github.manasmods.tensura_neb.entity.RimuruOgreFightEntity;
import io.github.manasmods.tensura_neb.entity.VeldoraEntity;
import io.github.manasmods.tensura_neb.handler.NebHandler;
import io.github.manasmods.tensura_neb.registry.NebEntityTypes;
import io.github.manasmods.tensura_neb.registry.NebRegistry;
import net.neoforged.api.distmarker.Dist;

public final class TensuraNeb {
   public static final String MOD_ID = "tensura_neb";

   public static void init() {
      ConfigRegistry.registerConfig(new NebConfig());
      NebRegistry.init();
      createAttributes();
      if (Platform.getEnv() == Dist.CLIENT) {
         TensuraNebClient.init();
      }

      NebHandler.init();
   }

   public static void createAttributes() {
      EntityAttributeRegistry.register(NebEntityTypes.RIMURU_OGRE_FIGHT, RimuruOgreFightEntity::setAttributes);
      EntityAttributeRegistry.register(NebEntityTypes.RIMURU_CLONE, RimuruCloneEntity::setAttributes);
      EntityAttributeRegistry.register(NebEntityTypes.CARRION, CarrionEntity::setAttributes);
      EntityAttributeRegistry.register(NebEntityTypes.LUMINOUS_VALENTINE, LuminousValentineEntity::setAttributes);
      EntityAttributeRegistry.register(NebEntityTypes.VELDORA, VeldoraEntity::setAttributes);
   }
}
