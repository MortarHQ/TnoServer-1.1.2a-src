package it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.charm;

import it.hurts.octostudios.reliquified_lenders_cataclysm.init.ItemRegistry;
import it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.RECItem;
import it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.data.RECLootEntries;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.ItemUtils;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.RECMathUtils;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.relics.ScouringEyeUtils;
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
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class ScouringEyeItem extends RECItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("glowing_scour")
                     .active(
                        CastData.builder()
                           .type(CastType.INSTANTANEOUS)
                           .predicate("target", PredicateType.CAST, (player, stack) -> !ScouringEyeUtils.getTargetUUID(stack).isEmpty())
                           .predicate("tp_allowed", PredicateType.CAST, (player, stack) -> ScouringEyeUtils.isTeleportAllowed(stack))
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(20.0, 15.0)
                           .upgradeModifier(UpgradeOperation.ADD, -1.0)
                           .formatValue(RECMathUtils::roundOneDigit)
                           .build()
                     )
                     .stat(
                        StatData.builder("glowing_time")
                           .initialValue(10.0, 12.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.525)
                           .formatValue(RECMathUtils::roundInt)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .step(100)
               .maxLevel(10)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("glowing_scour").gem(GemShape.SQUARE, GemColor.PURPLE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{RECLootEntries.CURSED_PYRAMID, LootEntries.THE_END}).build())
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-6196783).borderBottom(-8109653).textured(true).build())
               .beams(BeamsData.builder().startColor(-12188841).endColor(11020230).build())
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity entity = slotContext.entity();
      Level level = entity.level();
      if (!level.isClientSide) {
         LivingEntity target = ScouringEyeUtils.getEntityFromStack(level, stack);
         if (target != null) {
            if (target.isDeadOrDying()) {
               ScouringEyeUtils.resetData(stack);
            } else {
               ScouringEyeUtils.setTeleportSafe(stack, ScouringEyeUtils.getTeleportPos(entity, target) != null);
               int glowingTime = ScouringEyeUtils.getGlowingTime(stack);
               if (glowingTime > 0) {
                  int stackTime = ScouringEyeUtils.getStackTime(stack);
                  if (glowingTime == 1) {
                     level.playSound(null, entity.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS);
                  }

                  int gameTime = (int)level.getGameTime();
                  if (stackTime == 0) {
                     stackTime = gameTime;
                     glowingTime--;
                  } else {
                     int deltaTime = gameTime - stackTime;
                     if (deltaTime > 0) {
                        stackTime += deltaTime;
                        glowingTime -= deltaTime;
                     }
                  }

                  ScouringEyeUtils.setStackTime(stack, stackTime);
                  ScouringEyeUtils.setGlowingTime(stack, glowingTime);
               }
            }
         }
      }
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (ability.equals("glowing_scour")) {
         Level level = player.getCommandSenderWorld();
         if (!level.isClientSide) {
            LivingEntity target = ScouringEyeUtils.getEntityFromStack(level, stack);
            if (target != null) {
               BlockPos teleportPos = ScouringEyeUtils.getTeleportPos(player, target);
               if (teleportPos == null) {
                  ScouringEyeUtils.setTeleportSafe(stack, false);
               } else {
                  Vec3 teleportMovement = ScouringEyeUtils.getMovementOnTeleport(teleportPos, target.blockPosition()).scale(0.12);
                  ScouringEyeUtils.teleportToTarget(player, target, teleportPos, teleportMovement);
                  this.setAbilityCooldown(stack, "glowing_scour", ItemUtils.getCooldownStat(stack, "glowing_scour"));
                  this.spreadRelicExperience(player, stack, 1);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerAttack(Post event) {
      Player player = null;
      if (event.getSource().getEntity() instanceof Projectile projectile && projectile.getOwner() instanceof Player ownerPlayer) {
         player = ownerPlayer;
      } else if (event.getSource().getEntity() instanceof Player sourcePlayer) {
         player = sourcePlayer;
      }

      if (player != null) {
         Level level = player.getCommandSenderWorld();
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.SCOURING_EYE.get());
         LivingEntity target = event.getEntity();
         if (!level.isClientSide && !stack.isEmpty() && !target.isDeadOrDying() && !target.equals(player)) {
            ScouringEyeUtils.setStackTime(stack, (int)level.getGameTime());
            ScouringEyeUtils.setGlowingTime(stack, ScouringEyeUtils.getGlowingTimeStat(stack));
            ScouringEyeUtils.setTargetUUID(stack, target.getUUID().toString());
            ScouringEyeUtils.setPlayerDied(stack, false);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof Player player) {
         ItemStack var3 = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.SCOURING_EYE.get());
         if (!player.getCommandSenderWorld().isClientSide && !var3.isEmpty()) {
            ScouringEyeUtils.setPlayerDied(var3, true);
         }
      }
   }
}
