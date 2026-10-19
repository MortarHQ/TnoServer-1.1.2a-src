package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.util.CustomRaidMember;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raid.RaiderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RaiderType.class)
public final class AddCustomRaidMemberMixin {
   @Shadow
   @Final
   @Mutable
   private static RaiderType[] $VALUES;

   @Invoker("<init>")
   private static RaiderType newRaidMember(String internalName, int internalId, EntityType<? extends Raider> entityType, int[] countInWave) {
      throw new AssertionError();
   }

   @Inject(
      method = "<clinit>",
      at = @At(
         value = "FIELD",
         opcode = 179,
         target = "Lnet/minecraft/world/entity/raid/Raid$RaiderType;$VALUES:[Lnet/minecraft/world/entity/raid/Raid$RaiderType;",
         shift = Shift.AFTER
      )
   )
   private static void friendsandfoes_addCustomRaidMembers(CallbackInfo ci) {
      ArrayList<RaiderType> raidMembers = new ArrayList<>(Arrays.asList($VALUES));
      RaiderType lastRaidMember = raidMembers.get(raidMembers.size() - 1);
      if (FriendsAndFoes.getConfig().enableIceologerInRaids) {
         RaiderType iceologerRaidMember = newRaidMember(
            "ICEOLOGER",
            lastRaidMember.ordinal() + 1,
            (EntityType<? extends Raider>)FriendsAndFoesEntityTypes.ICEOLOGER.get(),
            CustomRaidMember.ICEOLOGER_COUNT_IN_WAVE
         );
         CustomRaidMember.ICEOLOGER = iceologerRaidMember;
         raidMembers.add(iceologerRaidMember);
      }

      if (FriendsAndFoes.getConfig().enableIllusionerInRaids) {
         RaiderType illusionerRaidMember = newRaidMember(
            "ILLUSIONER",
            lastRaidMember.ordinal() + 2,
            (EntityType<? extends Raider>)FriendsAndFoesEntityTypes.ILLUSIONER.get(),
            CustomRaidMember.ILLUSIONER_COUNT_IN_WAVE
         );
         CustomRaidMember.ILLUSIONER = illusionerRaidMember;
         raidMembers.add(illusionerRaidMember);
      }

      $VALUES = raidMembers.toArray(new RaiderType[0]);
   }
}
