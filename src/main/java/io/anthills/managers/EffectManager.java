package io.anthills.managers;

import io.anthills.classes.Ant;
import io.anthills.classes.Cell;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectManager {

    public static void processPlayer(Player player, Cell cell) {
        Ant ant = GlobalCache.getAnt(player.getUniqueId());

        if (cell.getColony() == null) {
            player.removePotionEffect(PotionEffectType.MINING_FATIGUE);
            return;
        }

        if (!ant.getColony().equals(cell.getColony())) {
            int amplifier = cell.getTier();
            player.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE,
                    Integer.MAX_VALUE, amplifier, false, false));
        } else {
            player.removePotionEffect(PotionEffectType.MINING_FATIGUE);
        }
    }
}
