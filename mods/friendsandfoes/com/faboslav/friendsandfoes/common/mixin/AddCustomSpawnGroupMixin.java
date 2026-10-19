package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.platform.CustomSpawnGroup;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobCategory.class)
public final class AddCustomSpawnGroupMixin {
   @Shadow
   @Final
   @Mutable
   private static MobCategory[] $VALUES;

   @Invoker("<init>")
   public static MobCategory newSpawnGroup(
      String internalName, int internalId, String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange
   ) {
      throw new AssertionError();
   }

   @Inject(
      method = "<clinit>",
      at = @At(
         value = "FIELD",
         opcode = 179,
         target = "Lnet/minecraft/world/entity/MobCategory;$VALUES:[Lnet/minecraft/world/entity/MobCategory;",
         shift = Shift.AFTER
      )
   )
   private static void friendsandfoes_addCustomSpawnGroup(CallbackInfo ci) {
      ArrayList<MobCategory> spawnGroups = new ArrayList<>(Arrays.asList($VALUES));
      MobCategory lastSpawnGroup = spawnGroups.get(spawnGroups.size() - 1);
      MobCategory glaresSpawnGroup = newSpawnGroup("GLARES", lastSpawnGroup.ordinal() + 1, "glares", 15, true, false, 128);
      CustomSpawnGroup.GLARES = glaresSpawnGroup;
      spawnGroups.add(glaresSpawnGroup);
      MobCategory rascalsSpawnGroup = newSpawnGroup("RASCALS", lastSpawnGroup.ordinal() + 2, "rascals", 1, true, false, 128);
      CustomSpawnGroup.RASCALS = rascalsSpawnGroup;
      spawnGroups.add(rascalsSpawnGroup);
      $VALUES = spawnGroups.toArray(new MobCategory[0]);
   }
}
