package me.decce.gnetum.compat.embeddium;

import java.util.List;
import me.decce.gnetum.Gnetum;
import org.embeddedt.embeddium.api.OptionGUIConstructionEvent;
import org.embeddedt.embeddium.api.options.structure.OptionPage;

public class GnetumEmbeddiumCompat {
   public static void onSodiumPagesRegister(OptionGUIConstructionEvent e) {
      Gnetum.ensureInitialized();
      List<OptionPage> pages = e.getPages();
      pages.add(new GnetumEmbeddiumPage());
   }
}
