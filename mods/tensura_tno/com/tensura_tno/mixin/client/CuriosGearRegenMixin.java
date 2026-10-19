package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.CuriosEPCompat;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingTickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "io.github.manasmods.tensura.storage.ep.ExistenceStorage", remap = false)
public class CuriosGearRegenMixin {
   @Inject(method = "init", at = @At("TAIL"), remap = false, require = 0)
   private static void tensuraTno$registerCuriosGearRegen(CallbackInfo ci) {
      EntityEvents.LIVING_POST_TICK.register((LivingTickEvent)entity -> {
         Level level = entity.level();
         if (!level.isClientSide()) {
            MinecraftServer server = level.getServer();
            if (server != null) {
               CuriosEPCompat.handleCuriosGearRegen(entity, server);
            }
         }
      });
   }
}
