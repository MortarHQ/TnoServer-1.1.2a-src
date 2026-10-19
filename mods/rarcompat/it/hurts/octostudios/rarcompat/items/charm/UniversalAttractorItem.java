package it.hurts.octostudios.rarcompat.items.charm;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.init.DataComponentRegistry;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
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
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.Locale;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent.Post;
import top.theillusivec4.curios.api.SlotContext;

public class UniversalAttractorItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("attractor")
                     .active(CastData.builder().type(CastType.INTERRUPTIBLE).build())
                     .icon((player, stack, ability) -> ability + "_" + this.getMode(stack).name().toLowerCase(Locale.ROOT))
                     .stat(
                        StatData.builder("radius")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .formatValue(value -> (int)MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 8, 12)
                           .star(1, 11, 5)
                           .star(2, 19, 8)
                           .star(3, 15, 16)
                           .star(4, 3, 18)
                           .star(5, 11, 19)
                           .star(6, 14, 25)
                           .star(7, 2, 24)
                           .star(8, 8, 26)
                           .star(9, 13, 30)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(4, 5)
                           .link(5, 6)
                           .link(7, 8)
                           .link(8, 9)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-2883584).borderBottom(-15245846).build())
               .beams(BeamsData.builder().startColor(-201383).endColor(7217930).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("attractor").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.CAVE, LootEntries.MINESHAFT}).build())
         .build();
   }

   public UniversalAttractorItem.Mode getMode(ItemStack stack) {
      return UniversalAttractorItem.Mode.byIndex((Integer)stack.getOrDefault(DataComponentRegistry.MODE, UniversalAttractorItem.Mode.ATTRACT.getIndex()));
   }

   public void setMode(ItemStack stack, UniversalAttractorItem.Mode mode) {
      stack.set(DataComponentRegistry.MODE, mode.getIndex());
   }

   public void cycleMode(ItemStack stack, int steps) {
      this.setMode(stack, this.getMode(stack).cycle(steps));
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      this.cycleMode(stack, 1);
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player
         && !player.getCommandSenderWorld().isClientSide()
         && this.canPlayerUseAbility(player, stack, "attractor")
         && this.getMode(stack) != UniversalAttractorItem.Mode.NEUTRAL) {
         Vec3 pos = player.position();
         RandomSource random = player.getRandom();
         Level level = player.getCommandSenderWorld();

         for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(this.getStatValue(stack, "attractor", "radius")))) {
            if (item.isAlive() && !item.hasPickUpDelay()) {
               Vec3 oldPos = new Vec3(item.getX(), item.getY() - item.getBbHeight() * 3.0F, item.getZ());
               if (this.getMode(stack) == UniversalAttractorItem.Mode.ATTRACT) {
                  item.moveTo(pos);
                  createLine(
                     ParticleUtils.constructSimpleSpark(new Color(200 + random.nextInt(55), random.nextInt(50), random.nextInt(50)), 0.2F, 20, 0.8F),
                     level,
                     pos,
                     oldPos
                  );
               } else {
                  item.setDeltaMovement(item.position().subtract(pos).normalize().scale(0.5));
                  if (!item.horizontalCollision) {
                     createLine(
                        ParticleUtils.constructSimpleSpark(new Color(random.nextInt(50), random.nextInt(50), 200 + random.nextInt(55)), 0.2F, 3, 0.5F),
                        level,
                        pos,
                        oldPos
                     );
                  }
               }
            }
         }
      }
   }

   public static void createLine(ParticleOptions particle, Level level, Vec3 start, Vec3 end) {
      Vec3 delta = end.subtract(start);
      Vec3 dir = delta.normalize();
      double amount = delta.length() * 3.0;

      for (double i = 0.0; i < amount; i++) {
         double progress = i * delta.length() / amount;
         ((ServerLevel)level)
            .sendParticles(particle, start.x + dir.x * progress, start.y + dir.y * progress + 1.0, start.z + dir.z * progress, 0, 0.0, 0.0, 0.0, 0.0);
      }
   }

   public enum Mode {
      ATTRACT(1),
      REPEL(2),
      NEUTRAL(3);

      private final int index;

      public static UniversalAttractorItem.Mode byIndex(int index) {
         for (UniversalAttractorItem.Mode mode : values()) {
            if (mode.getIndex() == index) {
               return mode;
            }
         }

         throw new IllegalArgumentException();
      }

      public UniversalAttractorItem.Mode cycle(int steps) {
         UniversalAttractorItem.Mode[] modes = values();
         int index = (this.ordinal() + steps) % modes.length;
         if (index < 0) {
            index += modes.length;
         }

         return modes[index];
      }

      public int getIndex() {
         return this.index;
      }

      Mode(final int index) {
         this.index = index;
      }
   }

   @EventBusSubscriber
   public static class UniversalAttractorEvent {
      @SubscribeEvent
      public static void onItemPickedUp(Post event) {
         Player player = event.getPlayer();
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.UNIVERSAL_ATTRACTOR.value());
         if (!player.getCommandSenderWorld().isClientSide()
            && stack.getItem() instanceof UniversalAttractorItem relic
            && (Boolean)stack.getOrDefault(it.hurts.sskirillss.relics.init.DataComponentRegistry.TOGGLED, true)) {
            ItemStack item = event.getOriginalStack();
            ItemEntity itemEntity = event.getItemEntity();
            if (itemEntity.getRandom().nextDouble() <= (double)item.getCount() / item.getMaxStackSize()) {
               if (itemEntity.getOwner() != null && itemEntity.getOwner().getUUID().equals(player.getUUID())) {
                  return;
               }

               relic.spreadRelicExperience(player, stack, 1);
            }
         }
      }
   }
}
