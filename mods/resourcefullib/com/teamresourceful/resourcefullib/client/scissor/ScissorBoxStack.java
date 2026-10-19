package com.teamresourceful.resourcefullib.client.scissor;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.LinkedList;

public class ScissorBoxStack {
   private final LinkedList<ScissorBox> stack = new LinkedList<>();

   public void push(int x, int y, int width, int height) {
      ScissorBox box = !this.stack.isEmpty() ? this.stack.peek().subBox(x, y, width, height) : new ScissorBox(x, y, width, height);
      box.start();
      this.stack.push(box);
   }

   public void pop() {
      if (!this.stack.isEmpty()) {
         this.stack.pop();
         if (this.stack.isEmpty()) {
            RenderSystem.disableScissor();
         } else {
            this.stack.peek().start();
         }
      }
   }
}
