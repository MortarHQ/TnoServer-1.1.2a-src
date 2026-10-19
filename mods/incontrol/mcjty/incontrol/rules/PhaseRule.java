package mcjty.incontrol.rules;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mcjty.incontrol.rules.support.GenericRuleEvaluator;
import mcjty.incontrol.rules.support.RuleKeys;
import mcjty.incontrol.tools.rules.IEventQuery;
import mcjty.incontrol.tools.typed.Attribute;
import mcjty.incontrol.tools.typed.AttributeMap;
import mcjty.incontrol.tools.typed.GenericAttributeMapFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public class PhaseRule {
   private final String name;
   private final int index;
   private final GenericRuleEvaluator ruleEvaluator;
   public static final IEventQuery<LevelAccessor> EVENT_QUERY = new IEventQuery<LevelAccessor>() {
      public LevelAccessor getWorld(LevelAccessor o) {
         return o;
      }

      public BlockPos getPos(LevelAccessor o) {
         return null;
      }

      public BlockPos getValidBlockPos(LevelAccessor o) {
         return null;
      }

      public int getY(LevelAccessor o) {
         return 0;
      }

      public Entity getEntity(LevelAccessor o) {
         return null;
      }

      public DamageSource getSource(LevelAccessor o) {
         return null;
      }

      public Entity getAttacker(LevelAccessor o) {
         return null;
      }

      public Player getPlayer(LevelAccessor o) {
         return null;
      }

      public ItemStack getItem(LevelAccessor o) {
         return null;
      }
   };
   private static final GenericAttributeMapFactory FACTORY = new GenericAttributeMapFactory();

   private PhaseRule(String name, AttributeMap map, int index) {
      this.name = name;
      this.index = index;
      this.ruleEvaluator = new GenericRuleEvaluator(map);
   }

   public String getName() {
      return this.name;
   }

   public boolean match(LevelAccessor world) {
      return this.ruleEvaluator.match(world, EVENT_QUERY);
   }

   public static PhaseRule parse(JsonElement element, int index) {
      if (element == null) {
         return null;
      }

      JsonObject object = element.getAsJsonObject();
      JsonElement conditions = object.get("conditions");
      AttributeMap map = FACTORY.parse(conditions, "phases.json");
      return new PhaseRule(object.get("name").getAsString(), map, index);
   }

   static {
      FACTORY.attribute(Attribute.create(RuleKeys.TIME))
         .attribute(Attribute.create(RuleKeys.MINTIME))
         .attribute(Attribute.create(RuleKeys.MAXTIME))
         .attribute(Attribute.create(RuleKeys.DAYCOUNT))
         .attribute(Attribute.create(RuleKeys.MINDAYCOUNT))
         .attribute(Attribute.create(RuleKeys.MAXDAYCOUNT))
         .attribute(Attribute.create(RuleKeys.WEATHER))
         .attribute(Attribute.create(RuleKeys.WINTER))
         .attribute(Attribute.create(RuleKeys.SUMMER))
         .attribute(Attribute.create(RuleKeys.SPRING))
         .attribute(Attribute.create(RuleKeys.AUTUMN))
         .attribute(Attribute.create(RuleKeys.STATE));
   }
}
