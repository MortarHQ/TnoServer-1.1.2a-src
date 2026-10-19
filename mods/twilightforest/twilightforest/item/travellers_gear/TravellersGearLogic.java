package twilightforest.item.travellers_gear;

import java.util.Collections;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import twilightforest.TwilightForestMod;
import twilightforest.components.entity.SlimySolesAttachment;
import twilightforest.components.entity.TravellersWingsAttachment;
import twilightforest.init.TFAttributeModifiers;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFDimensionData;
import twilightforest.init.TFItems;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.init.custom.TravellersModifiersManager;
import twilightforest.network.ParticlePacket;
import twilightforest.network.TravellersWingsStatePacket;
import twilightforest.util.TFMathUtil;

public class TravellersGearLogic {
   public static final double WATER_WALKING_MAX_SUBMERGED_HEIGHT = 0.4;
   private static final double AUTO_REPAIR_SUNLIGHT_BOOST = 3.0;
   private static final double AUTO_REPAIR_TWILIGHT_BOOST = 1.5;

   public static void travellersStealth(Player player, Consumer<Player> invisibilityHandler) {
      if (TravellersModifiersManager.isModifierActive(player, TravellersModifiersManager.STEALTH_MODIFIER)) {
         if (player.isCrouching()) {
            invisibilityHandler.accept(player);
         } else {
            MobEffectInstance invisibilityEffect = player.getEffect(MobEffects.INVISIBILITY);
            if (invisibilityEffect != null && invisibilityEffect.getDuration() < 2) {
               player.setInvisible(false);
            }
         }
      }
   }

   public static void waterWalkingSplashEffect(LivingEntity livingEntity) {
      Long lastTickWaterWalking = (Long)livingEntity.getData(TFDataAttachments.LAST_TICK_WATER_WALKING);
      Level level = livingEntity.level();
      Vec3 livingEntityVelocity = livingEntity.getKnownMovement();
      if (lastTickWaterWalking + 1L != level.getGameTime() && !(livingEntityVelocity.horizontalDistance() < 0.01)) {
         livingEntity.setData(TFDataAttachments.LAST_TICK_WATER_WALKING, livingEntity.level().getGameTime());
         ParticlePacket particlePacket = new ParticlePacket();

         for (int particleNumber = 0; particleNumber < livingEntity.dimensions.width(); particleNumber++) {
            double dx = (level.random.nextDouble() * 2.0 - 1.0) * livingEntity.dimensions.width() / 2.0;
            double dz = (level.random.nextDouble() * 2.0 - 1.0) * livingEntity.dimensions.width() / 2.0;
            Vec3 particlePos = new Vec3(livingEntity.getX() + dx, livingEntity.getY() + 0.4, livingEntity.getZ() + dz);
            Vec3 particleVelocity = new Vec3(-livingEntityVelocity.x, 0.5, -livingEntityVelocity.z);
            if (level.isClientSide()) {
               level.addParticle(
                  ParticleTypes.SPLASH,
                  false,
                  particlePos.x(),
                  particlePos.y(),
                  particlePos.z(),
                  particleVelocity.x(),
                  particleVelocity.y(),
                  particleVelocity.z()
               );
            } else {
               particlePacket.queueParticle(ParticleTypes.SPLASH, false, particlePos, particleVelocity);
            }
         }

         if (!level.isClientSide()) {
            PacketDistributor.sendToPlayersTrackingEntity(livingEntity, particlePacket, new CustomPacketPayload[0]);
         }
      }
   }

   public static boolean isBelowMaxWaterWalkingSubmergedHeight(LivingEntity livingEntity) {
      double waterHeight = livingEntity.getFluidTypeHeight((FluidType)NeoForgeMod.WATER_TYPE.value());
      return waterHeight < 0.4;
   }

   public static void travellersBootsStraightAhead(LivingEntity livingEntity) {
      ItemStack leggingsStack = livingEntity.getItemBySlot(EquipmentSlot.FEET);
      Double multiplier = (Double)leggingsStack.get(TFDataComponents.STRAIGHT_AHEAD_MULTIPLIER);
      AttributeInstance attributeInstance = livingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
      if (attributeInstance != null) {
         if (multiplier == null) {
            multiplier = 1.0;
         }

         boolean hasModifier = TravellersModifiersManager.isModifierActive(livingEntity, leggingsStack, TravellersModifiersManager.STRAIGHT_AHEAD_MODIFIER)
            && multiplier != 1.0;
         if (hasModifier != attributeInstance.hasModifier(TFAttributeModifiers.STRAIGHT_AHEAD_ATTRIBUTE_MODIFIER_LOCATION)) {
            if (hasModifier) {
               attributeInstance.addOrUpdateTransientModifier(
                  new AttributeModifier(TFAttributeModifiers.STRAIGHT_AHEAD_ATTRIBUTE_MODIFIER_LOCATION, multiplier - 1.0, Operation.ADD_MULTIPLIED_TOTAL)
               );
            } else {
               attributeInstance.removeModifier(TFAttributeModifiers.STRAIGHT_AHEAD_ATTRIBUTE_MODIFIER_LOCATION);
            }
         }
      }
   }

