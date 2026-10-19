package com.foxablazeultimate.ability.skill.ciel;

import com.foxablazeultimate.config.CielConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class CielEmergencyController {
   public static final String TAKEOVER_SYNC_TAG = "CielEmergencyTakeover";
   public static final String TAKEOVER_TARGET_TAG = "CielEmergencyTarget";
   public static final String TAKEOVER_MOVE_TAG = "CielEmergencyMove";
   public static final String TAKEOVER_SPRINT_TAG = "CielEmergencySprint";
   private static final String LEGACY_TAKEOVER_ESCAPE_JUMP_TAG = "CielEmergencyEscapeJump";
   private static final String LEGACY_TAKEOVER_ESCAPE_SNEAK_TAG = "CielEmergencyEscapeSneak";
   private static final String LEGACY_TAKEOVER_ESCAPE_DIRECTION_TAG = "CielEmergencyEscapeDirection";
   private static final TagKey<ManasSkill> CIEL_TAKEOVER_MELEE = TagKey.create(
      SkillAPI.getSkillRegistryKey(), ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "ciel_takeover_melee")
   );
   private static final int NO_TARGET = -1;
   private static final ThreadLocal<Boolean> AUTOMATED_ACTION = ThreadLocal.withInitial(() -> false);
   private static final Set<UUID> ACTIVE_PLAYERS = new HashSet<>();
   private static final Map<UUID, List<CielEmergencyController.AutoHold>> AUTO_HOLDS = new HashMap<>();
   private static final Map<UUID, Integer> NEXT_AUTO_SLOT = new HashMap<>();
   private static final Map<UUID, Long> NEXT_DRAIN_TIME = new HashMap<>();
   private static final Map<UUID, Integer> CURRENT_TARGETS = new HashMap<>();
   private static final Map<UUID, Long> NEXT_TARGET_SCAN_TIME = new HashMap<>();
   private static final Map<UUID, CielEmergencyController.TakeoverWeaponState> TAKEOVER_WEAPONS = new HashMap<>();

   private CielEmergencyController() {
   }

   public static boolean toggle(ServerPlayer player, ManasSkillInstance ciel) {
      boolean enabled = CielState.toggleEmergencyTakeover(player);
      cleanupAutomation(player);
      syncTakeoverState(player, ciel, enabled);
      return enabled;
   }

   public static boolean isAutomatedAction() {
      return AUTOMATED_ACTION.get();
   }

   public static boolean isClientTakeover(Player player) {
      ManasSkillInstance instance = getCielInstance(player);
      return instance != null && instance.getTag() != null && instance.getTag().getBoolean("CielEmergencyTakeover");
   }

   public static int getClientTargetId(Player player) {
      ManasSkillInstance instance = getCielInstance(player);
      return instance != null && instance.getTag() != null && instance.getTag().contains("CielEmergencyTarget", 3)
         ? instance.getTag().getInt("CielEmergencyTarget")
         : -1;
   }

   public static boolean shouldClientAdvance(Player player) {
      ManasSkillInstance instance = getCielInstance(player);
      return instance != null && instance.getTag() != null && instance.getTag().getBoolean("CielEmergencyMove");
   }

   public static boolean shouldClientSprint(Player player) {
      ManasSkillInstance instance = getCielInstance(player);
      return instance != null && instance.getTag() != null && instance.getTag().getBoolean("CielEmergencySprint");
   }

   private static ManasSkillInstance getCielInstance(Player player) {
      return (ManasSkillInstance)SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.CIEL.get()).orElse(null);
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (player.tickCount % 20 == 0) {
            CielPassiveHandler.refreshAttributes(player);
         }

         if (!CielState.isEmergencyTakeover(player)) {
            if (ACTIVE_PLAYERS.contains(player.getUUID())) {
               cleanupAutomation(player);
            }

            if (isClientTakeover(player)) {
               syncTakeoverState(player, null, false);
            }
         } else if (player.isAlive() && !player.isSpectator() && CielPassiveHandler.hasPermanentCiel(player)) {
            if (ACTIVE_PLAYERS.add(player.getUUID())) {
               cancelUnmanagedHeldSkills(player);
               syncTakeoverState(player, null, true);
               NEXT_DRAIN_TIME.put(player.getUUID(), player.level().getGameTime() + 20L);
            }

            CielConfig.CielSettings config = CielConfig.settings();
            long now = player.level().getGameTime();
            long nextDrain = NEXT_DRAIN_TIME.getOrDefault(player.getUUID(), now + 20L);
            if (now >= nextDrain) {
               NEXT_DRAIN_TIME.put(player.getUUID(), now + 20L);
               if (!drainMagicules(player, config)) {
                  forceStop(player, false);
                  player.displayClientMessage(
                     Component.translatable("foxablazeultimate.skill.ciel.takeover.insufficient_magicule").withStyle(ChatFormatting.RED), true
                  );
                  return;
               }
            }

            LivingEntity pursuitTarget = selectPursuitTarget(player, config, now);
            if (pursuitTarget == null) {
               processAutoHolds(player, false);
               updateClientControlState(player, null, false, false);
               applyMovementControl(player, false, false, false);
            } else {
               prepareStrongestWeapon(player, pursuitTarget, now, config);
               double stopDistance = Math.max(1.0, config.takeoverMeleeStopDistance);
               double distanceSquared = player.distanceToSqr(pursuitTarget);
               boolean hasSight = player.hasLineOfSight(pursuitTarget);
               boolean meleeRange = hasSight && player.canInteractWithEntity(pursuitTarget, 0.0);
               boolean advancing = !meleeRange && (distanceSquared > stopDistance * stopDistance || !hasSight);
               double sprintDistance = Math.max(stopDistance, config.takeoverSprintDistance);
               boolean sprinting = advancing && (distanceSquared > sprintDistance * sprintDistance || !hasSight);
               processAutoHolds(player, meleeRange);
               updateClientControlState(player, pursuitTarget, advancing, sprinting);
               applyMovementControl(player, advancing, sprinting, false);
               if (meleeRange) {
                  tryWeaponAttack(player, pursuitTarget);
               }

               int auraInterval = Math.max(1, config.killAuraIntervalTicks);
               int castInterval = Math.max(1, config.autoCastIntervalTicks);
               boolean auraDue = player.tickCount % auraInterval == 0;
               boolean castDue = player.tickCount % castInterval == 0;
               if (auraDue || castDue) {
                  if (!config.autoCastRequireLineOfSight || hasSight) {
                     if (auraDue && !meleeRange) {
                        pulseKillingAura(player, pursuitTarget, config);
                     }

                     if (castDue) {
                        autoCastPreset(player, pursuitTarget, config, meleeRange);
                     }
                  }
               }
            }
         } else {
            stopTakeover(player);
         }
      }
   }

   @SubscribeEvent
   public static void onManualAttack(AttackEntityEvent event) {
      if (event.getEntity() instanceof ServerPlayer player && CielState.isEmergencyTakeover(player) && !isAutomatedAction()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onManualEntityInteractSpecific(EntityInteractSpecific event) {
      if (shouldCancelManualInteraction(event)) {
         event.setCancellationResult(InteractionResult.FAIL);
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onManualEntityInteract(EntityInteract event) {
      if (shouldCancelManualInteraction(event)) {
         event.setCancellationResult(InteractionResult.FAIL);
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onManualRightClickBlock(RightClickBlock event) {
      if (shouldCancelManualInteraction(event)) {
         event.setCancellationResult(InteractionResult.FAIL);
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onManualRightClickItem(RightClickItem event) {
      if (shouldCancelManualInteraction(event)) {
         event.setCancellationResult(InteractionResult.FAIL);
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onManualLeftClickBlock(LeftClickBlock event) {
      if (shouldCancelManualInteraction(event)) {
         event.setCanceled(true);
      }
   }

   private static boolean shouldCancelManualInteraction(PlayerInteractEvent event) {
      return event.getEntity() instanceof ServerPlayer player && CielState.isEmergencyTakeover(player) && !isAutomatedAction();
   }

   @SubscribeEvent
   public static void onBlockBreak(BreakEvent event) {
      if (event.getPlayer() instanceof ServerPlayer player && CielState.isEmergencyTakeover(player) && !isAutomatedAction()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onLogout(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         cleanupAutomation(player);
      }
   }

   @SubscribeEvent
   public static void onDimensionChange(PlayerChangedDimensionEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         forceStop(player, false);
      }
   }

   private static void applyMovementControl(ServerPlayer player, boolean moving, boolean sprinting, boolean sneaking) {
      player.setSprinting(moving && sprinting);
   }

   private static LivingEntity selectPursuitTarget(ServerPlayer player, CielConfig.CielSettings config, long now) {
      UUID playerId = player.getUUID();
      double range = Math.max(1.0, config.takeoverTargetRange);
      LivingEntity attacker = player.getLastHurtByMob();
      if (attacker != null && isRetaliationTarget(player, attacker, config.autoCastTargetsPlayers) && player.distanceToSqr(attacker) <= range * range) {
         CURRENT_TARGETS.put(playerId, attacker.getId());
         NEXT_TARGET_SCAN_TIME.put(playerId, now + Math.max(1, config.takeoverTargetRefreshTicks));
         return attacker;
      }

      int currentId = CURRENT_TARGETS.getOrDefault(playerId, -1);
      LivingEntity current = (currentId == -1 ? null : player.level().getEntity(currentId)) instanceof LivingEntity living ? living : null;
      boolean currentValid = current != null
         && isHostileTarget(player, current, config.autoCastTargetsPlayers)
         && player.distanceToSqr(current) <= range * range;
      if (currentValid) {
         return current;
      }

      CURRENT_TARGETS.remove(playerId);
      long nextScan = NEXT_TARGET_SCAN_TIME.getOrDefault(playerId, 0L);
      if (now < nextScan) {
         return null;
      }

      LivingEntity selected = findNearestHostileTarget(player, range, config.autoCastTargetsPlayers);
      if (selected == null) {
         CURRENT_TARGETS.remove(playerId);
      } else {
         CURRENT_TARGETS.put(playerId, selected.getId());
      }

      NEXT_TARGET_SCAN_TIME.put(playerId, now + Math.max(1, config.takeoverTargetRefreshTicks));
      return selected;
   }

   private static void updateClientControlState(ServerPlayer player, LivingEntity target, boolean moving, boolean sprinting) {
      SkillStorage storage = SkillAPI.getSkillsFrom(player);
      ManasSkillInstance instance = (ManasSkillInstance)storage.getSkill((ManasSkill)FoxAblazeUltimateSkills.CIEL.get()).orElse(null);
      if (instance != null) {
         int targetId = target == null ? -1 : target.getId();
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getInt("CielEmergencyTarget") != targetId
            || tag.getBoolean("CielEmergencyMove") != moving
            || tag.getBoolean("CielEmergencySprint") != sprinting
            || tag.contains("CielEmergencyEscapeJump")
            || tag.contains("CielEmergencyEscapeSneak")
            || tag.contains("CielEmergencyEscapeDirection")) {
            tag.putInt("CielEmergencyTarget", targetId);
            tag.putBoolean("CielEmergencyMove", moving);
            tag.putBoolean("CielEmergencySprint", sprinting);
            removeLegacyEscapeState(tag);
            syncSkillState(player, storage, instance);
         }
      }
   }

   private static boolean drainMagicules(ServerPlayer player, CielConfig.CielSettings config) {
      double maximum = Math.max(0.0, EnergyHelper.getMaxMagicule(player));
      double percentage = Mth.clamp(config.takeoverDrainPercentPerSecond, 0.0, 100.0);
      double cost = maximum * percentage / 100.0;
      IExistence existence = TensuraStorages.getExistenceFrom(player);
      if (!(maximum <= 0.0) && !(existence.getMagicule() + 1.0E-7 < cost)) {
         existence.setMagicule(Math.max(0.0, existence.getMagicule() - cost));
         existence.markDirty();
         ((StorageHolder)player).manasCore$sync(player);
         return true;
      } else {
         return false;
      }
   }

   private static void pulseKillingAura(ServerPlayer player, LivingEntity target, CielConfig.CielSettings config) {
      double radius = Math.max(0.0, config.killAuraRadius);
      if (player.distanceToSqr(target) <= radius * radius) {
         tryWeaponAttack(player, target);
      }
   }

   private static boolean tryWeaponAttack(ServerPlayer player, LivingEntity target) {
      CielEmergencyController.TakeoverWeaponState weaponState = TAKEOVER_WEAPONS.get(player.getUUID());
      if (weaponState != null && player.level().getGameTime() < weaponState.readyAtTime) {
         return false;
      }

      if (player.getAttackStrengthScale(0.5F) < 0.9F) {
         return false;
      }

      runAutomated(() -> {
         player.swing(InteractionHand.MAIN_HAND, true);
         player.attack(target);
      });
      return true;
   }

   private static void prepareStrongestWeapon(ServerPlayer player, LivingEntity target, long now, CielConfig.CielSettings config) {
      if (!config.takeoverUseStrongestWeapon) {
         restoreTakeoverWeapon(player);
      } else {
         Inventory inventory = player.getInventory();
         CielEmergencyController.TakeoverWeaponState state = TAKEOVER_WEAPONS.computeIfAbsent(
            player.getUUID(), ignored -> new CielEmergencyController.TakeoverWeaponState(inventory.selected)
         );
         if (state.targetId != target.getId()) {
            state.targetId = target.getId();
            state.nextRefreshTime = now;
         }

         if (now < state.nextRefreshTime) {
            if (selectHotbarSlot(player, state.equippedHotbarSlot)) {
               state.readyAtTime = now + 1L;
            }
         } else {
            int bestSlot = findStrongestWeaponSlot(player, target);
            if (state.inventorySwapped && bestSlot != state.equippedHotbarSlot) {
               restoreWeaponSwap(player, state);
               state.readyAtTime = now + 1L;
               state.nextRefreshTime = now + 1L;
            } else {
               boolean changed = equipWeaponSlot(player, state, bestSlot);
               int refreshTicks = Mth.clamp(config.takeoverWeaponRefreshTicks, 1, 200);
               state.nextRefreshTime = now + (changed ? 1L : refreshTicks);
               if (changed) {
                  state.readyAtTime = now + 1L;
               }
            }
         }
      }
   }

   private static int findStrongestWeaponSlot(ServerPlayer player, LivingEntity target) {
      Inventory inventory = player.getInventory();
      int bestSlot = inventory.selected;
      CielEmergencyController.WeaponScore best = scoreWeapon(player, target, inventory.getItem(bestSlot), bestSlot);

      for (int slot = 0; slot < 36; slot++) {
         if (slot != inventory.selected) {
            ItemStack stack = inventory.getItem(slot);
            if (!stack.isEmpty()) {
               CielEmergencyController.WeaponScore candidate = scoreWeapon(player, target, stack, slot);
               if (candidate.isBetterThan(best)) {
                  best = candidate;
                  bestSlot = slot;
               }
            }
         }
      }

      ItemStack offhand = inventory.getItem(40);
      if (!offhand.isEmpty()) {
         CielEmergencyController.WeaponScore candidate = scoreWeapon(player, target, offhand, 40);
         if (candidate.isBetterThan(best)) {
            bestSlot = 40;
         }
      }

      return bestSlot;
   }

   private static CielEmergencyController.WeaponScore scoreWeapon(ServerPlayer player, LivingEntity target, ItemStack stack, int sourceSlot) {
      double attackDamage = hypotheticalAttributeValue(player, stack, sourceSlot, Attributes.ATTACK_DAMAGE);
      DamageSource source = player.damageSources().playerAttack(player);
      float enchantedDamage = EnchantmentHelper.modifyDamage(player.serverLevel(), stack, target, source, (float)attackDamage);
      double itemBonus = stack.getItem().getAttackDamageBonus(target, (float)attackDamage, source);
      double attackSpeed = hypotheticalAttributeValue(player, stack, sourceSlot, Attributes.ATTACK_SPEED);
      double finalDamage = enchantedDamage + itemBonus;
      if (!Double.isFinite(finalDamage)) {
         finalDamage = Double.NEGATIVE_INFINITY;
      }

      if (!Double.isFinite(attackSpeed)) {
         attackSpeed = Double.NEGATIVE_INFINITY;
      }

      return new CielEmergencyController.WeaponScore(finalDamage, attackSpeed);
   }

   private static double hypotheticalAttributeValue(ServerPlayer player, ItemStack candidate, int sourceSlot, Holder<Attribute> attribute) {
      AttributeInstance instance = player.getAttribute(attribute);
      if (instance == null) {
         return 0.0;
      }

      Set<ResourceLocation> replacedIds = new HashSet<>();
      collectModifierIds(player.getMainHandItem(), EquipmentSlot.MAINHAND, attribute, replacedIds);
      if (sourceSlot == 40) {
         collectModifierIds(player.getOffhandItem(), EquipmentSlot.OFFHAND, attribute, replacedIds);
      }

      Map<ResourceLocation, AttributeModifier> modifiers = new HashMap<>();

      for (AttributeModifier modifier : instance.getModifiers()) {
         if (!replacedIds.contains(modifier.id())) {
            modifiers.put(modifier.id(), modifier);
         }
      }

      if (sourceSlot == 40) {
         collectStackModifiers(player.getMainHandItem(), EquipmentSlot.OFFHAND, attribute, modifiers);
      }

      collectStackModifiers(candidate, EquipmentSlot.MAINHAND, attribute, modifiers);
      double base = instance.getBaseValue();
      double value = base;

      for (AttributeModifier modifier : modifiers.values()) {
         if (modifier.operation() == Operation.ADD_VALUE) {
            value += modifier.amount();
         }
      }

      double multiplied = value;

      for (AttributeModifier modifier : modifiers.values()) {
         if (modifier.operation() == Operation.ADD_MULTIPLIED_BASE) {
            multiplied += value * modifier.amount();
         }
      }

      for (AttributeModifier modifier : modifiers.values()) {
         if (modifier.operation() == Operation.ADD_MULTIPLIED_TOTAL) {
            multiplied *= 1.0 + modifier.amount();
         }
      }

      return ((Attribute)attribute.value()).sanitizeValue(multiplied);
   }

   private static void collectModifierIds(ItemStack stack, EquipmentSlot slot, Holder<Attribute> attribute, Set<ResourceLocation> destination) {
      stack.forEachModifier(slot, (candidateAttribute, modifier) -> {
         if (candidateAttribute.equals(attribute)) {
            destination.add(modifier.id());
         }
      });
   }

   private static void collectStackModifiers(
      ItemStack stack, EquipmentSlot slot, Holder<Attribute> attribute, Map<ResourceLocation, AttributeModifier> destination
   ) {
      stack.forEachModifier(slot, (candidateAttribute, modifier) -> {
         if (candidateAttribute.equals(attribute)) {
            destination.put(modifier.id(), modifier);
         }
      });
   }

   private static boolean equipWeaponSlot(ServerPlayer player, CielEmergencyController.TakeoverWeaponState state, int weaponSlot) {
      Inventory inventory = player.getInventory();
      if (weaponSlot == state.equippedHotbarSlot) {
         return selectHotbarSlot(player, state.equippedHotbarSlot);
      }

      if (state.inventorySwapped) {
         restoreWeaponSwap(player, state);
      }

      if (Inventory.isHotbarSlot(weaponSlot)) {
         boolean changed = inventory.selected != weaponSlot;
         state.equippedHotbarSlot = weaponSlot;
         selectHotbarSlot(player, weaponSlot);
         return changed;
      } else {
         int destination = inventory.selected;
         ItemStack weapon = inventory.getItem(weaponSlot);
         ItemStack displaced = inventory.getItem(destination);
         inventory.setItem(destination, weapon);
         inventory.setItem(weaponSlot, displaced);
         state.inventorySwapped = true;
         state.swapSourceSlot = weaponSlot;
         state.equippedHotbarSlot = destination;
         state.swappedWeapon = weapon;
         state.displacedStack = displaced;
         selectHotbarSlot(player, destination);
         broadcastInventoryChanges(player);
         return true;
      }
   }

   private static void restoreTakeoverWeapon(ServerPlayer player) {
      CielEmergencyController.TakeoverWeaponState state = TAKEOVER_WEAPONS.remove(player.getUUID());
      if (state != null) {
         restoreWeaponSwap(player, state);
         selectHotbarSlot(player, state.originalSelectedSlot);
      }
   }

   private static void restoreWeaponSwap(ServerPlayer player, CielEmergencyController.TakeoverWeaponState state) {
      if (state.inventorySwapped) {
         Inventory inventory = player.getInventory();
         ItemStack equipped = inventory.getItem(state.equippedHotbarSlot);
         ItemStack source = inventory.getItem(state.swapSourceSlot);
         if (equipped == state.swappedWeapon && source == state.displacedStack) {
            inventory.setItem(state.equippedHotbarSlot, source);
            inventory.setItem(state.swapSourceSlot, equipped);
            broadcastInventoryChanges(player);
         }

         state.inventorySwapped = false;
         state.swapSourceSlot = -1;
         state.equippedHotbarSlot = state.originalSelectedSlot;
         state.swappedWeapon = ItemStack.EMPTY;
         state.displacedStack = ItemStack.EMPTY;
      }
   }

   private static boolean selectHotbarSlot(ServerPlayer player, int slot) {
      if (Inventory.isHotbarSlot(slot) && player.getInventory().selected != slot) {
         player.getInventory().selected = slot;
         player.connection.send(new ClientboundSetCarriedItemPacket(slot));
         return true;
      } else {
         return false;
      }
   }

   private static void broadcastInventoryChanges(ServerPlayer player) {
      player.getInventory().setChanged();
      player.inventoryMenu.broadcastChanges();
      if (player.containerMenu != player.inventoryMenu) {
         player.containerMenu.broadcastChanges();
      }
   }

   private static void autoCastPreset(ServerPlayer player, LivingEntity target, CielConfig.CielSettings config, boolean meleeRange) {
      IAbility ability = TensuraStorages.getAbilityFrom(player);
      List<AbilitySlot> slots = ability.getAbilitySlots(ability.getActivePreset());
      if (!slots.isEmpty()) {
         SkillStorage storage = SkillAPI.getSkillsFrom(player);
         int start = Math.floorMod(NEXT_AUTO_SLOT.getOrDefault(player.getUUID(), 0), slots.size());
         int maximum = Math.max(0, config.autoCastMaxSkillsPerCycle);
         int activated = 0;

         for (int offset = 0; offset < slots.size() && activated < maximum; offset++) {
            int slotIndex = (start + offset) % slots.size();
            AbilitySlot slot = slots.get(slotIndex);
            ManasSkill skill = slot.getSkill();
            if (skill != null && skill != FoxAblazeUltimateSkills.CIEL.get()) {
               ManasSkillInstance instance = (ManasSkillInstance)storage.getSkill(skill).orElse(null);
               int mode = slot.getMode();
               boolean meleeSkill = instance != null && instance.is(CIEL_TAKEOVER_MELEE);
               if (instance != null
                  && mode >= 0
                  && mode < instance.getModes()
                  && (!meleeRange || meleeSkill)
                  && !TickingSkill.isTickingSkill(player, skill, mode)
                  && (!instance.onCoolDown(mode) || instance.canIgnoreCoolDown(player, mode))) {
                  CielEmergencyController.RotationSnapshot rotation = aimForSkillActivation(player, target);

                  boolean started;
                  try {
                     started = runAutomatedResult(() -> storage.startHoldSkill(instance, slotIndex, mode));
                  } finally {
                     rotation.restore(player);
                  }

                  if (started) {
                     long end = player.level().getGameTime() + Math.max(1, config.autoCastHoldTicks);
                     AUTO_HOLDS.computeIfAbsent(player.getUUID(), ignored -> new ArrayList<>())
                        .add(new CielEmergencyController.AutoHold(instance.getSkillId(), slotIndex, mode, end, meleeSkill));
                     NEXT_AUTO_SLOT.put(player.getUUID(), (slotIndex + 1) % slots.size());
                     activated++;
                  }
               }
            }
         }
      }
   }

   private static void processAutoHolds(ServerPlayer player, boolean meleeRange) {
      List<CielEmergencyController.AutoHold> holds = AUTO_HOLDS.get(player.getUUID());
      if (holds != null && !holds.isEmpty()) {
         SkillStorage storage = SkillAPI.getSkillsFrom(player);
         long now = player.level().getGameTime();
         holds.removeIf(hold -> {
            if (meleeRange && !hold.melee()) {
               cancelAutoHoldWithoutRelease(player, storage, hold);
               return true;
            }

            if (now < hold.releaseTime()) {
               return false;
            }

            ManasSkillInstance instance = (ManasSkillInstance)storage.getSkill(hold.skillId()).orElse(null);
            if (instance != null && storage.getHeldSkills().stream().anyMatch(ticking -> ticking.matches(instance.getSkill(), hold.mode(), hold.keyNumber()))) {
               runAutomated(() -> storage.handleSkillRelease(instance, hold.keyNumber(), hold.mode(), false));
            }

            return true;
         });
         if (holds.isEmpty()) {
            AUTO_HOLDS.remove(player.getUUID());
         }
      }
   }

   private static void cancelAutoHoldWithoutRelease(ServerPlayer player, SkillStorage storage, CielEmergencyController.AutoHold hold) {
      ManasSkillInstance instance = (ManasSkillInstance)storage.getSkill(hold.skillId()).orElse(null);
      if (instance != null) {
         boolean removed = storage.getHeldSkills().removeIf(ticking -> ticking.matches(instance.getSkill(), hold.mode(), hold.keyNumber()));
         if (removed) {
            instance.removeAttributeModifiers(player, hold.mode());
            instance.markDirty();
            storage.markDirty();
         }
      }
   }

   private static void cancelAllAutoHoldsWithoutRelease(ServerPlayer player) {
      List<CielEmergencyController.AutoHold> holds = AUTO_HOLDS.remove(player.getUUID());
      if (holds != null) {
         SkillStorage storage = SkillAPI.getSkillsFrom(player);

         for (CielEmergencyController.AutoHold hold : holds) {
            cancelAutoHoldWithoutRelease(player, storage, hold);
         }
      }
   }

   private static LivingEntity findNearestHostileTarget(ServerPlayer player, double range, boolean targetPlayers) {
      double safeRange = Math.max(1.0, range);
      double rangeSquared = safeRange * safeRange;
      LivingEntity nearest = null;
      double nearestDistance = rangeSquared;

      for (LivingEntity candidate : player.level()
         .getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(safeRange), target -> isHostileTarget(player, target, targetPlayers))) {
         double distance = player.distanceToSqr(candidate);
         if (distance <= nearestDistance) {
            nearest = candidate;
            nearestDistance = distance;
         }
      }

      return nearest;
   }

   private static boolean isHostileTarget(ServerPlayer owner, LivingEntity target, boolean targetPlayers) {
      if (target == owner || !target.isAlive() || owner.isAlliedTo(target) || target.isAlliedTo(owner) || SubordinateHelper.isSubordinate(owner, target)) {
         return false;
      } else if (target instanceof Player player) {
         return targetPlayers && !player.isCreative() && !player.isSpectator();
      } else if (target.getType().is(TensuraEntityTags.HOSTILE_MONSTER) || target.getType().getCategory() == MobCategory.MONSTER || target instanceof Enemy) {
         return true;
      } else {
         return !(target instanceof Mob mob)
            ? false
            : mob.getTarget() == owner || mob.getLastHurtByMob() == owner || mob.getLastHurtMob() == owner || mob.getLastAttacker() == owner;
      }
   }

   private static boolean isRetaliationTarget(ServerPlayer owner, LivingEntity target, boolean targetPlayers) {
      if (target == owner || !target.isAlive() || owner.isAlliedTo(target) || target.isAlliedTo(owner) || SubordinateHelper.isSubordinate(owner, target)) {
         return false;
      } else {
         return !(target instanceof Player player) ? true : targetPlayers && !player.isCreative() && !player.isSpectator();
      }
   }

   private static void cancelUnmanagedHeldSkills(ServerPlayer player) {
      SkillStorage storage = SkillAPI.getSkillsFrom(player);

      for (TickingSkill ticking : List.copyOf(storage.getHeldSkills())) {
         if (ticking.getSkill() != FoxAblazeUltimateSkills.CIEL.get()) {
            storage.getSkill(ticking.getSkill()).ifPresent(instance -> {
               if (storage.getHeldSkills().remove(ticking)) {
                  instance.removeAttributeModifiers(player, ticking.getMode());
                  instance.markDirty();
                  storage.markDirty();
               }
            });
         }
      }
   }

   public static void forceStop(ServerPlayer player, boolean announce) {
      CielState.setEmergencyTakeover(player, false);
      cleanupAutomation(player);
      syncTakeoverState(player, null, false);
      if (announce) {
         player.displayClientMessage(
            Component.translatable(
                  "foxablazeultimate.skill.ciel.assistance.switched", new Object[]{Component.translatable("foxablazeultimate.skill.ciel.assistance.normal")}
               )
               .withStyle(ChatFormatting.GOLD),
            true
         );
      }
   }

   private static void stopTakeover(ServerPlayer player) {
      forceStop(player, true);
   }

   private static void cleanupAutomation(ServerPlayer player) {
      cancelAllAutoHoldsWithoutRelease(player);
      restoreTakeoverWeapon(player);
      CielTakeoverTerrainHandler.reset(player);
      NEXT_AUTO_SLOT.remove(player.getUUID());
      NEXT_DRAIN_TIME.remove(player.getUUID());
      CURRENT_TARGETS.remove(player.getUUID());
      NEXT_TARGET_SCAN_TIME.remove(player.getUUID());
      ACTIVE_PLAYERS.remove(player.getUUID());
   }

   private static void syncTakeoverState(ServerPlayer player, ManasSkillInstance supplied, boolean enabled) {
      SkillStorage storage = SkillAPI.getSkillsFrom(player);
      ManasSkillInstance instance = supplied != null
         ? supplied
         : (ManasSkillInstance)storage.getSkill((ManasSkill)FoxAblazeUltimateSkills.CIEL.get()).orElse(null);
      if (instance != null) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.putBoolean("CielEmergencyTakeover", enabled);
         tag.putInt("CielEmergencyTarget", -1);
         tag.putBoolean("CielEmergencyMove", false);
         tag.putBoolean("CielEmergencySprint", false);
         removeLegacyEscapeState(tag);
         syncSkillState(player, storage, instance);
      }
   }

   private static void removeLegacyEscapeState(CompoundTag tag) {
      tag.remove("CielEmergencyEscapeJump");
      tag.remove("CielEmergencyEscapeSneak");
      tag.remove("CielEmergencyEscapeDirection");
   }

   private static void syncSkillState(ServerPlayer player, SkillStorage storage, ManasSkillInstance instance) {
      instance.markDirty();
      storage.markDirty();
      ((StorageHolder)player).manasCore$sync(player);
   }

   private static void runAutomated(Runnable action) {
      boolean previous = AUTOMATED_ACTION.get();
      AUTOMATED_ACTION.set(true);

      try {
         action.run();
      } finally {
         AUTOMATED_ACTION.set(previous);
      }
   }

   private static boolean runAutomatedResult(CielEmergencyController.BooleanAction action) {
      boolean previous = AUTOMATED_ACTION.get();
      AUTOMATED_ACTION.set(true);

      try {
         return action.run();
      } finally {
         AUTOMATED_ACTION.set(previous);
      }
   }

   static <T> T runAutomatedAction(Supplier<T> action) {
      boolean previous = AUTOMATED_ACTION.get();
      AUTOMATED_ACTION.set(true);

      try {
         return action.get();
      } finally {
         AUTOMATED_ACTION.set(previous);
      }
   }

   static boolean runAutomatedBoolean(BooleanSupplier action) {
      boolean previous = AUTOMATED_ACTION.get();
      AUTOMATED_ACTION.set(true);

      try {
         return action.getAsBoolean();
      } finally {
         AUTOMATED_ACTION.set(previous);
      }
   }

   private static CielEmergencyController.RotationSnapshot aimForSkillActivation(ServerPlayer player, LivingEntity target) {
      CielEmergencyController.RotationSnapshot snapshot = CielEmergencyController.RotationSnapshot.capture(player);
      Vec3 offset = target.getEyePosition().subtract(player.getEyePosition());
      double horizontalDistance = Math.sqrt(offset.x * offset.x + offset.z * offset.z);
      if (horizontalDistance < 1.0E-8 && Math.abs(offset.y) < 1.0E-8) {
         return snapshot;
      }

      float yaw = (float)(Math.toDegrees(Math.atan2(offset.z, offset.x)) - 90.0);
      float pitch = Mth.clamp((float)(-Math.toDegrees(Math.atan2(offset.y, horizontalDistance))), -90.0F, 90.0F);
      player.setYRot(yaw);
      player.setXRot(pitch);
      player.setYHeadRot(yaw);
      return snapshot;
   }

   static {
      CielPassiveHandler.init();
   }

   private record AutoHold(ResourceLocation skillId, int keyNumber, int mode, long releaseTime, boolean melee) {
   }

   @FunctionalInterface
   private interface BooleanAction {
      boolean run();
   }

   private record RotationSnapshot(float yaw, float pitch, float headYaw) {
      private static CielEmergencyController.RotationSnapshot capture(ServerPlayer player) {
         return new CielEmergencyController.RotationSnapshot(player.getYRot(), player.getXRot(), player.getYHeadRot());
      }

      private void restore(ServerPlayer player) {
         player.setYRot(this.yaw);
         player.setXRot(this.pitch);
         player.setYHeadRot(this.headYaw);
      }
   }

   private static final class TakeoverWeaponState {
      private final int originalSelectedSlot;
      private int equippedHotbarSlot;
      private int swapSourceSlot = -1;
      private boolean inventorySwapped;
      private ItemStack swappedWeapon = ItemStack.EMPTY;
      private ItemStack displacedStack = ItemStack.EMPTY;
      private int targetId = -1;
      private long readyAtTime;
      private long nextRefreshTime;

      private TakeoverWeaponState(int selectedSlot) {
         this.originalSelectedSlot = selectedSlot;
         this.equippedHotbarSlot = selectedSlot;
      }
   }

   private record WeaponScore(double attackDamage, double attackSpeed) {
      private boolean isBetterThan(CielEmergencyController.WeaponScore other) {
         int damageComparison = Double.compare(this.attackDamage, other.attackDamage);
         return damageComparison > 0 || damageComparison == 0 && this.attackSpeed > other.attackSpeed;
      }
   }
}
