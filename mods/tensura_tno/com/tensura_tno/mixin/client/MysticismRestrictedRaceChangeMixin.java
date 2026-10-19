package com.tensura_tno.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tensura_tno.compat.MysticismRestrictedSkillPolicy;
import com.tensura_tno.event.MysticismRestrictedSkillHandler;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.impl.RaceStorage;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = RaceStorage.class, remap = false)
public abstract class MysticismRestrictedRaceChangeMixin {
   @Shadow(remap = false)
   protected abstract LivingEntity getOwner();

   @WrapMethod(method = "setRace(Lio/github/manasmods/manascore/race/api/ManasRaceInstance;ZZLnet/minecraft/network/chat/MutableComponent;)Z")
   private boolean tensuraTno$trackRestrictedRace(
      ManasRaceInstance race, boolean evolution, boolean teleport, MutableComponent message, Operation<Boolean> original
   ) {
      if (this.getOwner() instanceof ServerPlayer player && race != null) {
         boolean changed = MysticismRestrictedSkillPolicy.withRaceChange(
            player.getUUID(), race.getRace().getRegistryName(), () -> (Boolean)original.call(new Object[]{race, evolution, teleport, message})
         );
         if (changed) {
            MysticismRestrictedSkillHandler.removeInvalidSkill(player);
         }

         return changed;
      } else {
         return (Boolean)original.call(new Object[]{race, evolution, teleport, message});
      }
   }
}