   public static void travellersWingsSidestepCooldownSound(Player player) {
      ItemStack leggingsStack = player.getItemBySlot(EquipmentSlot.LEGS);
      Long cooldown = (Long)leggingsStack.get(TFDataComponents.SIDESTEP_COOLDOWN);
      if (cooldown != null) {
         TravellersWingsAttachment attachment = (TravellersWingsAttachment)player.getData(TFDataAttachments.TRAVELLERS_WINGS);
         long dt = player.level().getGameTime() - attachment.lastSidestepTime;
         if (TravellersModifiersManager.isModifierActive(player, leggingsStack, TravellersModifiersManager.SIDESTEP_MODIFIER)
            && dt > cooldown
            && attachment.shouldPlaySideStepCooldownSound) {
            player.playNotifySound((SoundEvent)TFSounds.SIDE_STEP_CHARGED.get(), player.getSoundSource(), 1.0F, player.getVoicePitch());
            attachment.shouldPlaySideStepCooldownSound = false;
         }
      }
   }

   public static void travellersWingsGradualGlide(LivingEntity livingEntity) {
      ItemStack leggingsStack = livingEntity.getItemBySlot(EquipmentSlot.LEGS);
      Float multiplier = (Float)leggingsStack.get(TFDataComponents.GRADUALLY_GLIDING_MULTIPLIER);
      Vec3 deltaMovement = livingEntity.getDeltaMovement();
      if (TravellersModifiersManager.isModifierActive(livingEntity, leggingsStack, TravellersModifiersManager.GRADUAL_GLIDE_MODIFIER)
         && multiplier != null
         && !(deltaMovement.y() >= 0.0)
         && !livingEntity.isFallFlying()) {
         boolean isGraduallyGliding = !(livingEntity instanceof Player player && !(Boolean)player.getData(TFDataAttachments.IS_GRADUALLY_GLIDING));
         if (isGraduallyGliding) {
            double newDeltaMovementY = deltaMovement.y() * multiplier.floatValue();
            livingEntity.setDeltaMovement(deltaMovement.x(), newDeltaMovementY, deltaMovement.z());
            livingEntity.fallDistance = (float)(Math.pow(newDeltaMovementY, 2.0) / 2.0 / livingEntity.getGravity());
         }
      }
   }

   public static void travellersGearAutoRepair(LivingEntity livingEntity) {
      long lastHitTime = (Long)livingEntity.getData(TFDataAttachments.LAST_DAMAGE_ARMOR_TIME);
      if (livingEntity.level().getGameTime() - lastHitTime > 200L) {
         livingEntity.getArmorSlots().forEach(slot -> {
            Float probability = (Float)slot.get(TFDataComponents.AUTO_REPAIR_PROBABILITY);
            if (probability != null && TravellersModifiersManager.isModifierActive(livingEntity, slot, TravellersModifiersManager.AUTO_REPAIR_MODIFIER)) {
               Level level = livingEntity.level();
               double boostedProbability = getAutoRepairChance(probability.floatValue(), level, livingEntity.blockPosition());
               if (boostedProbability > level.random.nextFloat()) {
                  slot.setDamageValue(Math.max(slot.getDamageValue() - 1, 0));
               }
            }
         });
      }
   }

   private static double getAutoRepairChance(double baseProb, Level level, BlockPos pos) {
      if (!level.canSeeSky(pos)) {
         return baseProb;
      }

      double boostFactor;
      if (level.dimensionTypeRegistration().is(TFDimensionData.TWILIGHT_DIM_TYPE)) {
         boostFactor = 1.5;
      } else {
         if (!level.isDay()) {
            return baseProb;
         }

         boostFactor = 3.0;
      }

      return TFMathUtil.probabilityOfAtLeastOneSuccess(baseProb, boostFactor);
   }

