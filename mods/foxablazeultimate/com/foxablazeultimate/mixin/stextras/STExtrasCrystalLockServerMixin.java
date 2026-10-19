package com.foxablazeultimate.mixin.stextras;

import com.foxablazeultimate.config.FoxAblazeUltimateConfig;
import com.foxablazeultimate.event.UltimateSkillProtector;
import com.foxablazeultimate.item.CrystalLockState;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "org.crypticdev.stextras.storage.STExtarsStorage$Player", remap = false)
public class STExtrasCrystalLockServerMixin {
   private static final ResourceLocation FOXABLAZEULTIMATE$RAPHAEL = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "raphael");
   private static final ResourceLocation FOXABLAZEULTIMATE$CIEL = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "ciel");
   private static final ResourceLocation FOXABLAZEULTIMATE$SHUB_NIGGURATH = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "shub_niggurath");

   @Shadow(remap = false)
   public static List<ResourceLocation> getLockedSkills(ServerPlayer player) {
      throw new AssertionError();
   }

   @Shadow(remap = false)
   public static List<ResourceLocation> getAdminLockedSkills(ServerPlayer player) {
      throw new AssertionError();
   }

   @Shadow(remap = false)
   public static int getSoulGrade(ServerPlayer player) {
      throw new AssertionError();
   }

   @Shadow(remap = false)
   public static int getSoulGradeLevel(ServerPlayer player) {
      throw new AssertionError();
   }

   @Shadow(remap = false)
   public static int getMaxLocksForSoulGradeLevel(int soulGradeLevel) {
      throw new AssertionError();
   }

   @Shadow(remap = false)
   public static double getRequiredEpToUnlockSkill() {
      throw new AssertionError();
   }

   @Shadow(remap = false)
   public static int getRequiredSoulGradeToUnlockSkill() {
      throw new AssertionError();
   }

   @Shadow(remap = false)
   public static double getRequiredMagiculeToUnlockSkill() {
      throw new AssertionError();
   }

   @Shadow(remap = false)
   private static boolean isSkillLocked(ResourceLocation id, List<ResourceLocation> lockedSkills, List<ResourceLocation> adminLockedSkills) {
      throw new AssertionError();
   }

   @Shadow(remap = false)
   private static boolean isSkillAdminLocked(ResourceLocation id, List<ResourceLocation> adminLockedSkills) {
      throw new AssertionError();
   }

   @Shadow(remap = false)
   private static boolean isSkillInList(List<ResourceLocation> skills, ResourceLocation id) {
      throw new AssertionError();
   }

   @Inject(
      method = "isSkillLocked(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceLocation;)Z",
      at = @At("HEAD"),
      cancellable = true,
      remap = false
   )
   private static void foxablazeultimate$allowInternalRemoval(ServerPlayer player, ResourceLocation skillId, CallbackInfoReturnable<Boolean> cir) {
      if (UltimateSkillProtector.isInactivityRemoval(player, skillId)
         || isCielFamilySkill(skillId) && UltimateSkillProtector.isEvolutionRemoval(player, skillId)) {
         cir.setReturnValue(false);
      }
   }

   private static boolean isCielFamilySkill(ResourceLocation skillId) {
      return FOXABLAZEULTIMATE$RAPHAEL.equals(skillId) || FOXABLAZEULTIMATE$CIEL.equals(skillId);
   }

   @Inject(
      method = "canLockSkill(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceLocation;)Z",
      at = @At("HEAD"),
      cancellable = true,
      remap = false
   )
   private static void foxablazeultimate$canLockCrystalUnlockedSkill(ServerPlayer player, ResourceLocation skillId, CallbackInfoReturnable<Boolean> cir) {
      if (player != null && skillId != null) {
         if (FoxAblazeUltimateConfig.disableCrystalSkillLocking(skillId)) {
            cir.setReturnValue(false);
         } else {
            boolean crystalUnlocked = CrystalLockState.isCrystalLocked(player, skillId);
            if (FOXABLAZEULTIMATE$SHUB_NIGGURATH.equals(skillId) && !crystalUnlocked) {
               cir.setReturnValue(false);
            } else if (crystalUnlocked) {
               List<ResourceLocation> lockedSkills = getLockedSkills(player);
               List<ResourceLocation> adminLockedSkills = getAdminLockedSkills(player);
               if (isSkillLocked(skillId, lockedSkills, adminLockedSkills)) {
                  cir.setReturnValue(false);
               } else {
                  cir.setReturnValue(lockedSkills.size() < getMaxLocksForSoulGradeLevel(getSoulGradeLevel(player)));
               }
            }
         }
      }
   }

   @Inject(
      method = "canUnlockSkill(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceLocation;)Z",
      at = @At("HEAD"),
      cancellable = true,
      remap = false
   )
   private static void foxablazeultimate$canUnlockCrystalUnlockedSkill(ServerPlayer player, ResourceLocation skillId, CallbackInfoReturnable<Boolean> cir) {
      if (player != null && skillId != null) {
         if (CrystalLockState.isCrystalLocked(player, skillId)) {
            List<ResourceLocation> lockedSkills = getLockedSkills(player);
            List<ResourceLocation> adminLockedSkills = getAdminLockedSkills(player);
            if (!isSkillAdminLocked(skillId, adminLockedSkills) && isSkillInList(lockedSkills, skillId)) {
               IExistence existence = TensuraStorages.getExistenceFrom(player);
               if (existence == null) {
                  cir.setReturnValue(false);
               } else {
                  cir.setReturnValue(
                     existence.getEP() >= getRequiredEpToUnlockSkill()
                        && getSoulGrade(player) >= getRequiredSoulGradeToUnlockSkill()
                        && existence.getMagicule() >= getRequiredMagiculeToUnlockSkill()
                  );
               }
            } else {
               cir.setReturnValue(false);
            }
         }
      }
   }
}
