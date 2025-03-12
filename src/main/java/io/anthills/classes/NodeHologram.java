package io.anthills.classes;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Display.Billboard;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import io.anthills.config.NodeConfig;
import io.anthills.utils.FormatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class NodeHologram {
    private final Node node;
    private Hologram textHologram;
    private final String textHoloName;
    private NodeDisplayItem displayItem;

    private static final HologramManager hologramManager = FancyHologramsPlugin.get().getHologramManager();

    public NodeHologram(Node node) {
        this.node = node;
        this.textHoloName = "node_text_" + node.getBlockVector().toString();

        initializeTextHologram();
        initializeItemDisplay();
    }

    private void initializeTextHologram() {
        Location textLocation = node.getLocation().clone().add(0.5, 1.8, 0.5);
        TextHologramData textData = new TextHologramData(textHoloName, textLocation);
        textData.setBillboard(Billboard.CENTER);
        textData.setPersistent(false);
        textHologram = hologramManager.create(textData);
        hologramManager.addHologram(textHologram);
    }

    private void initializeItemDisplay() {
        Material material = (node.getNodeOption() != null) ? node.getNodeOption().getMaterial() : Material.AIR;
        Location location = node.getLocation().clone().add(0.5, 1.4, 0.5);
        displayItem = new NodeDisplayItem(location, material);
    }

    public void update(NodeOption nodeOption) {
        updateTextHologram();
        updateItemDisplay(nodeOption);
    }

    public void update() {
        updateTextHologram();
        updateItemDisplay(node.getNodeOption());
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

    public void updateItemDisplay(NodeOption nodeOption) {
        if (node.getNodeOption() != null) {
            displayItem.update(nodeOption.getMaterial());
        }
    }

    private void refresh(Hologram hologram) {
        hologram.forceUpdate();
        hologram.refreshForViewers();
    }

    public void delete() {
        if (textHologram != null) {
            hologramManager.removeHologram(textHologram);
        }
        if (displayItem != null) {
            displayItem.delete();
        }
    }
}
