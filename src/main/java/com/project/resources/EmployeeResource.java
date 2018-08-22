package com.project.resources;

import com.project.dao.EmployeeDAO;
import com.project.nodes.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/v1/neo4j")
@Api("/v1/neo4j")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private final EmployeeDAO employeeDAO;

    public EmployeeResource(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;

    }

    @POST
    @Path("/create_employee")
    @ApiOperation("Create Employee")
    @ApiResponses(value = {
        @ApiResponse(code = 202, message = "Employee Create successfully"),
        @ApiResponse(code = 400, message = "Could not create new Employee")
    })
    public Response createEmployee(Employee Emp) {
        long id = employeeDAO.createEmployee(Emp);
        if (id == 0) {
            Response.serverError().status(Response.Status.BAD_REQUEST);
        }
        return Response.ok(id).status(Response.Status.ACCEPTED).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/all_employees")
    @ApiOperation("show all Employees")
     @ApiResponses(value = {
        @ApiResponse(code = 202, message = "retrieved Employee list"),
        @ApiResponse(code = 400, message = "Could not fetch any results")
    })
    public Response findAllEmployees() {
        List<Employee> employeeList = employeeDAO.findAll();
        return Response.ok(employeeList).status(Response.Status.ACCEPTED).type(MediaType.APPLICATION_JSON).build();
    }

}
