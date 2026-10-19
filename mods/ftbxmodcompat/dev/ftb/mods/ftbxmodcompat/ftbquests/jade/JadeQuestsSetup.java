package dev.ftb.mods.ftbxmodcompat.ftbquests.jade;

import dev.ftb.mods.ftblibrary.util.ModUtils;
import dev.ftb.mods.ftbquests.block.QuestBarrierBlock;
import dev.ftb.mods.ftbquests.block.entity.BaseBarrierBlockEntity.BarrierSavedData;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.registry.ModBlocks;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.level.block.Block;
import snownee.jade.api.IWailaClientRegistration;

public class JadeQuestsSetup {
   public static void register(IWailaClientRegistration registration) {
      registration.usePickedResult((Block)ModBlocks.BARRIER.get());
      registration.usePickedResult((Block)ModBlocks.STAGE_BARRIER.get());
      registration.registerBlockComponent(new BarrierComponentProvider(), QuestBarrierBlock.class);
      registration.registerBlockIcon(new BarrierComponentProvider(), QuestBarrierBlock.class);
      registration.addItemModNameCallback(itemStack -> {
         if (!ClientQuestFile.canClientPlayerEdit()) {
            BarrierSavedData data = (BarrierSavedData)itemStack.get((DataComponentType)ModDataComponents.BARRIER_SAVED.get());
            if (data != null) {
               return (String)ModUtils.getModName(data.skin().getItem()).orElse(null);
            }
         }

         return null;
      });
   }
}
