package rogue.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import rogue.model.creature.MonsterFactoryImpl;
import rogue.model.creature.Player;
import rogue.model.items.ItemFactoryImpl;
import rogue.model.world.Direction;
import rogue.model.world.Level;
import rogue.model.world.LevelImpl;
import rogue.model.world.Tile;

public class GameImpl implements Game {
    private List<Level> levels = new ArrayList<>();
    private Player player;

    private final Runnable nextLevel = () -> {
        var entityList = new ArrayList<Entity>();
        entityList.add(player);
        entityList.addAll(new MonsterFactoryImpl().createMonsterList(player.getLife().getLevel()));
        entityList.addAll(new ItemFactoryImpl().getItems());

        levels.add(new LevelImpl(entityList, player));
    };

    private final Supplier<Level> getCurrentLevel = () -> levels.get(levels.size() - 1);

    public final Stream<Tile> getTiles() {
        return getCurrentLevel.get().getTileStream();
    }

    public final Map<Entity, Tile> getEntityMap() {
        return getCurrentLevel.get().getEntityMap();
    }

    /** round.
     * @param direction player movement direction
     * @return nextLevel?
     */
    public final boolean round(final Direction direction) {
        boolean nextlvl = false;
        if (getCurrentLevel.get().moveEntities(direction)) {
            nextLevel.run(); // change level
            nextlvl = true;
        }
        if (player.getInventory().getScrollContainer().getActiveScroll().isPresent()) {
            player.getInventory().getScrollContainer().updateEffectDuration(1);
        }

        return nextlvl;
    }

    public final int getWidth() {
        return getCurrentLevel.get().getWidth();
    }

    public final int getHeight() {
        return getCurrentLevel.get().getHeight();
    }

    public GameImpl(final int depth, final Player player) {
        this.player = player;
        // spawn player
        nextLevel.run();
    }
}