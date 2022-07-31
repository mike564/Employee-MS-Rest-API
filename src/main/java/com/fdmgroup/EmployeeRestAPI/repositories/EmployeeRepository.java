package com.fdmgroup.EmployeeRestAPI.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdmgroup.EmployeeRestAPI.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	@Modifying
	@Query("UPDATE Employee e "
			+ "SET e.firstName = :firstName, e.lastName = :lastName, e.salary = :salary "
			+ "WHERE e.id = :id")
	void saveFullNameAndSalary(@Param("firstName") String firstName, @Param("lastName") String lastName,
									@Param("salary") BigDecimal salary,@Param("id") long id);
	
	
	@Query("SELECT e FROM Employee e "
			+ "WHERE UPPER(e.firstName) LIKE CONCAT('%',UPPER(:firstName),'%') "
			+ "AND UPPER(e.lastName) LIKE CONCAT('%',UPPER(:lastName),'%')")
	List<Employee> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);
	
}
