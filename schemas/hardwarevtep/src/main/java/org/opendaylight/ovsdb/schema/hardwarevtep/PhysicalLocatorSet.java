/*
 * Copyright (C) 2014 Red Hat, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Authors : Madhu Venugopal
 */
package org.opendaylight.ovsdb.schema.hardwarevtep;

import java.util.Set;

import org.opendaylight.ovsdb.lib.notation.Column;
import org.opendaylight.ovsdb.lib.notation.UUID;
import org.opendaylight.ovsdb.lib.schema.GenericTableSchema;
import org.opendaylight.ovsdb.lib.schema.typed.MethodType;
import org.opendaylight.ovsdb.lib.schema.typed.TypedBaseTable;
import org.opendaylight.ovsdb.lib.schema.typed.TypedColumn;
import org.opendaylight.ovsdb.lib.schema.typed.TypedTable;

@TypedTable(name="Physical_Locator_Set", database="hardware_vtep", fromVersion="1.0.0")
public interface PhysicalLocatorSet extends TypedBaseTable<GenericTableSchema> {
    @TypedColumn(name="locators", method=MethodType.GETCOLUMN, fromVersion="1.0.0")
    public Column<GenericTableSchema, Set<UUID>> getLocatorsColumn();

    @TypedColumn(name="locators", method=MethodType.SETDATA, fromVersion="1.0.0")
    public void setLocators(Set<UUID> locators);
}