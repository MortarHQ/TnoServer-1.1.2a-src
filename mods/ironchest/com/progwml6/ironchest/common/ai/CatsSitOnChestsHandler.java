package com.progwml6.ironchest.common.ai;

import java.util.HashSet;
import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.animal.Cat;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

@EventBusSubscriber(modid = "ironchest", bus = Bus.GAME)
public class CatsSitOnChestsHandler {
   @SubscribeEvent
   static void changeSittingTaskForOcelots(Post evt) {
      if (evt.getEntity().tickCount < 5 && evt.getEntity() instanceof Cat cat) {
         HashSet<WrappedGoal> goals = new HashSet<>();

         for (WrappedGoal goal : cat.goalSelector.getAvailableGoals()) {
            if (goal.getGoal().getClass() == CatSitOnBlockGoal.class) {
               goals.add(goal);
            }
         }

         for (WrappedGoal goal : goals) {
            cat.goalSelector.removeGoal(goal.getGoal());
            cat.goalSelector.addGoal(goal.getPriority(), new IronChestCatSitOnBlockGoal(cat, 0.4F));
         }
      }
   }
}
