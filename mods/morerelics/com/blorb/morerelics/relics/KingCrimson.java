package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import com.blorb.morerelics.dataComponents.VectorDequeHolder;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.Random;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class KingCrimson extends MoreRelicBase {
   public KingCrimson(Properties properties) {
      super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant());
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("epitaph")
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(200.0, 140.0)
                           .upgradeModifier(UpgradeOperation.ADD, -30.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(4)
                     .requiredLevel(0)
                     .active(CastData.builder().type(CastType.INSTANTANEOUS).build())
                     .build()
               )
               .ability(
                  AbilityData.builder("talk_to_the_wind")
                     .stat(
                        StatData.builder("seconds")
                           .initialValue(3.0, 6.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.8)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("chance")
                           .initialValue(2.0, 4.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(3)
                     .build()
               )
               .ability(
                  AbilityData.builder("the_court")
                     .stat(
                        StatData.builder("threshold")
                           .initialValue(9.0, 14.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.6)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(7)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(14)
               .initialCost(10)
               .step(30)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("epitaph").initialValue(1).build())
                     .source(LevelingSourceData.abilityBuilder("the_court").initialValue(1).build())
                     .source(LevelingSourceData.abilityBuilder("talk_to_the_wind").initialValue(1).build())
                     .build()
               )
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      IntegerCounter counter = DataComponentHandler.getIntegerCounter(stack);
      int ticks = counter.current();
      if (20 > ticks) {
         ticks++;
      } else {
         Entity entity = slotContext.entity();
         Level clientLevel = entity.level();
         if (clientLevel.getServer() == null) {
            return;
         }

         ServerLevel serverLevel = clientLevel.getServer().getLevel(clientLevel.dimension());
         ticks = 0;
         VectorDequeHolder vecHolder = DataComponentHandler.getVecDeque(stack);
         vecHolder.addVecAndLevel(entity.position(), serverLevel);
         stack.set(DataComponentHandler.VECTOR_DEQUE_HOLDER.get(), vecHolder);
      }

      stack.set(DataComponentHandler.INT_COUNTER, counter.getUpdatedCounter(ticks));
      super.curioTick(slotContext, stack);
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (!player.level().isClientSide) {
         if (ability.equals("epitaph")) {
            VectorDequeHolder vecHolder = DataComponentHandler.getVecDeque(stack);
            Vec3 oldestPos = vecHolder.getOldestVec();
            ServerLevel level = vecHolder.getOldestLevel();
            if (oldestPos == null || level == null) {
               return;
            }

            IntegerCounter didEpitaphCounter = DataComponentHandler.getAltIntegerCounter(stack);
            stack.set(DataComponentHandler.ALT_INT_COUNTER.get(), didEpitaphCounter.getUpdatedCounter(1));
            player.resetFallDistance();
            player.teleportTo(level, oldestPos.x, oldestPos.y, oldestPos.z, Set.of(RelativeMovement.X, RelativeMovement.Y), player.getYRot(), player.getXRot());
            this.setAbilityCooldown(stack, "epitaph", (int)this.getStatValue(stack, "epitaph", "cooldown") * 20);
            this.spreadRelicExperience(player, stack, 20);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerAttack(LivingIncomingDamageEvent event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         if (!(event.getEntity().invulnerableTime > 10.0F)
            || event.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN)
            || !(event.getAmount() <= event.getEntity().lastHurt)) {
            LivingEntity victim = event.getEntity();
            float effectSeconds = (float)EntityUtils.findEquippedCurios(player, (Item)MoreRelics.KING_CRIMSON.get()).stream().mapToDouble(stack -> {
               KingCrimson item = (KingCrimson)stack.getItem();
               if (!item.isAbilityUnlocked(stack, "talk_to_the_wind")) {
                  return 0.0;
               }

               IntegerCounter didEpitaphCounter = DataComponentHandler.getAltIntegerCounter(stack);
               int didEpitaph = didEpitaphCounter.current();
               stack.set(DataComponentHandler.ALT_INT_COUNTER.get(), didEpitaphCounter.getUpdatedCounter(0));
               Random rand = new Random();
               if (didEpitaph != 1 && !(rand.nextFloat(0.0F, 100.0F) < item.getStatValue(stack, "talk_to_the_wind", "chance"))) {
                  return 0.0;
               }

               item.spreadRelicExperience(player, stack, 2);
               return item.getStatValue(stack, "talk_to_the_wind", "seconds");
            }).sum();
            if (effectSeconds > 0.0F) {
               MobEffectInstance weakness = new MobEffectInstance(MobEffects.WEAKNESS, (int)(effectSeconds * 20.0F));
               MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int)(effectSeconds * 20.0F));
               victim.addEffect(weakness);
               victim.addEffect(slowness);
               if (victim instanceof Player) {
                  MobEffectInstance staticEffect = new MobEffectInstance(MoreRelics.STATIC, (int)(effectSeconds * 20.0F) / 2);
                  victim.addEffect(staticEffect);
               }
            }
         }
      }
   }

   public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
      super.onEquip(slotContext, prevStack, stack);
      if (prevStack.getItem() != stack.getItem()) {
         DataComponentHandler.getVecDeque(stack).clear();
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onPlayerAttackExec(Pre event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         if (!event.getSource().type().msgId().equals("morerelics_king_crimson")) {
            if (!event.getEntity().isDeadOrDying()) {
               LivingEntity victim = event.getEntity();
               int shouldExecute = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.KING_CRIMSON.get()).stream().mapToInt(stack -> {
                  KingCrimson item = (KingCrimson)stack.getItem();
                  if (!item.isAbilityUnlocked(stack, "the_court")) {
                     return 0;
                  } else {
                     float ratio = (victim.getHealth() - event.getNewDamage()) / victim.getMaxHealth();
                     if (!victim.isDeadOrDying() && ratio > 0.0F && ratio <= item.getStatValue(stack, "the_court", "threshold") / 100.0) {
                        item.spreadRelicExperience(player, stack, 3);
                        return 1;
                     } else {
                        return 0;
                     }
                  }
               }).sum();
               if (shouldExecute >= 1) {
                  if (victim.getMaxHealth() <= player.getMaxHealth()) {
                     spawnGlitchedSoulParticles(victim.level(), victim.position());
                     event.setNewDamage(event.getNewDamage() + victim.getHealth() * 1.1F);
                  } else {
                     event.setNewDamage(event.getNewDamage() * 2.0F);
                  }
               }
            }
         }
      }
   }

   private static void spawnGlitchedSoulParticles(Level level, Vec3 position) {
      int particleCount = 20;
      level.playSound(null, position.x, position.y, position.z, MoreRelics.EXECUTE, SoundSource.MASTER, 1.0F, 1.0F);
      ServerLevel sevLevel = (ServerLevel)level;

      for (ServerPlayer player : level.players()) {
         sevLevel.sendParticles(player, MoreRelics.GLITCHED_SOUL.get(), true, position.x, position.y + 1.0, position.z, particleCount, 0.0, 0.0, 0.0, 0.07);
      }
   }
}
