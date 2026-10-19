package mcjty.incontrol.rules;

import com.google.gson.JsonElement;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import mcjty.incontrol.ErrorHandler;
import mcjty.incontrol.compat.ModRuleCompatibilityLayer;
import mcjty.incontrol.data.PhaseTools;
import mcjty.incontrol.rules.support.GenericRuleEvaluator;
import mcjty.incontrol.rules.support.ICResult;
import mcjty.incontrol.rules.support.RuleKeys;
import mcjty.incontrol.rules.support.SpawnWhen;
import mcjty.incontrol.setup.Config;
import mcjty.incontrol.tools.rules.IEventQuery;
import mcjty.incontrol.tools.rules.IModRuleCompatibilityLayer;
import mcjty.incontrol.tools.rules.RuleBase;
import mcjty.incontrol.tools.typed.Attribute;
import mcjty.incontrol.tools.typed.AttributeMap;
import mcjty.incontrol.tools.typed.GenericAttributeMapFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.MobDespawnEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.PositionCheck;

public class SpawnRule extends RuleBase<RuleBase.EventGetter> {
   public static final IEventQuery<FinalizeSpawnEvent> EVENT_QUERY = new IEventQuery<FinalizeSpawnEvent>() {
      public LevelAccessor getWorld(FinalizeSpawnEvent o) {
         return o.getLevel();
      }

      public BlockPos getPos(FinalizeSpawnEvent o) {
         return new BlockPos((int)o.getX(), (int)o.getY(), (int)o.getZ());
      }

      public BlockPos getValidBlockPos(FinalizeSpawnEvent o) {
         return new BlockPos((int)o.getX(), (int)(o.getY() - 1.0), (int)o.getZ());
      }

      public int getY(FinalizeSpawnEvent o) {
         return (int)o.getY();
      }

      public Entity getEntity(FinalizeSpawnEvent o) {
         return o.getEntity();
      }

      public DamageSource getSource(FinalizeSpawnEvent o) {
         return null;
      }

      public Entity getAttacker(FinalizeSpawnEvent o) {
         return null;
      }

      public Player getPlayer(FinalizeSpawnEvent o) {
         return SpawnRule.getClosestPlayer(o.getLevel(), new BlockPos((int)o.getX(), (int)o.getY(), (int)o.getZ()));
      }

      public ItemStack getItem(FinalizeSpawnEvent o) {
         return ItemStack.EMPTY;
      }
   };
   public static final IEventQuery<PositionCheck> EVENT_QUERY_POSITION = new IEventQuery<PositionCheck>() {
      public LevelAccessor getWorld(PositionCheck o) {
         return o.getLevel();
      }

      public BlockPos getPos(PositionCheck o) {
         return new BlockPos((int)o.getX(), (int)o.getY(), (int)o.getZ());
      }

      public BlockPos getValidBlockPos(PositionCheck o) {
         return new BlockPos((int)o.getX(), (int)(o.getY() - 1.0), (int)o.getZ());
      }

      public int getY(PositionCheck o) {
         return (int)o.getY();
      }

      public Entity getEntity(PositionCheck o) {
         return o.getEntity();
      }

      public DamageSource getSource(PositionCheck o) {
         return null;
      }

      public Entity getAttacker(PositionCheck o) {
         return null;
      }

      public Player getPlayer(PositionCheck o) {
         return SpawnRule.getClosestPlayer(o.getLevel(), new BlockPos((int)o.getX(), (int)o.getY(), (int)o.getZ()));
      }

      public ItemStack getItem(PositionCheck o) {
         return ItemStack.EMPTY;
      }
   };
   public static final IEventQuery<MobDespawnEvent> EVENT_QUERY_DESPAWN = new IEventQuery<MobDespawnEvent>() {
      public LevelAccessor getWorld(MobDespawnEvent o) {
         return o.getLevel();
      }

      public BlockPos getPos(MobDespawnEvent o) {
         return new BlockPos((int)o.getX(), (int)o.getY(), (int)o.getZ());
      }

      public BlockPos getValidBlockPos(MobDespawnEvent o) {
         return new BlockPos((int)o.getX(), (int)(o.getY() - 1.0), (int)o.getZ());
      }

      public int getY(MobDespawnEvent o) {
         return (int)o.getY();
      }

      public Entity getEntity(MobDespawnEvent o) {
         return o.getEntity();
      }

      public DamageSource getSource(MobDespawnEvent o) {
         return null;
      }

      public Entity getAttacker(MobDespawnEvent o) {
         return null;
      }

      public Player getPlayer(MobDespawnEvent o) {
         return SpawnRule.getClosestPlayer(o.getLevel(), new BlockPos((int)o.getX(), (int)o.getY(), (int)o.getZ()));
      }

      public ItemStack getItem(MobDespawnEvent o) {
         return ItemStack.EMPTY;
      }
   };
   public static final IEventQuery<EntityJoinLevelEvent> EVENT_QUERY_JOIN = new IEventQuery<EntityJoinLevelEvent>() {
      public Level getWorld(EntityJoinLevelEvent o) {
         return o.getLevel();
      }

      public BlockPos getPos(EntityJoinLevelEvent o) {
         return o.getEntity().blockPosition();
      }

      public BlockPos getValidBlockPos(EntityJoinLevelEvent o) {
         return o.getEntity().blockPosition().below();
      }

      public int getY(EntityJoinLevelEvent o) {
         return o.getEntity().blockPosition().getY();
      }

      public Entity getEntity(EntityJoinLevelEvent o) {
         return o.getEntity();
      }

      public DamageSource getSource(EntityJoinLevelEvent o) {
         return null;
      }

      public Entity getAttacker(EntityJoinLevelEvent o) {
         return null;
      }

      public Player getPlayer(EntityJoinLevelEvent o) {
         return SpawnRule.getClosestPlayer(o.getLevel(), o.getEntity().blockPosition());
      }

      public ItemStack getItem(EntityJoinLevelEvent o) {
         return ItemStack.EMPTY;
      }
   };
   private static final GenericAttributeMapFactory FACTORY = new GenericAttributeMapFactory();
   private final SpawnWhen when;
   private final GenericRuleEvaluator ruleEvaluator;
   private ICResult result = null;
   private boolean doContinue = false;

