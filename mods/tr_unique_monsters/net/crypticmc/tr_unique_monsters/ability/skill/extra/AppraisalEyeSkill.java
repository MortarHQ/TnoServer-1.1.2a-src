package net.crypticmc.tr_unique_monsters.ability.skill.extra;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.crypticmc.tr_unique_monsters.config.configs.ability.skill.ExtraSkillConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AppraisalEyeSkill extends Skill {
   private static final ExtraSkillConfig.AppraisalEye CONFIG = ((ExtraSkillConfig)ConfigRegistry.getConfig(ExtraSkillConfig.class)).AppraisalEye;
   public static final int MODE_SCAN = 0;
   public static final int MODE_REMOTE_ANALYSIS = 1;
   public static final ResourceLocation SCAN_PRESENCE_ID = ResourceLocation.fromNamespaceAndPath("tr_unique_monsters", "appraisal_eye_scan_presence");
   public static final ResourceLocation SCAN_RADIUS_ID = ResourceLocation.fromNamespaceAndPath("tr_unique_monsters", "appraisal_eye_scan_radius");
   public static final ResourceLocation REMOTE_ZOOM_ID = ResourceLocation.fromNamespaceAndPath("tr_unique_monsters", "appraisal_eye_remote_zoom");
   public static final ResourceLocation REMOTE_ANALYSIS_ID = ResourceLocation.fromNamespaceAndPath("tr_unique_monsters", "appraisal_eye_remote_analysis");
   private static final String TAG_REMOTE_ACTIVATED_TIMES = "remoteActivatedTimes";
   private static final String TAG_FARSIGHT_RANGE = "range";
   private static final String TAG_SCAN_FORCED_THIRD_PERSON = "scanForcedThirdPerson";

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tr_unique_monsters", "textures/skill/extra/appraisal_eye.png");
   }

   public AppraisalEyeSkill() {
      super(SkillType.EXTRA);
      this.addHeldAttributeModifier(TensuraAttributes.PRESENCE_SENSE, SCAN_PRESENCE_ID, CONFIG.scanPresenceLevel, Operation.ADD_VALUE);
      this.addHeldAttributeModifier(TensuraAttributes.PRESENCE_SENSE_RADIUS, SCAN_RADIUS_ID, CONFIG.scanRadius, Operation.ADD_VALUE);
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "appraisal_eye.scan";
         case 1 -> "appraisal_eye.remote_analysis";
         default -> super.getModeId(instance, mode);
      };
   }

   protected boolean canActivateInRaceLimit(ManasSkillInstance instance, int mode) {
      return true;
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      return true;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 1 ? 15.0 : 10.0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 0.6F, 1.0F);
      if (mode == 0 && entity instanceof ServerPlayer sp) {
         this.forceThirdPersonForScan(instance, sp, true);
      }

      if (mode == 1 && entity instanceof ServerPlayer sp) {
         this.applyRemoteAnalysis(instance, sp);
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
         instance.addMasteryPoint(entity);
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
      if (mode == 0 || mode == 1) {
         boolean mastered = instance.isMastered(entity);
         int cooldown;
         if (mode == 0) {
            cooldown = mastered ? CONFIG.scanReleaseCooldownMastered : CONFIG.scanReleaseCooldown;
         } else {
            cooldown = mastered ? CONFIG.remoteReleaseCooldownMastered : CONFIG.remoteReleaseCooldown;
         }

         instance.setCoolDown(cooldown, mode);
      }

      if (mode == 0 && entity instanceof ServerPlayer sp) {
         this.forceThirdPersonForScan(instance, sp, false);
      }

      if (mode == 1 && entity instanceof ServerPlayer sp) {
         this.removeRemoteAnalysis(sp);
      }
   }

   public void removeAttributeModifiers(ManasSkillInstance instance, LivingEntity entity, int mode) {
      super.removeAttributeModifiers(instance, entity, mode);
      if (mode == 0 && entity instanceof ServerPlayer sp) {
         this.forceThirdPersonForScan(instance, sp, false);
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         AttributeInstance analysisLevel = player.getAttribute(TensuraAttributes.ANALYSIS_LEVEL);
         if (analysisLevel != null && analysisLevel.hasModifier(REMOTE_ANALYSIS_ID)) {
            double analysisDistance = player.getAttributeValue(TensuraAttributes.ANALYSIS_DISTANCE);
            if (!(analysisDistance <= 0.01)) {
               boolean hasTarget = ObjectSelectionHelper.getTargetingEntity(player, analysisDistance, false, true) != null;
               if (!hasTarget) {
                  BlockHitResult hit = ObjectSelectionHelper.getPlayerPOVHitResult(player.level(), player, Fluid.NONE, Block.OUTLINE, analysisDistance);
                  hasTarget = !player.level().getBlockState(hit.getBlockPos()).isAir();
               }

               if (hasTarget) {
                  CompoundTag tag = instance.getOrCreateTag();
                  int activatedTimes = tag.getInt("remoteActivatedTimes");
                  if (activatedTimes % BASE_CONFIG.Mastery.masteryActivateTime == 0) {
                     instance.addMasteryPoint(entity);
                  }

                  tag.putInt("remoteActivatedTimes", activatedTimes + 1);
               }
            }
         }
      }
   }

   private void forceThirdPersonForScan(ManasSkillInstance instance, ServerPlayer sp, boolean enable) {
      ITensuraPlayer data = TensuraStorages.getPlayerDataFrom(sp);
      CompoundTag tag = instance.getOrCreateTag();
      if (enable) {
         boolean alreadyForced = false;

         try {
            alreadyForced = data.isForcedThirdPerson();
         } catch (Throwable var8) {
         }

         if (!alreadyForced) {
            tag.putBoolean("scanForcedThirdPerson", true);
            instance.markDirty();
         }

         data.setForcedThirdPerson(true);
         data.markDirty();
      } else if (tag.getBoolean("scanForcedThirdPerson")) {
         tag.putBoolean("scanForcedThirdPerson", false);
         instance.markDirty();
         data.setForcedThirdPerson(false);
         data.markDirty();
      }
   }

   private void applyRemoteAnalysis(ManasSkillInstance instance, ServerPlayer sp) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.contains("range")) {
         tag.putDouble("range", CONFIG.remoteBaseZoom);
         instance.markDirty();
      }

      AttributeInstance zoom = sp.getAttribute(TensuraAttributes.VIEW_ZOOM);
      if (zoom != null) {
         zoom.addOrUpdateTransientModifier(new AttributeModifier(REMOTE_ZOOM_ID, tag.getDouble("range"), Operation.ADD_VALUE));
      }

      AttributeHelper.addAnalysisAttributes(sp, CONFIG.remoteSenseLevel, CONFIG.remoteSenseRadius, REMOTE_ANALYSIS_ID);
      sp.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   private void removeRemoteAnalysis(ServerPlayer sp) {
      AttributeInstance zoom = sp.getAttribute(TensuraAttributes.VIEW_ZOOM);
      if (zoom != null) {
         zoom.removeModifier(REMOTE_ZOOM_ID);
      }

      AttributeInstance analysisDist = sp.getAttribute(TensuraAttributes.ANALYSIS_DISTANCE);
      if (analysisDist != null) {
         analysisDist.removeModifier(REMOTE_ZOOM_ID);
      }

      AttributeHelper.removeAnalysisAttributes(sp, true, true, false, REMOTE_ANALYSIS_ID);
   }
}
