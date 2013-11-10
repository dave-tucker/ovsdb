package org.opendaylight.ovsdb.lib.notation;

import org.opendaylight.ovsdb.lib.notation.json.MutationSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize(using = MutationSerializer.class)

public class Mutation {
    String column;
    Mutator mutator;
    Object value;

    public Mutation(String column, Mutator mutator, Object value) {
        super();
        this.column = column;
        this.mutator = mutator;
        this.value = value;
    }
    public String getColumn() {
        return column;
    }
    public void setColumn(String column) {
        this.column = column;
    }
    public Mutator getMutator() {
        return mutator;
    }
    public void setMutator(Mutator mutator) {
        this.mutator = mutator;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}