package net.unusual.block_factorys_bosses.data.gen.tag;

import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseEntityTagGen extends EntityTypeTagsProvider {
   public BossesRiseEntityTagGen(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, provider, "block_factorys_bosses", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(BossesRiseTags.EntityTypes.BF_BOSS)
         .add(
            new EntityType[]{
               (EntityType)BossesRiseEntities.INFERNAL_DRAGON.get(),
               (EntityType)BossesRiseEntities.YETI.get(),
               (EntityType)BossesRiseEntities.SANDWORM.get(),
               (EntityType)BossesRiseEntities.UNDERWORLD_KNIGHT.get(),
               (EntityType)BossesRiseEntities.KRAKEN.get()
            }
         );
      this.tag(EntityTypes.BOSSES).addTags(new TagKey[]{BossesRiseTags.EntityTypes.BF_BOSS});
      this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).addTag(BossesRiseTags.EntityTypes.BF_BOSS);
      this.tag(BossesRiseTags.EntityTypes.PIRATE)
         .add(
            new EntityType[]{
               (EntityType)BossesRiseEntities.PIRATE_CAPTAIN.get(),
               (EntityType)BossesRiseEntities.PIRATE_ROOK.get(),
               (EntityType)BossesRiseEntities.CROSSBOW_PIRATE.get()
            }
         );
      this.tag(EntityTypeTags.SKELETONS)
         .add(
            new EntityType[]{
               (EntityType)BossesRiseEntities.FROZEN_SKELETON.get(),
               (EntityType)BossesRiseEntities.DRAGON_GUARD_SWORD.get(),
               (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD.get(),
               (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_FIREBALL.get(),
               (EntityType)BossesRiseEntities.SOUL_SKELETON.get(),
               (EntityType)BossesRiseEntities.SOUL_KNIGHT_WITHER_SKELETON.get(),
               (EntityType)BossesRiseEntities.PIRATE_CAPTAIN.get(),
               (EntityType)BossesRiseEntities.PIRATE_ROOK.get(),
               (EntityType)BossesRiseEntities.CROSSBOW_PIRATE.get()
            }
         );
      this.tag(EntityTypeTags.UNDEAD)
         .add(
            new EntityType[]{
               (EntityType)BossesRiseEntities.FROZEN_SKELETON.get(),
               (EntityType)BossesRiseEntities.DRAGON_GUARD_SWORD.get(),
               (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD.get(),
               (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_FIREBALL.get(),
               (EntityType)BossesRiseEntities.SOUL_SKELETON.get(),
               (EntityType)BossesRiseEntities.SOUL_KNIGHT_WITHER_SKELETON.get(),
               (EntityType)BossesRiseEntities.UNDERWORLD_KNIGHT.get(),
               (EntityType)BossesRiseEntities.PIRATE_CAPTAIN.get(),
               (EntityType)BossesRiseEntities.PIRATE_ROOK.get(),
               (EntityType)BossesRiseEntities.CROSSBOW_PIRATE.get(),
               (EntityType)BossesRiseEntities.GHOST_TENTACLE.get()
            }
         );
      this.tag(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
         .add(
            new EntityType[]{
               (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD.get(), (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_FIREBALL.get()
            }
         );
      this.tag(EntityTypeTags.IMPACT_PROJECTILES)
         .add(
            new EntityType[]{
               (EntityType)BossesRiseEntities.ICE_SPIKE_PR.get(),
               (EntityType)BossesRiseEntities.RIFT_PROJECTILE.get(),
               (EntityType)BossesRiseEntities.BIG_RIFT_PROJECTILE.get(),
               (EntityType)BossesRiseEntities.POISON_SPIT_PR.get(),
               (EntityType)BossesRiseEntities.CANNONBALL.get(),
               (EntityType)BossesRiseEntities.THROWN_CRATE.get()
            }
         );
      this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add((EntityType)BossesRiseEntities.YETI.get());
      this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
         .add(new EntityType[]{(EntityType)BossesRiseEntities.KRAKEN.get(), (EntityType)BossesRiseEntities.KRAKEN_TENTACLE.get()});
   }
}
