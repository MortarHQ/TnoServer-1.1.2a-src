package team.creative.creativecore.common.util.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.lang.reflect.InvocationTargetException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.converation.ConfigTypeConveration;
import team.creative.creativecore.common.config.gui.GuiPlayerSelectorButton;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.util.mc.PlayerUtils;
import team.creative.creativecore.common.util.registry.NamedTypeRegistry;

public abstract class PlayerSelector {
   public static final NamedTypeRegistry<PlayerSelector> REGISTRY = new NamedTypeRegistry<PlayerSelector>().addConstructorPattern();

   public static PlayerSelector read(CompoundTag nbt) {
      Class<? extends PlayerSelector> clazz = REGISTRY.get(nbt.getString("id"));
      if (clazz == null) {
         throw new RuntimeException("Could not find player selector for " + nbt.getString("id"));
      }

      try {
         PlayerSelector selector = clazz.getConstructor().newInstance();
         selector.readFromNBT(nbt);
         return selector;
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
         throw new RuntimeException("PlayerSelector type " + nbt.getString("id") + " does not have an empty constructor!");
      }
   }

   public abstract boolean is(Player var1);

   public abstract void readFromNBT(CompoundTag var1);

   protected abstract void write(CompoundTag var1);

   public abstract String info();

   public CompoundTag writeToNBT(CompoundTag nbt) {
      this.write(nbt);
      nbt.putString("id", REGISTRY.getId((Class<? extends PlayerSelector>)this.getClass()));
      return nbt;
   }