   private static Player getClosestPlayer(LevelAccessor world, BlockPos pos) {
      return world.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), ((Integer)Config.PERPLAYER_RADIUS.get()).intValue(), false);
   }

   private SpawnRule(AttributeMap map, SpawnWhen when, Set<String> phases, int index) {
      super(phases, index);
      this.when = when;
      this.ruleEvaluator = new GenericRuleEvaluator(map);
      this.addActions(map, new ModRuleCompatibilityLayer());
      if (!map.isEmpty()) {
         StringBuffer buffer = new StringBuffer();
         map.getKeys().forEach(k -> buffer.append(k).append(' '));
         ErrorHandler.error("Invalid keywords in spawn rule: " + buffer);
      }
   }

   public static SpawnRule parse(JsonElement element, int index) {
      if (element == null) {
         return null;
      }

      AttributeMap map = FACTORY.parse(element, "spawn.json");
      String whenS = element.getAsJsonObject().has("when") ? element.getAsJsonObject().get("when").getAsString() : SpawnWhen.POSITION.name();
      SpawnWhen when = SpawnWhen.getByName(whenS);
      if (when == null) {
         ErrorHandler.error("Invalid spawn rule 'when' value '" + whenS + "'!. Should be one of " + Arrays.toString(SpawnWhen.values()));
      }

      return new SpawnRule(map, when, PhaseTools.getPhases(element), index);
   }

   @Override
   protected void addActions(AttributeMap map, IModRuleCompatibilityLayer layer) {
      super.addActions(map, layer);
      map.consumeOrElse(RuleKeys.ACTION_RESULT, br -> {
         if ("default".equals(br) || br.startsWith("def")) {
            this.result = ICResult.DEFAULT;
         } else if ("allow".equals(br) || "true".equals(br)) {
            this.result = ICResult.ALLOW;
         } else if ("deny_with_actions".equals(br)) {
            this.result = ICResult.DENY_WITH_ACTIONS;
         } else {
            this.result = ICResult.DENY;
         }
      }, () -> this.result = ICResult.DEFAULT);
      map.consume(RuleKeys.ACTION_CONTINUE, v -> this.doContinue = v);
   }

   public boolean match(FinalizeSpawnEvent event) {
      return this.ruleEvaluator.match(event, EVENT_QUERY);
   }

   public boolean match(PositionCheck event) {
      return this.ruleEvaluator.match(event, EVENT_QUERY_POSITION);
   }

   public boolean match(MobDespawnEvent event) {
      return this.ruleEvaluator.match(event, EVENT_QUERY_DESPAWN);
   }

   public boolean match(EntityJoinLevelEvent event) {
      return this.ruleEvaluator.match(event, EVENT_QUERY_JOIN);
   }

   public void action(final FinalizeSpawnEvent event) {
      RuleBase.EventGetter getter = new RuleBase.EventGetter() {
         @Override
         public LivingEntity getEntityLiving() {
            return event.getEntity();
         }

         @Override
         public Player getPlayer() {
            return null;
         }

         @Override
         public LevelAccessor getWorld() {
            return event.getLevel();
         }

         @Override
         public BlockPos getPosition() {
            return event.getEntity().blockPosition();
         }
      };

      for (Consumer<RuleBase.EventGetter> action : this.actions) {
         action.accept(getter);
      }
   }

   public void action(final PositionCheck event) {
      RuleBase.EventGetter getter = new RuleBase.EventGetter() {
         @Override
         public LivingEntity getEntityLiving() {
            return event.getEntity();
         }

         @Override
         public Player getPlayer() {
            return null;
         }

         @Override
         public LevelAccessor getWorld() {
            return event.getLevel();
         }

         @Override
         public BlockPos getPosition() {
            return event.getEntity().blockPosition();
         }
      };

      for (Consumer<RuleBase.EventGetter> action : this.actions) {
         action.accept(getter);
      }
   }

   public void action(final MobDespawnEvent event) {
      RuleBase.EventGetter getter = new RuleBase.EventGetter() {
         @Override
         public LivingEntity getEntityLiving() {
            return event.getEntity();
         }

         @Override
         public Player getPlayer() {
            return null;
         }

         @Override
         public LevelAccessor getWorld() {
            return event.getLevel();
         }

         @Override
         public BlockPos getPosition() {
            return event.getEntity().blockPosition();
         }
      };

      for (Consumer<RuleBase.EventGetter> action : this.actions) {
         action.accept(getter);
      }
   }

   public void action(final EntityJoinLevelEvent event) {
      RuleBase.EventGetter getter = new RuleBase.EventGetter() {
         @Override
         public LivingEntity getEntityLiving() {
            return event.getEntity() instanceof LivingEntity ? (LivingEntity)event.getEntity() : null;
         }

         @Override
         public Player getPlayer() {
            return null;
         }

         public Level getWorld() {
            return event.getLevel();
         }

         @Override
         public BlockPos getPosition() {
            return event.getEntity() != null ? event.getEntity().blockPosition() : null;
         }
      };

      for (Consumer<RuleBase.EventGetter> action : this.actions) {
         action.accept(getter);
      }
   }

   @Nonnull
   public ICResult getResult() {
      return this.result;
   }

   public boolean isDoContinue() {
      return this.doContinue;
   }

   public SpawnWhen getWhen() {
      return this.when;
   }

   static {
      FACTORY.attribute(Attribute.create(RuleKeys.WHEN))
         .attribute(Attribute.createMulti(RuleKeys.PHASE))
         .attribute(Attribute.create(RuleKeys.NUMBER))
         .attribute(Attribute.create(RuleKeys.TIME))
         .attribute(Attribute.create(RuleKeys.MINTIME))
         .attribute(Attribute.create(RuleKeys.MAXTIME))
         .attribute(Attribute.create(RuleKeys.DAYCOUNT))
         .attribute(Attribute.create(RuleKeys.MINDAYCOUNT))
         .attribute(Attribute.create(RuleKeys.MAXDAYCOUNT))
         .attribute(Attribute.create(RuleKeys.MINCOUNT))
         .attribute(Attribute.create(RuleKeys.MAXCOUNT))
         .attribute(Attribute.create(RuleKeys.LIGHT))
         .attribute(Attribute.create(RuleKeys.MINLIGHT))
         .attribute(Attribute.create(RuleKeys.MAXLIGHT))
         .attribute(Attribute.create(RuleKeys.MINLIGHT_FULL))
         .attribute(Attribute.create(RuleKeys.MAXLIGHT_FULL))
         .attribute(Attribute.create(RuleKeys.MINLIGHT_SKY))
         .attribute(Attribute.create(RuleKeys.MAXLIGHT_SKY))
         .attribute(Attribute.create(RuleKeys.HEIGHT))
         .attribute(Attribute.create(RuleKeys.MINHEIGHT))
         .attribute(Attribute.create(RuleKeys.MAXHEIGHT))
         .attribute(Attribute.create(RuleKeys.MINDIFFICULTY))
         .attribute(Attribute.create(RuleKeys.MAXDIFFICULTY))
         .attribute(Attribute.create(RuleKeys.MINDIST))
         .attribute(Attribute.create(RuleKeys.MAXDIST))
         .attribute(Attribute.create(RuleKeys.MINSPAWNDIST))
         .attribute(Attribute.create(RuleKeys.MAXSPAWNDIST))
         .attribute(Attribute.create(RuleKeys.RANDOM))
         .attribute(Attribute.create(RuleKeys.CANSPAWNHERE))
         .attribute(Attribute.create(RuleKeys.NOTCOLLIDING))
         .attribute(Attribute.create(RuleKeys.SPAWNER))
         .attribute(Attribute.createMulti(RuleKeys.SPAWNTYPE))
         .attribute(Attribute.create(RuleKeys.INCONTROL))
         .attribute(Attribute.create(RuleKeys.EVENTSPAWN))
         .attribute(Attribute.create(RuleKeys.INBUILDING))
         .attribute(Attribute.create(RuleKeys.INMULTIBUILDING))
         .attribute(Attribute.createMulti(RuleKeys.BUILDING))
         .attribute(Attribute.createMulti(RuleKeys.MULTIBUILDING))
         .attribute(Attribute.create(RuleKeys.INCITY))
         .attribute(Attribute.create(RuleKeys.INSTREET))
         .attribute(Attribute.create(RuleKeys.INSPHERE))
         .attribute(Attribute.create(RuleKeys.GAMESTAGE))
         .attribute(Attribute.create(RuleKeys.PASSIVE))
         .attribute(Attribute.create(RuleKeys.HOSTILE))
         .attribute(Attribute.create(RuleKeys.BABY))
         .attribute(Attribute.create(RuleKeys.CAVE))
         .attribute(Attribute.create(RuleKeys.SEESKY))
         .attribute(Attribute.create(RuleKeys.SLIME))
         .attribute(Attribute.create(RuleKeys.WEATHER))
         .attribute(Attribute.createMulti(RuleKeys.BIOMETAGS))
         .attribute(Attribute.create(RuleKeys.DIFFICULTY))
         .attribute(Attribute.create(RuleKeys.HASSTRUCTURE))
         .attribute(Attribute.createMulti(RuleKeys.STRUCTURE))
         .attribute(Attribute.createMulti(RuleKeys.STRUCTURETAGS))
         .attribute(Attribute.create(RuleKeys.WINTER))
         .attribute(Attribute.create(RuleKeys.SUMMER))
         .attribute(Attribute.create(RuleKeys.SPRING))
         .attribute(Attribute.create(RuleKeys.AUTUMN))
         .attribute(Attribute.createMulti(RuleKeys.MOB))
         .attribute(Attribute.createMulti(RuleKeys.NPC))
         .attribute(Attribute.createMulti(RuleKeys.MOD))
         .attribute(Attribute.create(RuleKeys.AREA))
         .attribute(Attribute.createMulti(RuleKeys.BLOCK))
         .attribute(Attribute.create(RuleKeys.BLOCKOFFSET))
         .attribute(Attribute.createMulti(RuleKeys.BLOCKTEST))
         .attribute(Attribute.createMulti(RuleKeys.BIOME))
         .attribute(Attribute.createMulti(RuleKeys.DIMENSION))
         .attribute(Attribute.createMulti(RuleKeys.DIMENSION_MOD))
         .attribute(Attribute.create(RuleKeys.STATE))
         .attribute(Attribute.createMulti(RuleKeys.SCOREBOARDTAGS_ALL))
         .attribute(Attribute.createMulti(RuleKeys.SCOREBOARDTAGS_ANY))
         .attribute(Attribute.createMulti(RuleKeys.HELMET))
         .attribute(Attribute.createMulti(RuleKeys.CHESTPLATE))
         .attribute(Attribute.createMulti(RuleKeys.LEGGINGS))
         .attribute(Attribute.createMulti(RuleKeys.BOOTS))
         .attribute(Attribute.createMulti(RuleKeys.AMULET))
         .attribute(Attribute.createMulti(RuleKeys.RING))
         .attribute(Attribute.createMulti(RuleKeys.BELT))
         .attribute(Attribute.createMulti(RuleKeys.TRINKET))
         .attribute(Attribute.createMulti(RuleKeys.HEAD))
         .attribute(Attribute.createMulti(RuleKeys.BODY))
         .attribute(Attribute.createMulti(RuleKeys.CHARM))
         .attribute(Attribute.createMulti(RuleKeys.PLAYER_HELDITEM))
         .attribute(Attribute.createMulti(RuleKeys.OFFHANDITEM))
         .attribute(Attribute.createMulti(RuleKeys.BOTHHANDSITEM))
         .attribute(Attribute.create(RuleKeys.ACTION_CUSTOMEVENT))
         .attribute(Attribute.create(RuleKeys.ACTION_CONTINUE))
         .attribute(Attribute.create(RuleKeys.ACTION_RESULT))
         .attribute(Attribute.create(RuleKeys.ACTION_MESSAGE))
         .attribute(Attribute.create(RuleKeys.ACTION_AI))
         .attribute(Attribute.create(RuleKeys.ACTION_ADDSCOREBOARDTAGS))
         .attribute(Attribute.create(RuleKeys.ACTION_HEALTHSET))
         .attribute(Attribute.create(RuleKeys.ACTION_HEALTHMULTIPLY))
         .attribute(Attribute.create(RuleKeys.ACTION_HEALTHADD))
         .attribute(Attribute.create(RuleKeys.ACTION_SPEEDSET))
         .attribute(Attribute.create(RuleKeys.ACTION_SPEEDMULTIPLY))
         .attribute(Attribute.create(RuleKeys.ACTION_SPEEDADD))
         .attribute(Attribute.create(RuleKeys.ACTION_DAMAGESET))
         .attribute(Attribute.create(RuleKeys.ACTION_DAMAGEMULTIPLY))
         .attribute(Attribute.create(RuleKeys.ACTION_DAMAGEADD))
         .attribute(Attribute.create(RuleKeys.ACTION_ARMORSET))
         .attribute(Attribute.create(RuleKeys.ACTION_ARMORMULTIPLY))
         .attribute(Attribute.create(RuleKeys.ACTION_ARMORADD))
         .attribute(Attribute.create(RuleKeys.ACTION_ARMORTOUGHNESSSET))
         .attribute(Attribute.create(RuleKeys.ACTION_ARMORTOUGHNESSMULTIPLY))
         .attribute(Attribute.create(RuleKeys.ACTION_ARMORTOUGHNESSADD))
         .attribute(Attribute.create(RuleKeys.ACTION_ATTACKSPEEDSET))
         .attribute(Attribute.create(RuleKeys.ACTION_ATTACKSPEEDMULTIPLY))
         .attribute(Attribute.create(RuleKeys.ACTION_ATTACKSPEEDADD))
         .attribute(Attribute.create(RuleKeys.ACTION_FOLLOWRANGESET))
         .attribute(Attribute.create(RuleKeys.ACTION_FOLLOWRANGEMULTIPLY))
         .attribute(Attribute.create(RuleKeys.ACTION_FOLLOWRANGEADD))
         .attribute(Attribute.create(RuleKeys.ACTION_KNOCKBACKSET))
         .attribute(Attribute.create(RuleKeys.ACTION_KNOCKBACKMULTIPLY))
         .attribute(Attribute.create(RuleKeys.ACTION_KNOCKBACKADD))
         .attribute(Attribute.create(RuleKeys.ACTION_KNOCKBACKRESISTANCESET))
         .attribute(Attribute.create(RuleKeys.ACTION_KNOCKBACKRESISTANCEMULTIPLY))
         .attribute(Attribute.create(RuleKeys.ACTION_KNOCKBACKRESISTANCEADD))
         .attribute(Attribute.create(RuleKeys.ACTION_SIZEMULTIPLY))
         .attribute(Attribute.create(RuleKeys.ACTION_SIZEADD))
         .attribute(Attribute.create(RuleKeys.ACTION_ANGRY))
         .attribute(Attribute.create(RuleKeys.ACTION_MOBNBT))
         .attribute(Attribute.create(RuleKeys.ACTION_CUSTOMNAME))
         .attribute(Attribute.create(RuleKeys.ACTION_SETPHASE))
         .attribute(Attribute.create(RuleKeys.ACTION_CLEARPHASE))
         .attribute(Attribute.create(RuleKeys.ACTION_TOGGLEPHASE))
         .attribute(Attribute.create(RuleKeys.ACTION_CHANGENUMBER))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_HELDITEM))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_ARMORBOOTS))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_ARMORLEGS))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_ARMORCHEST))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_ARMORHELMET))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_POTION))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_POTION_NOPARTICLES))
         .attribute(Attribute.create(RuleKeys.ACTION_NODESPAWN));
   }
}
