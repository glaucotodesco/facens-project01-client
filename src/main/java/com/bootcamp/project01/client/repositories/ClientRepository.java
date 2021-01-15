package com.bootcamp.project01.client.repositories;

import com.bootcamp.project01.client.entities.Client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long>{
    
}
