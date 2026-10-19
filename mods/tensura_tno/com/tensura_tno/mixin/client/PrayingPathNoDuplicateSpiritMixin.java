package com.tensura_tno.mixin.client;

import com.mojang.datafixers.util.Pair;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.spirit.ISpiritWielder;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "io.github.manasmods.tensura.block.entity.PrayingPathBlockEntity", remap = false)
public class PrayingPathNoDuplicateSpiritMixin {
   @Unique
   private static final ThreadLocal<Boolean> tensura_tno$rerollingSpirit = ThreadLocal.withInitial(() -> false);

   @Shadow
   private static Pair<Element, SpiritLevel> getRandomSpiritLevel(Player player) {
      throw new AssertionError();
   }

   @Inject(method = "getRandomSpiritLevel", at = @At("RETURN"), cancellable = true, remap = false)
   private static void tensura_tno$avoidDuplicatePrayerSpirit(Player player, CallbackInfoReturnable<Pair<Element, SpiritLevel>> cir) {
      if (!tensura_tno$rerollingSpirit.get()) {
         Pair<Element, SpiritLevel> result = (Pair<Element, SpiritLevel>)cir.getReturnValue();
         if (tensura_tno$isDuplicateSpirit(player, result)) {
            tensura_tno$rerollingSpirit.set(true);

            try {
               for (int i = 0; i < 128; i++) {
                  Pair<Element, SpiritLevel> rerolled = getRandomSpiritLevel(player);
                  if (!tensura_tno$isDuplicateSpirit(player, rerolled)) {
                     cir.setReturnValue(rerolled);
                     return;
                  }
               }

               cir.setReturnValue(null);
            } finally {
               tensura_tno$rerollingSpirit.set(false);
            }
         }
      }
   }

   @Unique
   private static boolean tensura_tno$isDuplicateSpirit(Player player, Pair<Element, SpiritLevel> result) {
      if (result == null) {
         return false;
      }

      ISpiritWielder spirit = TensuraStorages.getSpiritFrom(player);
      return spirit.getSpiritLevelId((Element)result.getFirst()) >= ((SpiritLevel)result.getSecond()).getId();
   }
}
