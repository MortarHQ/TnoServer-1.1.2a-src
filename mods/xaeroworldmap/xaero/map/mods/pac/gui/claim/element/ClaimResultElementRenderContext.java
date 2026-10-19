package xaero.map.mods.pac.gui.claim.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import xaero.map.mods.pac.gui.claim.ClaimResultElement;

public class ClaimResultElementRenderContext {
   public VertexConsumer guiIconBuffer;
   public VertexConsumer textBackgroundBuffer;
   public final List<ClaimResultElement> toDelete = new ArrayList<>();
}
