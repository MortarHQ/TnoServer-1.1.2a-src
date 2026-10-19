package dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.guideme;

import dev.ftb.mods.ftblibrary.integration.docsmod.DocsModRegistry;
import guideme.GuidesCommon;
import guideme.PageAnchor;

public class GuideMECompat {
   public static void init() {
      DocsModRegistry.INSTANCE.registerDocsMod("guideme", (player, bookId, pageId, anchor) -> {
         if (pageId == null) {
            GuidesCommon.openGuide(player, bookId);
         } else {
            GuidesCommon.openGuide(player, bookId, new PageAnchor(pageId, anchor.isEmpty() ? null : anchor));
         }
      });
   }
}
