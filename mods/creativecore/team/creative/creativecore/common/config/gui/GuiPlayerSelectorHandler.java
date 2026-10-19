package team.creative.creativecore.common.config.gui;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.control.collection.GuiListBoxBase;
import team.creative.creativecore.common.gui.control.simple.GuiButton;
import team.creative.creativecore.common.gui.control.simple.GuiStateButton;
import team.creative.creativecore.common.gui.control.simple.GuiTextfield;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.util.player.PlayerSelector;
import team.creative.creativecore.common.util.registry.NamedHandlerRegistry;
import team.creative.creativecore.common.util.text.TextMapBuilder;

@OnlyIn(Dist.CLIENT)
@Environment(EnvType.CLIENT)
public abstract class GuiPlayerSelectorHandler<T extends PlayerSelector> {
   public static final NamedHandlerRegistry<GuiPlayerSelectorHandler> REGISTRY = new NamedHandlerRegistry<>(null);
   private String name;

   public static GuiPlayerSelectorHandler get(PlayerSelector selector) {
      return REGISTRY.get(PlayerSelector.REGISTRY.getId(selector));
   }

   public String getName() {
      return this.name;
   }

   public abstract void createControls(PlayerSelectorDialog var1, PlayerSelector var2);

   public abstract T parseSelector(PlayerSelectorDialog var1);

   public void onChanged(PlayerSelectorDialog gui, GuiControlChangedEvent event) {
   }

   static {
      REGISTRY.register("and", new GuiPlayerSelectorHandler.GuiPlayerSelectorHandlerMultiple<PlayerSelector.PlayerSelectorAnd>() {
         public PlayerSelector.PlayerSelectorAnd parseSelector(PlayerSelector[] selectors) {
            return new PlayerSelector.PlayerSelectorAnd(selectors);
         }
      });
      REGISTRY.register("or", new GuiPlayerSelectorHandler.GuiPlayerSelectorHandlerMultiple<PlayerSelector.PlayerSelectorOr>() {
         public PlayerSelector.PlayerSelectorOr parseSelector(PlayerSelector[] selectors) {
            return new PlayerSelector.PlayerSelectorOr(selectors);
         }
      });
      REGISTRY.register(
         "not",
         new GuiPlayerSelectorHandler<PlayerSelector.PlayerSelectorNot>() {
            @Override
            public void createControls(PlayerSelectorDialog gui, PlayerSelector selector) {
               gui.add(
                  new GuiPlayerSelectorButton(
                     "not", selector instanceof PlayerSelector.PlayerSelectorNot select ? select.selector : new PlayerSelector.PlayerSelectorLevel(0)
                  )
               );
            }

            public PlayerSelector.PlayerSelectorNot parseSelector(PlayerSelectorDialog gui) {
               GuiPlayerSelectorButton button = gui.get("not");
               PlayerSelector selector = button.get();
               return selector != null ? new PlayerSelector.PlayerSelectorNot(selector) : null;
            }
         }
      );
      REGISTRY.register(
         "level",
         new GuiPlayerSelectorHandler<PlayerSelector.PlayerSelectorLevel>() {
            @Override
            public void createControls(PlayerSelectorDialog gui, PlayerSelector selector) {
               gui.add(
                  new GuiTextfield("content", selector instanceof PlayerSelector.PlayerSelectorLevel select ? select.permissionLevel + "" : "0")
                     .setNumbersOnly()
               );
            }

            public PlayerSelector.PlayerSelectorLevel parseSelector(PlayerSelectorDialog gui) {
               GuiTextfield text = gui.get("content");
               return new PlayerSelector.PlayerSelectorLevel(text.parseInteger());
            }
         }
      );
      REGISTRY.register(
         "mode",
         new GuiPlayerSelectorHandler<PlayerSelector.PlayerSelectorGamemode>() {
            @Override
            public void createControls(PlayerSelectorDialog gui, PlayerSelector selector) {
               gui.add(
                  new GuiStateButton<>(
                     "mode",
                     selector instanceof PlayerSelector.PlayerSelectorGamemode select ? select.type : GameType.SURVIVAL,
                     new TextMapBuilder<GameType>().addComponent(GameType.values(), x -> x.getShortDisplayName())
                  )
               );
            }

            public PlayerSelector.PlayerSelectorGamemode parseSelector(PlayerSelectorDialog gui) {
               GuiStateButton<GameType> mode = gui.get("mode");
               return new PlayerSelector.PlayerSelectorGamemode(mode.selected());
            }
         }
      );
      REGISTRY.register("selector", new GuiPlayerSelectorHandler<PlayerSelector.PlayerSelectorCommandSelector>() {
         @Override
         public void createControls(PlayerSelectorDialog gui, PlayerSelector selector) {
            gui.add(new GuiTextfield("content", selector instanceof PlayerSelector.PlayerSelectorCommandSelector select ? select.pattern : "@a[]"));
         }

         public PlayerSelector.PlayerSelectorCommandSelector parseSelector(PlayerSelectorDialog gui) {
            GuiTextfield text = gui.get("content");
            return text.getText().isEmpty() ? null : new PlayerSelector.PlayerSelectorCommandSelector(text.getText());
         }
      });
   }

   public abstract static class GuiPlayerSelectorHandlerMultiple<T extends PlayerSelector> extends GuiPlayerSelectorHandler<T> {
      public PlayerSelector[] getChildren(PlayerSelector selector) {
         if (selector instanceof PlayerSelector.PlayerSelectorAnd) {
            return ((PlayerSelector.PlayerSelectorAnd)selector).selectors;
         } else {
            return selector instanceof PlayerSelector.PlayerSelectorOr ? ((PlayerSelector.PlayerSelectorOr)selector).selectors : new PlayerSelector[]{selector};
         }
      }

      @Override
      public void createControls(PlayerSelectorDialog gui, PlayerSelector selector) {
         PlayerSelector[] selectors = this.getChildren(selector);
         List<GuiPlayerSelectorButton> buttons = new ArrayList<>();
         if (selectors != null) {
            for (int i = 0; i < selectors.length; i++) {
               buttons.add(new GuiPlayerSelectorButton(i + "", selectors[i]));
            }
         }

         GuiListBoxBase<GuiPlayerSelectorButton> list = new GuiListBoxBase<>("list", true, buttons);
         gui.add(list);
         gui.add(new GuiButton("add", x -> list.addItem(new GuiPlayerSelectorButton("new", new PlayerSelector.PlayerSelectorLevel(0)))));
      }

      @Override
      public T parseSelector(PlayerSelectorDialog gui) {
         GuiListBoxBase<GuiPlayerSelectorButton> list = gui.get("list");
         PlayerSelector[] selectors = new PlayerSelector[list.size()];

         for (int i = 0; i < selectors.length; i++) {
            selectors[i] = list.get(i).get();
         }

         return selectors.length > 0 ? this.parseSelector(selectors) : null;
      }

      public abstract T parseSelector(PlayerSelector[] var1);
   }
}
