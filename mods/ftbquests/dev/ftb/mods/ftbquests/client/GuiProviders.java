package dev.ftb.mods.ftbquests.client;

import dev.architectury.fluid.FluidStack;
import dev.ftb.mods.ftblibrary.config.ConfigFromString;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.FluidConfig;
import dev.ftb.mods.ftblibrary.config.IntConfig;
import dev.ftb.mods.ftblibrary.config.ItemStackConfig;
import dev.ftb.mods.ftblibrary.config.LongConfig;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.config.ui.EditConfigScreen;
import dev.ftb.mods.ftblibrary.config.ui.EditStringConfigOverlay;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectFluidScreen;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectItemStackScreen;
import dev.ftb.mods.ftblibrary.integration.currency.CurrencyHelper;
import dev.ftb.mods.ftbquests.client.gui.SelectQuestObjectScreen;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.QuestObjectType;
import dev.ftb.mods.ftbquests.quest.loot.RewardTable;
import dev.ftb.mods.ftbquests.quest.reward.CurrencyReward;
import dev.ftb.mods.ftbquests.quest.reward.ItemReward;
import dev.ftb.mods.ftbquests.quest.reward.RandomReward;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import dev.ftb.mods.ftbquests.quest.reward.StageReward;
import dev.ftb.mods.ftbquests.quest.reward.XPLevelsReward;
import dev.ftb.mods.ftbquests.quest.reward.XPReward;
import dev.ftb.mods.ftbquests.quest.task.CheckmarkTask;
import dev.ftb.mods.ftbquests.quest.task.DimensionTask;
import dev.ftb.mods.ftbquests.quest.task.FluidTask;
import dev.ftb.mods.ftbquests.quest.task.ISingleLongValueTask;
import dev.ftb.mods.ftbquests.quest.task.ItemTask;
import dev.ftb.mods.ftbquests.quest.task.LocationTask;
import dev.ftb.mods.ftbquests.quest.task.ObservationTask;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import dev.ftb.mods.ftbquests.quest.task.TaskTypes;
import dev.ftb.mods.ftbquests.quest.translation.TranslationKey;
import dev.ftb.mods.ftbquests.util.ConfigQuestObject;
import java.util.function.BiConsumer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.phys.BlockHitResult;

public class GuiProviders {
   public static RewardType.GuiProvider defaultRewardGuiProvider(RewardType.Provider provider) {
      return (gui, quest, callback) -> {
         Reward reward = provider.create(0L, quest);
         if (reward instanceof RandomReward randomReward) {
            ConfigQuestObject<RewardTable> config = new ConfigQuestObject<>(QuestObjectType.REWARD_TABLE);
            SelectQuestObjectScreen<?> s = new SelectQuestObjectScreen<>(config, accepted -> {
               if (accepted) {
                  randomReward.setTable((RewardTable)config.getValue());
                  callback.accept(reward);
               }

               gui.run();
            });
            s.setTitle(Component.translatable("ftbquests.gui.select_reward_table"));
            s.setHasSearchBox(true);
            s.openGui();
         } else {
            ConfigGroup group = new ConfigGroup("ftbquests", accepted -> {
               if (accepted) {
                  callback.accept(reward);
               }

               gui.run();
            });
            group.setNameKey(reward.getType().getTypeId().toLanguageKey("ftbquests.reward"));
            reward.fillConfigGroup(reward.createSubGroup(group));
            new EditConfigScreen(group).openGui();
         }
      };
   }

   public static TaskType.GuiProvider defaultTaskGuiProvider(TaskType.Provider provider) {
      return (panel, quest, callback) -> {
         Task task = provider.create(0L, quest);
         if (task instanceof ISingleLongValueTask slvTask) {
            LongConfig c = new LongConfig(slvTask.getMinConfigValue(), slvTask.getMaxConfigValue());
            c.setValue(slvTask.getMinConfigValue());
            EditStringConfigOverlay<Long> overlay = new EditStringConfigOverlay(panel.getGui(), c, accepted -> {
               if (accepted) {
                  slvTask.setValue((Long)c.getValue());
                  callback.accept(task, task.getType().makeExtraNBT());
               }

               panel.run();
            }, task.getType().getDisplayName()).atMousePosition();
            overlay.setExtraZlevel(300);
            panel.getGui().pushModalPanel(overlay);
         } else {
            openSetupGui(panel.getGui(), callback, task);
         }
      };
   }

