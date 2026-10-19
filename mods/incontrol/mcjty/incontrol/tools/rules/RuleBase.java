package mcjty.incontrol.tools.rules;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nonnull;
import mcjty.incontrol.ErrorHandler;
import mcjty.incontrol.InControl;
import mcjty.incontrol.ai.AISystem;
import mcjty.incontrol.data.DataStorage;
import mcjty.incontrol.events.EventsSystem;
import mcjty.incontrol.events.NumberAction;
import mcjty.incontrol.rules.support.RuleKeys;
import mcjty.incontrol.setup.Config;
import mcjty.incontrol.tools.typed.AttributeMap;
import mcjty.incontrol.tools.varia.LookAtTools;
import mcjty.incontrol.tools.varia.Tools;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class RuleBase<T extends RuleBase.EventGetter> {
   protected final List<Consumer<T>> actions = new ArrayList<>();
   private final Set<String> phases;
   private final int index;
   private static final Random rnd = new Random();
   public static final Component DEFAULT_NAME = Component.literal("@");
   public static final CommandSource EMPTY = new CommandSource() {
      public void sendSystemMessage(Component component) {
      }

      public boolean acceptsSuccess() {
         return false;
      }

      public boolean acceptsFailure() {
         return false;
      }

      public boolean shouldInformAdmins() {
         return false;
      }
   };

   public RuleBase(Set<String> phases, int index) {
      this.phases = phases;
      this.index = index;
   }

   public Set<String> getPhases() {
      return this.phases;
   }

   public int getIndex() {
      return this.index;
   }

   protected List<Pair<Float, ItemStack>> getItemsWeighted(List<String> itemNames) {
      List<Pair<Float, ItemStack>> items = new ArrayList<>();

      for (String json : itemNames) {
         JsonParser parser = new JsonParser();
         JsonElement element = parser.parse(json);
         if (element.isJsonPrimitive()) {
            String name = element.getAsString();
            Pair<Float, ItemStack> pair = Tools.parseStackWithFactor(name);
            if (((ItemStack)pair.getValue()).isEmpty()) {
               ErrorHandler.error("Unknown item '" + name + "'!");
            } else {
               items.add(pair);
            }
         } else if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            Pair<Float, ItemStack> pair = Tools.parseStackWithFactor(obj);
            if (pair != null) {
               items.add(pair);
            }
         } else {
            ErrorHandler.error("Item description '" + json + "' is not valid!");
         }
      }

      return items;
   }

   protected ItemStack getRandomItem(List<Pair<Float, ItemStack>> items, float total) {
      float r = rnd.nextFloat() * total;

      for (Pair<Float, ItemStack> pair : items) {
         if (r <= (Float)pair.getLeft()) {
            return ((ItemStack)pair.getRight()).copy();
         }

         r -= pair.getLeft();
      }

      return ItemStack.EMPTY;
   }

   protected float getTotal(List<Pair<Float, ItemStack>> items) {
      float total = 0.0F;

      for (Pair<Float, ItemStack> pair : items) {
         total += pair.getLeft();
      }

      return total;
   }

   protected void addActions(AttributeMap map, IModRuleCompatibilityLayer layer) {
      map.consume(RuleKeys.ACTION_CUSTOMEVENT, this::addCustomEventAction);
      map.consumeAsList(RuleKeys.ACTION_COMMAND, this::addCommandAction);
      map.consume(RuleKeys.ACTION_ADDSTAGE, stage -> this.addAddStage(stage, layer));
      map.consume(RuleKeys.ACTION_REMOVESTAGE, stage -> this.addRemoveStage(stage, layer));
      map.consume(RuleKeys.ACTION_HEALTHSET, v -> this.addAttributeAction("ctrlHealthS" + this.index, Attributes.MAX_HEALTH, (m, a) -> {
         a.setBaseValue(v.floatValue());
         m.setHealth(v);
      }));
      map.consume(RuleKeys.ACTION_HEALTHMULTIPLY, v -> this.addAttributeAction("ctrlHealthM" + this.index, Attributes.MAX_HEALTH, (m, a) -> {
         double newMax = a.getBaseValue() * v.floatValue();
         a.setBaseValue(newMax);
         m.setHealth((float)newMax);
      }));
      map.consume(RuleKeys.ACTION_HEALTHADD, v -> this.addAttributeAction("ctrlHealthA" + this.index, Attributes.MAX_HEALTH, (m, a) -> {
         double newMax = a.getBaseValue() + v.floatValue();
         a.setBaseValue(newMax);
         m.setHealth((float)newMax);
      }));
      map.consume(
         RuleKeys.ACTION_SPEEDSET, v -> this.addAttributeAction("ctrlSpeedS" + this.index, Attributes.MOVEMENT_SPEED, (m, a) -> a.setBaseValue(v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_SPEEDMULTIPLY,
         v -> this.addAttributeAction("ctrlSpeedM" + this.index, Attributes.MOVEMENT_SPEED, (m, a) -> a.setBaseValue(a.getBaseValue() * v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_SPEEDADD,
         v -> this.addAttributeAction("ctrlSpeedA" + this.index, Attributes.MOVEMENT_SPEED, (m, a) -> a.setBaseValue(a.getBaseValue() + v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_DAMAGESET,
         v -> this.addAttributeAction("ctrlDamageS" + this.index, Attributes.ATTACK_DAMAGE, (m, a) -> a.setBaseValue(v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_DAMAGEMULTIPLY,
         v -> this.addAttributeAction("ctrlDamageM" + this.index, Attributes.ATTACK_DAMAGE, (m, a) -> a.setBaseValue(a.getBaseValue() * v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_DAMAGEADD,
         v -> this.addAttributeAction("ctrlDamageA" + this.index, Attributes.ATTACK_DAMAGE, (m, a) -> a.setBaseValue(a.getBaseValue() + v.floatValue()))
      );
      map.consume(RuleKeys.ACTION_ARMORSET, v -> this.addAttributeAction("ctrlArmorS" + this.index, Attributes.ARMOR, (m, a) -> a.setBaseValue(v.floatValue())));
      map.consume(
         RuleKeys.ACTION_ARMORMULTIPLY,
         v -> this.addAttributeAction("ctrlArmorM" + this.index, Attributes.ARMOR, (m, a) -> a.setBaseValue(a.getBaseValue() * v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_ARMORADD,
         v -> this.addAttributeAction("ctrlArmorA" + this.index, Attributes.ARMOR, (m, a) -> a.setBaseValue(a.getBaseValue() + v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_ARMORTOUGHNESSSET,
         v -> this.addAttributeAction("ctrlArmorToS" + this.index, Attributes.ARMOR_TOUGHNESS, (m, a) -> a.setBaseValue(v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_ARMORTOUGHNESSMULTIPLY,
         v -> this.addAttributeAction("ctrlArmorToM" + this.index, Attributes.ARMOR_TOUGHNESS, (m, a) -> a.setBaseValue(a.getBaseValue() * v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_ARMORTOUGHNESSADD,
         v -> this.addAttributeAction("ctrlArmorToA" + this.index, Attributes.ARMOR_TOUGHNESS, (m, a) -> a.setBaseValue(a.getBaseValue() + v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_ATTACKSPEEDSET,
         v -> this.addAttributeAction("ctrlAttackSpS" + this.index, Attributes.ATTACK_SPEED, (m, a) -> a.setBaseValue(v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_ATTACKSPEEDMULTIPLY,
         v -> this.addAttributeAction("ctrlAttackSpM" + this.index, Attributes.ATTACK_SPEED, (m, a) -> a.setBaseValue(a.getBaseValue() * v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_ATTACKSPEEDADD,
         v -> this.addAttributeAction("ctrlAttackSpA" + this.index, Attributes.ATTACK_SPEED, (m, a) -> a.setBaseValue(a.getBaseValue() + v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_FOLLOWRANGESET,
         v -> this.addAttributeAction("ctrlFollowS" + this.index, Attributes.FOLLOW_RANGE, (m, a) -> a.setBaseValue(v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_FOLLOWRANGEMULTIPLY,
         v -> this.addAttributeAction("ctrlFollowM" + this.index, Attributes.FOLLOW_RANGE, (m, a) -> a.setBaseValue(a.getBaseValue() * v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_FOLLOWRANGEADD,
         v -> this.addAttributeAction("ctrlFollowA" + this.index, Attributes.FOLLOW_RANGE, (m, a) -> a.setBaseValue(a.getBaseValue() + v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_KNOCKBACKSET,
         v -> this.addAttributeAction("ctrlKnockbackS" + this.index, Attributes.ATTACK_KNOCKBACK, (m, a) -> a.setBaseValue(v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_KNOCKBACKMULTIPLY,
         v -> this.addAttributeAction("ctrlKnockbackM" + this.index, Attributes.ATTACK_KNOCKBACK, (m, a) -> a.setBaseValue(a.getBaseValue() * v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_KNOCKBACKADD,
         v -> this.addAttributeAction("ctrlKnockbackA" + this.index, Attributes.ATTACK_KNOCKBACK, (m, a) -> a.setBaseValue(a.getBaseValue() + v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_KNOCKBACKRESISTANCESET,
         v -> this.addAttributeAction("ctrlKnockbackResS" + this.index, Attributes.KNOCKBACK_RESISTANCE, (m, a) -> a.setBaseValue(v.floatValue()))
      );
      map.consume(
         RuleKeys.ACTION_KNOCKBACKRESISTANCEMULTIPLY,
         v -> this.addAttributeAction(
            "ctrlKnockbackResM" + this.index, Attributes.KNOCKBACK_RESISTANCE, (m, a) -> a.setBaseValue(a.getBaseValue() * v.floatValue())
         )
      );
      map.consume(
         RuleKeys.ACTION_KNOCKBACKRESISTANCEADD,
         v -> this.addAttributeAction(
            "ctrlKnockbackResA" + this.index, Attributes.KNOCKBACK_RESISTANCE, (m, a) -> a.setBaseValue(a.getBaseValue() + v.floatValue())
         )
      );
      map.consume2(RuleKeys.ACTION_SIZEMULTIPLY, RuleKeys.ACTION_SIZEADD, this::addSizeActions);
      map.consumeAsList(RuleKeys.ACTION_POTION, this::addPotionsAction);
      map.consumeAsList(RuleKeys.ACTION_POTION_NOPARTICLES, this::addPotionsNoParticlesAction);
      map.consume(RuleKeys.ACTION_NODESPAWN, this::addNoDespawnAction);
      map.consume(RuleKeys.ACTION_ANGRY, this::addAngryAction);
      map.consume(RuleKeys.ACTION_CUSTOMNAME, this::addCustomName);
      map.consume(RuleKeys.ACTION_MOBNBT, this::addMobNBT);
      map.consumeAsList(RuleKeys.ACTION_HELDITEM, this::addHeldItem);
      map.consumeAsList(RuleKeys.ACTION_ARMORBOOTS, items -> this.addArmorItem(items, EquipmentSlot.FEET));
      map.consumeAsList(RuleKeys.ACTION_ARMORLEGS, items -> this.addArmorItem(items, EquipmentSlot.LEGS));
      map.consumeAsList(RuleKeys.ACTION_ARMORHELMET, items -> this.addArmorItem(items, EquipmentSlot.HEAD));
      map.consumeAsList(RuleKeys.ACTION_ARMORCHEST, items -> this.addArmorItem(items, EquipmentSlot.CHEST));
      map.consume(RuleKeys.ACTION_FIRE, this::addFireAction);
      map.consume(RuleKeys.ACTION_EXPLOSION, this::addExplosionAction);
      map.consume(RuleKeys.ACTION_CLEAR, this::addClearAction);
      map.consume(RuleKeys.ACTION_DAMAGE, this::addDoDamageAction);
      map.consume(RuleKeys.ACTION_MESSAGE, this::addDoMessageAction);
      map.consume(RuleKeys.ACTION_AI, this::addAiAction);
      map.consumeAsList(RuleKeys.ACTION_ADDSCOREBOARDTAGS, this::addAddScoreboardTagsAction);
      map.consumeAsList(RuleKeys.ACTION_GIVE, this::addGiveAction);
      map.consumeAsList(RuleKeys.ACTION_DROP, this::addDropAction);
      map.consume2(RuleKeys.ACTION_SETBLOCK, RuleKeys.BLOCKOFFSET, this::addSetBlockAction);
      map.consume(RuleKeys.ACTION_SETHELDITEM, this::addSetHeldItemAction);
      map.consume(RuleKeys.ACTION_SETHELDAMOUNT, this::addSetHeldAmountAction);
      map.consume(RuleKeys.ACTION_SETPHASE, this::addSetPhaseAction);
      map.consume(RuleKeys.ACTION_CLEARPHASE, this::addClearPhaseAction);
      map.consume(RuleKeys.ACTION_TOGGLEPHASE, this::addTogglePhaseAction);
      map.consume(RuleKeys.ACTION_CHANGENUMBER, this::addChangeNumberAction);
      map.consume(RuleKeys.ACTION_SETSTATE, state -> {
         if (layer.hasEnigmaScript()) {
            this.addStateAction(state, layer);
         } else {
            InControl.setup.getLogger().warn("EnigmaScript is missing: this action cannot work!");
         }
      });
      map.consume(RuleKeys.ACTION_SETPSTATE, state -> {
         if (layer.hasEnigmaScript()) {
            this.addPStateAction(state, layer);
         } else {
            InControl.setup.getLogger().warn("EnigmaScript is missing: this action cannot work!");
         }
      });
   }

   private void addAiAction(String json) {
      Consumer<Mob> action = AISystem.parse(json);
      this.actions.add(event -> {
         if (event.getEntityLiving() instanceof Mob mob) {
            action.accept(mob);
         }
      });
   }

   private void addTogglePhaseAction(String phase) {
      this.actions.add(event -> {
         DataStorage data = DataStorage.getData(Tools.getServerWorld(event.getWorld()));
         data.togglePhase(phase);
      });
   }

   private void addClearPhaseAction(String phase) {
      this.actions.add(event -> {
         DataStorage data = DataStorage.getData(Tools.getServerWorld(event.getWorld()));
         data.setPhase(phase, false);
      });
   }

   private void addChangeNumberAction(String s) {
      try {
         String[] split = StringUtils.split(s, '=');
         String number = split[0];
         String value = split[1];
         NumberAction action = NumberAction.createUnnamed(value);
         if (action == null) {
            return;
         }

         this.actions.add(event -> {
            DataStorage data = DataStorage.getData(Tools.getServerWorld(event.getWorld()));
            int n = data.getNumber(number);
            n = action.perform(n);
            data.setNumber(number, n);
         });
      } catch (Exception e) {
         ErrorHandler.error("Bad number=expression specifier '" + s + "'!");
      }
   }

   private void addSetPhaseAction(String phase) {
      this.actions.add(event -> {
         DataStorage data = DataStorage.getData(Tools.getServerWorld(event.getWorld()));
         data.setPhase(phase, true);
      });
   }

   private void addCustomEventAction(String eventname) {
      this.actions.add(event -> {
         ServerLevel level = Tools.getServerWorld(event.getWorld());
         EventsSystem.onCustomEvent(level, event.getPosition(), eventname);
      });
   }

   private void addCommandAction(List<String> commands) {
      this.actions
         .add(
            event -> {
               MinecraftServer server = event.getWorld().getServer();
               Player player = event.getPlayer();
               CommandSourceStack stack = new CommandSourceStack(
                  EMPTY,
                  Vec3.atCenterOf(event.getPosition()),
                  Vec2.ZERO,
                  (ServerLevel)event.getWorld(),
                  2,
                  DEFAULT_NAME.getString(),
                  DEFAULT_NAME,
                  server,
                  player
               );

               for (String command : commands) {
                  server.getCommands().performPrefixedCommand(stack, command);
               }
            }
         );
   }

   private void addAddStage(String stage, IModRuleCompatibilityLayer layer) {
      this.actions.add(event -> {
         Player player = event.getPlayer();
         if (player != null) {
            layer.addGameStage(player, stage);
         }
      });
   }

   private void addRemoveStage(String stage, IModRuleCompatibilityLayer layer) {
      this.actions.add(event -> {
         Player player = event.getPlayer();
         if (player != null) {
            layer.removeGameStage(player, stage);
         }
      });
   }

   private void addNoDespawnAction(boolean a) {
      this.actions.add(event -> {
         if (event.getEntityLiving() instanceof Mob mob) {
            mob.setPersistenceRequired();
         }
      });
   }

   private void addDoDamageAction(String damage) {
      String[] split = StringUtils.split(damage, "=");
      Registry<DamageType> damageTypes = ServerLifecycleHooks.getCurrentServer().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
      Optional<Reference<DamageType>> type = damageTypes.getHolder(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse(split[0])));
      if (!type.isPresent()) {
         ErrorHandler.error("Can't find damage source '" + split[0] + "'!");
      } else {
         DamageSource source = new DamageSource((Holder)type.get());
         float amount = 1.0F;
         if (split.length > 1) {
            amount = Float.parseFloat(split[1]);
         }

         float finalAmount = amount;
         this.actions.add(event -> {
            LivingEntity living = event.getEntityLiving();
            if (living != null) {
               living.hurt(source, finalAmount);
            }
         });
      }
   }

   private void addDoMessageAction(String message) {
      this.actions.add(event -> {
         Player player = event.getPlayer();
         if (player == null) {
            player = event.getWorld().getNearestPlayer(event.getEntityLiving(), ((Integer)Config.PERPLAYER_RADIUS.get()).intValue());
         }

         if (player != null) {
            player.displayClientMessage(Component.literal(message), false);
         }
      });
   }

   private void addGiveAction(List<String> itemList) {
      List<Pair<Float, ItemStack>> items = this.getItemsWeighted(itemList);
      if (!items.isEmpty()) {
         if (items.size() == 1) {
            ItemStack item = (ItemStack)items.get(0).getRight();
            this.actions.add(event -> {
               Player player = event.getPlayer();
               if (player != null && !player.getInventory().add(item.copy())) {
                  player.spawnAtLocation(item.copy(), 1.05F);
               }
            });
         } else {
            float total = this.getTotal(items);
            this.actions.add(event -> {
               Player player = event.getPlayer();
               if (player != null) {
                  ItemStack item = this.getRandomItem(items, total);
                  if (!player.getInventory().add(item.copy())) {
                     player.spawnAtLocation(item.copy(), 1.05F);
                  }
               }
            });
         }
      }
   }

   private void addStateAction(String s, IModRuleCompatibilityLayer layer) {
      String[] split = StringUtils.split(s, '=');

      String state;
      String value;
      try {
         state = split[0];
         value = split[1];
      } catch (Exception e) {
         ErrorHandler.error("Bad state=value specifier '" + s + "'!");
         return;
      }

      String finalState = state;
      String finalValue = value;
      this.actions.add(event -> layer.setState(event.getWorld(), finalState, finalValue));
   }

   private void addPStateAction(String s, IModRuleCompatibilityLayer layer) {
      String[] split = StringUtils.split(s, '=');

      String state;
      String value;
      try {
         state = split[0];
         value = split[1];
      } catch (Exception e) {
         ErrorHandler.error("Bad state=value specifier '" + s + "'!");
         return;
      }

      String finalState = state;
      String finalValue = value;
      this.actions.add(event -> layer.setPlayerState(event.getPlayer(), finalState, finalValue));
   }

   @Nonnull
   private Function<RuleBase.EventGetter, BlockPos> parseOffset(String json) {
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(json);
      JsonObject obj = element.getAsJsonObject();
      int offsetX;
      int offsetY;
      int offsetZ;
      if (obj.has("offset")) {
         JsonObject offset = obj.getAsJsonObject("offset");
         offsetX = offset.has("x") ? offset.get("x").getAsInt() : 0;
         offsetY = offset.has("y") ? offset.get("y").getAsInt() : 0;
         offsetZ = offset.has("z") ? offset.get("z").getAsInt() : 0;
      } else {
         offsetX = 0;
         offsetY = 0;
         offsetZ = 0;
      }

      return obj.has("look")
         ? event -> {
            HitResult result = LookAtTools.getMovingObjectPositionFromPlayer(event.getWorld(), event.getPlayer(), false);
            return result instanceof BlockHitResult
               ? ((BlockHitResult)result).getBlockPos().offset(offsetX, offsetY, offsetZ)
               : event.getPosition().offset(offsetX, offsetY, offsetZ);
         }
         : event -> event.getPosition().offset(offsetX, offsetY, offsetZ);
   }

   private void addSetHeldItemAction(String json) {
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(json);
      ItemStack stack;
      if (element.isJsonPrimitive()) {
         String name = element.getAsString();
         stack = Tools.parseStack(name);
      } else {
         if (!element.isJsonObject()) {
            ErrorHandler.error("Item description '" + json + "' is not valid!");
            return;
         }

         JsonObject obj = element.getAsJsonObject();
         stack = Tools.parseStack(obj);
         if (stack == null) {
            return;
         }
      }

      this.actions.add(event -> event.getPlayer().setItemInHand(InteractionHand.MAIN_HAND, stack.copy()));
   }

   private void addSetHeldAmountAction(String amount) {
      int add = 0;
      int set = -1;
      if (amount.startsWith("+")) {
         add = Integer.parseInt(amount.substring(1));
      } else if (amount.startsWith("-")) {
         add = -Integer.parseInt(amount.substring(1));
      } else if (amount.startsWith("=")) {
         set = Integer.parseInt(amount.substring(1));
      } else {
         set = Integer.parseInt(amount);
      }

      int finalSet = set;
      if (finalSet >= 0) {
         this.actions.add(event -> {
            ItemStack item = event.getPlayer().getMainHandItem();
            item.setCount(finalSet);
            event.getPlayer().setItemInHand(InteractionHand.MAIN_HAND, item.copy());
         });
      } else {
         int finalAdd = add;
         this.actions.add(event -> {
            ItemStack item = event.getPlayer().getMainHandItem();
            int newCount = item.getCount() + finalAdd;
            if (newCount < 0) {
               newCount = 0;
            } else if (newCount >= item.getMaxStackSize()) {
               newCount = item.getMaxStackSize() - 1;
            }

            item.setCount(newCount);
            event.getPlayer().setItemInHand(InteractionHand.MAIN_HAND, item.copy());
         });
      }
   }

   private void addSetBlockAction(String json, String bo) {
      Function<RuleBase.EventGetter, BlockPos> posFunction;
      if (bo != null) {
         posFunction = this.parseOffset(bo);
      } else {
         posFunction = RuleBase.EventGetter::getPosition;
      }

      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(json);
      if (element.isJsonPrimitive()) {
         String blockname = element.getAsString();
         Block block = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse(blockname));
         if (block == null) {
            ErrorHandler.error("Block '" + blockname + "' is not valid!");
            return;
         }

         BlockState state = block.defaultBlockState();
         this.actions.add(event -> {
            BlockPos pos = posFunction.apply(event);
            if (pos != null) {
               event.getWorld().setBlock(pos, state, 3);
            }
         });
      } else {
         JsonObject obj = element.getAsJsonObject();
         if (!obj.has("block")) {
            ErrorHandler.error("Block is not valid!");
            return;
         }

         String blockname = obj.get("block").getAsString();
         Block block = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse(blockname));
         if (block == null) {
            ErrorHandler.error("Block '" + blockname + "' is not valid!");
            return;
         }

         BlockState state = block.defaultBlockState();
         if (obj.has("properties")) {
            for (JsonElement el : obj.get("properties").getAsJsonArray()) {
               JsonObject propObj = el.getAsJsonObject();
               String name = propObj.get("name").getAsString();
               String value = propObj.get("value").getAsString();

               for (Property<?> key : state.getProperties()) {
                  if (name.equals(key.getName())) {
                     state = TestingTools.set(state, (Property<T>)key, value);
                  }
               }
            }
         }

         BlockState finalState = state;
         this.actions.add(event -> {
            BlockPos pos = posFunction.apply(event);
            if (pos != null) {
               event.getWorld().setBlock(pos, finalState, 3);
            }
         });
      }
   }

   private void addDropAction(List<String> itemList) {
      List<Pair<Float, ItemStack>> items = this.getItemsWeighted(itemList);
      if (!items.isEmpty()) {
         if (items.size() == 1) {
            ItemStack item = (ItemStack)items.get(0).getRight();
            this.actions.add(event -> {
               if (event.getWorld() instanceof Level) {
                  BlockPos pos = event.getPosition();
                  ItemEntity entityItem = new ItemEntity((Level)event.getWorld(), pos.getX(), pos.getY(), pos.getZ(), item.copy());
                  event.getWorld().addFreshEntity(entityItem);
               }
            });
         } else {
            float total = this.getTotal(items);
            this.actions.add(event -> {
               if (event.getWorld() instanceof Level) {
                  BlockPos pos = event.getPosition();
                  ItemStack item = this.getRandomItem(items, total);
                  ItemEntity entityItem = new ItemEntity((Level)event.getWorld(), pos.getX(), pos.getY(), pos.getZ(), item.copy());
                  event.getWorld().addFreshEntity(entityItem);
               }
            });
         }
      }
   }

   private void addClearAction(boolean clear) {
      if (clear) {
         this.actions.add(event -> {
            LivingEntity living = event.getEntityLiving();
            if (living != null) {
               living.removeAllEffects();
            }
         });
      }
   }

   private void addFireAction(int fireAction) {
      this.actions.add(event -> {
         LivingEntity living = event.getEntityLiving();
         if (living != null) {
            living.hurt(living.damageSources().onFire(), 0.1F);
            living.igniteForTicks(fireAction * 20);
         }
      });
   }

   private void addExplosionAction(String fireAction) {
      String[] split = StringUtils.split(fireAction, ",");
      float strength = 1.0F;
      boolean flaming = false;
      boolean smoking = false;

      try {
         strength = Float.parseFloat(split[0]);
         flaming = "1".equalsIgnoreCase(split[1]) || "true".equals(split[1].toLowerCase()) || "yes".equals(split[1].toLowerCase());
         smoking = "1".equalsIgnoreCase(split[2]) || "true".equals(split[2].toLowerCase()) || "yes".equals(split[2].toLowerCase());
      } catch (Exception var9) {
      }

      float finalStrength = strength;
      boolean finalFlaming = flaming;
      this.actions
         .add(
            event -> {
               BlockPos pos = event.getPosition();
               if (pos != null && event.getWorld() instanceof Level) {
                  ((Level)event.getWorld())
                     .explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, finalStrength, finalFlaming, ExplosionInteraction.TNT);
               }
            }
         );
   }

   private void addPotionsAction(List<String> potions) {
      this.addPotionsAction(potions, true);
   }

   private void addPotionsNoParticlesAction(List<String> potions) {
      this.addPotionsAction(potions, false);
   }

   protected void addPotionsAction(List<String> potions, boolean showParticles) {
      List<MobEffectInstance> effects = new ArrayList<>();

      for (String p : potions) {
         String[] splitted = StringUtils.split(p, ',');
         if (splitted != null && splitted.length == 3) {
            Optional<Reference<MobEffect>> potion = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(splitted[0]));
            if (potion.isEmpty()) {
               ErrorHandler.error("Can't find potion '" + p + "'!");
            } else {
               int duration = 0;
               int amplifier = 0;

               try {
                  duration = Integer.parseInt(splitted[1]);
                  amplifier = Integer.parseInt(splitted[2]);
               } catch (NumberFormatException e) {
                  ErrorHandler.error("Bad duration or amplifier integer for '" + p + "'!");
                  continue;
               }

               effects.add(new MobEffectInstance((Holder)potion.get(), duration, amplifier));
            }
         } else {
            ErrorHandler.error("Bad potion specifier '" + p + "'! Use <potion>,<duration>,<amplifier>");
         }
      }

      if (!effects.isEmpty()) {
         this.actions.add(event -> {
            LivingEntity living = event.getEntityLiving();
            if (living != null) {
               for (MobEffectInstance effect : effects) {
                  MobEffectInstance neweffect = new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier(), false, showParticles);
                  living.addEffect(neweffect);
               }
            }
         });
      }
   }

   private void addAddScoreboardTagsAction(List<String> tags) {
      this.actions.add(event -> {
         LivingEntity entityLiving = event.getEntityLiving();
         if (entityLiving != null) {
            for (String tag : tags) {
               entityLiving.addTag(tag);
            }
         }
      });
   }

   private void addAttributeAction(String key, Holder<Attribute> attribute, BiConsumer<LivingEntity, AttributeInstance> action) {
      this.actions.add(event -> {
         LivingEntity entityLiving = event.getEntityLiving();
         if (entityLiving != null && !entityLiving.getTags().contains(key)) {
            AttributeInstance attr = entityLiving.getAttribute(attribute);
            if (attr != null) {
               action.accept(entityLiving, attr);
               entityLiving.addTag(key);
            }
         }
      });
   }

   private void addSizeActions(Float m, Float a) {
      m = m == null ? 1.0F : m;
      a = a == null ? 0.0F : a;
      ErrorHandler.error("Mob resizing not implemented yet!");
      this.actions.add(event -> {
         LivingEntity entityLiving = event.getEntityLiving();
         if (entityLiving != null) {
         }
      });
   }

   private void addDamageSetAction(float s, int index) {
      this.actions.add(event -> {
         LivingEntity entityLiving = event.getEntityLiving();
         if (entityLiving != null && !entityLiving.getTags().contains("ctrlDamageSet" + index)) {
            AttributeInstance entityAttribute = entityLiving.getAttribute(Attributes.ATTACK_DAMAGE);
            if (entityAttribute != null) {
               entityAttribute.setBaseValue(s);
               entityLiving.addTag("ctrlDamageSet" + index);
            }
         }
      });
   }

   private void addDamageAction(Float m, Float a, int index) {
      float finalM = m == null ? 1.0F : m;
      float finalA = a == null ? 0.0F : a;
      this.actions.add(event -> {
         LivingEntity entityLiving = event.getEntityLiving();
         if (entityLiving != null && !entityLiving.getTags().contains("ctrlDamage" + index)) {
            AttributeInstance entityAttribute = entityLiving.getAttribute(Attributes.ATTACK_DAMAGE);
            if (entityAttribute != null) {
               double newMax = entityAttribute.getBaseValue() * finalM + finalA;
               entityAttribute.setBaseValue(newMax);
               entityLiving.addTag("ctrlDamage" + index);
            }
         }
      });
   }

   protected void addArmorItem(List<String> itemList, EquipmentSlot slot) {
      List<Pair<Float, ItemStack>> items = this.getItemsWeighted(itemList);
      if (!items.isEmpty()) {
         if (items.size() == 1) {
            ItemStack item = (ItemStack)items.get(0).getRight();
            this.actions.add(event -> {
               LivingEntity entityLiving = event.getEntityLiving();
               if (entityLiving != null) {
                  entityLiving.setItemSlot(slot, item.copy());
               }
            });
         } else {
            float total = this.getTotal(items);
            this.actions.add(event -> {
               LivingEntity entityLiving = event.getEntityLiving();
               if (entityLiving != null) {
                  entityLiving.setItemSlot(slot, this.getRandomItem(items, total));
               }
            });
         }
      }
   }

   protected void addHeldItem(List<String> heldItems) {
      List<Pair<Float, ItemStack>> items = this.getItemsWeighted(heldItems);
      if (!items.isEmpty()) {
         if (items.size() == 1) {
            ItemStack item = (ItemStack)items.get(0).getRight();
            this.actions.add(event -> {
               LivingEntity entityLiving = event.getEntityLiving();
               if (entityLiving != null) {
                  if (entityLiving instanceof EnderMan) {
                     if (item.getItem() instanceof BlockItem) {
                        BlockItem b = (BlockItem)item.getItem();
                        ((EnderMan)entityLiving).setCarriedBlock(b.getBlock().defaultBlockState());
                     }
                  } else {
                     entityLiving.setItemInHand(InteractionHand.MAIN_HAND, item.copy());
                  }
               }
            });
         } else {
            float total = this.getTotal(items);
            this.actions.add(event -> {
               LivingEntity entityLiving = event.getEntityLiving();
               if (entityLiving != null) {
                  ItemStack item = this.getRandomItem(items, total);
                  if (entityLiving instanceof EnderMan) {
                     if (item.getItem() instanceof BlockItem) {
                        BlockItem b = (BlockItem)item.getItem();
                        ((EnderMan)entityLiving).setCarriedBlock(b.getBlock().defaultBlockState());
                     }
                  } else {
                     entityLiving.setItemInHand(InteractionHand.MAIN_HAND, item.copy());
                  }
               }
            });
         }
      }
   }

   private void addMobNBT(String mobnbt) {
      if (mobnbt != null) {
         CompoundTag tagCompound;
         try {
            tagCompound = TagParser.parseTag(mobnbt);
         } catch (CommandSyntaxException e) {
            ErrorHandler.error("Bad NBT for mob!");
            return;
         }

         this.actions.add(event -> {
            LivingEntity entityLiving = event.getEntityLiving();
            entityLiving.readAdditionalSaveData(tagCompound);
         });
      }
   }

   private void addCustomName(String customName) {
      if (customName != null) {
         this.actions.add(event -> {
            LivingEntity entityLiving = event.getEntityLiving();
            entityLiving.setCustomName(Component.literal(customName));
         });
      }
   }

   protected void addAngryAction(boolean angry) {
      if (angry) {
         this.actions.add(event -> {
            LivingEntity entityLiving = event.getEntityLiving();
            if (entityLiving != null) {
               Player player = event.getWorld().getNearestPlayer(entityLiving, 50.0);
               if (player != null) {
                  entityLiving.setLastHurtByMob(player);
                  entityLiving.setLastHurtMob(player);
                  if (entityLiving instanceof NeutralMob) {
                     ((NeutralMob)entityLiving).setTarget(player);
                  }
               }
            }
         });
      }
   }

   public interface EventGetter {
      LivingEntity getEntityLiving();

      Player getPlayer();

      LevelAccessor getWorld();

      BlockPos getPosition();
   }
}
