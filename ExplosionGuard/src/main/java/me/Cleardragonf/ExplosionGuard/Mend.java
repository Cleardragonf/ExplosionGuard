package me.Cleardragonf.ExplosionGuard;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class Mend {
    private final Collection<BlockSnapshot> blockSnapshot = new LinkedHashSet<BlockSnapshot>(15);

    public Mend(final List<Location<World>> tansactions) {
    	super();
    	tansactions.forEach(loc -> blockSnapshot.add(loc.createSnapshot())); 
    }

    public void heal(Task t) {
        this.blockSnapshot.forEach(
                bs->bs.restore(true, BlockChangeFlag.NONE)
        );
    }
}