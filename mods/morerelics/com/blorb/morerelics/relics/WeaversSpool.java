package com.blorb.morerelics.relics;

import com.blorb.morerelics.Config;
import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import com.blorb.morerelics.network.SilkCounterPayload;
import com.blorb.morerelics.network.SoarVelocityPayload;
import com.mojang.blaze3d.systems.RenderSystem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.PredicateType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.List;
import java.util.OptionalInt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class WeaversSpool extends MoreRelicBase {
   public static final ResourceLocation SILK_BACKGROUND = ResourceLocation.fromNamespaceAndPath("morerelics", "textures/gui/silk/background.png");
   public static final ResourceLocation SILK = ResourceLocation.fromNamespaceAndPath("morerelics", "textures/gui/silk/silk.png");
   public static final int BIND_COST = 6;
   public static final int SOAR_COST = 3;
   public static final int UNRAVEL_COST = 7;

   public WeaversSpool(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("weaver")
                     .stat(
                        StatData.builder("passive_regen_time")
                           .initialValue(7.0, 5.0)
                           .upgradeModifier(UpgradeOperation.ADD, -0.5)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("bind")
                     .stat(
                        StatData.builder("healing_amount")
                           .initialValue(2.0, 4.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.5)
                           .formatValue(value -> (float)Math.round(value * 2.0) / 2.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .active(
                        CastData.builder()
                           .type(CastType.INSTANTANEOUS)
                           .predicate("silk_amount", PredicateType.CAST, (player, itemStack) -> this.getCurrentSilkAmount(itemStack) >= 6)
                           .build()
                     )
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("soar")
                     .stat(
                        StatData.builder("leap_power")
                           .initialValue(1.1F, 1.3F)
                           .upgradeModifier(UpgradeOperation.ADD, 0.1F)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("speed_duration")
                           .initialValue(7.0, 10.0)
                           .upgradeModifier(UpgradeOperation.ADD, 2.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .active(
                        CastData.builder()
                           .type(CastType.INSTANTANEOUS)
                           .predicate("silk_amount", PredicateType.CAST, (player, itemStack) -> this.getCurrentSilkAmount(itemStack) >= 3)
                           .build()
                     )
                     .requiredLevel(3)
                     .build()
               )
               .ability(
                  AbilityData.builder("unravel")
                     .stat(
                        StatData.builder("damage")
                           .initialValue(4.0, 6.0)
                           .upgradeModifier(UpgradeOperation.ADD, 2.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("slow_duration")
                           .initialValue(2.0, 3.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.5)
                           .formatValue(value -> (float)Math.round(value * 2.0) / 2.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .active(
                        CastData.builder()
                           .type(CastType.INSTANTANEOUS)
                           .predicate("silk_amount", PredicateType.CAST, (player, itemStack) -> this.getCurrentSilkAmount(itemStack) >= 7)
                           .build()
                     )
                     .requiredLevel(6)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(12)
               .initialCost(140)
               .step(40)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("weaver").build()).build())
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         if (!player.level().isClientSide) {
            float regen_time = (float)this.getStatValue(stack, "weaver", "passive_regen_time") * 20.0F;
            IntegerCounter integerCounter = DataComponentHandler.getAltIntegerCounter(stack);
            int regenProgress = integerCounter.current();
            if (regenProgress < regen_time) {
               regenProgress++;
            } else {
               regenProgress = 0;
               IntegerCounter silkCounter = DataComponentHandler.getIntegerCounter(stack);
               int currentSilk = silkCounter.current();
               if (currentSilk < 3) {
                  stack.set(DataComponentHandler.INT_COUNTER, silkCounter.getUpdatedCounter(currentSilk + 1));
                  this.spreadRelicExperience(player, stack, 3);
                  this.sendSilkCounterPayload(player, currentSilk + 1);
               }
            }

            stack.set(DataComponentHandler.ALT_INT_COUNTER, integerCounter.getUpdatedCounter(regenProgress));
         }
      }
   }

   @Override
   public boolean canEquipMultipleCopies() {
      return false;
   }

   private int getCurrentSilkAmount(ItemStack stack) {
      return DataComponentHandler.getIntegerCounter(stack).current();
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (!player.level().isClientSide) {
         IntegerCounter counter = DataComponentHandler.getIntegerCounter(stack);
         int currentSilk = counter.current();
         if (ability.equals("bind") && currentSilk >= 6) {
            this.applyBind(stack, player, counter, currentSilk);
         } else if (ability.equals("soar") && currentSilk >= 3) {
            this.applySoar(stack, player, counter, currentSilk);
         } else if (ability.equals("unravel") && currentSilk >= 7) {
            this.applyUnravel(stack, player, counter, currentSilk);
         }
      }
   }

   private void applyBind(ItemStack stack, Player player, IntegerCounter counter, int currentSilk) {
      this.setAbilityCooldown(stack, "bind", 20);
      stack.set(DataComponentHandler.INT_COUNTER, counter.getUpdatedCounter(currentSilk - 6));
      player.heal((float)this.getStatValue(stack, "bind", "healing_amount"));
      player.level().playSound(null, player.position().x, player.position().y, player.position().z, MoreRelics.HEAL, SoundSource.MASTER, 1.1F, 1.0F);
      player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 80, 0));
      this.sendSilkCounterPayload(player, currentSilk - 6);
   }

   private void applySoar(ItemStack stack, Player player, IntegerCounter counter, int currentSilk) {
      this.setAbilityCooldown(stack, "soar", 20);
      stack.set(DataComponentHandler.INT_COUNTER, counter.getUpdatedCounter(currentSilk - 3));
      player.level()
         .playSound(null, player.position().x, player.position().y, player.position().z, SoundEvents.ENDER_DRAGON_FLAP, SoundSource.MASTER, 0.7F, 1.2F);
      Vec3 lookDirection = player.getLookAngle().scale(this.getStatValue(stack, "soar", "leap_power"));
      player.resetFallDistance();
      SoarVelocityPayload soarPayload = new SoarVelocityPayload(lookDirection.x, lookDirection.y, lookDirection.z);
      PacketDistributor.sendToPlayer((ServerPlayer)player, soarPayload, new CustomPacketPayload[0]);
      player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, (int)(this.getStatValue(stack, "soar", "speed_duration") * 20.0), 0));
      this.sendSilkCounterPayload(player, currentSilk - 3);
   }

   private void applyUnravel(ItemStack stack, Player player, IntegerCounter counter, int currentSilk) {
      this.setAbilityCooldown(stack, "unravel", 20);
      stack.set(DataComponentHandler.INT_COUNTER, counter.getUpdatedCounter(currentSilk - 7));
      Vec3 center = player.position();
      float radius = 4.0F;
      float damage = (float)this.getStatValue(stack, "unravel", "damage");
      float slowDuration = (float)this.getStatValue(stack, "unravel", "slow_duration");
      this.spawnSilkParticles(player.level(), player.position(), 1, radius);
      AABB area = new AABB(center.x - radius, center.y - radius, center.z - radius, center.x + radius, center.y + radius, center.z + radius);
      List<Entity> detectedEntities = player.level().getEntities(player, area);
      player.level()
         .playSound(null, player.position().x, player.position().y, player.position().z, SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.MASTER, 0.7F, 0.6F);

      for (Entity detected : detectedEntities) {
         if (detected instanceof LivingEntity livingEntity && player != detected) {
            livingEntity.hurt(new DamageSource(livingEntity.damageSources().generic().typeHolder(), player, player), damage);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int)(slowDuration * 20.0F), 2));
         }
      }

      this.sendSilkCounterPayload(player, currentSilk - 7);
   }

   private void sendSilkCounterPayload(Player player, int newSilkAmount) {
      SilkCounterPayload payload = new SilkCounterPayload(newSilkAmount);
      PacketDistributor.sendToPlayer((ServerPlayer)player, payload, new CustomPacketPayload[0]);
   }

   @SubscribeEvent
   public static void onPlayerAttack(Pre event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         EntityUtils.findEquippedCurios(player, (Item)MoreRelics.WEAVERS_SPOOL.get()).forEach(stack -> {
            WeaversSpool item = (WeaversSpool)stack.getItem();
            IntegerCounter silkCounter = DataComponentHandler.getIntegerCounter(stack);
            int currentSilkAmount = silkCounter.current();
            if (currentSilkAmount < 10) {
               stack.set(DataComponentHandler.INT_COUNTER, silkCounter.getUpdatedCounter(currentSilkAmount + 1));
               item.spreadRelicExperience(player, stack, 3);
               SilkCounterPayload payload = new SilkCounterPayload(silkCounter.current());
               PacketDistributor.sendToPlayer((ServerPlayer)player, payload, new CustomPacketPayload[0]);
            }
         });
      }
   }

   private void spawnSilkParticles(Level level, Vec3 position, int amplifier, float radius) {
      int particleCount = 30 + amplifier * 60;
      if (particleCount > 400) {
         particleCount = 400;
      }

      ServerLevel sevLevel = (ServerLevel)level;

      for (ServerPlayer player : level.players()) {
         sevLevel.sendParticles(player, ParticleTypes.END_ROD, true, position.x, position.y + 1.0, position.z, particleCount, 0.0, 0.0, 0.0, 0.025 * radius);
      }
   }

   @EventBusSubscriber(modid = "morerelics", value = Dist.CLIENT)
   public static class ClientRender {
      @SubscribeEvent
      public static void renderSilk(net.neoforged.neoforge.client.event.RenderGuiEvent.Pre event) {
         Minecraft minecraft = Minecraft.getInstance();
         Player player = minecraft.player;
         if (player.level().isClientSide) {
            boolean hasWeaver = !EntityUtils.findEquippedCurios(player, (Item)MoreRelics.WEAVERS_SPOOL.get()).isEmpty();
            if (hasWeaver) {
               OptionalInt silkAmount = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.WEAVERS_SPOOL.get()).stream().mapToInt(stack -> {
                  WeaversSpool item = (WeaversSpool)stack.getItem();
                  return DataComponentHandler.getIntegerCounter(stack).current();
               }).max();
               if (silkAmount.isPresent()) {
                  drawSilkBar(event.getGuiGraphics(), event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight(), silkAmount.getAsInt());
               }
            }
         }
      }

      public static void drawSilkBar(GuiGraphics guiGraphics, int screenWidth, int screenHeight, int silkLevel) {
         Minecraft minecraft = Minecraft.getInstance();
         Player player = minecraft.player;
         minecraft.getTextureManager().bindForSetup(WeaversSpool.SILK_BACKGROUND);
         RenderSystem.setShaderTexture(0, WeaversSpool.SILK_BACKGROUND);
         int barWidth = 20;
         int barHeight = 53;
         int margin = 5;
         Config.SilkGUICorner corner = (Config.SilkGUICorner)Config.SILK_BAR_CORNER.get();
         int left;
         int top;
         switch (corner) {
            case TOP_LEFT:
               left = margin;
               top = margin;
               break;
            case TOP_RIGHT:
               left = screenWidth - barWidth - margin;
               top = margin;
               break;
            case BOTTOM_LEFT:
               left = margin;
               top = screenHeight - barHeight - margin;
               break;
            default:
               left = screenWidth - barWidth - margin;
               top = screenHeight - barHeight - margin;
         }

         guiGraphics.blit(WeaversSpool.SILK_BACKGROUND, left, top, 0.0F, 0.0F, barWidth, barHeight, barWidth, barHeight);
         drawSilk(guiGraphics, left, top, barWidth, barHeight, silkLevel);
      }

      public static void drawSilk(GuiGraphics guiGraphics, int left, int top, int barWidth, int barHeight, int silkLevel) {
         Minecraft minecraft = Minecraft.getInstance();
         Player player = minecraft.player;
         minecraft.getTextureManager().bindForSetup(WeaversSpool.SILK);
         RenderSystem.setShaderTexture(0, WeaversSpool.SILK);
         int silkHeight = 5;
         int silkStart = top + barHeight - 6 - silkHeight;

         for (int i = 0; i < silkLevel; i++) {
            guiGraphics.blit(WeaversSpool.SILK, left, silkStart, 0.0F, 0.0F, barWidth, silkHeight, barWidth, silkHeight);
            silkStart -= 4;
         }
      }
   }
}
