package com.project.nodes;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter
public class Employee {
    
    private long id;
    @NotNull
    private Integer emp_id;
    @NotNull
    private String name;

	public static Employee fromNode(Node node) {
        try (Transaction t = node.getGraphDatabase().beginTx()) {
            Employee e = new Employee(
                     node.getId(),
                    (Integer) node.getProperty("emp_id"),
                    (String) node.getProperty("name")
                    
            );
            t.success();
            return e;
        }
    }

}
