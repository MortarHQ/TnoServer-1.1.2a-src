package artifacts.equipment.client;

import artifacts.client.item.renderer.ArtifactRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public interface EquipmentRenderingHandler {
   void registerArtifactRenderer(Item var1, Supplier<ArtifactRenderer> var2);

   @Nullable
   ArtifactRenderer getArtifactRenderer(Item var1);

   void renderArm(PoseStack var1, MultiBufferSource var2, int var3, AbstractClientPlayer var4, HumanoidArm var5);
}
