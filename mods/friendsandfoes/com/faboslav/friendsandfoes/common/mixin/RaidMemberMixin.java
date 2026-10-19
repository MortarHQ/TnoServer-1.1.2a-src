package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.world.entity.raid.Raid.RaiderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(RaiderType.class)
public final class RaidMemberMixin {
   @ModifyArg(
      method = "<clinit>",
      slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=EVOKER")),
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/raid/Raid$RaiderType;<init>(Ljava/lang/String;ILnet/minecraft/world/entity/EntityType;[I)V",
         ordinal = 0
      )
   )
   private static int[] friendsandfoes_updateCountInWave(int[] countInWave) {
      return (!FriendsAndFoes.getConfig().enableIllusioner || !FriendsAndFoes.getConfig().enableIllusionerInRaids)
            && (!FriendsAndFoes.getConfig().enableIceologer || !FriendsAndFoes.getConfig().enableIceologerInRaids)
         ? new int[]{0, 0, 0, 0, 0, 1, 1, 2}
         : new int[]{0, 0, 0, 0, 0, 1, 1, 1};
   }
}
