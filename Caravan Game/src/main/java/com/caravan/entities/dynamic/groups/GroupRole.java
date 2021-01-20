package com.caravan.entities.dynamic.groups;

import com.caravan.Role;

import java.util.ArrayList;
import java.util.List;

public enum GroupRole implements Role {
    NORTH_ROBBER, NORTH_NEUTRAL, NORTH_POLICE,
    SOUTH_ROBBER, SOUTH_NEUTRAL, SOUTH_POLICE,
    CENTRAL_ROBBER, CENTRAL_NEUTRAL, CENTRAL_POLICE,
    WEST_ROBBER, WEST_NEUTRAL, WEST_POLICE,
    EAST_ROBBER, EAST_NEUTRAL, EAST_POLICE;

    private final List<Group> groups = new ArrayList<>();

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public List<Group> getList() {
        return groups;
    }
}
