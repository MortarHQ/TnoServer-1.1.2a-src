package it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.head;

import it.hurts.octostudios.reliquified_lenders_cataclysm.entities.VoidShardModifiedEntity;
import it.hurts.octostudios.reliquified_lenders_cataclysm.init.ItemRegistry;
import it.hurts.octostudios.reliquified_lenders_cataclysm.init.RECDataComponentRegistry;
import it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.RECItem;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.ItemUtils;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.RECMathUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
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
import java.util.ArrayList;
import java.util.List;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class VoidBubbleItem extends RECItem {
   private static final String ABILITY_ID = "protective_bubble";

   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("protective_bubble")
                     .stat(
                        StatData.builder("attack_blocks")
                           .initialValue(2.0, 3.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.5)
                           .formatValue(RECMathUtils::roundInt)
                           .build()
                     )
                     .stat(
                        StatData.builder("projectiles")
                           .initialValue(16.0, 20.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.22)
                           .formatValue(RECMathUtils::roundInt)
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(0.4, 0.6)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.56)
                           .formatValue(RECMathUtils::roundDamage)
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(30.0, 25.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, -0.068)
                           .formatValue(RECMathUtils::roundOneDigit)
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
                     .source(LevelingSourceData.abilityBuilder("protective_bubble").gem(GemShape.SQUARE, GemColor.PURPLE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.THE_END}).build())
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-24639).borderBottom(-1615477).textured(true).build())
               .beams(BeamsData.builder().startColor(-2791990).endColor(4849829).build())
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && stack.getItem() instanceof VoidBubbleItem relic) {
         Level level = player.getCommandSenderWorld();
         if (!level.isClientSide) {
            if (getAttackBlocks(stack) == this.getAttackBlocksStat(stack)) {
               setCooldown(relic, stack);
               stack.set(RECDataComponentRegistry.ATTACK_BLOCKS, 0);
            }

            if (this.getAbilityCooldown(stack, "protective_bubble") == 1) {
               ItemUtils.playCooldownSound(level, player);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerBreathe(LivingBreatheEvent event) {
      if (event.getEntity() instanceof Player player) {
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.VOID_BUBBLE.get());
         if (!stack.isEmpty() && stack.getItem() instanceof VoidBubbleItem) {
            event.setCanBreathe(true);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerDamaged(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Entity) {
         Level level = player.getCommandSenderWorld();
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.VOID_BUBBLE.get());
         if (!level.isClientSide
            && !stack.isEmpty()
            && stack.getItem() instanceof VoidBubbleItem relic
            && !relic.isAbilityOnCooldown(stack, "protective_bubble")) {
            int attackBlocks = getAttackBlocks(stack);
            int attackBlocksStat = relic.getAttackBlocksStat(stack);
            if (attackBlocks < attackBlocksStat) {
               if (attackBlocks == attackBlocksStat - 1) {
                  relic.spawnShards(player, stack);
               }

               relic.spreadRelicExperience(player, stack, 1);
               level.playSound(null, player.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS);
               event.setCanceled(true);
               stack.set(RECDataComponentRegistry.ATTACK_BLOCKS, attackBlocks + 1);
            }
         }
      }
   }

   private void spawnShards(LivingEntity caster, ItemStack stack) {
      Level level = caster.getCommandSenderWorld();
      int projectilesNum = ItemUtils.getIntStat(stack, "protective_bubble", "projectiles");
      List<Vec3> movementVecs = this.getShootVectors(caster.getRandom(), projectilesNum);

      for (int i = 0; i < projectilesNum; i++) {
         Vec3 movementVec = movementVecs.get(i).scale(0.35);
         Entity shardEntity = new VoidShardModifiedEntity(
            level,
            caster,
            caster.getX() + movementVec.x,
            caster.getY() + movementVec.y + 1.2,
            caster.getZ() + movementVec.z,
            movementVec,
            caster,
            this.getDamageStat(stack)
         );
         level.addFreshEntity(shardEntity);
      }

      level.playSound(null, caster.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL);
   }

   public List<Vec3> getShootVectors(RandomSource random, float projectilesNum) {
      List<Vec3> vectors = new ArrayList<>();
      float turnFraction = (1.0F + Mth.sqrt(5.0F)) / 2.0F;

      for (int i = 1; i <= projectilesNum; i++) {
         float progress = i / projectilesNum;
         float inclination = (float)Math.acos(1.0F - 2.0F * progress);
         float azimuth = (float)((turnFraction * i + random.nextFloat()) * Math.PI * 2.0);
         double x = Math.sin(inclination) * Math.cos(azimuth);
         double y = Math.cos(inclination);
         double z = Math.sin(inclination) * Math.sin(azimuth);
         Vec3 vec = new Vec3(x, y, z);
         if (i == 1) {
            vec = vec.add(0.0, 1.0, 0.0).scale(0.5);
         }

         vectors.add(vec);
      }

      return vectors;
   }

   private static int getAttackBlocks(ItemStack stack) {
      return (Integer)stack.getOrDefault(RECDataComponentRegistry.ATTACK_BLOCKS, 0);
   }

   private int getAttackBlocksStat(ItemStack stack) {
      return (int)Math.round(this.getStatValue(stack, "protective_bubble", "attack_blocks"));
   }

   private float getDamageStat(ItemStack stack) {
      return (float)this.getStatValue(stack, "protective_bubble", "damage");
   }

   private static void setCooldown(IRelicItem relic, ItemStack stack) {
      relic.setAbilityCooldown(stack, "protective_bubble", ItemUtils.getCooldownStat(stack, "protective_bubble"));
   }
}
