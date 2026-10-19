package com.foxablazeultimate.ability.skill.azathoth;

import com.foxablazeultimate.config.AzathothConfig;
import com.foxablazeultimate.network.AzathothChargeVisualPayload;
import com.foxablazeultimate.registry.FoxAblazeUltimateDataComponents;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.entity.magic.barrier.BarrierEntity;
import io.github.manasmods.tensura.entity.magic.barrier.BarrierPart;
import io.github.manasmods.tensura.entity.template.subclass.ILivingPartEntity;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import org.joml.Vector3f;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class AzathothCombat {
   public static final String CHARGING = "AzathothVoidCharging";
   public static final String HELD = "AzathothVoidHeldTicks";
   public static final String READY = "AzathothVoidChargeTicks";
   public static final String TOKEN = "AzathothVoidToken";
   private static final String SOUL_TARGET = "AzathothSoulTarget";
   private static final String SOUL_TICKS = "AzathothSoulTicks";
   private static final String SOUL_AREA = "AzathothSoulArea";
   private static final Map<UUID, AzathothCombat.ChargeSelection> CHARGE_SELECTIONS = new HashMap<>();
   private static final String CONSUMED = "FoxAzathothConsumed";
   private static final DustParticleOptions BLACK = new DustParticleOptions(new Vector3f(0.015F), 0.8F);
   private static final DustParticleOptions WHITE = new DustParticleOptions(new Vector3f(1.0F), 0.65F);

   private AzathothCombat() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player owner = event.getEntity();
      if (!owner.level().isClientSide()) {
         cancelChargeIfItemChanged(owner);
         if (owner.tickCount % 10 == 0) {
            SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)FoxAblazeUltimateSkills.AZATHOTH.get()).ifPresent(instance -> {
               CompoundTag tag = instance.getTag();
               if (tag != null && (tag.getBoolean("AzathothVoidCharging") || tag.hasUUID("AzathothVoidToken"))) {
                  sendVisual(instance, owner);
               }
            });
         }
      }
   }

   public static boolean isVisualActive(ManasSkillInstance instance, LivingEntity owner) {
      CompoundTag tag = instance.getTag();
      if (!owner.isAlive() || tag == null) {
         return false;
      }

      if (tag.getBoolean("AzathothVoidCharging")) {
         return true;
      }

      if (!tag.hasUUID("AzathothVoidToken")) {
         return false;
      }

      if (!isWeapon(owner.getMainHandItem())) {
         return tag.getInt("AzathothVoidChargeTicks") > 0;
      }

      AzathothChargeData charge = (AzathothChargeData)owner.getMainHandItem().get((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get());
      return charge != null && owner.getUUID().equals(charge.owner()) && tag.getUUID("AzathothVoidToken").equals(charge.token());
   }

   private static void sendVisual(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.level() instanceof ServerLevel level) {
         boolean var9 = isVisualActive(instance, owner);
         CompoundTag tag = instance.getOrCreateTag();
         UUID weaponToken = var9 && !tag.getBoolean("AzathothVoidCharging") && isWeapon(owner.getMainHandItem()) ? tag.getUUID("AzathothVoidToken") : null;
         AzathothChargeVisualPayload payload = new AzathothChargeVisualPayload(
            owner.getId(), owner.getUUID(), var9, tag.getBoolean("AzathothVoidCharging"), weaponToken
         );

         for (ServerPlayer player : level.players()) {
            if (player.distanceToSqr(owner) <= 4096.0) {
               NetworkManager.sendToPlayer(player, payload);
            }
         }
      }
   }

   public static void beginSoul(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("AzathothSoulTarget");
      tag.remove("AzathothSoulTicks");
   }

   public static void onSoulPressed(ManasSkillInstance instance, LivingEntity owner) {
      beginSoul(instance);
      if (owner.isShiftKeyDown()) {
         CompoundTag tag = instance.getOrCreateTag();
         boolean area = !tag.getBoolean("AzathothSoulArea");
         tag.putBoolean("AzathothSoulArea", area);
         instance.markDirty();
         if (owner instanceof Player player) {
            AzathothSpatial.announceSubMode(player, Component.translatable("foxablazeultimate.skill.azathoth.soul." + (area ? "area" : "individual")));
         }
      }
   }

   public static boolean holdSoul(AzathothSkill skill, ManasSkillInstance instance, LivingEntity owner) {
      if (owner.isShiftKeyDown()) {
         return false;
      }

      if (!(!instance.onCoolDown(0) && owner.level() instanceof ServerLevel level)) {
         return false;
      }

      if (instance.getOrCreateTag().getBoolean("AzathothSoulArea")) {
         return holdAreaSoul(skill, instance, owner, level);
      }

      AzathothConfig.Settings c = AzathothConfig.settings();
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(owner, Math.clamp(c.soulRange, 1.0, 128.0), false);
      if (!validTarget(owner, target)) {
         beginSoul(instance);
         return true;
      }

      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.hasUUID("AzathothSoulTarget") || !target.getUUID().equals(tag.getUUID("AzathothSoulTarget"))) {
         tag.putUUID("AzathothSoulTarget", target.getUUID());
         tag.putInt("AzathothSoulTicks", 0);
      }

      int elapsed = tag.getInt("AzathothSoulTicks") + 1;
      tag.putInt("AzathothSoulTicks", elapsed);
      if (elapsed % 4 == 0) {
         Vec3 point = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
         level.sendParticles(ParticleTypes.SOUL, point.x, point.y, point.z, 2, 0.2, 0.3, 0.2, 0.015);
      }

      if (elapsed < Math.max(1, c.soulHoldTicks)) {
         return true;
      }

      if (!canConsumeSoul(target)) {
         beginSoul(instance);
         return true;
      }

      beginSoul(instance);
      if (EnergyHelper.isOutOfEnergy(owner, instance, 0)) {
         return false;
      }

      consumeSoul(skill, instance, owner, target);
      instance.setCoolDown(Math.max(0, c.soulCooldownSeconds), 0);
      return false;
   }

   private static boolean holdAreaSoul(AzathothSkill skill, ManasSkillInstance instance, LivingEntity owner, ServerLevel level) {
      AzathothConfig.Settings c = AzathothConfig.settings();
      CompoundTag tag = instance.getOrCreateTag();
      int elapsed = tag.getInt("AzathothSoulTicks") + 1;
      tag.putInt("AzathothSoulTicks", elapsed);
      if (elapsed < Math.max(1, c.soulHoldTicks)) {
         return true;
      }

      beginSoul(instance);
      double radius = Math.clamp(c.soulAreaRadius, 1.0, 128.0);
      Map<UUID, LivingEntity> targets = new LinkedHashMap<>();

      for (LivingEntity part : level.getEntitiesOfClass(LivingEntity.class, owner.getBoundingBox().inflate(radius))) {
         LivingEntity target = ILivingPartEntity.checkForHead(part);
         if (target != owner
            && target.isAlive()
            && !target.isSpectator()
            && owner.distanceToSqr(target) <= radius * radius
            && !(target instanceof Player p && p.getAbilities().invulnerable)
            && !(target instanceof CloneEntity clone && owner.getUUID().equals(clone.getOwnerUUID()))
            && canConsumeSoul(target)) {
            targets.putIfAbsent(target.getUUID(), target);
         }
      }

      if (targets.isEmpty()) {
         return true;
      }

      if (EnergyHelper.isOutOfEnergy(owner, instance, 0)) {
         return false;
      }

      for (LivingEntity target : targets.values()) {
         if (target.isAlive() && canConsumeSoul(target)) {
            consumeSoul(skill, instance, owner, target);
         }
      }

      instance.setCoolDown(Math.max(0, c.soulCooldownSeconds), 0);
      return false;
   }

   private static boolean canConsumeSoul(LivingEntity target) {
      AzathothConfig.Settings c = AzathothConfig.settings();
      return target.getMaxHealth() <= c.soulStrongTargetHealth
         || target.getHealth() <= target.getMaxHealth() * Math.clamp(c.soulStrongTargetHealthFraction, 0.0, 1.0);
   }

   private static void consumeSoul(AzathothSkill skill, ManasSkillInstance instance, LivingEntity owner, LivingEntity target) {
      AzathothConfig.Settings c = AzathothConfig.settings();
      boolean mastered = instance.isMastered(owner);
      DamageSource source = skill.createSource(instance, owner, TensuraDamageTypes.SOUL_CONSUMED, 0);
      TensuraDamageSource ts = (TensuraDamageSource)source;
      ts.tensura$setDodgeBypass();
      ts.tensura$setBarrierBypassLevel(mastered ? (float)c.masteredPenetration : 1.0F);
      ts.tensura$setResistanceBypassLevel(mastered ? (float)c.masteredPenetration : 1.0F);
      hitAndConsume(instance, owner, target, source, (float)Math.min(8.5070587E37F, target.getMaxHealth() * 20.0 + target.getAbsorptionAmount()), true);
   }

   private static boolean validTarget(LivingEntity owner, LivingEntity target) {
      return target != null
         && target != owner
         && target.isAlive()
         && !target.isSpectator()
         && !target.isAlliedTo(owner)
         && !owner.isAlliedTo(target)
         && !(target instanceof Player p && p.getAbilities().invulnerable)
         && !(target instanceof CloneEntity clone && owner.getUUID().equals(clone.getOwnerUUID()));
   }

   public static int maxChargeTicks() {
      return Math.clamp(AzathothConfig.settings().voidMaxChargeSeconds, 1, 3600) * 20;
   }

   public static void beginCharge(ManasSkillInstance instance, LivingEntity owner) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.getBoolean("AzathothVoidCharging") && !instance.onCoolDown(1)) {
         if (!EnergyHelper.isOutOfEnergy(owner, instance, 1)) {
            clear(instance, owner);
            tag.putBoolean("AzathothVoidCharging", true);
            tag.putInt("AzathothVoidHeldTicks", 0);
            CHARGE_SELECTIONS.put(
               owner.getUUID(),
               new AzathothCombat.ChargeSelection(
                  owner instanceof Player player ? player.getInventory().selected : -1, owner.getMainHandItem(), owner.getMainHandItem().copy()
               )
            );
            instance.markDirty();
            sendVisual(instance, owner);
         }
      }
   }

   public static boolean holdCharge(ManasSkillInstance instance, LivingEntity owner, int heldTicks) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.getBoolean("AzathothVoidCharging")) {
         return false;
      }

      if (!chargeSelectionMatches(owner)) {
         clear(instance, owner);
         return false;
      }

      int ticks = Math.min(maxChargeTicks(), heldTicks + 1);
      tag.putInt("AzathothVoidHeldTicks", ticks);
      if (ticks % 5 == 0 && owner instanceof ServerPlayer player) {
         player.displayClientMessage(
            Component.translatable(
               "tensura.magic.cast_time.max",
               new Object[]{SkillUtils.ROUND_DOUBLE.format(ticks / 20.0), SkillUtils.ROUND_DOUBLE.format(maxChargeTicks() / 20.0)}
            ),
            true
         );
      }

      if (ticks >= maxChargeTicks()) {
         storeCharge(instance, owner);
         return false;
      } else {
         return true;
      }
   }

   public static void storeCharge(ManasSkillInstance instance, LivingEntity owner) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.getBoolean("AzathothVoidCharging")) {
         if (!chargeSelectionMatches(owner)) {
            clear(instance, owner);
         } else {
            int ticks = Math.clamp(tag.getInt("AzathothVoidHeldTicks"), 1, maxChargeTicks());
            tag.remove("AzathothVoidCharging");
            tag.remove("AzathothVoidHeldTicks");
            CHARGE_SELECTIONS.remove(owner.getUUID());
            if (!owner.isAlive()) {
               instance.markDirty();
            } else {
               UUID token = UUID.randomUUID();
               tag.putUUID("AzathothVoidToken", token);
               if (isWeapon(owner.getMainHandItem())) {
                  owner.getMainHandItem()
                     .set((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get(), new AzathothChargeData(owner.getUUID(), token, ticks));
                  tag.remove("AzathothVoidChargeTicks");
               } else {
                  tag.putInt("AzathothVoidChargeTicks", ticks);
               }

               int maximumCooldown = Math.max(0, AzathothConfig.settings().voidCooldownSeconds);
               int fullChargeTicks = maxChargeTicks();
               int cooldownSeconds = (int)(((long)maximumCooldown * ticks + fullChargeTicks - 1L) / fullChargeTicks);
               instance.setCoolDown(cooldownSeconds, 1);
               instance.addMasteryPoint(owner);
               instance.markDirty();
               sendVisual(instance, owner);
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onAttack(AttackEntityEvent event) {
      if (!event.getEntity().level().isClientSide()) {
         Player owner = event.getEntity();
         cancelChargeIfItemChanged(owner);
         ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(owner)
            .getSkill((ManasSkill)FoxAblazeUltimateSkills.AZATHOTH.get())
            .orElse(null);
         if (instance != null) {
            BarrierEntity barrier = event.getTarget() instanceof BarrierPart part
               ? part.getBarrier()
               : (event.getTarget() instanceof BarrierEntity whole ? whole : null);
            if (barrier != null && !barrier.isRemoved() && instance.isToggled() && AzathothConfig.settings().breakBarriersOnAttack) {
               barrier.setHealth(0.0F);
               barrier.discard();
               owner.level()
                  .playSound(
                     null, owner.getX(), owner.getY(), owner.getZ(), (SoundEvent)TensuraSoundEvents.BARRIER_BREAK.get(), SoundSource.PLAYERS, 1.0F, 1.0F
                  );
               owner.swing(InteractionHand.MAIN_HAND, true);
               owner.resetAttackStrengthTicker();
               event.setCanceled(true);
            } else if (event.getTarget() instanceof LivingEntity target && validTarget(owner, target)) {
               CompoundTag tag = instance.getOrCreateTag();
               if (tag.hasUUID("AzathothVoidToken")) {
                  AzathothChargeData weapon = (AzathothChargeData)owner.getMainHandItem()
                     .get((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get());
                  boolean usingWeapon = weapon != null && owner.getUUID().equals(weapon.owner()) && tag.getUUID("AzathothVoidToken").equals(weapon.token());
                  int ticks = usingWeapon ? weapon.ticks() : (!isWeapon(owner.getMainHandItem()) ? tag.getInt("AzathothVoidChargeTicks") : 0);
                  if (ticks > 0) {
                     tag.remove("AzathothVoidToken");
                     tag.remove("AzathothVoidChargeTicks");
                     if (usingWeapon) {
                        owner.getMainHandItem().remove((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get());
                     }

                     instance.markDirty();
                     sendVisual(instance, owner);
                     AzathothConfig.Settings c = AzathothConfig.settings();
                     double damage = Math.max(0.0, c.voidFullChargeDamage) * Math.clamp((double)ticks / maxChargeTicks(), 0.0, 1.0);
                     if (usingWeapon) {
                        damage += Math.max(0.0, owner.getAttributeValue(Attributes.ATTACK_DAMAGE)) * Math.max(0.0, c.voidWeaponDamageFraction);
                     }

                     boolean mastered = instance.isMastered(owner);
                     DamageSource source = ((AzathothSkill)FoxAblazeUltimateSkills.AZATHOTH.get())
                        .createSource(instance, owner, TensuraDamageTypes.DARKNESS_ELEMENTAL, 1);
                     TensuraDamageSource ts = (TensuraDamageSource)source;
                     ts.tensura$setBarrierBypassLevel(mastered ? (float)c.masteredPenetration : 1.0F);
                     ts.tensura$setResistanceBypassLevel(mastered ? (float)c.masteredPenetration : 0.0F);
                     event.setCanceled(true);
                     target.invulnerableTime = 0;
                     hitAndConsume(instance, owner, target, source, (float)Math.min(damage, 8.5070587E37F), mastered);
                     owner.swing(InteractionHand.MAIN_HAND, true);
                     owner.resetAttackStrengthTicker();
                     if (owner.level() instanceof ServerLevel level) {
                        Vec3 point = target.position().add(0.0, target.getBbHeight() / 2.0F, 0.0);
                        level.sendParticles(BLACK, point.x, point.y, point.z, 24, 0.45, 0.55, 0.45, 0.0);
                        level.sendParticles(WHITE, point.x, point.y, point.z, 16, 0.35, 0.45, 0.35, 0.0);
                        level.playSound(null, point.x, point.y, point.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 0.7F, 1.5F);
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLogout(PlayerLoggedOutEvent event) {
      SkillAPI.getSkillsFrom(event.getEntity())
         .getSkill((ManasSkill)FoxAblazeUltimateSkills.AZATHOTH.get())
         .ifPresent(instance -> cancelUnfinishedCharge(instance, event.getEntity()));
      CHARGE_SELECTIONS.remove(event.getEntity().getUUID());
   }

   @SubscribeEvent
   public static void onLogin(PlayerLoggedInEvent event) {
      SkillAPI.getSkillsFrom(event.getEntity())
         .getSkill((ManasSkill)FoxAblazeUltimateSkills.AZATHOTH.get())
         .ifPresent(instance -> cancelUnfinishedCharge(instance, event.getEntity()));
   }

   private static void cancelUnfinishedCharge(ManasSkillInstance instance, LivingEntity owner) {
      if (instance.getOrCreateTag().getBoolean("AzathothVoidCharging")) {
         clear(instance, owner);
      }
   }

   private static boolean chargeSelectionMatches(LivingEntity owner) {
      AzathothCombat.ChargeSelection selection = CHARGE_SELECTIONS.get(owner.getUUID());
      return selection != null && selection.matches(owner);
   }

   public static void cancelChargeIfItemChanged(Player owner) {
      if (!owner.level().isClientSide() && CHARGE_SELECTIONS.containsKey(owner.getUUID()) && !chargeSelectionMatches(owner)) {
         SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)FoxAblazeUltimateSkills.AZATHOTH.get()).ifPresent(instance -> clear(instance, owner));
         CHARGE_SELECTIONS.remove(owner.getUUID());
      }
   }

   private static void hitAndConsume(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, float damage, boolean consume) {
      IExistence victim = TensuraStorages.getExistenceFrom(target);
      boolean eligible = consume
         && !(target instanceof CloneEntity)
         && !victim.isSkippingEPDrop()
         && !target.getPersistentData().getBoolean("FoxAzathothConsumed");
      double ep = eligible ? Math.max(0.0, EnergyHelper.getEPGain(target, owner, true)) : 0.0;
      double magicules = Math.max(0.0, victim.getMagicule());
      boolean skipping = victim.isSkippingEPDrop();
      if (eligible) {
         victim.setSkippingEPDrop(true);
      }

      try {
         boolean hit = target.hurt(source, damage);
         if (!hit || !eligible || target.isAlive()) {
            return;
         }

         target.getPersistentData().putBoolean("FoxAzathothConsumed", true);
         AzathothConfig.Settings c = AzathothConfig.settings();
         double gain = ep * Math.clamp(c.soulEpGainFraction, 0.0, 1.0);
         if (Double.isFinite(gain)) {
            EnergyHelper.gainMagicule(owner, gain / 2.0, GainType.MAX);
            EnergyHelper.gainAura(owner, gain / 2.0, GainType.MAX);
         }

         double recovered = magicules * Math.max(0.0, c.soulMagiculeGainFraction) + target.getMaxHealth() * Math.max(0.0, c.soulBodyMagiculesPerHealth);
         if (Double.isFinite(recovered)) {
            EnergyHelper.gainMagicule(owner, recovered, GainType.NORMAL);
         }

         victim.setMagicule(0.0);
         victim.setAura(0.0);
         victim.setSpiritualHealth(0.0);
         victim.markDirty();
         instance.addMasteryPoint(owner);
         if (!(target instanceof Player)) {
            target.discard();
         }
      } finally {
         if (target.isAlive() || !target.getPersistentData().getBoolean("FoxAzathothConsumed")) {
            victim.setSkippingEPDrop(skipping);
         }
      }
   }

   public static Vec3 handPosition(LivingEntity owner) {
      double yaw = Math.toRadians(owner.yBodyRot);
      double sign = owner.getMainArm() == HumanoidArm.RIGHT ? 1.0 : -1.0;
      return owner.position()
         .add(-Math.cos(yaw) * owner.getBbWidth() * 0.6 * sign, owner.getBbHeight() * 0.6, -Math.sin(yaw) * owner.getBbWidth() * 0.6 * sign)
         .add(owner.getLookAngle().scale(0.35));
   }

   public static boolean isWeapon(ItemStack stack) {
      if (stack.isEmpty()) {
         return false;
      } else if (stack.is(ItemTags.SWORDS)
         || stack.is(ItemTags.AXES)
         || stack.getItem() instanceof SwordItem
         || stack.getItem() instanceof AxeItem
         || stack.getItem() instanceof TridentItem
         || stack.getItem() instanceof MaceItem) {
         return true;
      } else if (!stack.canPerformAction(ItemAbilities.SWORD_DIG)
         && !stack.canPerformAction(ItemAbilities.SWORD_SWEEP)
         && !stack.canPerformAction(ItemAbilities.AXE_DIG)) {
         boolean[] weapon = new boolean[]{false};
         stack.forEachModifier(EquipmentSlot.MAINHAND, (attribute, modifier) -> {
            if (attribute.equals(Attributes.ATTACK_DAMAGE) && modifier.amount() > 0.0) {
               weapon[0] = true;
            }
         });
         return weapon[0];
      } else {
         return true;
      }
   }

   public static void clear(ManasSkillInstance instance, LivingEntity owner) {
      CHARGE_SELECTIONS.remove(owner.getUUID());
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("AzathothVoidCharging");
      tag.remove("AzathothVoidHeldTicks");
      tag.remove("AzathothVoidChargeTicks");
      tag.remove("AzathothVoidToken");
      sendVisual(instance, owner);
      beginSoul(instance);
      if (owner instanceof Player player) {
         for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            AzathothChargeData data = (AzathothChargeData)stack.get((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get());
            if (data != null && owner.getUUID().equals(data.owner())) {
               stack.remove((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get());
            }
         }
      }

      instance.markDirty();
   }

   private record ChargeSelection(int slot, ItemStack original, ItemStack snapshot) {
      boolean matches(LivingEntity owner) {
         ItemStack current = owner.getMainHandItem();
         return !(owner instanceof Player player && player.getInventory().selected != this.slot)
            && (current == this.original || current.isEmpty() && this.original.isEmpty())
            && ItemStack.matches(this.snapshot, current);
      }
   }
}
