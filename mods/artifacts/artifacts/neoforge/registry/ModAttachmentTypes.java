package artifacts.neoforge.registry;

import artifacts.component.SwimData;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class ModAttachmentTypes {
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(Keys.ATTACHMENT_TYPES, "artifacts");
   public static final Supplier<AttachmentType<SwimData>> SWIM_DATA = ATTACHMENT_TYPES.register(
      "swim_data", () -> AttachmentType.builder(SwimData::new).build()
   );
   @Deprecated(forRemoval = true, since = "1.21.5")
   public static final Supplier<AttachmentType<Unit>> ABILITY_TOGGLES = ATTACHMENT_TYPES.register(
      "ability_toggles",
      () -> AttachmentType.builder(() -> Unit.INSTANCE)
         .serialize(Codec.either(Codec.unit(Unit.INSTANCE), ResourceLocation.CODEC.listOf()).xmap(e -> Unit.INSTANCE, Either::left), unit -> false)
         .build()
   );
}
