package twilightforest.data.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.compat.ModdedEntityTagGenerator;
import twilightforest.init.TFEntities;

public class EntityTagGenerator extends ModdedEntityTagGenerator {
   public static final TagKey<EntityType<?>> BOSSES = create(TwilightForestMod.prefix("bosses"));
   public static final TagKey<EntityType<?>> LICH_POPPABLES = create(TwilightForestMod.prefix("lich_poppables"));
   public static final TagKey<EntityType<?>> LIFEDRAIN_DROPS_NO_FLESH = create(TwilightForestMod.prefix("lifedrain_drops_no_flesh"));
   public static final TagKey<EntityType<?>> RIDES_OBSTRUCT_SNATCHING = create(TwilightForestMod.prefix("rides_obstruct_snatching"));
   public static final TagKey<EntityType<?>> DONT_KILL_BUGS = create(TwilightForestMod.prefix("dont_kill_bugs"));
   public static final TagKey<EntityType<?>> SORTABLE_ENTITIES = create(TwilightForestMod.prefix("sortable_entities"));
   public static final TagKey<EntityType<?>> MULTIPLAYER_INCLUSIVE_ENTITIES = create(TwilightForestMod.prefix("multiplayer_inclusive_entities"));
   public static final TagKey<EntityType<?>> LICH_DEFLECTS_PHASE_2 = create(TwilightForestMod.prefix("lich_deflects_phase_2"));

   public EntityTagGenerator(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper helper) {
      super(output, provider, helper);
   }

