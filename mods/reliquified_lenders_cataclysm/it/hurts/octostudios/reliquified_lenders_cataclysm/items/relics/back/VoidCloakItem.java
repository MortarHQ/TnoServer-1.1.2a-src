package it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.back;

import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import it.hurts.octostudios.reliquified_lenders_cataclysm.init.ItemRegistry;
import it.hurts.octostudios.reliquified_lenders_cataclysm.init.RECDataComponentRegistry;
import it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.RECItem;
import it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.data.RECLootEntries;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.ItemUtils;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.RECMathUtils;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.relics.VoidCloakUtils;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
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
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class VoidCloakItem extends RECItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(AbilityData.builder("void_invulnerability").build())
               .ability(
                  AbilityData.builder("void_rune")
                     .active(CastData.builder().type(CastType.TOGGLEABLE).build())
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(20.0, 16.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, -0.05625)
                           .formatValue(RECMathUtils::roundOneDigit)
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(1.4, 1.76)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.525)
                           .formatValue(RECMathUtils::roundDamage)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("seismic_zone")
                     .requiredLevel(5)
                     .stat(
                        StatData.builder("radius")
                           .initialValue(2.0, 3.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.5)
                           .formatValue(RECMathUtils::roundInt)
                           .build()
                     )
                     .stat(
                        StatData.builder("waves").initialValue(2.0, 3.0).upgradeModifier(UpgradeOperation.ADD, 0.5).formatValue(RECMathUtils::roundInt).build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(1.4, 1.76)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.525)
                           .formatValue(RECMathUtils::roundDamage)
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
               .maxLevel(15)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("void_rune").gem(GemShape.SQUARE, GemColor.PURPLE).build())
                     .source(LevelingSourceData.abilityBuilder("seismic_zone").gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{RECLootEntries.CURSED_PYRAMID, RECLootEntries.FROSTED_PRISON, LootEntries.THE_END}).build())
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-4616238).borderBottom(-7774812).textured(true).build())
               .beams(BeamsData.builder().startColor(-4616238).endColor(16310422).build())
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (!stack.isEmpty() && slotContext.entity() instanceof Player player && this.isAbilityTicking(stack, "void_rune")) {
         Level level = player.getCommandSenderWorld();
         if (!level.isClientSide) {
            int voidRuneCooldown = (Integer)stack.getOrDefault(RECDataComponentRegistry.VOID_RUNE_TIME, 0);
            if (voidRuneCooldown > 0) {
               voidRuneCooldown--;
            } else {
               List<LivingEntity> entitiesInArea = ItemUtils.getEntitiesInArea(player, level, player.getBoundingBox().inflate(10.0));
               boolean runeSpawned = false;

               for (LivingEntity entity : entitiesInArea) {
                  if (entity != null && !entity.equals(player) && !(entity instanceof ArmorStand) && !EntityUtils.isAlliedTo(player, entity)) {
                     if (!(entity instanceof Mob mob)) {
                        VoidCloakUtils.spawnVoidRune(level, player, entity, stack);
                        runeSpawned = true;
                        break;
                     }

                     LivingEntity targetEntity = mob.getTarget();
                     if (targetEntity != null && targetEntity.is(player)) {
                        VoidCloakUtils.spawnVoidRune(level, player, mob, stack);
                        runeSpawned = true;
                        break;
                     }
                  }
               }

               if (runeSpawned) {
                  voidRuneCooldown = ItemUtils.getCooldownStat(stack, "void_rune");
               }
            }

            stack.set(RECDataComponentRegistry.VOID_RUNE_TIME, voidRuneCooldown);
         }
      }
   }

   @SubscribeEvent
   public static void onLivingDamage(LivingIncomingDamageEvent event) {
      if (event.getSource().getDirectEntity() instanceof Void_Rune_Entity voidRuneEntity && voidRuneEntity.getCaster() instanceof Player player) {
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.VOID_CLOAK.get());
         if (!stack.isEmpty() && stack.getItem() instanceof VoidCloakItem relic) {
            relic.spreadRelicExperience(player, stack, 1);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerDamage(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof Player player && !player.getCommandSenderWorld().isClientSide) {
         if (event.getSource().getDirectEntity() instanceof Void_Rune_Entity voidRuneEntity) {
            LivingEntity caster = voidRuneEntity.getCaster();
            if (caster != null && caster.getUUID().equals(player.getUUID())) {
               event.setCanceled(true);
            }
         }

         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.VOID_CLOAK.get());
         if (!stack.isEmpty() && stack.getItem() instanceof VoidCloakItem) {
            if (player.getY() < player.getCommandSenderWorld().getMinBuildHeight()) {
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityDeath(LivingDeathEvent event) {
      LivingEntity entity = event.getEntity();
      Player player = null;
      if (entity instanceof Player) {
         player = (Player)entity;
      } else if (entity instanceof OwnableEntity ownableEntity) {
         if (!(ownableEntity.getOwner() instanceof Player entityOwnerPlayer)) {
            return;
         }

         player = entityOwnerPlayer;
      }

      if (player != null) {
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.VOID_CLOAK.get());
         if (!stack.isEmpty() && stack.getItem() instanceof VoidCloakItem relic && relic.isAbilityUnlocked(stack, "seismic_zone")) {
            Level level = player.getCommandSenderWorld();
            if (!level.isClientSide) {
               level.explode(player, entity.getX(), entity.getY(), entity.getZ(), 1.0F, false, ExplosionInteraction.NONE);
               VoidCloakUtils.spawnSeismicZone(level, player, entity, stack);
               relic.spreadRelicExperience(player, stack, 5);
            }
         }
      }
   }
}
