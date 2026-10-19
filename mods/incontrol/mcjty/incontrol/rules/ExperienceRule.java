package mcjty.incontrol.rules;

import com.google.gson.JsonElement;
import java.util.Set;
import mcjty.incontrol.ErrorHandler;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck.Result;

public class ExperienceRule extends RuleBase<RuleBase.EventGetter> {
   public static final IEventQuery<LivingExperienceDropEvent> EVENT_QUERY = new IEventQuery<LivingExperienceDropEvent>() {
      public Level getWorld(LivingExperienceDropEvent o) {
         return o.getEntity().getCommandSenderWorld();
      }

      public BlockPos getPos(LivingExperienceDropEvent o) {
         return o.getEntity().blockPosition();
      }

      public BlockPos getValidBlockPos(LivingExperienceDropEvent o) {
         return o.getEntity().blockPosition().below();
      }

      public int getY(LivingExperienceDropEvent o) {
         return o.getEntity().blockPosition().getY();
      }

      public Entity getEntity(LivingExperienceDropEvent o) {
         return o.getEntity();
      }

      public DamageSource getSource(LivingExperienceDropEvent o) {
         return null;
      }

      public Entity getAttacker(LivingExperienceDropEvent o) {
         return o.getAttackingPlayer();
      }

      public Player getPlayer(LivingExperienceDropEvent o) {
         return o.getAttackingPlayer();
      }

      public ItemStack getItem(LivingExperienceDropEvent o) {
         return ItemStack.EMPTY;
      }
   };
   private static final GenericAttributeMapFactory FACTORY = new GenericAttributeMapFactory();
   private final GenericRuleEvaluator ruleEvaluator;
   private Result result;
   private Integer xp = null;
   private float multxp = 1.0F;
   private float addxp = 0.0F;

   private ExperienceRule(AttributeMap map, Set<String> phases, int index) {
      super(phases, index);
      this.ruleEvaluator = new GenericRuleEvaluator(map);
      this.addActions(map, new ModRuleCompatibilityLayer());
   }

   public static ExperienceRule parse(JsonElement element, int index) {
      if (element == null) {
         return null;
      }

      AttributeMap map = FACTORY.parse(element, "experience.json");
      return new ExperienceRule(map, PhaseTools.getPhases(element), index);
   }

   public int modifyXp(int xpIn) {
      if (this.xp != null) {
         xpIn = this.xp;
      }

      return (int)(xpIn * this.multxp + this.addxp);
   }

   @Override
   protected void addActions(AttributeMap map, IModRuleCompatibilityLayer layer) {
      super.addActions(map, layer);
      map.consumeOrElse(RuleKeys.ACTION_RESULT, br -> {
         if ("default".equals(br) || br.startsWith("def")) {
            this.result = Result.DEFAULT;
         } else if (!"allow".equals(br) && !"true".equals(br)) {
            this.result = Result.FAIL;
         } else {
            this.result = Result.SUCCEED;
         }
      }, () -> this.result = Result.DEFAULT);
      map.consume(RuleKeys.ACTION_SETXP, v -> this.xp = v);
      map.consume(RuleKeys.ACTION_ADDXP, v -> this.addxp = v);
      map.consume(RuleKeys.ACTION_MULTXP, v -> this.multxp = v);
      if (!map.isEmpty()) {
         StringBuffer buffer = new StringBuffer();
         map.getKeys().forEach(k -> buffer.append(k).append(' '));
         ErrorHandler.error("Invalid keywords in experience rule: " + buffer);
      }
   }

   public boolean match(LivingExperienceDropEvent event) {
      return this.ruleEvaluator.match(event, EVENT_QUERY);
   }

   public Result getResult() {
      return this.result;
   }

   static {
      FACTORY.attribute(Attribute.createMulti(RuleKeys.PHASE))
         .attribute(Attribute.create(RuleKeys.NUMBER))
         .attribute(Attribute.create(RuleKeys.TIME))
         .attribute(Attribute.create(RuleKeys.MINTIME))
         .attribute(Attribute.create(RuleKeys.MAXTIME))
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
         .attribute(Attribute.create(RuleKeys.MINSPAWNDIST))
         .attribute(Attribute.create(RuleKeys.MAXSPAWNDIST))
         .attribute(Attribute.create(RuleKeys.RANDOM))
         .attribute(Attribute.create(RuleKeys.INBUILDING))
         .attribute(Attribute.create(RuleKeys.INMULTIBUILDING))
         .attribute(Attribute.createMulti(RuleKeys.BUILDING))
         .attribute(Attribute.createMulti(RuleKeys.MULTIBUILDING))
         .attribute(Attribute.create(RuleKeys.INCITY))
         .attribute(Attribute.create(RuleKeys.INSTREET))
         .attribute(Attribute.create(RuleKeys.INSPHERE))
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
         .attribute(Attribute.create(RuleKeys.PLAYER))
         .attribute(Attribute.create(RuleKeys.REALPLAYER))
         .attribute(Attribute.create(RuleKeys.FAKEPLAYER))
         .attribute(Attribute.create(RuleKeys.WINTER))
         .attribute(Attribute.create(RuleKeys.SUMMER))
         .attribute(Attribute.create(RuleKeys.SPRING))
         .attribute(Attribute.create(RuleKeys.AUTUMN))
         .attribute(Attribute.create(RuleKeys.STATE))
         .attribute(Attribute.create(RuleKeys.PSTATE))
         .attribute(Attribute.createMulti(RuleKeys.SCOREBOARDTAGS_ALL))
         .attribute(Attribute.createMulti(RuleKeys.SCOREBOARDTAGS_ANY))
         .attribute(Attribute.createMulti(RuleKeys.MOB))
         .attribute(Attribute.createMulti(RuleKeys.MOD))
         .attribute(Attribute.create(RuleKeys.AREA))
         .attribute(Attribute.createMulti(RuleKeys.BLOCK))
         .attribute(Attribute.create(RuleKeys.BLOCKOFFSET))
         .attribute(Attribute.createMulti(RuleKeys.BLOCKTEST))
         .attribute(Attribute.createMulti(RuleKeys.BIOME))
         .attribute(Attribute.createMulti(RuleKeys.DIMENSION))
         .attribute(Attribute.createMulti(RuleKeys.DIMENSION_MOD))
         .attribute(Attribute.createMulti(RuleKeys.HELDITEM))
         .attribute(Attribute.createMulti(RuleKeys.PLAYER_HELDITEM))
         .attribute(Attribute.createMulti(RuleKeys.OFFHANDITEM))
         .attribute(Attribute.createMulti(RuleKeys.BOTHHANDSITEM))
         .attribute(Attribute.create(RuleKeys.ACTION_CUSTOMEVENT))
         .attribute(Attribute.create(RuleKeys.ACTION_RESULT))
         .attribute(Attribute.create(RuleKeys.ACTION_SETXP))
         .attribute(Attribute.create(RuleKeys.ACTION_ADDXP))
         .attribute(Attribute.create(RuleKeys.ACTION_MULTXP));
   }
}
