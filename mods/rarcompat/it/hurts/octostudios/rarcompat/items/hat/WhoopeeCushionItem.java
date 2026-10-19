package it.hurts.octostudios.rarcompat.items.hat;

import artifacts.registry.ModItems;
import artifacts.registry.ModSoundEvents;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
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
import it.hurts.sskirillss.relics.network.packets.PacketPlayerMotion;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import top.theillusivec4.curios.api.SlotContext;

public class WhoopeeCushionItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("push")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.2, 0.4)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.075)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("radius")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.05)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 10, 13)
                           .star(1, 8, 6)
                           .star(2, 16, 9)
                           .star(3, 17, 14)
                           .star(4, 16, 22)
                           .star(5, 6, 21)
                           .star(6, 3, 13)
                           .link(0, 1)
                           .link(0, 2)
                           .link(0, 3)
                           .link(0, 4)
                           .link(0, 5)
                           .link(0, 6)
                           .link(0, 6)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-2270887).borderBottom(-8639958).build())
               .beams(BeamsData.builder().startColor(-1017222).endColor(15759994).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("push").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.OVERWORLD}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player
         && !player.getCommandSenderWorld().isClientSide()
         && this.isAbilityUnlocked(stack, "push")
         && player.onGround()) {
         boolean isSneaking = player.isShiftKeyDown();
         RandomSource random = player.getRandom();
         if (isSneaking
            && !(Boolean)stack.getOrDefault(DataComponentRegistry.TOGGLED, false)
            && random.nextDouble() < this.getStatValue(stack, "push", "chance")) {
            this.createWhoopee((ServerLevel)player.level(), player, stack, random);
         }

         stack.set(DataComponentRegistry.TOGGLED, isSneaking);
      }
   }

   public void createWhoopee(ServerLevel level, Player player, ItemStack stack, RandomSource random) {
      level.playSound(
         null, player.blockPosition(), (SoundEvent)ModSoundEvents.FART.value(), player.getSoundSource(), 1.0F, 0.75F + player.getRandom().nextFloat()
      );
      this.spreadRelicExperience(player, stack, 1);

      for (LivingEntity livingEntity : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(this.getStatValue(stack, "push", "radius")))
         .stream()
         .filter(livingEntityx -> !livingEntityx.getUUID().equals(player.getUUID()))
         .toList()) {
         Vec3 vec3 = livingEntity.position().subtract(player.position()).normalize();
         if (livingEntity instanceof ServerPlayer serverPlayer) {
            NetworkHandler.sendToClient(new PacketPlayerMotion(vec3.x, 0.2, vec3.z), serverPlayer);
         } else {
            livingEntity.setDeltaMovement(vec3.x, 0.2, vec3.z);
         }

         livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1));
      }

      level.sendParticles(
         ParticleUtils.constructSimpleSpark(new Color(0, 100 + random.nextInt(50), 0), 0.5F, 50, 0.9F),
         player.getX(),
         player.getY() + 0.5,
         player.getZ(),
         30,
         0.25,
         0.3,
         0.25,
         0.1
      );
   }

   @EventBusSubscriber
   public static class WhoopeeCushionEvent {
      @SubscribeEvent
      public static void onAttackPlayer(Pre event) {
         if (event.getEntity() instanceof Player player && !player.getCommandSenderWorld().isClientSide() && event.getSource().getEntity() != player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.WHOOPEE_CUSHION.value());
            if (stack.getItem() instanceof WhoopeeCushionItem relic
               && relic.isAbilityUnlocked(stack, "push")
               && !(player.getRandom().nextDouble() > relic.getStatValue(stack, "push", "chance"))) {
               relic.createWhoopee((ServerLevel)player.getCommandSenderWorld(), player, stack, player.getRandom());
            }
         }
      }
   }
}
