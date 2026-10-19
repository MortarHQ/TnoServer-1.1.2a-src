package org.crypticdev.ancient_artifacts.mixin.world.entity.player;

import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import java.util.List;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.DataValue;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.crypticdev.ancient_artifacts.AncientArtifacts;
import org.crypticdev.ancient_artifacts.IPlayerArmorTracker;
import org.crypticdev.ancient_artifacts.artifact.ArtifactDefinition;
import org.crypticdev.ancient_artifacts.artifact.ArtifactEffects;
import org.crypticdev.ancient_artifacts.artifact.ArtifactItem;
import org.crypticdev.ancient_artifacts.artifact.ArtifactRefinement;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.ArtifactAttributesRegistry;
import org.crypticdev.ancient_artifacts.registry.Artifacts;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayerArmorTracker {
   @Shadow
   @Final
   private Abilities abilities;
   @Unique
   private static final EntityDataAccessor<Integer> LAST_TARGET_ARMOR = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

   @Shadow
   public abstract ItemCooldowns getCooldowns();

   @Shadow
   public abstract Inventory getInventory();

   @Inject(method = "defineSynchedData", at = @At("TAIL"))
   private void onDefineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder, CallbackInfo ci) {
      builder.define(LAST_TARGET_ARMOR, 0);
   }

   @Override
   public void your_mod_id$setLastTargetArmor(int armorPoints) {
      Player player = (Player)this;
      player.getEntityData().set(LAST_TARGET_ARMOR, armorPoints);
      if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
         List<DataValue<?>> packedData = player.getEntityData().packDirty();
         if (packedData != null) {
            serverPlayer.connection.send(new ClientboundSetEntityDataPacket(player.getId(), packedData));
         }
      }
   }

   @Override
   public int your_mod_id$getLastTargetArmor() {
      Player player = (Player)this;
      return (Integer)player.getEntityData().get(LAST_TARGET_ARMOR);
   }

   protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
      super(entityType, level);
   }

   @Inject(method = "createAttributes", at = @At("RETURN"))
   private static void addCustomAttributes(CallbackInfoReturnable<Builder> cir) {
      Builder builder = (Builder)cir.getReturnValue();
      builder.add(Attributes.FLYING_SPEED, 1.0);
      builder.add(ArtifactAttributesRegistry.MAGIC_BULLET_CHARGE_SPEED);
      builder.add(ArtifactAttributesRegistry.HELL_FLARE_DAMAGE_MULTIPLIER);
   }

   @Inject(method = "getFlyingSpeed", at = @At("HEAD"), cancellable = true)
   private void getNewFlySpeed(CallbackInfoReturnable<Float> cir) {
      if (this.abilities.flying && !this.isPassenger()) {
         cir.setReturnValue(
            this.isSprinting()
               ? (float)(this.abilities.getFlyingSpeed() * this.getAttributeValue(Attributes.FLYING_SPEED) * 2.0)
               : (float)(this.abilities.getFlyingSpeed() * this.getAttributeValue(Attributes.FLYING_SPEED))
         );
      } else {
         cir.setReturnValue(this.isSprinting() ? 0.025999999F : 0.02F);
      }
   }

   @Inject(method = "attack", at = @At("HEAD"))
   private void beforeAttack(Entity entity, CallbackInfo ci) {
      Player player = (Player)this;
      if (entity instanceof LivingEntity livingEntity && PlatformEquipment.hasArtifact(player, Artifacts.ShadowbindBracelet)) {
         ItemStack stack = PlatformEquipment.getArtifactStack(player, Artifacts.ShadowbindBracelet);
         ArtifactItem item = (ArtifactItem)stack.getItem();
         RefinementComponent refinement = ArtifactRefinement.get(stack);
         ArtifactDefinition definition = item.getArtifact();
         int refinementSteps = refinement != null ? Math.max(0, refinement.refinement() - 1) : 0;
         int maxRefinementSteps = refinement != null ? RefinementComponent.GRADES.getOrDefault(refinement.grade(), 0) : 0;
         refinementSteps = Math.min(refinementSteps, maxRefinementSteps);
         if (ArtifactEffects.EpUnlock(stack, AncientArtifacts.CONFIG.getGradeConfig(1).EffectUnlockEp.get(1))) {
            AttributeInstance attr = player.getAttribute(ManasCoreAttributes.CRITICAL_ATTACK_CHANCE);
            if (attr != null) {
               ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath("ancient_artifacts", definition.id() + "_armor_exploitation");
               attr.addTransientModifier(
                  new AttributeModifier(modifierId, Math.floor(livingEntity.getArmorValue() / 5.0) * (0.5 + refinementSteps * 0.05), Operation.ADD_VALUE)
               );
            }
         }
      }
   }

   @Inject(method = "attack", at = @At("TAIL"))
   private void afterAttack(Entity target, CallbackInfo ci) {
      Player player = (Player)this;
      if (PlatformEquipment.hasArtifact(player, Artifacts.ShadowbindBracelet)) {
         ItemStack stack = PlatformEquipment.getArtifactStack(player, Artifacts.ShadowbindBracelet);
         ArtifactItem item = (ArtifactItem)stack.getItem();
         RefinementComponent refinement = ArtifactRefinement.get(stack);
         ArtifactDefinition definition = item.getArtifact();
         int refinementSteps = refinement != null ? Math.max(0, refinement.refinement() - 1) : 0;
         int maxRefinementSteps = refinement != null ? RefinementComponent.GRADES.getOrDefault(refinement.grade(), 0) : 0;
         refinementSteps = Math.min(refinementSteps, maxRefinementSteps);
         if (ArtifactEffects.EpUnlock(stack, AncientArtifacts.CONFIG.getGradeConfig(1).EffectUnlockEp.get(1))) {
            AttributeInstance attr = player.getAttribute(ManasCoreAttributes.CRITICAL_ATTACK_CHANCE);
            if (attr != null) {
               ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath("ancient_artifacts", definition.id() + "_armor_exploitation");
               attr.removeModifier(modifierId);
            }
         }
      }
   }

   @Inject(method = "tick", at = @At("TAIL"))
   private void ancientArtifacts$tickUnlockableAndDynamicAttributes(CallbackInfo ci) {
      if (this instanceof Player player) {
         ArtifactEffects.tick(player);
      }
   }
}
