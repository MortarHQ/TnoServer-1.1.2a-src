package noobanidus.mods.lootr.neoforge.gen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootr.common.api.LootrConstants;
import noobanidus.mods.lootr.common.api.LootrTags;
import noobanidus.mods.lootr.neoforge.init.ModEntities;
import org.jetbrains.annotations.Nullable;

public class LootrEntityTagsProvider extends EntityTypeTagsProvider {
   public LootrEntityTagsProvider(PackOutput arg, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
      super(arg, completableFuture, "lootr", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(LootrTags.Entity.CONVERT_CARTS).add(EntityType.CHEST_MINECART);
      this.tag(LootrTags.Entity.CONVERT_ITEM_FRAMES).add(new EntityType[]{EntityType.ITEM_FRAME, EntityType.GLOW_ITEM_FRAME});
      this.tag(LootrTags.Entity.CONVERT_ENTITIES).addTags(new TagKey[]{LootrTags.Entity.CONVERT_CARTS});
      this.tag(LootrTags.Entity.MINECARTS)
         .add(TagEntry.optionalTag(ModEntities.LOOTR_MINECART_ENTITY.getId()))
         .add(TagEntry.optionalTag(LootrConstants.LOOTR_CART));
      this.tag(LootrTags.Entity.ITEM_FRAMES).add((EntityType)ModEntities.ITEM_FRAME.get());
      this.tag(LootrTags.Entity.CONTAINERS).addTags(new TagKey[]{LootrTags.Entity.MINECARTS, LootrTags.Entity.ITEM_FRAMES});
      this.tag(LootrTags.Entity.ENTITY_SELECTOR_SKIP).addTags(new TagKey[]{LootrTags.Entity.MINECARTS, LootrTags.Entity.ITEM_FRAMES});
   }

   public String getName() {
      return "Lootr Entity Type Tags";
   }
}
