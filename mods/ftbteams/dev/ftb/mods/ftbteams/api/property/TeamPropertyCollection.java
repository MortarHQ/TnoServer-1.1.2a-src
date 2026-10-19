package dev.ftb.mods.ftbteams.api.property;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public interface TeamPropertyCollection {
   <T> void forEach(BiConsumer<TeamProperty<T>, TeamPropertyValue<T>> var1);

   TeamPropertyCollection copy();

   TeamPropertyCollection copyIf(Predicate<TeamProperty<?>> var1);

   void updateFrom(TeamPropertyCollection var1);

   <T> T get(TeamProperty<T> var1);

   <T> void set(TeamProperty<T> var1, T var2);

   int size();
}
