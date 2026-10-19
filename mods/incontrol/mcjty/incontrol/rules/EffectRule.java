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
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class EffectRule extends RuleBase<RuleBase.EventGetter> {
   private static final GenericAttributeMapFactory FACTORY = new GenericAttributeMapFactory();
   public static final IEventQuery<PlayerTickEvent> EVENT_QUERY = new IEventQuery<PlayerTickEvent>() {
      public Level getWorld(PlayerTickEvent o) {
         return o.getEntity().getCommandSenderWorld();
      }

      public BlockPos getPos(PlayerTickEvent o) {
         return o.getEntity().blockPosition();
      }

      public BlockPos getValidBlockPos(PlayerTickEvent o) {
         return o.getEntity().blockPosition().below();
      }

      public int getY(PlayerTickEvent o) {
         return o.getEntity().blockPosition().getY();
      }

      public Entity getEntity(PlayerTickEvent o) {
         return o.getEntity();
      }

      public DamageSource getSource(PlayerTickEvent o) {
         return null;
      }

      public Entity getAttacker(PlayerTickEvent o) {
         return null;
      }

      public Player getPlayer(PlayerTickEvent o) {
         return o.getEntity();
      }

      public ItemStack getItem(PlayerTickEvent o) {
         return ItemStack.EMPTY;
      }
   };
   private final GenericRuleEvaluator ruleEvaluator;
   private final int timeout;

   private EffectRule(AttributeMap map, int time, Set<String> phases, int index) {
      super(phases, index);
      this.ruleEvaluator = new GenericRuleEvaluator(map);
      this.timeout = time > 0 ? time : 1;
      this.addActions(map, new ModRuleCompatibilityLayer());
   }

   public int getTimeout() {
      return this.timeout;
   }

   public boolean match(PlayerTickEvent event) {
      return this.ruleEvaluator.match(event, EVENT_QUERY);
   }

   public void action(final PlayerTickEvent event) {
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
            return event.getEntity().getCommandSenderWorld();
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

   public static EffectRule parse(JsonElement element, int index) {
      if (element == null) {
         return null;
      }

      AttributeMap map = null;

      try {
         map = FACTORY.parse(element, "effects.json");
      } catch (Exception e) {
         InControl.setup.getLogger().error(e);
         return null;
      }

      int time = element.getAsJsonObject().has("timeout") ? element.getAsJsonObject().get("timeout").getAsInt() : 20;
      return new EffectRule(map, time, PhaseTools.getPhases(element), index);
   }

   static {
      FACTORY.attribute(Attribute.createMulti(RuleKeys.PHASE))
         .attribute(Attribute.create(RuleKeys.NUMBER))
         .attribute(Attribute.create(RuleKeys.TIMEOUT))
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
