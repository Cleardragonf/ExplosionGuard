package me.Cleardragonf.ExplosionGuard;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.scheduler.Task;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Mend {
    private final Collection<BlockSnapshot> blockSnapshot = new LinkedHashSet<BlockSnapshot>(15);

    @SuppressWarnings("unchecked")
    public Mend(final Set<Object> set) {
        super();
        this.blockSnapshot.addAll((Collection<? extends BlockSnapshot>) set);
    }

    public void heal(Task t) {
        this.blockSnapshot.forEach(
                bs->bs.restore(true, false)
        );
    }
}