package com.shin.tensura_kumodesu.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.LivingEntity;

public class KumodesuModelsRegistry {
   private static final List<HierarchicalModel<? extends LivingEntity>> MODELS = new ArrayList<>();

   public static void register(HierarchicalModel<? extends LivingEntity> model) {
      MODELS.add(model);
   }

   public static List<HierarchicalModel<? extends LivingEntity>> getModels() {
      return Collections.unmodifiableList(MODELS);
   }
}
