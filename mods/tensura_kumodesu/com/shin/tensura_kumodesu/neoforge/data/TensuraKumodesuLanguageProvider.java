package com.shin.tensura_kumodesu.neoforge.data;

import com.shin.tensura_kumodesu.helper.TensuraKumodesuLanguage;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class TensuraKumodesuLanguageProvider extends LanguageProvider {
   public TensuraKumodesuLanguageProvider(PackOutput output) {
      super(output, "tensura_kumodesu", "en_us");
   }

   protected void addTranslations() {
      TensuraKumodesuLanguage.init(this::add);
   }
}
