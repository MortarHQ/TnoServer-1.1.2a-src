package com.github.L_Ender.cataclysm.event;

import com.github.L_Ender.cataclysm.Attachment.ChargeAttachment;
import com.github.L_Ender.cataclysm.Attachment.ParryAttachment;
import com.github.L_Ender.cataclysm.Attachment.RenderRushAttachment;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Royal_Draugr_Entity;
import com.github.L_Ender.cataclysm.init.ModAttribute;
import com.github.L_Ender.cataclysm.init.ModDataAttachments;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.items.ILeftClick;
import com.github.L_Ender.cataclysm.message.MessageSwingArm;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.github.L_Ender.lionfishapi.server.event.StandOnFluidEvent;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Start;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Stop;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Tick;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Remove;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent.AdvancementEarnEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@EventBusSubscriber(modid = "cataclysm")
public class ServerEventHandler {
   @SubscribeEvent
   public static void onLivingUpdateEvent(Post event) {
      ((ChargeAttachment)event.getEntity().getData(ModDataAttachments.CHARGE_ATTACHMENT)).tick(event.getEntity());
      ((RenderRushAttachment)event.getEntity().getData(ModDataAttachments.RENDER_RUSH_ATTACHMENT)).tick(event.getEntity());
      if ((Boolean)event.getEntity().getData(ModDataAttachments.HOOK_FALLING)) {
         event.getEntity().setIgnoreFallDamageFromCurrentImpulse(true);
         event.getEntity().currentImpulseImpactPos = event.getEntity().position();
      }
   }

