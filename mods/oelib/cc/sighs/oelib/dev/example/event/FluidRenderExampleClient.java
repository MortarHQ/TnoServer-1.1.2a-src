package cc.sighs.oelib.dev.example.event;

import cc.sighs.oelib.dev.DevConfig;
import cc.sighs.oelib.dev.example.net.OpenGuiPacket;
import cc.sighs.oelib.event.Subscribe;
import cc.sighs.oelib.event.events.ClientTickEvent;
import cc.sighs.oelib.registry.extra.KeyMappingRegister;
import java.util.Optional;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.BlockHitResult;

public class FluidRenderExampleClient {
   private static KeyMapping openExample;

   public static void onRegisterKeys() {
      if (DevConfig.UNIT.get().enableExampleContent()) {
         openExample = new KeyMapping("key.oelib.open_fluid_example", 71, "key.categories.oelib");
         KeyMappingRegister.register(openExample);
      }
   }

   @Subscribe
   public static void onClientTick(ClientTickEvent.Post event) {
      if (DevConfig.UNIT.get().enableExampleContent() && openExample != null && openExample.consumeClick()) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && mc.hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            MutableComponent title = Component.literal("Fluid Example");
            Registry<Enchantment> registry = mc.level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
            Optional<Reference<Enchantment>> holder = registry.getHolder(Enchantments.EFFICIENCY);
            if (holder.isPresent()) {
               OpenGuiPacket packet = new OpenGuiPacket(pos, title, (Holder<Enchantment>)holder.get());
               packet.sendToServer();
            }
         }
      }
   }
}
