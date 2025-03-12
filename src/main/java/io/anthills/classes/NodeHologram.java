package io.anthills.classes;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.inventory.ItemStack;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import io.anthills.config.NodeConfig;
import io.anthills.enums.HologramAnimationType;
import io.anthills.utils.FormatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class NodeHologram {
    private final Node node;
    private Hologram textHologram;
    private Hologram itemHologram;
    private HologramAnimation animation;

    private static final HologramManager hologramManager = FancyHologramsPlugin.get().getHologramManager();

    public NodeHologram(Node node) {
        this.node = node;

        initializeHolograms();
    }

    private void initializeHolograms() {
        Location textLocation = node.getLocation().clone().add(0.5, 1.8, 0.5);
        String textName = "node_text_" + node.getBlockVector().toString();
        TextHologramData textData = new TextHologramData(textName, textLocation);
        textData.setBillboard(Billboard.CENTER);
        textData.setPersistent(false);
        textHologram = hologramManager.create(textData);
        hologramManager.addHologram(textHologram);

        Location itemLocation = node.getLocation().clone().add(0.5, 1.4, 0.5);
        String itemName = "node_item_" + node.getBlockVector().toString();
        ItemHologramData itemData = new ItemHologramData(itemName, itemLocation);
        itemData.setBillboard(Billboard.VERTICAL);
        itemData.setPersistent(false);
        itemHologram = hologramManager.create(itemData);
        hologramManager.addHologram(itemHologram);

        animation = new HologramAnimation(itemHologram, HologramAnimationType.PULSE, 1000L, 0.45f, 0.05f);
        animation.start();
    }

    public void update() {
        updateTextHologram();
        updateItemHologram();
    }

    private void updateTextHologram() {
        NodeConfig.NodeData data = NodeConfig.getNodeData(node.getNodeType());
        Component text = data.getNodeName();
        int cooldown = node.getRemainingCooldown();

        if (cooldown > 0) {
            int totalCooldown = data.getRegenTimeSeconds();
            double ratio = (double) cooldown / totalCooldown;
            int gb = (int) (255 * ratio);
            String formattedCooldown = FormatUtils.formatCooldown(cooldown);
            TextColor dynamicColor = TextColor.fromHexString(String.format("#%02X%02X%02X", 255, gb, gb));
            Component cooldownLine = Component.text(formattedCooldown + " Remaining", dynamicColor);
            text = text.appendNewline().append(cooldownLine);
        } else {
            Component cooldownLine = Component.text("Ready", NamedTextColor.GREEN);
            text = text.appendNewline().append(cooldownLine);
        }

        String serialized = MiniMessage.miniMessage().serialize(text);
        List<String> lines = List.of(serialized.split("\n"));
        TextHologramData textData = (TextHologramData) textHologram.getData();
        textData.setText(lines);
        refresh(textHologram);
    }

    private void updateItemHologram() {
        NodeOption option = node.getNodeOption();
        ItemHologramData itemData = (ItemHologramData) itemHologram.getData();
        itemData.setItemStack(new ItemStack(option.getMaterial()));
        refresh(itemHologram);
    }

    private void refresh(Hologram hologram) {
        hologram.forceUpdate();
        hologram.refreshForViewers();
    }

    public void delete() {
        if (itemHologram != null) {
            hologramManager.removeHologram(itemHologram);
        }
        if (textHologram != null) {
            hologramManager.removeHologram(textHologram);
        }
        if (animation != null) {
            animation.delete();
        }
    }
}
