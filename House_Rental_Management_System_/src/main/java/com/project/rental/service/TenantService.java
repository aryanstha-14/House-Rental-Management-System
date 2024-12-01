package com.project.rental.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.rental.Entity.Tenant;
import com.project.rental.Entity.User;
import com.project.rental.Repository.TenantRepository;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    // Fetch all tenants
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }
    public Tenant findById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + id));
    }
    	
    // Fetch tenant by ID
    public Tenant getTenantById(Long id) {
        Optional<Tenant> tenant = tenantRepository.findById(id);
        return tenant.orElse(null);
    }

    // Save or update a tenant

    // Delete tenant by ID
    public void deleteTenant(Long id) {
        if (tenantRepository.existsById(id)) {
            tenantRepository.deleteById(id);
        }
    }

	public Tenant saveTenant(User user) {
	Tenant tenant=new Tenant();
	tenant.setUser(user);
		return tenantRepository.save(tenant);
	}
	public Tenant getTenantByUser(User user) {
		return tenantRepository.findTenantByUser(user);

	}
	public Tenant findTenantById(Long tenantId) {
		return tenantRepository.findTenantById(tenantId);

	}



}
