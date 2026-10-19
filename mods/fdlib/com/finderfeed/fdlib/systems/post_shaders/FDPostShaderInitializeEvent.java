package com.finderfeed.fdlib.systems.post_shaders;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.renderer.PostChain;
import net.neoforged.bus.api.Event;

public class FDPostShaderInitializeEvent extends Event {
   private List<FDPostShaderInitializeEvent.PostChainShaderLoadInstance> postChainRegistry = new ArrayList<>();

   public void registerPostChain(PostChainLoad load, Consumer<PostChain> onLoad) {
      this.postChainRegistry.add(new FDPostShaderInitializeEvent.PostChainShaderLoadInstance(load, onLoad));
   }

   public List<FDPostShaderInitializeEvent.PostChainShaderLoadInstance> getPostChainRegistry() {
      return this.postChainRegistry;
   }

   public record PostChainShaderLoadInstance(PostChainLoad postChainLoad, Consumer<PostChain> onLoad) {
   }
}
