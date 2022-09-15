package ru.csu.iit.distsys.controllers.commands;

import lombok.Data;

@Data
public class StudentCommand {
    String name;
    Integer course;
    String groupName;
}
