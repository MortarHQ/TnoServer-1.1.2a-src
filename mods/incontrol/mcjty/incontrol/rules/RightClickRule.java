package mcjty.incontrol.rules;

import com.google.gson.JsonElement;
import java.util.Set;
import java.util.function.Consumer;
import mcjty.incontrol.InControl;
import mcjty.incontrol.compat.ModRuleCompatibilityLayer;
import mcjty.incontrol.data.PhaseTools;
import mcjty.incontrol.rules.support.GenericRuleEvaluator;
import mcjty.incontrol.rules.support.RuleKeys;
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
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck.Result;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public class RightClickRule extends RuleBase<RuleBase.EventGetter> {
   private static final GenericAttributeMapFactory FACTORY = new GenericAttributeMapFactory();
   public static final IEventQuery<RightClickBlock> EVENT_QUERY = new IEventQuery<RightClickBlock>() {
      public Level getWorld(RightClickBlock o) {
         return o.getLevel();
      }

      public BlockPos getPos(RightClickBlock o) {
         return o.getPos();
      }

      public BlockPos getValidBlockPos(RightClickBlock o) {
         return o.getPos();
      }

      public int getY(RightClickBlock o) {
         return o.getPos().getY();
      }

      public Entity getEntity(RightClickBlock o) {
         return o.getEntity();
      }

      public DamageSource getSource(RightClickBlock o) {
         return null;
      }

      public Entity getAttacker(RightClickBlock o) {
         return null;
      }

      public Player getPlayer(RightClickBlock o) {
         return o.getEntity();
      }

      public ItemStack getItem(RightClickBlock o) {
         return o.getItemStack();
      }
   };
   private Result result;
   private final GenericRuleEvaluator ruleEvaluator;

   private RightClickRule(AttributeMap map, Set<String> phases, int index) {
      super(phases, index);
      this.ruleEvaluator = new GenericRuleEvaluator(map);
      this.addActions(map, new ModRuleCompatibilityLayer());
   }

   @Override
   protected void addActions(AttributeMap map, IModRuleCompatibilityLayer layer) {
      super.addActions(map, layer);
      if (map.has(RuleKeys.ACTION_RESULT)) {
         String br = map.get(RuleKeys.ACTION_RESULT);
         if ("default".equals(br) || br.startsWith("def")) {
            this.result = Result.DEFAULT;
         } else if (!"allow".equals(br) && !"true".equals(br)) {
            this.result = Result.FAIL;
         } else {
            this.result = Result.SUCCEED;
         }
      } else {
         this.result = Result.DEFAULT;
      }
   }

   public boolean match(RightClickBlock event) {
      return this.ruleEvaluator.match(event, EVENT_QUERY);
   }

   public void action(final RightClickBlock event) {
      RuleBase.EventGetter getter = new RuleBase.EventGetter() {
         @Override
         public LivingEntity getEntityLiving() {
            return event.getEntity();
         }

         @Override
         public Player getPlayer() {
            return event.getEntity();
         }

         public Level getWorld() {
            return event.getLevel();
         }

         @Override
         public BlockPos getPosition() {
            return event.getPos();
         }
      };

      for (Consumer<RuleBase.EventGetter> action : this.actions) {
         action.accept(getter);
      }
   }

   public Result getResult() {
      return this.result;
   }

   public static RightClickRule parse(JsonElement element, int index) {
      if (element == null) {
         return null;
      }

      AttributeMap map = null;

      try {
         map = FACTORY.parse(element, "rightclicks.json");
      } catch (Exception e) {
         InControl.setup.getLogger().log(org.apache.logging.log4j.Level.ERROR, e);
         return null;
      }

      return new RightClickRule(map, PhaseTools.getPhases(element), index);
   }

   static {
      FACTORY.attribute(Attribute.createMulti(RuleKeys.PHASE))
         .attribute(Attribute.create(RuleKeys.NUMBER))
         .attribute(Attribute.create(RuleKeys.MINTIME))
         .attribute(Attribute.create(RuleKeys.MAXTIME))
         .attribute(Attribute.create(RuleKeys.MINLIGHT))
         .attribute(Attribute.create(RuleKeys.MAXLIGHT))
         .attribute(Attribute.create(RuleKeys.MINHEIGHT))
         .attribute(Attribute.create(RuleKeys.MAXHEIGHT))
         .attribute(Attribute.create(RuleKeys.MINDIFFICULTY))
         .attribute(Attribute.create(RuleKeys.MAXDIFFICULTY))
         .attribute(Attribute.create(RuleKeys.MINSPAWNDIST))
         .attribute(Attribute.create(RuleKeys.MAXSPAWNDIST))
         .attribute(Attribute.create(RuleKeys.RANDOM))
         .attribute(Attribute.create(RuleKeys.CAVE))
         .attribute(Attribute.create(RuleKeys.SEESKY))
         .attribute(Attribute.create(RuleKeys.WEATHER))
         .attribute(Attribute.createMulti(RuleKeys.BIOMETAGS))
         .attribute(Attribute.create(RuleKeys.DIFFICULTY))
         .attribute(Attribute.create(RuleKeys.HASSTRUCTURE))
         .attribute(Attribute.createMulti(RuleKeys.STRUCTURE))
         .attribute(Attribute.createMulti(RuleKeys.STRUCTURETAGS))
         .attribute(Attribute.createMulti(RuleKeys.MOD))
         .attribute(Attribute.create(RuleKeys.GAMESTAGE))
         .attribute(Attribute.create(RuleKeys.WINTER))
         .attribute(Attribute.create(RuleKeys.SUMMER))
         .attribute(Attribute.create(RuleKeys.SPRING))
         .attribute(Attribute.create(RuleKeys.AUTUMN))
         .attribute(Attribute.create(RuleKeys.INBUILDING))
         .attribute(Attribute.create(RuleKeys.INMULTIBUILDING))
         .attribute(Attribute.createMulti(RuleKeys.BUILDING))
         .attribute(Attribute.createMulti(RuleKeys.MULTIBUILDING))
         .attribute(Attribute.create(RuleKeys.INCITY))
         .attribute(Attribute.create(RuleKeys.INSTREET))
         .attribute(Attribute.create(RuleKeys.INSPHERE))
         .attribute(Attribute.createMulti(RuleKeys.AMULET))
         .attribute(Attribute.createMulti(RuleKeys.RING))
         .attribute(Attribute.createMulti(RuleKeys.BELT))
         .attribute(Attribute.createMulti(RuleKeys.TRINKET))
         .attribute(Attribute.createMulti(RuleKeys.HEAD))
         .attribute(Attribute.createMulti(RuleKeys.BODY))
         .attribute(Attribute.createMulti(RuleKeys.CHARM))
         .attribute(Attribute.create(RuleKeys.AREA))
         .attribute(Attribute.createMulti(RuleKeys.BLOCK))
         .attribute(Attribute.create(RuleKeys.BLOCKOFFSET))
         .attribute(Attribute.createMulti(RuleKeys.BLOCKTEST))
         .attribute(Attribute.createMulti(RuleKeys.HELMET))
         .attribute(Attribute.createMulti(RuleKeys.CHESTPLATE))
         .attribute(Attribute.createMulti(RuleKeys.LEGGINGS))
         .attribute(Attribute.createMulti(RuleKeys.BOOTS))
         .attribute(Attribute.createMulti(RuleKeys.LACKHELMET))
         .attribute(Attribute.createMulti(RuleKeys.LACKCHESTPLATE))
         .attribute(Attribute.createMulti(RuleKeys.LACKLEGGINGS))
         .attribute(Attribute.createMulti(RuleKeys.LACKBOOTS))
         .attribute(Attribute.createMulti(RuleKeys.HELDITEM))
         .attribute(Attribute.createMulti(RuleKeys.PLAYER_HELDITEM))
         .attribute(Attribute.createMulti(RuleKeys.LACKHELDITEM))
         .attribute(Attribute.createMulti(RuleKeys.OFFHANDITEM))
         .attribute(Attribute.createMulti(RuleKeys.LACKOFFHANDITEM))
         .attribute(Attribute.createMulti(RuleKeys.BOTHHANDSITEM))
         .attribute(Attribute.createMulti(RuleKeys.BIOME))
         .attribute(Attribute.createMulti(RuleKeys.DIMENSION))
         .attribute(Attribute.createMulti(RuleKeys.DIMENSION_MOD))
         .attribute(Attribute.create(RuleKeys.ACTION_CUSTOMEVENT))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_COMMAND))
         .attribute(Attribute.create(RuleKeys.ACTION_ADDSTAGE))
         .attribute(Attribute.create(RuleKeys.ACTION_REMOVESTAGE))
         .attribute(Attribute.create(RuleKeys.ACTION_MESSAGE))
         .attribute(Attribute.create(RuleKeys.ACTION_FIRE))
         .attribute(Attribute.create(RuleKeys.ACTION_EXPLOSION))
         .attribute(Attribute.create(RuleKeys.ACTION_CLEAR))
         .attribute(Attribute.create(RuleKeys.ACTION_DAMAGE))
         .attribute(Attribute.create(RuleKeys.ACTION_SETBLOCK))
         .attribute(Attribute.create(RuleKeys.ACTION_SETHELDITEM))
         .attribute(Attribute.create(RuleKeys.ACTION_SETHELDAMOUNT))
         .attribute(Attribute.create(RuleKeys.ACTION_RESULT))
         .attribute(Attribute.create(RuleKeys.ACTION_SETSTATE))
         .attribute(Attribute.create(RuleKeys.ACTION_SETPSTATE))
         .attribute(Attribute.create(RuleKeys.ACTION_SETPHASE))
         .attribute(Attribute.create(RuleKeys.ACTION_CLEARPHASE))
         .attribute(Attribute.create(RuleKeys.ACTION_TOGGLEPHASE))
         .attribute(Attribute.create(RuleKeys.ACTION_CHANGENUMBER))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_POTION))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_POTION_NOPARTICLES))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_GIVE))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_DROP));
   }
}
