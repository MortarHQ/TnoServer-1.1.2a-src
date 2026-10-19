package org.crypticdev.ancient_artifacts;

import java.util.List;
import java.util.function.Function;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.crypticdev.ancient_artifacts.client.AmuletLayer;
import org.crypticdev.ancient_artifacts.client.BeltLayer;
import org.crypticdev.ancient_artifacts.client.BootsLayer;
import org.crypticdev.ancient_artifacts.client.BraceletsLayer;
import org.crypticdev.ancient_artifacts.client.ChestEmblemLayer;
import org.crypticdev.ancient_artifacts.client.FinLayer;
import org.crypticdev.ancient_artifacts.client.HornsLayer;
import org.crypticdev.ancient_artifacts.client.PendantLayer;
import org.crypticdev.ancient_artifacts.client.TasselsLayer;

public class AncientArtifactsClient {
   public static List<Function<RenderLayerParent, RenderLayer>> models = List.of(
      HornsLayer::new,
      PendantLayer::new,
      BootsLayer::new,
      AmuletLayer::new,
      BeltLayer::new,
      TasselsLayer::new,
      BraceletsLayer::new,
      ChestEmblemLayer::new,
      FinLayer::new
   );

   public static void initClient() {
   }
}
