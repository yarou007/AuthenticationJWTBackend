package com.AuthBack.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AuthBack.springboot.models.Role;
import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	 
	Optional<Role>  findByAuthority(String authority);

}
