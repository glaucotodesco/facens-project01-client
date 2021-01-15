package com.bootcamp.project01.client.services;

import com.bootcamp.project01.client.dto.ClientDTO;
import com.bootcamp.project01.client.entities.Client;
import com.bootcamp.project01.client.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository repository;

    public Page<ClientDTO> getPageOfClients(PageRequest pageRequest){
        Page<Client> page = repository.findAll(pageRequest);
        return page.map( c -> new ClientDTO(c));
    }


}
