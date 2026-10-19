package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.stextras.FoxBossQuestCompat;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "org.crypticdev.stextras.handler.HandlePrestigeListeners", remap = false)
public class STExtrasFoxBossQuestMixin {
   @Inject(
      method = "matchesQuest(Lorg/crypticdev/stextras/quest/definition/QuestDefinition;Lorg/crypticdev/stextras/quest/definition/QuestObjectiveType;Ljava/util/List;Lorg/crypticdev/stextras/handler/HandlePrestigeListeners$QuestProgressContext;)Z",
      at = @At("HEAD"),
      cancellable = true,
      require = 0,
      remap = false
   )
   private static void tensuraTno$keepBossThreshold(
      @Coerce Object quest, @Coerce Object creator, List<ResourceLocation> types, @Coerce Object context, CallbackInfoReturnable<Boolean> cir
   ) {
      if (!FoxBossQuestCompat.matches(quest, context)) {
         cir.setReturnValue(false);
      }
   }
}
