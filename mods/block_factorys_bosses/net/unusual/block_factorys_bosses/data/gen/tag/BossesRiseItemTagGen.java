package net.unusual.block_factorys_bosses.data.gen.tag;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseItemTagGen extends ItemTagsProvider {
   public BossesRiseItemTagGen(
      PackOutput output, CompletableFuture<Provider> provider, BossesRiseBlockTagGen blockTagGen, @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(output, provider, blockTagGen.contentsGetter(), "block_factorys_bosses", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.copy(BossesRiseTags.Blocks.BONE_REMAINS, BossesRiseTags.Items.BONE_REMAINS);
      this.copy(BossesRiseTags.Blocks.SHIP_PLANKS, BossesRiseTags.Items.SHIP_PLANKS);
      this.tag(BossesRiseTags.Items.GLOWING_LOOT)
         .add(
            new Item[]{
               (Item)BossesRiseItems.DRAGON_BONE.get(),
               (Item)BossesRiseItems.DRAGON_SKULL.get(),
               (Item)BossesRiseItems.ICE_GAUNTLET.get(),
               (Item)BossesRiseItems.KNIGHT_SWORD.get(),
               (Item)BossesRiseItems.KRAKEN_TOOTH.get(),
               (Item)BossesRiseItems.SANDWORM_GAUNTLET.get()
            }
         );
      this.tag(ItemTags.PLANKS).addTag(BossesRiseTags.Items.SHIP_PLANKS);
      this.tag(ItemTags.SWORDS)
         .add(
            new Item[]{
               (Item)BossesRiseItems.DAGGER.get(),
               (Item)BossesRiseItems.KNIGHT_SWORD.get(),
               (Item)BossesRiseItems.LARGE_SWORD.get(),
               (Item)BossesRiseItems.WARRIOR_SWORD.get(),
               (Item)BossesRiseItems.PIRATE_SABER.get()
            }
         );
      this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(new Item[]{(Item)BossesRiseItems.ICE_GAUNTLET.get(), (Item)BossesRiseItems.KRAKEN_TRIDENT.get()});
      this.tag(ItemTags.TRIDENT_ENCHANTABLE).add((Item)BossesRiseItems.KRAKEN_TRIDENT.get());
      this.tag(ItemTags.HEAD_ARMOR).add(new Item[]{(Item)BossesRiseItems.DRAGON_SKULL.get(), (Item)BossesRiseItems.KNIGHT_HELMET.get()});
      this.tag(ItemTags.CHEST_ARMOR).add(new Item[]{(Item)BossesRiseItems.DRAGON_BONES_CHESTPLATE.get(), (Item)BossesRiseItems.KNIGHT_CHESTPLATE.get()});
      this.tag(ItemTags.LEG_ARMOR).add(new Item[]{(Item)BossesRiseItems.DRAGON_BONES_LEGGINGS.get(), (Item)BossesRiseItems.KNIGHT_LEGGINGS.get()});
      this.tag(ItemTags.FOOT_ARMOR).add(new Item[]{(Item)BossesRiseItems.DRAGON_BONES_BOOTS.get(), (Item)BossesRiseItems.KNIGHT_BOOTS.get()});
      this.tag(ItemTags.MEAT).add((Item)BossesRiseItems.DRAGON_SHANK.get());
      this.tag(Items.BONES).add((Item)BossesRiseItems.DRAGON_BONE.get());
      this.tag(Items.MELEE_WEAPON_TOOLS).add((Item)BossesRiseItems.KRAKEN_TRIDENT.get());
      this.tag(Items.RANGED_WEAPON_TOOLS).add(new Item[]{(Item)BossesRiseItems.KRAKEN_TRIDENT.get(), (Item)BossesRiseItems.SANDWORM_GAUNTLET.get()});
      this.tag(Items.TOOLS_SPEAR).add((Item)BossesRiseItems.KRAKEN_TRIDENT.get());
      this.tag(Items.TOOLS_SHIELD)
         .add(
            new Item[]{(Item)BossesRiseItems.DRAGON_GUARD_SHIELD.get(), (Item)BossesRiseItems.ENHANCED_SHIELD.get(), (Item)BossesRiseItems.ICE_GAUNTLET.get()}
         );
      this.tag(ItemTags.BREAKS_DECORATED_POTS).add((Item)BossesRiseItems.KRAKEN_TRIDENT.get());
   }
}
