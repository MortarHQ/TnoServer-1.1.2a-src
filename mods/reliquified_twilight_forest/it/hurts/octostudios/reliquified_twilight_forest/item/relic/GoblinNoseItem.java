package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import com.mojang.blaze3d.shaders.FogShape;
import it.hurts.octostudios.reliquified_twilight_forest.api.OreCache;
import it.hurts.octostudios.reliquified_twilight_forest.data.loot.LootEntries;
import it.hurts.octostudios.reliquified_twilight_forest.init.ConfigRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.item.BundleLikeRelicItem;
import it.hurts.octostudios.reliquified_twilight_forest.network.UpdateChunkPacket;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
import it.hurts.sskirillss.relics.client.particles.BasicColoredParticle.Constructor;
import it.hurts.sskirillss.relics.client.particles.BasicColoredParticle.Options;
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
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.neoforged.neoforge.event.level.ChunkEvent.Load;
import net.neoforged.neoforge.event.level.ChunkEvent.Unload;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.SlotContext;

public class GoblinNoseItem extends BundleLikeRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("vein_seeker")
                     .stat(
                        StatData.builder("radius")
                           .initialValue(4.0, 8.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.6)
                           .formatValue(MathButCool::roundSingleDigit)
                           .build()
                     )
                     .stat(StatData.builder("filter_slots").initialValue(0.0, 0.0).upgradeModifier(UpgradeOperation.ADD, 1.0).formatValue(Math::round).build())
                     .active(CastData.builder().type(CastType.TOGGLEABLE).build())
                     .maxLevel(5)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("vein_seeker").gem(GemShape.SQUARE, GemColor.GREEN).build()).build()
               )
               .maxLevel(5)
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.TROLL}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (stack.getItem() instanceof GoblinNoseItem relic && relic.isAbilityTicking(stack, "vein_seeker")) {
         if (!livingEntity.level().isClientSide) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 30, 0, true, false, false));
         }

         if (livingEntity.level().isClientSide && livingEntity == Minecraft.getInstance().player) {
            List<BlockPos> ORES = OreCache.getNearbyOres(
               livingEntity.level(), livingEntity.blockPosition(), (float)relic.getStatValue(stack, "vein_seeker", "radius")
            );
            ORES.forEach(
               bp -> {
                  BlockState state = livingEntity.level().getBlockState(bp);
                  if (!(livingEntity.getRandom().nextFloat() < 0.66F)
                     && state.is(Blocks.ORES)
                     && (relic.getContents(stack).isEmpty() || relic.getItemCount(stack, state.getBlock().asItem()) >= 1)) {
                     spawnOreParticles(livingEntity, bp);
                  }
               }
            );
         }
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
      if (!ConfigRegistry.GENERAL.isEnabledOreScanner()) {
         tooltipComponents.add(Component.literal(" "));
         tooltipComponents.add(
            Component.literal("Temporarily disabled due to a technical issue!").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.DARK_RED)
         );
      }
   }

   @Override
   public int getMaxSlots(ItemStack stack) {
      return stack.getItem() instanceof GoblinNoseItem relic ? (int)Math.round(relic.getStatValue(stack, "vein_seeker", "filter_slots")) : 0;
   }

   public static void spawnOreParticles(LivingEntity livingEntity, BlockPos pos) {
      AABB area = new AABB(pos.getCenter(), pos.getCenter()).inflate(0.5);
      Random random = new Random();
      Level level = livingEntity.level();
      ParticleOptions ORE_PARTICLE = new Options(
         Constructor.builder()
            .color(new Color(random.nextInt(230, 255), random.nextInt(170, 255), 0, 255).getRGB())
            .diameter(random.nextFloat(0.2F, 0.35F))
            .lifetime(20)
            .scaleModifier(0.8F)
            .physical(false)
            .visibleThroughWalls(true)
            .roll(0.15F)
            .build()
      );
      level.addParticle(
         ORE_PARTICLE,
         true,
         random.nextDouble(area.minX, area.maxX),
         random.nextDouble(area.minY, area.maxY),
         random.nextDouble(area.minZ, area.maxZ),
         0.0,
         0.0,
         0.0
      );
      if (random.nextFloat() < 0.1F && livingEntity.tickCount % 4 == 0) {
         ORE_PARTICLE = new Options(
            Constructor.builder()
               .color(new Color(255, 255, 255, 255).getRGB())
               .diameter(0.4F)
               .lifetime(60)
               .scaleModifier(0.95F)
               .physical(false)
               .visibleThroughWalls(true)
               .roll(0.25F)
               .build()
         );
         level.addParticle(
            ORE_PARTICLE,
            true,
            random.nextDouble(area.minX, area.maxX),
            random.nextDouble(area.minY, area.maxY),
            random.nextDouble(area.minZ, area.maxZ),
            0.0,
            0.0,
            0.0
         );
      }
   }

   @Override
   public void playInsertSound(Player player, ItemStack toInsert) {
      player.playSound(SoundEvents.AMETHYST_BLOCK_PLACE, 0.8F, 1.25F);
   }

   @Override
   public Predicate<ItemStack> getPredicate() {
      return stack -> stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState().is(Blocks.ORES);
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class ClientEvents {
      @SubscribeEvent
      public static void changeFog(RenderFog event) {
         ItemStack stack = EntityUtils.findEquippedCurio(Minecraft.getInstance().player, (Item)ItemRegistry.GOBLIN_NOSE.get());
         if (stack.getItem() instanceof GoblinNoseItem relic
            && relic.isAbilityTicking(stack, "vein_seeker")
            && Minecraft.getInstance().player.getEffect(MobEffects.DARKNESS) instanceof MobEffectInstance inst
            && inst.getDuration() <= 30) {
            float newDistance = (float)relic.getStatValue(stack, "vein_seeker", "radius");
            event.scaleNearPlaneDistance(newDistance / 15.0F);
            event.scaleFarPlaneDistance(newDistance / 15.0F);
            event.setFogShape(FogShape.SPHERE);
            event.setCanceled(true);
         }
      }
   }

   @EventBusSubscriber
   public static class CommonEvents {
      @SubscribeEvent
      public static void onChunkLoad(Load event) {
         if (event.getLevel().isClientSide()) {
            if (ConfigRegistry.GENERAL.isEnabledOreScanner()) {
               ChunkAccess chunk = event.getChunk();
               OreCache.scanChunkAsync(event.getLevel(), chunk);
            }
         }
      }

      @SubscribeEvent
      public static void onBlockPlace(EntityPlaceEvent event) {
         if (!event.getLevel().isClientSide()) {
            if (ConfigRegistry.GENERAL.isEnabledOreScanner()) {
               ChunkAccess chunk = event.getLevel().getChunk(event.getPos());
               PacketDistributor.sendToPlayersTrackingChunk(
                  (ServerLevel)event.getLevel(), chunk.getPos(), new UpdateChunkPacket(chunk.getPos()), new CustomPacketPayload[0]
               );
            }
         }
      }

      @SubscribeEvent
      public static void onBlockBreak(BreakEvent event) {
         ItemStack stack = EntityUtils.findEquippedCurio(event.getPlayer(), (Item)ItemRegistry.GOBLIN_NOSE.get());
         if (!event.getLevel().isClientSide()) {
            if (ConfigRegistry.GENERAL.isEnabledOreScanner()) {
               ChunkAccess chunk = event.getLevel().getChunk(event.getPos());
               PacketDistributor.sendToPlayersTrackingChunk(
                  (ServerLevel)event.getLevel(), chunk.getPos(), new UpdateChunkPacket(chunk.getPos()), new CustomPacketPayload[0]
               );
            }

            if (stack.getItem() instanceof GoblinNoseItem relic && relic.isAbilityTicking(stack, "vein_seeker") && event.getState().is(Blocks.ORES)) {
               relic.spreadRelicExperience(event.getPlayer(), stack, 1);
            }
         }
      }

      @SubscribeEvent
      public static void onChunkUnload(Unload event) {
         if (event.getLevel().isClientSide()) {
            if (ConfigRegistry.GENERAL.isEnabledOreScanner()) {
               ChunkAccess chunk = event.getChunk();
               OreCache.removeChunk(chunk.getPos());
            }
         }
      }
   }
}
