package twilightforest.data.tags.compat;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.EntityTagGenerator;
import twilightforest.init.TFEntities;

public class ModdedEntityTagGenerator extends EntityTypeTagsProvider {
   public static final TagKey<EntityType<?>> AC_RESISTS_ACID = createTagFor("alexscaves", "resists_acid");
   public static final TagKey<EntityType<?>> AC_RESISTS_MAGNETS = createTagFor("alexscaves", "resists_magnets");
   public static final TagKey<EntityType<?>> AC_RESISTS_TREMORSAURUS_ROAR = createTagFor("alexscaves", "resists_tremorsaurus_roar");
   public static final TagKey<EntityType<?>> AETHER_DEFLECTABLE_PROJECTILES = createTagFor("aether", "deflectable_projectiles");
   public static final TagKey<EntityType<?>> AETHER_FIRE_MOB = createTagFor("aether", "fire_mob");
   public static final TagKey<EntityType<?>> AETHER_PIGS = createTagFor("aether", "pigs");
   public static final TagKey<EntityType<?>> AN_JAR_BLACKLIST = createTagFor("ars_nouveau", "jar_blacklist");
   public static final TagKey<EntityType<?>> AN_JAR_RELEASE_BLACKLIST = createTagFor("ars_nouveau", "jar_release_blacklist");
   public static final TagKey<EntityType<?>> IE_SHADER_BLACKLIST = createTagFor("immersiveengineering", "shaderbag/blacklist");

   public ModdedEntityTagGenerator(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper helper) {
      super(output, provider, "twilightforest", helper);
   }

   protected void addTags(Provider provider) {
      this.tag(AC_RESISTS_ACID).add(new EntityType[]{(EntityType)TFEntities.HYDRA.get(), (EntityType)TFEntities.NAGA.get()});
      this.tag(AC_RESISTS_MAGNETS).addTag(EntityTagGenerator.BOSSES);
      this.tag(AC_RESISTS_TREMORSAURUS_ROAR).add(new EntityType[]{(EntityType)TFEntities.HYDRA.get(), (EntityType)TFEntities.UR_GHAST.get()});
      this.tag(AETHER_DEFLECTABLE_PROJECTILES)
         .add(
            new EntityType[]{
               (EntityType)TFEntities.NATURE_BOLT.get(),
               (EntityType)TFEntities.LICH_BOLT.get(),
               (EntityType)TFEntities.WAND_BOLT.get(),
               (EntityType)TFEntities.SLIME_BLOB.get(),
               (EntityType)TFEntities.ICE_SNOWBALL.get()
            }
         );
      this.tag(AETHER_FIRE_MOB).add((EntityType)TFEntities.FIRE_BEETLE.get());
      this.tag(AETHER_PIGS).add((EntityType)TFEntities.BOAR.get());
      this.tag(AN_JAR_BLACKLIST).addTag(EntityTagGenerator.BOSSES);
      this.tag(AN_JAR_RELEASE_BLACKLIST).addTag(EntityTagGenerator.BOSSES);
      this.tag(IE_SHADER_BLACKLIST).addTag(EntityTagGenerator.BOSSES);
   }

   private static TagKey<EntityType<?>> createTagFor(String modid, String tagName) {
      return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(modid, tagName));
   }
}
