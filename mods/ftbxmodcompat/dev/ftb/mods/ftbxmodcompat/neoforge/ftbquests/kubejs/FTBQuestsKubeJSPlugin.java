package dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.kubejs;

import dev.architectury.event.EventResult;
import dev.ftb.mods.ftbquests.api.event.CustomFilterDisplayItemsEvent;
import dev.ftb.mods.ftbquests.events.CustomRewardEvent;
import dev.ftb.mods.ftbquests.events.CustomTaskEvent;
import dev.ftb.mods.ftbquests.events.ObjectCompletedEvent;
import dev.ftb.mods.ftbquests.events.ObjectStartedEvent;
import dev.ftb.mods.ftbquests.quest.QuestObject;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbxmodcompat.FTBXModCompat;
import dev.ftb.mods.ftbxmodcompat.kubejs.KJSUtil;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.AttachedData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

public class FTBQuestsKubeJSPlugin implements KubeJSPlugin {
   public void init() {
      CustomTaskEvent.EVENT.register(FTBQuestsKubeJSPlugin::onCustomTask);
      CustomRewardEvent.EVENT.register(FTBQuestsKubeJSPlugin::onCustomReward);
      ObjectCompletedEvent.GENERIC.register(FTBQuestsKubeJSPlugin::onCompleted);
      ObjectStartedEvent.GENERIC.register(FTBQuestsKubeJSPlugin::onStarted);
      CustomFilterDisplayItemsEvent.ADD_ITEMSTACK.register(FTBQuestsKubeJSPlugin::onCustomFilterItem);
      FTBXModCompat.LOGGER.info("[FTB Quests] Enabled KubeJS integration");
   }

   public void registerBindings(BindingRegistry bindings) {
      bindings.add("FTBQuests", FTBQuestsKubeJSWrapper.INSTANCE);
   }

   public void attachPlayerData(AttachedData<Player> event) {
      event.add("ftbquests", new FTBQuestsKubeJSPlayerData((Player)event.getParent()));
   }

   public void registerEvents(EventGroupRegistry registry) {
      registry.register(FTBQuestsKubeJSEvents.EVENT_GROUP);
   }

   private static void onCustomFilterItem(CustomFilterDisplayItemsEvent event) {
      FTBQuestsKubeJSEvents.CUSTOM_FILTER_ITEM.post(ScriptType.CLIENT, new CustomFilterItemKubeEvent(event));
   }

   public static EventResult onCustomTask(CustomTaskEvent event) {
      return KJSUtil.asArchResult(FTBQuestsKubeJSEvents.CUSTOM_TASK.post(ScriptType.SERVER, event.getTask().toString(), new CustomTaskKubeEvent(event)));
   }

   public static EventResult onCustomReward(CustomRewardEvent event) {
      return KJSUtil.asArchResult(FTBQuestsKubeJSEvents.CUSTOM_REWARD.post(ScriptType.SERVER, event.getReward().toString(), new CustomRewardKubeEvent(event)));
   }

   public static EventResult onCompleted(ObjectCompletedEvent<?> event) {
      if (event.getData().getFile().isServerSide()) {
         QuestObjectCompletedKubeEvent kjsEvent = new QuestObjectCompletedKubeEvent(event);
         QuestObject object = event.getObject();
         FTBQuestsKubeJSEvents.OBJECT_COMPLETED.post(ScriptType.SERVER, event.getObject().toString(), kjsEvent);

         for (String tag : object.getTags()) {
            FTBQuestsKubeJSEvents.OBJECT_COMPLETED.post(ScriptType.SERVER, "#" + tag, kjsEvent);
         }
      }

      return EventResult.pass();
   }

   public static EventResult onStarted(ObjectStartedEvent<?> event) {
      if (event.getData().getFile().isServerSide()) {
         QuestObjectStartedKubeEvent kjsEvent = new QuestObjectStartedKubeEvent(event);
         QuestObject object = event.getObject();
         FTBQuestsKubeJSEvents.OBJECT_STARTED.post(ScriptType.SERVER, event.getObject().toString(), kjsEvent);

         for (String tag : object.getTags()) {
            FTBQuestsKubeJSEvents.OBJECT_STARTED.post(ScriptType.SERVER, "#" + tag, kjsEvent);
         }
      }

      return EventResult.pass();
   }

   static MinecraftServer getServer(QuestObjectBase qo) {
      if (qo.getQuestFile() instanceof ServerQuestFile sqf) {
         return sqf.server;
      } else {
         throw new IllegalStateException("only use this on the server!");
      }
   }
}
