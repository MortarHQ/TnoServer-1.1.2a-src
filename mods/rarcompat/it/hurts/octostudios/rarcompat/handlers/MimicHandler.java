package it.hurts.octostudios.rarcompat.handlers;

import artifacts.entity.MimicEntity;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

@EventBusSubscriber
public class MimicHandler {
   public static final List<Item> MIMIC_LOOT = new ArrayList<>();
   public static final List<Item> MIMIFICABLE = new ArrayList<>();

   @SubscribeEvent
   public static void onStartedServer(TagsUpdatedEvent event) {
      List<Item> mimicLoot = BuiltInRegistries.ITEM
         .getTag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("rarcompat", "mimic_loot")))
         .stream()
         .flatMap(holderSet -> holderSet.stream().map(Holder::value))
         .toList();
      List<Item> mimificable = BuiltInRegistries.ITEM
         .getTag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("rarcompat", "mimificable")))
         .stream()
         .flatMap(holderSet -> holderSet.stream().map(Holder::value))
         .toList();
      MIMIC_LOOT.clear();
      MIMIFICABLE.clear();
      if (!mimicLoot.isEmpty() && !mimificable.isEmpty()) {
         mimicLoot.stream()
            .map(entry -> ResourceLocation.parse(String.valueOf(entry)))
            .filter(BuiltInRegistries.ITEM::containsKey)
            .map(BuiltInRegistries.ITEM::get)
            .forEach(MIMIC_LOOT::add);
         mimificable.stream()
            .map(entry -> ResourceLocation.parse(String.valueOf(entry)))
            .filter(BuiltInRegistries.ITEM::containsKey)
            .map(BuiltInRegistries.ITEM::get)
            .forEach(MIMIFICABLE::add);
      }
   }

   @SubscribeEvent
   public static void onLivingDrops(LivingDropsEvent event) {
      if (!MIMIC_LOOT.isEmpty() && event.getEntity() instanceof MimicEntity entity) {
         Level level = entity.getCommandSenderWorld();
         RandomSource random = level.getRandom();
         CompoundTag persistentData = entity.getPersistentData();

         for (int i = 0; i < (persistentData.getInt("relicCount") == 0 ? 1 : persistentData.getInt("relicCount")); i++) {
            event.getDrops()
               .add(new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), MIMIC_LOOT.get(random.nextInt(MIMIC_LOOT.size())).getDefaultInstance()));
         }
      }
   }
}
