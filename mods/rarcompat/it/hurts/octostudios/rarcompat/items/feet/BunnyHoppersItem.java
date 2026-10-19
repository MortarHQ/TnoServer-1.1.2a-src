package it.hurts.octostudios.rarcompat.items.feet;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.octostudios.rarcompat.network.packets.PowerJumpPacket;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
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
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import top.theillusivec4.curios.api.SlotContext;

public class BunnyHoppersItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("hold")
                     .stat(
                        StatData.builder("duration")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> MathUtils.round(value / 20.0, 2))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 3, 16)
                           .star(1, 8, 15)
                           .star(2, 7, 11)
                           .star(3, 9, 6)
                           .star(4, 11, 8)
                           .star(5, 14, 17)
                           .star(6, 16, 14)
                           .star(7, 17, 22)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(2, 4)
                           .link(1, 5)
                           .link(5, 6)
                           .link(5, 7)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-5730187).borderBottom(-12109265).build())
               .beams(BeamsData.builder().startColor(-1127196).endColor(2958109).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("hold").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.PLAINS, LootEntries.FOREST}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && this.canPlayerUseAbility(player, stack, "hold")) {
         if (player.onGround()) {
            this.addTime(stack, -this.getTime(stack));
         }

         double statValue = this.getStatValue(stack, "hold", "duration");
         if (player.hasEffect(MobEffects.JUMP)) {
            MobEffectInstance jumpBoost = player.getEffect(MobEffects.JUMP);
            if (jumpBoost != null) {
               statValue += (jumpBoost.getAmplifier() + 1) * 4;
            }
         }

         Level level = player.getCommandSenderWorld();
         if (level.isClientSide()
            && player instanceof LocalPlayer localPlayer
            && !(this.getTime(stack) >= statValue)
            && !player.isFallFlying()
            && this.getToggled(stack)) {
            if (!localPlayer.input.jumping) {
               this.setToggled(stack, false);
            } else {
               NetworkHandler.sendToServer(new PowerJumpPacket());
               player.setDeltaMovement(new Vec3(player.getDeltaMovement().x, 0.6, player.getDeltaMovement().z));
               RandomSource random = player.getRandom();

               for (int i = 0; i < 10; i++) {
                  double offsetX = (random.nextDouble() - 0.5) * 0.5;
                  double offsetY = (random.nextDouble() - 0.5) * 0.5;
                  double offsetZ = (random.nextDouble() - 0.5) * 0.5;
                  level.addParticle(
                     ParticleUtils.constructSimpleSpark(new Color(200 + random.nextInt(56), 200 + random.nextInt(56), 200 + random.nextInt(56)), 0.5F, 40, 0.9F),
                     player.getX() + offsetX,
                     player.getY() + 0.1 + offsetY,
                     player.getZ() + offsetZ,
                     0.0,
                     0.0,
                     0.0
                  );
               }
            }
         }
      }
   }

   public void addTime(ItemStack stack, int val) {
      stack.set(DataComponentRegistry.TIME, this.getTime(stack) + val);
   }

   public int getTime(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
   }

   public void setToggled(ItemStack stack, boolean val) {
      stack.set(DataComponentRegistry.TOGGLED, val);
   }

   public boolean getToggled(ItemStack stack) {
      return (Boolean)stack.getOrDefault(DataComponentRegistry.TOGGLED, false);
   }

   @EventBusSubscriber
   public static class BunnyHoppersEvent {
      @SubscribeEvent
      public static void onPlayerJumping(LivingJumpEvent event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.BUNNY_HOPPERS.value());
            if (stack.getItem() instanceof BunnyHoppersItem relic && relic.canPlayerUseAbility(player, stack, "hold")) {
               relic.setToggled(stack, true);
            }
         }
      }

      @SubscribeEvent
      public static void onPlayerFall(LivingFallEvent event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.BUNNY_HOPPERS.value());
            if (stack.getItem() instanceof BunnyHoppersItem relic && !player.getCommandSenderWorld().isClientSide()) {
               event.setDistance(Math.max(event.getDistance() - relic.getTime(stack) * 1.2F, 0.0F));
            }
         }
      }
   }
}
