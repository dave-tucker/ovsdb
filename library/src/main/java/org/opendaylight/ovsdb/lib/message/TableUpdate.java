/*
 *
 *  * Copyright (C) 2014 EBay Software Foundation
 *  *
 *  * This program and the accompanying materials are made available under the
 *  * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *  * and is available at http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Authors : Ashwin Raveendran, Madhu Venugopal
 *
 */

package org.opendaylight.ovsdb.lib.message;

import java.util.Map;

import org.opendaylight.ovsdb.lib.notation.Row;
import org.opendaylight.ovsdb.lib.notation.UUID;
import org.opendaylight.ovsdb.lib.schema.TableSchema;

import com.google.common.collect.Maps;

public class TableUpdate<E extends TableSchema<E>> {
    private Map<UUID, RowUpdate<E>> rows;

    public Map<UUID, RowUpdate<E>> getRows() {
        return rows;
    }

    public class RowUpdate <E extends TableSchema<E>> {
        private UUID uuid;
        private Row<E> old;
        private Row<E> new_;

        public RowUpdate (UUID uuid, Row<E> old, Row<E> new_) {
            this.uuid = uuid;
            this.old = old;
            this.new_ = new_;
        }

        public UUID getUuid() {
            return this.uuid;
        }

        public Row<E> getOld() {
            return old;
        }

        public void setOld(Row<E> old) {
            this.old = old;
        }

        public Row<E> getNew() {
            return new_;
        }

        public void setNew(Row<E> new_) {
            this.new_ = new_;
        }

        @Override
        public String toString() {
            return "RowUpdate [uuid=" + uuid + ", old=" + old + ", new_=" + new_
                    + "]";
        }
    }

    public TableUpdate() {
        super();
        rows = Maps.newHashMap();
    }

    public void addRow(UUID uuid, Row<E> old, Row<E> new_) {
        rows.put(uuid, new RowUpdate<E>(uuid, old, new_));
    }

    public Row<E> getOld(UUID uuid) {
        RowUpdate<E> rowUpdate = rows.get(uuid);
        if (rowUpdate == null) return null;
        return rowUpdate.getOld();
    }

    public Row<E> getNew(UUID uuid) {
        RowUpdate<E> rowUpdate = rows.get(uuid);
        if (rowUpdate == null) return null;
        return rowUpdate.getNew();
    }

    @Override
    public String toString() {
        return "TableUpdate [" + rows + "]";
    }
}
