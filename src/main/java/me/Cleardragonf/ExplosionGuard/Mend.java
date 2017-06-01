package me.Cleardragonf.ExplosionGuard;

import org.spongepowered.api.Game;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.World;

import java.util.Collection;
import java.util.LinkedHashSet;

public class Mend {
    private final Collection<BlockSnapshot> blockSnapshot = new LinkedHashSet<>(15);
    private final Cause cause;
    private final Game game;
    private Collection<BlockType> replaceable = new LinkedHashSet<>(3);

    {
        this.replaceable.add(BlockTypes.AIR);
        this.replaceable.add(BlockTypes.WATER);
        this.replaceable.add(BlockTypes.FLOWING_WATER);
    }

    public Mend(final Game game, final Cause cause, final Collection<BlockSnapshot> bs) {
        super();
        this.game = game;
        this.cause = cause;
        this.blockSnapshot.addAll(bs);
    }

    public void heal(Task t) {
        this.blockSnapshot.forEach(this::restore);

    }

    private void restore(BlockSnapshot bs){
        Vector3i pos = bs.getPosition();
        World world = bs.getLocation().get().getExtent();
        BlockState current = world.getBlock(pos);
        EntitySnapshot.Builder builder = game.getRegistry().createBuilder(EntitySnapshot.Builder.class);
        if(replaceable.contains(current.getType())){
            bs.restore(true, false);
            return;
        } else {
            //world.createEntity(builder.build().);
        }
    }

    public boolean contains(BlockSnapshot snapshot) {
        for(BlockSnapshot bs :blockSnapshot) {
            if(snapshot.getLocation().get().equals(bs.getLocation().get()))
                return true;
        }
        return false;
    }
}
