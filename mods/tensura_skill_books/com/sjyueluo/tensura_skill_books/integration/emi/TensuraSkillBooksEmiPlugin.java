package com.sjyueluo.tensura_skill_books.integration.emi;

import com.sjyueluo.tensura_skill_books.TensuraSkillBooksMod;
import com.sjyueluo.tensura_skill_books.registry.ModItems;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.Comparison;

@EmiEntrypoint
public final class TensuraSkillBooksEmiPlugin implements EmiPlugin {
   public void register(EmiRegistry registry) {
      try {
         registry.setDefaultComparison(ModItems.SKILL_STUDY_BOOK.get(), Comparison.compareComponents());
         TensuraSkillBooksMod.LOGGER.info("[{}] EMI: using creative-tab study book variants with component comparison.", "tensura_skill_books");
      } catch (Throwable t) {
         TensuraSkillBooksMod.LOGGER.warn("[{}] EMI: failed to register skill book variants.", "tensura_skill_books", t);
      }
   }
}