   @Override
   protected void addTags(Provider provider) {
      super.addTags(provider);
      this.tag(EntityTypeTags.SKELETONS)
         .add(new EntityType[]{(EntityType)TFEntities.SKELETON_DRUID.get(), (EntityType)TFEntities.LICH.get(), (EntityType)TFEntities.KNIGHT_PHANTOM.get()});
      this.tag(EntityTypeTags.ZOMBIES)
         .add(new EntityType[]{(EntityType)TFEntities.LICH_MINION.get(), (EntityType)TFEntities.LOYAL_ZOMBIE.get(), (EntityType)TFEntities.RISING_ZOMBIE.get()});
      this.tag(EntityTypeTags.ARROWS).add(new EntityType[]{(EntityType)TFEntities.ICE_ARROW.get(), (EntityType)TFEntities.SEEKER_ARROW.get()});
      this.tag(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES).add((EntityType)TFEntities.FIRE_BEETLE.get());
      this.tag(EntityTypeTags.FROG_FOOD).add((EntityType)TFEntities.MAZE_SLIME.get());
      this.tag(BOSSES)
         .add(
            new EntityType[]{
               (EntityType)TFEntities.NAGA.get(),
               (EntityType)TFEntities.LICH.get(),
               (EntityType)TFEntities.MINOSHROOM.get(),
               (EntityType)TFEntities.HYDRA.get(),
               (EntityType)TFEntities.KNIGHT_PHANTOM.get(),
               (EntityType)TFEntities.UR_GHAST.get(),
               (EntityType)TFEntities.ALPHA_YETI.get(),
               (EntityType)TFEntities.SNOW_QUEEN.get(),
               (EntityType)TFEntities.PLATEAU_BOSS.get()
            }
         );
      this.tag(EntityTypeTags.IMPACT_PROJECTILES)
         .add(
            new EntityType[]{
               (EntityType)TFEntities.NATURE_BOLT.get(),
               (EntityType)TFEntities.LICH_BOLT.get(),
               (EntityType)TFEntities.WAND_BOLT.get(),
               (EntityType)TFEntities.LICH_BOMB.get(),
               (EntityType)TFEntities.MOONWORM_SHOT.get(),
               (EntityType)TFEntities.SLIME_BLOB.get(),
               (EntityType)TFEntities.THROWN_WEP.get(),
               (EntityType)TFEntities.THROWN_ICE.get(),
               (EntityType)TFEntities.FALLING_ICE.get(),
               (EntityType)TFEntities.ICE_SNOWBALL.get(),
               (EntityType)TFEntities.CHAIN_BLOCK.get()
            }
         );
      this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)
         .add(
            new EntityType[]{
               (EntityType)TFEntities.PENGUIN.get(),
               (EntityType)TFEntities.STABLE_ICE_CORE.get(),
               (EntityType)TFEntities.UNSTABLE_ICE_CORE.get(),
               (EntityType)TFEntities.SNOW_GUARDIAN.get(),
               (EntityType)TFEntities.ICE_CRYSTAL.get()
            }
         )
         .add(
            new EntityType[]{
               (EntityType)TFEntities.RAVEN.get(),
               (EntityType)TFEntities.SQUIRREL.get(),
               (EntityType)TFEntities.DWARF_RABBIT.get(),
               (EntityType)TFEntities.TINY_BIRD.get(),
               (EntityType)TFEntities.KOBOLD.get(),
               (EntityType)TFEntities.DEATH_TOME.get(),
               (EntityType)TFEntities.MOSQUITO_SWARM.get(),
               (EntityType)TFEntities.TOWERWOOD_BORER.get()
            }
         );
      this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
         .add(
            new EntityType[]{
               (EntityType)TFEntities.PENGUIN.get(),
               (EntityType)TFEntities.STABLE_ICE_CORE.get(),
               (EntityType)TFEntities.UNSTABLE_ICE_CORE.get(),
               (EntityType)TFEntities.SNOW_GUARDIAN.get(),
               (EntityType)TFEntities.ICE_CRYSTAL.get()
            }
         )
         .add(
            new EntityType[]{
               (EntityType)TFEntities.WRAITH.get(),
               (EntityType)TFEntities.KNIGHT_PHANTOM.get(),
               (EntityType)TFEntities.WINTER_WOLF.get(),
               (EntityType)TFEntities.YETI.get()
            }
         )
         .addTag(BOSSES);
      this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
         .add(
            new EntityType[]{
               (EntityType)TFEntities.NAGA.get(),
               (EntityType)TFEntities.SQUIRREL.get(),
               (EntityType)TFEntities.WRAITH.get(),
               (EntityType)TFEntities.CARMINITE_GOLEM.get(),
               (EntityType)TFEntities.DEATH_TOME.get(),
               (EntityType)TFEntities.UR_GHAST.get(),
               (EntityType)TFEntities.CARMINITE_GHASTLING.get(),
               (EntityType)TFEntities.KNIGHT_PHANTOM.get(),
               (EntityType)TFEntities.SNOW_QUEEN.get(),
               (EntityType)TFEntities.PENGUIN.get(),
               (EntityType)TFEntities.RAVEN.get(),
               (EntityType)TFEntities.SNOW_GUARDIAN.get(),
               (EntityType)TFEntities.STABLE_ICE_CORE.get(),
               (EntityType)TFEntities.MOSQUITO_SWARM.get(),
               (EntityType)TFEntities.UNSTABLE_ICE_CORE.get(),
               (EntityType)TFEntities.ICE_CRYSTAL.get(),
               (EntityType)TFEntities.CARMINITE_GHASTGUARD.get(),
               (EntityType)TFEntities.TINY_BIRD.get()
            }
         );
      this.tag(LICH_POPPABLES)
         .addTag(EntityTypeTags.SKELETONS)
         .add(new EntityType[]{EntityType.ZOMBIE, EntityType.ENDERMAN, EntityType.SPIDER, EntityType.CREEPER, (EntityType)TFEntities.SWARM_SPIDER.get()})
         .remove(EntityTypes.BOSSES);
      this.tag(LIFEDRAIN_DROPS_NO_FLESH)
         .addTag(EntityTypeTags.SKELETONS)
         .addTag(EntityTypeTags.FROG_FOOD)
         .add(
            new EntityType[]{
               EntityType.BLAZE,
               EntityType.BREEZE,
               EntityType.IRON_GOLEM,
               EntityType.PHANTOM,
               EntityType.SHULKER,
               EntityType.SKELETON_HORSE,
               EntityType.SNOW_GOLEM,
               EntityType.VEX,
               EntityType.WITHER,
               (EntityType)TFEntities.CARMINITE_GOLEM.get(),
               (EntityType)TFEntities.DEATH_TOME.get(),
               (EntityType)TFEntities.ICE_CRYSTAL.get(),
               (EntityType)TFEntities.KNIGHT_PHANTOM.get(),
               (EntityType)TFEntities.LICH.get(),
               (EntityType)TFEntities.MOSQUITO_SWARM.get(),
               (EntityType)TFEntities.SNOW_GUARDIAN.get(),
               (EntityType)TFEntities.STABLE_ICE_CORE.get(),
               (EntityType)TFEntities.UNSTABLE_ICE_CORE.get(),
               (EntityType)TFEntities.WRAITH.get()
            }
         );
      this.tag(RIDES_OBSTRUCT_SNATCHING)
         .add(new EntityType[]{(EntityType)TFEntities.PINCH_BEETLE.get(), (EntityType)TFEntities.YETI.get(), (EntityType)TFEntities.ALPHA_YETI.get()});
      this.tag(DONT_KILL_BUGS).add((EntityType)TFEntities.MOONWORM_SHOT.get());
      this.tag(SORTABLE_ENTITIES)
         .add(
            new EntityType[]{
               EntityType.CHEST_MINECART, EntityType.HOPPER_MINECART, EntityType.LLAMA, EntityType.TRADER_LLAMA, EntityType.DONKEY, EntityType.MULE
            }
         );
      this.tag(MULTIPLAYER_INCLUSIVE_ENTITIES)
         .add(
            new EntityType[]{
               (EntityType)TFEntities.NAGA.get(),
               (EntityType)TFEntities.LICH.get(),
               (EntityType)TFEntities.MINOSHROOM.get(),
               (EntityType)TFEntities.HYDRA.get(),
               (EntityType)TFEntities.UR_GHAST.get(),
               (EntityType)TFEntities.ALPHA_YETI.get(),
               (EntityType)TFEntities.SNOW_QUEEN.get(),
               (EntityType)TFEntities.PLATEAU_BOSS.get()
            }
         );
      this.tag(EntityTypes.BOSSES).addTag(BOSSES);
      this.tag(EntityTypeTags.ARTHROPOD)
         .add(
            new EntityType[]{
               (EntityType)TFEntities.CARMINITE_BROODLING.get(),
               (EntityType)TFEntities.FIRE_BEETLE.get(),
               (EntityType)TFEntities.HEDGE_SPIDER.get(),
               (EntityType)TFEntities.HELMET_CRAB.get(),
               (EntityType)TFEntities.KING_SPIDER.get(),
               (EntityType)TFEntities.PINCH_BEETLE.get(),
               (EntityType)TFEntities.SLIME_BEETLE.get(),
               (EntityType)TFEntities.SWARM_SPIDER.get(),
               (EntityType)TFEntities.TOWERWOOD_BORER.get()
            }
         );
      this.tag(EntityTypeTags.UNDEAD).add((EntityType)TFEntities.WRAITH.get());
      this.tag(EntityTypeTags.IMMUNE_TO_OOZING).add((EntityType)TFEntities.MAZE_SLIME.get());
      this.tag(EntityTypeTags.IMMUNE_TO_INFESTED).add((EntityType)TFEntities.TOWERWOOD_BORER.get());
      this.tag(EntityTypeTags.REDIRECTABLE_PROJECTILE).add(new EntityType[]{(EntityType)TFEntities.HYDRA_MORTAR.get(), (EntityType)TFEntities.LICH_BOLT.get()});
      this.tag(LICH_DEFLECTS_PHASE_2)
         .add(new EntityType[]{(EntityType)TFEntities.WAND_BOLT.get(), (EntityType)TFEntities.LICH_BOLT.get(), (EntityType)TFEntities.LICH_BOMB.get()});
   }

   private static TagKey<EntityType<?>> create(ResourceLocation rl) {
      return TagKey.create(Registries.ENTITY_TYPE, rl);
   }

   public String getName() {
      return "Twilight Forest Entity Tags";
   }
}
