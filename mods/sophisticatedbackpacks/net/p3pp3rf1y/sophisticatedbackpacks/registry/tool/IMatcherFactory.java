package net.p3pp3rf1y.sophisticatedbackpacks.registry.tool;

import com.google.gson.JsonElement;
import java.util.Optional;
import java.util.function.Predicate;

public interface IMatcherFactory<T> {
   boolean appliesTo(JsonElement var1);

   Optional<Predicate<T>> getPredicate(JsonElement var1);
}
