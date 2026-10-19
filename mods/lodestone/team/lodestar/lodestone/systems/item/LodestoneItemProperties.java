package team.lodestar.lodestone.systems.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class LodestoneItemProperties extends Properties {
   public static final Map<ResourceKey<CreativeModeTab>, List<ResourceLocation>> TAB_SORTING = new HashMap<>();
   public final ResourceKey<CreativeModeTab> tab;

   public LodestoneItemProperties(DeferredHolder<CreativeModeTab, CreativeModeTab> tab) {
      this(tab.getKey());
   }

   public LodestoneItemProperties(ResourceKey<CreativeModeTab> tab) {
      this.tab = tab;
   }

   public LodestoneItemProperties() {
      this.tab = null;
   }

   public static Properties mergeAttributes(Properties properties, ItemAttributeModifiers attributes) {
      Builder components = properties.components;
      if (components != null && components.build().has(DataComponents.ATTRIBUTE_MODIFIERS)) {
         ItemAttributeModifiers existing = (ItemAttributeModifiers)components.build().get(DataComponents.ATTRIBUTE_MODIFIERS);
         net.minecraft.world.item.component.ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
         if (existing != null) {
            for (Entry entry : existing.modifiers()) {
               builder.add(entry.attribute(), entry.modifier(), entry.slot());
            }
         }

         for (Entry entry : attributes.modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
         }

         return properties.attributes(builder.build());
      } else {
         return properties.attributes(attributes);
      }
   }

   public LodestoneItemProperties mergeAttributes(ItemAttributeModifiers attributes) {
      if (this.components != null && this.components.build().has(DataComponents.ATTRIBUTE_MODIFIERS)) {
         ItemAttributeModifiers existing = (ItemAttributeModifiers)this.components.build().get(DataComponents.ATTRIBUTE_MODIFIERS);
         net.minecraft.world.item.component.ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

         for (Entry entry : existing.modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
         }

         for (Entry entry : attributes.modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
         }

         return this.attributes(builder.build());
      } else {
         return this.attributes(attributes);
      }
   }

   public LodestoneItemProperties food(FoodProperties food) {
      return (LodestoneItemProperties)super.food(food);
   }

   public LodestoneItemProperties stacksTo(int maxStackSize) {
      return (LodestoneItemProperties)super.stacksTo(maxStackSize);
   }

   public LodestoneItemProperties durability(int maxDamage) {
      return (LodestoneItemProperties)super.durability(maxDamage);
   }

   public LodestoneItemProperties craftRemainder(Item craftingRemainingItem) {
      return (LodestoneItemProperties)super.craftRemainder(craftingRemainingItem);
   }

   public LodestoneItemProperties rarity(Rarity rarity) {
      return (LodestoneItemProperties)super.rarity(rarity);
   }

   public LodestoneItemProperties fireResistant() {
      return (LodestoneItemProperties)super.fireResistant();
   }

   public LodestoneItemProperties jukeboxPlayable(ResourceKey<JukeboxSong> song) {
      return (LodestoneItemProperties)super.jukeboxPlayable(song);
   }

   public LodestoneItemProperties setNoRepair() {
      return (LodestoneItemProperties)super.setNoRepair();
   }

   public LodestoneItemProperties requiredFeatures(FeatureFlag... requiredFeatures) {
      return (LodestoneItemProperties)super.requiredFeatures(requiredFeatures);
   }

   public <T> LodestoneItemProperties component(DataComponentType<T> component, T value) {
      return (LodestoneItemProperties)super.component(component, value);
   }

   public LodestoneItemProperties attributes(ItemAttributeModifiers attributes) {
      return (LodestoneItemProperties)super.attributes(attributes);
   }

   public static void addToTabSorting(ResourceLocation itemId, Properties properties) {
      if (properties instanceof LodestoneItemProperties lodestoneItemProperties) {
         if (lodestoneItemProperties.tab == null) {
            return;
         }

         TAB_SORTING.computeIfAbsent(lodestoneItemProperties.tab, key -> new ArrayList<>()).add(itemId);
      }
   }

   public static void populateItemGroups(BuildCreativeModeTabContentsEvent event) {
      ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
      if (TAB_SORTING.containsKey(tabKey)) {
         TAB_SORTING.get(tabKey)
            .stream()
            .<Item>map(BuiltInRegistries.ITEM::get)
            .filter(s -> !event.getParentEntries().contains(s.getDefaultInstance()))
            .forEach(event::accept);
      }
   }
}
