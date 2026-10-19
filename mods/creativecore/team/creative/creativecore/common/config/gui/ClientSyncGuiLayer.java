package team.creative.creativecore.common.config.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.holder.ICreativeConfigHolder;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.sync.ConfigurationClientPacket;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.control.parent.GuiColumn;
import team.creative.creativecore.common.gui.control.parent.GuiLeftRightBox;
import team.creative.creativecore.common.gui.control.parent.GuiRow;
import team.creative.creativecore.common.gui.control.parent.GuiScrollY;
import team.creative.creativecore.common.gui.control.simple.GuiButton;
import team.creative.creativecore.common.gui.control.simple.GuiCheckBox;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.dialog.DialogGuiLayer;
import team.creative.creativecore.common.gui.dialog.GuiDialogHandler;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.util.text.TextBuilder;
import team.creative.creativecore.common.util.type.tree.CheckTree;

public class ClientSyncGuiLayer extends GuiLayer {
   public final CheckTree<ConfigKey> tree;
   public final ICreativeConfigHolder root;
   public CheckTree<ConfigKey>.CheckTreeEntry currentView;
   public boolean changed = false;
   public int nextAction;
   public boolean force;

   public ClientSyncGuiLayer(ICreativeConfigHolder holder) {
      super("client-sync", 300, 200);
      this.root = holder;
      this.flow = GuiFlow.STACK_Y;
      this.registerEvent(GuiControlChangedEvent.class, x -> {
         this.changed = true;
         if (x.control instanceof ClientSyncGuiLayer.GuiTreeCheckBox && !((ClientSyncGuiLayer.GuiTreeCheckBox)x.control).value) {
            ((ClientSyncGuiLayer.GuiTreeCheckBox)x.control).partial = ((ClientSyncGuiLayer.GuiTreeCheckBox)x.control).entry.isChildEnabled();
         }
      });
      BiConsumer<ConfigKey, Boolean> setter = (x, y) -> x.forceSynchronization = y;
      Function<ConfigKey, Boolean> getter = x -> x.forceSynchronization;
      Function<ConfigKey, Collection<? extends ConfigKey>> getChildren = x -> {
         if (!x.isFolder()) {
            return null;
         }

         List<ConfigKey> keysx = new ArrayList<>();

         for (ConfigKey keyx : x.holder().fields()) {
            if (keyx.isWithoutForce(Side.CLIENT) && (!keyx.isFolder() || !keyx.holder().isEmptyWithoutForce(Side.CLIENT))) {
               keysx.add(keyx);
            }
         }

         return keysx;
      };
      List<ConfigKey> keys = new ArrayList<>();

      for (ConfigKey key : holder.fields()) {
         if (key.isWithoutForce(Side.CLIENT) && (!key.isFolder() || !key.holder().isEmptyWithoutForce(Side.CLIENT))) {
            keys.add(key);
         }
      }

      this.tree = new CheckTree<>(keys, setter, getter, getChildren);
      this.currentView = this.tree.root;
   }

   @Override
   public void create() {
      this.load(this.currentView);
   }

   public void save() {
      this.getIntegratedParent().send(new ConfigurationClientPacket(this.root, this.tree));
   }

   public void load(CheckTree<ConfigKey>.CheckTreeEntry entry) {
      if (!this.isEmpty()) {
         this.clear();
      }

      ICreativeConfigHolder holder = entry.content == null ? this.root : ((ConfigKey)entry.content).holder();
      this.add(
         new GuiLeftRightBox()
            .addLeft(new GuiLabel("path").setTitle(Component.literal("/" + String.join("/", holder.path()))))
            .addRight(new GuiButton("back", x -> this.load(entry.parent)).setTranslate("gui.back").setEnabled(entry.parent != null))
      );
      this.currentView = entry;
      GuiScrollY box = new GuiScrollY("").setExpandable().setDim(100, 100);
      this.add(box);

      for (CheckTree<ConfigKey>.CheckTreeEntry key : this.currentView.children) {
         GuiRow row = new GuiRow();
         box.add(row);
         GuiColumn first = new GuiColumn(20);
         row.addColumn(first);
         first.setVAlign(VAlign.CENTER);
         first.setAlign(Align.CENTER);
         first.add(new ClientSyncGuiLayer.GuiTreeCheckBox(key));
         GuiColumn second = (GuiColumn)new GuiColumn().setExpandableX();
         row.addColumn(second);
         String caption = translateOrDefault(
            "config." + String.join(".", holder.path()) + "." + ((ConfigKey)key.content).name + ".name", ((ConfigKey)key.content).name
         );
         String comment = "config." + String.join(".", holder.path()) + "." + ((ConfigKey)key.content).name + ".comment";
         if (((ConfigKey)key.content).isFolder()) {
            second.add(
               new GuiButton(caption, x -> this.load(key)).setTitle(Component.literal(caption)).setTooltip(new TextBuilder().translateIfCan(comment).build())
            );
         } else {
            second.add(new GuiLabel(caption).setTitle(Component.literal(caption)).setTooltip(new TextBuilder().translateIfCan(comment).build()));
         }
      }

      this.add(new GuiLeftRightBox().addLeft(new GuiButton("cancel", x -> {
         this.nextAction = 0;
         this.closeTopLayer();
      }).setTranslate("gui.cancel")).addLeft(new GuiButton("config", x -> {
         this.nextAction = 1;
         this.closeTopLayer();
      }).setTranslate("gui.config")).addRight(new GuiButton("save", x -> {
         this.nextAction = 0;
         this.force = true;
         this.save();
         this.closeTopLayer();
      }).setTitle(Component.translatable("gui.save"))));
      this.reinit();
   }

   @Override
   public void closeTopLayer() {
      if (!this.force && this.changed) {
         GuiDialogHandler.openDialog(this.getIntegratedParent(), "savechanges", (x, y) -> {
            if (y == DialogGuiLayer.DialogButton.YES) {
               this.save();
            }

            if (y != DialogGuiLayer.DialogButton.CANCEL) {
               this.force = true;
               this.closeTopLayer();
            }
         }, DialogGuiLayer.DialogButton.YES, DialogGuiLayer.DialogButton.NO, DialogGuiLayer.DialogButton.CANCEL);
      } else if (this.nextAction == 0) {
         super.closeTopLayer();
      } else if (this.nextAction == 1) {
         CreativeCore.CONFIG_OPEN.open(this.getPlayer());
      }
   }

   public static class GuiTreeCheckBox extends GuiCheckBox {
      public final CheckTree<ConfigKey>.CheckTreeEntry entry;

      public GuiTreeCheckBox(CheckTree<ConfigKey>.CheckTreeEntry entry) {
         super(((ConfigKey)entry.content).name, entry.isEnabled());
         this.entry = entry;
         if (!this.value) {
            this.partial = entry.isChildEnabled();
         }
      }

      @Override
      public boolean mouseClicked(double x, double y, int button) {
         playSound(SoundEvents.UI_BUTTON_CLICK);
         this.value = !this.value;
         if (this.value) {
            this.entry.enable();
         } else {
            this.entry.disable();
         }

         this.raiseEvent(new GuiControlChangedEvent<>(this));
         return true;
      }
   }
}
