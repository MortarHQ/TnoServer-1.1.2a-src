package com.tensura_tno.mixin.client;

import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "org.crypticdev.stextras.quest.assignment.ScheduledQuestAssignmentService", remap = false)
public class STExtrasQuestRerollMixin {
   @Inject(
      method = "applySetForCategory(Lnet/minecraft/server/level/ServerPlayer;Lorg/crypticdev/stextras/quest/definition/QuestCategory;Ljava/util/Set;Z)V",
      at = @At("TAIL"),
      remap = false
   )
   private static void tno$onQuestReroll(ServerPlayer player, @Coerce Object category, Set<ResourceLocation> desired, boolean freshReroll, CallbackInfo ci) {
      if (freshReroll && ModList.get().isLoaded("ftbquests")) {
         String categoryName = ((Enum)category).name();
         boolean rerollDaily = "DAILY".equals(categoryName);
         boolean rerollWeekly = "WEEKLY".equals(categoryName);
         if (rerollDaily || rerollWeekly) {
            try {
               Class.forName("com.tensura_tno.ftb.FTBQuestsResetHelper")
                  .getMethod("onQuestReroll", ServerPlayer.class, boolean.class, boolean.class)
                  .invoke(null, player, rerollDaily, rerollWeekly);
            } catch (ReflectiveOperationException | LinkageError var9) {
            }
         }
      }
   }
}
