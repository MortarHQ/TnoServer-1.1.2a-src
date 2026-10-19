package it.hurts.octostudios.rarcompat.items.feet;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier.Modifier;
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
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.PacketItemActivation;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import top.theillusivec4.curios.api.SlotContext;

public class KittySlippersItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(AbilityData.builder("passive").maxLevel(0).build())
               .ability(
                  AbilityData.builder("fall")
                     .stat(
                        StatData.builder("modifier")
                           .initialValue(2.0, 4.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.4)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 6, 24)
                           .star(1, 2, 19)
                           .star(2, 2, 9)
                           .star(3, 6, 4)
                           .star(4, 16, 4)
                           .star(5, 20, 9)
                           .star(6, 20, 19)
                           .star(7, 16, 24)
                           .star(8, 11, 16)
                           .star(9, 8, 19)
                           .star(10, 5, 13)
                           .star(11, 8, 8)
                           .star(12, 14, 19)
                           .star(13, 17, 13)
                           .star(14, 14, 8)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(5, 6)
                           .link(6, 7)
                           .link(7, 0)
                           .link(8, 9)
                           .link(9, 10)
                           .link(8, 11)
                           .link(8, 12)
                           .link(12, 13)
                           .link(8, 14)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("resurrected")
                     .requiredLevel(5)
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.05, 0.1)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25)
                           .formatValue(value -> MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 6, 25)
                           .star(1, 2, 20)
                           .star(2, 2, 9)
                           .star(3, 6, 4)
                           .star(4, 16, 4)
                           .star(5, 20, 9)
                           .star(6, 20, 20)
                           .star(7, 16, 25)
                           .star(8, 15, 21)
                           .star(9, 13, 17)
                           .star(10, 7, 13)
                           .star(11, 4, 17)
                           .star(12, 17, 12)
                           .star(13, 16, 8)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(5, 6)
                           .link(6, 7)
                           .link(7, 0)
                           .link(8, 9)
                           .link(9, 10)
                           .link(10, 11)
                           .link(9, 12)
                           .link(12, 13)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-1184275).borderBottom(-9868951).build())
               .beams(BeamsData.builder().startColor(-4359773).endColor(2894892).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(15)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("fall").initialValue(1).gem(GemShape.SQUARE, GemColor.PURPLE).build())
                     .source(LevelingSourceData.abilityBuilder("resurrected").initialValue(1).gem(GemShape.SQUARE, GemColor.PURPLE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.TROPIC, LootEntries.VILLAGE}).build())
         .build();
   }

   public RelicAttributeModifier getRelicAttributeModifiers(ItemStack stack) {
      return RelicAttributeModifier.builder()
         .attribute(new Modifier(Attributes.SAFE_FALL_DISTANCE, (float)this.getStatValue(stack, "fall", "modifier") / 3.0F))
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && !player.getCommandSenderWorld().isClientSide()) {
         Level level = player.getCommandSenderWorld();

         for (Creeper creeper : level.getEntitiesOfClass(Creeper.class, player.getBoundingBox().inflate(5.0))) {
            Vec3 creeperPosition = creeper.position();
            Vec3 escapeDirection = player.position().subtract(creeperPosition).normalize().scale(-1.0);
            Vec3 escapePosition = creeperPosition.add(escapeDirection.scale(5.0));
            PathNavigation navigation = creeper.getNavigation();
            Path path = navigation.createPath(escapePosition.x, escapePosition.y, escapePosition.z, 0);
            if (path != null) {
               navigation.moveTo(path, 1.5);
            }

            creeper.setTarget(null);
            float yaw = (float)Math.toDegrees(Math.atan2(escapeDirection.z, escapeDirection.x));
            creeper.yBodyRot = yaw;
            creeper.yHeadRot = yaw;
         }

         for (Phantom phantom : level.getEntitiesOfClass(Phantom.class, player.getBoundingBox().inflate(5.0))) {
            Vec3 phantomPosition = phantom.position();
            Vec3 escapeDirection = player.position().subtract(phantomPosition).normalize().scale(-1.0);
            Vec3 escapePosition = phantomPosition.add(escapeDirection.scale(5.0));
            PathNavigation navigation = phantom.getNavigation();
            Path path = navigation.createPath(escapePosition.x, escapePosition.y, escapePosition.z, 0);
            if (path != null) {
               navigation.moveTo(path, 1.5);
            }

            phantom.setTarget(null);
            float yaw = (float)Math.toDegrees(Math.atan2(escapeDirection.z, escapeDirection.x));
            phantom.yBodyRot = yaw;
            phantom.yHeadRot = yaw;
         }
      }
   }

   @EventBusSubscriber
   public static class KittySlippersEvent {
      @SubscribeEvent
      public static void onLivingChangeTargetEvent(LivingChangeTargetEvent event) {
         if ((event.getEntity() instanceof Creeper || event.getEntity() instanceof Phantom) && event.getNewAboutToBeSetTarget() instanceof Player player) {
            ItemStack itemStack = EntityUtils.findEquippedCurio(player, (Item)ModItems.KITTY_SLIPPERS.value());
            if (!(itemStack.getItem() instanceof KittySlippersItem)) {
               return;
            }

            event.setCanceled(true);
         }
      }

      @SubscribeEvent
      public static void onLivingHurt(Pre event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.KITTY_SLIPPERS.value());
            if (stack.getItem() instanceof KittySlippersItem) {
               stack.set(DataComponentRegistry.COUNT, (int)event.getOriginalDamage());
            }
         }
      }

      @SubscribeEvent
      public static void onPlayerFall(LivingFallEvent event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.KITTY_SLIPPERS.value());
            if (stack.getItem() instanceof KittySlippersItem relic) {
               if (event.getDistance() > 4.0F) {
                  relic.spreadRelicExperience(player, stack, 1);
               }
            }
         }
      }

      @SubscribeEvent
      public static void onLivingDeath(LivingDeathEvent event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack var7 = EntityUtils.findEquippedCurio(player, (Item)ModItems.KITTY_SLIPPERS.value());
            Level level = player.getCommandSenderWorld();
            RandomSource random = player.getRandom();
            if (var7.getItem() instanceof KittySlippersItem relic
               && !level.isClientSide()
               && relic.canPlayerUseAbility(player, var7, "resurrected")
               && !(random.nextFloat() > relic.getStatValue(var7, "resurrected", "chance"))) {
               NetworkHandler.sendToClient(new PacketItemActivation(var7), (ServerPlayer)player);
               level.playSound(null, player, SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 0.9F + random.nextFloat() * 0.2F);
               relic.spreadRelicExperience(player, var7, (Integer)var7.getOrDefault(DataComponentRegistry.COUNT, 1));
               player.setHealth(1.0F);
               event.setCanceled(true);
               var7.set(DataComponentRegistry.TOGGLED, false);

               for (int i = 0; i < 50; i++) {
                  ((ServerLevel)level)
                     .sendParticles(
                        ParticleUtils.constructSimpleSpark(
                           new Color(100 + random.nextInt(156), random.nextInt(100 + random.nextInt(156)), random.nextInt(100 + random.nextInt(156))),
                           0.5F,
                           60,
                           0.95F
                        ),
                        player.getX(),
                        player.getY() + 1.0,
                        player.getZ(),
                        1,
                        (random.nextDouble() - 0.5) * 3.0,
                        random.nextDouble() * 1.5,
                        (random.nextDouble() - 0.5) * 3.0,
                        0.05
                     );
               }
            }
         }
      }
   }
}