   public static void travellersWingsHighJump(LivingEntity livingEntity) {
      ItemStack leggingsStack = livingEntity.getItemBySlot(EquipmentSlot.LEGS);
      Integer amplifier = (Integer)leggingsStack.get(TFDataComponents.HIGH_JUMP_AMPLIFIER);
      if (TravellersModifiersManager.isModifierActive(livingEntity, leggingsStack, TravellersModifiersManager.HIGH_JUMP_ABILITY) && amplifier != null) {
         livingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP, 2, amplifier, false, false, false));
      }
   }

   public static void travellersVestHaste(LivingEntity livingEntity) {
      ItemStack chestStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
      Integer amplifier = (Integer)chestStack.get(TFDataComponents.HASTE_AMPLIFIER);
      if (TravellersModifiersManager.isModifierActive(livingEntity, chestStack, TravellersModifiersManager.HASTE_MODIFIER) && amplifier != null) {
         livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2, amplifier, false, false, false));
      }
   }

   public static void travellersBootsUnrestrained(LivingEntity livingEntity) {
      if (TravellersModifiersManager.isModifierActive(livingEntity, TravellersModifiersManager.UNRESTRAINED_MODIFIER)) {
         livingEntity.stuckSpeedMultiplier = Vec3.ZERO;
      }
   }

   public static boolean tryPerformSidestep(Player player, boolean isLeftSidestep) {
      TravellersWingsAttachment attachment = (TravellersWingsAttachment)player.getData(TFDataAttachments.TRAVELLERS_WINGS);
      long lastSidestepTime = attachment.lastSidestepTime;
      ItemStack leggingsStack = player.getItemBySlot(EquipmentSlot.LEGS);
      Long cooldown = (Long)leggingsStack.get(TFDataComponents.SIDESTEP_COOLDOWN);
      long currentTime = player.level().getGameTime();
      if (TravellersModifiersManager.isModifierActive(player, leggingsStack, TravellersModifiersManager.SIDESTEP_MODIFIER)
         && cooldown != null
         && currentTime - lastSidestepTime > cooldown
         && !player.isFallFlying()
         && player.onGround()
         && !player.isCrouching()) {
         performSidestep(player, isLeftSidestep);
         attachment.lastSidestepTime = currentTime;
         attachment.shouldPlaySideStepCooldownSound = true;
         return true;
      } else {
         return false;
      }
   }

   public static void performSidestep(Player player, boolean isLeftSidestep) {
      float angle = player.getYRot();
      double rot = isLeftSidestep ? -Math.PI / 2 : Math.PI / 2;
      Vec3 dashDirection = new Vec3(-Math.sin(Math.toRadians(angle) + rot), 0.0, Math.cos(Math.toRadians(angle) + rot));
      player.push(dashDirection.scale(1.6));
      player.playSound((SoundEvent)TFSounds.SIDE_STEP.get(), 1.0F, player.getVoicePitch());
      TravellersWingsAttachment attachment = (TravellersWingsAttachment)player.getData(TFDataAttachments.TRAVELLERS_WINGS);
      TravellersWingsAttachment.WingState newState = TravellersWingsAttachment.WingState.SIDESTEP;
      attachment.state = newState;
      attachment.sidestepLeft = isLeftSidestep;
      attachment.sidestepTimer = 0;
      if (player.level() instanceof ServerLevel) {
         PacketDistributor.sendToPlayersTrackingEntityAndSelf(
            player,
            new TravellersWingsStatePacket(player.getId(), newState, isLeftSidestep, attachment.doubleJumpTimer, attachment.sidestepTimer),
            new CustomPacketPayload[0]
         );
      }
   }

   public static boolean performDoubleJump(Player player) {
      boolean hasDoubleJump = (Boolean)player.getData(TFDataAttachments.HAS_DOUBLE_JUMP);
      if (hasDoubleJump
         && !player.isFallFlying()
         && !player.onClimbable()
         && !player.onGround()
         && !player.isSwimming()
         && !player.getAbilities().flying
         && !player.isInLiquid()
         && !player.isPassenger()) {
         player.jumpFromGround();
         Vec3 velocity = player.getDeltaMovement();
         double boostVelocity = ((SlimySolesAttachment)player.getData(TFDataAttachments.SLIMY_SOLES_BOUNCE_INFO)).doubleJumpBoostVelocity;
         if (boostVelocity != 0.0) {
            player.setDeltaMovement(velocity.x(), Math.sqrt(Math.pow(velocity.y(), 2.0) + Math.pow(boostVelocity, 2.0)), velocity.z());
            ((SlimySolesAttachment)player.getData(TFDataAttachments.SLIMY_SOLES_BOUNCE_INFO)).doubleJumpBoostVelocity = 0.0;
         }

         player.resetFallDistance();
         float pitchShift = 0.1F;
         player.playSound((SoundEvent)TFSounds.DOUBLE_JUMP.get(), 1.5F, (player.getVoicePitch() - 1.0F) * (1.0F + pitchShift) + (1.0F - pitchShift * 0.2F));
         player.setData(TFDataAttachments.HAS_DOUBLE_JUMP, false);
         player.setData(TFDataAttachments.DOUBLE_JUMP_VALIDATOR, 0);
         AttributeInstance instance = player.getAttribute(Attributes.SAFE_FALL_DISTANCE);
         if (instance != null) {
            instance.addOrUpdateTransientModifier(TFAttributeModifiers.TRAVELLERS_DOUBLE_JUMP_SAFE_FALL_DISTANCE);
         }

         if (player.getItemBySlot(EquipmentSlot.LEGS).is(TFItems.TRAVELLERS_WINGS)) {
            TravellersWingsAttachment attachment = (TravellersWingsAttachment)player.getData(TFDataAttachments.TRAVELLERS_WINGS);
            attachment.state = TravellersWingsAttachment.WingState.DOUBLE_JUMP;
            attachment.doubleJumpTimer = 0;
         }

         if (player.level() instanceof ServerLevel serverLevel && player.getItemBySlot(EquipmentSlot.LEGS).is(TFItems.TRAVELLERS_WINGS)) {
            ParticlePacket particlePacket = new ParticlePacket();
            Vec3 deltaMovement = player.getDeltaMovement();

            for (int particleNumber = 0; particleNumber < 10; particleNumber++) {
               Vec3 particleVelocity = new Vec3(
                  serverLevel.random.nextDouble() - 0.5, serverLevel.random.nextDouble() + 1.0, serverLevel.random.nextDouble() - 0.5
               );
               ParticleOptions type = (ParticleOptions)TFParticleType.DOUBLE_JUMP.get();
               Vec3 wingsPosition = player.position()
                  .add(Math.sin(Math.toRadians(player.yBodyRot)) / 3.0, 1.2, -Math.cos(Math.toRadians(player.yBodyRot)) / 3.0);
               particlePacket.queueParticle(type, false, wingsPosition, particleVelocity.multiply(0.25, -0.5, 0.25).add(deltaMovement));
            }

            PacketDistributor.sendToPlayersTrackingEntityAndSelf(player, particlePacket, new CustomPacketPayload[0]);
            TravellersWingsAttachment attachment = (TravellersWingsAttachment)player.getData(TFDataAttachments.TRAVELLERS_WINGS);
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(
               player,
               new TravellersWingsStatePacket(
                  player.getId(),
                  TravellersWingsAttachment.WingState.DOUBLE_JUMP,
                  attachment.sidestepLeft,
                  attachment.doubleJumpTimer,
                  attachment.sidestepTimer
               ),
               new CustomPacketPayload[0]
            );
         }

         return true;
      } else {
         return false;
      }
   }

   public static void travellersBootsSlimySolesBounce(LivingEntity livingEntity) {
      SlimySolesAttachment slimySolesAttachment = (SlimySolesAttachment)livingEntity.getData(TFDataAttachments.SLIMY_SOLES_BOUNCE_INFO);
      if (slimySolesAttachment.bounceVelocity != 0.0 && !slimySolesAttachment.hasBounced) {
         Vec3 velocity = livingEntity.getDeltaMovement();
         livingEntity.playSound(SoundEvents.SLIME_JUMP, 0.5F, 1.0F);
         travellersBootsSlimySolesParticles(livingEntity, slimySolesAttachment);
         slimySolesAttachment.hasBounced = true;
         livingEntity.setDeltaMovement(velocity.x(), Math.sqrt(Math.pow(velocity.y(), 2.0) + Math.pow(slimySolesAttachment.bounceVelocity, 2.0)), velocity.z());
         slimySolesAttachment.forceBounce = Math.abs(livingEntity.getDeltaMovement().y()) > 0.25;
      }
   }

   public static void travellersBootsSlimySolesParticles(LivingEntity entity, SlimySolesAttachment attachment) {
      if (entity.level() instanceof ServerLevel level && !(attachment.bounceVelocity <= 0.0)) {
         double particleX = entity.getX();
         double particleY = entity.getY();
         double particleZ = entity.getZ();
         double intensity = Math.min(0.2 + attachment.bounceVelocity, 2.5);
         int count = (int)(40.0 * intensity);
         if (count > 0) {
            level.sendParticles(
               new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SLIME_BLOCK.defaultBlockState()),
               particleX,
               particleY,
               particleZ,
               count,
               0.0,
               0.0,
               0.0,
               0.15F
            );
         }
      }
   }

   private static void validateMovement(
      ServerPlayer serverPlayer,
      DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> validator,
      DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> lastCheck,
      String movementType
   ) {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if (server != null && server.isDedicatedServer()) {
         int count = (Integer)serverPlayer.getData(validator);
         int lastTick = (Integer)serverPlayer.getData(lastCheck);
         int currentTick = serverPlayer.tickCount;
         int diff = currentTick - lastTick;
         TwilightForestMod.LOGGER
            .debug(
               "{} {} check: count={}, lastTick={}, currentTick={}, diff={}",
               serverPlayer.getName().getString(),
               movementType,
               count,
               lastTick,
               currentTick,
               diff
            );
         if (diff >= 45 && !serverPlayer.isFallFlying()) {
            count = -1;
         }

         serverPlayer.setData(lastCheck, currentTick);
         if (count >= 5) {
            serverPlayer.connection.disconnect(new DisconnectionDetails(Component.translatable("multiplayer.disconnect.flying")));
         } else {
            serverPlayer.setData(validator, count + 1);
            if (count > 1) {
               TwilightForestMod.LOGGER.warn("{} illegal {}", serverPlayer.getName().getString(), movementType);
               serverPlayer.absMoveTo(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
               serverPlayer.connection
                  .send(
                     new ClientboundPlayerPositionPacket(
                        serverPlayer.getX(),
                        serverPlayer.getY(),
                        serverPlayer.getZ(),
                        serverPlayer.getYRot(),
                        serverPlayer.getXRot(),
                        Collections.emptySet(),
                        0
                     )
                  );
            }
         }
      }
   }

   public static void handleSidestepAbuse(Player player) {
      if (player instanceof ServerPlayer serverPlayer) {
         validateMovement(serverPlayer, TFDataAttachments.SIDESTEP_VALIDATOR, TFDataAttachments.SIDESTEP_VALIDATOR_LAST_CHECK, "sidestep");
      }
   }

   public static void handleDoubleJumpAbuse(Player player) {
      if (player instanceof ServerPlayer serverPlayer) {
         validateMovement(serverPlayer, TFDataAttachments.DOUBLE_JUMP_VALIDATOR, TFDataAttachments.DOUBLE_JUMP_VALIDATOR_LAST_CHECK, "double jump");
      }
   }

   public static void determineWingState(LivingEntity livingEntity) {
      TravellersWingsAttachment attachment = (TravellersWingsAttachment)livingEntity.getData(TFDataAttachments.TRAVELLERS_WINGS);
      TravellersWingsAttachment.WingState newState = TravellersWingsAttachment.WingState.IDLE;
      boolean isLocked = false;
      if (attachment.state == TravellersWingsAttachment.WingState.DOUBLE_JUMP) {
         attachment.doubleJumpTimer++;
         if (attachment.doubleJumpTimer < 15) {
            isLocked = true;
            newState = TravellersWingsAttachment.WingState.DOUBLE_JUMP;
         }
      } else if (attachment.state == TravellersWingsAttachment.WingState.SIDESTEP) {
         attachment.sidestepTimer++;
         if (attachment.sidestepTimer < 11) {
            isLocked = true;
            newState = attachment.state;
         }
      } else {
         attachment.doubleJumpTimer = 0;
         attachment.sidestepTimer = 0;
      }

      if (!isLocked) {
         if (livingEntity.isPassenger()) {
            newState = TravellersWingsAttachment.WingState.RIDE;
         } else if (livingEntity.isSwimming()) {
            newState = TravellersWingsAttachment.WingState.SWIM;
         } else if (livingEntity.onGround()
            || livingEntity.isInLiquid()
            || !(livingEntity.fallDistance < 2.3F)
            || livingEntity instanceof Player p && p.getAbilities().flying) {
            if (livingEntity.getDeltaMovement().y < 0.0 && livingEntity.fallDistance > 2.3F) {
               newState = TravellersWingsAttachment.WingState.FALL_FAST;
            } else if (livingEntity.isSprinting()) {
               newState = TravellersWingsAttachment.WingState.SPRINT;
            } else if (livingEntity.walkAnimation.speed() > 0.1) {
               newState = TravellersWingsAttachment.WingState.WALK;
            }
         } else {
            newState = TravellersWingsAttachment.WingState.FALL_SLOW;
         }
      }

      if (newState != attachment.state) {
         attachment.state = newState;
         PacketDistributor.sendToPlayersTrackingEntityAndSelf(
            livingEntity,
            new TravellersWingsStatePacket(livingEntity.getId(), newState, attachment.sidestepLeft, attachment.doubleJumpTimer, attachment.sidestepTimer),
            new CustomPacketPayload[0]
         );
      }
   }
}
