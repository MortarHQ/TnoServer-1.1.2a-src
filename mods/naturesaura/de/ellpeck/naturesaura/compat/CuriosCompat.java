package de.ellpeck.naturesaura.compat;

import com.google.common.collect.ImmutableMap;
import de.ellpeck.naturesaura.data.ItemTagProvider;
import de.ellpeck.naturesaura.items.ModItems;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.CuriosDataProvider;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CuriosCompat implements ICompat {
   private static final Map<Item, String> TYPES = ImmutableMap.builder()
      .put(ModItems.EYE, "charm")
      .put(ModItems.EYE_IMPROVED, "charm")
      .put(ModItems.AURA_CACHE, "belt")
      .put(ModItems.AURA_TROVE, "belt")
      .put(ModItems.SHOCKWAVE_CREATOR, "necklace")
      .put(ModItems.DEATH_RING, "ring")
      .build();

   @Override
   public void addCapabilities(RegisterCapabilitiesEvent event) {
      for (Item item : TYPES.keySet()) {
         event.registerItem(CuriosCapability.ITEM, (s, c) -> new ICurio() {
            public void curioTick(SlotContext slotContext) {
               s.getItem().inventoryTick(s, slotContext.entity().level(), slotContext.entity(), -1, false);
            }

            public ItemStack getStack() {
               return s;
            }

            public boolean canEquipFromUse(SlotContext slotContext) {
               return true;
            }

            public boolean canSync(SlotContext slotContext) {
               return true;
            }
         }, new ItemLike[]{item});
      }
   }

   @Override
   public void gatherData(GatherDataEvent event) {
      event.getGenerator()
         .addProvider(
            event.includeServer(),
            new CuriosCompat.CuriosProvider(event.getGenerator().getPackOutput(), event.getExistingFileHelper(), event.getLookupProvider())
         );
   }

   @Override
   public void addItemTags(ItemTagProvider provider) {
      for (Entry<Item, String> entry : TYPES.entrySet()) {
         TagKey<Item> tag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", entry.getValue()));
         provider.tag(tag).add(entry.getKey());
      }
   }

   private static class CuriosProvider extends CuriosDataProvider {
      public CuriosProvider(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<Provider> registries) {
         super("naturesaura", output, fileHelper, registries);
      }

      public void generate(Provider provider, ExistingFileHelper existingFileHelper) {
         String[] types = CuriosCompat.TYPES.values().stream().distinct().toArray(String[]::new);
         this.createEntities("entities").addPlayer().addSlots(types);

         for (String type : types) {
            this.createSlot(type).size(1).addCosmetic(true);
         }
      }
   }
}
