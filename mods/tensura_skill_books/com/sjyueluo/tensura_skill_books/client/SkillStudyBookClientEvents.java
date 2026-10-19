package com.sjyueluo.tensura_skill_books.client;

import com.sjyueluo.tensura_skill_books.registry.ModItems;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.ModelEvent.ModifyBakingResult;

@EventBusSubscriber(modid = "tensura_skill_books", value = Dist.CLIENT, bus = Bus.MOD)
public final class SkillStudyBookClientEvents {
   private static final ModelResourceLocation STUDY_BOOK_MODEL = ModelResourceLocation.inventory(
      ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "skill_study_book")
   );

   private SkillStudyBookClientEvents() {
   }

   @SubscribeEvent
   public static void onModifyBakingResult(ModifyBakingResult event) {
      BakedModel model = (BakedModel)event.getModels().get(STUDY_BOOK_MODEL);
      if (model != null && !(model instanceof SkillStudyBookBakedModel)) {
         event.getModels().put(STUDY_BOOK_MODEL, new SkillStudyBookBakedModel(model));
      }
   }

   @SubscribeEvent
   public static void onRegisterItemDecorations(RegisterItemDecorationsEvent event) {
      event.register((ItemLike)ModItems.SKILL_STUDY_BOOK.get(), new SkillStudyBookItemDecorator());
   }
}
