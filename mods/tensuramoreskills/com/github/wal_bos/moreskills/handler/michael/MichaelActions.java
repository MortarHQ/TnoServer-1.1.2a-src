package com.github.wal_bos.moreskills.handler.michael;

import com.github.wal_bos.moreskills.net.msg.MichaelAuraVfxPayload;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.ForceMovementEvent;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

public final class MichaelActions {
   public static final ResourceLocation KEY_MARK = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "michael_mark_speed_lock");
   public static final ResourceLocation ARMOR_ELOAH = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "michael_eloah_armor");
   public static final ResourceLocation JEREZ_ARMOR_BREAK = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "michael_jerez_armor_break");
   public static final ResourceLocation SKILL_LOCK = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "michael_skill_lock");
   private static boolean initialized;
   private static final MichaelAttack SUMMON_KEY = new MichaelAttack() {
      @Override
      public String modeId() {
         return "michael_angel_of_locks.summon";
      }

      @Override
      public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
         return 0.0;
      }

      @Override
      public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
         if (entity.level() instanceof ServerLevel level) {
            ItemStack var7 = ((Item)TenaddexItems.KEY_OF_SEALING.get()).getDefaultInstance();
            MichaelActions.enchantKey(level, var7, false);
            entity.setItemInHand(InteractionHand.MAIN_HAND, var7);
            entity.swing(InteractionHand.MAIN_HAND, true);
            MichaelAuraVfxPayload.send(entity, 3.2F, 60);
            MichaelAuraVfxPayload.sendTrail(entity);
            instance.addMasteryPoint(entity);
            instance.setCoolDown(20, mode);
         }
      }
   };
   private static final List<MichaelAttack> ATTACKS = List.of(
      SUMMON_KEY,
      new MichaelSegvaSealing(),
      new MichaelLataibUnleashing(),
      new MichaelLataibSpatialStorage(),
      new MichaelLataibHalberdShot(),
      new MichaelShifuruRelease(),
      new MichaelJerezSolution(),
      new MichaelKeterCrownBeam()
   );

   private MichaelActions() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.register(MichaelActions.class);
         SkillEvents.ACTIVATE_SKILL.register((SkillActivationEvent)(skillInstance, owner, keyNumber, mode) -> {
            ManasSkillInstance activated = (ManasSkillInstance)skillInstance.get();
            if (!MichaelSegvaSealing.isAbilityBlocked(owner, activated)) {
               return EventResult.pass();
            }

            if (owner instanceof Player player) {
               player.displayClientMessage(Component.literal("Your abilities are sealed.").withStyle(ChatFormatting.RED), true);
            }

            return EventResult.interruptFalse();
         });
         MichaelPassives.init();
      }
   }

   public static int getModes(ManasSkillInstance instance) {
      init();
      return ATTACKS.size();
   }

   public static int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      init();
      if (ATTACKS.isEmpty()) {
         return -1;
      }

      MichaelAttack current = getAttack(mode);
      if (current != null) {
         current.cleanup(entity);
      }

      return reverse ? (mode <= 0 ? ATTACKS.size() - 1 : mode - 1) : (mode >= ATTACKS.size() - 1 ? 0 : mode + 1);
   }

   public static String getModeId(ManasSkillInstance instance, int mode, String fallback) {
      init();
      MichaelAttack attack = getAttack(mode);
      return attack == null ? fallback : attack.modeId();
   }

   public static double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      init();
      MichaelAttack attack = getAttack(mode);
      return attack == null ? 0.0 : attack.magiculeCost(entity, instance);
   }

   public static void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      init();
      MichaelAttack attack = getAttack(mode);
      if (attack != null) {
         attack.activate(instance, entity, keyNumber, mode);
      }
   }

   public static boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      init();
      MichaelAttack attack = getAttack(mode);
      return attack != null && attack.onHeld(instance, entity, heldTicks, mode);
   }

   public static void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      init();
      MichaelAttack attack = getAttack(mode);
      if (attack != null) {
         attack.onRelease(instance, entity, heldTicks, keyNumber, mode);
      }
   }

   public static void cleanup(LivingEntity entity) {
      init();

      for (MichaelAttack attack : ATTACKS) {
         attack.cleanup(entity);
      }
   }

   public static boolean validEnemy(LivingEntity owner, LivingEntity target) {
      if (target == owner || !target.isAlive()) {
         return false;
      } else {
         return !target.isAlliedTo(owner) && !owner.isAlliedTo(target) ? !(target instanceof Player player && player.getAbilities().invulnerable) : false;
      }
   }

   public static void fail(LivingEntity entity, String message) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.literal(message).withStyle(ChatFormatting.RED), true);
      }
   }

   public static DamageSource damage(LivingEntity owner, ResourceKey<DamageType> type) {
      return TensuraDamageTypes.getEntityDamageSource(owner.level(), type, owner);
   }

   public static void forceMove(LivingEntity target, LivingEntity owner, ManasSkillInstance instance, Vec3 motion) {
      Changeable<Vec3> changeable = Changeable.of(motion);
      if (!((ForceMovementEvent)TensuraEntityEvents.FORCE_MOVEMENT_EVENT.invoker()).move(target, owner, instance, changeable).isFalse()) {
         target.setDeltaMovement((Vec3)changeable.get());
      } else {
         target.setDeltaMovement(motion);
      }

      target.fallDistance = 0.0F;
      target.hurtMarked = true;
   }

   public static boolean isHoldingAwakened(LivingEntity entity) {
      return entity.getMainHandItem().is((Item)TenaddexItems.AWAKENED_KEY_OF_SEALING.get())
         || entity.getOffhandItem().is((Item)TenaddexItems.AWAKENED_KEY_OF_SEALING.get());
   }

   public static boolean isHoldingAnyKey(LivingEntity entity) {
      return entity.getMainHandItem().is((Item)TenaddexItems.KEY_OF_SEALING.get())
         || entity.getOffhandItem().is((Item)TenaddexItems.KEY_OF_SEALING.get())
         || isHoldingAwakened(entity);
   }

   public static void enchantKey(ServerLevel level, ItemStack stack, boolean awakened) {
      Holder<Enchantment> energy = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(TensuraEnchantments.ENERGY_STEAL);
      Holder<Enchantment> holy = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(TensuraEnchantments.HOLY_WEAPON);
      stack.enchant(energy, awakened ? 3 : 2);
      stack.enchant(holy, 3);
      if (awakened) {
         Holder<Enchantment> barrier = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(TensuraEnchantments.BARRIER_PIERCING);
         Holder<Enchantment> soul = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(TensuraEnchantments.SOUL_EATER);
         stack.enchant(barrier, 10);
         stack.enchant(soul, 1);
      }
   }

   public static boolean isSeverance(DamageSource source) {
      return source.is(TensuraDamageTypes.SEVERANCE)
         || source.is(DamageTypeTags.IS_PROJECTILE) && source.getMsgId().toLowerCase().contains("severance")
         || source.getMsgId().toLowerCase().contains("severance");
   }

   public static boolean hasActiveState(LivingEntity entity) {
      return MichaelLataibUnleashing.hasActive(entity) || MichaelShifuruRelease.hasActive(entity) || MichaelSegvaSealing.hasOwnerState(entity);
   }

   private static MichaelAttack getAttack(int mode) {
      return mode >= 0 && mode < ATTACKS.size() ? ATTACKS.get(mode) : null;
   }

   @SubscribeEvent
   public static void onLevelTick(Post event) {
      if (event.getLevel() instanceof ServerLevel level) {
         MichaelSegvaSealing.tick(level);
         MichaelLataibUnleashing.tick(level);
         MichaelShifuruRelease.tick(level);
      }
   }

   @SubscribeEvent
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      if (MichaelPassives.cancelIncomingIfNeeded(event.getEntity(), event.getSource())) {
         event.setCanceled(true);
      }
   }

   static {
      init();
   }
}
