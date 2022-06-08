/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package com.Gruppe10.RoboRallyServer.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public enum Command {
    // @author Deniz Isikli

    // This is a very simplistic way of realizing different commands.
    FORWARD("Fwd"),
    BACKWARD("Bwd"),
    RIGHT("Turn Right"),
    LEFT("Turn Left"),
    FAST_FORWARD("Fast Fwd"),
    U_TURN("U-turn"),
    SPEED_ROUTINE("Fwd 3x"),
    SPAM_DAMAGE("SPAM Damage"),

    OPTION_LEFT_RIGHT("Left OR Right", LEFT, RIGHT),
    OPTION_WEASEL_ROUTINE("Left, Right OR U-Turn", LEFT, RIGHT, U_TURN),
    OPTION_SANDBOX_ROUTINE("Move 1-3, Back, Left, Right, U-Turn", FORWARD, FAST_FORWARD, SPEED_ROUTINE, BACKWARD, LEFT, RIGHT, U_TURN);

    final public String displayName;

    // XXX Assignment P3
    // Command(String displayName) {
    //     this.displayName = displayName;
    // }
    //
    // replaced by the code below:

    final private List<Command> options;

    Command(String displayName, Command... options) {
        this.displayName = displayName;
        this.options = Collections.unmodifiableList(Arrays.asList(options));
    }

    public boolean isInteractive() {
        return !options.isEmpty();
    }

    public List<Command> getOptions() {
        return options;
    }

}
