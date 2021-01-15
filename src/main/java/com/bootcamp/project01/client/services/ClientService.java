package com.bootcamp.project01.client.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.bootcamp.project01.client.dto.ClientDTO;
import com.bootcamp.project01.client.entities.Client;
import com.bootcamp.project01.client.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> getPageOfClients(PageRequest pageRequest){
        Page<Client> page = repository.findAll(pageRequest);
        return page.map( c -> new ClientDTO(c));
    }

    @Transactional(readOnly = true)
	public ClientDTO getClientById(Long id) {
        Optional<Client> op = repository.findById(id);
        Client client = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));
        return new ClientDTO(client);
    }
    
    @Transactional
    public void delete(Long id){
        try{
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
        }
    }

    @Transactional
	public ClientDTO create(ClientDTO dto) {
        dto.setId(null);
        Client client = repository.save(new Client(dto));
        return new ClientDTO(client);
    }
    
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getOne(id);
            
            entity.setName(dto.getName());
            entity.setBirthDate(dto.getBirthDate());
            entity.setCpf(dto.getCpf());
            entity.setIncome(dto.getIncome());
            entity.setChildren(dto.getChildren());
            
            repository.save(entity);

            return new ClientDTO(entity);
        }
        catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found " + id);
        }
    }

}