   public static void setTaskGuiProviders() {
      TaskTypes.ITEM.setGuiProvider((gui, quest, callback) -> {
         ItemStackConfig c = new ItemStackConfig(false, false);
         new SelectItemStackScreen(c, accepted -> {
            gui.run();
            if (accepted) {
               ItemTask itemTask = new ItemTask(0L, quest).setStackAndCount(c.getValue(), c.getValue().getCount());
               callback.accept(itemTask, itemTask.getType().makeExtraNBT());
            }
         }).openGui();
      });
      TaskTypes.CHECKMARK
         .setGuiProvider(
            (panel, quest, callback) -> {
               StringConfig c = new StringConfig(null);
               c.setValue("");
               EditStringConfigOverlay<String> overlay = new EditStringConfigOverlay(
                     panel.getGui(),
                     c,
                     accepted -> {
                        if (accepted) {
                           CheckmarkTask checkmarkTask = new CheckmarkTask(0L, quest);
                           checkmarkTask.setRawTitle((String)c.getValue());
                           CompoundTag extra = checkmarkTask.getType().makeExtraNBT();
                           quest.getQuestFile()
                              .getTranslationManager()
                              .addInitialTranslation(extra, quest.getQuestFile().getLocale(), TranslationKey.TITLE, (String)c.getValue());
                           callback.accept(checkmarkTask, extra);
                        }

                        panel.run();
                     },
                     TaskTypes.CHECKMARK.getDisplayName()
                  )
                  .atMousePosition();
               overlay.setExtraZlevel(300);
               panel.getGui().pushModalPanel(overlay);
            }
         );
      TaskTypes.FLUID.setGuiProvider((gui, quest, callback) -> {
         FluidConfig c = new FluidConfig(false);
         new SelectFluidScreen(c, accepted -> {
            gui.run();
            if (accepted) {
               FluidTask fluidTask = new FluidTask(0L, quest).setFluid((FluidStack)c.getValue());
               callback.accept(fluidTask, fluidTask.getType().makeExtraNBT());
            }
         }).openGui();
      });
      TaskTypes.DIMENSION.setGuiProvider((gui, quest, callback) -> {
         DimensionTask task = new DimensionTask(0L, quest).withDimension(Minecraft.getInstance().level.dimension());
         openSetupGui(gui, callback, task);
      });
      TaskTypes.OBSERVATION.setGuiProvider((gui, quest, callback) -> {
         ObservationTask task = new ObservationTask(0L, quest);
         if (Minecraft.getInstance().hitResult instanceof BlockHitResult bhr) {
            Block block = Minecraft.getInstance().level.getBlockState(bhr.getBlockPos()).getBlock();
            task.setToObserve(BuiltInRegistries.BLOCK.getKey(block).toString());
         }

         openSetupGui(gui, callback, task);
      });
      TaskTypes.LOCATION.setGuiProvider((gui, quest, callback) -> {
         LocationTask task = new LocationTask(0L, quest);
         Minecraft mc = Minecraft.getInstance();
         if (mc.hitResult instanceof BlockHitResult bhr && mc.level.getBlockEntity(bhr.getBlockPos()) instanceof StructureBlockEntity structure) {
            task.initFromStructure(structure);
            callback.accept(task, task.getType().makeExtraNBT());
         } else {
            openSetupGui(gui, callback, task);
         }
      });
   }

   private static void openSetupGui(Runnable gui, BiConsumer<Task, CompoundTag> callback, Task task) {
      ConfigGroup group = new ConfigGroup("ftbquests", accepted -> {
         gui.run();
         if (accepted) {
            callback.accept(task, task.getType().makeExtraNBT());
         }
      });
      group.setNameKey(task.getType().getTypeId().toLanguageKey("ftbquests.task"));
      task.fillConfigGroup(task.createSubGroup(group));
      new EditConfigScreen(group).openGui();
   }

   public static void setRewardGuiProviders() {
      RewardTypes.ITEM.setGuiProvider((gui, quest, callback) -> {
         ItemStackConfig c = new ItemStackConfig(false, false);
         new SelectItemStackScreen(c, accepted -> {
            if (accepted) {
               ItemStack copy = c.getValue().copy();
               copy.setCount(1);
               ItemReward reward = new ItemReward(0L, quest, copy, c.getValue().getCount());
               callback.accept(reward);
            }

            gui.run();
         }).openGui();
      });
      simpleRewardProvider(RewardTypes.XP, XPReward::new, (ConfigFromString<Integer>)Util.make(new IntConfig(1, Integer.MAX_VALUE), c -> c.setValue(100)));
      simpleRewardProvider(
         RewardTypes.XP_LEVELS, XPLevelsReward::new, (ConfigFromString<Integer>)Util.make(new IntConfig(1, Integer.MAX_VALUE), c -> c.setValue(5))
      );
      simpleRewardProvider(RewardTypes.STAGE, StageReward::new, new StringConfig());
      if (CurrencyHelper.getInstance().getProvider().isValidProvider()) {
         simpleRewardProvider(
            RewardTypes.CURRENCY, CurrencyReward::new, (ConfigFromString<Integer>)Util.make(new IntConfig(1, Integer.MAX_VALUE), c -> c.setValue(1))
         );
      }
   }

   private static <T> void simpleRewardProvider(RewardType type, GuiProviders.RewardFactory<T> factory, ConfigFromString<T> cfg) {
      type.setGuiProvider((panel, quest, callback) -> {
         EditStringConfigOverlay<T> overlay = new EditStringConfigOverlay(panel.getGui(), cfg, accepted -> {
            if (accepted) {
               callback.accept(factory.create(0L, quest, (T)cfg.getValue()));
            }

            panel.run();
         }, type.getDisplayName()).atMousePosition();
         panel.getGui().pushModalPanel(overlay);
      });
   }

   private interface RewardFactory<T> {
      Reward create(long var1, Quest var3, T var4);
   }
}
