package com.tensura_tno.mixin.client;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.data.TensuraItemTags;
import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import io.github.manasmods.tensura.registry.advancement.TensuraCriteriaTriggers;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResetScrollItem.class)
public class ResetScrollScholarFixMixin {
   @Unique
   private static final ResourceLocation tensuraTno$SCHOLAR_ID = ResourceLocation.fromNamespaceAndPath("mysticism", "scholar");

   @Inject(method = "resetEverything", at = @At("TAIL"))
   private static void tensuraTno$restoreLockedScholarSchematics(ServerPlayer player, CallbackInfo ci) {
      ManasSkill scholarSkill = (ManasSkill)SkillAPI.getSkillRegistry().get(tensuraTno$SCHOLAR_ID);
      if (scholarSkill != null) {
         if (SkillUtils.hasSkill(player, scholarSkill)) {
            ITensuraPlayer cap = TensuraStorages.getPlayerDataFrom(player);
            boolean unlockedAny = false;

            for (Item item : BuiltInRegistries.ITEM) {
               if (item.getDefaultInstance().is(TensuraItemTags.SCHEMATICS) && !cap.hasSchematic(item)) {
                  cap.unlockSchematic(item.getDefaultInstance());
                  unlockedAny = true;
               }
            }

            if (unlockedAny) {
               cap.markDirty();
               ((PlayerTrigger)TensuraCriteriaTriggers.LEARN_ALL_SCHEMATICS.get()).trigger(player);
            }
         }
      }
   }
}
