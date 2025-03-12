package io.anthills.persistence;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import io.anthills.Plugin;
import io.anthills.classes.Ant;
import io.anthills.classes.Cell;
import io.anthills.classes.Colony;
import io.anthills.classes.Node;
import io.anthills.managers.data.GlobalCache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PersistenceManager {
    private JdbcConnectionSource connectionSource;
    private Dao<ColonyRecord, String> colonyDao;
    private Dao<AntRecord, String> antDao;
    private Dao<CellRecord, String> cellDao;
    private Dao<NodeRecord, String> nodeDao;

    public PersistenceManager(String databaseUrl) throws Exception {
        connectionSource = new JdbcConnectionSource(databaseUrl);
        colonyDao = DaoManager.createDao(connectionSource, ColonyRecord.class);
        antDao = DaoManager.createDao(connectionSource, AntRecord.class);
        cellDao = DaoManager.createDao(connectionSource, CellRecord.class);
        nodeDao = DaoManager.createDao(connectionSource, NodeRecord.class);

        TableUtils.createTableIfNotExists(connectionSource, ColonyRecord.class);
        TableUtils.createTableIfNotExists(connectionSource, AntRecord.class);
        TableUtils.createTableIfNotExists(connectionSource, CellRecord.class);
        TableUtils.createTableIfNotExists(connectionSource, NodeRecord.class);
    }

    public void loadAll() throws Exception {
        // 1. Load Colonies
        Map<UUID, Colony> colonyQueenMap = new HashMap<>();
        colonyDao.queryForAll().stream().forEach(colonyRecord -> {
            Colony colony = colonyRecord.toColony();
            String queenId = colonyRecord.getQueenId();
            if (queenId != null) {
                colonyQueenMap.put(UUID.fromString(queenId), colony);
            }

            GlobalCache.registerColony(colony);
        });

        // 2. Load Ants
        antDao.queryForAll().stream().forEach(antRecord -> {
            Ant ant = antRecord.toAnt();
            String colonyId = antRecord.getColonyId();
            if (colonyId != null) {
                Colony colony = GlobalCache.getColony(UUID.fromString(colonyId));
                if (colony != null) {
                    ant.setColony(colony);
                    colony.addMember(ant);

                    if (colonyQueenMap.containsKey(ant.getPlayerId())) {
                        colony.setQueen(ant);
                    }
                }
            }
            GlobalCache.registerAnt(ant);
        });

        // 4. Load Cells
        cellDao.queryForAll().stream().forEach(cellRecord -> {
            Cell cell = cellRecord.toCell();
            String colonyId = cellRecord.getColonyId();
            if (colonyId != null) {
                Colony colony = GlobalCache.getColony(UUID.fromString(colonyId));
                if (colony != null) {
                    cell.setColony(colony);
                    colony.addCell(cell);
                }
            }
            GlobalCache.registerCell(cell);
        });

        // 5. Load Nodes
        nodeDao.queryForAll().stream().map(NodeRecord::toNode).forEach(node -> {
            GlobalCache.registerNode(node);
        });
    }

    public void saveAll() throws Exception {
        // 1. Save Colonies
        GlobalCache.getColonies().values().stream().map(ColonyRecord::new).forEach(ColonyRecord -> {
            try {
                colonyDao.createOrUpdate(ColonyRecord);
            } catch (Exception e) {
                Plugin.getInstance().getLogger().severe("Failed to save colony record: " + e.getMessage());
            }
        });

        // 2. Save Ants
        GlobalCache.getAnts().values().stream().map(AntRecord::new).forEach(antRecord -> {
            try {
                antDao.createOrUpdate(antRecord);
            } catch (Exception e) {
                Plugin.getInstance().getLogger().severe("Failed to save ant record: " + e.getMessage());
            }
        });

        // 3. Save Cells
        GlobalCache.getCells().values().stream().map(CellRecord::new).forEach(cellRecord -> {
            try {
                cellDao.createOrUpdate(cellRecord);
            } catch (Exception e) {
                Plugin.getInstance().getLogger().severe("Failed to save cell record: " + e.getMessage());
            }
        });

        // 4. Save Nodes
        GlobalCache.getNodes().values().stream().map(NodeRecord::new).forEach(nodeRecord -> {
            try {
                nodeDao.createOrUpdate(nodeRecord);
            } catch (Exception e) {
                Plugin.getInstance().getLogger().severe("Failed to save node record: " + e.getMessage());
            }
        });
    }

    public void deleteColony(Colony colony) throws Exception {
        ColonyRecord record = new ColonyRecord(colony);
        colonyDao.deleteById(record.getId());
    }

    public void deleteAnt(Ant ant) throws Exception {
        AntRecord record = new AntRecord(ant);
        antDao.deleteById(record.getId());
    }

    public void deleteCell(Cell cell) throws Exception {
        CellRecord record = new CellRecord(cell);
        cellDao.deleteById(record.getId());
    }

    public void deleteNode(Node node) throws Exception {
        NodeRecord record = new NodeRecord(node);
        nodeDao.deleteById(record.getId());
    }

    public void close() throws Exception {
        connectionSource.close();
    }
}
