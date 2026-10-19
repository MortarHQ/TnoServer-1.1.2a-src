package mcjty.incontrol.rules;

import com.google.gson.JsonElement;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.DataResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import mcjty.incontrol.ErrorHandler;
import mcjty.incontrol.InControl;
import mcjty.incontrol.compat.ModRuleCompatibilityLayer;
import mcjty.incontrol.data.PhaseTools;
import mcjty.incontrol.rules.support.GenericRuleEvaluator;
import mcjty.incontrol.rules.support.RuleKeys;
import mcjty.incontrol.tools.rules.IEventQuery;
import mcjty.incontrol.tools.rules.IModRuleCompatibilityLayer;
import mcjty.incontrol.tools.rules.RuleBase;
import mcjty.incontrol.tools.rules.TestingTools;
import mcjty.incontrol.tools.typed.Attribute;
import mcjty.incontrol.tools.typed.AttributeMap;
import mcjty.incontrol.tools.typed.GenericAttributeMapFactory;
import mcjty.incontrol.tools.varia.Tools;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class LootRule extends RuleBase<RuleBase.EventGetter> {
   public static final IEventQuery<LootContext> EVENT_QUERY = new IEventQuery<LootContext>() {
      public Level getWorld(LootContext o) {
         return o.getLevel();
      }

      public BlockPos getPos(LootContext o) {
         Entity entity = (Entity)o.getParamOrNull(LootContextParams.THIS_ENTITY);
         return entity == null ? null : entity.blockPosition();
      }

      public BlockPos getValidBlockPos(LootContext o) {
         Entity entity = (Entity)o.getParamOrNull(LootContextParams.THIS_ENTITY);
         return entity == null ? null : entity.blockPosition().below();
      }

      public int getY(LootContext o) {
         Entity entity = (Entity)o.getParamOrNull(LootContextParams.THIS_ENTITY);
         return entity == null ? 0 : entity.blockPosition().getY();
      }

      public Entity getEntity(LootContext o) {
         return (Entity)o.getParamOrNull(LootContextParams.THIS_ENTITY);
      }

      public DamageSource getSource(LootContext o) {
         return (DamageSource)o.getParamOrNull(LootContextParams.DAMAGE_SOURCE);
      }

      public Entity getAttacker(LootContext o) {
         return (Entity)o.getParamOrNull(LootContextParams.ATTACKING_ENTITY);
      }

      public Player getPlayer(LootContext o) {
         Entity entity = (Entity)o.getParamOrNull(LootContextParams.ATTACKING_ENTITY);
         return entity instanceof Player ? (Player)entity : null;
      }

      public ItemStack getItem(LootContext o) {
         return ItemStack.EMPTY;
      }
   };
   private static final GenericAttributeMapFactory FACTORY = new GenericAttributeMapFactory();
   private static final Random rnd = new Random();
   private final GenericRuleEvaluator ruleEvaluator;
   private final List<Predicate<ItemStack>> toRemoveItems = new ArrayList<>();
   private final List<Pair<ItemStack, Function<Integer, Integer>>> toAddItems = new ArrayList<>();
   private boolean removeAll = false;

   private LootRule(AttributeMap map, Set<String> phases, int index) {
      super(phases, index);
      this.ruleEvaluator = new GenericRuleEvaluator(map);
      this.addActions(map, new ModRuleCompatibilityLayer());
   }

   public static LootRule parse(JsonElement element, int index) {
      if (element == null) {
         return null;
      }

      AttributeMap map = FACTORY.parse(element, "loot.json");
      return new LootRule(map, PhaseTools.getPhases(element), index);
   }

   @Override
   protected void addActions(AttributeMap map, IModRuleCompatibilityLayer layer) {
      super.addActions(map, layer);
      map.consumeAsList(RuleKeys.ACTION_ITEM, itemList -> this.addItem(map, itemList));
      map.consumeAsList(RuleKeys.ACTION_REMOVE, this::removeItem);
      map.consume(RuleKeys.ACTION_REMOVEALL, v -> this.removeAll = v);
      if (!map.isEmpty()) {
         StringBuffer buffer = new StringBuffer();
         map.getKeys().forEach(k -> buffer.append(k).append(' '));
         ErrorHandler.error("Invalid keywords in loot rule: " + buffer);
      }
   }

   public List<Predicate<ItemStack>> getToRemoveItems() {
      return this.toRemoveItems;
   }

   public boolean isRemoveAll() {
      return this.removeAll;
   }

   public List<Pair<ItemStack, Function<Integer, Integer>>> getToAddItems() {
      return this.toAddItems;
   }

   private Function<Integer, Integer> getCountFunction(@Nullable String itemcount) {
      if (itemcount == null) {
         return looting -> 1;
      }

      String[] loottable = StringUtils.split(itemcount, '/');
      int[] min = new int[loottable.length];
      int[] max = new int[loottable.length];

      for (int i = 0; i < loottable.length; i++) {
         String[] minmax = StringUtils.split(loottable[i], '-');
         if (minmax.length == 1) {
            try {
               min[i] = max[i] = Integer.parseInt(minmax[0]);
            } catch (NumberFormatException e) {
               InControl.setup.getLogger().log(org.apache.logging.log4j.Level.ERROR, "Bad amount specified in loot rule: " + minmax);
               min[i] = max[i] = 1;
            }
         } else if (minmax.length == 2) {
            try {
               min[i] = Integer.parseInt(minmax[0]);
               max[i] = Integer.parseInt(minmax[1]);
            } catch (NumberFormatException e) {
               InControl.setup.getLogger().log(org.apache.logging.log4j.Level.ERROR, "Bad amounts specified in loot rule: " + minmax);
               min[i] = max[i] = 1;
            }
         } else {
            InControl.setup.getLogger().log(org.apache.logging.log4j.Level.ERROR, "Bad amount range specified in loot rule: " + minmax);
            min[i] = max[i] = 1;
         }
      }

      if (loottable.length != 1) {
         return looting -> {
            if (looting >= min.length) {
               return rnd.nextInt(max[min.length - 1] - min[min.length - 1] + 1) + min[min.length - 1];
            } else {
               return looting >= 0 ? rnd.nextInt(max[looting] - min[looting] + 1) + min[looting] : rnd.nextInt(max[0] - min[0] + 1) + min[0];
            }
         };
      } else {
         return min[0] == max[0] ? looting -> min[0] : looting -> rnd.nextInt(max[0] - min[0] + 1) + min[0];
      }
   }

   private List<Pair<ItemStack, Function<Integer, Integer>>> getItems(List<String> itemNames, @Nullable String nbtJson, @Nullable String itemcount) {
      Function<Integer, Integer> countFunction = this.getCountFunction(itemcount);
      List<Pair<ItemStack, Function<Integer, Integer>>> items = new ArrayList<>();

      for (String name : itemNames) {
         ItemStack stack = Tools.parseStack(name);
         if (stack.isEmpty()) {
            InControl.setup.getLogger().log(org.apache.logging.log4j.Level.ERROR, "Unknown item '" + name + "'!");
         } else {
            if (nbtJson != null) {
               try {
                  CompoundTag tag = TagParser.parseTag(nbtJson);
                  DataResult<com.mojang.datafixers.util.Pair<DataComponentPatch, Tag>> decoded = DataComponentPatch.CODEC.decode(NbtOps.INSTANCE, tag);
                  stack.applyComponents((DataComponentPatch)((com.mojang.datafixers.util.Pair)decoded.result().get()).getFirst());
               } catch (CommandSyntaxException e) {
                  InControl.setup.getLogger().log(org.apache.logging.log4j.Level.ERROR, "Bad nbt for '" + name + "'!");
               }
            }

            items.add(Pair.of(stack, countFunction));
         }
      }

      return items;
   }

   private void addItem(AttributeMap map, List<String> itemList) {
      String nbt = map.consumeAndFetch(RuleKeys.ACTION_ITEMNBT);
      String itemcount = map.consumeAndFetch(RuleKeys.ACTION_ITEMCOUNT);
      this.toAddItems.addAll(this.getItems(itemList, nbt, itemcount));
   }

   private void removeItem(List<String> itemList) {
      this.toRemoveItems.addAll(TestingTools.getItems(itemList));
   }

   public boolean match(LootContext event) {
      return this.ruleEvaluator.match(event, EVENT_QUERY);
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
         .attribute(Attribute.create(RuleKeys.GAMESTAGE))
         .attribute(Attribute.create(RuleKeys.WINTER))
         .attribute(Attribute.create(RuleKeys.SUMMER))
         .attribute(Attribute.create(RuleKeys.SPRING))
         .attribute(Attribute.create(RuleKeys.AUTUMN))
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
         .attribute(Attribute.create(RuleKeys.PROJECTILE))
         .attribute(Attribute.create(RuleKeys.EXPLOSION))
         .attribute(Attribute.create(RuleKeys.FIRE))
         .attribute(Attribute.create(RuleKeys.MAGIC))
         .attribute(Attribute.createMulti(RuleKeys.MOB))
         .attribute(Attribute.createMulti(RuleKeys.MOD))
         .attribute(Attribute.create(RuleKeys.AREA))
         .attribute(Attribute.createMulti(RuleKeys.BLOCK))
         .attribute(Attribute.create(RuleKeys.BLOCKOFFSET))
         .attribute(Attribute.createMulti(RuleKeys.BLOCKTEST))
         .attribute(Attribute.createMulti(RuleKeys.BIOME))
         .attribute(Attribute.createMulti(RuleKeys.DIMENSION))
         .attribute(Attribute.createMulti(RuleKeys.DIMENSION_MOD))
         .attribute(Attribute.createMulti(RuleKeys.SOURCE))
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
         .attribute(Attribute.create(RuleKeys.STATE))
         .attribute(Attribute.create(RuleKeys.PSTATE))
         .attribute(Attribute.createMulti(RuleKeys.SCOREBOARDTAGS_ALL))
         .attribute(Attribute.createMulti(RuleKeys.SCOREBOARDTAGS_ANY))
         .attribute(Attribute.createMulti(RuleKeys.HELDITEM))
         .attribute(Attribute.createMulti(RuleKeys.PLAYER_HELDITEM))
         .attribute(Attribute.createMulti(RuleKeys.OFFHANDITEM))
         .attribute(Attribute.createMulti(RuleKeys.BOTHHANDSITEM))
         .attribute(Attribute.create(RuleKeys.ACTION_CUSTOMEVENT))
         .attribute(Attribute.create(RuleKeys.ACTION_ITEMNBT))
         .attribute(Attribute.create(RuleKeys.ACTION_ITEMCOUNT))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_ITEM))
         .attribute(Attribute.createMulti(RuleKeys.ACTION_REMOVE))
         .attribute(Attribute.create(RuleKeys.ACTION_REMOVEALL));
   }
}
