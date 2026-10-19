package io.github.Memoires.mysticism.neoforge.data.tag;

import io.github.Memoires.mysticism.data.MysticismItemTags;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import io.github.Memoires.mysticism.registry.item.MysticismWeaponItems;
import io.github.manasmods.tensura.data.TensuraItemTags;
import io.github.manasmods.tensura.registry.item.TensuraToolItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MysticismItemTagProvider extends ItemTagsProvider {
   public MysticismItemTagProvider(
      PackOutput output, CompletableFuture<Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper
   ) {
      super(output, future, provider, "mysticism", helper);
   }

   protected void addTags(Provider lookup) {
      this.tag(MysticismItemTags.KAMUI_PROJECTILE)
         .add(
            new Item[]{
               Items.ARROW,
               Items.TIPPED_ARROW,
               Items.SPECTRAL_ARROW,
               Items.SNOWBALL,
               Items.EGG,
               Items.ENDER_PEARL,
               Items.EXPERIENCE_BOTTLE,
               Items.SPLASH_POTION,
               Items.LINGERING_POTION,
               Items.TRIDENT,
               Items.FIREWORK_ROCKET,
               Items.WIND_CHARGE,
               (Item)TensuraToolItems.KUNAI.get(),
               (Item)TensuraToolItems.PURE_MAGISTEEL_KUNAI.get(),
               (Item)TensuraToolItems.WEB_CARTRIDGE.get(),
               (Item)TensuraToolItems.SEVERER_BLADE.get(),
               (Item)TensuraToolItems.SPEARED_FIN_ARROW.get(),
               (Item)TensuraToolItems.ADAMANTITE_SPEAR.get(),
               (Item)TensuraToolItems.DIAMOND_SPEAR.get(),
               (Item)TensuraToolItems.HIHIIROKANE_SPEAR.get(),
               (Item)TensuraToolItems.GOLDEN_SPEAR.get(),
               (Item)TensuraToolItems.HIGH_MAGISTEEL_SPEAR.get(),
               (Item)TensuraToolItems.IRON_SPEAR.get(),
               (Item)TensuraToolItems.LOW_MAGISTEEL_SPEAR.get(),
               (Item)TensuraToolItems.NETHERITE_SPEAR.get(),
               (Item)TensuraToolItems.MITHRIL_SPEAR.get(),
               (Item)TensuraToolItems.ORICHALCUM_SPEAR.get(),
               (Item)TensuraToolItems.WOODEN_SPEAR.get(),
               (Item)TensuraToolItems.SILVER_SPEAR.get(),
               (Item)TensuraToolItems.STONE_SPEAR.get(),
               (Item)TensuraToolItems.UNICORN_HORN_SPEAR.get()
            }
         );
      this.tag(TensuraItemTags.INDESTRUCTIBLE_BY_ENVIRONMENTAL_CAUSE).add(new Item[]{(Item)MysticismMobDropItems.LIGHTNING_ESSENCE.get()});
      this.tag(TensuraItemTags.EVOLUTION_ESSENCES)
         .add(
            new Item[]{
               (Item)MysticismMobDropItems.LIGHTNING_ESSENCE.get(),
               (Item)MysticismMobDropItems.CRYPTID_ESSENCE.get(),
               (Item)MysticismMobDropItems.FLAME_ESSENCE.get(),
               (Item)MysticismMobDropItems.ICE_ESSENCE.get()
            }
         );
      this.tag(TensuraItemTags.LONG_SWORDS).add(new Item[]{(Item)MysticismWeaponItems.AXIOM.get(), (Item)MysticismWeaponItems.WALTZ.get()});
   }
}
