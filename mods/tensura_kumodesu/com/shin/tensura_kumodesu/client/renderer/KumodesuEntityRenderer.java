package com.shin.tensura_kumodesu.client.renderer;

import com.shin.tensura_kumodesu.entity.renderer.ArachneTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.ArchTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.EdeSaineTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.GreatPoisonTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.GreatTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.GreaterTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.LesserPoisonTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.LesserTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.OrthocadinahtTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.PoisonTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.QueenTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.SmallLesserTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.SmallPoisonTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.SmallTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.TaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.ZanaHorowaTaratectRenderer;
import com.shin.tensura_kumodesu.entity.renderer.ZoaEleTaratectRenderer;
import com.shin.tensura_kumodesu.registry.entities.KumodesuEntities;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;

public class KumodesuEntityRenderer {
   public static void initEntityRendering() {
   }

   public static void register() {
      EntityRendererRegistry.register(KumodesuEntities.SMALL_LESSER_TARATECT, SmallLesserTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.ORTHOCADINAHT, OrthocadinahtTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.SMALL_TARATECT, SmallTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.TARATECT, TaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.GREAT_TARATECT, GreatTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.GREATER_TARATECT, GreaterTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.ARCH_TARATECT, ArchTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.QUEEN_TARATECT, QueenTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.SMALL_POISON_TARATECT, SmallPoisonTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.ZOA_ELE, ZoaEleTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.EDE_SAINE, EdeSaineTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.ZANA_HOROWA, ZanaHorowaTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.ARACHNE, ArachneTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.LESSER_TARATECT, LesserTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.LESSER_POISON_TARATECT, LesserPoisonTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.POISON_TARATECT, PoisonTaratectRenderer::new);
      EntityRendererRegistry.register(KumodesuEntities.GREAT_POISON_TARATECT, GreatPoisonTaratectRenderer::new);
   }
}
