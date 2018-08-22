package com.project.dao;

import com.project.labels.ProjectLabels;
import com.project.nodes.Employee;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.Iterators;

public class EmployeeDAO {

    private final GraphDatabaseService graphDb;

    public EmployeeDAO(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;

    }

    public long createEmployee(Employee entity) {
        long id = 0;
        try (Transaction tx = graphDb.beginTx()) {
            Node node = graphDb.createNode(ProjectLabels.Employee);
            node.setProperty("emp_id", entity.getEmp_id());
            node.setProperty("name", entity.getName());
            tx.success();
            id = node.getId();
            tx.close();
        }        
        return id;
    }

    public List<Employee> findAll() {
        Result result = graphDb.execute("MATCH (e:Employee) RETURN e");
        Iterator<Node> nodes = result.columnAs("e");
        return fromEmployeeNodes(nodes);
    }

    private LinkedList<Employee> fromEmployeeNodes(Iterator<Node> nodes) {
        LinkedList<Employee> nodeList = new LinkedList<>();
        for (Node node : Iterators.asIterable(nodes)) {
            nodeList.add(Employee.fromNode(node));
        }
        return nodeList;
    }

}
