package artifacts.neoforge.data.tags;

import artifacts.Artifacts;
import artifacts.registry.ModItems;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemTags extends ItemTagsProvider {
   public static final TagKey<Item> ARTIFACTS = createTag("artifacts");
   public static final TagKey<Item> HEAD = createTag("slot/head");
   public static final TagKey<Item> FACE = createTag("slot/face");
   public static final TagKey<Item> NECKLACE = createTag("slot/necklace");
   public static final TagKey<Item> HANDS = createTag("slot/hands");
   public static final TagKey<Item> BELT = createTag("slot/belt");
   public static final TagKey<Item> FEET = createTag("slot/feet");
   public static final TagKey<Item> ALL = createTag("slot/all");
   public static final TagKey<Item> ORIGINS_MEAT = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("origins", "meat"));
   public static final TagKey<Item> ORIGINS_SHIELDS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("origins", "shields"));

   private static TagKey<Item> createTag(String name) {
      return TagKey.create(Registries.ITEM, Artifacts.id(name));
   }

   public ItemTags(
      PackOutput packOutput,
      CompletableFuture<Provider> lookupProvider,
      CompletableFuture<TagLookup<Block>> blockTags,
      @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(packOutput, lookupProvider, blockTags, "artifacts", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(ARTIFACTS)
         .add(
            (Item[])BuiltInRegistries.ITEM
               .stream()
               .filter(item -> BuiltInRegistries.ITEM.getKey(item).getNamespace().equals("artifacts"))
               .filter(item -> item != ModItems.MIMIC_SPAWN_EGG.value())
               .toList()
               .toArray(new Item[0])
         );
      this.tag(HEAD)
         .add(
            new Item[]{
               (Item)ModItems.PLASTIC_DRINKING_HAT.value(),
               (Item)ModItems.NOVELTY_DRINKING_HAT.value(),
               (Item)ModItems.VILLAGER_HAT.value(),
               (Item)ModItems.SUPERSTITIOUS_HAT.value(),
               (Item)ModItems.COWBOY_HAT.value(),
               (Item)ModItems.ANGLERS_HAT.value()
            }
         );
      this.tag(FACE).add(new Item[]{(Item)ModItems.SNORKEL.value(), (Item)ModItems.NIGHT_VISION_GOGGLES.value()});
      this.tag(NECKLACE)
         .add(
            new Item[]{
               (Item)ModItems.LUCKY_SCARF.value(),
               (Item)ModItems.SCARF_OF_INVISIBILITY.value(),
               (Item)ModItems.CROSS_NECKLACE.value(),
               (Item)ModItems.PANIC_NECKLACE.value(),
               (Item)ModItems.SHOCK_PENDANT.value(),
               (Item)ModItems.FLAME_PENDANT.value(),
               (Item)ModItems.THORN_PENDANT.value(),
               (Item)ModItems.CHARM_OF_SINKING.value(),
               (Item)ModItems.CHARM_OF_SHRINKING.value()
            }
         );
      this.tag(HANDS)
         .add(
            new Item[]{
               (Item)ModItems.DIGGING_CLAWS.value(),
               (Item)ModItems.FERAL_CLAWS.value(),
               (Item)ModItems.POWER_GLOVE.value(),
               (Item)ModItems.FIRE_GAUNTLET.value(),
               (Item)ModItems.POCKET_PISTON.value(),
               (Item)ModItems.VAMPIRIC_GLOVE.value(),
               (Item)ModItems.GOLDEN_HOOK.value(),
               (Item)ModItems.ONION_RING.value(),
               (Item)ModItems.PICKAXE_HEATER.value(),
               (Item)ModItems.WITHERED_BRACELET.value()
            }
         );
      this.tag(BELT)
         .add(
            new Item[]{
               (Item)ModItems.CLOUD_IN_A_BOTTLE.value(),
               (Item)ModItems.OBSIDIAN_SKULL.value(),
               (Item)ModItems.ANTIDOTE_VESSEL.value(),
               (Item)ModItems.UNIVERSAL_ATTRACTOR.value(),
               (Item)ModItems.CRYSTAL_HEART.value(),
               (Item)ModItems.HELIUM_FLAMINGO.value(),
               (Item)ModItems.CHORUS_TOTEM.value(),
               (Item)ModItems.WARP_DRIVE.value()
            }
         );
      this.tag(FEET)
         .add(
            new Item[]{
               (Item)ModItems.AQUA_DASHERS.value(),
               (Item)ModItems.BUNNY_HOPPERS.value(),
               (Item)ModItems.KITTY_SLIPPERS.value(),
               (Item)ModItems.RUNNING_SHOES.value(),
               (Item)ModItems.SNOWSHOES.value(),
               (Item)ModItems.STEADFAST_SPIKES.value(),
               (Item)ModItems.FLIPPERS.value(),
               (Item)ModItems.ROOTED_BOOTS.value(),
               (Item)ModItems.STRIDER_SHOES.value()
            }
         );
      this.tag(ALL).add((Item)ModItems.WHOOPEE_CUSHION.value());
      this.tag(ORIGINS_MEAT).add(new Item[]{(Item)ModItems.EVERLASTING_BEEF.value(), (Item)ModItems.ETERNAL_STEAK.value()});
      this.tag(ORIGINS_SHIELDS).add((Item)ModItems.UMBRELLA.value());
      this.tag(net.minecraft.tags.ItemTags.PIGLIN_LOVED)
         .add(
            new Item[]{
               (Item)ModItems.GOLDEN_HOOK.value(),
               (Item)ModItems.CROSS_NECKLACE.value(),
               (Item)ModItems.ANTIDOTE_VESSEL.value(),
               (Item)ModItems.UNIVERSAL_ATTRACTOR.value()
            }
         );
   }
}