   static {
      REGISTRY.register("and", PlayerSelector.PlayerSelectorAnd.class);
      REGISTRY.register("or", PlayerSelector.PlayerSelectorOr.class);
      REGISTRY.register("not", PlayerSelector.PlayerSelectorNot.class);
      REGISTRY.register("level", PlayerSelector.PlayerSelectorLevel.class);
      REGISTRY.register("mode", PlayerSelector.PlayerSelectorGamemode.class);
      REGISTRY.register("selector", PlayerSelector.PlayerSelectorCommandSelector.class);
      ConfigTypeConveration.registerSpecialType(
         PlayerSelector.class::isAssignableFrom, new ConfigTypeConveration.SimpleConfigTypeConveration<PlayerSelector>() {
            public PlayerSelector readElement(ConfigKey key, PlayerSelector defaultValue, Side side, JsonElement element) {
               if (element.isJsonPrimitive() && ((JsonPrimitive)element).isString()) {
                  try {
                     return PlayerSelector.read(TagParser.parseTag(element.getAsString()));
                  } catch (CommandSyntaxException e) {
                     CreativeCore.LOGGER.error(e);
                  }
               }

               return defaultValue;
            }

            public JsonElement writeElement(PlayerSelector value, ConfigKey key, Side side) {
               return new JsonPrimitive(value.writeToNBT(new CompoundTag()).toString());
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            @Override
            public void createControls(GuiParent parent, ConfigKey key) {
               parent.add(new GuiPlayerSelectorButton("data", new PlayerSelector.PlayerSelectorLevel(0)));
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            public void loadValue(PlayerSelector value, GuiParent parent) {
               GuiPlayerSelectorButton button = parent.get("data");
               button.set(value);
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            protected PlayerSelector saveValue(GuiParent parent, ConfigKey key) {
               GuiPlayerSelectorButton button = parent.get("data");
               return button.get();
            }

            public PlayerSelector set(ConfigKey key, PlayerSelector value) {
               return value;
            }
         }
      );
   }

   public static class PlayerSelectorAnd extends PlayerSelector {
      public PlayerSelector[] selectors;

      public PlayerSelectorAnd() {
      }

      public PlayerSelectorAnd(PlayerSelector... selector) {
         this.selectors = selector;
      }

      @Override
      public boolean is(Player player) {
         for (int i = 0; i < this.selectors.length; i++) {
            if (!this.selectors[i].is(player)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public void readFromNBT(CompoundTag nbt) {
         ListTag list = nbt.getList("selectors", 10);
         this.selectors = new PlayerSelector[list.size()];

         for (int i = 0; i < this.selectors.length; i++) {
            this.selectors[i] = PlayerSelector.read(list.getCompound(i));
         }
      }

      @Override
      protected void write(CompoundTag nbt) {
         ListTag list = new ListTag();

         for (int i = 0; i < this.selectors.length; i++) {
            list.add(this.selectors[i].writeToNBT(new CompoundTag()));
         }

         nbt.put("selectors", list);
      }

      @Override
      public String info() {
         StringBuilder text = new StringBuilder("[");

         for (int i = 0; i < this.selectors.length; i++) {
            if (i > 0) {
               text.append("&");
            }

            text.append(this.selectors[i].info());
         }

         return text + "]";
      }
   }

   public static class PlayerSelectorCommandSelector extends PlayerSelector {
      public String pattern;

      public PlayerSelectorCommandSelector() {
      }

      public PlayerSelectorCommandSelector(String pattern) {
         this.pattern = pattern;
      }

      @Override
      public boolean is(Player player) {
         try {
            return player instanceof ServerPlayer
               ? EntityArgument.players().parse(new StringReader(this.pattern)).findPlayers(player.getServer().createCommandSourceStack()).contains(player)
               : true;
         } catch (CommandSyntaxException var3) {
            return false;
         }
      }

      @Override
      public void readFromNBT(CompoundTag nbt) {
         this.pattern = nbt.getString("pattern");
      }

      @Override
      protected void write(CompoundTag nbt) {
         nbt.putString("pattern", this.pattern);
      }

      @Override
      public String info() {
         return this.pattern;
      }
   }

   public static class PlayerSelectorGamemode extends PlayerSelector {
      public GameType type;

      public PlayerSelectorGamemode() {
      }

      public PlayerSelectorGamemode(GameType type) {
         this.type = type;
      }

      @Override
      public boolean is(Player player) {
         return PlayerUtils.getGameType(player) == this.type;
      }

      @Override
      public void readFromNBT(CompoundTag nbt) {
         this.type = GameType.byId(nbt.getInt("mode"));
      }

      @Override
      protected void write(CompoundTag nbt) {
         nbt.putInt("mode", this.type.getId());
      }

      @Override
      public String info() {
         return this.type.getName();
      }
   }

   public static class PlayerSelectorLevel extends PlayerSelector {
      public int permissionLevel;

      public PlayerSelectorLevel() {
      }

      public PlayerSelectorLevel(int permissionLevel) {
         this.permissionLevel = permissionLevel;
      }

      @Override
      public boolean is(Player player) {
         if (player instanceof ServerPlayer) {
            ServerOpListEntry entry = (ServerOpListEntry)player.getServer().getPlayerList().getOps().get(player.getGameProfile());
            return entry != null ? entry.getLevel() >= this.permissionLevel : player.getServer().getOperatorUserPermissionLevel() >= this.permissionLevel;
         } else {
            return true;
         }
      }

      @Override
      public void readFromNBT(CompoundTag nbt) {
         this.permissionLevel = nbt.getInt("level");
      }

      @Override
      protected void write(CompoundTag nbt) {
         nbt.putInt("level", this.permissionLevel);
      }

      @Override
      public String info() {
         return "level>=" + this.permissionLevel;
      }
   }

   public static class PlayerSelectorNot extends PlayerSelector {
      public PlayerSelector selector;

      public PlayerSelectorNot() {
      }

      public PlayerSelectorNot(PlayerSelector selector) {
         this.selector = selector;
      }

      @Override
      public boolean is(Player player) {
         return !this.selector.is(player);
      }

      @Override
      public void readFromNBT(CompoundTag nbt) {
         this.selector = PlayerSelector.read(nbt.getCompound("child"));
      }

      @Override
      protected void write(CompoundTag nbt) {
         nbt.put("child", this.selector.writeToNBT(new CompoundTag()));
      }

      @Override
      public String info() {
         return "!" + this.selector.info();
      }
   }

   public static class PlayerSelectorOr extends PlayerSelector {
      public PlayerSelector[] selectors;

      public PlayerSelectorOr() {
      }

      public PlayerSelectorOr(PlayerSelector... selector) {
         this.selectors = selector;
      }

      @Override
      public boolean is(Player player) {
         for (int i = 0; i < this.selectors.length; i++) {
            if (this.selectors[i].is(player)) {
               return true;
            }
         }

         return false;
      }

      @Override
      public void readFromNBT(CompoundTag nbt) {
         ListTag list = nbt.getList("selectors", 10);
         this.selectors = new PlayerSelector[list.size()];

         for (int i = 0; i < this.selectors.length; i++) {
            this.selectors[i] = PlayerSelector.read(list.getCompound(i));
         }
      }

      @Override
      protected void write(CompoundTag nbt) {
         ListTag list = new ListTag();

         for (int i = 0; i < this.selectors.length; i++) {
            list.add(this.selectors[i].writeToNBT(new CompoundTag()));
         }

         nbt.put("selectors", list);
      }

      @Override
      public String info() {
         StringBuilder text = new StringBuilder("[");

         for (int i = 0; i < this.selectors.length; i++) {
            if (i > 0) {
               text.append("|");
            }

            text.append(this.selectors[i].info());
         }

         return text + "]";
      }
   }
}