   @SubscribeEvent
   public static void StandOnFluidEventEvent(StandOnFluidEvent event) {
      if (!event.getEntity().getItemBySlot(EquipmentSlot.FEET).isEmpty()
         && event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.IGNITIUM_BOOTS.get()
         && !event.getEntity().isShiftKeyDown()
         && (event.getFluidState().is(Fluids.LAVA) || event.getFluidState().is(Fluids.FLOWING_LAVA))) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onPlayerAttack(AttackEntityEvent event) {
      if (event.getEntity().hasEffect(ModEffect.EFFECTSTUN)) {
         event.setCanceled(true);
      }

      if (event.getEntity().hasEffect(ModEffect.EFFECTGHOST_FORM)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void BlockHeal(LivingHealEvent event) {
      float heal = event.getAmount();
      if (event.getEntity().hasEffect(ModEffect.EFFECTABYSSAL_FEAR)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onLivingJump(LivingJumpEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity.getEffect(ModEffect.EFFECTSTUN) != null) {
         entity.setDeltaMovement(entity.getDeltaMovement().x(), 0.0, entity.getDeltaMovement().z());
      }
   }

   @SubscribeEvent
   public static void onPlayerLeftClick(LeftClickBlock event) {
      Player player = event.getEntity();
      if (player.hasEffect(ModEffect.EFFECTSTUN)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onUseItemStart(Start event) {
      LivingEntity living = event.getEntity();
      ItemStack itemStack = event.getItem();
      if (living.hasEffect(ModEffect.EFFECTSTUN)) {
         event.setCanceled(true);
      }

      if (living.hasEffect(ModEffect.EFFECTGHOST_FORM)) {
         event.setCanceled(true);
      }

      if (itemStack.has(DataComponents.FOOD)) {
         int originalDuration = event.getDuration();
         float EatSpeed = (float)living.getAttributeValue(ModAttribute.EAT_SPEED) / 100.0F;
         int newDuration = (int)(originalDuration / (1.0 + EatSpeed));
         event.setDuration(Math.max(1, newDuration));
      }
   }

   @SubscribeEvent
   public static void onUseItem(LivingEntityUseItemEvent event) {
      LivingEntity living = event.getEntity();
      if (living.hasEffect(ModEffect.EFFECTSTUN)) {
         event.setDuration(0);
      }

      if (living.hasEffect(ModEffect.EFFECTGHOST_FORM)) {
         event.setDuration(0);
      }
   }

   @SubscribeEvent
   public static void onPlaceBlock(EntityPlaceEvent event) {
      if (event.getEntity() instanceof LivingEntity living && living.hasEffect(ModEffect.EFFECTSTUN)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void KnockbackEvent(LivingKnockBackEvent event) {
      if (event.getEntity() instanceof Royal_Draugr_Entity royalDraugr && royalDraugr.isDraugrBlocking()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onBreakBlock(BreakEvent event) {
      if (event.getPlayer().hasEffect(ModEffect.EFFECTSTUN)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onPlayerInteract1(RightClickEmpty event) {
   }

   @SubscribeEvent
   public static void onPlayerInteract2(LeftClickEmpty event) {
      if (event.getEntity().hasEffect(ModEffect.EFFECTSTUN)) {
      }

      boolean flag = false;
      ItemStack leftItem = event.getEntity().getOffhandItem();
      ItemStack rightItem = event.getEntity().getMainHandItem();
      if (!event.getEntity().hasEffect(ModEffect.EFFECTSTUN)) {
         if (leftItem.getItem() instanceof ILeftClick) {
            ((ILeftClick)leftItem.getItem()).onLeftClick(leftItem, event.getEntity());
            flag = true;
         }

         if (rightItem.getItem() instanceof ILeftClick) {
            ((ILeftClick)rightItem.getItem()).onLeftClick(rightItem, event.getEntity());
            flag = true;
         }

         if (event.getLevel().isClientSide && flag) {
            PacketDistributor.sendToServer(new MessageSwingArm(InteractionHand.MAIN_HAND), new CustomPacketPayload[0]);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerInteract3(EntityInteract event) {
      if (event.getEntity().hasEffect(ModEffect.EFFECTSTUN)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onPlayerInteract4(RightClickBlock event) {
      if (event.getEntity().hasEffect(ModEffect.EFFECTSTUN)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onPlayerInteract5(LeftClickBlock event) {
      if (event.getEntity().hasEffect(ModEffect.EFFECTSTUN)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onLivingSetTargetEvent(LivingChangeTargetEvent event) {
      if (event.getNewAboutToBeSetTarget() != null
         && event.getEntity() instanceof Mob mob
         && mob.getType().is(ModTag.LAVA_MONSTER)
         && event.getEntity().getLastHurtByMob() != event.getNewAboutToBeSetTarget()
         && event.getNewAboutToBeSetTarget().getItemBySlot(EquipmentSlot.HEAD).is((Item)ModItems.IGNITIUM_HELMET.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onLivingDamage(net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post event) {
      LivingEntity entity = event.getEntity();
      DamageSource source = event.getSource();
      if (entity.getHealth() <= event.getNewDamage() && entity.hasEffect(ModEffect.EFFECTSTUN)) {
         entity.removeEffect(ModEffect.EFFECTSTUN);
      }

      if (source.is(CMDamageTypes.DRACONIC_WOUND) && entity.hasEffect(MobEffects.ABSORPTION)) {
         entity.removeEffect(MobEffects.ABSORPTION);
      }

      if (event.getSource().getDirectEntity() instanceof LivingEntity living) {
         List<SlotResult> slot = CuriosApi.getCuriosHelper().findCurios(living, stack -> stack.is((Item)ModItems.BLAZING_GRIPS.get()));
         if (!slot.isEmpty() && event.getEntity().getRandom().nextFloat() < 0.15F * slot.size()) {
            MobEffectInstance effectinstance = new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND, 60, 0);
            entity.addEffect(effectinstance);
         }
      }

      if (!event.getEntity().getItemBySlot(EquipmentSlot.LEGS).isEmpty()
         && event.getSource() != null
         && event.getSource().getEntity() != null
         && event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.IGNITIUM_LEGGINGS.get()) {
         Entity attacker = event.getSource().getEntity();
         if (attacker instanceof LivingEntity && attacker != event.getEntity() && event.getEntity().getRandom().nextFloat() < 0.5F) {
            MobEffectInstance effectinstance = new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND, 100, 0, false, false, true);
            ((LivingEntity)attacker).addEffect(effectinstance);
            if (!attacker.isOnFire()) {
               attacker.igniteForSeconds(5.0F);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onShieldDamage(LivingShieldBlockEvent event) {
      DamageSource source = event.getDamageSource();
      LivingEntity entity = event.getEntity();
      Item item = entity.getUseItem().getItem();
      Entity directEntity = source.getDirectEntity();
      if (source.is(CMDamageTypes.MALEDICTIO_SAGITTA)) {
         event.setShieldDamage(0.0F);
      }

      ParryAttachment charge = (ParryAttachment)entity.getData(ModDataAttachments.PARRY_ATTACHMENT);
      if (item == ModItems.BULWARK_OF_THE_FLAME.get() && event.getBlocked() && charge.getParryFrame() < 13 && directEntity instanceof LivingEntity livingEntity
         )
       {
         livingEntity.igniteForSeconds(3.0F);
         livingEntity.playSound(SoundEvents.ANVIL_LAND, 0.8F, 1.3F);
         MobEffectInstance effectinstance = new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND, 100, 0);
         livingEntity.knockback(0.5, entity.getX() - livingEntity.getX(), entity.getZ() - livingEntity.getZ());
         livingEntity.addEffect(effectinstance);
      }

      if (item == ModItems.AZURE_SEA_SHIELD.get() && event.getBlocked() && charge.getParryFrame() < 10) {
         event.setShieldDamage(0.0F);
         if (directEntity instanceof LivingEntity livingEntity) {
            livingEntity.playSound((SoundEvent)ModSounds.PARRY.get(), 0.4F, 1.0F);
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 1));
            MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1);
            livingEntity.knockback(0.5, entity.getX() - livingEntity.getX(), entity.getZ() - livingEntity.getZ());
            livingEntity.addEffect(effectinstance);
         }
      }
   }

   @SubscribeEvent
   public static void DeathEvent(LivingDeathEvent event) {
      DamageSource source = event.getSource();
      if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && tryCursiumPlateRebirth(event.getEntity())) {
         event.setCanceled(true);
      }
   }

   private static boolean tryCursiumPlateRebirth(LivingEntity living) {
      ItemStack chestplate = living.getItemBySlot(EquipmentSlot.CHEST);
      if (living.level() instanceof ServerLevel serverLevel
         && chestplate.getItem() == ModItems.CURSIUM_CHESTPLATE.get()
         && !living.hasEffect(ModEffect.EFFECTGHOST_SICKNESS)
         && !living.hasEffect(ModEffect.EFFECTGHOST_FORM)) {
         living.setHealth(5.0F);
         serverLevel.playSound(null, living.getX(), living.getY(), living.getZ(), SoundEvents.TOTEM_USE, living.getSoundSource(), 1.25F, 1.0F);
         living.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0));
         living.addEffect(new MobEffectInstance(ModEffect.EFFECTGHOST_FORM, 100, 0, false, true, true));
         double d0 = living.getX();
         double d1 = living.getY() + 3.0;
         double d2 = living.getZ();
         serverLevel.sendParticles((SimpleParticleType)ModParticle.CURSED_ALGIZ.get(), d0, d1, d2, 1, 0.0, 0.0, 0.0, 0.0);
         return true;
      } else {
         return false;
      }
   }

   @SubscribeEvent
   public static void onLivingAttack(LivingIncomingDamageEvent event) {
      DamageSource source = event.getSource();
      float damage = event.getAmount();
      if (source.is(DamageTypeTags.IS_LIGHTNING) && event.getEntity().hasEffect(ModEffect.EFFECTWETNESS)) {
         MobEffectInstance effectinstance1 = event.getEntity().getEffect(ModEffect.EFFECTWETNESS);
         if (effectinstance1 != null) {
            float i = (effectinstance1.getAmplifier() + 1) * 0.2F;
            float f = damage + damage * i;
            damage = Math.min(Float.MAX_VALUE, f);
            event.setAmount(damage);
         }
      }

      if (event.getEntity().hasEffect(ModEffect.EFFECTGHOST_FORM) && !event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         event.setCanceled(true);
      }

      if (!event.getEntity().getItemBySlot(EquipmentSlot.LEGS).isEmpty()
         && event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.CURSIUM_LEGGINGS.get()) {
         if (event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
            if (event.getEntity().getRandom().nextFloat() < 0.15F) {
               event.setCanceled(true);
            }
         } else if (!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY) && event.getEntity().getRandom().nextFloat() < 0.08F) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void onCriticalAttack(CriticalHitEvent event) {
      Player player = event.getEntity();
      ItemStack weapon = player.getMainHandItem();
      if (!weapon.isEmpty() && event.getTarget() instanceof LivingEntity livingEntity) {
         if (weapon.getItem() == ModItems.THE_IMMOLATOR.get() && livingEntity.hasEffect(ModEffect.EFFECTBLAZING_BRAND)) {
            event.setCriticalHit(true);
         }

         if ((
               weapon.getItem() == ModItems.THE_ANNIHILATOR.get()
                  || weapon.getItem() == ModItems.CERAUNUS.get()
                  || weapon.getItem() == ModItems.THE_IMMOLATOR.get()
            )
            && event.isCriticalHit()) {
            livingEntity.playSound((SoundEvent)ModSounds.PARRY.get(), 0.5F, 1.1F);
         }
      }

      AttributeInstance attackDamageAttr = player.getAttribute(ModAttribute.ADDITIONAL_CRITICAL_DAMAGE);
      if (attackDamageAttr != null) {
         double extraCritPercent = attackDamageAttr.getValue();
         float vanillaCrit = event.getDamageMultiplier();
         float finalMultiplier = (float)(vanillaCrit + extraCritPercent / 100.0);
         if (finalMultiplier < 1.0F) {
            finalMultiplier = 1.0F;
         }

         event.setDamageMultiplier(finalMultiplier);
      }
   }

   @SubscribeEvent
   public static void preventEffectRemoval(Remove event) {
      MobEffectInstance effectInstance = event.getEffectInstance();
      if (effectInstance != null && effectInstance.getEffect() == ModEffect.EFFECTGHOST_SICKNESS.get()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onLivingFall(LivingFallEvent event) {
      if (!event.getEntity().getItemBySlot(EquipmentSlot.FEET).isEmpty()
         && event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.CURSIUM_BOOTS.get()) {
         event.setDistance(event.getDistance() * 0.3F);
      }

      if (event.getEntity() instanceof Player player && (Boolean)player.getData(ModDataAttachments.HOOK_FALLING)) {
         event.setDistance(0.0F);
         event.setCanceled(false);
      }
   }

   @SubscribeEvent
   public static void onPlayerInteract(RightClickItem event) {
      if (event.getEntity().hasEffect(ModEffect.EFFECTSTUN)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onStartUsing(Start event) {
      Item item = event.getItem().getItem();
      if ((item == ModItems.AZURE_SEA_SHIELD.get() || item == ModItems.BULWARK_OF_THE_FLAME.get())
         && event.getEntity() instanceof Player player
         && !player.getCooldowns().isOnCooldown(item)) {
         ParryAttachment charge = (ParryAttachment)player.getData(ModDataAttachments.PARRY_ATTACHMENT);
         charge.setParryFrame(0);
      }
   }

   @SubscribeEvent
   public static void onUseTick(Tick event) {
      Item item = event.getItem().getItem();
      if ((item == ModItems.AZURE_SEA_SHIELD.get() || item == ModItems.BULWARK_OF_THE_FLAME.get()) && event.getEntity() instanceof Player player) {
         ParryAttachment charge = (ParryAttachment)player.getData(ModDataAttachments.PARRY_ATTACHMENT);
         charge.setParryFrame(charge.getParryFrame() + 1);
      }
   }

   @SubscribeEvent
   public static void onStopUsing(Stop event) {
      Item item = event.getItem().getItem();
      if ((item == ModItems.AZURE_SEA_SHIELD.get() || item == ModItems.BULWARK_OF_THE_FLAME.get()) && event.getEntity() instanceof Player player) {
         ParryAttachment charge = (ParryAttachment)player.getData(ModDataAttachments.PARRY_ATTACHMENT);
         charge.setParryFrame(0);
      }
   }

   @SubscribeEvent
   public static void onAdvancementEarned(AdvancementEarnEvent event) {
      Player player = event.getEntity();
      ResourceLocation advId = event.getAdvancement().id();
      if (advId.equals(ResourceLocation.fromNamespaceAndPath("cataclysm", "kill_all_bosses"))) {
         ItemStack reward = new ItemStack((ItemLike)ModItems.MUSIC_DISC_THE_CATACLYSM_FARER.get());
         if (!player.getInventory().add(reward)) {
            player.drop(reward, false);
         }

         player.playNotifySound((SoundEvent)ModSounds.THE_CATACLYSM_FARER.get(), SoundSource.RECORDS, 1.0F, 1.0F);
      }
   }
}
