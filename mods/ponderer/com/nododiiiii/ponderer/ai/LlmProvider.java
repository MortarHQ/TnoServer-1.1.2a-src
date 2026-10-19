package com.nododiiiii.ponderer.ai;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface LlmProvider {
   CompletableFuture<String> generate(String var1, List<LlmProvider.ContentBlock> var2, String var3, String var4, String var5);

   sealed interface ContentBlock permits LlmProvider.ContentBlock.Text, LlmProvider.ContentBlock.Image {
      record Image(String base64Data, String mediaType) implements LlmProvider.ContentBlock {
      }

      record Text(String text) implements LlmProvider.ContentBlock {
      }
   }
}
