package com.project.rental.Repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.rental.Entity.Tenant;
import com.project.rental.Entity.User;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

	Tenant save(User user);
    // You can define custom queries here if needed

	Tenant findTenantByUser(User user);

	Tenant findTenantById(Long tenantId);
}
