package com.tensura_tno.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tensura_tno.compat.MysticismSkillRefundGuard;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillEvents.UnlockSkillEvent;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {SkillStorage.class, SkillHelper.class, TensuraSkill.class}, remap = false)
public abstract class MysticismSkillUnlockScopeMixin {
   @WrapOperation(
      method = "*",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/manascore/skill/api/SkillEvents$UnlockSkillEvent;unlockSkill(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;Lio/github/manasmods/manascore/network/api/util/Changeable;)Ldev/architectury/event/EventResult;"
      ),
      remap = false,
      require = 1
   )
   private static EventResult tensuraTno$trackUnlockMagicule(
      UnlockSkillEvent event, ManasSkillInstance instance, LivingEntity entity, Changeable<MutableComponent> message, Operation<EventResult> original
   ) {
      AttributeInstance attribute = entity.getAttribute(TensuraAttributes.MAX_MAGICULE);
      if (attribute != null && !entity.level().isClientSide()) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         return MysticismSkillRefundGuard.duringUnlock(
            attribute,
            attribute.getBaseValue(),
            existence,
            existence.getMagicule(),
            () -> (EventResult)original.call(new Object[]{event, instance, entity, message})
         );
      } else {
         return (EventResult)original.call(new Object[]{event, instance, entity, message});
      }
   }
}
